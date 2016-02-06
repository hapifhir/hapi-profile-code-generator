getCodesAsStringListBody(className,parameterName) ::= <<
	 List\<String\> codes = new ArrayList\<\>();
	 List\<CodingDt\> codings = <className>.get<parameterName>().getCoding();
	 for (Iterator\<CodingDt\> iterator = codings.iterator(); iterator.hasNext();) {
        CodingDt codingDt = (CodingDt) iterator.next();
        codes.add(codingDt.getCode());
     }
     return codes;
>>