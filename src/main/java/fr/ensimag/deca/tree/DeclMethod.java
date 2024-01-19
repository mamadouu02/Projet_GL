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
    final private AbstractMethodBody body;

    public DeclMethod(AbstractIdentifier type, AbstractIdentifier name, ListDeclParam params,AbstractMethodBody body) {
        Validate.notNull(type);
        Validate.notNull(name);
        Validate.notNull(params);
        Validate.notNull(body);
        this.type = type;
        this.name = name;
        this.params = params;
        this.body = body;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected void verifyDeclMethod(DecacCompiler compiler, ClassDefinition currentClass)
            throws ContextualError {
        //throw new UnsupportedOperationException("not yet implemented");
        Type type_return = type.verifyType(compiler);
        Signature sig = params.verifyListDeclParam(compiler);
        EnvironmentExp env_exp_super = currentClass.getMembers();
        ExpDefinition def = env_exp_super.get(name.getName());
        if(def != null){


            if (def.isMethod()){

            }
            MethodDefinition def_methode = def.asMethodDefinition("ce n'est pas une définition de méthode", getLocation());
            Signature sig2 = def_methode.getSignature();
        }
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
        body.prettyPrint(s,prefix,false);
    }

    @Override
    protected void codeGenDeclMethod(DecacCompiler compiler) {
        throw new UnsupportedOperationException("not yet implemented");
    }

}