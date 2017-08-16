package ca.uhn.fhir.utils.common.st;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Code generation framework using StringTemplate
 * 
 * @author Claude Nanjo
 *
 */
public abstract class TemplateUtils {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(TemplateUtils.class);

	private String templateFolderPath = "/templates";
	private String templateFolderPathCommon = "/templates/common";
	private String templateFolderPathDstu2 = "/templates/dstu2";
	private String templateFolderPathDstu3 = "/templates/dstu3";
	private STGroup groupMain;
	private STGroup groupCommon;
	private STGroup groupDstu2;
	private STGroup groupDstu3;

	public TemplateUtils() {}
	
	/**
	 * Method initializes the template utility by loading
	 * the templates into memory.
	 *
	 * @param resourceRoot
	 */
	public TemplateUtils initialize(String resourceRoot) {
		try {

			File rootMain;
			try {
				rootMain = new File(resourceRoot + templateFolderPath);
				groupMain = new STGroupDir(rootMain.getCanonicalPath());
			} catch (Exception e) {
				LOGGER.warn(e.getMessage());
			}

			try {
				File rootCommon = new File(resourceRoot + templateFolderPathCommon);
				groupCommon = new STGroupDir(rootCommon.getCanonicalPath());
				groupMain.importTemplates(groupCommon);
			} catch (Exception e) {
				LOGGER.warn(e.getMessage());
			}

			try {
				File rootDstu2 = new File(resourceRoot + templateFolderPathDstu2);
				groupMain.importTemplates(groupDstu2);
				groupDstu2 = new STGroupDir(rootDstu2.getCanonicalPath());
			} catch (Exception e) {
				LOGGER.warn(e.getMessage());
			}

			try {
				File rootDstu3 = new File(resourceRoot + templateFolderPathDstu3);
				groupDstu3 = new STGroupDir(rootDstu3.getCanonicalPath());
				groupMain.importTemplates(groupDstu3);
			} catch (Exception e) {
				LOGGER.warn(e.getMessage());
			}
			return this;
		} catch(Exception e) {
			LOGGER.error("Error initializing StringTemplate. Validate template path: " + templateFolderPath, e);
			throw new RuntimeException("Error initializing StringTemplate. Validate template path: " + templateFolderPath, e);
		}
	}
	
	public STGroup getGroupMain() {
		return groupMain;
	}

	public void setGroupMain(STGroup groupMain) {
		this.groupMain = groupMain;
	}

	/**
	 * Returns path to templates.
	 * 
	 * @return
	 */
	public String getTemplateFolderPath() {
		return templateFolderPath;
	}
	
	/**
	 * Sets path to directory containing the templates
	 * 
	 * @param templateFolderPath Path to the folder containing the ST templates
	 */
	public void setTemplateFolderPath(String templateFolderPath) {
		this.templateFolderPath = templateFolderPath;
	}
	
	public String getTemplateInstance(String templateName, String parameterName, String parameterValue) {
		Map<String, String> parameters = new HashMap<String,String>();
		parameters.put(parameterName, parameterValue);
		return getTemplateInstance(templateName, parameters);
	}
	
	
	public String getTemplateInstance(String templateName, Map<String,String> parameters) {
		String filledTemplateInstance = null;
		ST st = groupMain.getInstanceOf(templateName);
		if(st != null) {
			for(String parameterName : parameters.keySet()) {
				st.add(parameterName, parameters.get(parameterName));
			}
			filledTemplateInstance = st.render();
		} else {
			throw new RuntimeException("Error. Template not found: " + templateName);
		}
		return filledTemplateInstance;
	}

}
