package fr.ensimag.deca.tree;

import fr.ensimag.ima.pseudocode.BinaryInstruction;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.instructions.QUO;
import fr.ensimag.ima.pseudocode.instructions.DIV;

/**
 *
 * @author gl42
 * @date 01/01/2024
 */
public class Divide extends AbstractOpArith {
    public Divide(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "/";
    }

    @Override
    public BinaryInstruction mnemo(DVal op1, GPRegister op2) {
        if (getType().isInt()) {
            return new QUO(op1, op2);
        }
        
        return new DIV(op1, op2);
    }

}
