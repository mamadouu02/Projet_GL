package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import java.util.List;
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
     * Pass 1 of [SyntaxeContextuelle]
     */
    void verifyListField(DecacCompiler compiler, ClassType currentClass) throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
    }

    /**
     * Pass 2 of [SyntaxeContextuelle]
     */
    public void verifyListFieldMembers(DecacCompiler compiler, ClassType currentClass) throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");

    }

    /**
     * Pass 3 of [SyntaxeContextuelle]
     */
    public void verifyListFieldBody(DecacCompiler compiler, ClassType currentClass) throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");

    }
}
