package fr.ensimag.deca;

import java.io.File;
import org.apache.log4j.Logger;

/**
 * Main class for the command-line Deca compiler.
 *
 * @author gl42
 * @date 01/01/2024
 */
public class DecacMain {
    private static Logger LOG = Logger.getLogger(DecacMain.class);

    public static void main(String[] args) {
        // example log4j message.
        LOG.info("Decac compiler started");
        boolean error = false;
        final CompilerOptions options = new CompilerOptions();

        try {
            options.parseArgs(args);
        } catch (CLIException e) {
            System.err.println("Error during option parsing:\n" + e.getMessage());
            options.displayUsage();
            System.exit(1);
        }

        if (options.getPrintBanner()) {
            System.out.println();
            System.out.println("=========== ProjetGL2024, GR9, GL42 ===========");
            System.out.println(" ahjaous, bouihih, guessouo, senameg, thiongam ");
            System.out.println("===============================================");
            System.out.println();
            System.exit(0);
        }

        if (options.getSourceFiles().isEmpty()) {
            options.displayUsage();
            System.exit(0);
        }

        if (options.getParallel()) {
            // A FAIRE : instancier DecacCompiler pour chaque fichier à
            // compiler, et lancer l'exécution des méthodes compile() de chaque
            // instance en parallèle. Il est conseillé d'utiliser
            // java.util.concurrent de la bibliothèque standard Java.
            throw new UnsupportedOperationException("Parallel build not yet implemented");
        } else {
            for (File source : options.getSourceFiles()) {
                DecacCompiler compiler = new DecacCompiler(options, source);
                if (compiler.compile()) {
                    error = true;
                }
            }
        }

        System.exit(error ? 1 : 0);
    }
}
