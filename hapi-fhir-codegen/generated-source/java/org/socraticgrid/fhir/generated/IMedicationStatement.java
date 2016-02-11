package org.socraticgrid.fhir.generated;

import ca.uhn.fhir.model.dstu2.resource.MedicationStatement;
import ca.uhn.fhir.model.api.ExtensionDt;
import ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt;
import ca.uhn.fhir.model.dstu2.composite.ResourceReferenceDt;
import java.util.List;
import ca.uhn.fhir.model.dstu2.composite.IdentifierDt;
import ca.uhn.fhir.model.primitive.StringDt;
import ca.uhn.fhir.model.primitive.DateTimeDt;
import java.util.Date;
import ca.uhn.fhir.model.dstu2.composite.NarrativeDt;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.model.primitive.CodeDt;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.dstu2.valueset.MedicationStatementStatusEnum;
import ca.uhn.fhir.model.primitive.BoundCodeDt;
import ca.uhn.fhir.model.dstu2.composite.PeriodDt;
import ca.uhn.fhir.model.dstu2.composite.ContainedDt;
import ca.uhn.fhir.model.primitive.BooleanDt;

public interface IMedicationStatement
{

   public MedicationStatement getAdaptee();

   public void setAdaptee(MedicationStatement param);

   public CodeableConceptDt getReasonForUseCodeableConcept();

   public IMedicationStatement setReasonForUseCodeableConcept(
         CodeableConceptDt param);

   public ResourceReferenceDt getReasonForUseReference();

   public IMedicationStatement setReasonForUseReference(
         ResourceReferenceDt param);

   public List<IdentifierDt> getIdentifier();

   public IMedicationStatement setIdentifier(List<IdentifierDt> param);

   public IMedicationStatement addIdentifier(IdentifierDt param);

   public IdentifierDt addIdentifier();

   public IdentifierDt getIdentifierFirstRep();

   public StringDt getNoteElement();

   public String getNote();

   public IMedicationStatement setNote(String param);

   public IMedicationStatement setNote(StringDt param);

   public List<MedicationStatement.Dosage> getDosage();

   public IMedicationStatement setDosage(List<MedicationStatement.Dosage> param);

   public IMedicationStatement addDosage(MedicationStatement.Dosage param);

   public MedicationStatement.Dosage addDosage();

   public MedicationStatement.Dosage getDosageFirstRep();

   public DateTimeDt getDateAssertedElement();

   public Date getDateAsserted();

   public IMedicationStatement setDateAsserted(Date param);

   public IMedicationStatement setDateAsserted(DateTimeDt param);

   public NarrativeDt getText();

   public IMedicationStatement setText(NarrativeDt param);

   public IdDt getId();

   public IMedicationStatement setId(IdDt param);

   public CodeDt getLanguage();

   public IMedicationStatement setLanguage(CodeDt param);

   public Patient getPatientResource();

   public IMedicationStatement setPatientResource(Patient param);

   public String getStatus();

   public IMedicationStatement setStatus(String param);

   public BoundCodeDt<MedicationStatementStatusEnum> getStatusElement();

   public IMedicationStatement setStatus(
         BoundCodeDt<MedicationStatementStatusEnum> param);

   public DateTimeDt getEffectiveDateTimeElement();

   public Date getEffectiveDateTime();

   public IMedicationStatement setEffectiveDateTime(DateTimeDt param);

   public IMedicationStatement setEffectiveDateTime(Date param);

   public PeriodDt getEffectivePeriod();

   public IMedicationStatement setEffectivePeriod(PeriodDt param);

   public ContainedDt getContained();

   public IMedicationStatement setContained(ContainedDt param);

   public BooleanDt getWasNotTakenElement();

   public Boolean getWasNotTaken();

   public IMedicationStatement setWasNotTaken(Boolean param);

   public IMedicationStatement setWasNotTaken(BooleanDt param);

   public CodeableConceptDt getMedicationCodeableConcept();

   public IMedicationStatement setMedicationCodeableConcept(
         CodeableConceptDt param);

   public ResourceReferenceDt getMedicationReference();

   public IMedicationStatement setMedicationReference(ResourceReferenceDt param);
}