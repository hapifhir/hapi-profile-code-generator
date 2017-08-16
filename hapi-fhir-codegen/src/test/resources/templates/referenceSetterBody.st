referenceSetterBody(className, propertyName) ::= <<
	<className>.get<propertyName>().setResource(param);
	return this;
>>