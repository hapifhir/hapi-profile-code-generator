package ca.uhn.fhir.utils.codegen.hapi;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;

import ca.uhn.fhir.utils.common.st.TemplateUtils;

/**
 * Code generation framework using StringTemplate in conjunction
 * with the Roaster library.
 * 
 * @author Claude Nanjo
 *
 */
public class MethodBodyGenerator extends TemplateUtils {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(MethodBodyGenerator.class);
	
	public MethodBodyGenerator() {}
	
	public MethodBodyGenerator initialize() {
		super.initialize();
		return this;
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
		ST st = getGroupMain().getInstanceOf("multivaluedPrimitiveMethodBody");
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
		ST st = getGroupMain().getInstanceOf("multivaluedMethodBody");
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
		ST st = getGroupMain().getInstanceOf("referenceGetterBody");
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
	public String getReferenceListAsTypedList(String callee, String propertyName, String propertyType) {
		propertyName = StringUtils.capitalize(propertyName);
		ST st = getGroupMain().getInstanceOf("getReferenceListAsTypedList");
		st.add("callee", callee);
		st.add("propertyName", propertyName);
		st.add("propertyType", propertyType);
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
		ST st = getGroupMain().getInstanceOf("profiledReferenceGetterBody");
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
		ST st = getGroupMain().getInstanceOf("addToListMethodBody");
		st.add("fieldName", className);
		st.add("propertyName", propertyName);
		return st.render();
	}
	
	/**
	 * Method returning method body for adding item to a list.
	 * 
	 * @param className - The class name
	 * @param propertyName - The name of the property
	 * @return
	 */
	public String getAddToListMethodDelegatedBody(String className, String propertyName, String propertyType) {
		propertyName = StringUtils.capitalize(propertyName);
		ST st = getGroupMain().getInstanceOf("addToListMethodDelegatedBody");
		st.add("fieldName", className);
		st.add("propertyName", propertyName);
		st.add("propertyType", propertyType);
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
		ST st = getGroupMain().getInstanceOf("setMethodInvocation");
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
	public String getFirstRepInvocationBody(String propertyName) {
		propertyName = StringUtils.capitalize(propertyName);
		ST st = getGroupMain().getInstanceOf("getFirstRepInvocation");
		st.add("className", "adaptedClass");
		st.add("propertyName", propertyName);
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
		ST st = getGroupMain().getInstanceOf("setMethodInvocation");
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
		ST st = getGroupMain().getInstanceOf("userDefinedExtensionTypeGetterBody");
		st.add("type", type);
		st.add("fieldUri", fieldUri);
		return st.render();
	}

	public String castTypeToReferenceAndReturnTarget(String propertyName, String propertyType) {
		propertyName = StringUtils.capitalize(propertyName);
		ST st = getGroupMain().getInstanceOf("castTypeToReferenceAndReturnTarget");
		st.add("propertyName", propertyName);
		st.add("propertyType", propertyType);
		return st.render();
	}

	public String wrapResourceInReferenceAndSet(String propertyName) {
		propertyName = StringUtils.capitalize(propertyName);
		ST st = getGroupMain().getInstanceOf("wrapResourceInReferenceAndSet");
		st.add("propertyName", propertyName);
		return st.render();
	}
	
	/**
	 * Method delegating to the adapter's class setter.
	 * 
	 * @param propertyName - The name of the property
	 * @return
	 */
	public String getUserDefinedExtensionTypeSetterBody(String type) {
		ST st = getGroupMain().getInstanceOf("userDefinedExtensionTypeSetterBody");
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
		ST st = getGroupMain().getInstanceOf("getMethodInvocation");
		st.add("className", "adaptedClass");
		st.add("propertyName", propertyName);
		return st.render();
	}

	/**
	 * Method delegating to the adapter's class getter.
	 *
	 * @param propertyName - The name of the property
	 * @return
	 */
	public String getAdapterGetWithCastMethodDelegationBody(String propertyName, String castType) {
		propertyName = StringUtils.capitalize(propertyName);
		ST st = getGroupMain().getInstanceOf("getMethodInvocationWithCast");
		st.add("className", "adaptedClass");
		st.add("propertyName", propertyName);
		st.add("castType", castType);
		return st.render();
	}
	
	/**
	 * Method delegating to the adapter's class getter. Catches any
	 * exception and rethrows as a runtime exception.
	 * 
	 * @param propertyName - The name of the property
	 * @return
	 */
	public String getAdapterGetMethodDelegationWithTryCatchBody(String propertyName) {
		propertyName = StringUtils.capitalize(propertyName);
		ST st = getGroupMain().getInstanceOf("getMethodInvocationWithTryCatch");
		st.add("className", "adaptedClass");
		st.add("propertyName", propertyName);
		return st.render();
	}
	
	public String getAdapterGetMethodDelegationWithCastBody(String propertyName, String castTo) {
		propertyName = StringUtils.capitalize(propertyName);
		ST st = getGroupMain().getInstanceOf("getAndCastToExtendedType");
		st.add("className", "adaptedClass");
		st.add("propertyName", propertyName);
		st.add("castTo", castTo);
		return st.render();
	}
	
	public String getAdapterGetListMethodDelegationWithCastToListBody(String propertyName, String castTo) {
		propertyName = StringUtils.capitalize(propertyName);
		ST st = getGroupMain().getInstanceOf("getListAndCastToExtendedTypeList");
		st.add("className", "adaptedClass");
		st.add("propertyName", propertyName);
		st.add("castTo", castTo);
		return st.render();
	}
	
	public String getAdapterSetListMethodDelegationWithCastToListBody(String propertyName, String castTo) {
		propertyName = StringUtils.capitalize(propertyName);
		ST st = getGroupMain().getInstanceOf("setListAndCastToExtendedTypeList");
		st.add("className", "adaptedClass");
		st.add("propertyName", propertyName);
		st.add("castTo", castTo);
		return st.render();
	}
	
	public String getWrappedTypeListBody(String adapterType, String adaptedType, String propertyName) {
		propertyName = StringUtils.capitalize(propertyName);
		ST st = getGroupMain().getInstanceOf("getWrappedTypeList");
		st.add("adapterType", adapterType);
		st.add("adaptedType", adaptedType);
		st.add("propertyName", propertyName);
		return st.render();
	}
	
	public String setWrappedTypeListBody(String adapterType, String adaptedType, String propertyName) {
		propertyName = StringUtils.capitalize(propertyName);
		ST st = getGroupMain().getInstanceOf("setWrappedTypeList");
		st.add("adapterType", adapterType);
		st.add("adaptedType", adaptedType);
		st.add("propertyName", propertyName);
		return st.render();
	}
	
	public String addWrappedTypeToListMethodDelegatedBody(String propertyName) {
		propertyName = StringUtils.capitalize(propertyName);
		ST st = getGroupMain().getInstanceOf("addWrappedTypeToListMethodDelegatedBody");
		st.add("propertyName", propertyName);
		return st.render();
	}
	
	public String addWrappedTypeToListMethodBody(String adapterType, String adaptedType, String propertyName) {
		propertyName = StringUtils.capitalize(propertyName);
		ST st = getGroupMain().getInstanceOf("addWrappedTypeToListMethodBody");
		st.add("adapterType", adapterType);
		st.add("adaptedType", adaptedType);
		st.add("propertyName", propertyName);
		return st.render();
	}
	
	public String getWrappedTypeFirstRepMethodBody(String adapterType, String adaptedType, String propertyName) {
		propertyName = StringUtils.capitalize(propertyName);
		ST st = getGroupMain().getInstanceOf("getWrappedTypeFirstRep");
		st.add("adapterType", adapterType);
		st.add("adaptedType", adaptedType);
		st.add("propertyName", propertyName);
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
		ST st = getGroupMain().getInstanceOf("referenceSetterBody");
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
		ST st = getGroupMain().getInstanceOf("profiledReferenceSetterBody");
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
		ST st = getGroupMain().getInstanceOf("getCodesAsStringListBody");
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
		ST st = getGroupMain().getInstanceOf("adaptBundle");
		return st.render();
	}
	
	/**
	 * Template for AdapterFactory.adapt(Resource)
	 * @return
	 */
	public String getAdaptResource() {
		ST st = getGroupMain().getInstanceOf("adaptResource");
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
		ST st = getGroupMain().getInstanceOf("extensionListGetterBody");
		st.add("type", type);
		st.add("uri", uri);
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
	public String getExtensionListGetterBodyDstu3(String type, String uri) {
		ST st = getGroupMain().getInstanceOf("extensionListGetterBodyDstu3");
		st.add("type", type);
		st.add("uri", uri);
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
	public String getExtensionListGetterBodyResourceDstu3(String type, String uri) {
		ST st = getGroupMain().getInstanceOf("extensionListGetterBodyResourceDstu3");
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
		ST st = getGroupMain().getInstanceOf("extensionListSetterBody");
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
	public String getExtensionListSetterBodyDstu3(String type, String uri) {
		ST st = getGroupMain().getInstanceOf("extensionListSetterBodyDstu3");
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
	public String getExtensionListSetterBodyResourceDstu3(String uri) {
		ST st = getGroupMain().getInstanceOf("extensionListSetterBodyResourceDstu3");
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
		ST st = getGroupMain().getInstanceOf("extensionSetterBody");
		st.add("rootClassName", rootClassName);
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
	public String getExtensionSetterBodyDstu3(String rootClassName, String uri) {
		ST st = getGroupMain().getInstanceOf("extensionSetterBodyDstu3");
		st.add("rootClassName", rootClassName);
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
	public String getExtensionSetterBodyResourceDstu3(String rootClassName, String uri) {
		ST st = getGroupMain().getInstanceOf("extensionSetterBodyResourceDstu3");
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
		ST st = getGroupMain().getInstanceOf("extensionGetterBody");
		st.add("rootClassName", rootClassName);
		st.add("uri", uri);
		st.add("type", type);
		st.add("fieldName", fieldName);
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
	public String getExtensionGetterBodyDstu3(String rootClassName, String type, String uri, String fieldName) {
		ST st = getGroupMain().getInstanceOf("extensionGetterBodyDstu3");
		st.add("rootClassName", rootClassName);
		st.add("uri", uri);
		st.add("type", type);
		st.add("fieldName", fieldName);
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
	public String getExtensionGetterBodyResourceDstu3(String rootClassName, String type, String uri, String fieldName) {
		ST st = getGroupMain().getInstanceOf("extensionGetterBodyResourceDstu3");
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
		ST st = getGroupMain().getInstanceOf("extensionGetterBodyPrimitive");
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
		ST st = getGroupMain().getInstanceOf("extensionSetterBodyPrimitive");
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
//	public String getSimpleGetter(String fieldName) {
//		ST st = getGroupMain().getInstanceOf("simpleGetter");
//		st.add("fieldName", fieldName);
//		return st.render();
//	}
	
	/**
	 * Method returning setter body
	 * 
	 * @param type - The argument type
	 * @param uri - The URI for the FHIR extension
	 * 
	 * @return
	 */
//	public String getSimpleSetter(String fieldName, String param) {
//		ST st = getGroupMain().getInstanceOf("simpleSetter");
//		st.add("fieldName", fieldName);
//		st.add("param", param);
//		return st.render();
//	}
	
	public String getSerializeExtensionType() {
		ST st = getGroupMain().getInstanceOf("serializeExtensionType");
		return st.render();
	}
	
	public String getCodeEnumAsStringSetterBody(String fieldName, String bindingName) {
		ST st = getGroupMain().getInstanceOf("codeEnumAsStringSetterBody");
		st.add("fieldName", fieldName);
		st.add("bindingName", bindingName);
		return st.render();
	}
	
	public String getExtendedTypeGetterBody(String returnType, String fieldUri) {
		ST st = getGroupMain().getInstanceOf("extendedTypeGetterBody");
		st.add("type", returnType);
		st.add("fieldUri", fieldUri);
		return st.render();
	}
	
	public String getExtendedTypeSetterBody(String fieldUri) {
		ST st = getGroupMain().getInstanceOf("extendedTypeSetterBody");
		st.add("fieldUri", fieldUri);
		return st.render();
	}
	
	public String getBindExtensionToParent() {
		ST st = getGroupMain().getInstanceOf("bindExtensionToParent");
		return st.render();
	}
	
	public String getExtendedStructureListGetterBody(String callee, String type, String uri) {
		ST st = getGroupMain().getInstanceOf("extendedStructureListGetterBody");
		st.add("callee", callee);
		st.add("type", type);
		st.add("uri", uri);
		return st.render();
	}
	
	public String getExtendedStructureListSetterBody(String callee, String uri) {
		ST st = getGroupMain().getInstanceOf("extendedStructureListSetterBody");
		st.add("callee", callee);
		st.add("uri", uri);
		return st.render();
	}
	

	public String getUnsupportedGetter() {
		ST st = getGroupMain().getInstanceOf("unsupportedGetter");
		return st.render();
	}
	

	public String getUnsupportedSetter() {
		ST st = getGroupMain().getInstanceOf("unsupportedSetter");
		return st.render();
	}

	public String getGeneratedClassMultiCardinalityGetter(String generatedType, String wrappedType, String propertyName) {
		ST st = getGroupMain().getInstanceOf("generatedClassMultiCardinalityGetter");
		st.add("generatedType", generatedType);
		st.add("wrappedType", wrappedType);
		st.add("propertyName", propertyName);
		return st.render();
	}

	public String getGeneratedClassMultiCardinalitySetter(String generatedType, String wrappedType, String propertyName) {
		ST st = getGroupMain().getInstanceOf("generatedClassMultiCardinalitySetter");
		st.add("generatedType", generatedType);
		st.add("wrappedType", wrappedType);
		st.add("propertyName", propertyName);
		return st.render();
	}
	
	/**************************************************************
	 * Generic Code Generation Functions
	 * ************************************************************/
	
	/**
	 * Template for initializing an instance variable to the fieldType argument
	 * using the 'new' operator.
	 * 
	 * @param fieldName The instance field name
	 * @param fieldType The type argument to the 'new' operator
	 * 
	 * @return
	 */
	public String getInitializeVariableStatement(String fieldName, String fieldType) {
		ST st = getGroupMain().getInstanceOf("initializeVariable");
		st.add("fieldName", fieldName);
		st.add("fieldType", fieldType);
		return st.render();
	}
	
	public String getAssignVariableStatement(String fieldName, String argName) {
		ST st = getGroupMain().getInstanceOf("assignVariable");
		st.add("fieldName", fieldName);
		st.add("argName", argName);
		return st.render();
	}
}
