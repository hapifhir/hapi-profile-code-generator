package ca.uhn.fhir.utils.codegen.hapi.methodgenerator;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt.Type;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;
import ca.uhn.fhir.utils.codegen.CodeTemplateUtils;
import ca.uhn.fhir.utils.codegen.hapi.FhirResourceManager;
import ca.uhn.fhir.utils.common.metamodel.Method;

/**
 * Class handles top-level, non-multi-type FHIR attributes that do not map to
 * a java language type.
 * <p>
 * Note that multi-type attributes (e.g., resource.attribute[x]) or resource 
 * reference types (e.g., Procedure.patient) are not handled by this handler.
 * <p>
 * Also note that after instantiating this class you must also call the initialize() 
 * method as illustrated below:
 * <pre>
 * <code>
 * SimpleAttributeHandler handler = new SimpleAttributeHandler(manager, template, profile, element);
 * handler.initialize();
 * </code>
 * </pre>
 * 
 * @author Claude Nanjo
 *
 */
public class FilteredAttributeHandler extends BaseMethodGenerator {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(FilteredAttributeHandler.class);
	
	public FilteredAttributeHandler(FhirResourceManager manager, CodeTemplateUtils template, StructureDefinition profile, ElementDefinitionDt element) {
		super(manager, template, profile, element);
	}
	
	/**
	 * Workhorse method of MethodHandler. Generates methods corresponding
	 * to the metadata specified by the FHIR profile element.
	 * <p>
	 * This method filters certain attributes out.
	 */
	@Override
	public List<Method> buildCorrespondingMethods() {
		List<Method> methods = new ArrayList<Method>();
		LOGGER.info("No methods generated for " + getElement().getPath());
		return methods;
	}
	
	public void initialize() {
		super.initialize();
	}
	
	/**
	 * Method assesses whether the handler can process the ElementDefinitionDt argument.
	 * If the method can process the argument, it returns true. Otherwise, it returns false.
	 * This method will return true when it encounters types that are not currently handled.
	 * At this time, the following types are filtered from code generation:
	 * <ul>
	 * <li>ModifierExtensions - not allowed for CDS</li>
	 * <li>ImplicitRules - Need to figure out what to do with those</li>
	 * <li>Meta - due to a bug in Roaster</li>
	 * </ul>
	 * 
	 * @param profile
	 * @param element
	 * @return
	 */
	public static boolean appliesTo(StructureDefinition profile, ElementDefinitionDt element) {
		boolean appliesTo = false;
		if(element == null || element.getPath() == null) {
			return appliesTo;
		} else if(element.getPath().endsWith("ModifierExtension")) {
			appliesTo = true;
			LOGGER.info("Ignoring ModifierExtension");
		} else if(element.getPath().endsWith("ImplicitRules")) { //TODO Not sure what this is. Investigate.
			appliesTo = true;
			LOGGER.info("Ignoring ImplicitRules");
		} else if(element.getPath().endsWith("meta")) { 
			appliesTo = true;
			LOGGER.info("Ignoring meta");
		}
		return appliesTo;
	}

}
