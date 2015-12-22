package org.cdscollaborative.tools.fhir.codegenerator.method;

import java.util.ArrayList;
import java.util.List;

import org.cdscollaborative.model.meta.Method;
import org.cdscollaborative.tools.fhir.codegenerator.CodeTemplateUtils;
import org.cdscollaborative.tools.fhir.utils.FhirResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt.Type;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;

public class ExtendedBackboneElementHandler extends BaseMethodGenerator {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(ExtendedBackboneElementHandler.class);
	
	private String extendedSupertype;
	
	public ExtendedBackboneElementHandler(FhirResourceManager manager, CodeTemplateUtils template, StructureDefinition profile, ElementDefinitionDt element) {
		super(manager, template, profile, element);
	}
	
	/**
	 * TODO Fix if there is a more elegant way of doing this.
	 * @param supertype
	 */
	public void setExtendedSupertype(String supertype) {
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
		accessors.add(constructGetMethod(getFullyQualifiedType()).setBody(buildDelegatedGetterWithCastToExtendedTypeBody(getTopLevelCoreAttribute(),getElement().getTypeFirstRep().getCode())));
		accessors.add(constructSetMethod(getFullyQualifiedType()).setBody(buildDelegatedSetterBody(getTopLevelCoreAttribute())));
	}
	
	protected void handleMultipleCardinality(List<Method> accessors) {
		String methodBody = buildDelegatedSetterWithCastToExtendedTypeListBody(getTopLevelCoreAttribute(), this.extendedSupertype);
		System.out.println(methodBody);
		accessors.add(constructGetMethodForMultiCardinalityField(getTopLevelCoreAttribute(),getFullyQualifiedType()).setBody(buildDelegatedGetterWithCastToExtendedTypeListBody(getTopLevelCoreAttribute(), getElement().getTypeFirstRep().getCode())));
		accessors.add(constructSetMethodForMultiCardinalityField(getTopLevelCoreAttribute(),getFullyQualifiedType()).setBody(methodBody));
		accessors.add(constructAddMethod(getTopLevelCoreAttribute(), getFullyQualifiedType()).setBody(buildDefaultAddBody(getTopLevelCoreAttribute())));
	}
	
	public void handleType(Type type) {
		String typeString = type.getCode();
		setFullyQualifiedType(getFhirResourceManager().getFullyQualifiedJavaType(typeString));
	}
	
	public static boolean appliesTo(StructureDefinition profile, ElementDefinitionDt element) {
		return true;
	}

}
