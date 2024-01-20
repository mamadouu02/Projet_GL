package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;

import org.apache.log4j.Logger;

public class ListDeclField extends TreeList<AbstractDeclField> {
    private static final Logger LOG = Logger.getLogger(ListDeclField.class);

    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractDeclField f : getList()) {
            f.decompile(s);
            s.println();
        }
    }


    /**
     * Pass 2 of [SyntaxeContextuelle]
     */
    public void verifyListFieldMembers(DecacCompiler compiler, ClassDefinition currentClass) throws ContextualError {
        //throw new UnsupportedOperationException("not yet implemented");
        //EnvironmentExp env_exp_r = new EnvironmentExp(null);
        for(AbstractDeclField declfield : this.getList()){
            declfield.verifyDeclField(compiler, currentClass);
            //env_exp_r.declare();

        }
        //env_exp_r.


    }

    /**
     * Pass 3 of [SyntaxeContextuelle]
     */
    public void verifyListFieldBody(DecacCompiler compiler, ClassDefinition currentClass) throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");

    }


    public void codeGenListField(DecacCompiler compiler, Symbol className) {
        compiler.addComment("---------- Initialisation des champs de la classe " + className + " ----------");
        for (AbstractDeclField f : getList()) {
            f.codeGenDeclField(compiler);
        }
    }
}
