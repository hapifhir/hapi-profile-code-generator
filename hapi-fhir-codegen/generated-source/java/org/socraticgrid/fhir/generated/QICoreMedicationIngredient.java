package org.socraticgrid.fhir.generated;

import ca.uhn.fhir.model.primitive.BooleanDt;
import java.util.List;
import ca.uhn.fhir.model.dstu2.resource.*;

public class QICoreMedicationIngredient extends Medication.ProductIngredient
{

   public BooleanDt getIsActiveIngredient()
   {
      List<ca.uhn.fhir.model.api.ExtensionDt> extensions = this
            .getUndeclaredExtensionsByUrl("http://hl7.org/fhir/StructureDefinition/medication-isActiveIngredient");
      if (extensions == null || extensions.size() <= 0)
      {
         return null;
      }
      else if (extensions.size() == 1)
      {
         return (ca.uhn.fhir.model.primitive.BooleanDt) extensions.get(0)
               .getValue();
      }
      else
      {
         throw new RuntimeException(
               "More than one extension exists for isActiveIngredient");
      }
   }

   public QICoreMedicationIngredient setIsActiveIngredient(BooleanDt param)
   {
      this.addUndeclaredExtension(
            false,
            "http://hl7.org/fhir/StructureDefinition/medication-isActiveIngredient",
            param);
      return this;
   }
}