package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
/**
 *
 * @author gl42
 * @date 01/01/2024
 */
import fr.ensimag.ima.pseudocode.RegisterOffset;
public class And extends AbstractOpBool {

    public And(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "&&";
    }

    @Override
    protected void code(DecacCompiler compiler, boolean b) {
        if (b) {

        } else {
            getLeftOperand().code(compiler, false);
            getRightOperand().code(compiler, false);
        }
    }

    protected void codeGenExpr(DecacCompiler compiler) {
        // <Code(C1 && C2, faux, false)>
        code(compiler, false);
        // LOAD #1 R2
        compiler.addInstruction(new LOAD(1, Register.getR(compiler.getIdReg())));
        // BRA end
        compiler.addInstruction(new BRA(new Label("end")));
        // E:
        compiler.addLabel(new Label("E"));
        // LOAD #0 R2
        compiler.addInstruction(new LOAD(0, Register.getR(compiler.getIdReg())));
        // end:
        compiler.addLabel(new Label("end"));
    }
}
