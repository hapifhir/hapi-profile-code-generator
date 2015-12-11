package org.cdscollaborative.tools.fhir.codegenerator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.cdscollaborative.model.meta.Cardinality;
import org.cdscollaborative.model.meta.Method;
import org.cdscollaborative.tools.fhir.codegenerator.method.MethodHandlerResolver;
import org.cdscollaborative.tools.fhir.utils.FhirResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.model.api.ExtensionDt;
import ca.uhn.fhir.model.dstu2.composite.CodingDt;
import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt.Binding;
import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt.Type;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;

/**
 * TODO Document this class after refactoring
 * What does this class do? It is a bridge between ROASTER and FHIR Profiles.
 * It may make use of the model meta classes (lightweight ecore-like classes).
 * It knows about both FHIR and the meta classes.
 * Should it know about Roaster? Perhaps it is a bad idea - excessive coupling?
 * Ideally the workflow would be such:
 * 
 * InterfaceAdapterGenerator loads profile and processes individual elements by delegating
 * to method generator which translates FHIR to a logical model. InterfaceAdapterGenerator
 * then acts on logical model to generate code with Roaster.
 * 
 * InterfaceAdapterGenerator speaks plan, high-level profiles and meta
 * CodeGenerationUtils speak Roaster and meta
 * FhirMethodGenerator speaks element and meta
 * 
 * @author Claude Nanjo
 *
 */
@Deprecated
public class FhirMethodGenerator {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FhirMethodGenerator.class.getName());
	
	private Type type;
	private String typeString;
	private String fullyQualifiedType;
	private String javaEquivalentType;
	private String enumType;
	private String profileUrl;
	private String extensionDefinitionUri;
	
	//private Class<?> parentClass;
	private String parentClass;
	private String topLevelAttribute;
	private boolean hasPrimitiveEquivalent;
	private String correspondingExtensibleType;
	private boolean isEnum;
	private boolean isReferenceType;
	private String referenceType;
	private ElementDefinitionDt element;
	private StructureDefinition profile;
	private Cardinality cardinality;
	private FhirResourceManager coreResourceLoader;
	private boolean isMultivaluedType;
	private List<String> imports;
	private CodeTemplateUtils template;

	private FhirMethodGenerator(StructureDefinition profile, ElementDefinitionDt element, String parentClass, Type type, FhirResourceManager loader, String extensionDefUri) {
		this.element = element;
		this.profile = profile;
		this.type = type;
		this.coreResourceLoader = loader;
		this.imports = new ArrayList<String>();
		this.template = new CodeTemplateUtils();
		this.parentClass = parentClass;
		this.extensionDefinitionUri = extensionDefUri;
	}
	
	private void initialize() {
		if(element == null) {throw new RuntimeException("Profile element is null");}
		populateCardinality();
		handleType();
		if(parentClass == null) {
			assignParentClass();
		}
		parseTopLevelAttribute();//TODO: If necessary parse the structure
		template.initialize();
	}
	
	
	public String getParentClass() {
		return parentClass;
	}

	public void setParentClass(String parentClass) {
		this.parentClass = parentClass;
	}
	
	protected void assignParentClass() {
		this.parentClass = coreResourceLoader.addResourceToIndex(profile).getCanonicalName();
	}
	
	public boolean hasPrimitiveEquivalent() {
		return hasPrimitiveEquivalent;
	}

	public void setHasPrimitiveEquivalent(boolean hasPrimitiveEquivalent) {
		this.hasPrimitiveEquivalent = hasPrimitiveEquivalent;
	}

	public String getCorrespondingExtensibleType() {
		return correspondingExtensibleType;
	}

	public void setCorrespondingExtensibleType(String correspondingExtensibleType) {
		this.correspondingExtensibleType = correspondingExtensibleType;
	}
	
	public String getTopLevelAttribute() {
		return topLevelAttribute;
	}

	public void setOriginalAttribute(String topLevelAttribute) {
		this.topLevelAttribute = topLevelAttribute;
	}

	public boolean isHasPrimitiveEquivalent() {
		return hasPrimitiveEquivalent;
	}
	
	public boolean isEnum() {
		return isEnum;
	}

	public void setEnum(boolean isEnum) {
		this.isEnum = isEnum;
	}

	public String getEnumType() {
		return enumType;
	}

	public void setEnumType(String enumType) {
		this.enumType = enumType;
	}
	
	public boolean isReferenceType() {
		return isReferenceType;
	}

	public void setReferenceType(boolean isReferenceType) {
		this.isReferenceType = isReferenceType;
	}

	public String getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}
	
	public Cardinality getCardinality() {
		return cardinality;
	}

	public void setCardinality(Cardinality cardinality) {
		this.cardinality = cardinality;
	}
	
	public boolean isMultipleCardinality() {
		return (cardinality == Cardinality.OPTIONAL_MULTIPLE || cardinality == Cardinality.REQUIRED_MULTIPLE);
	}
	
	private void handleType() {
		if(type == null) {
			typeString = element.getPath();
			String[] components = typeString.split("\\.");
			if(components.length == 2) {
				typeString = components[0] + "." + StringUtils.capitalize(components[1]);
			} else if(components.length == 3) {//TODO Fix to arbirary depth
				typeString = components[0] + "." + StringUtils.capitalize(components[1]) + StringUtils.capitalize(components[2]);
			}
			
		} else {
			typeString = type.getCode();
			profileUrl = type.getProfileFirstRep().getValueAsString();
		}
		
		if(typeString == null) {
			LOGGER.error("The type for this element is null " + element.getPath());
			throw new RuntimeException("The type for this element is null " + element.getPath());
		}
		
		fullyQualifiedType = coreResourceLoader.getFullyQualifiedJavaType(typeString);
		javaEquivalentType = coreResourceLoader.getPrimitiveEquivalent(fullyQualifiedType);
		
		if(fullyQualifiedType.equalsIgnoreCase(ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt.class.getName()) && element.getBinding() != null && !isMultipleCardinality()) {
			Binding binding = element.getBinding();
			String bindingName = binding.getName();
			String enumClassName1 = "ca.uhn.fhir.model.dstu2.valueset." + bindingName + "Enum";
			String enumClassName2 = "ca.uhn.fhir.model.dstu2.valueset." + bindingName + "CodesEnum";
			if(classExists(enumClassName1)) {
				enumType = enumClassName1;
			} else if(classExists(enumClassName2)) {
				enumType = enumClassName2;
			}
			if(enumType != null) {
				fullyQualifiedType = "ca.uhn.fhir.model.dstu2.composite.BoundCodeableConceptDt<" + enumType  + ">";
				imports.add(enumType);
				isEnum = true;
			} else {
				
			}
		}
		
		if(fullyQualifiedType.equalsIgnoreCase(ca.uhn.fhir.model.primitive.CodeDt.class.getName()) && element.getBinding() != null) {
			Binding binding = element.getBinding();
			String bindingName = binding.getName();
			fullyQualifiedType = "ca.uhn.fhir.model.primitive.BoundCodeDt<ca.uhn.fhir.model.dstu2.valueset." + bindingName + "Enum>";
			enumType = "ca.uhn.fhir.model.dstu2.valueset." + bindingName + "Enum";
			imports.add(enumType);
			isEnum = true;
		}
	}
	
	public void populateCardinality() {
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
	
	public String getFirstTypeCode() {
		return element.getType().get(0).getCode();
	}
	
	public String getFullyQualifiedClassForType(String type) {
		return coreResourceLoader.getFullyQualifiedJavaType(getFirstTypeCode());
	}
	
	public String getFullyQualifiedClassForFirstTypeCode() {
		return getFullyQualifiedClassForType(getFirstTypeCode());
	}
	
	public boolean isMultivaluedType() {
		return isMultivaluedType;
	}
	
	/**
	 * A top level attribute is a direct child of the parent resource (e.g., Condition.id).
	 * Lower level attributes are more deeply nested (e.g., Condition.location.code).
	 * Method returns the name of the top level attribute or null if the attribute is a structure
	 * containing more deeply nested attributes. 
	 * 
	 * @param underlyingFhirCoreResourceName
	 * @param attributePath
	 * @return
	 */
	public void parseTopLevelAttribute() {
		String resourceName = getParentClass().substring(getParentClass().lastIndexOf('.') + 1);//getParentClass().getSimpleName();
		String attributePath = element.getPath();
		String suffix = getSuffix(resourceName, attributePath);
		if(suffix != null && suffix.indexOf('.') >= 0) {
			suffix = null;
		}
		if(suffix != null && suffix.indexOf('.') < 0) {
			if (suffix.contains("[x]")) {
				suffix = suffix.replace("[x]","");
				isMultivaluedType = true;
			}
		}
		topLevelAttribute = suffix;
		
	}
	
	public boolean isTopLevelAttribute() {
		return topLevelAttribute != null;
	}
	
	public boolean isNotTopLevelAttribute() {
		return topLevelAttribute == null;
	}
	
	/**
	 * Method returns the suffix of a path starting from the resource of interest.
	 * For instance, for the path 'Condition.id' and the prefix 'Condition', this method returns the string
	 * 'id' as the suffix. It is the path relative to the containing resource.
	 * 
	 * @param prefix - The prefix, generally the name of the resource
	 * @param attributePath - The full attribute path as defined in the profile
	 * @return
	 */
	public String getSuffix(String prefix, String attributePath) {
		String suffix = null;
		if((attributePath.indexOf('.')>= 0) && attributePath.contains(prefix)) {
			suffix = attributePath.substring(prefix.length() + 1);
		}
		return suffix;
	}
		
	public Method constructGetMethod() {
		return constructGetMethodFromField(topLevelAttribute, getFullyQualifiedClassForFirstTypeCode());
	}
	
	public Method constructGetMethodFromField(String fieldName, String type) {
		return constructGetMethod(buildGetterName(fieldName), type);
	}
	
	public Method constructGetMethod(String methodName, String type) {
		Method method = new Method();
		method.setName(methodName);
		if(isMultipleCardinality()) {
			imports.add(type);//TODO Handle bug in Roaster
			method.setReturnType("java.util.List<" + type + ">");
		} else {
			method.setReturnType(type);
		}
		return method;
	}
	
	public Method constructAddMethod(String fieldName, String type) {
		Method method = new Method();
		method.setName(buildAddMethodName(fieldName));
		method.addParameter(type);
		return method;
	}
	
	public Method constructSetMethod(String parameterType) {
		return constructSetMethod(topLevelAttribute, parameterType);
	}
	
	public Method constructSetMethod(String fieldName, String parameterType) {
		List<String> parameterTypes = new ArrayList<String>();
		parameterTypes.add(parameterType);
		return constructSetMethod(buildSetterName(fieldName), parameterTypes);
	}
	
	public Method constructSetMethod(String methodName, List<String> parameterTypes) {
		Method method = new Method();
		method.setName(methodName);
		for(String paramType : parameterTypes) {
			if(isMultipleCardinality()) {
				method.addParameter("List<" + paramType+ ">");
			} else {
				method.addParameter(paramType);
			}
		}
		return method;
	}
	
	public static Method constructSerializeExtensionType() {
		CodeTemplateUtils template = new CodeTemplateUtils();//TODO Fix this by refactoring this class' responsibility
		template.initialize();
		Method method = new Method();
		method.setName("serializeExtensionType");
		method.setBody(template.getSerializeExtensionType());
		method.setReturnType(ExtensionDt.class.getCanonicalName());
		method.addImport("java.lang.reflect.Field");
		method.addImport("org.hl7.fhir.instance.model.api.IBaseDatatype");
		return method;
	}
	
	/**
	 * Some field types in HAPI can be ignored
	 * 
	 * @param suffix
	 * @return
	 */
	public boolean ignoreField() {
		boolean ignore = false;
		if(isNotTopLevelAttribute()) {
			return ignore;
		} else if(topLevelAttribute.equalsIgnoreCase("ModifierExtension")) {
			ignore = true;
		} else if(topLevelAttribute.equalsIgnoreCase("ImplicitRules")) { //TODO Not sure what this is. Investigate.
			ignore = true;
		}
		return ignore;
	}
	
	public boolean processField() {
		return !ignoreField() && isAttribute() && isTopLevelAttribute();
	}
	
	public boolean isAttribute() {
		return element.getPath().indexOf('.') >= 0;
	}
	
	/**
	 * Builds getter accessor name using the JavaBean convention
	 * 
	 * @param accessor
	 * @return
	 */
	public String buildGetterName(String accessor) {
		return "get" + StringUtils.capitalize(accessor);
	}
	
	/**
	 * Builds setter accessor name using the JavaBean convention
	 * 
	 * @param accessor
	 * @return
	 */
	public String buildSetterName(String fieldName) {
		return "set" + StringUtils.capitalize(fieldName);
	}
	
	public String buildAddMethodName(String fieldName) {
		return "add" + StringUtils.capitalize(fieldName);
	}
	
	public String getProfileSuffix() {
		if(profileUrl != null && profileUrl.lastIndexOf('/') > 0) {
			return profileUrl.substring(profileUrl.lastIndexOf('/') + 1);
		} else {
			return "";
		}
	}
	
	/*************************************************************************************************
	 * Code generation methods
	 * ***********************************************************************************************/
	
	/**
	 * Method parses the FHIR element structure and populates the necessary metadata to support
	 * subsequent method generation.
	 * 
	 * @param element
	 * @return
	 */
	public static List<Method> generateAccessorMethods(StructureDefinition profile, ElementDefinitionDt element, FhirResourceManager loader, String extensionDefUri) {
		return FhirMethodGenerator.generateAccessorMethods(profile, element, null, loader, extensionDefUri);
	}
	
	/**
	 * Method parses the FHIR element structure and populates the necessary metadata to support
	 * subsequent method generation.
	 * 
	 * @param element
	 * @return
	 */
	public static List<Method> generateAccessorMethods(StructureDefinition profile, ElementDefinitionDt element, String parentClass, FhirResourceManager loader, String extensionDefUri) {
		List<Method> methods = new ArrayList<Method>();
		for(Type type : element.getType()) {
			FhirMethodGenerator generator = new FhirMethodGenerator(profile, element, parentClass, type, loader, extensionDefUri);
			generator.initialize();
			if(generator.processField()) {
				generator.handleTopLevelAttribute(methods);
			}
			generator.addImports(methods);
		}
		if(element.getType() == null || element.getType().size() == 0) {
			FhirMethodGenerator generator = new FhirMethodGenerator(profile, element, parentClass, null, loader, extensionDefUri);
			generator.initialize();
			if(generator.processField()) {
				generator.handleTopLevelAttribute(methods);
			}
			generator.addImports(methods);
		}
		return methods;
	}
	
	/**
	 * Method parses the FHIR element structure and populates the necessary metadata to support
	 * subsequent method generation.
	 * 
	 * @param element
	 * @return
	 */
	public static List<Method> generateAccessorMethodsForExtendedTypes(StructureDefinition profile, ElementDefinitionDt element, String parentClass, FhirResourceManager loader, String extensionDefUri) {
		List<Method> methods = new ArrayList<Method>();
		for(Type type : element.getType()) {
			FhirMethodGenerator generator = new FhirMethodGenerator(profile, element, parentClass, type, loader, extensionDefUri);
			generator.initialize();
			if(generator.processField()) {
				generator.handleExtendedTypes(methods);
			}
			generator.addImports(methods);
		}
		return methods;
	}
	
	public void addImports(List<Method> methods) {
		if(imports.size() > 0) {
			for(Method method : methods) {
				method.addImports(imports);
			}
		}
	}
	
	/**
	 * Method processes a top-level attribute (not a FHIR structure type)
	 * 
	 * @param name - The name of the attribute
	 * @param accessors
	 * @param element - The profile element definition for this attribute
	 * @param topLevelAttribute
	 */
	public void handleExtendedTypes(List<Method> accessors) {
		if(isMultivaluedType()) {
			handleMultiTypeAttributes(accessors);
		} else if(fullyQualifiedType.equalsIgnoreCase("ca.uhn.fhir.model.dstu2.composite.ResourceReferenceDt") && profileUrl != null) {
			handleReferenceTypes(accessors);
		} else {
			handleSimpleAttributeForExtendedType(accessors);
		}
	}
	
	/**
	 * Method processes a top-level attribute (not a FHIR structure type)
	 * 
	 * @param name - The name of the attribute
	 * @param accessors
	 * @param element - The profile element definition for this attribute
	 * @param topLevelAttribute
	 */
	public void handleTopLevelAttribute(List<Method> accessors) {
//		if(topLevelAttribute.equalsIgnoreCase("value")) {
//			System.out.println("HERE");
//		}
		if(isBaseResourceAttribute()) {
			handleBaseResourceAccessors(accessors);
		} else if(isMultivaluedType()) {
			handleMultiTypeAttributes(accessors);
		} else if(topLevelAttribute.equals("meta")) {
			//handleMeta(accessors); //TODO Pending fix from ROASTER guys.
		} else if(fullyQualifiedType.equalsIgnoreCase("ca.uhn.fhir.model.dstu2.composite.ResourceReferenceDt") && profileUrl != null) {
			handleReferenceTypes(accessors);
		} else {
			handleSimpleAttribute(accessors);
		}
	}
	
	public boolean isFhirExtension() {
		return extensionDefinitionUri != null;
	}
	
	public boolean isBaseResourceAttribute() {
		boolean isBaseResourceAttribute = false;
		if(topLevelAttribute.equalsIgnoreCase("language")) {
			isBaseResourceAttribute = true;
			imports.clear();//TODO Maybe a better to handle this exception case
			isEnum = false;
			javaEquivalentType = null;
			fullyQualifiedType = ca.uhn.fhir.model.primitive.CodeDt.class.getName();
		} else if(topLevelAttribute.equalsIgnoreCase("contained")) {
			cardinality = Cardinality.OPTIONAL;//TODO Maybe a better to handle this exception case
			isBaseResourceAttribute = true;
		}
		return isBaseResourceAttribute;
	}
	
	public void handleBaseResourceAccessors(List<Method> accessors) {
		if(topLevelAttribute.equalsIgnoreCase("contained")) {
			accessors.add(constructGetMethod().setBody(getDefaultGetterBody()));
			accessors.add(constructSetMethod(coreResourceLoader.getFullyQualifiedJavaType(getFirstTypeCode())).setBody(getDefaultSetterBody()));
		} else if(topLevelAttribute.equalsIgnoreCase("language")) {
			accessors.add(constructGetMethod().setBody(getDefaultGetterBody()));
			accessors.add(constructSetMethod(fullyQualifiedType).setBody(getDefaultSetterBody()));
		}
	}
	
	public void handleMultiTypeAttributes(List<Method> accessors) {
		String qualifiedAttribute = topLevelAttribute + StringUtils.capitalize(typeString);
		if(javaEquivalentType == null) {
			accessors.add(constructGetMethodFromField(qualifiedAttribute, fullyQualifiedType).setBody(getMultivaluedGetterBody()));
			accessors.add(constructSetMethod(qualifiedAttribute, fullyQualifiedType).setBody(getDefaultSetterBody()));
		} else {
			accessors.add(constructGetMethodFromField(qualifiedAttribute + "Element", fullyQualifiedType).setBody(getMultivaluedGetterBody()));
			accessors.add(constructGetMethodFromField(qualifiedAttribute, javaEquivalentType).setBody(getMultivaluedPrimitiveGetterBody()));
			accessors.add(constructSetMethod(qualifiedAttribute, fullyQualifiedType).setBody(getDefaultSetterBody()));
			accessors.add(constructSetMethod(qualifiedAttribute, javaEquivalentType).setBody(getPrimitiveJavaSetterBody()));
		}
	}
	
	/**
	 * Handles the core Resource Metadata
	 * 
	 * @param accessors
	 */
	public void handleMeta(List<Method> accessors) {
		String type = "java.util.Map<ca.uhn.fhir.model.api.ResourceMetadataKeyEnum<T>,java.lang.Object>";
		accessors.add(constructGetMethodFromField("resourceMetadata", fullyQualifiedType));
		accessors.add(constructSetMethod("resourceMetadata", fullyQualifiedType));
	}

	private void handleSimpleAttribute(List<Method> accessors) {
		if(javaEquivalentType != null) {
			if(isMultipleCardinality()) {//Note, primitive type support primitive accessors only in the singular case
				accessors.add(constructGetMethodFromField(topLevelAttribute,fullyQualifiedType).setBody(getDefaultGetterBody()));
				accessors.add(constructSetMethod(fullyQualifiedType).setBody(getDefaultSetterBody()));
			} else {
				accessors.add(constructGetMethodFromField(topLevelAttribute + "Element",fullyQualifiedType).setBody(getDefaultGetterBody()));
				accessors.add(constructGetMethodFromField(topLevelAttribute, javaEquivalentType).setBody(getPrimitiveGetterBody()));
				accessors.add(constructSetMethod(javaEquivalentType).setBody(getPrimitiveJavaSetterBody()));
				accessors.add(constructSetMethod(fullyQualifiedType).setBody(getDefaultSetterBody()));
			}
		} else {
			accessors.add(constructGetMethodFromField(topLevelAttribute,fullyQualifiedType).setBody(getDefaultGetterBody()));
			accessors.add(constructSetMethod(fullyQualifiedType).setBody(getDefaultSetterBody()));
		}
		
		if(isMultipleCardinality() && !isFhirExtension()) {
			accessors.add(constructAddMethod(topLevelAttribute, fullyQualifiedType).setBody(getDefaultAddBody()));
		}
		
		if(isCodeableConcept() && !isMultipleCardinality() && !isFhirExtension()) {
			imports.add(CodingDt.class.getCanonicalName());
			imports.add("java.util.ArrayList");
			imports.add("java.util.Iterator");
			accessors.add(constructGetMethod("get" + StringUtils.capitalize(topLevelAttribute) + "AsStringList", "java.util.List<String>").setBody(template.getCodesAsStringListBody("adaptedClass",StringUtils.capitalize(topLevelAttribute))));
		}
	}
	
	private void handleSimpleAttributeForExtendedType(List<Method> accessors) {
		accessors.add(constructGetMethodFromField(topLevelAttribute,fullyQualifiedType).setBody(template.getSimpleGetter(StringUtils.uncapitalize(topLevelAttribute))));
		accessors.add(constructSetMethod(fullyQualifiedType).setBody(template.getSimpleSetter(StringUtils.uncapitalize(topLevelAttribute), "param")));
	}

	public void handleReferenceTypes(List<Method> accessors) {
		if(!isMultipleCardinality()) {
			String descriminator = "";
			String resource = getProfileSuffix();
			if(element.getType().size() > 1) {
				descriminator = resource;
			}
			fullyQualifiedType = coreResourceLoader.addResourceToIndex(resource).getCanonicalName();
			accessors.add(constructGetMethodFromField(topLevelAttribute + descriminator + "Resource", fullyQualifiedType).setBody(getReferenceGetterBody()));
			accessors.add(constructSetMethod(topLevelAttribute + "Resource", fullyQualifiedType).setBody(getReferenceSetterBody()));
		}
	}
	
	
	public String getDefaultGetterBody() {
		return getDefaultGetterBody(StringUtils.capitalize(topLevelAttribute));
	}
	
	public String getDefaultGetterBody(String propertyName) {
		if(isFhirExtension()) {
			if(isMultipleCardinality()) {
				return template.getExtensionListGetterBody(fullyQualifiedType, extensionDefinitionUri);
			} else {
				return template.getExtensionGetterBody(fullyQualifiedType, extensionDefinitionUri, topLevelAttribute);
			}
		} else if(javaEquivalentType != null) {
			return template.getAdapterGetMethodDelegationBody(propertyName + "Element");
		} else {
			return template.getAdapterGetMethodDelegationBody(propertyName);
		}
	}
	
//	public String getSimpleGetterBody() {
//		return template.getAdapterGetMethodDelegationBody(topLevelAttribute);
//	}
	
	public String getPrimitiveGetterBody() {
		if(isFhirExtension()) {
			return template.getExtensionGetterBodyPrimitive(fullyQualifiedType, extensionDefinitionUri, topLevelAttribute);
		} else {
			return template.getAdapterGetMethodDelegationBody(topLevelAttribute);
		}
	}
	
	public String getDefaultAddBody() {
		return template.getAddToListMethodBody("adaptedClass", topLevelAttribute);
	}
	
	public String getReferenceGetterBody() {
		return template.getReferenceGetterBody(topLevelAttribute, fullyQualifiedType);
	}
	
	public String getMultivaluedGetterBody() {
		return template.getMultivaluedBody(topLevelAttribute, fullyQualifiedType);
	}
	
	public String getMultivaluedPrimitiveGetterBody() {
		return template.getMultivaluedPrimitiveBody(topLevelAttribute, fullyQualifiedType);
	}
	
	public String getDefaultSetterBody(String propertyName) {
		if(isFhirExtension()) {
			if(isMultipleCardinality()) {
				return template.getExtensionListSetterBody(fullyQualifiedType, extensionDefinitionUri);
			} else {
				return template.getExtensionSetterBody(extensionDefinitionUri);
			}
		} else {
			return template.getAdapterSetMethodDelegationBody(propertyName);
		}
	}
	
	public String getDefaultSetterBody() {
		return getDefaultSetterBody(topLevelAttribute);
	}
	
	public String getPrimitiveJavaSetterBody() {
		if(isFhirExtension()) {
			return template.getExtensionSetterBodyPrimitive(fullyQualifiedType, extensionDefinitionUri);
		} if(isEnum) {
			return template.getSetMethodDelegationBody(topLevelAttribute, enumType + ".valueOf(param)");
		} else {
			return template.getSetMethodDelegationBody(topLevelAttribute, "new " + fullyQualifiedType + "(param)");
		}
	}
	
	public String getReferenceSetterBody() {
		return template.getReferenceSetterBody(topLevelAttribute);
	}
	
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
	
	public boolean isCodeableConcept() {
		return fullyQualifiedType.equalsIgnoreCase(ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt.class.getName());
	}
	
}
