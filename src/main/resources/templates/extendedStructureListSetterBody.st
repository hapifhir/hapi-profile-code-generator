extendedStructureListSetterBody() ::= <<
      if (param != null && param.size() \> 0)
      {
         for (int index = 0; index \< param.size(); index++)
         {
            adaptedClass.addUndeclaredExtension(param.get(index).getRootObjectExtension());
         }
      }
>>