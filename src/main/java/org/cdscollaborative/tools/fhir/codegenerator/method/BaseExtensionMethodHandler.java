package org.cdscollaborative.tools.fhir.codegenerator.method;

import org.apache.commons.lang3.StringUtils;
import org.cdscollaborative.tools.fhir.codegenerator.CodeTemplateUtils;
import org.cdscollaborative.tools.fhir.model.FhirExtension;
import org.cdscollaborative.tools.fhir.utils.FhirResourceManager;

import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt.Type;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;

public abstract class BaseExtensionMethodHandler extends BaseMethodGenerator {
	
	private String extensionUri;
	private ElementDefinitionDt extendedElement;
	private boolean skipProcessing = false;/*Nested extensions do not define a type and should be skipped*/
	private boolean isExtendedStructure = false;
	

	public BaseExtensionMethodHandler(FhirResourceManager manager, CodeTemplateUtils template, StructureDefinition profile, ElementDefinitionDt element) {
		super(manager, template, profile, element);
	}
	
	public String getExtensionUri() {
		return extensionUri;
	}

	public void setExtensionUri(String extensionUri) {
		this.extensionUri = extensionUri;
	}

	public ElementDefinitionDt getExtendedElement() {
		return extendedElement;
	}

	public void setExtendedElement(ElementDefinitionDt extendedElement) {
		this.extendedElement = extendedElement;
	}

	public boolean isSkipProcessing() {
		return skipProcessing;
	}

	public void setSkipProcessing(boolean skipProcessing) {
		this.skipProcessing = skipProcessing;
	}

	public boolean isExtendedStructure() {
		return isExtendedStructure;
	}

	public void setExtendedStructure(boolean isExtendedStructure) {
		this.isExtendedStructure = isExtendedStructure;
	}

	/**
	 * Method loads the extension definition and uses the extension definition to configure a
	 * clone of the extended element. All defaults are set on the clone based
	 * on the extension definition provided these have not been overridden in the profile.
	 * <p>
	 * All method generation operations will be done using the metadata specified
	 * by the cloned element definition which represent the formal extension in the
	 * context of this profile.
	 * 
	 * @param element
	 * @return
	 */
	public void handleExtensionElement() {
		if(getElement().getName() == null || getElement().getPath().contains("extension.extension")) {
			skipProcessing = true;
			LOGGER.info("Nested extensions not currently supported. " + getElement().getName());
			return;
		}
		if(getElement().getPath().split("\\.").length > 2) {
			skipProcessing = true;
			LOGGER.info("Extensions on types not currently supported. " + getElement().getName());
			return;
		}
		
//		if(getElement().getName().indexOf('.') >= 0) {
//			skipProcessing = true;
//			LOGGER.info("Extensions on extensions not currently supported. " + getElement().getName());
//			return;
//		}
		extensionUri = getElement().getTypeFirstRep().getProfile();
		FhirExtension extensionDef = getFhirResourceManager().getFhirExtension(extensionUri);
		extendedElement = FhirResourceManager.shallowCloneElement(getElement());
		if(extensionDef != null) {
			if(extendedElement.getMin() == null) {
				extendedElement.setMin(extensionDef.getLowCardinality());
			}
			if(extendedElement.getMax() == null) {
				extendedElement.setMax(extensionDef.getHighCardinality());
			}
			if(extendedElement.getType().size() == 1 
					&& extendedElement.getTypeFirstRep().getCode().equals("Extension")) {
				extendedElement.setType(extensionDef.getTypes());
				if(extensionDef.getTypes().size() == 1) {
					handleType(extensionDef.getTypes().get(0));//TODO Hack until I figure what to do with multi-type attributes. Need a way to chain handlers. Investigate
				} else {
					setFullyQualifiedType(getGeneratedCodePackage() + "." + StringUtils.capitalize(getElement().getName()));
					isExtendedStructure = true;
					return;
				}
			}
			if(extendedElement.getPath() != null) {
				String prefix = extendedElement.getPath().substring(0, extendedElement.getPath().indexOf('.'));
				extendedElement.setPath(prefix + "." + extendedElement.getName());
			}
		}
	}
	
	/**
	 * Method that identifies the HAPI FHIR type
	 * 
	 * @param type
	 */
	public void handleType(Type type) {
		String typeString = type.getCode();
		setFullyQualifiedType(getFhirResourceManager().getFullyQualifiedJavaType(typeString));
	}

}
