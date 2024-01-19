package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.LabelOperand;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;

import org.apache.log4j.Logger;

/**
 *
 * @author gl42
 * @date 01/01/2024
 */
public class ListDeclClass extends TreeList<AbstractDeclClass> {
    private static final Logger LOG = Logger.getLogger(ListDeclClass.class);

    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractDeclClass c : getList()) {
            c.decompile(s);
            s.println();
        }
    }

    /**
     * Pass 1 of [SyntaxeContextuelle]
     */
    void verifyListClass(DecacCompiler compiler) throws ContextualError {
        LOG.debug("verify listClass: start");
        throw new UnsupportedOperationException("not yet implemented");
        // LOG.debug("verify listClass: end");
    }

    /**
     * Pass 2 of [SyntaxeContextuelle]
     */
    public void verifyListClassMembers(DecacCompiler compiler) throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
    }

    /**
     * Pass 3 of [SyntaxeContextuelle]
     */
    public void verifyListClassBody(DecacCompiler compiler) throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
    }

    public void codeGenListMethodTable(DecacCompiler compiler) {
       compiler.addComment("--------------------------------------------------");
       compiler.addComment("       Construction des tables des methodes");
       compiler.addComment("--------------------------------------------------");
       compiler.addComment("Construction de la table des methodes de Object");

       compiler.addInstruction(new LOAD(new NullOperand(), Register.getR(0)));
       compiler.addInstruction(new STORE(Register.getR(compiler.getIdReg()), new RegisterOffset(compiler.getD(), Register.GB)));
       compiler.incrD();

       compiler.addInstruction(new LOAD(new LabelOperand(new Label("code.Object.equals")), Register.getR(0)));
       compiler.addInstruction(new STORE(Register.getR(compiler.getIdReg()), new RegisterOffset(compiler.getD(), Register.GB)));
       compiler.incrD();

       for (AbstractDeclClass i : getList()) {
           i.codeGenMethodTable(compiler);
       }
    }

}
