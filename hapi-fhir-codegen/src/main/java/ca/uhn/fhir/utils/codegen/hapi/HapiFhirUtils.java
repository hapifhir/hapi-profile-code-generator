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
	
	/**
	 * Method returns the HAPI FHIR type for this fhir primitive or null
	 * if no such mapping exists.
	 * 
	 * @param ctx
	 * @param fhirPrimitiveType The FHIR primitive type whose HAPI type we are looking for
	 * @return
	 */
	public static Class<?> getPrimitiveTypeClass(FhirContext ctx, String fhirPrimitiveType) {
		BaseRuntimeElementDefinition<?> elementDefinition = ctx.getElementDefinition(fhirPrimitiveType);
		if(elementDefinition != null) {
			Class<?> primitiveClassName = elementDefinition.getImplementingClass();
			LOGGER.trace(primitiveClassName.getName());
			return primitiveClassName;
		} else {
			LOGGER.trace("No primitive class found for " + fhirPrimitiveType);
			return null;
		}
	}
	
	/**
	 * Method returns the resource class if it exists or null if no such resource can 
	 * be found.
	 * 
	 * @param ctx
	 * @param resourceName The name of the resource whose class we wish to find
	 * @return
	 */
	public static Class<? extends IBaseResource> getResourceClass(FhirContext ctx, String resourceName) {
		Class<? extends IBaseResource> clazz = null;
		try {
			RuntimeResourceDefinition def = ctx.getResourceDefinition(resourceName);
			clazz = def.getImplementingClass();
		} catch(Exception e) {
			LOGGER.trace(resourceName + " is not a valid resource");
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
			if(child == null) {
				if(nextPart.equals("org")) {
					System.out.println("Stop here");
				}
				throw new RuntimeException("No type found for " + resourceName + "." + nextPart);
			}
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
				} else if(child instanceof ca.uhn.fhir.context.RuntimeChildPrimitiveBoundCodeDatatypeDefinition) {
					enumerationType = ((BaseRuntimeChildDatatypeDefinition)child).getBoundEnumType();
					datatype = ((BaseRuntimeChildDatatypeDefinition)child).getDatatype();
				} else if(child instanceof ca.uhn.fhir.context.RuntimeChildPrimitiveDatatypeDefinition) {
					datatype = ((BaseRuntimeChildDatatypeDefinition)child).getDatatype();
				}else {
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
