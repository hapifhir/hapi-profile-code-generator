package org.hspc.fhir.model.stu3;

import org.hspc.fhir.model.stu3.IUsCoreDiagnosticreport;
import org.hl7.fhir.dstu3.model.DiagnosticReport;
import java.util.List;
import org.hl7.fhir.dstu3.model.Reference;
import org.hl7.fhir.dstu3.model.CarePlan;
import org.hl7.fhir.dstu3.model.ImmunizationRecommendation;
import org.hl7.fhir.dstu3.model.MedicationRequest;
import org.hl7.fhir.dstu3.model.NutritionOrder;
import org.hl7.fhir.dstu3.model.ProcedureRequest;
import org.hl7.fhir.dstu3.model.ReferralRequest;
import org.hl7.fhir.dstu3.model.Type;
import org.hl7.fhir.dstu3.model.DateTimeType;
import org.hl7.fhir.dstu3.model.Period;
import org.hl7.fhir.dstu3.model.Identifier;
import org.hl7.fhir.dstu3.model.IdType;
import java.lang.String;
import org.hl7.fhir.dstu3.model.CodeType;
import org.hl7.fhir.dstu3.model.Resource;
import org.hl7.fhir.dstu3.model.Enumeration;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.ImagingStudy;
import org.hl7.fhir.dstu3.model.ImagingManifest;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.dstu3.model.StringType;
import org.hl7.fhir.dstu3.model.InstantType;
import java.util.Date;
import org.hl7.fhir.dstu3.model.Encounter;
import org.hl7.fhir.dstu3.model.EpisodeOfCare;
import org.hl7.fhir.dstu3.model.UriType;
import org.hl7.fhir.dstu3.model.Attachment;
import org.hl7.fhir.dstu3.model.*;
import ca.uhn.fhir.model.api.ExtensionDt;

public class UsCoreDiagnosticreportAdapter implements IUsCoreDiagnosticreport {

	private DiagnosticReport adaptedClass;

	public UsCoreDiagnosticreportAdapter() {
		this.adaptedClass = new org.hl7.fhir.dstu3.model.DiagnosticReport();
	}

	public UsCoreDiagnosticreportAdapter(
			org.hl7.fhir.dstu3.model.DiagnosticReport adaptee) {
		this.adaptedClass = adaptee;
	}

	public DiagnosticReport getAdaptee() {
		return adaptedClass;
	}

	public void setAdaptee(org.hl7.fhir.dstu3.model.DiagnosticReport param) {
		this.adaptedClass = param;
	}

	public List<DiagnosticReport.DiagnosticReportImageComponent> getImage() {
		try {
			return adaptedClass.getImage();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Image", e);
		}
	}

	public UsCoreDiagnosticreportAdapter setImage(
			java.util.List<org.hl7.fhir.dstu3.model.DiagnosticReport.DiagnosticReportImageComponent> param) {
		adaptedClass.setImage(param);
		return this;
	}

	public boolean hasImage() {
		return adaptedClass.hasImage();
	}

	public UsCoreDiagnosticreportAdapter addImage(
			org.hl7.fhir.dstu3.model.DiagnosticReport.DiagnosticReportImageComponent param) {
		adaptedClass.addImage(param);
		return this;
	}

	public DiagnosticReport.DiagnosticReportImageComponent addImage() {
		return adaptedClass.addImage();
	}

	public List<DiagnosticReport.DiagnosticReportPerformerComponent> getPerformer() {
		try {
			return adaptedClass.getPerformer();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Performer", e);
		}
	}

	public UsCoreDiagnosticreportAdapter setPerformer(
			java.util.List<org.hl7.fhir.dstu3.model.DiagnosticReport.DiagnosticReportPerformerComponent> param) {
		adaptedClass.setPerformer(param);
		return this;
	}

	public boolean hasPerformer() {
		return adaptedClass.hasPerformer();
	}

	public UsCoreDiagnosticreportAdapter addPerformer(
			org.hl7.fhir.dstu3.model.DiagnosticReport.DiagnosticReportPerformerComponent param) {
		adaptedClass.addPerformer(param);
		return this;
	}

	public DiagnosticReport.DiagnosticReportPerformerComponent addPerformer() {
		return adaptedClass.addPerformer();
	}

	public boolean hasResult() {
		return adaptedClass.hasResult();
	}

	public List<Reference> getResult() {
		try {
			return adaptedClass.getResult();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Result", e);
		}
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

	public Type getEffective() {
		try {
			return adaptedClass.getEffective();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Effective", e);
		}
	}

	public IUsCoreDiagnosticreport setEffective(
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

	public List<Identifier> getIdentifier() {
		try {
			return adaptedClass.getIdentifier();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Identifier", e);
		}
	}

	public IUsCoreDiagnosticreport setIdentifier(
			java.util.List<org.hl7.fhir.dstu3.model.Identifier> param) {
		adaptedClass.setIdentifier(param);
		return this;
	}

	public boolean hasIdentifier() {
		return adaptedClass.hasIdentifier();
	}

	public IUsCoreDiagnosticreport addIdentifier(
			org.hl7.fhir.dstu3.model.Identifier param) {
		adaptedClass.addIdentifier(param);
		return this;
	}

	public Identifier addIdentifier() {
		return adaptedClass.addIdentifier();
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

	public IUsCoreDiagnosticreport setIdElement(
			org.hl7.fhir.dstu3.model.IdType param) {
		adaptedClass.setIdElement(param);
		return this;
	}

	public IUsCoreDiagnosticreport setId(java.lang.String param) {
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

	public IUsCoreDiagnosticreport setLanguageElement(
			org.hl7.fhir.dstu3.model.CodeType param) {
		adaptedClass.setLanguageElement(param);
		return this;
	}

	public IUsCoreDiagnosticreport setLanguage(java.lang.String param) {
		adaptedClass.setLanguage(param);
		return this;
	}

	public List<Resource> getContained() {
		try {
			return adaptedClass.getContained();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Contained", e);
		}
	}

	public IUsCoreDiagnosticreport setContained(
			java.util.List<org.hl7.fhir.dstu3.model.Resource> param) {
		adaptedClass.setContained(param);
		return this;
	}

	public boolean hasContained() {
		return adaptedClass.hasContained();
	}

	public IUsCoreDiagnosticreport addContained(
			org.hl7.fhir.dstu3.model.Resource param) {
		adaptedClass.addContained(param);
		return this;
	}

	public boolean hasStatus() {
		return adaptedClass.hasStatus();
	}

	public boolean hasStatusElement() {
		return adaptedClass.hasStatusElement();
	}

	public DiagnosticReport.DiagnosticReportStatus getStatus() {
		try {
			return adaptedClass.getStatus();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Status", e);
		}
	}

	public Enumeration<DiagnosticReport.DiagnosticReportStatus> getStatusElement() {
		try {
			return adaptedClass.getStatusElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting StatusElement", e);
		}
	}

	public IUsCoreDiagnosticreport setStatus(
			org.hl7.fhir.dstu3.model.DiagnosticReport.DiagnosticReportStatus param) {
		adaptedClass.setStatus(param);
		return this;
	}

	public IUsCoreDiagnosticreport setStatusElement(
			org.hl7.fhir.dstu3.model.Enumeration<org.hl7.fhir.dstu3.model.DiagnosticReport.DiagnosticReportStatus> param) {
		adaptedClass.setStatusElement(param);
		return this;
	}

	public CodeableConcept getCategory() {
		try {
			return adaptedClass.getCategory();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Category", e);
		}
	}

	public IUsCoreDiagnosticreport setCategory(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.setCategory(param);
		return this;
	}

	public boolean hasCategory() {
		return adaptedClass.hasCategory();
	}

	public boolean hasImagingStudy() {
		return adaptedClass.hasImagingStudy();
	}

	public List<ImagingStudy> getImagingStudyImagingStudyTarget() {
		List<org.hl7.fhir.dstu3.model.ImagingStudy> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getImagingStudyTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.ImagingStudy) resource);
		}
		return items;
	}

	public List<Reference> getImagingStudy() {
		try {
			return adaptedClass.getImagingStudy();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ImagingStudy", e);
		}
	}

	public List<ImagingManifest> getImagingStudyImagingManifestTarget() {
		List<org.hl7.fhir.dstu3.model.ImagingManifest> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getImagingStudyTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.ImagingManifest) resource);
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

	public IUsCoreDiagnosticreport setSubject(
			org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setSubject(param);
		return this;
	}

	public Patient getSubjectTarget() {
		return (org.hl7.fhir.dstu3.model.Patient) adaptedClass
				.getSubjectTarget();
	}

	public IUsCoreDiagnosticreport setSubjectTarget(
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

	public IUsCoreDiagnosticreport setSubjectAdapterTarget(
			org.hspc.fhir.model.stu3.UsCorePatientAdapter param) {
		adaptedClass.setSubjectTarget(param.getAdaptee());
		return this;
	}

	public boolean hasConclusion() {
		return adaptedClass.hasConclusion();
	}

	public boolean hasConclusionElement() {
		return adaptedClass.hasConclusionElement();
	}

	public StringType getConclusionElement() {
		try {
			return adaptedClass.getConclusionElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ConclusionElement", e);
		}
	}

	public String getConclusion() {
		try {
			return adaptedClass.getConclusion();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Conclusion", e);
		}
	}

	public IUsCoreDiagnosticreport setConclusionElement(
			org.hl7.fhir.dstu3.model.StringType param) {
		adaptedClass.setConclusionElement(param);
		return this;
	}

	public IUsCoreDiagnosticreport setConclusion(java.lang.String param) {
		adaptedClass.setConclusion(param);
		return this;
	}

	public List<CodeableConcept> getCodedDiagnosis() {
		try {
			return adaptedClass.getCodedDiagnosis();
		} catch (Exception e) {
			throw new RuntimeException("Error getting CodedDiagnosis", e);
		}
	}

	public IUsCoreDiagnosticreport setCodedDiagnosis(
			java.util.List<org.hl7.fhir.dstu3.model.CodeableConcept> param) {
		adaptedClass.setCodedDiagnosis(param);
		return this;
	}

	public boolean hasCodedDiagnosis() {
		return adaptedClass.hasCodedDiagnosis();
	}

	public IUsCoreDiagnosticreport addCodedDiagnosis(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.addCodedDiagnosis(param);
		return this;
	}

	public CodeableConcept addCodedDiagnosis() {
		return adaptedClass.addCodedDiagnosis();
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

	public IUsCoreDiagnosticreport setIssuedElement(
			org.hl7.fhir.dstu3.model.InstantType param) {
		adaptedClass.setIssuedElement(param);
		return this;
	}

	public IUsCoreDiagnosticreport setIssued(java.util.Date param) {
		adaptedClass.setIssued(param);
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

	public IUsCoreDiagnosticreport setContext(
			org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setContext(param);
		return this;
	}

	public Encounter getContextEncounterTarget() {
		return (org.hl7.fhir.dstu3.model.Encounter) adaptedClass
				.getContextTarget();
	}

	public IUsCoreDiagnosticreport setContextTarget(
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

	public IUsCoreDiagnosticreport setContextTarget(
			org.hl7.fhir.dstu3.model.EpisodeOfCare param) {
		adaptedClass.setContextTarget(param);
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

	public IUsCoreDiagnosticreport setImplicitRulesElement(
			org.hl7.fhir.dstu3.model.UriType param) {
		adaptedClass.setImplicitRulesElement(param);
		return this;
	}

	public IUsCoreDiagnosticreport setImplicitRules(java.lang.String param) {
		adaptedClass.setImplicitRules(param);
		return this;
	}

	public boolean hasSpecimen() {
		return adaptedClass.hasSpecimen();
	}

	public List<Reference> getSpecimen() {
		try {
			return adaptedClass.getSpecimen();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Specimen", e);
		}
	}

	public CodeableConcept getCode() {
		try {
			return adaptedClass.getCode();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Code", e);
		}
	}

	public IUsCoreDiagnosticreport setCode(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.setCode(param);
		return this;
	}

	public boolean hasCode() {
		return adaptedClass.hasCode();
	}

	public List<Attachment> getPresentedForm() {
		try {
			return adaptedClass.getPresentedForm();
		} catch (Exception e) {
			throw new RuntimeException("Error getting PresentedForm", e);
		}
	}

	public IUsCoreDiagnosticreport setPresentedForm(
			java.util.List<org.hl7.fhir.dstu3.model.Attachment> param) {
		adaptedClass.setPresentedForm(param);
		return this;
	}

	public boolean hasPresentedForm() {
		return adaptedClass.hasPresentedForm();
	}

	public IUsCoreDiagnosticreport addPresentedForm(
			org.hl7.fhir.dstu3.model.Attachment param) {
		adaptedClass.addPresentedForm(param);
		return this;
	}

	public Attachment addPresentedForm() {
		return adaptedClass.addPresentedForm();
	}
}