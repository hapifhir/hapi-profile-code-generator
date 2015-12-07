package org.socraticgrid.fhir.generated;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.composite.CodeableConceptDt;
import ca.uhn.fhir.model.dstu2.composite.CodingDt;
import ca.uhn.fhir.model.dstu2.composite.TimingDt;
import ca.uhn.fhir.parser.IParser;

public class QICorePatientAdapterTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		QICorePatientAdapter patient = new QICorePatientAdapter();
		CodeableConceptDt religion = new CodeableConceptDt();
		CodingDt religionCode = new CodingDt("http://thedevine", "Spiritual");
		religion.getCoding().add(religionCode);
		patient.setReligion(religion);
		patient.setActive(true);
		patient.setBirthDate(new Date());
		List<Citizenship> citizenships = new ArrayList<Citizenship>();
		Citizenship citizenship = new Citizenship();
		CodeableConceptDt codeableConcept = new CodeableConceptDt();
		CodingDt coding = new CodingDt("http://snomedct", "234");
		coding.setDisplay("My first code");
		codeableConcept.getCoding().add(coding);
		citizenship.setCode(codeableConcept);
		TimingDt timing = new TimingDt();
		timing.addEvent(new Date());
		citizenship.setPeriod(timing);
		citizenships.add(citizenship);
		patient.setCitizenship(citizenships);
		FhirContext context = FhirContext.forDstu2();
		IParser parser = context.newXmlParser();
		String patientXml = parser.encodeResourceToString(patient.getAdaptee());
		System.out.println(patientXml);
	}

}
