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
	public String parseAttributeName(String attributePath) {
		String suffix = PathUtils.getPathMinusRootComponent(attributePath);
		if(suffix != null && suffix.indexOf('.') < 0) {
			if(PathUtils.isMultivaluedAttribute(suffix)) {
				suffix = PathUtils.cleanMultiValuedAttributeName(suffix);
			}
		}
		if(suffix != null && suffix.equalsIgnoreCase("class")) {
			suffix = suffix + "_"; //Class is a reserved word in java. Note for DSTU2, instead of "_", use "Element" TODO Fix
		}
		return suffix;
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
