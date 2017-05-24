package org.hspc.fhir.model.stu3;

import org.hl7.fhir.dstu3.model.CareTeam;
import org.hl7.fhir.dstu3.model.*;
import ca.uhn.fhir.model.api.ExtensionDt;
import java.util.List;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.Annotation;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.CodeType;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import java.lang.String;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.Reference;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.Resource;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.Encounter;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.EpisodeOfCare;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.Identifier;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.IdType;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.UriType;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.StringType;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.Period;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.Enumeration;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.*;

public interface IUsCoreCareteam {

	public CareTeam getAdaptee();

	public void setAdaptee(org.hl7.fhir.dstu3.model.CareTeam param);

	public List<CareTeam.CareTeamParticipantComponent> getParticipant();

	public UsCoreCareteamAdapter setParticipant(
			java.util.List<org.hl7.fhir.dstu3.model.CareTeam.CareTeamParticipantComponent> param);

	public boolean hasParticipant();

	public UsCoreCareteamAdapter addParticipant(
			org.hl7.fhir.dstu3.model.CareTeam.CareTeamParticipantComponent param);

	public CareTeam.CareTeamParticipantComponent addParticipant();

	public List<Annotation> getNote();

	public IUsCoreCareteam setNote(
			java.util.List<org.hl7.fhir.dstu3.model.Annotation> param);

	public boolean hasNote();

	public IUsCoreCareteam addNote(org.hl7.fhir.dstu3.model.Annotation param);

	public Annotation addNote();

	public boolean hasLanguage();

	public boolean hasLanguageElement();

	public CodeType getLanguageElement();

	public String getLanguage();

	public IUsCoreCareteam setLanguageElement(
			org.hl7.fhir.dstu3.model.CodeType param);

	public IUsCoreCareteam setLanguage(java.lang.String param);

	public boolean hasManagingOrganization();

	public List<Reference> getManagingOrganization();

	public boolean hasSubject();

	public Reference getSubject();

	public IUsCoreCareteam setSubject(org.hl7.fhir.dstu3.model.Reference param);

	public Patient getSubjectTarget();

	public IUsCoreCareteam setSubjectTarget(
			org.hl7.fhir.dstu3.model.Patient param);

	public UsCorePatientAdapter getSubjectAdapterTarget();

	public IUsCoreCareteam setSubjectAdapterTarget(
			org.hspc.fhir.model.stu3.UsCorePatientAdapter param);

	public List<CodeableConcept> getReasonCode();

	public IUsCoreCareteam setReasonCode(
			java.util.List<org.hl7.fhir.dstu3.model.CodeableConcept> param);

	public boolean hasReasonCode();

	public IUsCoreCareteam addReasonCode(
			org.hl7.fhir.dstu3.model.CodeableConcept param);

	public CodeableConcept addReasonCode();

	public Reference getContext();

	public Resource getContextTarget();

	public boolean hasContext();

	public Reference getContextEncounter();

	public IUsCoreCareteam setContext(org.hl7.fhir.dstu3.model.Reference param);

	public Encounter getContextEncounterTarget();

	public IUsCoreCareteam setContextTarget(
			org.hl7.fhir.dstu3.model.Encounter param);

	public Reference getContextEpisodeOfCare();

	public EpisodeOfCare getContextEpisodeOfCareTarget();

	public IUsCoreCareteam setContextTarget(
			org.hl7.fhir.dstu3.model.EpisodeOfCare param);

	public List<Resource> getContained();

	public IUsCoreCareteam setContained(
			java.util.List<org.hl7.fhir.dstu3.model.Resource> param);

	public boolean hasContained();

	public IUsCoreCareteam addContained(org.hl7.fhir.dstu3.model.Resource param);

	public List<Identifier> getIdentifier();

	public IUsCoreCareteam setIdentifier(
			java.util.List<org.hl7.fhir.dstu3.model.Identifier> param);

	public boolean hasIdentifier();

	public IUsCoreCareteam addIdentifier(
			org.hl7.fhir.dstu3.model.Identifier param);

	public Identifier addIdentifier();

	public boolean hasId();

	public boolean hasIdElement();

	public IdType getIdElement();

	public String getId();

	public IUsCoreCareteam setIdElement(org.hl7.fhir.dstu3.model.IdType param);

	public IUsCoreCareteam setId(java.lang.String param);

	public boolean hasImplicitRules();

	public boolean hasImplicitRulesElement();

	public UriType getImplicitRulesElement();

	public String getImplicitRules();

	public IUsCoreCareteam setImplicitRulesElement(
			org.hl7.fhir.dstu3.model.UriType param);

	public IUsCoreCareteam setImplicitRules(java.lang.String param);

	public List<CodeableConcept> getCategory();

	public IUsCoreCareteam setCategory(
			java.util.List<org.hl7.fhir.dstu3.model.CodeableConcept> param);

	public boolean hasCategory();

	public IUsCoreCareteam addCategory(
			org.hl7.fhir.dstu3.model.CodeableConcept param);

	public CodeableConcept addCategory();

	public boolean hasName();

	public boolean hasNameElement();

	public StringType getNameElement();

	public String getName();

	public IUsCoreCareteam setNameElement(
			org.hl7.fhir.dstu3.model.StringType param);

	public IUsCoreCareteam setName(java.lang.String param);

	public Period getPeriod();

	public IUsCoreCareteam setPeriod(org.hl7.fhir.dstu3.model.Period param);

	public boolean hasPeriod();

	public boolean hasStatus();

	public boolean hasStatusElement();

	public CareTeam.CareTeamStatus getStatus();

	public Enumeration<CareTeam.CareTeamStatus> getStatusElement();

	public IUsCoreCareteam setStatus(
			org.hl7.fhir.dstu3.model.CareTeam.CareTeamStatus param);

	public IUsCoreCareteam setStatusElement(
			org.hl7.fhir.dstu3.model.Enumeration<org.hl7.fhir.dstu3.model.CareTeam.CareTeamStatus> param);

	public boolean hasReasonReference();

	public List<Reference> getReasonReference();
}