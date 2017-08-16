package ca.uhn.fhir.utils.codegen.hapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import ca.uhn.fhir.utils.codegen.hapi.dstu3.FhirResourceManagerDstu3;
import ca.uhn.fhir.utils.codegen.hapi.dstu3.FhirToHapiTypeConverter;
import org.hl7.fhir.dstu3.model.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.uhn.fhir.model.primitive.BooleanDt;
import ca.uhn.fhir.model.primitive.CodeDt;
import ca.uhn.fhir.model.primitive.DecimalDt;
import ca.uhn.fhir.utils.common.io.ResourceLoadingUtils;
import ca.uhn.fhir.utils.fhir.model.datatype.dstu2.FhirDatatypeEnum;

public class FhirToHapiTypeConverterTest {

	private ElementDefinition element;
//	private static FhirResourceManagerDstu2 manager;
	private static FhirResourceManagerDstu3 manager;
	private static CodeGeneratorConfigurator configurator;
	
	@BeforeClass
	public static void setupSuite() {
		configurator = CodeGeneratorConfigurator
				.buildConfigurator(ResourceLoadingUtils.getPathFromResourceClassPath("/config/generation-plan-dstu3.xml"));
		manager = CodeGeneratorConfigurator.buildFhirResourceManagerDstu3(configurator, true);
	}

	@Before
	public void setupTest() {
		element = new ElementDefinition();
	}

//	@Test
//	public void testReferenceTypeConversion() {
//		ElementDefinition.TypeRefComponent type = new ElementDefinition.TypeRefComponent();
//		type.setCode(FhirDatatypeEnum.REFERENCE.toString());
//		type.setTargetProfile("http://hl7.org/fhir/StructureDefinition/MedicationAdministration");
//		element.addType(type);
//		FhirToHapiTypeConverter converter = new FhirToHapiTypeConverter(manager, element, configurator.getGeneratedCodePackage());
//		assertTrue(converter.getHapiType().isReference());
//		assertFalse(converter.isExtension());
//		assertEquals(MedicationAdministration.class.getName(), converter.getHapiType().getDatatype());
//	}

//	@Test
//	public void testExtensionTypeSingleConversion() {
//		ElementDefinition.TypeRefComponent type = new ElementDefinition.TypeRefComponent();
//		type.setCode(FhirDatatypeEnum.EXTENSION.toString());
//		type.setTargetProfile("http://hl7.org/fhir/StructureDefinition/us-core-race");
//		element.addType(type);
//		FhirToHapiTypeConverter converter = new FhirToHapiTypeConverter(manager, element, configurator.getGeneratedCodePackage());
//		assertEquals(1, converter.getHapiTypes().size());
//		assertTrue(converter.isExtension());
//		assertFalse(converter.isMultiType());
//		assertFalse(converter.getHapiType().isReference());
//		assertEquals(CodeableConcept.class.getName(), converter.getHapiType().getDatatype());
//
//	}
	
//	@Test
//	public void testExtensionTypeMultiConversion() {
//		ElementDefinition.TypeRefComponent type = new ElementDefinition.TypeRefComponent();
//		type.setCode(FhirDatatypeEnum.EXTENSION.toString());
//		type.setTargetProfile("http://hl7.org/fhir/StructureDefinition/qicore-procedurerequest-appropriatenessScore");
//		element.addType(type);
//		FhirToHapiTypeConverter converter = new FhirToHapiTypeConverter(manager, element, configurator.getGeneratedCodePackage());
//		assertEquals(2, converter.getHapiTypes().size());
//		assertTrue(converter.isExtension());
//		assertTrue(converter.isMultiType());
//		assertFalse(converter.getHapiType().isReference());
//		assertEquals(CodeDt.class.getName(), converter.getHapiType().getDatatype());
//		assertEquals(DecimalDt.class.getName(), converter.getHapiTypes().get(1).getDatatype());
//	}

//	@Test
//	public void testBooleanTypeConversion() {
//		ElementDefinition.TypeRefComponent type = new ElementDefinition.TypeRefComponent();
//		type.setCode(FhirDatatypeEnum.BOOLEAN.toString());
//		element.addType(type);
//		FhirToHapiTypeConverter converter = new FhirToHapiTypeConverter(manager, element, configurator.getGeneratedCodePackage());
//		assertEquals(BooleanDt.class.getName(), converter.getHapiType().getDatatype());
//	}

//	@Test
//	public void testAddressTypeConversion() {
//		ElementDefinition.TypeRefComponent type = new ElementDefinition.TypeRefComponent();
//		type.setCode(FhirDatatypeEnum.ADDRESS.toString());
//		element.addType(type);
//		FhirToHapiTypeConverter converter = new FhirToHapiTypeConverter(manager, element, configurator.getGeneratedCodePackage());
//		assertEquals(Address.class.getName(), converter.getHapiType().getDatatype());
//	}

//	@Test
//	public void testPatientTypeConversion() {
//		ElementDefinition.TypeRefComponent type = new ElementDefinition.TypeRefComponent();
//		type.setCode("Patient");
//		element.addType(type);
//		FhirToHapiTypeConverter converter = new FhirToHapiTypeConverter(manager, element, configurator.getGeneratedCodePackage());
//		assertEquals(Patient.class.getName(), converter.getHapiType().getDatatype());
//	}
	
//	@Test
//	public void testIsMultiType() {
//		ElementDefinition.TypeRefComponent type = new ElementDefinition.TypeRefComponent();
//		type.setCode("Patient");
//		element.addType(type);
//
//		type = new ElementDefinition.TypeRefComponent();
//		type.setCode("boolean");
//		element.addType(type);
//
//		FhirToHapiTypeConverter converter = new FhirToHapiTypeConverter(manager, configurator.getGeneratedCodePackage());
//		assertEquals(2, converter.getHapiTypes().size());
//		assertEquals(Patient.class.getName(), converter.getHapiType().getDatatype());
//		assertEquals(BooleanDt.class.getName(), converter.getHapiTypes().get(1).getDatatype());
//	}

}
