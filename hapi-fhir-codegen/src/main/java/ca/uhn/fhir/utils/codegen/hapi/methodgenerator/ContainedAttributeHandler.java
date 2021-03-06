package ca.uhn.fhir.utils.codegen.hapi.methodgenerator;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt.Type;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;
import ca.uhn.fhir.utils.codegen.hapi.MethodBodyGenerator;
import ca.uhn.fhir.utils.codegen.hapi.dstu2.FhirResourceManagerDstu2;
import ca.uhn.fhir.utils.codegen.hapi.InterfaceAdapterGenerator;
import ca.uhn.fhir.utils.common.metamodel.Cardinality;
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
public class ContainedAttributeHandler extends BaseMethodGenerator {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(ContainedAttributeHandler.class);
	
	public ContainedAttributeHandler(FhirResourceManagerDstu2 manager, MethodBodyGenerator template, StructureDefinition profile, ElementDefinitionDt element) {
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
		methods.add(constructGetMethod(getFullyQualifiedType()).setBody(buildDelegatedGetterBody(getTopLevelCoreAttribute())));
		methods.add(constructSetMethod(getFullyQualifiedType(), getFluentReturnType()).setBody(buildDelegatedSetterBody(getTopLevelCoreAttribute())));
		return methods;
	}
	
	public void initialize() {
		super.initialize();
		handleType(getElement().getTypeFirstRep());
		parseTopLevelCoreAttribute();
		setCardinality(Cardinality.OPTIONAL);//TODO Not sure why HAPI does this. Investigate
	}
	
	/**
	 * Method that identifies the HAPI FHIR type
	 * 
	 * @param type
	 */
	public void handleType(Type type) {
		//setFullyQualifiedType(getFhirResourceManager().getFullyQualifiedJavaType(getProfile(), type));
		setFullyQualifiedType(ca.uhn.fhir.model.dstu2.composite.ContainedDt.class.getName());
	}
	
	/**
	 * Method assesses whether the handler can process the ElementDefinitionDt argument.
	 * If the method can process the argument, it returns true. Otherwise, it returns false.
	 * Method returns true only for the 'contained' resource attribute.
	 * 
	 * @param profile
	 * @param element
	 * @return
	 */
	public static boolean appliesTo(StructureDefinition profile, ElementDefinitionDt element) {
		boolean appliesTo = false;
		if(element == null || element.getPath() == null) {
			return appliesTo;
		} else if(element.getPath().endsWith("contained")) {
			appliesTo = true;
		}
		return appliesTo;
	}

}
