package fr.ensimag.deca.codegen;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tree.AbstractIdentifier;
import fr.ensimag.deca.tree.Identifier;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.LabelOperand;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;

/**
 * ObjectClass
 */
public class ObjectClass {
    
    private Symbol name;

    public ObjectClass(DecacCompiler compiler) {
        this.name = compiler.createSymbol("object");
    }

    public void codeGenClass(DecacCompiler compiler){
        compiler.addComment("--------------------------------------------------");
        compiler.addComment("                   Classe Object");
        compiler.addComment("--------------------------------------------------");
        compiler.addComment("Code de la méthode equals dans la classe Object");
        compiler.addLabel(new Label("code.Object.equals"));
    }

    public void codeGenMethodTable(DecacCompiler compiler) {
        compiler.addComment("Construction de la table des methodes de Object");
        compiler.getClassAdresses().put(name, new RegisterOffset(compiler.getD(), Register.GB));

        compiler.addInstruction(new LOAD(new NullOperand(), Register.getR(0)));
        compiler.addInstruction(new STORE(Register.getR(compiler.getIdReg()), new RegisterOffset(compiler.getD(), Register.GB)));
        compiler.incrD();

        compiler.addInstruction(new LOAD(new LabelOperand(new Label("code.Object.equals")), Register.getR(0)));
        compiler.addInstruction(new STORE(Register.getR(compiler.getIdReg()), new RegisterOffset(compiler.getD(), Register.GB)));
        compiler.incrD();
    }
}