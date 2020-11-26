package com.ibm.wala.cast.python.jython3.test;

import com.ibm.wala.ipa.callgraph.CallGraph;
import com.ibm.wala.ipa.cha.ClassHierarchyException;
import com.ibm.wala.util.CancelException;
import org.junit.Test;

import java.io.IOException;

public class TestCollections extends TestPythonCallGraphShape {

	@Test
	public void testCollections1() throws ClassHierarchyException, IllegalArgumentException, CancelException, IOException {
		CallGraph CG = process("collections.py");
		System.err.println(CG);
		//verifyGraphAssertions(CG, assertionsCalls1);
	}
	

}
