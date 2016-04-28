package ca.uhn.fhir.utils.codegen.hapi.dstu3;

import java.util.List;

import org.hl7.fhir.dstu3.model.ElementDefinition;
import org.hl7.fhir.dstu3.model.ElementDefinition.TypeRefComponent;
import org.hl7.fhir.dstu3.model.StructureDefinition;
import org.hl7.fhir.dstu3.model.UriType;
import org.hl7.fhir.instance.model.api.IBaseResource;

import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.utils.codegen.hapi.BaseTypeConverter;
import ca.uhn.fhir.utils.codegen.hapi.HapiFhirUtils;
import ca.uhn.fhir.utils.codegen.hapi.HapiType;
import ca.uhn.fhir.utils.fhir.PathUtils;
import ca.uhn.fhir.utils.fhir.model.FhirExtensionDefinition;

public class FhirToHapiTypeConverter extends BaseTypeConverter<ElementDefinition, FhirResourceManagerDstu3> {
	
	public FhirToHapiTypeConverter(FhirResourceManagerDstu3 manager) {
		super(manager);
	}

	public FhirToHapiTypeConverter(FhirResourceManagerDstu3 manager, ElementDefinition element) {
		super(manager, element);
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
				getHapiTypes().addAll(hapiTypes);
			} if(hasOnlyReferenceTypes(elt)) {
				setReferenceMultiType(true);
			}else
			{
				for(TypeRefComponent type : elt.getType()) {
					UriType uri = null;
					if(type.getProfile() != null && type.getProfile().size() > 0) {
						uri = type.getProfile().get(0);
					}
					processType(type.getCode(), (uri != null)? uri.getValueAsString():null);
				}
			}
		}
	}

	protected boolean hasOnlyReferenceTypes(ElementDefinition elt) {
		boolean onlyReferences = true;
		for(ElementDefinition.TypeRefComponent type : elt.getType()) {
			onlyReferences = type.getCode() != null && type.getCode().equalsIgnoreCase("Reference");
		}
		return onlyReferences;
	}
	
	protected void processReference(String code, String profileUri) {
		HapiType hapiType = createHapiType(code);
		hapiType.setReference(true);
		StructureDefinition profile = getFhirResourceManager().getProfileFromProfileUri(profileUri);
		if(profile != null) {
			String baseType = profile.getBaseType();
			if(baseType != null && baseType.equalsIgnoreCase("DomainResource")) {
				//It is a base resource profile
				hapiType.setDatatypeClass(HapiFhirUtils.getResourceClass(getFhirContext(), profile.getName()));
			} else {
				//TODO For short term, just get the base resource of the profile. In future, get the generated wrapper
				hapiType.setDatatypeClass(HapiFhirUtils.getResourceClass(getFhirContext(), profile.getName()));
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
		ElementDefinitionDt extendedElement = extensionDef.getExtensionByName(extensionName);
		processElementDt(extendedElement);
	}
	
	public void assignCardinality(ElementDefinition element) {
		int min = element.getMin();
		String max = (element.getMax()!= null)?element.getMax():"";
		assignCardinality(min, max);
	}
	
}