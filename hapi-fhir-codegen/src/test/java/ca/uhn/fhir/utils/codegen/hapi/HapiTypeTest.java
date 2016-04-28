package ca.uhn.fhir.utils.codegen.hapi;

import org.hl7.fhir.dstu3.model.BodySite;
import org.hl7.fhir.dstu3.model.DomainResource;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by cnanjo on 4/26/16.
 */
public class HapiTypeTest {
    @Test
    public void isResource() throws Exception {
        HapiType hapiType = new HapiType();
        hapiType.setDatatypeClass(BodySite.class);
        assertTrue(hapiType.isResource());
    }

    @Test
    public void removeMe() {
        assertTrue(DomainResource.class.isAssignableFrom(BodySite.class));
    }

}