package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.WFLOAT;
import fr.ensimag.ima.pseudocode.instructions.WFLOATX;
import fr.ensimag.ima.pseudocode.instructions.WINT;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * Expression, i.e. anything that has a value.
 *
 * @author gl42
 * @date 01/01/2024
 */
public abstract class AbstractExpr extends AbstractInst {
    /**
     * @return true if the expression does not correspond to any concrete token
     * in the source code (and should be decompiled to the empty string).
     */
    boolean isImplicit() {
        return false;
    }

    /**
     * Get the type decoration associated to this expression (i.e. the type computed by contextual verification).
     */
    public Type getType() {
        return type;
    }

    protected void setType(Type type) {
        Validate.notNull(type);
        this.type = type;
    }
    private Type type;

    @Override
    protected void checkDecoration() {
        if (getType() == null) {
            throw new DecacInternalError("Expression " + decompile() + " has no Type decoration");
        }
    }

    private boolean printHex;

    public void setPrintHex(boolean printHex) {
        this.printHex = printHex;
    }

    /**
     * Verify the expression for contextual error.
     * 
     * implements non-terminals "expr" and "lvalue" 
     *    of [SyntaxeContextuelle] in pass 3
     *
     * @param compiler  (contains the "env_types" attribute)
     * @param localEnv
     *            Environment in which the expression should be checked
     *            (corresponds to the "env_exp" attribute)
     * @param currentClass
     *            Definition of the class containing the expression
     *            (corresponds to the "class" attribute)
     *             is null in the main bloc.
     * @return the Type of the expression
     *            (corresponds to the "type" attribute)
     */
    public abstract Type verifyExpr(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError;

    /**
     * Verify the expression in right hand-side of (implicit) assignments 
     * 
     * implements non-terminal "rvalue" of [SyntaxeContextuelle] in pass 3
     *
     * @param compiler  contains the "env_types" attribute
     * @param localEnv corresponds to the "env_exp" attribute
     * @param currentClass corresponds to the "class" attribute
     * @param expectedType corresponds to the "type1" attribute            
     * @return this with an additional ConvFloat if needed...
     */
    public AbstractExpr verifyRValue(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass,
            Type expectedType)
            throws ContextualError {

        Type type2 = verifyExpr(compiler, localEnv, currentClass);
        if (type2.isClass() && expectedType.isClass()
                && type2.asClassType("", getLocation()).isSubClassOf(expectedType.asClassType("", getLocation()))) {
            setType(expectedType.asClassType("", getLocation()));
            return this;
        } else if (expectedType.isFloat() && type2.isInt()) {
            AbstractExpr floattype = new ConvFloat(this);
            floattype.setType(compiler.environmentType.FLOAT);
            return floattype;
        } else if (expectedType.sameType(type2)) {
            return this;
        } else {
            throw new ContextualError("les deux types ne sont pas compatibles", getLocation());
        }
    }


    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
                              ClassDefinition currentClass, Type returnType)
            throws ContextualError {
        this.verifyExpr(compiler,localEnv,currentClass);
    }

    /**
     * Verify the expression as a condition, i.e. check that the type is
     * boolean.
     *
     * @param localEnv
     *            Environment in which the condition should be checked.
     * @param currentClass
     *            Definition of the class containing the expression, or null in
     *            the main program.
     */
    void verifyCondition(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type expected = this.verifyExpr(compiler,localEnv,currentClass);

        if(!expected.isBoolean()) {
            throw new ContextualError("Votre condition doit être de type Boolean", getLocation());
        }
    }

    /**
     * Generate code to print the expression
     *
     * @param compiler
     */
    protected void codeGenPrint(DecacCompiler compiler) {
        if (dVal() != null) {
            compiler.addInstruction(new LOAD(dVal(), Register.R1), "Load in R1 to be able to display");
        } else {
            codeGenExpr(compiler);
            compiler.addInstruction(new LOAD(Register.getR(compiler.getIdReg()), Register.R1), "Load in R1 to be able to display");
        }
        
        if (getType().isFloat()) {
            if (printHex) {
                compiler.addInstruction(new WFLOATX());
            } else {
                compiler.addInstruction(new WFLOAT());
            }
        } else {
            compiler.addInstruction(new WINT());
        }
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        codeGenExpr(compiler);
    }

    protected void codeGenExpr(DecacCompiler compiler) {
        compiler.addInstruction(new LOAD(dVal(), Register.getR(compiler.getIdReg())));
    }

    protected DVal dVal() {
        return null;
    }

    protected void code(DecacCompiler compiler, boolean b, Label label) {
        codeGenExpr(compiler);
    };

    @Override
    protected void decompileInst(IndentPrintStream s) {
        decompile(s);
        s.print(";");
    }

    @Override
    protected void prettyPrintType(PrintStream s, String prefix) {
        Type t = getType();
        if (t != null) {
            s.print(prefix);
            s.print("type: ");
            s.print(t);
            s.println();
        }
    }
}
