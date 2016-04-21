package ca.uhn.fhir.utils.codegen.hapi.methodgenerator;

import org.apache.commons.lang3.StringUtils;

import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt.Type;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;
import ca.uhn.fhir.utils.codegen.hapi.MethodBodyGenerator;
import ca.uhn.fhir.utils.codegen.hapi.FhirResourceManager;
import ca.uhn.fhir.utils.fhir.PathUtils;
import ca.uhn.fhir.utils.fhir.model.FhirExtensionDefinition;

public abstract class BaseExtensionMethodHandler extends BaseMethodGenerator {
	
	private String extensionUri;
	private ElementDefinitionDt extendedElement;
	private boolean skipProcessing = false;/*Nested extensions do not define a type and should be skipped*/
	private boolean isExtendedStructure = false;
	

	public BaseExtensionMethodHandler(FhirResourceManager manager, MethodBodyGenerator template, StructureDefinition profile, ElementDefinitionDt element) {
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
		extensionUri = getElement().getTypeFirstRep().getProfileFirstRep().getValueAsString();//TODO How to handle multiple profiles on types
		FhirExtensionDefinition extensionDef = getFhirResourceManager().getFhirExtension(PathUtils.getExtensionRootPath(extensionUri));
		if(extensionDef == null) {
			LOGGER.error(extensionUri + " for element " + getElement().getName() + " has no associated extension registered");
		}
		String extensionName = PathUtils.getExtensionName(extensionUri);
		extendedElement = extensionDef.getExtensionByName(extensionName);
		if(extendedElement == null) {
			LOGGER.error("Error processing " + extensionUri + " no extension definition found. Check URI or extension directories");
		}
		//Handle extended structure types. If it is an extended structure, it will have its own type. Set that as the type:
		if(getElement().getType().size() == 2 && getElement().getType().get(1).getCode().contains(getGeneratedCodePackage())) {
			extendedElement.setType(getElement().getType());//Override the extension type with the type of the class we will create for the extended structure
		}
		if(extensionName != null && extensionName.contains("-")) {
			extensionName = extensionName.substring(extensionName.lastIndexOf('-') + 1);
			extendedElement.setName(extensionName);
		}
		extendedElement = FhirResourceManager.shallowCloneElement(extendedElement);
		if(extendedElement.getType().size() == 1) {
			handleType(extendedElement.getTypeFirstRep());
		} else if(extendedElement.getType().size() == 0){
				setFullyQualifiedType(getGeneratedCodePackage() + "." + StringUtils.capitalize(getElement().getName()));
				return;
		} else if(extendedElement.getType().size() == 2) {//TODO Is this done because index 0 is extension and index 1 is a type? Fix this behavior for legitimate multitypes given new logic. LOGIC IS WRONG.
			handleType(extendedElement.getType().get(1));
		} else {
			//TODO Implement support for multi-type attributes
			LOGGER.error("Multitype attribute " + extendedElement.getName() + " encountered but not yet handled in code");
			extendedElement.getType().clear();
			extendedElement.getTypeFirstRep().setCode("unsupported");
		}
//		if(extensionDef != null) {//Need to gracefully handle case when extensionDef is null
//			if(extendedElement.getType().size() >= 1 && extendedElement.getType().size() <= 2 
//					&& extendedElement.getTypeFirstRep().getCode().equals("Extension")) {
//				if(extendedElement.getType().size() == 1) {
//					extendedElement.setType(extensionDef.getTypes());
//					if(extensionDef.getTypes().size() == 1) {
//						handleType(extensionDef.getTypes().get(0));//TODO Hack until I figure what to do with multi-type attributes. Need a way to chain handlers. Investigate
//					} else {
//						setFullyQualifiedType(getGeneratedCodePackage() + "." + StringUtils.capitalize(getElement().getName()));
//						isExtendedStructure = true;
//						return;
//					}
//				} else {
//					setFullyQualifiedType(extendedElement.getType().get(1).getCode());
//					isExtendedStructure = true;
//				}
//			}
			//Do I still need this?
			if(extendedElement.getPath() != null) {
				extendedElement.setPath(PathUtils.generateExtensionPath(extendedElement.getPath(), extendedElement.getName()));
			}
	}
	
	/**
	 * Method that identifies the HAPI FHIR type
	 * 
	 * @param type
	 */
	public void handleType(Type type) {
		setFullyQualifiedType(getFhirResourceManager().getFullyQualifiedJavaType(getProfile(), type));
	}

}
