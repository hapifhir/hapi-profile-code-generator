package ca.uhn.fhir.utils.codegen.hapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.hl7.fhir.instance.model.api.IBase;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.context.BaseRuntimeChildDatatypeDefinition;
import ca.uhn.fhir.context.BaseRuntimeChildDefinition;
import ca.uhn.fhir.context.BaseRuntimeElementCompositeDefinition;
import ca.uhn.fhir.context.BaseRuntimeElementDefinition;
import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.RuntimeChildChoiceDefinition;
import ca.uhn.fhir.context.RuntimeResourceDefinition;
import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt.Type;
import ca.uhn.fhir.utils.fhir.PathUtils;
import ca.uhn.fhir.utils.fhir.model.datatype.dstu2.FhirDatatypeEnum;

/**
 * Utility class for HAPI-related convenience methods
 * 
 * @author cnanjo
 *
 */
public class HapiFhirUtils {

	public static final Logger LOGGER = LoggerFactory.getLogger(HapiFhirUtils.class);// TODO
																						// Change
																						// to
																						// use
																						// HAPI
																						// Logging
																						// framework:
																						// private
																						// static
																						// final
																						// org.slf4j.Logger
																						// ourLog
																						// =
																						// org.slf4j.LoggerFactory.getLogger(FhirContextDstu2Test.class);

	public static HapiType resolveBoundedAttributeTypes(FhirContext ctx, String resourceName,
			String fieldName) {
		Class<? extends IBaseResource> resourceClass = getResourceClass(ctx, resourceName);
		return resolveBoundedAttributeTypes(ctx, resourceClass, fieldName);
	}

	public static HapiType resolveBoundedAttributeTypes(FhirContext ctx,
			Class<? extends IBaseResource> resourceClass, String fieldName) {
		return getStructureTypeDef(ctx, resourceClass, fieldName);
	}

	/**
	 * Method takes a Type argument and returns the corresponding HAPI datatype
	 * class. If the type is a specialization of the parent type specified in
	 * Type.code (this specialization will be given by the Type.profile
	 * attribute), the specialized type will be returned instead.
	 * 
	 * If the type does not correspond to any FHIR datatype, null is returned.
	 * Note, no exception will be thrown so the caller must test for nullity.
	 * 
	 * For instance, if Type.code = 'Quantity' and Type.profile =
	 * 'http://hl7.org/fhir/StructureDefinition/SimpleQuantity'
	 * ca.uhn.fhir.model.dstu2.composite.SimpleQuantityDt.class shall be
	 * returned and not ca.uhn.fhir.model.dstu2.composite.QuantityDt.class
	 * 
	 * @param ctx
	 * @param type
	 * @return
	 */
	public static Class<?> getDataTypeClass(FhirContext ctx, Type type) {
		Class<?> datatype = null;
		String fhirType = type.getCode();
		String profileType = type.getProfileFirstRep().getValue();
		if (FhirDatatypeEnum.contains(fhirType)) {
			if (profileType != null) {
				profileType = PathUtils.getLastPathComponent(profileType, '/');
				if (FhirDatatypeEnum.contains(profileType)
						&& FhirDatatypeEnum.getEnumeratedDatatype(fhirType).hasSpecialization(profileType)) {
					datatype = getPrimitiveTypeClass(ctx, profileType);
				} else {
					LOGGER.error("Unrecognized profile: " + type.getProfileFirstRep().getValue());
				}
			} else {
				datatype = getPrimitiveTypeClass(ctx, fhirType);
			}
		}
		return datatype;
	}
	
	/**
	 * Method checks to see if:
	 * <ol>
	 *   <li>type is a simple type</li>
	 *   <li>type is a structure</li>
	 * </ol>
	 * Method returns the HAPI class corresponding to this type or attribute.
	 * @param ctx
	 * @param type
	 * @param parentName
	 * @param attributePath
	 * @return
	 */
	public static Class<?> discoverType(FhirContext ctx, Type type, String parentName, String attributePath) {
		Class<?> datatypeClass = HapiFhirUtils.getDataTypeClass(ctx, type);
		if(datatypeClass == null) {
			datatypeClass = HapiFhirUtils.getStructureTypeClass(ctx,
					parentName, attributePath);
		}
		if(datatypeClass == null) {
			throw new RuntimeException("Unknown type " + type.getCode() + ", profile: " + type.getProfileFirstRep().getValue());
		}
		return datatypeClass;
	}

	public static String getPrimitiveTypeClassName(FhirContext ctx, String fhirPrimitiveType) {
		Class<?> primitiveType = getPrimitiveTypeClass(ctx, fhirPrimitiveType);
		if (primitiveType != null) {
			return primitiveType.getName();
		} else {
			return null;
		}
	}

	/**
	 * Method returns the HAPI FHIR type for this fhir primitive or null if no
	 * such mapping exists.
	 * 
	 * @param ctx
	 * @param fhirPrimitiveType
	 *            The FHIR primitive type whose HAPI type we are looking for
	 * @return
	 */
	public static Class<? extends IBase> getPrimitiveTypeClass(FhirContext ctx, String fhirPrimitiveType) {
		BaseRuntimeElementDefinition<?> elementDefinition = ctx.getElementDefinition(fhirPrimitiveType);
		if (elementDefinition != null) {
			Class<? extends IBase> primitiveClassName = elementDefinition.getImplementingClass();
			LOGGER.trace(primitiveClassName.getName());
			return primitiveClassName;
		} else {
			LOGGER.trace("No primitive class found for " + fhirPrimitiveType);
			return null;
		}
	}

	/**
	 * Method returns the resource class if it exists or null if no such
	 * resource can be found.
	 * 
	 * @param ctx
	 * @param resourceName
	 *            The name of the resource whose class we wish to find
	 * @return
	 */
	public static Class<? extends IBaseResource> getResourceClass(FhirContext ctx, String resourceName) {
		Class<? extends IBaseResource> clazz = null;
		try {
			RuntimeResourceDefinition def = ctx.getResourceDefinition(resourceName);
			clazz = def.getImplementingClass();
		} catch (Exception e) {
			LOGGER.trace(resourceName + " is not a valid resource");
		}
		return clazz;
	}
	
	public static String getResourceClassName(FhirContext ctx, String resourceName) {
		Class<? extends IBaseResource> resourceClass = getResourceClass(ctx, resourceName);
		String resourceClassName = null;
		if(resourceClass != null) {
			resourceClassName = resourceClass.getName();
		}
		return resourceClassName;
	}

	/**
	 * 
	 * @param ctx
	 * @param resourceName
	 *            E.g., "Patient"
	 * @param structurePath
	 *            E.g., "address.line"
	 */
	public static Class<? extends IBase> getStructureTypeClass(FhirContext ctx, String resourceName, String structurePath) {

		BaseRuntimeElementCompositeDefinition<?> parentDef = ctx.getResourceDefinition(resourceName);

		Class<? extends IBase> childType = null;
		for (Iterator<String> iter = Arrays.asList(structurePath.split("\\.")).iterator(); iter.hasNext();) {

			String nextPart = iter.next();
			BaseRuntimeChildDefinition child = parentDef.getChildByName(nextPart);
			if (child == null) {
				throw new RuntimeException("No type found for " + resourceName + "." + structurePath);
			}
			BaseRuntimeElementDefinition<?> childDef = child.getChildByName(nextPart);

			if (iter.hasNext()) {
				parentDef = (BaseRuntimeElementCompositeDefinition<?>) childDef;
			} else {
				childType = childDef.getImplementingClass();
			}
		}

		if (childType != null) {
			LOGGER.trace(childType.getName());
		} else {
			LOGGER.trace("No type found for " + resourceName + "." + structurePath);
		}

		return childType;
	}

	/**
	 * 
	 * @param ctx
	 * @param resourceName
	 *            E.g., "Patient"
	 * @param structurePath
	 *            E.g., "address.line"
	 */
	public static HapiType getStructureTypeDef(FhirContext ctx,
			Class<? extends IBaseResource> resourceClass, String structurePath) {
		BaseRuntimeElementCompositeDefinition<?> parentDef = ctx.getResourceDefinition(resourceClass);

		HapiType childType = null;
		for (Iterator<String> iter = Arrays.asList(structurePath.split("\\.")).iterator(); iter.hasNext();) {

			String nextPart = iter.next();

			BaseRuntimeChildDefinition child = parentDef.getChildByName(nextPart);
			BaseRuntimeElementDefinition<?> childDef = child.getChildByName(nextPart);

			if (iter.hasNext()) {
				parentDef = (BaseRuntimeElementCompositeDefinition<?>) childDef;
			} else {
				Class<? extends IBase> datatype = null;
				Class<? extends Enum<?>> enumerationType = null;

				if (child instanceof ca.uhn.fhir.context.RuntimeChildCompositeBoundDatatypeDefinition) {
					enumerationType = ((BaseRuntimeChildDatatypeDefinition) child).getBoundEnumType();
					datatype = ((BaseRuntimeChildDatatypeDefinition) child).getDatatype();
				} else if (child instanceof ca.uhn.fhir.context.RuntimeChildCompositeDatatypeDefinition) {
					datatype = ((BaseRuntimeChildDatatypeDefinition) child).getDatatype();
				} else if (child instanceof ca.uhn.fhir.context.RuntimeChildResourceBlockDefinition) {
					// Do nothing here
				} else if (child instanceof ca.uhn.fhir.context.RuntimeChildPrimitiveBoundCodeDatatypeDefinition) {
					enumerationType = ((BaseRuntimeChildDatatypeDefinition) child).getBoundEnumType();
					datatype = ((BaseRuntimeChildDatatypeDefinition) child).getDatatype();
				} else if (child instanceof ca.uhn.fhir.context.RuntimeChildPrimitiveDatatypeDefinition) {
					enumerationType = ((BaseRuntimeChildDatatypeDefinition) child).getBoundEnumType();
					datatype = ((BaseRuntimeChildDatatypeDefinition) child).getDatatype();
				} else if(child instanceof ca.uhn.fhir.context.RuntimeChildChoiceDefinition) {
					((RuntimeChildChoiceDefinition)child).getChoices();
					System.out.println("RuntimeChildChoiceDefinition found!!");
				} else {
					throw new RuntimeException("Unknown type " + child.getClass().getName() + " - " + structurePath);
				}
				childType = new HapiType(datatype, enumerationType);
			}
		}

		return childType;
	}
	
	/**
	 * 
	 * @param ctx
	 * @param resourceName
	 *            E.g., "Patient"
	 * @param structurePath
	 *            E.g., "address.line"
	 */
	public static List<HapiType> getChoiceTypes(FhirContext ctx,
			Class<? extends IBaseResource> resourceClass, String structurePath) {
		BaseRuntimeElementCompositeDefinition<?> parentDef = ctx.getResourceDefinition(resourceClass);

		List<HapiType> childrenType = new ArrayList<HapiType>();
		
		for (Iterator<String> iter = Arrays.asList(structurePath.split("\\.")).iterator(); iter.hasNext();) {

			String nextPart = iter.next();
			BaseRuntimeChildDefinition child = parentDef.getChildByName(nextPart);
			if(child == null) {
				System.out.println("HERE");
			}
			BaseRuntimeElementDefinition<?> childDef = child.getChildByName(nextPart);

			if (iter.hasNext()) {
				parentDef = (BaseRuntimeElementCompositeDefinition<?>) childDef;
			} else {
				Class<? extends IBase> datatype = null;
				Class<? extends Enum<?>> enumerationType = null;

				if (child instanceof RuntimeChildChoiceDefinition) {
					RuntimeChildChoiceDefinition choice = (RuntimeChildChoiceDefinition) child;
					List<Class<? extends IBase>> items = choice.getChoices();
					for(Class<? extends IBase> item : items) {
						boolean isReference = false;
						Class<? extends IBase> reference = null;
						String itemName = choice.getChildNameByDatatype(item);
						HapiType hapiType = null;
						if(itemName == null) {
							isReference = true;
							reference = org.hl7.fhir.dstu3.model.Reference.class;//Try this
							itemName = choice.getChildNameByDatatype(reference);
						}
						System.out.println("Processing multi " + structurePath + ", item name: " + itemName);
						if(isReference) {
							BaseRuntimeElementDefinition<?> definition = choice.getChildElementDefinitionByDatatype(reference);
							datatype = item;
							hapiType = new HapiType(datatype, null);
							hapiType.setReference(true);
							
						} else {
							BaseRuntimeElementDefinition<?> definition = choice.getChildElementDefinitionByDatatype(item);
							datatype = definition.getImplementingClass();
							hapiType = new HapiType(datatype, null);

						}
						hapiType.setAssignedName(itemName);
						childrenType.add(hapiType);
					}
				} else {
					throw new RuntimeException("Unknown type " + child.getClass().getName() + " - " + structurePath);
				}
			}
		}

		return childrenType;
	}
}
