package ca.uhn.fhir.utils.common.xml;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Static utility class providing methods for common XML operations and manipulations.
 * 
 * @author Claude Nanjo
 *
 */
public class XmlUtils {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(XmlUtils.class);
	
	/**
	 * Method returns a Document node from an input stream to a valid XML document resource.
	 * 
	 * @param in
	 * @return
	 */
	public static Document createDocumentFromInputStream(InputStream in) {
	    DocumentBuilderFactory factory = null;
	    DocumentBuilder builder = null;
	    Document document = null;

	    try {
	      factory = DocumentBuilderFactory.newInstance();
	      builder = factory.newDocumentBuilder();
	      document = builder.parse(new InputSource(in));
	    } catch (Exception e) {
	    	throw new XmlParsingException("Error building document from input stream", e);
	    }
	    return document;
	}
	
	/**
	 * Method returns a new Document node.
	 * 
	 * @param in
	 * @return
	 */
	public static Document newDocument() {
	    DocumentBuilderFactory factory = null;
	    DocumentBuilder builder = null;
	    Document document = null;

	    try {
	      factory = DocumentBuilderFactory.newInstance();
	      builder = factory.newDocumentBuilder();
	      document = builder.newDocument();
	    } catch (Exception e) {
	    	throw new XmlParsingException("Error creating new XML document node", e);
	    }
	    
	    return document;
	}
	
	public static String nodeToString(Node node) {
		StringWriter sw = new StringWriter();
		try {
			 Transformer t = TransformerFactory.newInstance().newTransformer();
			 t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			 t.setOutputProperty(OutputKeys.INDENT, "yes");
			 t.transform(new DOMSource(node), new StreamResult(sw));
		} catch (TransformerException te) {
			throw new XmlParsingException("nodeToString Transformer Exception", te);
		}
		return sw.toString();
	}
	
	/**
	 * Convenience method identifying text nodes containing whitespace as in pretty-printed XML documents.
	 * Method will result in a NullPointerException if a text node with a null value is passed
	 * in.
	 * Method returns false if the argument is not a Node.TEXT_NODE.
	 * 
	 * @param node
	 * @return
	 */
	public static boolean isWhitespaceTextNode(Node node) {
        if (isTextNode(node)) {
            String text = node.getNodeValue();
            return text.trim().length() == 0;
        } else {
            return false;
        }
    }
	
	/**
	 * Convenience method identifying text nodes (Node.TEXT_NODE).
	 * 
	 * @param node
	 * @return
	 */
	public static boolean isTextNode(Node node) {
		return node.getNodeType() == Node.TEXT_NODE;
	}
	
	/**
	 * Convenience method identifying element nodes (Node.ELEMENT_NODE).
	 * 
	 * @param node
	 * @return
	 */
	public static boolean isElementNode(Node node) {
		return node.getNodeType() == Node.ELEMENT_NODE;
	}
	
	/**
	 * TODO Add tests
	 * Helper method for loading an XML list of string elements into a java
	 * List<String>. List must be of format:
	 * 
	 * <pre>
	 * <code>
	 * &lt;elementListLabel&gt;
	 * 		&lt;elementItemLabel&gt;String content&lt;/elementItemLabel&gt;
	 * 		&lt;elementItemLabel&gt;String content&lt;/elementItemLabel&gt;
	 * &lt;/elementListLabel&gt;
	 * </code>
	 * </pre>
	 * 
	 * Note: Method does a 'getElementByTagName'
	 * 
	 * @param elementTagName
	 * @param itemList
	 */
	public static List<String> loadElementList(Document document, String elementTagName) {
		List<String> itemList = new ArrayList<String>();
		try {
			NodeList itemListNL = document
					.getElementsByTagName(elementTagName);
			for (int index = 0; index < itemListNL.getLength(); index++) {
				Element item = (Element) itemListNL.item(index);
				String itemTextContent = item.getTextContent();
				itemList.add(itemTextContent);
			}
		} catch (Exception e) {
			throw new RuntimeException(
					"Error occurred while loading element list: ", e);
		}
		return itemList;
	}
	
	/**
	 * TODO Add tests
	 * Helper method that returns the string content of an XML element
	 * 
	 * Note: Method does a 'getElementByTagName' and enforces a unique
	 * element of that name in the document, otherwise throws an error. 
	 * 
	 * Element must be of form:
	 * 
	 * <pre>
	 * <code>
	 * 		&lt;elementLabel&gt;String content&lt;/elementLabel&gt;
	 * </code>
	 * </pre>
	 * 
	 * @param elementTagName
	 * @param itemList
	 *            The text value of that element.
	 */
	public static String loadElementContentFromConfiguration(Document document, String elementName) {
		NodeList itemNL = document.getElementsByTagName(elementName);
		String elementValue = null;
		if (itemNL == null || itemNL.getLength() == 0) {
			return elementValue;
		} else if (itemNL.getLength() == 1) {
			Element itemTextContent = (Element) itemNL.item(0);
			elementValue = itemTextContent.getTextContent();
		} else {
			throw new RuntimeException("Only one " + elementName
					+ " node supported in configuration file");
		}
		return elementValue;
	}
}
