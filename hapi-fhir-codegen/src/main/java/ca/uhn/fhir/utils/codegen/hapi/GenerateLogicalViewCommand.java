package ca.uhn.fhir.utils.codegen.hapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;
import ca.uhn.fhir.utils.codegen.CodeGenerationUtils;
import ca.uhn.fhir.utils.codegen.hapi.methodgenerator.BaseMethodGenerator;
import ca.uhn.fhir.utils.codegen.hapi.methodgenerator.ExtendedAttributeHandler;
import ca.uhn.fhir.utils.codegen.hapi.methodgenerator.ExtendedBackboneElementHandler;
import ca.uhn.fhir.utils.codegen.hapi.methodgenerator.ExtendedStructureAttributeHandler;
import ca.uhn.fhir.utils.codegen.hapi.methodgenerator.MethodHandlerResolver;
import ca.uhn.fhir.utils.codegen.methodgenerators.IMethodHandler;
import ca.uhn.fhir.utils.common.graph.CommandInterface;
import ca.uhn.fhir.utils.common.graph.Node;
import ca.uhn.fhir.utils.common.metamodel.ClassField;
import ca.uhn.fhir.utils.common.metamodel.ClassModel;
import ca.uhn.fhir.utils.common.metamodel.Method;
import ca.uhn.fhir.utils.fhir.PathUtils;
import ca.uhn.fhir.utils.fhir.ProfileWalker;

/**
 * Command visits a StructureDefinition hierarchical tree using post-depth-first search
 * and generates the UML class, method, and field definitions required for code generation.
 * 
 * @author cnanjo
 *
 */
public class GenerateLogicalViewCommand implements CommandInterface<ElementDefinitionDt> {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(FhirResourceManager.class);
	
	public static final String EXTENDED_TYPE = "extended_type";
	
	private MethodHandlerResolver resolver;
	private FhirResourceManager fhirResourceManager;
	private MethodBodyGenerator templateUtils;
	private Map<String, ClassModel> itemClassMap;
	private StructureDefinition profile;
	private String generatedCodePackage;
	private Node<ElementDefinitionDt> rootNode;
	private String rootNodeName;
	
	public GenerateLogicalViewCommand() {
		itemClassMap = new HashMap<>();
	}
	
	public GenerateLogicalViewCommand(StructureDefinition profile,
							FhirResourceManager fhirResourceManager,
							MethodBodyGenerator templateUtils,
							MethodHandlerResolver resolver, 
							String generatedCodePackage) {
		this();
		this.profile = profile;
		this.resolver = resolver;
		this.fhirResourceManager = fhirResourceManager;
		this.templateUtils = templateUtils;
		this.generatedCodePackage = generatedCodePackage;
	}
	
	@Override
	public void execute(Node<ElementDefinitionDt> node) {
		boolean found = false;
		if(node.getPayload() != null) {
			found = node.getPayload().getPath() != null && (node.getPayload().getPath().contains("preferred"));
		}
		if(found) {// && profile.getName().equals("Immunization")) {
			LOGGER.debug("Found!");
		}
		try {
			handleNode(node);
		} catch(Exception e) {
			LOGGER.error("Error processing node: " + node.getPathFromRoot() + ". Skipping element " + node.getPayload().getPath(), e);
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
	public void handleNode(Node<ElementDefinitionDt> node) {
		if(node.isLeaf()) {
			handleLeafNode(node);
		} else {
			handleInnerNode(node);
		}
	}
	
	/**
	 * Method handles leaf nodes in a StructureDefinition tree.
	 * Leaf nodes have no children.
	 * <p>
	 * A leaf node can either be an extension element or a (possibly
	 * constrained) FHIR Core Model resource attribute.
	 * 
	 * @param node
	 */
	public void handleLeafNode(Node<ElementDefinitionDt> node) {
		if(isExtensionNode(node)) {
			handleExtensionLeafNode(node);
		} else {
			handleNonExtensionLeafNode(node);
		}
	}
	
	/**
	 * Method handles inner nodes in a StructureDefinition tree
	 * (that is, nodes that are not leaf nodes or, more precisely,
	 * nodes that have children nodes and represent an intermediate
	 * layer is a tree hierarchy).
	 * <p>
	 * Inner nodes are classified as either
	 * <ol>
	 *   <li> L0 or root node
	 *   <li> L1 or a first level node under the root node that is NOT a leaf node
	 *   <li> LN or a non-leaf node that is neither root nor L1
	 * </ol>
	 * and as to whether they are inner extension nodes or inner non-extension nodes.
	 * @param node
	 */
	public void handleInnerNode(Node<ElementDefinitionDt> node) {
		if(node.isRoot()) {
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
	public void handleRootNode(Node<ElementDefinitionDt> node) {
		rootNode = node;
	}
	
	public void handleInnerNonRootNode(Node<ElementDefinitionDt> node) {
		if(isExtensionNode(node)) {
			handleInnerNonRootExtensionNode(node);
		} else {
			handleInnerNonRootNonExtensionNode(node);
		}
	}
	
	/**
	 * Method handles 'user defined extension types elements'. That is, extensions that contain themselves extensions.
	 * An example of such node might be a nationality UDT extension on Patient which contains a 'code' attribute.
	 * 
	 * @param node
	 */
	public void handleInnerNonRootExtensionNode(Node<ElementDefinitionDt> node) {
		buildExtendedParentClass(node);
		ElementDefinitionDt clone = FhirResourceManager.shallowCloneElement(node.getPayload());
		String generatedType = generatedCodePackage + "." + CodeGenerationUtils.makeIdentifierJavaSafe(profile.getName()) + node.getName();
		fhirResourceManager.addGeneratedType(generatedType);
		clone.addType().setCode(generatedType);
		List<Method> methods = handleUserDefinedExtensionType(clone, false);
		ClassModel rootClass = retrieveClassModel(node.getParent(), node.getParent().getName());
		rootClass.addMethods(methods);
	}
	
	public void handleInnerNonRootNonExtensionNode(Node<ElementDefinitionDt> node) {
		if(node.isInnerL1()) {
			handleInnerL1Node(node);
		} else {
			handleInnerLnNode(node);
		}
	}
	
	/**
	 * <p>Handles leaf nodes that are FHIR extensions. There are two kinds of
	 * extension leaf nodes:</p>
	 * <ul>
	 * 	<li>extensions on the root node</li>
	 * 	<li>extensions on backbone elements or data types.</li>
	 * </ul>
	 * 
	 * <p>When processing the latter, the creation of logical accessors for extended attributes
	 * will need to be adjusted to return the extended type for getters and take the extended type
	 * as an argument to setters. This method handles this by clearing the default DO_NOT_PROCESS 
	 * PROCESSING_INSTRUCTION tagged value assigned to backbone elements. (For more information about
	 * this process, please refer to method: handleNonExtensionLeafNode.)</p> 
	 * 
	 * @param node
	 */
	public void handleExtensionLeafNode(Node<ElementDefinitionDt> node) {
		if(ProfileWalker.isFhirExtension(node.getParent().getPayload())) { // A leaf extension on an extension ...
			if(node.getPayload().getTypeFirstRep().getProfileFirstRep().getValueAsString() == null) { // FHIR profiles are all over the place on this. Sometimes a profile is given with an anchor. At other times, nothing is provided and you have to figure this out. Yuck!
				node.getPayload().getTypeFirstRep().getProfileFirstRep().setValue(node.getParent().getPayload().getTypeFirstRep().getProfileFirstRep().getValueAsString() + "#" + PathUtils.getLastPathComponent(node.getPayload().getName()));
			}
			ClassModel parentClass = retrieveClassModel(node.getParent(), node.getParent().getName());
			ExtendedStructureAttributeHandler handler = new ExtendedStructureAttributeHandler(fhirResourceManager, templateUtils, profile, node.getPayload());
			handler.initialize();
			handler.setExtendedStructureName(parentClass.getName());
			List<Method> methods = handler.buildCorrespondingMethods();//FhirMethodGenerator.generateAccessorMethodsForExtendedTypes(profile, node.getPayload(), parentClassName, fhirResourceManager, extensionDefUri);
			parentClass.addMethods(methods);
		} else {
			if(node.getParent().isRoot()) { //A leaf extension on root
				List<Method> extensionMethods = handleStructureDefinitionElement(node.getPayload(), false);
				ClassModel rootClass = retrieveClassModel(node.getParent(), node.getParent().getName());
				rootClass.addMethods(extensionMethods);
			} else { //Leaf extension on a type or backbone element
				ClassModel parentClass = retrieveClassModel(node.getParent(), node.getParent().getName());
				if(!parentClass.hasTaggedValue(EXTENDED_TYPE)) {
					parentClass.addTaggedValue(EXTENDED_TYPE, EXTENDED_TYPE);
				}
				if(parentClass.hasTaggedValue(InterfaceAdapterGenerator.PROCESSING_INSTRUCTION)) {
					parentClass.removeTaggedValue(InterfaceAdapterGenerator.PROCESSING_INSTRUCTION);
				}
				
				ExtendedAttributeHandler handler = new ExtendedAttributeHandler(fhirResourceManager, templateUtils, profile, node.getPayload());
				handler.initialize();
				handler.setGeneratedCodePackage(generatedCodePackage);
				handler.setAddExtensionsToThis(false);
				handler.setExtendedStructure(true);
				handler.setExtendedTypeName(parentClass.getName());
				parentClass.addMethods(handler.buildCorrespondingMethods());
			}
		}
	}

	private void initializeAdaptedModel(Node<ElementDefinitionDt> node, ClassModel model) {
		String tentativeType = null;
		String nodeType = node.getPayload().getTypeFirstRep().getCode();
		String path = node.getPayload().getPath();
		String suffix =  PathUtils.getPathMinusRootComponent(path);
		String root = PathUtils.getFirstPathComponent(path);
		if(nodeType != null && nodeType.equals("Extension")) {//Extensions are handled differently
			root = nodeType;
			suffix = null;
		}
		if(root.equals("Extension")) {
			tentativeType = ca.uhn.fhir.model.api.ExtensionDt.class.getName();//Not sure how else to get
		} else if(nodeType.equals("DomainResource") || suffix == null) { //We are dealing with a resource
			tentativeType = HapiFhirUtils.getResourceClass(fhirResourceManager.getFhirContext(), root).getName();
		} else { //We are dealing with an attribute of a resource or a backbone element
			tentativeType = HapiFhirUtils.getStructureTypeClass(fhirResourceManager.getFhirContext(), root, suffix).getName();
		}
		InterfaceAdapterGenerator.addAdapteeField(model, tentativeType);
		InterfaceAdapterGenerator.generateConstructors(templateUtils, tentativeType, model);
		InterfaceAdapterGenerator.generateAdapteeGetter(model, tentativeType);//fhirResourceManager.getResourceNameToClassMap().get(typeName).getName());
		InterfaceAdapterGenerator.generateAdapteeSetter(model, tentativeType);//fhirResourceManager.getResourceNameToClassMap().get(typeName).getName());
		model.addImport("java.util.List"); 
		model.addImport("ca.uhn.fhir.model.dstu2.resource.*");//Why not just import 'supertype'?
	}
	
	/**
	 * <p>Handles leaf nodes that are NOT FHIR Extensions. There are two kinds of
	 * non-extension leaf nodes:
	 * <ul>
	 *   <li>leaf nodes on the root node</li>
	 *   <li>leaf nodes on a backbone structure or data type such as Address</li>
	 * </ul>
	 * 
	 * <p>It is important to note that extended backbone types or datatypes become new logical types which must
	 * be returned by getters and taken as arguments to setters instead of 
	 * the original type.</p>
	 * 
	 * <p>For instance, if an address is extended with a preferred attribute, it becomes an 
	 * 'ExtendedAddress' and getAddress() must now return
	 * this new 'ExtendedAddress' in order to support the 'logical' chaining of methods such as
	 * patient.getAddress().setPreferred(true) even for extended attributes. However, this is only the case IF THE
	 * BACKBONE ELEMENT IS IN FACT EXTENDED. As extended types are wrapped and must delegate
	 * all calls to the HAPI FHIR adaptee which they wrap, all non-extended attributes must be exposed by the adapter.
	 * Thus, by default the backbone parent class is constructed as if it were to be wrapped but 
	 * is tagged with a processing instruction of DO_NOT_PROCESS
	 * upon creation. This processing instruction is cleared if any extension is added to
	 * the type by the call handleExtensionLeafNode() to signal that the signature for the getter and setter 
	 * for this extended backbone type will need to be adjusted and that the type must be wrapped. If
	 * no extended attributes exist for the type, the temporary class is simply ignored at processing time
	 * (so as not to wrap a type that has no extensions).
	 * 
	 * 
	 * @param node
	 */
	public void handleNonExtensionLeafNode(Node<ElementDefinitionDt> node) {
		if(node.isLeaf() && isNotExtensionNode(node)) {
			if(node.parentIsRoot()) { 
				// A leaf element on root
				List<Method> methods = handleStructureDefinitionElement(node.getPayload(), false);
				ClassModel rootClass = retrieveClassModel(node.getParent(), node.getParent().getName());
				rootClass.addMethods(methods);
			} else { 
				//a leaf on a type or backbone element
				List<Method> methods = handleStructureDefinitionElement(node.getPayload(), false, node.getParent().getName());
				ClassModel parentClass = retrieveDoNotProcessClassModel(node.getParent(), node.getParent().getName());
				parentClass.addMethods(methods);
			}
		}
	}
	
	
	
	/**
	 * Handles non-leaf nodes that are the direct children of the root node.
	 * <p>
	 * @param node
	 */
	public void handleInnerL1Node(Node<ElementDefinitionDt> node) {
		ClassModel currentClass = retrieveClassModel(node, node.getName());
		if(currentClass.hasTaggedValue(EXTENDED_TYPE)) {
			//Clone the payload so as not to change the original
			ElementDefinitionDt clone = FhirResourceManager.shallowCloneElement(node.getPayload());
			//Set new type
			clone.getType().clear();
			clone.addType().setCode(generatedCodePackage + "." + CodeGenerationUtils.makeIdentifierJavaSafe(profile.getName()) + node.getName());
			fhirResourceManager.addGeneratedType(generatedCodePackage + "." + CodeGenerationUtils.makeIdentifierJavaSafe(profile.getName()) + node.getName());
			//Rest is the same
			ExtendedBackboneElementHandler handler = new ExtendedBackboneElementHandler(fhirResourceManager, templateUtils, profile, clone);
			handler.initialize();
			String supertype = node.getPayload().getTypeFirstRep().getCode();
			if(supertype != null && supertype.equals("BackboneElement")) {
				handler.setExtendedSupertype(handler.getResourceName(), handler.getTopLevelCoreAttribute());
			} else {
				handler.setExtendedSupertype(null, node.getPayload().getTypeFirstRep().getCode());
			}
			List<Method> methods = handler.buildCorrespondingMethods();
			ClassModel rootClass = retrieveClassModel(node.getParent(), node.getParent().getName());
			rootClass.addMethods(methods);
			//Add the original set from HAPI FHIR as well
			methods = handleStructureDefinitionElement(node.getPayload(), false);
			rootClass.addMethods(methods);
		} else {
			List<Method> methods = handleStructureDefinitionElement(node.getPayload(), false);
			ClassModel rootClass = retrieveClassModel(node.getParent(), node.getParent().getName());
			rootClass.addMethods(methods);
		}
	}
	
	/**
	 * Handles non-leaf nodes that are neither root nor the direct children of root.
	 * <p>
	 * @param node
	 */
	public void handleInnerLnNode(Node<ElementDefinitionDt> node) {
	}
	
	public boolean isExtensionNode(Node<ElementDefinitionDt> node) {
		return ProfileWalker.isFhirExtension(node.getPayload());
	}
	
	public boolean isNotExtensionNode(Node<ElementDefinitionDt> node) {
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
		return getClassMap().get(rootNode.getPathFromRoot()).setName(rootNodeName);
	}
	
	/**
	 * Method returns an index of generated UML class models for all inner nodes
	 * in the StructuredDefinition hierarchy. These are generated based on the 
	 * ElementDefinitionDt payload of the node and any children of the node.
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
	
	public List<Method> handleUserDefinedExtensionType(ElementDefinitionDt element, boolean addExtensionsToThis) {
		IMethodHandler handler = resolver.buildUserDefinedExtensionTypeHandler(profile, element, generatedCodePackage);
		return handler.buildCorrespondingMethods();
	}
	
	public List<Method> handleStructureDefinitionElement(ElementDefinitionDt element, boolean addExtensionsToThis, String fluentReturnOverride) {
		List<Method> methodDefinitions = new ArrayList<>();
		IMethodHandler handler = resolver.identifyHandler(profile, element, generatedCodePackage);
		if(handler != null) {
			handler.setGeneratedCodePackage(generatedCodePackage);
			((BaseMethodGenerator)handler).setFluentReturnTypeOverride(fluentReturnOverride);
			if(handler instanceof ExtendedAttributeHandler) {//TODO Fix this ugliness
				((ExtendedAttributeHandler)handler).setAddExtensionsToThis(addExtensionsToThis);
				if(addExtensionsToThis) {
					((ExtendedAttributeHandler)handler).setExtendedStructure(true);
				}
			}
			methodDefinitions.addAll(handler.buildCorrespondingMethods());
		}
		return methodDefinitions;
	}
	
	public List<Method> handleStructureDefinitionElement(ElementDefinitionDt element, boolean addExtensionsToThis) {
		return handleStructureDefinitionElement(element, addExtensionsToThis, null);
	}
	
	public void buildExtendedParentClass(Node<ElementDefinitionDt> node) {
		LOGGER.info("Creating a new class for: " + node.getName());
		String extensionDefUri = node.getPayload().getTypeFirstRep().getProfileFirstRep().getValueAsString();
		ClassModel classModel = retrieveClassModel(node, node.getName());//StringUtils.capitalize(CodeGenerationUtils.makeIdentifierJavaSafe(node.getName())));
		classModel.setNamespace(generatedCodePackage);
		ClassField fieldUri = buildUriField("uri", extensionDefUri);
		classModel.addField(fieldUri);
		Method.addGetterSetterFieldToClass(classModel, "rootObjectExtension", "ca.uhn.fhir.model.api.ExtensionDt");
		Method bindMethod = new Method();
		bindMethod.setName("bindTemplateToParent");
		bindMethod.addParameter("containingResource", "ca.uhn.fhir.model.dstu2.resource.BaseResource");
		bindMethod.setBody(templateUtils.getBindExtensionToParent());
		bindMethod.setReturnType("ca.uhn.fhir.model.api.ExtensionDt");
		classModel.addMethod(bindMethod);
	}
	
	/**
	 * Retrieves a class model for the element at that path location or creates a new
	 * one if none exists.
	 * 
	 * @param node
	 * @param className
	 * @return
	 */
	public ClassModel retrieveClassModel(Node<ElementDefinitionDt> node, String className) {
		return retrieveClassModel(node, className, null);
	}
		
		
	/**
	 * Retrieves a class model for the element at that path location or creates a new
	 * one if none exists and initializes with the taggedValues argument.
	 * 
	 * @param node
	 * @param className
	 * @param taggedValues
	 * @return
	 */
	public ClassModel retrieveClassModel(Node<ElementDefinitionDt> node, String className, Map<String, Object> taggedValues) {
		ClassModel model = itemClassMap.get(node.getPathFromRoot());
		if(model == null) {
			model = new ClassModel(CodeGenerationUtils.makeIdentifierJavaSafe(className));
			model.setNamespace(generatedCodePackage);
			if(taggedValues != null) {
				for(String key: taggedValues.keySet()) {
					model.addTaggedValue(key, taggedValues.get(key));
				}
			}
			initializeAdaptedModel(node, model);
			itemClassMap.put(node.getPathFromRoot(), model);
		}
		return model;
	}
	
	/**
	 * Method creates a new ClassModel with tagged value PROCESSING_INSTRUCTION = DO_NOT_PROCESS. The intent
	 * is to create a class that by default should not be processed by code generating frameworks unless
	 * the tag is cleared downstream.
	 * 
	 * @param node
	 * @param className
	 * @return
	 */
	public ClassModel retrieveDoNotProcessClassModel(Node<ElementDefinitionDt> node, String className) {
		ClassModel model = itemClassMap.get(node.getPathFromRoot());
		if(model == null) {
			model = new ClassModel(CodeGenerationUtils.makeIdentifierJavaSafe(className));
			model.setNamespace(generatedCodePackage);
			model.addTaggedValue(InterfaceAdapterGenerator.PROCESSING_INSTRUCTION, InterfaceAdapterGenerator.DO_NOT_PROCESS);
			initializeAdaptedModel(node, model);
			itemClassMap.put(node.getPathFromRoot(), model);
		}
		return model;
	}
	
	private ClassField buildUriField(String name, String extensionDefUri) {
		return ClassField.buildStaticConstant(name, java.lang.String.class.getCanonicalName(), "\"" + extensionDefUri + "\"");
	}
	
}
