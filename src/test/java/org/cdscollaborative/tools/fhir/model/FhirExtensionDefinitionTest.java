package org.cdscollaborative.tools.fhir.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.cdscollaborative.common.utils.io.ResourceLoadingUtils;
import org.junit.Before;
import org.junit.Test;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;
import ca.uhn.fhir.model.primitive.UriDt;
import ca.uhn.fhir.parser.IParser;

public class FhirExtensionDefinitionTest {

	private FhirExtensionDefinition definition;
	
	@Before
	public void setup() {
		FhirContext context = FhirContext.forDstu2();
		IParser parser = context.newXmlParser();
		StructureDefinition profile = (StructureDefinition) parser.parseResource(ResourceLoadingUtils.getReaderFromClasspath("extension-patient-clinicaltrial.xml"));
		this.definition = FhirExtensionDefinition.populateFromStructureDefinition(profile);
	}

	@Test
	public void testProcessElements() {
		assertNotNull(definition);
		assertEquals(4, definition.getExtensions().size()); //Root, NCT, Period, Reason
		
		//Validate that root was properly constructed
		ElementDefinitionDt clinicalTrial = definition.getExtensions().get("patient-clinicalTrial");
		assertNotNull(clinicalTrial);
		assertEquals(new Integer(0), clinicalTrial.getMin());
		assertEquals("*", clinicalTrial.getMax());
		assertNotNull(clinicalTrial.getFixed());
		assertEquals("http://hl7.org/fhir/StructureDefinition/patient-clinicalTrial", ((UriDt)clinicalTrial.getFixed()).getValueAsString());
		assertEquals("The patient's participation in clinical trials", clinicalTrial.getShort());
		assertEquals("The clinical trials this patient has or is participating in.", clinicalTrial.getDefinition());
		assertEquals(33, clinicalTrial.getType().size());
		assertEquals("boolean", clinicalTrial.getType().get(0).getCode());
		assertEquals("decimal", clinicalTrial.getType().get(2).getCode());
		
		//Validate that NCT was properly constructed
		ElementDefinitionDt nct = definition.getExtensions().get("NCT");
		assertNotNull(nct);
		assertEquals(new Integer(1), nct.getMin());
		assertEquals("1", nct.getMax());
		assertNotNull(nct.getFixed());
		assertEquals("NCT", ((UriDt)nct.getFixed()).getValueAsString());
		assertEquals("National Clinical Trial number", nct.getShort());
		assertEquals("The National Clinical Trial number. The format for the US ClinicalTrials.gov registry number is \"NCT\" followed by an 8-digit number, e.g.: NCT00000419.", nct.getDefinition());
		assertEquals(1, nct.getType().size());
		assertEquals("string", nct.getType().get(0).getCode());
		
		//Validate that period was properly constructed
		ElementDefinitionDt period = definition.getExtensions().get("period");
		assertNotNull(period);
		assertEquals(new Integer(0), period.getMin());
		assertEquals("1", period.getMax());
		assertNotNull(period.getFixed());
		assertEquals("period", ((UriDt)period.getFixed()).getValueAsString());
		assertEquals("The period of participation in the clinical trial", period.getShort());
		assertEquals("The start and end times of the participation of this patient in the clinical trial.", period.getDefinition());
		assertEquals(1, period.getType().size());
		assertEquals("Period", period.getType().get(0).getCode());
		
		//Validate that period was properly constructed
		ElementDefinitionDt reason = definition.getExtensions().get("reason");
		assertNotNull(period);
		assertEquals(new Integer(0), reason.getMin());
		assertEquals("1", reason.getMax());
		assertNotNull(reason.getFixed());
		assertEquals("reason", ((UriDt)reason.getFixed()).getValueAsString());
		assertEquals("The reason for participation in the clinical trial", reason.getShort());
		assertEquals("The indication or reason the patient is part of this trial.", reason.getDefinition());
		assertEquals(1, reason.getType().size());
		assertEquals("CodeableConcept", reason.getType().get(0).getCode());
	}

}
