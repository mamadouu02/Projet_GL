package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * 
 *
 * @author gl42
 * @date 01/01/2024
 */
public class This extends AbstractExpr {

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        //throw new UnsupportedOperationException("not yet implemented");
        if(currentClass == null){
            throw new ContextualError("Vous ne pouvez pas utilisez This dans le Main", getLocation());
        }
        Type return_type = currentClass.getType().asClassType("", getLocation());
        setType(return_type);
        return return_type;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("this");
    }

    @Override
    String prettyPrintNode() {
        return "This";
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }

}
