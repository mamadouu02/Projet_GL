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

public class MethodBodyAsm extends AbstractMethodBody {
    final private String code;

    public MethodBodyAsm(String code) {

        Validate.notNull(code);
        this.code = code;

    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        s.print(prefix);
        s.println(code);

    }

    @Override
    String prettyPrintNode() {
        return "asm (" + code + ")";
    }

    @Override
    protected void verifyMethodBody(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass, Type type_return)
            throws ContextualError {
        //throw new UnsupportedOperationException("not yet implemented");

    }

    @Override
    protected void codeGenMethodBody(DecacCompiler compiler) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected void iterChildren(TreeFunction f) {

    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(code);
    }

}
