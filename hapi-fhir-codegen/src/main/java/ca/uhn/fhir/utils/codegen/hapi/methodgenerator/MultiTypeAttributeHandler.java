package ca.uhn.fhir.utils.codegen.hapi.methodgenerator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt.Type;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;
import ca.uhn.fhir.utils.codegen.hapi.HapiFhirUtils;
import ca.uhn.fhir.utils.codegen.hapi.MethodBodyGenerator;
import ca.uhn.fhir.utils.codegen.hapi.dstu2.FhirResourceManagerDstu2;
import ca.uhn.fhir.utils.common.metamodel.Method;
import ca.uhn.fhir.utils.fhir.PathUtils;

/**
 * Class handles top-level, non-multi-type FHIR attributes that do not map to
 * a java language type.
 * <p>
 * Note that multi-type attributes (e.g., resource.attribute[x]) or resource 
 * reference types (e.g., Procedure.patient) are not handled by this handler.
 * <p>
 * Also note that after instantiating this class you must also call the initialize() 
 * method as illustrated below:
 * <pre>
 * <code>
 * SimpleAttributeHandler handler = new SimpleAttributeHandler(manager, template, profile, element);
 * handler.initialize();
 * </code>
 * </pre>
 * 
 * @author Claude Nanjo
 *
 */
public class MultiTypeAttributeHandler extends BaseMethodGenerator {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(MultiTypeAttributeHandler.class);
	
	public MultiTypeAttributeHandler(FhirResourceManagerDstu2 manager, MethodBodyGenerator template, StructureDefinition profile, ElementDefinitionDt element) {
		super(manager, template, profile, element);
	}
	
	/**
	 * Method assesses whether the handler can process the ElementDefinitionDt argument.
	 * If the method can process the argument, it returns true. Otherwise, it returns false.
	 * This method will return true only if:
	 * <ul>
	 * <li>The element has a single type</li>
	 * <li>The element has a HAPI FHIR type that does not correspond to a java type (e.g., CodeableConceptDt)</li>
	 * </ul>
	 * 
	 * @param profile
	 * @param element
	 * @return
	 */
	public static boolean appliesTo(StructureDefinition profile, ElementDefinitionDt element) {
		if(FhirResourceManagerDstu2.elementHasNoType(element) || element.getType().size() == 1  || FhirResourceManagerDstu2.isFhirExtension(element)) {
			return false;
		} else {
			if(PathUtils.isMultivaluedAttribute(element.getPath())) {
				return true;
			} else {
				return false;
			}
		}
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
				List<ElementDefinitionDt> flattenedElements = FhirResourceManagerDstu2.flattenElementByType(getElement());
				for(ElementDefinitionDt flattenedElement : flattenedElements) {
					if(isMultipleCardinality()) {
						handleMultipleCardinality(methods, flattenedElement);
					} else {
						handleSingleCardinality(methods, flattenedElement);
					}
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
	protected void handleSingleCardinality(List<Method> accessors, ElementDefinitionDt element) {
		String typeString = element.getTypeFirstRep().getCode();
		String qualifiedAttribute = getTopLevelCoreAttribute() + StringUtils.capitalize(typeString);
		String fullyQualifiedType = getFullyQualifiedType(element.getTypeFirstRep());
		String fluentReturnType = getFluentReturnType();
		String javaType = FhirResourceManagerDstu2.getPrimitiveEquivalent(fullyQualifiedType);
		if(javaType == null) {
			accessors.add(constructGetMethodFromField(qualifiedAttribute, fullyQualifiedType).setBody(buildMultivaluedGetterBody(fullyQualifiedType)));
			accessors.add(constructSetMethodFromField(qualifiedAttribute, fullyQualifiedType, fluentReturnType).setBody(buildDelegatedSetterBody(getTopLevelCoreAttribute())));
		} else {
			accessors.add(constructGetMethodFromField(qualifiedAttribute + "Element", fullyQualifiedType).setBody(buildMultivaluedGetterBody(fullyQualifiedType)));
			accessors.add(constructGetMethodFromField(qualifiedAttribute, javaType).setBody(buildMultivaluedPrimitiveGetterBody(fullyQualifiedType)));
			accessors.add(constructSetMethodFromField(qualifiedAttribute, fullyQualifiedType, fluentReturnType).setBody(buildDelegatedSetterBody(getTopLevelCoreAttribute())));
			accessors.add(constructSetMethodFromField(qualifiedAttribute, javaType, fluentReturnType).setBody(buildJavaTypeSetterBody(getTopLevelCoreAttribute(), fullyQualifiedType)));
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
	protected void handleMultipleCardinality(List<Method> accessors, ElementDefinitionDt element) {
//		accessors.add(constructGetMethodForMultiCardinalityField(getTopLevelCoreAttribute(),getFullyQualifiedType()).setBody(buildDelegatedGetterBody(getTopLevelCoreAttribute())));
//		accessors.add(constructSetMethodForMultiCardinalityField(getTopLevelCoreAttribute(),getFullyQualifiedType()).setBody(buildDelegatedSetterBody(getTopLevelCoreAttribute())));
//		accessors.add(constructAddMethod(getTopLevelCoreAttribute(), getFullyQualifiedType()).setBody(buildDefaultAddBody(getTopLevelCoreAttribute())));
	}
	
	/**
	 * Method that identifies the HAPI FHIR type
	 * 
	 * @param type
	 */
	public String getFullyQualifiedType(Type type) {
		System.out.println(getElement().getPath());
		System.out.println(type.getCode());
		System.out.println(type.getProfile());
		String tentativeType = null;
		if(type.getCode().equals("Reference")) {
			System.out.println("Stop here");
			//tentativeType = getFhirResourceManager().getFullyQualifiedJavaType(getProfile(), type);
			tentativeType = ca.uhn.fhir.model.dstu2.composite.ResourceReferenceDt.class.getName(); //TODO Talk to James about this case.
		} else {
			//tentativeType = getFhirResourceManager().getFullyQualifiedJavaType(getProfile(), type);
			tentativeType = HapiFhirUtils.getDataTypeClass(getFhirResourceManager().getFhirContext(), type).getName();
		}
		return tentativeType;
	}
}
