package com.ibm.wala.cast.python.parser;

import java.io.File;
import java.util.Collection;

import com.ibm.wala.cast.ir.translator.TranslatorToCAst;
import com.ibm.wala.cast.python.ipa.summaries.BuiltinFunctions;
import com.ibm.wala.cast.python.ir.PythonCAstToIRTranslator;
import com.ibm.wala.cast.tree.CAst;
import com.ibm.wala.cast.tree.CAstNode;
import com.ibm.wala.cast.tree.CAstSourcePositionMap;
import com.ibm.wala.cast.tree.CAstSourcePositionMap.Position;
import com.ibm.wala.cast.tree.impl.CAstImpl;
import com.ibm.wala.cast.tree.impl.CAstSymbolImpl;

public abstract class AbstractTransToCAst<T> implements TranslatorToCAst {

    public static String[] defaultImportNames = new String[]{
            "__name__",
            "print",
            "super",
            "open",
            "hasattr",
            "BaseException",
            "abs",
            "del",
    };

    public final CAst cast = new CAstImpl();

    public abstract class CAstVisitor {

        private final String scriptName;

        public CAstVisitor(String scriptName) {
            this.scriptName = scriptName;
        }

        protected void defaultImports(Collection<CAstNode> elts) {
            for (String n : BuiltinFunctions.builtins()) {
                elts.add(
                        notePosition(
                                cast.makeNode(CAstNode.DECL_STMT,
                                        cast.makeConstant(new CAstSymbolImpl(n, PythonCAstToIRTranslator.Any)),
                                        cast.makeNode(CAstNode.NEW, cast.makeConstant("wala/builtin/" + n))),
                                CAstSourcePositionMap.NO_INFORMATION));
            }
            for (String n : defaultImportNames) {
                elts.add(
                        notePosition(
                                cast.makeNode(CAstNode.DECL_STMT,
                                        cast.makeConstant(new CAstSymbolImpl(n, PythonCAstToIRTranslator.Any)),
                                        cast.makeNode(CAstNode.PRIMITIVE, cast.makeConstant("import"), cast.makeConstant(n))),
                                CAstSourcePositionMap.NO_INFORMATION));
            }

            File file = new File(scriptName);        //获取其file对象
            File[] fs = file.getParentFile().listFiles();    //遍历path下的文件和目录，放在File数组中
            for (File f : fs) {                    //遍历File[]数组
                String n=f.getAbsolutePath();
                elts.add(
                        notePosition(
                                cast.makeNode(CAstNode.DECL_STMT,
                                        cast.makeConstant(new CAstSymbolImpl(n, PythonCAstToIRTranslator.Any)),
                                        cast.makeNode(CAstNode.PRIMITIVE, cast.makeConstant("import"), cast.makeConstant(n))),
                                CAstSourcePositionMap.NO_INFORMATION));
            }
        }

        protected abstract CAstNode notePosition(CAstNode makeNode, Position noInformation);

    }

    public AbstractTransToCAst() {
        // TODO Auto-generated constructor stub
    }

}
