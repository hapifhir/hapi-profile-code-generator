package ca.uhn.fhir.utils.fhir.model.datatype.dstu2;

import java.net.URI;
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
	
	INSTANT("instant", true, java.util.Date.class.getName()),
	TIME("time", true, java.util.Date.class.getName()),
	DATE("date", true, java.util.Date.class.getName()),
	DATE_TIME("dateTime", true, java.util.Date.class.getName()),
	DECIMAL("decimal", true, java.lang.Double.class.getName()),
	BOOLEAN("boolean", true, java.lang.Boolean.class.getName()),
	UNSIGNED_INT("unsignedInt", true, java.lang.Integer.class.getName()),
	POSITIVE_INT("positiveInt", true, java.lang.Integer.class.getName()),
	INTEGER("integer", Arrays.asList(FhirDatatypeEnum.UNSIGNED_INT, FhirDatatypeEnum.POSITIVE_INT), true, java.lang.Integer.class.getName()),
	CODE("code", true, java.lang.String.class.getName()),
	MARKDOWN("markdown", true, java.lang.String.class.getName()),
	ID("id", true, java.lang.String.class.getName()),
	STRING("string", Arrays.asList(FhirDatatypeEnum.CODE, FhirDatatypeEnum.MARKDOWN, FhirDatatypeEnum.ID), true, java.lang.String.class.getName()),
	OID("oid", true, java.net.URI.class.getName()),
	URI("uri", Arrays.asList(FhirDatatypeEnum.OID), true, java.net.URI.class.getName()),
	RATIO("Ratio"),
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
	REFERENCE("reference"),
	EXTENSION("Extension");
	
	/**
	 * The FHIR string value for this datatype
	 */
	private String value;
	
	private boolean isPrimitiveDatatype;
	
	private String javaClassEquivalent;
	
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
		if(children != null) {
			this.children.addAll(children);
		}
	}
	
	
	private FhirDatatypeEnum(String value, List<FhirDatatypeEnum> children, boolean isPrimitiveDatatype, String javaClassEquivalent) {
		this(value, children);
		this.isPrimitiveDatatype = isPrimitiveDatatype;
		this.javaClassEquivalent = javaClassEquivalent;
	}
	
	private FhirDatatypeEnum(String value, boolean isPrimitiveDatatype, String javaClassEquivalent) {
		this(value, null, isPrimitiveDatatype, javaClassEquivalent);
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

	public boolean isPrimitiveDatatype() {
		return isPrimitiveDatatype;
	}

	public void setPrimitiveDatatype(boolean isPrimitiveDatatype) {
		this.isPrimitiveDatatype = isPrimitiveDatatype;
	}

	public String getJavaClassEquivalent() {
		return javaClassEquivalent;
	}

	public void setJavaClassEquivalent(String javaClassEquivalent) {
		this.javaClassEquivalent = javaClassEquivalent;
	}
	
}
