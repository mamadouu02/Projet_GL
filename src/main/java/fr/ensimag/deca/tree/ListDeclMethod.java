package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import java.util.List;
import org.apache.log4j.Logger;

public class ListDeclMethod extends TreeList<AbstractDeclMethod> {
    private static final Logger LOG = Logger.getLogger(ListDeclMethod.class);

    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractDeclMethod f : getList()) {
            f.decompile(s);
            s.println();
        }
    }

    /**
     * Pass 2 of [SyntaxeContextuelle]
     */
    public void verifyListMethodMembers(DecacCompiler compiler, ClassDefinition currentClass) throws ContextualError {
        for (AbstractDeclMethod method : this.getList()) {
            method.verifyDeclMethod(compiler, currentClass);
        }
    }

    /**
     * Pass 3 of [SyntaxeContextuelle]
     */
    public void verifyListMethodBody(DecacCompiler compiler, ClassDefinition currentClass) throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");

    }
}
