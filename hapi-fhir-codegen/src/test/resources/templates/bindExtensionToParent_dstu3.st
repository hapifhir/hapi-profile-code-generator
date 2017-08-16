bindExtensionToParent_dstu3() ::= <<
  rootObjectExtension = new Extension(uri);
  containingResource.getExtension().add(rootObjectExtension);
  return rootObjectExtension;
>>