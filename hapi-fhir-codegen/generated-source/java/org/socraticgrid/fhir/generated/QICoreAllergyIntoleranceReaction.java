package org.socraticgrid.fhir.generated;

import ca.uhn.fhir.model.api.ExtensionDt;
import java.util.List;
import ca.uhn.fhir.model.dstu2.resource.*;

public class QICoreAllergyIntoleranceReaction extends AllergyIntolerance.Reaction
{

   public ExtensionDt getDuration()
   {
      List<ca.uhn.fhir.model.api.ExtensionDt> extensions = this
            .getUndeclaredExtensionsByUrl("http://hl7.org/fhir/StructureDefinition/allergyintolerance-duration");
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
               "More than one extension exists for duration");
      }
   }

   public QICoreAllergyIntoleranceReaction setDuration(ExtensionDt param)
   {
      this.addUndeclaredExtension(
            false,
            "http://hl7.org/fhir/StructureDefinition/allergyintolerance-duration",
            param);
      return this;
   }
}