package ca.uhn.fhir.utils.fhir;

import org.apache.commons.lang3.StringUtils;

/**
 * Utility class for handling miscellaneous FHIR paths manipulations.
 * 
 * @author cnanjo
 *
 */
public class PathUtils {
	
	private static final String delimiter = ".";
//	private static final String extensionPrefixDelimiter = "-"; 
	
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
	 * Preconditions: elementPath and elementName are not null. If either or both are null,
	 * the original path is returned.
	 * 
	 * @param elementPath
	 * @param elementName
	 * @return
	 */
	public static String generateExtensionPath(String elementPath, String elementName) {
		String newPath = elementPath;
		if(elementPath.indexOf("extension") > 0 && elementName != null) {
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
			newPath = prefix.toString();
		}
		return newPath;
	}
	
	/**
	 * Returns the last component of a '.' delimited path.
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
		return getLastPathComponent(path, '.');
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
	public static String getLastPathComponent(String path, char delimiter) {
		if(path != null && path.indexOf(delimiter) >= 0) {
			return path.substring(path.lastIndexOf(delimiter) + 1);
		} else {
			return path;
		}
	}
	
	/**
	 * Returns the path minus the first component of the path.
	 * 
	 * If path is
	 * <p>
	 * <code>
	 * Condition.evidence.details
	 * </code>
	 * <p>
	 * Method will return
	 * <p>
	 * <code>
	 * evidence.details
	 * </code>
	 * <p>
	 * @param path
	 * @return
	 */
	public static String getPathMinusRootComponent(String path) {
		if(path != null && path.indexOf('.') >= 0) {
			return path.substring(path.indexOf('.') + 1);
		} else {
			return null;
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
		if(StringUtils.isNotBlank(valueSetBindingUri)) {
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
	
	/**
	 * Method returns the number of extension levels in path.
	 * Patient.race has 0 extension levels.
	 * Patient.extension.extension has 2 levels.
	 * @param path
	 * @return
	 */
	public static int getExtensionLevelInPath(String path) {
		return StringUtils.countMatches(path, ".extension");
	}
	
	public static String getNonExtensionRootPath(String path) {
		if(StringUtils.isNotBlank(path) && path.indexOf(".extension") >= 0) {
			return path.substring(0, path.indexOf(".extension"));
		} else {
			return null;
		}
	}
	
	/**
	 * Returns the path for the parent component. For instance,
	 * if the path is Patient.clinicalTrial.NCT, this method
	 * will return the path Patient.clinicalTrial.
	 * 
	 * @param path
	 * @return
	 */
	public static String getPathPrefix(String path) {
		if(StringUtils.isBlank(path) || path.indexOf('.') < 0) {
			return path;
		} else {
			return path.substring(0, path.lastIndexOf('.'));
		}
	}
	
	/**
	 * Returns the extension path from a path that may have
	 * an anchor. For instance, the path:
	 * 
	 * http://hl7.org/fhir/StructureDefinition/patient-clinicalTrial#NCT
	 * 
	 * will be returned as:
	 * 
	 * http://hl7.org/fhir/StructureDefinition/patient-clinicalTrial
	 * @return
	 */
	public static String getExtensionRootPath(String path) {
		if(StringUtils.isBlank(path) || path.indexOf('#') < 0) {
			return path;
		} else {
			return path.substring(0, path.lastIndexOf('#'));
		}
	}
	
	
	/**
	 * Returns the anchor from a path that may have
	 * an anchor. For instance, the path:
	 * 
	 * http://hl7.org/fhir/StructureDefinition/patient-clinicalTrial#NCT
	 * 
	 * will be returned as:
	 * 
	 * NCT
	 * 
	 * @return
	 */
	public static String getExtensionAnchor(String path) {
		if(StringUtils.isBlank(path) || path.indexOf('#') < 0) {
			return null;
		} else {
			return path.substring(path.lastIndexOf('#') + 1);
		}
	}
	
	public static String getExtensionName(String path) {
		String name = getExtensionAnchor(path);
		if(name == null) {
			name = getLastResourcePathComponent(path);
		}
		return name;
	}
	
	/**
	 * Returns the last part of a '/' delimited path:
	 * 
	 * http://hl7.org/fhir/StructureDefinition/patient-clinicalTrial
	 * 
	 * will be returned as:
	 * 
	 * patient-clinicalTrial
	 * 
	 * @return
	 */
	public static String getLastResourcePathComponent(String path) {
		if(StringUtils.isBlank(path) || path.indexOf('/') < 0) {
			return null;
		} else {
			return path.substring(path.lastIndexOf('/') + 1);
		}
	}
}
