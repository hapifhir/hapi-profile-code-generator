extensionSetterBody(rootClassName, uri) ::= <<
	<rootClassName>.addUndeclaredExtension(false, "<uri>", param);
	return this;
>>