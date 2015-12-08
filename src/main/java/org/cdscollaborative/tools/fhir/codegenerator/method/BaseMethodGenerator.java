package org.cdscollaborative.tools.fhir.codegenerator.method;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.cdscollaborative.model.meta.Cardinality;
import org.cdscollaborative.model.meta.Method;
import org.cdscollaborative.tools.fhir.codegenerator.CodeGenerationUtils;
import org.cdscollaborative.tools.fhir.codegenerator.CodeTemplateUtils;
import org.cdscollaborative.tools.fhir.utils.FhirResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;


public abstract class BaseMethodGenerator implements IMethodHandler {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(BaseMethodGenerator.class);
	
	private FhirResourceManager fhirResourceManager;
	private StructureDefinition profile;
	private String parentClass;
	private ElementDefinitionDt element;
	private Cardinality cardinality;
	private String topLevelCoreAttribute;
	private boolean isMultivaluedType;
	private String fullyQualifiedType;
	private CodeTemplateUtils template;
	private String generatedCodePackage;

	/**
	 * Constructor setting an element and its parent profile
	 * 
	 * @param profile
	 * @param element
	 */
	public BaseMethodGenerator(FhirResourceManager manager, CodeTemplateUtils template, StructureDefinition profile, ElementDefinitionDt element) {
		this.fhirResourceManager = manager;
		this.template = template;
		this.profile = profile;
		this.element = element;
	}
	
	public abstract List<Method> buildCorrespondingMethods();
	
	/** 
	 * Method returns the profile that defines the element
	 * 
	 * @return
	 */
	public StructureDefinition getProfile() {
		return profile;
	}
	
	/**
	 * Method sets the profile that defines the element
	 * 
	 * @param profile
	 */
	public void setProfile(StructureDefinition profile) {
		this.profile = profile;
	}

	/**
	 * Method returns the element defined in the profile
	 * 
	 * @return
	 */
	public ElementDefinitionDt getElement() {
		return element;
	}
	
	/**
	 * Method sets the element defined in the profile
	 * @param element
	 */
	public void setElement(ElementDefinitionDt element) {
		this.element = element;
	}
	
	/**
	 * Returns the FHIR Resource Manager associated with this generator
	 * 
	 * @return
	 */
	public FhirResourceManager getFhirResourceManager() {
		return fhirResourceManager;
	}
	
	/**
	 * Sets the FHIR Resource Manager for this generator
	 * 
	 * @param fhirResourceManager
	 */
	public void setFhirResourceManager(FhirResourceManager fhirResourceManager) {
		this.fhirResourceManager = fhirResourceManager;
	}
	
	/**
	 * Returns the root resource that contains this attribute
	 * 
	 * @return
	 */
	public String getParentClass() {
		return parentClass;
	}
	
	/**
	 * Sets the root resource defined by this profile and that contains this attribute
	 * 
	 * @param rootResource
	 */
	public void setParentClass(String rootResource) {
		this.parentClass = rootResource;
	}
	
	/**
	 * Returns the unqualified name of the top level attribute being processed.
	 * For instance, if FHIR Condition or a profile derived from
	 * Condition has an attribute with path <code>Condition.status</code>, this
	 * method will return <code> status </code>
	 * @return
	 */
	public String getTopLevelCoreAttribute() {
		return topLevelCoreAttribute;
	}
	
	/**
	 * Method sets the top level attribute being processed. See getter
	 * definition for more information on top level attributes.
	 * 
	 * @param topLevelCoreAttribute
	 */
	public void setTopLevelCoreAttribute(String topLevelCoreAttribute) {
		this.topLevelCoreAttribute = topLevelCoreAttribute;
	}
	
	/**
	 * Method returns true if FHIR attribute has more than one type
	 * assigned. In FHIR such fields are typically denoted by
	 * <code>Resource.attribute[x]</code>
	 * 
	 */
	public Boolean isMultivaluedType() {
		return isMultivaluedType;
	}
	
	/**
	 * Method sets isMultivaluedType flag.
	 * 
	 * @param isMultivaluedType - A flag indicating whether a FHIR attribute is multivalued
	 */
	public void setIsMultivaluedType(Boolean isMultivaluedType) {
		this.isMultivaluedType = isMultivaluedType;
	}
	
	/**
	 * Returns the element's cardinality
	 * 
	 * @return
	 */
	public Cardinality getCardinality() {
		return cardinality;
	}
	
	/**
	 * Sets the element's cardinality
	 * 
	 * @param cardinality
	 */
	public void setCardinality(Cardinality cardinality) {
		this.cardinality = cardinality;
	}
	
	/**
	 * Returns the fully qualified HAPI FHIR type associated
	 * with this element type.
	 * 
	 * @return
	 */
	public String getFullyQualifiedType() {
		return fullyQualifiedType;
	}
	
	/**
	 * Sets the fully qualified HAPI FHIR type associated
	 * with this element type.
	 *  
	 * @param fullyQualifiedType
	 */
	public void setFullyQualifiedType(String fullyQualifiedType) {
		this.fullyQualifiedType = fullyQualifiedType;
	}
	
	/**
	 * Returns the template generation manager associated with this handler.
	 * 
	 * @return
	 */
	public CodeTemplateUtils getTemplate() {
		return template;
	}
	
	/**
	 * Sets the template generation manager associated with this handler.
	 * 
	 * @param template
	 */
	public void setTemplate(CodeTemplateUtils template) {
		this.template = template;
	}
	
	/**
	 * Assigns the parent class for this attribute. For instance, if the attribute
	 * Condition.status is processed, the FHIR Condition class' canonical name shall 
	 * be returned.
	 */
	protected void assignParentClass() {
		this.parentClass = fhirResourceManager.addResourceToIndex(profile).getCanonicalName();
	}
	
	/**
	 * Returns the code generation package. This is necessary for generated
	 * types not part of FHIR core.
	 * 
	 * @return
	 */
	public String getGeneratedCodePackage() {
		return generatedCodePackage;
	}

	/**
	 * Sets the code generation package. This is necessary for generated
	 * types not part of FHIR core.
	 * 
	 * @return
	 */
	public void setGeneratedCodePackage(String generatedCodePackage) {
		this.generatedCodePackage = generatedCodePackage;
	}
	
	/*
	 * Configuration and helper method section starts here
	 */

	/**
	 * Initialization method that must be called after the object is
	 * constructed. Initialize is responsible for populating the state
	 * of the handler based on the metadata supplied by the ElementDefinitionDt
	 * instance. This metadata is used to guide the generation of method
	 * definitions.
	 * <p>
	 * Specialized handlers must call super.initialize().
	 */
	public void initialize() {
		if(element == null) {
			LOGGER.error("Profile element cannot be null");
			throw new RuntimeException("Profile element cannot be null");
		}
		assignCardinality();
		if(parentClass == null) {
			assignParentClass();
		}
	}
	
	/**
	 * A top level attribute is a direct child of the parent resource (e.g., Condition.id).
	 * Lower level attributes are more deeply nested (e.g., Condition.location.code).
	 * Method returns the name of the top level attribute or null if the attribute is a structure
	 * containing more deeply nested attributes.
	 * 
	 *  TODO Does it belong here? Does this method do too much?
	 * 
	 * @return
	 */
	public void parseTopLevelCoreAttribute() {
		String resourceName = CodeGenerationUtils.getLastPathComponent(parentClass);
		String attributePath = element.getPath();
		String suffix = CodeGenerationUtils.getSuffix(resourceName, attributePath);
		if(suffix != null && suffix.indexOf('.') >= 0) {
			LOGGER.info(element.getPath() + " is not of the form Resource.attribute.");
			suffix = null;
		}
		if(suffix != null && suffix.indexOf('.') < 0) {
			if(FhirResourceManager.isMultivaluedAttribute(suffix)) {
				suffix = FhirResourceManager.cleanMultiValuedAttributeName(suffix);
			}
		}
		topLevelCoreAttribute = suffix;
	}
	
	/**
	 * Translate common cardinalities into a corresponding enumeration type.
	 * 
	 */
	public void assignCardinality() {
		int min = element.getMin();
		String max = (element.getMax()!= null)?element.getMax():"";
		if(min == 1 && max.equals("1")) {
			setCardinality(Cardinality.REQUIRED);
		} else if(min == 0 && max.equals("1")) {
			setCardinality(Cardinality.OPTIONAL);
		} else if(min == 0 && max.equals("0")) {
			setCardinality(Cardinality.CONSTRAINED_OUT);
		} else if(min == 0 && max.equals("*")) {
			setCardinality(Cardinality.OPTIONAL_MULTIPLE);
		} else if(min == 1 && max.equals("*")) {
			setCardinality(Cardinality.REQUIRED_MULTIPLE);
		}
	}
	
	public boolean isMultipleCardinality() {
		return (cardinality == Cardinality.OPTIONAL_MULTIPLE || cardinality == Cardinality.REQUIRED_MULTIPLE);
	}
	
	/**
	 * Some field types in HAPI can be ignored
	 * 
	 * @param suffix
	 * @return
	 */
	public boolean ignoreField() {
		boolean ignoreField = false;
		if(topLevelCoreAttribute == null) {
			if(!FhirResourceManager.isFhirExtension(element)) {
				ignoreField = true;
				LOGGER.debug("Top level attribute is null for " + element.getPath() + ". It is probably a FHIR structure.");
			} else {
				ignoreField = false;
			}
		} else if(topLevelCoreAttribute.equalsIgnoreCase("ModifierExtension")) {
			ignoreField = true;
		} else if(topLevelCoreAttribute.equalsIgnoreCase("ImplicitRules")) { //TODO Not sure what this is. Investigate.
			ignoreField = true;
		}
		return ignoreField;
	}
	
	/**
	 * Returns true is class has been loaded by current
	 * classloader. 
	 * 
	 * @param clazz
	 * @return
	 */
	public boolean classExists(String clazz) {
		boolean found = false;
		try {
			Class.forName(clazz, false, this.getClass().getClassLoader());
			found = true;
		} catch(ClassNotFoundException cnfe) {
			LOGGER.info(clazz + " does not exist!");
		}
		return found;
	}
	
	/*
	 * Method construction helpers
	 */
	
	/**
	 * Builds getter signature name using the JavaBean convention
	 * 
	 * @param fieldName
	 * @return
	 */
	public String buildGetterName(String fieldName) {
		return "get" + StringUtils.capitalize(fieldName);
	}
	
	/**
	 * Builds setter signature name using the JavaBean convention
	 * 
	 * @param fieldName
	 * @return
	 */
	public String buildSetterName(String fieldName) {
		return "set" + StringUtils.capitalize(fieldName);
	}
	
	/**
	 * Builds a method signature that adds an element to a List type
	 * 	
	 * @param fieldName
	 * @return
	 */
	public String buildAddMethodName(String fieldName) {
		return "add" + StringUtils.capitalize(fieldName);
	}
	
	/**
	 * Constructs a setter method for the given field name and parameters
	 * using the java bean convention.
	 * 
	 * @param fieldName
	 * @param parameterType
	 * @return
	 */
	public Method constructSetMethodFromField(String fieldName, String parameterType) {
		List<String> parameterTypes = new ArrayList<String>();
		parameterTypes.add(parameterType);
		return Method.constructVoidMethod(buildSetterName(fieldName), parameterTypes);
	}
	
	/**
	 * Construct a getter method for the given field name and type with the java bean
	 * convention.
	 * 
	 * @param fieldName
	 * @param type
	 * @return
	 */
	public Method constructGetMethodFromField(String fieldName, String type) {
		return Method.constructNoArgMethod(buildGetterName(fieldName), type);
	}
	
	/**
	 * Constructs a setter for a field of multiple cardinality.
	 * <pre>
	 * <code>
	 * public void setFieldName(List&lt;ParameterType&gt;)
	 * </code>
	 * </pre>
	 * 
	 * @param fieldName
	 * @param parameterType
	 * @return
	 */
	public Method constructSetMethodForMultiCardinalityField(String fieldName, String parameterType) {
		List<String> parameterTypes = new ArrayList<String>();
		parameterTypes.add("List<" + parameterType + ">");
		Method method = Method.constructVoidMethod(buildSetterName(fieldName), parameterTypes);
		method.addImport("java.util.List");
		return method;
	}
	
	/**
	 * Constructs a getter for a field of multiple cardinality.
	 * <pre>
	 * <code>
	 * public List&lt;ParameterType&gt; getFieldName()
	 * </code>
	 * </pre>
	 * @param fieldName
	 * @param type
	 * @return
	 */
	public Method constructGetMethodForMultiCardinalityField(String fieldName, String type) {
		String listType = "List<" + type + ">";
		Method method = Method.constructNoArgMethod(buildGetterName(fieldName), listType);
		method.addImport("java.util.List");
		return method;
	}
	
	/**
	 * Constructs add method for a field of multiple cardinality.
	 * <pre>
	 * <code>
	 * public void addFieldName(type param)
	 * </code>
	 * </pre>
	 * @param fieldName
	 * @param type
	 * @return
	 */
	public Method constructAddMethod(String fieldName, String type) {
		Method method = new Method();
		method.setName(buildAddMethodName(fieldName));
		method.addParameter(type);
		return method;
	}
	
	/**
	 * Convenience method to create setter using top level attribute as the field.
	 * 
	 * @param parameterType
	 * @return
	 */
	public Method constructSetMethod(String parameterType) {
		return constructSetMethodFromField(getTopLevelCoreAttribute(), parameterType);
	}
	
	/**
	 * Convenience method to create getter using top level attribute as the field.
	 * 
	 * @param fieldType
	 * @return
	 */
	public Method constructGetMethod(String fieldType) {
		return constructGetMethodFromField(getTopLevelCoreAttribute(), fieldType);
	}
	
	/*
	 * Method body constructor
	 */
	
	/**
	 * <pre>
	 * <code>
	 * return adaptee.get{PropertyName}Element()
	 * </code>
	 * </pre>
	 * @param propertyName
	 * @return
	 */
	public String buildDelegatedGetterBody(String propertyName) {
		return template.getAdapterGetMethodDelegationBody(propertyName);
	}
	
	/**
	 * <pre>
	 * <code>
	 * return adaptee.get{PropertyName}();
	 * </code>
	 * </pre>
	 * @param propertyName
	 * @return
	 */
	public String buildJavaTypeGetterBody(String propertyName) {
		return template.getAdapterGetMethodDelegationBody(propertyName);
	}
	
	/**
	 * <pre>
	 * <code>
	 * adaptedClass.set{PropertyName}(param);
	 * </code>
	 * </pre>
	 * @param propertyName
	 * @return
	 */
	public String buildDelegatedSetterBody(String propertyName) {
		return template.getAdapterSetMethodDelegationBody(propertyName);
	}
	
	/**
	 * <pre>
	 * <code>
	 * adaptedClass.set{ParameterName}(new ca.uhn.fhir.model.primitive.BooleanDt(param));
	 * </code>
	 * </pre>
	 * @param propertyName
	 * @return
	 */
	public String buildJavaTypeSetterBody(String propertyName, String type) {
		return template.getSetMethodDelegationBody(propertyName, "new " + type + "(param)");
	}
	
	/**
	 * <pre>
	 * <code>
	 * adaptedClass.set{ParameterName}(new ca.uhn.fhir.model.primitive.BooleanDt(param));
	 * </code>
	 * </pre>
	 * @param propertyName
	 * @return
	 */
	public String buildJavaTypeSetterBody(String propertyName) {
		return buildJavaTypeSetterBody(propertyName, getFullyQualifiedType());
	}
	
	/**
	 * <pre>
	 * <code>
	 * adaptedClass.get{ParameterName}.add(param);
	 * </code>
	 * </pre>
	 * @param propertyName
	 * @return
	 */
	public String buildDefaultAddBody(String propertyName) {
		return template.getAddToListMethodBody("adaptedClass", propertyName);
	}
	
	/**
	 * 
	 * @param propertyName
	 * @return
	 */
	public String buildCodeableConceptSetterBody(String propertyName) {
		return template.getSetMethodDelegationBody(propertyName, "param");
	}
	
	/**
	 * <pre>
	 * <code>
	 * adaptedClass.set{FieldName}({bindingName}.valueOf(param));
	 * </code>
	 * </pre>
	 * 
	 * TODO Handle capitalization generically
	 * 
	 * @param fieldName
	 * @param bindingName
	 * @return
	 */
	public String buildCodeEnumAsStringSetterBody(String fieldName, String bindingName) {
		return template.getCodeEnumAsStringSetterBody(StringUtils.capitalize(fieldName), bindingName);
	}
	
	public String buildReferenceSetterBody() {
		return template.getReferenceSetterBody(topLevelCoreAttribute);
	}
	
	public String buildReferenceGetterBody() {
		return template.getReferenceGetterBody(topLevelCoreAttribute, fullyQualifiedType);
	}
	
	public String buildMultivaluedGetterBody(String type) {
		return template.getMultivaluedBody(topLevelCoreAttribute, type);
	}
	
	public String buildMultivaluedPrimitiveGetterBody(String type) {
		return template.getMultivaluedPrimitiveBody(topLevelCoreAttribute, type);
	}
	
	public String buildExtensionListGetterBody(String fullyQualifiedType, String extensionUri) {
		return template.getExtensionListGetterBody(fullyQualifiedType, extensionUri);
	}
	
	public String buildExtensionGetterBody(String fullyQualifiedType, String extensionUri, String fieldName) {
		return template.getExtensionGetterBody(fullyQualifiedType, extensionUri, fieldName);
	}
	
	public String buildExtensionListSetterBody(String fullyQualifiedType, String extensionUri) {
		return template.getExtensionListSetterBody(fullyQualifiedType, extensionUri);
	}
	
	public String buildExtensionSetterBody(String extensionUri) {
		return template.getExtensionSetterBody(extensionUri);
	}
	
	public String buildExtendedTypeGetterBody(String returnType, String fieldUri) {
		return template.getExtendedTypeGetterBody(returnType, fieldUri);
	}
	
	public String buildExtendedTypeSetterBody(String fieldUri) {
		return template.getExtendedTypeSetterBody(fieldUri);
	}
	
	public String buildExtendedStructureListGetterBody(String type, String uri) {
		return template.getExtendedStructureListGetterBody(type, uri);
	}
	
	public String buildExtendedStructureListSetterBody() {
		return template.getExtendedStructureListSetterBody();
	}
}