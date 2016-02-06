package ca.uhn.fhir.utils.codegen;

import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaInterfaceSource;

/**
 * Convenience class that packages the generated profile interface
 * with its corresponding adapter implementation.
 * 
 * @author Claude Nanjo
 *
 */
public class InterfaceAdapterPair {
	
	private JavaInterfaceSource resourceInterface;
	private JavaClassSource resourceAdapter;

	public InterfaceAdapterPair() {}
	
	public InterfaceAdapterPair(JavaInterfaceSource javaInterface, JavaClassSource javaClass) {
		this.resourceInterface = javaInterface;
		this.resourceAdapter = javaClass;
	}
	
	/**
	 * Returns the generated FHIR profile interface
	 * 
	 * @return
	 */
	public JavaInterfaceSource getResourceInterface() {
		return resourceInterface;
	}
	
	/**
	 * Sets the generated FHIR profile interface
	 * 
	 * @param resourceInterface
	 */
	public void setResourceInterface(JavaInterfaceSource resourceInterface) {
		this.resourceInterface = resourceInterface;
	}
	
	/**
	 * Returns the generated HAPI FHIR profile interface implementation - a.k.a., resource adapter
	 * 
	 * @return
	 */
	public JavaClassSource getResourceAdapter() {
		return resourceAdapter;
	}
	
	/**
	 * Sets the generated HAPI FHIR profile interface implementation - a.k.a., resource adapter
	 * 
	 * @param resourceAdapter
	 */
	public void setResourceAdapter(JavaClassSource resourceAdapter) {
		this.resourceAdapter = resourceAdapter;
	}
	
}
