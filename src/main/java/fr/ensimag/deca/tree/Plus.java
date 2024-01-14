package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.LabelOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.ADD;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;

/**
 * @author gl42
 * @date 01/01/2024
 */
public class Plus extends AbstractOpArith {
    public Plus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }
 

    @Override
    protected String getOperatorName() {
        return "+";
    }


    @Override
    protected void codeGenExpr(DecacCompiler compiler) {
        if (getRightOperand().dVal() != null) {
            this.getLeftOperand().codeGenExpr(compiler);
            compiler.addInstruction(new ADD(getRightOperand().dVal(), Register.getR(compiler.getIdReg())));
        } else {
            if (compiler.getIdReg() < compiler.getCompilerOptions().getRegMax()) {
                this.getLeftOperand().codeGenExpr(compiler);
                compiler.setIdReg(compiler.getIdReg()+1);
                this.getRightOperand().codeGenExpr(compiler);
                compiler.addInstruction(new ADD(Register.getR(compiler.getIdReg()), Register.getR(compiler.getIdReg()-1)));
                compiler.setIdReg(compiler.getIdReg()-1);
            } else {
                this.getLeftOperand().codeGenExpr(compiler);
                compiler.addInstruction(new PUSH(Register.getR(compiler.getIdReg())), "sauvegarde");
                this.getRightOperand().codeGenExpr(compiler);
                compiler.addInstruction(new LOAD(Register.getR(compiler.getIdReg()),Register.R0));
                compiler.addInstruction(new POP(Register.getR(compiler.getIdReg())), "restauration");
                compiler.addInstruction(new ADD(Register.R0, Register.getR(compiler.getIdReg())));
            }
        }
    }
}
