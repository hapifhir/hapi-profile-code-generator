package org.socraticgrid.fhir.generated;

import ca.uhn.fhir.model.primitive.IntegerDt;
import java.util.List;
import ca.uhn.fhir.model.dstu2.resource.*;

public class QICoreSpecimenContainer extends Specimen.Container
{

   public IntegerDt getSequenceNumber()
   {
      List<ca.uhn.fhir.model.api.ExtensionDt> extensions = this
            .getUndeclaredExtensionsByUrl("http://hl7.org/fhir/StructureDefinition/specimen-sequenceNumber");
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
               "More than one extension exists for sequenceNumber");
      }
   }

   public QICoreSpecimenContainer setSequenceNumber(IntegerDt param)
   {
      this.addUndeclaredExtension(
            false,
            "http://hl7.org/fhir/StructureDefinition/specimen-sequenceNumber",
            param);
      return this;
   }
}