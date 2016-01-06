package org.cdscollaborative.tools.fhir.codegenerator;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;

/**
 * Code generation framework using StringTemplate in conjunction
 * with the Roaster library.
 * 
 * @author Claude Nanjo
 *
 */
public class CodeTemplateUtils {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(CodeTemplateUtils.class);
	
	private String templatePath = "src/main/resources/templates";
	private STGroup groupMain;

	public CodeTemplateUtils() {}
	
	/**
	 * Method initializes the template utility by loading
	 * the templates into memory.
	 * 
	 */
	public CodeTemplateUtils initialize() {
		try {
			File rootMain = new File(templatePath);
			groupMain = new STGroupDir(rootMain.getCanonicalPath());
			return this;
		} catch(Exception e) {
			LOGGER.error("Error initializing StringTemplate. Validate template path: " + templatePath, e);
			throw new RuntimeException("Error initializing StringTemplate. Validate template path: " + templatePath, e);
		}
	}
	
	/**
	 * Returns path to templates.
	 * 
	 * @return
	 */
	public String getTemplatePath() {
		return templatePath;
	}
	
	/**
	 * Sets path to directory containing the templates
	 * 
	 * @param templatePath
	 */
	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}
	
	/**
	 * Method returning method body for multi-valued FHIR primitive attributes.
	 * 
	 * @param propertyName - The name of the property
	 * @param canonicalPath - The full class name/path of the return type
	 * @return
	 */
	public String getMultivaluedPrimitiveBody(String propertyName, String canonicalPath) {
		propertyName = StringUtils.capitalize(propertyName);
		ST st = groupMain.getInstanceOf("multivaluedPrimitiveMethodBody");
		st.add("propertyName", propertyName);
		st.add("canonicalClassPath", canonicalPath);
		return st.render();
	}
	
	/**
	 * Method returning method body for multi-valued FHIR attributes.
	 * 
	 * @param propertyName - The name of the property
	 * @param canonicalPath - The full class name/path of the return type
	 * @return
	 */
	public String getMultivaluedBody(String propertyName, String canonicalPath) {
		propertyName = StringUtils.capitalize(propertyName);
		ST st = groupMain.getInstanceOf("multivaluedMethodBody");
		st.add("propertyName", propertyName);
		st.add("canonicalClassPath", canonicalPath);
		return st.render();
	}
	
	/**
	 * Method returning method body for reference-type FHIR attributes.
	 * 
	 * @param propertyName - The name of the property
	 * @param canonicalPath - The full class name/path of the return type
	 * @return
	 */
	public String getReferenceGetterBody(String propertyName, String canonicalPath) {
		propertyName = StringUtils.capitalize(propertyName);
		ST st = groupMain.getInstanceOf("referenceGetterBody");
		st.add("propertyName", propertyName);
		st.add("canonicalClassPath", canonicalPath);
		return st.render();
	}
	
	/**
	 * Method returning method body for reference-type FHIR attributes.
	 * 
	 * @param propertyName - The name of the property
	 * @param canonicalPath - The full class name/path of the return type
	 * @return
	 */
	public String getProfiledReferenceGetterBody(String propertyName, String adapterClassPath, String canonicalPath) {
		propertyName = StringUtils.capitalize(propertyName);
		ST st = groupMain.getInstanceOf("profiledReferenceGetterBody");
		st.add("propertyName", propertyName);
		st.add("adapterClassPath", adapterClassPath);
		st.add("canonicalClassPath", canonicalPath);
		return st.render();
	}
	
	/**
	 * Method returning method body for adding item to a list.
	 * 
	 * @param className - The class name
	 * @param propertyName - The name of the property
	 * @return
	 */
	public String getAddToListMethodBody(String className, String propertyName) {
		propertyName = StringUtils.capitalize(propertyName);
		ST st = groupMain.getInstanceOf("addToListMethodBody");
		st.add("className", className);
		st.add("propertyName", propertyName);
		return st.render();
	}
	
	/**
	 * Method delegating to the adapter's class setter.
	 * 
	 * @param propertyName - The name of the property
	 * @return
	 */
	public String getAdapterSetMethodDelegationBody(String propertyName) {
		propertyName = StringUtils.capitalize(propertyName);
		ST st = groupMain.getInstanceOf("setMethodInvocation");
		st.add("className", "adaptedClass");
		st.add("propertyName", propertyName);
		st.add("param", "param");
		return st.render();
	}
	
	/**
	 * Method delegating to the adapter's class setter.
	 * 
	 * @param propertyName - The name of the property
	 * @return
	 */
	public String getSetMethodDelegationBody(String propertyName, String param) {
		propertyName = StringUtils.capitalize(propertyName);
		ST st = groupMain.getInstanceOf("setMethodInvocation");
		st.add("className", "adaptedClass");
		st.add("propertyName", propertyName);
		st.add("param", param);
		return st.render();
	}
	
	/**
	 * Method delegating to the adapter's class setter.
	 * 
	 * @param propertyName - The name of the property
	 * @return
	 */
	public String getUserDefinedExtensionTypeGetterBody(String type, String fieldUri) {
		ST st = groupMain.getInstanceOf("userDefinedExtensionTypeGetterBody");
		st.add("type", type);
		st.add("fieldUri", fieldUri);
		return st.render();
	}
	
	/**
	 * Method delegating to the adapter's class setter.
	 * 
	 * @param propertyName - The name of the property
	 * @return
	 */
	public String getUserDefinedExtensionTypeSetterBody(String type) {
		ST st = groupMain.getInstanceOf("userDefinedExtensionTypeSetterBody");
		st.add("type", type);
		return st.render();
	}
	
	/**
	 * Method delegating to the adapter's class getter.
	 * 
	 * @param propertyName - The name of the property
	 * @return
	 */
	public String getAdapterGetMethodDelegationBody(String propertyName) {
		propertyName = StringUtils.capitalize(propertyName);
		ST st = groupMain.getInstanceOf("getMethodInvocation");
		st.add("className", "adaptedClass");
		st.add("propertyName", propertyName);
		return st.render();
	}
	
	public String getAdapterGetMethodDelegationWithCastBody(String propertyName, String castTo) {
		propertyName = StringUtils.capitalize(propertyName);
		ST st = groupMain.getInstanceOf("getAndCastToExtendedType");
		st.add("className", "adaptedClass");
		st.add("propertyName", propertyName);
		st.add("castTo", castTo);
		return st.render();
	}
	
	public String getAdapterGetListMethodDelegationWithCastToListBody(String propertyName, String castTo) {
		propertyName = StringUtils.capitalize(propertyName);
		ST st = groupMain.getInstanceOf("getListAndCastToExtendedTypeList");
		st.add("className", "adaptedClass");
		st.add("propertyName", propertyName);
		st.add("castTo", castTo);
		return st.render();
	}
	
	public String getAdapterSetListMethodDelegationWithCastToListBody(String propertyName, String castTo) {
		propertyName = StringUtils.capitalize(propertyName);
		ST st = groupMain.getInstanceOf("setListAndCastToExtendedTypeList");
		st.add("className", "adaptedClass");
		st.add("propertyName", propertyName);
		st.add("castTo", castTo);
		return st.render();
	}
	
	/**
	 * Sets a reference type in HAPI FHIR.
	 * 
	 * @param propertyName - The name of the property
	 * @return
	 */
	public String getReferenceSetterBody(String propertyName) {
		propertyName = StringUtils.capitalize(propertyName);
		ST st = groupMain.getInstanceOf("referenceSetterBody");
		st.add("className", "adaptedClass");
		st.add("propertyName", propertyName);
		return st.render();
	}
	
	/**
	 * Sets a reference type in HAPI FHIR.
	 * 
	 * @param propertyName - The name of the property
	 * @return
	 */
	public String getProfiledReferenceSetterBody(String propertyName) {
		propertyName = StringUtils.capitalize(propertyName);
		ST st = groupMain.getInstanceOf("profiledReferenceSetterBody");
		st.add("className", "adaptedClass");
		st.add("propertyName", propertyName);
		return st.render();
	}
	
	/**
	 * Template for convenience method that returns the list of code labels in a 
	 * CodeableConcept. Will most likely be deprecated as it does not 
	 * specify the code system.
	 * 
	 * @param className
	 * @param parameterName
	 * @return
	 */
	public String getCodesAsStringListBody(String className, String parameterName) {
		ST st = groupMain.getInstanceOf("getCodesAsStringListBody");
		st.add("className", className);
		st.add("parameterName", parameterName);
		return st.render();
	}
	
	/**
	 * Template for the AdapterFactory.adapt(Bundle)
	 * 
	 * @return
	 */
	public String getAdaptBundle() {
		ST st = groupMain.getInstanceOf("adaptBundle");
		return st.render();
	}
	
	/**
	 * Template for AdapterFactory.adapt(Resource)
	 * @return
	 */
	public String getAdaptResource() {
		ST st = groupMain.getInstanceOf("adaptResource");
		return st.render();
	}
	
	/**
	 * Method returning getter body for extensions of a single type with multiple cardinality.
	 * 
	 * @param type - The return type
	 * @param uri - The URI for the FHIR extension
	 * 
	 * @return
	 */
	public String getExtensionListGetterBody(String type, String uri) {
		ST st = groupMain.getInstanceOf("extensionListGetterBody");
		st.add("type", type);
		st.add("uri", uri);
		return st.render();
	}
	
	/**
	 * Method returning setter body for extensions of a single type with multiple cardinality.
	 * 
	 * @param type - The argument type
	 * @param uri - The URI for the FHIR extension
	 * 
	 * @return
	 */
	public String getExtensionListSetterBody(String type, String uri) {
		ST st = groupMain.getInstanceOf("extensionListSetterBody");
		st.add("type", type);
		st.add("uri", uri);
		return st.render();
	}
	
	/**
	 * Method returning setter body for extensions of a single type with multiple cardinality.
	 * 
	 * @param type - The argument type
	 * @param uri - The URI for the FHIR extension
	 * 
	 * @return
	 */
	public String getExtensionSetterBody(String rootClassName, String uri) {
		ST st = groupMain.getInstanceOf("extensionSetterBody");
		st.add("rootClassName", rootClassName);
		st.add("uri", uri);
		return st.render();
	}
	
	public String getExtensionSetterBody(String uri) {
		return getExtensionSetterBody("adaptedClass", uri);
	}
	
	/**
	 * Method returning setter body for extensions of a single type with multiple cardinality.
	 * 
	 * @param type - The argument type
	 * @param uri - The URI for the FHIR extension
	 * 
	 * @return
	 */
	public String getExtensionGetterBody(String rootClassName, String type, String uri, String fieldName) {
		ST st = groupMain.getInstanceOf("extensionGetterBody");
		st.add("rootClassName", rootClassName);
		st.add("uri", uri);
		st.add("type", type);
		st.add("fieldName", fieldName);
		return st.render();
	}
	
	public String getExtensionGetterBody(String type, String uri, String fieldName) {
		return getExtensionGetterBody(type, uri, fieldName);
	}
	
	/**
	 * Method returning getter body for extensions of a single primitive type.
	 * 
	 * @param type - The argument type
	 * @param uri - The URI for the FHIR extension
	 * @param fieldName - The name of the extension field in question
	 * 
	 * @return
	 */
	public String getExtensionGetterBodyPrimitive(String type, String uri, String fieldName) {
		ST st = groupMain.getInstanceOf("extensionGetterBodyPrimitive");
		st.add("uri", uri);
		st.add("type", type);
		st.add("fieldName", fieldName);
		return st.render();
	}
	
	/**
	 * Method returning setter body for extensions of a single primitive type.
	 * 
	 * @param type - The argument type
	 * @param uri - The URI for the FHIR extension
	 * 
	 * @return
	 */
	public String getExtensionSetterBodyPrimitive(String type, String uri) {
		ST st = groupMain.getInstanceOf("extensionSetterBodyPrimitive");
		st.add("uri", uri);
		st.add("type", type);
		return st.render();
	}
	
	/**
	 * Method returning getter body
	 * 
	 * @param fieldName - The name of the field to return
	 * 
	 * @return
	 */
	public String getSimpleGetter(String fieldName) {
		ST st = groupMain.getInstanceOf("simpleGetter");
		st.add("fieldName", fieldName);
		return st.render();
	}
	
	/**
	 * Method returning setter body
	 * 
	 * @param type - The argument type
	 * @param uri - The URI for the FHIR extension
	 * 
	 * @return
	 */
	public String getSimpleSetter(String fieldName, String param) {
		ST st = groupMain.getInstanceOf("simpleSetter");
		st.add("fieldName", fieldName);
		st.add("param", param);
		return st.render();
	}
	
	public String getSerializeExtensionType() {
		ST st = groupMain.getInstanceOf("serializeExtensionType");
		return st.render();
	}
	
	public String getCodeEnumAsStringSetterBody(String fieldName, String bindingName) {
		ST st = groupMain.getInstanceOf("codeEnumAsStringSetterBody");
		st.add("fieldName", fieldName);
		st.add("bindingName", bindingName);
		return st.render();
	}
	
	public String getExtendedTypeGetterBody(String returnType, String fieldUri) {
		ST st = groupMain.getInstanceOf("extendedTypeGetterBody");
		st.add("type", returnType);
		st.add("fieldUri", fieldUri);
		return st.render();
	}
	
	public String getExtendedTypeSetterBody(String fieldUri) {
		ST st = groupMain.getInstanceOf("extendedTypeSetterBody");
		st.add("fieldUri", fieldUri);
		return st.render();
	}
	
	public String getBindExtensionToParent() {
		ST st = groupMain.getInstanceOf("bindExtensionToParent");
		return st.render();
	}
	
	public String getExtendedStructureListGetterBody(String callee, String type, String uri) {
		ST st = groupMain.getInstanceOf("extendedStructureListGetterBody");
		st.add("callee", callee);
		st.add("type", type);
		st.add("uri", uri);
		return st.render();
	}
	
	public String getExtendedStructureListSetterBody(String callee, String uri) {
		ST st = groupMain.getInstanceOf("extendedStructureListSetterBody");
		st.add("callee", callee);
		st.add("uri", uri);
		return st.render();
	}
	

	public String getUnsupportedGetter() {
		ST st = groupMain.getInstanceOf("unsupportedGetter");
		return st.render();
	}
	

	public String getUnsupportedSetter() {
		ST st = groupMain.getInstanceOf("unsupportedSetter");
		return st.render();
	}
}
