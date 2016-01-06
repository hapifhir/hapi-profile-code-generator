package org.cdscollaborative.tools.fhir.codegenerator.method;

import org.cdscollaborative.tools.fhir.codegenerator.CodeTemplateUtils;
import org.cdscollaborative.tools.fhir.utils.FhirResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt.Type;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;

/**
 * The MethodHandlerResolver is used to identify which MethodHandlerGenerator applies
 * to the given profile element. It then instantiates and configures the instance before
 * returning it to the calling application.
 * 
 * @author Claude Nanjo
 *
 */
public class MethodHandlerResolver {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(MethodHandlerResolver.class);
	
	private FhirResourceManager fhirResourceManager;
	private CodeTemplateUtils codeTemplateUtils;
	
	/**
	 * Constructor passing configuration resources to the resolver instance.
	 * 
	 * @param fhirResourceManager
	 * @param codeTemplateUtils
	 */
	public MethodHandlerResolver(FhirResourceManager fhirResourceManager, CodeTemplateUtils codeTemplateUtils) {
		this.fhirResourceManager = fhirResourceManager;
		this.codeTemplateUtils = codeTemplateUtils;
	}
	
	/**
	 * Returns the FhirResourceManager associated with this instance
	 * 
	 * @return
	 */
	public FhirResourceManager getFhirResourceManager() {
		return fhirResourceManager;
	}

	/**
	 * Sets the FhirResourceManager for this instance
	 * 
	 * @param fhirResourceManager
	 */
	public void setManager(FhirResourceManager fhirResourceManager) {
		this.fhirResourceManager = fhirResourceManager;
	}
	
	/**
	 * Returns the code generation template manager for this instance
	 * 
	 * @return
	 */
	public CodeTemplateUtils getTemplate() {
		return codeTemplateUtils;
	}
	
	/**
	 * Sets the code template manager to use for this instance
	 * 
	 * @param codeTemplateUtils
	 */
	public void setTemplate(CodeTemplateUtils codeTemplateUtils) {
		this.codeTemplateUtils = codeTemplateUtils;
	}
	
	/**
	 * Returns the first matching handler for this element. 
	 * <p>
	 * Note that all handlers are first constructed and then initialized.
	 * <p>
	 * Note that handlers are selected if the IMethodHandler.appliesTo returns true for
	 * the given profile and element.
	 * 
	 * @param profile
	 * @param element
	 * @return
	 */
	public IMethodHandler identifyHandler(StructureDefinition profile, ElementDefinitionDt element, String generatedCodePackage) {
		IMethodHandler handler = null;
		if(FilteredAttributeHandler.appliesTo(profile, element)) {
			handler = new FilteredAttributeHandler(fhirResourceManager, codeTemplateUtils, profile, element);
			handler.initialize();
		} else if(ContainedAttributeHandler.appliesTo(profile, element)) {
			handler = new ContainedAttributeHandler(fhirResourceManager, codeTemplateUtils, profile, element);
			handler.initialize();
		} else if(LanguageAttributeHandler.appliesTo(profile, element)) {
			handler = new LanguageAttributeHandler(fhirResourceManager, codeTemplateUtils, profile, element);
			handler.initialize();
		} else if(ExtendedAttributeHandler.appliesTo(profile, element)) {
			handler = new ExtendedAttributeHandler(fhirResourceManager, codeTemplateUtils, profile, element);
			handler.setGeneratedCodePackage(generatedCodePackage);
			handler.initialize();
		} else if(JavaTypeHandler.appliesTo(profile, element)) {
			handler = new JavaTypeHandler(fhirResourceManager, codeTemplateUtils, profile, element);
			handler.initialize();
		} else if(CodedAttributeHandler.appliesTo(profile, element)) {
			handler = new CodedAttributeHandler(fhirResourceManager, codeTemplateUtils, profile, element);
			handler.initialize();
		} else if(CodedEnumAttributeHandler.appliesTo(profile, element)) {
			handler = new CodedEnumAttributeHandler(fhirResourceManager, codeTemplateUtils, profile, element);
			handler.initialize();
		} else if(SimpleAttributeHandler.appliesTo(profile, element)) {
			handler = new SimpleAttributeHandler(fhirResourceManager, codeTemplateUtils, profile, element);
			handler.initialize();
		} else if(ReferenceTypeHandler.appliesTo(profile, element)) {
			handler = new ReferenceTypeHandler(fhirResourceManager, codeTemplateUtils, profile, element);
			handler.initialize();
		} else if(MultiTypeAttributeHandler.appliesTo(profile, element)) {
			handler = new MultiTypeAttributeHandler(fhirResourceManager, codeTemplateUtils, profile, element);
			handler.initialize();
		} else {
			LOGGER.info("No method handler found for element " + element.getPath());
		}
		return handler;
	}
	
	/**
	 * Creates and initializes a new handler
	 * 
	 * @param profile
	 * @param element
	 * @param generatedCodePackage
	 * @return
	 */
	public UserDefinedExtensionTypeHandler buildUserDefinedExtensionTypeHandler(StructureDefinition profile, ElementDefinitionDt element, String generatedCodePackage) {
		UserDefinedExtensionTypeHandler handler = new UserDefinedExtensionTypeHandler(fhirResourceManager, codeTemplateUtils, profile, element);
		handler.setGeneratedCodePackage(generatedCodePackage);
		handler.initialize();
		return handler;
	}
}
