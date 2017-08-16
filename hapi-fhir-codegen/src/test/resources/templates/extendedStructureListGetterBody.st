extendedStructureListGetterBody(callee, type, uri) ::= <<
	List\<ca.uhn.fhir.model.api.ExtensionDt\> extensions = <callee>
				.getUndeclaredExtensionsByUrl("<uri>");
	List\<<type>\> returnList = new java.util.ArrayList\<\>();
	for (ca.uhn.fhir.model.api.ExtensionDt extension : extensions) {
		<type> item = (<type>)extension.getValue();
		returnList
				.add(item);
	}
	return returnList;
>>