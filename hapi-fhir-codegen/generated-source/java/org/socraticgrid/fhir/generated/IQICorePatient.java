package org.socraticgrid.fhir.generated;

import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.api.ExtensionDt;
import java.util.List;
import ca.uhn.fhir.model.dstu2.composite.AttachmentDt;
import ca.uhn.fhir.model.dstu2.valueset.AdministrativeGenderEnum;
import ca.uhn.fhir.model.primitive.BoundCodeDt;
import org.socraticgrid.fhir.generated.QICorePatientTelecom;
import org.socraticgrid.fhir.generated.QICorePatientAddress;
import org.socraticgrid.fhir.generated.QICorePatientClinicalTrial;
import ca.uhn.fhir.model.primitive.BooleanDt;
import ca.uhn.fhir.model.primitive.DateTimeDt;
import java.util.Date;
import ca.uhn.fhir.model.primitive.DateDt;
import ca.uhn.fhir.model.dstu2.composite.HumanNameDt;
import ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt;
import org.socraticgrid.fhir.generated.QICorePatientNationality;
import ca.uhn.fhir.model.dstu2.composite.NarrativeDt;
import ca.uhn.fhir.model.dstu2.valueset.MaritalStatusCodesEnum;
import ca.uhn.fhir.model.dstu2.composite.BoundCodeableConceptDt;
import ca.uhn.fhir.model.primitive.CodeDt;
import ca.uhn.fhir.model.dstu2.composite.IdentifierDt;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.model.dstu2.composite.AddressDt;
import ca.uhn.fhir.model.dstu2.composite.ContainedDt;
import ca.uhn.fhir.model.primitive.IntegerDt;

public interface IQICorePatient
{

   public Patient getAdaptee();

   public void setAdaptee(Patient param);

   public List<AttachmentDt> getPhoto();

   public IQICorePatient setPhoto(List<AttachmentDt> param);

   public IQICorePatient addPhoto(AttachmentDt param);

   public AttachmentDt addPhoto();

   public List<Patient.Contact> getContact();

   public IQICorePatient setContact(List<Patient.Contact> param);

   public IQICorePatient addContact(Patient.Contact param);

   public Patient.Contact addContact();

   public String getGender();

   public IQICorePatient setGender(String param);

   public BoundCodeDt<AdministrativeGenderEnum> getGenderElement();

   public IQICorePatient setGender(BoundCodeDt<AdministrativeGenderEnum> param);

   public List<QICorePatientTelecom> getTelecom();

   public IQICorePatient setTelecom(List<QICorePatientTelecom> param);

   public IQICorePatient addTelecom(QICorePatientTelecom param);

   public QICorePatientTelecom addTelecom();

   public List<QICorePatientAddress> getAddress();

   public IQICorePatient setAddress(List<QICorePatientAddress> param);

   public IQICorePatient addAddress(QICorePatientAddress param);

   public QICorePatientAddress addAddress();

   public List<QICorePatientClinicalTrial> getClinicalTrial();

   public IQICorePatient setClinicalTrial(
         List<QICorePatientClinicalTrial> param);

   public BooleanDt getDeceasedBooleanElement();

   public Boolean getDeceasedBoolean();

   public IQICorePatient setDeceasedBoolean(BooleanDt param);

   public IQICorePatient setDeceasedBoolean(Boolean param);

   public DateTimeDt getDeceasedDateTimeElement();

   public Date getDeceasedDateTime();

   public IQICorePatient setDeceasedDateTime(DateTimeDt param);

   public IQICorePatient setDeceasedDateTime(Date param);

   public Patient.Animal getAnimal();

   public IQICorePatient setAnimal(Patient.Animal param);

   public QICoreOrganizationAdapter getManagingOrganizationResource();

   public IQICorePatient setManagingOrganizationResource(
         QICoreOrganizationAdapter param);

   public DateDt getBirthDateElement();

   public Date getBirthDate();

   public IQICorePatient setBirthDate(Date param);

   public IQICorePatient setBirthDate(DateDt param);

   public List<Patient.Communication> getCommunication();

   public IQICorePatient setCommunication(List<Patient.Communication> param);

   public IQICorePatient addCommunication(Patient.Communication param);

   public Patient.Communication addCommunication();

   public DateTimeDt getBirthTime();

   public IQICorePatient setBirthTime(DateTimeDt param);

   public List<HumanNameDt> getName();

   public IQICorePatient setName(List<HumanNameDt> param);

   public IQICorePatient addName(HumanNameDt param);

   public HumanNameDt addName();

   public List<CodeableConceptDt> getDisability();

   public IQICorePatient setDisability(List<CodeableConceptDt> param);

   public List<QICorePatientNationality> getNationality();

   public IQICorePatient setNationality(List<QICorePatientNationality> param);

   public List<Patient.Link> getLink();

   public IQICorePatient setLink(List<Patient.Link> param);

   public IQICorePatient addLink(Patient.Link param);

   public Patient.Link addLink();

   public NarrativeDt getText();

   public IQICorePatient setText(NarrativeDt param);

   public BoundCodeableConceptDt<MaritalStatusCodesEnum> getMaritalStatus();

   public IQICorePatient setMaritalStatus(
         BoundCodeableConceptDt<MaritalStatusCodesEnum> param);

   public BooleanDt getCadavericDonor();

   public IQICorePatient setCadavericDonor(BooleanDt param);

   public CodeDt getLanguage();

   public IQICorePatient setLanguage(CodeDt param);

   public CodeableConceptDt getRace();

   public IQICorePatient setRace(CodeableConceptDt param);

   public BooleanDt getActiveElement();

   public Boolean getActive();

   public IQICorePatient setActive(Boolean param);

   public IQICorePatient setActive(BooleanDt param);

   public List<IdentifierDt> getIdentifier();

   public IQICorePatient setIdentifier(List<IdentifierDt> param);

   public IQICorePatient addIdentifier(IdentifierDt param);

   public IdentifierDt addIdentifier();

   public IdDt getId();

   public IQICorePatient setId(IdDt param);

   public AddressDt getBirthPlace();

   public IQICorePatient setBirthPlace(AddressDt param);

   public ContainedDt getContained();

   public IQICorePatient setContained(ContainedDt param);

   public CodeableConceptDt getMilitaryService();

   public IQICorePatient setMilitaryService(CodeableConceptDt param);

   public BooleanDt getMultipleBirthBooleanElement();

   public Boolean getMultipleBirthBoolean();

   public IQICorePatient setMultipleBirthBoolean(BooleanDt param);

   public IQICorePatient setMultipleBirthBoolean(Boolean param);

   public IntegerDt getMultipleBirthIntegerElement();

   public Integer getMultipleBirthInteger();

   public IQICorePatient setMultipleBirthInteger(IntegerDt param);

   public IQICorePatient setMultipleBirthInteger(Integer param);

   public CodeableConceptDt getEthnicity();

   public IQICorePatient setEthnicity(CodeableConceptDt param);

   public CodeableConceptDt getReligion();

   public IQICorePatient setReligion(CodeableConceptDt param);
}