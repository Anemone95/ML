package com.ibm.wala.cast.python.loader;

import com.ibm.wala.cast.python.ir.PythonLanguage;
import com.ibm.wala.cast.python.types.PythonTypes;
import com.ibm.wala.ipa.callgraph.AnalysisScope;
import com.ibm.wala.types.ClassLoaderReference;

import java.util.Collections;

public class PythonAnalysisScope extends AnalysisScope {
    public PythonAnalysisScope() {
        super(Collections.singleton(PythonLanguage.Python));
        loadersByName.put(PythonTypes.pythonLoaderName, PythonTypes.pythonLoader);
        loadersByName.put(SYNTHETIC, new ClassLoaderReference(SYNTHETIC, PythonLanguage.Python.getName(), PythonTypes.pythonLoader));
    }
}
