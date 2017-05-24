package org.hspc.fhir.model.stu3;

import org.hspc.fhir.model.stu3.IUsCorePatient;
import org.hl7.fhir.dstu3.model.Patient;
import java.util.List;
import org.hl7.fhir.dstu3.model.HumanName;
import org.hl7.fhir.dstu3.model.IdType;
import java.lang.String;
import org.hl7.fhir.dstu3.model.ContactPoint;
import org.hl7.fhir.dstu3.model.Identifier;
import org.hl7.fhir.dstu3.model.DateType;
import java.util.Date;
import org.hl7.fhir.dstu3.model.BooleanType;
import java.lang.Boolean;
import org.hl7.fhir.dstu3.model.Resource;
import org.hl7.fhir.dstu3.model.CodeType;
import org.hl7.fhir.dstu3.model.UriType;
import org.hl7.fhir.dstu3.model.Reference;
import org.hl7.fhir.dstu3.model.Organization;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.Practitioner;
import org.hl7.fhir.dstu3.model.Attachment;
import org.hl7.fhir.dstu3.model.Type;
import org.hl7.fhir.dstu3.model.IntegerType;
import org.hl7.fhir.dstu3.model.Address;
import org.hl7.fhir.dstu3.model.Enumeration;
import org.hl7.fhir.dstu3.model.DateTimeType;
import org.hl7.fhir.dstu3.model.*;
import ca.uhn.fhir.model.api.ExtensionDt;

public class UsCorePatientAdapter implements IUsCorePatient {

	private Patient adaptedClass;

	public UsCorePatientAdapter() {
		this.adaptedClass = new org.hl7.fhir.dstu3.model.Patient();
	}

	public UsCorePatientAdapter(org.hl7.fhir.dstu3.model.Patient adaptee) {
		this.adaptedClass = adaptee;
	}

	public Patient getAdaptee() {
		return adaptedClass;
	}

	public void setAdaptee(org.hl7.fhir.dstu3.model.Patient param) {
		this.adaptedClass = param;
	}

	public List<Patient.ContactComponent> getContact() {
		try {
			return adaptedClass.getContact();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Contact", e);
		}
	}

	public UsCorePatientAdapter setContact(
			java.util.List<org.hl7.fhir.dstu3.model.Patient.ContactComponent> param) {
		adaptedClass.setContact(param);
		return this;
	}

	public boolean hasContact() {
		return adaptedClass.hasContact();
	}

	public UsCorePatientAdapter addContact(
			org.hl7.fhir.dstu3.model.Patient.ContactComponent param) {
		adaptedClass.addContact(param);
		return this;
	}

	public Patient.ContactComponent addContact() {
		return adaptedClass.addContact();
	}

	public List<HumanName> getName() {
		try {
			return adaptedClass.getName();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Name", e);
		}
	}

	public UsCorePatientAdapter setName(
			java.util.List<org.hl7.fhir.dstu3.model.HumanName> param) {
		adaptedClass.setName(param);
		return this;
	}

	public boolean hasName() {
		return adaptedClass.hasName();
	}

	public UsCorePatientAdapter addName(org.hl7.fhir.dstu3.model.HumanName param) {
		adaptedClass.addName(param);
		return this;
	}

	public HumanName addName() {
		return adaptedClass.addName();
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

	public IUsCorePatient setIdElement(org.hl7.fhir.dstu3.model.IdType param) {
		adaptedClass.setIdElement(param);
		return this;
	}

	public IUsCorePatient setId(java.lang.String param) {
		adaptedClass.setId(param);
		return this;
	}

	public List<ContactPoint> getTelecom() {
		try {
			return adaptedClass.getTelecom();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Telecom", e);
		}
	}

	public IUsCorePatient setTelecom(
			java.util.List<org.hl7.fhir.dstu3.model.ContactPoint> param) {
		adaptedClass.setTelecom(param);
		return this;
	}

	public boolean hasTelecom() {
		return adaptedClass.hasTelecom();
	}

	public IUsCorePatient addTelecom(org.hl7.fhir.dstu3.model.ContactPoint param) {
		adaptedClass.addTelecom(param);
		return this;
	}

	public ContactPoint addTelecom() {
		return adaptedClass.addTelecom();
	}

	public List<Patient.PatientLinkComponent> getLink() {
		try {
			return adaptedClass.getLink();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Link", e);
		}
	}

	public UsCorePatientAdapter setLink(
			java.util.List<org.hl7.fhir.dstu3.model.Patient.PatientLinkComponent> param) {
		adaptedClass.setLink(param);
		return this;
	}

	public boolean hasLink() {
		return adaptedClass.hasLink();
	}

	public UsCorePatientAdapter addLink(
			org.hl7.fhir.dstu3.model.Patient.PatientLinkComponent param) {
		adaptedClass.addLink(param);
		return this;
	}

	public Patient.PatientLinkComponent addLink() {
		return adaptedClass.addLink();
	}

	public List<Identifier> getIdentifier() {
		try {
			return adaptedClass.getIdentifier();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Identifier", e);
		}
	}

	public UsCorePatientAdapter setIdentifier(
			java.util.List<org.hl7.fhir.dstu3.model.Identifier> param) {
		adaptedClass.setIdentifier(param);
		return this;
	}

	public boolean hasIdentifier() {
		return adaptedClass.hasIdentifier();
	}

	public UsCorePatientAdapter addIdentifier(
			org.hl7.fhir.dstu3.model.Identifier param) {
		adaptedClass.addIdentifier(param);
		return this;
	}

	public Identifier addIdentifier() {
		return adaptedClass.addIdentifier();
	}

	public boolean hasBirthDate() {
		return adaptedClass.hasBirthDate();
	}

	public boolean hasBirthDateElement() {
		return adaptedClass.hasBirthDateElement();
	}

	public DateType getBirthDateElement() {
		try {
			return adaptedClass.getBirthDateElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting BirthDateElement", e);
		}
	}

	public Date getBirthDate() {
		try {
			return adaptedClass.getBirthDate();
		} catch (Exception e) {
			throw new RuntimeException("Error getting BirthDate", e);
		}
	}

	public IUsCorePatient setBirthDateElement(
			org.hl7.fhir.dstu3.model.DateType param) {
		adaptedClass.setBirthDateElement(param);
		return this;
	}

	public IUsCorePatient setBirthDate(java.util.Date param) {
		adaptedClass.setBirthDate(param);
		return this;
	}

	public boolean hasActive() {
		return adaptedClass.hasActive();
	}

	public boolean hasActiveElement() {
		return adaptedClass.hasActiveElement();
	}

	public BooleanType getActiveElement() {
		try {
			return adaptedClass.getActiveElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ActiveElement", e);
		}
	}

	public Boolean getActive() {
		try {
			return adaptedClass.getActive();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Active", e);
		}
	}

	public IUsCorePatient setActiveElement(
			org.hl7.fhir.dstu3.model.BooleanType param) {
		adaptedClass.setActiveElement(param);
		return this;
	}

	public IUsCorePatient setActive(java.lang.Boolean param) {
		adaptedClass.setActive(param);
		return this;
	}

	public List<Resource> getContained() {
		try {
			return adaptedClass.getContained();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Contained", e);
		}
	}

	public IUsCorePatient setContained(
			java.util.List<org.hl7.fhir.dstu3.model.Resource> param) {
		adaptedClass.setContained(param);
		return this;
	}

	public boolean hasContained() {
		return adaptedClass.hasContained();
	}

	public IUsCorePatient addContained(org.hl7.fhir.dstu3.model.Resource param) {
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

	public IUsCorePatient setLanguageElement(
			org.hl7.fhir.dstu3.model.CodeType param) {
		adaptedClass.setLanguageElement(param);
		return this;
	}

	public IUsCorePatient setLanguage(java.lang.String param) {
		adaptedClass.setLanguage(param);
		return this;
	}

	public List<Patient.PatientCommunicationComponent> getCommunication() {
		try {
			return adaptedClass.getCommunication();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Communication", e);
		}
	}

	public UsCorePatientAdapter setCommunication(
			java.util.List<org.hl7.fhir.dstu3.model.Patient.PatientCommunicationComponent> param) {
		adaptedClass.setCommunication(param);
		return this;
	}

	public boolean hasCommunication() {
		return adaptedClass.hasCommunication();
	}

	public UsCorePatientAdapter addCommunication(
			org.hl7.fhir.dstu3.model.Patient.PatientCommunicationComponent param) {
		adaptedClass.addCommunication(param);
		return this;
	}

	public Patient.PatientCommunicationComponent addCommunication() {
		return adaptedClass.addCommunication();
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

	public IUsCorePatient setImplicitRulesElement(
			org.hl7.fhir.dstu3.model.UriType param) {
		adaptedClass.setImplicitRulesElement(param);
		return this;
	}

	public IUsCorePatient setImplicitRules(java.lang.String param) {
		adaptedClass.setImplicitRules(param);
		return this;
	}

	public boolean hasManagingOrganization() {
		return adaptedClass.hasManagingOrganization();
	}

	public Reference getManagingOrganization() {
		try {
			return adaptedClass.getManagingOrganization();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ManagingOrganization", e);
		}
	}

	public IUsCorePatient setManagingOrganization(
			org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setManagingOrganization(param);
		return this;
	}

	public Organization getManagingOrganizationTarget() {
		return (org.hl7.fhir.dstu3.model.Organization) adaptedClass
				.getManagingOrganizationTarget();
	}

	public IUsCorePatient setManagingOrganizationTarget(
			org.hl7.fhir.dstu3.model.Organization param) {
		adaptedClass.setManagingOrganizationTarget(param);
		return this;
	}

	public CodeableConcept getMaritalStatus() {
		try {
			return adaptedClass.getMaritalStatus();
		} catch (Exception e) {
			throw new RuntimeException("Error getting MaritalStatus", e);
		}
	}

	public IUsCorePatient setMaritalStatus(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.setMaritalStatus(param);
		return this;
	}

	public boolean hasMaritalStatus() {
		return adaptedClass.hasMaritalStatus();
	}

	public boolean hasGeneralPractitioner() {
		return adaptedClass.hasGeneralPractitioner();
	}

	public List<Organization> getGeneralPractitionerOrganizationTarget() {
		List<org.hl7.fhir.dstu3.model.Organization> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getGeneralPractitionerTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.Organization) resource);
		}
		return items;
	}

	public List<Reference> getGeneralPractitioner() {
		try {
			return adaptedClass.getGeneralPractitioner();
		} catch (Exception e) {
			throw new RuntimeException("Error getting GeneralPractitioner", e);
		}
	}

	public List<Practitioner> getGeneralPractitionerPractitionerTarget() {
		List<org.hl7.fhir.dstu3.model.Practitioner> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getGeneralPractitionerTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.Practitioner) resource);
		}
		return items;
	}

	public List<Attachment> getPhoto() {
		try {
			return adaptedClass.getPhoto();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Photo", e);
		}
	}

	public IUsCorePatient setPhoto(
			java.util.List<org.hl7.fhir.dstu3.model.Attachment> param) {
		adaptedClass.setPhoto(param);
		return this;
	}

	public boolean hasPhoto() {
		return adaptedClass.hasPhoto();
	}

	public IUsCorePatient addPhoto(org.hl7.fhir.dstu3.model.Attachment param) {
		adaptedClass.addPhoto(param);
		return this;
	}

	public Attachment addPhoto() {
		return adaptedClass.addPhoto();
	}

	public Type getMultipleBirth() {
		try {
			return adaptedClass.getMultipleBirth();
		} catch (Exception e) {
			throw new RuntimeException("Error getting MultipleBirth", e);
		}
	}

	public IUsCorePatient setMultipleBirth(org.hl7.fhir.dstu3.model.Type param) {
		adaptedClass.setMultipleBirth(param);
		return this;
	}

	public BooleanType getMultipleBirthBooleanType() {
		try {
			return adaptedClass.getMultipleBirthBooleanType();
		} catch (Exception e) {
			throw new RuntimeException(
					"Error getting MultipleBirthBooleanType", e);
		}
	}

	public boolean hasMultipleBirthBooleanType() {
		return adaptedClass.hasMultipleBirthBooleanType();
	}

	public IntegerType getMultipleBirthIntegerType() {
		try {
			return adaptedClass.getMultipleBirthIntegerType();
		} catch (Exception e) {
			throw new RuntimeException(
					"Error getting MultipleBirthIntegerType", e);
		}
	}

	public boolean hasMultipleBirthIntegerType() {
		return adaptedClass.hasMultipleBirthIntegerType();
	}

	public List<Address> getAddress() {
		try {
			return adaptedClass.getAddress();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Address", e);
		}
	}

	public IUsCorePatient setAddress(
			java.util.List<org.hl7.fhir.dstu3.model.Address> param) {
		adaptedClass.setAddress(param);
		return this;
	}

	public boolean hasAddress() {
		return adaptedClass.hasAddress();
	}

	public IUsCorePatient addAddress(org.hl7.fhir.dstu3.model.Address param) {
		adaptedClass.addAddress(param);
		return this;
	}

	public Address addAddress() {
		return adaptedClass.addAddress();
	}

	public boolean hasGender() {
		return adaptedClass.hasGender();
	}

	public boolean hasGenderElement() {
		return adaptedClass.hasGenderElement();
	}

	public Enumerations.AdministrativeGender getGender() {
		try {
			return adaptedClass.getGender();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Gender", e);
		}
	}

	public Enumeration<Enumerations.AdministrativeGender> getGenderElement() {
		try {
			return adaptedClass.getGenderElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting GenderElement", e);
		}
	}

	public IUsCorePatient setGender(
			org.hl7.fhir.dstu3.model.Enumerations.AdministrativeGender param) {
		adaptedClass.setGender(param);
		return this;
	}

	public IUsCorePatient setGenderElement(
			org.hl7.fhir.dstu3.model.Enumeration<org.hl7.fhir.dstu3.model.Enumerations.AdministrativeGender> param) {
		adaptedClass.setGenderElement(param);
		return this;
	}

	public Type getDeceased() {
		try {
			return adaptedClass.getDeceased();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Deceased", e);
		}
	}

	public IUsCorePatient setDeceased(org.hl7.fhir.dstu3.model.Type param) {
		adaptedClass.setDeceased(param);
		return this;
	}

	public BooleanType getDeceasedBooleanType() {
		try {
			return adaptedClass.getDeceasedBooleanType();
		} catch (Exception e) {
			throw new RuntimeException("Error getting DeceasedBooleanType", e);
		}
	}

	public boolean hasDeceasedBooleanType() {
		return adaptedClass.hasDeceasedBooleanType();
	}

	public DateTimeType getDeceasedDateTimeType() {
		try {
			return adaptedClass.getDeceasedDateTimeType();
		} catch (Exception e) {
			throw new RuntimeException("Error getting DeceasedDateTimeType", e);
		}
	}

	public boolean hasDeceasedDateTimeType() {
		return adaptedClass.hasDeceasedDateTimeType();
	}
}