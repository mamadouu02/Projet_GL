package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import java.util.List;

/**
 * Declaration of a class (<code>class name extends superClass {members}<code>).
 * 
 * @author gl42
 * @date 01/01/2024
 */
public class DeclClass extends AbstractDeclClass {
    final private AbstractIdentifier name;
    final private AbstractIdentifier superClass;
    private ListDeclField fields;
    private ListDeclMethod methods;

    public DeclClass(AbstractIdentifier name, AbstractIdentifier superClass, ListDeclField fields,
            ListDeclMethod methods) {
        this.name = name;
        this.superClass = superClass;
        this.fields = fields;
        this.methods = methods;
    }

    public DeclClass(AbstractIdentifier name, AbstractIdentifier superClass) {
        this.name = name;
        this.superClass = superClass;
        this.fields = new ListDeclField();
        this.methods = new ListDeclMethod();
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("class { ... A FAIRE ... }");
    }

    @Override
    protected void verifyClass(DecacCompiler compiler) throws ContextualError {
        //throw new UnsupportedOperationException("not yet implemented");

        EnvironmentType env_types = compiler.environmentType;
        TypeDefinition def = env_types.defOfType(superClass.getName());
        if(def == null){
            throw new ContextualError("La classe mère n'existe pas", getLocation());
        }
        if(!def.isClass()){
            throw new ContextualError("Vous ne pouvez pas hériter de ce que vous avez écrit", getLocation());
        }

        try {
            ClassDefinition cdef  = (ClassDefinition) def;
            ClassType ct = new ClassType(name.getName(), getLocation(),cdef);
            env_types.declare_type(name.getName(), ct.getDefinition());
            name.setDefinition(ct.getDefinition());
            superClass.setDefinition(def);
            name.setType(ct);

        }
        catch(EnvironmentType.DoubleDefException e){
            throw new ContextualError("Classe déjà définie", getLocation());

        }





    }

    @Override
    protected void verifyClassMembers(DecacCompiler compiler)
            throws ContextualError {
        //throw new UnsupportedOperationException("not yet implemented");
        ClassDefinition def = (ClassDefinition) compiler.environmentType.defOfType(name.getName());
        fields.verifyListFieldMembers(compiler,def);
        methods.verifyListMethodMembers(compiler,def );
    }

    @Override
    protected void verifyClassBody(DecacCompiler compiler) throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");

    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        name.prettyPrint(s, prefix, false);
        if (superClass != null) {
            superClass.prettyPrint(s, prefix, false);
        }
        fields.prettyPrint(s, prefix, false);
        methods.prettyPrint(s, prefix, true);

    }

    @Override
    protected void iterChildren(TreeFunction f) {
        fields.iter(f);
        methods.iter(f);
    }

}
