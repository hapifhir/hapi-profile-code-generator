userDefinedExtensionTypeGetterBody_dstu3(type, fieldUri) ::= <<
		List\<org.hl7.fhir.dstu3.model.Extension\> extensions = adaptedClass
				.getExtensionsByUrl("<fieldUri>");
		List\<<type>\> returnList = new java.util.ArrayList\<\>();
		for (org.hl7.fhir.dstu3.model.Extension extension : extensions) {
			<type> udt = new <type>();
			udt.setRootObjectExtension(extension);
			returnList
					.add(udt);
		}
		return returnList;
>>