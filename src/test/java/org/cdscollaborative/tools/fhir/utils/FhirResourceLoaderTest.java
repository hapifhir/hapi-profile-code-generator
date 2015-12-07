package org.cdscollaborative.tools.fhir.utils;

import static org.junit.Assert.fail;
import guru.mwangaza.common.util.io.ResourceLoadingUtils;

import java.io.FileNotFoundException;

import org.cdscollaborative.tools.fhir.utils.FhirResourceManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
