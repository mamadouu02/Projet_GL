package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Arithmetic binary operations (+, -, /, ...)
 * 
 * @author gl42
 * @date 01/01/2024
 */
public abstract class AbstractOpArith extends AbstractBinaryExpr {

    public AbstractOpArith(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
                           ClassDefinition currentClass) throws ContextualError {
        //throw new UnsupportedOperationException("not yet implemented");
        Type leftType = getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type rightType = getRightOperand().verifyExpr(compiler, localEnv, currentClass);

        if (leftType.isInt() && rightType.isInt()) {
            setType(leftType);
            return leftType;
        } else if (leftType.isFloat() && rightType.isFloat()) {
            setType(leftType);
            return leftType;
        } else if (leftType.isFloat() && rightType.isInt()) {

            ConvFloat rightTypeconv = new ConvFloat(getRightOperand());
            setRightOperand(rightTypeconv);
            Type returnType = getRightOperand().verifyExpr(compiler, localEnv, currentClass);
            setType(returnType);
            return returnType;

        } else if (leftType.isInt() && rightType.isFloat()) {
            ConvFloat leftTypeConv = new ConvFloat(getLeftOperand());
            setLeftOperand(leftTypeConv);
            Type returnType = getLeftOperand().verifyExpr(compiler, localEnv, currentClass);

            setType(returnType);
            return returnType;
        }
        else{
            throw new ContextualError("operation arithmetique impossible avec ces types", getLocation());
        }
    }
}

