package ca.uhn.fhir.utils.codegen.hapi;

import ca.uhn.fhir.utils.common.metamodel.Method;
import ca.uhn.fhir.utils.fhir.PathUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseMethodHandler {
	
	public static final String ATTRIBUTE_NAME_ELEMENT_SUFFIX = "Element";
	
	private MethodBodyGenerator template;
	private String parentType;

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
}
