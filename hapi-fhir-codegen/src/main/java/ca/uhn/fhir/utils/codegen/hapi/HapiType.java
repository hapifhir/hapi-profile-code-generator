package ca.uhn.fhir.utils.codegen.hapi;

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
	private Class<? extends IBase> datatype;
	private Class<? extends Enum<?>> enumerationType;
	
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
	public Class<? extends IBase> getDatatypeClass() {
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
	public String toString() {
		return "Datatype: " + datatype + ", Enumeration Type: " + enumerationType;
	}
}
