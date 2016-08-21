extensionGetterBodyResourceDstu3(rootClassName, uri, type) ::= <<
	List\<org.hl7.fhir.dstu3.model.Extension\> extensions = <rootClassName>
    				.getExtensionsByUrl("<uri>");
    <type> returnItem = null;
    if (extensions.size() == 1) {
        returnItem = (<type>)((org.hl7.fhir.dstu3.model.Reference)extensions.get(0)
                .getValue()).getResource();
    } else if(extensions.size() > 1) {
        throw new RuntimeException("Object has more than one extension with uri: " + "<uri>");
    }
    return returnItem;
>>