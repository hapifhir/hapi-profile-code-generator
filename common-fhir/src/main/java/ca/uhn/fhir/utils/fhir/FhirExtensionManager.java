package ca.uhn.fhir.utils.fhir;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.utils.fhir.model.FhirExtensionDefinition;

/**
 * Class manages FHIR extensions and acts like an in-memory FHIR extension registry.
 * 
 * @author Claude Nanjo
 *
 */
public class FhirExtensionManager {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(FhirExtensionManager.class);
	
	private List<String> standardFhirNamespaces;
	private Map<String, FhirExtensionDefinition> extensionRegistry;
	private List<String> profileRepositoryLocations;

	public FhirExtensionManager() {
		standardFhirNamespaces = new ArrayList<String>();
		extensionRegistry = new HashMap<String, FhirExtensionDefinition>();
		profileRepositoryLocations = new ArrayList<String>();
	}
	
	public void initialize() {
		populateStandardFhirNamespaces();
		loadExtensions();
	}
	
	/**
	 * Populates the common FHIR profile namespace prefixes
	 */
	private void populateStandardFhirNamespaces() { //TODO Do by scanning somehow. For now, hard-coded
		standardFhirNamespaces.add("http://hl7.org/fhir/StructureDefinition/");
	}
	
	public void addProfileRepositoryLocation(String location) {
		profileRepositoryLocations.add(location);
	}
	
	public void addAllProfileRepositoryLocation(List<String> locations) {
		if(locations != null) {
			profileRepositoryLocations.addAll(locations);
		}
	}
	
	public void loadExtensions() {
		for(String location : profileRepositoryLocations) {
			LOGGER.info("Processing profile directory: " + location);
			File profileDirectory = new File(location);
			File[] extensions = findExtensionDefinitionFiles(profileDirectory);
			for(File extensionFile : extensions) {
				FhirExtensionDefinition extensionDef = FhirExtensionDefinition.loadExtension(extensionFile);
				if(extensionDef != null) {
					LOGGER.info("Adding extension: " + extensionDef.getProfileUrl());//TODO Fill in later
					extensionRegistry.put(extensionDef.getProfileUrl(), extensionDef);
				}
			}
		}
	}
	
	public int getRegistrySize() {
		return extensionRegistry.size();
	}
	
	public boolean registryContains(String uri) {
		return extensionRegistry.containsKey(uri);
	}
	
	public FhirExtensionDefinition getFromRegistry(String uri) {
		return extensionRegistry.get(uri);
	}
	
	protected File[] findExtensionDefinitionFiles(File profileDirectory) {
		LOGGER.debug("Profile directory processed: " + profileDirectory);
		File [] extensions = profileDirectory.listFiles(new FilenameFilter() {
		    @Override
		    public boolean accept(File dir, String name) {
		        return (name.startsWith("extension") && name.endsWith(".xml"));
		    }
		});
		return extensions;
	}

}
