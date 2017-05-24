package org.hspc.fhir.model.stu3;

import org.hspc.fhir.model.stu3.IUsCoreCareplan;
import org.hl7.fhir.dstu3.model.CarePlan;
import java.util.List;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.dstu3.model.Reference;
import org.hl7.fhir.dstu3.model.Practitioner;
import org.hl7.fhir.dstu3.model.RelatedPerson;
import org.hl7.fhir.dstu3.model.Organization;
import org.hl7.fhir.dstu3.model.CareTeam;
import org.hl7.fhir.dstu3.model.StringType;
import java.lang.String;
import org.hl7.fhir.dstu3.model.Resource;
import org.hl7.fhir.dstu3.model.Encounter;
import org.hl7.fhir.dstu3.model.EpisodeOfCare;
import org.hl7.fhir.dstu3.model.Enumeration;
import org.hl7.fhir.dstu3.model.Annotation;
import org.hl7.fhir.dstu3.model.UriType;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.PlanDefinition;
import org.hl7.fhir.dstu3.model.Questionnaire;
import org.hl7.fhir.dstu3.model.Identifier;
import org.hl7.fhir.dstu3.model.IdType;
import org.hl7.fhir.dstu3.model.Period;
import org.hl7.fhir.dstu3.model.CodeType;
import org.hl7.fhir.dstu3.model.*;
import ca.uhn.fhir.model.api.ExtensionDt;

public class UsCoreCareplanAdapter implements IUsCoreCareplan {

	private CarePlan adaptedClass;

	public UsCoreCareplanAdapter() {
		this.adaptedClass = new org.hl7.fhir.dstu3.model.CarePlan();
	}

	public UsCoreCareplanAdapter(org.hl7.fhir.dstu3.model.CarePlan adaptee) {
		this.adaptedClass = adaptee;
	}

	public CarePlan getAdaptee() {
		return adaptedClass;
	}

	public void setAdaptee(org.hl7.fhir.dstu3.model.CarePlan param) {
		this.adaptedClass = param;
	}

	public boolean hasAuthor() {
		return adaptedClass.hasAuthor();
	}

	public List<Patient> getAuthorPatientTarget() {
		List<org.hl7.fhir.dstu3.model.Patient> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getAuthorTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.Patient) resource);
		}
		return items;
	}

	public List<Reference> getAuthor() {
		try {
			return adaptedClass.getAuthor();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Author", e);
		}
	}

	public List<Practitioner> getAuthorPractitionerTarget() {
		List<org.hl7.fhir.dstu3.model.Practitioner> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getAuthorTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.Practitioner) resource);
		}
		return items;
	}

	public List<RelatedPerson> getAuthorRelatedPersonTarget() {
		List<org.hl7.fhir.dstu3.model.RelatedPerson> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getAuthorTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.RelatedPerson) resource);
		}
		return items;
	}

	public List<Organization> getAuthorOrganizationTarget() {
		List<org.hl7.fhir.dstu3.model.Organization> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getAuthorTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.Organization) resource);
		}
		return items;
	}

	public List<CareTeam> getAuthorCareTeamTarget() {
		List<org.hl7.fhir.dstu3.model.CareTeam> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getAuthorTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.CareTeam) resource);
		}
		return items;
	}

	public boolean hasTitle() {
		return adaptedClass.hasTitle();
	}

	public boolean hasTitleElement() {
		return adaptedClass.hasTitleElement();
	}

	public StringType getTitleElement() {
		try {
			return adaptedClass.getTitleElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting TitleElement", e);
		}
	}

	public String getTitle() {
		try {
			return adaptedClass.getTitle();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Title", e);
		}
	}

	public IUsCoreCareplan setTitleElement(
			org.hl7.fhir.dstu3.model.StringType param) {
		adaptedClass.setTitleElement(param);
		return this;
	}

	public IUsCoreCareplan setTitle(java.lang.String param) {
		adaptedClass.setTitle(param);
		return this;
	}

	public List<CarePlan.CarePlanActivityComponent> getActivity() {
		try {
			return adaptedClass.getActivity();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Activity", e);
		}
	}

	public UsCoreCareplanAdapter setActivity(
			java.util.List<org.hl7.fhir.dstu3.model.CarePlan.CarePlanActivityComponent> param) {
		adaptedClass.setActivity(param);
		return this;
	}

	public boolean hasActivity() {
		return adaptedClass.hasActivity();
	}

	public UsCoreCareplanAdapter addActivity(
			org.hl7.fhir.dstu3.model.CarePlan.CarePlanActivityComponent param) {
		adaptedClass.addActivity(param);
		return this;
	}

	public CarePlan.CarePlanActivityComponent addActivity() {
		return adaptedClass.addActivity();
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

	public IUsCoreCareplan setContext(org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setContext(param);
		return this;
	}

	public Encounter getContextEncounterTarget() {
		return (org.hl7.fhir.dstu3.model.Encounter) adaptedClass
				.getContextTarget();
	}

	public IUsCoreCareplan setContextTarget(
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

	public IUsCoreCareplan setContextTarget(
			org.hl7.fhir.dstu3.model.EpisodeOfCare param) {
		adaptedClass.setContextTarget(param);
		return this;
	}

	public boolean hasReplaces() {
		return adaptedClass.hasReplaces();
	}

	public List<Reference> getReplaces() {
		try {
			return adaptedClass.getReplaces();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Replaces", e);
		}
	}

	public boolean hasSupportingInfo() {
		return adaptedClass.hasSupportingInfo();
	}

	public List<Reference> getSupportingInfo() {
		try {
			return adaptedClass.getSupportingInfo();
		} catch (Exception e) {
			throw new RuntimeException("Error getting SupportingInfo", e);
		}
	}

	public boolean hasIntent() {
		return adaptedClass.hasIntent();
	}

	public boolean hasIntentElement() {
		return adaptedClass.hasIntentElement();
	}

	public CarePlan.CarePlanIntent getIntent() {
		try {
			return adaptedClass.getIntent();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Intent", e);
		}
	}

	public Enumeration<CarePlan.CarePlanIntent> getIntentElement() {
		try {
			return adaptedClass.getIntentElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting IntentElement", e);
		}
	}

	public IUsCoreCareplan setIntent(
			org.hl7.fhir.dstu3.model.CarePlan.CarePlanIntent param) {
		adaptedClass.setIntent(param);
		return this;
	}

	public IUsCoreCareplan setIntentElement(
			org.hl7.fhir.dstu3.model.Enumeration<org.hl7.fhir.dstu3.model.CarePlan.CarePlanIntent> param) {
		adaptedClass.setIntentElement(param);
		return this;
	}

	public List<Annotation> getNote() {
		try {
			return adaptedClass.getNote();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Note", e);
		}
	}

	public IUsCoreCareplan setNote(
			java.util.List<org.hl7.fhir.dstu3.model.Annotation> param) {
		adaptedClass.setNote(param);
		return this;
	}

	public boolean hasNote() {
		return adaptedClass.hasNote();
	}

	public IUsCoreCareplan addNote(org.hl7.fhir.dstu3.model.Annotation param) {
		adaptedClass.addNote(param);
		return this;
	}

	public Annotation addNote() {
		return adaptedClass.addNote();
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

	public IUsCoreCareplan setSubject(org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setSubject(param);
		return this;
	}

	public Patient getSubjectTarget() {
		return (org.hl7.fhir.dstu3.model.Patient) adaptedClass
				.getSubjectTarget();
	}

	public IUsCoreCareplan setSubjectTarget(
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

	public IUsCoreCareplan setSubjectAdapterTarget(
			org.hspc.fhir.model.stu3.UsCorePatientAdapter param) {
		adaptedClass.setSubjectTarget(param.getAdaptee());
		return this;
	}

	public boolean hasBasedOn() {
		return adaptedClass.hasBasedOn();
	}

	public List<Reference> getBasedOn() {
		try {
			return adaptedClass.getBasedOn();
		} catch (Exception e) {
			throw new RuntimeException("Error getting BasedOn", e);
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

	public IUsCoreCareplan setImplicitRulesElement(
			org.hl7.fhir.dstu3.model.UriType param) {
		adaptedClass.setImplicitRulesElement(param);
		return this;
	}

	public IUsCoreCareplan setImplicitRules(java.lang.String param) {
		adaptedClass.setImplicitRules(param);
		return this;
	}

	public boolean hasAddresses() {
		return adaptedClass.hasAddresses();
	}

	public List<Reference> getAddresses() {
		try {
			return adaptedClass.getAddresses();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Addresses", e);
		}
	}

	public boolean hasPartOf() {
		return adaptedClass.hasPartOf();
	}

	public List<Reference> getPartOf() {
		try {
			return adaptedClass.getPartOf();
		} catch (Exception e) {
			throw new RuntimeException("Error getting PartOf", e);
		}
	}

	public boolean hasGoal() {
		return adaptedClass.hasGoal();
	}

	public List<Reference> getGoal() {
		try {
			return adaptedClass.getGoal();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Goal", e);
		}
	}

	public List<CodeableConcept> getCategory() {
		try {
			return adaptedClass.getCategory();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Category", e);
		}
	}

	public IUsCoreCareplan setCategory(
			java.util.List<org.hl7.fhir.dstu3.model.CodeableConcept> param) {
		adaptedClass.setCategory(param);
		return this;
	}

	public boolean hasCategory() {
		return adaptedClass.hasCategory();
	}

	public IUsCoreCareplan addCategory(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.addCategory(param);
		return this;
	}

	public CodeableConcept addCategory() {
		return adaptedClass.addCategory();
	}

	public boolean hasCareTeam() {
		return adaptedClass.hasCareTeam();
	}

	public List<Reference> getCareTeam() {
		try {
			return adaptedClass.getCareTeam();
		} catch (Exception e) {
			throw new RuntimeException("Error getting CareTeam", e);
		}
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

	public List<Questionnaire> getDefinitionQuestionnaireTarget() {
		List<org.hl7.fhir.dstu3.model.Questionnaire> items = new java.util.ArrayList<>();
		List<org.hl7.fhir.dstu3.model.Resource> resources = adaptedClass
				.getDefinitionTarget();
		for (org.hl7.fhir.dstu3.model.Resource resource : resources) {
			items.add((org.hl7.fhir.dstu3.model.Questionnaire) resource);
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

	public IUsCoreCareplan setIdentifier(
			java.util.List<org.hl7.fhir.dstu3.model.Identifier> param) {
		adaptedClass.setIdentifier(param);
		return this;
	}

	public boolean hasIdentifier() {
		return adaptedClass.hasIdentifier();
	}

	public IUsCoreCareplan addIdentifier(
			org.hl7.fhir.dstu3.model.Identifier param) {
		adaptedClass.addIdentifier(param);
		return this;
	}

	public Identifier addIdentifier() {
		return adaptedClass.addIdentifier();
	}

	public boolean hasDescription() {
		return adaptedClass.hasDescription();
	}

	public boolean hasDescriptionElement() {
		return adaptedClass.hasDescriptionElement();
	}

	public StringType getDescriptionElement() {
		try {
			return adaptedClass.getDescriptionElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting DescriptionElement", e);
		}
	}

	public String getDescription() {
		try {
			return adaptedClass.getDescription();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Description", e);
		}
	}

	public IUsCoreCareplan setDescriptionElement(
			org.hl7.fhir.dstu3.model.StringType param) {
		adaptedClass.setDescriptionElement(param);
		return this;
	}

	public IUsCoreCareplan setDescription(java.lang.String param) {
		adaptedClass.setDescription(param);
		return this;
	}

	public boolean hasStatus() {
		return adaptedClass.hasStatus();
	}

	public boolean hasStatusElement() {
		return adaptedClass.hasStatusElement();
	}

	public CarePlan.CarePlanStatus getStatus() {
		try {
			return adaptedClass.getStatus();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Status", e);
		}
	}

	public Enumeration<CarePlan.CarePlanStatus> getStatusElement() {
		try {
			return adaptedClass.getStatusElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting StatusElement", e);
		}
	}

	public IUsCoreCareplan setStatus(
			org.hl7.fhir.dstu3.model.CarePlan.CarePlanStatus param) {
		adaptedClass.setStatus(param);
		return this;
	}

	public IUsCoreCareplan setStatusElement(
			org.hl7.fhir.dstu3.model.Enumeration<org.hl7.fhir.dstu3.model.CarePlan.CarePlanStatus> param) {
		adaptedClass.setStatusElement(param);
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

	public IUsCoreCareplan setIdElement(org.hl7.fhir.dstu3.model.IdType param) {
		adaptedClass.setIdElement(param);
		return this;
	}

	public IUsCoreCareplan setId(java.lang.String param) {
		adaptedClass.setId(param);
		return this;
	}

	public List<Resource> getContained() {
		try {
			return adaptedClass.getContained();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Contained", e);
		}
	}

	public IUsCoreCareplan setContained(
			java.util.List<org.hl7.fhir.dstu3.model.Resource> param) {
		adaptedClass.setContained(param);
		return this;
	}

	public boolean hasContained() {
		return adaptedClass.hasContained();
	}

	public IUsCoreCareplan addContained(org.hl7.fhir.dstu3.model.Resource param) {
		adaptedClass.addContained(param);
		return this;
	}

	public Period getPeriod() {
		try {
			return adaptedClass.getPeriod();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Period", e);
		}
	}

	public IUsCoreCareplan setPeriod(org.hl7.fhir.dstu3.model.Period param) {
		adaptedClass.setPeriod(param);
		return this;
	}

	public boolean hasPeriod() {
		return adaptedClass.hasPeriod();
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

	public IUsCoreCareplan setLanguageElement(
			org.hl7.fhir.dstu3.model.CodeType param) {
		adaptedClass.setLanguageElement(param);
		return this;
	}

	public IUsCoreCareplan setLanguage(java.lang.String param) {
		adaptedClass.setLanguage(param);
		return this;
	}
}