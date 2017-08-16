package org.socraticgrid.fhir.generated;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.uhn.fhir.model.api.ExtensionDt;
import ca.uhn.fhir.model.dstu2.composite.AddressDt;
import ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt;
import ca.uhn.fhir.model.dstu2.composite.CodingDt;
import ca.uhn.fhir.model.dstu2.composite.HumanNameDt;
import ca.uhn.fhir.model.dstu2.composite.PeriodDt;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.dstu2.valueset.AddressTypeEnum;
import ca.uhn.fhir.model.dstu2.valueset.AdministrativeGenderEnum;
import ca.uhn.fhir.model.primitive.BooleanDt;
import ca.uhn.fhir.model.primitive.DateDt;
import ca.uhn.fhir.model.primitive.DateTimeDt;
import ca.uhn.fhir.model.primitive.StringDt;

public class QICorePatientAdapterTest {
	
//	private QICorePatientAdapter patient;
//
//	@Before
//	public void setUp() throws Exception {
//		patient = new QICorePatientAdapter();
//	}
//
//	@After
//	public void tearDown() throws Exception {
//	}
//
//	/**
//	 * Test whether (1) an extension can be added/retrieved using the logical interface
//	 * and whether this extension is properly represented in the underlying
//	 * HAPI FHIR instance.
//	 */
//	@Test
//	public void testExtendedAttributeReligion() {
//		try {
//
//			//We create a code for religion
//			CodeableConceptDt religion = new CodeableConceptDt();
//			CodingDt religionCode = new CodingDt("http://thedevine", "Spiritual");
//			religion.getCoding().add(religionCode);
//
//			//We set the religion code on the Patient resource
//			patient.setReligion(religion);
//
//			//We fetch it and make sure all was properly persisted
//			religion = patient.getReligion();
//			assertEquals(1, religion.getCoding().size());
//			assertNotNull(religion.getCodingFirstRep());
//			assertEquals("http://thedevine", religion.getCodingFirstRep().getSystem());
//			assertEquals("Spiritual", religion.getCodingFirstRep().getCode());
//
//			//We then verify that the underlying HAPI FHIR Patient Resource has the proper extension defined
//			List<ExtensionDt> extensions = patient.getAdaptee().getUndeclaredExtensionsByUrl("http://hl7.org/fhir/StructureDefinition/us-core-religion");
//			assertEquals(1, extensions.size());
//			assertEquals("http://hl7.org/fhir/StructureDefinition/us-core-religion", extensions.get(0).getUrl());
//			religion = (CodeableConceptDt)extensions.get(0).getValue();
//			assertEquals("Spiritual", religion.getCodingFirstRep().getCode());
//
//		} catch(Exception e) {
//			fail(); //Operations should not fail. If so, it is an error.
//		}
//	}
//
//	@Test
//	public void testCodeableConceptsAndEnums() {
//		patient.setGender(AdministrativeGenderEnum.MALE);
//	}
//
//	/**
//	 * Tests whether nested extensions such as Nationality (Patient.extension) is
//	 * properly handled during code generation.
//	 *
//	 */
//	@Test
//	public void testExtendedStructuresNationality() {
//		try {
//
//			//We create a list of nationalities (containing only a single element)
//			List<QICorePatientNationality> nationalities = new ArrayList<QICorePatientNationality>();
//			QICorePatientNationality nationality = new QICorePatientNationality();
//
//			//We set its Patient.nationality.code attribute (note: it is a Patient.extension.extension)
//			CodeableConceptDt russianNationality = new CodeableConceptDt();
//			CodingDt russianCode = new CodingDt("http://national.code.systems", "1234");
//			russianCode.setDisplay("Russian National");
//			russianNationality.getCoding().add(russianCode);
//			nationality.setCode(russianNationality);
//
//			//We set its Patient.nationality.period attribute (note: it is also a Patient.extension.extension)
//			PeriodDt period = new PeriodDt();
//			period.setStartWithSecondsPrecision(new Date());
//			period.setEndWithSecondsPrecision(new Date());
//			nationality.setPeriod(period);
//
//			//We add the nationalities to the underlying Patient resource and test that all is properly persisted
//			nationalities.add(nationality);
//			patient.setNationality(nationalities);
//			patient.getNationality().get(0).setCode(russianNationality);
//			assertNotNull(patient.getNationality());
//			assertEquals(1, patient.getNationality().size());
//			assertEquals("http://national.code.systems", patient.getNationality().get(0).getCode().getCodingFirstRep().getSystem());
//			assertEquals("1234", patient.getNationality().get(0).getCode().getCodingFirstRep().getCode());
//			assertEquals("Russian National", patient.getNationality().get(0).getCode().getCodingFirstRep().getDisplay());
//			assertEquals(1, patient.getAdaptee().getUndeclaredExtensionsByUrl("http://hl7.org/fhir/StructureDefinition/patient-nationality").size());
//
//			//We then check the underlying HAPI FHIR Patient object to make sure all is persisted as expected
//			ExtensionDt nationalityExtension = patient.getAdaptee().getUndeclaredExtensionsByUrl("http://hl7.org/fhir/StructureDefinition/patient-nationality").get(0);
//			assertNull(nationalityExtension.getValue());
//			assertEquals(russianNationality, nationalityExtension.getUndeclaredExtensionsByUrl("http://hl7.org/fhir/StructureDefinition/patient-nationality#code").get(0).getValue());
//			assertEquals(period, nationalityExtension.getUndeclaredExtensionsByUrl("http://hl7.org/fhir/StructureDefinition/patient-nationality#period").get(0).getValue());
//
//		} catch(Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//	}
//
//	@Test
//	public void testPassThroughSingleCardinalityBirthdate() {
//		Date date = new Date();
//		DateDt dateDt = new DateDt(date);
//
//		patient.setBirthDate(date);
//		assertEquals(date, patient.getBirthDate());
//		assertEquals(date, patient.getAdaptee().getBirthDate());
//
//		patient.setBirthDate(dateDt);
//		assertEquals(date, patient.getBirthDate());
//		assertEquals(date, patient.getAdaptee().getBirthDate());
//		assertEquals(dateDt, patient.getBirthDateElement());
//	}
//
//	@Test
//	public void testPassThroughMultipleCardinalitHumanName() {
//		HumanNameDt name1 = new HumanNameDt();
//		name1.addGiven().setValue("Maribel");
//		name1.addFamily().setValue("Gonzalez");
//		HumanNameDt name2 = new HumanNameDt();
//		name2.addGiven().setValue("Maribel");
//		name2.addFamily().setValue("Johnson-Gonzalez");
//		List<HumanNameDt> names = new ArrayList<HumanNameDt>();
//		names.add(name1);
//		names.add(name2);
//
//		patient.addName(name1);
//		patient.addName(name2);
//		assertEquals(2, patient.getName().size());
//		assertEquals(name1, patient.getName().get(0));
//		assertEquals(name2, patient.getName().get(1));
//
//		patient.setName(null);
//		assertEquals(new ArrayList<HumanNameDt>(), patient.getName());
//
//		patient.setName(names);
//		assertEquals(2, patient.getName().size());
//		assertEquals(name1, patient.getName().get(0));
//		assertEquals(name2, patient.getName().get(1));
//	}
//
//	@Test
//	public void testExtendedBackboneElementsAndType() {
//		QICorePatientAddress address = buildAddressInstance();
//		List<QICorePatientAddress> addresses = new ArrayList<QICorePatientAddress>();
//		addresses.add(address);
//		address.setPreferred(new BooleanDt(true));
//		patient.addWrappedAddress(address);
//		assertEquals(new BooleanDt(true), patient.getWrappedAddress().get(0).getPreferred());
//		assertEquals(address.getAdaptee(), patient.getAddressFirstRep());
//		patient.setWrappedAddress(addresses);
//		assertEquals(patient.getWrappedAddressFirstRep().getAdaptee(), patient.getAddressFirstRep());
//		assertEquals(patient.getWrappedAddress().get(0).getAdaptee(), addresses.get(0).getAdaptee());
//		assertEquals(1, patient.getWrappedAddress().size());
//
//		assertTrue(patient.addAddress() instanceof AddressDt);
//		assertNotNull(patient.addAddress());
//		patient.addAddress(address.getAdaptee());
//		assertEquals(patient.getAddressFirstRep(), address.getAdaptee());
//		assertEquals(4, patient.getAddress().size());
//		patient.setAddress(patient.getAddress());
//		assertEquals(4, patient.getAddress().size());
//	}
//
//	@Test
//	public void testMultiTypeLeafLevelNonExtensionAttributesDeceased() {
//		patient.setDeceasedBoolean(true);
//		assertTrue(patient.getDeceasedBoolean());
//		assertTrue(((BooleanDt)patient.getAdaptee().getDeceased()).getValue());
//
//		Date date = new Date();
//		patient.setDeceasedDateTime(date);
//		assertEquals(date, patient.getDeceasedDateTime());
//		assertEquals(new DateTimeDt(date), (DateTimeDt)patient.getAdaptee().getDeceased());
//	}
//
//	@Test
//	public void testProfiledResources() {
//		QICoreOrganizationAdapter organization = new QICoreOrganizationAdapter();
//		AddressDt address = new AddressDt();
//		address.addLine().setValue("123 Main Street");
//		organization.addAddress(address);
//		patient.setManagingOrganizationResource(organization);//TODO API should take interface and not adapter. Need to fix.
//		assertEquals(organization.getAddress().get(0).getLine().get(0), patient.getManagingOrganizationResource().getAddress().get(0).getLine().get(0));
//	}
//
//	@Test
//	public void testConstructorGeneration() {
//		//Simple test for no-arg constructor
//		QICorePatientAdapter adapter = new QICorePatientAdapter();
//		assertNotNull(adapter.getAdaptee());
//
//		//Simple test for arg-constructor
//		Patient hapiPatient = new Patient();
//		adapter.setAdaptee(hapiPatient);
//		hapiPatient.setId("Patient/1234");
//		assertEquals("Patient/1234", adapter.getId().getValueAsString());
//	}
//
//	@Test
//	public void testFluentSetters() {
//		try {
//			assertEquals(patient, patient.setActive(true));
//			assertEquals(patient, patient.setWrappedAddress(new ArrayList<QICorePatientAddress>()));
//			assertEquals(patient, patient.setCadavericDonor(new BooleanDt(true)));
//			assertEquals(patient, patient.setClinicalTrial(new ArrayList<QICorePatientClinicalTrial>()));
//			assertEquals(patient, patient.setContact(new ArrayList<Patient.Contact>()));
//			assertEquals(patient, patient.setRace(new CodeableConceptDt()));
//			assertEquals(patient, patient.setWrappedTelecom(new ArrayList<QICorePatientTelecom>()));
//		} catch(Exception e) {
//			fail();
//		}
//	}
//
//	@Test
//	public void testFluentAddMethod() {
//		//Test no-arg add method. Note that direct delegation to HAPI FHIR cannot occur because the returned object reference must support an extended type.
//		patient.addWrappedAddress().setPreferred(new BooleanDt(true));
//		assertTrue(patient.getWrappedAddress().get(0).getPreferred().getValue());//TODO Add FirstRep convenience methods and support primitives for extension methods
//
//		//Test single arg add method.
//		QICorePatientAddress address = new QICorePatientAddress();
//		address.setPreferred(new BooleanDt(true));
//		patient.addWrappedAddress(address);
//		assertTrue(patient.getWrappedAddress().get(0).getPreferred().getValue());
//	}
//
//	/***********************************************************
//	 * 	Helper Methods for Test
//	 * *********************************************************/
//
//	public QICorePatientAddress buildAddressInstance() {
//		QICorePatientAddress address = new QICorePatientAddress();
//		address.addLine().setValue("123 Main Street");
//		address.addLine("Apartment 123");
//		StringDt line = new StringDt("Second Floor");
//		address.addLine(line);
//		address.setCity("Los Angeles");
//		address.setState("California");
//		address.setPostalCode("90049");
//		address.setType(AddressTypeEnum.POSTAL);
//		return address;
//	}
//
//	public CodeableConceptDt createCodeableConcept(String codeSystem, String code) {
//		CodeableConceptDt codeableConcept = new CodeableConceptDt();
//		CodingDt codeDt = new CodingDt(codeSystem, code);
//		codeableConcept.getCoding().add(codeDt);
//		return codeableConcept;
//	}
}
