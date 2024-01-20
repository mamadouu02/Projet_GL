package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * @author gl42
 * @date 01/01/2024
 */
public class DeclParam extends AbstractDeclParam {

    final private AbstractIdentifier type;
    final private AbstractIdentifier name;

    public DeclParam(AbstractIdentifier type, AbstractIdentifier name) {
        Validate.notNull(type);
        Validate.notNull(name);
        this.type = type;
        this.name = name;
    }

    @Override
    protected Type verifyDeclParam(DecacCompiler compiler)
            throws ContextualError {

        //throw new UnsupportedOperationException("not yet implemented");
        Type t_ver = type.verifyType(compiler);
        if(t_ver.isVoid()){
            throw new ContextualError("le paramètre ne peut pas être de type Void", getLocation());
        }
        
        return t_ver;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        type.decompile(s);
        s.print(" ");
        name.decompile(s);
        s.println(";");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        name.iter(f);

    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        name.prettyPrint(s, prefix, false);
    }

    @Override
    public void codeGenDeclParam(DecacCompiler compiler) {
        throw new UnsupportedOperationException("not yet implemented");
    }
}
