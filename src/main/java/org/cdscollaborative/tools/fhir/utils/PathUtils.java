package org.cdscollaborative.tools.fhir.utils;

import org.apache.commons.lang.StringUtils;

/**
 * Utility class for handling miscellaneous FHIR paths manipulations.
 * 
 * @author cnanjo
 *
 */
public class PathUtils {
	
	private static String delimiter = ".";
	
	/**
	 * Method takes an extension path such as:
	 * <p>
	 * <code>
	 * Patient.extension
	 * </code>
	 * <p>
	 * and an element name such as
	 *  <p>
	 * <code>
	 * race
	 * </code>
	 * <p>
	 * and creates the path:
	 *  <p>
	 * <code>
	 * Patient.race.
	 * </code>
	 * <p>
	 * In the case of recursive extensions, this method eliminates duplicate components of a path.
	 * For instance, 
	 *  <p>
	 * <code>
	 * Patient.birthDate.extension
	 * </code>
	 * <p>
	 * and element name 
	 *  <p>
	 * <code>
	 * birthdate.verification
	 * </code>
	 * <p>
	 * overlap on birthDate (not sure why profiles do this, but oh well)
	 * <p>
	 * The resulting path will eliminate the duplication and be:
	 *  <p>
	 * <code>
	 * Patient.birthDate.verification
	 * </code>
	 * <p>
	 * @param elementPath
	 * @param elementName
	 * @return
	 */
	public static String generateExtensionPath(String elementPath, String elementName) {
		String[] pathComponents = elementPath.split("\\" + delimiter);
		String[] nameComponents = elementName.split("\\" + delimiter);
		StringBuilder prefix = new StringBuilder();
		int nameIndex = 0;
		for(String pathComponent : pathComponents) {
			if(!pathComponent.equals("extension")) {
				prefix.append(pathComponent).append(delimiter);
				if(pathComponent.equals(nameComponents[nameIndex])) {//There is overlap somehow.
					nameIndex += 1;
				} else {
					nameIndex = 0;
				}
			}
		}
		for(int i = nameIndex; i< nameComponents.length; i++) {
			prefix.append(nameComponents[i]);
			if(i < nameComponents.length - 1) {
				prefix.append(delimiter);
			}
		}
		return prefix.toString();
	}
	
	/**
	 * Returns the last component of a delimited path.
	 * If path is
	 * <p>
	 * <code>
	 * birthDate.verification
	 * </code>
	 * <p>
	 * Method will return
	 * <p>
	 * <code>
	 * verification
	 * </code>
	 * <p>
	 * @param path
	 * @return
	 */
	public static String getLastPathComponent(String path) {
		if(path != null && path.indexOf('.') >= 0) {
			return path.substring(path.lastIndexOf('.') + 1);
		} else {
			return path;
		}
	}
	
	/**
	 * Returns value set name from URI using the following convention:
	 * <p>
	 * <ul>
	 * 	<li>Any part after the last '/' is assumed to be the name of the value set.
	 * 	<li>If the name is separated by a Hyphen, the words are Upper Camel Cased.
	 *  <li>If URI ends with a terminal '/', null is returned.
	 *  <li>If the URI is null or empty, it will be echoed back.
	 *  <li>If the URI does not have a '/', null is returned.
	 * </ul>
	 * <p>
	 * For instance, 
	 * <p>
	 * <code>
	 * http://hl7.org/fhir/ValueSet/administrative-gender
	 * </code>
	 * <p>
	 * returns
	 * <p>
	 * <code>
	 * AdministrativeGender
	 * </code>
	 * <p>
	 * @param valueSetBindingUri
	 * @return
	 */
	public static String getEnumNameFromValueSetBindingUri(String valueSetBindingUri) {
		String returnValue = null;
		if(!StringUtils.isBlank(valueSetBindingUri)) {
			if(valueSetBindingUri.indexOf('/') >= 0 && valueSetBindingUri.substring(valueSetBindingUri.lastIndexOf('/')).length() > 1) {
				String suffix = valueSetBindingUri.substring(valueSetBindingUri.lastIndexOf('/') + 1);
				String[] components = suffix.split("-");
				returnValue = "";
				for(String component : components) {
					returnValue += StringUtils.capitalize(component);
				}
			} else {
				returnValue = null;
			}
		} else {
			returnValue = valueSetBindingUri;
		}
		return returnValue;
	}
}
