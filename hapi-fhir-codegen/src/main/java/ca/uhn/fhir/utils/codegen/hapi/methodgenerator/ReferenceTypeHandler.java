package ca.uhn.fhir.utils.codegen.hapi.methodgenerator;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt.Type;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;
import ca.uhn.fhir.parser.DataFormatException;
import ca.uhn.fhir.utils.codegen.CodeGenerationUtils;
import ca.uhn.fhir.utils.codegen.hapi.MethodBodyGenerator;
import ca.uhn.fhir.utils.codegen.hapi.dstu2.FhirResourceManagerDstu2;
import ca.uhn.fhir.utils.codegen.hapi.HapiFhirUtils;
import ca.uhn.fhir.utils.codegen.hapi.InterfaceAdapterGenerator;
import ca.uhn.fhir.utils.common.metamodel.Method;

/**
 * Class handles top-level, non-multi-type FHIR attributes that are reference types.
 * In FHIR, a reference type is a pointer to another FHIR resource. This resource may
 * be profiled or not profiled.
 * <p>
 * Note that multi-type attributes (e.g., resource.attribute[x]) are not handled by this handler.
 * <p>
 * Also note that after instantiating this class you must also call the initialize() 
 * method as illustrated below:
 * <pre>
 * <code>
 * ReferenceTypeHandler handler = new ReferenceTypeHandler(manager, template, profile, element);
 * handler.initialize();
 * </code>
 * </pre>
 * 
 * TODO Handle profiled references.
 * 
 * @author Claude Nanjo
 *
 */
public class ReferenceTypeHandler extends BaseMethodGenerator {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(ReferenceTypeHandler.class);
	
	public ReferenceTypeHandler(FhirResourceManagerDstu2 manager, MethodBodyGenerator template, StructureDefinition profile, ElementDefinitionDt element) {
		super(manager, template, profile, element);
	}
	
	/**
	 * Workhorse method of MethodHandler. Generates methods corresponding
	 * to the metadata specified by the FHIR profile element.
	 * <p>
	 * This method generates two getters and two setters:
	 * <ol>
	 * <li>A getter that returns the java type</li>
	 * <li>A getter that returns the HAPI FHIR type</li>
	 * <li>A setter that takes the java type</li>
	 * <li>A setter that takes the HAPI FHIR type</li>
	 * </ol>
	 * For instance, given an attribute called <code>active</code>
	 * defined on the FHIR <code>Patient</code> resource. The following
	 * methods are generated:
	 * <pre>
	 * <code>
	 * public BooleanDt getActiveElement();
	 * public Boolean getActive();
	 * public void setActive(Boolean param);
	 * public void setActive(BooleanDt param);
	 * </code>
	 * </pre>
	 */
	@Override
	public List<Method> buildCorrespondingMethods() {
		try {
			List<Method> methods = new ArrayList<Method>();
			if(!appliesTo(getProfile(), getElement()) || ignoreField()) {
				return methods;
			} else {
				if(isMultipleCardinality()) {
					handleMultipleCardinality(methods);
				} else {
					handleSingleCardinality(methods);
				}
				
				return methods;
			}
		} catch(Exception e) {
			LOGGER.error("Error building methods for " + getElement().getPath(), e);
			throw new RuntimeException("Error building methods for " + getElement().getPath(), e);
		}
	}
	
	public void initialize() {
		super.initialize();
		handleType(getElement().getTypeFirstRep());
		parseTopLevelCoreAttribute();
	}
	
	/**
	 * Generates methods for attributes of 0..1 or 1..1 cardinalities.
	 * <p>
	 * This method generates two getters and two setters:
	 * <ol>
	 * <li>A getter that returns the java type</li>
	 * <li>A getter that returns the HAPI FHIR type</li>
	 * <li>A setter that takes the java type</li>
	 * <li>A setter that takes the HAPI FHIR type</li>
	 * </ol>
	 * For instance, given an attribute called <code>active</code>
	 * defined on the FHIR <code>Patient</code> resource, the following
	 * methods are generated:
	 * <pre>
	 * <code>
	 * public BooleanDt getActiveElement();
	 * public Boolean getActive();
	 * public void setActive(Boolean param);
	 * public void setActive(BooleanDt param);
	 * <p>
	 * @param accessors
	 */
	protected void handleSingleCardinality(List<Method> accessors) {
		String descriminator = "";
		String profileUrl = getElement().getTypeFirstRep().getProfileFirstRep().getValueAsString();
		boolean isProfiledResource = false;
		String adapterClass = null;
		String resourceName = FhirResourceManagerDstu2.getProfileSuffix(profileUrl);
		if(getElement().getType().size() > 1) {
			descriminator = resourceName;
		}
		Class<?> resourceClass = getFhirResourceManager().addResourceToIndex(resourceName);
		if(resourceClass == null) {
			StructureDefinition profile = getFhirResourceManager().getProfileFromProfileUri(profileUrl);
			String profileName = CodeGenerationUtils.makeIdentifierJavaSafe(profile.getName());
			resourceName = FhirResourceManagerDstu2.getProfileSuffix(profile.getBase());
			resourceClass = getFhirResourceManager().addResourceToIndex(resourceName);
			isProfiledResource = (resourceClass != null);
			adapterClass = getGeneratedCodePackage() + "." + profileName + "Adapter";
		}
		if(resourceClass != null) {
			String fluentReturnType = getFluentReturnType();
			setFullyQualifiedType(getFhirResourceManager().addResourceToIndex(resourceName).getCanonicalName());
			if(!isProfiledResource) {
				accessors.add(constructGetMethodFromField(getTopLevelCoreAttribute() + descriminator + "Resource", getFullyQualifiedType()).setBody(buildReferenceGetterBody()));
				accessors.add(constructSetMethodFromField(getTopLevelCoreAttribute() + "Resource", getFullyQualifiedType(), fluentReturnType).setBody(buildReferenceSetterBody()));
			} else {
				accessors.add(constructGetMethodFromField(getTopLevelCoreAttribute() + descriminator + "Resource", adapterClass).setBody(buildProfiledReferenceGetterBody(adapterClass)));
				accessors.add(constructSetMethodFromField(getTopLevelCoreAttribute() + "Resource", adapterClass, fluentReturnType).setBody(buildProfiledReferenceSetterBody()));
			}
		} else {
			LOGGER.error("No class found for the following type: " + resourceName + ". Skipping code generation for " + getTopLevelCoreAttribute());
		}
	}
	
	/**
	 * Generates methods for attributes of 0..* cardinalities.
	 * <p>
	 * This method generates a getter, a setter, and an add method:
	 * <ol>
	 * <li>A getter that returns a list of HAPI FHIR types</li>
	 * <li>A setter that takes a list of HAPI FHIR type</li>
	 * <li>An add method that takes an item to add to a list</li>
	 * </ol>
	 * For instance, given an attribute called <code>address</code>
	 * with a cardinality of 0..1 defined on the FHIR <code>Patient</code>
	 * resource, the following methods are generated:
	 * <pre>
	 * <code>
	 * public List<AddressDt> getAddress();
	 * public void setAddress(List<AddressDt> param);
	 * public void addAddress(AddressDt param);
	 * <p>
	 * @param accessors
	 */
	protected void handleMultipleCardinality(List<Method> accessors) {
		//TODO defer for later if a case arises
	}
	
	/**
	 * Method to get the HAPI FHIR type associated with the FHIR type: 'Reference'
	 * 
	 * @param type
	 */
	public void handleType(Type type) {
		setFullyQualifiedType(HapiFhirUtils.getPrimitiveTypeClassName(getFhirResourceManager().getFhirContext(), type.getCode()));
	}
	
	/**
	 * Method assesses whether the handler can process the ElementDefinitionDt argument.
	 * If the method can process the argument, it returns true. Otherwise, it returns false.
	 * This method will return true only if:
	 * <ul>
	 * <li>The element has a reference type</li>
	 * </ul>
	 * 
	 * TODO Handle profiled resources such as QICore
	 * 
	 * @param profile
	 * @param element
	 * @return
	 */
	public static boolean appliesTo(StructureDefinition profile, ElementDefinitionDt element) {
		if(FhirResourceManagerDstu2.elementHasNoType(element) || FhirResourceManagerDstu2.isMultiTypeAttribute(element)) {
			return false;
		} else {
			if(element.getTypeFirstRep().getCode() == null || FhirResourceManagerDstu2.isFhirExtension(element)) {
				return false;
			} else if(element.getTypeFirstRep().getCode().equals("Reference")) {
				return true;
			} else {
				return false;
			}
		}
	}

}
