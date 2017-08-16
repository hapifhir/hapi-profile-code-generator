package ca.uhn.fhir.utils.codegen.hapi.dstu3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.Bundle.BundleEntryComponent;
import org.hl7.fhir.dstu3.model.ElementDefinition;
import org.hl7.fhir.dstu3.model.StructureDefinition;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.DataFormatException;
import ca.uhn.fhir.utils.codegen.CodeGenerationUtils;
import ca.uhn.fhir.utils.codegen.hapi.CodeGeneratorConfigurator;
import ca.uhn.fhir.utils.codegen.hapi.HapiFhirUtils;
import ca.uhn.fhir.utils.codegen.hapi.IFhirResourceManager;
import ca.uhn.fhir.utils.common.io.ResourceLoadingUtils;
import ca.uhn.fhir.utils.fhir.FhirExtensionManager;
import ca.uhn.fhir.utils.fhir.PathUtils;
import ca.uhn.fhir.utils.fhir.model.FhirExtensionDefinition;

/**
 * Class is responsible for loading all FHIR Core resources (defined in FHIR as profiles)
 * and generating an index by profile name of all unmarshalled
 * profile definition objects as well as an index mapping a resource
 * to its HAPI FHIR corresponding classes.
 * 
 * @author Claude Nanjo
 *
 */
public class FhirResourceManagerDstu3 implements IFhirResourceManager<StructureDefinition> {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(FhirResourceManagerDstu3.class);
	public static final String CORE_PROFILE_PREFIX = "http://hl7.org/fhir/StructureDefinition/";
	public static final String CORE_BASE_RESOURCE_PROFILE = "http://hl7.org/fhir/StructureDefinition/DomainResource";
	
	private Map<String,Class<?>> resourceNameToClassMap;
	private Map<String, StructureDefinition> profileNameToProfileMap;
	private Map<String,String> primitiveMap;
	private FhirContext ctx;
	private FhirExtensionManager extensionManager;
	private Map<String, String> profileUriToResourceNameMap;
	private Map<String, StructureDefinition> profileUriToProfileMap;
	private Map<String, String> profileNameToBaseResourceNameMap;
	private Map<String, String> codeableConceptEnumTypeOverride;
	private Map<String, String> codeEnumTypeOverride;
	private List<String> generatedType;
	
	public FhirResourceManagerDstu3() {
		super();
		resourceNameToClassMap = new HashMap<String,Class<?>>();
		profileNameToProfileMap = new HashMap<String,StructureDefinition>();
		profileUriToResourceNameMap = new HashMap<String, String>();
		profileUriToProfileMap = new HashMap<String, StructureDefinition>();
		profileNameToBaseResourceNameMap = new HashMap<String, String>();
		codeableConceptEnumTypeOverride = new HashMap<String, String>();
		codeEnumTypeOverride = new HashMap<String, String>();
		generatedType = new ArrayList<String>();
		loadProfileUriToResourceMap();
		populatePrimitiveMap();
		//populateCodeableConceptEnumTypeOverrides();
		populateCodeEnumTypeOverrides();
		ctx = FhirContext.forDstu3();
	}
	
	public FhirContext getFhirContext() {
		return ctx;
	}
	
	public StructureDefinition getProfileFromProfileUri(String uri) {
		return profileUriToProfileMap.get(uri);
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
	
	public void addGeneratedType(String classPath) {
		generatedType.add(classPath);
	}
	
	public boolean generatedTypeExists(String generatedType) {
		return this.generatedType.contains(generatedType);
	}

	public static String getProfileName(StructureDefinition profile) {
		String id = profile.getId();
		String name = PathUtils.getLastResourcePathComponent(id);
		return name;
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
					if(FilenameUtils.getExtension(profileFile.getAbsolutePath()).equalsIgnoreCase("xml") ||
							FilenameUtils.getExtension(profileFile.getAbsolutePath()).equalsIgnoreCase("json")) {
						loadResourceProfile(profileFile.getAbsolutePath());
					}
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
			System.out.println(String.format("Loading Profile '%s'", profileFilePath));
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
			IBaseResource resource = (IBaseResource) ctx.newXmlParser().parseResource(resourceProfiles);
			if(resource instanceof Bundle) {
				List<BundleEntryComponent> entries = ((Bundle)resource).getEntry();
				for(BundleEntryComponent entry : entries) {
					if(entry.getResource() instanceof StructureDefinition) {
						StructureDefinition profile = (StructureDefinition)entry.getResource();
						populateProfileMaps(profile);
					}
				}
			} else if(resource instanceof StructureDefinition) {
				populateProfileMaps((StructureDefinition)resource);
			} else {
				LOGGER.info("Unknown resource " + resource.getClass().getCanonicalName());
				System.out.println("This resource is neither a profile nor a bundle of profiles " + resource.getClass().getCanonicalName());
			}
			populateResourceNameToClassMap();
		} catch(Exception e) {
			LOGGER.error("Error loading profile from reader", e);
			e.printStackTrace();
			throw new RuntimeException("Error loading profile from reader", e);
		}
	}

	protected void populateProfileMaps(StructureDefinition profile) {
		profileNameToProfileMap.put(getProfileName(profile),profile);
		profileUriToProfileMap.put(profile.getUrl(), profile);
		populateProfileToBaseResourceMap(profile);
	}

	protected void populateProfileToBaseResourceMap(StructureDefinition profile) {
		String profileBase = profile.getBaseDefinition();
		if(profileBase != null) {
			String baseResource = getBaseResourceFromProfileName(profileBase);
			if(baseResource != null) {
				profileNameToBaseResourceNameMap.put(getProfileName(profile), baseResource);
//				if(profile.getId() != null && !profile.getName().equalsIgnoreCase(profile.getId()) && !profile.getId().equals("http://hl7.org/fhir/StructureDefinition/Resource")) { //TODO Check to see if this path is ever taken
//					profileNameToBaseResourceNameMap.put(profile.getId(), baseResource);
//				}
			}
		}
	}
	
	/**
	 * Method generates the resource-name-to-class index for all resources
	 * defined in a profile.
	 *
	 */
	private void populateResourceNameToClassMap() {
		System.out.println("Populating Resource Name to Class");
		for(StructureDefinition profile : profileNameToProfileMap.values()) {
	    	String profileName = getProfileName(profile);
	    	try {
	    		Class<?> clazz = ctx.getResourceDefinition(profileName).getImplementingClass();
	    		resourceNameToClassMap.put(profileName, clazz);
	    	} catch(Exception e) {
	    		LOGGER.info("Resource missing: "  + profileName);
	    	}
	    }
	}
	
	public Class<?> addResourceToIndex(StructureDefinition profile) {
		String profileName = getProfileName(profile);
		Class<?> clazz = null;
		try {
			clazz = addResourceToIndex(profileName);
		} catch(DataFormatException dfe) {
			LOGGER.info(profileName + " not found. Next trying base structure: " + profile.getBaseDefinition());
			clazz = addResourceToIndex(getBaseResourceFromProfileName(profile.getBaseDefinition()));
		}
		return clazz;
	}
	
	/**
	 * Method interrogates HAPI library for class associated with the given resource
	 * name. If a class is not found for the given resource name, the base resource 
	 * is considered.
	 * 
	 * TODO Method should take profile and call the HapiFhirUtils
	 * 
	 * @param resourceName
	 * @return
	 */
	public Class<?> addResourceToIndex(String resourceName) {
		Class<?> clazz = null;
		try {
			clazz = ctx.getResourceDefinition(resourceName).getImplementingClass();
		} catch(Exception e) {
			LOGGER.info(resourceName + " not found! Trying base resource.");
			String resource = profileNameToBaseResourceNameMap.get(resourceName);
			if(resource != null && ctx.getResourceDefinition(resource) != null) {
				clazz = ctx.getResourceDefinition(resource).getImplementingClass();
			} else {
				LOGGER.info("No implementing class found for " + resourceName,e);
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
	
	public FhirExtensionDefinition getFhirExtension(String uri) {
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
//	public String getFullyQualifiedJavaType(StructureDefinition profile, Type type) {
//		String typeProfile = null;
//		if(type.getProfileFirstRep() != null) {
//			typeProfile = type.getProfileFirstRep().getValueAsString();
//		}
//		return getFullyQualifiedJavaType(profile, type.getCode(), typeProfile); //TODO Handle multi-profiles later
//	}

	/**
	 * Method returns the fully qualified Java class name
	 * for the type argument. If the type is not recognized,
	 * an exception will be thrown.
	 * 
	 * @param type
	 * @return
	 */
//	public String getFullyQualifiedJavaType(StructureDefinition profile, String typeCode, String typeProfile) {
//		if(true){throw new RuntimeException("Find me");}
//		String typeClass = null;
//		if(generatedTypeExists(typeCode)) {
//			typeClass = typeCode;
//		} else if(typeCode != null && typeCode.equals("Resource")) {//TODO This needs to be handled in ContainedResource Handler
//			typeClass = ca.uhn.fhir.model.dstu2.composite.ContainedDt.class.getName();
//		} else if(typeCode.equals("Reference")) {
//			typeClass = ca.uhn.fhir.model.dstu2.composite.ResourceReferenceDt.class.getName();
//		} else if(typeCode.equals("Extension")) {
//			typeClass = ca.uhn.fhir.model.api.ExtensionDt.class.getName();
//		} else {
//			typeCode = updateIfReferenceType(typeCode, typeProfile);//If it is a reference, set the type of what is being referenced - only first profile considered for now
//			if(typeCode.equals("Resource")) {
//				typeClass = "ca.uhn.fhir.model.api.IResource";//Handles the case for references to 'any'
//			} else {
//				if(typeCode.equals("Extension")) {
//					System.out.println("Stop here");
//				}
//				typeClass = getHapiTypeForFhirType(profile, typeCode);
//			}
//		}
//		String typeClass = primitiveMap.get(typeCode);
//		if(typeClass != null) {
//			//throw new RuntimeException("Check what is happening here " + typeClass);
//		}
//		if(typeClass != null) {
//			typeClass = handlePrimitiveSpecializations(typeClass, typeProfile);
//		}
//		if(typeClass == null && generatedTypeExists(typeCode)) {
//			typeClass = typeCode;
//		}
//		if(typeClass == null && typeCode.equals("DomainResource")) {//TODO Figure how to handle this
//			typeClass = "ca.uhn.fhir.model.dstu2.resource.BaseResource";
//		}
//		if(typeClass != null) {
//			return typeClass;
//		} else if(typeCode.equals("*")){
//			throw new RuntimeException("Invalid type: *"); //TODO Handle at a later time
//		} else {
//			RuntimeResourceDefinition def = ctx.getResourceDefinition(typeCode);
//			if(def == null) {
//				throw new RuntimeException("Unknown type " + typeCode);
//			} else {
//				typeClass = def.getImplementingClass().getName();
//				primitiveMap.put(typeCode, typeClass);
//			}
//		}
//		return typeClass;
//	}
	
	/**
	 * For attributes that are references to other resources, method returns the type of the reference.
	 * If the type is not a resource reference, the original typeCode is returned.
	 * 
	 * @param typeCode
	 * @param typeProfile
	 * @return
	 */
	public String updateIfReferenceType(String typeCode, String typeProfile) {
		String referenceType = typeCode;
		if(typeCode != null && typeCode.equals("Reference")) {
			StructureDefinition profile = getProfileFromProfileUri(typeProfile);//We first get the profile associated with the type
			String profileUri = profile.getBaseDefinition();
			if(profileUri == null) {
				profileUri = typeProfile;
			}
			referenceType = CodeGenerationUtils.getLastPathComponent(profileUri, '/');
			if(referenceType.equals("DomainResource")) {
				referenceType = CodeGenerationUtils.getLastPathComponent(typeProfile, '/');
			}
		}
		return referenceType;
	}
	
	//TODO Handle more elegantly during refactoring
	public String handlePrimitiveSpecializations(String primitiveType, String primitiveProfile) {
		String type = primitiveType;
		if(primitiveProfile != null) {
			if(primitiveType.equals(ca.uhn.fhir.model.dstu2.composite.QuantityDt.class.getName()) && primitiveProfile.equals("http://hl7.org/fhir/StructureDefinition/SimpleQuantity")) {
				type = ca.uhn.fhir.model.dstu2.composite.SimpleQuantityDt.class.getName();
			} else if(primitiveType.equals(ca.uhn.fhir.model.dstu2.composite.QuantityDt.class.getName()) && primitiveProfile.equals("http://hl7.org/fhir/StructureDefinition/Duration")) {
				type = ca.uhn.fhir.model.dstu2.composite.DurationDt.class.getName();
			}
			
		}
		return type;
	}
	
	public String getCodeableConceptOverride(String path) {
		return codeableConceptEnumTypeOverride.get(path);
	}
	
	public String getCodeOverride(String path) {
		return codeEnumTypeOverride.get(path);
	}
	
	/**
	 * Method returns the equivalent HAPI Type for a given FHIR type. It checks
	 * for primitive types, resources, and structure types.
	 * 
	 * @param profile The relevant FHIR profile for the resource of interest
	 * @param fhirType The FHIR attribute type to translate
	 * @return The equivalent HAPI type
	 */
	public String getHapiTypeForFhirType(StructureDefinition profile, String fhirType) {
		String hapiTypeName = HapiFhirUtils.getPrimitiveTypeClassName(getFhirContext(), fhirType);
		if(hapiTypeName != null) {
			return hapiTypeName;
		} else {
			Class<?> hapiTypeClass = HapiFhirUtils.getResourceClass(getFhirContext(), fhirType);
			if(hapiTypeClass != null) {
				hapiTypeName = hapiTypeClass.getName();
			} else {
				String baseResourcePath = profile.getBaseDefinition();
				String baseResourceName = PathUtils.getLastResourcePathComponent(baseResourcePath);
				if(baseResourceName.equals("DomainResource")) {
					baseResourceName = getProfileName(profile);
				}
				try {
					Class<?> typeClass = HapiFhirUtils.getStructureTypeClass(getFhirContext(), baseResourceName, fhirType);
				if(typeClass == null) {
					throw new RuntimeException("Unknown type " + fhirType + " for base resource " + baseResourcePath);
				} else {
					return typeClass.getName();
				}
				} catch(Exception e) {
					e.printStackTrace();
					throw e;
				}
			}
		}
		return hapiTypeName;
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
		primitiveMap.put("positiveInt", ca.uhn.fhir.model.primitive.PositiveIntDt.class.getName());
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
		primitiveMap.put("Annotation", ca.uhn.fhir.model.dstu2.composite.AnnotationDt.class.getName());
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
		primitiveMap.put("Period", ca.uhn.fhir.model.dstu2.composite.PeriodDt.class.getName());
		primitiveMap.put("Quantity", ca.uhn.fhir.model.dstu2.composite.QuantityDt.class.getName());
		primitiveMap.put("Range", ca.uhn.fhir.model.dstu2.composite.RangeDt.class.getName());
		primitiveMap.put("Ratio", ca.uhn.fhir.model.dstu2.composite.RatioDt.class.getName());
		primitiveMap.put("Reference", ca.uhn.fhir.model.dstu2.composite.ResourceReferenceDt.class.getName());
		primitiveMap.put("SampledData", ca.uhn.fhir.model.dstu2.composite.SampledDataDt.class.getName());
		primitiveMap.put("Timing", ca.uhn.fhir.model.dstu2.composite.TimingDt.class.getName());
		primitiveMap.put("Timing.Repeat", ca.uhn.fhir.model.dstu2.composite.TimingDt.Repeat.class.getName());
		primitiveMap.put("Extension", ca.uhn.fhir.model.api.ExtensionDt.class.getName());
		primitiveMap.put("MedicationPrescription.DosageInstruction", ca.uhn.fhir.model.dstu2.resource.MedicationOrder.DosageInstruction.class.getName());
		primitiveMap.put("MedicationPrescription.Dispense", ca.uhn.fhir.model.dstu2.resource.MedicationOrder.DispenseRequest.class.getName());
		primitiveMap.put("MedicationPrescription.Substitution", ca.uhn.fhir.model.dstu2.resource.MedicationOrder.Substitution.class.getName());
		primitiveMap.put("Condition.Stage", ca.uhn.fhir.model.dstu2.resource.Condition.Stage.class.getName());
		primitiveMap.put("Condition.Evidence", ca.uhn.fhir.model.dstu2.resource.Condition.Evidence.class.getName());
		primitiveMap.put("CommunicationRequest.Payload", ca.uhn.fhir.model.dstu2.resource.CommunicationRequest.Payload.class.getName());
		primitiveMap.put("Communication.Payload", ca.uhn.fhir.model.dstu2.resource.Communication.Payload.class.getName());
		primitiveMap.put("MedicationAdministration.Dosage", ca.uhn.fhir.model.dstu2.resource.MedicationAdministration.Dosage.class.getName());
		primitiveMap.put("Medication.Product", ca.uhn.fhir.model.dstu2.resource.Medication.Product.class.getName());
		primitiveMap.put("Procedure.Performer", ca.uhn.fhir.model.dstu2.resource.Procedure.Performer.class.getName());
		primitiveMap.put("Observation.ReferenceRange", ca.uhn.fhir.model.dstu2.resource.Observation.ReferenceRange.class.getName());
		primitiveMap.put("Observation.Related", ca.uhn.fhir.model.dstu2.resource.Observation.Related.class.getName());
		primitiveMap.put("Organization.Contact", ca.uhn.fhir.model.dstu2.resource.Organization.Contact.class.getName());
		primitiveMap.put("Medication.ProductIngredient", ca.uhn.fhir.model.dstu2.resource.Medication.ProductIngredient.class.getName());
		primitiveMap.put("Medication.ProductBatch", ca.uhn.fhir.model.dstu2.resource.Medication.ProductBatch.class.getName());
		primitiveMap.put("Medication.Package", ca.uhn.fhir.model.dstu2.resource.Medication.Package.class.getName());
		primitiveMap.put("Medication.PackageContent", ca.uhn.fhir.model.dstu2.resource.Medication.PackageContent.class.getName());
		primitiveMap.put("Patient.Contact", ca.uhn.fhir.model.dstu2.resource.Patient.Contact.class.getName());
		primitiveMap.put("Patient.Animal", ca.uhn.fhir.model.dstu2.resource.Patient.Animal.class.getName());
		primitiveMap.put("Patient.Communication", ca.uhn.fhir.model.dstu2.resource.Patient.Communication.class.getName());
		primitiveMap.put("Patient.Link", ca.uhn.fhir.model.dstu2.resource.Patient.Link.class.getName());
		primitiveMap.put("Patient.Address", ca.uhn.fhir.model.dstu2.composite.AddressDt.class.getName());
		primitiveMap.put("Patient.Telecom", ca.uhn.fhir.model.dstu2.composite.ContactPointDt.class.getName());
		primitiveMap.put("Organization.Address", ca.uhn.fhir.model.dstu2.composite.AddressDt.class.getName());
		primitiveMap.put("Location.Address", ca.uhn.fhir.model.dstu2.composite.AddressDt.class.getName());
		primitiveMap.put("Observation.Component", ca.uhn.fhir.model.dstu2.resource.Observation.Component.class.getName());
		primitiveMap.put("DiagnosticOrder.Item", ca.uhn.fhir.model.dstu2.resource.DiagnosticOrder.Item.class.getName());
		primitiveMap.put("AllergyIntolerance.Reaction", ca.uhn.fhir.model.dstu2.resource.AllergyIntolerance.Reaction.class.getName());
		primitiveMap.put("DiagnosticOrder.Event", ca.uhn.fhir.model.dstu2.resource.DiagnosticOrder.Event.class.getName());
		primitiveMap.put("DiagnosticReport.Image", ca.uhn.fhir.model.dstu2.resource.DiagnosticReport.Image.class.getName());
		primitiveMap.put("Encounter.Hospitalization", ca.uhn.fhir.model.dstu2.resource.Encounter.Hospitalization.class.getName());
		primitiveMap.put("Encounter.StatusHistory", ca.uhn.fhir.model.dstu2.resource.Encounter.StatusHistory.class.getName());
		primitiveMap.put("Encounter.Participant", ca.uhn.fhir.model.dstu2.resource.Encounter.Participant.class.getName());
		primitiveMap.put("Encounter.Location", ca.uhn.fhir.model.dstu2.resource.Encounter.Location.class.getName());
		primitiveMap.put("Goal.Outcome", ca.uhn.fhir.model.dstu2.resource.Goal.Outcome.class.getName());
		primitiveMap.put("MedicationDispense.Substitution", ca.uhn.fhir.model.dstu2.resource.MedicationDispense.Substitution.class.getName());
		primitiveMap.put("MedicationDispense.DosageInstruction", ca.uhn.fhir.model.dstu2.resource.MedicationDispense.DosageInstruction.class.getName());
		primitiveMap.put("MedicationOrder.DosageInstruction", ca.uhn.fhir.model.dstu2.resource.MedicationOrder.DosageInstruction.class.getName());
		primitiveMap.put("MedicationOrder.DispenseRequest", ca.uhn.fhir.model.dstu2.resource.MedicationOrder.DispenseRequest.class.getName());
		primitiveMap.put("Medication.Ingredient", ca.uhn.fhir.model.dstu2.resource.Medication.ProductIngredient.class.getName());
		primitiveMap.put("Medication.Batch", ca.uhn.fhir.model.dstu2.resource.Medication.ProductBatch.class.getName());
		primitiveMap.put("Product.Batch", ca.uhn.fhir.model.dstu2.resource.Medication.ProductBatch.class.getName());
		primitiveMap.put("Package.Content", ca.uhn.fhir.model.dstu2.resource.Medication.PackageContent.class.getName());
		primitiveMap.put("Medication.Content", ca.uhn.fhir.model.dstu2.resource.Medication.PackageContent.class.getName());
		primitiveMap.put("MedicationOrder.Substitution", ca.uhn.fhir.model.dstu2.resource.MedicationOrder.Substitution.class.getName());
		primitiveMap.put("MedicationStatement.Dosage", ca.uhn.fhir.model.dstu2.resource.MedicationStatement.Dosage.class.getName());
		primitiveMap.put("Immunization.Explanation", ca.uhn.fhir.model.dstu2.resource.Immunization.Explanation.class.getName());
		primitiveMap.put("Immunization.VaccinationProtocol", ca.uhn.fhir.model.dstu2.resource.Immunization.VaccinationProtocol.class.getName());
		primitiveMap.put("Immunization.Reaction", ca.uhn.fhir.model.dstu2.resource.Immunization.Reaction.class.getName());
		primitiveMap.put("Practitioner.Qualification", ca.uhn.fhir.model.dstu2.resource.Practitioner.Qualification.class.getName());
		primitiveMap.put("Practitioner.Specialty", ca.uhn.fhir.model.dstu2.resource.Practitioner.Qualification.class.getName());
		primitiveMap.put("Procedure.FocalDevice", ca.uhn.fhir.model.dstu2.resource.Procedure.FocalDevice.class.getName());
		primitiveMap.put("Substance.Ingredient", ca.uhn.fhir.model.dstu2.resource.Substance.Ingredient.class.getName());
		primitiveMap.put("Specimen.Treatment", ca.uhn.fhir.model.dstu2.resource.Specimen.Treatment.class.getName());
		primitiveMap.put("Specimen.Collection", ca.uhn.fhir.model.dstu2.resource.Specimen.Collection.class.getName());
		primitiveMap.put("Specimen.Container", ca.uhn.fhir.model.dstu2.resource.Specimen.Container.class.getName());
		primitiveMap.put("Substance.Instance", ca.uhn.fhir.model.dstu2.resource.Substance.Instance.class.getName());
		primitiveMap.put("Practitioner.PractitionerRole", ca.uhn.fhir.model.dstu2.resource.Practitioner.PractitionerRole.class.getName());
		primitiveMap.put("Location.Position", ca.uhn.fhir.model.dstu2.resource.Location.Position.class.getName());
		primitiveMap.put("Product.Ingredient", ca.uhn.fhir.model.dstu2.resource.Medication.ProductIngredient.class.getName());
		primitiveMap.put("Patient.Communication", ca.uhn.fhir.model.dstu2.resource.Patient.Communication.class.getName());
	}
	
	/**
	 * TODO Discuss with James Agnew
	 */
//	public void populateCodeableConceptEnumTypeOverrides() {
//		codeableConceptEnumTypeOverride.put("CommunicationRequest.priority", "ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt");
//		codeableConceptEnumTypeOverride.put("Encounter.priority", "ca.uhn.fhir.model.dstu2.valueset.PriorityCodesEnum");
//		codeableConceptEnumTypeOverride.put("Location.type", "ca.uhn.fhir.model.dstu2.valueset.LocationTypeEnum");
//		codeableConceptEnumTypeOverride.put("ReferralRequest.status", "ca.uhn.fhir.model.dstu2.valueset.ReferralStatusEnum");
//		codeableConceptEnumTypeOverride.put("ReferralRequest.specialty", "ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt");
//		codeableConceptEnumTypeOverride.put("ReferralRequest.priority", "ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt");
//	}
	
	/**
	 * TODO Discuss with James Agnew
	 */
	public void populateCodeEnumTypeOverrides() {
		codeEnumTypeOverride.put("ReferralRequest.status", "ca.uhn.fhir.model.dstu2.valueset.ReferralStatusEnum");
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
		} else if(type.equals("ca.uhn.fhir.model.primitive.PositiveIntDt")) {
			return java.lang.Integer.class.getName();
		} else {
			return null;
		}
	}
	
	/**
	 * Returns true if type has a java equivalent type
	 * Ask James Agnew
	 * TODO Cover all simple types - https://hl7-fhir.github.io/datatypes.html
	 * 
	 * @param code
	 * @return
	 */
	public static boolean hasEquivalentJavaType(String code) {
		boolean hasEquivalentJavaType = false;
		if(code == null) {
			return hasEquivalentJavaType;
		} else if(code.equalsIgnoreCase("dateTime")) {
			hasEquivalentJavaType = true;
		} else if(code.equalsIgnoreCase("date")) {
			hasEquivalentJavaType = true;
		} else if(code.equalsIgnoreCase("boolean")) {
			hasEquivalentJavaType = true;
		} else if(code.equalsIgnoreCase("integer")) {
			hasEquivalentJavaType = true;
		} else if(code.equalsIgnoreCase("string")) {
			hasEquivalentJavaType = true;
		} else if(code.equalsIgnoreCase("time")) {
			hasEquivalentJavaType = true;
		} else if(code.equalsIgnoreCase("instant")) {
			hasEquivalentJavaType = true;
		} else if(code.equalsIgnoreCase("decimal")) {
			hasEquivalentJavaType = true;
		} else if(code.equalsIgnoreCase("positiveInt")) {
			hasEquivalentJavaType = true;
		} else if(code.equalsIgnoreCase("uri")) {
			hasEquivalentJavaType = true;
		}
		return hasEquivalentJavaType;
	}
	
	//TODO Temporary hack for now
	public void loadProfileUriToResourceMap() {
		profileUriToResourceNameMap.put("http://hl7.org/fhir/StructureDefinition/Patient", "Patient");
		profileUriToResourceNameMap.put("http://hl7.org/fhir/StructureDefinition/Observation", "Observation");
		profileUriToResourceNameMap.put("http://hl7.org/fhir/StructureDefinition/Condition", "Condition");
		profileUriToResourceNameMap.put("http://hl7.org/fhir/StructureDefinition/Encounter", "Encounter");
		profileUriToResourceNameMap.put("http://hl7.org/fhir/StructureDefinition/Specimen", "Specimen");
	}
	
	public String getResource(String profileUri) {
		return profileUriToResourceNameMap.get(profileUri);
	}
	
	/**
	 * Method returns true if the element has no associated type
	 * 
	 * @param element
	 * @return
	 */
	public static boolean elementHasNoType(ElementDefinition element) {
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
	public static boolean isFhirExtension(ElementDefinition element) {
		//For FHIR core structure, they appear to have no code - e.g., Condition.evidence
		return element.getType().get(0) != null && element.getType().get(0).getCode().equalsIgnoreCase("Extension");
	}
	
	/**
	 * Method returns true if the element is a multi-type attribute.
	 * 
	 * TODO Add more checks if needed. Fix isMultivalued attribute and call here.
	 * @param element
	 * @return
	 */
	public static boolean isMultiTypeAttribute(ElementDefinition element) {
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
	public static boolean isFhirStructure(ElementDefinition element) {
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
	 * Method takes an ordered list of path components and returns the type of the
	 * last non-extension item on the path.For instance, if the path is Patient.contact.telecom.extension, 
	 * the input to this method will be the list {Patient, contact, telecom, extension} and
	 * this method will return the return type for Patient.getContact().getTelecom(). If the item has 
	 * multiple cardinality and thus a List<T> is returned instead of the type 'T' directly, 
	 * the type of 'T' will be returned instead.
	 * 
	 * @param canonicalPath
	 * @return
	 */
	
//	public String getLeafLevelItemType(List<String> canonicalPath) {
//		String resourceName = canonicalPath.get(0);
//		Class<?> lastClass = ctx.getResourceDefinition(resourceName).getImplementingClass();
//		String methodType = null;
//		for(int index = 1; index < canonicalPath.size(); index++) {
//			if(canonicalPath.get(index).equals("extension")) {
//				break;
//			} else {
//				try {
//					String methodName = "get" + StringUtils.capitalize(canonicalPath.get(index));
//					Method method = lastClass.getDeclaredMethod(methodName);
//					lastClass = method.getReturnType();
//					methodType = method.getReturnType().getName();
//					if(lastClass.getName().equals("java.util.List")) {
//						methodType  = "";//((ParameterizedType)method.getGenericReturnType()).getActualTypeArguments()[0].getTypeName();//TODO Fix this
//						lastClass = Class.forName(methodType);
//					}
//				} catch(Exception e) {
//					e.printStackTrace();
//					throw new RuntimeException("Invalid path: " + canonicalPath);
//				}
//			}
//		}
//		return methodType;
//	}
	
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
	public static List<ElementDefinition> flattenElementByType(ElementDefinition element) {
		List<ElementDefinition> flattenedElements = new ArrayList<>();
		String path = element.getPath();
		path = path.replaceAll("\\[x\\]", "");
		for(ElementDefinition.TypeRefComponent type : element.getType()) {
			List<ElementDefinition.TypeRefComponent> types = new ArrayList<>();
			types.add(type);
			ElementDefinition flattenedElement = shallowCloneElement(element);
			flattenedElement.getType().addAll(types);//Be sure to not modify original collection - replace it altogether
			flattenedElement.setPath(buildMultiAttributePath(path, type));//Remove the multi-type indicator
			flattenedElements.add(flattenedElement);
		}
		return flattenedElements;
	}
	
	public static String buildMultiAttributePath(String path, ElementDefinition.TypeRefComponent type) {
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
	public static ElementDefinition shallowCloneElement(ElementDefinition element) {
		ElementDefinition clonedElement = new ElementDefinition();
		clonedElement.setSliceName(element.getSliceName());
		clonedElement.setContentReference(element.getContentReference());
		clonedElement.setPath(element.getPath());
		List<ElementDefinition.TypeRefComponent> clonedTypes = new ArrayList<ElementDefinition.TypeRefComponent>();//Should do for all lists
		clonedTypes.addAll(element.getType());
		clonedElement.getType().addAll(clonedTypes);
		clonedElement.setMin(element.getMin());
		clonedElement.setMax(element.getMax());
		clonedElement.setBinding(element.getBinding());
		clonedElement.setMustSupport(element.getMustSupport());
		clonedElement.setIsModifier(element.getIsModifier());
		clonedElement.setShort(element.getShort());
		clonedElement.setDefinition(element.getDefinition());
		clonedElement.getAlias().addAll(element.getAlias());
		clonedElement.getCode().addAll(element.getCode());
		clonedElement.setCommentElement(element.getCommentElement());
		clonedElement.getCondition().addAll(element.getCondition());
		clonedElement.getConstraint().addAll(element.getConstraint());
		clonedElement.setDefaultValue(element.getDefaultValue());
		clonedElement.setId(element.getId());
		clonedElement.setExample(element.getExample());
		clonedElement.setFixed(element.getFixed());
		clonedElement.setIsSummary(element.getIsSummary());
		clonedElement.getMapping().addAll(element.getMapping());
		clonedElement.setMaxLength(element.getMaxLength());
		clonedElement.setMeaningWhenMissing(element.getMeaningWhenMissing());
		clonedElement.setPattern(element.getPattern());
		clonedElement.getRepresentation().addAll(element.getRepresentation());
		clonedElement.setRequirements(element.getRequirements());
		clonedElement.setSlicing(element.getSlicing());
		return clonedElement;
	}
}