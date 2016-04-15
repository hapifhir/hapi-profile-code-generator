getWrappedTypeList(adapterType, adaptedType, propertyName) ::= <<
	List\<<adapterType>\> items = new java.util.ArrayList\<\>();
   	for(<adaptedType> type : adaptedClass.get<propertyName>()) {
	   items.add(new <adapterType>(type));
   	}
   	return items;
>>

