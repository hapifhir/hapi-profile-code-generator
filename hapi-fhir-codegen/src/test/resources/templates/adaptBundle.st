adaptBundle() ::= <<
   Map\<String, List\<?\>\> adapteeMap = new java.util.HashMap\<\>();
   for (ca.uhn.fhir.model.api.BundleEntry entry : bundle.getEntries()) {
       Object adapter = adapt(entry.getResource());
       if(adapter != null) {
    	   List list = adapteeMap.get(entry.getResource().getClass().getCanonicalName());
    	   if(list == null) {
    		   list = new java.util.ArrayList();
    		   adapteeMap.put(entry.getResource().getClass().getCanonicalName(), list);
    	   }
    	   list.add(adapter);
       }
   }
	return adapteeMap;
>>