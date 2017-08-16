package ca.uhn.fhir.utils.common.st;

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
	
	//private STGroup groupMain;
	private STGroup groupTest;

	@Before
	public void setUp() throws Exception {
//		File rootMain = new File("src/main/resources/templates");
		File rootTest = new File("src/test/resources/templates");
		//groupMain = new STGroupDir(rootMain.getCanonicalPath());
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
		String ifString = "if (true)\r\n{\r\n\t//do something\r\n}\r\nelse\r\n{\r\n\t//do something else\r\n}";
		ST st = groupTest.getInstanceOf("ifClause");
		st.add("conditional", "true");
		st.add("ifBody", "//do something");
		st.add("elseBody", "//do something else");
		assertEquals(ifString, st.render());
	}
	
	@Test
	public void testMultivaluedBody() {
		String multiValuedBodyString = "if (adaptedClass.getOnset() != null && adaptedClass.getOnset() instanceof ca.uhn.fhir.model.primitive.DateTimeDt)\r\n{\r\n\treturn ((ca.uhn.fhir.model.primitive.DateTimeDt) adaptedClass.getOnset()).getValue();\r\n}\r\nelse\r\n{\r\n\treturn null;\r\n}";
		ST st = groupTest.getInstanceOf("multivaluedPrimitiveMethodBody");
		st.add("propertyName", "Onset");
		st.add("canonicalClassPath", "ca.uhn.fhir.model.primitive.DateTimeDt");
		assertEquals(multiValuedBodyString, st.render());
	}
	
	@Test
	public void testMe() {
		String extensionGetterBody = "\tList<ca.uhn.fhir.model.api.ExtensionDt> extensions = \r\n" +
				"            .getUndeclaredExtensionsByUrl(\"http://some.uri/address\");\r\n" +
				"      if(extensions == null || extensions.size() <= 0) {\r\n" +
				"    \t  return null;\r\n" +
				"      } else if(extensions.size() == 1) {\r\n" +
				"    \t  return (BooleanDt)extensions.get(0).getValue();\r\n" +
				"      } else {\r\n" +
				"    \t  throw new RuntimeException(\"More than one extension exists for Myfield\");\r\n" +
				"      }";
		ST st = groupTest.getInstanceOf("extensionGetterBody");
		st.add("uri", "http://some.uri/address");
		st.add("type", "BooleanDt");
		st.add("fieldName", "Myfield");
		//System.out.println(st.render());
		assertEquals(extensionGetterBody, st.render());
	}

}
