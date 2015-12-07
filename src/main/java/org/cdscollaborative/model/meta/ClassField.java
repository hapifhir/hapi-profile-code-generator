package org.cdscollaborative.model.meta;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents an instance field in a class such as
 * 
 * <code>
 * <pre>
 * public static final File directory = new File("/data/");
 * </pre>
 * </code>
 * 
 * @author Claude Nanjo
 *
 */
public class ClassField {
	
	private String name;
	private String initializer;
	private List<ModifierEnum> modifiers;
	private String type;
	
	public ClassField() {
		modifiers = new ArrayList<>();
	}
	
	public ClassField(String name) {
		this();
		this.name = name;
	}
	
	public ClassField(String name, String type) {
		this(name);
		this.type = type;
	}

	public ClassField(String name, String type, List<ModifierEnum> modifiers) {
		this(name, type);
		this.modifiers = modifiers;
	}
	
	public ClassField(String name, String type, List<ModifierEnum> modifiers, String initializer) {
		this(name, type, modifiers);
		this.initializer = initializer;
	}
	
	/**
	 * Returns the name of the field
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name of the field
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the field initialization expression
	 * 
	 * @return
	 */
	public String getInitializer() {
		return initializer;
	}
	
	/**
	 * Sets the field initialization expression
	 * 
	 * @param initializer
	 */
	public void setInitializer(String initializer) {
		this.initializer = initializer;
	}
	
	/**
	 * Returns the modifiers (public, private, protected, etc...) for this field
	 * 
	 * @return
	 */
	public List<ModifierEnum> getModifiers() {
		return modifiers;
	}
	
	/**
	 * Sets the modifiers (public, private, protected, etc...) for this field
	 * 
	 * @param modifier
	 */
	public void setModifier(List<ModifierEnum> modifiers) {
		this.modifiers = modifiers;
	}
	
	/**
	 * Adds modifier to list
	 * 
	 * @param modifier
	 */
	public ClassField addModifier(ModifierEnum modifier) {
		this.modifiers.add(modifier);
		return this;
	}
	
	/**
	 * Returns the field type
	 * 
	 * @return
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Sets the field type
	 * 
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	//Helper Factory Method
	
	/**
	 * Method builds field with signature:
	 * 
	 * <code>
	 * <pre>
	 * public static final string MY_FIELD_NAME = "some value";
	 * </pre>
	 * </code>
	 * @return
	 */
	public static ClassField buildStaticConstant(String fieldName, String fieldType, String initializer) {
		ClassField field = new ClassField(fieldName, fieldType);
		field.addModifier(ModifierEnum.PUBLIC).addModifier(ModifierEnum.STATIC).addModifier(ModifierEnum.FINAL);
		field.setInitializer(initializer);
		return field;
	}
	
}
