extendedStructureListSetterBody(callee, uri) ::= <<
      if (param != null && param.size() \> 0)
      {
         for (int index = 0; index \< param.size(); index++)
         {
            ca.uhn.fhir.model.api.ExtensionDt extension = new ca.uhn.fhir.model.api.ExtensionDt();
            extension.setUrl("<uri>");
            extension.setValue(param.get(index));
        	 <callee>.addUndeclaredExtension(extension);
         }
      }
      return this;
>>