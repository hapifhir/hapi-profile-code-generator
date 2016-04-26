package ca.uhn.fhir.utils.codegen.hapi;

import ca.uhn.fhir.utils.codegen.hapi.dstu2.FhirResourceManagerDstu2;
import ca.uhn.fhir.utils.codegen.hapi.dstu3.FhirResourceManagerDstu3;

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
		FhirResourceManagerDstu2 loader = CodeGeneratorConfigurator.buildFhirResourceManager(configurator, true);
		InterfaceAdapterGenerator codeGenerator = CodeGeneratorConfigurator.buildInterfaceAdapterGenerator(configurator, loader);
		codeGenerator.executePlan(failOnError);
	}
	
	/**
	 * Configures all code generation classes and then runs the code generation plan.
	 * 
	 * @param configurationPath
	 */
	public static void generateDstu3Code(String configurationPath, boolean failOnError) {
		CodeGeneratorConfigurator configurator = CodeGeneratorConfigurator.buildConfigurator(configurationPath);
		FhirResourceManagerDstu3 loader = CodeGeneratorConfigurator.buildFhirResourceManagerDstu3(configurator, true);
		InterfaceAdapterGenerator codeGenerator = CodeGeneratorConfigurator.buildInterfaceAdapterGeneratorDstu3(configurator, loader);
		codeGenerator.executePlanDstu3(failOnError);
	}

}
