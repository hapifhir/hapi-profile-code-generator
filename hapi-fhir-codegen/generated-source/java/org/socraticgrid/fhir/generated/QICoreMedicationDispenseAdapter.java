package org.socraticgrid.fhir.generated;

import org.socraticgrid.fhir.generated.IQICoreMedicationDispense;
import ca.uhn.fhir.model.dstu2.resource.MedicationDispense;
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
import ca.uhn.fhir.model.api.ExtensionDt;

public class QICoreMedicationDispenseAdapter implements IQICoreMedicationDispense
{

   private MedicationDispense adaptedClass = null;

   public QICoreMedicationDispenseAdapter()
   {
      this.adaptedClass = new ca.uhn.fhir.model.dstu2.resource.MedicationDispense();
   }

   public QICoreMedicationDispenseAdapter(MedicationDispense adaptee)
   {
      this.adaptedClass = adaptee;
   }

   public MedicationDispense getAdaptee()
   {
      return adaptedClass;
   }

   public void setAdaptee(MedicationDispense param)
   {
      this.adaptedClass = param;
   }

   public SimpleQuantityDt getQuantity()
   {
      return adaptedClass.getQuantity();
   }

   public IQICoreMedicationDispense setQuantity(SimpleQuantityDt param)
   {
      adaptedClass.setQuantity(param);
      return this;
   }

   public IdDt getId()
   {
      return adaptedClass.getId();
   }

   public IQICoreMedicationDispense setId(IdDt param)
   {
      adaptedClass.setId(param);
      return this;
   }

   public IntegerDt getRefillsRemaining()
   {
      List<ca.uhn.fhir.model.api.ExtensionDt> extensions = adaptedClass
            .getUndeclaredExtensionsByUrl("http://hl7.org/fhir/StructureDefinition/pharmacy-core-refillsRemaining");
      if (extensions == null || extensions.size() <= 0)
      {
         return null;
      }
      else if (extensions.size() == 1)
      {
         return (ca.uhn.fhir.model.primitive.IntegerDt) extensions.get(0)
               .getValue();
      }
      else
      {
         throw new RuntimeException(
               "More than one extension exists for refillsRemaining");
      }
   }

   public IQICoreMedicationDispense setRefillsRemaining(IntegerDt param)
   {
      adaptedClass
            .addUndeclaredExtension(
                  false,
                  "http://hl7.org/fhir/StructureDefinition/pharmacy-core-refillsRemaining",
                  param);
      return this;
   }

   public QICorePractitionerAdapter getDispenserResource()
   {
      if (adaptedClass.getDispenser().getResource() instanceof ca.uhn.fhir.model.dstu2.resource.Practitioner)
      {
         org.socraticgrid.fhir.generated.QICorePractitionerAdapter profiledType = new org.socraticgrid.fhir.generated.QICorePractitionerAdapter();
         profiledType
               .setAdaptee((ca.uhn.fhir.model.dstu2.resource.Practitioner) adaptedClass
                     .getDispenser().getResource());
         return profiledType;
      }
      else
      {
         return null;
      }
   }

   public IQICoreMedicationDispense setDispenserResource(
         QICorePractitionerAdapter param)
   {
      adaptedClass.getDispenser().setResource(param.getAdaptee());
      return this;
   }

   public StringDt getNoteElement()
   {
      return adaptedClass.getNoteElement();
   }

   public String getNote()
   {
      return adaptedClass.getNote();
   }

   public IQICoreMedicationDispense setNote(String param)
   {
      adaptedClass.setNote(new ca.uhn.fhir.model.primitive.StringDt(param));
      return this;
   }

   public IQICoreMedicationDispense setNote(StringDt param)
   {
      adaptedClass.setNote(param);
      return this;
   }

   public DateTimeDt getWhenPreparedElement()
   {
      return adaptedClass.getWhenPreparedElement();
   }

   public Date getWhenPrepared()
   {
      return adaptedClass.getWhenPrepared();
   }

   public IQICoreMedicationDispense setWhenPrepared(Date param)
   {
      adaptedClass
            .setWhenPrepared(new ca.uhn.fhir.model.primitive.DateTimeDt(
                  param));
      return this;
   }

   public IQICoreMedicationDispense setWhenPrepared(DateTimeDt param)
   {
      adaptedClass.setWhenPrepared(param);
      return this;
   }

   public List<QICoreMedicationDispenseDosageInstruction> getDosageInstruction()
   {
      return (List<org.socraticgrid.fhir.generated.QICoreMedicationDispenseDosageInstruction>) (List<?>) adaptedClass
            .getDosageInstruction();
   }

   public IQICoreMedicationDispense setDosageInstruction(
         List<QICoreMedicationDispenseDosageInstruction> param)
   {
      adaptedClass
            .setDosageInstruction((List<ca.uhn.fhir.model.dstu2.resource.MedicationDispense.DosageInstruction>) (List<?>) param);
      return this;
   }

   public IQICoreMedicationDispense addDosageInstruction(
         QICoreMedicationDispenseDosageInstruction param)
   {
      adaptedClass.addDosageInstruction(param);
      return this;
   }

   public QICoreMedicationDispenseDosageInstruction addDosageInstruction()
   {
      org.socraticgrid.fhir.generated.QICoreMedicationDispenseDosageInstruction item = new org.socraticgrid.fhir.generated.QICoreMedicationDispenseDosageInstruction();
      adaptedClass.addDosageInstruction(item);
      return item;
   }

   public MedicationDispense.Substitution getSubstitution()
   {
      return adaptedClass.getSubstitution();
   }

   public IQICoreMedicationDispense setSubstitution(
         MedicationDispense.Substitution param)
   {
      adaptedClass.setSubstitution(param);
      return this;
   }

   public PeriodDt getValidityPeriod()
   {
      List<ca.uhn.fhir.model.api.ExtensionDt> extensions = adaptedClass
            .getUndeclaredExtensionsByUrl("http://hl7.org/fhir/StructureDefinition/medicationdispense-validityPeriod");
      if (extensions == null || extensions.size() <= 0)
      {
         return null;
      }
      else if (extensions.size() == 1)
      {
         return (ca.uhn.fhir.model.dstu2.composite.PeriodDt) extensions.get(
               0).getValue();
      }
      else
      {
         throw new RuntimeException(
               "More than one extension exists for validityPeriod");
      }
   }

   public IQICoreMedicationDispense setValidityPeriod(PeriodDt param)
   {
      adaptedClass
            .addUndeclaredExtension(
                  false,
                  "http://hl7.org/fhir/StructureDefinition/medicationdispense-validityPeriod",
                  param);
      return this;
   }

   public ContainedDt getContained()
   {
      return adaptedClass.getContained();
   }

   public IQICoreMedicationDispense setContained(ContainedDt param)
   {
      adaptedClass.setContained(param);
      return this;
   }

   public String getStatus()
   {
      return adaptedClass.getStatus();
   }

   public IQICoreMedicationDispense setStatus(String param)
   {
      adaptedClass
            .setStatus(ca.uhn.fhir.model.dstu2.valueset.MedicationDispenseStatusEnum
                  .valueOf(param));
      return this;
   }

   public BoundCodeDt<MedicationDispenseStatusEnum> getStatusElement()
   {
      return adaptedClass.getStatusElement();
   }

   public IQICoreMedicationDispense setStatus(
         BoundCodeDt<MedicationDispenseStatusEnum> param)
   {
      adaptedClass.setStatus(param);
      return this;
   }

   public CodeableConceptDt getType()
   {
      return adaptedClass.getType();
   }

   public IQICoreMedicationDispense setType(CodeableConceptDt param)
   {
      adaptedClass.setType(param);
      return this;
   }

   public SimpleQuantityDt getDaysSupply()
   {
      return adaptedClass.getDaysSupply();
   }

   public IQICoreMedicationDispense setDaysSupply(SimpleQuantityDt param)
   {
      adaptedClass.setDaysSupply(param);
      return this;
   }

   public DateTimeDt getWhenHandedOverElement()
   {
      return adaptedClass.getWhenHandedOverElement();
   }

   public Date getWhenHandedOver()
   {
      return adaptedClass.getWhenHandedOver();
   }

   public IQICoreMedicationDispense setWhenHandedOver(Date param)
   {
      adaptedClass
            .setWhenHandedOver(new ca.uhn.fhir.model.primitive.DateTimeDt(
                  param));
      return this;
   }

   public IQICoreMedicationDispense setWhenHandedOver(DateTimeDt param)
   {
      adaptedClass.setWhenHandedOver(param);
      return this;
   }

   public IdentifierDt getIdentifier()
   {
      return adaptedClass.getIdentifier();
   }

   public IQICoreMedicationDispense setIdentifier(IdentifierDt param)
   {
      adaptedClass.setIdentifier(param);
      return this;
   }

   public QICoreLocationAdapter getDestinationResource()
   {
      if (adaptedClass.getDestination().getResource() instanceof ca.uhn.fhir.model.dstu2.resource.Location)
      {
         org.socraticgrid.fhir.generated.QICoreLocationAdapter profiledType = new org.socraticgrid.fhir.generated.QICoreLocationAdapter();
         profiledType
               .setAdaptee((ca.uhn.fhir.model.dstu2.resource.Location) adaptedClass
                     .getDestination().getResource());
         return profiledType;
      }
      else
      {
         return null;
      }
   }

   public IQICoreMedicationDispense setDestinationResource(
         QICoreLocationAdapter param)
   {
      adaptedClass.getDestination().setResource(param.getAdaptee());
      return this;
   }

   public NarrativeDt getText()
   {
      return adaptedClass.getText();
   }

   public IQICoreMedicationDispense setText(NarrativeDt param)
   {
      adaptedClass.setText(param);
      return this;
   }

   public CodeDt getLanguage()
   {
      return adaptedClass.getLanguage();
   }

   public IQICoreMedicationDispense setLanguage(CodeDt param)
   {
      adaptedClass.setLanguage(param);
      return this;
   }

   public QICorePatientAdapter getPatientResource()
   {
      if (adaptedClass.getPatient().getResource() instanceof ca.uhn.fhir.model.dstu2.resource.Patient)
      {
         org.socraticgrid.fhir.generated.QICorePatientAdapter profiledType = new org.socraticgrid.fhir.generated.QICorePatientAdapter();
         profiledType
               .setAdaptee((ca.uhn.fhir.model.dstu2.resource.Patient) adaptedClass
                     .getPatient().getResource());
         return profiledType;
      }
      else
      {
         return null;
      }
   }

   public IQICoreMedicationDispense setPatientResource(
         QICorePatientAdapter param)
   {
      adaptedClass.getPatient().setResource(param.getAdaptee());
      return this;
   }

   public CodeableConceptDt getMedicationCodeableConcept()
   {
      if (adaptedClass.getMedication() != null
            && adaptedClass.getMedication() instanceof ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt)
      {
         return (ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt) adaptedClass
               .getMedication();
      }
      else
      {
         return null;
      }
   }

   public IQICoreMedicationDispense setMedicationCodeableConcept(
         CodeableConceptDt param)
   {
      adaptedClass.setMedication(param);
      return this;
   }

   public ResourceReferenceDt getMedicationReference()
   {
      if (adaptedClass.getMedication() != null
            && adaptedClass.getMedication() instanceof ca.uhn.fhir.model.dstu2.composite.ResourceReferenceDt)
      {
         return (ca.uhn.fhir.model.dstu2.composite.ResourceReferenceDt) adaptedClass
               .getMedication();
      }
      else
      {
         return null;
      }
   }

   public IQICoreMedicationDispense setMedicationReference(
         ResourceReferenceDt param)
   {
      adaptedClass.setMedication(param);
      return this;
   }
}