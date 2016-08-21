package ca.uhn.fhir.utils.codegen.hapi;

import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.instance.model.api.IBase;

/**
 * Represents the HAPI FHIR representation for a single FHIR element type.
 * 
 * @author cnanjo
 *
 */
public class HapiType {
	
	private boolean isReference;
	private boolean isBackboneElement;
	private boolean isMultipleCardinality;
	private String fhirType;
	private Class<?> datatype;
	private String generatedType;
	private Class<? extends Enum<?>> enumerationType;
	private String assignedName;
	
	public HapiType() {}
	
	public HapiType(Class<? extends IBase> datatype, Class<? extends Enum<?>> enumerationType) {
		this();
		this.datatype = datatype;
		this.enumerationType = enumerationType;
	}
	
	public void configureFrom(HapiType hapiType) {
		this.datatype = hapiType.getDatatypeClass();
		this.enumerationType = hapiType.getEnumerationTypeClass();
	}
	
	public void setFhirType(String fhirType) {
		this.fhirType = fhirType;
	}
	
	public String getFhirType() {
		return fhirType;
	}
	
	public boolean isReference() {
		return isReference;
	}
	public void setReference(boolean isReference) {
		this.isReference = isReference;
	}
	public boolean isBackboneElement() {
		return isBackboneElement;
	}
	public void setBackboneElement(boolean isBackboneElement) {
		this.isBackboneElement = isBackboneElement;
	}
	public String getDatatype() {
		if(datatype != null) {
			return datatype.getName();
		} else {
			return null;
		}
	}
	public String getDatatypeOrList() {
		if(datatype != null) {
			if(isMultipleCardinality) {
				return "java.util.List<" + datatype.getName() + ">";
			} else {
				return datatype.getName();
			}
		} else {
			return null;
		}
	}
	public Class<?> getDatatypeClass() {
		return datatype;
	}
	public void setDatatypeClass(Class<? extends IBase>  hapiType) {
		this.datatype = hapiType;
	}
	public String getEnumerationType() {
		if(enumerationType != null) {
			return enumerationType.getName();
		} else {
			return null;
		}
	}
	public String getGeneratedType() {
		return generatedType;
	}
	public String getGeneratedTypeOrList() {
		if(generatedType != null) {
			if(isMultipleCardinality) {
				return "java.util.List<" + generatedType + ">";
			} else {
				return generatedType;
			}
		} else {
			return null;
		}
	}

	public void setGeneratedType(String generatedType) {
		this.generatedType = generatedType;
	}
	public Class<? extends Enum<?>> getEnumerationTypeClass() {
		return enumerationType;
	}
	public void setEnumerationType(Class<? extends Enum<?>> enumType) {
		this.enumerationType = enumType;
	}
	public boolean isEnumerationType() {
		return enumerationType != null;
	}
	public String getCodedTypeAsString() {
		String boundType = null;
		if (isEnumerationType()) {
			boundType = getDatatype() + "<" + getEnumerationType() + ">";
		} else {
			boundType = getDatatype();
		}
		return boundType;
	}
	public void setMultipleCardinality(boolean multiple) {
		this.isMultipleCardinality = multiple;
	}
	public boolean isMultipleCardinality() {
		return isMultipleCardinality;
	}
	public String getAssignedName() {
		return assignedName;
	}
	public void setAssignedName(String assignedName) {
		this.assignedName = assignedName;
	}
	public boolean isResource() {
		return this.datatype != null && DomainResource.class.isAssignableFrom(this.datatype);
	}
	public boolean isPrimitive() {
		return this.datatype != null && PrimitiveType.class.isAssignableFrom(this.datatype);
	}
	public boolean isType() {
		return this.datatype != null && Type.class.isAssignableFrom(this.datatype);
	}

	/**
	 * FHIR supports extensible types. For each extensible type, there is a
	 * primitive Java type equivalent. Method returns the primitive java type
	 * corresponding to this FHIR extensible type.
	 *
	 * @param type
	 * @return
	 */
	public String getPrimitiveEquivalent() {
		if(datatype == null) {
			return null;
		} else if(getDatatype().equals(DateTimeType.class.getCanonicalName())) {
			return java.util.Date.class.getName();
		} else if(getDatatype().equals(DateType.class.getCanonicalName())) {
			return  java.util.Date.class.getName();
		} else if(getDatatype().equals(BooleanType.class.getCanonicalName())) {
			return java.lang.Boolean.class.getName();
		} else if(getDatatype().equals(IntegerType.class.getName())) {
			return java.lang.Integer.class.getName();
		} else if(getDatatype().equals(StringType.class.getCanonicalName())) {
			return java.lang.String.class.getName();
		} else if(getDatatype().equals(CodeType.class.getCanonicalName())) {
			return java.lang.String.class.getName();
		} else if(getDatatype().equals(TimeType.class.getCanonicalName())) {
			return java.lang.String.class.getName();
		} else if(getDatatype().equals(InstantType.class.getName())) {
			return java.util.Date.class.getName();
		} else if(getDatatype().equals(PositiveIntType.class.getCanonicalName())) {
			return java.lang.Integer.class.getName();
		} else if(getDatatype().equals(UnsignedIntType.class.getCanonicalName())) {
			return java.lang.Integer.class.getName();
		} else if(getDatatype().equals(UriType.class.getCanonicalName())) {
			return java.lang.String.class.getName();
		} else if(getDatatype().equals(OidType.class.getCanonicalName())) {
			return java.lang.String.class.getName();
		} else if(getDatatype().equals(IdType.class.getCanonicalName())) {
			return java.lang.String.class.getName();
		} else if(getDatatype().equals(CodeType.class.getCanonicalName())) {
			return java.lang.String.class.getName();
		} else {
			return null;
		}
	}

	public String toString() {
		return "Datatype: " + datatype + ", Enumeration Type: " + enumerationType;
	}
}
