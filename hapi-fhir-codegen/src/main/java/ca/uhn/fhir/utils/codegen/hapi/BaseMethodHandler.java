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
	public Method constructSetMethodFromField(String fieldName, String parameterType, String returnType) {
		List<String> parameterTypes = new ArrayList<String>();
		parameterTypes.add(parameterType);
		return Method.constructMethod(buildSetterName(fieldName), parameterTypes, returnType);
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
	public Method constructAddMethod(String fieldName, String parameterType, String returnType) {
		Method method = new Method();
		method.setName(buildAddMethodName(fieldName));
		method.addParameter(parameterType);
		method.setReturnType(returnType);
		return method;
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
}
