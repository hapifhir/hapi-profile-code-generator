package ca.uhn.fhir.utils.codegen.hapi.dstu3;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hl7.fhir.dstu3.model.ElementDefinition;

import ca.uhn.fhir.utils.codegen.hapi.BaseMethodHandler;
import ca.uhn.fhir.utils.codegen.hapi.HapiType;
import ca.uhn.fhir.utils.codegen.hapi.MethodBodyGenerator;
import ca.uhn.fhir.utils.common.graph.Node;
import ca.uhn.fhir.utils.common.metamodel.Cardinality;
import ca.uhn.fhir.utils.common.metamodel.Method;
import ca.uhn.fhir.utils.fhir.PathUtils;
import ca.uhn.fhir.utils.fhir.model.datatype.dstu2.FhirDatatypeEnum;

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
            if (addUserDefinedStructureToParent()) {
                generateAccessorsForLogicalDatatypes(converter, methods);
            } else if(converter.isExtension()) {
                generateLogicalAccessors(converter, methods);
            } else {
                generateMethods(converter, methods);
            }
        } catch (Exception e) {
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
        if (types == null || types.isEmpty() || converter.getCardinality() == Cardinality.CONSTRAINED_OUT) {
            return;
        } else {
            if(converter.isMultiType()) {
                Method method = Method.constructNoArgMethod(Method.buildGetterName(converter.parseAttributeName()), "org.hl7.fhir.dstu3.model.Type");
                method.setBody(getTemplate().getAdapterGetMethodDelegationWithTryCatchBody(converter.parseAttributeName()));
                addMethod(methods, method);

                method = constructSetMethodFromField(converter.parseAttributeName(), "org.hl7.fhir.dstu3.model.Type", getParentType());
                method.setBody(getTemplate().getAdapterSetMethodDelegationBody(converter.parseAttributeName()));
                method.addImport("org.hl7.fhir.dstu3.model.Type");
                addMethod(methods, method);
            }
            if(converter.isReferenceMultiType() && !converter.isMultipleCardinality()) {
                Method method = Method.constructNoArgMethod(Method.buildGetterName(converter.parseAttributeName()), "org.hl7.fhir.dstu3.model.Reference");
                method.setBody(getTemplate().getAdapterGetMethodDelegationWithTryCatchBody(converter.parseAttributeName()));
                addMethod(methods, method);

                method = Method.constructNoArgMethod(Method.buildGetterName(converter.parseAttributeName() + "Target"), "org.hl7.fhir.dstu3.model.Resource");
                method.setBody(getTemplate().getAdapterGetMethodDelegationWithTryCatchBody(converter.parseAttributeName() + "Target"));
                addMethod(methods, method);
            }
            for (HapiType type : types) {
                if (type.getDatatype() == null && type.getGeneratedType() == null) {
                    System.out.println("Investigate");
                    //TODO Currently not handled: text, meta, references
                    continue;
                }
                if (type.isReference()) {
                    handleReferenceTypes(converter, type, methods);
                } else if (!type.isResource() && type.getGeneratedType() != null) {
                    handleGeneratedType(converter, type, methods);
                } else if (type.isBackboneElement()) {
                    handleDatatypeMethods(converter, type, methods);
                } else if (type.isEnumerationType()) {
                    handleEnumTypeMethods(converter, type, methods);
                } else {
                    FhirDatatypeEnum datatype = FhirDatatypeEnum.getEnumeratedDatatype(type.getFhirType());
                    if (datatype != null && datatype.isPrimitiveDatatype()) {
                        handlePrimitiveTypeMethods(converter, type, methods);
                        //TODO Create methods that return the equivalent java type
                    } else {
                        handleDatatypeMethods(converter, type, methods);
                    }
                }
            }
            if (converter.isMultiType()) {
                //TODO Add org.hl7.fhir.dstu3.model.Type method
            }
        }
    }

    public void handlePrimitiveTypeMethods(FhirToHapiTypeConverter converter, HapiType type, List<Method> methods) {
        String attributeName = converter.parseAttributeName();
        buildHasMethod(methods, attributeName);
        if (converter.isMultiType()) {
            //Getter
            Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName + PathUtils.getLastPathComponent(type.getDatatype())), type.getDatatypeOrList());
            method.setBody(getTemplate().getAdapterGetMethodDelegationWithTryCatchBody(attributeName + PathUtils.getLastPathComponent(type.getDatatype())));
            if (type.getDatatype() == null) {
                System.out.println("STOP HERE");
            }
            method.addImport(type.getDatatype());
            addMethod(methods, method);

            //Has method
            buildHasMethod(methods, attributeName, PathUtils.getLastPathComponent(type.getDatatype()));
        } else {
            buildHasMethod(methods, attributeName, BaseMethodHandler.ATTRIBUTE_NAME_ELEMENT_SUFFIX);
            //Getter that return HAPI FHIR datatypes
            Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName + BaseMethodHandler.ATTRIBUTE_NAME_ELEMENT_SUFFIX), type.getDatatypeOrList());
            if (converter.isExtension()) {
                method.setBody(getTemplate().getExtensionGetterBody("adaptedClass", type.getDatatype(), converter.getExtensionUri(), attributeName));
            } else {
                method.setBody(getTemplate().getAdapterGetMethodDelegationBody(attributeName + BaseMethodHandler.ATTRIBUTE_NAME_ELEMENT_SUFFIX));
            }
            if (type.getDatatype() == null) {
                System.out.println("STOP HERE");
            }
            method.addImport(type.getDatatype());
            addMethod(methods, method);

            //Getter that return primitive type
            method = Method.constructNoArgMethod(Method.buildGetterName(attributeName), type.getPrimitiveEquivalent());
            method.setBody(getTemplate().getAdapterGetMethodDelegationBody(attributeName));
            addMethod(methods, method);

            //Setter that take HAPI FHIR datatypes
            method = constructSetMethodFromField(attributeName + BaseMethodHandler.ATTRIBUTE_NAME_ELEMENT_SUFFIX, type.getDatatype(), getParentType());
            method.setBody(getTemplate().getAdapterSetMethodDelegationBody(attributeName + BaseMethodHandler.ATTRIBUTE_NAME_ELEMENT_SUFFIX));
            if (type.getDatatype() == null) {
                System.out.println("STOP HERE");
            } else {
                method.addImport(type.getDatatype());
                if (!methods.contains(method)) {
                    addMethod(methods, method);
                }
            }

            //Setter that take primitive type
            method = constructSetMethodFromField(attributeName, type.getPrimitiveEquivalent(), getParentType());
            method.setBody(getTemplate().getAdapterSetMethodDelegationBody(attributeName));
            method.addImport(type.getPrimitiveEquivalent());
            addMethod(methods, method);

            if(converter.isMultipleCardinality()) {
                method = constructAddMethod(attributeName + BaseMethodHandler.ATTRIBUTE_NAME_ELEMENT_SUFFIX, type.getDatatype(), getParentType());
                method.setBody(getTemplate().getAdapterSetMethodDelegationBody(attributeName + BaseMethodHandler.ATTRIBUTE_NAME_ELEMENT_SUFFIX));
                method.addImport(type.getDatatype());
                if (!methods.contains(method)) {
                    addMethod(methods, method);
                }
            }
        }
    }

    public void handleDatatypeMethods(FhirToHapiTypeConverter converter, HapiType type, List<Method> methods) {
        String attributeName = converter.parseAttributeName();
        if (converter.isMultiType()) {
            //Getter
            Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName + PathUtils.getLastPathComponent(type.getDatatype())), type.getDatatypeOrList());
            method.setBody(getTemplate().getAdapterGetMethodDelegationWithTryCatchBody(attributeName + PathUtils.getLastPathComponent(type.getDatatype())));
            if (type.getDatatype() == null) {
                System.out.println("STOP HERE");
            }
            method.addImport(type.getDatatype());
            method.addImport("org.hl7.fhir.dstu3.model.Enumerations"); //Todo fix this as it will result in a lot of duplication
            addMethod(methods, method);

            //Setter
            method = constructSetMethodFromField(attributeName, "org.hl7.fhir.dstu3.model.Type", getParentType());
            method.setBody(getTemplate().getAdapterSetMethodDelegationBody(attributeName));
            if (type.getDatatype() == null) {
                System.out.println("STOP HERE");
            } else {
                method.addImport("org.hl7.fhir.dstu3.model.Type");
                if (!methods.contains(method)) {
                    addMethod(methods, method);
                }
            }

            //Has
            buildHasMethod(methods, attributeName, PathUtils.getLastPathComponent(type.getDatatype()));

        } else {

            //Getter
            Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName), type.getDatatypeOrList());
            method.setBody(getTemplate().getAdapterGetMethodDelegationBody(attributeName));
            if (type.getDatatype() == null) {
                System.out.println("STOP HERE");
            } else {
                method.addImport(type.getDatatype());
                addMethod(methods, method);
            }

            //Setter
            if (!converter.isMultipleCardinality()) {//No setters on lists in HAPI at this time
                method = constructSetMethodFromField(attributeName, type.getDatatypeOrList(), getParentType());
                method.setBody(getTemplate().getAdapterSetMethodDelegationBody(attributeName));
                if (type.getDatatype() == null) {
                    System.out.println("STOP HERE");
                } else {
                    method.addImport(type.getDatatype());
                    addMethod(methods, method);
                }
            }

            //has
            buildHasMethod(methods, attributeName);

            if(converter.isMultipleCardinality()) {
                method = constructAddMethod(attributeName, type.getDatatype(), getParentType());
                method.setBody(getTemplate().getAddToListMethodBody("adaptedClass", attributeName));
                method.addImport(type.getDatatype());
                if (!methods.contains(method)) {
                    addMethod(methods, method);
                }
                if(attributeName.equalsIgnoreCase("contained")) {
                    //Do nothing since the method would have no idea what resource to return
                } else {
                    method = buildAddMethodDelegated(attributeName, type.getDatatype());
                    method.setBody(getTemplate().getAddToListMethodDelegatedBody_dstu3("adaptedClass", attributeName, type.getDatatype()));
                    method.addImport(type.getDatatype());
                    if (!methods.contains(method)) {
                        addMethod(methods, method);
                    }
                }
            }
        }
    }

    public void handleEnumTypeMethods(FhirToHapiTypeConverter converter, HapiType type, List<Method> methods) {
        String attributeName = converter.parseAttributeName();
        buildHasMethod(methods, attributeName);
        buildHasMethod(methods, attributeName, BaseMethodHandler.ATTRIBUTE_NAME_ELEMENT_SUFFIX);
        if (converter.isMultiType()) {
//			Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName + PathUtils.getLastPathComponent(type.getDatatype())), type.getDatatypeOrList());
//			method.setBody(getTemplate().getAdapterGetMethodDelegationWithTryCatchBody(attributeName + PathUtils.getLastPathComponent(type.getDatatype())));
//			if(type.getDatatype() == null) {
//				System.out.println("STOP HERE");
//			}
//			method.addImport(type.getDatatype());
//			addMethod(methods, method);
            throw new RuntimeException("Multi Enum Types Not implemented yet " + converter.getFullAttributePath());
        } else {
            //Getter style 1
            Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName), type.getEnumerationType());
            method.setBody(getTemplate().getAdapterGetMethodDelegationBody(attributeName));
            if (type.getDatatype() == null) {
                System.out.println("Enum datatype is null!");
            } else {
                method.addImport(type.getEnumerationType());
                addMethod(methods, method);
            }

            //Setter style 1
            method = constructSetMethodFromField(attributeName, type.getEnumerationType(), getParentType());
            method.setBody(getTemplate().getSetMethodDelegationBody(attributeName, "param"));
            if (type.getDatatype() == null) {
                System.out.println("Enum datatype is null!");
            } else {
                method.addImport(type.getEnumerationType());
                addMethod(methods, method);
            }

            //Getter style 2
            method = Method.constructNoArgMethod(Method.buildGetterName(attributeName + BaseMethodHandler.ATTRIBUTE_NAME_ELEMENT_SUFFIX), type.getDatatype() + "<" + type.getEnumerationType() + ">");
            method.setBody(getTemplate().getAdapterGetMethodDelegationBody(attributeName + BaseMethodHandler.ATTRIBUTE_NAME_ELEMENT_SUFFIX));
            if (type.getDatatype() == null) {
                System.out.println("Enum datatype is null!");
            } else {
                method.addImport(type.getDatatype());
                method.addImport(type.getEnumerationType());
                method.addImport("org.hl7.fhir.dstu3.model.Enumerations"); //Todo fix this as it will result in a lot of duplication
                addMethod(methods, method);
            }

            //Setter style 2
            method = constructSetMethodFromField(attributeName + BaseMethodHandler.ATTRIBUTE_NAME_ELEMENT_SUFFIX, type.getDatatype() + "<" + type.getEnumerationType() + ">", getParentType());
            method.setBody(getTemplate().getSetMethodDelegationBody(attributeName + BaseMethodHandler.ATTRIBUTE_NAME_ELEMENT_SUFFIX, "param"));
            if (type.getDatatype() == null) {
                System.out.println("Enum datatype is null!");
            } else {
                method.addImport(type.getEnumerationType());
                addMethod(methods, method);
            }
        }
    }

    public void handleGeneratedType(FhirToHapiTypeConverter converter, HapiType type, List<Method> methods) {
        String attributeName = converter.parseAttributeName();
        if (converter.isMultiType()) {
            //Getter
            throw new RuntimeException("Multi generated types should not exist. If so, this is not implemented");
        } else {
            //Getter that return HAPI FHIR datatypes
            Method method = Method.constructNoArgMethod(Method.buildGetterName("Wrapped" + StringUtils.capitalize(attributeName)), type.getGeneratedTypeOrList());
            method.setBody(getTemplate().getGeneratedClassMultiCardinalityGetter(type.getGeneratedType(), type.getDatatype(), StringUtils.capitalize(attributeName)));
            method.addImport(type.getGeneratedType());
            addMethod(methods, method);

            //Setter that take HAPI FHIR datatypes
            method = constructSetMethodFromField("Wrapped" + StringUtils.capitalize(attributeName), type.getGeneratedTypeOrList(), getParentType());
            method.setBody(getTemplate().getGeneratedClassMultiCardinalitySetter(type.getGeneratedType(), type.getDatatype(), StringUtils.capitalize(attributeName)));
            method.addImport(type.getGeneratedType());
            if (!methods.contains(method)) {
                addMethod(methods, method);
            }

            if(converter.isMultipleCardinality()) {
                method = constructAddMethod("Wrapped" + StringUtils.capitalize(attributeName), type.getGeneratedType(), getParentType());
                method.setBody(getTemplate().addWrappedTypeToListMethodDelegatedBody(attributeName));
                method.addImport(type.getDatatype());
                if (!methods.contains(method)) {
                    addMethod(methods, method);
                }
                if(attributeName.equalsIgnoreCase("contained")) {
                    //Do nothing since the method would have no idea what resource to return
                } else {
                    method = buildAddMethodDelegated("Wrapped" + StringUtils.capitalize(attributeName), type.getGeneratedType());
                    method.setBody(getTemplate().addWrappedTypeToListMethodBody(type.getGeneratedType(), type.getDatatype(), attributeName));
                    method.addImport(type.getDatatype());
                    if (!methods.contains(method)) {
                        addMethod(methods, method);
                    }
                }
            }

            //Now generate the standard datatype methods
            handleDatatypeMethods(converter, type, methods);
        }
    }

    public void handleReferenceTypes(FhirToHapiTypeConverter converter, HapiType type, List<Method> methods) {
        String attributeName = converter.parseAttributeName();
        String disambiguationGetterSuffix = "";
        if (converter.isReferenceMultiType()) {
            disambiguationGetterSuffix = PathUtils.getLastPathComponent(type.getDatatype());
        }
        buildHasMethod(methods, attributeName);
        if (converter.isMultipleCardinality()) {

            if(converter.isReferenceMultiType()) {
                //Getter
                Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName + disambiguationGetterSuffix) + "Target", "java.util.List<" + type.getDatatype() + ">");
                method.setBody(getTemplate().getReferenceListAsTypedList("adaptedClass", attributeName + "Target", type.getDatatype()));
                method.addImport(type.getGeneratedType());
                addMethod(methods, method);
            } else {
                Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName + "Target"), "java.util.List<" + type.getDatatype() + ">");
                method.setBody(getTemplate().getAdapterGetMethodDelegationBody(attributeName));
                method.addImport(type.getDatatype());
            }

            //Getter
            Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName), "java.util.List<org.hl7.fhir.dstu3.model.Reference>");
            method.setBody(getTemplate().getAdapterGetMethodDelegationBody(attributeName));
            method.addImport("org.hl7.fhir.dstu3.model.Reference");
            addMethod(methods, method);
        } else {
            if(converter.isMultiType()) {
                //Getter
                Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName + "Reference"), "org.hl7.fhir.dstu3.model.Reference");
                method.setBody(getTemplate().getAdapterGetMethodDelegationWithTryCatchBody(attributeName +  "Reference"));
                method.addImport(type.getDatatype());
                method.addImport("org.hl7.fhir.dstu3.model.Reference"); //Todo fix this as it will result in a lot of duplication
                addMethod(methods, method);

                //Getter
                method = Method.constructNoArgMethod(Method.buildGetterName(attributeName + disambiguationGetterSuffix) + "Target", type.getDatatype());
                method.setBody(getTemplate().castTypeToReferenceAndReturnTarget(attributeName, type.getDatatype()));
                method.addImport(type.getDatatype());
                addMethod(methods, method);

                //Setter
                method = constructSetMethodFromField(attributeName, "org.hl7.fhir.dstu3.model.Reference", getParentType());
                method.setBody(getTemplate().getAdapterSetMethodDelegationBody(attributeName));
                method.addImport(type.getDatatype());
                addMethod(methods, method);

                //Setter
                method = constructSetMethodFromField(attributeName + "Target", type.getDatatype(), getParentType());
                method.setBody(getTemplate().wrapResourceInReferenceAndSet(attributeName));
                method.addImport(type.getDatatype());
                addMethod(methods, method);

            } else {
                //Getter
                Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName + disambiguationGetterSuffix), "org.hl7.fhir.dstu3.model.Reference");
                method.setBody(getTemplate().getAdapterGetMethodDelegationBody(attributeName));
                addMethod(methods, method);

                //Setter
                method = constructSetMethodFromField(attributeName, "org.hl7.fhir.dstu3.model.Reference", getParentType());
                method.setBody(getTemplate().getAdapterSetMethodDelegationBody(attributeName));
                addMethod(methods, method);

                if(type.getDatatype() != null) {
                    //Getter
                    method = Method.constructNoArgMethod(Method.buildGetterName(attributeName + disambiguationGetterSuffix) + "Target", type.getDatatype());
                    method.setBody(getTemplate().getAdapterGetWithCastMethodDelegationBody(attributeName + "Target", type.getDatatype()));
                    method.addImport(type.getDatatype());
                    addMethod(methods, method);

                    //Setter
                    method = constructSetMethodFromField(attributeName + "Target", type.getDatatype(), getParentType());
                    method.setBody(getTemplate().getAdapterSetMethodDelegationBody(attributeName + "Target"));
                    method.addImport(type.getDatatype());
                    addMethod(methods, method);
                }

                if(type.getGeneratedType() != null) {
                    //Getter
                    method = Method.constructNoArgMethod(Method.buildGetterName(attributeName + disambiguationGetterSuffix) + "AdapterTarget", type.getGeneratedType());
                    method.setBody(getTemplate().getProfiledReferenceGetterBody(attributeName, type.getGeneratedType(), type.getDatatype()));
                    method.addImport(type.getDatatype());
                    addMethod(methods, method);

                    //Setter
                    method = constructSetMethodFromField(attributeName + "AdapterTarget", type.getGeneratedType(), getParentType());
                    method.setBody(getTemplate().getProfiledReferenceSetterBody_dstu3(attributeName + "Target"));
                    method.addImport(type.getDatatype());
                    addMethod(methods, method);

                }
            }
        }
    }

    public void generateLogicalAccessors(FhirToHapiTypeConverter converter, List<Method> methods) {
        List<HapiType> types = converter.getHapiTypes();
        String attributeName = converter.parseAttributeName();
        if (types == null || types.isEmpty() || converter.getCardinality() == Cardinality.CONSTRAINED_OUT) {
            return;
        } else {
            boolean isMultitype = converter.isMultiType();
            String adaptedClass = "adaptedClass";
            if(isExtensionStructure()) {
                adaptedClass = getExtensionStructureAttributeName();
            }
            for (HapiType type : types) {
                //Some prep for multi-type field to disambiguate getter signatures
                String disambiguatingSuffix = "";
                if (isMultitype) {
                    disambiguatingSuffix = StringUtils.capitalize(type.getDatatypeClass().getSimpleName());
                }
                if (converter.isMultipleCardinality()) {//No setters on lists in HAPI at this time
                    //Getter
                    Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName + disambiguatingSuffix), type.getDatatypeOrList());
                    if (type.isResource()) {
                        method.setBody(getTemplate().getExtensionListGetterBodyResourceDstu3(type.getDatatype(), converter.getExtensionUri()));
                    } else {
                        method.setBody(getTemplate().getExtensionListGetterBodyDstu3(type.getDatatype(), converter.getExtensionUri()));
                    }
                    if (type.getDatatype() == null) {
                        System.out.println("STOP HERE");
                    } else {
                        method.addImport(type.getDatatype());
                        method.addImport("java.util.List");
                        addMethod(methods, method);
                    }

                    //Setter
                    method = constructSetMethodFromField(attributeName, type.getDatatypeOrList(), getParentType());
                    if (type.isResource()) {
                        method.setBody(getTemplate().getExtensionListSetterBodyResourceDstu3(converter.getExtensionUri()));
                    } else {
                        method.setBody(getTemplate().getExtensionListSetterBodyDstu3(type.getDatatype(), converter.getExtensionUri()));
                    }
                    if (type.getDatatype() == null) {
                        System.out.println("STOP HERE");
                    } else {
                        method.addImport(type.getDatatype());
                        method.addImport("java.util.List");
                        addMethod(methods, method);
                    }
                } else {
                    //Getter
                    Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName + disambiguatingSuffix), type.getDatatypeOrList());
                    if (type.isResource()) {
                        method.setBody(getTemplate().getExtensionGetterBodyResourceDstu3(adaptedClass, type.getDatatype(), converter.getExtensionUri(), attributeName));
                    } else {
                        method.setBody(getTemplate().getExtensionGetterBodyDstu3(adaptedClass, type.getDatatype(), converter.getExtensionUri(), attributeName));
                    }
                    if (type.getDatatype() == null) {
                        System.out.println("STOP HERE");
                    } else {
                        method.addImport(type.getDatatype());
                        method.addImport("java.util.List");
                        addMethod(methods, method);
                    }

                    //Setter
                    method = constructSetMethodFromField(attributeName, type.getDatatypeOrList(), getParentType());
                    if (type.isResource()) {
                        method.setBody(getTemplate().getExtensionSetterBodyResourceDstu3(adaptedClass, converter.getExtensionUri()));
                    } else {
                        method.setBody(getTemplate().getExtensionSetterBodyDstu3(adaptedClass, converter.getExtensionUri()));
                    }
                    if (type.getDatatype() == null) {
                        System.out.println("STOP HERE");
                    } else {
                        method.addImport(type.getDatatype());
                        method.addImport("java.util.List");
                        addMethod(methods, method);
                    }
                }
            }
        }
    }

    public void generateAccessorsForLogicalDatatypes(FhirToHapiTypeConverter converter, List<Method> methods) {
        HapiType type = converter.getHapiType();
        String attributeName = converter.parseAttributeName();
        if (type.getGeneratedType() == null || converter.getCardinality() == Cardinality.CONSTRAINED_OUT) {
            return;
        } if (converter.isMultipleCardinality()) {
            //Getter
            Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName), "java.util.List<" + type.getGeneratedType() + ">");
            method.setBody(getTemplate().getUserDefinedExtensionTypeGetterBody_dstu3(type.getGeneratedType(), getUserDefinedStructureExtensionURL()));
            method.addImport(type.getDatatype());
            method.addImport("java.util.List");
            addMethod(methods, method);

            //Setter
            method = constructSetMethodFromField(attributeName, "java.util.List<" + type.getGeneratedType() + ">", getParentType());
            method.setBody(getTemplate().getUserDefinedExtensionTypeSetterBody_dstu3(type.getGeneratedType()));
            method.addImport(type.getDatatype());
            method.addImport("java.util.List");
            addMethod(methods, method);
        } else {
            throw new RuntimeException("Single cardinality user defined structures are not supported yet");
        }
    }

}
