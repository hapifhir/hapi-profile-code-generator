package ca.uhn.fhir.utils.common.metamodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassModel {
	
	private String name;
	private List<Method> methods;
	private List<ClassField> fields;
	private List<ModifierEnum> modifiers;
	private String namespace;
	private List<String> interfaces;
	private List<String> supertypes;
	private List<String> imports;
	private Map<String, Object> taggedValues;
	private boolean allowDuplicateMethods = false;
	
	public ClassModel() {
		fields = new ArrayList<ClassField>();
		interfaces = new ArrayList<String>();
		supertypes = new ArrayList<String>();
		methods = new ArrayList<Method>();
		imports = new ArrayList<String>();
		taggedValues = new HashMap<>();
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

	public ClassModel setName(String name) {
		this.name = name;
		return this;
	}
	
	/**
	 * Method returns a shallow clone of the list
	 * of methods contained by this class.
	 * 
	 * @return
	 */
	public List<Method> getClassMethods() {
		List<Method> shallowClone = new ArrayList<Method>();
		shallowClone.addAll(methods);
		return shallowClone;
	}
	
	/**
	 * Method returns the list of methods defined
	 * for this class. Its use to add new methods
	 * is discourage. Please use addMethod() or
	 * addMethods() instead as they check for any
	 * duplicate methods prior to adding to the list
	 * of class methods.
	 * 
	 * @return
	 */
	public List<Method> getMethods() {
		return this.methods;
	}
	
	/**
	 * Sets a new list of method definitions for this class.
	 * 
	 * @param methods
	 */
	public void setMethods(List<Method> methods) {
		this.methods = methods;
	}
	
	/**
	 * Adds a new method definition safely to this class. If 
	 * allowDuplicateMethods is true, method will not prevent method 
	 * definitions with the same signature from being added. If
	 * allowDuplicateMethod is false, method will only add
	 * method definition if it is not already present in the list.
	 * 
	 * @param method
	 */
	public void addMethod(Method method) {
		if(allowDuplicateMethods || doesNotContainMethod(method)) {
			this.methods.add(method);
		}
	}
	
	/**
	 * Adds a collection of method definitions safely. See
	 * definition of addMethod(..)
	 * 
	 * @param methods
	 */
	public void addMethods(Collection<Method> methods) {
		for(Method method : methods) {
			addMethod(method);
		}
	}
	
	/**
	 * Adds a method at a specific index in a list. 
	 * 
	 * 
	 * @param index a valid index
	 * @param method
	 */
	public void addMethodAtIndex(int index, Method method) {
		if(allowDuplicateMethods || doesNotContainMethod(method)) {
			this.methods.add(index, method);
		}
	}
	
	/**
	 * Removes all methods associated with the class definition
	 */
	public void clearMethods() {
		this.methods.clear();
	}
	
	/**
	 * Returns the method definition count for this class
	 * 
	 * @return
	 */
	public int getMethodCount() {
		return this.methods.size();
	}
	
	public void addMethods(List<Method> methods) {
		if(methods != null) {
			this.methods.addAll(methods);
		}
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
	
	public List<String> getSupertypes() {
		return supertypes;
	}

	public void setSupertypes(List<String> supertypes) {
		this.supertypes = supertypes;
	}
	
	public void addSupertype(String aSupertype) {
		this.supertypes.add(aSupertype);
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
	
	public void addTaggedValue(String key, Object value) {
		taggedValues.put(key, value);
	}
	
	public Object getTaggedValue(String key) {
		return taggedValues.get(key);
	}
	
	public boolean hasTaggedValue(String key) {
		return taggedValues.get(key) != null;
	}
	
	public boolean containsMethod(Method method) {
		return methods.contains(method);
	}
	
	public boolean doesNotContainMethod(Method method) {
		return !containsMethod(method);
	}
	
	public String toString() {
		return name;
	}
}
