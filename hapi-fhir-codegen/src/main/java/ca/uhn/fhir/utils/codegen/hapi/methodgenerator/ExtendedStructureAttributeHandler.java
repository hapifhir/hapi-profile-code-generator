package ca.uhn.fhir.utils.codegen.hapi.methodgenerator;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;
import ca.uhn.fhir.utils.codegen.CodeGenerationUtils;
import ca.uhn.fhir.utils.codegen.hapi.MethodBodyGenerator;
import ca.uhn.fhir.utils.codegen.hapi.dstu2.FhirResourceManagerDstu2;
import ca.uhn.fhir.utils.codegen.hapi.InterfaceAdapterGenerator;
import ca.uhn.fhir.utils.common.metamodel.Method;
import ca.uhn.fhir.utils.fhir.PathUtils;

/**
 * Class handles FHIR extensions.
 * <p>
 * Note that non-extended attributes (part of FHIR core) are not handled by this handler.
 * <p>
 * Also note that after instantiating this class you must also call the initialize() 
 * method as illustrated below:
 * <pre>
 * <code>
 * ExtendedAttributeHandler handler = new ExtendedAttributeHandler(manager, template, profile, element);
 * handler.initialize();
 * </code>
 * </pre>
 * 
 * @author Claude Nanjo
 *
 */
public class ExtendedStructureAttributeHandler extends BaseExtensionMethodHandler {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(ExtendedStructureAttributeHandler.class);
	
	private boolean unsupported = false;
	private String extendedStructureName;
	
	public ExtendedStructureAttributeHandler(FhirResourceManagerDstu2 manager, MethodBodyGenerator template, StructureDefinition profile, ElementDefinitionDt element) {
		super(manager, template, profile, element);
	}
	
	public String getExtendedStructureName() {
		return extendedStructureName;
	}

	public void setExtendedStructureName(String extendedStructureName) {
		this.extendedStructureName = extendedStructureName;
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
			if(getExtendedElement().getType().get(0).getCode() != null && getExtendedElement().getType().get(0).getCode().equals("unsupported")) {//TODO add support for multi-typed attributes
				unsupported = true;
			}
			if(!appliesTo(getProfile(), getElement()) || ignoreField() || isSkipProcessing()) {
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
		handleExtensionElement();
	}
	
	/**
	 * Generates methods for extended attributes.
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
		String attributeName = PathUtils.getLastPathComponent(getElement().getName());
		if(!unsupported) {
			accessors.add(constructGetMethodFromField(attributeName, getFullyQualifiedType()).setBody(buildExtendedTypeGetterBody(getFullyQualifiedType(), getExtensionUri())));
			accessors.add(constructSetMethodFromField(attributeName, getFullyQualifiedType(), CodeGenerationUtils.makeIdentifierJavaSafe(getProfile().getName()) + getExtendedStructureName()).setBody(buildExtendedTypeSetterBody(getExtensionUri())));
		} else {
			accessors.add(constructGetMethodFromField(attributeName, getFullyQualifiedType()).setBody(buildUnsupportedGetter()));
			accessors.add(constructSetMethodFromField(attributeName, getFullyQualifiedType(), CodeGenerationUtils.makeIdentifierJavaSafe(getProfile().getName()) + getExtendedStructureName()).setBody(buildUnsupportedSetter()));
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
		//TODO Not implemented yet
		throw new UnsupportedOperationException("Operation is currently unsupported for multiple cardinality element " + getExtendedElement().getName());
	}
	
	/**
	 * Method assesses whether the handler can process the ElementDefinitionDt argument.
	 * If the method can process the argument, it returns true. Otherwise, it returns false.
	 * This method will return true only if:
	 * <ul>
	 * <li>The element is a FHIR extension (defined as an element with a single Type where
	 * code = 'Extension'</li>
	 * </ul>
	 * 
	 * @param profile
	 * @param element
	 * @return
	 */
	public static boolean appliesTo(StructureDefinition profile, ElementDefinitionDt element) {
//		if(FhirResourceManager.elementHasNoType(element) || FhirResourceManager.isMultiTypeAttribute(element)) {
//			return false;
//		} else {
//			return true;//TODO Fix later
//		}
		return true;
	}

}
