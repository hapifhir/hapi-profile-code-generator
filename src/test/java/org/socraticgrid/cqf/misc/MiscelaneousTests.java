package org.socraticgrid.cqf.misc;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.uhn.fhir.context.FhirContext;

public class MiscelaneousTests {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		FhirContext ctx = FhirContext.forDstu2();
//		Object o = ctx.getResourceDefinition("MedicationPrescription.DosageInstruction");
//		assertNotNull(o);
	}

}
