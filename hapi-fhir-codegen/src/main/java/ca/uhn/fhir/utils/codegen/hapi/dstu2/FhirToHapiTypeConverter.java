package ca.uhn.fhir.utils.codegen.hapi.dstu2;

import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;
import ca.uhn.fhir.utils.codegen.hapi.BaseTypeConverter;
import ca.uhn.fhir.utils.codegen.hapi.HapiFhirUtils;
import ca.uhn.fhir.utils.codegen.hapi.HapiType;
import ca.uhn.fhir.utils.fhir.PathUtils;
import ca.uhn.fhir.utils.fhir.model.FhirExtensionDefinition;

public class FhirToHapiTypeConverter extends BaseTypeConverter<ElementDefinitionDt, FhirResourceManagerDstu2> {
	
	public FhirToHapiTypeConverter(FhirResourceManagerDstu2 manager, String generatedPackage) {
		super(manager, generatedPackage);
	}

	public FhirToHapiTypeConverter(FhirResourceManagerDstu2 manager, ElementDefinitionDt element, String generatedPackage) {
		super(manager, element, generatedPackage);
	}
	
	protected void processElement() {
		processElement(getElement());
		String path = getElement().getPath();
		setRoot(PathUtils.getFirstPathComponent(path));
		setRelativePath(PathUtils.getPathMinusRootComponent(path));
	}
	
	protected void processElement(ElementDefinitionDt elt) {
		processElementDt(elt);
	}
	
	protected void processReference(String code, String profileUri) {
		HapiType hapiType = new HapiType();
		hapiType.setReference(true);
		StructureDefinition profile = getFhirResourceManager().getProfileFromProfileUri(profileUri);
		if(profile.getBase() != null && profile.getBase().equalsIgnoreCase(FhirResourceManagerDstu2.CORE_BASE_RESOURCE_PROFILE)) {
			//It is a base resource profile
			hapiType.setDatatypeClass(HapiFhirUtils.getResourceClass(getFhirContext(), profile.getName()));
		} else {
			throw new RuntimeException("Not implemented yet");
		}
		getHapiTypes().add(hapiType);
	}
	
	protected void processExtension(String code, String extensionUri) {
		FhirExtensionDefinition extensionDef = getFhirResourceManager().getFhirExtension(PathUtils.getExtensionRootPath(extensionUri));
		String extensionName = PathUtils.getExtensionName(extensionUri);
		ElementDefinitionDt extendedElement = extensionDef.getExtensionByNameDstu2(extensionName);
		processElement(extendedElement);
	}

}
