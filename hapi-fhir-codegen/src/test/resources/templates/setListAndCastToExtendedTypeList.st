setListAndCastToExtendedTypeList(className, propertyName, castTo) ::= <<
	<className>.set<propertyName>((List\<<castTo>\>) (List\<?\>)param);
	return this;
>>