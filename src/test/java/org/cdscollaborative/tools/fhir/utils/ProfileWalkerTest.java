package org.cdscollaborative.tools.fhir.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.cdscollaborative.common.utils.graph.Node;
import org.cdscollaborative.common.utils.io.ResourceLoadingUtils;
import org.cdscollaborative.model.meta.ClassModel;
import org.cdscollaborative.tools.fhir.codegenerator.CodeGenerationUtils;
import org.cdscollaborative.tools.fhir.codegenerator.CodeTemplateUtils;
import org.cdscollaborative.tools.fhir.codegenerator.GenerateLogicalViewCommand;
import org.cdscollaborative.tools.fhir.codegenerator.InterfaceAdapterGenerator;
import org.cdscollaborative.tools.fhir.codegenerator.config.CodeGeneratorConfigurator;
import org.cdscollaborative.tools.fhir.codegenerator.method.MethodHandlerResolver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;

public class ProfileWalkerTest {
	
	private FhirResourceManager fhirResourceManager;
	private ProfileWalker profileWalker;
	private MethodHandlerResolver resolver;
	private CodeTemplateUtils templateUtils;
	
	@Before
	public void setUp() throws Exception {
		fhirResourceManager = new FhirResourceManager();
		String path = ResourceLoadingUtils.getPathFromResourceClassPath("/config/generation-plan.xml");
		CodeGeneratorConfigurator configurator = CodeGeneratorConfigurator.buildConfigurator(path);
		fhirResourceManager = CodeGeneratorConfigurator.buildFhirResourceManager(configurator, true);
		templateUtils = new CodeTemplateUtils().initialize();
		resolver = new MethodHandlerResolver(fhirResourceManager, templateUtils);
		StructureDefinition profile = fhirResourceManager.getProfile("QICore-Patient");
		profileWalker = new ProfileWalker(profile);
		profileWalker.initialize();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testProfileWalkerIndexing() {
		try {
			
			//A profile should be set
			assertNotNull(profileWalker.getProfile());
			
			//Each element must be retrievable and index key must be unique
			List<ElementDefinitionDt> elements = profileWalker.getProfile().getSnapshot().getElement();
			for(ElementDefinitionDt element: elements) {
				if(!ProfileWalker.isRootElement(element) && !ProfileWalker.isProfileMetaElement(profileWalker.getRootElement().getPath(), element)) {
					assertNotNull(profileWalker.getFromElementIndex(ProfileWalker.generateElementSignature(element)));
				}
			}
			
			//There should be four meta elements in this StructureDefinition
			System.out.println(profileWalker.getMetaElementIndex());
			assertEquals(profileWalker.getMetaElementIndex().size(), 15);
			
			//There should be a root element in this profile and it is 'Patient'
			assertNotNull(profileWalker.getRootElement());
			assertEquals(profileWalker.getRootElement().getPath(), "Patient");
			
		} catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testPathRewriting() {
		String path = ProfileWalker.getModifiedExtensionPath("Patient.demographics.extension.extension", "nationality.code");
		assertEquals(path, "Patient.demographics.nationality.code");
		path = ProfileWalker.getModifiedExtensionPath("Patient.contact.extension","preferred");
		assertEquals(path, "Patient.contact.preferred");
	}
	
	@Test
	public void testProfileWalkerCodeGeneration() {
		Node<ElementDefinitionDt> root = profileWalker.getRoot();
		StructureDefinition profile = profileWalker.getProfile();
		GenerateLogicalViewCommand command = new GenerateLogicalViewCommand(profile, fhirResourceManager, templateUtils, resolver, "org.socraticgrid.fhir.generated");
		root.executeCommandDepthFirstPost(command);
		for(ClassModel model : command.getClassMap().values()) {
			System.out.println("Printing class " + model.getName());
			System.out.println(InterfaceAdapterGenerator.cleanUpWorkaroundClass(CodeGenerationUtils.buildJavaClass(model), true));
			//System.out.println(InterfaceAdapterGenerator.cleanUpWorkaroundInterface(CodeGenerationUtils.buildJavaInterface(model), true));
		}
	}
}
