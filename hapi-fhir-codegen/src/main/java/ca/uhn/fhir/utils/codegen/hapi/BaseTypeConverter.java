package ca.uhn.fhir.utils.codegen.hapi;

import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.dstu3.model.CodeType;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.Resource;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt;
import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt.Type;
import ca.uhn.fhir.model.primitive.CodeDt;
import ca.uhn.fhir.model.primitive.UriDt;
import ca.uhn.fhir.utils.common.metamodel.Cardinality;
import ca.uhn.fhir.utils.fhir.PathUtils;
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
public abstract class BaseTypeConverter<E,M extends IFhirResourceManager> {

	protected final String resourcePackage;
	private List<HapiType> hapiTypes;
	private M manager;
	private FhirContext ctx;
	private E element;
	private String root;
	private String fullAttributePath;
	private String relativePath;

	/**
	 * Determines if field is an attribute[x] type field in FHIR
	 */
	private boolean isMultiType;

	/**
	 * Determines if field is a Reference(A|B|C) type field in FHIR
	 */
	private boolean isReferenceMultiType;

	private boolean isExtension;
	private Cardinality cardinality;
	private String extensionUri;
	
	public BaseTypeConverter(M manager, String resourcePackage) {
		this.manager = manager;
		this.resourcePackage = resourcePackage;
		this.ctx = manager.getFhirContext();
		this.hapiTypes = new ArrayList<HapiType>();
	}

	public BaseTypeConverter(M manager, E element, String resourcePackage) {
		this(manager, resourcePackage);
		this.element = element;
		processElement();
	}
	
	public E getElement() {
		return element;
	}

	public void setElement(E element) {
		this.element = element;
	}
	
	public M getFhirResourceManager() {
		return manager;
	}

	public void setFhirResourceManager(M manager) {
		this.manager = manager;
	}

	public FhirContext getFhirContext() {
		return ctx;
	}

	public void setFhirContext(FhirContext ctx) {
		this.ctx = ctx;
	}
	
	/**
	 * Returns the element's cardinality
	 * 
	 * @return
	 */
	public Cardinality getCardinality() {
		return cardinality;
	}
	
	/**
	 * Sets the element's cardinality
	 * 
	 * @param cardinality
	 */
	public void setCardinality(Cardinality cardinality) {
		this.cardinality = cardinality;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String base) {
		this.root = base;
	}
	
	public String getFullAttributePath() {
		return fullAttributePath;
	}

	public void setFullAttributePath(String fullAttributePath) {
		this.fullAttributePath = fullAttributePath;
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}
	
	public void setMultiType(boolean isMultiType) {
		this.isMultiType = isMultiType;
	}

	public void setExtension(boolean isExtension) {
		this.isExtension = isExtension;
	}

	public String getExtensionUri() {
		return extensionUri;
	}

	public void setExtensionUri(String extensionUri) {
		this.extensionUri = extensionUri;
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

	public boolean isReferenceMultiType() {
		return isReferenceMultiType;
	}

	public void setReferenceMultiType(boolean referenceMultiType) {
		isReferenceMultiType = referenceMultiType;
	}

	public boolean isExtension() {
		return isExtension;
	}
	
	protected abstract void processElement();
	
	protected abstract void processElement(E elt);
	
	protected abstract void processReference(String code, String profileUri);
	
	protected abstract void processExtension(String code, String profileUri);
	
	/**
	 * For now, shared between DSTU2 and DSTU3 due to common dependency
	 * on ExtensionManager
	 * 
	 * @param elt
	 */
	protected void processElementDt(ElementDefinitionDt elt) {
		if(elt != null && elt.getType() != null && elt.getType().size() > 0) {
			if(elt.getType().size() > 1) {
				setMultiType(true);
			}
			for (Type type : elt.getType()) {
				UriDt uri = type.getProfileFirstRep();
				processType(type.getCode(), (uri != null) ? uri.getValueAsString() : null);
			}
		}
	}
	
	protected void processType(String code, String profileUri) {
		if(getFullAttributePath().contains("race")) {
			System.out.println("Here");
		}
		try {
			if (isSpecialAttribute()) {
				processSpecialAttribute(code, profileUri);
			} else if(code.equalsIgnoreCase("Reference")) {
				processReference(code, profileUri);
			} else if(code.equalsIgnoreCase("Extension")) {
				isExtension = true;
				processExtension(code, profileUri);
			} else if(code.equalsIgnoreCase("BackboneElement")) {
				processBackboneElement(code);
			} else {
				HapiType hapiType = createHapiType(code);
				boolean skip = false;
				if(code.equalsIgnoreCase("CodeableConcept") || code.equalsIgnoreCase("code")) {
					if(root != null && !isExtension()) {
						try {
							System.out.println("Processing: " + root + "." + relativePath);
							hapiType.configureFrom(HapiFhirUtils.resolveBoundedAttributeTypes(ctx, root, relativePath));
						} catch(Exception e) {
							e.printStackTrace();
							skip = true;
							//investigate
						}
					} else {
						if(code.equalsIgnoreCase("CodeableConcept")) {
							hapiType.setDatatypeClass(CodeableConcept.class);
						} else {
							hapiType.setDatatypeClass(CodeType.class);
						}
					}
				} else if(isFhirDatatype(code)) {
					String profileType = PathUtils.getLastResourcePathComponent(profileUri); //Handling cases when specializations of primitive types are specified (e.g., SimpleQuantity)
					if(profileType != null) {
						code = profileType;
					}
					hapiType.setDatatypeClass(HapiFhirUtils.getPrimitiveTypeClass(ctx, code));
				} else if(isGeneratedType(code)) {
					if(getHapiTypes().size() > 0) {
						getHapiType().setGeneratedType(code);
						skip = true;//We are modifying the first type rather than adding a new type for the generated type
					} else {//We are dealing with an extension
						hapiType.setGeneratedType(code);
					}
				} else if(isResource(code)){
					if(code.equalsIgnoreCase("DomainResource")) {
						hapiType.setDatatypeClass(HapiFhirUtils.getResourceClass(ctx, getRoot()));
					} else {
						hapiType.setDatatypeClass(HapiFhirUtils.getResourceClass(ctx, code));
					}
				} else {
					throw new RuntimeException("Not yet implemented");
				}
				if(!skip) {
					hapiTypes.add(hapiType);
				}
			}
		} catch(Exception e) {
			//TODO Add log statement
			e.printStackTrace();
		}
	}
	
	private void processBackboneElement(String fhirType) {
		HapiType hapiType = createHapiType(fhirType);
		hapiType.setBackboneElement(true);
		hapiType.setDatatypeClass(HapiFhirUtils.getStructureTypeClass(ctx, root, relativePath));
		hapiTypes.add(hapiType);
	}
	
	private boolean isFhirDatatype(String code) {
		return (code != null && FhirDatatypeEnum.contains(code));
	}
	
	private boolean isResource(String code) { //TODO Fix to be more robust
		return code !=  null && !isFhirDatatype(code);
	}

	private boolean isGeneratedType(String code) {
		return getFhirResourceManager().generatedTypeExists(code);
	}
	
	protected HapiType createHapiType(String fhirType) {
		HapiType hapiType = new HapiType();
		hapiType.setFhirType(fhirType);
		hapiType.setMultipleCardinality(isMultipleCardinality());
		return hapiType;
	}

	public String getBaseType() {
		String baseType = HapiFhirUtils.getResourceClassName(ctx, getRoot());
		return baseType;
	}
	
	protected boolean isSpecialAttribute() {
		boolean iAmSpecial = false;
		String suffix = PathUtils.getLastPathComponent(fullAttributePath);
		if(suffix.equalsIgnoreCase("contained")) {
			iAmSpecial = true;
		}
		return iAmSpecial;
	}
	
	protected void processSpecialAttribute(String code, String profileUri) {
		String suffix = PathUtils.getLastPathComponent(fullAttributePath);
		if(suffix.equalsIgnoreCase("contained")) {
			HapiType hapiType = createHapiType(code);
			hapiType.setDatatypeClass(Resource.class);
			hapiTypes.add(hapiType);
		} else if(suffix.equalsIgnoreCase("id")) {
			
		}
	}

	/**
	 * Returns the attribute name cleaned up from other notations such
	 * as the FHIR choice notation [x].
	 *
	 * @param attributePath
	 * @return
     */
	public String parseAttributeName() {
		String suffix = PathUtils.getLastPathComponent(fullAttributePath);
		if(PathUtils.isMultivaluedAttribute(suffix)) {
			suffix = PathUtils.cleanMultiValuedAttributeName(suffix);
		}
		if(suffix != null && suffix.equalsIgnoreCase("class")) {
			suffix = suffix + "_"; //Class is a reserved word in java. Note for DSTU2, instead of "_", use "Element" TODO Fix
		}
		return suffix;
	}


	public boolean isMultipleCardinality() {
		return (cardinality == Cardinality.OPTIONAL_MULTIPLE || cardinality == Cardinality.REQUIRED_MULTIPLE);
	}
	
	/**
	 * Translate common cardinalities into a corresponding enumeration type.
	 * 
	 */
	public void assignCardinality(int min, String max) {
		if(min == 1 && max.equals("1")) {
			setCardinality(Cardinality.REQUIRED);
		} else if(min == 0 && max.equals("1")) {
			setCardinality(Cardinality.OPTIONAL);
		} else if(min == 0 && max.equals("0")) {
			setCardinality(Cardinality.CONSTRAINED_OUT);
		} else if(min == 0 && max.equals("*")) {
			setCardinality(Cardinality.OPTIONAL_MULTIPLE);
		} else if(min == 1 && max.equals("*")) {
			setCardinality(Cardinality.REQUIRED_MULTIPLE);
		}
	}
}
