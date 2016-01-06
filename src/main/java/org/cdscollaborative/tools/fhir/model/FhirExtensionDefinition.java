package org.cdscollaborative.tools.fhir.model;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.cdscollaborative.common.utils.io.ResourceLoadingUtils;
import org.cdscollaborative.tools.fhir.utils.PathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.api.IResource;
import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;
import ca.uhn.fhir.parser.IParser;

/**
 * TODO Handle http://hl7.org/fhir/StructureDefinition/goal-target
 * 
 * @author cnanjo
 *
 */
public class FhirExtensionDefinition {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(FhirExtensionDefinition.class);
	
	private String profileUrl;
	private String shortDescription;
	private String longDescription;
	private Map<String, ElementDefinitionDt> extensionIndex;
	private List<List<ElementDefinitionDt>> orderedHierarchy = new ArrayList<List<ElementDefinitionDt>>();

	public FhirExtensionDefinition() {
		extensionIndex = new HashMap<String, ElementDefinitionDt>();
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}
	
	public Map<String, ElementDefinitionDt> getExtensions() {
		return this.extensionIndex;
	}
	
	public void addExtension(ElementDefinitionDt extension) {
		this.extensionIndex.put(extension.getName(), extension);
	}
	
	protected void addToOrderedHierarchy(int level, ElementDefinitionDt element) {
		List<ElementDefinitionDt> levelItems = null;
		if(orderedHierarchy.size() <= level || orderedHierarchy.get(level) == null) {
			levelItems = new ArrayList<ElementDefinitionDt>();
			orderedHierarchy.add(levelItems);
		}
		levelItems = orderedHierarchy.get(level);
		levelItems.add(element);
	}
	
	protected ElementDefinitionDt getLastVisitedAtLevel(int level) {
		List<ElementDefinitionDt> levelList = null;
		if(orderedHierarchy.size() > level && orderedHierarchy.get(level) != null && orderedHierarchy.get(level).size() >= 0) {
			levelList = orderedHierarchy.get(level);
			return levelList.get(levelList.size() - 1);
		} else {
			return null;
		}
	}
	
	public ElementDefinitionDt getExtensionByName(String name) {
		return extensionIndex.get(name);
	}
	
	public ElementDefinitionDt getExtensionByFullyQualifiedUri(String uri) {
		String name = PathUtils.getExtensionAnchor(uri);
		if(name != null) {
			return getExtensionByName(name);
		} else {
			return null;
		}
	}

	/**
	 * Factory method that creates a FhirExtension from a StructureDefinition
	 * 
	 * @param definition
	 * @return
	 */
	public static FhirExtensionDefinition populateFromStructureDefinition(StructureDefinition definition) {
		FhirExtensionDefinition extensionDefinition = new FhirExtensionDefinition();
		extensionDefinition.setProfileUrl(definition.getUrl());
		List<ElementDefinitionDt> elements = definition.getSnapshot().getElement();
		int currentLevel = 0;
		for(ElementDefinitionDt element : elements) {
			LOGGER.debug("Visiting " + element.getPath() + " --> " + element.getName());
			currentLevel = PathUtils.getExtensionLevelInPath(element.getPath());
			if(element.getPath().equals("Extension")) {
				ElementDefinitionDt consolidated = new ElementDefinitionDt();
				consolidated.setName(PathUtils.getLastResourcePathComponent(definition.getUrl()));
				consolidated.setPath(element.getPath());
				extensionDefinition.addExtension(consolidated);
				extensionDefinition.addToOrderedHierarchy(currentLevel, consolidated);
			} else if(StringUtils.isNotBlank(element.getName()) && !element.getName().equals("extension")) {
				ElementDefinitionDt consolidated = new ElementDefinitionDt();
				consolidated.setName(element.getName());
				consolidated.setPath(element.getPath());
				extensionDefinition.addExtension(consolidated);
				extensionDefinition.addToOrderedHierarchy(currentLevel, consolidated);
			}
			ElementDefinitionDt consolidated = extensionDefinition.getLastVisitedAtLevel(currentLevel);
			if(consolidated == null) {
				consolidated = extensionDefinition.getLastVisitedAtLevel(currentLevel - 1);
			}
 			if(isExtensionRoot(element)) {
				processRootExtensionElement(element, consolidated);
			} else {
				processNestedExtensionElement(element, consolidated);
			}
		}
		return extensionDefinition;
	}
	
	public static boolean isExtensionRoot(ElementDefinitionDt element) {
		return element.getPath() != null && (element.getPath().equals("Extension") || element.getPath().equals("Extension.id") || element.getPath().equals("Extension.url") || element.getPath().equals("Extension.value[x]") ||
				(element.getPath().equals("Extension.extension") && StringUtils.isBlank(element.getName())));
	}
	
	protected static ElementDefinitionDt processRootExtensionElement(ElementDefinitionDt original, ElementDefinitionDt consolidated) {
		if(original.getPath().equals("Extension")) {
			processExtensionElement(original, consolidated);
		} else if(original.getPath().equals("Extension.url")) {
			processExtensionUrlElement(original, consolidated);
		} else if(original.getPath().equals("Extension.value[x]")) {
			processExtensionValueElement(original, consolidated);
		} else if(original.getPath().equals("Extension.id")) {
			//Do nothing
		}
		return consolidated;
	}
	
	protected static ElementDefinitionDt processNestedExtensionElement(ElementDefinitionDt original, ElementDefinitionDt consolidated) {
		if(original.getPath().endsWith(".extension") && StringUtils.isNotEmpty(original.getName()) && !original.getName().equals("extension")) {
			processExtensionElement(original, consolidated);
		} else if(original.getPath().endsWith(".extension.url")) {
			processExtensionUrlElement(original, consolidated);
		} else if(original.getPath().contains("value")) {
			processExtensionValueElement(original, consolidated);
		} else if(original.getPath().endsWith(".extension.id")) {
			//Do nothing
		}
		return consolidated;
	}
	
	protected static void processExtensionElement(ElementDefinitionDt element, ElementDefinitionDt consolidated) {
		consolidated.setMin(element.getMin());
		consolidated.setMax(element.getMax());
		consolidated.setShort(element.getShort());
		consolidated.setDefinition(element.getDefinitionElement());
		consolidated.setType(element.getType());
	}
	
	protected static void processExtensionUrlElement(ElementDefinitionDt element, ElementDefinitionDt consolidated) {
		consolidated.setFixed(element.getFixed());
	}
	
	protected static void processExtensionValueElement(ElementDefinitionDt element, ElementDefinitionDt consolidated) {
		consolidated.setType(element.getType());
	}
	
	public static FhirExtensionDefinition loadExtension(String extensionFilePath) {
		File file = ResourceLoadingUtils.getFileFromResourceClasspath(extensionFilePath);
		return loadExtension(file);
	}
	
	public static FhirExtensionDefinition loadExtension(File extensionFile) {
		FhirExtensionDefinition extension = null;
		try(FileReader reader = new FileReader(extensionFile)) {
			FhirContext context = FhirContext.forDstu2();
			IParser parser = context.newXmlParser();
			IResource resource = (IResource) parser.parseResource(reader);
			if(resource instanceof StructureDefinition) {
				extension = FhirExtensionDefinition.populateFromStructureDefinition((StructureDefinition)resource);
			}
		} catch(Exception e) {
			LOGGER.error("Error loading or parsing extension file", e);
			throw new RuntimeException("Error loading or parsing extension file", e);
		}
		return extension;
	}
	
}
