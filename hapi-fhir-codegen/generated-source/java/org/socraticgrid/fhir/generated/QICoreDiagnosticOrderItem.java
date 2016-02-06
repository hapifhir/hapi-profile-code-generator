package org.socraticgrid.fhir.generated;

import java.util.List;
import ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt;
import ca.uhn.fhir.model.dstu2.resource.*;

public class QICoreDiagnosticOrderItem extends DiagnosticOrder.Item
{

   public List<CodeableConceptDt> getPrecondition() {
		List<ca.uhn.fhir.model.api.ExtensionDt> extensions = this
				.getUndeclaredExtensionsByUrl("http://hl7.org/fhir/StructureDefinition/diagnosticorder-precondition");
		List<ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt> returnList = new java.util.ArrayList<>();
		for (ca.uhn.fhir.model.api.ExtensionDt extension : extensions) {
			ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt item = (ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt) extension
					.getValue();
			returnList.add(item);
		}
		return returnList;
	}

   public QICoreDiagnosticOrderItem setPrecondition(
         List<CodeableConceptDt> param)
   {
      if (param != null && param.size() > 0)
      {
         for (int index = 0; index < param.size(); index++)
         {
            ca.uhn.fhir.model.api.ExtensionDt extension = new ca.uhn.fhir.model.api.ExtensionDt();
            extension
                  .setUrl("http://hl7.org/fhir/StructureDefinition/diagnosticorder-precondition");
            extension.setValue(param.get(index));
            this.addUndeclaredExtension(extension);
         }
      }
      return this;
   }
}