package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.DAddr;

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
    protected void codeGenExpr(DecacCompiler compiler, DAddr addr) {
        throw new UnsupportedOperationException("Unimplemented method 'codeGenExpr'");
    }
}
