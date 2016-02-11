package ca.uhn.fhir.utils.common.st;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;

/**
 * Code generation framework using StringTemplate
 * 
 * @author Claude Nanjo
 *
 */
public abstract class TemplateUtils {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(TemplateUtils.class);
	
	private String templateFolderPath = "src/main/resources/templates";
	private STGroup groupMain;

	public TemplateUtils() {}
	
	/**
	 * Method initializes the template utility by loading
	 * the templates into memory.
	 * 
	 */
	public TemplateUtils initialize() {
		try {
			File rootMain = new File(templateFolderPath);
			groupMain = new STGroupDir(rootMain.getCanonicalPath());
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
