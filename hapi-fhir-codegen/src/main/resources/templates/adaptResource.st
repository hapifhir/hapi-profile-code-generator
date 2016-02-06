adaptResource() ::= <<
	Object adapter = null;
   	String adapterName = resource.getResourceName() + "Adapter";
   	String resourceName = resource.getResourceName();
   	try {
	   Class clazz = Class.forName(GENERATED_PACKAGE_PREFIX + adapterName);
	   adapter = clazz.newInstance();
	   Class[] args = new Class[1];
	   args[0] = Class.forName(HAPI_FHIR_RESOURCE_PREFIX + resourceName);
	   java.lang.reflect.Method method = clazz.getDeclaredMethod("setAdaptee", args);
	   Object[] resourceArray = new Object[1];
	   resourceArray[0] = resource;
	   method.invoke(adapter, resourceArray);
   	} catch(Exception e) {
	   LOGGER.error("Unable to adapt " + resourceName, e);
   	}
   	return adapter;
>>