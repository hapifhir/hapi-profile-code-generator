package org.cdscollaborative.tools.fhir.codegenerator.method;

import java.util.ArrayList;
import java.util.List;

import org.cdscollaborative.model.meta.Method;
import org.cdscollaborative.tools.fhir.codegenerator.CodeTemplateUtils;
import org.cdscollaborative.tools.fhir.utils.FhirResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt.Type;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;
import ca.uhn.fhir.model.primitive.BooleanDt;

/**
 * Class handles top-level, non-multi-type FHIR attributes that map to
 * a java language type such as <code>Patient.active</code> (a boolean 
 * attribute)
 * <p>
 * Note that multi-type attributes (e.g., resource.attribute[x], top-level 
 * attributes with types that do not map directly to a java type (e.g., 
 * CodeableConcept), or resource reference types (e.g., Procedure.patient)
 * are not handled by this handler.
 * <p>
 * Also note that after instantiating this class you must also call the initialize() 
 * method as illustrated below:
 * <pre>
 * <code>
 * JavaTypeHandler handler = new JavaTypeHandler(manager, template, profile, element);
 * handler.initialize();
 * </code>
 * </pre>
 * 
 * @author Claude Nanjo
 *
 */
public class JavaTypeHandler extends BaseMethodGenerator {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(JavaTypeHandler.class);
	
	private String javaType;
	
	public JavaTypeHandler(FhirResourceManager manager, CodeTemplateUtils template, StructureDefinition profile, ElementDefinitionDt element) {
		super(manager, template, profile, element);
	}
	
	/**
	 * Returns the java type that corresponds to the type returned
	 * by element.getType().
	 * 
	 * @return
	 */
	public String getJavaType() {
		return javaType;
	}
	
	/**
	 * Sets the java type associated with the type returned by
	 * element.getType();
	 * 
	 * @param javaType
	 */
	public void setJavaType(String javaType) {
		this.javaType = javaType;
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
					handleMultipleCardinalityJavaType(methods);
				} else {
					handleSingleCardinalityJavaType(methods);
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
	protected void handleSingleCardinalityJavaType(List<Method> accessors) {
		accessors.add(constructGetMethodFromField(getTopLevelCoreAttribute() + "Element",getFullyQualifiedType()).setBody(buildDelegatedGetterBody(getTopLevelCoreAttribute() + "Element")));
		accessors.add(constructGetMethod(javaType).setBody(buildJavaTypeGetterBody(getTopLevelCoreAttribute())));
		accessors.add(constructSetMethod(javaType).setBody(buildJavaTypeSetterBody(getTopLevelCoreAttribute())));
		accessors.add(constructSetMethod(getFullyQualifiedType()).setBody(buildDelegatedSetterBody(getTopLevelCoreAttribute())));
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
	protected void handleMultipleCardinalityJavaType(List<Method> accessors) {
		accessors.add(constructGetMethodForMultiCardinalityField(getTopLevelCoreAttribute(),getFullyQualifiedType()).setBody(buildDelegatedGetterBody(getTopLevelCoreAttribute())));
		accessors.add(constructSetMethodForMultiCardinalityField(getTopLevelCoreAttribute(),getFullyQualifiedType()).setBody(buildDelegatedSetterBody(getTopLevelCoreAttribute())));
		accessors.add(constructAddMethod(getTopLevelCoreAttribute(), getFullyQualifiedType()).setBody(buildDefaultAddBody(getTopLevelCoreAttribute())));
	}
	
	/**
	 * Method that identifies the corresponding java type for the HAPI FHIR type
	 * 
	 * @param type
	 */
	public void handleType(Type type) {
		setFullyQualifiedType(getFhirResourceManager().getFullyQualifiedJavaType(type));
		javaType = getFhirResourceManager().getPrimitiveEquivalent(getFullyQualifiedType());
	}
	
	/**
	 * Method assesses whether the handler can process the ElementDefinitionDt argument.
	 * If the method can process the argument, it returns true. Otherwise, it returns false.
	 * This method will return true only if:
	 * <ul>
	 * <li>The element has a single type</li>
	 * <li>The element has a HAPI FHIR type that has a corresponding java type (e.g., BooleanDt corresponds to java.lang.Boolean.</li>
	 * </ul>
	 * 
	 * @param profile
	 * @param element
	 * @return
	 */
	public static boolean appliesTo(StructureDefinition profile, ElementDefinitionDt element) {
		if(FhirResourceManager.elementHasNoType(element) || FhirResourceManager.isMultiTypeAttribute(element)) {
			return false;
		} else {
			if(element.getTypeFirstRep().getCode() == null || FhirResourceManager.isFhirExtension(element)) {
				return false;
			} else if(!FhirResourceManager.hasEquivalentJavaType(element.getTypeFirstRep())) {
				return false;
			} else if(element.getTypeFirstRep().getCode().equals("code")) {//Make exception for Code Enums
				return false;
			} else {
				return true;
			}
		}
	}

}
