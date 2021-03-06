package ca.uhn.fhir.utils.fhir.dstu3;

import org.apache.commons.lang3.StringUtils;
import org.hl7.fhir.dstu3.model.ElementDefinition;
import org.hl7.fhir.dstu3.model.StructureDefinition;

import ca.uhn.fhir.utils.fhir.PathUtils;

/**
 * Handles path slicing by providing full named paths in element.name
 * 
 * @author cnanjo
 *
 */
public class StructureDefinitionPreprocessor {
	private int currentLevel = 0;
	private int previousLevel = 0;
	private int delta = 0;
	private String rootPath;
//	private Stack nodeStack;
	private StructureDefinition profile;
	
	public StructureDefinitionPreprocessor(StructureDefinition profile) {
		this.profile = profile;
	}
	
	public void processElements() {
		for(ElementDefinition element: profile.getSnapshot().getElement()) {
			if(!isProfileMetaElement(element)) {
				currentLevel = PathUtils.getExtensionLevelInPath(element.getPath());
				if(currentLevel > 0) {
					if(StringUtils.isBlank(rootPath)) {
						rootPath = PathUtils.getNonExtensionRootPath(element.getPath());
					}
					delta = currentLevel - previousLevel;
					System.out.println(element.getPath() + "  -  " + element.getSliceName() + ":" + currentLevel);
					if(delta <= 0) {
						for(int i = 0; i < -delta + 1; i++) {
							rootPath = PathUtils.getPathPrefix(rootPath);
						}
					} 
					rootPath += "." + PathUtils.getLastPathComponent(element.getSliceName());
					System.out.println(rootPath + ":" + currentLevel);
					System.out.println("---------------");
					element.setPath(rootPath);
				} else {
					rootPath = null;
				}
				previousLevel = currentLevel;
			}
		}
	}
	
	/**
	 * Method will flag all StructureDefinition elements that are metadata definitions.
	 * These includes:
	 * <ul>
	 * 	<li>Resource.extension.id
	 *  <li>Resource.extension.extension
	 *  <li>Resource.extension.url
	 *  <li>Resource.extension.value[x]
	 * </ul>
	 * 
	 * @param element
	 * @return
	 */
	public static boolean isProfileMetaElement(ElementDefinition element) {
		boolean isMeta = false;
		String path = element.getPath();
		isMeta = (path.indexOf(".extension") > 0 ||
				 path.indexOf(".modifierExtension") > 0 ||
				 path.indexOf(".extension.id") > 0 ||
				 path.indexOf(".extension.extension") > 0 ||
				 path.indexOf(".extension.url") > 0 ||
				 path.indexOf(".extension.value[x]") > 0 ) &&
				 (element.getSliceName() == null || element.getSliceName().equals("extension"));
		return isMeta;
	}
}
