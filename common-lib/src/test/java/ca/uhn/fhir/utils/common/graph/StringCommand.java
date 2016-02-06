package ca.uhn.fhir.utils.common.graph;

import ca.uhn.fhir.utils.common.graph.CommandInterface;
import ca.uhn.fhir.utils.common.graph.Node;

public class StringCommand implements CommandInterface<String> {
	private StringBuilder builder = new StringBuilder();
	public void execute(Node<String> node) {
		builder.append(node.toString());
		chat(node);
	}
	public String toString() {
		return builder.toString();
	}
	
	public void chat(Node<String> node) {
		if(node.hasChildren()) {
			System.out.println(node + " is a class with attributes");
			for(Node<String> child : node.getChildren()) {
				System.out.println(child.toString());
			}
		} else {
			System.out.println(node + " is a leaf-level class");
		}
	}
}
