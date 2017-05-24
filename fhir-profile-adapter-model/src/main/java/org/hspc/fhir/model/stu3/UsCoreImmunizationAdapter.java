package org.hspc.fhir.model.stu3;

import org.hspc.fhir.model.stu3.IUsCoreImmunization;
import org.hl7.fhir.dstu3.model.Immunization;
import java.util.List;
import org.hl7.fhir.dstu3.model.SimpleQuantity;
import org.hl7.fhir.dstu3.model.Identifier;
import org.hl7.fhir.dstu3.model.StringType;
import java.lang.String;
import org.hl7.fhir.dstu3.model.Reference;
import org.hl7.fhir.dstu3.model.Encounter;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.Organization;
import org.hl7.fhir.dstu3.model.UriType;
import org.hl7.fhir.dstu3.model.IdType;
import org.hl7.fhir.dstu3.model.Resource;
import org.hl7.fhir.dstu3.model.Annotation;
import org.hl7.fhir.dstu3.model.BooleanType;
import java.lang.Boolean;
import org.hl7.fhir.dstu3.model.DateTimeType;
import java.util.Date;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.dstu3.model.DateType;
import org.hl7.fhir.dstu3.model.Enumeration;
import org.hl7.fhir.dstu3.model.CodeType;
import org.hl7.fhir.dstu3.model.Location;
import org.hl7.fhir.dstu3.model.*;
import ca.uhn.fhir.model.api.ExtensionDt;

public class UsCoreImmunizationAdapter implements IUsCoreImmunization {

	private Immunization adaptedClass;

	public UsCoreImmunizationAdapter() {
		this.adaptedClass = new org.hl7.fhir.dstu3.model.Immunization();
	}

	public UsCoreImmunizationAdapter(
			org.hl7.fhir.dstu3.model.Immunization adaptee) {
		this.adaptedClass = adaptee;
	}

	public Immunization getAdaptee() {
		return adaptedClass;
	}

	public void setAdaptee(org.hl7.fhir.dstu3.model.Immunization param) {
		this.adaptedClass = param;
	}

	public List<Immunization.ImmunizationVaccinationProtocolComponent> getVaccinationProtocol() {
		try {
			return adaptedClass.getVaccinationProtocol();
		} catch (Exception e) {
			throw new RuntimeException("Error getting VaccinationProtocol", e);
		}
	}

	public UsCoreImmunizationAdapter setVaccinationProtocol(
			java.util.List<org.hl7.fhir.dstu3.model.Immunization.ImmunizationVaccinationProtocolComponent> param) {
		adaptedClass.setVaccinationProtocol(param);
		return this;
	}

	public boolean hasVaccinationProtocol() {
		return adaptedClass.hasVaccinationProtocol();
	}

	public UsCoreImmunizationAdapter addVaccinationProtocol(
			org.hl7.fhir.dstu3.model.Immunization.ImmunizationVaccinationProtocolComponent param) {
		adaptedClass.addVaccinationProtocol(param);
		return this;
	}

	public Immunization.ImmunizationVaccinationProtocolComponent addVaccinationProtocol() {
		return adaptedClass.addVaccinationProtocol();
	}

	public SimpleQuantity getDoseQuantity() {
		try {
			return adaptedClass.getDoseQuantity();
		} catch (Exception e) {
			throw new RuntimeException("Error getting DoseQuantity", e);
		}
	}

	public IUsCoreImmunization setDoseQuantity(
			org.hl7.fhir.dstu3.model.SimpleQuantity param) {
		adaptedClass.setDoseQuantity(param);
		return this;
	}

	public boolean hasDoseQuantity() {
		return adaptedClass.hasDoseQuantity();
	}

	public List<Identifier> getIdentifier() {
		try {
			return adaptedClass.getIdentifier();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Identifier", e);
		}
	}

	public IUsCoreImmunization setIdentifier(
			java.util.List<org.hl7.fhir.dstu3.model.Identifier> param) {
		adaptedClass.setIdentifier(param);
		return this;
	}

	public boolean hasIdentifier() {
		return adaptedClass.hasIdentifier();
	}

	public IUsCoreImmunization addIdentifier(
			org.hl7.fhir.dstu3.model.Identifier param) {
		adaptedClass.addIdentifier(param);
		return this;
	}

	public Identifier addIdentifier() {
		return adaptedClass.addIdentifier();
	}

	public boolean hasLotNumber() {
		return adaptedClass.hasLotNumber();
	}

	public boolean hasLotNumberElement() {
		return adaptedClass.hasLotNumberElement();
	}

	public StringType getLotNumberElement() {
		try {
			return adaptedClass.getLotNumberElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting LotNumberElement", e);
		}
	}

	public String getLotNumber() {
		try {
			return adaptedClass.getLotNumber();
		} catch (Exception e) {
			throw new RuntimeException("Error getting LotNumber", e);
		}
	}

	public IUsCoreImmunization setLotNumberElement(
			org.hl7.fhir.dstu3.model.StringType param) {
		adaptedClass.setLotNumberElement(param);
		return this;
	}

	public IUsCoreImmunization setLotNumber(java.lang.String param) {
		adaptedClass.setLotNumber(param);
		return this;
	}

	public List<Immunization.ImmunizationReactionComponent> getReaction() {
		try {
			return adaptedClass.getReaction();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Reaction", e);
		}
	}

	public UsCoreImmunizationAdapter setReaction(
			java.util.List<org.hl7.fhir.dstu3.model.Immunization.ImmunizationReactionComponent> param) {
		adaptedClass.setReaction(param);
		return this;
	}

	public boolean hasReaction() {
		return adaptedClass.hasReaction();
	}

	public UsCoreImmunizationAdapter addReaction(
			org.hl7.fhir.dstu3.model.Immunization.ImmunizationReactionComponent param) {
		adaptedClass.addReaction(param);
		return this;
	}

	public Immunization.ImmunizationReactionComponent addReaction() {
		return adaptedClass.addReaction();
	}

	public List<Immunization.ImmunizationPractitionerComponent> getPractitioner() {
		try {
			return adaptedClass.getPractitioner();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Practitioner", e);
		}
	}

	public UsCoreImmunizationAdapter setPractitioner(
			java.util.List<org.hl7.fhir.dstu3.model.Immunization.ImmunizationPractitionerComponent> param) {
		adaptedClass.setPractitioner(param);
		return this;
	}

	public boolean hasPractitioner() {
		return adaptedClass.hasPractitioner();
	}

	public UsCoreImmunizationAdapter addPractitioner(
			org.hl7.fhir.dstu3.model.Immunization.ImmunizationPractitionerComponent param) {
		adaptedClass.addPractitioner(param);
		return this;
	}

	public Immunization.ImmunizationPractitionerComponent addPractitioner() {
		return adaptedClass.addPractitioner();
	}

	public boolean hasEncounter() {
		return adaptedClass.hasEncounter();
	}

	public Reference getEncounter() {
		try {
			return adaptedClass.getEncounter();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Encounter", e);
		}
	}

	public IUsCoreImmunization setEncounter(
			org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setEncounter(param);
		return this;
	}

	public Encounter getEncounterTarget() {
		return (org.hl7.fhir.dstu3.model.Encounter) adaptedClass
				.getEncounterTarget();
	}

	public IUsCoreImmunization setEncounterTarget(
			org.hl7.fhir.dstu3.model.Encounter param) {
		adaptedClass.setEncounterTarget(param);
		return this;
	}

	public CodeableConcept getRoute() {
		try {
			return adaptedClass.getRoute();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Route", e);
		}
	}

	public IUsCoreImmunization setRoute(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.setRoute(param);
		return this;
	}

	public boolean hasRoute() {
		return adaptedClass.hasRoute();
	}

	public boolean hasManufacturer() {
		return adaptedClass.hasManufacturer();
	}

	public Reference getManufacturer() {
		try {
			return adaptedClass.getManufacturer();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Manufacturer", e);
		}
	}

	public IUsCoreImmunization setManufacturer(
			org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setManufacturer(param);
		return this;
	}

	public Organization getManufacturerTarget() {
		return (org.hl7.fhir.dstu3.model.Organization) adaptedClass
				.getManufacturerTarget();
	}

	public IUsCoreImmunization setManufacturerTarget(
			org.hl7.fhir.dstu3.model.Organization param) {
		adaptedClass.setManufacturerTarget(param);
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

	public IUsCoreImmunization setImplicitRulesElement(
			org.hl7.fhir.dstu3.model.UriType param) {
		adaptedClass.setImplicitRulesElement(param);
		return this;
	}

	public IUsCoreImmunization setImplicitRules(java.lang.String param) {
		adaptedClass.setImplicitRules(param);
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

	public IUsCoreImmunization setIdElement(
			org.hl7.fhir.dstu3.model.IdType param) {
		adaptedClass.setIdElement(param);
		return this;
	}

	public IUsCoreImmunization setId(java.lang.String param) {
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

	public IUsCoreImmunization setContained(
			java.util.List<org.hl7.fhir.dstu3.model.Resource> param) {
		adaptedClass.setContained(param);
		return this;
	}

	public boolean hasContained() {
		return adaptedClass.hasContained();
	}

	public IUsCoreImmunization addContained(
			org.hl7.fhir.dstu3.model.Resource param) {
		adaptedClass.addContained(param);
		return this;
	}

	public List<Annotation> getNote() {
		try {
			return adaptedClass.getNote();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Note", e);
		}
	}

	public IUsCoreImmunization setNote(
			java.util.List<org.hl7.fhir.dstu3.model.Annotation> param) {
		adaptedClass.setNote(param);
		return this;
	}

	public boolean hasNote() {
		return adaptedClass.hasNote();
	}

	public IUsCoreImmunization addNote(org.hl7.fhir.dstu3.model.Annotation param) {
		adaptedClass.addNote(param);
		return this;
	}

	public Annotation addNote() {
		return adaptedClass.addNote();
	}

	public boolean hasNotGiven() {
		return adaptedClass.hasNotGiven();
	}

	public boolean hasNotGivenElement() {
		return adaptedClass.hasNotGivenElement();
	}

	public BooleanType getNotGivenElement() {
		try {
			return adaptedClass.getNotGivenElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting NotGivenElement", e);
		}
	}

	public Boolean getNotGiven() {
		try {
			return adaptedClass.getNotGiven();
		} catch (Exception e) {
			throw new RuntimeException("Error getting NotGiven", e);
		}
	}

	public IUsCoreImmunization setNotGivenElement(
			org.hl7.fhir.dstu3.model.BooleanType param) {
		adaptedClass.setNotGivenElement(param);
		return this;
	}

	public IUsCoreImmunization setNotGiven(java.lang.Boolean param) {
		adaptedClass.setNotGiven(param);
		return this;
	}

	public boolean hasDate() {
		return adaptedClass.hasDate();
	}

	public boolean hasDateElement() {
		return adaptedClass.hasDateElement();
	}

	public DateTimeType getDateElement() {
		try {
			return adaptedClass.getDateElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting DateElement", e);
		}
	}

	public Date getDate() {
		try {
			return adaptedClass.getDate();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Date", e);
		}
	}

	public IUsCoreImmunization setDateElement(
			org.hl7.fhir.dstu3.model.DateTimeType param) {
		adaptedClass.setDateElement(param);
		return this;
	}

	public IUsCoreImmunization setDate(java.util.Date param) {
		adaptedClass.setDate(param);
		return this;
	}

	public boolean hasPrimarySource() {
		return adaptedClass.hasPrimarySource();
	}

	public boolean hasPrimarySourceElement() {
		return adaptedClass.hasPrimarySourceElement();
	}

	public BooleanType getPrimarySourceElement() {
		try {
			return adaptedClass.getPrimarySourceElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting PrimarySourceElement", e);
		}
	}

	public Boolean getPrimarySource() {
		try {
			return adaptedClass.getPrimarySource();
		} catch (Exception e) {
			throw new RuntimeException("Error getting PrimarySource", e);
		}
	}

	public IUsCoreImmunization setPrimarySourceElement(
			org.hl7.fhir.dstu3.model.BooleanType param) {
		adaptedClass.setPrimarySourceElement(param);
		return this;
	}

	public IUsCoreImmunization setPrimarySource(java.lang.Boolean param) {
		adaptedClass.setPrimarySource(param);
		return this;
	}

	public boolean hasPatient() {
		return adaptedClass.hasPatient();
	}

	public Reference getPatient() {
		try {
			return adaptedClass.getPatient();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Patient", e);
		}
	}

	public IUsCoreImmunization setPatient(
			org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setPatient(param);
		return this;
	}

	public Patient getPatientTarget() {
		return (org.hl7.fhir.dstu3.model.Patient) adaptedClass
				.getPatientTarget();
	}

	public IUsCoreImmunization setPatientTarget(
			org.hl7.fhir.dstu3.model.Patient param) {
		adaptedClass.setPatientTarget(param);
		return this;
	}

	public UsCorePatientAdapter getPatientAdapterTarget() {
		if (adaptedClass.getPatient().getResource() instanceof org.hl7.fhir.dstu3.model.Patient) {
			org.hspc.fhir.model.stu3.UsCorePatientAdapter profiledType = new org.hspc.fhir.model.stu3.UsCorePatientAdapter();
			profiledType
					.setAdaptee((org.hl7.fhir.dstu3.model.Patient) adaptedClass
							.getPatient().getResource());
			return profiledType;
		} else {
			return null;
		}
	}

	public IUsCoreImmunization setPatientAdapterTarget(
			org.hspc.fhir.model.stu3.UsCorePatientAdapter param) {
		adaptedClass.setPatientTarget(param.getAdaptee());
		return this;
	}

	public Immunization.ImmunizationExplanationComponent getExplanation() {
		try {
			return adaptedClass.getExplanation();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Explanation", e);
		}
	}

	public UsCoreImmunizationAdapter setExplanation(
			org.hl7.fhir.dstu3.model.Immunization.ImmunizationExplanationComponent param) {
		adaptedClass.setExplanation(param);
		return this;
	}

	public boolean hasExplanation() {
		return adaptedClass.hasExplanation();
	}

	public boolean hasExpirationDate() {
		return adaptedClass.hasExpirationDate();
	}

	public boolean hasExpirationDateElement() {
		return adaptedClass.hasExpirationDateElement();
	}

	public DateType getExpirationDateElement() {
		try {
			return adaptedClass.getExpirationDateElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ExpirationDateElement", e);
		}
	}

	public Date getExpirationDate() {
		try {
			return adaptedClass.getExpirationDate();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ExpirationDate", e);
		}
	}

	public IUsCoreImmunization setExpirationDateElement(
			org.hl7.fhir.dstu3.model.DateType param) {
		adaptedClass.setExpirationDateElement(param);
		return this;
	}

	public IUsCoreImmunization setExpirationDate(java.util.Date param) {
		adaptedClass.setExpirationDate(param);
		return this;
	}

	public boolean hasStatus() {
		return adaptedClass.hasStatus();
	}

	public boolean hasStatusElement() {
		return adaptedClass.hasStatusElement();
	}

	public Immunization.ImmunizationStatus getStatus() {
		try {
			return adaptedClass.getStatus();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Status", e);
		}
	}

	public Enumeration<Immunization.ImmunizationStatus> getStatusElement() {
		try {
			return adaptedClass.getStatusElement();
		} catch (Exception e) {
			throw new RuntimeException("Error getting StatusElement", e);
		}
	}

	public IUsCoreImmunization setStatus(
			org.hl7.fhir.dstu3.model.Immunization.ImmunizationStatus param) {
		adaptedClass.setStatus(param);
		return this;
	}

	public IUsCoreImmunization setStatusElement(
			org.hl7.fhir.dstu3.model.Enumeration<org.hl7.fhir.dstu3.model.Immunization.ImmunizationStatus> param) {
		adaptedClass.setStatusElement(param);
		return this;
	}

	public CodeableConcept getVaccineCode() {
		try {
			return adaptedClass.getVaccineCode();
		} catch (Exception e) {
			throw new RuntimeException("Error getting VaccineCode", e);
		}
	}

	public IUsCoreImmunization setVaccineCode(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.setVaccineCode(param);
		return this;
	}

	public boolean hasVaccineCode() {
		return adaptedClass.hasVaccineCode();
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

	public IUsCoreImmunization setLanguageElement(
			org.hl7.fhir.dstu3.model.CodeType param) {
		adaptedClass.setLanguageElement(param);
		return this;
	}

	public IUsCoreImmunization setLanguage(java.lang.String param) {
		adaptedClass.setLanguage(param);
		return this;
	}

	public CodeableConcept getReportOrigin() {
		try {
			return adaptedClass.getReportOrigin();
		} catch (Exception e) {
			throw new RuntimeException("Error getting ReportOrigin", e);
		}
	}

	public IUsCoreImmunization setReportOrigin(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.setReportOrigin(param);
		return this;
	}

	public boolean hasReportOrigin() {
		return adaptedClass.hasReportOrigin();
	}

	public CodeableConcept getSite() {
		try {
			return adaptedClass.getSite();
		} catch (Exception e) {
			throw new RuntimeException("Error getting Site", e);
		}
	}

	public IUsCoreImmunization setSite(
			org.hl7.fhir.dstu3.model.CodeableConcept param) {
		adaptedClass.setSite(param);
		return this;
	}

	public boolean hasSite() {
		return adaptedClass.hasSite();
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

	public IUsCoreImmunization setLocation(
			org.hl7.fhir.dstu3.model.Reference param) {
		adaptedClass.setLocation(param);
		return this;
	}

	public Location getLocationTarget() {
		return (org.hl7.fhir.dstu3.model.Location) adaptedClass
				.getLocationTarget();
	}

	public IUsCoreImmunization setLocationTarget(
			org.hl7.fhir.dstu3.model.Location param) {
		adaptedClass.setLocationTarget(param);
		return this;
	}
}