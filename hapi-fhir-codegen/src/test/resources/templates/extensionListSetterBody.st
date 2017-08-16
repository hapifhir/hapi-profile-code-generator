extensionListSetterBody(uri, type) ::= <<
	if(param != null && param.size() > 0) {
		for(int index = 0; index \< ((List\<<type>\>)param).size(); index++) {
			adaptedClass.addUndeclaredExtension(false, "<uri>", (<type>)param.get(index));
		}
	}
	return this;
>>