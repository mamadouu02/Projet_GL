package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

public class Cast extends AbstractExpr{
    private AbstractIdentifier ident;
    private AbstractExpr expr;

    public Cast(AbstractIdentifier ident, AbstractExpr expr) {
        this.ident = ident;
        this.expr = expr;
    }


    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");

    }

    @Override
    public void decompile(IndentPrintStream s) {
        throw new UnsupportedOperationException("not yet implemented");

    }

    @Override
    protected void iterChildren(TreeFunction f) {
        ident.iter(f);
        expr.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        ident.prettyPrint(s, prefix, false);
        expr.prettyPrint(s, prefix, true);
    }

}


