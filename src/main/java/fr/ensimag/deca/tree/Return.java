package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;

public class Return {
    private AbstractExpr expr;

    public Return(AbstractExpr expr) {
        this.expr = expr;
    }

    public AbstractExpr getExpr() {
        return expr;
    }

    public void setExpr(AbstractExpr expr) {
        this.expr = expr;
    }

    public void codeGenReturn(DecacCompiler compiler) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    public void decompile(IndentPrintStream s) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    public void iterChildren(TreeFunction f) {
        expr.iter(f);
    }

    public void prettyPrintChildren(PrintStream s, String prefix) {
        expr.prettyPrint(s, prefix, false);
    }
}
