package org.cdscollaborative.tools.fhir.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class PathUtilsTest {
	
	@Test
	public void testGenerateExtensionPath() {
		assertEquals("Patient.birthDate.verification", PathUtils.generateExtensionPath("Patient.birthDate.extension", "birthDate.verification"));
		assertEquals("Patient.nationality.code", PathUtils.generateExtensionPath("Patient.extension.extension", "nationality.code"));
		assertEquals("Patient.race", PathUtils.generateExtensionPath("Patient.extension", "race"));
	}
	
	@Test
	public void testGetEnumNameFromValueSetBindingUri() {
		assertEquals("AdministrativeGender", PathUtils.getEnumNameFromValueSetBindingUri("http://hl7.org/fhir/ValueSet/administrative-gender"));
	}
	
	@Test
	public void test() {
		assertEquals("zee", PathUtils.getLastPathComponent("ay.uway.zee"));
	}
}
