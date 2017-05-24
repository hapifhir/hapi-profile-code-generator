package org.hspc.fhir.model.stu3;

import org.hspc.fhir.model.stu3.IUsCoreGoal;
import org.hl7.fhir.dstu3.model.Goal;
import org.hl7.fhir.dstu3.model.Resource;
import java.util.List;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.Type;
import org.hl7.fhir.dstu3.model.DateType;
import org.hl7.fhir.dstu3.model.StringType;
import java.lang.String;
import org.hl7.fhir.dstu3.model.Enumeration;
import org.hl7.fhir.dstu3.model.Reference;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.dstu3.model.Annotation;
import org.hl7.fhir.dstu3.model.Practitioner;
import org.hl7.fhir.dstu3.model.RelatedPerson;
import org.hl7.fhir.dstu3.model.IdType;
import org.hl7.fhir.dstu3.model.Identifier;
import java.util.Date;
import org.hl7.fhir.dstu3.model.CodeType;
import org.hl7.fhir.dstu3.model.Condition;
import org.hl7.fhir.dstu3.model.Observation;
import org.hl7.fhir.dstu3.model.MedicationStatement;
import org.hl7.fhir.dstu3.model.NutritionOrder;
import org.hl7.fhir.dstu3.model.ProcedureRequest;
import org.hl7.fhir.dstu3.model.RiskAssessment;
import org.hl7.fhir.dstu3.model.UriType;
import org.hl7.fhir.dstu3.model.*;
import ca.uhn.fhir.model.api.ExtensionDt;

public class UsCoreGoalAdapter implements IUsCoreGoal {

	private Goal adaptedClass;

	public UsCoreGoalAdapter() {
		this.adaptedClass = new org.hl7.fhir.dstu3.model.Goal();
	}

	public UsCoreGoalAdapter(org.hl7.fhir.dstu3.model.Goal adaptee) {
		this.adaptedClass = adaptee;
	}

	public Goal getAdaptee() {
		return adaptedClass;
	}

	public void setAdaptee(org.hl7.fhir.dstu3.model.Goal param) {
		this.adaptedClass = param;
	}

	public List<Resource> getContained() {
		try {
			return adaptedClass.getContained();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Contained", e);
		}
	}

	public IUsCoreGoal setContained(
			java.util.List<org.hl7.fhir.dstu3.model.Resource> param) {
		adaptedClass.setContained(param);
		return this;
	}

	public boolean hasContained() {
		return adaptedClass.hasContained();
	}

	public IUsCoreGoal addContained(org.hl7.fhir.dstu3.model.Resource param) {
		adaptedClass.addContained(param);
		return this;
	}

	public List<CodeableConcept> getCategory() {
		try {
			return adaptedClass.getCategory();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Category", e);
		}
	}

	public IUsCoreGoal setCategory(
			java.util.List<org.hl7.fhir.dstu3.model.CodeableConcept> param) {
		adaptedClass.setCategory(param);
		return this;
	}

	public boolean hasCategory() {
		return adaptedClass.hasCategory();
	}

	public IUsCoreGoal addCategory(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.addCategory(param);
		return this;
	}

	public CodeableConcept addCategory() {
		return adaptedClass.addCategory();
	}

	public Goal.GoalTargetComponent getTarget() {
		try {
			return adaptedClass.getTarget();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Target", e);
		}
	}

	public UsCoreGoalAdapter setTarget(
			org.hl7.fhir.dstu3.model.Goal.GoalTargetComponent param) {
		adaptedClass.setTarget(param);
		return this;
	}

	public boolean hasTarget() {
		return adaptedClass.hasTarget();
	}

	public Type getStart() {
		try {
			return adaptedClass.getStart();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Start", e);
		}
	}

	public IUsCoreGoal setStart(org.hl7.fhir.dstu3.model.Type param) {
		adaptedClass.setStart(param);
		return this;
	}

	public DateType getStartDateType() {
		try {
			return adaptedClass.getStartDateType();
		} catch (Exception e) {
			throw new RuntimeException("Error getting StartDateType", e);
		}
	}

	public boolean hasStartDateType() {
		return adaptedClass.hasStartDateType();
	}

	public CodeableConcept getStartCodeableConcept() {
		try {
			return adaptedClass.getStartCodeableConcept();
		} catch (Exception e) {
			throw new RuntimeException("Error getting StartCodeableConcept", e);
		}
	}

	public boolean hasStartCodeableConcept() {
		return adaptedClass.hasStartCodeableConcept();
	}

	public List<CodeableConcept> getOutcomeCode() {
		try {
			return adaptedClass.getOutcomeCode();
		} catch (Exception e) {
			throw new RuntimeException("Error getting OutcomeCode", e);
		}
	}

	public IUsCoreGoal setOutcomeCode(
			java.util.List<org.hl7.fhir.dstu3.model.CodeableConcept> param) {
		adaptedClass.setOutcomeCode(param);
		return this;
	}

	public boolean hasOutcomeCode() {
		return adaptedClass.hasOutcomeCode();
	}

	public IUsCoreGoal addOutcomeCode(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.addOutcomeCode(param);
		return this;
	}

	public CodeableConcept addOutcomeCode() {
		return adaptedClass.addOutcomeCode();
	}

	public boolean hasStatusReason() {
		return adaptedClass.hasStatusReason();
	}

	public boolean hasStatusReasonElement() {
		return adaptedClass.hasStatusReasonElement();
	}

	public StringType getStatusReasonElement() {
		try {
			return adaptedClass.getStatusReasonElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting StatusReasonElement", e);
		}
	}

	public String getStatusReason() {
		try {
			return adaptedClass.getStatusReason();
		} catch (Exception e) {
			throw new RuntimeException("Error getting StatusReason", e);
		}
	}

	public IUsCoreGoal setStatusReasonElement(
			org.hl7.fhir.dstu3.model.StringType param) {
		adaptedClass.setStatusReasonElement(param);
		return this;
	}

	public IUsCoreGoal setStatusReason(java.lang.String param) {
		adaptedClass.setStatusReason(param);
		return this;
	}

	public boolean hasStatus() {
		return adaptedClass.hasStatus();
	}

	public boolean hasStatusElement() {
		return adaptedClass.hasStatusElement();
	}

	public Goal.GoalStatus getStatus() {
		try {
			return adaptedClass.getStatus();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Status", e);
		}
	}

	public Enumeration<Goal.GoalStatus> getStatusElement() {
		try {
			return adaptedClass.getStatusElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting StatusElement", e);
		}
	}

	public IUsCoreGoal setStatus(org.hl7.fhir.dstu3.model.Goal.GoalStatus param) {
		adaptedClass.setStatus(param);
		return this;
	}

	public IUsCoreGoal setStatusElement(
			org.hl7.fhir.dstu3.model.Enumeration<org.hl7.fhir.dstu3.model.Goal.GoalStatus> param) {
		adaptedClass.setStatusElement(param);
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

	public IUsCoreGoal setSubject(org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setSubject(param);
		return this;
	}

	public Patient getSubjectTarget() {
		return (org.hl7.fhir.dstu3.model.Patient) adaptedClass
				.getSubjectTarget();
	}

	public IUsCoreGoal setSubjectTarget(org.hl7.fhir.dstu3.model.Patient param) {
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

	public IUsCoreGoal setSubjectAdapterTarget(
			org.hspc.fhir.model.stu3.UsCorePatientAdapter param) {
		adaptedClass.setSubjectTarget(param.getAdaptee());
		return this;
	}

	public boolean hasOutcomeReference() {
		return adaptedClass.hasOutcomeReference();
	}

	public List<Reference> getOutcomeReference() {
		try {
			return adaptedClass.getOutcomeReference();
		} catch (Exception e) {
			throw new RuntimeException("Error getting OutcomeReference", e);
		}
	}

	public CodeableConcept getDescription() {
		try {
			return adaptedClass.getDescription();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Description", e);
		}
	}

	public IUsCoreGoal setDescription(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.setDescription(param);
		return this;
	}

	public boolean hasDescription() {
		return adaptedClass.hasDescription();
	}

	public List<Annotation> getNote() {
		try {
			return adaptedClass.getNote();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Note", e);
		}
	}

	public IUsCoreGoal setNote(
			java.util.List<org.hl7.fhir.dstu3.model.Annotation> param) {
		adaptedClass.setNote(param);
		return this;
	}

	public boolean hasNote() {
		return adaptedClass.hasNote();
	}

	public IUsCoreGoal addNote(org.hl7.fhir.dstu3.model.Annotation param) {
		adaptedClass.addNote(param);
		return this;
	}

	public Annotation addNote() {
		return adaptedClass.addNote();
	}

	public Reference getExpressedBy() {
		try {
			return adaptedClass.getExpressedBy();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ExpressedBy", e);
		}
	}

	public Resource getExpressedByTarget() {
		try {
			return adaptedClass.getExpressedByTarget();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ExpressedByTarget", e);
		}
	}

	public boolean hasExpressedBy() {
		return adaptedClass.hasExpressedBy();
	}

	public Reference getExpressedByPatient() {
		try {
			return adaptedClass.getExpressedBy();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ExpressedBy", e);
		}
	}

	public IUsCoreGoal setExpressedBy(org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setExpressedBy(param);
		return this;
	}

	public Patient getExpressedByPatientTarget() {
		return (org.hl7.fhir.dstu3.model.Patient) adaptedClass
				.getExpressedByTarget();
	}

	public IUsCoreGoal setExpressedByTarget(
			org.hl7.fhir.dstu3.model.Patient param) {
		adaptedClass.setExpressedByTarget(param);
		return this;
	}

	public Reference getExpressedByPractitioner() {
		try {
			return adaptedClass.getExpressedBy();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ExpressedBy", e);
		}
	}

	public Practitioner getExpressedByPractitionerTarget() {
		return (org.hl7.fhir.dstu3.model.Practitioner) adaptedClass
				.getExpressedByTarget();
	}

	public IUsCoreGoal setExpressedByTarget(
			org.hl7.fhir.dstu3.model.Practitioner param) {
		adaptedClass.setExpressedByTarget(param);
		return this;
	}

	public Reference getExpressedByRelatedPerson() {
		try {
			return adaptedClass.getExpressedBy();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ExpressedBy", e);
		}
	}

	public RelatedPerson getExpressedByRelatedPersonTarget() {
		return (org.hl7.fhir.dstu3.model.RelatedPerson) adaptedClass
				.getExpressedByTarget();
	}

	public IUsCoreGoal setExpressedByTarget(
			org.hl7.fhir.dstu3.model.RelatedPerson param) {
		adaptedClass.setExpressedByTarget(param);
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

	public IUsCoreGoal setIdElement(org.hl7.fhir.dstu3.model.IdType param) {
		adaptedClass.setIdElement(param);
		return this;
	}

	public IUsCoreGoal setId(java.lang.String param) {
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

	public IUsCoreGoal setIdentifier(
			java.util.List<org.hl7.fhir.dstu3.model.Identifier> param) {
		adaptedClass.setIdentifier(param);
		return this;
	}

	public boolean hasIdentifier() {
		return adaptedClass.hasIdentifier();
	}

	public IUsCoreGoal addIdentifier(org.hl7.fhir.dstu3.model.Identifier param) {
		adaptedClass.addIdentifier(param);
		return this;
	}

	public Identifier addIdentifier() {
		return adaptedClass.addIdentifier();
	}

	public boolean hasStatusDate() {
		return adaptedClass.hasStatusDate();
	}

	public boolean hasStatusDateElement() {
		return adaptedClass.hasStatusDateElement();
	}

	public DateType getStatusDateElement() {
		try {
			return adaptedClass.getStatusDateElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting StatusDateElement", e);
		}
	}

	public Date getStatusDate() {
		try {
			return adaptedClass.getStatusDate();
		} catch (Exception e) {
			throw new RuntimeException("Error getting StatusDate", e);
		}
	}

	public IUsCoreGoal setStatusDateElement(
			org.hl7.fhir.dstu3.model.DateType param) {
		adaptedClass.setStatusDateElement(param);
		return this;
	}

	public IUsCoreGoal setStatusDate(java.util.Date param) {
		adaptedClass.setStatusDate(param);
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

	public IUsCoreGoal setLanguageElement(
			org.hl7.fhir.dstu3.model.CodeType param) {
		adaptedClass.setLanguageElement(param);
		return this;
	}

	public IUsCoreGoal setLanguage(java.lang.String param) {
		adaptedClass.setLanguage(param);
		return this;
	}

	public CodeableConcept getPriority() {
		try {
			return adaptedClass.getPriority();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Priority", e);
		}
	}

	public IUsCoreGoal setPriority(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.setPriority(param);
		return this;
	}

	public boolean hasPriority() {
		return adaptedClass.hasPriority();
	}

	public boolean hasAddresses() {
		return adaptedClass.hasAddresses();
	}

	public List<Condition> getAddressesConditionTarget() {
		List<org.hl7.fhir.dstu3.model.Condition> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getAddressesTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.Condition) resource);
		}
		return items;
	}

	public List<Reference> getAddresses() {
		try {
			return adaptedClass.getAddresses();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Addresses", e);
		}
	}

	public List<Observation> getAddressesObservationTarget() {
		List<org.hl7.fhir.dstu3.model.Observation> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getAddressesTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.Observation) resource);
		}
		return items;
	}

	public List<MedicationStatement> getAddressesMedicationStatementTarget() {
		List<org.hl7.fhir.dstu3.model.MedicationStatement> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getAddressesTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.MedicationStatement) resource);
		}
		return items;
	}

	public List<NutritionOrder> getAddressesNutritionOrderTarget() {
		List<org.hl7.fhir.dstu3.model.NutritionOrder> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getAddressesTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.NutritionOrder) resource);
		}
		return items;
	}

	public List<ProcedureRequest> getAddressesProcedureRequestTarget() {
		List<org.hl7.fhir.dstu3.model.ProcedureRequest> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getAddressesTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.ProcedureRequest) resource);
		}
		return items;
	}

	public List<RiskAssessment> getAddressesRiskAssessmentTarget() {
		List<org.hl7.fhir.dstu3.model.RiskAssessment> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getAddressesTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.RiskAssessment) resource);
		}
		return items;
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

	public IUsCoreGoal setImplicitRulesElement(
			org.hl7.fhir.dstu3.model.UriType param) {
		adaptedClass.setImplicitRulesElement(param);
		return this;
	}

	public IUsCoreGoal setImplicitRules(java.lang.String param) {
		adaptedClass.setImplicitRules(param);
		return this;
	}
}