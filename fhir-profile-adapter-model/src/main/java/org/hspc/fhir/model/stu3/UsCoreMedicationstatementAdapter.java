package org.hspc.fhir.model.stu3;

import org.hspc.fhir.model.stu3.IUsCoreMedicationstatement;
import org.hl7.fhir.dstu3.model.MedicationStatement;
import org.hl7.fhir.dstu3.model.Reference;
import org.hl7.fhir.dstu3.model.Patient;
import java.util.List;
import org.hl7.fhir.dstu3.model.Condition;
import org.hl7.fhir.dstu3.model.Observation;
import org.hl7.fhir.dstu3.model.Resource;
import org.hl7.fhir.dstu3.model.Type;
import org.hl7.fhir.dstu3.model.DateTimeType;
import org.hl7.fhir.dstu3.model.Period;
import java.util.Date;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.MedicationRequest;
import org.hl7.fhir.dstu3.model.CarePlan;
import org.hl7.fhir.dstu3.model.ProcedureRequest;
import org.hl7.fhir.dstu3.model.ReferralRequest;
import org.hl7.fhir.dstu3.model.Encounter;
import org.hl7.fhir.dstu3.model.EpisodeOfCare;
import org.hl7.fhir.dstu3.model.MedicationAdministration;
import org.hl7.fhir.dstu3.model.MedicationDispense;
import org.hl7.fhir.dstu3.model.Procedure;
import org.hl7.fhir.dstu3.model.Practitioner;
import org.hl7.fhir.dstu3.model.RelatedPerson;
import org.hl7.fhir.dstu3.model.Organization;
import org.hl7.fhir.dstu3.model.Enumeration;
import org.hl7.fhir.dstu3.model.Medication;
import org.hl7.fhir.dstu3.model.IdType;
import java.lang.String;
import org.hl7.fhir.dstu3.model.Identifier;
import org.hl7.fhir.dstu3.model.UriType;
import org.hl7.fhir.dstu3.model.CodeType;
import org.hl7.fhir.dstu3.model.Annotation;
import org.hl7.fhir.dstu3.model.*;
import ca.uhn.fhir.model.api.ExtensionDt;

public class UsCoreMedicationstatementAdapter
		implements
			IUsCoreMedicationstatement {

	private MedicationStatement adaptedClass;

	public UsCoreMedicationstatementAdapter() {
		this.adaptedClass = new org.hl7.fhir.dstu3.model.MedicationStatement();
	}

	public UsCoreMedicationstatementAdapter(
			org.hl7.fhir.dstu3.model.MedicationStatement adaptee) {
		this.adaptedClass = adaptee;
	}

	public MedicationStatement getAdaptee() {
		return adaptedClass;
	}

	public void setAdaptee(org.hl7.fhir.dstu3.model.MedicationStatement param) {
		this.adaptedClass = param;
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

	public IUsCoreMedicationstatement setSubject(
			org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setSubject(param);
		return this;
	}

	public Patient getSubjectTarget() {
		return (org.hl7.fhir.dstu3.model.Patient) adaptedClass
				.getSubjectTarget();
	}

	public IUsCoreMedicationstatement setSubjectTarget(
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

	public IUsCoreMedicationstatement setSubjectAdapterTarget(
			org.hspc.fhir.model.stu3.UsCorePatientAdapter param) {
		adaptedClass.setSubjectTarget(param.getAdaptee());
		return this;
	}

	public boolean hasReasonReference() {
		return adaptedClass.hasReasonReference();
	}

	public List<Condition> getReasonReferenceConditionTarget() {
		List<org.hl7.fhir.dstu3.model.Condition> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getReasonReferenceTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.Condition) resource);
		}
		return items;
	}

	public List<Reference> getReasonReference() {
		try {
			return adaptedClass.getReasonReference();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ReasonReference", e);
		}
	}

	public List<Observation> getReasonReferenceObservationTarget() {
		List<org.hl7.fhir.dstu3.model.Observation> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getReasonReferenceTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.Observation) resource);
		}
		return items;
	}

	public boolean hasDerivedFrom() {
		return adaptedClass.hasDerivedFrom();
	}

	public List<Reference> getDerivedFrom() {
		try {
			return adaptedClass.getDerivedFrom();
		} catch (Exception e) {
			throw new RuntimeException("Error getting DerivedFrom", e);
		}
	}

	public List<Resource> getContained() {
		try {
			return adaptedClass.getContained();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Contained", e);
		}
	}

	public IUsCoreMedicationstatement setContained(
			java.util.List<org.hl7.fhir.dstu3.model.Resource> param) {
		adaptedClass.setContained(param);
		return this;
	}

	public boolean hasContained() {
		return adaptedClass.hasContained();
	}

	public IUsCoreMedicationstatement addContained(
			org.hl7.fhir.dstu3.model.Resource param) {
		adaptedClass.addContained(param);
		return this;
	}

	public Type getEffective() {
		try {
			return adaptedClass.getEffective();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Effective", e);
		}
	}

	public IUsCoreMedicationstatement setEffective(
			org.hl7.fhir.dstu3.model.Type param) {
		adaptedClass.setEffective(param);
		return this;
	}

	public DateTimeType getEffectiveDateTimeType() {
		try {
			return adaptedClass.getEffectiveDateTimeType();
		} catch (Exception e) {
			throw new RuntimeException("Error getting EffectiveDateTimeType", e);
		}
	}

	public boolean hasEffectiveDateTimeType() {
		return adaptedClass.hasEffectiveDateTimeType();
	}

	public Period getEffectivePeriod() {
		try {
			return adaptedClass.getEffectivePeriod();
		} catch (Exception e) {
			throw new RuntimeException("Error getting EffectivePeriod", e);
		}
	}

	public boolean hasEffectivePeriod() {
		return adaptedClass.hasEffectivePeriod();
	}

	public boolean hasDateAsserted() {
		return adaptedClass.hasDateAsserted();
	}

	public boolean hasDateAssertedElement() {
		return adaptedClass.hasDateAssertedElement();
	}

	public DateTimeType getDateAssertedElement() {
		try {
			return adaptedClass.getDateAssertedElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting DateAssertedElement", e);
		}
	}

	public Date getDateAsserted() {
		try {
			return adaptedClass.getDateAsserted();
		} catch (Exception e) {
			throw new RuntimeException("Error getting DateAsserted", e);
		}
	}

	public IUsCoreMedicationstatement setDateAssertedElement(
			org.hl7.fhir.dstu3.model.DateTimeType param) {
		adaptedClass.setDateAssertedElement(param);
		return this;
	}

	public IUsCoreMedicationstatement setDateAsserted(java.util.Date param) {
		adaptedClass.setDateAsserted(param);
		return this;
	}

	public CodeableConcept getCategory() {
		try {
			return adaptedClass.getCategory();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Category", e);
		}
	}

	public IUsCoreMedicationstatement setCategory(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.setCategory(param);
		return this;
	}

	public boolean hasCategory() {
		return adaptedClass.hasCategory();
	}

	public boolean hasBasedOn() {
		return adaptedClass.hasBasedOn();
	}

	public List<MedicationRequest> getBasedOnMedicationRequestTarget() {
		List<org.hl7.fhir.dstu3.model.MedicationRequest> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getBasedOnTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.MedicationRequest) resource);
		}
		return items;
	}

	public List<Reference> getBasedOn() {
		try {
			return adaptedClass.getBasedOn();
		} catch (Exception e) {
			throw new RuntimeException("Error getting BasedOn", e);
		}
	}

	public List<CarePlan> getBasedOnCarePlanTarget() {
		List<org.hl7.fhir.dstu3.model.CarePlan> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getBasedOnTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.CarePlan) resource);
		}
		return items;
	}

	public List<ProcedureRequest> getBasedOnProcedureRequestTarget() {
		List<org.hl7.fhir.dstu3.model.ProcedureRequest> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getBasedOnTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.ProcedureRequest) resource);
		}
		return items;
	}

	public List<ReferralRequest> getBasedOnReferralRequestTarget() {
		List<org.hl7.fhir.dstu3.model.ReferralRequest> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getBasedOnTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.ReferralRequest) resource);
		}
		return items;
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

	public IUsCoreMedicationstatement setContext(
			org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setContext(param);
		return this;
	}

	public Encounter getContextEncounterTarget() {
		return (org.hl7.fhir.dstu3.model.Encounter) adaptedClass
				.getContextTarget();
	}

	public IUsCoreMedicationstatement setContextTarget(
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

	public IUsCoreMedicationstatement setContextTarget(
			org.hl7.fhir.dstu3.model.EpisodeOfCare param) {
		adaptedClass.setContextTarget(param);
		return this;
	}

	public List<CodeableConcept> getReasonCode() {
		try {
			return adaptedClass.getReasonCode();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ReasonCode", e);
		}
	}

	public IUsCoreMedicationstatement setReasonCode(
			java.util.List<org.hl7.fhir.dstu3.model.CodeableConcept> param) {
		adaptedClass.setReasonCode(param);
		return this;
	}

	public boolean hasReasonCode() {
		return adaptedClass.hasReasonCode();
	}

	public IUsCoreMedicationstatement addReasonCode(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.addReasonCode(param);
		return this;
	}

	public CodeableConcept addReasonCode() {
		return adaptedClass.addReasonCode();
	}

	public boolean hasPartOf() {
		return adaptedClass.hasPartOf();
	}

	public List<MedicationAdministration> getPartOfMedicationAdministrationTarget() {
		List<org.hl7.fhir.dstu3.model.MedicationAdministration> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getPartOfTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.MedicationAdministration) resource);
		}
		return items;
	}

	public List<Reference> getPartOf() {
		try {
			return adaptedClass.getPartOf();
		} catch (Exception e) {
			throw new RuntimeException("Error getting PartOf", e);
		}
	}

	public List<MedicationDispense> getPartOfMedicationDispenseTarget() {
		List<org.hl7.fhir.dstu3.model.MedicationDispense> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getPartOfTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.MedicationDispense) resource);
		}
		return items;
	}

	public List<MedicationStatement> getPartOfMedicationStatementTarget() {
		List<org.hl7.fhir.dstu3.model.MedicationStatement> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getPartOfTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.MedicationStatement) resource);
		}
		return items;
	}

	public List<Procedure> getPartOfProcedureTarget() {
		List<org.hl7.fhir.dstu3.model.Procedure> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getPartOfTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.Procedure) resource);
		}
		return items;
	}

	public List<Observation> getPartOfObservationTarget() {
		List<org.hl7.fhir.dstu3.model.Observation> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getPartOfTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.Observation) resource);
		}
		return items;
	}

	public Reference getInformationSource() {
		try {
			return adaptedClass.getInformationSource();
		} catch (Exception e) {
			throw new RuntimeException("Error getting InformationSource", e);
		}
	}

	public Resource getInformationSourceTarget() {
		try {
			return adaptedClass.getInformationSourceTarget();
		} catch (Exception e) {
			throw new RuntimeException("Error getting InformationSourceTarget",
					e);
		}
	}

	public boolean hasInformationSource() {
		return adaptedClass.hasInformationSource();
	}

	public Reference getInformationSourcePatient() {
		try {
			return adaptedClass.getInformationSource();
		} catch (Exception e) {
			throw new RuntimeException("Error getting InformationSource", e);
		}
	}

	public IUsCoreMedicationstatement setInformationSource(
			org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setInformationSource(param);
		return this;
	}

	public Patient getInformationSourcePatientTarget() {
		return (org.hl7.fhir.dstu3.model.Patient) adaptedClass
				.getInformationSourceTarget();
	}

	public IUsCoreMedicationstatement setInformationSourceTarget(
			org.hl7.fhir.dstu3.model.Patient param) {
		adaptedClass.setInformationSourceTarget(param);
		return this;
	}

	public Reference getInformationSourcePractitioner() {
		try {
			return adaptedClass.getInformationSource();
		} catch (Exception e) {
			throw new RuntimeException("Error getting InformationSource", e);
		}
	}

	public Practitioner getInformationSourcePractitionerTarget() {
		return (org.hl7.fhir.dstu3.model.Practitioner) adaptedClass
				.getInformationSourceTarget();
	}

	public IUsCoreMedicationstatement setInformationSourceTarget(
			org.hl7.fhir.dstu3.model.Practitioner param) {
		adaptedClass.setInformationSourceTarget(param);
		return this;
	}

	public Reference getInformationSourceRelatedPerson() {
		try {
			return adaptedClass.getInformationSource();
		} catch (Exception e) {
			throw new RuntimeException("Error getting InformationSource", e);
		}
	}

	public RelatedPerson getInformationSourceRelatedPersonTarget() {
		return (org.hl7.fhir.dstu3.model.RelatedPerson) adaptedClass
				.getInformationSourceTarget();
	}

	public IUsCoreMedicationstatement setInformationSourceTarget(
			org.hl7.fhir.dstu3.model.RelatedPerson param) {
		adaptedClass.setInformationSourceTarget(param);
		return this;
	}

	public Reference getInformationSourceOrganization() {
		try {
			return adaptedClass.getInformationSource();
		} catch (Exception e) {
			throw new RuntimeException("Error getting InformationSource", e);
		}
	}

	public Organization getInformationSourceOrganizationTarget() {
		return (org.hl7.fhir.dstu3.model.Organization) adaptedClass
				.getInformationSourceTarget();
	}

	public IUsCoreMedicationstatement setInformationSourceTarget(
			org.hl7.fhir.dstu3.model.Organization param) {
		adaptedClass.setInformationSourceTarget(param);
		return this;
	}

	public boolean hasStatus() {
		return adaptedClass.hasStatus();
	}

	public boolean hasStatusElement() {
		return adaptedClass.hasStatusElement();
	}

	public MedicationStatement.MedicationStatementStatus getStatus() {
		try {
			return adaptedClass.getStatus();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Status", e);
		}
	}

	public Enumeration<MedicationStatement.MedicationStatementStatus> getStatusElement() {
		try {
			return adaptedClass.getStatusElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting StatusElement", e);
		}
	}

	public IUsCoreMedicationstatement setStatus(
			org.hl7.fhir.dstu3.model.MedicationStatement.MedicationStatementStatus param) {
		adaptedClass.setStatus(param);
		return this;
	}

	public IUsCoreMedicationstatement setStatusElement(
			org.hl7.fhir.dstu3.model.Enumeration<org.hl7.fhir.dstu3.model.MedicationStatement.MedicationStatementStatus> param) {
		adaptedClass.setStatusElement(param);
		return this;
	}

	public Type getMedication() {
		try {
			return adaptedClass.getMedication();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Medication", e);
		}
	}

	public IUsCoreMedicationstatement setMedication(
			org.hl7.fhir.dstu3.model.Type param) {
		adaptedClass.setMedication(param);
		return this;
	}

	public CodeableConcept getMedicationCodeableConcept() {
		try {
			return adaptedClass.getMedicationCodeableConcept();
		} catch (Exception e) {
			throw new RuntimeException(
					"Error getting MedicationCodeableConcept", e);
		}
	}

	public boolean hasMedicationCodeableConcept() {
		return adaptedClass.hasMedicationCodeableConcept();
	}

	public boolean hasMedication() {
		return adaptedClass.hasMedication();
	}

	public Reference getMedicationReference() {
		try {
			return adaptedClass.getMedicationReference();
		} catch (Exception e) {
			throw new RuntimeException("Error getting MedicationReference", e);
		}
	}

	public Medication getMedicationTarget() {
		return (org.hl7.fhir.dstu3.model.Medication) ((org.hl7.fhir.dstu3.model.Reference) adaptedClass
				.getMedication()).getResource();
	}

	public IUsCoreMedicationstatement setMedication(
			org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setMedication(param);
		return this;
	}

	public IUsCoreMedicationstatement setMedicationTarget(
			org.hl7.fhir.dstu3.model.Medication param) {
		Reference reference = new Reference(param);
		adaptedClass.setMedication(reference);
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

	public IUsCoreMedicationstatement setIdElement(
			org.hl7.fhir.dstu3.model.IdType param) {
		adaptedClass.setIdElement(param);
		return this;
	}

	public IUsCoreMedicationstatement setId(java.lang.String param) {
		adaptedClass.setId(param);
		return this;
	}

	public List<Identifier> getIdentifier() {
		try {
			return adaptedClass.getIdentifier();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Identifier", e);
		}
	}

	public IUsCoreMedicationstatement setIdentifier(
			java.util.List<org.hl7.fhir.dstu3.model.Identifier> param) {
		adaptedClass.setIdentifier(param);
		return this;
	}

	public boolean hasIdentifier() {
		return adaptedClass.hasIdentifier();
	}

	public IUsCoreMedicationstatement addIdentifier(
			org.hl7.fhir.dstu3.model.Identifier param) {
		adaptedClass.addIdentifier(param);
		return this;
	}

	public Identifier addIdentifier() {
		return adaptedClass.addIdentifier();
	}

	public boolean hasTaken() {
		return adaptedClass.hasTaken();
	}

	public boolean hasTakenElement() {
		return adaptedClass.hasTakenElement();
	}

	public MedicationStatement.MedicationStatementTaken getTaken() {
		try {
			return adaptedClass.getTaken();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Taken", e);
		}
	}

	public Enumeration<MedicationStatement.MedicationStatementTaken> getTakenElement() {
		try {
			return adaptedClass.getTakenElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting TakenElement", e);
		}
	}

	public IUsCoreMedicationstatement setTaken(
			org.hl7.fhir.dstu3.model.MedicationStatement.MedicationStatementTaken param) {
		adaptedClass.setTaken(param);
		return this;
	}

	public IUsCoreMedicationstatement setTakenElement(
			org.hl7.fhir.dstu3.model.Enumeration<org.hl7.fhir.dstu3.model.MedicationStatement.MedicationStatementTaken> param) {
		adaptedClass.setTakenElement(param);
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

	public IUsCoreMedicationstatement setImplicitRulesElement(
			org.hl7.fhir.dstu3.model.UriType param) {
		adaptedClass.setImplicitRulesElement(param);
		return this;
	}

	public IUsCoreMedicationstatement setImplicitRules(java.lang.String param) {
		adaptedClass.setImplicitRules(param);
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

	public IUsCoreMedicationstatement setLanguageElement(
			org.hl7.fhir.dstu3.model.CodeType param) {
		adaptedClass.setLanguageElement(param);
		return this;
	}

	public IUsCoreMedicationstatement setLanguage(java.lang.String param) {
		adaptedClass.setLanguage(param);
		return this;
	}

	public List<Annotation> getNote() {
		try {
			return adaptedClass.getNote();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Note", e);
		}
	}

	public IUsCoreMedicationstatement setNote(
			java.util.List<org.hl7.fhir.dstu3.model.Annotation> param) {
		adaptedClass.setNote(param);
		return this;
	}

	public boolean hasNote() {
		return adaptedClass.hasNote();
	}

	public IUsCoreMedicationstatement addNote(
			org.hl7.fhir.dstu3.model.Annotation param) {
		adaptedClass.addNote(param);
		return this;
	}

	public Annotation addNote() {
		return adaptedClass.addNote();
	}

	public List<CodeableConcept> getReasonNotTaken() {
		try {
			return adaptedClass.getReasonNotTaken();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ReasonNotTaken", e);
		}
	}

	public IUsCoreMedicationstatement setReasonNotTaken(
			java.util.List<org.hl7.fhir.dstu3.model.CodeableConcept> param) {
		adaptedClass.setReasonNotTaken(param);
		return this;
	}

	public boolean hasReasonNotTaken() {
		return adaptedClass.hasReasonNotTaken();
	}

	public IUsCoreMedicationstatement addReasonNotTaken(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.addReasonNotTaken(param);
		return this;
	}

	public CodeableConcept addReasonNotTaken() {
		return adaptedClass.addReasonNotTaken();
	}
}