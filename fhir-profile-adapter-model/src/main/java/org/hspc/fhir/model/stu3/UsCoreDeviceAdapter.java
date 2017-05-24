package org.hspc.fhir.model.stu3;

import org.hspc.fhir.model.stu3.IUsCoreDevice;
import org.hl7.fhir.dstu3.model.Device;
import org.hl7.fhir.dstu3.model.Annotation;
import java.util.List;
import org.hl7.fhir.dstu3.model.StringType;
import java.lang.String;
import org.hl7.fhir.dstu3.model.Enumeration;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.ContactPoint;
import org.hl7.fhir.dstu3.model.UriType;
import org.hl7.fhir.dstu3.model.Identifier;
import org.hl7.fhir.dstu3.model.IdType;
import org.hl7.fhir.dstu3.model.Reference;
import org.hl7.fhir.dstu3.model.Location;
import org.hl7.fhir.dstu3.model.Resource;
import org.hl7.fhir.dstu3.model.CodeType;
import org.hl7.fhir.dstu3.model.Organization;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.dstu3.model.DateTimeType;
import java.util.Date;
import org.hl7.fhir.dstu3.model.*;
import ca.uhn.fhir.model.api.ExtensionDt;

public class UsCoreDeviceAdapter implements IUsCoreDevice {

	private Device adaptedClass;

	public UsCoreDeviceAdapter() {
		this.adaptedClass = new org.hl7.fhir.dstu3.model.Device();
	}

	public UsCoreDeviceAdapter(org.hl7.fhir.dstu3.model.Device adaptee) {
		this.adaptedClass = adaptee;
	}

	public Device getAdaptee() {
		return adaptedClass;
	}

	public void setAdaptee(org.hl7.fhir.dstu3.model.Device param) {
		this.adaptedClass = param;
	}

	public List<Annotation> getNote() {
		try {
			return adaptedClass.getNote();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Note", e);
		}
	}

	public IUsCoreDevice setNote(
			java.util.List<org.hl7.fhir.dstu3.model.Annotation> param) {
		adaptedClass.setNote(param);
		return this;
	}

	public boolean hasNote() {
		return adaptedClass.hasNote();
	}

	public IUsCoreDevice addNote(org.hl7.fhir.dstu3.model.Annotation param) {
		adaptedClass.addNote(param);
		return this;
	}

	public Annotation addNote() {
		return adaptedClass.addNote();
	}

	public boolean hasManufacturer() {
		return adaptedClass.hasManufacturer();
	}

	public boolean hasManufacturerElement() {
		return adaptedClass.hasManufacturerElement();
	}

	public StringType getManufacturerElement() {
		try {
			return adaptedClass.getManufacturerElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ManufacturerElement", e);
		}
	}

	public String getManufacturer() {
		try {
			return adaptedClass.getManufacturer();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Manufacturer", e);
		}
	}

	public IUsCoreDevice setManufacturerElement(
			org.hl7.fhir.dstu3.model.StringType param) {
		adaptedClass.setManufacturerElement(param);
		return this;
	}

	public IUsCoreDevice setManufacturer(java.lang.String param) {
		adaptedClass.setManufacturer(param);
		return this;
	}

	public Device.DeviceUdiComponent getUdi() {
		try {
			return adaptedClass.getUdi();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Udi", e);
		}
	}

	public UsCoreDeviceAdapter setUdi(
			org.hl7.fhir.dstu3.model.Device.DeviceUdiComponent param) {
		adaptedClass.setUdi(param);
		return this;
	}

	public boolean hasUdi() {
		return adaptedClass.hasUdi();
	}

	public boolean hasStatus() {
		return adaptedClass.hasStatus();
	}

	public boolean hasStatusElement() {
		return adaptedClass.hasStatusElement();
	}

	public Device.FHIRDeviceStatus getStatus() {
		try {
			return adaptedClass.getStatus();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Status", e);
		}
	}

	public Enumeration<Device.FHIRDeviceStatus> getStatusElement() {
		try {
			return adaptedClass.getStatusElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting StatusElement", e);
		}
	}

	public IUsCoreDevice setStatus(
			org.hl7.fhir.dstu3.model.Device.FHIRDeviceStatus param) {
		adaptedClass.setStatus(param);
		return this;
	}

	public IUsCoreDevice setStatusElement(
			org.hl7.fhir.dstu3.model.Enumeration<org.hl7.fhir.dstu3.model.Device.FHIRDeviceStatus> param) {
		adaptedClass.setStatusElement(param);
		return this;
	}

	public CodeableConcept getType() {
		try {
			return adaptedClass.getType();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Type", e);
		}
	}

	public IUsCoreDevice setType(org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.setType(param);
		return this;
	}

	public boolean hasType() {
		return adaptedClass.hasType();
	}

	public List<ContactPoint> getContact() {
		try {
			return adaptedClass.getContact();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Contact", e);
		}
	}

	public IUsCoreDevice setContact(
			java.util.List<org.hl7.fhir.dstu3.model.ContactPoint> param) {
		adaptedClass.setContact(param);
		return this;
	}

	public boolean hasContact() {
		return adaptedClass.hasContact();
	}

	public IUsCoreDevice addContact(org.hl7.fhir.dstu3.model.ContactPoint param) {
		adaptedClass.addContact(param);
		return this;
	}

	public ContactPoint addContact() {
		return adaptedClass.addContact();
	}

	public boolean hasImplicitRules() {
		return adaptedClass.hasImplicitRules();
	}

	public boolean hasImplicitRulesElement() {
		return adaptedClass.hasImplicitRulesElement();
	}

	public UriType getImplicitRulesElement() {
		try {
			return adaptedClass.getImplicitRulesElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ImplicitRulesElement", e);
		}
	}

	public String getImplicitRules() {
		try {
			return adaptedClass.getImplicitRules();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ImplicitRules", e);
		}
	}

	public IUsCoreDevice setImplicitRulesElement(
			org.hl7.fhir.dstu3.model.UriType param) {
		adaptedClass.setImplicitRulesElement(param);
		return this;
	}

	public IUsCoreDevice setImplicitRules(java.lang.String param) {
		adaptedClass.setImplicitRules(param);
		return this;
	}

	public List<CodeableConcept> getSafety() {
		try {
			return adaptedClass.getSafety();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Safety", e);
		}
	}

	public IUsCoreDevice setSafety(
			java.util.List<org.hl7.fhir.dstu3.model.CodeableConcept> param) {
		adaptedClass.setSafety(param);
		return this;
	}

	public boolean hasSafety() {
		return adaptedClass.hasSafety();
	}

	public IUsCoreDevice addSafety(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.addSafety(param);
		return this;
	}

	public CodeableConcept addSafety() {
		return adaptedClass.addSafety();
	}

	public List<Identifier> getIdentifier() {
		try {
			return adaptedClass.getIdentifier();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Identifier", e);
		}
	}

	public IUsCoreDevice setIdentifier(
			java.util.List<org.hl7.fhir.dstu3.model.Identifier> param) {
		adaptedClass.setIdentifier(param);
		return this;
	}

	public boolean hasIdentifier() {
		return adaptedClass.hasIdentifier();
	}

	public IUsCoreDevice addIdentifier(org.hl7.fhir.dstu3.model.Identifier param) {
		adaptedClass.addIdentifier(param);
		return this;
	}

	public Identifier addIdentifier() {
		return adaptedClass.addIdentifier();
	}

	public boolean hasId() {
		return adaptedClass.hasId();
	}

	public boolean hasIdElement() {
		return adaptedClass.hasIdElement();
	}

	public IdType getIdElement() {
		try {
			return adaptedClass.getIdElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting IdElement", e);
		}
	}

	public String getId() {
		try {
			return adaptedClass.getId();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Id", e);
		}
	}

	public IUsCoreDevice setIdElement(org.hl7.fhir.dstu3.model.IdType param) {
		adaptedClass.setIdElement(param);
		return this;
	}

	public IUsCoreDevice setId(java.lang.String param) {
		adaptedClass.setId(param);
		return this;
	}

	public boolean hasLocation() {
		return adaptedClass.hasLocation();
	}

	public Reference getLocation() {
		try {
			return adaptedClass.getLocation();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Location", e);
		}
	}

	public IUsCoreDevice setLocation(org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setLocation(param);
		return this;
	}

	public Location getLocationTarget() {
		return (org.hl7.fhir.dstu3.model.Location) adaptedClass
				.getLocationTarget();
	}

	public IUsCoreDevice setLocationTarget(
			org.hl7.fhir.dstu3.model.Location param) {
		adaptedClass.setLocationTarget(param);
		return this;
	}

	public List<Resource> getContained() {
		try {
			return adaptedClass.getContained();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Contained", e);
		}
	}

	public IUsCoreDevice setContained(
			java.util.List<org.hl7.fhir.dstu3.model.Resource> param) {
		adaptedClass.setContained(param);
		return this;
	}

	public boolean hasContained() {
		return adaptedClass.hasContained();
	}

	public IUsCoreDevice addContained(org.hl7.fhir.dstu3.model.Resource param) {
		adaptedClass.addContained(param);
		return this;
	}

	public boolean hasLanguage() {
		return adaptedClass.hasLanguage();
	}

	public boolean hasLanguageElement() {
		return adaptedClass.hasLanguageElement();
	}

	public CodeType getLanguageElement() {
		try {
			return adaptedClass.getLanguageElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting LanguageElement", e);
		}
	}

	public String getLanguage() {
		try {
			return adaptedClass.getLanguage();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Language", e);
		}
	}

	public IUsCoreDevice setLanguageElement(
			org.hl7.fhir.dstu3.model.CodeType param) {
		adaptedClass.setLanguageElement(param);
		return this;
	}

	public IUsCoreDevice setLanguage(java.lang.String param) {
		adaptedClass.setLanguage(param);
		return this;
	}

	public boolean hasOwner() {
		return adaptedClass.hasOwner();
	}

	public Reference getOwner() {
		try {
			return adaptedClass.getOwner();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Owner", e);
		}
	}

	public IUsCoreDevice setOwner(org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setOwner(param);
		return this;
	}

	public Organization getOwnerTarget() {
		return (org.hl7.fhir.dstu3.model.Organization) adaptedClass
				.getOwnerTarget();
	}

	public IUsCoreDevice setOwnerTarget(
			org.hl7.fhir.dstu3.model.Organization param) {
		adaptedClass.setOwnerTarget(param);
		return this;
	}

	public boolean hasModel() {
		return adaptedClass.hasModel();
	}

	public boolean hasModelElement() {
		return adaptedClass.hasModelElement();
	}

	public StringType getModelElement() {
		try {
			return adaptedClass.getModelElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ModelElement", e);
		}
	}

	public String getModel() {
		try {
			return adaptedClass.getModel();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Model", e);
		}
	}

	public IUsCoreDevice setModelElement(
			org.hl7.fhir.dstu3.model.StringType param) {
		adaptedClass.setModelElement(param);
		return this;
	}

	public IUsCoreDevice setModel(java.lang.String param) {
		adaptedClass.setModel(param);
		return this;
	}

	public boolean hasPatient() {
		return adaptedClass.hasPatient();
	}

	public Reference getPatient() {
		try {
			return adaptedClass.getPatient();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Patient", e);
		}
	}

	public IUsCoreDevice setPatient(org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setPatient(param);
		return this;
	}

	public Patient getPatientTarget() {
		return (org.hl7.fhir.dstu3.model.Patient) adaptedClass
				.getPatientTarget();
	}

	public IUsCoreDevice setPatientTarget(org.hl7.fhir.dstu3.model.Patient param) {
		adaptedClass.setPatientTarget(param);
		return this;
	}

	public UsCorePatientAdapter getPatientAdapterTarget() {
		if (adaptedClass.getPatient().getResource() instanceof org.hl7.fhir.dstu3.model.Patient) {
			org.hspc.fhir.model.stu3.UsCorePatientAdapter profiledType = new org.hspc.fhir.model.stu3.UsCorePatientAdapter();
			profiledType
					.setAdaptee((org.hl7.fhir.dstu3.model.Patient) adaptedClass
							.getPatient().getResource());
			return profiledType;
		} else {
			return null;
		}
	}

	public IUsCoreDevice setPatientAdapterTarget(
			org.hspc.fhir.model.stu3.UsCorePatientAdapter param) {
		adaptedClass.setPatientTarget(param.getAdaptee());
		return this;
	}

	public boolean hasLotNumber() {
		return adaptedClass.hasLotNumber();
	}

	public boolean hasLotNumberElement() {
		return adaptedClass.hasLotNumberElement();
	}

	public StringType getLotNumberElement() {
		try {
			return adaptedClass.getLotNumberElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting LotNumberElement", e);
		}
	}

	public String getLotNumber() {
		try {
			return adaptedClass.getLotNumber();
		} catch (Exception e) {
			throw new RuntimeException("Error getting LotNumber", e);
		}
	}

	public IUsCoreDevice setLotNumberElement(
			org.hl7.fhir.dstu3.model.StringType param) {
		adaptedClass.setLotNumberElement(param);
		return this;
	}

	public IUsCoreDevice setLotNumber(java.lang.String param) {
		adaptedClass.setLotNumber(param);
		return this;
	}

	public boolean hasManufactureDate() {
		return adaptedClass.hasManufactureDate();
	}

	public boolean hasManufactureDateElement() {
		return adaptedClass.hasManufactureDateElement();
	}

	public DateTimeType getManufactureDateElement() {
		try {
			return adaptedClass.getManufactureDateElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ManufactureDateElement",
					e);
		}
	}

	public Date getManufactureDate() {
		try {
			return adaptedClass.getManufactureDate();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ManufactureDate", e);
		}
	}

	public IUsCoreDevice setManufactureDateElement(
			org.hl7.fhir.dstu3.model.DateTimeType param) {
		adaptedClass.setManufactureDateElement(param);
		return this;
	}

	public IUsCoreDevice setManufactureDate(java.util.Date param) {
		adaptedClass.setManufactureDate(param);
		return this;
	}

	public boolean hasUrl() {
		return adaptedClass.hasUrl();
	}

	public boolean hasUrlElement() {
		return adaptedClass.hasUrlElement();
	}

	public UriType getUrlElement() {
		try {
			return adaptedClass.getUrlElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting UrlElement", e);
		}
	}

	public String getUrl() {
		try {
			return adaptedClass.getUrl();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Url", e);
		}
	}

	public IUsCoreDevice setUrlElement(org.hl7.fhir.dstu3.model.UriType param) {
		adaptedClass.setUrlElement(param);
		return this;
	}

	public IUsCoreDevice setUrl(java.lang.String param) {
		adaptedClass.setUrl(param);
		return this;
	}

	public boolean hasExpirationDate() {
		return adaptedClass.hasExpirationDate();
	}

	public boolean hasExpirationDateElement() {
		return adaptedClass.hasExpirationDateElement();
	}

	public DateTimeType getExpirationDateElement() {
		try {
			return adaptedClass.getExpirationDateElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ExpirationDateElement", e);
		}
	}

	public Date getExpirationDate() {
		try {
			return adaptedClass.getExpirationDate();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ExpirationDate", e);
		}
	}

	public IUsCoreDevice setExpirationDateElement(
			org.hl7.fhir.dstu3.model.DateTimeType param) {
		adaptedClass.setExpirationDateElement(param);
		return this;
	}

	public IUsCoreDevice setExpirationDate(java.util.Date param) {
		adaptedClass.setExpirationDate(param);
		return this;
	}

	public boolean hasVersion() {
		return adaptedClass.hasVersion();
	}

	public boolean hasVersionElement() {
		return adaptedClass.hasVersionElement();
	}

	public StringType getVersionElement() {
		try {
			return adaptedClass.getVersionElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting VersionElement", e);
		}
	}

	public String getVersion() {
		try {
			return adaptedClass.getVersion();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Version", e);
		}
	}

	public IUsCoreDevice setVersionElement(
			org.hl7.fhir.dstu3.model.StringType param) {
		adaptedClass.setVersionElement(param);
		return this;
	}

	public IUsCoreDevice setVersion(java.lang.String param) {
		adaptedClass.setVersion(param);
		return this;
	}
}