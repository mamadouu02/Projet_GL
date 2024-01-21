package fr.ensimag.deca.tree;

import java.io.PrintStream;

import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;

public class MethodCall extends AbstractExpr {

    final private AbstractExpr expr;
    final private AbstractIdentifier ident;
    final private ListExpr list;

    public MethodCall(AbstractExpr expr, AbstractIdentifier ident, ListExpr list) {
        this.expr = expr;
        this.ident = ident;
        this.list = list;
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        //throw new UnsupportedOperationException("not yet implemented");
        Type type2 = expr.verifyExpr(compiler, localEnv,currentClass);
        ClassType c_type2 = type2.asClassType("Vous ne pouvez pas appelez la méthode sur ce type", getLocation());

        //je sais si j en ai vraiment besoin , je sais pas si ca me renvoie cce que je pense vrmnt
        ExpDefinition def = c_type2.getDefinition().getMembers().get(ident.getName());
        if(def !=null) {
            MethodDefinition m_def_vrai = def.asMethodDefinition("Ce n'est pas une méthode ", getLocation());
            Signature sig_vrai = m_def_vrai.getSignature();
            int len = sig_vrai.size();
            if(list.getList().size() != len ){
                throw new ContextualError("Verifiez le nombre de parametre de la fonctions " + ident.getName() , getLocation());
            }
            else {
                for (int i = 0; i < len ; i++){
                //Type type_param = list.getList().get(i).verifyExpr(compiler, localEnv, currentClass);
                Type expected_type = sig_vrai.paramNumber(i);
                list.getList().get(i).verifyRValue(compiler, localEnv, currentClass, expected_type);

                }
                ident.
                setType(m_def_vrai.getType());
                ident.setDefinition(m_def_vrai);
                ident.setType(m_def_vrai.getType());


                return m_def_vrai.getType();

            }

        }
        else {
            throw new ContextualError("Methode "+ ident.getName()+ " non définie dans la classe " + c_type2.getDefinition().getType().getName().toString(), getLocation());
        }
        //System.out.println(m_def.getSignature().isSameSignature(m_def_vrai.getSignature()));



    }

    @Override
    public void decompile(IndentPrintStream s) {
        expr.decompile(s);
        s.print(".");
        ident.decompile(s);
        s.print("(");
        list.decompile(s);
        s.print(")");

    }

    @Override
    protected void iterChildren(TreeFunction f) {
        expr.iter(f);
        ident.iter(f);
        list.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        expr.prettyPrint(s, prefix, false);
        ident.prettyPrint(s, prefix, false);
        list.prettyPrint(s, prefix, true);
    }

}
