package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.LabelOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LEA;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;

import java.io.PrintStream;

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
        s.print("class ");
        name.decompile(s);
        if (superClass != null) {
            s.print(" extends ");
            superClass.decompile(s);
        }
        s.println(" {");
        s.indent();
        fields.decompile(s);
        methods.decompile(s);
        s.unindent();
        s.println("}");
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

        //def.setNumberOfFields(def.getSuperClass().getNumberOfFields());
        // def.setNumberOfMethods(def.getSuperClass().getNumberOfMethods());

        fields.verifyListFieldMembers(compiler,def);
        methods.verifyListMethodMembers(compiler,def );

        EnvironmentExp env_exp_super = def.getSuperClass().getMembers();

        if(env_exp_super == null){
            throw new ContextualError("Env_exp_object non défini", getLocation());
        }


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

    @Override
    protected void codeGenMethodTable(DecacCompiler compiler) {
        // TODO codeGenMethodTable for DeclClass
        compiler.addComment("Construction de la table des methodes de " + this.name.getName());
        
        this.name.codeGenInst(compiler);
        compiler.incrD();

        compiler.addInstruction(new LEA(this.superClass.getExpDefinition().getOperand(), Register.getR(0)));
        compiler.addInstruction(new STORE(Register.getR(compiler.getIdReg()),this.name.getExpDefinition().getOperand()));
        
        compiler.addInstruction(new LOAD(new LabelOperand(new Label("code.Object.equals")), Register.getR(0)));
        compiler.addInstruction(new STORE(Register.getR(compiler.getIdReg()), new RegisterOffset(compiler.getD(), Register.GB)));
        compiler.incrD();

        for (AbstractDeclMethod i : this.methods.getList()) {
            i.codeGenMethodTable(compiler, this.name.getName().getName());
        }

    }

}
