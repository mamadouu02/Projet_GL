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
        s.print(visibility.toString() + " ");
        type.decompile(s);
        s.print(" ");
        name.decompile(s);
        s.print(" = ");
        init.decompile(s);
        s.print(";");
    }

    @Override
    protected void verifyDeclField(DecacCompiler compiler, ClassDefinition currentClass)
            throws ContextualError {
        //throw new UnsupportedOperationException("not yet implemented");
        Type type_verified = this.type.verifyType(compiler);
        if(type_verified.isVoid()){
            throw new ContextualError("Vous ne pouvez pas déclarer un field de type void ", getLocation());
        }
        ClassDefinition super_def = currentClass.getSuperClass();
        EnvironmentExp env_exp_super = super_def.getMembers();
        ExpDefinition def = env_exp_super.get(name.getName());
        //System.out.println(def + "\n\n\n\n\n");
        if(def != null &&  !def.isField()){
            throw new ContextualError("attribut déjà définie dans la classe mère autant que methode", getLocation());
        }
        else {
            int index_previous = currentClass.getNumberOfFields();
            FieldDefinition field_def = new FieldDefinition(type_verified, getLocation(), visibility, currentClass, index_previous+1);
            try {
                currentClass.getMembers().declare(name.getName(), field_def);
                name.setDefinition(field_def);
                currentClass.incNumberOfFields();

            } catch (EnvironmentExp.DoubleDefException e) {
                throw new ContextualError("field déjà définie", getLocation());
            }
        }
        //init.verifyInitialization(compiler, type_verified, currentClass.getMembers(), currentClass);


    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        name.iter(f);
        init.iter(f);
    }

    protected Visibility getVisibilty() {
        return this.visibility;
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        name.prettyPrint(s, prefix, false);
        init.prettyPrint(s, prefix, false);
    }

    @Override
    String prettyPrintNode() {
        return "[visibilty=" + getVisibilty().toString() + "] " + super.prettyPrintNode();
    }

    @Override
    protected void codeGenDeclField(DecacCompiler compiler) {
        throw new UnsupportedOperationException("not yet implemented");
    }

}
