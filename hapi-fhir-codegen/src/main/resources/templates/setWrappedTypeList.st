setWrappedTypeList(adapterType, adaptedType, propertyName) ::= <<
	List\<<adaptedType>\> items = new java.util.ArrayList\<\>();
	   for(<adapterType> item : param) {
		   items.add(item.getAdaptee());
	   }
	   adaptedClass.set<propertyName>(items);
      return this;
>>

