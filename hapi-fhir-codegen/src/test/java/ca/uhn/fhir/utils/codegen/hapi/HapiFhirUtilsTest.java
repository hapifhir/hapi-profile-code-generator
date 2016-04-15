package ca.uhn.fhir.utils.codegen.hapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.junit.Before;
import org.junit.Test;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.resource.Patient;

public class HapiFhirUtilsTest {
	
	private FhirContext ctx;
	
	@Before
	public void initialize() {
		ctx = FhirContext.forDstu2();
	}

	@Test
	public void testGetBoundCode() {
		Class<? extends Enum<?>> genderEnumClass = HapiFhirUtils.getBoundCode(ctx, Patient.class, "gender");
		assertNotNull(genderEnumClass);
		assertEquals("ca.uhn.fhir.model.dstu2.valueset.AdministrativeGenderEnum", genderEnumClass.getName());
		
		genderEnumClass = HapiFhirUtils.getBoundCode(ctx, "Patient", "gender");
		assertNotNull(genderEnumClass);
		assertEquals("ca.uhn.fhir.model.dstu2.valueset.AdministrativeGenderEnum", genderEnumClass.getName());
	}

	@Test
	public void testGetBoundCodeableConcept() {
		Class<? extends Enum<?>> maritalStatusEnum = HapiFhirUtils.getBoundCodeableConcept(ctx, Patient.class, "maritalStatus").getEnumerationTypeClass();
		assertNotNull(maritalStatusEnum);
		assertEquals("ca.uhn.fhir.model.dstu2.valueset.MaritalStatusCodesEnum", maritalStatusEnum.getName());
		
		maritalStatusEnum = HapiFhirUtils.getBoundCodeableConcept(ctx, "Patient", "maritalStatus").getEnumerationTypeClass();
		assertNotNull(maritalStatusEnum);
		assertEquals("ca.uhn.fhir.model.dstu2.valueset.MaritalStatusCodesEnum", maritalStatusEnum.getName());
	}

	@Test
	public void testGetPrimitiveTypeClassName() {
		String primitiveType = HapiFhirUtils.getPrimitiveTypeClassName(ctx, "boolean");
		assertEquals("ca.uhn.fhir.model.primitive.BooleanDt", primitiveType);
	}

	@Test
	public void testGetResourceClass() {
		Class<? extends IBaseResource> patientClass = HapiFhirUtils.getResourceClass(ctx, "Patient");
		assertNotNull(patientClass);
		assertEquals(Patient.class, patientClass);
	}

	@Test
	public void testGetStructureTypeClass() {
		Class<?> patientAddress = HapiFhirUtils.getStructureTypeClass(ctx, "Patient", "address");
		assertNotNull(patientAddress);
		assertEquals("ca.uhn.fhir.model.dstu2.composite.AddressDt", patientAddress.getName());
	}

}
