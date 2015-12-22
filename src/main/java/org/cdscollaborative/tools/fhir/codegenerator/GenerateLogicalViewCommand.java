package org.cdscollaborative.tools.fhir.codegenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cdscollaborative.common.utils.graph.CommandInterface;
import org.cdscollaborative.common.utils.graph.Node;
import org.cdscollaborative.model.meta.ClassField;
import org.cdscollaborative.model.meta.ClassModel;
import org.cdscollaborative.model.meta.Method;
import org.cdscollaborative.tools.fhir.codegenerator.method.ExtendedAttributeHandler;
import org.cdscollaborative.tools.fhir.codegenerator.method.ExtendedBackboneElementHandler;
import org.cdscollaborative.tools.fhir.codegenerator.method.ExtendedStructureAttributeHandler;
import org.cdscollaborative.tools.fhir.codegenerator.method.IMethodHandler;
import org.cdscollaborative.tools.fhir.codegenerator.method.MethodHandlerResolver;
import org.cdscollaborative.tools.fhir.utils.FhirResourceManager;
import org.cdscollaborative.tools.fhir.utils.ProfileWalker;

import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;
import ca.uhn.fhir.model.primitive.UriDt;

/**
 * Command visits a StructureDefinition hierarchical tree using post-depth-first search
 * and generates the UML class, method, and field definitions required for code generation.
 * 
 * @author cnanjo
 *
 */
public class GenerateLogicalViewCommand implements CommandInterface<ElementDefinitionDt> {
	
	private MethodHandlerResolver resolver;
	private FhirResourceManager fhirResourceManager;
	private CodeTemplateUtils templateUtils;
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
							CodeTemplateUtils templateUtils,
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
		boolean found = node.getPayload().getPath() != null && node.getPayload().getPath().equalsIgnoreCase("Organization.contact");
		if(found) {
			System.out.println("Found!");
		}
		handleNode(node);
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
	
	public void handleInnerNonRootExtensionNode(Node<ElementDefinitionDt> node) {
		buildExtendedParentClass(node);
		ElementDefinitionDt clone = FhirResourceManager.shallowCloneElement(node.getPayload());
		//clone.getType().clear();
		clone.addType().setCode(generatedCodePackage + "." + CodeGenerationUtils.makeIdentifierJavaSafe(profile.getName()) + node.getName());
		List<Method> methods = handleStructureDefinitionElement(clone, false);
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
			ClassModel parentClass = retrieveClassModel(node.getParent(), node.getParent().getName());
			//String parentClassName = StringUtils.capitalize(CodeGenerationUtils.makeIdentifierJavaSafe(node.getParent().getName()));
			String type = node.getPayload().getTypeFirstRep().getCode();
			type = fhirResourceManager.getFullyQualifiedJavaType(type);
			ExtendedStructureAttributeHandler handler = new ExtendedStructureAttributeHandler(fhirResourceManager, templateUtils, profile, node.getPayload());
			handler.initialize();
			List<Method> methods = handler.buildCorrespondingMethods();//FhirMethodGenerator.generateAccessorMethodsForExtendedTypes(profile, node.getPayload(), parentClassName, fhirResourceManager, extensionDefUri);
			parentClass.getMethods().addAll(methods);
		} else {
			if(node.getParent().isRoot()) { //A leaf extension on root
				List<Method> extensionMethods = handleStructureDefinitionElement(node.getPayload(), false);
				ClassModel rootClass = retrieveClassModel(node.getParent(), node.getParent().getName());
				rootClass.getMethods().addAll(extensionMethods);
			} else { //Leaf extension on a type or backbone element
				List<Method> extensionMethods = handleStructureDefinitionElement(node.getPayload(), true);
				ClassModel parentClass = retrieveClassModel(node.getParent(), node.getParent().getName());
				parentClass.getMethods().addAll(extensionMethods);
				if(parentClass.getSupertypes() == null || parentClass.getSupertypes().size() == 0) {
					parentClass.addImport("java.util.List"); 
					parentClass.addSupertype(fhirResourceManager.getFullyQualifiedJavaType(node.getParent().getPayload().getTypeFirstRep().getCode()));
				}
			}
		}
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
				if(profile != null && profile.getValueAsString() != null && profile.getValueAsString().equals("http://hl7.org/fhir/StructureDefinition/organization-qicore-qicore-organization")) {
					System.out.println(node.getPayload().getTypeFirstRep().getProfileFirstRep());
				}
				List<Method> methods = handleStructureDefinitionElement(node.getPayload(), false);
				ClassModel rootClass = retrieveClassModel(node.getParent(), node.getParent().getName());
				rootClass.getMethods().addAll(methods);
			} else { //a leaf on a type or backbone element
				//Nothing needs to be done unless extension is used
			}
		}
	}
	
	/**
	 * Handles non-leaf nodes that are the direct children of the root node.
	 * <p>
	 * @param node
	 */
	public void handleInnerL1Node(Node<ElementDefinitionDt> node) {
		System.out.println("---> " + node.getPathFromRoot());
		ClassModel currentClass = retrieveClassModel(node, node.getName());
		if(currentClass != null && currentClass.getSupertypes() != null && currentClass.getSupertypes().size() > 0) {
			System.out.println("------> Need to add getter and setter for new extended type " + currentClass.getNamespace() + "." + currentClass.getName());
			//Clone the payload
			ElementDefinitionDt clone = FhirResourceManager.shallowCloneElement(node.getPayload());
			//Set new type
			clone.getType().clear();
			clone.addType().setCode(generatedCodePackage + "." + CodeGenerationUtils.makeIdentifierJavaSafe(profile.getName()) + node.getName());
			fhirResourceManager.addGeneratedType(generatedCodePackage + "." + CodeGenerationUtils.makeIdentifierJavaSafe(profile.getName()) + node.getName());
			//Rest is the same
			ExtendedBackboneElementHandler handler = new ExtendedBackboneElementHandler(fhirResourceManager, templateUtils, profile, clone);
			handler.initialize();
			handler.setExtendedSupertype(node.getPayload().getTypeFirstRep().getCode());
			List<Method> methods = handler.buildCorrespondingMethods();
			ClassModel rootClass = retrieveClassModel(node.getParent(), node.getParent().getName());
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
	
	public List<Method> handleStructureDefinitionElement(ElementDefinitionDt element, boolean addExtensionsToThis) {
		List<Method> methodDefinitions = new ArrayList<>();
		IMethodHandler handler = resolver.identifyHandler(profile, element, generatedCodePackage);
		if(handler != null) {
			handler.setGeneratedCodePackage(generatedCodePackage);
			if(handler instanceof ExtendedAttributeHandler) {//TODO Don't resolve. Just make it so.
				((ExtendedAttributeHandler)handler).setAddExtensionsToThis(addExtensionsToThis);
			}
			methodDefinitions.addAll(handler.buildCorrespondingMethods());
		}
		return methodDefinitions;
	}
	
	public void buildExtendedParentClass(Node<ElementDefinitionDt> node) {
		System.out.println("Create a class for: " + node.getName());
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
		Method constructor = new Method();
		constructor.setBody("this.rootObjectExtension = new ExtensionDt(false, uri);");
		constructor.isConstructor(true);
		classModel.addMethod(constructor);
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
			itemClassMap.put(node.getPathFromRoot(), model);
		}
		return model;
	}
	
	private ClassField buildUriField(String name, String extensionDefUri) {
		return ClassField.buildStaticConstant(name, java.lang.String.class.getCanonicalName(), "\"" + extensionDefUri + "\"");
	}
}
