package ca.uhn.fhir.utils.fhir.model.datatype.dstu2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Enumeration for FHIR DSTU 2 datatypes
 * 
 * @author cnanjo
 *
 */
public enum FhirDatatypeEnum {
	
	INSTANT("instant"),
	TIME("time"),
	DATE("date"),
	DATE_TIME("dateTime"),
	DECIMAL("decimal"),
	BOOLEAN("boolean"),
	UNSIGNED_INT("unsignedInt"),
	POSITIVE_INT("positiveInt"),
	INTEGER("integer", Arrays.asList(FhirDatatypeEnum.UNSIGNED_INT, FhirDatatypeEnum.POSITIVE_INT)),
	CODE("code"),
	MARKDOWN("markdown"),
	ID("id"),
	STRING("string", Arrays.asList(FhirDatatypeEnum.CODE, FhirDatatypeEnum.MARKDOWN, FhirDatatypeEnum.ID)),
	OID("oid"),
	URI("uri", Arrays.asList(FhirDatatypeEnum.OID)),
	RATION("Ratio"),
	PERIOD("Period"),
	RANGE("Range"),
	ATTACHMENT("Attachment"),
	IDENTIFIER("Identifier"),
	HUMAN_NAME("HumanName"),
	ANNOTATION("Annotation"),
	ADDRESS("Address"),
	CONTACT_POINT("ContactPoint"),
	SAMPLE_DATA("SampledData"),
	AGE("Age"),
	DISTANCE("Distance"),
	SIMPLE_QUANTITY("SimpleQuantity"),
	DURATION("Duration"),
	COUNT("Count"),
	MONEY("Money"),
	QUANTITY("Quantity", Arrays.asList(FhirDatatypeEnum.AGE, FhirDatatypeEnum.DISTANCE, FhirDatatypeEnum.SIMPLE_QUANTITY, FhirDatatypeEnum.DURATION, FhirDatatypeEnum.COUNT, FhirDatatypeEnum.MONEY)),
	CODEABLE_CONCEPT("CodeableConcept"),
	SIGNATURE("Signature"),
	CODING("Coding"),
	TIMING("Timing"),
	REFERENCE("Reference");
	
	/**
	 * The FHIR string value for this datatype
	 */
	private String value;
	
	/**
	 * Specializations of this datatype if any
	 */
	private List<FhirDatatypeEnum> children;
	
	/**
	 * No-arg constructor
	 */
	private FhirDatatypeEnum() {
		this.children = new ArrayList<>();
	}
	
	/**
	 * Initializes enumeration member with the FHIR string value for 
	 * this datatype.
	 * 
	 * @param value
	 */
	private FhirDatatypeEnum(String value) {
		this();
		this.value = value;
	}
	
	/**
	 * Initializes enumeration member with the FHIR string value for 
	 * this datatype and any specializations thereof.
	 * 
	 * @param value
	 * @param children
	 */
	private FhirDatatypeEnum(String value, List<FhirDatatypeEnum> children) {
		this(value);
		this.children.addAll(children);
	}
	
	/**
	 * Returns the FHIR string value for this datatype. For instance
	 * FhirDatatypeEnum.BOOLEAN will return the string "boolean".
	 */
	public String toString() {
		return this.value;
	}
	
	/**
	 * Method returns true the specified type argument is a
	 * FHIR DSTU2 datatype.
	 * 
	 * @param type
	 * @return
	 */
	public static boolean contains(String type) {
	    for (FhirDatatypeEnum c : FhirDatatypeEnum.values()) {
	        if (c.toString().equals(type)) {
	            return true;
	        }
	    }
	    return false;
	}
	
	/**
	 * Method returns the corresponding enumerated datatype or null if the type
	 * is not in the set of FHIR DSTU2 datatypes.
	 * 
	 * @param type
	 * @return
	 */
	public static FhirDatatypeEnum getEnumeratedDatatype(String type) {
		FhirDatatypeEnum item = null;
		for (FhirDatatypeEnum c : FhirDatatypeEnum.values()) {
	        if (c.toString().equals(type)) {
	            item = c;
	            break;
	        }
	    }
		return item;
	}
	
	/**
	 * Method returns true of the type argument is a specialization of
	 * the given FHIR datatype. For instance, SimpleQuantity is a
	 * specialization of Quantity so FhirDatatypeEnum.QUANTITY.hasSpecialization("SimpleQuantity")
	 * will return 'true'
	 * 
	 * @param type
	 * @return
	 */
	public boolean hasSpecialization(String type) {
	    FhirDatatypeEnum enumType = FhirDatatypeEnum.getEnumeratedDatatype(type);
	    return hasSpecialization(enumType);
	}
	
	public boolean hasSpecialization(FhirDatatypeEnum type) {
	    return children.contains(type);
	}
	
}
