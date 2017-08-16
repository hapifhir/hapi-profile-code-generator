getWrappedTypeFirstRep(adapterType, adaptedType, propertyName) ::= <<
	<adapterType> wrapperItem = new <adapterType>();
	   <adaptedType> item = adaptedClass.get<propertyName>FirstRep();
	   if(item != null) {
		   wrapperItem = new <adapterType>(item);
	   }
	   return wrapperItem;
>>