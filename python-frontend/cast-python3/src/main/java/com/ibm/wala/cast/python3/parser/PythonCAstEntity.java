package com.ibm.wala.cast.python3.parser;

import com.ibm.wala.cast.ir.translator.AbstractScriptEntity;
import com.ibm.wala.cast.tree.CAstNode;
import com.ibm.wala.cast.tree.CAstSourcePositionMap;
import com.ibm.wala.cast.tree.CAstType;
import org.python.antlr.ast.Module;

import java.net.MalformedURLException;
import java.net.URL;

class PythonCAstEntity extends AbstractScriptEntity {

    private final Module jythonAst;
    private final CAstNode cast;
    private final PythonParser.CAstVisitor visitor;

    // FIXME: 纠正scriptname，relpath/name
    public PythonCAstEntity(PythonParser pythonParser, CAstType scriptType, PythonParser.WalkContext root, Module jythonAst, WalaPythonParser parser) throws Exception {
        super(pythonParser.scriptName(), scriptType);
        this.jythonAst = jythonAst;
        PythonParser.WalkContext context = new PythonParser.FunctionContext(root, this, jythonAst);
        this.visitor = pythonParser.new CAstVisitor(pythonParser.scriptName(), context, parser);
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
        try {
            return "script " + new URL(this.getFile().getPath()).toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
