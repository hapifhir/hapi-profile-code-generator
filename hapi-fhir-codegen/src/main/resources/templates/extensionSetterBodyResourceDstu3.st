extensionSetterBodyResourceDstu3(rootClassName, uri) ::= <<
	if (param != null) {
        <rootClassName>
            .addExtension()
            .setUrl("<uri>")
            .setValue(new Reference(param));
    }
          return this;
>>