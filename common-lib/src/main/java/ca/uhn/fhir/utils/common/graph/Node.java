package ca.uhn.fhir.utils.common.graph;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

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
	
	public boolean childOfNameAlreadyExists(Node<T> node) {
		boolean childOfNameAlreadyExists = false;
		for(Node<T> child : children) {
			if(child.getName().equalsIgnoreCase(node.getName())) {
				childOfNameAlreadyExists = true;
			}
			break;
		}
		return childOfNameAlreadyExists;
	}
	
	public void addChildIfNotExist(Node<T> node) {
		if(!childOfNameAlreadyExists(node)) {
			addChild(node);
		}
	}
	
	public Node<T> getFirstChild() {
		return children.get(0);
	}
	
	public boolean hasChildren() {
		return children != null && children.size() > 0;
	}
	
	public boolean hasNoChildren() {
		return !hasChildren();
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
	
	public boolean isNotRoot() {
		return !isRoot();
	}
	
	public boolean parentIsRoot() {
		return hasParent() && getParent().isRoot();
	}
	
	public boolean parentIsNotRoot() {
		return !parentIsRoot();
	}
	
	public boolean isLeaf() {
		return hasNoChildren();
	}
	
	public boolean isNotLeaf() {
		return !isLeaf();
	}
	
	public boolean isInnerNode() {
		return isNotLeaf();
	}
	
	public boolean isNotInnerNode() {
		return !isInnerNode();
	}
	
	public boolean isL1() {
		return parentIsRoot();
	}
	
	public boolean isNotL1() {
		return !isL1();
	}
	
	public boolean isInnerL1() {
		return isL1() && isNotLeaf();
	}
	
	public boolean isNotInnerL1() {
		return isNotL1() || isLeaf();
	}
	
	public boolean isLN() {
		return parentIsNotRoot();
	}
	
	public boolean isNotLN() {
		return !isLN();
	}
	
	public boolean isInnerLN() {
		return isLN() && hasChildren();
	}
	
	public boolean isNotInnerLN() {
		return !isLN() || !hasChildren();
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

	public Node<T> shallowClone() {
		Node<T> node = new Node<T>();
		node.setParent(this.getParent());
		node.setName(this.getName());
		node.setChildren(this.getChildren());
		node.setOriginalName(this.getOriginalName());
		node.setPayload(this.getPayload());
		return node;
	}
}
