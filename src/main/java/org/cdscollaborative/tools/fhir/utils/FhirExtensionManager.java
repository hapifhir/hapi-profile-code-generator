package org.cdscollaborative.tools.fhir.utils;

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cdscollaborative.tools.fhir.model.FhirExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.api.IResource;
import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt.Type;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;
import ca.uhn.fhir.parser.IParser;

/**
 * Class manages FHIR extensions and acts like an in-memory FHIR extension registry.
 * 
 * @author Claude Nanjo
 *
 */
public class FhirExtensionManager {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(FhirExtensionManager.class);
	
	private List<String> standardFhirNamespaces;
	private Map<String, FhirExtension> extensionRegistry;
	private List<String> profileRepositoryLocations;

	public FhirExtensionManager() {
		standardFhirNamespaces = new ArrayList<String>();
		extensionRegistry = new HashMap<String, FhirExtension>();
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
			System.out.println("Processing profile directory: " + location);
			File profileDirectory = new File(location);
			File[] extensions = findExtensionDefinitionFiles(profileDirectory);
			for(File extensionFile : extensions) {
				FhirExtension extensionDef = loadExtension(extensionFile);
				if(extensionDef != null) {
					LOGGER.info("Adding extension: " + extensionDef.getUri());//TODO Fill in later
					extensionRegistry.put(extensionDef.getUri(), extensionDef);
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
	
	public FhirExtension getFromRegistry(String uri) {
		return extensionRegistry.get(uri);
	}
	
	public FhirExtension loadExtension(File extensionFile) {
		FhirExtension extension = null;
		try(FileReader reader = new FileReader(extensionFile)) {
			FhirContext context = FhirContext.forDstu2();
			IParser parser = context.newXmlParser();
			IResource resource = (IResource) parser.parseResource(reader);
			if(resource instanceof StructureDefinition) {
				extension = FhirExtension.populateFromStructureDefinition((StructureDefinition)resource);
			}
		} catch(Exception e) {
			LOGGER.error("Error loading or parsing extension file", e);
			throw new RuntimeException("Error loading or parsing extension file", e);
		}
		return extension;
	}
	
	protected File[] findExtensionDefinitionFiles(File profileDirectory) {
		File [] extensions = profileDirectory.listFiles(new FilenameFilter() {
		    @Override
		    public boolean accept(File dir, String name) {
		        return (name.startsWith("extension") && name.endsWith(".xml"));
		    }
		});
		return extensions;
	}

}
