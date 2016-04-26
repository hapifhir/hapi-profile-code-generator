package ca.uhn.fhir.utils.codegen.hapi;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.utils.fhir.model.FhirExtensionDefinition;

public interface IFhirResourceManager<T> {
	
	public FhirContext getFhirContext();
	
	public T getProfileFromProfileUri(String uri);
	
	public FhirExtensionDefinition getFhirExtension(String uri);

}
