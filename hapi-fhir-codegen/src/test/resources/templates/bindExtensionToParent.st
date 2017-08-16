bindExtensionToParent() ::= <<
  rootObjectExtension = new ExtensionDt(false, uri);
  containingResource.getExtension().add(rootObjectExtension);
  return rootObjectExtension;
>>