package ca.uhn.fhir.utils.fhir.model.datatype.dstu2;

import static org.junit.Assert.*;

import org.junit.Test;

public class FhirDatatypeEnumTest {

	@Test
	public void testContainment() {
		assertTrue(FhirDatatypeEnum.contains("SimpleQuantity"));
		assertFalse(FhirDatatypeEnum.contains("Megatype"));
		assertTrue(FhirDatatypeEnum.QUANTITY.hasSpecialization("SimpleQuantity"));
	}

}
