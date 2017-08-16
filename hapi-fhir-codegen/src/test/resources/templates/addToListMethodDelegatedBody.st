addToListMethodDelegatedBody(fieldName, propertyName, propertyType) ::= <<
	<propertyType> item = new <propertyType>();
	adaptedClass.add<propertyName>(item);
	return item;
>>