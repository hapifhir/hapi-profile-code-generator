package org.socraticgrid.fhir.generated;

import ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt;
import ca.uhn.fhir.model.dstu2.composite.RatioDt;
import ca.uhn.fhir.model.api.ExtensionDt;
import ca.uhn.fhir.model.dstu2.composite.QuantityDt;
import java.util.List;
import ca.uhn.fhir.model.dstu2.resource.*;

public class QICoreMedicationOrderDosageInstruction extends MedicationOrder.DosageInstruction
{

   public CodeableConceptDt getDoseType()
   {
      List<ca.uhn.fhir.model.api.ExtensionDt> extensions = this
            .getUndeclaredExtensionsByUrl("http://hl7.org/fhir/StructureDefinition/pharmacy-core-doseType");
      if (extensions == null || extensions.size() <= 0)
      {
         return null;
      }
      else if (extensions.size() == 1)
      {
         return (ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt) extensions
               .get(0).getValue();
      }
      else
      {
         throw new RuntimeException(
               "More than one extension exists for doseType");
      }
   }

   public QICoreMedicationOrderDosageInstruction setDoseType(
         CodeableConceptDt param)
   {
      this.addUndeclaredExtension(
            false,
            "http://hl7.org/fhir/StructureDefinition/pharmacy-core-doseType",
            param);
      return this;
   }

   public RatioDt getMinDosePerPeriod()
   {
      List<ca.uhn.fhir.model.api.ExtensionDt> extensions = this
            .getUndeclaredExtensionsByUrl("http://hl7.org/fhir/StructureDefinition/pharmacy-core-minDosePerPeriod");
      if (extensions == null || extensions.size() <= 0)
      {
         return null;
      }
      else if (extensions.size() == 1)
      {
         return (ca.uhn.fhir.model.dstu2.composite.RatioDt) extensions
               .get(0).getValue();
      }
      else
      {
         throw new RuntimeException(
               "More than one extension exists for minDosePerPeriod");
      }
   }

   public QICoreMedicationOrderDosageInstruction setMinDosePerPeriod(
         RatioDt param)
   {
      this.addUndeclaredExtension(
            false,
            "http://hl7.org/fhir/StructureDefinition/pharmacy-core-minDosePerPeriod",
            param);
      return this;
   }

   public RatioDt getMaxDeliveryRate()
   {
      List<ca.uhn.fhir.model.api.ExtensionDt> extensions = this
            .getUndeclaredExtensionsByUrl("http://hl7.org/fhir/StructureDefinition/pharmacy-core-maxDeliveryRate");
      if (extensions == null || extensions.size() <= 0)
      {
         return null;
      }
      else if (extensions.size() == 1)
      {
         return (ca.uhn.fhir.model.dstu2.composite.RatioDt) extensions
               .get(0).getValue();
      }
      else
      {
         throw new RuntimeException(
               "More than one extension exists for maxDeliveryRate");
      }
   }

   public QICoreMedicationOrderDosageInstruction setMaxDeliveryRate(
         RatioDt param)
   {
      this.addUndeclaredExtension(
            false,
            "http://hl7.org/fhir/StructureDefinition/pharmacy-core-maxDeliveryRate",
            param);
      return this;
   }

   public RatioDt getRateGoal()
   {
      List<ca.uhn.fhir.model.api.ExtensionDt> extensions = this
            .getUndeclaredExtensionsByUrl("http://hl7.org/fhir/StructureDefinition/pharmacy-core-rateGoal");
      if (extensions == null || extensions.size() <= 0)
      {
         return null;
      }
      else if (extensions.size() == 1)
      {
         return (ca.uhn.fhir.model.dstu2.composite.RatioDt) extensions
               .get(0).getValue();
      }
      else
      {
         throw new RuntimeException(
               "More than one extension exists for rateGoal");
      }
   }

   public QICoreMedicationOrderDosageInstruction setRateGoal(RatioDt param)
   {
      this.addUndeclaredExtension(
            false,
            "http://hl7.org/fhir/StructureDefinition/pharmacy-core-rateGoal",
            param);
      return this;
   }

   public ExtensionDt getInfuseOver()
   {
      List<ca.uhn.fhir.model.api.ExtensionDt> extensions = this
            .getUndeclaredExtensionsByUrl("http://hl7.org/fhir/StructureDefinition/pharmacy-core-infuseOver");
      if (extensions == null || extensions.size() <= 0)
      {
         return null;
      }
      else if (extensions.size() == 1)
      {
         return (ca.uhn.fhir.model.api.ExtensionDt) extensions.get(0)
               .getValue();
      }
      else
      {
         throw new RuntimeException(
               "More than one extension exists for infuseOver");
      }
   }

   public QICoreMedicationOrderDosageInstruction setInfuseOver(
         ExtensionDt param)
   {
      this.addUndeclaredExtension(
            false,
            "http://hl7.org/fhir/StructureDefinition/pharmacy-core-infuseOver",
            param);
      return this;
   }

   public ExtensionDt getRateIncrementInterval()
   {
      List<ca.uhn.fhir.model.api.ExtensionDt> extensions = this
            .getUndeclaredExtensionsByUrl("http://hl7.org/fhir/StructureDefinition/pharmacy-core-rateIncrementInterval");
      if (extensions == null || extensions.size() <= 0)
      {
         return null;
      }
      else if (extensions.size() == 1)
      {
         return (ca.uhn.fhir.model.api.ExtensionDt) extensions.get(0)
               .getValue();
      }
      else
      {
         throw new RuntimeException(
               "More than one extension exists for rateIncrementInterval");
      }
   }

   public QICoreMedicationOrderDosageInstruction setRateIncrementInterval(
         ExtensionDt param)
   {
      this.addUndeclaredExtension(
            false,
            "http://hl7.org/fhir/StructureDefinition/pharmacy-core-rateIncrementInterval",
            param);
      return this;
   }

   public QuantityDt getMaxDeliveryVolume()
   {
      List<ca.uhn.fhir.model.api.ExtensionDt> extensions = this
            .getUndeclaredExtensionsByUrl("http://hl7.org/fhir/StructureDefinition/pharmacy-core-maxDeliveryVolume");
      if (extensions == null || extensions.size() <= 0)
      {
         return null;
      }
      else if (extensions.size() == 1)
      {
         return (ca.uhn.fhir.model.dstu2.composite.QuantityDt) extensions
               .get(0).getValue();
      }
      else
      {
         throw new RuntimeException(
               "More than one extension exists for maxDeliveryVolume");
      }
   }

   public QICoreMedicationOrderDosageInstruction setMaxDeliveryVolume(
         QuantityDt param)
   {
      this.addUndeclaredExtension(
            false,
            "http://hl7.org/fhir/StructureDefinition/pharmacy-core-maxDeliveryVolume",
            param);
      return this;
   }

   public RatioDt getRateIncrement()
   {
      List<ca.uhn.fhir.model.api.ExtensionDt> extensions = this
            .getUndeclaredExtensionsByUrl("http://hl7.org/fhir/StructureDefinition/pharmacy-core-rateIncrement");
      if (extensions == null || extensions.size() <= 0)
      {
         return null;
      }
      else if (extensions.size() == 1)
      {
         return (ca.uhn.fhir.model.dstu2.composite.RatioDt) extensions
               .get(0).getValue();
      }
      else
      {
         throw new RuntimeException(
               "More than one extension exists for rateIncrement");
      }
   }

   public QICoreMedicationOrderDosageInstruction setRateIncrement(RatioDt param)
   {
      this.addUndeclaredExtension(
            false,
            "http://hl7.org/fhir/StructureDefinition/pharmacy-core-rateIncrement",
            param);
      return this;
   }
}