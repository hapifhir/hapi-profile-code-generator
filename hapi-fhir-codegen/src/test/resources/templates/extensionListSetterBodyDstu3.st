extensionListSetterBodyDstu3(uri, type) ::= <<
	if(param != null && param.size() > 0) {
		for(int index = 0; index \< param.size(); index++) {
			adaptedClass.addExtension().setUrl("<uri>").setValue(param.get(index));
		}
	}
	return this;
>>