package ca.uhn.fhir.utils.codegen.hapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.uhn.fhir.utils.codegen.hapi.CodeGeneratorConfigurator;
import ca.uhn.fhir.utils.common.io.ResourceLoadingUtils;

public class CodeGeneratorConfiguratorTest {
	
	private CodeGeneratorConfigurator configurator;
	private String path;

	@Before
	public void setUp() throws Exception {
		path = ResourceLoadingUtils.getPathFromResourceClassPath("/config/generation-plan.xml");
		try {
			configurator = new CodeGeneratorConfigurator(path);
			configurator.initialize();
		} catch(Exception e) {
			e.printStackTrace();
			fail("Error configurating CodeGeneratorConfigurator");
		}
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetProfileFilePaths() {
		assertEquals(1, configurator.getProfileFilePaths().size());
		assertEquals("/Users/cnanjo/repository/fhir/trunk/build/publish/profiles-resources.xml", configurator.getProfileFilePaths().get(0));
	}

	@Test
	public void testSetProfileFilePaths() {
		List<String> filePaths = new ArrayList<>();
		filePaths.add("/root/profile/source/path4");
		configurator.setProfileFilePaths(filePaths);
		assertEquals(1, configurator.getProfileFilePaths().size());
		assertEquals("/root/profile/source/path4", configurator.getProfileFilePaths().get(0));
	}

	@Test
	public void testGetProfileDirectoryPaths() {
		assertEquals(1, configurator.getProfileDirectoryPaths().size());
		assertEquals("/Users/cnanjo/work/qicore/profiles", configurator.getProfileDirectoryPaths().get(0));
	}

	@Test
	public void testSetProfileDirectoryPaths() {
		List<String> dirPaths = new ArrayList<>();
		dirPaths.add("/root/profile/source/dir5");
		configurator.setProfileDirectoryPaths(dirPaths);
		assertEquals(1, configurator.getProfileDirectoryPaths().size());
		assertEquals("/root/profile/source/dir5", configurator.getProfileDirectoryPaths().get(0));
	}

	@Test
	public void testGetGeneratedCodePackage() {
		assertEquals(CodeGeneratorConfigurator.DEFAULT_GENERATED_CODE_PACKAGE, configurator.getGeneratedCodePackage());
	}

	@Test
	public void testSetGeneratedCodePackage() {
		configurator.setGeneratedCodePackage("org.example");
		assertEquals("org.example", configurator.getGeneratedCodePackage());
	}
	
	@Test
	public void testGetTargetCodeDirectory() {
		assertEquals("generated-source/java/", configurator.getTargetCodeGenerationDirectory());
	}

	@Test
	public void testSetTargetCodeRepository() {
		configurator.setTargetCodeGenerationDirectory("/root/dir/target1");
		assertEquals("/root/dir/target1", configurator.getTargetCodeGenerationDirectory());
	}

	@Test
	public void testGetConfigurationFilePath() {
		assertEquals(path, configurator.getConfigurationFilePath());
	}

	@Test
	public void testSetConfigurationFilePath() {
		configurator.setConfigurationFilePath("/root/path/1");
		assertEquals("/root/path/1", configurator.getConfigurationFilePath());
	}

	@Test
	public void testGetProfileNameList() {
		assertEquals(48, configurator.getProfileNameList().size());
		assertEquals("Goal", configurator.getProfileNameList().get(10));
	}

	@Test
	public void testSetProfileNameList() {
		List<String> profileNames = new ArrayList<>();
		profileNames.add("profileName1");
		profileNames.add("profileName2");
		configurator.setProfileNameList(profileNames);
		assertEquals(2, configurator.getProfileNameList().size());
		assertEquals("profileName2", configurator.getProfileNameList().get(1));
	}

	@Test
	public void testGetExtensionRepositories() {
		assertEquals(2, configurator.getExtensionRepositories().size());
		assertEquals("/Users/cnanjo/repository/fhir/trunk/build/publish/qicore", configurator.getExtensionRepositories().get(1));
	}

	@Test
	public void testSetExtensionRepositories() {
		List<String> extensionRepositories = new ArrayList<>();
		extensionRepositories.add("/root/extension/dir/path6");
		configurator.setExtensionRepositories(extensionRepositories);
		assertEquals(1, configurator.getExtensionRepositories().size());
		assertEquals("/root/extension/dir/path6", configurator.getExtensionRepositories().get(0));
	}

	@Test
	public void testInitialize() {
		//Currently tested in setup and through other calls
	}

	@Test
	public void testBuildConfigurator() {
		//TODO Need to mock
	}

	@Test
	public void testBuildCoreResourceLoader() {
		//TODO Need to mock
	}

	@Test
	public void testBuildFhirCodeGenerator() {
		//TODO Need to mock
	}

	@Test
	public void testLoadConfiguration() {
		//Currently tested in setup and through other calls
	}

	@Test
	public void testLoadExtensionRepositories() {
		//Currently tested in setup and through other calls
	}

	@Test
	public void testLoadGeneratedCodePackage() {
		//Currently tested in setup and through other calls
	}

}
