package org.cdscollaborative.common.utils.graph;


public interface CommandInterface<T> {
	public void execute(Node<T> node);
}
