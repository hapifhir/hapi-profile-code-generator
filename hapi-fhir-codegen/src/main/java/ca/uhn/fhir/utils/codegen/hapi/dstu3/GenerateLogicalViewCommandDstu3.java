package ca.uhn.fhir.utils.codegen.hapi.dstu3;

import ca.uhn.fhir.utils.codegen.CodeGenerationUtils;
import ca.uhn.fhir.utils.codegen.hapi.GenerateLogicalViewCommandBase;
import ca.uhn.fhir.utils.codegen.hapi.InterfaceAdapterGenerator;
import ca.uhn.fhir.utils.codegen.hapi.MethodBodyGenerator;
import ca.uhn.fhir.utils.codegen.hapi.dstu2.FhirResourceManagerDstu2;
import ca.uhn.fhir.utils.common.graph.Node;
import ca.uhn.fhir.utils.common.metamodel.ClassField;
import ca.uhn.fhir.utils.common.metamodel.ClassModel;
import ca.uhn.fhir.utils.common.metamodel.Method;
import ca.uhn.fhir.utils.fhir.PathUtils;
import ca.uhn.fhir.utils.fhir.dstu3.ProfileTreeBuilder;
import org.apache.commons.lang3.StringUtils;
import org.hl7.fhir.dstu3.model.ElementDefinition;
import org.hl7.fhir.dstu3.model.StructureDefinition;
import org.hl7.fhir.dstu3.model.UriType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Command visits a StructureDefinition hierarchical tree using post-depth-first
 * search and generates the UML class, method, and field definitions required
 * for code generation.
 *
 * @author cnanjo
 */
public class GenerateLogicalViewCommandDstu3 extends GenerateLogicalViewCommandBase<ElementDefinition> {

    public static final Logger LOGGER = LoggerFactory.getLogger(FhirResourceManagerDstu2.class);

    public static final String EXTENDED_TYPE = "extended_type";
    public static final String EXTENSION_ADAPTER_ATTRIBUTE_NAME = "rootObjectExtension";

    private FhirResourceManagerDstu3 fhirResourceManager;
    private MethodBodyGenerator templateUtils;
    private Map<String, ClassModel> itemClassMap;
    private StructureDefinition profile;
    private String generatedCodePackage;
    private String adapterName;
    private String interfaceName;
    private String rootNodeName;

    public GenerateLogicalViewCommandDstu3() {
        itemClassMap = new HashMap<>();
    }

    public GenerateLogicalViewCommandDstu3(StructureDefinition profile, FhirResourceManagerDstu3 fhirResourceManager,
                                           MethodBodyGenerator templateUtils, String generatedCodePackage) {
        this();
        this.profile = profile;
        this.fhirResourceManager = fhirResourceManager;
        this.templateUtils = templateUtils;
        this.generatedCodePackage = generatedCodePackage;
        buildAdapterInterfaceNames();
    }

    public String getAdapterName() {
        return adapterName;
    }

    public void setAdapterName(String adapterName) {
        this.adapterName = adapterName;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public void buildAdapterInterfaceNames() {
        if (profile != null) {
            String base = profile.getType();
            if (base != null && base.equalsIgnoreCase("DomainResource")) {
                String profileName = FhirResourceManagerDstu3.getProfileName(profile);
                interfaceName = "I" + profileName;
                adapterName = profileName + "Adapter";
            } else {
                interfaceName = generateInterfaceName(profile);
                adapterName = generateAdapterName(profile);
            }
        } else {
            //Log error
        }
    }

    public String buildAdaptedTypeName(String adaptedType) {
        String adaptedName = null;
        if (profile != null) {
            adaptedName = CodeGenerationUtils.makeIdentifierJavaSafe(FhirResourceManagerDstu3.getProfileName(profile)) + StringUtils.capitalize(adaptedType) + "Adapter";
        } else {
            //Log error
        }
        return adaptedName;
    }

    @Override
    public void execute(Node<ElementDefinition> node) {
        boolean found = false;
        if (node.getPayload() != null) {
            found = node.getPayload().getPath() != null && (node.getPayload().getPath().contains("locationPerformed"));
        }
        if (found) {// && profile.getName().equals("Immunization")) {
            LOGGER.debug("Found!");
        }
        try {
            handleNode(node);
        } catch (Exception e) {
            LOGGER.error("Error processing node: " + node.getPathFromRoot() + ". Skipping element "
                    + node.getPayload().getPath(), e);
            e.printStackTrace();
        }
    }

    /*************************
     * Node handling API
     *************************/

    /**
     * Method handles nodes in a StructureDefinition tree.
     * <p>
     * A node can either be a leaf node or an inner node.
     *
     * @param node
     */
    public void handleNode(Node<ElementDefinition> node) {
        if (node.isLeaf()) {
            handleLeafNode(node);
        } else {
            handleInnerNode(node);
        }
    }

    /**
     * Method handles leaf nodes in a StructureDefinition tree. Leaf nodes have
     * no children.
     * <p>
     * A leaf node can either be an extension element or a (possibly
     * constrained) FHIR Core Model resource attribute.
     *
     * @param node
     */
    public void handleLeafNode(Node<ElementDefinition> node) {
        if (isExtensionNode(node)) {
            handleExtensionLeafNode(node);
        } else {
            handleNonExtensionLeafNode(node);
        }
    }

    /**
     * Method handles inner nodes in a StructureDefinition tree (that is, nodes
     * that are not leaf nodes or, more precisely, nodes that have children
     * nodes and represent an intermediate layer is a tree hierarchy).
     * <p>
     * Inner nodes are classified as either
     * <ol>
     * <li>L0 or root node
     * <li>L1 or a first level node under the root node that is NOT a leaf node
     * <li>LN or a non-leaf node that is neither root nor L1
     * </ol>
     * and as to whether they are inner extension nodes or inner non-extension
     * nodes.
     *
     * @param node
     */
    public void handleInnerNode(Node<ElementDefinition> node) {
        if (node.isRoot()) {
            handleRootNode(node);
        } else {
            handleInnerNonRootNode(node);
        }
    }

    /**
     * Method handles the root node in the StructureDefinition tree. The root
     * node represents the FHIR Core Model resource.
     *
     * @param node
     */
    public void handleRootNode(Node<ElementDefinition> node) {
        setRootNode(node);
    }

    public void handleInnerNonRootNode(Node<ElementDefinition> node) {
        if (isExtensionNode(node)) {
            handleInnerNonRootExtensionNode(node);
        } else {
            handleInnerNonRootNonExtensionNode(node);
        }
    }

    /**
     * Method handles 'user defined' extension types elements. That is,
     * extensions that contain themselves extensions. An example of such node
     * might be a nationality UDT extension on Patient which contains a 'code'
     * attribute.
     *
     * @param node
     */
    public void handleInnerNonRootExtensionNode(Node<ElementDefinition> node) {
        // 1. Clone the node as we are going to override its type (currently set
        // to 'Extension' with a profile URI) with the new generated type
        ElementDefinition clonedElement = FhirResourceManagerDstu3.shallowCloneElement(node.getPayload());
        // 2. Build the canonical class path for this to-be-generated type
        String generatedType = CodeGenerationUtils.buildGeneratedClassName(generatedCodePackage, FhirResourceManagerDstu3.getProfileName(profile),
                node.getName() + "Adapter");
        // 3. Keep track of user-defined types
        fhirResourceManager.addGeneratedType(generatedType);
        // 4. Set the type of this node to the new class name
        clonedElement.getType().clear();
        clonedElement.addType().setCode(generatedType);
        Node<ElementDefinition> clonedNode = node.shallowClone();
        clonedNode.setPayload(clonedElement);
        // 5. Generate the accessor methods for this new type
        MethodHandler handler = new MethodHandler(fhirResourceManager, templateUtils, clonedNode);
        handler.setParentType(generatedCodePackage + "." + getInterfaceName());
        handler.setAddUserDefinedStructureToParent(true);
        handler.setUserDefinedStructureExtensionURL(node.getPayload().getType().get(0).getProfile());//Yuck
        List<Method> accessors = handler.generateMethods();
        // 6. Add method definitions to parent class
        ClassModel parentClass = null;
        if (node.getParent().isRoot()) {
            parentClass = retrieveRootClassModel(node.getParent(), getAdapterName());
        } else {
            parentClass = retrieveClassModel(node.getParent(), node.getParent().getName());
        }
        parentClass.addMethods(accessors);
    }

    /**
     * <p>
     * Methods handles nodes that are neither root nor leaves, that is nodes
     * that have both parent and children. Method does not process inner nodes
     * that are extensions.
     * </p>
     *
     * @param node
     */
    public void handleInnerNonRootNonExtensionNode(Node<ElementDefinition> node) {
        if (node.isInnerL1()) {
            handleInnerL1Node(node);
        } else {
            handleInnerLnNode(node);
        }
    }

    /**
     * <p>
     * Handles leaf nodes that are FHIR extensions. There are two kinds of
     * extension leaf nodes:
     * </p>
     * <ul>
     * <li>extensions on the root node</li>
     * <li>extensions on backbone elements or data types.</li>
     * </ul>
     * <p>
     * <p>
     * When processing the latter, the creation of logical accessors for
     * extended attributes will need to be adjusted to return the extended type
     * for getters and take the extended type as an argument to setters. This
     * method handles this by clearing the default DO_NOT_PROCESS
     * PROCESSING_INSTRUCTION tagged value assigned to backbone elements. (For
     * more information about this process, please refer to method:
     * handleNonExtensionLeafNode.)
     * </p>
     *
     * @param node
     */
    public void handleExtensionLeafNode(Node<ElementDefinition> node) {
        if (ifParentIsExtension(node)) { // A leaf extension on an extension ...
            // 1. Determine profile URI if none is given (generally relative to
            // parent extension)
            determineProfileUri(node);
            // 2. Fetch the parent class definition
            ClassModel parentClass = retrieveClassModel(node.getParent(), buildAdaptedTypeName(node.getParent().getName()));
            // 3. Initialize method handlers and generate accessors
            MethodHandler handler = new MethodHandler(fhirResourceManager, templateUtils, node);
            handler.setExtensionStructure(true);//Flag to indicate that root is an extension and not a FHIR core element
            handler.setExtensionStructureAttributeName(EXTENSION_ADAPTER_ATTRIBUTE_NAME);
            handler.setParentType(generatedCodePackage + "." + buildAdaptedTypeName(node.getParent().getName()));
            List<Method> methods = handler.generateMethods();
            // 4. Add accessors for this field to parent
            parentClass.addMethods(methods);
        } else {
            if (node.getParent().isRoot()) {
                // A leaf extension on root
                MethodHandler handler = new MethodHandler(fhirResourceManager, templateUtils, node);
                handler.setParentType(generatedCodePackage + "." + getInterfaceName());
                List<Method> methods = handler.generateMethods();
                ClassModel rootClass = retrieveRootClassModel(node.getParent(),
                        getAdapterName());
                rootClass.addMethods(methods);
            } else {
                // Leaf extension on a type or backbone element
                ClassModel parentClass = retrieveClassModel(node.getParent(), buildAdaptedTypeName(node.getParent().getName()));

                // 1. Flag the parent FHIR Core datatype as containing
                // extensions and thus
                // indicating that it will need special processing when it is
                // visited
                if (!parentClass.hasTaggedValue(EXTENDED_TYPE)) {
                    parentClass.addTaggedValue(EXTENDED_TYPE, EXTENDED_TYPE);
                }

                // 2. Since FHIR CORE type has extensions, remove the
                // DO_NOT_PROCESS flag (which tells the framework to handle this
                // type as any other type)
                if (parentClass.hasTaggedValue(InterfaceAdapterGenerator.PROCESSING_INSTRUCTION)) {
                    parentClass.removeTaggedValue(InterfaceAdapterGenerator.PROCESSING_INSTRUCTION);
                }

                // 3. Create the logical accessors for this extension. Note that
                // unlike the original ones, these
                // must return the wrapped type rather than the original type
                MethodHandler handler = new MethodHandler(fhirResourceManager, templateUtils, node);
                handler.setParentType(generatedCodePackage + "." + buildAdaptedTypeName(node.getParent().getName()));
                List<Method> methods = handler.generateMethods();
                parentClass.addMethods(methods);
            }
        }
    }

    /**
     * <p>
     * Handles leaf nodes that are NOT FHIR Extensions. There are two kinds of
     * non-extension leaf nodes:
     * <ul>
     * <li>leaf nodes on the root node</li>
     * <li>leaf nodes on a backbone structure or data type such as Address</li>
     * </ul>
     * <p>
     * <p>
     * It is important to note that extended backbone types or datatypes become
     * new logical types which must be returned by getters and taken as
     * arguments to setters instead of the original type.
     * </p>
     * <p>
     * <p>
     * For instance, if an address is extended with a preferred attribute, it
     * becomes an 'ExtendedAddress' and getAddress() must now return this new
     * 'ExtendedAddress' in order to support the 'logical' chaining of methods
     * such as patient.getAddress().setPreferred(true) even for extended
     * attributes. However, this is only the case IF THE BACKBONE ELEMENT IS IN
     * FACT EXTENDED. As extended types are wrapped and must delegate all calls
     * to the HAPI FHIR adaptee which they wrap, all non-extended attributes
     * must be exposed by the adapter. Thus, by default the backbone parent
     * class is constructed as if it were to be wrapped but is tagged with a
     * processing instruction of DO_NOT_PROCESS upon creation. This processing
     * instruction is cleared if any extension is added to the type by the call
     * handleExtensionLeafNode() to signal that the signature for the getter and
     * setter for this extended backbone type will need to be adjusted and that
     * the type must be wrapped. If no extended attributes exist for the type,
     * the temporary class is simply ignored at processing time (so as not to
     * wrap a type that has no extensions).
     *
     * @param node
     */
    public void handleNonExtensionLeafNode(Node<ElementDefinition> node) {
        if (node.isLeaf() && isNotExtensionNode(node)) {
            if (node.parentIsRoot()) {
                // A leaf element on root
                MethodHandler handler = new MethodHandler(fhirResourceManager, templateUtils, node);
                handler.setParentType(generatedCodePackage + "." + getInterfaceName());
                List<Method> methods = handler.generateMethods();
                ClassModel rootClass = retrieveRootClassModel(node.getParent(),
                        getAdapterName());
                rootClass.addMethods(methods);
            } else {
                //a leaf on a type or backbone element
                MethodHandler handler = new MethodHandler(fhirResourceManager, templateUtils, node);
                FhirToHapiTypeConverter converter = new FhirToHapiTypeConverter(fhirResourceManager, node.getParent().getPayload());
                handler.setParentType(generatedCodePackage + "." + buildAdaptedTypeName(node.getParent().getName()));
                List<Method> methods = handler.generateMethods();
                ClassModel parentClass =
                        retrieveDoNotProcessClassModel(node.getParent(), buildAdaptedTypeName(node.getParent().getName()));
                parentClass.addMethods(methods);
            }
        }
    }

    /**
     * Handles non-leaf nodes that are the direct children of the root node.
     * <p>
     *
     * @param node
     */
    public void handleInnerL1Node(Node<ElementDefinition> node) {
        ClassModel currentClass = retrieveClassModel(node, node.getName());
        if (currentClass.hasTaggedValue(EXTENDED_TYPE)) {
            //1. Creating an element definition that is the same as the existing one (clone) but where the
            //   type is now the wrapped type we are about to create rather than the original type.
            //   The wrapped type should expose the same interface as the original type but with
            //   fields either constrained out or new ones added.
//			ElementDefinition clonedElement = FhirResourceManagerDstu3.shallowCloneElement(node.getPayload());
//            Node<ElementDefinition> clonedNode = node.shallowClone();
//            clonedNode.setPayload(clonedElement);
            //2. Clear the current type and set the new wrapped type instead
//            clonedElement.getType().clear();
//            clonedElement.addType().setCode(generatedCodePackage + "."
//					+ CodeGenerationUtils.makeIdentifierJavaSafe(profile.getName()) + node.getName());
            node.getPayload().addType().setCode(generatedCodePackage + "."
                    + CodeGenerationUtils.makeIdentifierJavaSafe(FhirResourceManagerDstu3.getProfileName(profile)) + node.getName() + "Adapter");
            fhirResourceManager.addGeneratedType(generatedCodePackage + "."
                    + CodeGenerationUtils.makeIdentifierJavaSafe(FhirResourceManagerDstu3.getProfileName(profile)) + node.getName() + "Adapter");

            MethodHandler handler = new MethodHandler(fhirResourceManager, templateUtils, node);
            handler.setParentType(generatedCodePackage + "." + getInterfaceName());
            List<Method> updatedAccessors = handler.generateMethods();
            ClassModel parentClass = null;
            if (node.getParent().isRoot()) {
                parentClass = retrieveRootClassModel(node.getParent(), getAdapterName());
            } else {
                parentClass = retrieveClassModel(node.getParent(), node.getParent().getName());
            }
            parentClass.addMethods(updatedAccessors);
        } else {
            MethodHandler handler = new MethodHandler(fhirResourceManager, templateUtils, node);
            handler.setParentType(generatedCodePackage + "." + generateAdapterName(profile));
            List<Method> methods = handler.generateMethods();
            ClassModel rootClass = retrieveRootClassModel(node.getParent(), getAdapterName());
            rootClass.addMethods(methods);
        }

    }

    /**
     * Handles non-leaf nodes that are neither root nor the direct children of
     * root.
     * <p>
     *
     * @param node
     */
    public void handleInnerLnNode(Node<ElementDefinition> node) {
    }

    public boolean isExtensionNode(Node<ElementDefinition> node) {
        return ProfileTreeBuilder.isFhirExtension(node.getPayload());
    }

    public boolean isNotExtensionNode(Node<ElementDefinition> node) {
        return !isExtensionNode(node);
    }

    /*************************
     * Accessor Methods
     *************************/

    /**
     * Method returns the UML class model generated for the root node.
     * Generally, this represents the class model definition for the profiled
     * FHIR resource.
     *
     * @return
     */
    public ClassModel getRootNodeClassModel() {
        return getClassMap().get(getRootNode().getPathFromRoot()).setName(rootNodeName);
    }

    /**
     * Method returns an index of generated UML class models for all inner nodes
     * in the StructuredDefinition hierarchy. These are generated based on the
     * ElementDefinition payload of the node and any children of the node.
     *
     * @return
     */
    public Map<String, ClassModel> getClassMap() {
        return itemClassMap;
    }

    public void setRootNodeName(String rootNodeName) {
        this.rootNodeName = rootNodeName;
    }

    public String getRootNodeName() {
        return rootNodeName;
    }

    /*************************
     * Helper Methods
     *************************/

    /**
     * <p>
     * Method configures and invokes the User Defined Extension Handler, and
     * delegates to this handler the generation of accessor methods for the user
     * defined type represented by the element argument.
     * </p>
     *
     * @param element             The element that defines the user defined type
     * @param addExtensionsToThis Flag if extensions are to be added to 'this' when type
     *                            specialization rather than wrapping is used.
     * @return
     */
    public List<Method> handleUserDefinedExtensionType(ElementDefinition element, boolean addExtensionsToThis) {
//		IMethodHandler handler = resolver.buildUserDefinedExtensionTypeHandler(profile, element, generatedCodePackage);
//		return handler.buildCorrespondingMethods();
        return null;
    }

    public List<Method> handleStructureDefinitionElement(ElementDefinition element, boolean addExtensionsToThis,
                                                         String fluentReturnOverride) {
//		List<Method> methodDefinitions = new ArrayList<>();
//		IMethodHandler handler = resolver.identifyHandler(profile, element, generatedCodePackage);
//		if (handler != null) {
//			handler.setGeneratedCodePackage(generatedCodePackage);
//			((BaseMethodGenerator) handler).setFluentReturnTypeOverride(fluentReturnOverride);
//			if (handler instanceof ExtendedAttributeHandler) {// TODO Fix this
//																// ugliness
//				((ExtendedAttributeHandler) handler).setAddExtensionsToThis(addExtensionsToThis);
//				if (addExtensionsToThis) {
//					((ExtendedAttributeHandler) handler).setExtendedStructure(true);
//				}
//			}
//			methodDefinitions.addAll(handler.buildCorrespondingMethods());
//		}
//		return methodDefinitions;
        return null;
    }

    public List<Method> handleStructureDefinitionElement(ElementDefinition element, boolean addExtensionsToThis) {
        //return handleStructureDefinitionElement(element, addExtensionsToThis, null);
        return null;
    }

    public ClassModel retrieveRootClassModel(Node<ElementDefinition> node, String className) {
        return retrieveClassModel(node, className, null, generatedCodePackage + "." + getInterfaceName());
    }

    /**
     * Retrieves a class model for the element at that path location or creates
     * a new one if none exists.
     *
     * @param node
     * @param className
     * @return
     */
    public ClassModel retrieveClassModel(Node<ElementDefinition> node, String className) {
        return retrieveClassModel(node, className, null, null);
    }

    /**
     * Retrieves a class model for the element at that path location or creates
     * a new one if none exists and initializes with the taggedValues argument.
     *
     * @param node
     * @param className
     * @param taggedValues
     * @return
     */
    public ClassModel retrieveClassModel(Node<ElementDefinition> node, String className,
                                         Map<String, Object> taggedValues, String interfaceName) {
        ClassModel model = itemClassMap.get(node.getPathFromRoot());
        if (model == null) {
            model = new ClassModel(CodeGenerationUtils.makeIdentifierJavaSafe(className));
            model.setNamespace(generatedCodePackage);
            model.addImport("org.hl7.fhir.dstu3.model.*");
            if (taggedValues != null) {
                for (String key : taggedValues.keySet()) {
                    model.addTaggedValue(key, taggedValues.get(key));
                }
            }
            if (interfaceName != null) {
                model.addInterface(interfaceName);
            }
            initializeAdaptedModel(node, model);
            itemClassMap.put(node.getPathFromRoot(), model);
        }
        return model;
    }

    /**
     * Method creates a new ClassModel with tagged value PROCESSING_INSTRUCTION
     * = DO_NOT_PROCESS. The intent is to create a class that by default should
     * not be processed by code generating frameworks unless the tag is cleared
     * downstream.
     *
     * @param node
     * @param className
     * @return
     */
    public ClassModel retrieveDoNotProcessClassModel(Node<ElementDefinition> node, String className) {
        ClassModel model = itemClassMap.get(node.getPathFromRoot());
        if (model == null) {
            model = new ClassModel(CodeGenerationUtils.makeIdentifierJavaSafe(className));
            model.setNamespace(generatedCodePackage);
            model.addImport("org.hl7.fhir.dstu3.model.*");
            model.addTaggedValue(InterfaceAdapterGenerator.PROCESSING_INSTRUCTION,
                    InterfaceAdapterGenerator.DO_NOT_PROCESS);
            initializeAdaptedModel(node, model);
            itemClassMap.put(node.getPathFromRoot(), model);
        }
        return model;
    }

    /**
     * <p>
     * Method infers HAPI field type from FHIR type. Then, based on whether the
     * type is a FHIR extension or a FHIR core type (which could contain
     * extensions), determines how to initialize the class definition used for
     * code generation.
     * </p>
     * <p>
     * <p>
     * FHIR Core resources and datatypes that have extensions are wrapped using
     * the adaptor/adaptee pattern so that extended types can be accessed as
     * 'first-level' attributes. This approach allows one to hide the FHIR
     * extension and constraint mechanism from the caller.
     * </p>
     * <p>
     * <p>
     * Types that do not exist in FHIR Core but that are extensions are handled
     * in a different manner. Rather than wrapping a FHIR type, they wrap a FHIR
     * extension that is bound to the first non-extension type.
     * </p>
     *
     * @param node
     * @param model
     */
    private void initializeAdaptedModel(Node<ElementDefinition> node, ClassModel model) {
        //Need to add a type of DomainResource when element is root node and type is null. This type existed in earlier profiles but was removed in latest STU3 profiles.
        //It led to a bug downstream when no type was found but one was expected for the element definition.
        if(node.isRoot() && (node.getPayload().getType() == null || node.getPayload().getType().size() == 0)) {
            if(profile.getBaseDefinition().endsWith("DomainResource")) {
                node.getPayload().addType().setCode("DomainResource");
            } else {
                node.getPayload().addType().setCode(PathUtils.getLastResourcePathComponent(profile.getBaseDefinition()));
            }
        }
        FhirToHapiTypeConverter converter = new FhirToHapiTypeConverter(fhirResourceManager, node.getPayload());
        if (converter.isExtension()) {// TODO Following code couples FHIR
            buildExtendedParentClass(model, node);
        } else {
            // TODO model building to code generation  which really should be agnostic
            InterfaceAdapterGenerator.addAdapteeField(model, converter.getHapiType().getDatatype());
            InterfaceAdapterGenerator.generateConstructors(templateUtils, converter.getHapiType().getDatatype(), model);
            InterfaceAdapterGenerator.generateAdapteeGetter(model, converter.getHapiType().getDatatype());
            InterfaceAdapterGenerator.generateAdapteeSetter(model, converter.getHapiType().getDatatype());
            model.addImport("java.util.List");
        }
    }

    /**
     * <p>
     * Method builds the core structure of a user-defined extension that itself
     * contains extensions. An example may include an extension to a patient for
     * nationality (Patient.extension) which itself contains a code and a
     * validity period (both Patient.extension.extension.
     * </p>
     * <p>
     * Each such class thus contains a root extensions to represent the
     * Patient.extension along with the URI for this extension (e.g.,
     * 'http://hl7.org/fhir/StructureDefinition/patient-nationality') and a
     * method to bind the root extension to the parent resource's declared
     * extension tree (e.g., Patient)
     * </p>
     * <p>
     * This class will then expose logical accessors for its own extensions and
     * can be manipulated as a first class object (e.g.,
     * Patient.getNationality().getCode(), rather than
     * Patient.getExtension(...).getExtension...
     * </p>
     *
     * @param node
     */
    public void buildExtendedParentClass(ClassModel classDefinition, Node<ElementDefinition> node) {
        // 1. Get the extension URI (we are only considering single extensions
        // in this implementation)
        String extensionDefUri = node.getPayload().getType().get(0).getProfile();
        // 2. Sets the package for this new type.
        classDefinition.setNamespace(generatedCodePackage);
        // 3. Build a field for this extension URI. E.g.,
        // public static final String uri =
        // "http://hl7.org/fhir/StructureDefinition/patient-nationality";
        ClassField fieldUri = buildUriField("uri", extensionDefUri);
        classDefinition.addField(fieldUri);
        // 4. Create class field and accessors for the wrapped ExtensionDt field
        Method.addGetterSetterFieldToClass(classDefinition, EXTENSION_ADAPTER_ATTRIBUTE_NAME, "org.hl7.fhir.dstu3.model.Extension");
        // 5. Bind wrapped extension to parent class
        Method bindMethod = new Method();
        bindMethod.setName("bindTemplateToParent");
        bindMethod.addParameter("containingResource", "org.hl7.fhir.dstu3.model.DomainResource");
        bindMethod.setBody(templateUtils.getBindExtensionToParent_dstu3());
        bindMethod.setReturnType("org.hl7.fhir.dstu3.model.Extension");
        classDefinition.addMethod(bindMethod);
    }

    /**
     * <p>
     * Method builds a field definition for a String value attribute that holds
     * a URI. E.g., String extensionUri = /some/extension/uri
     * </p>
     *
     * @param name            The name of the field
     * @param extensionDefUri The extension URI
     * @return
     */
    private ClassField buildUriField(String name, String extensionDefUri) {
        return ClassField.buildStaticConstant(name, java.lang.String.class.getCanonicalName(),
                "\"" + extensionDefUri + "\"");
    }

    /**
     * <p>
     * FHIR Profiles are defined heterogeneously. This method is a
     * pre-processing method that attempts to determine the profile URI for
     * extensions on extensions in cases where no profile URI is given. It does
     * so by finding the profile URI of the parent (assuming the parent is
     * itself an extension), and appending an anchor with the child extension
     * name.
     * </p>
     *
     * @param node
     */
    private void determineProfileUri(Node<ElementDefinition> node) {
        ElementDefinition.TypeRefComponent type = node.getPayload().getType().get(0);
        if (type.getProfile() == null) {
            try {
                String uri = node.getParent().getPayload().getType().get(0).getProfile()
                        + "#" + PathUtils.getLastPathComponent(node.getPayload().getName());
                type.setProfile(new UriType(uri).getValue());//TODO Revisit
            } catch (Exception e) {
                throw new RuntimeException("Unable to determine profile URI", e);
            }
        }
    }

    /**
     * Determines if parent node is also an extension.
     *
     * @param node The parent node
     * @return
     */
    private boolean ifParentIsExtension(Node<ElementDefinition> node) {
        return ProfileTreeBuilder.isFhirExtension(node.getParent().getPayload());
    }

    public static String generateAdapterName(StructureDefinition profile) {
        String javaSafeProfileName = CodeGenerationUtils.makeIdentifierJavaSafe(FhirResourceManagerDstu3.getProfileName(profile));
        return generateAdapterName(javaSafeProfileName);
    }

    public static String generateInterfaceName(StructureDefinition profile) {
        String javaSafeProfileName = CodeGenerationUtils.makeIdentifierJavaSafe(FhirResourceManagerDstu3.getProfileName(profile));
        return generateInterfaceName(javaSafeProfileName);
    }
}
