package ca.uhn.fhir.utils.common.metamodel;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Method {
	
	private List<MethodParameter> parameters;
	private String returnType;
	private String name;
	private String body;
	private List<String> imports;
	private boolean isConstructor;
	
	public Method() {
		parameters = new ArrayList<MethodParameter>();
		imports = new ArrayList<String>();
	}
	
	public String getReturnType() {
		return returnType;
	}
	
	public void setReturnType(String type) {
		this.returnType = type;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		if(name.contains(".")) {
			throw new RuntimeException(name);
		}
		this.name = name;
	}

	public void addParameter(String paramName, String paramType) {
		parameters.add(new MethodParameter(paramName, paramType));
	}
	
	public void addParameter(String paramType) {
		parameters.add(new MethodParameter("param", paramType));
	}
	
	public List<MethodParameter> getParameters() {
		return parameters;
	}
	
	public void setParameters(List<MethodParameter> parameters) {
		this.parameters = parameters;
	}
	
	public Method setBody(String body) {
		this.body = body;
		return this;
	}
	
	public String getBody() {
		return body;
	}
	
	public void addImport(String importString) {
		imports.add(importString);
	}
	
	public void addImports(List<String> imports) {
		this.imports.addAll(imports);
	}
	
	public List<String> getImports() {
		return imports;
	}
	
	public String toString() {
		return returnType + " " + name;
	}
	
	public boolean isConstructor() {
		return isConstructor;
	}

	public void isConstructor(boolean isConstructor) {
		this.isConstructor = isConstructor;
	}

	/**
	 * Returns a no-arg method with methodName and return type.
	 * 
	 * @param methodName
	 * @param type
	 * @param imports
	 * @return
	 */
	public static Method constructNoArgMethod(String methodName, String type, List<String> imports) {
		Method method = new Method();
		method.setName(methodName);
		if(imports != null) {
			method.getImports().addAll(imports);
		}
		method.setReturnType(type);
		return method;
	}
	
	public static Method constructNoArgMethod(String methodName, String type) {
		return constructNoArgMethod(methodName, type, null);
	}
	
	/**
	 * Constructs a void signature method
	 * 
	 * @param methodName
	 * @param parameterTypes
	 * @return
	 */
	public static Method constructVoidMethod(String methodName, List<String> parameterTypes) {
		Method method = new Method();
		method.setName(methodName);
		for(String paramType : parameterTypes) {
			method.addParameter(paramType);
		}
		return method;
	}
	
	/**
	 * Constructs a method with the name argument, list of parameters, and the return type as the method signature.
	 * @param methodName
	 * @param parameterTypes
	 * @param returnType
	 * @return
	 */
	public static Method constructMethod(String methodName, List<String> parameterTypes, String returnType) {
		Method method = constructVoidMethod(methodName, parameterTypes);
		method.setReturnType(returnType);
		return method;
	}
	
	/**
	 * Returns a getter following the JavaBean convention.
	 * 
	 * <pre>
	 * <code>
	 * public attributeType getAttributeName() {
	 *    return attributeName;
	 * }
	 * </code>
	 * </pre>
	 * @param attributeName
	 * @param attributeType
	 * @return
	 */
	public static Method constructSimpleGetterMethod(String attributeName, String attributeType) {
		Method getter = constructNoArgMethod("get" + StringUtils.capitalize(attributeName), attributeType);
		getter.setBody("return " + StringUtils.uncapitalize(attributeName) + ";");
		return getter;
	}
	
	/**
	 * Returns a setter following the JavaBean convention.
	 * 
	 * <pre>
	 * <code>
	 * public void setAttributeName(attributeType attributeName) {
	 *    this.attributeName = attributeName;
	 * }
	 * </code>
	 * </pre>
	 * @param attributeName
	 * @param attributeType
	 * @return
	 */
	public static Method constructSimpleSetterMethod(String attributeName, String attributeType) {
		Method setter = new Method();
		setter.setName("set" + StringUtils.capitalize(attributeName));
		setter.addParameter(attributeName, attributeType);
		setter.setBody("this." + StringUtils.uncapitalize(attributeName) + "=" + StringUtils.uncapitalize(attributeName) + ";");
		return setter;
	}
	
	public static void addGetterSetterFieldToClass(ClassModel classModel, String attributeName, String attributeType) {
		Method getter = constructSimpleGetterMethod(attributeName, attributeType);
		Method setter = constructSimpleSetterMethod(attributeName, attributeType);
//		ClassField field = new ClassField(StringUtils.uncapitalize(attributeName), attributeType);
//		field.addModifier(ModifierEnum.PRIVATE);
//		classModel.addField(field);
		classModel.addMethod(getter);
		classModel.addMethod(setter);
		List<ModifierEnum> modifiers = new ArrayList<ModifierEnum>();
		modifiers.add(ModifierEnum.PRIVATE);
		ClassField field = new ClassField(StringUtils.uncapitalize(attributeName), attributeType , modifiers, "new ExtensionDt(false, uri);");//TODO Move to constructor body
		classModel.addField(field);
		
	}
}
