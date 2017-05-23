getMethodInvocationWithCast(className, propertyName, castType) ::= <<
    if(<className>.get<propertyName>() instanceof <castType>) {
        return (<castType>) <className>.get<propertyName>();
    } else {
        return null;
    }
>>