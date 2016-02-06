package org.socraticgrid.fhir.generated;

import ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt;
import ca.uhn.fhir.model.primitive.BooleanDt;
import java.util.List;
import ca.uhn.fhir.model.dstu2.resource.*;

public class QICorePractitionerSpecialty extends CodeableConceptDt
{

   public BooleanDt getPrimaryInd()
   {
      List<ca.uhn.fhir.model.api.ExtensionDt> extensions = this
            .getUndeclaredExtensionsByUrl("http://hl7.org/fhir/StructureDefinition/practitioner-primaryInd");
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
               "More than one extension exists for primaryInd");
      }
   }

   public QICorePractitionerSpecialty setPrimaryInd(BooleanDt param)
   {
      this.addUndeclaredExtension(
            false,
            "http://hl7.org/fhir/StructureDefinition/practitioner-primaryInd",
            param);
      return this;
   }
}