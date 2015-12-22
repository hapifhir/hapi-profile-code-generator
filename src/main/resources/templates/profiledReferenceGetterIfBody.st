profiledReferenceGetterIfBody(propertyName, adapterClassPath, canonicalClassPath) ::= <<
	<adapterClassPath> profiledType = new <adapterClassPath>();
    profiledType.setAdaptee((<canonicalClassPath>) adaptedClass.get<propertyName>().getResource());
    return profiledType;
>>