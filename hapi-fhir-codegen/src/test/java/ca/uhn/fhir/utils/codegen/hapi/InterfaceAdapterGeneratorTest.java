package ca.uhn.fhir.utils.codegen.hapi;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import ca.uhn.fhir.utils.codegen.hapi.FhirResourceManager;
import ca.uhn.fhir.utils.codegen.hapi.InterfaceAdapterGenerator;
import ca.uhn.fhir.utils.codegen.hapi.Main;
import ca.uhn.fhir.utils.common.io.ResourceLoadingUtils;
import ca.uhn.fhir.utils.fhir.FhirExtensionManager;

public class InterfaceAdapterGeneratorTest {
	
	private FhirResourceManager loader;

	@Before
	public void setUp() throws Exception {
		loader = new FhirResourceManager();
		loader.loadResourceProfiles(ResourceLoadingUtils.getReaderFromClasspath("profiles-resources.xml"));
		//loader.loadResourceProfiles(ResourceLoadingUtils.getReaderFromFilePath("/Users/cnanjo/repository/fhir/trunk/build/publish/profiles-resources.xml"));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGenerateInterface() {
		try {//TODO Fix hard coded path
			Main.generateCode(ResourceLoadingUtils.getPathFromResourceClassPath("/config/generation-plan.xml"), false);
		} catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testQiCoreProfile() {
		loader = new FhirResourceManager();
		loader.loadResourceProfiles(ResourceLoadingUtils.getReaderFromClasspath("patient-qicore-qicore-patient.profile.xml"));
		FhirExtensionManager manager = new FhirExtensionManager();
		manager.addProfileRepositoryLocation("/Users/cnanjo/repository/fhir/trunk/build/publish");
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
		Assert.assertEquals(type, field.getType().getQualifiedName());//This test will fail. Pending ROASTER fix from Davide.
		Assert.assertEquals(2, clazz.getImports().size());
	}

}
