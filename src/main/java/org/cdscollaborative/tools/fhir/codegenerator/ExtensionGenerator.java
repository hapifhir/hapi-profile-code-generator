package org.cdscollaborative.tools.fhir.codegenerator;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.cdscollaborative.common.utils.graph.Node;
import org.cdscollaborative.tools.fhir.utils.ProfileWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;

public class ExtensionGenerator {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(ExtensionGenerator.class);
	
	private Map<String, Node<ElementDefinitionDt>> extensionGraphs;

	public ExtensionGenerator() {
		extensionGraphs = new HashMap<>();
	}
	
	public Map<String, Node<ElementDefinitionDt>> getExtensionGraphs() {
		return extensionGraphs;
	}

	public void setExtensionGraphs(
			Map<String, Node<ElementDefinitionDt>> extensionGraphs) {
		this.extensionGraphs = extensionGraphs;
	}
	
	public void processProfile(StructureDefinition profile) {
		for(ElementDefinitionDt element : profile.getSnapshot().getElement()) {
			if(ProfileWalker.isFhirExtension(element)) {
				if(element.getName() == null) {
					continue;//exclude standard extension definitions
				}
				String name = element.getName();
				if(StringUtils.isNotBlank(name)) {
					String[] nameComponents = name.split("\\.");
					if(nameComponents.length == 1) {
						if(!extensionGraphs.containsKey(StringUtils.capitalize(nameComponents[0]))) {
							Node<ElementDefinitionDt> root = new Node<>(StringUtils.capitalize(nameComponents[0]));
							root.setOriginalName(nameComponents[0]);
							root.setPayload(element);
							extensionGraphs.put(StringUtils.capitalize(nameComponents[0]), root);
						} else {
							throw new RuntimeException("Duplicate root node: " + name);
						}
					} else {
						Node<ElementDefinitionDt> root = extensionGraphs.get(StringUtils.capitalize(nameComponents[0]));
						if(root == null) {
							System.out.println(extensionGraphs);
							LOGGER.error("Unknown root for path " + name);//TODO: Need to add logic for adding extensions on types.
						} else {
							root.addToPath(name, element);
						}
					}
				}
			}
		}
	}

}
