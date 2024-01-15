package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.ImmediateFloat;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;
import fr.ensimag.ima.pseudocode.instructions.SUB;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * @author gl42
 * @date 01/01/2024
 */
public class UnaryMinus extends AbstractUnaryExpr {

    public UnaryMinus(AbstractExpr operand) {
        super(operand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        //throw new UnsupportedOperationException("not yet implemented");
        Type type = getOperand().verifyExpr(compiler,localEnv,currentClass);
        if( !type.isFloat() && !type.isInt()){
            throw new ContextualError("Le type attendu apres unaryMinus est int ou float!",getLocation());
        }
        setType(type);
        return type;
    }


    @Override
    protected String getOperatorName() {
        return "-";
    }


    @Override
    protected void codeGenExpr(DecacCompiler compiler) {
            if (getType().isFloat()) {
                compiler.addInstruction(new LOAD(new ImmediateFloat(0), Register.getR(compiler.getIdReg())));
            } else {
                compiler.addInstruction(new LOAD(new ImmediateInteger(0), Register.getR(compiler.getIdReg())));
            }

            compiler.addInstruction(new SUB(getOperand().dVal(), Register.getR(compiler.getIdReg())));

    }
}
