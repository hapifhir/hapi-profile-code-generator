codeEnumAsStringSetterBody(fieldName, bindingName) ::= <<
	adaptedClass.set<fieldName>(<bindingName>.valueOf(param));
>>