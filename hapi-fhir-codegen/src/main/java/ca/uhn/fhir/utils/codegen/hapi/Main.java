package ca.uhn.fhir.utils.codegen.hapi;

/**
 * Main code generation entry point.
 * 
 * TODO Add main method.
 * 
 * @author Claude Nanjo
 *
 */
public class Main {
	
	/**
	 * Configures all code generation classes and then runs the code generation plan.
	 * 
	 * @param configurationPath
	 */
	public static void generateCode(String configurationPath, boolean failOnError) {
		CodeGeneratorConfigurator configurator = CodeGeneratorConfigurator.buildConfigurator(configurationPath);
		FhirResourceManager loader = CodeGeneratorConfigurator.buildFhirResourceManager(configurator, true);
		InterfaceAdapterGenerator codeGenerator = CodeGeneratorConfigurator.buildInterfaceAdapterGenerator(configurator, loader);
		codeGenerator.executePlan(failOnError);
	}

}
