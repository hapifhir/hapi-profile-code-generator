extensionListGetterBodyDstu3(uri, type) ::= <<
	List\<org.hl7.fhir.dstu3.model.Extension\> extensions = adaptedClass.getExtensionsByUrl("<uri>");
  	List\<<type>\> returnList = new java.util.ArrayList\<\>();
  	for(org.hl7.fhir.dstu3.model.Extension extension : extensions) {
	  	returnList.add((<type>)extension.getValue());
  	}
  	return returnList;
>>