package org.cdscollaborative.common.utils.graph;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class Node<T> {
	
	public static final String DEFAULT_DELIM = ".";
	
	private String name;
	private String originalName;
	private Node<T> parent;
	private List<Node<T>> children;
	private T payload;
	private String delim = DEFAULT_DELIM;

	public Node() {
		children = new ArrayList<Node<T>>();
	}
	
	public Node(String name) {
		this();
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public Node<T> getParent() {
		return parent;
	}

	public void setParent(Node<T> parent) {
		this.parent = parent;
	}

	public List<Node<T>> getChildren() {
		return children;
	}

	public void setChildren(List<Node<T>> children) {
		this.children = children;
	}
	
	public void addChild(Node<T> node) {
		children.add(node);
		node.setParent(this);
	}
	
	public Node<T> getFirstChild() {
		return children.get(0);
	}
	
	public boolean hasChildren() {
		return children != null && children.size() > 0;
	}

	public T getPayload() {
		return payload;
	}

	public void setPayload(T payload) {
		this.payload = payload;
	}
	
	public boolean hasParent() {
		return parent != null;
	}
	
	public boolean hasNoParent() {
		return !hasParent();
	}
	
	public boolean isRoot() {
		return hasNoParent();
	}
	
	public boolean isLeaf() {
		return children == null || children.size() == 0;
	}
	
	public String toString() {
		return name;
	}
	
	public boolean isNameEqualTo(String name) {
		boolean isEqual = false;
		if(name != null && this.name.equalsIgnoreCase(name)) {
			isEqual = true;
		}
		return isEqual;
	}
	
	public String getPathFromRoot() {
		String path;
		if(parent == null) {
			return name;
		} else {
			path = parent.getPathFromRoot();
			return path + delim + name;
		}
	}
	
	public boolean isPathPrefix(String pathPrefix) {
		String path = getPathFromRoot();
		return path.startsWith(pathPrefix);
	}
	
	/**
	 * Method builds path from root node if:
	 * - the root of the path is the same as this node
	 * - the path is two levels or more deep
	 * 
	 * @param path
	 * @param payload
	 */
	public void addToPath(String path, T payload) {
		if(StringUtils.isBlank(path)) {
			return;
		} else if(path.indexOf(delim) < 0) {
			setPayload(payload);
			return;
		}
		String[] pathComponents = path.split("\\" + delim);
		if(isNameEqualTo(pathComponents[0])) {
			String remainder = path.substring(path.indexOf(delim) + 1);
			boolean found = false;
			for(Node<T> child : children) {
				if(child.isNameEqualTo(pathComponents[1])) {
					child.addToPath(remainder, payload);
					found = true;
					break;
				}
			}
			if(!found) {
				Node<T> child = new Node<T>(StringUtils.capitalize(pathComponents[1]));
				child.setOriginalName(pathComponents[1]);
				child.addToPath(remainder, payload);
				this.addChild(child);
			}
		}
	}
	
	public void executeCommandDepthFirstPre(CommandInterface<T> command) {
		command.execute(this);
		for(Node<T> child : children) {
			child.executeCommandDepthFirstPre(command);
		}
	}
	
	public void executeCommandDepthFirstPost(CommandInterface<T> command) {
		for(Node<T> child : children) {
			child.executeCommandDepthFirstPost(command);
		}
		command.execute(this);
	}
	
	public void executeCommandBreadthFirst(CommandInterface<T> command) {
		if(isRoot()) {
			command.execute(this);
		}
		for(Node<T> child : children) {
			command.execute(child);
		}
		for(Node<T> child : children) {
			child.executeCommandBreadthFirst(command);
		}
	}
}
