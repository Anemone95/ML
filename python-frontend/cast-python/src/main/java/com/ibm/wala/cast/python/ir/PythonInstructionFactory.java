package com.ibm.wala.cast.python.ir;

import com.ibm.wala.cast.ir.ssa.*;
import com.ibm.wala.cast.java.loader.JavaSourceLoaderImpl;
import com.ibm.wala.cast.python.ssa.PythonInvokeInstruction;
import com.ibm.wala.cast.python.ssa.PythonPropertyRead;
import com.ibm.wala.cast.python.ssa.PythonPropertyWrite;
import com.ibm.wala.cast.types.AstMethodReference;
import com.ibm.wala.classLoader.CallSiteReference;
import com.ibm.wala.shrikeCT.BootstrapMethodsReader;
import com.ibm.wala.ssa.SSAAbstractInvokeInstruction;
import com.ibm.wala.ssa.SSAInvokeInstruction;
import com.ibm.wala.types.FieldReference;
import com.ibm.wala.util.collections.Pair;

class PythonInstructionFactory extends JavaSourceLoaderImpl.InstructionFactory {

    @Override
    public AstIsDefinedInstruction IsDefinedInstruction(int iindex, int lval, int rval, int fieldVal) {
        return new AstIsDefinedInstruction(iindex, lval, rval, fieldVal);
    }

    @Override
    public AstIsDefinedInstruction IsDefinedInstruction(int iindex, int lval, int rval, int fieldVal,
                                                        FieldReference fieldRef) {
        assert fieldRef == null;
        return new AstIsDefinedInstruction(iindex, lval, rval, fieldVal, fieldRef);
    }

    @Override
    public AstEchoInstruction EchoInstruction(int iindex, int[] rvals) {
        return new AstEchoInstruction(iindex, rvals);
    }

    @Override
    public AstPropertyRead PropertyRead(int iindex, int result, int objectRef, int memberRef) {
        return new PythonPropertyRead(iindex, result, objectRef, memberRef);
    }

    @Override
    public AstPropertyWrite PropertyWrite(int iindex, int objectRef, int memberRef, int value) {
        return new PythonPropertyWrite(iindex, objectRef, memberRef, value);
    }

    @Override
    public AstGlobalRead GlobalRead(int iindex, int lhs, FieldReference global) {
        return new AstGlobalRead(iindex, lhs, global);
    }

    @Override
    public AstGlobalWrite GlobalWrite(int iindex, FieldReference global, int rhs) {
        return new AstGlobalWrite(iindex, global, rhs);
    }

    @SuppressWarnings("unchecked")
    @Override
    public SSAAbstractInvokeInstruction InvokeInstruction(int iindex, int result, int[] params, int exception,
                                                          CallSiteReference site, BootstrapMethodsReader.BootstrapMethod bootstrap) {
        if (site.getDeclaredTarget().getName().equals(AstMethodReference.fnAtom) &&
                site.getDeclaredTarget().getDescriptor().equals(AstMethodReference.fnDesc)) {
            return new PythonInvokeInstruction(iindex, result, exception, site, params, new Pair[0]);
        } else {
            return super.InvokeInstruction(iindex, result, params, exception, site, bootstrap);
        }
    }

    @Override
    public SSAInvokeInstruction InvokeInstruction(int iindex, int[] params, int exception,
                                                  CallSiteReference site, BootstrapMethodsReader.BootstrapMethod bootstrap) {
        // TODO Auto-generated method stub
        return super.InvokeInstruction(iindex, params, exception, site, bootstrap);
    }

    @Override
    public EachElementGetInstruction EachElementGetInstruction(int iindex, int value, int objectRef, int prevProp) {
        return new EachElementGetInstruction(iindex, value, objectRef, prevProp);
    }

    @Override
    public AstYieldInstruction YieldInstruction(int iindex, int[] rvals) {
        return new AstYieldInstruction(iindex, rvals);
    }
}
