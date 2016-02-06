package ca.uhn.fhir.utils.codegen.methodgenerators;

import java.util.List;

import ca.uhn.fhir.utils.common.metamodel.Method;

public interface IMethodHandler {
	
	/**
	 * Initialization method that must be called after the object is
	 * constructed. Initialize is responsible for populating the state
	 * of the handler based on the metadata supplied by the ElementDefinitionDt
	 * instance. This metadata is used to guide the generation of method
	 * definitions.
	 */
	public void initialize();
	
	/**
	 * Workhorse method of IMethodHandlers. Generates methods corresponding
	 * to the metadata specified by the FHIR profile element.
	 * 
	 * @return
	 */
	public List<Method> buildCorrespondingMethods();
	
	/**
	 * Returns the code generation package. This is necessary for generated
	 * types not part of FHIR core.
	 * 
	 * @return
	 */
	public String getGeneratedCodePackage();
	
	/**
	 * Sets the code generation package. This is necessary for generated
	 * types not part of FHIR core.
	 * 
	 * @param codePackage
	 */
	public void setGeneratedCodePackage(String codePackage);
	
}
