profiledReferenceSetterBody(className, propertyName) ::= <<
	<className>.get<propertyName>().setResource(param.getAdaptee());
	return this;
>>