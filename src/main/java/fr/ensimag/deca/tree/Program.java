package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.*;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

/**
 * Deca complete program (class definition plus main block)
 *
 * @author gl42
 * @date 01/01/2024
 */
public class Program extends AbstractProgram {
    private static final Logger LOG = Logger.getLogger(Program.class);

    public Program(ListDeclClass classes, AbstractMain main) {
        Validate.notNull(classes);
        Validate.notNull(main);
        this.classes = classes;
        this.main = main;
    }

    public ListDeclClass getClasses() {
        return classes;
    }

    public AbstractMain getMain() {
        return main;
    }

    private ListDeclClass classes;
    private AbstractMain main;

    @Override
    public void verifyProgram(DecacCompiler compiler) throws ContextualError {
        LOG.debug("verify program: start");
        classes.verifyListClass(compiler);
        classes.verifyListClassMembers(compiler);
        //classes.verifyListClassBody(compiler);
        main.verifyMain(compiler);
        LOG.debug("verify program: end");
    }

    @Override
    public void codeGenProgram(DecacCompiler compiler) {
        compiler.addInstruction(new TSTO(main.getNbVar()));

        if (compiler.getCompilerOptions().getCheck()) {
            compiler.addInstruction(new BOV(new Label("stack_overflow_error")));
        }

        compiler.addInstruction(new ADDSP(main.getNbVar()));
        compiler.addComment("Main program");
        main.codeGenMain(compiler);
        compiler.addInstruction(new HALT());
    }

    public void codeGenError(DecacCompiler compiler) {
        if (compiler.getCompilerOptions().getCheck()) {
            if (compiler.getError(0)) {
                compiler.addLabel(new Label("zero_division_error"));
                compiler.addInstruction(new WSTR("Error: Division by zero"));
                compiler.addInstruction(new WNL());
                compiler.addInstruction(new ERROR());
            }

            if (compiler.getError(1)) {
                compiler.addLabel(new Label("zero_modulo_error"));
                compiler.addInstruction(new WSTR("Error: Integer modulo by zero"));
                compiler.addInstruction(new WNL());
                compiler.addInstruction(new ERROR());
            }

            if (compiler.getError(2)) {
                compiler.addLabel(new Label("overflow_error"));
                compiler.addInstruction(new WSTR("Error: Overflow during float arithmetic operation or Float division by zero"));
                compiler.addInstruction(new WNL());
                compiler.addInstruction(new ERROR());
            }

            compiler.addLabel(new Label("stack_overflow_error"));
            compiler.addInstruction(new WSTR("Error: Stack Overflow"));
            compiler.addInstruction(new WNL());
            compiler.addInstruction(new ERROR());
        }

        if (compiler.getError(3)) {
            compiler.addLabel(new Label("io_error"));
            compiler.addInstruction(new WSTR("Error: Input/Output error"));
            compiler.addInstruction(new WNL());
            compiler.addInstruction(new ERROR());
        }
    }

    @Override
    public void decompile(IndentPrintStream s) {
        getClasses().decompile(s);
        getMain().decompile(s);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        classes.iter(f);
        main.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        classes.prettyPrint(s, prefix, false);
        main.prettyPrint(s, prefix, true);
    }
}
