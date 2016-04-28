userDefinedExtensionTypeSetterBody_dstu3(type) ::= <<
	if (param != null && param.size() \> 0)
      {
         for (int index = 0; index \< ((List\<<type>\>) param).size(); index++)
         {
            adaptedClass
                  .addExtension(param.get(index).getRootObjectExtension());
         }
      }
      return this;
>>