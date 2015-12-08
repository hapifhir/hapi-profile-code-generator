package org.cdscollaborative.tools.fhir.utils;

import guru.mwangaza.common.util.io.ResourceLoadingUtils;

import java.io.File;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.cdscollaborative.tools.fhir.codegenerator.config.CodeGeneratorConfigurator;
import org.cdscollaborative.tools.fhir.model.FhirExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.RuntimeResourceDefinition;
import ca.uhn.fhir.model.api.IResource;
import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt.Type;
import ca.uhn.fhir.model.dstu2.resource.Bundle;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;
import ca.uhn.fhir.model.dstu2.valueset.DataTypeEnum;
import ca.uhn.fhir.parser.DataFormatException;

/**
 * Class is responsible for loading all FHIR Core resources (defined in FHIR as profiles)
 * and generating an index by profile name of all unmarshalled
 * profile definition objects as well as an index mapping a resource
 * to its HAPI FHIR corresponding classes.
 * 
 * @author Claude Nanjo
 *
 */
public class FhirResourceManager {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(FhirResourceManager.class);
	public static final String CORE_PROFILE_PREFIX = "http://hl7.org/fhir/StructureDefinition/";
	
	private Map<String,Class<?>> resourceNameToClassMap;
	private Map<String, StructureDefinition> profileNameToProfileMap;
	private Map<String,String> primitiveMap;
	private FhirContext ctx;
	private FhirExtensionManager extensionManager;
	private Map<String, String> profileUriToResourceMap;
	private Map<String, String> profileNameToBaseResourceNameMap;
	
	public FhirResourceManager() {
		super();
		resourceNameToClassMap = new HashMap<String,Class<?>>();
		profileNameToProfileMap = new HashMap<String,StructureDefinition>();
		profileUriToResourceMap = new HashMap<String, String>();
		profileNameToBaseResourceNameMap = new HashMap<String, String>();
		loadProfileUriToResourceMap();
		populatePrimitiveMap();
		ctx = FhirContext.forDstu2();
	}
	
	/**
	 * Returns a resource-name-to-resource-class index
	 * 
	 * @return
	 */
	public Map<String, Class<?>> getResourceNameToClassMap() {
		return resourceNameToClassMap;
	}
	
	public Class<?> getClassNameForResource(String profileName) {
		return resourceNameToClassMap.get(profileName);
	}

	/**
	 * Returns a profile-name-to-profile index
	 * 
	 * @return
	 */
	public Map<String,StructureDefinition> getProfileIndex() {
		return profileNameToProfileMap;
	}
	
	/**
	 * Method returns profile associated with this profile name.
	 * If no profile with that name exists, an exception is thrown.
	 * <p>
	 * @param profileName The profile to retrieve from the index.
	 * @return
	 */
	public StructureDefinition getProfile(String profileName) {
		StructureDefinition definition = profileNameToProfileMap.get(profileName);
		if(definition == null) {
			LOGGER.error("No profile with name " + profileName + " was found");
			throw new RuntimeException("No profile with name " + profileName + " was found");
		}
		return definition;
	}
	
	/**
	 * Load profiles from CodeGeneratorConfigurator
	 * 
	 * In the case of profile directories, all files in the directory are treated
	 * as profile files and loaded. 
	 * 
	 * Precondition: All profile directory paths point to directories that contain 
	 * only profile files.
	 * 
	 * @param configurator
	 */
	public void loadResourceProfiles(CodeGeneratorConfigurator configurator) {
		for(String profileFilePath: configurator.getProfileFilePaths()) {
			loadResourceProfile(profileFilePath);
		}
		
		for(String directoryPath : configurator.getProfileDirectoryPaths()) {
			File dir = new File(directoryPath);
			if(dir.exists() && dir.isDirectory()) {
				File[] profileFiles = dir.listFiles();
				for(File profileFile : profileFiles) {
					loadResourceProfile(profileFile.getAbsolutePath());
				}
			}
		}
	}

	/**
	 * Loads profile from file resource
	 * 
	 * @param profileFilePath
	 */
	private void loadResourceProfile(String profileFilePath) {
		try {
			loadResourceProfiles(ResourceLoadingUtils.getReaderFromFilePath(profileFilePath));
		} catch(Exception e) {
			LOGGER.error("Error loading resources from configurator " + profileFilePath, e);
			throw new RuntimeException("Error loading resources from configurator " + profileFilePath);
		}
	}
	
	/**
	 * Method loads the profile(s) from the Reader argument.
	 * 
	 * @param resourceProfiles - A reader to a profile definition file or a bundle of profiles
	 */
	public void loadResourceProfiles(Reader resourceProfiles) {
		try (Reader reader = resourceProfiles) {
			IResource resource = (IResource) ctx.newXmlParser().parseResource(resourceProfiles);
			if(resource instanceof Bundle) {
				List<Bundle.Entry> entries = ((Bundle)resource).getEntry();
				for(Bundle.Entry entry : entries) {
					if(entry.getResource() instanceof StructureDefinition) {
						StructureDefinition profile = (StructureDefinition)entry.getResource();
						populateProfileMaps(profile);
					}
				}
			} else if(resource instanceof StructureDefinition) {
				populateProfileMaps((StructureDefinition)resource);
			} else {
				throw new RuntimeException("Unknown resource " + resource.getClass().getCanonicalName());
			}
			populateResourceNameToClassMap();
		} catch(Exception e) {
			LOGGER.error("Error loading profile from reader", e);
			throw new RuntimeException("Error loading profile from reader", e);
		}
	}

	protected void populateProfileMaps(StructureDefinition profile) {
		profileNameToProfileMap.put(profile.getName(),profile);
		populateProfileToBaseResourceMap(profile);
	}

	protected void populateProfileToBaseResourceMap(StructureDefinition profile) {
		String profileBase = profile.getBase();
		if(profileBase != null) {
			String baseResource = getBaseResourceFromProfileName(profileBase);
			if(baseResource != null) {
				profileNameToBaseResourceNameMap.put(profile.getName(), baseResource);
				if(profile.getId() != null && !profile.getName().equalsIgnoreCase(profile.getId().getValue()) && !profile.getId().equals("http://hl7.org/fhir/StructureDefinition/Resource")) {
					profileNameToBaseResourceNameMap.put(profile.getId().getValue(), baseResource);
				}
			}
		}
	}
	
	/**
	 * Method generates the resource-name-to-class index for all resources
	 * defined in a profile.
	 * 
	 * @param ctx
	 */
	private void populateResourceNameToClassMap() {
		for(StructureDefinition profile : profileNameToProfileMap.values()) {
	    	String profileName = profile.getName();
	    	try {
	    		Class<?> clazz = ctx.getResourceDefinition(profileName).getImplementingClass();
	    		resourceNameToClassMap.put(profileName, clazz);
	    	} catch(Exception e) {
	    		LOGGER.info("Resource missing: "  + profileName);
	    	}
	    }
	}
	
	public Class<?> addResourceToIndex(StructureDefinition profile) {
		String profileName = profile.getName();
		Class<?> clazz = null;
		try {
			clazz = addResourceToIndex(profileName);
		} catch(DataFormatException dfe) {
			LOGGER.info(profileName + " not found. Next trying base structure: " + profile.getBase());
			clazz = addResourceToIndex(getBaseResourceFromProfileName(profile.getBase()));
		}
		return clazz;
	}
	
	public Class<?> addResourceToIndex(String resourceName) {
		Class<?> clazz = null;
		try {
			clazz = ctx.getResourceDefinition(resourceName).getImplementingClass();
		} catch(Exception e) {
			LOGGER.info(resourceName + " not found! Trying base resource.");
			String resource = profileNameToBaseResourceNameMap.get(resourceName);
			System.out.println(resource);
			if(resource != null && ctx.getResourceDefinition(resource) != null) {
				clazz = ctx.getResourceDefinition(resource).getImplementingClass();
			} else {
				LOGGER.error("No implementing class found for " + resourceName,e);
			}
		}
		if(clazz != null && !resourceNameToClassMap.containsKey(resourceName)) {
			resourceNameToClassMap.put(resourceName, clazz);
		}
		return clazz;
	}
	
	public String getBaseResourceFromProfileName(String profileName) {
		String suffix = null;
		if(profileName.contains(CORE_PROFILE_PREFIX)) {
			suffix = profileName.substring(CORE_PROFILE_PREFIX.length());
		}
		return suffix;
	}
	
	public FhirExtensionManager getExtensionManager() {
		return extensionManager;
	}

	public void setExtensionManager(FhirExtensionManager extensionManager) {
		this.extensionManager = extensionManager;
	}
	
	public FhirExtension getFhirExtension(String uri) {
		return extensionManager.getFromRegistry(uri);
	}

	/**
	 * Method returns the fully qualified Java class name
	 * for the type argument. If the type is not recognized,
	 * an exception will be thrown.
	 * 
	 * @param type
	 * @return
	 */
	public String getFullyQualifiedJavaType(String type) {
		String typeClass = primitiveMap.get(type);
		if(typeClass != null) {
			return typeClass;
		} else if(type.equals("*")){
			throw new RuntimeException("Invalid type: *"); //TODO Handle at a later time
		} else {
			RuntimeResourceDefinition def = ctx.getResourceDefinition(type);
			if(def == null) {
				throw new RuntimeException("Unknown type " + type);
			} else {
				typeClass = def.getImplementingClass().getName();
				primitiveMap.put(type, typeClass);
			}
		}
		return typeClass;
	}
	
	/**
	 * TODO Discuss with James Agnew
	 */
	public void populatePrimitiveMap() {
		primitiveMap = new HashMap<String,String>();
		primitiveMap.put("Meta", java.util.Map.class.getName());
		primitiveMap.put("base64", ca.uhn.fhir.model.primitive.Base64BinaryDt.class.getName());
		primitiveMap.put("boolean", ca.uhn.fhir.model.primitive.BooleanDt.class.getName());
		primitiveMap.put("integer", ca.uhn.fhir.model.primitive.IntegerDt.class.getName());
		primitiveMap.put("code", ca.uhn.fhir.model.primitive.CodeDt.class.getName());
		primitiveMap.put("date", ca.uhn.fhir.model.primitive.DateDt.class.getName());
		primitiveMap.put("dateTime", ca.uhn.fhir.model.primitive.DateTimeDt.class.getName());
		primitiveMap.put("decimal", ca.uhn.fhir.model.primitive.DecimalDt.class.getName());
		primitiveMap.put("id", ca.uhn.fhir.model.primitive.IdDt.class.getName());
		primitiveMap.put("idref", ca.uhn.fhir.model.primitive.IdrefDt.class.getName());
		primitiveMap.put("instant", ca.uhn.fhir.model.primitive.InstantDt.class.getName());
		primitiveMap.put("oid", ca.uhn.fhir.model.primitive.OidDt.class.getName());
		primitiveMap.put("string", ca.uhn.fhir.model.primitive.StringDt.class.getName());
		primitiveMap.put("time", ca.uhn.fhir.model.primitive.TimeDt.class.getName());
		primitiveMap.put("uri", ca.uhn.fhir.model.primitive.UriDt.class.getName());
		primitiveMap.put("xhtml", ca.uhn.fhir.model.primitive.XhtmlDt.class.getName());
		primitiveMap.put("Resource", ca.uhn.fhir.model.dstu2.composite.ContainedDt.class.getName());
		primitiveMap.put("Narrative", ca.uhn.fhir.model.dstu2.composite.NarrativeDt.class.getName());
		primitiveMap.put("ResourceReference", ca.uhn.fhir.model.dstu2.composite.ResourceReferenceDt.class.getName());
		primitiveMap.put("CodeableConcept", ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt.class.getName());
		primitiveMap.put("Address", ca.uhn.fhir.model.dstu2.composite.AddressDt.class.getName());
		primitiveMap.put("Age", ca.uhn.fhir.model.dstu2.composite.AgeDt.class.getName());
		primitiveMap.put("Attachment", ca.uhn.fhir.model.dstu2.composite.AttachmentDt.class.getName());
		primitiveMap.put("Coding", ca.uhn.fhir.model.dstu2.composite.CodingDt.class.getName());
		primitiveMap.put("ContactPoint", ca.uhn.fhir.model.dstu2.composite.ContactPointDt.class.getName());
		primitiveMap.put("Count", ca.uhn.fhir.model.dstu2.composite.CountDt.class.getName());
		primitiveMap.put("Distance", ca.uhn.fhir.model.dstu2.composite.DistanceDt.class.getName());
		primitiveMap.put("Duration", ca.uhn.fhir.model.dstu2.composite.DurationDt.class.getName());
		primitiveMap.put("ElementDefinition", ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt.class.getName());
		primitiveMap.put("ElementDefinition.Binding", ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt.Binding.class.getName());
		primitiveMap.put("ElementDefinition.Constraint", ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt.Constraint.class.getName());
		primitiveMap.put("ElementDefinition.Mapping", ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt.Mapping.class.getName());
		primitiveMap.put("ElementDefinition.Slicing", ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt.Slicing.class.getName());
		primitiveMap.put("ElementDefinition.Type", ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt.Type.class.getName());
		primitiveMap.put("HumanName", ca.uhn.fhir.model.dstu2.composite.HumanNameDt.class.getName());
		primitiveMap.put("Identifier", ca.uhn.fhir.model.dstu2.composite.IdentifierDt.class.getName());
		primitiveMap.put("Money", ca.uhn.fhir.model.dstu2.composite.MoneyDt.class.getName());
		primitiveMap.put("PeriodDt", ca.uhn.fhir.model.dstu2.composite.PeriodDt.class.getName());
		primitiveMap.put("Quantity", ca.uhn.fhir.model.dstu2.composite.QuantityDt.class.getName());
		primitiveMap.put("Range", ca.uhn.fhir.model.dstu2.composite.RangeDt.class.getName());
		primitiveMap.put("Ratio", ca.uhn.fhir.model.dstu2.composite.RatioDt.class.getName());
		primitiveMap.put("Reference", ca.uhn.fhir.model.dstu2.composite.ResourceReferenceDt.class.getName());
		primitiveMap.put("SampledData", ca.uhn.fhir.model.dstu2.composite.SampledDataDt.class.getName());
		primitiveMap.put("Timing", ca.uhn.fhir.model.dstu2.composite.TimingDt.class.getName());
		primitiveMap.put("Period", ca.uhn.fhir.model.dstu2.composite.TimingDt.class.getName());
		primitiveMap.put("Timing.Repeat", ca.uhn.fhir.model.dstu2.composite.TimingDt.Repeat.class.getName());
		primitiveMap.put("Extension", ca.uhn.fhir.model.api.ExtensionDt.class.getName());
		primitiveMap.put("MedicationPrescription.DosageInstruction", ca.uhn.fhir.model.dstu2.resource.MedicationPrescription.DosageInstruction.class.getName());
		primitiveMap.put("MedicationPrescription.Dispense", ca.uhn.fhir.model.dstu2.resource.MedicationPrescription.Dispense.class.getName());
		primitiveMap.put("MedicationPrescription.Substitution", ca.uhn.fhir.model.dstu2.resource.MedicationPrescription.Substitution.class.getName());
		primitiveMap.put("Condition.Stage", ca.uhn.fhir.model.dstu2.resource.Condition.Stage.class.getName());
		primitiveMap.put("Condition.Evidence", ca.uhn.fhir.model.dstu2.resource.Condition.Evidence.class.getName());
		primitiveMap.put("Condition.Location", ca.uhn.fhir.model.dstu2.resource.Condition.Location.class.getName());
		primitiveMap.put("Condition.DueTo", ca.uhn.fhir.model.dstu2.resource.Condition.DueTo.class.getName());
		primitiveMap.put("Condition.OccurredFollowing", ca.uhn.fhir.model.dstu2.resource.Condition.OccurredFollowing.class.getName());
		primitiveMap.put("CommunicationRequest.Payload", ca.uhn.fhir.model.dstu2.resource.CommunicationRequest.Payload.class.getName());
		primitiveMap.put("Communication.Payload", ca.uhn.fhir.model.dstu2.resource.Communication.Payload.class.getName());
		primitiveMap.put("MedicationAdministration.Dosage", ca.uhn.fhir.model.dstu2.resource.MedicationAdministration.Dosage.class.getName());
		primitiveMap.put("Medication.Product", ca.uhn.fhir.model.dstu2.resource.Medication.Product.class.getName());
		primitiveMap.put("Procedure.Performer", ca.uhn.fhir.model.dstu2.resource.Procedure.Performer.class.getName());
		primitiveMap.put("Procedure.RelatedItem", ca.uhn.fhir.model.dstu2.resource.Procedure.RelatedItem.class.getName());
		primitiveMap.put("Observation.ReferenceRange", ca.uhn.fhir.model.dstu2.resource.Observation.ReferenceRange.class.getName());
		primitiveMap.put("Observation.Related", ca.uhn.fhir.model.dstu2.resource.Observation.Related.class.getName());
		primitiveMap.put("Medication.ProductIngredient", ca.uhn.fhir.model.dstu2.resource.Medication.ProductIngredient.class.getName());
		primitiveMap.put("Medication.ProductBatch", ca.uhn.fhir.model.dstu2.resource.Medication.ProductBatch.class.getName());
		primitiveMap.put("Medication.Package", ca.uhn.fhir.model.dstu2.resource.Medication.Package.class.getName());
		primitiveMap.put("Medication.PackageContent", ca.uhn.fhir.model.dstu2.resource.Medication.PackageContent.class.getName());
		primitiveMap.put("Patient.Contact", ca.uhn.fhir.model.dstu2.resource.Patient.Contact.class.getName());
		primitiveMap.put("Patient.Animal", ca.uhn.fhir.model.dstu2.resource.Patient.Animal.class.getName());
		primitiveMap.put("Patient.Communication", ca.uhn.fhir.model.dstu2.resource.Patient.Communication.class.getName());
		primitiveMap.put("Patient.Link", ca.uhn.fhir.model.dstu2.resource.Patient.Link.class.getName());
		primitiveMap.put("ProcedureRequest.BodySite", ca.uhn.fhir.model.dstu2.resource.ProcedureRequest.BodySite.class.getName());
		primitiveMap.put("Procedure.BodySite", ca.uhn.fhir.model.dstu2.resource.Procedure.BodySite.class.getName());
		primitiveMap.put("Procedure.Device", ca.uhn.fhir.model.dstu2.resource.Procedure.Device.class.getName());
	}
	
	/**
	 * FHIR supports extensible types. For each extensible type, there is a 
	 * primitive Java type equivalent. Method returns the primitive java type
	 * corresponding to this FHIR extensible type.
	 * 
	 * @param type
	 * @return
	 */
	public static String getPrimitiveEquivalent(String type) {
		if(type == null) {
			return type;
		} else if(type.equals("ca.uhn.fhir.model.primitive.DateTimeDt")) {
			return java.util.Date.class.getName();
		} else if(type.equals("ca.uhn.fhir.model.primitive.DateDt")) {
			return  java.util.Date.class.getName();
		} else if(type.equals("ca.uhn.fhir.model.primitive.BooleanDt")) {
			return java.lang.Boolean.class.getName();
		} else if(type.equals("ca.uhn.fhir.model.primitive.IntegerDt")) {
			return java.lang.Integer.class.getName();
		} else if(type.equals("ca.uhn.fhir.model.primitive.StringDt")) {
			return java.lang.String.class.getName();
		} else if(type.equals("ca.uhn.fhir.model.primitive.CodeDt")) {
			return java.lang.String.class.getName();
		} else if(type.equals("ca.uhn.fhir.model.primitive.TimeDt")) {
			return java.lang.String.class.getName();
		} else if(type.equals("ca.uhn.fhir.model.primitive.InstantDt")) {
			return java.util.Date.class.getName();
		} else {
			return null;
		}
	}
	
	/**
	 * Returns true if type has a java equivalent type
	 * 
	 * @param element
	 * @return
	 */
	public static boolean hasEquivalentJavaType(Type type) {
		boolean hasEquivalentJavaType = false;
		DataTypeEnum code = type.getCodeElement().getValueAsEnum();
		if(type == null || type.getCodeElement() == null) {
			return hasEquivalentJavaType;
		} else if(code == DataTypeEnum.DATETIME) {
			hasEquivalentJavaType = true;
		} else if(code == DataTypeEnum.DATE) {
			hasEquivalentJavaType = true;
		} else if(code == DataTypeEnum.BOOLEAN) {
			hasEquivalentJavaType = true;
		} else if(code == DataTypeEnum.INTEGER) {
			hasEquivalentJavaType = true;
		} else if(code == DataTypeEnum.STRING) {
			hasEquivalentJavaType = true;
		} else if(code == DataTypeEnum.TIME) {
			hasEquivalentJavaType = true;
		} else if(code == DataTypeEnum.INSTANT) {
			hasEquivalentJavaType = true;
		} else if(code == DataTypeEnum.DECIMAL) {
			hasEquivalentJavaType = true;
		} else if(code == DataTypeEnum.POSITIVEINT) {
			hasEquivalentJavaType = true;
		} else if(code == DataTypeEnum.URI) {
			hasEquivalentJavaType = true;
		}
		return hasEquivalentJavaType;
	}
	
	//TODO Temporary hack for now
	public void loadProfileUriToResourceMap() {
		profileUriToResourceMap.put("http://hl7.org/fhir/StructureDefinition/Patient", "Patient");
		profileUriToResourceMap.put("http://hl7.org/fhir/StructureDefinition/Observation", "Observation");
		profileUriToResourceMap.put("http://hl7.org/fhir/StructureDefinition/Condition", "Condition");
		profileUriToResourceMap.put("http://hl7.org/fhir/StructureDefinition/Encounter", "Encounter");
		profileUriToResourceMap.put("http://hl7.org/fhir/StructureDefinition/Specimen", "Specimen");
	}
	
	public String getResource(String profileUri) {
		return profileUriToResourceMap.get(profileUri);
	}
	
	/**
	 * A FHIR attribute is a multivalued attribute if it has the form
	 * Resource.attribute[x] and/or has multiple types associated with it.
	 * This method tests the former and looks for the present of [x] in
	 * the name. If present it returns true, otherwise it returns false.
	 * 
	 * @return
	 */
	public static  boolean isMultivaluedAttribute(String attributeName) {
		if (attributeName.contains("[x]")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * A FHIR attribute is a multivalued attribute if it has the form
	 * Resource.attribute[x] and/or has multiple types associated with it.
	 * <p>
	 * This method removes the [x] suffix from the name and returns the
	 * prefix to the caller.
	 * 
	 * @return
	 */
	public static  String cleanMultiValuedAttributeName(String attributeName) {
		return attributeName.replace("[x]","");
	}
	
	/**
	 * Method returns true if the element has no associated type
	 * 
	 * @param element
	 * @return
	 */
	public static boolean elementHasNoType(ElementDefinitionDt element) {
		return element.getType() == null || element.getType().size() == 0;
	}
	
	/**
	 * Method returns true if the element is a FHIR extension
	 * 
	 * TODO Add more checks if needed. Review FHIR specification.
	 * 
	 * @param element
	 * @return
	 */
	public static boolean isFhirExtension(ElementDefinitionDt element) {
		//For FHIR core structure, they appear to have no code - e.g., Condition.evidence
		return element.getTypeFirstRep().getCode() != null && element.getTypeFirstRep().getCode().equalsIgnoreCase("Extension");
	}
	
	/**
	 * Method returns true if the element is a multi-type attribute.
	 * 
	 * TODO Add more checks if needed. Fix isMultivalued attribute and call here.
	 * @param element
	 * @return
	 */
	public static boolean isMultiTypeAttribute(ElementDefinitionDt element) {
		return element.getType() != null && element.getType().size() > 1;
	}
	
	/**
	 * Returns true if path represents a root level attribute.
	 * 
	 * @param path
	 * @return
	 */
	public static boolean isRootLevelAttribute(String path) {
		return path.split("\\.").length == 2;
	}
	
	/**
	 * Returns true if the element represents a FHIR structure of some
	 * kind. FHIR structures can be core structure types defined in the
	 * FHIR logical model or extended structures defined in non-core profiles.
	 * 
	 * @param element
	 * @return
	 */
	public static boolean isFhirStructure(ElementDefinitionDt element) {
		String path = element.getPath();
		String[] pathComponents = path.split("\\.");
		if(pathComponents.length > 2) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * If profile URL is <code>http://hl7.org/fhir/StructureDefinition/organization</code>
	 * method will return <code>organization</code>.
	 * @param profileUrl
	 * @return
	 */
	public static String getProfileSuffix(String profileUrl) {
		if(profileUrl != null && profileUrl.lastIndexOf('/') > 0) {
			return profileUrl.substring(profileUrl.lastIndexOf('/') + 1);
		} else {
			return "";
		}
	}
	
	/**
	 * Method performs a shallow clone of the element and, when multi-type,
	 * replaces the type collection with a collection of a single type.
	 * 
	 * Goal: Flatten a multi-type element into a collection of clones each
	 * representing a single type.
	 * 
	 * @param element
	 * @return
	 */
	public static List<ElementDefinitionDt> flattenElementByType(ElementDefinitionDt element) {
		List<ElementDefinitionDt> flattenedElements = new ArrayList<>();
		String path = element.getPath();
		path = path.replaceAll("\\[x\\]", "");
		for(Type type : element.getType()) {
			List<Type> types = new ArrayList<>();
			types.add(type);
			ElementDefinitionDt flattenedElement = shallowCloneElement(element);
			flattenedElement.setType(types);//Be sure to not modify original collection - replace it altogether
			flattenedElement.setPath(buildMultiAttributePath(path, type));//Remove the multi-type indicator
			flattenedElements.add(flattenedElement);
		}
		return flattenedElements;
	}
	
	public static String buildMultiAttributePath(String path, Type type) {
		String modifiedPath = path.replaceAll("\\[x\\]", "");
		if(type.getCode().equalsIgnoreCase("Reference")) {
			modifiedPath += getProfileSuffix(type.getProfile());
		} else {
			modifiedPath += StringUtils.capitalize(type.getCode());
		}
		return modifiedPath;
	}
	
	/**
	 * Method performs a shallow clone of the element argument.
	 * 
	 * TODO Ask HAPI to provide this method. This is needed because FHIR
	 * uses a shorthand to represent a family of attributes through the definition
	 * of multi-type fields.
	 * 
	 * 
	 * @param element
	 * @return
	 */
	public static ElementDefinitionDt shallowCloneElement(ElementDefinitionDt element) {
		ElementDefinitionDt clonedElement = new ElementDefinitionDt();
		clonedElement.setName(element.getNameElement());
		clonedElement.setNameReference(element.getNameReferenceElement());
		clonedElement.setPath(element.getPathElement());
		clonedElement.setType(element.getType());
		clonedElement.setMin(element.getMinElement());
		clonedElement.setMax(element.getMaxElement());
		clonedElement.setBinding(element.getBinding());
		clonedElement.setMustSupport(element.getMustSupportElement());
		clonedElement.setIsModifier(element.getIsModifierElement());
		clonedElement.setShort(element.getShortElement());
		clonedElement.setDefinition(element.getDefinition());
		clonedElement.setAlias(element.getAlias());
		clonedElement.setCode(element.getCode());
		clonedElement.setComments(element.getComments());
		clonedElement.setCondition(element.getCondition());
		clonedElement.setConstraint(element.getConstraint());
		clonedElement.setDefaultValue(element.getDefaultValue());
		clonedElement.setElementSpecificId(element.getElementSpecificId());
		clonedElement.setExample(element.getExample());
		clonedElement.setFixed(element.getFixed());
		clonedElement.setIsSummary(element.getIsSummaryElement());
		clonedElement.setMapping(element.getMapping());
		clonedElement.setMaxLength(element.getMaxLengthElement());
		clonedElement.setMeaningWhenMissing(element.getMeaningWhenMissingElement());
		clonedElement.setPattern(element.getPattern());
		clonedElement.setRepresentation(element.getRepresentation());
		clonedElement.setRequirements(element.getRequirementsElement());
		clonedElement.setSlicing(element.getSlicing());
		return clonedElement;
	}
}