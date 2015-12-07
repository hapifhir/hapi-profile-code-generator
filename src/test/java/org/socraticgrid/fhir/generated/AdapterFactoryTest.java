package org.socraticgrid.fhir.generated;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.api.Bundle;
import ca.uhn.fhir.model.dstu2.resource.Condition;
import ca.uhn.fhir.model.dstu2.resource.Observation;

public class AdapterFactoryTest {
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAdaptBundle() {
		Bundle bundle = new Bundle();
		bundle.addResource(new Condition(), FhirContext.forDstu2(), null);
		bundle.addResource(new Condition(), FhirContext.forDstu2(), null);
		Map<String, List<?>> map = AdapterFactory.adapt(bundle);
		assertEquals(2, map.get(Condition.class.getCanonicalName()).size());
		assertNull(map.get(Observation.class.getCanonicalName()));
	}

	@Test
	public void testAdaptIResource() {
		Object o = AdapterFactory.adapt(new Condition());
		assertEquals("org.socraticgrid.fhir.generated.ConditionAdapter", o.getClass().getCanonicalName());
		assertEquals("ca.uhn.fhir.model.dstu2.resource.Condition", ((ConditionAdapter)o).getAdaptee().getClass().getCanonicalName());
	}

}
