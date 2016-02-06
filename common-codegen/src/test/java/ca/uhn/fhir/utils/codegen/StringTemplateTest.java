package ca.uhn.fhir.utils.codegen;

import static org.junit.Assert.fail;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;
import static org.junit.Assert.*;

public class StringTemplateTest {
	
	private STGroup groupMain;
	private STGroup groupTest;

	@Before
	public void setUp() throws Exception {
		File rootMain = new File("src/main/resources/templates");
		File rootTest = new File("src/test/resources/templates");
		groupMain = new STGroupDir(rootMain.getCanonicalPath());
		groupTest = new STGroupDir(rootTest.getCanonicalPath());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTemplateDependency() {
		try {
			ST st = groupTest.getInstanceOf("decl");
			st.add("type", "int");
			st.add("name", "x");
			st.add("value", 0);
			String result = st.render(); // yields "int x = 0;"
			assertEquals("int x = 0;", result);
		} catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testIf() {
		String ifString = "if (true)\n{\n\t//do something\n}\nelse\n{\n\t//do something else\n}";
		ST st = groupMain.getInstanceOf("ifClause");
		st.add("conditional", "true");
		st.add("ifBody", "//do something");
		st.add("elseBody", "//do something else");
		assertEquals(ifString, st.render());
	}
	
	@Test
	public void testMultivaluedBody() {
		String multiValuedBodyString = "if (adaptedClass.getOnset() != null && adaptedClass.getOnset() instanceof ca.uhn.fhir.model.primitive.DateTimeDt)\n{\n\treturn ((ca.uhn.fhir.model.primitive.DateTimeDt) adaptedClass.getOnset()).getValue();\n}\nelse\n{\n\treturn null;\n}";
		ST st = groupMain.getInstanceOf("multivaluedPrimitiveMethodBody");
		st.add("propertyName", "Onset");
		st.add("canonicalClassPath", "ca.uhn.fhir.model.primitive.DateTimeDt");
		assertEquals(multiValuedBodyString, st.render());
	}
	
	@Test
	public void testMe() {
		ST st = groupMain.getInstanceOf("extensionGetterBody");
		st.add("uri", "http://some.uri/address");
		st.add("type", "BooleanDt");
		st.add("fieldName", "Myfield");
		System.out.println(st.render());
	}

}
