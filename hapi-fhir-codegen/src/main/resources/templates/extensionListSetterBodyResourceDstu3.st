extensionListSetterBodyResourceDstu3(uri) ::= <<
	if (param != null && param.size() > 0)
          {
             for (int index = 0; index \< param.size(); index++)
             {
                org.hl7.fhir.dstu3.model.Reference reference = new org.hl7.fhir.dstu3.model.Reference(param.get(index));
                adaptedClass
                      .addExtension()
                      .setUrl("<uri>")
                      .setValue(reference);
             }
          }
          return this;
>>