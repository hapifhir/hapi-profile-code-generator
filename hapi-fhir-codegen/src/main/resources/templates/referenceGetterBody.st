referenceGetterBody(propertyName, canonicalClassPath) ::= <<
<ifClause(referenceGetterCondition(propertyName,canonicalClassPath), referenceGetterIfBody(propertyName, canonicalClassPath), "return null;")>
>>