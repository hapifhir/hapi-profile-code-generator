package ca.uhn.fhir.utils.fhir.dstu3;

import java.util.HashMap;
import java.util.Map;

import org.hl7.fhir.dstu3.model.ElementDefinition;
import org.hl7.fhir.dstu3.model.StructureDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.utils.common.graph.Node;
import ca.uhn.fhir.utils.fhir.PathUtils;
import ca.uhn.fhir.utils.fhir.dstu3.StructureDefinitionPreprocessor;

public class ProfileTreeBuilder {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(ProfileTreeBuilder.class);
	
	private StructureDefinition profile;
	private ElementDefinition rootElement;
	private Map<String, ElementDefinition> elementIndex;
	private Map<String, ElementDefinition> metaElementIndex;
	private Map<String, ElementDefinition> valueReferenceElementIndex;
	private Node<ElementDefinition> root;
	
	public ProfileTreeBuilder(StructureDefinition profile) {
		this.profile = profile;
		this.elementIndex = new HashMap<String, ElementDefinition>();
		this.metaElementIndex = new HashMap<String, ElementDefinition>();
		this.valueReferenceElementIndex = new HashMap<String, ElementDefinition>();
	}
	
	/**
	 * Method populates all element indexes and builds the StructureDefinition
	 * graph.
	 */
	public void initialize() {
		StructureDefinitionPreprocessor preprocessor = new StructureDefinitionPreprocessor(profile);
		preprocessor.processElements();
		populateElementIndices();
		populateElementGraph();
	}
	
	/**
	 * Method parses through StructureDefinition and indexes all elements
	 * based on whether they are slots, Metadata slots, or the root resource
	 * element definitions. Indexes are a convenience to support fast element 
	 * retrieval.
	 * 
	 */
	protected void populateElementIndices() {
		for(ElementDefinition element : profile.getSnapshot().getElement()) {
			if(isRootElement(element)) {
				rootElement = element;
			} else if(StructureDefinitionPreprocessor.isProfileMetaElement(element)) {
				addToMetaElementIndex(element);
			} else if(isValueRefElement(element)) {
				addToValueRefElementIndex(element);
			} else {
				addToElementIndex(element);
			}
		}
		processValueRefElements();
	}
	
	/**
	 * Build a graph of element nodes. FHIR StructureDefinitions are flattened
	 * hierarchies. This method rebuilds the tree hierarchy based on the element
	 * paths.
	 */
	protected void populateElementGraph() {
		root = new Node<ElementDefinition>(rootElement.getPath());
		root.setPayload(rootElement);
		for(ElementDefinition element : elementIndex.values()) {
			String path = PathUtils.generateExtensionPath(element.getPath(), element.getSliceName());
			LOGGER.debug(path);
			if(path.contains("Encounter.relatedCondition")) {
				System.out.println("Stop here");
			}
			root.addToPath(path, element);
		}
	}
	
	protected void processValueRefElements() {
		//TODO Implement
		//For each value ref, path = DiagnosticReport.locationPerformed.valueReference, name = DiagnosticReport.extension.valueReference
		//  Get corresponding element name = DiagnosticReport.locationPerformed
		//  Replace type.profile with valueRef's profile
	}
	
	/**
	 * Returns the element with the given key from the element index.
	 * 
	 * @param key
	 * @return
	 */
	public ElementDefinition getFromElementIndex(String key) {
		return elementIndex.get(key);
	}
	
	/**
	 * Returns the meta element with the given key from the meta element index.
	 * 
	 * @param key
	 * @return
	 */
	public ElementDefinition getFromMetaElementIndex(String key) {
		return metaElementIndex.get(key);
	}
	
	/**
	 * Adds an element to the element index indexed using the 
	 * generateElementSignature() method.
	 *  
	 * @param element
	 */
	public void addToElementIndex(ElementDefinition element) {
		String key = generateElementSignature(element);
		if(elementIndex.get(key) != null) {
			LOGGER.error("Key for element " + key + " already exists in index and is not unique");
		}
		elementIndex.put(key, element);
	}
	
	/**
	 * Adds an element to the meta element index using the path of the element
	 * as the key.
	 * 
	 * @param element
	 */
	public void addToMetaElementIndex(ElementDefinition element) {
		String key = element.getPath();
		if(StructureDefinitionPreprocessor.isProfileMetaElement(element)) {
			metaElementIndex.put(key, element);
		} else {
			throw new RuntimeException(element.getPath() + " is not a meta element");
		}
	}
	
	public void addToValueRefElementIndex(ElementDefinition element) {
		valueReferenceElementIndex.put(generateElementSignature(element), element);
	}
	
	/****************************************************
	 * 		Accessor Methods
	 ****************************************************/
	
	/**
	 * Method returns the StructureDefinition (a.k.a., 'Profile) associated with this ProfileWalker.
	 * 
	 * @return
	 */
	public StructureDefinition getProfile() {
		return profile;
	}
	
	/**
	 * Method initializes the ProfileWalker with the passed in
	 * StructureDefinition
	 * 
	 * @param profile
	 */
	public void setProfile(StructureDefinition profile) {
		this.profile = profile;
	}
	
	/**
	 * Returns the root resource element for this StructureDefinition
	 * 
	 * @return
	 */
	public ElementDefinition getRootElement() {
		return rootElement;
	}
	
	/**
	 * Method sets the root resource element for this ProfileWalker
	 * 
	 * @param rootElement
	 */
	public void setRootElement(ElementDefinition rootElement) {
		this.rootElement = rootElement;
	}
	
	/**
	 * Method returns an index for all non-root, non-meta elements in this
	 * StructureDefinition.
	 * 
	 * @return
	 */
	public Map<String, ElementDefinition> getElementIndex() {
		return elementIndex;
	}
	
	/**
	 * Method sets the element index for this StructureDefinition
	 * 
	 * @param elementIndex
	 */
	public void setElementIndex(Map<String, ElementDefinition> elementIndex) {
		this.elementIndex = elementIndex;
	}
	
	/**
	 * Method returns the meta element index.
	 * 
	 * @return
	 */
	public Map<String, ElementDefinition> getMetaElementIndex() {
		return metaElementIndex;
	}
	
	/**
	 * Method sets the meta element index.
	 * 
	 * @param metaElementIndex
	 */
	public void setMetaElementIndex(Map<String, ElementDefinition> metaElementIndex) {
		this.metaElementIndex = metaElementIndex;
	}
	
	/**
	 * Returns the root node of the StructureDefinition tree
	 * 
	 * @return
	 */
	public Node<ElementDefinition> getRoot() {
		return root;
	}
	
	/**
	 * Sets the root node of the StructureDefinition tree
	 * 
	 * @param root
	 */
	protected void setRoot(Node<ElementDefinition> root) {
		this.root = root;
	}
	
	/****************************************************
	 * 		Utility Methods
	 ****************************************************/

	/**
	 * Method returns true if a StructureDefnition element defines
	 * an extension. The extension detection algorithm looks for any
	 * element which defines a type and specifies a type.code of 'Extension'.
	 * 
	 * @param element
	 * @return
	 */
	public static boolean isFhirExtension(ElementDefinition element) {
		boolean success = false;
		for(ElementDefinition.TypeRefComponent type : element.getType()) {
			if(type != null && type.getCode() != null && type.getCode().equals("Extension")) {
				success = true;
				break;
			}
		}
		return success;
	}
	
	/**
	 * Method returns true if element represents the base resource for this
	 * StructureDefinition (a path with no dot-delimiter).
	 * <p>
	 * At this time, the base resource is assumed to be
	 * the resource named by the first path component. For instance,
	 * <p>
	 * <code>
	 * Patient.telecom.preferred
	 * </code>
	 * <p>
	 * indicates a base resource of 'Patient'.
	 * 
	 * @param element
	 * @return
	 */
	public static boolean isRootElement(ElementDefinition element) {
		String path = element.getPath();
		return !path.contains(".");
	}
	
	public static boolean isValueRefElement(ElementDefinition element) {
		boolean valueRefElement = false;
		String path = element.getPath();
		if(path != null && path.endsWith("valueReference")) {
			valueRefElement = true;
			LOGGER.info("Processing ValueReference " + path);
		}
		return valueRefElement;
	}
	
	/**
	 * Method generates an element handle using the element path and name.
	 * 
	 * @param element
	 * @return
	 */
	public static String generateElementSignature(ElementDefinition element) {
		String pathAndName = element.getPath() + ": " + element.getSliceName();
		return pathAndName;
	}
}
