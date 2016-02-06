profiledReferenceGetterBody(propertyName, adapterClassPath, canonicalClassPath) ::= <<
<ifClause(referenceGetterCondition(propertyName,canonicalClassPath), profiledReferenceGetterIfBody(propertyName, adapterClassPath, canonicalClassPath), "return null;")>
>>