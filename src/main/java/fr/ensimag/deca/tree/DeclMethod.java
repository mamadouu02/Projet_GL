package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.LabelOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

public class DeclMethod extends AbstractDeclMethod {
    final private AbstractIdentifier type;
    final private AbstractIdentifier name;
    final private ListDeclParam params;
    final private AbstractMethodBody body;

    public DeclMethod(AbstractIdentifier type, AbstractIdentifier name, ListDeclParam params, AbstractMethodBody body) {
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
        type.decompile(s);
        s.print(" ");
        name.decompile(s);
        s.print("(");
        params.decompile(s);
        s.print(")");
        body.decompile(s);
    }

    @Override
    protected void verifyDeclMethod(DecacCompiler compiler, ClassDefinition currentClass)
            throws ContextualError {
        //throw new UnsupportedOperationException("not yet implemented");
        Type type_return = type.verifyType(compiler);

        Signature sig = params.verifyListDeclParam(compiler);
        EnvironmentExp env_exp_super = currentClass.getSuperClass().getMembers();
        ExpDefinition def = env_exp_super.get(name.getName());
        //System.out.println(def + "\n\n\n");
        if(def != null && !def.isMethod()) {
            throw new ContextualError("methode déjà définie dans la classe mère autant que field", getLocation());
        }
        else if(def != null && def.isMethod()) {
            MethodDefinition def_methode = def.asMethodDefinition("ce n'est pas une définition de méthode", getLocation());
            Signature sig2 = def_methode.getSignature();
            Type type2 = def_methode.getType();
            if(sig.isSameSignature(sig2) && (type2.sameType(type_return) ||type_return.asClassType("Override impossible, verifiez  le type que renvoie votre fonction", getLocation()).isSubClassOf(type2.asClassType("Override impossible, verifiez  type que renvoie votre fonction", getLocation())))){
                try {
                    currentClass.getMembers().declare(name.getName(), def_methode);
                    name.setDefinition(def_methode);
                    //currentClass.incNumberOfFields();
                }
                catch (EnvironmentExp.DoubleDefException e) {
                    throw new ContextualError("methode déjà definie", getLocation());
                }
            }
            else if(!sig.isSameSignature(sig2)) {
                throw new ContextualError("Override impossible, verifiez la signature de votre fonction", getLocation());
            }
            else{
                throw new ContextualError("Override impossible, verifiez le type que renvoie votre fonction", getLocation());

            }
        }
        else if(def == null){
            try {
                int index_previous = currentClass.getNumberOfFields();
                MethodDefinition m_def = new MethodDefinition(type_return, getLocation(), sig, index_previous + 1);
                currentClass.incNumberOfFields();
                currentClass.getMembers().declare(name.getName(), m_def);
                //name.setDefinition(def_methode);

                name.setDefinition(m_def);
            }
            catch (EnvironmentExp.DoubleDefException e) {
                throw new ContextualError("methode deja définie", getLocation());
            }


        }
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        name.iter(f);
        params.iter(f);
        body.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        name.prettyPrint(s, prefix, false);
        params.prettyPrint(s, prefix, false);
        body.prettyPrint(s, prefix, false);
    }

    @Override
    public void codeGenMethodTable(DecacCompiler compiler, String className) {
        compiler.addInstruction(new LOAD(new LabelOperand(new Label("code." + className + "." + name.getName().getName())), Register.getR(0)));
        compiler.addInstruction(new STORE(Register.getR(0), new RegisterOffset(compiler.getD(), Register.GB)));
        compiler.incrD();
    }

}
