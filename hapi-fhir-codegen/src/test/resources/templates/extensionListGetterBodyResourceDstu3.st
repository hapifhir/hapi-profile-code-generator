extensionListGetterBodyResourceDstu3(uri, type) ::= <<
	List\<org.hl7.fhir.dstu3.model.Extension\> extensions = adaptedClass
    				.getExtensionsByUrl("<uri>");
    List\<<type>\> returnList = new java.util.ArrayList\<\>();
    for (org.hl7.fhir.dstu3.model.Extension extension : extensions) {
        org.hl7.fhir.dstu3.model.Reference reference = (org.hl7.fhir.dstu3.model.Reference)extension
                .getValue();
       returnList.add((<type>)reference.getResource());
    }
    return returnList;
>>