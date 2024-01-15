package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

public class DeclField extends AbstractDeclField {

    @Override
    public void decompile(IndentPrintStream s) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected void verifyDeclField(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected void codeGenDeclField(DecacCompiler compiler) {
        throw new UnsupportedOperationException("not yet implemented");
    }

}
