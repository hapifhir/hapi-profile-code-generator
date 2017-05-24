package org.hspc.fhir.model.stu3;

import org.hspc.fhir.model.stu3.IUsCoreProcedure;
import org.hl7.fhir.dstu3.model.Procedure;
import java.util.List;
import org.hl7.fhir.dstu3.model.Annotation;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.Type;
import org.hl7.fhir.dstu3.model.DateTimeType;
import org.hl7.fhir.dstu3.model.Period;
import org.hl7.fhir.dstu3.model.Device;
import org.hl7.fhir.dstu3.model.Reference;
import org.hl7.fhir.dstu3.model.Medication;
import org.hl7.fhir.dstu3.model.Substance;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.dstu3.model.Resource;
import org.hl7.fhir.dstu3.model.Encounter;
import org.hl7.fhir.dstu3.model.EpisodeOfCare;
import org.hl7.fhir.dstu3.model.IdType;
import java.lang.String;
import org.hl7.fhir.dstu3.model.Location;
import org.hl7.fhir.dstu3.model.Observation;
import org.hl7.fhir.dstu3.model.MedicationAdministration;
import org.hl7.fhir.dstu3.model.Condition;
import org.hl7.fhir.dstu3.model.UriType;
import org.hl7.fhir.dstu3.model.BooleanType;
import java.lang.Boolean;
import org.hl7.fhir.dstu3.model.PlanDefinition;
import org.hl7.fhir.dstu3.model.ActivityDefinition;
import org.hl7.fhir.dstu3.model.HealthcareService;
import org.hl7.fhir.dstu3.model.Identifier;
import org.hl7.fhir.dstu3.model.CodeType;
import org.hl7.fhir.dstu3.model.Enumeration;
import org.hl7.fhir.dstu3.model.CarePlan;
import org.hl7.fhir.dstu3.model.ProcedureRequest;
import org.hl7.fhir.dstu3.model.ReferralRequest;
import org.hl7.fhir.dstu3.model.*;
import ca.uhn.fhir.model.api.ExtensionDt;

public class UsCoreProcedureAdapter implements IUsCoreProcedure {

	private Procedure adaptedClass;

	public UsCoreProcedureAdapter() {
		this.adaptedClass = new org.hl7.fhir.dstu3.model.Procedure();
	}

	public UsCoreProcedureAdapter(org.hl7.fhir.dstu3.model.Procedure adaptee) {
		this.adaptedClass = adaptee;
	}

	public Procedure getAdaptee() {
		return adaptedClass;
	}

	public void setAdaptee(org.hl7.fhir.dstu3.model.Procedure param) {
		this.adaptedClass = param;
	}

	public List<Procedure.ProcedurePerformerComponent> getPerformer() {
		try {
			return adaptedClass.getPerformer();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Performer", e);
		}
	}

	public UsCoreProcedureAdapter setPerformer(
			java.util.List<org.hl7.fhir.dstu3.model.Procedure.ProcedurePerformerComponent> param) {
		adaptedClass.setPerformer(param);
		return this;
	}

	public boolean hasPerformer() {
		return adaptedClass.hasPerformer();
	}

	public UsCoreProcedureAdapter addPerformer(
			org.hl7.fhir.dstu3.model.Procedure.ProcedurePerformerComponent param) {
		adaptedClass.addPerformer(param);
		return this;
	}

	public Procedure.ProcedurePerformerComponent addPerformer() {
		return adaptedClass.addPerformer();
	}

	public List<Annotation> getNote() {
		try {
			return adaptedClass.getNote();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Note", e);
		}
	}

	public IUsCoreProcedure setNote(
			java.util.List<org.hl7.fhir.dstu3.model.Annotation> param) {
		adaptedClass.setNote(param);
		return this;
	}

	public boolean hasNote() {
		return adaptedClass.hasNote();
	}

	public IUsCoreProcedure addNote(org.hl7.fhir.dstu3.model.Annotation param) {
		adaptedClass.addNote(param);
		return this;
	}

	public Annotation addNote() {
		return adaptedClass.addNote();
	}

	public List<CodeableConcept> getReasonCode() {
		try {
			return adaptedClass.getReasonCode();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ReasonCode", e);
		}
	}

	public IUsCoreProcedure setReasonCode(
			java.util.List<org.hl7.fhir.dstu3.model.CodeableConcept> param) {
		adaptedClass.setReasonCode(param);
		return this;
	}

	public boolean hasReasonCode() {
		return adaptedClass.hasReasonCode();
	}

	public IUsCoreProcedure addReasonCode(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.addReasonCode(param);
		return this;
	}

	public CodeableConcept addReasonCode() {
		return adaptedClass.addReasonCode();
	}

	public Type getPerformed() {
		try {
			return adaptedClass.getPerformed();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Performed", e);
		}
	}

	public IUsCoreProcedure setPerformed(org.hl7.fhir.dstu3.model.Type param) {
		adaptedClass.setPerformed(param);
		return this;
	}

	public DateTimeType getPerformedDateTimeType() {
		try {
			return adaptedClass.getPerformedDateTimeType();
		} catch (Exception e) {
			throw new RuntimeException("Error getting PerformedDateTimeType", e);
		}
	}

	public boolean hasPerformedDateTimeType() {
		return adaptedClass.hasPerformedDateTimeType();
	}

	public Period getPerformedPeriod() {
		try {
			return adaptedClass.getPerformedPeriod();
		} catch (Exception e) {
			throw new RuntimeException("Error getting PerformedPeriod", e);
		}
	}

	public boolean hasPerformedPeriod() {
		return adaptedClass.hasPerformedPeriod();
	}

	public List<CodeableConcept> getBodySite() {
		try {
			return adaptedClass.getBodySite();
		} catch (Exception e) {
			throw new RuntimeException("Error getting BodySite", e);
		}
	}

	public IUsCoreProcedure setBodySite(
			java.util.List<org.hl7.fhir.dstu3.model.CodeableConcept> param) {
		adaptedClass.setBodySite(param);
		return this;
	}

	public boolean hasBodySite() {
		return adaptedClass.hasBodySite();
	}

	public IUsCoreProcedure addBodySite(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.addBodySite(param);
		return this;
	}

	public CodeableConcept addBodySite() {
		return adaptedClass.addBodySite();
	}

	public boolean hasUsedReference() {
		return adaptedClass.hasUsedReference();
	}

	public List<Device> getUsedReferenceDeviceTarget() {
		List<org.hl7.fhir.dstu3.model.Device> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getUsedReferenceTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.Device) resource);
		}
		return items;
	}

	public List<Reference> getUsedReference() {
		try {
			return adaptedClass.getUsedReference();
		} catch (Exception e) {
			throw new RuntimeException("Error getting UsedReference", e);
		}
	}

	public List<Medication> getUsedReferenceMedicationTarget() {
		List<org.hl7.fhir.dstu3.model.Medication> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getUsedReferenceTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.Medication) resource);
		}
		return items;
	}

	public List<Substance> getUsedReferenceSubstanceTarget() {
		List<org.hl7.fhir.dstu3.model.Substance> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getUsedReferenceTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.Substance) resource);
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

	public IUsCoreProcedure setCategory(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.setCategory(param);
		return this;
	}

	public boolean hasCategory() {
		return adaptedClass.hasCategory();
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

	public IUsCoreProcedure setSubject(org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setSubject(param);
		return this;
	}

	public Patient getSubjectTarget() {
		return (org.hl7.fhir.dstu3.model.Patient) adaptedClass
				.getSubjectTarget();
	}

	public IUsCoreProcedure setSubjectTarget(
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

	public IUsCoreProcedure setSubjectAdapterTarget(
			org.hspc.fhir.model.stu3.UsCorePatientAdapter param) {
		adaptedClass.setSubjectTarget(param.getAdaptee());
		return this;
	}

	public List<CodeableConcept> getFollowUp() {
		try {
			return adaptedClass.getFollowUp();
		} catch (Exception e) {
			throw new RuntimeException("Error getting FollowUp", e);
		}
	}

	public IUsCoreProcedure setFollowUp(
			java.util.List<org.hl7.fhir.dstu3.model.CodeableConcept> param) {
		adaptedClass.setFollowUp(param);
		return this;
	}

	public boolean hasFollowUp() {
		return adaptedClass.hasFollowUp();
	}

	public IUsCoreProcedure addFollowUp(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.addFollowUp(param);
		return this;
	}

	public CodeableConcept addFollowUp() {
		return adaptedClass.addFollowUp();
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

	public IUsCoreProcedure setContext(org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setContext(param);
		return this;
	}

	public Encounter getContextEncounterTarget() {
		return (org.hl7.fhir.dstu3.model.Encounter) adaptedClass
				.getContextTarget();
	}

	public IUsCoreProcedure setContextTarget(
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

	public IUsCoreProcedure setContextTarget(
			org.hl7.fhir.dstu3.model.EpisodeOfCare param) {
		adaptedClass.setContextTarget(param);
		return this;
	}

	public boolean hasComplicationDetail() {
		return adaptedClass.hasComplicationDetail();
	}

	public List<Reference> getComplicationDetail() {
		try {
			return adaptedClass.getComplicationDetail();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ComplicationDetail", e);
		}
	}

	public List<Procedure.ProcedureFocalDeviceComponent> getFocalDevice() {
		try {
			return adaptedClass.getFocalDevice();
		} catch (Exception e) {
			throw new RuntimeException("Error getting FocalDevice", e);
		}
	}

	public UsCoreProcedureAdapter setFocalDevice(
			java.util.List<org.hl7.fhir.dstu3.model.Procedure.ProcedureFocalDeviceComponent> param) {
		adaptedClass.setFocalDevice(param);
		return this;
	}

	public boolean hasFocalDevice() {
		return adaptedClass.hasFocalDevice();
	}

	public UsCoreProcedureAdapter addFocalDevice(
			org.hl7.fhir.dstu3.model.Procedure.ProcedureFocalDeviceComponent param) {
		adaptedClass.addFocalDevice(param);
		return this;
	}

	public Procedure.ProcedureFocalDeviceComponent addFocalDevice() {
		return adaptedClass.addFocalDevice();
	}

	public boolean hasReport() {
		return adaptedClass.hasReport();
	}

	public List<Reference> getReport() {
		try {
			return adaptedClass.getReport();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Report", e);
		}
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

	public IUsCoreProcedure setIdElement(org.hl7.fhir.dstu3.model.IdType param) {
		adaptedClass.setIdElement(param);
		return this;
	}

	public IUsCoreProcedure setId(java.lang.String param) {
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

	public IUsCoreProcedure setLocation(org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setLocation(param);
		return this;
	}

	public Location getLocationTarget() {
		return (org.hl7.fhir.dstu3.model.Location) adaptedClass
				.getLocationTarget();
	}

	public IUsCoreProcedure setLocationTarget(
			org.hl7.fhir.dstu3.model.Location param) {
		adaptedClass.setLocationTarget(param);
		return this;
	}

	public boolean hasPartOf() {
		return adaptedClass.hasPartOf();
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

	public List<Reference> getPartOf() {
		try {
			return adaptedClass.getPartOf();
		} catch (Exception e) {
			throw new RuntimeException("Error getting PartOf", e);
		}
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

	public List<MedicationAdministration> getPartOfMedicationAdministrationTarget() {
		List<org.hl7.fhir.dstu3.model.MedicationAdministration> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getPartOfTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.MedicationAdministration) resource);
		}
		return items;
	}

	public List<Resource> getContained() {
		try {
			return adaptedClass.getContained();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Contained", e);
		}
	}

	public IUsCoreProcedure setContained(
			java.util.List<org.hl7.fhir.dstu3.model.Resource> param) {
		adaptedClass.setContained(param);
		return this;
	}

	public boolean hasContained() {
		return adaptedClass.hasContained();
	}

	public IUsCoreProcedure addContained(org.hl7.fhir.dstu3.model.Resource param) {
		adaptedClass.addContained(param);
		return this;
	}

	public CodeableConcept getNotDoneReason() {
		try {
			return adaptedClass.getNotDoneReason();
		} catch (Exception e) {
			throw new RuntimeException("Error getting NotDoneReason", e);
		}
	}

	public IUsCoreProcedure setNotDoneReason(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.setNotDoneReason(param);
		return this;
	}

	public boolean hasNotDoneReason() {
		return adaptedClass.hasNotDoneReason();
	}

	public List<CodeableConcept> getUsedCode() {
		try {
			return adaptedClass.getUsedCode();
		} catch (Exception e) {
			throw new RuntimeException("Error getting UsedCode", e);
		}
	}

	public IUsCoreProcedure setUsedCode(
			java.util.List<org.hl7.fhir.dstu3.model.CodeableConcept> param) {
		adaptedClass.setUsedCode(param);
		return this;
	}

	public boolean hasUsedCode() {
		return adaptedClass.hasUsedCode();
	}

	public IUsCoreProcedure addUsedCode(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.addUsedCode(param);
		return this;
	}

	public CodeableConcept addUsedCode() {
		return adaptedClass.addUsedCode();
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

	public IUsCoreProcedure setImplicitRulesElement(
			org.hl7.fhir.dstu3.model.UriType param) {
		adaptedClass.setImplicitRulesElement(param);
		return this;
	}

	public IUsCoreProcedure setImplicitRules(java.lang.String param) {
		adaptedClass.setImplicitRules(param);
		return this;
	}

	public List<CodeableConcept> getComplication() {
		try {
			return adaptedClass.getComplication();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Complication", e);
		}
	}

	public IUsCoreProcedure setComplication(
			java.util.List<org.hl7.fhir.dstu3.model.CodeableConcept> param) {
		adaptedClass.setComplication(param);
		return this;
	}

	public boolean hasComplication() {
		return adaptedClass.hasComplication();
	}

	public IUsCoreProcedure addComplication(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.addComplication(param);
		return this;
	}

	public CodeableConcept addComplication() {
		return adaptedClass.addComplication();
	}

	public boolean hasNotDone() {
		return adaptedClass.hasNotDone();
	}

	public boolean hasNotDoneElement() {
		return adaptedClass.hasNotDoneElement();
	}

	public BooleanType getNotDoneElement() {
		try {
			return adaptedClass.getNotDoneElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting NotDoneElement", e);
		}
	}

	public Boolean getNotDone() {
		try {
			return adaptedClass.getNotDone();
		} catch (Exception e) {
			throw new RuntimeException("Error getting NotDone", e);
		}
	}

	public IUsCoreProcedure setNotDoneElement(
			org.hl7.fhir.dstu3.model.BooleanType param) {
		adaptedClass.setNotDoneElement(param);
		return this;
	}

	public IUsCoreProcedure setNotDone(java.lang.Boolean param) {
		adaptedClass.setNotDone(param);
		return this;
	}

	public boolean hasDefinition() {
		return adaptedClass.hasDefinition();
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

	public List<Reference> getDefinition() {
		try {
			return adaptedClass.getDefinition();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Definition", e);
		}
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

	public List<HealthcareService> getDefinitionHealthcareServiceTarget() {
		List<org.hl7.fhir.dstu3.model.HealthcareService> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getDefinitionTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.HealthcareService) resource);
		}
		return items;
	}

	public List<Identifier> getIdentifier() {
		try {
			return adaptedClass.getIdentifier();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Identifier", e);
		}
	}

	public IUsCoreProcedure setIdentifier(
			java.util.List<org.hl7.fhir.dstu3.model.Identifier> param) {
		adaptedClass.setIdentifier(param);
		return this;
	}

	public boolean hasIdentifier() {
		return adaptedClass.hasIdentifier();
	}

	public IUsCoreProcedure addIdentifier(
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

	public IUsCoreProcedure setLanguageElement(
			org.hl7.fhir.dstu3.model.CodeType param) {
		adaptedClass.setLanguageElement(param);
		return this;
	}

	public IUsCoreProcedure setLanguage(java.lang.String param) {
		adaptedClass.setLanguage(param);
		return this;
	}

	public CodeableConcept getOutcome() {
		try {
			return adaptedClass.getOutcome();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Outcome", e);
		}
	}

	public IUsCoreProcedure setOutcome(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.setOutcome(param);
		return this;
	}

	public boolean hasOutcome() {
		return adaptedClass.hasOutcome();
	}

	public boolean hasStatus() {
		return adaptedClass.hasStatus();
	}

	public boolean hasStatusElement() {
		return adaptedClass.hasStatusElement();
	}

	public Procedure.ProcedureStatus getStatus() {
		try {
			return adaptedClass.getStatus();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Status", e);
		}
	}

	public Enumeration<Procedure.ProcedureStatus> getStatusElement() {
		try {
			return adaptedClass.getStatusElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting StatusElement", e);
		}
	}

	public IUsCoreProcedure setStatus(
			org.hl7.fhir.dstu3.model.Procedure.ProcedureStatus param) {
		adaptedClass.setStatus(param);
		return this;
	}

	public IUsCoreProcedure setStatusElement(
			org.hl7.fhir.dstu3.model.Enumeration<org.hl7.fhir.dstu3.model.Procedure.ProcedureStatus> param) {
		adaptedClass.setStatusElement(param);
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

	public CodeableConcept getCode() {
		try {
			return adaptedClass.getCode();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Code", e);
		}
	}

	public IUsCoreProcedure setCode(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.setCode(param);
		return this;
	}

	public boolean hasCode() {
		return adaptedClass.hasCode();
	}
}