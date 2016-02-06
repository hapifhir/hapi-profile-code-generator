package org.socraticgrid.fhir.generated;

import ca.uhn.fhir.model.dstu2.resource.MedicationOrder;
import ca.uhn.fhir.model.api.ExtensionDt;
import java.util.List;
import org.socraticgrid.fhir.generated.QICoreMedicationOrderDosageInstruction;
import ca.uhn.fhir.model.primitive.StringDt;
import ca.uhn.fhir.model.dstu2.composite.NarrativeDt;
import ca.uhn.fhir.model.primitive.DateTimeDt;
import java.util.Date;
import ca.uhn.fhir.model.primitive.CodeDt;
import ca.uhn.fhir.model.dstu2.composite.ContainedDt;
import ca.uhn.fhir.model.dstu2.valueset.MedicationOrderStatusEnum;
import ca.uhn.fhir.model.primitive.BoundCodeDt;
import ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt;
import ca.uhn.fhir.model.dstu2.composite.ResourceReferenceDt;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.model.dstu2.composite.IdentifierDt;

public interface IQICoreMedicationOrder
{

   public MedicationOrder getAdaptee();

   public void setAdaptee(MedicationOrder param);

   public List<QICoreMedicationOrderDosageInstruction> getDosageInstruction();

   public IQICoreMedicationOrder setDosageInstruction(
         List<QICoreMedicationOrderDosageInstruction> param);

   public IQICoreMedicationOrder addDosageInstruction(
         QICoreMedicationOrderDosageInstruction param);

   public QICoreMedicationOrderDosageInstruction addDosageInstruction();

   public StringDt getNoteElement();

   public String getNote();

   public IQICoreMedicationOrder setNote(String param);

   public IQICoreMedicationOrder setNote(StringDt param);

   public NarrativeDt getText();

   public IQICoreMedicationOrder setText(NarrativeDt param);

   public MedicationOrder.Substitution getSubstitution();

   public IQICoreMedicationOrder setSubstitution(
         MedicationOrder.Substitution param);

   public DateTimeDt getDateWrittenElement();

   public Date getDateWritten();

   public IQICoreMedicationOrder setDateWritten(Date param);

   public IQICoreMedicationOrder setDateWritten(DateTimeDt param);

   public CodeDt getLanguage();

   public IQICoreMedicationOrder setLanguage(CodeDt param);

   public QICoreEncounterAdapter getEncounterResource();

   public IQICoreMedicationOrder setEncounterResource(
         QICoreEncounterAdapter param);

   public MedicationOrder.DispenseRequest getDispenseRequest();

   public IQICoreMedicationOrder setDispenseRequest(
         MedicationOrder.DispenseRequest param);

   public ContainedDt getContained();

   public IQICoreMedicationOrder setContained(ContainedDt param);

   public String getStatus();

   public IQICoreMedicationOrder setStatus(String param);

   public BoundCodeDt<MedicationOrderStatusEnum> getStatusElement();

   public IQICoreMedicationOrder setStatus(
         BoundCodeDt<MedicationOrderStatusEnum> param);

   public QICorePractitionerAdapter getPrescriberResource();

   public IQICoreMedicationOrder setPrescriberResource(
         QICorePractitionerAdapter param);

   public QICorePatientAdapter getPatientResource();

   public IQICoreMedicationOrder setPatientResource(QICorePatientAdapter param);

   public CodeableConceptDt getReasonCodeableConcept();

   public IQICoreMedicationOrder setReasonCodeableConcept(
         CodeableConceptDt param);

   public ResourceReferenceDt getReasonReference();

   public IQICoreMedicationOrder setReasonReference(ResourceReferenceDt param);

   public DateTimeDt getDateEndedElement();

   public Date getDateEnded();

   public IQICoreMedicationOrder setDateEnded(Date param);

   public IQICoreMedicationOrder setDateEnded(DateTimeDt param);

   public CodeableConceptDt getMedicationCodeableConcept();

   public IQICoreMedicationOrder setMedicationCodeableConcept(
         CodeableConceptDt param);

   public ResourceReferenceDt getMedicationReference();

   public IQICoreMedicationOrder setMedicationReference(
         ResourceReferenceDt param);

   public IdDt getId();

   public IQICoreMedicationOrder setId(IdDt param);

   public MedicationOrder getPriorPrescriptionResource();

   public IQICoreMedicationOrder setPriorPrescriptionResource(
         MedicationOrder param);

   public List<IdentifierDt> getIdentifier();

   public IQICoreMedicationOrder setIdentifier(List<IdentifierDt> param);

   public IQICoreMedicationOrder addIdentifier(IdentifierDt param);

   public IdentifierDt addIdentifier();

   public CodeableConceptDt getReasonEnded();

   public IQICoreMedicationOrder setReasonEnded(CodeableConceptDt param);
}