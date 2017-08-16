addWrappedTypeToListMethodBody(adapterType, adaptedType, propertyName) ::= <<
	<adaptedType> item = new <adaptedType>();
	adaptedClass.add<propertyName>(item);
	return new <adapterType>(item);
>>
