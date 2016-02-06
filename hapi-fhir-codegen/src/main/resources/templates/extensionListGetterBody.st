extensionListGetterBody(uri, type) ::= <<
	List\<ExtensionDt\> extensions = adaptedClass.getUndeclaredExtensionsByUrl("<uri>");
  	List\<<type>\> returnList = new java.util.ArrayList\<\>();
  	for(ExtensionDt extension : extensions) {
	  	returnList.add((<type>)extension.getValue());
  	}
  	return returnList;
>>