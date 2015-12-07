package org.cdscollaborative.tools.fhir.codegenerator.method;

import static org.junit.Assert.*;

import org.cdscollaborative.tools.fhir.codegenerator.CodeTemplateUtils;
import org.cdscollaborative.tools.fhir.utils.FhirResourceManager;
import org.junit.Test;

import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt.Type;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;
import ca.uhn.fhir.model.dstu2.valueset.DataTypeEnum;

public class BaseHandlerTest {
	
	private CodeTemplateUtils template;
	private FhirResourceManager fhirResourceManager;
	
	public BaseHandlerTest() {
		template = new CodeTemplateUtils().initialize();
		fhirResourceManager = new FhirResourceManager();
	}
	
	public CodeTemplateUtils getTemplate() {
		return template;
	}

	public void setTemplate(CodeTemplateUtils template) {
		this.template = template;
	}
	
	public FhirResourceManager getFhirResourceManager() {
		return fhirResourceManager;
	}

	public void setFhirResourceManager(FhirResourceManager fhirResourceManager) {
		this.fhirResourceManager = fhirResourceManager;
	}

	/**
	 * Creates a HAPI FHIR type that has a Boolean java type equivalent
	 * @return
	 */
	public Type buildBooleanJavaType() {
		Type type = new Type();
		type.setCode(DataTypeEnum.BOOLEAN);
		return type;
	}
	
	public Type buildCodeableConceptType() {
		Type type = new Type();
		type.setCode(DataTypeEnum.CODEABLECONCEPT);
		return type;
	}
	
	public Type buildExtensionType() {
		Type type = new Type();
		type.setCode(DataTypeEnum.EXTENSION);
		type.setProfile("http://hl7.fhir.org/StructureDefinition/my-extension-attribute");
		return type;
	}
	
	public Type buildReferenceType() {
		Type type = new Type();
		type.setCode(DataTypeEnum.REFERENCE);
		type.setProfile("http://hl7.fhir.org/StructureDefinition/my-reference");
		return type;
	}
	
	public Type buildEmptyType() {
		Type type = new Type();
		return type;
	}
	
	public ElementDefinitionDt buildMockJavaBooleanTypeElement() {
		ElementDefinitionDt element = new ElementDefinitionDt();
		element.setName("coreAttribute1");
		element.setPath("Patient.coreAttribute1");
		element.getType().add(buildBooleanJavaType());
		element.setMin(0);
		element.setMax("1");
		return element;
	}
	
	public ElementDefinitionDt buildMockJavaBooleanTypeMultipleCardinalityElement() {
		ElementDefinitionDt element = new ElementDefinitionDt();
		element.setName("coreAttribute1");
		element.setPath("Patient.coreAttribute1");
		element.getType().add(buildBooleanJavaType());
		element.setMin(0);
		element.setMax("*");
		return element;
	}
	
	public ElementDefinitionDt buildMockCodeableConceptElement() {
		ElementDefinitionDt element = new ElementDefinitionDt();
		element.setName("coreAttribute1");
		element.setPath("Patient.coreAttribute1");
		element.getType().add(buildCodeableConceptType());
		element.setMin(0);
		element.setMax("1");
		return element;
	}
	
	public ElementDefinitionDt buildMockMultiTypeElement() {
		ElementDefinitionDt element = new ElementDefinitionDt();
		element.setName("coreAttribute1");
		element.setPath("Patient.coreAttribute1[x]");
		element.getType().add(buildBooleanJavaType());
		element.getType().add(buildCodeableConceptType());
		element.setMin(0);
		element.setMax("1");
		return element;
	}
	
	public ElementDefinitionDt buildMockExtensionTypeElement() {
		ElementDefinitionDt element = new ElementDefinitionDt();
		element.setName("coreAttribute1");
		element.setPath("Patient.coreAttribute1");
		element.getType().add(buildExtensionType());
		element.setMin(0);
		element.setMax("1");
		return element;
	}
	
	public ElementDefinitionDt buildMockEmptyTypeElement() {
		ElementDefinitionDt element = new ElementDefinitionDt();
		element.setName("coreAttribute1");
		element.setPath("Patient.coreAttribute1");
		element.getType().add(buildEmptyType());
		element.setMin(0);
		element.setMax("1");
		return element;
	}
	
	public ElementDefinitionDt buildMockReferenceTypeElement() {
		ElementDefinitionDt element = new ElementDefinitionDt();
		element.setName("coreAttribute1");
		element.setPath("Patient.coreAttribute1");
		element.getType().add(buildReferenceType());
		element.setMin(0);
		element.setMax("1");
		return element;
	}
	
	public StructureDefinition buildMockProfile() {
		StructureDefinition profile = new StructureDefinition();
		profile.setName("Patient");
		profile.setBase("http://hl7.org/fhir/StructureDefinition/Resource");
		return profile;
	}
	
	public void addElementToProfile(ElementDefinitionDt element, StructureDefinition profile) {
		profile.getSnapshot().getElement().add(element);
		profile.getDifferential().getElement().add(element);
	}
	
	@Test
	public void testMe() {
		assertTrue(true);
	}

}
