serializeExtensionType() ::= <<
   ExtensionDt parentExtension = new ExtensionDt();
   try {
	   parentExtension.setUrl(uri);
	   Field[] fields = this.getClass().getDeclaredFields();
	   for(Field field : fields) {
		   field.setAccessible(true);
		   if(field.getName().contains("Uri")) {
			   ExtensionDt extension = new ExtensionDt();
			   extension.setUrl((String)field.get(this));
			   Field valueField = this.getClass().getDeclaredField(field.getName().substring(0, field.getName().length() - 3));
			   valueField.setAccessible(true);
			   extension.setValue((IBaseDatatype)valueField.get(this));
			   parentExtension.addUndeclaredExtension(extension);
		   }
	   }
   } catch(Exception e) {
	   throw new RuntimeException("Error building extension serialization for " + this.getClass().getCanonicalName(), e);
   }
   return parentExtension;
>>