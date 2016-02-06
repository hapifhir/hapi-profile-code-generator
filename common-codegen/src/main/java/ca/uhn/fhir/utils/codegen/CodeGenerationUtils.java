package ca.uhn.fhir.utils.codegen;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaInterfaceSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.utils.common.metamodel.ClassField;
import ca.uhn.fhir.utils.common.metamodel.ClassModel;
import ca.uhn.fhir.utils.common.metamodel.Method;
import ca.uhn.fhir.utils.common.metamodel.MethodParameter;
import ca.uhn.fhir.utils.common.metamodel.ModifierEnum;

public class CodeGenerationUtils {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(CodeGenerationUtils.class);

	/**
	 * Generates the destination directory for code generation.
	 * <p>
	 * Precondition: destination directory must terminate with a File.separator (e.g., '/' in unix-based systems)
	 * <p>
	 * TODO Handle proper separators.
	 * 
	 * @param generatedPackage
	 * @param destinationDirectory
	 */
	public static void buildTargetPackageDirectoryStructure(String destinationDirectory, String generatedPackage) {
		String destinationPath = generateGeneratedSourcePathFromPackageAndRootLocation(destinationDirectory, generatedPackage);
		new File(destinationPath).mkdirs();
	}
	
	/**
	 * Builds the path for the generated code from:
	 * <ol>
	 * <li>The destination directory in the source path</li>
	 * <li>The source package</li>
	 * </ol>
	 * File.separator is used to delimit the path. All '.' in the
	 * package are replaced with the File.separator character.
	 * <p>
	 * Note that the destinationDirectory should not end with a path delimiter
	 * 
	 * @param destinationDirectory
	 * @param generatedPackage
	 * @return
	 */
	public static String generateGeneratedSourcePathFromPackageAndRootLocation(String destinationDirectory, String generatedPackage) {
		return destinationDirectory + File.separator + generatedPackage.replaceAll("\\.", File.separator);
	}

	/**
	 * Creates a java file in the specified package.
	 * <p>
	 * Precondition: Package should not terminate with a File.separator (e.g. '/' in unix-based systems)
	 * 
	 * @param packageName The java package for the generated java file.
	 * @param fileName The name of the java file.
	 * @param fileContent The content of the java file.
	 */
	public static void writeJavaClassFile(String destinationDirectory, String packageName, String fileName, String fileContent) {
		try(FileWriter writer = new FileWriter(generateGeneratedSourcePathFromPackageAndRootLocation(destinationDirectory, packageName) + File.separator + fileName + ".java")) {
			writer.write(fileContent);
			writer.close();
			LOGGER.debug("\n {}", fileContent);
		} catch(Exception e) {
			LOGGER.error("Error writing class file " + fileName);
			throw new RuntimeException("Error writing class file " + fileName, e);
		}
	}
	
	/**
	 * Cleans name so that it conforms to Java identifier
	 * specification.
	 * 
	 * @param name
	 * @return
	 */
	public static String makeIdentifierJavaSafe(String name) {
		String cleanedName = name.replaceAll("-", "");
		cleanedName = cleanedName.replaceAll("", "");
		return cleanedName;
	}
	
	/**
	 * Methods adds FieldSource to JavaClassSource based on the field definition
	 * specified by the ClassField.
	 * 
	 * @param classSource - The class to which the field is to be added
	 * @param field - The definition of the field to add to the JavaClassSource
	 */
	public static void addFieldToJavaClass(JavaClassSource classSource, ClassField field) {
		FieldSource<?> fieldSource = classSource.addField().setName(field.getName())
				  .setType(field.getType())
				  .setLiteralInitializer(field.getInitializer());
		for(ModifierEnum modifier: field.getModifiers()) {
			setFieldSourceModifier(fieldSource, modifier);
		}
	}
	
	public static void setFieldSourceModifier(FieldSource<?> fieldSource, ModifierEnum modifier) {
		if(modifier == ModifierEnum.PRIVATE) {
			fieldSource.setPrivate();
		} else if(modifier == ModifierEnum.PUBLIC) {
			fieldSource.setPublic();
		} else if(modifier == ModifierEnum.PROTECTED) {
			fieldSource.setProtected();
		} else if(modifier == ModifierEnum.FINAL) {
			fieldSource.setFinal(true);
		} else if(modifier == ModifierEnum.STATIC) {
			fieldSource.setStatic(true);
		} else if(modifier == ModifierEnum.TRANSIENT) {
			fieldSource.setTransient(true);
		} else if(modifier == ModifierEnum.VOLATILE) {
			fieldSource.setVolatile(true);
		}
	}
	
	/**
	 * Generic method to build a java interface
	 * 
	 * @param interfaceName
	 * @param accessors
	 * @return
	 */
	public static JavaInterfaceSource buildJavaInterface(ClassModel interfaceModel, String interfaceName) {
		final JavaInterfaceSource javaInterface = Roaster.create(JavaInterfaceSource.class);//Roaster.parse(JavaInterfaceSource.class, "import java.util.Map; public interface myInterface {public void setMeta(Map<String,String> meta);}" );
		javaInterface.setPackage(interfaceModel.getNamespace()).setName(interfaceName);
		for(Method accessor : interfaceModel.getMethods()) {
			if(accessor.isConstructor()) {//Interfaces don't specify constructors
				continue;
			}
			for(String importString : accessor.getImports()) { //TODO remove when Roaster fixes bug
				javaInterface.addImport(importString);
			}
			MethodSource<JavaInterfaceSource> methodSource = javaInterface.addMethod()
								.setPublic()
								.setName(accessor.getName());
			if(StringUtils.isNotBlank(accessor.getReturnType())) {
				methodSource.setReturnType(accessor.getReturnType());
			}
			for(MethodParameter argument : accessor.getParameters()) {
				methodSource.addParameter(argument.getValue(), argument.getName());
			}
			for(String importString : interfaceModel.getImports()) {
				javaInterface.addImport(importString);
			}
		}
		return javaInterface;
	}
	
	/**
	 * Generic method to build a java class
	 * 
	 * @param interfaceName
	 * @param accessors
	 * @return
	 */
	public static JavaClassSource buildJavaClass(ClassModel classModel, String adapterName) {
		final JavaClassSource javaClass = Roaster.create(JavaClassSource.class);
		javaClass.setPackage(classModel.getNamespace()).setName(adapterName);
		for(String classInterface : classModel.getInterfaces()) {
			javaClass.addInterface(classInterface);
		}
		for(ClassField field: classModel.getFields()) {
			CodeGenerationUtils.addFieldToJavaClass(javaClass, field);
		}
		if(classModel.getSupertypes() != null && 
				classModel.getSupertypes().size() > 0 && 
				StringUtils.isNotBlank(classModel.getSupertypes().get(0))) { //Java supports only single inheritance
			javaClass.setSuperType(classModel.getSupertypes().get(0));
		}
		for(Method methodDefinition: classModel.getMethods()) {
			for(String importString : methodDefinition.getImports()) {
				javaClass.addImport(importString);
			}
			MethodSource<JavaClassSource> methodSource = javaClass.addMethod().setPublic();
			if(methodDefinition.isConstructor()) {
				methodSource.setConstructor(true);
			}
			if(StringUtils.isNotBlank(methodDefinition.getName()) && !methodDefinition.isConstructor()) {
				methodSource.setName(methodDefinition.getName());
			}
			if(StringUtils.isNotBlank(methodDefinition.getReturnType())) {
					methodSource.setReturnType(methodDefinition.getReturnType());
			}
			methodSource.setBody(methodDefinition.getBody());
			for(MethodParameter argument : methodDefinition.getParameters()) {
				try {
					methodSource.addParameter(argument.getValue(), argument.getName());
				} catch(Exception e) {
					LOGGER.error("Error adding method parameter " + argument.getName() + " on method " + methodDefinition.getName() + " to class " + classModel.getName(), e);
				}
			}
		}
		for(String importString : classModel.getImports()) {
			javaClass.addImport(importString);
		}
		return javaClass;
	}
	
	/**
	 * Returns the last component of a delimited path
	 * 
	 * @param path
	 * @param delimiter
	 * @return
	 */
	public static String getLastPathComponent(String path, char delimiter) {
		return path.substring(path.lastIndexOf(delimiter) + 1);
	}
	
	/**
	 * Returns the last component of a '.' delimited path.
	 * For instance, for the following path:
	 * 
	 * <code>
	 * java.lang.String
	 * </code>
	 * 
	 * This method returns <code>String</code>.
	 * <p>
	 * @param path
	 * @return
	 */
	public static String getLastPathComponent(String path) {
		return getLastPathComponent(path, '.');
	}
	
	/**
	 * Method returns the suffix of a path starting from the resource of interest.
	 * For instance, for the path 'Condition.id' and the prefix 'Condition', this method returns the string
	 * 'id' as the suffix. It is the path relative to the containing resource.
	 * 
	 * @param prefix - The prefix, generally the name of the resource
	 * @param attributePath - The full attribute path as defined in the profile
	 * @return
	 */
	public static String getSuffix(String prefix, String attributePath) {
		String suffix = null;
		if((attributePath.indexOf('.')>= 0) && attributePath.startsWith(prefix)) {
			suffix = attributePath.substring(prefix.length() + 1);
		}
		return suffix;
	}
	
	/**
	 * Method takes a '.' delimited invocation path and returns a list
	 * of component split by the '.' delimiter.
	 * 
	 * For instance, Patient.contact.telecom.extension will be split
	 * and returned as list {Patient, contact, telecom, extension}
	 * 
	 * @param path
	 * @return
	 */
	public static List<String> getPathComponents(String path) {
		List<String> pathComponents = new ArrayList<String>();
		if(StringUtils.isNotBlank(path)) {
			String[] pathArray = path.split("\\.");
			pathComponents.addAll(Arrays.asList(pathArray));
		}
		return pathComponents;
	}

}
