package org.hspc.fhir.model.stu3;

import org.hspc.fhir.model.stu3.IUsCorePractitioner;
import org.hl7.fhir.dstu3.model.Practitioner;
import org.hl7.fhir.dstu3.model.Identifier;
import java.util.List;
import org.hl7.fhir.dstu3.model.Address;
import org.hl7.fhir.dstu3.model.Resource;
import org.hl7.fhir.dstu3.model.Enumeration;
import org.hl7.fhir.dstu3.model.HumanName;
import java.util.Arrays;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.IdType;
import java.lang.String;
import org.hl7.fhir.dstu3.model.CodeType;
import org.hl7.fhir.dstu3.model.Attachment;
import org.hl7.fhir.dstu3.model.ContactPoint;
import org.hl7.fhir.dstu3.model.DateType;
import java.util.Date;
import org.hl7.fhir.dstu3.model.UriType;
import org.hl7.fhir.dstu3.model.BooleanType;
import java.lang.Boolean;
import org.hl7.fhir.dstu3.model.*;
import ca.uhn.fhir.model.api.ExtensionDt;

public class UsCorePractitionerAdapter implements IUsCorePractitioner {

	private Practitioner adaptedClass;

	public UsCorePractitionerAdapter() {
		this.adaptedClass = new org.hl7.fhir.dstu3.model.Practitioner();
	}

	public UsCorePractitionerAdapter(
			org.hl7.fhir.dstu3.model.Practitioner adaptee) {
		this.adaptedClass = adaptee;
	}

	public Practitioner getAdaptee() {
		return adaptedClass;
	}

	public void setAdaptee(org.hl7.fhir.dstu3.model.Practitioner param) {
		this.adaptedClass = param;
	}

	public List<Identifier> getIdentifier() {
		try {
			return adaptedClass.getIdentifier();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Identifier", e);
		}
	}

	public UsCorePractitionerAdapter setIdentifier(
			java.util.List<org.hl7.fhir.dstu3.model.Identifier> param) {
		adaptedClass.setIdentifier(param);
		return this;
	}

	public boolean hasIdentifier() {
		return adaptedClass.hasIdentifier();
	}

	public UsCorePractitionerAdapter addIdentifier(
			org.hl7.fhir.dstu3.model.Identifier param) {
		adaptedClass.addIdentifier(param);
		return this;
	}

	public Identifier addIdentifier() {
		return adaptedClass.addIdentifier();
	}

	public List<Address> getAddress() {
		try {
			return adaptedClass.getAddress();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Address", e);
		}
	}

	public IUsCorePractitioner setAddress(
			java.util.List<org.hl7.fhir.dstu3.model.Address> param) {
		adaptedClass.setAddress(param);
		return this;
	}

	public boolean hasAddress() {
		return adaptedClass.hasAddress();
	}

	public IUsCorePractitioner addAddress(org.hl7.fhir.dstu3.model.Address param) {
		adaptedClass.addAddress(param);
		return this;
	}

	public Address addAddress() {
		return adaptedClass.addAddress();
	}

	public List<Practitioner.PractitionerQualificationComponent> getQualification() {
		try {
			return adaptedClass.getQualification();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Qualification", e);
		}
	}

	public UsCorePractitionerAdapter setQualification(
			java.util.List<org.hl7.fhir.dstu3.model.Practitioner.PractitionerQualificationComponent> param) {
		adaptedClass.setQualification(param);
		return this;
	}

	public boolean hasQualification() {
		return adaptedClass.hasQualification();
	}

	public UsCorePractitionerAdapter addQualification(
			org.hl7.fhir.dstu3.model.Practitioner.PractitionerQualificationComponent param) {
		adaptedClass.addQualification(param);
		return this;
	}

	public Practitioner.PractitionerQualificationComponent addQualification() {
		return adaptedClass.addQualification();
	}

	public List<Resource> getContained() {
		try {
			return adaptedClass.getContained();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Contained", e);
		}
	}

	public IUsCorePractitioner setContained(
			java.util.List<org.hl7.fhir.dstu3.model.Resource> param) {
		adaptedClass.setContained(param);
		return this;
	}

	public boolean hasContained() {
		return adaptedClass.hasContained();
	}

	public IUsCorePractitioner addContained(
			org.hl7.fhir.dstu3.model.Resource param) {
		adaptedClass.addContained(param);
		return this;
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

	public IUsCorePractitioner setGender(
			org.hl7.fhir.dstu3.model.Enumerations.AdministrativeGender param) {
		adaptedClass.setGender(param);
		return this;
	}

	public IUsCorePractitioner setGenderElement(
			org.hl7.fhir.dstu3.model.Enumeration<org.hl7.fhir.dstu3.model.Enumerations.AdministrativeGender> param) {
		adaptedClass.setGenderElement(param);
		return this;
	}

	public HumanName getName() {
		List<org.hl7.fhir.dstu3.model.Extension> extensions = adaptedClass
				.getExtensionsByUrl("");
		if (extensions == null || extensions.size() <= 0) {
			return null;
		} else if (extensions.size() == 1) {
			return (org.hl7.fhir.dstu3.model.HumanName) extensions.get(0)
					.getValue();
		} else {
			throw new RuntimeException(
					"More than one extension exists for name");
		}
	}

	public UsCorePractitionerAdapter setName(
			org.hl7.fhir.dstu3.model.HumanName param) {
		adaptedClass.setName(Arrays
				.asList(new org.hl7.fhir.dstu3.model.HumanName[]{param}));
		return this;
	}

	public UsCorePractitionerAdapter addName(
			org.hl7.fhir.dstu3.model.HumanName param) {
		adaptedClass.addName(param);
		return this;
	}

	public boolean hasName() {
		return adaptedClass.hasName();
	}

	public List<CodeableConcept> getCommunication() {
		try {
			return adaptedClass.getCommunication();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Communication", e);
		}
	}

	public IUsCorePractitioner setCommunication(
			java.util.List<org.hl7.fhir.dstu3.model.CodeableConcept> param) {
		adaptedClass.setCommunication(param);
		return this;
	}

	public boolean hasCommunication() {
		return adaptedClass.hasCommunication();
	}

	public IUsCorePractitioner addCommunication(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.addCommunication(param);
		return this;
	}

	public CodeableConcept addCommunication() {
		return adaptedClass.addCommunication();
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

	public IUsCorePractitioner setIdElement(
			org.hl7.fhir.dstu3.model.IdType param) {
		adaptedClass.setIdElement(param);
		return this;
	}

	public IUsCorePractitioner setId(java.lang.String param) {
		adaptedClass.setId(param);
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

	public IUsCorePractitioner setLanguageElement(
			org.hl7.fhir.dstu3.model.CodeType param) {
		adaptedClass.setLanguageElement(param);
		return this;
	}

	public IUsCorePractitioner setLanguage(java.lang.String param) {
		adaptedClass.setLanguage(param);
		return this;
	}

	public List<Attachment> getPhoto() {
		try {
			return adaptedClass.getPhoto();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Photo", e);
		}
	}

	public IUsCorePractitioner setPhoto(
			java.util.List<org.hl7.fhir.dstu3.model.Attachment> param) {
		adaptedClass.setPhoto(param);
		return this;
	}

	public boolean hasPhoto() {
		return adaptedClass.hasPhoto();
	}

	public IUsCorePractitioner addPhoto(
			org.hl7.fhir.dstu3.model.Attachment param) {
		adaptedClass.addPhoto(param);
		return this;
	}

	public Attachment addPhoto() {
		return adaptedClass.addPhoto();
	}

	public List<ContactPoint> getTelecom() {
		try {
			return adaptedClass.getTelecom();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Telecom", e);
		}
	}

	public IUsCorePractitioner setTelecom(
			java.util.List<org.hl7.fhir.dstu3.model.ContactPoint> param) {
		adaptedClass.setTelecom(param);
		return this;
	}

	public boolean hasTelecom() {
		return adaptedClass.hasTelecom();
	}

	public IUsCorePractitioner addTelecom(
			org.hl7.fhir.dstu3.model.ContactPoint param) {
		adaptedClass.addTelecom(param);
		return this;
	}

	public ContactPoint addTelecom() {
		return adaptedClass.addTelecom();
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

	public IUsCorePractitioner setBirthDateElement(
			org.hl7.fhir.dstu3.model.DateType param) {
		adaptedClass.setBirthDateElement(param);
		return this;
	}

	public IUsCorePractitioner setBirthDate(java.util.Date param) {
		adaptedClass.setBirthDate(param);
		return this;
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

	public IUsCorePractitioner setImplicitRulesElement(
			org.hl7.fhir.dstu3.model.UriType param) {
		adaptedClass.setImplicitRulesElement(param);
		return this;
	}

	public IUsCorePractitioner setImplicitRules(java.lang.String param) {
		adaptedClass.setImplicitRules(param);
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

	public IUsCorePractitioner setActiveElement(
			org.hl7.fhir.dstu3.model.BooleanType param) {
		adaptedClass.setActiveElement(param);
		return this;
	}

	public IUsCorePractitioner setActive(java.lang.Boolean param) {
		adaptedClass.setActive(param);
		return this;
	}
}