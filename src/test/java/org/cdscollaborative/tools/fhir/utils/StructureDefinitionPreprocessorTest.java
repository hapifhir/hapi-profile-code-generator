package org.cdscollaborative.tools.fhir.utils;

import static org.junit.Assert.fail;

import org.cdscollaborative.common.utils.io.ResourceLoadingUtils;
import org.junit.Before;
import org.junit.Test;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;
import ca.uhn.fhir.parser.IParser;

public class StructureDefinitionPreprocessorTest {
	
	private StructureDefinitionPreprocessor processor;
	
	@Before
	public void setup() {
		FhirContext context = FhirContext.forDstu2();
		IParser parser = context.newXmlParser();
		StructureDefinition profile = (StructureDefinition) parser.parseResource(ResourceLoadingUtils.getReaderFromClasspath("qicore-patient.profile.xml"));
		this.processor = new StructureDefinitionPreprocessor(profile);
	}

	@Test
	public void testProcessElements() {
		processor.processElements();
	}

}
