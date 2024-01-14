package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.BinaryInstruction;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.instructions.REM;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 *
 * @author gl42
 * @date 01/01/2024
 */
public class Modulo extends AbstractOpArith {

    public Modulo(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        //throw new UnsupportedOperationException("not yet implemented");
        Type leftType = getLeftOperand().verifyExpr(compiler,localEnv,currentClass);
        Type rightType = getRightOperand().verifyExpr(compiler,localEnv,currentClass);
        if( !leftType.isInt() || !rightType.isInt()){
            throw new ContextualError("Le type des deux operandes doit etre int !", getLocation());
        }
        setType(leftType);
        return rightType;
    }


    @Override
    protected String getOperatorName() {
        return "%";
    }

    @Override
    public BinaryInstruction mnemo(DVal op1, GPRegister op2) {
        return new REM(op1, op2);
    }
}
