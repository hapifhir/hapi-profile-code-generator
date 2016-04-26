package ca.uhn.fhir.utils.codegen.hapi.methodgenerator;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt.Type;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;
import ca.uhn.fhir.model.primitive.UriDt;
import ca.uhn.fhir.utils.codegen.hapi.MethodBodyGenerator;
import ca.uhn.fhir.utils.codegen.hapi.dstu2.FhirResourceManagerDstu2;

public class BaseHandlerTest {
	
	private MethodBodyGenerator template;
	private FhirResourceManagerDstu2 fhirResourceManager;
	
	public BaseHandlerTest() {
		template = new MethodBodyGenerator().initialize();
		fhirResourceManager = new FhirResourceManagerDstu2();
	}
	
	public MethodBodyGenerator getTemplate() {
		return template;
	}

	public void setTemplate(MethodBodyGenerator template) {
		this.template = template;
	}
	
	public FhirResourceManagerDstu2 getFhirResourceManager() {
		return fhirResourceManager;
	}

	public void setFhirResourceManager(FhirResourceManagerDstu2 fhirResourceManager) {
		this.fhirResourceManager = fhirResourceManager;
	}

	/**
	 * Creates a HAPI FHIR type that has a Boolean java type equivalent
	 * @return
	 */
	public Type buildBooleanJavaType() {
		Type type = new Type();
		type.setCode("boolean");
		return type;
	}
	
	public Type buildCodeableConceptType() {
		Type type = new Type();
		type.setCode("codeableconcept");
		return type;
	}
	
	public Type buildExtensionType() {
		Type type = new Type();
		type.setCode("extension");
		UriDt uri = new UriDt("http://hl7.fhir.org/StructureDefinition/my-extension-attribute");
		List<UriDt> uris = new ArrayList<UriDt>();
		uris.add(uri);
		type.setProfile(uris);
		return type;
	}
	
	public Type buildReferenceType() {
		Type type = new Type();
		type.setCode("reference");
		UriDt uri = new UriDt("http://hl7.fhir.org/StructureDefinition/my-reference");
		List<UriDt> uris = new ArrayList<UriDt>();
		uris.add(uri);
		type.setProfile(uris);
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
