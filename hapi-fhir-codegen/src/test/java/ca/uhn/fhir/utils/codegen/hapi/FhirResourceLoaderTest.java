package ca.uhn.fhir.utils.codegen.hapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.uhn.fhir.utils.codegen.hapi.dstu2.FhirResourceManagerDstu2;
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
		FhirResourceManagerDstu2 loader = new FhirResourceManagerDstu2();
		try {
			loader.loadResourceProfiles(ResourceLoadingUtils.getReaderFromClasspath("profiles-resources.xml"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testLoadProfilesFromConfiguration() {
		CodeGeneratorConfigurator configurator = CodeGeneratorConfigurator.buildConfigurator(ResourceLoadingUtils.getPathFromResourceClassPath("/config/generation-plan.xml"));
		FhirResourceManagerDstu2 loader = CodeGeneratorConfigurator.buildFhirResourceManager(configurator, true);
		try {
			String hapiAddressType = loader.getHapiTypeForFhirType(loader.getProfile("Patient"), "address");
			assertEquals(ca.uhn.fhir.model.dstu2.composite.AddressDt.class.getName(), hapiAddressType);
		} catch(Exception e) {
			e.printStackTrace();
			fail("Error retrieving HAPI address type");
		}
		
		try {
			String hapiMedProductType = loader.getHapiTypeForFhirType(loader.getProfile("Medication"), "product");
			assertEquals(ca.uhn.fhir.model.dstu2.resource.Medication.Product.class.getName(), hapiMedProductType);
		} catch(Exception e) {
			e.printStackTrace();
			fail("Error retrieving HAPI Medication.Product type");
		}
		
		try {
			String hapiDosageInstructionType = loader.getHapiTypeForFhirType(loader.getProfile("MedicationOrder"), "dosageInstruction");
			assertEquals(ca.uhn.fhir.model.dstu2.resource.MedicationOrder.DosageInstruction.class.getName(), hapiDosageInstructionType);
		} catch(Exception e) {
			e.printStackTrace();
			fail("Error retrieving HAPI MedicationOrder.DosageInstruction type");
		}
		
		try {
			String medicationIngredient = loader.getHapiTypeForFhirType(loader.getProfile("Medication"), "product.ingredient");
			assertEquals(ca.uhn.fhir.model.dstu2.resource.Medication.ProductIngredient.class.getName(), medicationIngredient);
		} catch(Exception e) {
			e.printStackTrace();
			fail("Error retrieving HAPI Medication.ingredient type");
		}
		
	}

}
