/******************************************************************************
 * Copyright (c) 2018 IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *****************************************************************************/
package com.ibm.wala.cast.python.ir;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import com.ibm.wala.analysis.typeInference.PrimitiveType;
import com.ibm.wala.cast.ir.ssa.AstInstructionFactory;
import com.ibm.wala.cast.python.cfg.PythonInducedCFG;
import com.ibm.wala.cast.python.modref.PythonModRef.PythonModVisitor;
import com.ibm.wala.cast.python.modref.PythonModRef.PythonRefVisitor;
import com.ibm.wala.cast.python.types.PythonTypes;
import com.ibm.wala.cfg.InducedCFG;
import com.ibm.wala.classLoader.IMethod;
import com.ibm.wala.classLoader.Language;
import com.ibm.wala.ipa.callgraph.AnalysisOptions;
import com.ibm.wala.ipa.callgraph.CGNode;
import com.ibm.wala.ipa.callgraph.Context;
import com.ibm.wala.ipa.callgraph.IAnalysisCacheView;
import com.ibm.wala.ipa.callgraph.impl.AbstractRootMethod;
import com.ibm.wala.ipa.callgraph.impl.FakeRootClass;
import com.ibm.wala.ipa.callgraph.impl.FakeRootMethod;
import com.ibm.wala.ipa.callgraph.propagation.InstanceKey;
import com.ibm.wala.ipa.callgraph.propagation.PointerAnalysis;
import com.ibm.wala.ipa.callgraph.propagation.PointerKey;
import com.ibm.wala.ipa.cha.IClassHierarchy;
import com.ibm.wala.ipa.modref.ExtendedHeapModel;
import com.ibm.wala.ipa.modref.ModRef.ModVisitor;
import com.ibm.wala.ipa.modref.ModRef.RefVisitor;
import com.ibm.wala.shrikeCT.InvalidClassFileException;
import com.ibm.wala.ssa.SSAInstruction;
import com.ibm.wala.types.MethodReference;
import com.ibm.wala.types.TypeName;
import com.ibm.wala.types.TypeReference;
import com.ibm.wala.util.strings.Atom;

public class PythonLanguage implements Language {

    public static PythonLanguage Python = new PythonLanguage();

    private PythonLanguage() {

    }

    @Override
    public Atom getName() {
        return PythonTypes.pythonName;
    }

    @Override
    public Language getBaseLanguage() {
        return null;
    }

    @Override
    public void registerDerivedLanguage(Language l) {
        assert false;
    }

    @Override
    public Set<Language> getDerivedLanguages() {
        return Collections.emptySet();
    }

    @Override
    public TypeReference getRootType() {
        return PythonTypes.Root;
    }

    @Override
    public TypeReference getThrowableType() {
        return PythonTypes.Exception;
    }

    @Override
    public TypeReference getConstantType(Object o) {
        if (o instanceof String) {
            return PythonTypes.string;
/*		} else if (o instanceof Number) {
			return TypeReference.Int;
*/
        } else {
            return PythonTypes.Root;
        }
    }

    @Override
    public boolean isNullType(TypeReference t) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isIntType(TypeReference t) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isLongType(TypeReference t) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isVoidType(TypeReference t) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isFloatType(TypeReference t) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isDoubleType(TypeReference t) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isStringType(TypeReference t) {
        // TODO Auto-generated method stub
        return t.equals(PythonTypes.string);
    }

    @Override
    public boolean isMetadataType(TypeReference t) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isCharType(TypeReference t) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isBooleanType(TypeReference t) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Object getMetadataToken(Object value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeReference[] getArrayInterfaces() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeName lookupPrimitiveType(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AstInstructionFactory instructionFactory() {
        return new PythonInstructionFactory();
    }

    @Override
    public Collection<TypeReference> inferInvokeExceptions(MethodReference target, IClassHierarchy cha)
            throws InvalidClassFileException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeReference getStringType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeReference getPointerType(TypeReference pointee) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PrimitiveType getPrimitive(TypeReference reference) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean methodsHaveDeclaredParameterTypes() {
        return false;
    }

    @Override
    public AbstractRootMethod getFakeRootMethod(IClassHierarchy cha, AnalysisOptions options, IAnalysisCacheView cache) {
        return new FakeRootMethod(new FakeRootClass(PythonTypes.pythonLoader, cha), options, cache);
    }

    @Override
    public InducedCFG makeInducedCFG(SSAInstruction[] instructions, IMethod method, Context context) {
        return new PythonInducedCFG(instructions, method, context);
    }

    @Override
    public boolean modelConstant(Object o) {
        return true;
    }

    @Override
    public <T extends InstanceKey> RefVisitor<T, ? extends ExtendedHeapModel> makeRefVisitor(CGNode n,
                                                                                             Collection<PointerKey> result, PointerAnalysis<T> pa, ExtendedHeapModel h) {
        return new PythonRefVisitor<>(n, result, pa, h);
    }

    @Override
    public <T extends InstanceKey> ModVisitor<T, ? extends ExtendedHeapModel> makeModVisitor(CGNode n, Collection<PointerKey> result,
                                                                                             PointerAnalysis<T> pa, ExtendedHeapModel h, boolean ignoreAllocHeapDefs) {
        return new PythonModVisitor<>(n, result, h, pa, ignoreAllocHeapDefs);
    }

}
