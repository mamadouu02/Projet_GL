package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

public class DeclMethod extends AbstractDeclMethod {
    final private AbstractIdentifier type;
    final private AbstractIdentifier name;
    final private ListDeclParam params;
    private ListDeclVar declVariables;
    private ListInst insts;

    public DeclMethod(AbstractIdentifier type, AbstractIdentifier name, ListDeclVar declVariables,
            ListInst insts, ListDeclParam params) {
        Validate.notNull(type);
        Validate.notNull(name);
        Validate.notNull(declVariables);
        Validate.notNull(insts);
        Validate.notNull(params);
        this.type = type;
        this.name = name;
        this.declVariables = declVariables;
        this.insts = insts;
        this.params = params;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected void verifyDeclMethod(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        name.prettyPrint(s, prefix, false);
        params.prettyPrint(s, prefix, false);
        declVariables.prettyPrint(s, prefix, false);
        insts.prettyPrint(s, prefix, false);
    }

    @Override
    protected void codeGenDeclMethod(DecacCompiler compiler) {
        throw new UnsupportedOperationException("not yet implemented");
    }

}
