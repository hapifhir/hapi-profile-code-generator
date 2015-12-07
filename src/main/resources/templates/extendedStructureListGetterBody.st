extendedStructureListGetterBody(type, uri) ::= <<
	List\<ExtensionDt\> extensions = adaptedClass
				.getUndeclaredExtensionsByUrl("<uri>");
	List\<<type>\> returnList = new java.util.ArrayList\<\>();
	for (ExtensionDt extension : extensions) {
		<type> item = new <type>();
		item.setRootObjectExtension(extension);
		returnList
				.add(item);
	}
	return returnList;
>>