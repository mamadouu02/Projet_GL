package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.BinaryInstruction;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;
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
            throw new ContextualError("operation arithmetique impossible avec ces deux types", getLocation());
        }
    }

    public abstract BinaryInstruction mnemo(DVal op1, GPRegister op2);

    @Override
    protected void codeGenExpr(DecacCompiler compiler) {
        if (getRightOperand().dVal() != null) {
            this.getLeftOperand().codeGenExpr(compiler);
            compiler.addInstruction(mnemo(getRightOperand().dVal(), Register.getR(compiler.getIdReg())));
        } else {
            if (compiler.getIdReg() < compiler.getCompilerOptions().getRegMax()) {
                this.getLeftOperand().codeGenExpr(compiler);
                compiler.setIdReg(compiler.getIdReg() + 1);
                this.getRightOperand().codeGenExpr(compiler);
                compiler.addInstruction(mnemo(Register.getR(compiler.getIdReg()), Register.getR(compiler.getIdReg() - 1)));
                compiler.setIdReg(compiler.getIdReg() - 1);
            } else {
                this.getLeftOperand().codeGenExpr(compiler);
                compiler.addInstruction(new PUSH(Register.getR(compiler.getIdReg())), "sauvegarde");
                this.getRightOperand().codeGenExpr(compiler);
                compiler.addInstruction(new LOAD(Register.getR(compiler.getIdReg()),Register.R0));
                compiler.addInstruction(new POP(Register.getR(compiler.getIdReg())), "restauration");
                compiler.addInstruction(mnemo(Register.R0, Register.getR(compiler.getIdReg())));
            }
        }
    }
}

