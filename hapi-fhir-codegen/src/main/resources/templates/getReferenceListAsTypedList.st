getReferenceListAsTypedList(callee, propertyName, propertyType) ::= <<
    List\<<propertyType>\> items = new java.util.ArrayList\<\>();
    List\<org.hl7.fhir.dstu3.model.Resource\> resources = <callee>.get<propertyName>();
    for(org.hl7.fhir.dstu3.model.Resource resource : resources) {
     items.add((<propertyType>)resource);
    }
    return items;
>>