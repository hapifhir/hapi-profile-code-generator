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
	 * @param type
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

	public Method constructGetterMethod(String attributeName, String returnType, String fhirType, boolean isExtension, String extensionUri) {
		Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName), returnType);
		if (isExtension) {
			method.setBody(getTemplate().getExtensionGetterBodyDstu3("adaptedClass", fhirType, extensionUri, attributeName));//TODO Test this out for primitive types that are extended. THIS IS UNTESTED CODE.
		} else {
			method.setBody(getTemplate().getAdapterGetMethodDelegationWithTryCatchBody(attributeName));
		}
		method.addImport(fhirType);
		return method;
	}

	public void buildAddMethod(List<Method> methods, String attributeName, String parameterType, String returnType) {
		Method method = constructAddMethod(attributeName, parameterType, returnType);
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

	public void buildGetterMethod(List<Method> methods, String attributeName, String returnType, String fhirType, boolean isExtension, String extensionUri) {
		Method method = constructGetterMethod(attributeName, returnType, fhirType, isExtension, extensionUri);
		addMethod(methods, method);
	}

	public void buildListSetterMethod(List<Method> methods, String attributeName, String listType, String listParameterType, String returnType) {
		Method method = constructListSetterMethod(attributeName, listType, listParameterType, returnType);
		addMethod(methods, method);
	}

	public void buildSetterMethod(List<Method> methods, String attributeName, String argumentType, String returnType) {
		Method method = constructSetterMethod(attributeName, argumentType, returnType);
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
	 * @param type
	 * @return
	 */
	public Method buildAddMethodDelegated(String fieldName, String returnType) {
		Method method = new Method();
		method.setName(buildAddMethodName(fieldName));
		method.setReturnType(returnType);
		return method;
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

	public void addMethod(List<Method> methods, Method method) {
		if(method != null && !methods.contains(method)) { //Check is needed for multi-type method
			methods.add(method);
		}
	}
}
