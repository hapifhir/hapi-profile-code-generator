package ca.uhn.fhir.utils.codegen.hapi.methodgenerator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt.Type;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;
import ca.uhn.fhir.utils.codegen.hapi.FhirResourceManager;
import ca.uhn.fhir.utils.codegen.hapi.InterfaceAdapterGenerator;
import ca.uhn.fhir.utils.codegen.hapi.MethodBodyGenerator;
import ca.uhn.fhir.utils.common.metamodel.Method;

public class ExtendedBackboneElementHandler extends BaseMethodGenerator {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(ExtendedBackboneElementHandler.class);
	
	private String extendedSupertype;
	private String adaptedType;
	
	public ExtendedBackboneElementHandler(FhirResourceManager manager, MethodBodyGenerator template, StructureDefinition profile, ElementDefinitionDt element) {
		super(manager, template, profile, element);
	}
	
	/**
	 * TODO Fix if there is a more elegant way of doing this.
	 * @param supertype
	 */
	public void setExtendedSupertype(Type supertype) {
		this.extendedSupertype = getFhirResourceManager().getFullyQualifiedJavaType(supertype);
	}

	@Override
	public List<Method> buildCorrespondingMethods() {
		try {
			List<Method> methods = new ArrayList<Method>();
			if(!appliesTo(getProfile(), getElement()) || ignoreField()) {
				return methods;
			} else {
				if(isMultipleCardinality()) {
					handleMultipleCardinality(methods);
				} else {
					handleSingleCardinality(methods);
				}
				
				return methods;
			}
		} catch(Exception e) {
			LOGGER.error("Error building methods for " + getElement().getPath(), e);
			throw new RuntimeException("Error building methods for " + getElement().getPath(), e);
		}
	}
	
	public void initialize() {
		super.initialize();
		parseTopLevelCoreAttribute();
		handleType(getElement().getTypeFirstRep());
	}
	
	protected void handleSingleCardinality(List<Method> accessors) {
		accessors.add(constructGetMethodFromField("Wrapped" + StringUtils.capitalize(getTopLevelCoreAttribute()),getFullyQualifiedType()).setBody(buildDelegatedGetterWithCastToExtendedTypeBody(getTopLevelCoreAttribute(),getElement().getTypeFirstRep().getCode())));
		accessors.add(constructSetMethodFromField("Wrapped" + StringUtils.capitalize(getTopLevelCoreAttribute()),getFullyQualifiedType(), InterfaceAdapterGenerator.generateInterfaceName(getProfile())).setBody(buildDelegatedSetterBody(getTopLevelCoreAttribute())));
	}
	
	protected void handleMultipleCardinality(List<Method> accessors) {
//		String methodBody = buildDelegatedSetterWithCastToExtendedTypeListBody(getTopLevelCoreAttribute(), this.extendedSupertype);
		accessors.add(constructGetMethodForMultiCardinalityField("Wrapped" + StringUtils.capitalize(getTopLevelCoreAttribute()),getFullyQualifiedType()).setBody(getWrappedTypeListBody(getFullyQualifiedType(), this.extendedSupertype, StringUtils.capitalize(getTopLevelCoreAttribute()))));
		accessors.add(constructSetMethodForMultiCardinalityField("Wrapped" + StringUtils.capitalize(getTopLevelCoreAttribute()),getFullyQualifiedType(),  InterfaceAdapterGenerator.generateInterfaceName(getProfile())).setBody(setWrappedTypeListBody(getFullyQualifiedType(), this.extendedSupertype, StringUtils.capitalize(getTopLevelCoreAttribute()))));
		accessors.add(constructAddMethod("Wrapped" + StringUtils.capitalize(getTopLevelCoreAttribute()), getFullyQualifiedType(), InterfaceAdapterGenerator.generateInterfaceName(getProfile())).setBody(addWrappedTypeToListMethodDelegatedBody(getTopLevelCoreAttribute())));
		accessors.add(constructAddMethodDelegated("Wrapped" + StringUtils.capitalize(getTopLevelCoreAttribute()), getFullyQualifiedType()).setBody(addWrappedTypeToListMethodBody(getFullyQualifiedType(), this.extendedSupertype, StringUtils.capitalize(getTopLevelCoreAttribute()))));
		accessors.add(constructGetFirstRepMethodFromField("Wrapped" + StringUtils.capitalize(getTopLevelCoreAttribute()),getFullyQualifiedType()).setBody(getWrappedTypeFirstRepMethodBody(getFullyQualifiedType(), this.extendedSupertype, StringUtils.capitalize(getTopLevelCoreAttribute()))));
	}
	
	public void handleType(Type type) {
		setFullyQualifiedType(getFhirResourceManager().getFullyQualifiedJavaType(type));
	}
	
	public static boolean appliesTo(StructureDefinition profile, ElementDefinitionDt element) {
		return true;
	}

}
