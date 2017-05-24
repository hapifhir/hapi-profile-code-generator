package org.hspc.fhir.model.stu3;

import org.hspc.fhir.model.stu3.IUsCoreCareteam;
import org.hl7.fhir.dstu3.model.CareTeam;
import java.util.List;
import org.hl7.fhir.dstu3.model.Annotation;
import org.hl7.fhir.dstu3.model.CodeType;
import java.lang.String;
import org.hl7.fhir.dstu3.model.Reference;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.Resource;
import org.hl7.fhir.dstu3.model.Encounter;
import org.hl7.fhir.dstu3.model.EpisodeOfCare;
import org.hl7.fhir.dstu3.model.Identifier;
import org.hl7.fhir.dstu3.model.IdType;
import org.hl7.fhir.dstu3.model.UriType;
import org.hl7.fhir.dstu3.model.StringType;
import org.hl7.fhir.dstu3.model.Period;
import org.hl7.fhir.dstu3.model.Enumeration;
import org.hl7.fhir.dstu3.model.*;
import ca.uhn.fhir.model.api.ExtensionDt;

public class UsCoreCareteamAdapter implements IUsCoreCareteam {

	private CareTeam adaptedClass;

	public UsCoreCareteamAdapter() {
		this.adaptedClass = new org.hl7.fhir.dstu3.model.CareTeam();
	}

	public UsCoreCareteamAdapter(org.hl7.fhir.dstu3.model.CareTeam adaptee) {
		this.adaptedClass = adaptee;
	}

	public CareTeam getAdaptee() {
		return adaptedClass;
	}

	public void setAdaptee(org.hl7.fhir.dstu3.model.CareTeam param) {
		this.adaptedClass = param;
	}

	public List<CareTeam.CareTeamParticipantComponent> getParticipant() {
		try {
			return adaptedClass.getParticipant();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Participant", e);
		}
	}

	public UsCoreCareteamAdapter setParticipant(
			java.util.List<org.hl7.fhir.dstu3.model.CareTeam.CareTeamParticipantComponent> param) {
		adaptedClass.setParticipant(param);
		return this;
	}

	public boolean hasParticipant() {
		return adaptedClass.hasParticipant();
	}

	public UsCoreCareteamAdapter addParticipant(
			org.hl7.fhir.dstu3.model.CareTeam.CareTeamParticipantComponent param) {
		adaptedClass.addParticipant(param);
		return this;
	}

	public CareTeam.CareTeamParticipantComponent addParticipant() {
		return adaptedClass.addParticipant();
	}

	public List<Annotation> getNote() {
		try {
			return adaptedClass.getNote();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Note", e);
		}
	}

	public IUsCoreCareteam setNote(
			java.util.List<org.hl7.fhir.dstu3.model.Annotation> param) {
		adaptedClass.setNote(param);
		return this;
	}

	public boolean hasNote() {
		return adaptedClass.hasNote();
	}

	public IUsCoreCareteam addNote(org.hl7.fhir.dstu3.model.Annotation param) {
		adaptedClass.addNote(param);
		return this;
	}

	public Annotation addNote() {
		return adaptedClass.addNote();
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

	public IUsCoreCareteam setLanguageElement(
			org.hl7.fhir.dstu3.model.CodeType param) {
		adaptedClass.setLanguageElement(param);
		return this;
	}

	public IUsCoreCareteam setLanguage(java.lang.String param) {
		adaptedClass.setLanguage(param);
		return this;
	}

	public boolean hasManagingOrganization() {
		return adaptedClass.hasManagingOrganization();
	}

	public List<Reference> getManagingOrganization() {
		try {
			return adaptedClass.getManagingOrganization();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ManagingOrganization", e);
		}
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

	public IUsCoreCareteam setSubject(org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setSubject(param);
		return this;
	}

	public Patient getSubjectTarget() {
		return (org.hl7.fhir.dstu3.model.Patient) adaptedClass
				.getSubjectTarget();
	}

	public IUsCoreCareteam setSubjectTarget(
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

	public IUsCoreCareteam setSubjectAdapterTarget(
			org.hspc.fhir.model.stu3.UsCorePatientAdapter param) {
		adaptedClass.setSubjectTarget(param.getAdaptee());
		return this;
	}

	public List<CodeableConcept> getReasonCode() {
		try {
			return adaptedClass.getReasonCode();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ReasonCode", e);
		}
	}

	public IUsCoreCareteam setReasonCode(
			java.util.List<org.hl7.fhir.dstu3.model.CodeableConcept> param) {
		adaptedClass.setReasonCode(param);
		return this;
	}

	public boolean hasReasonCode() {
		return adaptedClass.hasReasonCode();
	}

	public IUsCoreCareteam addReasonCode(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.addReasonCode(param);
		return this;
	}

	public CodeableConcept addReasonCode() {
		return adaptedClass.addReasonCode();
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

	public IUsCoreCareteam setContext(org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setContext(param);
		return this;
	}

	public Encounter getContextEncounterTarget() {
		return (org.hl7.fhir.dstu3.model.Encounter) adaptedClass
				.getContextTarget();
	}

	public IUsCoreCareteam setContextTarget(
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

	public IUsCoreCareteam setContextTarget(
			org.hl7.fhir.dstu3.model.EpisodeOfCare param) {
		adaptedClass.setContextTarget(param);
		return this;
	}

	public List<Resource> getContained() {
		try {
			return adaptedClass.getContained();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Contained", e);
		}
	}

	public IUsCoreCareteam setContained(
			java.util.List<org.hl7.fhir.dstu3.model.Resource> param) {
		adaptedClass.setContained(param);
		return this;
	}

	public boolean hasContained() {
		return adaptedClass.hasContained();
	}

	public IUsCoreCareteam addContained(org.hl7.fhir.dstu3.model.Resource param) {
		adaptedClass.addContained(param);
		return this;
	}

	public List<Identifier> getIdentifier() {
		try {
			return adaptedClass.getIdentifier();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Identifier", e);
		}
	}

	public IUsCoreCareteam setIdentifier(
			java.util.List<org.hl7.fhir.dstu3.model.Identifier> param) {
		adaptedClass.setIdentifier(param);
		return this;
	}

	public boolean hasIdentifier() {
		return adaptedClass.hasIdentifier();
	}

	public IUsCoreCareteam addIdentifier(
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

	public IUsCoreCareteam setIdElement(org.hl7.fhir.dstu3.model.IdType param) {
		adaptedClass.setIdElement(param);
		return this;
	}

	public IUsCoreCareteam setId(java.lang.String param) {
		adaptedClass.setId(param);
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

	public IUsCoreCareteam setImplicitRulesElement(
			org.hl7.fhir.dstu3.model.UriType param) {
		adaptedClass.setImplicitRulesElement(param);
		return this;
	}

	public IUsCoreCareteam setImplicitRules(java.lang.String param) {
		adaptedClass.setImplicitRules(param);
		return this;
	}

	public List<CodeableConcept> getCategory() {
		try {
			return adaptedClass.getCategory();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Category", e);
		}
	}

	public IUsCoreCareteam setCategory(
			java.util.List<org.hl7.fhir.dstu3.model.CodeableConcept> param) {
		adaptedClass.setCategory(param);
		return this;
	}

	public boolean hasCategory() {
		return adaptedClass.hasCategory();
	}

	public IUsCoreCareteam addCategory(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.addCategory(param);
		return this;
	}

	public CodeableConcept addCategory() {
		return adaptedClass.addCategory();
	}

	public boolean hasName() {
		return adaptedClass.hasName();
	}

	public boolean hasNameElement() {
		return adaptedClass.hasNameElement();
	}

	public StringType getNameElement() {
		try {
			return adaptedClass.getNameElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting NameElement", e);
		}
	}

	public String getName() {
		try {
			return adaptedClass.getName();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Name", e);
		}
	}

	public IUsCoreCareteam setNameElement(
			org.hl7.fhir.dstu3.model.StringType param) {
		adaptedClass.setNameElement(param);
		return this;
	}

	public IUsCoreCareteam setName(java.lang.String param) {
		adaptedClass.setName(param);
		return this;
	}

	public Period getPeriod() {
		try {
			return adaptedClass.getPeriod();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Period", e);
		}
	}

	public IUsCoreCareteam setPeriod(org.hl7.fhir.dstu3.model.Period param) {
		adaptedClass.setPeriod(param);
		return this;
	}

	public boolean hasPeriod() {
		return adaptedClass.hasPeriod();
	}

	public boolean hasStatus() {
		return adaptedClass.hasStatus();
	}

	public boolean hasStatusElement() {
		return adaptedClass.hasStatusElement();
	}

	public CareTeam.CareTeamStatus getStatus() {
		try {
			return adaptedClass.getStatus();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Status", e);
		}
	}

	public Enumeration<CareTeam.CareTeamStatus> getStatusElement() {
		try {
			return adaptedClass.getStatusElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting StatusElement", e);
		}
	}

	public IUsCoreCareteam setStatus(
			org.hl7.fhir.dstu3.model.CareTeam.CareTeamStatus param) {
		adaptedClass.setStatus(param);
		return this;
	}

	public IUsCoreCareteam setStatusElement(
			org.hl7.fhir.dstu3.model.Enumeration<org.hl7.fhir.dstu3.model.CareTeam.CareTeamStatus> param) {
		adaptedClass.setStatusElement(param);
		return this;
	}

	public boolean hasReasonReference() {
		return adaptedClass.hasReasonReference();
	}

	public List<Reference> getReasonReference() {
		try {
			return adaptedClass.getReasonReference();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ReasonReference", e);
		}
	}
}