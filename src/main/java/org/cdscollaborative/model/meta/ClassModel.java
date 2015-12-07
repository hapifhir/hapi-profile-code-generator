package org.cdscollaborative.model.meta;

import java.util.ArrayList;
import java.util.List;

public class ClassModel {
	
	private String name;
	private List<Method> methods;
	private List<ClassField> fields;
	private List<ModifierEnum> modifiers;
	private String namespace;
	private List<String> interfaces;
	private List<String> imports;
	
	public ClassModel() {
		fields = new ArrayList<ClassField>();
		interfaces = new ArrayList<String>();
		methods = new ArrayList<Method>();
		imports = new ArrayList<String>();
	}

	public ClassModel(String name) {
		this();
		this.name = name;
	}
	
	public ClassModel(String namespace, String name) {
		this(name);
		this.namespace = namespace;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Method> getMethods() {
		return methods;
	}

	public void setMethods(List<Method> methods) {
		this.methods = methods;
	}
	
	public void addMethod(Method method) {
		this.methods.add(method);
	}

	public List<ClassField> getFields() {
		return fields;
	}

	public void setFields(List<ClassField> fields) {
		this.fields = fields;
	}
	
	public void addField(ClassField field) {
		this.fields.add(field);
	}

	public List<ModifierEnum> getModifiers() {
		return modifiers;
	}

	public void setModifiers(List<ModifierEnum> modifiers) {
		this.modifiers = modifiers;
	}
	
	public void addModifiers(ModifierEnum modifier) {
		this.modifiers.add(modifier);
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public List<String> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(List<String> interfaces) {
		this.interfaces = interfaces;
	}
	
	public void addInterface(String anInterface) {
		this.interfaces.add(anInterface);
	}

	public List<String> getImports() {
		return imports;
	}

	public void setImports(List<String> imports) {
		this.imports = imports;
	}
	
	public void addImport(String anImport) {
		this.imports.add(anImport);
	}
}
