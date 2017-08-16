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
import org.apache.commons.lang3.StringUtils;
import org.hl7.fhir.dstu3.model.ElementDefinition;

import java.util.ArrayList;
import java.util.List;

public class MethodHandler extends BaseMethodHandler {

    private Node<ElementDefinition> node;
    private FhirResourceManagerDstu3 manager;
    private final String generatedCodePackage;

    public MethodHandler(FhirResourceManagerDstu3 manager, MethodBodyGenerator template, Node<ElementDefinition> node, String generatedCodePackage) {
        super(template);
        this.node = node;
        this.manager = manager;
        this.generatedCodePackage = generatedCodePackage;
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
        FhirToHapiTypeConverter converter = new FhirToHapiTypeConverter(manager, element, generatedCodePackage);
        return converter;
    }

    public void generateMethods(FhirToHapiTypeConverter converter, List<Method> methods) {
        List<HapiType> types = converter.getHapiTypes();
        System.out.println("Attribute path:" + converter.getFullAttributePath());

        if ("Immunization.doseQuantity".equals(converter.getFullAttributePath())) {
            System.out.println("Start Debugging");
        }
        if (types == null || types.isEmpty() || converter.getCardinality() == Cardinality.CONSTRAINED_OUT) {
            return;
        } else {
            if(converter.isMultiType()) {
//                Method method = Method.constructNoArgMethod(Method.buildGetterName(converter.parseAttributeName()), "org.hl7.fhir.dstu3.model.Type");
//                method.setBody(getTemplate().getAdapterGetMethodDelegationWithTryCatchBody(converter.parseAttributeName()));
//                addMethod(methods, method);
                buildGetterMethod(methods, converter.parseAttributeName(), "org.hl7.fhir.dstu3.model.Type", null, false, null);

//                method = constructSetMethodSignature(converter.parseAttributeName(), "org.hl7.fhir.dstu3.model.Type", getParentType());
//                method.setBody(getTemplate().getAdapterSetMethodDelegationBody(converter.parseAttributeName()));
//                method.addImport("org.hl7.fhir.dstu3.model.Type");
//                addMethod(methods, method);
                buildSetterMethod(methods, converter.parseAttributeName(), "org.hl7.fhir.dstu3.model.Type", getParentType());
            }
            if(converter.isReferenceMultiType() && !converter.isMultipleCardinality() && !converter.isMultiType()) {
//                Method method = Method.constructNoArgMethod(Method.buildGetterName(converter.parseAttributeName()), "org.hl7.fhir.dstu3.model.Reference");
//                method.setBody(getTemplate().getAdapterGetMethodDelegationWithTryCatchBody(converter.parseAttributeName()));
//                addMethod(methods, method);
                buildGetterMethod(methods, converter.parseAttributeName(), "org.hl7.fhir.dstu3.model.Reference", null, false, null);

//                method = Method.constructNoArgMethod(Method.buildGetterName(converter.parseAttributeName() + "Target"), "org.hl7.fhir.dstu3.model.Resource");
//                method.setBody(getTemplate().getAdapterGetMethodDelegationWithTryCatchBody(converter.parseAttributeName() + "Target"));
//                addMethod(methods, method);
                buildGetterMethod(methods, converter.parseAttributeName() + "Target", "org.hl7.fhir.dstu3.model.Resource", null, false, null);
            }
            for (HapiType type : types) {
                if (type.getDatatype() == null && type.getGeneratedType() == null) {
                    System.out.println("Investigate : " + converter.getFullAttributePath());
                    //TODO Currently not handled: text, meta, references
                    continue;
                }
                if (type.isReference()) {
                    handleReferenceTypes(converter, type, methods);
                } else if (!type.isResource() && type.getGeneratedType() != null) {
                    handleExtendedDatatypes(converter, type, methods);
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
        if (converter.isMultiType()) {
            handlePrimitiveMultiType(converter, type, methods, attributeName);//TODO Path never appears to be visited. Consider removing.
        } else {
            if(converter.isMultipleCardinality()) {
                handlePrimitiveListType(converter, type, methods, attributeName);
            } else {
                handlePrimitiveType(converter, type, methods, attributeName);
            }
        }
    }

    private void handlePrimitiveMultiType(FhirToHapiTypeConverter converter, HapiType type, List<Method> methods, String attributeName) {
        buildHasMethod(methods, attributeName);
        buildHasMethod(methods, attributeName, PathUtils.getLastPathComponent(type.getDatatype()));
        buildGetterMethod(methods, attributeName + PathUtils.getLastPathComponent(type.getDatatype()), type.getDatatypeOrList(), type.getDatatype(), converter.isExtension(), converter.getExtensionUri());
    }

    /**
     * Method constructs the six HAPI FHIR primitive list type methods. If thingy is a field in ContainerAdapter, the methods have the following signature:
     * <ol>
     *     <li>public boolean hasThingy()</li>
     *     <li>public boolean hasThingyElement()</li>
     *     <li>public PrimitiveType getThingy()</li>
     *     <li>public FhirPrimitiveType getThingyElement()</li>
     *     <li>public ContainerAdapter setThingy(PrimitiveType thingy)</li>
     *     <li>public ContainerAdapter setThingyElement(FhirPrimitiveType thingy)</li>
     * </ol>
     * Each method delegates to the underlying HAPI FHIR type.
     *
     * @param converter
     * @param type
     * @param methods
     * @param attributeName
     */
    private void handlePrimitiveType(FhirToHapiTypeConverter converter, HapiType type, List<Method> methods, String attributeName) {
        buildHasMethod(methods, attributeName);
        buildHasMethod(methods, attributeName, BaseMethodHandler.ATTRIBUTE_NAME_ELEMENT_SUFFIX);
        buildGetterMethod(methods, attributeName + BaseMethodHandler.ATTRIBUTE_NAME_ELEMENT_SUFFIX, type.getDatatypeOrList(), type.getDatatype(), converter.isExtension(), converter.getExtensionUri());
        buildGetterMethod(methods, attributeName, type.getPrimitiveEquivalent(), type.getDatatype(), converter.isExtension(), converter.getExtensionUri());
        buildSetterMethod(methods, attributeName + BaseMethodHandler.ATTRIBUTE_NAME_ELEMENT_SUFFIX, type.getDatatype(), getParentType());
        buildSetterMethod(methods, attributeName, type.getPrimitiveEquivalent(), getParentType());
    }

    /**
     * Method constructs the six HAPI FHIR primitive list type methods. If thingy is a field in ContainerAdapter, the methods have the following signature:
     * <ol>
     *     <li>public boolean hasThingy()</li>
     *     <li>public boolean hasThingy(PrimitiveType thingy)</li>
     *     <li>public ContainerAdapter addThingy(PrimitiveType thingy)</li>
     *     <li>public FhirPrimitiveType addThingyElement()</li>
     *     <li>public List&lt;FhirPrimitiveType&gt; getThingy()</li>
     *     <li>public ContainerAdapter setThingy(List&lt;FhirPrimitiveType&gt; listOfThingies)</li>
     * </ol>
     * Each method delegates to the underlying HAPI FHIR type.
     *
     * @param converter
     * @param type
     * @param methods
     * @param attributeName
     */
    private void handlePrimitiveListType(FhirToHapiTypeConverter converter, HapiType type, List<Method> methods, String attributeName) {
        buildHasMethod(methods, attributeName);
        buildHasMethodWithArgument(methods, attributeName, "", type.getPrimitiveEquivalent());
        buildAddMethod(methods, attributeName, type.getPrimitiveEquivalent(), getParentType());
        buildFluentAddMethod(methods, attributeName + BaseMethodHandler.ATTRIBUTE_NAME_ELEMENT_SUFFIX, type.getDatatype());
        buildListGetterMethod(methods, attributeName, type.getDatatypeOrList(), type.getDatatype(), converter.isExtension(), converter.getExtensionUri());
        buildListSetterMethod(methods, attributeName, type.getDatatypeOrList(), type.getDatatype(), getParentType());
    }

    public void handleDatatypeMethods(FhirToHapiTypeConverter converter, HapiType type, List<Method> methods) {
        String attributeName = converter.parseAttributeName();
        if(attributeName.equalsIgnoreCase("period")) {
            System.out.println("STOP HERE");
        }
        if (converter.isMultiType()) {
            //Getter
//            Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName + PathUtils.getLastPathComponent(type.getDatatype())), type.getDatatypeOrList());
//            method.setBody(getTemplate().getAdapterGetMethodDelegationWithTryCatchBody(attributeName + PathUtils.getLastPathComponent(type.getDatatype())));
//            method.addImport(type.getDatatype());
//            method.addImport("org.hl7.fhir.dstu3.model.Enumerations"); //Todo fix this as it will result in a lot of duplication
//            addMethod(methods, method);
            buildGetterMethod(methods, attributeName + PathUtils.getLastPathComponent(type.getDatatype()), type.getDatatypeOrList(), type.getDatatype() , false, null);

            //Setter
//            method = constructSetMethodSignature(attributeName, "org.hl7.fhir.dstu3.model.Type", getParentType());
//            method.setBody(getTemplate().getAdapterSetMethodDelegationBody(attributeName));
//            method.addImport("org.hl7.fhir.dstu3.model.Type");
//            if (!methods.contains(method)) {
//                addMethod(methods, method);
//            }
            buildSetterMethod(methods, attributeName, "org.hl7.fhir.dstu3.model.Type", getParentType());

            //Has
            buildHasMethod(methods, attributeName, PathUtils.getLastPathComponent(type.getDatatype()));

        } else {
            handleFhirDatatype(converter, type, methods, attributeName);

            if(converter.isMultipleCardinality()) {
//                Method method = constructAddMethodSignature(attributeName, type.getDatatype(), getParentType());
//                method.setBody(getTemplate().getAddToListMethodBody("adaptedClass", attributeName));
//                method.addImport(type.getDatatype());
//                if (!methods.contains(method)) {
//                    addMethod(methods, method);
//                }
                buildAddMethod(methods, attributeName, type.getDatatype(), getParentType());
                if(attributeName.equalsIgnoreCase("contained")) {
                    //Do nothing since the method would have no idea what resource to return
                } else {
//                    method = buildAddMethodDelegated(attributeName, type.getDatatype());
//                    method.setBody(getTemplate().getAddToListMethodDelegatedBody_dstu3("adaptedClass", attributeName, type.getDatatype()));
//                    method.addImport(type.getDatatype());
//                    if (!methods.contains(method)) {
//                        addMethod(methods, method);
//                    }
                    buildFluentAddMethod(methods, attributeName, type.getDatatype());
                }
            }
        }
    }

    /**
     * Method constructs the three HAPI FHIR datatype methods. If thingy is a field in ContainerAdapter, the methods have the following signature:
     * <ol>
     *     <li>public boolean hasThingy()</li>
     *     <li>public Datatype getThingy()</li>
     *     <li>public ContainerAdapter setThingy(Datatype thingy)</li>
     * </ol>
     * Each method delegates to the underlying HAPI FHIR type.
     *
     * @param converter
     * @param type
     * @param methods
     * @param attributeName
     */
    private void handleFhirDatatype(FhirToHapiTypeConverter converter, HapiType type, List<Method> methods, String attributeName) {
        if (attributeName.equalsIgnoreCase("name")) {
            System.out.println(attributeName);
        }
        Boolean cadinilityChangedToRequiredSingleValue = converter.getElement().getBase().getMax().equals("*") && converter.getElement().getMax().equals("1");
        ;
//Noman 3
        if (cadinilityChangedToRequiredSingleValue) {
            buildGetterMethod(methods, attributeName, type.getDatatypeOrList(), type.getDatatype(), converter.isExtension(), converter.getExtensionUri(), cadinilityChangedToRequiredSingleValue);
            buildSetterMethod(methods, attributeName, type.getDatatypeOrList(), getParentType(), cadinilityChangedToRequiredSingleValue);
            buildAddMethod(methods, attributeName, type.getDatatypeOrList(), getParentType());
        } else {
            buildGetterMethod(methods, attributeName, type.getDatatypeOrList(), type.getDatatype(), converter.isExtension(), converter.getExtensionUri());
            buildSetterMethod(methods, attributeName, type.getDatatypeOrList(), getParentType());
        }
        buildHasMethod(methods, attributeName);
    }

    private Boolean cadinilityChangedToRequiredSingleValue(FhirToHapiTypeConverter converter) {
        return converter.getElement().getBase().getMax().equals("*") && converter.getElement().getMax().equals("1");
    }

    /**
     * Method constructs the six HAPI FHIR enumeration type methods. If thingy is an enumerated field in
     * ContainerAdapter, the methods have the following signature:
     *
     * <ol>
     *     <li>public boolean hasThingy()</li>
     *     <li>public boolean hasThingy(PrimitiveType thingy)</li>
     *     <li>public EnumeratedType getThingy()</li>
     *     <li>public Enumeration&lt;EnumeratedType&gt; getThingyElement()</li>
     *     <li>public ContainerAdapter setThingy(EnumeratedType thingy)</li>
     *     <li>public ContainerAdapter setThingy(Enumeration&lt;EnumeratedType&gt; thingy)</li>
     * </ol>
     * Each method delegates to the underlying HAPI FHIR type.
     * @param converter
     * @param type
     * @param methods
     */
    public void handleEnumTypeMethods(FhirToHapiTypeConverter converter, HapiType type, List<Method> methods) {
        String attributeName = converter.parseAttributeName();
        if (converter.isMultiType()) {
            throw new RuntimeException("Multi Enum Types Not implemented yet " + converter.getFullAttributePath());
        } else {
            buildHasMethod(methods, attributeName);

            if (converter.getCardinality().equals(Cardinality.OPTIONAL_MULTIPLE)) {
                buildHasMethodWithArgument(methods, attributeName, "", type.getEnumerationType());
                buildGetterMethod(methods, attributeName, "List<Enumeration<" + type.getEnumerationType() + ">>",  type.getEnumerationType() , converter.isExtension(), converter.getExtensionUri());
//                buildGetterMethod(methods, attributeName + BaseMethodHandler.ATTRIBUTE_NAME_ELEMENT_SUFFIX, type.getDatatype() + "<" + type.getEnumerationType() + ">", type.getDatatype(), converter.isExtension(), converter.getExtensionUri());

                buildSetterMethod(methods, attributeName, "List<Enumeration<" + type.getEnumerationType()+ ">>", getParentType());
                buildAddMethod(methods, attributeName, type.getEnumerationType(), getParentType());
//                buildAddMethod(methods, attributeName + BaseMethodHandler.ATTRIBUTE_NAME_ELEMENT_SUFFIX,  type.getEnumerationType(), getParentType());

            } else {
                buildHasMethod(methods, attributeName, BaseMethodHandler.ATTRIBUTE_NAME_ELEMENT_SUFFIX);
                buildGetterMethod(methods, attributeName, type.getEnumerationType(), type.getEnumerationType(), converter.isExtension(), converter.getExtensionUri());
                buildGetterMethod(methods, attributeName + BaseMethodHandler.ATTRIBUTE_NAME_ELEMENT_SUFFIX, type.getDatatype() + "<" + type.getEnumerationType() + ">", type.getDatatype(), converter.isExtension(), converter.getExtensionUri());

                buildSetterMethod(methods, attributeName, type.getEnumerationType(), getParentType());
                buildSetterMethod(methods, attributeName + BaseMethodHandler.ATTRIBUTE_NAME_ELEMENT_SUFFIX, type.getDatatype() + "<" + type.getEnumerationType() + ">", getParentType());

            }
        }
    }

    /**
     * FHIR Datatypes can be extended. This results in the generation of a new wrapper class for the extended
     * type. The wrapper class is then exposed through getters and setters allowing the caller to get
     * a handle to the extended 'logical' type rather than the underlying FHIR datatype.
     *
     * Method constructs four 'wrapper-type' methods (note that two are list methods when the cardinality is
     * greater than one. and then all the relevant datatype methods.
     * If thingy is an enumerated field in ContainerAdapter, the methods have the following signature:
     *
     * <ol>
     *     <li>public List&lt;WrappedDataType&gt; getWrappedThingy()</li>
     *     <li>public ContainerAdapter setWrappedThingy(List&lt;WrappedType&gt; param)</li>
     *     <li>public WrappedDataType getWrappedThingy()</li>
     *     <li>public ContainerAdapter setWrappedThingy(WrappedType param)</li>
     *     <li>public ContainerAdapter addWrappedThingy(WrappedDataType param)</li>
     *     <li>public WrappedDataType addWrappedThingy()</li>
     * </ol>
     *
     * @param converter
     * @param type
     * @param methods
     */
    public void handleExtendedDatatypes(FhirToHapiTypeConverter converter, HapiType type, List<Method> methods) {
        String attributeName = converter.parseAttributeName();
        if (converter.isMultiType()) {
            throw new RuntimeException("Multi generated types should not exist yet. If so, this is not implemented");
        } else {
            buildExtendedDatatypeGetter(methods, attributeName, type.getGeneratedTypeOrList(), type.getGeneratedType(), type.getDatatype());
            buildExtendedDatatypeSetter(methods, attributeName, type.getGeneratedTypeOrList(), type.getGeneratedType(), type.getDatatype(), getParentType());

            if(converter.isMultipleCardinality()) {
                buildFluentAddExtendedTypeMethod(methods, attributeName, type.getGeneratedType(), type.getDatatype(), getParentType());
                if(attributeName.equalsIgnoreCase("contained")) {
                    //Do nothing since the method would have no idea what resource to return
                } else {
                    buildAddExtendedTypeMethod(methods, attributeName, type.getGeneratedType(), type.getDatatype(), getParentType());
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
//                Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName + disambiguationGetterSuffix) + "Target", "java.util.List<" + type.getDatatype() + ">");
//                method.setBody(getTemplate().getReferenceListAsTypedList("adaptedClass", attributeName + "Target", type.getDatatype()));
//                method.addImport(type.getGeneratedType());
//                addMethod(methods, method);
                buildReferenceListAsTypedListGetter(methods, attributeName, disambiguationGetterSuffix + "Target", "Target", "java.util.List<" + type.getDatatype() + ">", type.getGeneratedType(), type.getDatatype());
            } else {
                //TODO Investigate whether this code is necessary. Method was never added and adding it results in an error.
//                Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName + "Target"), "java.util.List<" + type.getDatatype() + ">");
//                method.setBody(getTemplate().getAdapterGetMethodDelegationBody(attributeName));
//                method.addImport(type.getDatatype());
//                buildGetterMethod(methods, attributeName, "Target", "", "java.util.List<" + type.getDatatype() + ">", type.getDatatype(), converter.isExtension(), converter.getExtensionUri());
            }

            //Getter
//            Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName), "java.util.List<org.hl7.fhir.dstu3.model.Reference>");
//            method.setBody(getTemplate().getAdapterGetMethodDelegationBody(attributeName));
//            method.addImport("org.hl7.fhir.dstu3.model.Reference");
//            addMethod(methods, method);
            buildGetterMethod(methods, attributeName, "java.util.List<org.hl7.fhir.dstu3.model.Reference>", "org.hl7.fhir.dstu3.model.Reference", converter.isExtension(), converter.getExtensionUri());
        } else {
            if(converter.isMultiType()) {
                //Getter
//                Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName + "Reference"), "org.hl7.fhir.dstu3.model.Reference");
//                method.setBody(getTemplate().getAdapterGetMethodDelegationWithTryCatchBody(attributeName +  "Reference"));
//                method.addImport(type.getDatatype());
//                method.addImport("org.hl7.fhir.dstu3.model.Reference"); //Todo fix this as it will result in a lot of duplication
//                addMethod(methods, method);
                buildGetterMethod(methods, attributeName, "Reference", "Reference", "org.hl7.fhir.dstu3.model.Reference", "org.hl7.fhir.dstu3.model.Reference", false, null);

                //Getter
//                Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName + disambiguationGetterSuffix) + "Target", type.getDatatype());
//                method.setBody(getTemplate().castTypeToReferenceAndReturnTarget(attributeName, type.getDatatype()));
//                method.addImport(type.getDatatype());
//                addMethod(methods, method);
                buildCastTypeToReferenceAndReturnTargetGetter(methods, attributeName, disambiguationGetterSuffix + "Target", "", type.getDatatype());

                //Setter
//                method = constructSetMethodSignature(attributeName, "org.hl7.fhir.dstu3.model.Reference", getParentType());
//                method.setBody(getTemplate().getAdapterSetMethodDelegationBody(attributeName));
//                method.addImport(type.getDatatype());
//                addMethod(methods, method);
                buildSetterMethod(methods, attributeName, "org.hl7.fhir.dstu3.model.Reference", getParentType());

                //Setter
//                method = constructSetMethodSignature(attributeName + "Target", type.getDatatype(), getParentType());
//                method.setBody(getTemplate().wrapResourceInReferenceAndSet(attributeName));
//                method.addImport(type.getDatatype());
//                addMethod(methods, method);
                buildWrapResourceInReferenceSetter(methods, attributeName, "Target", "", type.getDatatype(), getParentType());

            } else {
                if(StringUtils.isEmpty(disambiguationGetterSuffix)) {
                    buildGetterMethod(methods, attributeName, disambiguationGetterSuffix, "", "org.hl7.fhir.dstu3.model.Reference", null, converter.isExtension(), converter.getExtensionUri());//Useless function
                }
                buildSetterMethod(methods, attributeName, "org.hl7.fhir.dstu3.model.Reference", getParentType());

                if(type.getDatatype() != null) {
                    buildGetterMethodWithCast(methods, attributeName, disambiguationGetterSuffix + "Target", "Target", type.getDatatype(), type.getDatatype());
                    buildSetterMethod(methods, attributeName + "Target", type.getDatatype(), getParentType());
                }

                if(type.getGeneratedType() != null) {
                    buildProfiledReferenceGetter(methods, attributeName, disambiguationGetterSuffix + "AdapterTarget", "", type.getGeneratedType(), type.getDatatype());
                    buildProfiledReferenceSetter(methods, attributeName, type.getGeneratedType(), type.getDatatype(), getParentType());
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
//                    Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName + disambiguatingSuffix), type.getDatatypeOrList());
//                    if (type.isResource()) {
//                        method.setBody(getTemplate().getExtensionListGetterBodyResourceDstu3(type.getDatatype(), converter.getExtensionUri()));
//                    } else {
//                        method.setBody(getTemplate().getExtensionListGetterBodyDstu3(type.getDatatype(), converter.getExtensionUri()));
//                    }
//                    if (type.getDatatype() == null) {
//                        System.out.println("STOP HERE");
//                    } else {
//                        method.addImport(type.getDatatype());
//                        method.addImport("java.util.List");
//                        addMethod(methods, method);
//                    }
                    buildListOfReferenceValuedExtensionGetter(methods, attributeName, disambiguatingSuffix, "", type.getDatatypeOrList(), type.getDatatype(), type.isResource(), converter.getExtensionUri());

                    //Setter
//                    method = constructSetMethodSignature(attributeName, type.getDatatypeOrList(), getParentType());
//                    if (type.isResource()) {
//                        method.setBody(getTemplate().getExtensionListSetterBodyResourceDstu3(converter.getExtensionUri()));
//                    } else {
//                        method.setBody(getTemplate().getExtensionListSetterBodyDstu3(type.getDatatype(), converter.getExtensionUri()));
//                    }
//                    if (type.getDatatype() == null) {
//                        System.out.println("STOP HERE");
//                    } else {
//                        method.addImport(type.getDatatype());
//                        method.addImport("java.util.List");
//                        addMethod(methods, method);
//                    }
                    buildListOfReferenceValuedExtensionSetter(methods, attributeName, type.getDatatypeOrList(), getParentType(), type.getDatatype(), type.isResource(), converter.getExtensionUri());
                } else {
                    //Getter
                    Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName + disambiguatingSuffix), type.getDatatypeOrList());
                    if (type.isResource()) {
                        method.setBody(getTemplate().getExtensionGetterBodyResourceDstu3(adaptedClass, type.getDatatype(), converter.getExtensionUri()));
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
                    buildExtensionGetter(methods, attributeName, disambiguatingSuffix, adaptedClass, type.getDatatypeOrList(), type.getDatatype(), type.isResource(), converter.getExtensionUri());

                    //Setter
//                    method = constructSetMethodSignature(attributeName, type.getDatatypeOrList(), getParentType());
//                    if (type.isResource()) {
//                        method.setBody(getTemplate().getExtensionSetterBodyResourceDstu3(adaptedClass, converter.getExtensionUri()));
//                    } else {
//                        method.setBody(getTemplate().getExtensionSetterBodyDstu3(adaptedClass, converter.getExtensionUri()));
//                    }
//                    if (type.getDatatype() == null) {
//                        System.out.println("STOP HERE");
//                    } else {
//                        method.addImport(type.getDatatype());
//                        method.addImport("java.util.List");
//                        addMethod(methods, method);
//                    }
                    buildExtensionSetter(methods, attributeName, adaptedClass, type.getDatatypeOrList(), getParentType(), type.getDatatype(), type.isResource(), converter.getExtensionUri());
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
//            Method method = Method.constructNoArgMethod(Method.buildGetterName(attributeName), "java.util.List<" + type.getGeneratedType() + ">");
//            method.setBody(getTemplate().getUserDefinedExtensionTypeGetterBody_dstu3(type.getGeneratedType(), getUserDefinedStructureExtensionURL()));
//            method.addImport(type.getDatatype());
//            method.addImport("java.util.List");
//            addMethod(methods, method);
            buildUserDefinedExtensionTypeGetter(methods, attributeName, type.getGeneratedType(), type.getGeneratedType(), type.getDatatype(), getUserDefinedStructureExtensionURL());

            //Setter
//            method = constructSetMethodSignature(attributeName, "java.util.List<" + type.getGeneratedType() + ">", getParentType());
//            method.setBody(getTemplate().getUserDefinedExtensionTypeSetterBody_dstu3(type.getGeneratedType()));
//            method.addImport(type.getDatatype());
//            method.addImport("java.util.List");
//            addMethod(methods, method);
            buildUserDefinedExtensionTypeSetter(methods, attributeName, "java.util.List<" + type.getGeneratedType() + ">", getParentType(), type.getGeneratedType(), type.getDatatype());
        } else {
            throw new RuntimeException("Single cardinality user defined structures are not supported yet");
        }
    }

}
