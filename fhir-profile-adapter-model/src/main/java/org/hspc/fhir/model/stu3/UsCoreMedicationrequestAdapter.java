package org.hspc.fhir.model.stu3;

import org.hspc.fhir.model.stu3.IUsCoreMedicationrequest;
import org.hl7.fhir.dstu3.model.MedicationRequest;
import org.hl7.fhir.dstu3.model.Resource;
import java.util.List;
import org.hl7.fhir.dstu3.model.Reference;
import org.hl7.fhir.dstu3.model.Enumeration;
import org.hl7.fhir.dstu3.model.Annotation;
import org.hl7.fhir.dstu3.model.Encounter;
import org.hl7.fhir.dstu3.model.EpisodeOfCare;
import org.hl7.fhir.dstu3.model.ActivityDefinition;
import org.hl7.fhir.dstu3.model.PlanDefinition;
import org.hl7.fhir.dstu3.model.Identifier;
import org.hl7.fhir.dstu3.model.CodeType;
import java.lang.String;
import org.hl7.fhir.dstu3.model.Condition;
import org.hl7.fhir.dstu3.model.Observation;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.DateTimeType;
import java.util.Date;
import org.hl7.fhir.dstu3.model.CarePlan;
import org.hl7.fhir.dstu3.model.ProcedureRequest;
import org.hl7.fhir.dstu3.model.ReferralRequest;
import org.hl7.fhir.dstu3.model.Practitioner;
import org.hl7.fhir.dstu3.model.Type;
import org.hl7.fhir.dstu3.model.Medication;
import org.hl7.fhir.dstu3.model.IdType;
import org.hl7.fhir.dstu3.model.UriType;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.dstu3.model.*;
import ca.uhn.fhir.model.api.ExtensionDt;

public class UsCoreMedicationrequestAdapter implements IUsCoreMedicationrequest {

	private MedicationRequest adaptedClass;

	public UsCoreMedicationrequestAdapter() {
		this.adaptedClass = new org.hl7.fhir.dstu3.model.MedicationRequest();
	}

	public UsCoreMedicationrequestAdapter(
			org.hl7.fhir.dstu3.model.MedicationRequest adaptee) {
		this.adaptedClass = adaptee;
	}

	public MedicationRequest getAdaptee() {
		return adaptedClass;
	}

	public void setAdaptee(org.hl7.fhir.dstu3.model.MedicationRequest param) {
		this.adaptedClass = param;
	}

	public MedicationRequest.MedicationRequestSubstitutionComponent getSubstitution() {
		try {
			return adaptedClass.getSubstitution();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Substitution", e);
		}
	}

	public UsCoreMedicationrequestAdapter setSubstitution(
			org.hl7.fhir.dstu3.model.MedicationRequest.MedicationRequestSubstitutionComponent param) {
		adaptedClass.setSubstitution(param);
		return this;
	}

	public boolean hasSubstitution() {
		return adaptedClass.hasSubstitution();
	}

	public List<Resource> getContained() {
		try {
			return adaptedClass.getContained();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Contained", e);
		}
	}

	public IUsCoreMedicationrequest setContained(
			java.util.List<org.hl7.fhir.dstu3.model.Resource> param) {
		adaptedClass.setContained(param);
		return this;
	}

	public boolean hasContained() {
		return adaptedClass.hasContained();
	}

	public IUsCoreMedicationrequest addContained(
			org.hl7.fhir.dstu3.model.Resource param) {
		adaptedClass.addContained(param);
		return this;
	}

	public boolean hasDetectedIssue() {
		return adaptedClass.hasDetectedIssue();
	}

	public List<Reference> getDetectedIssue() {
		try {
			return adaptedClass.getDetectedIssue();
		} catch (Exception e) {
			throw new RuntimeException("Error getting DetectedIssue", e);
		}
	}

	public boolean hasPriorPrescription() {
		return adaptedClass.hasPriorPrescription();
	}

	public Reference getPriorPrescription() {
		try {
			return adaptedClass.getPriorPrescription();
		} catch (Exception e) {
			throw new RuntimeException("Error getting PriorPrescription", e);
		}
	}

	public IUsCoreMedicationrequest setPriorPrescription(
			org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setPriorPrescription(param);
		return this;
	}

	public MedicationRequest getPriorPrescriptionTarget() {
		return (org.hl7.fhir.dstu3.model.MedicationRequest) adaptedClass
				.getPriorPrescriptionTarget();
	}

	public IUsCoreMedicationrequest setPriorPrescriptionTarget(
			org.hl7.fhir.dstu3.model.MedicationRequest param) {
		adaptedClass.setPriorPrescriptionTarget(param);
		return this;
	}

	public boolean hasStatus() {
		return adaptedClass.hasStatus();
	}

	public boolean hasStatusElement() {
		return adaptedClass.hasStatusElement();
	}

	public MedicationRequest.MedicationRequestStatus getStatus() {
		try {
			return adaptedClass.getStatus();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Status", e);
		}
	}

	public Enumeration<MedicationRequest.MedicationRequestStatus> getStatusElement() {
		try {
			return adaptedClass.getStatusElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting StatusElement", e);
		}
	}

	public IUsCoreMedicationrequest setStatus(
			org.hl7.fhir.dstu3.model.MedicationRequest.MedicationRequestStatus param) {
		adaptedClass.setStatus(param);
		return this;
	}

	public IUsCoreMedicationrequest setStatusElement(
			org.hl7.fhir.dstu3.model.Enumeration<org.hl7.fhir.dstu3.model.MedicationRequest.MedicationRequestStatus> param) {
		adaptedClass.setStatusElement(param);
		return this;
	}

	public boolean hasEventHistory() {
		return adaptedClass.hasEventHistory();
	}

	public List<Reference> getEventHistory() {
		try {
			return adaptedClass.getEventHistory();
		} catch (Exception e) {
			throw new RuntimeException("Error getting EventHistory", e);
		}
	}

	public boolean hasIntent() {
		return adaptedClass.hasIntent();
	}

	public boolean hasIntentElement() {
		return adaptedClass.hasIntentElement();
	}

	public MedicationRequest.MedicationRequestIntent getIntent() {
		try {
			return adaptedClass.getIntent();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Intent", e);
		}
	}

	public Enumeration<MedicationRequest.MedicationRequestIntent> getIntentElement() {
		try {
			return adaptedClass.getIntentElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting IntentElement", e);
		}
	}

	public IUsCoreMedicationrequest setIntent(
			org.hl7.fhir.dstu3.model.MedicationRequest.MedicationRequestIntent param) {
		adaptedClass.setIntent(param);
		return this;
	}

	public IUsCoreMedicationrequest setIntentElement(
			org.hl7.fhir.dstu3.model.Enumeration<org.hl7.fhir.dstu3.model.MedicationRequest.MedicationRequestIntent> param) {
		adaptedClass.setIntentElement(param);
		return this;
	}

	public MedicationRequest.MedicationRequestRequesterComponent getRequester() {
		try {
			return adaptedClass.getRequester();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Requester", e);
		}
	}

	public UsCoreMedicationrequestAdapter setRequester(
			org.hl7.fhir.dstu3.model.MedicationRequest.MedicationRequestRequesterComponent param) {
		adaptedClass.setRequester(param);
		return this;
	}

	public boolean hasRequester() {
		return adaptedClass.hasRequester();
	}

	public List<Annotation> getNote() {
		try {
			return adaptedClass.getNote();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Note", e);
		}
	}

	public IUsCoreMedicationrequest setNote(
			java.util.List<org.hl7.fhir.dstu3.model.Annotation> param) {
		adaptedClass.setNote(param);
		return this;
	}

	public boolean hasNote() {
		return adaptedClass.hasNote();
	}

	public IUsCoreMedicationrequest addNote(
			org.hl7.fhir.dstu3.model.Annotation param) {
		adaptedClass.addNote(param);
		return this;
	}

	public Annotation addNote() {
		return adaptedClass.addNote();
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

	public IUsCoreMedicationrequest setContext(
			org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setContext(param);
		return this;
	}

	public Encounter getContextEncounterTarget() {
		return (org.hl7.fhir.dstu3.model.Encounter) adaptedClass
				.getContextTarget();
	}

	public IUsCoreMedicationrequest setContextTarget(
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

	public IUsCoreMedicationrequest setContextTarget(
			org.hl7.fhir.dstu3.model.EpisodeOfCare param) {
		adaptedClass.setContextTarget(param);
		return this;
	}

	public boolean hasDefinition() {
		return adaptedClass.hasDefinition();
	}

	public List<ActivityDefinition> getDefinitionActivityDefinitionTarget() {
		List<org.hl7.fhir.dstu3.model.ActivityDefinition> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getDefinitionTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.ActivityDefinition) resource);
		}
		return items;
	}

	public List<Reference> getDefinition() {
		try {
			return adaptedClass.getDefinition();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Definition", e);
		}
	}

	public List<PlanDefinition> getDefinitionPlanDefinitionTarget() {
		List<org.hl7.fhir.dstu3.model.PlanDefinition> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getDefinitionTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.PlanDefinition) resource);
		}
		return items;
	}

	public MedicationRequest.MedicationRequestDispenseRequestComponent getDispenseRequest() {
		try {
			return adaptedClass.getDispenseRequest();
		} catch (Exception e) {
			throw new RuntimeException("Error getting DispenseRequest", e);
		}
	}

	public UsCoreMedicationrequestAdapter setDispenseRequest(
			org.hl7.fhir.dstu3.model.MedicationRequest.MedicationRequestDispenseRequestComponent param) {
		adaptedClass.setDispenseRequest(param);
		return this;
	}

	public boolean hasDispenseRequest() {
		return adaptedClass.hasDispenseRequest();
	}

	public List<Identifier> getIdentifier() {
		try {
			return adaptedClass.getIdentifier();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Identifier", e);
		}
	}

	public IUsCoreMedicationrequest setIdentifier(
			java.util.List<org.hl7.fhir.dstu3.model.Identifier> param) {
		adaptedClass.setIdentifier(param);
		return this;
	}

	public boolean hasIdentifier() {
		return adaptedClass.hasIdentifier();
	}

	public IUsCoreMedicationrequest addIdentifier(
			org.hl7.fhir.dstu3.model.Identifier param) {
		adaptedClass.addIdentifier(param);
		return this;
	}

	public Identifier addIdentifier() {
		return adaptedClass.addIdentifier();
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

	public IUsCoreMedicationrequest setLanguageElement(
			org.hl7.fhir.dstu3.model.CodeType param) {
		adaptedClass.setLanguageElement(param);
		return this;
	}

	public IUsCoreMedicationrequest setLanguage(java.lang.String param) {
		adaptedClass.setLanguage(param);
		return this;
	}

	public Identifier getGroupIdentifier() {
		try {
			return adaptedClass.getGroupIdentifier();
		} catch (Exception e) {
			throw new RuntimeException("Error getting GroupIdentifier", e);
		}
	}

	public IUsCoreMedicationrequest setGroupIdentifier(
			org.hl7.fhir.dstu3.model.Identifier param) {
		adaptedClass.setGroupIdentifier(param);
		return this;
	}

	public boolean hasGroupIdentifier() {
		return adaptedClass.hasGroupIdentifier();
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

	public CodeableConcept getCategory() {
		try {
			return adaptedClass.getCategory();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Category", e);
		}
	}

	public IUsCoreMedicationrequest setCategory(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.setCategory(param);
		return this;
	}

	public boolean hasCategory() {
		return adaptedClass.hasCategory();
	}

	public boolean hasAuthoredOn() {
		return adaptedClass.hasAuthoredOn();
	}

	public boolean hasAuthoredOnElement() {
		return adaptedClass.hasAuthoredOnElement();
	}

	public DateTimeType getAuthoredOnElement() {
		try {
			return adaptedClass.getAuthoredOnElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting AuthoredOnElement", e);
		}
	}

	public Date getAuthoredOn() {
		try {
			return adaptedClass.getAuthoredOn();
		} catch (Exception e) {
			throw new RuntimeException("Error getting AuthoredOn", e);
		}
	}

	public IUsCoreMedicationrequest setAuthoredOnElement(
			org.hl7.fhir.dstu3.model.DateTimeType param) {
		adaptedClass.setAuthoredOnElement(param);
		return this;
	}

	public IUsCoreMedicationrequest setAuthoredOn(java.util.Date param) {
		adaptedClass.setAuthoredOn(param);
		return this;
	}

	public boolean hasBasedOn() {
		return adaptedClass.hasBasedOn();
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

	public List<Reference> getBasedOn() {
		try {
			return adaptedClass.getBasedOn();
		} catch (Exception e) {
			throw new RuntimeException("Error getting BasedOn", e);
		}
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

	public boolean hasPriority() {
		return adaptedClass.hasPriority();
	}

	public boolean hasPriorityElement() {
		return adaptedClass.hasPriorityElement();
	}

	public MedicationRequest.MedicationRequestPriority getPriority() {
		try {
			return adaptedClass.getPriority();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Priority", e);
		}
	}

	public Enumeration<MedicationRequest.MedicationRequestPriority> getPriorityElement() {
		try {
			return adaptedClass.getPriorityElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting PriorityElement", e);
		}
	}

	public IUsCoreMedicationrequest setPriority(
			org.hl7.fhir.dstu3.model.MedicationRequest.MedicationRequestPriority param) {
		adaptedClass.setPriority(param);
		return this;
	}

	public IUsCoreMedicationrequest setPriorityElement(
			org.hl7.fhir.dstu3.model.Enumeration<org.hl7.fhir.dstu3.model.MedicationRequest.MedicationRequestPriority> param) {
		adaptedClass.setPriorityElement(param);
		return this;
	}

	public boolean hasRecorder() {
		return adaptedClass.hasRecorder();
	}

	public Reference getRecorder() {
		try {
			return adaptedClass.getRecorder();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Recorder", e);
		}
	}

	public IUsCoreMedicationrequest setRecorder(
			org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setRecorder(param);
		return this;
	}

	public Practitioner getRecorderTarget() {
		return (org.hl7.fhir.dstu3.model.Practitioner) adaptedClass
				.getRecorderTarget();
	}

	public IUsCoreMedicationrequest setRecorderTarget(
			org.hl7.fhir.dstu3.model.Practitioner param) {
		adaptedClass.setRecorderTarget(param);
		return this;
	}

	public Type getMedication() {
		try {
			return adaptedClass.getMedication();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Medication", e);
		}
	}

	public IUsCoreMedicationrequest setMedication(
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

	public IUsCoreMedicationrequest setMedication(
			org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setMedication(param);
		return this;
	}

	public IUsCoreMedicationrequest setMedicationTarget(
			org.hl7.fhir.dstu3.model.Medication param) {
		Reference reference = new Reference(param);
		adaptedClass.setMedication(reference);
		return this;
	}

	public List<CodeableConcept> getReasonCode() {
		try {
			return adaptedClass.getReasonCode();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ReasonCode", e);
		}
	}

	public IUsCoreMedicationrequest setReasonCode(
			java.util.List<org.hl7.fhir.dstu3.model.CodeableConcept> param) {
		adaptedClass.setReasonCode(param);
		return this;
	}

	public boolean hasReasonCode() {
		return adaptedClass.hasReasonCode();
	}

	public IUsCoreMedicationrequest addReasonCode(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.addReasonCode(param);
		return this;
	}

	public CodeableConcept addReasonCode() {
		return adaptedClass.addReasonCode();
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

	public IUsCoreMedicationrequest setIdElement(
			org.hl7.fhir.dstu3.model.IdType param) {
		adaptedClass.setIdElement(param);
		return this;
	}

	public IUsCoreMedicationrequest setId(java.lang.String param) {
		adaptedClass.setId(param);
		return this;
	}

	public boolean hasSupportingInformation() {
		return adaptedClass.hasSupportingInformation();
	}

	public List<Reference> getSupportingInformation() {
		try {
			return adaptedClass.getSupportingInformation();
		} catch (Exception e) {
			throw new RuntimeException("Error getting SupportingInformation", e);
		}
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

	public IUsCoreMedicationrequest setImplicitRulesElement(
			org.hl7.fhir.dstu3.model.UriType param) {
		adaptedClass.setImplicitRulesElement(param);
		return this;
	}

	public IUsCoreMedicationrequest setImplicitRules(java.lang.String param) {
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

	public IUsCoreMedicationrequest setSubject(
			org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setSubject(param);
		return this;
	}

	public Patient getSubjectTarget() {
		return (org.hl7.fhir.dstu3.model.Patient) adaptedClass
				.getSubjectTarget();
	}

	public IUsCoreMedicationrequest setSubjectTarget(
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

	public IUsCoreMedicationrequest setSubjectAdapterTarget(
			org.hspc.fhir.model.stu3.UsCorePatientAdapter param) {
		adaptedClass.setSubjectTarget(param.getAdaptee());
		return this;
	}
}