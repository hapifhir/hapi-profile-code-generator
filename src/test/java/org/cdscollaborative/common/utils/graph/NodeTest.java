package org.cdscollaborative.common.utils.graph;

import static org.junit.Assert.*;

import org.cdscollaborative.common.utils.graph.Node;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NodeTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetPathFromRoot() {
		Node<String> root = new Node<String>("Root");
		Node<String> child1 = new Node<String>("Child1");
		Node<String> child11 = new Node<String>("Child11");
		Node<String> child12 = new Node<String>("Child12");
		Node<String> child2 = new Node<String>("Child2");
		Node<String> child21 = new Node<String>("Child21");
		Node<String> child22 = new Node<String>("Child22");
		root.addChild(child1);
		root.addChild(child2);
		child1.addChild(child11);
		child1.addChild(child12);
		child2.addChild(child21);
		child2.addChild(child22);
		assertEquals("Root.Child2.Child22",child22.getPathFromRoot());
		assertTrue(child22.isPathPrefix("Root.Child2"));
		assertFalse(child22.isPathPrefix("Root.Child1"));
	}
	
	@Test
	public void testBuildTree() {
		Node<String> root = new Node<String>("root");
		root.setPayload("rootPayload");
		root.addToPath("root.child1.child2.child3", "child3payload");
		assertEquals("child3", root.getFirstChild().getFirstChild().getFirstChild().getName());
		assertEquals("child3payload", root.getFirstChild().getFirstChild().getFirstChild().getPayload());
	}
	
	@Test
	public void testExecuteCommandDepthFirstPre() {
		Node<String> root = buildTree();
		StringCommand command = new StringCommand();
		root.executeCommandDepthFirstPre(command);
		assertEquals("RootChild1Child11Child12Child2Child21Child22", command.toString());
	}
	
	@Test
	public void testExecuteCommandDepthFirstPost() {
		Node<String> root = buildTree();
		StringCommand command = new StringCommand();
		root.executeCommandDepthFirstPost(command);
		assertEquals("Child11Child12Child1Child21Child22Child2Root", command.toString());
	}
	
	@Test
	public void testExecuteCommandBreadthFirst() {
		Node<String> root = buildTree();
		StringCommand command = new StringCommand();
		root.executeCommandBreadthFirst(command);
		assertEquals("RootChild1Child2Child11Child12Child21Child22", command.toString());
	}
	
	public Node<String> buildTree() {
		Node<String> root = new Node<String>("Root");
		Node<String> child1 = new Node<String>("Child1");
		Node<String> child11 = new Node<String>("Child11");
		Node<String> child12 = new Node<String>("Child12");
		Node<String> child2 = new Node<String>("Child2");
		Node<String> child21 = new Node<String>("Child21");
		Node<String> child22 = new Node<String>("Child22");
		root.addChild(child1);
		root.addChild(child2);
		child1.addChild(child11);
		child1.addChild(child12);
		child2.addChild(child21);
		child2.addChild(child22);
		return root;
	}
}
