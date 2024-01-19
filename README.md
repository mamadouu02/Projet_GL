# Projet Génie Logiciel, Ensimag.
gl42, 01/01/2024

ahjaous, bouihih, guessouo, senameg, thiongam

## Exécution des tests

Les fichiers de tests Deca sont dans le répertoire `src/test/deca`.

Les scripts de tests sont dans le répertoire `src/test/script`.

Le fichier `src/test/script/tests.py` permet de lancer nos tests sur les fichiers Deca. Il s'exécute avec la commande `python3 tests.py [options]`. Les options sont les suivantes :
- sans option : exécute tous les tests.
- `-h` : affiche l'aide.
- `-w` : redirige les résultats dans des fichiers `.lis` et `.res` au lieu de les afficher dans la sortie standard.
- `-dev` : exécute les tests en mode développement.
- `-lex` : exécute les tests pour l'analyse lexicographique.
- `-synt` : exécute les tests pour l'analyse syntaxique.
- `-context` : exécute les tests pour les vérifications contextuelles.
- `-gen` : exécute les tests pour la génération de code.
- `-decompile` : exécute les tests pour la décompilation.

La commande `mvn test` permet de lancer les scripts Shell et le script Python sans option.

La commande `make verify` permet de recompiler le projet et de lancer les scripts Shell et le script Python sans option.

## Structure du projet

Le projet a la structure suivante :
