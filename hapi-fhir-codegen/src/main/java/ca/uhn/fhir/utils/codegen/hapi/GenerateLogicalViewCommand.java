package ca.uhn.fhir.utils.codegen.hapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt.Type;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;
import ca.uhn.fhir.model.primitive.UriDt;
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
	private Map<String, List<Method>> classToMethodStore;
	private StructureDefinition profile;
	private String generatedCodePackage;
	private Node<ElementDefinitionDt> rootNode;
	private String rootNodeName;
	
	public GenerateLogicalViewCommand() {
		itemClassMap = new HashMap<>();
		classToMethodStore = new HashMap<>();
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
	
	public void addMethodToStore(String modelName, Method method) {
		List<Method> cache = classToMethodStore.get(modelName);
		if(cache == null) {
			cache = new ArrayList<Method>();
			classToMethodStore.put(modelName, cache);
		}
		if(!cache.contains(method)) {
			cache.add(method);
		} else {
			LOGGER.info("Method already exist - skipping" + method);
			System.out.println("Method already exist - skipping" + method);
		}
	}
	
	public void addMethodsToStore(String modelName, List<Method> methods) {
		for(Method method : methods) {
			addMethodToStore(modelName, method);
		}
	}
	
	public List<Method> getMethodsForClass(String modelName) {
		return classToMethodStore.get(modelName);
	}
	
	@Override
	public void execute(Node<ElementDefinitionDt> node) {
		boolean found = false;
		if(node.getPayload() != null) {
			found = node.getPayload().getPath() != null && (node.getPayload().getPath().contains("criticality"));
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
		rootClass.getMethods().addAll(methods);
	}
	
	public void handleInnerNonRootNonExtensionNode(Node<ElementDefinitionDt> node) {
		if(node.isInnerL1()) {
			handleInnerL1Node(node);
		} else {
			handleInnerLnNode(node);
		}
	}
	
	/**
	 * Handles leaf nodes that are FHIR extensions. There are two kinds of
	 * extension leaf nodes. Extensions on the root node or extensions on backbone
	 * elements or data types.
	 * 
	 * @param node
	 */
	public void handleExtensionLeafNode(Node<ElementDefinitionDt> node) {
		if(ProfileWalker.isFhirExtension(node.getParent().getPayload())) { // A leaf extension on an extension ...
			if(node.getPayload().getTypeFirstRep().getProfileFirstRep().getValueAsString() == null) { // FHIR profiles are all over the place on this. Sometimes a profile is given with an anchor. At other times, nothing is provided and you have to figure this out. Yuck!
				node.getPayload().getTypeFirstRep().getProfileFirstRep().setValue(node.getParent().getPayload().getTypeFirstRep().getProfileFirstRep().getValueAsString() + "#" + PathUtils.getLastPathComponent(node.getPayload().getName()));
			}
			ClassModel parentClass = retrieveClassModel(node.getParent(), node.getParent().getName());
			//String parentClassName = StringUtils.capitalize(CodeGenerationUtils.makeIdentifierJavaSafe(node.getParent().getName()));
			String type = fhirResourceManager.getFullyQualifiedJavaType(profile, node.getPayload().getTypeFirstRep());
			ExtendedStructureAttributeHandler handler = new ExtendedStructureAttributeHandler(fhirResourceManager, templateUtils, profile, node.getPayload());
			handler.initialize();
			handler.setExtendedStructureName(parentClass.getName());
			List<Method> methods = handler.buildCorrespondingMethods();//FhirMethodGenerator.generateAccessorMethodsForExtendedTypes(profile, node.getPayload(), parentClassName, fhirResourceManager, extensionDefUri);
			parentClass.getMethods().addAll(methods);
		} else {
			if(node.getParent().isRoot()) { //A leaf extension on root
				List<Method> extensionMethods = handleStructureDefinitionElement(node.getPayload(), false);
				ClassModel rootClass = retrieveClassModel(node.getParent(), node.getParent().getName());
				rootClass.getMethods().addAll(extensionMethods);
			} else { //Leaf extension on a type or backbone element
				//List<Method> extensionMethods = handleStructureDefinitionElement(node.getPayload(), true);
				ClassModel parentClass = retrieveClassModel(node.getParent(), node.getParent().getName());
				if(!parentClass.hasTaggedValue(EXTENDED_TYPE)) {
					parentClass.addTaggedValue(EXTENDED_TYPE, EXTENDED_TYPE);
				}
//				if(parentClass.getSupertypes() == null || parentClass.getSupertypes().size() == 0) {
//					parentClass.addImport("java.util.List"); 
//					String supertype = fhirResourceManager.getFullyQualifiedJavaType(node.getParent().getPayload().getTypeFirstRep());
//					if(supertype.equals("BackboneElement")) {
//						String code = node.getParent().getParent().getName() + "." + node.getParent().getName();
//						Type type = new Type();
//						type.setCode(code);
//						supertype = fhirResourceManager.getFullyQualifiedJavaType(type);
//						System.out.println("Supertype: " + supertype);
//					}
//					parentClass.addImport("ca.uhn.fhir.model.dstu2.resource.*");//Why not just import 'supertype'?
//					parentClass.addSupertype(supertype);
//				}
				
				
				ExtendedAttributeHandler handler = new ExtendedAttributeHandler(fhirResourceManager, templateUtils, profile, node.getPayload());
				handler.initialize();
				handler.setGeneratedCodePackage(generatedCodePackage);
				handler.setAddExtensionsToThis(false);
				handler.setExtendedStructure(true);
				handler.setExtendedTypeName(parentClass.getName());
				parentClass.getMethods().addAll(handler.buildCorrespondingMethods());
			}
		}
	}

	private void initializeAdaptedModel(Node<ElementDefinitionDt> node, ClassModel model) {
		String tentativeType = null;
		String nodeType = node.getPayload().getTypeFirstRep().getCode();
		if(nodeType.equals("BackboneElement")) {
			String code = node.getParent().getName() + "." + node.getName();
			//String code = node.getPayload().getPath();
			//code = PathUtils.getPathMinusRootComponent(code);
//			String code = node.getName();
//			code = Character.toLowerCase(code.charAt(0)) + code.substring(1);
			Type type = new Type();
			type.setCode(code);
			tentativeType = fhirResourceManager.getFullyQualifiedJavaType(profile, type);
			System.out.println("Supertype: " + tentativeType);
		} else if(nodeType.equals("DomainResource")) {
			tentativeType = fhirResourceManager.getFullyQualifiedJavaType(profile, node.getName(), null);
		} else {
			tentativeType = fhirResourceManager.getFullyQualifiedJavaType(profile, node.getPayload().getTypeFirstRep());
			if(tentativeType.contains("BaseResource")) {
				System.out.println("STOP HERE");
			}
		}
		InterfaceAdapterGenerator.addAdapteeField(model, tentativeType);
		InterfaceAdapterGenerator.generateConstructors(templateUtils, model.getName(), tentativeType, model.getMethods());
		InterfaceAdapterGenerator.generateAdapteeGetter(model.getMethods(), tentativeType);//fhirResourceManager.getResourceNameToClassMap().get(typeName).getName());
		InterfaceAdapterGenerator.generateAdapteeSetter(model.getMethods(), tentativeType);//fhirResourceManager.getResourceNameToClassMap().get(typeName).getName());
		model.addImport("java.util.List"); 
		model.addImport("ca.uhn.fhir.model.dstu2.resource.*");//Why not just import 'supertype'?
	}
	
	/**
	 * Handles leaf nodes that are NOT FHIR Extensions. There are two kinds of
	 * non-extension leaf nodes. leaf nodes on the root node or leaf nodes on backbone
	 * elements or data types. The latter requires no special handling as they
	 * are already handled by HAPI FHIR.
	 * 
	 * @param node
	 */
	public void handleNonExtensionLeafNode(Node<ElementDefinitionDt> node) {
		if(node.isLeaf() && isNotExtensionNode(node)) {
			if(node.parentIsRoot()) { // A leaf element on root
				UriDt profile = node.getPayload().getTypeFirstRep().getProfileFirstRep();
				List<Method> methods = handleStructureDefinitionElement(node.getPayload(), false);
				ClassModel rootClass = retrieveClassModel(node.getParent(), node.getParent().getName());
				rootClass.getMethods().addAll(methods);
			} else { //a leaf on a type or backbone element
//				System.out.println("NON EXTENSION LEAF NODE: " + node.getParent().getName() + "." + node);
//				if(node.getParent().getName().equals("Address")) {
//					System.out.println("Stop here");
//				}
//				//Nothing needs to be done unless extension is used
//				UriDt profile = node.getPayload().getTypeFirstRep().getProfileFirstRep();
				List<Method> methods = handleStructureDefinitionElement(node.getPayload(), false, node.getParent().getName());
				ClassModel parentClass = retrieveClassModel(node.getParent(), node.getParent().getName());
				addMethodsToStore(parentClass.getName(), methods);
//				parentClass.getMethods().addAll(methods);
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
				Type type = new Type();
				type.setCode(handler.getBackboneElementName());
				handler.setExtendedSupertype(type);
			} else {
				handler.setExtendedSupertype(node.getPayload().getTypeFirstRep());
			}
			List<Method> methods = handler.buildCorrespondingMethods();
			ClassModel rootClass = retrieveClassModel(node.getParent(), node.getParent().getName());
			rootClass.getMethods().addAll(methods);
			//Add the original set from HAPI FHIR as well
			methods = handleStructureDefinitionElement(node.getPayload(), false);
			rootClass.getMethods().addAll(methods);
		} else {
			List<Method> methods = handleStructureDefinitionElement(node.getPayload(), false);
			ClassModel rootClass = retrieveClassModel(node.getParent(), node.getParent().getName());
			rootClass.getMethods().addAll(methods);
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
//		Method constructor = new Method();
//		constructor.setBody("this.rootObjectExtension = new ExtensionDt(false, uri);");
//		constructor.isConstructor(true);
//		classModel.addMethod(constructor);
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
		ClassModel model = itemClassMap.get(node.getPathFromRoot());
		if(model == null) {
			model = new ClassModel(CodeGenerationUtils.makeIdentifierJavaSafe(className));
			model.setNamespace(generatedCodePackage);
			initializeAdaptedModel(node, model);
			itemClassMap.put(node.getPathFromRoot(), model);
		}
		return model;
	}
	
	private ClassField buildUriField(String name, String extensionDefUri) {
		return ClassField.buildStaticConstant(name, java.lang.String.class.getCanonicalName(), "\"" + extensionDefUri + "\"");
	}
	
}
