package ca.uhn.fhir.utils.codegen.hapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.uhn.fhir.model.dstu2.composite.AddressDt;
import ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt;
import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt.Type;
import ca.uhn.fhir.model.dstu2.resource.MedicationAdministration;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.primitive.BooleanDt;
import ca.uhn.fhir.utils.common.io.ResourceLoadingUtils;
import ca.uhn.fhir.utils.fhir.model.datatype.dstu2.FhirDatatypeEnum;

public class FhirToHapiTypeConverterTest {

	private ElementDefinitionDt element;
	private static FhirResourceManager manager;
	
	@BeforeClass
	public static void setupSuite() {
		CodeGeneratorConfigurator configurator = CodeGeneratorConfigurator
				.buildConfigurator(ResourceLoadingUtils.getPathFromResourceClassPath("/config/generation-plan.xml"));
		manager = CodeGeneratorConfigurator.buildFhirResourceManager(configurator, true);
	}

	@Before
	public void setupTest() {
		element = new ElementDefinitionDt();
	}

	@Test
	public void testReferenceTypeConversion() {
		Type type = new Type();
		type.setCode(FhirDatatypeEnum.REFERENCE.toString());
		type.addProfile("http://hl7.org/fhir/StructureDefinition/MedicationAdministration");
		element.addType(type);
		FhirToHapiTypeConverter converter = new FhirToHapiTypeConverter(manager, element);
		assertTrue(converter.getHapiType().isReference());
		assertFalse(converter.isExtension());
		assertEquals(MedicationAdministration.class.getName(), converter.getHapiType().getDatatype());
	}

	@Test
	public void testExtensionTypeSingleConversion() {
		Type type = new Type();
		type.setCode(FhirDatatypeEnum.EXTENSION.toString());
		type.addProfile("http://hl7.org/fhir/StructureDefinition/us-core-race");
		element.addType(type);
		FhirToHapiTypeConverter converter = new FhirToHapiTypeConverter(manager, element);
		assertEquals(1, converter.getHapiTypes().size());
		assertTrue(converter.isExtension());
		assertFalse(converter.getHapiType().isReference());
		assertEquals(CodeableConceptDt.class.getName(), converter.getHapiType().getDatatype());
		
	}
	
	@Test
	public void testExtensionTypeMultiConversion() {
//		Type type = new Type();
//		type.setCode(FhirDatatypeEnum.EXTENSION.toString());
//		element.addType(type);
//		FhirToHapiTypeConverter converter = new FhirToHapiTypeConverter(manager, element);
//		assertTrue(converter.getHapiType().isExtension());
//		assertFalse(converter.getHapiType().isReference());
	}

	@Test
	public void testBooleanTypeConversion() {
		Type type = new Type();
		type.setCode(FhirDatatypeEnum.BOOLEAN.toString());
		element.addType(type);
		FhirToHapiTypeConverter converter = new FhirToHapiTypeConverter(manager, element);
		assertEquals(BooleanDt.class.getName(), converter.getHapiType().getDatatype());
	}

	@Test
	public void testAddressTypeConversion() {
		Type type = new Type();
		type.setCode(FhirDatatypeEnum.ADDRESS.toString());
		element.addType(type);
		FhirToHapiTypeConverter converter = new FhirToHapiTypeConverter(manager, element);
		assertEquals(AddressDt.class.getName(), converter.getHapiType().getDatatype());
	}

	@Test
	public void testPatientTypeConversion() {
		Type type = new Type();
		type.setCode("Patient");
		element.addType(type);
		FhirToHapiTypeConverter converter = new FhirToHapiTypeConverter(manager, element);
		assertEquals(Patient.class.getName(), converter.getHapiType().getDatatype());
	}
	
	@Test
	public void testIsMultiType() {
		Type type = new Type();
		type.setCode("Patient");
		element.addType(type);
		
		type = new Type();
		type.setCode("boolean");
		element.addType(type);
		
		FhirToHapiTypeConverter converter = new FhirToHapiTypeConverter(manager, element);
		assertEquals(2, converter.getHapiTypes().size());
		assertEquals(Patient.class.getName(), converter.getHapiType().getDatatype());
		assertEquals(BooleanDt.class.getName(), converter.getHapiTypes().get(1).getDatatype());
	}

}
