package org.cdscollaborative.tools.fhir.codegenerator.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.cdscollaborative.common.utils.io.ResourceLoadingUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
		assertEquals(3, configurator.getProfileFilePaths().size());
		assertEquals("/root/profile/source/path1", configurator.getProfileFilePaths().get(0));
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
		assertEquals(4, configurator.getProfileDirectoryPaths().size());
		assertEquals("/root/profile/dir/dir4", configurator.getProfileDirectoryPaths().get(3));
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
		assertEquals("/root/dir/target", configurator.getTargetCodeGenerationDirectory());
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
		assertEquals(11, configurator.getProfileNameList().size());
		assertEquals("DeviceUseStatement", configurator.getProfileNameList().get(10));
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
		assertEquals(5, configurator.getExtensionRepositories().size());
		assertEquals("/root/extension/dir/path3", configurator.getExtensionRepositories().get(2));
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
