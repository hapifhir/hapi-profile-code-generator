package org.cdscollaborative.tools.fhir.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt.Type;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;
import ca.uhn.fhir.model.primitive.UriDt;

public class FhirExtension {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(FhirExtension.class);
	
	private String profileUrl;
	private String shortDescription;
	private String longDescription;
	private int lowCardinality;
	private String highCardinality;
	private String uri;
	private List<String> context;
	private String contextType;
	private List<Type> types;

	public FhirExtension() {}

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

	public int getLowCardinality() {
		return lowCardinality;
	}

	public void setLowCardinality(int lowCardinality) {
		this.lowCardinality = lowCardinality;
	}

	public String getHighCardinality() {
		return highCardinality;
	}

	public void setHighCardinality(String highCardinality) {
		this.highCardinality = highCardinality;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public List<String> getContext() {
		return context;
	}

	public void setContext(List<String> context) {
		this.context = context;
	}

	public String getContextType() {
		return contextType;
	}

	public void setContextType(String contextType) {
		this.contextType = contextType;
	}
	
	public List<Type> getTypes() {
		return types;
	}

	public void setTypes(List<Type> types) {
		this.types = types;
	}

	/**
	 * Factory method that creates a FhirExtension from a StructureDefinition
	 * 
	 * @param definition
	 * @return
	 */
	public static FhirExtension populateFromStructureDefinition(StructureDefinition definition) {
		FhirExtension extension = new FhirExtension();
		extension.setUri(definition.getUrl());
		//extension.setContext(definition.getContext());//TODO Handle
		extension.setContextType(definition.getContextType());
		int numElements = definition.getDifferential().getElement().size();
		if(numElements != 3) {
			LOGGER.warn(numElements + " contained in profile differential");
		} else {
			for(ElementDefinitionDt element : definition.getDifferential().getElement()) {
				processExtensionElement(element, extension);
				processExtensionUrlElement(element, extension);
				processExtensionValueElement(element, extension);
			}
		}
		return extension;
	}
	
	protected static void processExtensionElement(ElementDefinitionDt element, FhirExtension extension) {
		if(element.getPath().equals("Extension")) {
			extension.setLowCardinality(element.getMin());
			extension.setHighCardinality(element.getMax());
			extension.setShortDescription(element.getShort());
			extension.setLongDescription(element.getDefinition());
		}
		
	}
	
	protected static void processExtensionUrlElement(ElementDefinitionDt element, FhirExtension extension) {
		if(element.getPath().equals("Extension.url")) {
			if(element.getFixed() instanceof UriDt) {
				extension.setProfileUrl(((UriDt)element.getFixed()).getValue());
			}
		}
	}
	
	protected static void processExtensionValueElement(ElementDefinitionDt element, FhirExtension extension) {
		if(element.getPath().equals("Extension.value[x]")) {
			extension.setTypes(element.getType());
		}
	}
	
}
