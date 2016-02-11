package ca.uhn.fhir.utils.codegen.hapi;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.Import;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaInterfaceSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;
import ca.uhn.fhir.utils.codegen.CodeGenerationUtils;
import ca.uhn.fhir.utils.codegen.hapi.methodgenerator.MethodHandlerResolver;
import ca.uhn.fhir.utils.codegen.methodgenerators.IMethodHandler;
import ca.uhn.fhir.utils.common.metamodel.ClassField;
import ca.uhn.fhir.utils.common.metamodel.ClassModel;
import ca.uhn.fhir.utils.common.metamodel.Method;
import ca.uhn.fhir.utils.common.metamodel.MethodParameter;
import ca.uhn.fhir.utils.common.metamodel.ModifierEnum;
import ca.uhn.fhir.utils.fhir.ProfileWalker;

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
	
	//public static final String profilePrefix = "http://hl7.org/fhir/StructureDefinition/";
	public static final Logger LOGGER = LoggerFactory.getLogger(InterfaceAdapterGenerator.class);
	public static final String DEFAULT_DESTINATION_DIRECTORY = "generated-source/java/";
	public static final String ADAPTER_FIELD_NAME = "adaptedClass";
	
	private String generatedPackage;
	private String destinationDirectory;
	private FhirResourceManager fhirResourceManager;
	private MethodBodyGenerator templateUtils;
	private List<String> resourceGenerationPlan;
	private MethodHandlerResolver resolver;
	private ProfileWalker profileWalker;
	
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
	public InterfaceAdapterGenerator(String generatedPackage, FhirResourceManager fhirResourceManager, MethodBodyGenerator templateUtils) {
		this.generatedPackage = generatedPackage;
		this.templateUtils = templateUtils;
		this.resourceGenerationPlan = new ArrayList<String>();
		this.fhirResourceManager = fhirResourceManager;
		this.resolver = new MethodHandlerResolver(fhirResourceManager, templateUtils);
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
			profileWalker = new ProfileWalker(profile);
			profileWalker.initialize();
			GenerateLogicalViewCommand command = new GenerateLogicalViewCommand(profile, fhirResourceManager, templateUtils, resolver, "org.socraticgrid.fhir.generated");
			profileWalker.getRoot().executeCommandDepthFirstPost(command);
			ClassModel rootModel = command.getClassMap().get(profileWalker.getRoot().getPathFromRoot());
			String resourceName = getUnderlyingFhirCoreResourceName(profile);
			buildAdapter(rootModel, resourceName, generatedPackage + "." + generateInterfaceName(javaSafeProfileName));
			generateConstructors(generateAdapterName(javaSafeProfileName), fhirResourceManager.getResourceNameToClassMap().get(resourceName).getName(), rootModel.getMethods() );
			generateAdapteeGetter(rootModel.getMethods(), fhirResourceManager.getResourceNameToClassMap().get(resourceName).getName());
			generateAdapteeSetter(rootModel.getMethods(), fhirResourceManager.getResourceNameToClassMap().get(resourceName).getName());
			for(ClassModel model : command.getClassMap().values()) {
				if(model != rootModel && model.getMethods().size() > 0) {
					String supportingClass = InterfaceAdapterGenerator.cleanUpWorkaroundClass(CodeGenerationUtils.buildJavaClass(model, javaSafeProfileName + model.getName()), true);
					CodeGenerationUtils.writeJavaClassFile(getDestinationDirectory(), generatedPackage, javaSafeProfileName + model.getName(), supportingClass);
				}
			}
//			String generatedProfileInterface = cleanUpWorkaroundInterface(interfaceAdapterPair.getResourceInterface(), true);
//			String generatedProfileAdapter = cleanUpWorkaroundClass(interfaceAdapterPair.getResourceAdapter(), true);
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
	 * Method iterates through all element definitions in a FHIR StructureDefinition and generates
	 * from these definitions the method signatures required for code generation.
	 * 
	 * @param profile
	 * @return
	 */
//	public List<Method> generateMethodsFromFhirProfileDefinition(StructureDefinition profile) {
//		List<Method> methodDefinitions = new ArrayList<Method>();
//		List<ElementDefinitionDt> elements = profile.getSnapshot().getElement();
//		for(ElementDefinitionDt element: elements) {
//			if(skipProcessing(element)) {
//				continue;
//			}
//			String extensionDefUri = null;
//			if(FhirExtensionManager.isFhirExtension(element)) {
//				extensionDefUri = fhirResourceManager.handleExtensionElement(element);
//			}
//			
//			try {
//				methodDefinitions.addAll(FhirMethodGenerator.generateAccessorMethods(profile, element, fhirResourceManager, extensionDefUri));
//			} catch(Exception e) {
//				LOGGER.error("Error processing element " + element.getName() + ". Skipping element", e);
//			}
//		}
//		return methodDefinitions;
//	}
	
//	public List<Method> generateMethodsFromFhirProfileDefinition(StructureDefinition profile) {
// 		List<Method> methodDefinitions = new ArrayList<Method>();
//		generateExtendedTypes(profile);
//		List<ElementDefinitionDt> elements = profile.getSnapshot().getElement();
//		for(ElementDefinitionDt element: elements) {
//			if(skipProcessing(element)) {
//				continue;
//			}
//			handleElement(profile, methodDefinitions, element);
//		}
//		return methodDefinitions;
//	}
	
//	public void generateExtendedTypes(StructureDefinition profile) {
//		ExtensionGenerator generator = new ExtensionGenerator();
//		generator.processProfile(profile);
//		for(Node<ElementDefinitionDt> node: generator.getExtensionGraphs().values()) {
//			ElementCommand command = new ElementCommand(profile);
//			command.setTemplate(templateUtils);
//			command.setFhirResourceManager(fhirResourceManager);
//			node.executeCommandBreadthFirst(command);
//			if(node.hasChildren()) {
//				ClassModel classModel = command.getClassModels().get(node.getName());
//				JavaClassSource source = CodeGenerationUtils.buildJavaClass(classModel);
//				//TODO remove hard coding
//				CodeGenerationUtils.writeJavaClassFile("generated-source/java", "org.socraticgrid.fhir.generated", classModel.getName(), source.toString());
//				//System.out.println(source);
//				
//			}
//		}
//	}
	
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
	 * Method generates a Java Interface and its corresponding adapter 
	 * from a set of method definitions
	 * 
	 * definition.
	 * 
	 * @param profile
	 * @return
	 */
//	public InterfaceAdapterPair generateCodeFromFhirProfile(String safeProfileName, StructureDefinition profile, List<Method> methodDefinitions) {
//		String resourceName = getUnderlyingFhirCoreResourceName(profile);
//		generateAdapteeGetter(methodDefinitions, fhirResourceManager.getResourceNameToClassMap().get(resourceName).getName());
//		generateAdapteeSetter(methodDefinitions, fhirResourceManager.getResourceNameToClassMap().get(resourceName).getName());
//		String interfaceName = "I" + safeProfileName;
//		String className = safeProfileName + "Adapter";
//		JavaInterfaceSource interfaceSource = buildLogicalInterface(interfaceName, resourceName, methodDefinitions);
//		JavaClassSource classSource = buildAdapter(className, resourceName, methodDefinitions, generatedPackage + "." + interfaceName);
//		InterfaceAdapterPair implementation = new InterfaceAdapterPair(interfaceSource, classSource);
//		return implementation;
//	}
	
	/**
	 * Method returns the name of the profile and, if the name of the profile does not correspond to a core 
	 * FHIR resource, the name of the base resource from which this profile is derived. (Assumes a single 
	 * level profile hierarchy from FHIR core at this time. Profiles derived from profiles not supported at this
	 * time).
	 * 
	 * @param profile
	 * @return
	 */
	public String getUnderlyingFhirCoreResourceName(StructureDefinition profile) {
		String name = profile.getName();
		if(fhirResourceManager.getResourceNameToClassMap().get(name) == null) {
			name = fhirResourceManager.getResource(profile.getBase());
		}
		return name;
	}
	
	/**
	 * Method builds a Java interface for this profile (represented as a name and set of method definitions)
	 * 
	 * @param cleanedName
	 * @param resourceName
	 * @param methodDefinitions
	 * @return
	 */
//	public JavaInterfaceSource buildLogicalInterface(String interfaceName, String resourceName, List<Method> methodDefinitions) {
//		ClassModel classModel = new ClassModel(generatedPackage, interfaceName);
//		classModel.getMethods().addAll(methodDefinitions);
//		classModel.addImport(fhirResourceManager.getResourceNameToClassMap().get(resourceName).getName());
//		classModel.addImport(ca.uhn.fhir.model.api.ExtensionDt.class.getCanonicalName());
//		JavaInterfaceSource interfaceSource = CodeGenerationUtils.buildJavaInterface(classModel);
//		return interfaceSource;
//	}
	
	/**
	 * Method builds a Java Adapter for this profile (represented as a name and set of method definitions)
	 * 
	 * @param cleanedName
	 * @param resourceName
	 * @param methodDefinitions
	 * @param interfaceSource
	 * @return
	 */
//	public JavaClassSource buildAdapter(String className, String resourceName, List<Method> methodDefinitions, String anInterface) {
//		ClassModel classModel = new ClassModel(generatedPackage, className);
//		classModel.addInterface(anInterface);
//		classModel.getMethods().addAll(methodDefinitions);
//		ClassField field = new ClassField("adaptedClass");
//		field.setType(fhirResourceManager.getResourceNameToClassMap().get(resourceName).getCanonicalName());
//		field.addModifier(ModifierEnum.PRIVATE);
//		field.setInitializer("new " + fhirResourceManager.getResourceNameToClassMap().get(resourceName).getSimpleName() + "()");
//		classModel.addField(field);
//		classModel.addImport(fhirResourceManager.getResourceNameToClassMap().get(resourceName).getName());
//		classModel.addImport(ca.uhn.fhir.model.api.ExtensionDt.class.getCanonicalName());
//		JavaClassSource classSource = CodeGenerationUtils.buildJavaClass(classModel);
//		return classSource;
//	}
	
	public void buildAdapter(ClassModel classModel, String resourceName, String interfaceName) {
		classModel.addInterface(interfaceName);
		ClassField field = new ClassField("adaptedClass");
		field.setType(fhirResourceManager.getResourceNameToClassMap().get(resourceName).getCanonicalName());
		field.addModifier(ModifierEnum.PRIVATE);
		//field.setInitializer("new " + fhirResourceManager.getResourceNameToClassMap().get(resourceName).getSimpleName() + "()");
		classModel.addField(field);
		classModel.addImport(fhirResourceManager.getResourceNameToClassMap().get(resourceName).getName());
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
	 * Generates Adapter constructors
	 * 
	 * @param constructorName
	 * @param adapterType
	 * @param accessors
	 */
	protected void generateConstructors(String constructorName, String adapterType, List<Method> accessors) {
		Method noArgConstructor = new Method();
		noArgConstructor.isConstructor(true);
		noArgConstructor.setBody(this.templateUtils.getInitializeVariableStatement(ADAPTER_FIELD_NAME, adapterType));
		accessors.add(0, noArgConstructor);
		
		Method singleArgConstructor = new Method();
		singleArgConstructor.addParameter("adaptee", adapterType);
		singleArgConstructor.isConstructor(true);
		singleArgConstructor.setBody(this.templateUtils.getAssignVariableStatement(ADAPTER_FIELD_NAME, "adaptee"));
		accessors.add(1, singleArgConstructor);
	}
	
	/**
	 * Method generates accessor to underlying HAPI FHIR Adapter
	 * 
	 * @param accessors
	 * @param resourcePath
	 */
	protected void generateAdapteeGetter(List<Method> accessors, String resourcePath) {
		Method method = new Method();
		method.setName("getAdaptee");
		method.setReturnType(resourcePath);
		method.setBody("return adaptedClass;");
		accessors.add(2, method);
	}
	
	/**
	 * Method generates accessor to underlying HAPI FHIR Adapter
	 * 
	 * @param accessors
	 * @param resourcePath
	 */
	protected void generateAdapteeSetter(List<Method> accessors, String resourcePath) {
		Method method = new Method();
		method.setName("setAdaptee");
		List<MethodParameter> params = new ArrayList<MethodParameter>();
		params.add(new MethodParameter("param", resourcePath));
		method.setParameters(params);
		method.setBody("this.adaptedClass = param;");
		accessors.add(3, method);
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
			javaClass.setPackage(generatedPackage).setName("AdapterFactory");
			javaClass.addImport(org.slf4j.LoggerFactory.class);
			javaClass.addImport(java.util.List.class);
			javaClass.addImport(ca.uhn.fhir.model.api.BundleEntry.class);
			javaClass.addImport(ca.uhn.fhir.model.api.IResource.class);
			javaClass.addField().setName("GENERATED_PACKAGE_PREFIX").setStringInitializer("org.socraticgrid.fhir.generated.").setStatic(true).setPublic().setFinal(true).setType(java.lang.String.class);
			javaClass.addField().setName("HAPI_FHIR_RESOURCE_PREFIX").setStringInitializer("ca.uhn.fhir.model.dstu2.resource.").setStatic(true).setPublic().setFinal(true).setType(java.lang.String.class);
			javaClass.addField().setName("LOGGER").setLiteralInitializer("LoggerFactory.getLogger(AdapterFactory.class)").setStatic(true).setPublic().setFinal(true).setType(org.slf4j.Logger.class);
			javaClass.addMethod().setPublic().setStatic(true).setName("adapt").setReturnType("java.util.Map").setParameters("ca.uhn.fhir.model.api.Bundle bundle").setBody(templateUtils.getAdaptBundle());
			javaClass.addMethod().setPublic().setStatic(true).setName("adapt").setReturnType("java.lang.Object").setParameters("ca.uhn.fhir.model.api.IResource resource").setBody(templateUtils.getAdaptResource());
			String factoryString = cleanUpWorkaroundClass(javaClass, true);
			CodeGenerationUtils.buildTargetPackageDirectoryStructure(getDestinationDirectory(), getGeneratedPackage());
			writer = new FileWriter("generated-source/java/org/socraticgrid/fhir/generated/AdapterFactory.java");
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
}
