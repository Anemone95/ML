package com.ibm.wala.cast.python.parser;

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

public abstract class AbstractParser<T> implements TranslatorToCAst {

	public static  String[] defaultImportNames = new String[] {
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

		protected void defaultImports(Collection<CAstNode> elts) {
			for(String n : BuiltinFunctions.builtins()) {
				elts.add(
				notePosition(
				    cast.makeNode(CAstNode.DECL_STMT,
						cast.makeConstant(new CAstSymbolImpl(n, PythonCAstToIRTranslator.Any)),
						cast.makeNode(CAstNode.NEW, cast.makeConstant("wala/builtin/" + n))),
				    CAstSourcePositionMap.NO_INFORMATION));			
			}
			for(String n : defaultImportNames) {
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
	
	public AbstractParser() {
		// TODO Auto-generated constructor stub
	}

}
