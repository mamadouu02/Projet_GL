# Projet Génie Logiciel, Ensimag.
gl42, 01/01/2024.

 ahjaous, bouihih, guessouo, senameg, thiongam

## Exécutions des tests

Les fichiers deca tests sont dans le dossier ```src/test/deca```.

Les scripts de tests sont dans le dossier ```src/test/script```. Le fichier ```tests.py``` dans ce dossier permet de lancer nos tests sur les fichiers deca.
Il s'exécute avec la commande ```python3 tests.py [options]```.
Les options sont les suivantes :
- sans option : exécute tous les tests.
- ```-h``` : affiche l'aide.
- ```-w``` : écrit les résultats dans les fichiers ```.lis``` et ```.res``` au lieu de les afficher dans le terminal.
- ```-dev``` : exécute les tests en mode développement.
- ```-lex``` : exécute les tests de lex.
- ```-synt``` : exécute les tests de synt.
- ```-context``` : exécute les tests de context.
- ```-gen``` : exécute les tests de gen.
- ```-decompile``` : exécute les tests de decompile.

La commande maven ```mvn test``` permet de lancer les tests shell et les tests python sans options.

La commande ```make verify``` permet de recompiler le projet et de lancer les tests shell et les tests python sans options.

## Explication de la mise en forme du projet

Le projet a la structure suivante :
