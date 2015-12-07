bindExtensionToParent() ::= <<
	rootObjectExtension = new ExtensionDt(false, uri);
	containingResource.addUndeclaredExtension(rootObjectExtension);
	return rootObjectExtension;
>>