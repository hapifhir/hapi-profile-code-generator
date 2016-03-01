package ca.uhn.fhir.utils.codegen.hapi.methodgenerator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.model.api.IDatatype;
import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt.Binding;
import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt.Type;
import ca.uhn.fhir.model.dstu2.composite.ResourceReferenceDt;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;
import ca.uhn.fhir.utils.codegen.hapi.MethodBodyGenerator;
import ca.uhn.fhir.utils.codegen.hapi.FhirResourceManager;
import ca.uhn.fhir.utils.codegen.hapi.InterfaceAdapterGenerator;
import ca.uhn.fhir.utils.common.metamodel.Method;
import ca.uhn.fhir.utils.fhir.PathUtils;

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
	private String override;
	private List<String> imports;
	
	public CodedEnumAttributeHandler(FhirResourceManager manager, MethodBodyGenerator template, StructureDefinition profile, ElementDefinitionDt element) {
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
		if(bindingName != null) {
			accessors.add(constructSetMethod(java.lang.String.class.getCanonicalName(), InterfaceAdapterGenerator.generateInterfaceName(getProfile())).setBody(buildCodeEnumAsStringSetterBody(getTopLevelCoreAttribute(), bindingName)));
		} else if(bindingName == null && getFullyQualifiedType().equals("ca.uhn.fhir.model.primitive.CodeDt")){
			//Handle Immunization.getStatus() oddity
			accessors.add(constructSetMethod(java.lang.String.class.getCanonicalName(), InterfaceAdapterGenerator.generateInterfaceName(getProfile())).setBody(buildDelegatedSetterBody(getTopLevelCoreAttribute())));
		} else {
			//Handle ReferralRequest.status
			accessors.add(constructSetMethod(java.lang.String.class.getCanonicalName(), InterfaceAdapterGenerator.generateInterfaceName(getProfile())).setBody(buildCodeEnumAsStringSetterBody(getTopLevelCoreAttribute(), override)));
		}
		Method getMethod = constructGetMethodFromField(getTopLevelCoreAttribute() + "Element", getFullyQualifiedType()).setBody(buildDelegatedGetterBody(getTopLevelCoreAttribute() + "Element"));
		Method setMethod = constructSetMethod(getFullyQualifiedType(), InterfaceAdapterGenerator.generateInterfaceName(getProfile())).setBody(buildDelegatedSetterBody(getTopLevelCoreAttribute()));
		if(bindingName != null) {//There is in fact an enumeration defined in HAPI
			Method setMethodEnum = constructSetMethod(bindingName, InterfaceAdapterGenerator.generateInterfaceName(getProfile())).setBody(buildDelegatedSetterBody(getTopLevelCoreAttribute()));//HAPI Supports passing the enum directly to the setter.
			accessors.add(setMethodEnum);
		}
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
		setFullyQualifiedType(getFhirResourceManager().getFullyQualifiedJavaType(type));
		handleCode();
	}
	
	public void handleCode() {
		override = getFhirResourceManager().getCodeOverride(getElement().getPath());
		if(override != null) { //Enumerated type does not follow convention in naming
			if(override.equals("ca.uhn.fhir.model.dstu2.composite.CodeDt")) {
				setFullyQualifiedType(override);
			} else {
				setFullyQualifiedType("ca.uhn.fhir.model.primitive.BoundCodeDt<" + override + ">");
				bindingName = override;
			}
			imports.add(override);
		} else if(getFullyQualifiedType().equalsIgnoreCase(ca.uhn.fhir.model.primitive.CodeDt.class.getName()) 
				&& getElement().getBinding() != null) {
			bindingName = getBindingNameFromValueSetReference();
			identifyValidEnumerationType(bindingName);
			if(enumType == null) {
				bindingName = getResourceName() + StringUtils.capitalize(getTopLevelCoreAttribute());
				identifyValidEnumerationType(bindingName);
			}
			bindingName = enumType;
			if(bindingName != null) {
				setFullyQualifiedType("ca.uhn.fhir.model.primitive.BoundCodeDt<" + bindingName + ">");
				imports.add(bindingName);
			} else {
				LOGGER.error("No bindings discovered for " + getElement().getPath());
			}
		}
	}
	
	public String getBindingNameFromValueSetReference() {
		Binding binding = getElement().getBinding();
		String bindingName = null;
		if(binding != null && binding.getValueSet() != null) {
			IDatatype valueSet = binding.getValueSet();
			if(valueSet instanceof ResourceReferenceDt) {
				String valueSetUri = ((ResourceReferenceDt) valueSet).getReference().getValueAsString();
				bindingName = PathUtils.getEnumNameFromValueSetBindingUri(valueSetUri);
			}
		}
		return bindingName;
	}
	
	public void identifyValidEnumerationType(String bindingName) {
		if(bindingName != null) {
			String enumType1 = getEnumType1(bindingName);
			String enumType2 = getEnumType2(bindingName);
			if(classExists(enumType1)) {
				enumType = enumType1;
			} else if(classExists(enumType2)) {
				enumType = enumType2;
			} else {
				enumType = null;//Reset enumType
			}
		}
	}
	
	public String getEnumType1(String bindingName) {
		return "ca.uhn.fhir.model.dstu2.valueset." + bindingName + "Enum";
	}
	
	public String getEnumType2(String bindingName) {
		return "ca.uhn.fhir.model.dstu2.valueset." + bindingName + "CodesEnum";
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
