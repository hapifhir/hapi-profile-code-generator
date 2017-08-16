 castTypeToReferenceAndReturnTarget(propertyName, propertyType) ::= <<
    return (<propertyType>) ((org.hl7.fhir.dstu3.model.Reference)adaptedClass
                .get<propertyName>()).getResource();
  >>