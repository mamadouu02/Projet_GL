package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

public class DeclField extends AbstractDeclField {
    final private Visibility visibility;
    final private AbstractIdentifier type;
    final private AbstractIdentifier name;
    final private AbstractInitialization init;

    public DeclField(Visibility visibility, AbstractIdentifier type, AbstractIdentifier name,
            AbstractInitialization init) {
        Validate.notNull(visibility);
        Validate.notNull(name);
        Validate.notNull(init);
        this.visibility = visibility;
        this.type = type;
        this.name = name;
        this.init = init;
    }

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
        type.iter(f);
        name.iter(f);
        init.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        name.prettyPrint(s, prefix, false);
        init.prettyPrint(s, prefix, false);
    }

    @Override
    protected void codeGenDeclField(DecacCompiler compiler) {
        throw new UnsupportedOperationException("not yet implemented");
    }

}
