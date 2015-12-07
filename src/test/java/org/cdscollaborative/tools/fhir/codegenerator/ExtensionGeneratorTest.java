package org.cdscollaborative.tools.fhir.codegenerator;

import static org.junit.Assert.assertEquals;

import org.cdscollaborative.common.utils.graph.Node;
import org.cdscollaborative.model.meta.ClassModel;
import org.cdscollaborative.tools.fhir.codegenerator.config.CodeGeneratorConfigurator;
import org.cdscollaborative.tools.fhir.utils.FhirResourceManager;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;

public class ExtensionGeneratorTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testProcessProfile() {
		CodeGeneratorConfigurator config = new CodeGeneratorConfigurator("/Users/cnanjo/repository/fhir-cds-tools/src/main/resources/config/generation-plan.xml");
		config.initialize();
		FhirResourceManager fhirManager = CodeGeneratorConfigurator.buildFhirResourceManager(config, true);
		assertEquals("generated-source/java/", config.getTargetCodeGenerationDirectory());
		ExtensionGenerator generator = new ExtensionGenerator();
		StructureDefinition profile = fhirManager.getProfile("QICore-Patient");
		generator.processProfile(profile);
		int complexExtensionCount = 0;
		for(Node<ElementDefinitionDt> node: generator.getExtensionGraphs().values()) {
			ElementCommand command = new ElementCommand(profile);
			command.setFhirResourceManager(fhirManager);
			node.executeCommandBreadthFirst(command);
			if(node.hasChildren()) {
				++complexExtensionCount;
			}
			if(node.getName().equals("Nationality") || node.getName().equals("Citizenship")) {
				System.out.println(node.getName() + ": " + node.getChildren());
				assertEquals(2, node.getChildren().size());
			}
			if(node.getName().equals("ClinicalTrial")) {
				System.out.println(node.getName() + ": " + node.getChildren());
				assertEquals(3, node.getChildren().size());
			}
			if(node.hasChildren()) {
				ClassModel classModel = command.getClassModels().get(node.getName());
				JavaClassSource source = CodeGenerationUtils.buildJavaClass(classModel);
				CodeGenerationUtils.writeJavaClassFile("generated-source/java", "org.socraticgrid.fhir.generated", classModel.getName(), source.toString());
				System.out.println(source);
				
			}
		}
		assertEquals(3, complexExtensionCount);
	}

}
