package ca.uhn.fhir.utils.common.graph;


public interface CommandInterface<T> {
	public void execute(Node<T> node);
}
