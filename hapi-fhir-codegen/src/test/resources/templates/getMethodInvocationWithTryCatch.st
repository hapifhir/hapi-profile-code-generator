getMethodInvocationWithTryCatch(className, propertyName) ::= <<
	try {
		return <className>.get<propertyName>();
	} catch(Exception e) {
		throw new RuntimeException("Error getting <propertyName>", e);
	}
>>