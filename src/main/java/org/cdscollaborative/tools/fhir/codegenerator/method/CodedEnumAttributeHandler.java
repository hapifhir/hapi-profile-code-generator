package org.cdscollaborative.tools.fhir.codegenerator.method;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.cdscollaborative.model.meta.Method;
import org.cdscollaborative.tools.fhir.codegenerator.CodeTemplateUtils;
import org.cdscollaborative.tools.fhir.utils.FhirResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt.Type;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;

/**
 * Class handles top-level, non-multi-type FHIR attributes that represent coded fields.

 * Note that after instantiating this class you must also call the initialize() 
 * method as illustrated below:
 * <pre>
 * <code>
 * CodedAttributeHandler handler = new CodedAttributeHandler(manager, template, profile, element);
 * handler.initialize();
 * </code>
 * </pre>
 * 
 * @author Claude Nanjo
 *
 */
public class CodedEnumAttributeHandler extends BaseMethodGenerator {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(CodedEnumAttributeHandler.class);
	private String enumType;
	private String bindingName;
	private List<String> imports;
	
	public CodedEnumAttributeHandler(FhirResourceManager manager, CodeTemplateUtils template, StructureDefinition profile, ElementDefinitionDt element) {
		super(manager, template, profile, element);
		imports = new ArrayList<String>();
	}
	
	public boolean isEnumeration() {
		return enumType != null;
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
		parseTopLevelCoreAttribute();
		handleType(getElement().getTypeFirstRep());
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
		accessors.add(constructGetMethod(java.lang.String.class.getCanonicalName()).setBody(buildJavaTypeGetterBody(getTopLevelCoreAttribute())));
		accessors.add(constructSetMethod(java.lang.String.class.getCanonicalName()).setBody(buildCodeEnumAsStringSetterBody(getTopLevelCoreAttribute(), bindingName)));
		Method getMethod = constructGetMethodFromField(getTopLevelCoreAttribute() + "Element", getFullyQualifiedType()).setBody(buildDelegatedGetterBody(getTopLevelCoreAttribute() + "Element"));
		Method setMethod = constructSetMethod(getFullyQualifiedType()).setBody(buildDelegatedSetterBody(getTopLevelCoreAttribute()));
		getMethod.getImports().addAll(imports);
		setMethod.getImports().addAll(imports);
		accessors.add(getMethod);
		accessors.add(setMethod);
		
		
		
//		accessors.add(constructGetMethodFromField(getTopLevelCoreAttribute() + "Element",getFullyQualifiedType()).setBody(buildDelegatedGetterBody(getTopLevelCoreAttribute() + "Element")));
//		accessors.add(constructGetMethod(javaType).setBody(buildJavaTypeGetterBody(getTopLevelCoreAttribute())));
//		accessors.add(constructSetMethod(javaType).setBody(buildJavaTypeSetterBody(getTopLevelCoreAttribute())));
//		accessors.add(constructSetMethod(getFullyQualifiedType()).setBody(buildDelegatedSetterBody(getTopLevelCoreAttribute())));
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
//		accessors.add(constructGetMethodForMultiCardinalityField(getTopLevelCoreAttribute(),getFullyQualifiedType()).setBody(buildDelegatedGetterBody(getTopLevelCoreAttribute())));
//		accessors.add(constructSetMethodForMultiCardinalityField(getTopLevelCoreAttribute(),getFullyQualifiedType()).setBody(buildDelegatedSetterBody(getTopLevelCoreAttribute())));
//		accessors.add(constructAddMethod(getTopLevelCoreAttribute(), getFullyQualifiedType()).setBody(buildDefaultAddBody(getTopLevelCoreAttribute())));
	}
	
	/**
	 * Method that identifies, where relevant, associates the proper enumeration to
	 * this type. If no enumeration exists, the type itself is set.
	 * 
	 * @param type
	 */
	public void handleType(Type type) {
		String typeString = type.getCode();
		setFullyQualifiedType(getFhirResourceManager().getFullyQualifiedJavaType(typeString));
		handleCode();
	}
	
	public void handleCode() {
		if(getFullyQualifiedType().equalsIgnoreCase(ca.uhn.fhir.model.primitive.CodeDt.class.getName()) 
				&& getElement().getBinding() != null) {
			//Binding binding = getElement().getBinding();
			bindingName = getResourceName() + StringUtils.capitalize(getTopLevelCoreAttribute());//TODO Condition[v]erificationStatusEnum
			enumType = "ca.uhn.fhir.model.dstu2.valueset." + bindingName + "CodesEnum";
			if(classExists(enumType)) {
				setFullyQualifiedType("ca.uhn.fhir.model.primitive.BoundCodeDt<" + enumType + ">");
				imports.add(enumType);
			} else {
				enumType = "ca.uhn.fhir.model.dstu2.valueset." + bindingName + "Enum";
				if(classExists(enumType)) {
					setFullyQualifiedType("ca.uhn.fhir.model.primitive.BoundCodeDt<" + enumType + ">");
					imports.add(enumType);
				} else {
					LOGGER.info("Class " + enumType + " does not exist");
					enumType = null;
				}
			}
		}
	}
	
	/**
	 * Method assesses whether the handler can process the ElementDefinitionDt argument.
	 * If the method can process the argument, it returns true. Otherwise, it returns false.
	 * This method will return true only if:
	 * <ul>
	 * <li>The element has a single type of CodeableConcept</li>
	 * </ul>
	 * Note: Multi-type elements containing codeable concepts will require processing in order
	 * to have a single type.
	 * <p>
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
			} else if(element.getTypeFirstRep().getCode().equals("code")) {
				return true;
			} else {
				return false;
			}
		}
	}

}
