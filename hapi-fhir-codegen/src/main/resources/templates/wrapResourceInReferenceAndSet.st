wrapResourceInReferenceAndSet(propertyName) ::= <<
      Reference reference = new Reference(param);
      adaptedClass.set<propertyName>(reference);
      return this;
>>