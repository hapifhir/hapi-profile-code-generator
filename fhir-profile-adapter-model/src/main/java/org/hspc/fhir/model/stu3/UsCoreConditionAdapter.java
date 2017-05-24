package org.hspc.fhir.model.stu3;

import org.hspc.fhir.model.stu3.IUsCoreCondition;
import org.hl7.fhir.dstu3.model.Condition;
import org.hl7.fhir.dstu3.model.Identifier;
import java.util.List;
import org.hl7.fhir.dstu3.model.DateTimeType;
import java.util.Date;
import org.hl7.fhir.dstu3.model.Annotation;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.CodeType;
import java.lang.String;
import org.hl7.fhir.dstu3.model.Enumeration;
import org.hl7.fhir.dstu3.model.Reference;
import org.hl7.fhir.dstu3.model.Resource;
import org.hl7.fhir.dstu3.model.Encounter;
import org.hl7.fhir.dstu3.model.EpisodeOfCare;
import org.hl7.fhir.dstu3.model.Practitioner;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.dstu3.model.RelatedPerson;
import org.hl7.fhir.dstu3.model.IdType;
import org.hl7.fhir.dstu3.model.Type;
import org.hl7.fhir.dstu3.model.Age;
import org.hl7.fhir.dstu3.model.Period;
import org.hl7.fhir.dstu3.model.Range;
import org.hl7.fhir.dstu3.model.StringType;
import org.hl7.fhir.dstu3.model.UriType;
import org.hl7.fhir.dstu3.model.BooleanType;
import org.hl7.fhir.dstu3.model.*;
import ca.uhn.fhir.model.api.ExtensionDt;

public class UsCoreConditionAdapter implements IUsCoreCondition {

	private Condition adaptedClass;

	public UsCoreConditionAdapter() {
		this.adaptedClass = new org.hl7.fhir.dstu3.model.Condition();
	}

	public UsCoreConditionAdapter(org.hl7.fhir.dstu3.model.Condition adaptee) {
		this.adaptedClass = adaptee;
	}

	public Condition getAdaptee() {
		return adaptedClass;
	}

	public void setAdaptee(org.hl7.fhir.dstu3.model.Condition param) {
		this.adaptedClass = param;
	}

	public List<Identifier> getIdentifier() {
		try {
			return adaptedClass.getIdentifier();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Identifier", e);
		}
	}

	public IUsCoreCondition setIdentifier(
			java.util.List<org.hl7.fhir.dstu3.model.Identifier> param) {
		adaptedClass.setIdentifier(param);
		return this;
	}

	public boolean hasIdentifier() {
		return adaptedClass.hasIdentifier();
	}

	public IUsCoreCondition addIdentifier(
			org.hl7.fhir.dstu3.model.Identifier param) {
		adaptedClass.addIdentifier(param);
		return this;
	}

	public Identifier addIdentifier() {
		return adaptedClass.addIdentifier();
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

	public IUsCoreCondition setAssertedDateElement(
			org.hl7.fhir.dstu3.model.DateTimeType param) {
		adaptedClass.setAssertedDateElement(param);
		return this;
	}

	public IUsCoreCondition setAssertedDate(java.util.Date param) {
		adaptedClass.setAssertedDate(param);
		return this;
	}

	public List<Annotation> getNote() {
		try {
			return adaptedClass.getNote();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Note", e);
		}
	}

	public IUsCoreCondition setNote(
			java.util.List<org.hl7.fhir.dstu3.model.Annotation> param) {
		adaptedClass.setNote(param);
		return this;
	}

	public boolean hasNote() {
		return adaptedClass.hasNote();
	}

	public IUsCoreCondition addNote(org.hl7.fhir.dstu3.model.Annotation param) {
		adaptedClass.addNote(param);
		return this;
	}

	public Annotation addNote() {
		return adaptedClass.addNote();
	}

	public List<CodeableConcept> getCategory() {
		try {
			return adaptedClass.getCategory();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Category", e);
		}
	}

	public IUsCoreCondition setCategory(
			java.util.List<org.hl7.fhir.dstu3.model.CodeableConcept> param) {
		adaptedClass.setCategory(param);
		return this;
	}

	public boolean hasCategory() {
		return adaptedClass.hasCategory();
	}

	public IUsCoreCondition addCategory(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.addCategory(param);
		return this;
	}

	public CodeableConcept addCategory() {
		return adaptedClass.addCategory();
	}

	public CodeableConcept getCode() {
		try {
			return adaptedClass.getCode();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Code", e);
		}
	}

	public IUsCoreCondition setCode(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.setCode(param);
		return this;
	}

	public boolean hasCode() {
		return adaptedClass.hasCode();
	}

	public List<CodeableConcept> getBodySite() {
		try {
			return adaptedClass.getBodySite();
		} catch (Exception e) {
			throw new RuntimeException("Error getting BodySite", e);
		}
	}

	public IUsCoreCondition setBodySite(
			java.util.List<org.hl7.fhir.dstu3.model.CodeableConcept> param) {
		adaptedClass.setBodySite(param);
		return this;
	}

	public boolean hasBodySite() {
		return adaptedClass.hasBodySite();
	}

	public IUsCoreCondition addBodySite(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.addBodySite(param);
		return this;
	}

	public CodeableConcept addBodySite() {
		return adaptedClass.addBodySite();
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

	public IUsCoreCondition setLanguageElement(
			org.hl7.fhir.dstu3.model.CodeType param) {
		adaptedClass.setLanguageElement(param);
		return this;
	}

	public IUsCoreCondition setLanguage(java.lang.String param) {
		adaptedClass.setLanguage(param);
		return this;
	}

	public Condition.ConditionStageComponent getStage() {
		try {
			return adaptedClass.getStage();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Stage", e);
		}
	}

	public UsCoreConditionAdapter setStage(
			org.hl7.fhir.dstu3.model.Condition.ConditionStageComponent param) {
		adaptedClass.setStage(param);
		return this;
	}

	public boolean hasStage() {
		return adaptedClass.hasStage();
	}

	public List<Condition.ConditionEvidenceComponent> getEvidence() {
		try {
			return adaptedClass.getEvidence();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Evidence", e);
		}
	}

	public UsCoreConditionAdapter setEvidence(
			java.util.List<org.hl7.fhir.dstu3.model.Condition.ConditionEvidenceComponent> param) {
		adaptedClass.setEvidence(param);
		return this;
	}

	public boolean hasEvidence() {
		return adaptedClass.hasEvidence();
	}

	public UsCoreConditionAdapter addEvidence(
			org.hl7.fhir.dstu3.model.Condition.ConditionEvidenceComponent param) {
		adaptedClass.addEvidence(param);
		return this;
	}

	public Condition.ConditionEvidenceComponent addEvidence() {
		return adaptedClass.addEvidence();
	}

	public boolean hasClinicalStatus() {
		return adaptedClass.hasClinicalStatus();
	}

	public boolean hasClinicalStatusElement() {
		return adaptedClass.hasClinicalStatusElement();
	}

	public Condition.ConditionClinicalStatus getClinicalStatus() {
		try {
			return adaptedClass.getClinicalStatus();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ClinicalStatus", e);
		}
	}

	public Enumeration<Condition.ConditionClinicalStatus> getClinicalStatusElement() {
		try {
			return adaptedClass.getClinicalStatusElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ClinicalStatusElement", e);
		}
	}

	public IUsCoreCondition setClinicalStatus(
			org.hl7.fhir.dstu3.model.Condition.ConditionClinicalStatus param) {
		adaptedClass.setClinicalStatus(param);
		return this;
	}

	public IUsCoreCondition setClinicalStatusElement(
			org.hl7.fhir.dstu3.model.Enumeration<org.hl7.fhir.dstu3.model.Condition.ConditionClinicalStatus> param) {
		adaptedClass.setClinicalStatusElement(param);
		return this;
	}

	public Reference getContext() {
		try {
			return adaptedClass.getContext();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Context", e);
		}
	}

	public Resource getContextTarget() {
		try {
			return adaptedClass.getContextTarget();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ContextTarget", e);
		}
	}

	public boolean hasContext() {
		return adaptedClass.hasContext();
	}

	public Reference getContextEncounter() {
		try {
			return adaptedClass.getContext();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Context", e);
		}
	}

	public IUsCoreCondition setContext(org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setContext(param);
		return this;
	}

	public Encounter getContextEncounterTarget() {
		return (org.hl7.fhir.dstu3.model.Encounter) adaptedClass
				.getContextTarget();
	}

	public IUsCoreCondition setContextTarget(
			org.hl7.fhir.dstu3.model.Encounter param) {
		adaptedClass.setContextTarget(param);
		return this;
	}

	public Reference getContextEpisodeOfCare() {
		try {
			return adaptedClass.getContext();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Context", e);
		}
	}

	public EpisodeOfCare getContextEpisodeOfCareTarget() {
		return (org.hl7.fhir.dstu3.model.EpisodeOfCare) adaptedClass
				.getContextTarget();
	}

	public IUsCoreCondition setContextTarget(
			org.hl7.fhir.dstu3.model.EpisodeOfCare param) {
		adaptedClass.setContextTarget(param);
		return this;
	}

	public CodeableConcept getSeverity() {
		try {
			return adaptedClass.getSeverity();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Severity", e);
		}
	}

	public IUsCoreCondition setSeverity(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.setSeverity(param);
		return this;
	}

	public boolean hasSeverity() {
		return adaptedClass.hasSeverity();
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

	public Reference getAsserterPractitioner() {
		try {
			return adaptedClass.getAsserter();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Asserter", e);
		}
	}

	public IUsCoreCondition setAsserter(org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setAsserter(param);
		return this;
	}

	public Practitioner getAsserterPractitionerTarget() {
		return (org.hl7.fhir.dstu3.model.Practitioner) adaptedClass
				.getAsserterTarget();
	}

	public IUsCoreCondition setAsserterTarget(
			org.hl7.fhir.dstu3.model.Practitioner param) {
		adaptedClass.setAsserterTarget(param);
		return this;
	}

	public Reference getAsserterPatient() {
		try {
			return adaptedClass.getAsserter();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Asserter", e);
		}
	}

	public Patient getAsserterPatientTarget() {
		return (org.hl7.fhir.dstu3.model.Patient) adaptedClass
				.getAsserterTarget();
	}

	public IUsCoreCondition setAsserterTarget(
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

	public IUsCoreCondition setAsserterTarget(
			org.hl7.fhir.dstu3.model.RelatedPerson param) {
		adaptedClass.setAsserterTarget(param);
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

	public IUsCoreCondition setIdElement(org.hl7.fhir.dstu3.model.IdType param) {
		adaptedClass.setIdElement(param);
		return this;
	}

	public IUsCoreCondition setId(java.lang.String param) {
		adaptedClass.setId(param);
		return this;
	}

	public Type getOnset() {
		try {
			return adaptedClass.getOnset();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Onset", e);
		}
	}

	public IUsCoreCondition setOnset(org.hl7.fhir.dstu3.model.Type param) {
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

	public List<Resource> getContained() {
		try {
			return adaptedClass.getContained();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Contained", e);
		}
	}

	public IUsCoreCondition setContained(
			java.util.List<org.hl7.fhir.dstu3.model.Resource> param) {
		adaptedClass.setContained(param);
		return this;
	}

	public boolean hasContained() {
		return adaptedClass.hasContained();
	}

	public IUsCoreCondition addContained(org.hl7.fhir.dstu3.model.Resource param) {
		adaptedClass.addContained(param);
		return this;
	}

	public boolean hasVerificationStatus() {
		return adaptedClass.hasVerificationStatus();
	}

	public boolean hasVerificationStatusElement() {
		return adaptedClass.hasVerificationStatusElement();
	}

	public Condition.ConditionVerificationStatus getVerificationStatus() {
		try {
			return adaptedClass.getVerificationStatus();
		} catch (Exception e) {
			throw new RuntimeException("Error getting VerificationStatus", e);
		}
	}

	public Enumeration<Condition.ConditionVerificationStatus> getVerificationStatusElement() {
		try {
			return adaptedClass.getVerificationStatusElement();
		} catch (Exception e) {
			throw new RuntimeException(
					"Error getting VerificationStatusElement", e);
		}
	}

	public IUsCoreCondition setVerificationStatus(
			org.hl7.fhir.dstu3.model.Condition.ConditionVerificationStatus param) {
		adaptedClass.setVerificationStatus(param);
		return this;
	}

	public IUsCoreCondition setVerificationStatusElement(
			org.hl7.fhir.dstu3.model.Enumeration<org.hl7.fhir.dstu3.model.Condition.ConditionVerificationStatus> param) {
		adaptedClass.setVerificationStatusElement(param);
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

	public IUsCoreCondition setImplicitRulesElement(
			org.hl7.fhir.dstu3.model.UriType param) {
		adaptedClass.setImplicitRulesElement(param);
		return this;
	}

	public IUsCoreCondition setImplicitRules(java.lang.String param) {
		adaptedClass.setImplicitRules(param);
		return this;
	}

	public boolean hasSubject() {
		return adaptedClass.hasSubject();
	}

	public Reference getSubject() {
		try {
			return adaptedClass.getSubject();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Subject", e);
		}
	}

	public IUsCoreCondition setSubject(org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setSubject(param);
		return this;
	}

	public Patient getSubjectTarget() {
		return (org.hl7.fhir.dstu3.model.Patient) adaptedClass
				.getSubjectTarget();
	}

	public IUsCoreCondition setSubjectTarget(
			org.hl7.fhir.dstu3.model.Patient param) {
		adaptedClass.setSubjectTarget(param);
		return this;
	}

	public UsCorePatientAdapter getSubjectAdapterTarget() {
		if (adaptedClass.getSubject().getResource() instanceof org.hl7.fhir.dstu3.model.Patient) {
			org.hspc.fhir.model.stu3.UsCorePatientAdapter profiledType = new org.hspc.fhir.model.stu3.UsCorePatientAdapter();
			profiledType
					.setAdaptee((org.hl7.fhir.dstu3.model.Patient) adaptedClass
							.getSubject().getResource());
			return profiledType;
		} else {
			return null;
		}
	}

	public IUsCoreCondition setSubjectAdapterTarget(
			org.hspc.fhir.model.stu3.UsCorePatientAdapter param) {
		adaptedClass.setSubjectTarget(param.getAdaptee());
		return this;
	}

	public Type getAbatement() {
		try {
			return adaptedClass.getAbatement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Abatement", e);
		}
	}

	public IUsCoreCondition setAbatement(org.hl7.fhir.dstu3.model.Type param) {
		adaptedClass.setAbatement(param);
		return this;
	}

	public DateTimeType getAbatementDateTimeType() {
		try {
			return adaptedClass.getAbatementDateTimeType();
		} catch (Exception e) {
			throw new RuntimeException("Error getting AbatementDateTimeType", e);
		}
	}

	public boolean hasAbatementDateTimeType() {
		return adaptedClass.hasAbatementDateTimeType();
	}

	public Age getAbatementAge() {
		try {
			return adaptedClass.getAbatementAge();
		} catch (Exception e) {
			throw new RuntimeException("Error getting AbatementAge", e);
		}
	}

	public boolean hasAbatementAge() {
		return adaptedClass.hasAbatementAge();
	}

	public BooleanType getAbatementBooleanType() {
		try {
			return adaptedClass.getAbatementBooleanType();
		} catch (Exception e) {
			throw new RuntimeException("Error getting AbatementBooleanType", e);
		}
	}

	public boolean hasAbatementBooleanType() {
		return adaptedClass.hasAbatementBooleanType();
	}

	public Period getAbatementPeriod() {
		try {
			return adaptedClass.getAbatementPeriod();
		} catch (Exception e) {
			throw new RuntimeException("Error getting AbatementPeriod", e);
		}
	}

	public boolean hasAbatementPeriod() {
		return adaptedClass.hasAbatementPeriod();
	}

	public Range getAbatementRange() {
		try {
			return adaptedClass.getAbatementRange();
		} catch (Exception e) {
			throw new RuntimeException("Error getting AbatementRange", e);
		}
	}

	public boolean hasAbatementRange() {
		return adaptedClass.hasAbatementRange();
	}

	public StringType getAbatementStringType() {
		try {
			return adaptedClass.getAbatementStringType();
		} catch (Exception e) {
			throw new RuntimeException("Error getting AbatementStringType", e);
		}
	}

	public boolean hasAbatementStringType() {
		return adaptedClass.hasAbatementStringType();
	}
}