/*
 * #%L
 * hspc-client-ioc-example
 * %%
 * Copyright (C) 2014 - 2015 Healthcare Services Platform Consortium
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.hspconsortium.client.example;

import ca.uhn.fhir.context.FhirContext;
import org.hl7.fhir.dstu3.model.*;
import org.hspconsortium.client.auth.credentials.Credentials;
import org.hspconsortium.client.session.clientcredentials.ClientCredentialsSessionFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Calendar;
import java.util.UUID;

public interface PatientCompareService {

    int comparePatientName(String patientId);

    @Component
    class Impl implements PatientCompareService {
        @Inject
        ClientCredentialsSessionFactory<? extends Credentials> ehr1SessionFactory;

        @Inject
        ClientCredentialsSessionFactory<? extends Credentials> ehr2SessionFactory;

        @Inject
        FhirContext fhirContext;

        public int comparePatientName(String patientId) {

            org.hspc.fhir.model.stu3.IUsCorePatient patient = new org.hspc.fhir.model.stu3.UsCorePatientAdapter();
            patient.setId(UUID.randomUUID().toString());
            patient.addIdentifier().setId(UUID.randomUUID().toString());
            patient.setBirthDate(Calendar.getInstance().getTime());
            final CodeableConcept maritalStatus = new CodeableConcept();
            maritalStatus.setId("M");
            maritalStatus.setText("Married");
            maritalStatus.setCoding(Arrays.asList(new Coding[] {new Coding("http://hl7.org/fhir/v3/MaritalStatus", "M", "Married")
                    , new Coding("http://hl7.org/fhir/v3/MaritalStatus", "A", "Annulled")
            , new Coding("http://hl7.org/fhir/v3/MaritalStatus", "W", "Widowed")}));
            patient.setMaritalStatus(maritalStatus);

            patient.setGender(Enumerations.AdministrativeGender.MALE);

            patient.setMultipleBirth(new IntegerType(3));

            patient.setActive(true);
            Address address = new Address();
            address.setType(Address.AddressType.PHYSICAL);
            address.addLine("1234 Neverland Road");
            address.setCity("Red Land");
            address.setState("UT");
            address.setPostalCode("95112-1234");

            patient.getAddress().add(address);

//            String output =fhirContext.newJsonParser().setPrettyPrint(true).encodeResourceToString(patient.getAdaptee());
            String output =fhirContext.newXmlParser().setPrettyPrint(true).encodeResourceToString(patient.getAdaptee());
            System.out.println(output);
            ehr1SessionFactory.validate(patient.getAdaptee());
            ehr1SessionFactory
                    .createSession().create().resource(patient.getAdaptee());

            // look up the patient in ehr1
            System.out.println("Looking up patient in EHR1...");
            Patient patient1 = ehr1SessionFactory
                    .createSession()
                    .read()
                    .resource(Patient.class)
                    .withId(patientId)
                    .execute();
            System.out.println("Found patient: " + patient1.getNameFirstRep().getNameAsSingleString());

            // look up the patient in ehr2
            System.out.println("Looking up patient in EHR2...");
            Patient patient2 = ehr2SessionFactory
                    .createSession()
                    .read()
                    .resource(Patient.class)
                    .withId(patientId)
                    .execute();
            System.out.println("Found patient: " + patient2.getNameFirstRep().getNameAsSingleString());

            // Compare the patient based on their name string
            return patient1.getNameFirstRep().getNameAsSingleString()
                    .compareTo(patient2.getNameFirstRep().getNameAsSingleString());
        }
    }
}
