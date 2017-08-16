package ca.uhn.fhir.utils.codegen.hapi.dstu3;

import java.util.List;
import java.util.logging.Logger;

import ca.uhn.fhir.utils.codegen.hapi.GenerateLogicalViewCommandBase;
import org.hl7.fhir.dstu3.model.ElementDefinition;
import org.hl7.fhir.dstu3.model.ElementDefinition.TypeRefComponent;
import org.hl7.fhir.dstu3.model.Resource;
import org.hl7.fhir.dstu3.model.StructureDefinition;
import org.hl7.fhir.dstu3.model.UriType;
import org.hl7.fhir.instance.model.api.IBaseResource;

import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.utils.codegen.hapi.BaseTypeConverter;
import ca.uhn.fhir.utils.codegen.hapi.HapiFhirUtils;
import ca.uhn.fhir.utils.codegen.hapi.HapiType;
import ca.uhn.fhir.utils.fhir.PathUtils;
import ca.uhn.fhir.utils.fhir.model.FhirExtensionDefinition;

//import static ca.uhn.fhir.utils.codegen.hapi.CodeGeneratorConfigurator.DEFAULT_GENERATED_CODE_PACKAGE;

public class FhirToHapiTypeConverter extends BaseTypeConverter<ElementDefinition, FhirResourceManagerDstu3> {
	
	public FhirToHapiTypeConverter(FhirResourceManagerDstu3 manager, String resourcePackage) {
		super(manager, resourcePackage);
	}

	public FhirToHapiTypeConverter(FhirResourceManagerDstu3 manager, ElementDefinition element, String resourcePackage) {
		super(manager, element, resourcePackage);
	}
	
	protected void processElement() {
		assignCardinality(getElement());
		String path = getElement().getPath();
		setFullAttributePath(path);
		setRoot(PathUtils.getFirstPathComponent(path));
		setRelativePath(PathUtils.getPathMinusRootComponent(path));
		processElement(getElement());
	}
	
	protected void processElement(ElementDefinition elt) {
		if(elt != null && elt.getType() != null && elt.getType().size() > 0) {
			if(PathUtils.isMultivaluedAttribute(elt.getPath())) {
				setMultiType(true);
				Class<? extends IBaseResource> resourceClass = HapiFhirUtils.getResourceClass(getFhirContext(), getRoot());
				List<HapiType> hapiTypes = HapiFhirUtils.getChoiceTypes(getFhirContext(), resourceClass, getRelativePath());
				updateHapiReferenceTypes(hapiTypes, elt);
				getHapiTypes().addAll(hapiTypes);
			} else if(elt.getType().size() > 1 && hasOnlyReferenceTypes(elt)) {
				setReferenceMultiType(true);
				handleElementTypes(elt);
			} else {
				handleElementTypes(elt);
			}
		} else {
			System.out.println(elt.getPath() + " does not appear to have a code assigned");//TODO Change to DEBUG later on
		}
	}

	private void handleElementTypes(ElementDefinition elt) {
		for(TypeRefComponent type : elt.getType()) {
			String typeProfile = null;
			if(type.getProfile() != null) {
                typeProfile = type.getProfile();
            } else if (type.getTargetProfile() != null) {
				typeProfile = type.getTargetProfile();
			}
			processType(type.getCode(), typeProfile);
        }
	}

	protected boolean hasOnlyReferenceTypes(ElementDefinition elt) {
		boolean onlyReferences = true;
		for(ElementDefinition.TypeRefComponent type : elt.getType()) {
			onlyReferences = type.getCode() != null && type.getCode().equalsIgnoreCase("Reference");
			if (!onlyReferences){
				break;
			}
		}
		return onlyReferences;
	}

	protected boolean hasMultipleReferenceTypes(ElementDefinition elt) {
		boolean moreThanOneReference = false;
		int count = 0;
		for(ElementDefinition.TypeRefComponent type : elt.getType()) {
			if(type.getCode() != null && type.getCode().equalsIgnoreCase("Reference")) {
				++count;
			}
			if (count > 1){
				moreThanOneReference = true;
				break;
			}
		}
		return moreThanOneReference;
	}

	/**
	 * HAPI types looked up using FHIR utils do not have profile URIs associated with reference types.
	 * This method fills in the gaps so downstream processing can handle references appropriately.
	 *
	 * TODO: Implementation of this method is still incomplete and does not handle profiled references.
	 *
	 * @param hapiTypes
	 * @param elt
	 */
	protected void updateHapiReferenceTypes(List<HapiType> hapiTypes, ElementDefinition elt) {
		if(hasMultipleReferenceTypes(elt)) {
			setReferenceMultiType(true);
		}

	}
	
	protected void processReference(String code, String profileUri) {
		HapiType hapiType = createHapiType(code);
		hapiType.setReference(true);
		StructureDefinition profile = getFhirResourceManager().getProfileFromProfileUri(profileUri);
		if(profile != null) {
			String baseType = profile.getBaseDefinition();
			if(baseType != null && baseType.endsWith("DomainResource")) {
				//It is a base resource profile
				hapiType.setDatatypeClass(HapiFhirUtils.getResourceClass(getFhirContext(), profile.getName()));
			} else if(profileUri != null && profileUri.equals("http://hl7.org/fhir/StructureDefinition/Resource")) {
				hapiType.setDatatypeClass(Resource.class);
			} else {
				//TODO Fix to return generated type rather than profile.
				String generatedType = GenerateLogicalViewCommandDstu3.generateAdapterName(profile);
				if(generatedType != null) {
					hapiType.setGeneratedType(resourcePackage + "." + generatedType);
				}
				hapiType.setDatatypeClass(HapiFhirUtils.getResourceClass(getFhirContext(), PathUtils.getLastResourcePathComponent(profile.getBaseDefinition())));
			}
			getHapiTypes().add(hapiType);
		} else {
			throw new RuntimeException("ERROR: No profile found for " + profileUri);
		}
	}
	
	protected void processExtension(String code, String extensionUri) {
		setExtensionUri(extensionUri);
		FhirExtensionDefinition extensionDef = getFhirResourceManager().getFhirExtension(PathUtils.getExtensionRootPath(extensionUri));
		String extensionName = PathUtils.getExtensionName(extensionUri);
		if(extensionDef == null) {
			System.out.println("ExtensionDef is null for " + extensionUri);
		}
		if(extensionUri == null) {
			System.out.println("Extension URI is null");
		}

		ElementDefinition extendedElement = extensionDef.getExtensionByName(extensionName);
		processElement(extendedElement);
	}
	
	public void assignCardinality(ElementDefinition element) {
		int min = element.getMin();
		String max = (element.getMax()!= null)?element.getMax():"";
		assignCardinality(min, max);
	}

}
