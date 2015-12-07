package org.cdscollaborative.model.meta;

public class MethodParameter {
	private String name;
	private String value;
	
	public MethodParameter(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}
}
