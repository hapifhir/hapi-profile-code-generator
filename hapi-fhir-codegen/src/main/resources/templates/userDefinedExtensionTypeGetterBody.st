userDefinedExtensionTypeGetterBody(type, fieldUri) ::= <<
		List\<ca.uhn.fhir.model.api.ExtensionDt\> extensions = adaptedClass
				.getUndeclaredExtensionsByUrl("<fieldUri>");
		List\<<type>\> returnList = new java.util.ArrayList\<\>();
		for (ca.uhn.fhir.model.api.ExtensionDt extension : extensions) {
			<type> udt = new <type>();
			udt.setRootObjectExtension(extension);
			returnList
					.add(udt);
		}
		return returnList;
>>