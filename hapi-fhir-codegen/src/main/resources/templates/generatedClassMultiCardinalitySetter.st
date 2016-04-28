generatedClassMultiCardinalitySetter(generatedType, wrappedType, propertyName) ::= <<
    List\<<wrappedType>\> items = new java.util.ArrayList\<\>();
    for(<generatedType> item : param) {
     items.add(item.getAdaptee());
    }
    adaptedClass.get<propertyName>().addAll(items);
    return this;
>>