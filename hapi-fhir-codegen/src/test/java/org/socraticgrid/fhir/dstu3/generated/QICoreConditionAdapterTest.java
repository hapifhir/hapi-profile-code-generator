package org.socraticgrid.fhir.dstu3.generated;

import org.hl7.fhir.dstu3.model.*;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by cnanjo on 5/4/16.
 */
public class QICoreConditionAdapterTest {

//    @Test
//    public void testCodeGeneration() {
//        QICoreConditionAdapter adapter = new QICoreConditionAdapter(new Condition());
//        testBodySite(adapter);
//        testEvidence(adapter);
//        testIdentifier(adapter);
//        testAbatement(adapter);
//        testAsserter(adapter);
//        testCategory(adapter);
//        testPatient(adapter);
//        testClinicalStatus(adapter);
//        testDateRecorded(adapter);
//        getVerificationStatus(adapter);
//        //adapter.copy();
//        //adapter.equalsDeep();
//        //adapter.equalsShallow();
//        //adapter.fhirType();
//    }
//
//    private void testBodySite(QICoreConditionAdapter adapter) {
//        CodeableConcept bodySiteCodeableConcept = adapter.addBodySite();
//        bodySiteCodeableConcept.addCoding().setSystem("http://www.example.com").setCode("123").setDisplay("Some Code");
//        assertEquals(1, adapter.getBodySite().size());
//        assertEquals(adapter.getAdaptee().getBodySite(), adapter.getBodySite());
//        adapter.getBodySite().clear();
//        assertEquals(0, adapter.getAdaptee().getBodySite().size());
//        adapter.addBodySite(buildCodeableConcept());
//        assertEquals(1, adapter.getBodySite().size());
//        assertEquals(buildCodeableConcept().getCoding().get(0).getCode(), adapter.getBodySite().get(0).getCoding().get(0).getCode());
//        assertTrue(adapter.getAdaptee().hasBodySite());
//        assertTrue(adapter.hasBodySite());
//    }
//
//    private void testEvidence(QICoreConditionAdapter adapter) {
//        Condition.ConditionEvidenceComponent evidence = adapter.addEvidence();
//        CodeableConcept concept = buildCodeableConcept();
//        evidence.setCode(concept);
//        assertNotNull(adapter.getEvidence());
//        adapter.getEvidence().clear();
//        assertEquals(0, adapter.getEvidence().size());
//        adapter.addEvidence(evidence);
//        assertEquals(concept, adapter.getEvidence().get(0).getCode());
//        assertTrue(adapter.hasEvidence());
//    }
//
//    private void testIdentifier(QICoreConditionAdapter adapter) {
//        assertTrue(adapter.getIdentifier() == null || adapter.getIdentifier().size() == 0);
//        Identifier identifier = adapter.addIdentifier();
//        identifier.setSystem("http://some/system");
//        identifier.setValue("1");
//        assertEquals(identifier, adapter.getIdentifier().get(0));
//        adapter.getIdentifier().clear();
//        assertEquals(0, adapter.getIdentifier().size());
//        adapter.addIdentifier(identifier);
//        assertTrue(adapter.hasIdentifier());
//    }
//
//    private void testAbatement(QICoreConditionAdapter adapter) {
//        adapter.setAbatement(new BooleanType().setValue(true));
//        assertFalse(adapter.hasAbatementAge());
//        assertTrue(adapter.hasAbatementBooleanType());
//        assertFalse(adapter.hasAbatementDateTimeType());
//        assertFalse(adapter.hasAbatementPeriod());
//        assertFalse(adapter.hasAbatementRange());
//        assertFalse(adapter.hasAbatementStringType());
//        Type abatementType = adapter.getAbatement();
//        assertTrue(abatementType instanceof BooleanType);
//        assertNull(adapter.getAbatementAge());
//        assertNotNull(adapter.getAbatementBooleanType());
//        assertNull(adapter.getAbatementDateTimeType());
//        assertNull(adapter.getAbatementPeriod());
//        assertNull(adapter.getAbatementRange());
//        assertNull(adapter.getAbatementStringType());
//        assertTrue(adapter.hasAbatement());
//        adapter.setAbatement(new DateType());
//        assertFalse(adapter.hasAbatementDateTimeType());
//    }
//
//    private void testAsserter(QICoreConditionAdapter adapter) {
//        adapter.setAsserterTarget(new Patient());
//        Reference reference = adapter.getAsserter();
//        assertNotNull(adapter.getAsserter());
//        assertTrue(adapter.getAsserter().getResource() instanceof Patient);
//        assertNotNull(adapter.getAsserterPatient());
//        assertNotNull(adapter.getAsserterPatientTarget());
//        assertNull(adapter.getAsserterPractitioner());
//        assertNull(adapter.getAsserterPractitionerTarget());
//        adapter.setAsserter(reference);
//        adapter.setAsserterTarget(new Practitioner());
//        assertTrue(adapter.hasAsserter());
//        adapter.setAsserterAdapterTarget(new QICorePatientAdapter(new Patient()));
//        assertNotNull(adapter.getAsserterPatientTarget());
//    }
//
//    private void testCategory(QICoreConditionAdapter adapter) {
//        CodeableConcept category = buildCodeableConcept();
//        adapter.setCategory(category);
//        assertNotNull(adapter.getCategory());
//        assertTrue(adapter.hasCategory());
//        adapter.setCategory(null);
//        assertFalse(adapter.hasCategory());
//    }
//
//    private void testPatient(QICoreConditionAdapter adapter) {
//        Reference reference = adapter.getPatient();
//        assertNull(reference);
//        assertFalse(adapter.hasPatient());
//        adapter.setPatientTarget(new Patient());
//        assertNotNull(adapter.getPatient());
//        assertTrue(adapter.getPatientTarget() instanceof Patient);
//        assertTrue(adapter.hasPatient());
//        adapter.setPatient(null);
//        reference = new Reference(new Patient());
//        adapter.setPatient(reference);
//        assertTrue(adapter.hasPatient());
//        adapter.setPatientAdapterTarget(new QICorePatientAdapter());
//        QICorePatientAdapter patientAdapter = adapter.getPatientAdapterTarget();
//        assertNotNull(patientAdapter);
//    }
//
//    private void testClinicalStatus(QICoreConditionAdapter adapter) {
//        CodeType code = new CodeType();
//        code.setValue("myCode");
//        adapter.setClinicalStatusElement(code);
//        assertEquals("myCode", adapter.getClinicalStatus());
//        assertEquals(code, adapter.getClinicalStatusElement());
//        assertTrue(adapter.hasClinicalStatus());
//        assertTrue(adapter.hasClinicalStatusElement());
//        adapter.setClinicalStatusElement(null);
//        assertFalse(adapter.hasClinicalStatus());
//        assertFalse(adapter.hasClinicalStatusElement());
//    }
//
//    private void testDateRecorded(QICoreConditionAdapter adapter) {
//        Date date = new Date();
//        DateType dateType = new DateType(date);
//        adapter.setDateRecordedElement(dateType);
//        assertTrue(adapter.hasDateRecorded());
//        assertTrue(adapter.hasDateRecordedElement());
//        DateType dateElt = adapter.getDateRecordedElement();
//        assertEquals(dateType, dateElt);
//        assertEquals(date, adapter.getDateRecorded());
//        adapter.setDateRecordedElement(null);
//        assertFalse(adapter.hasDateRecorded());
//        adapter.setDateRecorded(date);
//        assertTrue(adapter.hasDateRecorded());
//    }
//
//    private void testGetStage(QICoreConditionAdapter adapter) {
//        Condition.ConditionStageComponent stage = new Condition.ConditionStageComponent();
//        assertFalse(adapter.hasStage());
//        CodeableConcept code = buildCodeableConcept();
//        stage.setSummary(code);
//        adapter.setStage(stage);
//        assertTrue(adapter.hasStage());
//        assertNotNull(adapter.getStage());
//    }
//
//    private void getVerificationStatus(QICoreConditionAdapter adapter) {
//        Condition.ConditionVerificationStatus verificationStatus = Condition.ConditionVerificationStatus.CONFIRMED;
//        assertFalse(adapter.hasVerificationStatus());
//        assertFalse(adapter.hasVerificationStatusElement());
//        adapter.setVerificationStatus(verificationStatus);
//        assertEquals(Condition.ConditionVerificationStatus.CONFIRMED, adapter.getVerificationStatus());
//        Enumeration<Condition.ConditionVerificationStatus> verificationStatusEnum = adapter.getVerificationStatusElement();
//        assertTrue(adapter.hasVerificationStatus());
//        assertTrue(adapter.hasVerificationStatusElement());
//        adapter.setVerificationStatusElement(null);
//    }
//
//    private CodeableConcept buildCodeableConcept() {
//        CodeableConcept concept = new CodeableConcept();
//        concept.addCoding().setSystem("http://www.example.com").setCode("123").setDisplay("Some Code");
//        return concept;
//    }
}