package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Method body ASm
 *
 * @author gl42
 * @date 01/01/2024
 */

 public class MethodBodyAsm extends AbstractMethodBody{
    final private String code;
    

    public MethodBodyAsm(String code) {

        Validate.notNull(code);
        this.code = code;
        
    }


        @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        //throw new UnsupportedOperationException("not yet implemented");
        
    }
    @Override
    String prettyPrintNode() {
        return "asm (" + code + ")";
    }

    @Override
    protected void verifyMethodBody(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected void codeGenMethodBody(DecacCompiler compiler) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    public void decompile(IndentPrintStream s) {
        throw new UnsupportedOperationException("not yet implemented");
    }

 }
