package org.hspc.fhir.model.stu3;

import org.hspc.fhir.model.stu3.IUsCoreAllergyintolerance;
import org.hl7.fhir.dstu3.model.AllergyIntolerance;
import org.hl7.fhir.dstu3.model.Identifier;
import java.util.List;
import org.hl7.fhir.dstu3.model.IdType;
import java.lang.String;
import org.hl7.fhir.dstu3.model.Enumeration;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.DateTimeType;
import java.util.Date;
import org.hl7.fhir.dstu3.model.Reference;
import org.hl7.fhir.dstu3.model.Resource;
import org.hl7.fhir.dstu3.model.Practitioner;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.dstu3.model.Type;
import org.hl7.fhir.dstu3.model.Age;
import org.hl7.fhir.dstu3.model.Period;
import org.hl7.fhir.dstu3.model.Range;
import org.hl7.fhir.dstu3.model.StringType;
import org.hl7.fhir.dstu3.model.CodeType;
import org.hl7.fhir.dstu3.model.RelatedPerson;
import org.hl7.fhir.dstu3.model.Annotation;
import org.hl7.fhir.dstu3.model.UriType;
import org.hl7.fhir.dstu3.model.*;
import ca.uhn.fhir.model.api.ExtensionDt;

public class UsCoreAllergyintoleranceAdapter
		implements
			IUsCoreAllergyintolerance {

	private AllergyIntolerance adaptedClass;

	public UsCoreAllergyintoleranceAdapter() {
		this.adaptedClass = new org.hl7.fhir.dstu3.model.AllergyIntolerance();
	}

	public UsCoreAllergyintoleranceAdapter(
			org.hl7.fhir.dstu3.model.AllergyIntolerance adaptee) {
		this.adaptedClass = adaptee;
	}

	public AllergyIntolerance getAdaptee() {
		return adaptedClass;
	}

	public void setAdaptee(org.hl7.fhir.dstu3.model.AllergyIntolerance param) {
		this.adaptedClass = param;
	}

	public List<Identifier> getIdentifier() {
		try {
			return adaptedClass.getIdentifier();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Identifier", e);
		}
	}

	public IUsCoreAllergyintolerance setIdentifier(
			java.util.List<org.hl7.fhir.dstu3.model.Identifier> param) {
		adaptedClass.setIdentifier(param);
		return this;
	}

	public boolean hasIdentifier() {
		return adaptedClass.hasIdentifier();
	}

	public IUsCoreAllergyintolerance addIdentifier(
			org.hl7.fhir.dstu3.model.Identifier param) {
		adaptedClass.addIdentifier(param);
		return this;
	}

	public Identifier addIdentifier() {
		return adaptedClass.addIdentifier();
	}

	public List<AllergyIntolerance.AllergyIntoleranceReactionComponent> getReaction() {
		try {
			return adaptedClass.getReaction();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Reaction", e);
		}
	}

	public UsCoreAllergyintoleranceAdapter setReaction(
			java.util.List<org.hl7.fhir.dstu3.model.AllergyIntolerance.AllergyIntoleranceReactionComponent> param) {
		adaptedClass.setReaction(param);
		return this;
	}

	public boolean hasReaction() {
		return adaptedClass.hasReaction();
	}

	public UsCoreAllergyintoleranceAdapter addReaction(
			org.hl7.fhir.dstu3.model.AllergyIntolerance.AllergyIntoleranceReactionComponent param) {
		adaptedClass.addReaction(param);
		return this;
	}

	public AllergyIntolerance.AllergyIntoleranceReactionComponent addReaction() {
		return adaptedClass.addReaction();
	}

	public boolean hasCategory() {
		return adaptedClass.hasCategory();
	}

	public boolean hasCategory(
			AllergyIntolerance.AllergyIntoleranceCategory param) {
		return adaptedClass.hasCategory(param);
	}

	public List<Enumeration<AllergyIntolerance.AllergyIntoleranceCategory>> getCategory() {
		try {
			return adaptedClass.getCategory();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Category", e);
		}
	}

	public IUsCoreAllergyintolerance setCategory(
			List<Enumeration<org.hl7.fhir.dstu3.model.AllergyIntolerance.AllergyIntoleranceCategory>> param) {
		adaptedClass.setCategory(param);
		return this;
	}

	public IUsCoreAllergyintolerance addCategory(
			org.hl7.fhir.dstu3.model.AllergyIntolerance.AllergyIntoleranceCategory param) {
		adaptedClass.addCategory(param);
		return this;
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

	public IUsCoreAllergyintolerance setIdElement(
			org.hl7.fhir.dstu3.model.IdType param) {
		adaptedClass.setIdElement(param);
		return this;
	}

	public IUsCoreAllergyintolerance setId(java.lang.String param) {
		adaptedClass.setId(param);
		return this;
	}

	public boolean hasType() {
		return adaptedClass.hasType();
	}

	public boolean hasTypeElement() {
		return adaptedClass.hasTypeElement();
	}

	public AllergyIntolerance.AllergyIntoleranceType getType() {
		try {
			return adaptedClass.getType();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Type", e);
		}
	}

	public Enumeration<AllergyIntolerance.AllergyIntoleranceType> getTypeElement() {
		try {
			return adaptedClass.getTypeElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting TypeElement", e);
		}
	}

	public IUsCoreAllergyintolerance setType(
			org.hl7.fhir.dstu3.model.AllergyIntolerance.AllergyIntoleranceType param) {
		adaptedClass.setType(param);
		return this;
	}

	public IUsCoreAllergyintolerance setTypeElement(
			org.hl7.fhir.dstu3.model.Enumeration<org.hl7.fhir.dstu3.model.AllergyIntolerance.AllergyIntoleranceType> param) {
		adaptedClass.setTypeElement(param);
		return this;
	}

	public CodeableConcept getCode() {
		try {
			return adaptedClass.getCode();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Code", e);
		}
	}

	public IUsCoreAllergyintolerance setCode(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.setCode(param);
		return this;
	}

	public boolean hasCode() {
		return adaptedClass.hasCode();
	}

	public boolean hasAssertedDate() {
		return adaptedClass.hasAssertedDate();
	}

	public boolean hasAssertedDateElement() {
		return adaptedClass.hasAssertedDateElement();
	}

	public DateTimeType getAssertedDateElement() {
		try {
			return adaptedClass.getAssertedDateElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting AssertedDateElement", e);
		}
	}

	public Date getAssertedDate() {
		try {
			return adaptedClass.getAssertedDate();
		} catch (Exception e) {
			throw new RuntimeException("Error getting AssertedDate", e);
		}
	}

	public IUsCoreAllergyintolerance setAssertedDateElement(
			org.hl7.fhir.dstu3.model.DateTimeType param) {
		adaptedClass.setAssertedDateElement(param);
		return this;
	}

	public IUsCoreAllergyintolerance setAssertedDate(java.util.Date param) {
		adaptedClass.setAssertedDate(param);
		return this;
	}

	public boolean hasClinicalStatus() {
		return adaptedClass.hasClinicalStatus();
	}

	public boolean hasClinicalStatusElement() {
		return adaptedClass.hasClinicalStatusElement();
	}

	public AllergyIntolerance.AllergyIntoleranceClinicalStatus getClinicalStatus() {
		try {
			return adaptedClass.getClinicalStatus();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ClinicalStatus", e);
		}
	}

	public Enumeration<AllergyIntolerance.AllergyIntoleranceClinicalStatus> getClinicalStatusElement() {
		try {
			return adaptedClass.getClinicalStatusElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ClinicalStatusElement", e);
		}
	}

	public IUsCoreAllergyintolerance setClinicalStatus(
			org.hl7.fhir.dstu3.model.AllergyIntolerance.AllergyIntoleranceClinicalStatus param) {
		adaptedClass.setClinicalStatus(param);
		return this;
	}

	public IUsCoreAllergyintolerance setClinicalStatusElement(
			org.hl7.fhir.dstu3.model.Enumeration<org.hl7.fhir.dstu3.model.AllergyIntolerance.AllergyIntoleranceClinicalStatus> param) {
		adaptedClass.setClinicalStatusElement(param);
		return this;
	}

	public Reference getRecorder() {
		try {
			return adaptedClass.getRecorder();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Recorder", e);
		}
	}

	public Resource getRecorderTarget() {
		try {
			return adaptedClass.getRecorderTarget();
		} catch (Exception e) {
			throw new RuntimeException("Error getting RecorderTarget", e);
		}
	}

	public boolean hasRecorder() {
		return adaptedClass.hasRecorder();
	}

	public Reference getRecorderPractitioner() {
		try {
			return adaptedClass.getRecorder();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Recorder", e);
		}
	}

	public IUsCoreAllergyintolerance setRecorder(
			org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setRecorder(param);
		return this;
	}

	public Practitioner getRecorderPractitionerTarget() {
		return (org.hl7.fhir.dstu3.model.Practitioner) adaptedClass
				.getRecorderTarget();
	}

	public IUsCoreAllergyintolerance setRecorderTarget(
			org.hl7.fhir.dstu3.model.Practitioner param) {
		adaptedClass.setRecorderTarget(param);
		return this;
	}

	public Reference getRecorderPatient() {
		try {
			return adaptedClass.getRecorder();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Recorder", e);
		}
	}

	public Patient getRecorderPatientTarget() {
		return (org.hl7.fhir.dstu3.model.Patient) adaptedClass
				.getRecorderTarget();
	}

	public IUsCoreAllergyintolerance setRecorderTarget(
			org.hl7.fhir.dstu3.model.Patient param) {
		adaptedClass.setRecorderTarget(param);
		return this;
	}

	public boolean hasCriticality() {
		return adaptedClass.hasCriticality();
	}

	public boolean hasCriticalityElement() {
		return adaptedClass.hasCriticalityElement();
	}

	public AllergyIntolerance.AllergyIntoleranceCriticality getCriticality() {
		try {
			return adaptedClass.getCriticality();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Criticality", e);
		}
	}

	public Enumeration<AllergyIntolerance.AllergyIntoleranceCriticality> getCriticalityElement() {
		try {
			return adaptedClass.getCriticalityElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting CriticalityElement", e);
		}
	}

	public IUsCoreAllergyintolerance setCriticality(
			org.hl7.fhir.dstu3.model.AllergyIntolerance.AllergyIntoleranceCriticality param) {
		adaptedClass.setCriticality(param);
		return this;
	}

	public IUsCoreAllergyintolerance setCriticalityElement(
			org.hl7.fhir.dstu3.model.Enumeration<org.hl7.fhir.dstu3.model.AllergyIntolerance.AllergyIntoleranceCriticality> param) {
		adaptedClass.setCriticalityElement(param);
		return this;
	}

	public List<Resource> getContained() {
		try {
			return adaptedClass.getContained();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Contained", e);
		}
	}

	public IUsCoreAllergyintolerance setContained(
			java.util.List<org.hl7.fhir.dstu3.model.Resource> param) {
		adaptedClass.setContained(param);
		return this;
	}

	public boolean hasContained() {
		return adaptedClass.hasContained();
	}

	public IUsCoreAllergyintolerance addContained(
			org.hl7.fhir.dstu3.model.Resource param) {
		adaptedClass.addContained(param);
		return this;
	}

	public Type getOnset() {
		try {
			return adaptedClass.getOnset();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Onset", e);
		}
	}

	public IUsCoreAllergyintolerance setOnset(
			org.hl7.fhir.dstu3.model.Type param) {
		adaptedClass.setOnset(param);
		return this;
	}

	public DateTimeType getOnsetDateTimeType() {
		try {
			return adaptedClass.getOnsetDateTimeType();
		} catch (Exception e) {
			throw new RuntimeException("Error getting OnsetDateTimeType", e);
		}
	}

	public boolean hasOnsetDateTimeType() {
		return adaptedClass.hasOnsetDateTimeType();
	}

	public Age getOnsetAge() {
		try {
			return adaptedClass.getOnsetAge();
		} catch (Exception e) {
			throw new RuntimeException("Error getting OnsetAge", e);
		}
	}

	public boolean hasOnsetAge() {
		return adaptedClass.hasOnsetAge();
	}

	public Period getOnsetPeriod() {
		try {
			return adaptedClass.getOnsetPeriod();
		} catch (Exception e) {
			throw new RuntimeException("Error getting OnsetPeriod", e);
		}
	}

	public boolean hasOnsetPeriod() {
		return adaptedClass.hasOnsetPeriod();
	}

	public Range getOnsetRange() {
		try {
			return adaptedClass.getOnsetRange();
		} catch (Exception e) {
			throw new RuntimeException("Error getting OnsetRange", e);
		}
	}

	public boolean hasOnsetRange() {
		return adaptedClass.hasOnsetRange();
	}

	public StringType getOnsetStringType() {
		try {
			return adaptedClass.getOnsetStringType();
		} catch (Exception e) {
			throw new RuntimeException("Error getting OnsetStringType", e);
		}
	}

	public boolean hasOnsetStringType() {
		return adaptedClass.hasOnsetStringType();
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

	public IUsCoreAllergyintolerance setLanguageElement(
			org.hl7.fhir.dstu3.model.CodeType param) {
		adaptedClass.setLanguageElement(param);
		return this;
	}

	public IUsCoreAllergyintolerance setLanguage(java.lang.String param) {
		adaptedClass.setLanguage(param);
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

	public IUsCoreAllergyintolerance setPatient(
			org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setPatient(param);
		return this;
	}

	public Patient getPatientTarget() {
		return (org.hl7.fhir.dstu3.model.Patient) adaptedClass
				.getPatientTarget();
	}

	public IUsCoreAllergyintolerance setPatientTarget(
			org.hl7.fhir.dstu3.model.Patient param) {
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

	public IUsCoreAllergyintolerance setPatientAdapterTarget(
			org.hspc.fhir.model.stu3.UsCorePatientAdapter param) {
		adaptedClass.setPatientTarget(param.getAdaptee());
		return this;
	}

	public boolean hasVerificationStatus() {
		return adaptedClass.hasVerificationStatus();
	}

	public boolean hasVerificationStatusElement() {
		return adaptedClass.hasVerificationStatusElement();
	}

	public AllergyIntolerance.AllergyIntoleranceVerificationStatus getVerificationStatus() {
		try {
			return adaptedClass.getVerificationStatus();
		} catch (Exception e) {
			throw new RuntimeException("Error getting VerificationStatus", e);
		}
	}

	public Enumeration<AllergyIntolerance.AllergyIntoleranceVerificationStatus> getVerificationStatusElement() {
		try {
			return adaptedClass.getVerificationStatusElement();
		} catch (Exception e) {
			throw new RuntimeException(
					"Error getting VerificationStatusElement", e);
		}
	}

	public IUsCoreAllergyintolerance setVerificationStatus(
			org.hl7.fhir.dstu3.model.AllergyIntolerance.AllergyIntoleranceVerificationStatus param) {
		adaptedClass.setVerificationStatus(param);
		return this;
	}

	public IUsCoreAllergyintolerance setVerificationStatusElement(
			org.hl7.fhir.dstu3.model.Enumeration<org.hl7.fhir.dstu3.model.AllergyIntolerance.AllergyIntoleranceVerificationStatus> param) {
		adaptedClass.setVerificationStatusElement(param);
		return this;
	}

	public Reference getAsserter() {
		try {
			return adaptedClass.getAsserter();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Asserter", e);
		}
	}

	public Resource getAsserterTarget() {
		try {
			return adaptedClass.getAsserterTarget();
		} catch (Exception e) {
			throw new RuntimeException("Error getting AsserterTarget", e);
		}
	}

	public boolean hasAsserter() {
		return adaptedClass.hasAsserter();
	}

	public Reference getAsserterPatient() {
		try {
			return adaptedClass.getAsserter();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Asserter", e);
		}
	}

	public IUsCoreAllergyintolerance setAsserter(
			org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setAsserter(param);
		return this;
	}

	public Patient getAsserterPatientTarget() {
		return (org.hl7.fhir.dstu3.model.Patient) adaptedClass
				.getAsserterTarget();
	}

	public IUsCoreAllergyintolerance setAsserterTarget(
			org.hl7.fhir.dstu3.model.Patient param) {
		adaptedClass.setAsserterTarget(param);
		return this;
	}

	public Reference getAsserterRelatedPerson() {
		try {
			return adaptedClass.getAsserter();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Asserter", e);
		}
	}

	public RelatedPerson getAsserterRelatedPersonTarget() {
		return (org.hl7.fhir.dstu3.model.RelatedPerson) adaptedClass
				.getAsserterTarget();
	}

	public IUsCoreAllergyintolerance setAsserterTarget(
			org.hl7.fhir.dstu3.model.RelatedPerson param) {
		adaptedClass.setAsserterTarget(param);
		return this;
	}

	public Reference getAsserterPractitioner() {
		try {
			return adaptedClass.getAsserter();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Asserter", e);
		}
	}

	public Practitioner getAsserterPractitionerTarget() {
		return (org.hl7.fhir.dstu3.model.Practitioner) adaptedClass
				.getAsserterTarget();
	}

	public IUsCoreAllergyintolerance setAsserterTarget(
			org.hl7.fhir.dstu3.model.Practitioner param) {
		adaptedClass.setAsserterTarget(param);
		return this;
	}

	public boolean hasLastOccurrence() {
		return adaptedClass.hasLastOccurrence();
	}

	public boolean hasLastOccurrenceElement() {
		return adaptedClass.hasLastOccurrenceElement();
	}

	public DateTimeType getLastOccurrenceElement() {
		try {
			return adaptedClass.getLastOccurrenceElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting LastOccurrenceElement", e);
		}
	}

	public Date getLastOccurrence() {
		try {
			return adaptedClass.getLastOccurrence();
		} catch (Exception e) {
			throw new RuntimeException("Error getting LastOccurrence", e);
		}
	}

	public IUsCoreAllergyintolerance setLastOccurrenceElement(
			org.hl7.fhir.dstu3.model.DateTimeType param) {
		adaptedClass.setLastOccurrenceElement(param);
		return this;
	}

	public IUsCoreAllergyintolerance setLastOccurrence(java.util.Date param) {
		adaptedClass.setLastOccurrence(param);
		return this;
	}

	public List<Annotation> getNote() {
		try {
			return adaptedClass.getNote();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Note", e);
		}
	}

	public IUsCoreAllergyintolerance setNote(
			java.util.List<org.hl7.fhir.dstu3.model.Annotation> param) {
		adaptedClass.setNote(param);
		return this;
	}

	public boolean hasNote() {
		return adaptedClass.hasNote();
	}

	public IUsCoreAllergyintolerance addNote(
			org.hl7.fhir.dstu3.model.Annotation param) {
		adaptedClass.addNote(param);
		return this;
	}

	public Annotation addNote() {
		return adaptedClass.addNote();
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

	public IUsCoreAllergyintolerance setImplicitRulesElement(
			org.hl7.fhir.dstu3.model.UriType param) {
		adaptedClass.setImplicitRulesElement(param);
		return this;
	}

	public IUsCoreAllergyintolerance setImplicitRules(java.lang.String param) {
		adaptedClass.setImplicitRules(param);
		return this;
	}
}