package ca.uhn.fhir.utils.codegen.hapi;

import ca.uhn.fhir.utils.codegen.hapi.dstu2.FhirResourceManagerDstu2;
import ca.uhn.fhir.utils.codegen.hapi.dstu3.FhirResourceManagerDstu3;
import ca.uhn.fhir.utils.common.io.ResourceLoadingUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;

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
		CodeGeneratorConfigurator codeGeneratorConfigurator = new CodeGeneratorConfigurator(configurationPath);
		InterfaceAdapterGenerator codeGenerator = codeGeneratorConfigurator.buildInterfaceAdapterGenerator(configurator, loader);
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
		CodeGeneratorConfigurator codeGeneratorConfigurator = new CodeGeneratorConfigurator(configurationPath);
		InterfaceAdapterGenerator codeGenerator = codeGeneratorConfigurator.buildInterfaceAdapterGeneratorDstu3(configurator, loader);
		codeGenerator.executePlanDstu3(failOnError);
	}

	public static void generateDstu3Code(CodeGeneratorConfigurator configurator, boolean failOnError) {
		FhirResourceManagerDstu3 loader = CodeGeneratorConfigurator.buildFhirResourceManagerDstu3(configurator, true);
		InterfaceAdapterGenerator codeGenerator = configurator.buildInterfaceAdapterGeneratorDstu3(configurator, loader);
		codeGenerator.executePlanDstu3(failOnError);
	}

	public static void main(String[] args) throws Exception {
		FileUtils.deleteDirectory(new File("generated-source/java/org/hspc/fhir/dstu3/generated/"));
		Main.generateDstu3Code(ResourceLoadingUtils.getPathFromResourceClassPath("/config/generation-plan-dstu3.xml"), true);
	}

}
