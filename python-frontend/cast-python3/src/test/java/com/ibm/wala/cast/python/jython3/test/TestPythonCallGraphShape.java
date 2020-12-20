package com.ibm.wala.cast.python.jython3.test;

import com.ibm.wala.cast.ipa.callgraph.CAstCallGraphUtil;
import com.ibm.wala.cast.python.client.PythonAnalysisEngine;
import com.ibm.wala.cast.python.loader.PythonLoaderFactory;
import com.ibm.wala.cast.python.module.PyScriptModule;
import com.ibm.wala.cast.python.types.PythonTypes;
import com.ibm.wala.cast.python.util.PythonInterpreter;
import com.ibm.wala.cast.python.util.TestCallGraphShape;
import com.ibm.wala.cast.types.AstMethodReference;
import com.ibm.wala.classLoader.Module;
import com.ibm.wala.classLoader.SourceURLModule;
import com.ibm.wala.ipa.callgraph.CGNode;
import com.ibm.wala.ipa.callgraph.CallGraph;
import com.ibm.wala.ipa.callgraph.CallGraphBuilder;
import com.ibm.wala.ipa.callgraph.propagation.InstanceKey;
import com.ibm.wala.ipa.callgraph.propagation.PropagationCallGraphBuilder;
import com.ibm.wala.ipa.callgraph.propagation.SSAPropagationCallGraphBuilder;
import com.ibm.wala.ipa.cha.ClassHierarchyException;
import com.ibm.wala.types.MethodReference;
import com.ibm.wala.types.TypeName;
import com.ibm.wala.types.TypeReference;
import com.ibm.wala.util.CancelException;
import com.ibm.wala.util.NullProgressMonitor;
import com.ibm.wala.util.collections.HashSetFactory;
import com.ibm.wala.util.strings.Atom;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Set;

public abstract class TestPythonCallGraphShape extends TestCallGraphShape {
	
	static {
		try {
			Class<?> j3 = Class.forName("com.ibm.wala.cast.python3.loader.Python3LoaderFactory");
			PythonAnalysisEngine.setLoaderFactory((Class<? extends PythonLoaderFactory>) j3);
			Class<?> i3 = Class.forName("com.ibm.wala.cast.python3.util.Python3Interpreter");
			PythonInterpreter.setInterpreter((PythonInterpreter)i3.newInstance());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Collection<CGNode> getNodes(CallGraph CG, String functionIdentifier) {
		if (functionIdentifier.contains(":")) {
			String cls = functionIdentifier.substring(0, functionIdentifier.indexOf(":"));
			String name = functionIdentifier.substring(functionIdentifier.indexOf(":")+1);
			return CG.getNodes(MethodReference.findOrCreate(TypeReference.findOrCreate(PythonTypes.pythonLoader, TypeName.string2TypeName("L" + cls)), Atom.findOrCreateUnicodeAtom(name), AstMethodReference.fnDesc));
		} else {
			return CG.getNodes(MethodReference.findOrCreate(TypeReference.findOrCreate(PythonTypes.pythonLoader, TypeName.string2TypeName("L" + functionIdentifier)), AstMethodReference.fnSelector));
		}
	}

	protected SourceURLModule getScript(String name) throws IOException {
		try {
			File f = new File(name);
			if (f.exists()) {
				return new PyScriptModule(f.toURI().toURL());
			} else {
				URL url = new URL(name);
				return new PyScriptModule(url);
			}
		} catch (MalformedURLException e) {
			return new PyScriptModule(getClass().getClassLoader().getResource(name));
		}
	}

	protected PythonAnalysisEngine<?> createEngine() throws ClassHierarchyException, IllegalArgumentException, CancelException, IOException {
		return new PythonAnalysisEngine<Void>() {
			@Override
			public Void performAnalysis(PropagationCallGraphBuilder builder) throws CancelException {
				assert false;
				return null;
			}
		};
	}
	
	protected PythonAnalysisEngine<?> makeEngine(PythonAnalysisEngine<?> engine, String... name) throws ClassHierarchyException, IllegalArgumentException, CancelException, IOException {	
		Set<Module> modules = HashSetFactory.make();
		for(String n : name) {
			modules.add(getScript(n));
		}
		engine.setModuleFiles(modules);
		return engine;
	}

	protected PythonAnalysisEngine<?> makeEngine(String... name) throws ClassHierarchyException, IllegalArgumentException, CancelException, IOException {	
		return makeEngine(createEngine(), name);
	}
	
	protected CallGraph process(String... name) throws ClassHierarchyException, IllegalArgumentException, CancelException, IOException {
		return makeEngine(name).buildDefaultCallGraph();
	}
	
	StringBuffer dump(CallGraph CG) {
		StringBuffer sb = new StringBuffer();
		for(CGNode n : CG) {
			sb.append(n.getIR()).append("\n");
		}
		return sb;
	}

}
