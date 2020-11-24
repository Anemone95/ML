package com.ibm.wala.cast.python3.parser;

import com.ibm.wala.cast.ir.translator.AbstractScriptEntity;
import com.ibm.wala.cast.tree.CAstNode;
import com.ibm.wala.cast.tree.CAstSourcePositionMap;
import com.ibm.wala.cast.tree.CAstType;
import org.python.antlr.ast.Module;

class PythonCAstEntity extends AbstractScriptEntity {

    private final Module jythonAst;
    private final CAstNode cast;
    private final PythonParser.CAstVisitor visitor;

    // FIXME: 纠正scriptname，考虑root文件夹
    public PythonCAstEntity(PythonParser pythonParser, CAstType scriptType, PythonParser.WalkContext root, Module jythonAst, WalaPythonParser parser) throws Exception {
        super(pythonParser.scriptName(), scriptType);
        this.jythonAst = jythonAst;
        PythonParser.WalkContext context = new PythonParser.FunctionContext(root, this, jythonAst);
        this.visitor = pythonParser.new CAstVisitor(context, parser);
        this.cast = this.jythonAst.<CAstNode>accept(this.visitor);
    }


    @Override
    public CAstNode getAST() {
        return cast;
    }

    @Override
    public CAstSourcePositionMap.Position getPosition() {
        return visitor.makePosition(jythonAst);
    }

    public CAstSourcePositionMap.Position getPosition(int arg) {
        return null;
    }

    @Override
    public CAstSourcePositionMap.Position getNamePosition() {
        return null;
    }

    @Override
    public String getName() {
        // TODO "script " + relPath / file.getName();
        return super.getName();
    }
}
