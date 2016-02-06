package ca.uhn.fhir.utils.codegen.hapi;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.uhn.fhir.utils.codegen.hapi.FhirResourceManager;
import ca.uhn.fhir.utils.common.io.ResourceLoadingUtils;

public class FhirResourceLoaderTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLoadResources() {
		FhirResourceManager loader = new FhirResourceManager();
		try {
			loader.loadResourceProfiles(ResourceLoadingUtils.getReaderFromFilePath("/Users/cnanjo/repository/fhir/trunk/build/publish/profiles-resources.xml"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
