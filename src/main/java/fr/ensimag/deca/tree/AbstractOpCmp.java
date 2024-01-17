package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.BranchInstruction;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 *
 * @author gl42
 * @date 01/01/2024
 */
public abstract class AbstractOpCmp extends AbstractBinaryExpr {

    public AbstractOpCmp(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        //throw new UnsupportedOperationException("not yet implemented");
        Type leftType = getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type rightType = getRightOperand().verifyExpr(compiler, localEnv, currentClass);

        if (leftType.sameType(rightType)) {
            setType(compiler.environmentType.BOOLEAN);
            return compiler.environmentType.BOOLEAN;

        } else if (leftType.isFloat() && rightType.isInt()) {

            ConvFloat rightTypeconv = new ConvFloat(getRightOperand());
            setRightOperand(rightTypeconv);
            Type returnType = getRightOperand().verifyExpr(compiler, localEnv, currentClass);
            setType(compiler.environmentType.BOOLEAN);
            return compiler.environmentType.BOOLEAN;

        } else if (leftType.isInt() && rightType.isFloat()) {
            ConvFloat leftTypeConv = new ConvFloat(getLeftOperand());
            setLeftOperand(leftTypeConv);
            Type returnType = getLeftOperand().verifyExpr(compiler, localEnv, currentClass);

            setType(compiler.environmentType.BOOLEAN);
            return compiler.environmentType.BOOLEAN;
        }
        else{
            throw new ContextualError("operation de compraison impossible avec ces types", getLocation());
        }
    }

    public abstract BranchInstruction mnemo(boolean b, Label label);

    @Override
    protected void code(DecacCompiler compiler, boolean b, Label label) {
        if (getLeftOperand().dVal() != null) {
            compiler.addInstruction(new LOAD(getLeftOperand().dVal(), Register.getR(compiler.getIdReg())));
        } else {
            getLeftOperand().codeGenExpr(compiler);
            // CMP
        }

        if (getRightOperand().dVal() != null) {
            compiler.addInstruction(new CMP(getRightOperand().dVal(), Register.getR(compiler.getIdReg())));
        } else {
            getRightOperand().codeGenExpr(compiler);
            // CMP
        }

        compiler.addInstruction(mnemo(b, label));
    };

    @Override
    protected void codeGenExpr(DecacCompiler compiler) {
        // nothing
    }
}
