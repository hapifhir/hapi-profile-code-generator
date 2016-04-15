addWrappedTypeToListMethodDelegatedBody(propertyName) ::= <<
	if(param != null) {
		adaptedClass.add<propertyName>(param.getAdaptee());
	}
	return this;
>>
