package ca.uhn.fhir.utils.codegen.hapi;

import ca.uhn.fhir.utils.common.metamodel.Method;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseMethodHandler {
	
	public static final String ATTRIBUTE_NAME_ELEMENT_SUFFIX = "Element";
	
	private MethodBodyGenerator template;
	private String parentType;
	private boolean isExtensionStructure;
	private String extensionStructureAttributeName;
	private boolean addUserDefinedStructureToParent;
	private String userDefinedStructureExtensionURL;

	public BaseMethodHandler(MethodBodyGenerator template) {
		this.template = template;
	}
	
	public MethodBodyGenerator getTemplate() {
		return template;
	}

	public void setTemplate(MethodBodyGenerator template) {
		this.template = template;
	}

	public String getParentType() {
		return parentType;
	}

	public void setParentType(String parentType) {
		this.parentType = parentType;
	}

	public boolean isExtensionStructure() {
		return isExtensionStructure;
	}

	public void setExtensionStructure(boolean extensionStructure) {
		isExtensionStructure = extensionStructure;
	}

	public String getExtensionStructureAttributeName() {
		return extensionStructureAttributeName;
	}

	public void setExtensionStructureAttributeName(String extensionStructureAttributeName) {
		this.extensionStructureAttributeName = extensionStructureAttributeName;
	}

	public boolean addUserDefinedStructureToParent() {
		return addUserDefinedStructureToParent;
	}

	public void setAddUserDefinedStructureToParent(boolean addExtensionStructureToParent) {
		this.addUserDefinedStructureToParent = addExtensionStructureToParent;
	}

	public String getUserDefinedStructureExtensionURL() {
		return userDefinedStructureExtensionURL;
	}

	public void setUserDefinedStructureExtensionURL(String userDefinedStructureExtensionURL) {
		this.userDefinedStructureExtensionURL = userDefinedStructureExtensionURL;
	}

/****************************************************************
	 * HELPER METHODS
	 */

	/**
	 * Constructs a setter method for the given field name and parameters
	 * using the java bean convention.
	 *
	 * @param fieldName
	 * @param parameterType
	 * @return
	 */
	public Method constructSetMethodSignature(String fieldName, String parameterType, String returnType) {
		List<String> parameterTypes = new ArrayList<String>();
		parameterTypes.add(parameterType);
		return Method.constructMethod(buildSetterName(fieldName), parameterTypes, returnType);
	}

	public Method constructListSetterMethod(String attributeName, String listType, String listParameterType, String returnType) {
		Method method = constructSetMethodSignature(attributeName, listType, returnType);
		method.setBody(getTemplate().getAdapterSetMethodDelegationBody(attributeName));
		method.addImport(listParameterType);
		return method;
	}

	public Method constructSetterMethod(String attributeName, String argumentType, String returnType) {
		Method method = constructSetMethodSignature(attributeName, argumentType, returnType);
		method.setBody(getTemplate().getAdapterSetMethodDelegationBody(attributeName));
		method.addImport(argumentType);
		return method;
	}

	//Noman 1
	public Method constructSetterMethod(String attributeName, String argumentType, String returnType, boolean cardinalityChanged) {
		Method method = constructSetMethodSignature(attributeName, argumentType, returnType);
		method.setBody(getTemplate().getAdapterSetMethodDelegationBodyWithListToToSingleElement(argumentType, attributeName));
		method.addImport(argumentType);
		method.addImport("java.util.Arrays");
		return method;
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
	 * Constructs add method for a field of multiple cardinality.
	 * <pre>
	 * <code>
	 * public &lt;adapterType&gt; addFieldName(&lt;fieldType&gt; param)
	 * </code>
	 * </pre>
	 * @param fieldName
	 * @param parameterType
	 * @return
	 */
	public Method constructAddMethodSignature(String fieldName, String parameterType, String returnType) {
		Method method = new Method();
		method.setName(buildAddMethodName(fieldName));
		method.addParameter(parameterType);
		method.setReturnType(returnType);
		return method;
	}

	/**
	 *
	 * @param attributeName
	 * @param parameterType
	 * @param returnType
	 * @return
	 */
	public Method constructAddMethod(String attributeName, String parameterType, String returnType) {
		Method method = constructAddMethodSignature(attributeName, parameterType, returnType);
		method.setBody(getTemplate().getAddToListMethodBody("adaptedClass", attributeName)); //TODO adaptedClass should be part of template and not an argument.
		method.addImport(parameterType);
		return method;
	}

	public Method constructFluentAddMethod(String attributeName, String returnType) {
		Method method = buildAddMethodDelegated(attributeName, returnType);
		method.setBody(getTemplate().getAddToListMethodDelegatedBody_dstu3("adaptedClass", attributeName, returnType)); //TODO adaptedClass should be part of template and not an argument to method.
		method.addImport(returnType);
		return method;
	}

	public Method constructListGetterMethod(String attributeName, String listReturnType, String listParameterType, boolean isExtension, String extensionUri) {
		Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName), listReturnType);
		if (isExtension) {
			method.setBody(getTemplate().getExtensionListGetterBodyDstu3(listParameterType, extensionUri));
		} else {
			method.setBody(getTemplate().getAdapterGetMethodDelegationWithTryCatchBody(attributeName));
		}
		method.addImport(listParameterType);
		return method;
	}

	//Noman 1
	public Method constructGetterMethod(String attributeName, String returnType, String datatype, boolean isExtension, String extensionUri, boolean cardinilityChangted) {
		return constructGetterMethod(attributeName, "", "", returnType, datatype, isExtension, extensionUri, cardinilityChangted);
	}

	//Noman 0
	public Method constructGetterMethod(String attributeName, String methodSignatureSuffix, String delegatedCallSuffix, String returnType, String datatype, boolean isExtension, String extensionUri, boolean cardinilityChanged) {
		Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName) + methodSignatureSuffix, returnType);
		if (cardinilityChanged) {
			method.setBody(getTemplate().getExtensionGetterBodyDstu3("adaptedClass", datatype, extensionUri, attributeName));//TODO Test this out for primitive types that are extended. THIS IS UNTESTED CODE.
		} else {
			method.setBody(getTemplate().getAdapterGetMethodDelegationWithTryCatchBody(attributeName + delegatedCallSuffix));
		}
		if (datatype != null) {
			method.addImport(datatype);
		}
		return method;
	}
	public Method constructGetterMethod(String attributeName, String returnType, String datatype, boolean isExtension, String extensionUri) {
		return constructGetterMethod(attributeName, "", "", returnType, datatype, isExtension, extensionUri);
	}

	public Method constructGetterMethod(String attributeName, String methodSignatureSuffix, String delegatedCallSuffix, String returnType, String datatype, boolean isExtension, String extensionUri) {
		Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName) + methodSignatureSuffix, returnType);
		if (isExtension) {
			method.setBody(getTemplate().getExtensionGetterBodyDstu3("adaptedClass", datatype, extensionUri, attributeName));//TODO Test this out for primitive types that are extended. THIS IS UNTESTED CODE.
		} else {
			method.setBody(getTemplate().getAdapterGetMethodDelegationWithTryCatchBody(attributeName + delegatedCallSuffix));
		}
		if(datatype != null) {
			method.addImport(datatype);
		}
		return method;
	}

	public Method constructGetterMethodWithCast(String attributeName, String suffix, String returnType, String castType) {
		Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName + suffix) + "Target", returnType);
		method.setBody(getTemplate().getAdapterGetWithCastMethodDelegationBody(attributeName + "Target", castType));
		method.addImport(castType);
		return method;
	}

	public Method constructProfiledReferenceGetter(String attributeName, String methodSignatureSuffix, String delegatedMethodSuffix, String returnType, String wrappedType) {
		Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName) + methodSignatureSuffix, returnType);
		method.setBody(getTemplate().getProfiledReferenceGetterBody(attributeName + delegatedMethodSuffix, returnType, wrappedType));
		method.addImport(wrappedType);
		return method;
	}

	public Method constructUserDefinedExtensionTypeGetter(String attributeName, String returnType, String generatedType, String datatype, String userDefinedStructureExtensionUrl) {
		Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName), "java.util.List<" + returnType + ">");
		method.setBody(getTemplate().getUserDefinedExtensionTypeGetterBody_dstu3(generatedType, userDefinedStructureExtensionUrl));
		method.addImport(datatype);
		method.addImport("java.util.List");
		return method;
	}

	public Method constructReferenceListAsTypedListGetter(String attributeName, String methodSignatureSuffix, String delegatedMethodSuffix, String returnType, String generatedType, String datatype) {
		Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName + methodSignatureSuffix), returnType);//TODO Fix deprecated call
		method.setBody(getTemplate().getReferenceListAsTypedList("adaptedClass", attributeName + delegatedMethodSuffix, datatype));
		return method;
	}

	public Method constructCastTypeToReferenceAndReturnTargetGetter(String attributeName, String methodSignatureSuffix, String delegatedMethodSuffix, String returnType) {
		Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName + methodSignatureSuffix), returnType);
		method.setBody(getTemplate().castTypeToReferenceAndReturnTarget(attributeName + delegatedMethodSuffix, returnType));
		method.addImport(returnType);
		return method;
	}

	/**
	 * Constructs getter for an extended FHIR datatype. Method returns the wrapped datatype.
	 *
	 * @return
	 */
	public Method constructExtendedDatatypeGetter(String attributeName, String returnType, String generatedType, String datatype) {
		Method method = Method.constructNoArgMethod(Method.buildGetterName("Wrapped" + StringUtils.capitalize(attributeName)), returnType);
		method.setBody(getTemplate().getGeneratedClassMultiCardinalityGetter(generatedType, datatype, StringUtils.capitalize(attributeName)));
		method.addImport(generatedType);
		return method;
	}

	public Method constructListOfReferenceValuedExtensionGetter(String attributeName, String methodSignatureSuffix, String delegatedMethodSuffix, String returnType, String datatype, boolean isResource, String extensionUri) {
		Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName + methodSignatureSuffix), returnType);
		if (isResource) {
			method.setBody(getTemplate().getExtensionListGetterBodyResourceDstu3(datatype, extensionUri));
		} else {
			method.setBody(getTemplate().getExtensionListGetterBodyDstu3(datatype, extensionUri));
		}
		method.addImport(datatype);
		method.addImport("java.util.List");
		return method;
	}

	public Method constructExtensionGetterBody(String attributeName, String methodSignatureSuffix, String adaptedClassName, String returnType, String datatype, boolean isResource, String extensionUri) {
		Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName + methodSignatureSuffix), returnType);
		if (isResource) {
			method.setBody(getTemplate().getExtensionGetterBodyResourceDstu3(adaptedClassName, datatype, extensionUri));
		} else {
			method.setBody(getTemplate().getExtensionGetterBodyDstu3(adaptedClassName, datatype, extensionUri, attributeName));
		}
		method.addImport(datatype);
		return method;
	}

	public Method constructListOfReferenceValuedExtensionSetter(String attributeName, String argumentType, String returnType, String datatype, boolean isResource, String extensionUri) {
		Method method = constructSetMethodSignature(attributeName + "0000", argumentType, returnType);
		if (isResource) {
			method.setBody(getTemplate().getExtensionListSetterBodyResourceDstu3(extensionUri));
		} else {
			method.setBody(getTemplate().getExtensionListSetterBodyDstu3(datatype, extensionUri));
		}
		method.addImport(datatype);
		return method;
	}

	public Method constructExtensionSetter(String attributeName, String adaptedClassName, String argumentType, String returnType, String datatype, boolean isResource, String extensionUri) {
		Method method = constructSetMethodSignature(attributeName, argumentType, returnType);
		if (isResource) {
			method.setBody(getTemplate().getExtensionSetterBodyResourceDstu3(adaptedClassName, extensionUri));
		} else {
			method.setBody(getTemplate().getExtensionSetterBodyDstu3(adaptedClassName, extensionUri));
		}
		method.addImport(datatype);

		return method;
	}

	public Method constructExtendedDatatypeSetter(String attributeName, String argumentType, String generatedType, String datatype, String returnType) {
		Method method = constructSetMethodSignature("Wrapped" + StringUtils.capitalize(attributeName), argumentType, returnType);
		method.setBody(getTemplate().getGeneratedClassMultiCardinalitySetter(generatedType, datatype, StringUtils.capitalize(attributeName)));
		method.addImport(generatedType);
		return method;
	}

	public Method constructProfiledReferenceSetter(String attributeName, String argumentType, String wrappedType, String returnType) {
		Method method = constructSetMethodSignature(attributeName + "AdapterTarget", argumentType, returnType);
		method.setBody(getTemplate().getProfiledReferenceSetterBody_dstu3(attributeName + "Target"));
		method.addImport(wrappedType);
		return method;
	}

	public Method constructUserDefinedExtensionTypeSetter(String attributeName, String argumentType, String returnType, String generatedType, String datatype) {
		Method method = constructSetMethodSignature(attributeName, argumentType, returnType);
		method.setBody(getTemplate().getUserDefinedExtensionTypeSetterBody_dstu3(generatedType));
		method.addImport(datatype);
		method.addImport("java.util.List");
		return method;
	}

	public Method constructWrapResourceInReferenceSetter(String attributeName, String methodSignatureSuffix, String delegatedMethodSuffix, String argumentType, String returnType) {
		Method method = constructSetMethodSignature(attributeName + methodSignatureSuffix, argumentType, returnType);
		method.setBody(getTemplate().wrapResourceInReferenceAndSet(attributeName + delegatedMethodSuffix));
		method.addImport(argumentType);
		return method;
	}

	public void buildAddMethod(List<Method> methods, String attributeName, String parameterType, String returnType) {
		Method method = constructAddMethod(attributeName, parameterType, returnType);
		addMethod(methods, method);
	}

	public void buildAddElementMethod(List<Method> methods, String attributeName, String returnType) {
		Method method = constructFluentAddMethod(attributeName, returnType);
		addMethod(methods, method);
	}

	public void buildFluentAddMethod(List<Method> methods, String attributeName, String returnType) {
		Method method = constructFluentAddMethod(attributeName, returnType);
		addMethod(methods, method);
	}

	public void buildListGetterMethod(List<Method> methods, String attributeName, String listReturnType, String listParameterType, boolean isExtension, String extensionUri) {
		Method method = constructListGetterMethod(attributeName, listReturnType, listParameterType, isExtension, extensionUri);
		addMethod(methods, method);
	}

	//Noman 2
	public void buildGetterMethod(List<Method> methods, String attributeName, String returnType, String fhirType, boolean isExtension, String extensionUri, boolean cardinilityChanged) {
		Method method = constructGetterMethod(attributeName, returnType, fhirType, isExtension, extensionUri, cardinilityChanged);
		addMethod(methods, method);
	}

	public void buildGetterMethod(List<Method> methods, String attributeName, String returnType, String fhirType, boolean isExtension, String extensionUri) {
		Method method = constructGetterMethod(attributeName, returnType, fhirType, isExtension, extensionUri);
		addMethod(methods, method);
	}

	public void buildGetterMethod(List<Method> methods, String attributeName, String methodSignatureSuffix, String delegatedMethodSuffix, String returnType, String datatype, boolean isExtension, String extensionUri) {
		Method method = constructGetterMethod(attributeName, methodSignatureSuffix, delegatedMethodSuffix, returnType, datatype, isExtension, extensionUri);
		addMethod(methods, method);
	}

	public void buildGetterMethodWithCast(List<Method> methods, String attributeName, String suffix, String returnType, String castType) {
		Method method = constructGetterMethodWithCast(attributeName, suffix, returnType, castType);
		addMethod(methods, method);
	}

	public void buildUserDefinedExtensionTypeGetter(List<Method> methods, String attributeName, String returnType, String generatedType, String datatype, String userDefinedStructureExtensionUrl) {
		Method method = constructUserDefinedExtensionTypeGetter(attributeName, returnType, generatedType, datatype, userDefinedStructureExtensionUrl);
		addMethod(methods, method);
	}

	public void buildProfiledReferenceGetter(List<Method> methods, String attributeName, String methodSignatureSuffix, String delegatedMethodSuffix, String returnType, String wrappedType) {
		Method method = constructProfiledReferenceGetter(attributeName, methodSignatureSuffix, delegatedMethodSuffix, returnType, wrappedType);
		addMethod(methods, method);
	}

	public void buildListSetterMethod(List<Method> methods, String attributeName, String listType, String listParameterType, String returnType) {
		Method method = constructListSetterMethod(attributeName, listType, listParameterType, returnType);
		addMethod(methods, method);
	}

	///Noman s2
	public void buildSetterMethod(List<Method> methods, String attributeName, String argumentType, String returnType) {
		Method method = constructSetterMethod(attributeName, argumentType, returnType);
		addMethod(methods, method);
	}

	public void buildSetterMethod(List<Method> methods, String attributeName, String argumentType, String returnType, boolean cardinilityChanged) {
		Method method = constructSetterMethod(attributeName, argumentType, returnType, cardinilityChanged);
		addMethod(methods, method);
	}

	public void buildExtendedDatatypeGetter(List<Method> methods, String attributeName, String returnType, String generatedType, String datatype) {
		Method method = constructExtendedDatatypeGetter(attributeName, returnType, generatedType, datatype);
		addMethod(methods, method);
	}

	public void buildReferenceListAsTypedListGetter(List<Method> methods, String attributeName, String methodSignatureSuffix, String delegatedMethodSuffix, String returnType, String generatedType, String datatype) {
		Method method = constructReferenceListAsTypedListGetter(attributeName, methodSignatureSuffix, delegatedMethodSuffix, returnType, generatedType, datatype);
		addMethod(methods, method);
	}

	public Method buildListOfReferenceValuedExtensionGetter(List<Method> methods, String attributeName, String methodSignatureSuffix, String delegatedMethodSuffix, String returnType, String datatype, boolean isResource, String extensionUri) {
		Method method = constructListOfReferenceValuedExtensionGetter(attributeName, methodSignatureSuffix, delegatedMethodSuffix, returnType, datatype, isResource, extensionUri);
		return method;
	}

	public void buildExtendedDatatypeSetter(List<Method> methods, String attributeName, String argumentType, String generatedType, String datatype, String returnType) {
		Method method = constructExtendedDatatypeSetter(attributeName, argumentType, generatedType, datatype, returnType);
		addMethod(methods, method);
	}

	public void buildProfiledReferenceSetter(List<Method> methods, String attributeName, String argumentType, String wrappedType, String returnType) {
		Method method = constructProfiledReferenceSetter(attributeName, argumentType, wrappedType, returnType);
		addMethod(methods, method);
	}

	public void buildUserDefinedExtensionTypeSetter(List<Method> methods, String attributeName, String argumentType, String returnType, String generatedType, String datatype) {
		Method method = constructUserDefinedExtensionTypeSetter(attributeName, argumentType, returnType, generatedType, datatype);
		addMethod(methods, method);
	}

	public void buildWrapResourceInReferenceSetter(List<Method> methods, String attributeName, String methodSignatureSuffix, String delegatedMethodSuffix, String argumentType, String returnType) {
		Method method = constructWrapResourceInReferenceSetter(attributeName, methodSignatureSuffix, delegatedMethodSuffix, argumentType, returnType);
		addMethod(methods, method);
	}

	public void buildCastTypeToReferenceAndReturnTargetGetter(List<Method> methods, String attributeName, String methodSignatureSuffix, String delegatedMethodSuffix, String returnType) {
		Method method = constructCastTypeToReferenceAndReturnTargetGetter(attributeName, methodSignatureSuffix, delegatedMethodSuffix, returnType);
		addMethod(methods, method);
	}

	/**
	 * Constructs add method for a field of multiple cardinality.
	 * <pre>
	 * <code>
	 * public &lt;fieldType&gt; addFieldName()
	 * </code>
	 * </pre>
	 * @param fieldName
	 * @param returnType
	 * @return
	 */
	public Method buildAddMethodDelegated(String fieldName, String returnType) {
		Method method = new Method();
		method.setName(buildAddMethodName(fieldName));
		method.setReturnType(returnType);
		return method;
	}

	public void addMethod(List<Method> methods, Method method) {
		if(method != null && !methods.contains(method)) { //Check is needed for multi-type method
			methods.add(method);
		}
	}

	public Method constructFluentAddExtendedTypeMethod(String attributeName, String generatedType, String datatype, String returnType) {
		Method method = constructAddMethodSignature("Wrapped" + StringUtils.capitalize(attributeName), generatedType, returnType);
		method.setBody(getTemplate().addWrappedTypeToListMethodDelegatedBody(attributeName));
		method.addImport(datatype);
		return method;
	}

	public void buildFluentAddExtendedTypeMethod(List<Method> methods, String attributeName, String generatedType, String datatype, String returnType) {
		Method method = constructFluentAddExtendedTypeMethod(attributeName, generatedType, datatype, returnType);
		methods.add(method);
	}

	public Method constructAddExtendedTypeMethod(String attributeName, String generatedType, String datatype, String returnType) {
		Method method = buildAddMethodDelegated("Wrapped" + StringUtils.capitalize(attributeName), generatedType);
		method.setBody(getTemplate().addWrappedTypeToListMethodBody(generatedType, datatype, attributeName));
		method.addImport(datatype);
		return method;
	}

	public void buildAddExtendedTypeMethod(List<Method> methods, String attributeName, String generatedType, String datatype, String returnType) {
		Method method = constructAddExtendedTypeMethod(attributeName, generatedType, datatype, returnType);
		methods.add(method);
	}

	public void buildListOfReferenceValuedExtensionSetter(List<Method> methods, String attributeName, String argumentType, String returnType, String datatype, boolean isResource, String extensionUri) {
		Method method = constructListOfReferenceValuedExtensionSetter(attributeName, argumentType, returnType, datatype, isResource, extensionUri);
		addMethod(methods, method);
	}

	public void buildExtensionGetter(List<Method> methods, String attributeName, String methodSignatureSuffix, String adaptedClassName, String returnType, String datatype, boolean isResource, String extensionUri) {
		Method method = constructExtensionGetterBody(attributeName, methodSignatureSuffix, adaptedClassName, returnType, datatype, isResource, extensionUri);
		addMethod(methods, method);
	}

	public void buildExtensionSetter(List<Method> methods, String attributeName, String adaptedClassName, String argumentType, String returnType, String datatype, boolean isResource, String extensionUri) {
		Method method = constructExtensionSetter(attributeName, adaptedClassName, argumentType, returnType, datatype, isResource, extensionUri);
		addMethod(methods, method);
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


	protected void buildHasMethod(List<Method> methods, String attributeName) {
		buildHasMethod(methods, attributeName, "");
	}

	protected void buildHasMethod(List<Method> methods, String attributeName, String nameSuffix) {
		Method method;
		method = Method.constructNoArgMethod(Method.buildHasMethodName(attributeName + nameSuffix), "boolean");
		method.setBody(getTemplate().getAdapterHasMethodDelegationBody(attributeName + nameSuffix));
		addMethod(methods, method);
	}

	protected void buildHasMethodWithArgument(List<Method> methods, String attributeName, String nameSuffix, String type) { //TODO Remove nameSuffix parameter. Should be handled prior to method call.
		Method method;
		List<String> arguments = new ArrayList<>();
		arguments.add(type);
		method = Method.constructMethod(Method.buildHasMethodName(attributeName + nameSuffix), arguments, "boolean");
		method.setBody(getTemplate().getAdapterHasMethodDelegationBody(attributeName + nameSuffix, type));
		addMethod(methods, method);
	}
}
