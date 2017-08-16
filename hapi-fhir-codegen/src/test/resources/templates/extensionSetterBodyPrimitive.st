extensionSetterBodyPrimitive(uri, type) ::= <<
	adaptedClass.addUndeclaredExtension(false, "<uri>", new <type>(param));
	return this;
>>