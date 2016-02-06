package org.socraticgrid.fhir.generated;

import ca.uhn.fhir.model.dstu2.resource.MedicationDispense;
import ca.uhn.fhir.model.api.ExtensionDt;
import ca.uhn.fhir.model.dstu2.composite.SimpleQuantityDt;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.model.primitive.IntegerDt;
import ca.uhn.fhir.model.primitive.StringDt;
import ca.uhn.fhir.model.primitive.DateTimeDt;
import java.util.Date;
import java.util.List;
import org.socraticgrid.fhir.generated.QICoreMedicationDispenseDosageInstruction;
import ca.uhn.fhir.model.dstu2.composite.PeriodDt;
import ca.uhn.fhir.model.dstu2.composite.ContainedDt;
import ca.uhn.fhir.model.dstu2.valueset.MedicationDispenseStatusEnum;
import ca.uhn.fhir.model.primitive.BoundCodeDt;
import ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt;
import ca.uhn.fhir.model.dstu2.composite.IdentifierDt;
import ca.uhn.fhir.model.dstu2.composite.NarrativeDt;
import ca.uhn.fhir.model.primitive.CodeDt;
import ca.uhn.fhir.model.dstu2.composite.ResourceReferenceDt;

public interface IQICoreMedicationDispense
{

   public MedicationDispense getAdaptee();

   public void setAdaptee(MedicationDispense param);

   public SimpleQuantityDt getQuantity();

   public IQICoreMedicationDispense setQuantity(SimpleQuantityDt param);

   public IdDt getId();

   public IQICoreMedicationDispense setId(IdDt param);

   public IntegerDt getRefillsRemaining();

   public IQICoreMedicationDispense setRefillsRemaining(IntegerDt param);

   public QICorePractitionerAdapter getDispenserResource();

   public IQICoreMedicationDispense setDispenserResource(
         QICorePractitionerAdapter param);

   public StringDt getNoteElement();

   public String getNote();

   public IQICoreMedicationDispense setNote(String param);

   public IQICoreMedicationDispense setNote(StringDt param);

   public DateTimeDt getWhenPreparedElement();

   public Date getWhenPrepared();

   public IQICoreMedicationDispense setWhenPrepared(Date param);

   public IQICoreMedicationDispense setWhenPrepared(DateTimeDt param);

   public List<QICoreMedicationDispenseDosageInstruction> getDosageInstruction();

   public IQICoreMedicationDispense setDosageInstruction(
         List<QICoreMedicationDispenseDosageInstruction> param);

   public IQICoreMedicationDispense addDosageInstruction(
         QICoreMedicationDispenseDosageInstruction param);

   public QICoreMedicationDispenseDosageInstruction addDosageInstruction();

   public MedicationDispense.Substitution getSubstitution();

   public IQICoreMedicationDispense setSubstitution(
         MedicationDispense.Substitution param);

   public PeriodDt getValidityPeriod();

   public IQICoreMedicationDispense setValidityPeriod(PeriodDt param);

   public ContainedDt getContained();

   public IQICoreMedicationDispense setContained(ContainedDt param);

   public String getStatus();

   public IQICoreMedicationDispense setStatus(String param);

   public BoundCodeDt<MedicationDispenseStatusEnum> getStatusElement();

   public IQICoreMedicationDispense setStatus(
         BoundCodeDt<MedicationDispenseStatusEnum> param);

   public CodeableConceptDt getType();

   public IQICoreMedicationDispense setType(CodeableConceptDt param);

   public SimpleQuantityDt getDaysSupply();

   public IQICoreMedicationDispense setDaysSupply(SimpleQuantityDt param);

   public DateTimeDt getWhenHandedOverElement();

   public Date getWhenHandedOver();

   public IQICoreMedicationDispense setWhenHandedOver(Date param);

   public IQICoreMedicationDispense setWhenHandedOver(DateTimeDt param);

   public IdentifierDt getIdentifier();

   public IQICoreMedicationDispense setIdentifier(IdentifierDt param);

   public QICoreLocationAdapter getDestinationResource();

   public IQICoreMedicationDispense setDestinationResource(
         QICoreLocationAdapter param);

   public NarrativeDt getText();

   public IQICoreMedicationDispense setText(NarrativeDt param);

   public CodeDt getLanguage();

   public IQICoreMedicationDispense setLanguage(CodeDt param);

   public QICorePatientAdapter getPatientResource();

   public IQICoreMedicationDispense setPatientResource(
         QICorePatientAdapter param);

   public CodeableConceptDt getMedicationCodeableConcept();

   public IQICoreMedicationDispense setMedicationCodeableConcept(
         CodeableConceptDt param);

   public ResourceReferenceDt getMedicationReference();

   public IQICoreMedicationDispense setMedicationReference(
         ResourceReferenceDt param);
}