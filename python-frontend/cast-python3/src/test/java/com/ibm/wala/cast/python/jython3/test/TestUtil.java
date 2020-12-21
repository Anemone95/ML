package com.ibm.wala.cast.python.jython3.test;

import com.ibm.wala.cast.ipa.callgraph.CAstCallGraphUtil;
import com.ibm.wala.examples.drivers.PDFTypeHierarchy;
import com.ibm.wala.ipa.callgraph.CGNode;
import com.ibm.wala.ipa.callgraph.CallGraph;
import com.ibm.wala.ipa.callgraph.propagation.InstanceKey;
import com.ibm.wala.ipa.callgraph.propagation.PointerAnalysis;
import com.ibm.wala.ipa.callgraph.propagation.PropagationCallGraphBuilder;
import com.ibm.wala.ipa.callgraph.propagation.SSAContextInterpreter;
import com.ibm.wala.ssa.IRView;
import com.ibm.wala.util.WalaException;
import com.ibm.wala.util.collections.Iterator2Iterable;
import com.ibm.wala.viz.DotUtil;

import static com.ibm.wala.cast.ipa.callgraph.CAstCallGraphUtil.getShortName;

public class TestUtil {
    public static void dumpCG(PropagationCallGraphBuilder builder, CallGraph CG) throws WalaException {
        CAstCallGraphUtil.AVOID_DUMP = false;
        CAstCallGraphUtil.dumpCG((SSAContextInterpreter)builder.getContextInterpreter(), builder.getPointerAnalysis(), CG);
        DotUtil.dotify(CG, null, PDFTypeHierarchy.DOT_FILE, "callgraph.pdf", "dot");
    }

    public static boolean hasEdge(CallGraph CG, String from, String to) {
        for (CGNode N : CG) {
            if (getShortName(N).startsWith(from)) {
                boolean fst = true;
                for (CGNode n : Iterator2Iterable.make(CG.getSuccNodes(N))) {
                    if (fst) fst = false;
                    if (getShortName(n).startsWith(to)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
