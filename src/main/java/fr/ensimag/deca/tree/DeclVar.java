package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * @author gl42
 * @date 01/01/2024
 */
public class DeclVar extends AbstractDeclVar {

    
    final private AbstractIdentifier type;
    final private AbstractIdentifier varName;
    final private AbstractInitialization initialization;

    public DeclVar(AbstractIdentifier type, AbstractIdentifier varName, AbstractInitialization initialization) {
        Validate.notNull(type);
        Validate.notNull(varName);
        Validate.notNull(initialization);
        this.type = type;
        this.varName = varName;
        this.initialization = initialization;
    }

    @Override
    protected void verifyDeclVar(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
    }

    
    @Override
    public void decompile(IndentPrintStream s) {
        type.decompile(s);
        s.print(" ");
        varName.decompile(s);
        initialization.decompile(s);
        s.println(";");
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        type.iter(f);
        varName.iter(f);
        initialization.iter(f);
    }
    
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        varName.prettyPrint(s, prefix, false);
        initialization.prettyPrint(s, prefix, true);
    }

    @Override
    public void codeGenDeclVar(DecacCompiler compiler) {
        int regMax = compiler.getCompilerOptions().getRegMax();
        int idReg = compiler.getIdreg();
        int d = compiler.getD();

        this.varName.getExpDefinition().setOperand(new RegisterOffset(compiler.getD(), Register.GB));
        compiler.incrD();
        if (this.initialization instanceof Initialization) {
            Type currentInit = this.varName.getExpDefinition().getType();
            // Inititialisation d'entiers
            if (currentInit.isInt()) {
                compiler.addInstruction(new LOAD( 0 , Register.getR(idReg)));
                compiler.addInstruction(new STORE(Register.getR(idReg), this.varName.getExpDefinition().getOperand()));
                this.varName.getExpDefinition().getOperand();
            } else if (currentInit.isFloat()) {
                throw new UnsupportedOperationException("not yet implemented");
            } else if (currentInit.isString()) {
                throw new UnsupportedOperationException("not yet implemented");
            } else if (currentInit.isBoolean()) {
                throw new UnsupportedOperationException("not yet implemented");
            } else {
                throw new UnsupportedOperationException("not yet implemented (Maybe for the class part)");
            }
        }
    }
}
