package ca.uhn.fhir.utils.codegen.hapi;

import java.util.Arrays;
import java.util.Iterator;

import org.hl7.fhir.instance.model.api.IBase;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.context.BaseRuntimeChildDatatypeDefinition;
import ca.uhn.fhir.context.BaseRuntimeChildDefinition;
import ca.uhn.fhir.context.BaseRuntimeElementCompositeDefinition;
import ca.uhn.fhir.context.BaseRuntimeElementDefinition;
import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.RuntimeResourceDefinition;

/**
 * Utility class for HAPI-related convenience methods
 * 
 * @author cnanjo
 *
 */
public class HapiFhirUtils {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(HapiFhirUtils.class);//TODO Change to use HAPI Logging framework: private static final org.slf4j.Logger ourLog = org.slf4j.LoggerFactory.getLogger(FhirContextDstu2Test.class);
	
	public static Class<? extends Enum<?>> getBoundCode(FhirContext ctx, String resourceName, String fieldName) {
		Class<? extends IBaseResource> resourceClass = getResourceClass(ctx, resourceName);
		return getBoundCode(ctx, resourceClass, fieldName);
	}
	
	public static Class<? extends Enum<?>> getBoundCode(FhirContext ctx, Class<? extends IBaseResource> resourceClass, String fieldName) {
		RuntimeResourceDefinition resourceType = ctx.getResourceDefinition(resourceClass);
		BaseRuntimeChildDatatypeDefinition field = (BaseRuntimeChildDatatypeDefinition) resourceType.getChildByName(fieldName);
		LOGGER.trace(field.getClass().getName());
		
		return ((BaseRuntimeChildDatatypeDefinition)field).getBoundEnumType();
	}
	
	public static HapiFhirUtils.TypeDefinition getBoundCodeableConcept(FhirContext ctx, String resourceName, String fieldName) {
		Class<? extends IBaseResource> resourceClass = getResourceClass(ctx, resourceName);
		return getBoundCodeableConcept(ctx, resourceClass, fieldName);
	}
	
	public static HapiFhirUtils.TypeDefinition getBoundCodeableConcept(FhirContext ctx, Class<? extends IBaseResource> resourceClass, String fieldName) {
		return getStructureTypeDef(ctx, resourceClass, fieldName);
	}
	
	public static String getPrimitiveTypeClassName(FhirContext ctx, String fhirPrimitiveType) {
		Class<?> primitiveType = getPrimitiveTypeClass(ctx, fhirPrimitiveType);
		if(primitiveType != null) {
			return primitiveType.getName();
		} else {
			return null;
		}
	}
	
	public static Class<?> getPrimitiveTypeClass(FhirContext ctx, String fhirPrimitiveType) {
		Class<?> primitiveClassName = ctx.getElementDefinition(fhirPrimitiveType).getImplementingClass();
		if(primitiveClassName != null) {
			LOGGER.trace(primitiveClassName.getName());
		} else {
			LOGGER.trace("No primitive class found for " + fhirPrimitiveType);
		}
		return primitiveClassName;
	}
	
	public static Class<? extends IBaseResource> getResourceClass(FhirContext ctx, String resourceName) {
		Class clazz = null;
		RuntimeResourceDefinition def = ctx.getResourceDefinition(resourceName);
		if(def == null) {
			throw new RuntimeException("Unknown type " + resourceName);
		} else {
			clazz = def.getImplementingClass();
		}
		return clazz;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param resourceName E.g., "Patient"
	 * @param structurePath E.g., "address.line"
	 */
	public static Class<?> getStructureTypeClass(FhirContext ctx, String resourceName, String structurePath) {
		
		BaseRuntimeElementCompositeDefinition<?> parentDef = ctx.getResourceDefinition(resourceName);
		
		Class<?> childType = null;
		for (Iterator<String> iter = Arrays.asList(structurePath.split("\\.")).iterator(); iter.hasNext(); ) {
			
			String nextPart = iter.next();
			BaseRuntimeChildDefinition child = parentDef.getChildByName(nextPart);
			BaseRuntimeElementDefinition<?> childDef = child.getChildByName(nextPart);
			
			if (iter.hasNext()) {
				parentDef = (BaseRuntimeElementCompositeDefinition<?>) childDef;
			} else {
				childType = childDef.getImplementingClass();
			}
		}
		
		if(childType != null) {
			LOGGER.trace(childType.getName());
		} else {
			LOGGER.trace("No type found for " + resourceName + "." + structurePath);
		}
		
		return childType;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param resourceName E.g., "Patient"
	 * @param structurePath E.g., "address.line"
	 */
	public static HapiFhirUtils.TypeDefinition getStructureTypeDef(FhirContext ctx, Class<? extends IBaseResource> resourceClass, String structurePath) {
		BaseRuntimeElementCompositeDefinition<?> parentDef = ctx.getResourceDefinition(resourceClass);
		
		HapiFhirUtils.TypeDefinition childType = null;
		for (Iterator<String> iter = Arrays.asList(structurePath.split("\\.")).iterator(); iter.hasNext(); ) {
			
			String nextPart = iter.next();
			BaseRuntimeChildDefinition child = parentDef.getChildByName(nextPart);
			BaseRuntimeElementDefinition<?> childDef = child.getChildByName(nextPart);
			
			if (iter.hasNext()) {
				parentDef = (BaseRuntimeElementCompositeDefinition<?>) childDef;
			} else {
				Class<? extends IBase> datatype = null;
				Class<? extends Enum<?>> enumerationType = null;
				
				if(child instanceof ca.uhn.fhir.context.RuntimeChildCompositeBoundDatatypeDefinition) {
					enumerationType = ((BaseRuntimeChildDatatypeDefinition)child).getBoundEnumType();
					datatype = ((BaseRuntimeChildDatatypeDefinition)child).getDatatype();
				} else if(child instanceof ca.uhn.fhir.context.RuntimeChildCompositeDatatypeDefinition) {
					datatype = ((BaseRuntimeChildDatatypeDefinition)child).getDatatype();
				} else if(child instanceof ca.uhn.fhir.context.RuntimeChildResourceBlockDefinition) {
					//Do nothing here
				} else {
					throw new RuntimeException("Unknown type " + child.getClass().getName());
				}
				childType = new HapiFhirUtils.TypeDefinition(datatype, enumerationType);
			}
		}
		
		return childType;
	}
	
	public static final class TypeDefinition {
		private final Class<? extends IBase> datatype;
		private final Class<? extends Enum<?>> enumerationType;
		
		public TypeDefinition(Class<? extends IBase> datatype, Class<? extends Enum<?>> enumerationType) {
			this.datatype = datatype;
			this.enumerationType = enumerationType;
		}
		
		public String getDatatype() {
			return datatype.getName();
		}
		
		public Class<? extends IBase> getDatatypeClass() {
			return datatype;
		}

		public String getEnumerationType() {
			return enumerationType.getName();
		}
		
		public Class<? extends Enum<?>> getEnumerationTypeClass() {
			return enumerationType;
		}

		public boolean isEnumerationType() {
			return enumerationType != null;
		}
		
		public String toString() {
			return "Datatype: " + datatype + ", Enumeration Type: " + enumerationType;
		}
		
		public String getCodedTypeAsString() {
			String boundType = null;
			if(isEnumerationType()) {
				boundType = getDatatype() + "<" + getEnumerationType() + ">";
			} else {
				boundType = getDatatype();
			}
			return boundType;
		}
	}
}
