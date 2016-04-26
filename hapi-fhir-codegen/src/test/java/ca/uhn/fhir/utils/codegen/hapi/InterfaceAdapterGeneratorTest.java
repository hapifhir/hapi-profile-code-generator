package ca.uhn.fhir.utils.codegen.hapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ca.uhn.fhir.utils.codegen.hapi.dstu2.FhirResourceManagerDstu2;
import ca.uhn.fhir.utils.codegen.hapi.dstu3.FhirResourceManagerDstu3;
import ca.uhn.fhir.utils.common.io.ResourceLoadingUtils;
import ca.uhn.fhir.utils.fhir.FhirExtensionManager;

public class InterfaceAdapterGeneratorTest {
	
	private FhirResourceManagerDstu2 loader;
	private FhirResourceManagerDstu3 loaderDstu3;

	@Before
	public void setUp() throws Exception {
		loader = new FhirResourceManagerDstu2();
		loader.loadResourceProfiles(ResourceLoadingUtils.getReaderFromClasspath("profiles-resources.xml"));
		loaderDstu3 = new FhirResourceManagerDstu3();
		loaderDstu3.loadResourceProfiles(ResourceLoadingUtils.getReaderFromFilePath("/Users/cnanjo/repository/fhir_dstu3/fhir/trunk/build/publish/profiles-resources.xml"));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGenerateInterface() {
		try {//TODO Fix hard coded path
			FileUtils.deleteDirectory(new File("generated-source/java/org/socraticgrid/fhir/generated/"));
			Main.generateCode(ResourceLoadingUtils.getPathFromResourceClassPath("/config/generation-plan.xml"), false);
			File genDir = new File("generated-source/java/org/socraticgrid/fhir/generated/");
			assertTrue(genDir.exists());
			assertTrue(genDir.isDirectory());
			assertEquals(111, genDir.listFiles().length);
		} catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testGenerateInterface_dstu3() {
		try {//TODO Fix hard coded path
			FileUtils.deleteDirectory(new File("generated-source/java/org/socraticgrid/fhir/dstu3/generated/"));
			Main.generateDstu3Code(ResourceLoadingUtils.getPathFromResourceClassPath("/config/generation-plan-dstu3.xml"), false);
			File genDir = new File("generated-source/java/org/socraticgrid/fhir/dstu3/generated/");
			assertTrue(genDir.exists());
			assertTrue(genDir.isDirectory());
			assertEquals(111, genDir.listFiles().length);
		} catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testQiCoreProfile() {
		loader = new FhirResourceManagerDstu2();
		loader.loadResourceProfiles(ResourceLoadingUtils.getReaderFromClasspath("qicore-patient.profile.xml"));
		loader.loadResourceProfiles(ResourceLoadingUtils.getReaderFromClasspath("qicore-organization.profile.xml"));
		FhirExtensionManager manager = new FhirExtensionManager();
		manager.addProfileRepositoryLocation("/Users/cnanjo/repository/fhir/trunk/build/publish");
		manager.addProfileRepositoryLocation("/Users/cnanjo/repository/fhir/trunk/build/publish/qicore");
		manager.initialize();
		loader.setExtensionManager(manager);
		MethodBodyGenerator utils = new MethodBodyGenerator();
		utils.initialize();
		InterfaceAdapterGenerator generator = new InterfaceAdapterGenerator("org.socraticgrid.fhir.generated", loader, utils);
		generator.generateInterfaceAndAdapter("QICore-Patient");
	}
	
	@Test
	public void testRoaster() {
		JavaClassSource clazz = Roaster.create(JavaClassSource.class).setName("MyClass");
		String type = "ca.uhn.fhir.model.primitive.BoundCodeDt<ca.uhn.fhir.model.dstu2.valueset.ConditionStatusEnum>";
		FieldSource<JavaClassSource> field = clazz.addField().setName("param").setType(type);
		//Assert.assertEquals(type, field.getType().getQualifiedName());//TODO This test will fail. Pending ROASTER fix from Davide.
		//Assert.assertEquals(2, clazz.getImports().size());
	}

}
