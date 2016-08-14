package ca.uhn.fhir.utils.fhir;

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.uhn.fhir.context.FhirContext;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.hl7.fhir.dstu3.model.StructureDefinition;
import org.hl7.fhir.instance.model.api.IBaseResource;
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
			List<File> extensions = findExtensionDefinitionFiles(profileDirectory);
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
	
	protected List<File> findExtensionDefinitionFiles(File profileDirectory) {
		LOGGER.debug("Profile directory processed: " + profileDirectory);
		if(!profileDirectory.isDirectory()) {
			throw new RuntimeException("Invalid extension directory path. Path is not a valid directory: " + profileDirectory);
		}
		File[] profiles = null;
		try {
			profiles = profileDirectory.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					boolean isStructureDefinition = (
							(name.startsWith("StructureDefinition-") && name.endsWith(".xml")) ||
							(name.startsWith("extension-") && name.endsWith(".xml")) ||
							(name.endsWith(".profile.xml")));
					return isStructureDefinition;
				}
			});
		} catch(Exception e) {e.printStackTrace();}
		List<File> extensions = new ArrayList<File>();
		FhirContext ctx = FhirContext.forDstu3();
		if(profiles != null) {
			System.out.println(profiles.length);
		} else {
			throw new NullPointerException("Profiles is null!");
		}
		for(File file : profiles) {
			try {
				IBaseResource resource = ctx.newXmlParser().parseResource(new FileReader(file));
				if(resource instanceof StructureDefinition) {
					StructureDefinition profile = (StructureDefinition) ctx.newXmlParser().parseResource(new FileReader(file));
					if (profile.getType() != null && profile.getType().equals("Extension")) {
						extensions.add(file);
					}
				} else {
					System.out.println(file.getName());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return extensions;
	}

}
