package org.cdscollaborative.tools.fhir.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class PathUtilsTest {
	
	@Test
	public void testGenerateExtensionPath() {
		assertEquals("Patient.birthDate.verification", PathUtils.generateExtensionPath("Patient.birthDate.extension", "birthDate.verification"));
		assertEquals("Patient.nationality.code", PathUtils.generateExtensionPath("Patient.extension.extension", "nationality.code"));
		assertEquals("Patient.race", PathUtils.generateExtensionPath("Patient.extension", "race"));
		assertEquals("Specimen.collection.quantity.isDryWeight", PathUtils.generateExtensionPath("Specimen.collection.quantity.extension", "Specimen.collection.quantity.isDryWeight"));
		assertEquals("MedicationDispense.dosageInstruction.doseType", PathUtils.generateExtensionPath("MedicationDispense.dosageInstruction.extension", "MedicationDispense.dosageInstruction.doseType"));
		assertEquals("MedicationDispense.dosageInstruction.extension", PathUtils.generateExtensionPath("MedicationDispense.dosageInstruction.extension", null));
	}
	
	@Test
	public void testGetEnumNameFromValueSetBindingUri() {
		assertEquals("AdministrativeGender", PathUtils.getEnumNameFromValueSetBindingUri("http://hl7.org/fhir/ValueSet/administrative-gender"));
	}
	
	@Test
	public void testGetLastPathComponent() {
		assertEquals("zee", PathUtils.getLastPathComponent("ay.uway.zee"));
	}
	
	@Test
	public void testGetExtensionLevelInPath() {
		assertEquals(0,PathUtils.getExtensionLevelInPath("Patient.race"));
		assertEquals(1,PathUtils.getExtensionLevelInPath("Patient.extension"));
		assertEquals(2,PathUtils.getExtensionLevelInPath("Patient.extension.extension"));
		assertEquals(3,PathUtils.getExtensionLevelInPath("Patient.extension.extension.extension"));
		assertEquals(4,PathUtils.getExtensionLevelInPath("Patient.extension.extension.extension.extension"));
	}
	
	@Test
	public void testGetNonExtensionRootPath() {
		assertEquals("Patient",PathUtils.getNonExtensionRootPath("Patient.extension.extension"));
		assertEquals("Patient.birthdate",PathUtils.getNonExtensionRootPath("Patient.birthdate.extension"));
	}
	
	@Test
	public void testGetPathPrefix() {
		assertEquals("Patient.birthdate", PathUtils.getPathPrefix("Patient.birthdate.verification"));
	}
	
	@Test
	public void testGetExtensionRootPath() {
		assertEquals("http://hl7.org/fhir/StructureDefinition/patient-clinicalTrial", PathUtils.getExtensionRootPath("http://hl7.org/fhir/StructureDefinition/patient-clinicalTrial#NCT"));
		assertEquals("http://hl7.org/fhir/StructureDefinition/patient-clinicalTrial", PathUtils.getExtensionRootPath("http://hl7.org/fhir/StructureDefinition/patient-clinicalTrial"));
		assertEquals("", PathUtils.getExtensionRootPath(""));
	}
	
	@Test
	public void testGetExtensionAnchor() {
		assertEquals("NCT", PathUtils.getExtensionAnchor("http://hl7.org/fhir/StructureDefinition/patient-clinicalTrial#NCT"));
		assertNull(PathUtils.getExtensionAnchor("http://hl7.org/fhir/StructureDefinition/patient-clinicalTrial"));
		assertNull(PathUtils.getExtensionAnchor(""));
	}
	
	@Test
	public void testGetExtensionName() {
		assertEquals("NCT", PathUtils.getExtensionName("http://hl7.org/fhir/StructureDefinition/patient-clinicalTrial#NCT"));
		assertEquals("patient-clinicalTrial", PathUtils.getExtensionName("http://hl7.org/fhir/StructureDefinition/patient-clinicalTrial"));
		assertNull(PathUtils.getExtensionName(""));
	}
}
