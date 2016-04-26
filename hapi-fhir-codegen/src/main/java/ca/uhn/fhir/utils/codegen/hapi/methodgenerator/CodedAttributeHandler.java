package ca.uhn.fhir.utils.codegen.hapi.methodgenerator;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt.Type;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;
import ca.uhn.fhir.utils.codegen.CodeGenerationUtils;
import ca.uhn.fhir.utils.codegen.hapi.HapiFhirUtils;
import ca.uhn.fhir.utils.codegen.hapi.HapiType;
import ca.uhn.fhir.utils.codegen.hapi.MethodBodyGenerator;
import ca.uhn.fhir.utils.codegen.hapi.dstu2.FhirResourceManagerDstu2;
import ca.uhn.fhir.utils.common.metamodel.Method;

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
public class CodedAttributeHandler extends BaseMethodGenerator {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(CodedAttributeHandler.class);
	private String enumType;
	private List<String> imports;
	
	public CodedAttributeHandler(FhirResourceManagerDstu2 manager, MethodBodyGenerator template, StructureDefinition profile, ElementDefinitionDt element) {
		super(manager, template, profile, element);
		imports = new ArrayList<String>();
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
		if(FhirResourceManagerDstu2.elementHasNoType(element) || FhirResourceManagerDstu2.isMultiTypeAttribute(element)) {
			return false;
		} else {
			if(element.getTypeFirstRep().getCode() == null || FhirResourceManagerDstu2.isFhirExtension(element)) {
				return false;
			} else if(element.getTypeFirstRep().getCode().equals("CodeableConcept")) {
				return true;
			} else {
				return false;
			}
		}
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
		Method getMethod = constructGetMethod(getFullyQualifiedType()).setBody(buildDelegatedGetterBody(getTopLevelCoreAttribute()));
		Method setMethod = constructSetMethod(getFullyQualifiedType(), getFluentReturnType()).setBody(buildCodeableConceptSetterBody(getTopLevelCoreAttribute()));
		getMethod.getImports().addAll(imports);
		setMethod.getImports().addAll(imports);
		accessors.add(getMethod);
		accessors.add(setMethod);
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
		//setFullyQualifiedType(getFhirResourceManager().getFullyQualifiedJavaType(getProfile(), type));
		handleCodeableConcept();
	}
	
	public void handleCodeableConcept() {
		String attributePath = getElement().getPath();
		String fieldName = CodeGenerationUtils.getSuffix(getResourceName(), attributePath);
		HapiType boundType = HapiFhirUtils.resolveBoundedAttributeTypes(getFhirResourceManager().getFhirContext(), getResourceName(), fieldName);
		if(boundType.isEnumerationType()) {
			enumType = boundType.getEnumerationType();
			imports.add(boundType.getEnumerationType());
			setFullyQualifiedType(boundType.getCodedTypeAsString());
		} else {
			setFullyQualifiedType(boundType.getDatatype());
		}
//		if(override != null) { //Enumerated type does not follow convention in naming
//			if(override.equals("ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt")) {
//				setFullyQualifiedType(override);
//			} else {
//				setFullyQualifiedType("ca.uhn.fhir.model.dstu2.composite.BoundCodeableConceptDt<" + override + ">");
//			}
//			imports.add(override);
//		} else if(getFullyQualifiedType().equalsIgnoreCase(ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt.class.getName()) 
//				&& getElement().getBinding() != null 
//				&& !isMultipleCardinality()) {
//			Binding binding = getElement().getBinding();
//			String bindingName = null;
//			if(binding != null && binding.getValueSet() != null) {
//				IDatatype vs = binding.getValueSet();
//				if(vs instanceof ResourceReferenceDt) {
//					String valueSetUri = ((ResourceReferenceDt) vs).getReference().getValueAsString();
//					bindingName = PathUtils.getEnumNameFromValueSetBindingUri(valueSetUri);
//				}
//			}
//			if(bindingName == null) {
//				bindingName = getResourceName() + StringUtils.capitalize(getTopLevelCoreAttribute());
//			}
//			//bindingName = getParentClass() + getElement().getName();//TODO may be Condition[c]ategoryCodesEnum - check case
//			String enumClassName1 = "ca.uhn.fhir.model.dstu2.valueset." + bindingName + "Enum";
//			String enumClassName2 = "ca.uhn.fhir.model.dstu2.valueset." + bindingName + "CodesEnum";
//			if(classExists(enumClassName1)) {
//				enumType = enumClassName1;
//			} else if(classExists(enumClassName2)) {
//				enumType = enumClassName2;
//			}
//			if(enumType != null) {
//				setFullyQualifiedType("ca.uhn.fhir.model.dstu2.composite.BoundCodeableConceptDt<" + enumType  + ">");
//				imports.add(enumType);
//			}
//		}
	}

}
