package ca.uhn.fhir.utils.fhir.model;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import org.apache.commons.lang3.StringUtils;
import org.hl7.fhir.dstu3.model.ElementDefinition;
import org.hl7.fhir.dstu3.model.Resource;
import org.hl7.fhir.dstu3.model.StructureDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.api.IResource;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.utils.common.io.ResourceLoadingUtils;
import ca.uhn.fhir.utils.fhir.PathUtils;

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
	private Map<String, ElementDefinition> extensionIndex;
	private List<List<ElementDefinition>> orderedHierarchy = new ArrayList<List<ElementDefinition>>();

	public FhirExtensionDefinition() {
		extensionIndex = new HashMap<String, ElementDefinition>();
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
	
	public Map<String, ElementDefinition> getExtensions() {
		return this.extensionIndex;
	}
	
	public void addExtension(ElementDefinition extension) {
		this.extensionIndex.put(extension.getSliceName(), extension);
	}
	
	protected void addToOrderedHierarchy(int level, ElementDefinition element) {
		List<ElementDefinition> levelItems = null;
		if(orderedHierarchy.size() <= level || orderedHierarchy.get(level) == null) {
			levelItems = new ArrayList<ElementDefinition>();
			orderedHierarchy.add(levelItems);
		}
		levelItems = orderedHierarchy.get(level);
		levelItems.add(element);
	}
	
	protected ElementDefinition getLastVisitedAtLevel(int level) {
		List<ElementDefinition> levelList = null;
		if(orderedHierarchy.size() > level && orderedHierarchy.get(level) != null && orderedHierarchy.get(level).size() >= 0) {
			levelList = orderedHierarchy.get(level);
			return levelList.get(levelList.size() - 1);
		} else {
			return null;
		}
	}
	
	public ElementDefinition getExtensionByName(String name) {
		return extensionIndex.get(name);
	}

	public ElementDefinitionDt getExtensionByNameDstu2(String name) {
//		return extensionIndex.get(name);
		return null;
	}
	
	public ElementDefinition getExtensionByFullyQualifiedUri(String uri) {
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
		if(definition.getUrl().contains("relatedCondition")) {
			System.out.println("STOP HERE");
		}
		List<ElementDefinition> elements = definition.getSnapshot().getElement();
		int currentLevel = 0;
		for(ElementDefinition element : elements) {
			LOGGER.debug("Visiting " + element.getPath() + " --> " + element.getSliceName());
			currentLevel = PathUtils.getExtensionLevelInPath(element.getPath());
			if(element.getPath().equals("Extension")) {
				ElementDefinition consolidated = new ElementDefinition();
				consolidated.setSliceName(PathUtils.getLastResourcePathComponent(definition.getUrl()));
				consolidated.setPath(element.getPath());
				extensionDefinition.addExtension(consolidated);
				extensionDefinition.addToOrderedHierarchy(currentLevel, consolidated);
			} else if(StringUtils.isNotBlank(element.getSliceName()) && !element.getSliceName().equals("extension")) {
				ElementDefinition consolidated = new ElementDefinition();
				consolidated.setSliceName(element.getSliceName());
				consolidated.setPath(element.getPath());
				extensionDefinition.addExtension(consolidated);
				extensionDefinition.addToOrderedHierarchy(currentLevel, consolidated);
			}
			ElementDefinition consolidated = extensionDefinition.getLastVisitedAtLevel(currentLevel);
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
	
	public static boolean isExtensionRoot(ElementDefinition element) {
		return element.getPath() != null && (element.getPath().equals("Extension") || element.getPath().equals("Extension.id") || element.getPath().equals("Extension.url") || element.getPath().equals("Extension.value[x]") ||
				(element.getPath().equals("Extension.extension") && StringUtils.isBlank(element.getSliceName())));
	}
	
	protected static ElementDefinition processRootExtensionElement(ElementDefinition original, ElementDefinition consolidated) {
		if(original.getPath().equals("Extension")) {
			processExtensionElement(original, consolidated);
		} else if(original.getPath().equals("Extension.url")) {
			processExtensionUrlElement(original, consolidated);
		} else if(original.getPath().equals("Extension.value[x]")) {
			processExtensionMultiValueElement(original, consolidated);
		} else if(original.getPath().equals("Extension.id")) {
			//Do nothing
		}
		return consolidated;
	}
	
	protected static ElementDefinition processNestedExtensionElement(ElementDefinition original, ElementDefinition consolidated) {
		if(original.getPath().endsWith(".extension") && StringUtils.isNotEmpty(original.getSliceName()) && !original.getSliceName().equals("extension")) {
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
	
	protected static void processExtensionElement(ElementDefinition element, ElementDefinition consolidated) {
		consolidated.setMin(element.getMin());
		consolidated.setMax(element.getMax());
		consolidated.setShort(element.getShort());
		consolidated.setDefinition(element.getDefinitionElement().getValue());
		consolidated.setType(element.getType());
	}
	
	protected static void processExtensionUrlElement(ElementDefinition element, ElementDefinition consolidated) {
		consolidated.setFixed(element.getFixed());
	}
	
	protected static void processExtensionValueElement(ElementDefinition element, ElementDefinition consolidated) {
		consolidated.setType(element.getType());
	}

	protected static void processExtensionMultiValueElement(ElementDefinition element, ElementDefinition consolidated) {
		if(element.getType() != null && element.getType().size() == 38) {
			consolidated.getType().clear(); //TODO This is a hack. Currently, structures simply specify a value[x] even if they have no value.
			//Clearing the type or else downstream logic will be assume that this extension has 38 value types.
			//Need to find out from Grahame if multi-valued extension also use the value[x] syntax. This will break if values are no longer 38 in the future.
		} else {
			consolidated.setType(element.getType());
		}
	}
	
	public static FhirExtensionDefinition loadExtension(String extensionFilePath) {
		File file = ResourceLoadingUtils.getFileFromResourceClasspath(extensionFilePath);
		return loadExtension(file);
	}
	
	public static FhirExtensionDefinition loadExtension(File extensionFile) {
		FhirExtensionDefinition extension = null;
		try(FileReader reader = new FileReader(extensionFile)) {
			FhirContext context = FhirContext.forDstu3();
			IParser parser = context.newXmlParser();
			Resource resource = (Resource) parser.parseResource(reader);
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
