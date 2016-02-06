extendedTypeSetterBody(fieldUri) ::= <<
	java.util.List\<ExtensionDt\> extensions = rootObjectExtension
			.getUndeclaredExtensionsByUrl("<fieldUri>");
	if (extensions.size() == 1) {
		extensions.get(0).setValue(param);
	} else if (extensions.size() == 0) {
		ExtensionDt newExtension = new ExtensionDt(false, "<fieldUri>", param);
		rootObjectExtension.addUndeclaredExtension(newExtension);
	} else {
		throw new IllegalStateException(
				"More than one extension specified for this object.");
	}
	return this;
>>