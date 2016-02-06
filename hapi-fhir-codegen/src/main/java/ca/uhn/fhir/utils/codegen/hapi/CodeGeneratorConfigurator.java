package ca.uhn.fhir.utils.codegen.hapi;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import ca.uhn.fhir.utils.codegen.CodeTemplateUtils;
import ca.uhn.fhir.utils.common.xml.XmlUtils;
import ca.uhn.fhir.utils.fhir.FhirExtensionManager;

/**
 * Naive configurator whose responsibilities include:
 * 
 * <ol>
 * <li>Loading all configuration metadata for code generation from a
 * configuration file
 * <li>Fetching relevant resources 
 * <li>Providing factory
 * methods for building and configurating code generation objects.
 * </ol>
 * 
 * @author Claude Nanjo
 *
 */
public class CodeGeneratorConfigurator {

	public static final Logger LOGGER = LoggerFactory
			.getLogger(CodeGeneratorConfigurator.class);
	public static final String DEFAULT_GENERATED_CODE_PACKAGE = "org.socraticgrid.fhir.generated";
	public static final String ELEMENT_LABEL_PROFILE_SOURCE_FILE_PATH = "profileSourceFilePath";
	public static final String ELEMENT_LABEL_PROFILE_SOURCE_DIRECTORY_PATH = "profileDirectoryPath";
	public static final String ELEMENT_LABEL_PROFILE_NAME = "profileName";
	public static final String ELEMENT_LABEL_EXTENSION_REPOSITORY = "extensionRepository";
	public static final String ELEMENT_LABEL_GENERATED_CODE_PACKAGE = "generatedCodePackage";
	public static final String ELEMENT_LABEL_TARGET_CODE_DIRECTORY = "targetCodeGenerationDirectory";

	private String configurationFilePath;
	private List<String> profileSourceFilePaths;
	private List<String> profileDirectoryPaths;
	private List<String> profileNameList;
	private List<String> extensionRepositories;
	private Document configuration;
	private String generatedCodePackage = DEFAULT_GENERATED_CODE_PACKAGE;
	private String targetCodeGenerationDirectory;
	/**
	 * Constructor for CodeGeneratorConfigurator.
	 * 
	 * Precondition: configFilePath exists and is a valid configuration file.
	 * 
	 * Note: To configure the configurator, it does not suffice to instantiate
	 * it. You must also call initialize() prior to first use.
	 * 
	 * @param configFilePath
	 */
	public CodeGeneratorConfigurator(String configFilePath) {
		profileSourceFilePaths = new ArrayList<>();
		profileDirectoryPaths = new ArrayList<>();
		configurationFilePath = configFilePath;
		profileNameList = new ArrayList<String>();
		extensionRepositories = new ArrayList<String>();
	}
	
	/**
	 * Returns list of profile file paths
	 * @return
	 */
	public List<String> getProfileFilePaths() {
		return profileSourceFilePaths;
	}
	
	/**
	 * Sets list of profile file paths
	 * @param profileFilePaths
	 */
	public void setProfileFilePaths(List<String> profileFilePaths) {
		this.profileSourceFilePaths = profileFilePaths;
	}

	/**
	 * Returns profile directory paths
	 * 
	 * @return
	 */
	public List<String> getProfileDirectoryPaths() {
		return profileDirectoryPaths;
	}
	
	/**
	 * Sets list of directory paths.
	 * 
	 * Note: directory may only contain profile definition files at this time.
	 * At a future time, this requirement may be relaxed.
	 * 
	 * @param profileDirectoryPaths
	 */
	public void setProfileDirectoryPaths(List<String> profileDirectoryPaths) {
		this.profileDirectoryPaths = profileDirectoryPaths;
	}

	/**
	 * Returns the java package to assign to generated classes
	 * 
	 * @return
	 */
	public String getGeneratedCodePackage() {
		return generatedCodePackage;
	}

	/**
	 * Sets the java package for the generated classes
	 * 
	 * @param generatedCodePackage
	 */
	public void setGeneratedCodePackage(String generatedCodePackage) {
		this.generatedCodePackage = generatedCodePackage;
	}
	
	/**
	 * Returns the target directory for code generation.
	 * 
	 * @return
	 */
	public String getTargetCodeGenerationDirectory() {
		return targetCodeGenerationDirectory;
	}
	
	/**
	 * Sets the target directory for code generation
	 * 
	 * @param targetCodeGenerationDirectory
	 */
	public void setTargetCodeGenerationDirectory(
			String targetCodeGenerationDirectory) {
		this.targetCodeGenerationDirectory = targetCodeGenerationDirectory;
	}

	/**
	 * Returns the configuration file path
	 * 
	 * @return
	 */
	public String getConfigurationFilePath() {
		return configurationFilePath;
	}
	
	/**
	 * Sets the configuration file path
	 * 
	 * @param configurationFilePath
	 */
	public void setConfigurationFilePath(String configurationFilePath) {
		this.configurationFilePath = configurationFilePath;
	}
	
	/**
	 * Returns the list of profile names used for code generation
	 * 
	 * @return
	 */
	public List<String> getProfileNameList() {
		return profileNameList;
	}
	
	/**
	 * Sets the list of profile names to use for code generation
	 * 
	 * @param profileNameList
	 */
	public void setProfileNameList(List<String> profileNameList) {
		this.profileNameList = profileNameList;
	}
	
	/**
	 * Returns the repository locations for FHIR Extensions
	 * 
	 * @return
	 */
	public List<String> getExtensionRepositories() {
		return extensionRepositories;
	}
	
	/**
	 * Sets the repository locations for FHIR extensions
	 * 
	 * @param extensionRepositories
	 */
	public void setExtensionRepositories(List<String> extensionRepositories) {
		this.extensionRepositories = extensionRepositories;
	}
	
	/**
	 * Reads the configuration XML file and configures this instance.
	 * Note that resources are not fetched at this time.
	 */
	public void initialize() {
		try {
			loadConfiguration();
			loadProfileSourceFilePaths();
			loadProfileDirectoryPath();
			loadProfileNames();
			loadExtensionRepositories();
			loadGeneratedCodePackage();
			loadTargetCodeGenerationDirectory();
		} catch (Exception e) {
			LOGGER.error("Error configurating CodeGeneratorConfigurator", e);
			throw new RuntimeException(
					"Error configurating CodeGeneratorConfigurator", e);
		}
	}

	/**
	 * Returns a configured and initialized CodeGeneratorConfigurator.
	 * 
	 * @param configurationFilePath
	 * @return
	 */
	public static CodeGeneratorConfigurator buildConfigurator(
			String configurationFilePath) {
		CodeGeneratorConfigurator configurator = new CodeGeneratorConfigurator(
				configurationFilePath);
		configurator.initialize();
		return configurator;
	}
	
	/**
	 * Builds a new resource loader based on the configuration metadata associated
	 * with the argument.
	 * 
	 * @param config Configuration to use for configuring the CoreFhirResourceLoader.
	 * @param loadExtensions Flag indicating whether to preload FHIR Extensions.
	 * @return configured CoreResourceLoader.
	 * 
	 */
	public static FhirResourceManager buildFhirResourceManager(
			CodeGeneratorConfigurator config, boolean loadExtensions) {
		try {
			FhirResourceManager loader = new FhirResourceManager();
			loader.loadResourceProfiles(config);
			if(loadExtensions) {
				FhirExtensionManager manager = new FhirExtensionManager();
				manager.addAllProfileRepositoryLocation(config
						.getExtensionRepositories());
				manager.initialize();
				loader.setExtensionManager(manager);
			}
			return loader;
		} catch (Exception e) {
			LOGGER.error("Error creating and configurating resource loader", e);
			throw new RuntimeException(
					"Error creating and configurating resource loader", e);
		}
	}

	/**
	 * Factory method for building and configurating the ProfileInterfaceGenerator from
	 * the metadata specified in the config file.
	 * 
	 * @param config
	 * @param resourceLoader
	 * @return
	 */
	public static InterfaceAdapterGenerator buildInterfaceAdapterGenerator(
			CodeGeneratorConfigurator config, FhirResourceManager resourceLoader) {
		CodeTemplateUtils templateUtils = new CodeTemplateUtils().initialize();
		InterfaceAdapterGenerator generator = new InterfaceAdapterGenerator(
				config.getGeneratedCodePackage(), resourceLoader, templateUtils);
		generator.setResourceLoadingPlan(config.getProfileNameList());
		return generator;
	}

	/**
	 * Reads file input stream into memory. Configuration at this point is a 
	 * DOM object.
	 */
	protected void loadConfiguration() {
		try (FileInputStream fis = new FileInputStream(new File(
				configurationFilePath))) {
			configuration = XmlUtils.createDocumentFromInputStream(fis);
		} catch (Exception e) {
			LOGGER.error("Error loading the configuration at "
					+ configurationFilePath, e);
			throw new RuntimeException("Error loading the configuration at "
					+ configurationFilePath, e);
		}
	}
	
	/**
	 * Loads profile source files from the configuration file. 
	 */
	private void loadProfileSourceFilePaths() {
		loadElementList(ELEMENT_LABEL_PROFILE_SOURCE_FILE_PATH,
				profileSourceFilePaths);
	}
	
	/**
	 * Loads profile directory paths from the configuration file.
	 */
	private void loadProfileDirectoryPath() {
		loadElementList(ELEMENT_LABEL_PROFILE_SOURCE_DIRECTORY_PATH,
				profileDirectoryPaths);
	}
	
	/**
	 * Loads the set of profile names to consider for code generation.
	 */
	private void loadProfileNames() {
		loadElementList(ELEMENT_LABEL_PROFILE_NAME, profileNameList);
	}

	/**
	 * Loads all directories that contain relevant FHIR extensions for code generation.
	 */
	public void loadExtensionRepositories() {
		loadElementList(ELEMENT_LABEL_EXTENSION_REPOSITORY,
				extensionRepositories);
	}
	
	/**
	 * Loads the package name for the generated code. At this time, only one package name is supported.
	 */
	public void loadGeneratedCodePackage() {
		generatedCodePackage = loadElementContentFromConfiguration(ELEMENT_LABEL_GENERATED_CODE_PACKAGE);
	}
	
	/**
	 * Loads the target directory for the generated code. 
	 */
	public void loadTargetCodeGenerationDirectory() {
		targetCodeGenerationDirectory = loadElementContentFromConfiguration(ELEMENT_LABEL_TARGET_CODE_DIRECTORY);
	}

	/**
	 * Helper method for loading an XML list of string elements into a java
	 * List<String>.
	 * 
	 * @param elementTagName
	 * @param itemList
	 */
	private void loadElementList(String elementTagName, List<String> itemList) {
		try {
			NodeList itemListNL = configuration
					.getElementsByTagName(elementTagName);
			for (int index = 0; index < itemListNL.getLength(); index++) {
				Element item = (Element) itemListNL.item(index);
				String itemTextContent = item.getTextContent();
				itemList.add(itemTextContent);
			}
		} catch (Exception e) {
			LOGGER.error("Error occurred while loading element list: "
					+ elementTagName, e);
			throw new RuntimeException(
					"Error occurred while loading element list: ", e);
		}
	}

	/**
	 * Helper method for loading an XML element.
	 * 
	 * @param elementTagName
	 * @param itemList
	 *            The text value of that element.
	 */
	private String loadElementContentFromConfiguration(String elementName) {
		NodeList itemNL = configuration.getElementsByTagName(elementName);
		String elementValue = null;
		if (itemNL == null || itemNL.getLength() == 0) {
			return elementValue;
		} else if (itemNL.getLength() == 1) {
			Element itemTextContent = (Element) itemNL.item(0);
			elementValue = itemTextContent.getTextContent();
		} else {
			throw new RuntimeException("Only one " + elementName
					+ " node supported in configuration file");
		}
		return elementValue;
	}
}
