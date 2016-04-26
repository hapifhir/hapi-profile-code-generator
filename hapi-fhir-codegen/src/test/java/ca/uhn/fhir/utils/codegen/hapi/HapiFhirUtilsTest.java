package ca.uhn.fhir.utils.codegen.hapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.hl7.fhir.instance.model.api.IBaseResource;
import org.junit.Before;
import org.junit.Test;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.api.ExtensionDt;
import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt.Type;
import ca.uhn.fhir.model.dstu2.resource.Patient;

public class HapiFhirUtilsTest {
	
	private FhirContext ctx;
	
	@Before
	public void initialize() {
		ctx = FhirContext.forDstu2();
	}

	@Test
	public void testGetBoundCode() {
		Class<? extends Enum<?>> genderEnumClass = HapiFhirUtils.resolveBoundedAttributeTypes(ctx, Patient.class, "gender").getEnumerationTypeClass();
		assertNotNull(genderEnumClass);
		assertEquals("ca.uhn.fhir.model.dstu2.valueset.AdministrativeGenderEnum", genderEnumClass.getName());
		
		genderEnumClass = HapiFhirUtils.resolveBoundedAttributeTypes(ctx, "Patient", "gender").getEnumerationTypeClass();
		assertNotNull(genderEnumClass);
		assertEquals("ca.uhn.fhir.model.dstu2.valueset.AdministrativeGenderEnum", genderEnumClass.getName());
	}

	@Test
	public void testGetBoundCodeableConcept() {
		Class<? extends Enum<?>> maritalStatusEnum = HapiFhirUtils.resolveBoundedAttributeTypes(ctx, Patient.class, "maritalStatus").getEnumerationTypeClass();
		assertNotNull(maritalStatusEnum);
		assertEquals("ca.uhn.fhir.model.dstu2.valueset.MaritalStatusCodesEnum", maritalStatusEnum.getName());
		
		maritalStatusEnum = HapiFhirUtils.resolveBoundedAttributeTypes(ctx, "Patient", "maritalStatus").getEnumerationTypeClass();
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
	
	@Test 
	public void testGetDatatypeClass() {
		Type type = new Type();
		type.setCode("Quantity");
		type.addProfile("http://hl7.org/fhir/StructureDefinition/SimpleQuantity");
		assertEquals(ca.uhn.fhir.model.dstu2.composite.SimpleQuantityDt.class, HapiFhirUtils.getDataTypeClass(ctx, type));
		assertNotEquals(ca.uhn.fhir.model.dstu2.composite.QuantityDt.class, HapiFhirUtils.getDataTypeClass(ctx, type));
		
		type = new Type();
		type.setCode("Quantity");
		type.addProfile("http://hl7.org/fhir/StructureDefinition/SomeUserProfileOnQuantity");
		assertNull(HapiFhirUtils.getDataTypeClass(ctx, type));
		
		type = new Type();
		type.setCode("Quantity");
		assertEquals(ca.uhn.fhir.model.dstu2.composite.QuantityDt.class, HapiFhirUtils.getDataTypeClass(ctx, type));
		
		type = new Type();
		type.setCode("reference");
		assertEquals(ca.uhn.fhir.model.dstu2.composite.ResourceReferenceDt.class, ctx.getElementDefinition("reference").getImplementingClass());
		
		type = new Type();
		type.setCode("Extension");
		assertEquals(ca.uhn.fhir.model.api.ExtensionDt.class, ctx.getElementDefinition("Extension").getImplementingClass());
		
		//Class condition = HapiFhirUtils.getResourceClass(ctx, "Condition");
		Class contained = HapiFhirUtils.getStructureTypeClass(ctx, "Condition", "contained");
		
		Class language = HapiFhirUtils.getStructureTypeClass(ctx, "Condition", "language");
		
		Class meta = HapiFhirUtils.getStructureTypeClass(ctx, "Condition", "meta");
		
	}

}
