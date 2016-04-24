package ca.uhn.fhir.utils.codegen.hapi;

import java.util.ArrayList;
import java.util.List;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt;
import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt.Type;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;
import ca.uhn.fhir.model.primitive.CodeDt;
import ca.uhn.fhir.utils.fhir.PathUtils;
import ca.uhn.fhir.utils.fhir.model.FhirExtensionDefinition;
import ca.uhn.fhir.utils.fhir.model.datatype.dstu2.FhirDatatypeEnum;

/**
 * <p>A FHIR Element is a definition for either a single attribute
 * or a group of semantically related attributes that differ in
 * their types.</p>
 * 
 * <p>A semantically related group of attributes has more than one type
 * defined in its element definition OR is an extension which defines
 * more than one type.</p>
 * 
 * @author cnanjo
 *
 */
public final class FhirToHapiTypeConverter {
	
	private List<HapiType> hapiTypes;
	private FhirResourceManager manager;
	private FhirContext ctx;
	private ElementDefinitionDt element;
	private String base;
	private String relativePath;
	private boolean isMultiType;
	private boolean isExtension;
	
	public FhirToHapiTypeConverter(FhirResourceManager manager) {
		this.manager = manager;
		this.ctx = manager.getFhirContext();
		this.hapiTypes = new ArrayList<HapiType>();
	}

	public FhirToHapiTypeConverter(FhirResourceManager manager, ElementDefinitionDt element) {
		this(manager);
		this.element = element;
		processElement();
	}
	
	public HapiType getHapiType() {
		return hapiTypes.get(0);
	}
	
	public List<HapiType> getHapiTypes() {
		return hapiTypes;
	}
	
	public boolean isMultiType() {
		return isMultiType;
	}
	
	public boolean isExtension() {
		return isExtension;
	}
	
	private void processElement() {
		processElement(this.element);
		String path = element.getPath();
		base = PathUtils.getFirstPathComponent(path);
		relativePath =  PathUtils.getPathMinusRootComponent(path);
	}
	
	private void processElement(ElementDefinitionDt elt) {
		if(elt != null && elt.getType() != null && elt.getType().size() > 0) {
			if(elt.getType().size() > 1) {
				isMultiType = true;
			}
			for(Type type : elt.getType()) {
				processType(type);
			}
		}
	}
	
	private void processType(Type type) {
		if(type == null || type.getCode() == null) {
			return;
		}
		String code = type.getCode();
		try {
			if(code.equalsIgnoreCase("Reference")) {
				processReference(type);
			} else if(code.equalsIgnoreCase("Extension")) {
				isExtension = true;
				processExtension(type);
			} else if(code.equalsIgnoreCase("BackboneElement")) {
				processBackboneElement();
			} else {
				HapiType hapiType = new HapiType();
				if(code.equalsIgnoreCase("CodeableConcept") || code.equalsIgnoreCase("code")) {
					if(base != null) {
						hapiType.configureFrom(HapiFhirUtils.resolveBoundedAttributeTypes(ctx, base, relativePath));
					} else {
						if(code.equalsIgnoreCase("CodeableConcept")) {
							hapiType.setDatatypeClass(CodeableConceptDt.class);
						} else {
							hapiType.setDatatypeClass(CodeDt.class);
						}
					}
				} else if(isFhirDatatype(code)) {
					hapiType.setDatatypeClass(HapiFhirUtils.getPrimitiveTypeClass(ctx, code));
				} else if(isResource(code)){
					hapiType.setDatatypeClass(HapiFhirUtils.getResourceClass(ctx, code));
				} else {
					throw new RuntimeException("Not yet implemented");
				}
				hapiTypes.add(hapiType);
			}
		} catch(Exception e) {
			//TODO Add log statement
			e.printStackTrace();
		}
	}
	
	private void processBackboneElement() {
		HapiType hapiType = new HapiType();
		hapiType.setBackboneElement(true);
		hapiType.setDatatypeClass(HapiFhirUtils.getStructureTypeClass(ctx, base, relativePath));
		hapiTypes.add(hapiType);
	}
	
	private void processReference(Type type) {
		HapiType hapiType = new HapiType();
		hapiType.setReference(true);
		String profileUri = null;
		if(type.getProfile() != null && type.getProfile().size() > 0) {
			profileUri = type.getProfileFirstRep().getValueAsString();
		}
		StructureDefinition profile = manager.getProfileFromProfileUri(profileUri);
		if(profile.getBase() != null && profile.getBase().equalsIgnoreCase(FhirResourceManager.CORE_BASE_RESOURCE_PROFILE)) {
			//It is a base resource profile
			hapiType.setDatatypeClass(HapiFhirUtils.getResourceClass(ctx, profile.getName()));
		} else {
			throw new RuntimeException("Not implemented yet");
		}
		hapiTypes.add(hapiType);
	}
	
	private void processExtension(Type type) {
		String extensionUri = type.getProfileFirstRep().getValueAsString();
		FhirExtensionDefinition extensionDef = manager.getFhirExtension(PathUtils.getExtensionRootPath(extensionUri));
		String extensionName = PathUtils.getExtensionName(extensionUri);
		ElementDefinitionDt extendedElement = extensionDef.getExtensionByName(extensionName);
		processElement(extendedElement);
	}
	
	private boolean isFhirDatatype(String code) {
		return (code != null && FhirDatatypeEnum.contains(code));
	}
	
	private boolean isResource(String code) {
		return code !=  null && !isFhirDatatype(code);
	}

}
