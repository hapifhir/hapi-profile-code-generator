userDefinedExtensionTypeSetterBody(type) ::= <<
	if (param != null && param.size() \> 0)
      {
         for (int index = 0; index \< ((List\<<type>\>) param).size(); index++)
         {
            adaptedClass
                  .addUndeclaredExtension(param.get(index).getRootObjectExtension());
         }
      }
>>