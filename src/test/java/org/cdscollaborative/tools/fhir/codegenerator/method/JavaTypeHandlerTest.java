package org.cdscollaborative.tools.fhir.codegenerator.method;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.cdscollaborative.model.meta.Method;
import org.cdscollaborative.model.meta.MethodParameter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;
import ca.uhn.fhir.model.primitive.BooleanDt;

public class JavaTypeHandlerTest extends BaseHandlerTest {
	
	private JavaTypeHandler handler;
	ElementDefinitionDt booleanElement;
	ElementDefinitionDt booleanMultipleElement;
	ElementDefinitionDt multiTypeElement;
	ElementDefinitionDt extensionElement;
	ElementDefinitionDt emptyTypeElement;
	ElementDefinitionDt codeableConceptElement;
	ElementDefinitionDt referenceTypeElement;
	StructureDefinition profile;
	
	public JavaTypeHandlerTest() {
		super();
	}

	@Before
	public void setUp() throws Exception {
//		profile = buildMockProfile();
//		booleanElement = buildMockJavaBooleanTypeElement();
//		addElementToProfile(booleanElement, profile);
//		multiTypeElement = buildMockMultiTypeElement();
//		addElementToProfile(multiTypeElement, profile);
//		extensionElement = buildMockMultiTypeElement();
//		addElementToProfile(extensionElement, profile);
//		emptyTypeElement = buildMockEmptyTypeElement();
//		addElementToProfile(emptyTypeElement, profile);
//		codeableConceptElement = buildMockCodeableConceptElement();
//		addElementToProfile(codeableConceptElement, profile);
//		referenceTypeElement = buildMockReferenceTypeElement();
//		addElementToProfile(referenceTypeElement, profile);
//		booleanMultipleElement = buildMockJavaBooleanTypeMultipleCardinalityElement();
//		addElementToProfile(booleanMultipleElement, profile);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAppliesTo() {
		assertTrue(JavaTypeHandler.appliesTo(profile, booleanElement));
		assertTrue(JavaTypeHandler.appliesTo(profile, booleanMultipleElement));
		assertFalse(JavaTypeHandler.appliesTo(profile, multiTypeElement));
		assertFalse(JavaTypeHandler.appliesTo(profile, extensionElement));
		assertFalse(JavaTypeHandler.appliesTo(profile, emptyTypeElement));
		assertFalse(JavaTypeHandler.appliesTo(profile, codeableConceptElement));
		assertFalse(JavaTypeHandler.appliesTo(profile, referenceTypeElement));
	}
	
	@Test
	public void testBuildCorrespondingMethodsSingle() {
		handler = new JavaTypeHandler(getFhirResourceManager(), getTemplate(), profile, booleanElement);
		handler.initialize();
		List<Method> methods = handler.buildCorrespondingMethods();
		assertNotNull(methods);
		assertEquals(4, methods.size());
		for(Method method : methods) {
			if(method.getName().equals("getCoreAttribute1Element") ||
					method.getName().equals("getCoreAttribute1") ||
					method.getName().equals("setCoreAttribute1")) {
				assertTrue(true);
			} else {
				fail();
			}
			if(method.getName().equals("setCoreAttribute1")) {
				assertNull(method.getReturnType());
				assertEquals(1, method.getParameters().size());
				MethodParameter parameter = method.getParameters().get(0);
				boolean isValidParam = parameter.getValue().equals(Boolean.class.getCanonicalName()) ||
						parameter.getValue().equals(BooleanDt.class.getCanonicalName());
				assertTrue(isValidParam);
				if(method.getParameters().get(0).getValue().equals("java.lang.Boolean")) {
					assertEquals("adaptedClass.setCoreAttribute1(new ca.uhn.fhir.model.primitive.BooleanDt(param));", method.getBody());
				} else {
					assertEquals("adaptedClass.setCoreAttribute1(param);", method.getBody());
				}
			}
			if(method.getName().equals("getCoreAttribute1Element")) {
				assertEquals(BooleanDt.class.getCanonicalName(), method.getReturnType());
				assertEquals(0, method.getParameters().size());
				assertEquals("return adaptedClass.getCoreAttribute1Element();", method.getBody());
			}
			if(method.getName().equals("getCoreAttribute1")) {
				assertEquals(Boolean.class.getCanonicalName(), method.getReturnType());
				assertEquals(0, method.getParameters().size());
				assertEquals("return adaptedClass.getCoreAttribute1();", method.getBody());
			}
		}
	}
	
	@Test
	public void testBuildCorrespondingMethodsMultipleCardinality() {
		handler = new JavaTypeHandler(getFhirResourceManager(), getTemplate(), profile, booleanMultipleElement);
		handler.initialize();
		List<Method> methods = handler.buildCorrespondingMethods();
		assertNotNull(methods);
		assertEquals(3, methods.size());
		for(Method method : methods) {
			if(	method.getName().equals("addCoreAttribute1") || 
					method.getName().equals("getCoreAttribute1") ||
					method.getName().equals("setCoreAttribute1")) {
				assertTrue(true);
			} else {
				fail();
			}
			if(method.getName().equals("getCoreAttribute1")) {
				assertEquals("List<ca.uhn.fhir.model.primitive.BooleanDt>", method.getReturnType());
				assertEquals(0, method.getParameters().size());
				assertEquals("return adaptedClass.getCoreAttribute1();", method.getBody());
			}
			if(method.getName().equals("setCoreAttribute1")) {
				assertNull(method.getReturnType());
				assertEquals(1, method.getParameters().size());
				MethodParameter parameter = method.getParameters().get(0);
				assertEquals("List<" + BooleanDt.class.getCanonicalName() + ">", parameter.getValue());
				if(method.getParameters().get(0).getValue().equals("java.lang.Boolean")) {
					assertEquals("adaptedClass.setCoreAttribute1(new ca.uhn.fhir.model.primitive.BooleanDt(param));", method.getBody());
				} else {
					assertEquals("adaptedClass.setCoreAttribute1(param);", method.getBody());
				}
			}
			if(method.getName().equals("addCoreAttribute1")) {
				assertNull(method.getReturnType());
				assertEquals(ca.uhn.fhir.model.primitive.BooleanDt.class.getCanonicalName(), method.getParameters().get(0).getValue());
				assertEquals("adaptedClass.getCoreAttribute1().add(param);", method.getBody());
			}
		}
	}

}
