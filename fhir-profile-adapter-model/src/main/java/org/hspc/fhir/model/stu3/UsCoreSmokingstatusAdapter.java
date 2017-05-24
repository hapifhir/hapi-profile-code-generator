package org.hspc.fhir.model.stu3;

import org.hspc.fhir.model.stu3.IUsCoreSmokingstatus;
import org.hl7.fhir.dstu3.model.Observation;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import java.util.List;
import org.hl7.fhir.dstu3.model.Type;
import org.hl7.fhir.dstu3.model.DateTimeType;
import org.hl7.fhir.dstu3.model.Period;
import org.hl7.fhir.dstu3.model.CodeType;
import java.lang.String;
import org.hl7.fhir.dstu3.model.InstantType;
import java.util.Date;
import org.hl7.fhir.dstu3.model.Reference;
import org.hl7.fhir.dstu3.model.Resource;
import org.hl7.fhir.dstu3.model.Device;
import org.hl7.fhir.dstu3.model.DeviceMetric;
import org.hl7.fhir.dstu3.model.Encounter;
import org.hl7.fhir.dstu3.model.EpisodeOfCare;
import org.hl7.fhir.dstu3.model.Specimen;
import org.hl7.fhir.dstu3.model.CarePlan;
import org.hl7.fhir.dstu3.model.DeviceRequest;
import org.hl7.fhir.dstu3.model.ImmunizationRecommendation;
import org.hl7.fhir.dstu3.model.MedicationRequest;
import org.hl7.fhir.dstu3.model.NutritionOrder;
import org.hl7.fhir.dstu3.model.ProcedureRequest;
import org.hl7.fhir.dstu3.model.ReferralRequest;
import org.hl7.fhir.dstu3.model.IdType;
import org.hl7.fhir.dstu3.model.Identifier;
import org.hl7.fhir.dstu3.model.Enumeration;
import org.hl7.fhir.dstu3.model.StringType;
import org.hl7.fhir.dstu3.model.Practitioner;
import org.hl7.fhir.dstu3.model.Organization;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.dstu3.model.RelatedPerson;
import org.hl7.fhir.dstu3.model.UriType;
import org.hl7.fhir.dstu3.model.*;
import ca.uhn.fhir.model.api.ExtensionDt;

public class UsCoreSmokingstatusAdapter implements IUsCoreSmokingstatus {

	private Observation adaptedClass;

	public UsCoreSmokingstatusAdapter() {
		this.adaptedClass = new org.hl7.fhir.dstu3.model.Observation();
	}

	public UsCoreSmokingstatusAdapter(
			org.hl7.fhir.dstu3.model.Observation adaptee) {
		this.adaptedClass = adaptee;
	}

	public Observation getAdaptee() {
		return adaptedClass;
	}

	public void setAdaptee(org.hl7.fhir.dstu3.model.Observation param) {
		this.adaptedClass = param;
	}

	public CodeableConcept getInterpretation() {
		try {
			return adaptedClass.getInterpretation();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Interpretation", e);
		}
	}

	public IUsCoreSmokingstatus setInterpretation(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.setInterpretation(param);
		return this;
	}

	public boolean hasInterpretation() {
		return adaptedClass.hasInterpretation();
	}

	public List<CodeableConcept> getCategory() {
		try {
			return adaptedClass.getCategory();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Category", e);
		}
	}

	public IUsCoreSmokingstatus setCategory(
			java.util.List<org.hl7.fhir.dstu3.model.CodeableConcept> param) {
		adaptedClass.setCategory(param);
		return this;
	}

	public boolean hasCategory() {
		return adaptedClass.hasCategory();
	}

	public IUsCoreSmokingstatus addCategory(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.addCategory(param);
		return this;
	}

	public CodeableConcept addCategory() {
		return adaptedClass.addCategory();
	}

	public List<Observation.ObservationReferenceRangeComponent> getReferenceRange() {
		try {
			return adaptedClass.getReferenceRange();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ReferenceRange", e);
		}
	}

	public UsCoreSmokingstatusAdapter setReferenceRange(
			java.util.List<org.hl7.fhir.dstu3.model.Observation.ObservationReferenceRangeComponent> param) {
		adaptedClass.setReferenceRange(param);
		return this;
	}

	public boolean hasReferenceRange() {
		return adaptedClass.hasReferenceRange();
	}

	public UsCoreSmokingstatusAdapter addReferenceRange(
			org.hl7.fhir.dstu3.model.Observation.ObservationReferenceRangeComponent param) {
		adaptedClass.addReferenceRange(param);
		return this;
	}

	public Observation.ObservationReferenceRangeComponent addReferenceRange() {
		return adaptedClass.addReferenceRange();
	}

	public Type getEffective() {
		try {
			return adaptedClass.getEffective();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Effective", e);
		}
	}

	public IUsCoreSmokingstatus setEffective(org.hl7.fhir.dstu3.model.Type param) {
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

	public IUsCoreSmokingstatus setLanguageElement(
			org.hl7.fhir.dstu3.model.CodeType param) {
		adaptedClass.setLanguageElement(param);
		return this;
	}

	public IUsCoreSmokingstatus setLanguage(java.lang.String param) {
		adaptedClass.setLanguage(param);
		return this;
	}

	public CodeableConcept getMethod() {
		try {
			return adaptedClass.getMethod();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Method", e);
		}
	}

	public IUsCoreSmokingstatus setMethod(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.setMethod(param);
		return this;
	}

	public boolean hasMethod() {
		return adaptedClass.hasMethod();
	}

	public CodeableConcept getDataAbsentReason() {
		try {
			return adaptedClass.getDataAbsentReason();
		} catch (Exception e) {
			throw new RuntimeException("Error getting DataAbsentReason", e);
		}
	}

	public IUsCoreSmokingstatus setDataAbsentReason(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.setDataAbsentReason(param);
		return this;
	}

	public boolean hasDataAbsentReason() {
		return adaptedClass.hasDataAbsentReason();
	}

	public List<Observation.ObservationComponentComponent> getComponent() {
		try {
			return adaptedClass.getComponent();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Component", e);
		}
	}

	public UsCoreSmokingstatusAdapter setComponent(
			java.util.List<org.hl7.fhir.dstu3.model.Observation.ObservationComponentComponent> param) {
		adaptedClass.setComponent(param);
		return this;
	}

	public boolean hasComponent() {
		return adaptedClass.hasComponent();
	}

	public UsCoreSmokingstatusAdapter addComponent(
			org.hl7.fhir.dstu3.model.Observation.ObservationComponentComponent param) {
		adaptedClass.addComponent(param);
		return this;
	}

	public Observation.ObservationComponentComponent addComponent() {
		return adaptedClass.addComponent();
	}

	public boolean hasIssued() {
		return adaptedClass.hasIssued();
	}

	public boolean hasIssuedElement() {
		return adaptedClass.hasIssuedElement();
	}

	public InstantType getIssuedElement() {
		try {
			return adaptedClass.getIssuedElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting IssuedElement", e);
		}
	}

	public Date getIssued() {
		try {
			return adaptedClass.getIssued();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Issued", e);
		}
	}

	public IUsCoreSmokingstatus setIssuedElement(
			org.hl7.fhir.dstu3.model.InstantType param) {
		adaptedClass.setIssuedElement(param);
		return this;
	}

	public IUsCoreSmokingstatus setIssued(java.util.Date param) {
		adaptedClass.setIssued(param);
		return this;
	}

	public Reference getDevice() {
		try {
			return adaptedClass.getDevice();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Device", e);
		}
	}

	public Resource getDeviceTarget() {
		try {
			return adaptedClass.getDeviceTarget();
		} catch (Exception e) {
			throw new RuntimeException("Error getting DeviceTarget", e);
		}
	}

	public boolean hasDevice() {
		return adaptedClass.hasDevice();
	}

	public Reference getDeviceDevice() {
		try {
			return adaptedClass.getDevice();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Device", e);
		}
	}

	public IUsCoreSmokingstatus setDevice(
			org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setDevice(param);
		return this;
	}

	public Device getDeviceDeviceTarget() {
		return (org.hl7.fhir.dstu3.model.Device) adaptedClass.getDeviceTarget();
	}

	public IUsCoreSmokingstatus setDeviceTarget(
			org.hl7.fhir.dstu3.model.Device param) {
		adaptedClass.setDeviceTarget(param);
		return this;
	}

	public Reference getDeviceDeviceMetric() {
		try {
			return adaptedClass.getDevice();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Device", e);
		}
	}

	public DeviceMetric getDeviceDeviceMetricTarget() {
		return (org.hl7.fhir.dstu3.model.DeviceMetric) adaptedClass
				.getDeviceTarget();
	}

	public IUsCoreSmokingstatus setDeviceTarget(
			org.hl7.fhir.dstu3.model.DeviceMetric param) {
		adaptedClass.setDeviceTarget(param);
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

	public IUsCoreSmokingstatus setContext(
			org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setContext(param);
		return this;
	}

	public Encounter getContextEncounterTarget() {
		return (org.hl7.fhir.dstu3.model.Encounter) adaptedClass
				.getContextTarget();
	}

	public IUsCoreSmokingstatus setContextTarget(
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

	public IUsCoreSmokingstatus setContextTarget(
			org.hl7.fhir.dstu3.model.EpisodeOfCare param) {
		adaptedClass.setContextTarget(param);
		return this;
	}

	public List<Observation.ObservationRelatedComponent> getRelated() {
		try {
			return adaptedClass.getRelated();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Related", e);
		}
	}

	public UsCoreSmokingstatusAdapter setRelated(
			java.util.List<org.hl7.fhir.dstu3.model.Observation.ObservationRelatedComponent> param) {
		adaptedClass.setRelated(param);
		return this;
	}

	public boolean hasRelated() {
		return adaptedClass.hasRelated();
	}

	public UsCoreSmokingstatusAdapter addRelated(
			org.hl7.fhir.dstu3.model.Observation.ObservationRelatedComponent param) {
		adaptedClass.addRelated(param);
		return this;
	}

	public Observation.ObservationRelatedComponent addRelated() {
		return adaptedClass.addRelated();
	}

	public boolean hasSpecimen() {
		return adaptedClass.hasSpecimen();
	}

	public Reference getSpecimen() {
		try {
			return adaptedClass.getSpecimen();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Specimen", e);
		}
	}

	public IUsCoreSmokingstatus setSpecimen(
			org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setSpecimen(param);
		return this;
	}

	public Specimen getSpecimenTarget() {
		return (org.hl7.fhir.dstu3.model.Specimen) adaptedClass
				.getSpecimenTarget();
	}

	public IUsCoreSmokingstatus setSpecimenTarget(
			org.hl7.fhir.dstu3.model.Specimen param) {
		adaptedClass.setSpecimenTarget(param);
		return this;
	}

	public CodeableConcept getBodySite() {
		try {
			return adaptedClass.getBodySite();
		} catch (Exception e) {
			throw new RuntimeException("Error getting BodySite", e);
		}
	}

	public IUsCoreSmokingstatus setBodySite(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.setBodySite(param);
		return this;
	}

	public boolean hasBodySite() {
		return adaptedClass.hasBodySite();
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

	public List<DeviceRequest> getBasedOnDeviceRequestTarget() {
		List<org.hl7.fhir.dstu3.model.DeviceRequest> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getBasedOnTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.DeviceRequest) resource);
		}
		return items;
	}

	public List<ImmunizationRecommendation> getBasedOnImmunizationRecommendationTarget() {
		List<org.hl7.fhir.dstu3.model.ImmunizationRecommendation> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getBasedOnTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.ImmunizationRecommendation) resource);
		}
		return items;
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

	public List<NutritionOrder> getBasedOnNutritionOrderTarget() {
		List<org.hl7.fhir.dstu3.model.NutritionOrder> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getBasedOnTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.NutritionOrder) resource);
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

	public IUsCoreSmokingstatus setIdElement(
			org.hl7.fhir.dstu3.model.IdType param) {
		adaptedClass.setIdElement(param);
		return this;
	}

	public IUsCoreSmokingstatus setId(java.lang.String param) {
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

	public IUsCoreSmokingstatus setIdentifier(
			java.util.List<org.hl7.fhir.dstu3.model.Identifier> param) {
		adaptedClass.setIdentifier(param);
		return this;
	}

	public boolean hasIdentifier() {
		return adaptedClass.hasIdentifier();
	}

	public IUsCoreSmokingstatus addIdentifier(
			org.hl7.fhir.dstu3.model.Identifier param) {
		adaptedClass.addIdentifier(param);
		return this;
	}

	public Identifier addIdentifier() {
		return adaptedClass.addIdentifier();
	}

	public boolean hasStatus() {
		return adaptedClass.hasStatus();
	}

	public boolean hasStatusElement() {
		return adaptedClass.hasStatusElement();
	}

	public Observation.ObservationStatus getStatus() {
		try {
			return adaptedClass.getStatus();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Status", e);
		}
	}

	public Enumeration<Observation.ObservationStatus> getStatusElement() {
		try {
			return adaptedClass.getStatusElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting StatusElement", e);
		}
	}

	public IUsCoreSmokingstatus setStatus(
			org.hl7.fhir.dstu3.model.Observation.ObservationStatus param) {
		adaptedClass.setStatus(param);
		return this;
	}

	public IUsCoreSmokingstatus setStatusElement(
			org.hl7.fhir.dstu3.model.Enumeration<org.hl7.fhir.dstu3.model.Observation.ObservationStatus> param) {
		adaptedClass.setStatusElement(param);
		return this;
	}

	public boolean hasComment() {
		return adaptedClass.hasComment();
	}

	public boolean hasCommentElement() {
		return adaptedClass.hasCommentElement();
	}

	public StringType getCommentElement() {
		try {
			return adaptedClass.getCommentElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting CommentElement", e);
		}
	}

	public String getComment() {
		try {
			return adaptedClass.getComment();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Comment", e);
		}
	}

	public IUsCoreSmokingstatus setCommentElement(
			org.hl7.fhir.dstu3.model.StringType param) {
		adaptedClass.setCommentElement(param);
		return this;
	}

	public IUsCoreSmokingstatus setComment(java.lang.String param) {
		adaptedClass.setComment(param);
		return this;
	}

	public List<Resource> getContained() {
		try {
			return adaptedClass.getContained();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Contained", e);
		}
	}

	public IUsCoreSmokingstatus setContained(
			java.util.List<org.hl7.fhir.dstu3.model.Resource> param) {
		adaptedClass.setContained(param);
		return this;
	}

	public boolean hasContained() {
		return adaptedClass.hasContained();
	}

	public IUsCoreSmokingstatus addContained(
			org.hl7.fhir.dstu3.model.Resource param) {
		adaptedClass.addContained(param);
		return this;
	}

	public CodeableConcept getCode() {
		try {
			return adaptedClass.getCode();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Code", e);
		}
	}

	public IUsCoreSmokingstatus setCode(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.setCode(param);
		return this;
	}

	public boolean hasCode() {
		return adaptedClass.hasCode();
	}

	public boolean hasPerformer() {
		return adaptedClass.hasPerformer();
	}

	public List<Practitioner> getPerformerPractitionerTarget() {
		List<org.hl7.fhir.dstu3.model.Practitioner> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getPerformerTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.Practitioner) resource);
		}
		return items;
	}

	public List<Reference> getPerformer() {
		try {
			return adaptedClass.getPerformer();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Performer", e);
		}
	}

	public List<Organization> getPerformerOrganizationTarget() {
		List<org.hl7.fhir.dstu3.model.Organization> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getPerformerTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.Organization) resource);
		}
		return items;
	}

	public List<Patient> getPerformerPatientTarget() {
		List<org.hl7.fhir.dstu3.model.Patient> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getPerformerTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.Patient) resource);
		}
		return items;
	}

	public List<RelatedPerson> getPerformerRelatedPersonTarget() {
		List<org.hl7.fhir.dstu3.model.RelatedPerson> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getPerformerTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.RelatedPerson) resource);
		}
		return items;
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

	public IUsCoreSmokingstatus setSubject(
			org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setSubject(param);
		return this;
	}

	public Patient getSubjectTarget() {
		return (org.hl7.fhir.dstu3.model.Patient) adaptedClass
				.getSubjectTarget();
	}

	public IUsCoreSmokingstatus setSubjectTarget(
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

	public IUsCoreSmokingstatus setSubjectAdapterTarget(
			org.hspc.fhir.model.stu3.UsCorePatientAdapter param) {
		adaptedClass.setSubjectTarget(param.getAdaptee());
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

	public IUsCoreSmokingstatus setImplicitRulesElement(
			org.hl7.fhir.dstu3.model.UriType param) {
		adaptedClass.setImplicitRulesElement(param);
		return this;
	}

	public IUsCoreSmokingstatus setImplicitRules(java.lang.String param) {
		adaptedClass.setImplicitRules(param);
		return this;
	}
}