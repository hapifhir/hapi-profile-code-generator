generatedClassMultiCardinalityGetter(generatedType, wrappedType, propertyName) ::= <<
    List\<<generatedType>\> wrappedItems = new java.util.ArrayList\<\>();
    List\<<wrappedType>\> items = adaptedClass.get<propertyName>();
    for(<wrappedType> item : items) {
     wrappedItems.add(new <generatedType>(item));
    }
    return wrappedItems;
>>