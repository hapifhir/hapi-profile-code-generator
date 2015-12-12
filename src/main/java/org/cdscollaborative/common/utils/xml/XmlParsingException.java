package org.cdscollaborative.common.utils.xml;

/**
 * Exception thrown when exceptions are encountered during the serialization
 * and deserialization to and from XML.
 * 
 * @author cnanjo
 *
 */
public class XmlParsingException extends RuntimeException {

	private static final long serialVersionUID = -8399132377164777646L;

	/**
	 * Default constructor
	 */
	public XmlParsingException() {
		super();
	}

	/**
	 * Constructor taking a message argument as a string to indicate
	 * the nature of the error.
	 * 
	 * @param message
	 */
	public XmlParsingException(String message) {
		super(message);
	}

	/**
	 * Constructor taking as an argument the exception that triggered the error.
	 * 
	 * @param cause
	 */
	public XmlParsingException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor taking both a message and the exception that triggered
	 * this exception.
	 * 
	 * @param message
	 * @param cause
	 */
	public XmlParsingException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public XmlParsingException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
