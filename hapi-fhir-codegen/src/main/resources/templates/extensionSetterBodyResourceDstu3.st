extensionSetterBodyResourceDstu3(uri) ::= <<
	if (param != null) {
        rootObjectExtension
            .addExtension()
            .setUrl("<uri>")
            .setValue(new Reference(param));
    }
          return this;
>>