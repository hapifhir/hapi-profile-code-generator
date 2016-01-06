package org.socraticgrid.cqf.meta.fhir;

import static org.junit.Assert.*;

import org.cdscollaborative.tools.fhir.model.FhirExtensionDefinition;
import org.cdscollaborative.tools.fhir.utils.FhirExtensionManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FhirExtensionManagerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

//	@Test
//	public void testLoadProfiles() {
//		//Check one item in overall collection as collection size may change over time but item is likely to remain
//		FhirExtensionManager extensionManager = new FhirExtensionManager();
//		extensionManager.addProfileRepositoryLocation("/Users/cnanjo/repository/fhir/trunk/build/publish");
//		extensionManager.loadExtensions();
//		assertTrue(extensionManager.registryContains("http://hl7.org/fhir/StructureDefinition/us-core-race"));
//		FhirExtensionDefinition usCoreRaceExtension = extensionManager.getFromRegistry("http://hl7.org/fhir/StructureDefinition/us-core-race");
//		assertNotNull(usCoreRaceExtension);
//		assertEquals("CodeableConcept", usCoreRaceExtension.getTypes().get(0).getCode());
//		assertEquals("1", usCoreRaceExtension.getHighCardinality());
//		assertEquals("A category of humans sharing history, origin or nationality", usCoreRaceExtension.getShortDescription());
//	}

}
