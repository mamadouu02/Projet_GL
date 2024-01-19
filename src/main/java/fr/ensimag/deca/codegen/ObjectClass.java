package fr.ensimag.deca.codegen;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;

/**
 * ObjectClass
 */
public class ObjectClass {
    
    public void codeGenClass(DecacCompiler compiler){
        compiler.addComment("--------------------------------------------------");
        compiler.addComment("                   Classe Object");
        compiler.addComment("--------------------------------------------------");
        compiler.addComment("Code de la méthode equals dans la classe Object");
        compiler.addLabel(new Label("code.Object.equals"));
    }
}