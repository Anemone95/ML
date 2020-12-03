package com.ibm.wala.cast.python.jython3.test;

import com.ibm.wala.cast.ipa.callgraph.CAstCallGraphUtil;
import com.ibm.wala.cast.python.client.PythonAnalysisEngine;
import com.ibm.wala.examples.drivers.PDFTypeHierarchy;
import com.ibm.wala.ipa.callgraph.CallGraph;
import com.ibm.wala.ipa.callgraph.propagation.PropagationCallGraphBuilder;
import com.ibm.wala.ipa.callgraph.propagation.SSAContextInterpreter;
import com.ibm.wala.ipa.cha.ClassHierarchyException;
import com.ibm.wala.util.CancelException;
import com.ibm.wala.util.NullProgressMonitor;
import com.ibm.wala.util.WalaException;
import com.ibm.wala.viz.DotUtil;
import org.junit.Test;

import java.io.IOException;

public class TestMulti extends TestPythonCallGraphShape {

    protected static final Object[][] assertionsCalls1 = new Object[][]{
            new Object[]{ROOT, new String[]{"script calls1.py", "script calls2.py"}}
    };

    @Test
    public void testCalls1() throws ClassHierarchyException, IllegalArgumentException, CancelException, IOException {
        CallGraph CG = process("calls1.py", "calls2.py");
        verifyGraphAssertions(CG, assertionsCalls1);
    }

    protected static final Object[][] assertionsMulti1 = new Object[][]{
            new Object[]{ROOT, new String[]{"script multi1.py", "script multi2.py"}},
            new Object[]{"script multi1.py", new String[]{"script multi2.py/silly"}},
            new Object[]{"script multi2.py/silly", new String[]{"script multi2.py/silly/inner"}},
    };

    @Test
    public void testMulti1() throws WalaException, IllegalArgumentException, CancelException, IOException {
        PythonAnalysisEngine<?> engine = makeEngine("multi2.py", "multi1.py");
        PropagationCallGraphBuilder builder = (PropagationCallGraphBuilder) engine.defaultCallGraphBuilder();
        CallGraph CG = builder.makeCallGraph(engine.getOptions(), new NullProgressMonitor());
        CAstCallGraphUtil.AVOID_DUMP = false;
        CAstCallGraphUtil.dumpCG((SSAContextInterpreter) builder.getContextInterpreter(), builder.getPointerAnalysis(), CG);
        DotUtil.dotify(CG, null, PDFTypeHierarchy.DOT_FILE, "callgraph.pdf", "dot");
        verifyGraphAssertions(CG, assertionsMulti1);
    }

    @Test
    public void testMulti2() throws WalaException, IllegalArgumentException, CancelException, IOException {
        PythonAnalysisEngine<?> engine = makeEngine( "multi3.py");
        PropagationCallGraphBuilder builder = (PropagationCallGraphBuilder) engine.defaultCallGraphBuilder();
        CallGraph CG = builder.makeCallGraph(engine.getOptions(), new NullProgressMonitor());
        CAstCallGraphUtil.AVOID_DUMP = false;
        CAstCallGraphUtil.dumpCG((SSAContextInterpreter) builder.getContextInterpreter(), builder.getPointerAnalysis(), CG);
        DotUtil.dotify(CG, null, PDFTypeHierarchy.DOT_FILE, "callgraph.pdf", "dot");
    }

    @Test
    public void testModule() throws WalaException, IllegalArgumentException, CancelException, IOException {
        PythonAnalysisEngine<?> engine = makeEngine("multi5.py",
                "pkg1/__init__.py",
                "pkg1/moduleI.py");
        PropagationCallGraphBuilder builder = (PropagationCallGraphBuilder) engine.defaultCallGraphBuilder();
        CallGraph CG = builder.makeCallGraph(engine.getOptions(), new NullProgressMonitor());
        CAstCallGraphUtil.AVOID_DUMP = false;
        CAstCallGraphUtil.dumpCG((SSAContextInterpreter) builder.getContextInterpreter(), builder.getPointerAnalysis(), CG);
        DotUtil.dotify(CG, null, PDFTypeHierarchy.DOT_FILE, "callgraph.pdf", "dot");
    }

    @Test
    public void testMulti4() throws WalaException, IllegalArgumentException, CancelException, IOException {
        PythonAnalysisEngine<?> engine = makeEngine("multi4.py", "multi7.py");
        PropagationCallGraphBuilder builder = (PropagationCallGraphBuilder) engine.defaultCallGraphBuilder();
        CallGraph CG = builder.makeCallGraph(engine.getOptions(), new NullProgressMonitor());
        CAstCallGraphUtil.AVOID_DUMP = false;
        CAstCallGraphUtil.dumpCG((SSAContextInterpreter) builder.getContextInterpreter(), builder.getPointerAnalysis(), CG);
        DotUtil.dotify(CG, null, PDFTypeHierarchy.DOT_FILE, "callgraph.pdf", "dot");
    }
}
