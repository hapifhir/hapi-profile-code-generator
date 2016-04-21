package ca.uhn.fhir.utils.codegen.hapi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import ca.uhn.fhir.model.dstu2.composite.ElementDefinitionDt;
import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;
import ca.uhn.fhir.utils.codegen.CodeGenerationUtils;
import ca.uhn.fhir.utils.codegen.hapi.methodgenerator.ExtendedStructureAttributeHandler;
import ca.uhn.fhir.utils.common.graph.CommandInterface;
import ca.uhn.fhir.utils.common.graph.Node;
import ca.uhn.fhir.utils.common.metamodel.ClassField;
import ca.uhn.fhir.utils.common.metamodel.ClassModel;
import ca.uhn.fhir.utils.common.metamodel.Method;

public class ElementCommand implements CommandInterface<ElementDefinitionDt> {
	
	private FhirResourceManager fhirResourceManager;
	private MethodBodyGenerator template;
	private StructureDefinition profile;
	private Map<String, ClassModel> classModels;
	
	public ElementCommand() {
		classModels = new HashMap<>();
	}

	public ElementCommand(StructureDefinition profile) {
		this();
		this.profile = profile;
	}
	
	public FhirResourceManager getFhirResourceManager() {
		return fhirResourceManager;
	}

	public void setFhirResourceManager(FhirResourceManager fhirResourceManager) {
		this.fhirResourceManager = fhirResourceManager;
	}

	public StructureDefinition getProfile() {
		return profile;
	}

	public void setProfile(StructureDefinition profile) {
		this.profile = profile;
	}
	
	public Map<String, ClassModel> getClassModels() {
		return classModels;
	}

	public void setClassModels(Map<String, ClassModel> classModels) {
		this.classModels = classModels;
	}
	
	public MethodBodyGenerator getTemplate() {
		return template;
	}

	public void setTemplate(MethodBodyGenerator template) {
		this.template = template;
	}

	@Override
	public void execute(Node<ElementDefinitionDt> node) {
		String extensionDefUri = node.getPayload().getTypeFirstRep().getProfileFirstRep().getValueAsString();
		if(node.hasChildren()) {
			String className = StringUtils.capitalize(CodeGenerationUtils.makeIdentifierJavaSafe(node.getName()));
			if(classModels.get(className) != null) {
				throw new RuntimeException("Error: a duplicate class was found.");
			}
			ClassModel classModel = new ClassModel(className);
			classModel.setNamespace("org.socraticgrid.fhir.generated");//TODO Makes this an argument and configurable
			ClassField fieldUri = buildUriField("uri", extensionDefUri);
			classModel.addField(fieldUri);
			Method.addGetterSetterFieldToClass(classModel, "rootObjectExtension", "ca.uhn.fhir.model.api.ExtensionDt");
			Method bindMethod = new Method();
			bindMethod.setName("bindTemplateToParent");
			bindMethod.addParameter("containingResource", "ca.uhn.fhir.model.dstu2.resource.BaseResource");
			bindMethod.setBody(template.getBindExtensionToParent());
			bindMethod.setReturnType("ca.uhn.fhir.model.api.ExtensionDt");
			classModel.addMethod(bindMethod);
			classModels.put(className, classModel);
			Method constructor = new Method();
			constructor.setBody("this.rootObjectExtension = new ExtensionDt(false, uri);");
			constructor.isConstructor(true);
			classModel.addMethod(constructor);
		}
		if(node.hasParent()) {
			node.getPayload().setPath(node.getParent().getName() + "." + node.getName());
			String parentClassName = StringUtils.capitalize(CodeGenerationUtils.makeIdentifierJavaSafe(node.getParent().getName()));
			ClassModel parentClass = classModels.get(parentClassName);
			String type = fhirResourceManager.getFullyQualifiedJavaType(profile, node.getPayload().getTypeFirstRep());
//			ClassField field = new ClassField(StringUtils.uncapitalize(node.getOriginalName()), type);
//			field.addModifier(ModifierEnum.PRIVATE);
//			ClassField fieldUri = buildUriField(node.getOriginalName() + "Uri", extensionDefUri);
			ExtendedStructureAttributeHandler handler = new ExtendedStructureAttributeHandler(fhirResourceManager, template, profile, node.getPayload());
			handler.initialize();
			List<Method> methods = handler.buildCorrespondingMethods();//FhirMethodGenerator.generateAccessorMethodsForExtendedTypes(profile, node.getPayload(), parentClassName, fhirResourceManager, extensionDefUri);
			parentClass.getMethods().addAll(methods);
//			parentClass.addField(field);
//			parentClass.addField(fieldUri);
		}
		
	}
	
	private ClassField buildUriField(String name, String extensionDefUri) {//TODO Find a home for this. Also used in GenerateLogicalView
		return ClassField.buildStaticConstant(name, java.lang.String.class.getCanonicalName(), "\"" + extensionDefUri + "\"");
	}
	
	

}
