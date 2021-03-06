extensionGetterBody(rootClassName, uri, type, fieldName) ::= <<
	List\<ca.uhn.fhir.model.api.ExtensionDt\> extensions = <rootClassName>
            .getUndeclaredExtensionsByUrl("<uri>");
      if(extensions == null || extensions.size() \<= 0) {
    	  return null;
      } else if(extensions.size() == 1) {
    	  return (<type>)extensions.get(0).getValue();
      } else {
    	  throw new RuntimeException("More than one extension exists for <fieldName>");
      }
>>