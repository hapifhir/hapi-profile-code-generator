package ca.uhn.fhir.utils.codegen.hapi;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import ca.uhn.fhir.utils.fhir.PathUtils;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.Import;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaInterfaceSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;
import ca.uhn.fhir.utils.codegen.CodeGenerationUtils;
import ca.uhn.fhir.utils.codegen.hapi.dstu2.FhirResourceManagerDstu2;
import ca.uhn.fhir.utils.codegen.hapi.dstu2.GenerateLogicalViewCommandDstu2;
import ca.uhn.fhir.utils.codegen.hapi.dstu3.FhirResourceManagerDstu3;
import ca.uhn.fhir.utils.codegen.hapi.dstu3.GenerateLogicalViewCommandDstu3;
import ca.uhn.fhir.utils.codegen.hapi.methodgenerator.MethodHandlerResolver;
import ca.uhn.fhir.utils.codegen.methodgenerators.IMethodHandler;
import ca.uhn.fhir.utils.common.metamodel.ClassField;
import ca.uhn.fhir.utils.common.metamodel.ClassModel;
import ca.uhn.fhir.utils.common.metamodel.Method;
import ca.uhn.fhir.utils.common.metamodel.MethodParameter;
import ca.uhn.fhir.utils.common.metamodel.ModifierEnum;
import ca.uhn.fhir.utils.fhir.dstu2.ProfileTreeBuilder;

/**
 * Class processes FHIR profiles and generates a logical interface
 * and an implementation class for that interface. The adapter wraps 
 * a HAPI FHIR class.
 * <p>
 * The logical interface is expressed as a Java Interface that provides
 * convenience methods for easier handling of FHIR profiles within rule
 * engines such as Drools.
 * <p>
 * A requirement for code generation is that a FHIR instance can be seamlessly
 * wrapped by such an adapter class, which then exposes to the calling application
 * a logical interface primarily through method invocation delegation to the
 * underlying HAPI FHIR class.
 * <p>
 * Note: FHIR extensions added at runtime are exposed by neither the interface nor adapter. 
 * Extensions can be added by getting the underlying FHIR object and adding extensions
 * as necessary. Such extensions, though, are not part of the logical interface and thus 
 * invisible to the underlying Drools engine.
 * <p>
 * Limitations:
 * <ul>
 * <li>Composite profiles must first be merged before code generation can occur.
 * <li>In addition, profiles extending other profiles are not supported at this time.
 * </ul>
 * TODO Be sure to use HAPI annotations on the generation
 * 
 * @author Claude Nanjo
 *
 */
public class InterfaceAdapterGenerator {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(InterfaceAdapterGenerator.class);
	public static final String DEFAULT_DESTINATION_DIRECTORY = "generated-source/java";
	public static final String ADAPTER_FIELD_NAME = "adaptedClass";
	public static final String PROCESSING_INSTRUCTION = "processing_instruction";
	public static final String DO_NOT_PROCESS = "do_not_process";
	
	private String generatedPackage;
	private String destinationDirectory;
	private FhirResourceManagerDstu2 fhirResourceManager;
	private FhirResourceManagerDstu3 fhirResourceManagerDstu3;
	private MethodBodyGenerator templateUtils;
	private List<String> resourceGenerationPlan;
	private MethodHandlerResolver resolver;
	private ProfileTreeBuilder profileTreeBuilder;
	private ca.uhn.fhir.utils.fhir.dstu3.ProfileTreeBuilder profileTreeBuilderDstu3;
	
	/**
	 * Precondition: 
	 * 
	 * <ul>
	 * <li> CodeTemplateUtils argument requires prior initialization.
	 * <li> FhirResourceManager requires prior initialization.
	 * </ul>
	 * 
	 * @param generatedPackage The name of the java package for the generated code
	 * @param fhirResourceManager A FHIR Resource Manager required to access profiles and extensions for code generation
	 * @param templateUtils The Code Template Utility used for code generation
	 */
	public InterfaceAdapterGenerator(String generatedPackage, FhirResourceManagerDstu2 fhirResourceManager, MethodBodyGenerator templateUtils) {
		this.generatedPackage = generatedPackage;
		this.templateUtils = templateUtils;
		this.resourceGenerationPlan = new ArrayList<String>();
		this.fhirResourceManager = fhirResourceManager;
		this.resolver = new MethodHandlerResolver(fhirResourceManager, templateUtils);
	}
	
	/**
	 * Precondition: 
	 * 
	 * <ul>
	 * <li> CodeTemplateUtils argument requires prior initialization.
	 * <li> FhirResourceManager requires prior initialization.
	 * </ul>
	 * 
	 * @param generatedPackage The name of the java package for the generated code
	 * @param fhirResourceManager A FHIR Resource Manager required to access profiles and extensions for code generation
	 * @param templateUtils The Code Template Utility used for code generation
	 */
	public InterfaceAdapterGenerator(String generatedPackage, FhirResourceManagerDstu3 fhirResourceManager, MethodBodyGenerator templateUtils) {
		this.generatedPackage = generatedPackage;
		this.templateUtils = templateUtils;
		this.resourceGenerationPlan = new ArrayList<String>();
		this.fhirResourceManagerDstu3 = fhirResourceManager;
		//this.resolver = new MethodHandlerResolver(fhirResourceManager, templateUtils);
	}
	
	/**
	 * Returns the java package for the generated code
	 * 
	 * @return
	 */
	public String getGeneratedPackage() {
		return generatedPackage;
	}


	/**
	 * Sets the java package for the generated code
	 * 
	 * @param generatedPackage
	 */
	public void setGeneratedPackage(String generatedPackage) {
		this.generatedPackage = generatedPackage;
	}


	/**
	 * Returns the destination directory for code generation. If none is specified, it will
	 * use the DEFAULT_DESTINATION_DIRECTORY
	 * 
	 * @return
	 */
	public String getDestinationDirectory() {
		if(destinationDirectory != null) {
			return destinationDirectory;
		} else {
			return DEFAULT_DESTINATION_DIRECTORY;
		}
	}

	/**
	 * Sets the destination directory for code generation.
	 * @param destinationDirectory
	 */
	public void setDestinationDirectory(String destinationDirectory) {
		this.destinationDirectory = destinationDirectory;
	}
	
	/**
	 * Adds a profile to process
	 * 
	 * @param resource
	 */
	public void addToPlan(String resource) {
		resourceGenerationPlan.add(resource);
	}
	
	/**
	 * Sets list of profiles to process.
	 * A list of profiles to process is called a plan.
	 * 
	 * @param plan
	 */
	public void setResourceLoadingPlan(List<String> plan) {
		this.resourceGenerationPlan = plan;
	}

	/**
	 * Method build AdapterFactory and runs through list of profiles
	 * to generate both an Interface and Adapter class for each profile.
	 * <p>
	 * @param failOnError Flag indicating whether plan processing should stop upon
	 * encountering an error. If true, errors are silently ignored.
	 */
	public void executePlan(boolean failOnError) {
		buildFactory();
		for(String resourceName : resourceGenerationPlan) {
			try {
				generateInterfaceAndAdapter(resourceName);
			} catch(Exception e) {
				LOGGER.error("Error generating " + resourceName, e);
				if(failOnError) {
					throw new RuntimeException("Error generating " + resourceName, e);
				}
			}
		}
	}
	
	public void executePlanDstu3(boolean failOnError) {
		buildFactoryDstu3(getGeneratedPackage());//TODO Must handle this
		for(String resourceName : resourceGenerationPlan) {
			try {
				generateInterfaceAndAdapterDstu3(resourceName);
			} catch(Exception e) {
				LOGGER.error("Error generating " + resourceName, e);
				if(failOnError) {
					throw new RuntimeException("Error generating " + resourceName, e);
				}
			}
		}
	}
	
	/**
	 * Methods reads in a FHIR StructureDefinition and generates the corresponding java 'logical' interface and adapter class 
	 * which wrap a HAPI FHIR class.
	 * 
	 * @param profileName
	 */
	public void generateInterfaceAndAdapter(String profileName) {
		try {
			String javaSafeProfileName = CodeGenerationUtils.makeIdentifierJavaSafe(profileName);
			StructureDefinition profile = fhirResourceManager.getProfile(profileName);
			//List<Method> methodDefinitions = generateMethodsFromFhirProfileDefinition(profile);
			//InterfaceAdapterPair interfaceAdapterPair = generateCodeFromFhirProfile(javaSafeProfileName, profile, methodDefinitions);
			//Node<ElementDefinitionDt> root = profileWalker.getRoot();
			profileTreeBuilder = new ProfileTreeBuilder(profile);
			profileTreeBuilder.initialize();
			GenerateLogicalViewCommandDstu2 command = new GenerateLogicalViewCommandDstu2(profile, fhirResourceManager, templateUtils, resolver, "org.hspc.fhir.generated");
			profileTreeBuilder.getRoot().executeCommandDepthFirstPost(command);
			ClassModel rootModel = command.getClassMap().get(profileTreeBuilder.getRoot().getPathFromRoot());
			String resourceName = getUnderlyingFhirCoreResourceName(profile.getName(), profile.getBase());
			buildAdapter(rootModel, resourceName, generatedPackage + "." + generateInterfaceName(javaSafeProfileName));
			for(ClassModel model : command.getClassMap().values()) {
				//fhirResourceManager.getFullyQualifiedJavaType(node.getParent().getPayload().getTypeFirstRep());
				if(model != rootModel && canProcess(model)) {//getAdaptee, setAdaptee, no-arg constructor, adaptee constructor
					try {
						String supportingClass = InterfaceAdapterGenerator.cleanUpWorkaroundClass(CodeGenerationUtils.buildJavaClass(model, javaSafeProfileName + model.getName()), true);
						CodeGenerationUtils.writeJavaClassFile(getDestinationDirectory(), generatedPackage, javaSafeProfileName + model.getName(), supportingClass);
					}catch(Exception e) {
						e.printStackTrace();
						LOGGER.error("Error processing " + model.getName(), e);
					}
				}
			}
			String generatedProfileAdapter = cleanUpWorkaroundClass(CodeGenerationUtils.buildJavaClass(rootModel, generateAdapterName(javaSafeProfileName)), true);
			String generatedProfileInterface = cleanUpWorkaroundInterface(CodeGenerationUtils.buildJavaInterface(rootModel, generateInterfaceName(javaSafeProfileName)), true);
			CodeGenerationUtils.buildTargetPackageDirectoryStructure(getDestinationDirectory(), generatedPackage);
			CodeGenerationUtils.writeJavaClassFile(getDestinationDirectory(), generatedPackage, generateInterfaceName(javaSafeProfileName), generatedProfileInterface);
			CodeGenerationUtils.writeJavaClassFile(getDestinationDirectory(), generatedPackage, generateAdapterName(javaSafeProfileName), generatedProfileAdapter);
		} catch(Exception e) {
			LOGGER.error("Error generating code", e);
			throw new RuntimeException("Error generating code", e);
		}
	}
	
	/**
	 * Methods reads in a FHIR StructureDefinition and generates the corresponding java 'logical' interface and adapter class 
	 * which wrap a HAPI FHIR class.
	 * 
	 * @param profileName
	 */
	public void generateInterfaceAndAdapterDstu3(String profileName) {
		try {
			String javaSafeProfileName = CodeGenerationUtils.makeIdentifierJavaSafe(profileName);
			org.hl7.fhir.dstu3.model.StructureDefinition profile = fhirResourceManagerDstu3.getProfile(profileName);
			profileTreeBuilderDstu3 = new ca.uhn.fhir.utils.fhir.dstu3.ProfileTreeBuilder(profile);
			profileTreeBuilderDstu3.initialize();
			GenerateLogicalViewCommandDstu3 command = new GenerateLogicalViewCommandDstu3(profile, fhirResourceManagerDstu3, templateUtils, getGeneratedPackage());
			profileTreeBuilderDstu3.getRoot().executeCommandDepthFirstPost(command);
			ClassModel rootModel = command.getClassMap().get(profileTreeBuilderDstu3.getRoot().getPathFromRoot());
			for(ClassModel model : command.getClassMap().values()) {
				System.out.println("Processing Model : " + model);
				if(model != rootModel && canProcess(model)) {
					try {
						String supportingClass = InterfaceAdapterGenerator.cleanUpWorkaroundClass(CodeGenerationUtils.buildJavaClass(model, model.getName()), true);
						CodeGenerationUtils.writeJavaClassFile(getDestinationDirectory(), generatedPackage, model.getName(), supportingClass);
					}catch(Exception e) {
						e.printStackTrace();
						LOGGER.error("Error processing " + model.getName(), e);
					}
				}
			}
			String generatedProfileAdapter = cleanUpWorkaroundClass(CodeGenerationUtils.buildJavaClass(rootModel, rootModel.getName()), true);
			String generatedProfileInterface = cleanUpWorkaroundInterface(CodeGenerationUtils.buildJavaInterface(rootModel, PathUtils.getLastPathComponent(rootModel.getInterfaces().get(0))), true);
			CodeGenerationUtils.buildTargetPackageDirectoryStructure(getDestinationDirectory(), generatedPackage);
			CodeGenerationUtils.writeJavaClassFile(getDestinationDirectory(), generatedPackage, generateInterfaceName(javaSafeProfileName), generatedProfileInterface);
			CodeGenerationUtils.writeJavaClassFile(getDestinationDirectory(), generatedPackage, generateAdapterName(javaSafeProfileName), generatedProfileAdapter);
		} catch(Exception e) {
			e.printStackTrace();
			LOGGER.error("Error generating code", e);
			throw new RuntimeException("Error generating code", e);
		}
	}
	
	public static String generateAdapterName(StructureDefinition profile) {
		String javaSafeProfileName = CodeGenerationUtils.makeIdentifierJavaSafe(profile.getName());
		return generateAdapterName(javaSafeProfileName);
	}
	
	public static String generateAdapterName(String javaSafeProfileName) {
		return javaSafeProfileName + "Adapter";
	}
	
	public static String generateInterfaceName(StructureDefinition profile) {
		String javaSafeProfileName = CodeGenerationUtils.makeIdentifierJavaSafe(profile.getName());
		return generateInterfaceName(javaSafeProfileName);
	}
	
	public static String generateInterfaceName(String javaSafeProfileName) {
		return "I" + javaSafeProfileName;
	}
	
	/**
	 * Method generates corresponding methods for element argument.
	 * 
	 * @param profile
	 * @param methodDefinitions
	 * @param element
	 */
	protected void handleElement(StructureDefinition profile,
			List<Method> methodDefinitions, ElementDefinitionDt element) {
		try {
			//methodDefinitions.addAll(FhirMethodGenerator.generateAccessorMethods(profile, element, fhirResourceManager, extensionDefUri));
			IMethodHandler handler = resolver.identifyHandler(profile, element, generatedPackage);
			if(handler != null) {
				methodDefinitions.addAll(handler.buildCorrespondingMethods());
			}
		} catch(Exception e) {
			LOGGER.error("Error processing element " + element.getName() + ". Skipping element", e);
		}
	}
	
	/**
	 * Method returns the name of the profile and, if the name of the profile does not correspond to a core 
	 * FHIR resource, the name of the base resource from which this profile is derived. (Assumes a single 
	 * level profile hierarchy from FHIR core at this time. Profiles derived from profiles not supported at this
	 * time).
	 * 
	 * @param profile
	 * @return
	 */
	public String getUnderlyingFhirCoreResourceName(String profileName, String profileBase) {
		String name = profileName;
		if(fhirResourceManager.getResourceNameToClassMap().get(profileName) == null) {
			name = fhirResourceManager.getResource(profileBase);
		}
		return name;
	}
	
	/**
	 * Method returns the name of the profile and, if the name of the profile does not correspond to a core 
	 * FHIR resource, the name of the base resource from which this profile is derived. (Assumes a single 
	 * level profile hierarchy from FHIR core at this time. Profiles derived from profiles not supported at this
	 * time).
	 * 
	 * @param profile
	 * @return
	 */
	public String getUnderlyingFhirCoreResourceNameDstu3(String profileName, String profileBase) {
		String name = profileName;
		if(fhirResourceManagerDstu3.getResourceNameToClassMap().get(profileName) == null) {
			name = fhirResourceManagerDstu3.getResource(profileBase);
		}
		return name;
	}
	
	public void buildAdapter(ClassModel classModel, String resourceName, String interfaceName) {
		classModel.addInterface(interfaceName);
		String type = fhirResourceManager.getResourceNameToClassMap().get(resourceName).getCanonicalName();
		//addAdapteeField(classModel, type);
	}
	
	public static void addAdapteeField(ClassModel classModel, String type) {
		ClassField field = new ClassField("adaptedClass");
		field.setType(type);//fhirResourceManager.getResourceNameToClassMap().get(resourceName).getCanonicalName());
		field.addModifier(ModifierEnum.PRIVATE);
		//field.setInitializer("new " + fhirResourceManager.getResourceNameToClassMap().get(resourceName).getSimpleName() + "()");
		classModel.addField(field);
		classModel.addImport(type);//fhirResourceManager.getResourceNameToClassMap().get(resourceName).getName());
		classModel.addImport(ca.uhn.fhir.model.api.ExtensionDt.class.getCanonicalName());
	}
	
	/**
	 * Method acts as profile element filter. 
	 * If it returns true, the element should be filtered out.
	 * 
	 * @param element
	 * @return
	 */
	protected boolean skipProcessing(ElementDefinitionDt element) {
		boolean skipProcessing = false;
		if(element.getPath().contains(".extension") && element.getName() == null) {
			skipProcessing = true;
		}
		return skipProcessing;
	}

	/**
	 *
	 * @param bodyGenerator
	 * @param adapterType
	 * @param model
     */
	public static void generateConstructors(MethodBodyGenerator bodyGenerator, String adapterType, ClassModel model) {
		Method noArgConstructor = new Method();
		noArgConstructor.isConstructor(true);
		noArgConstructor.setBody(bodyGenerator.getInitializeVariableStatement(ADAPTER_FIELD_NAME, adapterType));
		model.addMethodAtIndex(0, noArgConstructor);
		
		Method singleArgConstructor = new Method();
		singleArgConstructor.addParameter("adaptee", adapterType);
		singleArgConstructor.isConstructor(true);
		singleArgConstructor.setBody(bodyGenerator.getAssignVariableStatement(ADAPTER_FIELD_NAME, "adaptee"));
		model.addMethodAtIndex(1, singleArgConstructor);
	}

	/**
	 *
	 * @param model
	 * @param resourcePath
     */
	public static void generateAdapteeGetter(ClassModel model, String resourcePath) {
		Method method = new Method();
		method.setName("getAdaptee");
		method.setReturnType(resourcePath);
		method.setBody("return adaptedClass;");
		if(model.getMethodCount() > 2) {
			model.addMethodAtIndex(2, method);
		} else {
			model.addMethod(method);
		}
	}
	
	/**
	 * Method generates accessor to underlying HAPI FHIR Adapter
	 * 
	 * @param accessors
	 * @param resourcePath
	 */
	public static void generateAdapteeSetter(ClassModel model, String resourcePath) {
		Method method = new Method();
		method.setName("setAdaptee");
		List<MethodParameter> params = new ArrayList<MethodParameter>();
		params.add(new MethodParameter("param", resourcePath));
		method.setParameters(params);
		method.setBody("this.adaptedClass = param;");
		if(model.getMethodCount() > 3) {
			model.addMethodAtIndex(3, method);
		} else {
			model.addMethod(method);
		}
	}
	
	/**
	 * Method build the Adapter factory - a class that uses reflection to properly
	 * convert incoming FHIR message to the appropriate adapter, provided one exists.
	 * 
	 */
	public void buildFactory() {
		FileWriter writer = null;
		try {
			final JavaClassSource javaClass = Roaster.create(JavaClassSource.class);
			javaClass.setPackage(getGeneratedPackage()).setName("AdapterFactory");
			javaClass.addImport(org.slf4j.LoggerFactory.class);
			javaClass.addImport(java.util.List.class);
			javaClass.addImport(ca.uhn.fhir.model.api.BundleEntry.class);
			javaClass.addImport(ca.uhn.fhir.model.api.IResource.class);
			javaClass.addField().setName("GENERATED_PACKAGE_PREFIX").setStringInitializer(getGeneratedPackage() + ".").setStatic(true).setPublic().setFinal(true).setType(java.lang.String.class);
			javaClass.addField().setName("HAPI_FHIR_RESOURCE_PREFIX").setStringInitializer(getGeneratedPackage() + ".resource.").setStatic(true).setPublic().setFinal(true).setType(java.lang.String.class);
			javaClass.addField().setName("LOGGER").setLiteralInitializer("LoggerFactory.getLogger(AdapterFactory.class)").setStatic(true).setPublic().setFinal(true).setType(org.slf4j.Logger.class);
			javaClass.addMethod().setPublic().setStatic(true).setName("adapt").setReturnType("java.util.Map").setParameters("ca.uhn.fhir.model.api.Bundle bundle").setBody(templateUtils.getAdaptBundle());
			javaClass.addMethod().setPublic().setStatic(true).setName("adapt").setReturnType("java.lang.Object").setParameters("ca.uhn.fhir.model.api.IResource resource").setBody(templateUtils.getAdaptResource());
			String factoryString = cleanUpWorkaroundClass(javaClass, true);
			CodeGenerationUtils.buildTargetPackageDirectoryStructure(getDestinationDirectory(), getGeneratedPackage());
//			writer = new FileWriter("generated-source/java/org/hspc/fhir/dstu2/generated/AdapterFactory.java");
			writer = new FileWriter(getDestinationDirectory() + "/AdapterFactory.java");
			writer.write(factoryString);
			LOGGER.debug("\n {}", factoryString);
		} catch(Exception e) {
			LOGGER.error("Error generating AdapterFactory code", e);
			throw new RuntimeException("Error generating AdapterFactory code", e);
		} finally {
			try{writer.close();}catch(Exception e){LOGGER.error("Error closing writer", e);}
		}
	}
	
	/**
	 * Method build the Adapter factory - a class that uses reflection to properly
	 * convert incoming FHIR message to the appropriate adapter, provided one exists.
	 * 
	 */
	public void buildFactoryDstu3(String generatedPackage) {
		FileWriter writer = null;
		try {
			setGeneratedPackage(generatedPackage);
			final JavaClassSource javaClass = Roaster.create(JavaClassSource.class);
			javaClass.setPackage(this.generatedPackage).setName("AdapterFactory");
			javaClass.addImport(org.slf4j.LoggerFactory.class);
			javaClass.addImport(java.util.List.class);
			javaClass.addImport(ca.uhn.fhir.model.api.BundleEntry.class);
			javaClass.addImport(ca.uhn.fhir.model.api.IResource.class);
			javaClass.addField().setName("GENERATED_PACKAGE_PREFIX").setStringInitializer(generatedPackage + ".").setStatic(true).setPublic().setFinal(true).setType(java.lang.String.class);
			javaClass.addField().setName("HAPI_FHIR_RESOURCE_PREFIX").setStringInitializer("org.hl7.fhir.dstu3.model.").setStatic(true).setPublic().setFinal(true).setType(java.lang.String.class);
//			javaClass.addField().setName("HAPI_FHIR_RESOURCE_PREFIX").setStringInitializer("ca.uhn.fhir.model.dstu2.resource.").setStatic(true).setPublic().setFinal(true).setType(java.lang.String.class);
			javaClass.addField().setName("LOGGER").setLiteralInitializer("LoggerFactory.getLogger(AdapterFactory.class)").setStatic(true).setPublic().setFinal(true).setType(org.slf4j.Logger.class);
			javaClass.addMethod().setPublic().setStatic(true).setName("adapt").setReturnType("java.util.Map").setParameters("ca.uhn.fhir.model.api.Bundle bundle").setBody(templateUtils.getAdaptBundle());
			javaClass.addMethod().setPublic().setStatic(true).setName("adapt").setReturnType("java.lang.Object").setParameters("ca.uhn.fhir.model.api.IResource resource").setBody(templateUtils.getAdaptResource());
			String factoryString = cleanUpWorkaroundClass(javaClass, true);
			CodeGenerationUtils.buildTargetPackageDirectoryStructure(getDestinationDirectory(), getGeneratedPackage());
//			writer = new FileWriter("generated-source/java/org/hspc/fhir/dstu3/generated/AdapterFactory.java");
			writer = new FileWriter(CodeGenerationUtils.generateGeneratedSourcePathFromPackageAndRootLocation(getDestinationDirectory(), getGeneratedPackage())+ "/AdapterFactory.java");
			writer.write(factoryString);
			LOGGER.debug("\n {}", factoryString);
		} catch(Exception e) {
			LOGGER.error("Error generating AdapterFactory code", e);
			throw new RuntimeException("Error generating AdapterFactory code", e);
		} finally {
			try{writer.close();}catch(Exception e){LOGGER.error("Error closing writer", e);}
		}
	}
	
	/**
	 * Bug with ROASTER generation of inner classes. Workaround for now.
	 * 
	 * TODO Combine both cleanup methods.
	 * 
	 * @param profileInterface
	 * @param cleanup
	 * @return
	 */
	public static String cleanUpWorkaroundInterface(JavaInterfaceSource profileInterface, boolean cleanup) {
		String profileInterfaceValue = null;
		if(cleanup) {
			List<Import> imports = profileInterface.getImports();
			for(Import importStatement : imports) {
				if(importStatement.toString().contains("$")) {
					profileInterface.removeImport(importStatement);
				}
			}
			profileInterfaceValue = profileInterface.toString().replace('$', '.');
		} else {
			profileInterfaceValue = profileInterface.toString();
		}
		return profileInterfaceValue;
	}
	
	/**
	 * Bug with ROASTER generation of inner classes. Workaround for now.
	 * 
	 * @param profileInterface
	 * @param cleanup
	 * @return
	 */
	public static String cleanUpWorkaroundClass(JavaClassSource adapterClass, boolean cleanup) {
		String profileInterfaceValue = null;
		if(cleanup) {
			List<Import> imports = adapterClass.getImports();
			for(Import importStatement : imports) {
				if(importStatement.toString().contains("$")) {
					adapterClass.removeImport(importStatement);
				}
			}
			profileInterfaceValue = adapterClass.toString().replace('$', '.');
		} else {
			profileInterfaceValue = adapterClass.toString();
		}
		return profileInterfaceValue;
	}
	
	private static boolean canProcess(ClassModel model) {
		return !model.hasTaggedValue(PROCESSING_INSTRUCTION) || (model.hasTaggedValue(PROCESSING_INSTRUCTION) && !model.getTaggedValue(PROCESSING_INSTRUCTION).equals(DO_NOT_PROCESS));
	}
}
