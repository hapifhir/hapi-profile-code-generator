extendedTypeGetterBody(type, fieldUri) ::= <<
	<type> returnValue;
	java.util.List\<ExtensionDt\> extensions = rootObjectExtension
			.getUndeclaredExtensionsByUrl("<fieldUri>");
	if (extensions.size() == 1) {
		returnValue = (<type>) extensions.get(0).getValue();
	} else if (extensions.size() == 0) {
		returnValue = null;
	} else {
		throw new IllegalStateException(
				"More than one extension specified for this object.");
	}
	return returnValue;
>>