package ca.uhn.fhir.utils.codegen.hapi;

import ca.uhn.fhir.model.dstu2.resource.StructureDefinition;
import ca.uhn.fhir.utils.codegen.CodeGenerationUtils;
import ca.uhn.fhir.utils.common.graph.CommandInterface;
import ca.uhn.fhir.utils.common.graph.Node;

public abstract class GenerateLogicalViewCommandBase<T>  implements CommandInterface<T>{
	
	private Node<T> rootNode;

	public Node<T> getRootNode() {
		return rootNode;
	}

	public void setRootNode(Node<T> rootNode) {
		this.rootNode = rootNode;
	}

	public static String generateAdapterName(String javaSafeProfileName) {
		return javaSafeProfileName + "Adapter";
	}

	public static String generateInterfaceName(String javaSafeProfileName) {
		return "I" + javaSafeProfileName;
	}

}
