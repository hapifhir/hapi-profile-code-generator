package org.hspc.fhir.model.stu3;

import org.hl7.fhir.dstu3.model.Practitioner;
import org.hl7.fhir.dstu3.model.*;
import ca.uhn.fhir.model.api.ExtensionDt;
import java.util.List;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.Identifier;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.Address;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.Resource;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.Enumeration;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.HumanName;
import org.hl7.fhir.dstu3.model.*;
import java.util.Arrays;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.IdType;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import java.lang.String;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.CodeType;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.Attachment;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.ContactPoint;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.DateType;
import org.hl7.fhir.dstu3.model.*;
import java.util.Date;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.UriType;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.BooleanType;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import java.lang.Boolean;
import org.hl7.fhir.dstu3.model.*;

public interface IUsCorePractitioner {

	public Practitioner getAdaptee();

	public void setAdaptee(org.hl7.fhir.dstu3.model.Practitioner param);

	public List<Identifier> getIdentifier();

	public UsCorePractitionerAdapter setIdentifier(
			java.util.List<org.hl7.fhir.dstu3.model.Identifier> param);

	public boolean hasIdentifier();

	public UsCorePractitionerAdapter addIdentifier(
			org.hl7.fhir.dstu3.model.Identifier param);

	public Identifier addIdentifier();

	public List<Address> getAddress();

	public IUsCorePractitioner setAddress(
			java.util.List<org.hl7.fhir.dstu3.model.Address> param);

	public boolean hasAddress();

	public IUsCorePractitioner addAddress(org.hl7.fhir.dstu3.model.Address param);

	public Address addAddress();

	public List<Practitioner.PractitionerQualificationComponent> getQualification();

	public UsCorePractitionerAdapter setQualification(
			java.util.List<org.hl7.fhir.dstu3.model.Practitioner.PractitionerQualificationComponent> param);

	public boolean hasQualification();

	public UsCorePractitionerAdapter addQualification(
			org.hl7.fhir.dstu3.model.Practitioner.PractitionerQualificationComponent param);

	public Practitioner.PractitionerQualificationComponent addQualification();

	public List<Resource> getContained();

	public IUsCorePractitioner setContained(
			java.util.List<org.hl7.fhir.dstu3.model.Resource> param);

	public boolean hasContained();

	public IUsCorePractitioner addContained(
			org.hl7.fhir.dstu3.model.Resource param);

	public boolean hasGender();

	public boolean hasGenderElement();

	public Enumerations.AdministrativeGender getGender();

	public Enumeration<Enumerations.AdministrativeGender> getGenderElement();

	public IUsCorePractitioner setGender(
			org.hl7.fhir.dstu3.model.Enumerations.AdministrativeGender param);

	public IUsCorePractitioner setGenderElement(
			org.hl7.fhir.dstu3.model.Enumeration<org.hl7.fhir.dstu3.model.Enumerations.AdministrativeGender> param);

	public HumanName getName();

	public UsCorePractitionerAdapter setName(
			org.hl7.fhir.dstu3.model.HumanName param);

	public UsCorePractitionerAdapter addName(
			org.hl7.fhir.dstu3.model.HumanName param);

	public boolean hasName();

	public List<CodeableConcept> getCommunication();

	public IUsCorePractitioner setCommunication(
			java.util.List<org.hl7.fhir.dstu3.model.CodeableConcept> param);

	public boolean hasCommunication();

	public IUsCorePractitioner addCommunication(
			org.hl7.fhir.dstu3.model.CodeableConcept param);

	public CodeableConcept addCommunication();

	public boolean hasId();

	public boolean hasIdElement();

	public IdType getIdElement();

	public String getId();

	public IUsCorePractitioner setIdElement(
			org.hl7.fhir.dstu3.model.IdType param);

	public IUsCorePractitioner setId(java.lang.String param);

	public boolean hasLanguage();

	public boolean hasLanguageElement();

	public CodeType getLanguageElement();

	public String getLanguage();

	public IUsCorePractitioner setLanguageElement(
			org.hl7.fhir.dstu3.model.CodeType param);

	public IUsCorePractitioner setLanguage(java.lang.String param);

	public List<Attachment> getPhoto();

	public IUsCorePractitioner setPhoto(
			java.util.List<org.hl7.fhir.dstu3.model.Attachment> param);

	public boolean hasPhoto();

	public IUsCorePractitioner addPhoto(
			org.hl7.fhir.dstu3.model.Attachment param);

	public Attachment addPhoto();

	public List<ContactPoint> getTelecom();

	public IUsCorePractitioner setTelecom(
			java.util.List<org.hl7.fhir.dstu3.model.ContactPoint> param);

	public boolean hasTelecom();

	public IUsCorePractitioner addTelecom(
			org.hl7.fhir.dstu3.model.ContactPoint param);

	public ContactPoint addTelecom();

	public boolean hasBirthDate();

	public boolean hasBirthDateElement();

	public DateType getBirthDateElement();

	public Date getBirthDate();

	public IUsCorePractitioner setBirthDateElement(
			org.hl7.fhir.dstu3.model.DateType param);

	public IUsCorePractitioner setBirthDate(java.util.Date param);

	public boolean hasImplicitRules();

	public boolean hasImplicitRulesElement();

	public UriType getImplicitRulesElement();

	public String getImplicitRules();

	public IUsCorePractitioner setImplicitRulesElement(
			org.hl7.fhir.dstu3.model.UriType param);

	public IUsCorePractitioner setImplicitRules(java.lang.String param);

	public boolean hasActive();

	public boolean hasActiveElement();

	public BooleanType getActiveElement();

	public Boolean getActive();

	public IUsCorePractitioner setActiveElement(
			org.hl7.fhir.dstu3.model.BooleanType param);

	public IUsCorePractitioner setActive(java.lang.Boolean param);
}