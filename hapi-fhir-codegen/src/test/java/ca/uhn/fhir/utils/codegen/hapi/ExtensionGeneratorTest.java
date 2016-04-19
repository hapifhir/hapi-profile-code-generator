package ca.uhn.fhir.utils.codegen.hapi;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.fail;

import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;
import ca.uhn.fhir.utils.codegen.CodeGenerationUtils;
import ca.uhn.fhir.utils.common.graph.Node;
import ca.uhn.fhir.utils.common.io.ResourceLoadingUtils;

public class ExtensionGeneratorTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testProcessProfile() {
//		try {
//		String path = new File(".").getAbsoluteFile() + "/src/test/resources/config/generation-plan.xml";
//		CodeGeneratorConfigurator config = new CodeGeneratorConfigurator(path);
//		config.initialize();
//		FhirResourceManager fhirManager = CodeGeneratorConfigurator.buildFhirResourceManager(config, true);
//		assertEquals("generated-source/java/", config.getTargetCodeGenerationDirectory());
//		ExtensionGenerator generator = new ExtensionGenerator();
//		StructureDefinition profile = fhirManager.getProfile("QICore-Patient");
//		generator.processProfile(profile);
//		int complexExtensionCount = 0;
//		for(Node<ElementDefinitionDt> node: generator.getExtensionGraphs().values()) {
//			ElementCommand command = new ElementCommand(profile);
//			command.setFhirResourceManager(fhirManager);
//			node.executeCommandBreadthFirst(command);
//			if(node.hasChildren()) {
//				++complexExtensionCount;
//			}
//			if(node.getName().equals("Nationality") || node.getName().equals("Citizenship")) {
//				System.out.println(node.getName() + ": " + node.getChildren());
//				assertEquals(2, node.getChildren().size());
//			}
//			if(node.getName().equals("ClinicalTrial")) {
//				System.out.println(node.getName() + ": " + node.getChildren());
//				assertEquals(3, node.getChildren().size());
//			}
//		}
//		assertEquals(3, complexExtensionCount);
//		} catch(Exception e) {
//			e.printStackTrace();
//			fail();
//		}
	}
	
	@Test
	public void testTypeExtension() {
//		String path = "Patient.contact.telecom.extension";
//		assertEquals(CodeGenerationUtils.getPathComponents(path).size(), 4);
//		FhirResourceManager fhirManager = new FhirResourceManager();
//		fhirManager.loadResourceProfiles(ResourceLoadingUtils.getReaderFromClasspath("profiles-resources.xml"));
//		fhirManager.getLeafLevelItemType(CodeGenerationUtils.getPathComponents(path));
	}

}
