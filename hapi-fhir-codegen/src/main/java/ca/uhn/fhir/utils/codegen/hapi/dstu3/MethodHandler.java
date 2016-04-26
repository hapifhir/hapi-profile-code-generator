package ca.uhn.fhir.utils.codegen.hapi.dstu3;

import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.dstu3.model.ElementDefinition;

import ca.uhn.fhir.utils.codegen.hapi.BaseMethodHandler;
import ca.uhn.fhir.utils.codegen.hapi.HapiType;
import ca.uhn.fhir.utils.codegen.hapi.MethodBodyGenerator;
import ca.uhn.fhir.utils.common.graph.Node;
import ca.uhn.fhir.utils.common.metamodel.Cardinality;
import ca.uhn.fhir.utils.common.metamodel.Method;
import ca.uhn.fhir.utils.fhir.PathUtils;
import ca.uhn.fhir.utils.fhir.model.datatype.dstu2.FhirDatatypeEnum;
import org.hl7.fhir.dstu3.model.Extension;
import org.hl7.fhir.dstu3.model.Patient;

public class MethodHandler extends BaseMethodHandler {
	
	private Node<ElementDefinition> node;
	private FhirResourceManagerDstu3 manager;
	
	public MethodHandler(FhirResourceManagerDstu3 manager, MethodBodyGenerator template, Node<ElementDefinition> node) {
		super(template);
		this.node = node;
		this.manager = manager;
	}
	
	public List<Method> generateMethods() {
		List<Method> methods = new ArrayList<Method>();
		try {
			FhirToHapiTypeConverter converter = createConverter();
			if(converter.isExtension()) {
				generateLogicalAccessors(converter, methods);
			} else {
				generateMethods(converter, methods);
			}
		} catch(Exception e) {
			e.printStackTrace();
			//Investigate
		}
		return methods;
	}
	
	public FhirToHapiTypeConverter createConverter() {
		ElementDefinition element = node.getPayload();
		FhirToHapiTypeConverter converter = new FhirToHapiTypeConverter(manager, element);
		return converter;
	}
	
	public void generateMethods(FhirToHapiTypeConverter converter, List<Method> methods) {
		List<HapiType> types = converter.getHapiTypes();
		if(types == null || types.isEmpty() || converter.getCardinality() == Cardinality.CONSTRAINED_OUT) {
			return;
		} else {
			for(HapiType type: types) {
				if(type.isBackboneElement()) {
					
				} else if(type.isEnumerationType()) {
					handleEnumTypeMethods(converter, type, methods);
				} else if(type.isReference()) {
					
				} else {
					FhirDatatypeEnum datatype = FhirDatatypeEnum.getEnumeratedDatatype(type.getFhirType());
					if(datatype != null && datatype.isPrimitiveDatatype()) {
						handlePrimitiveTypeMethods(converter, type, methods);
						//TODO Create methods that return the equivalent java type
					} else {
						handleDatatypeMethods(converter, type, methods);
					}
				}
			}
			if(converter.isMultiType()) {
				//TODO Add org.hl7.fhir.dstu3.model.Type method
			}
		}
	}
	
	public void handlePrimitiveTypeMethods(FhirToHapiTypeConverter converter, HapiType type, List<Method> methods) {
		String attributeName = parseAttributeName(converter.getFullAttributePath());
		if(converter.isMultiType()) {
			//Getter
			Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName + PathUtils.getLastPathComponent(type.getDatatype())), type.getDatatypeOrList());
			method.setBody(getTemplate().getAdapterGetMethodDelegationWithTryCatchBody(attributeName + PathUtils.getLastPathComponent(type.getDatatype())));
			if(type.getDatatype() == null) {
				System.out.println("STOP HERE");
			}
			method.addImport(type.getDatatype());
			methods.add(method);
		} else {
			//Getter that return HAPI FHIR datatypes
			Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName + BaseMethodHandler.ATTRIBUTE_NAME_ELEMENT_SUFFIX), type.getDatatypeOrList());
			if(converter.isExtension()) {
				method.setBody(getTemplate().getExtensionGetterBody("adaptedClass", type.getDatatype(), converter.getExtensionUri(), attributeName));
			} else {
				method.setBody(getTemplate().getAdapterGetMethodDelegationBody(attributeName + BaseMethodHandler.ATTRIBUTE_NAME_ELEMENT_SUFFIX));
			}
			if(type.getDatatype() == null) {
				System.out.println("STOP HERE");
			}
			method.addImport(type.getDatatype());
			methods.add(method);

			//Setter that take HAPI FHIR datatypes
			method = constructSetMethodFromField(attributeName + BaseMethodHandler.ATTRIBUTE_NAME_ELEMENT_SUFFIX, type.getDatatype(), getParentType());
			method.setBody(getTemplate().getAdapterSetMethodDelegationBody(attributeName + BaseMethodHandler.ATTRIBUTE_NAME_ELEMENT_SUFFIX));
			if (type.getDatatype() == null) {
				System.out.println("STOP HERE");
			} else {
				method.addImport(type.getDatatype());
				if(!methods.contains(method)) {
					methods.add(method);
				}
			}
		}
	}
	
	public void handleDatatypeMethods(FhirToHapiTypeConverter converter, HapiType type, List<Method> methods) {
		String attributeName = parseAttributeName(converter.getFullAttributePath());
		if(converter.isMultiType()) {
			//Getter
			Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName + PathUtils.getLastPathComponent(type.getDatatype())), type.getDatatypeOrList());
			method.setBody(getTemplate().getAdapterGetMethodDelegationWithTryCatchBody(attributeName + PathUtils.getLastPathComponent(type.getDatatype())));
			if(type.getDatatype() == null) {
				System.out.println("STOP HERE");
			}
			method.addImport(type.getDatatype());
			method.addImport("org.hl7.fhir.dstu3.model.Enumerations"); //Todo fix this as it will result in a lot of duplication
			methods.add(method);

			//Setter
			method = constructSetMethodFromField(attributeName, "org.hl7.fhir.dstu3.model.Type", getParentType());
			method.setBody(getTemplate().getAdapterSetMethodDelegationBody(attributeName));
			if (type.getDatatype() == null) {
				System.out.println("STOP HERE");
			} else {
				method.addImport("org.hl7.fhir.dstu3.model.Type");
				if(!methods.contains(method)) {
					methods.add(method);
				}
			}
		} else {

			//Getter
			Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName), type.getDatatypeOrList());
			method.setBody(getTemplate().getAdapterGetMethodDelegationBody(attributeName));
			if(type.getDatatype() == null) {
				System.out.println("STOP HERE");
			} else {
				method.addImport(type.getDatatype());
				methods.add(method);
			}

			//Setter
			if(!converter.isMultipleCardinality()) {//No setters on lists in HAPI at this time
				method = constructSetMethodFromField(attributeName, type.getDatatypeOrList(), getParentType());
				method.setBody(getTemplate().getAdapterSetMethodDelegationBody(attributeName));
				if (type.getDatatype() == null) {
					System.out.println("STOP HERE");
				} else {
					method.addImport(type.getDatatype());
					methods.add(method);
				}
			}
		}
	}
	
	public void handleEnumTypeMethods(FhirToHapiTypeConverter converter, HapiType type, List<Method> methods) {
		String attributeName = parseAttributeName(converter.getFullAttributePath());
		if(converter.isMultiType()) {
//			Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName + PathUtils.getLastPathComponent(type.getDatatype())), type.getDatatypeOrList());
//			method.setBody(getTemplate().getAdapterGetMethodDelegationWithTryCatchBody(attributeName + PathUtils.getLastPathComponent(type.getDatatype())));
//			if(type.getDatatype() == null) {
//				System.out.println("STOP HERE");
//			}
//			method.addImport(type.getDatatype());
//			methods.add(method);
			throw new RuntimeException("Multi Enum Types Not implemented yet " + converter.getFullAttributePath());
		} else {
			//Getter style 1
			Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName), type.getEnumerationType());
			method.setBody(getTemplate().getAdapterGetMethodDelegationBody(attributeName));
			if(type.getDatatype() == null) {
				System.out.println("Enum datatype is null!");
			} else {
				method.addImport(type.getEnumerationType());
				methods.add(method);
			}

			//Setter style 1
			method = constructSetMethodFromField(attributeName, type.getEnumerationType(), getParentType());
			method.setBody(getTemplate().getSetMethodDelegationBody(attributeName, "param"));
			if(type.getDatatype() == null) {
				System.out.println("Enum datatype is null!");
			} else {
				method.addImport(type.getEnumerationType());
				methods.add(method);
			}

			//Getter style 2
			method = Method.constructNoArgMethod(Method.buildGetterName(attributeName + BaseMethodHandler.ATTRIBUTE_NAME_ELEMENT_SUFFIX), type.getDatatype() + "<" + type.getEnumerationType() + ">");
			method.setBody(getTemplate().getAdapterGetMethodDelegationBody(attributeName + BaseMethodHandler.ATTRIBUTE_NAME_ELEMENT_SUFFIX));
			if(type.getDatatype() == null) {
				System.out.println("Enum datatype is null!");
			} else {
				method.addImport(type.getDatatype());
				method.addImport(type.getEnumerationType());
				method.addImport("org.hl7.fhir.dstu3.model.Enumerations"); //Todo fix this as it will result in a lot of duplication
				methods.add(method);
			}

			//Setter style 2
			method = constructSetMethodFromField(attributeName + BaseMethodHandler.ATTRIBUTE_NAME_ELEMENT_SUFFIX, type.getDatatype() + "<" + type.getEnumerationType() + ">", getParentType());
			method.setBody(getTemplate().getSetMethodDelegationBody(attributeName + BaseMethodHandler.ATTRIBUTE_NAME_ELEMENT_SUFFIX, "param"));
			if(type.getDatatype() == null) {
				System.out.println("Enum datatype is null!");
			} else {
				method.addImport(type.getEnumerationType());
				methods.add(method);
			}
		}
	}
	
	public void generateLogicalAccessors(FhirToHapiTypeConverter converter, List<Method> methods) {
		List<HapiType> types = converter.getHapiTypes();
		String attributeName = parseAttributeName(converter.getFullAttributePath());
		if(types == null || types.isEmpty() || converter.getCardinality() == Cardinality.CONSTRAINED_OUT) {
			return;
		} else {
			for (HapiType type : types) {
				if(!converter.isMultipleCardinality()) {//No setters on lists in HAPI at this time
					//Getter
					Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName), type.getDatatypeOrList());
					method.setBody(getTemplate().getExtensionGetterBodyDstu3("adaptedClass", type.getDatatype(), converter.getExtensionUri(), attributeName));
					if (type.getDatatype() == null) {
						System.out.println("STOP HERE");
					} else {
						method.addImport(type.getDatatype());
						methods.add(method);
					}

					//Setter
					method = constructSetMethodFromField(attributeName, type.getDatatypeOrList(), getParentType());
					method.setBody(getTemplate().getExtensionSetterBodyDstu3("adaptedClass", converter.getExtensionUri()));
					if (type.getDatatype() == null) {
						System.out.println("STOP HERE");
					} else {
						method.addImport(type.getDatatype());
						methods.add(method);
					}
				} else {
					//Getter
					Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName), type.getDatatypeOrList());
					method.setBody(getTemplate().getExtensionListGetterBodyDstu3(type.getDatatype(), converter.getExtensionUri()));
					if (type.getDatatype() == null) {
						System.out.println("STOP HERE");
					} else {
						method.addImport(type.getDatatype());
						methods.add(method);
					}

					//Setter
					method = constructSetMethodFromField(attributeName, type.getDatatypeOrList(), getParentType());
					method.setBody(getTemplate().getExtensionListSetterBodyDstu3(type.getDatatype(), converter.getExtensionUri()));
					if (type.getDatatype() == null) {
						System.out.println("STOP HERE");
					} else {
						method.addImport(type.getDatatype());
						methods.add(method);
					}
				}
			}
		}
	}

}
