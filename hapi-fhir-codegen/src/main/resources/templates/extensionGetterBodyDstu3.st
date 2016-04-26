extensionGetterBodyDstu3(rootClassName, uri, type, fieldName) ::= <<
	List\<org.hl7.fhir.dstu3.model.Extension\> extensions = <rootClassName>
            .getExtensionsByUrl("<uri>");
      if(extensions == null || extensions.size() \<= 0) {
    	  return null;
      } else if(extensions.size() == 1) {
    	  return (<type>)extensions.get(0).getValue();
      } else {
    	  throw new RuntimeException("More than one extension exists for <fieldName>");
      }
>>