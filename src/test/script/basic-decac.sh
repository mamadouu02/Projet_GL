#! /bin/sh

# Test de l'interface en ligne de commande de decac.
# On ne met ici qu'un test trivial, a vous d'en ecrire de meilleurs.

PATH=./src/main/bin:"$PATH"


if decac 2>&1 |
    grep -q -e 'OPTIONS' -e 'SYNTAXE'; then    
    echo "Pas de probleme detecte avec decac."
else
    echo "ERREUR: decac a termine avec un status different de 1."
    exit 1
fi

if decac -n 2>&1 |
    grep -q -e 'Error during option parsing:'; then    
    echo "Pas de probleme detecte avec decac."
else
    echo "ERREUR: decac a termine avec un status different de 1."
    exit 1
fi

decac_moins_b=$(decac -b)

if [ "$?" -ne 0 ]; then
    echo "ERREUR: decac -b a termine avec un status different de zero."
    exit 1
fi

if [ "$decac_moins_b" = "" ]; then
    echo "ERREUR: decac -b n'a produit aucune sortie"
    exit 1
fi

if echo "$decac_moins_b" | grep -i -e "erreur" -e "error"; then
    echo "ERREUR: La sortie de decac -b contient erreur ou error"
    exit 1
fi

echo "Pas de probleme detecte avec decac -b."

decac_moins_p=$(decac -p src/test/deca/syntax/invalid/provided/chaine_incomplete.deca)

if [ "$?" -ne 1 ]; then
    echo "ERREUR: decac -p a termine avec un status different de 1."
    exit 1
fi

decac_moins_p=$(decac -p src/test/deca/context/valid/provided/hello-world.deca)

if [ "$?" -ne 0 ]; then
    echo "ERREUR: decac -p a termine avec un status different de 0."
    exit 1
fi

if decac -p src/test/deca/context/valid/provided/hello-world.deca 2>&1 |
    grep -q -e 'println("Hello, world!");'; then
    echo "Pas de probleme detecte avec decac -p."
else
    echo "ERREUR: decac -p n'a pas produit le code attendu."
    exit 1
fi

decac_moins_v=$(decac -v src/test/deca/syntax/invalid/provided/chaine_incomplete.deca)

if [ "$?" -ne 1 ]; then
    echo "ERREUR: decac -v a termine avec un status different de 1."
    exit 1
fi

decac_moins_v=$(decac -v src/test/deca/context/valid/provided/hello-world.deca)

if [ "$?" -ne 0 ]; then
    echo "ERREUR: decac -v a termine avec un status different de 0."
    exit 1
else
    echo "Pas de probleme detecte avec decac -v."
fi

if !decac -d src/test/deca/syntax/invalid/provided/chaine_incomplete.deca 2>&1 |
    grep -q -e 'INFO'; then
    echo "ERREUR: decac -d n'a pas produit le code attendu."
    exit 1
fi

if decac -d -d src/test/deca/syntax/invalid/provided/chaine_incomplete.deca 2>&1 |
    grep -q -e 'DEBUG'; then
    echo "Pas de probleme detecte avec decac -d."
else
    echo "ERREUR: decac -d n'a pas produit le code attendu."
    exit 1
fi

decac_moins_r=$(decac -r 4 src/test/deca/decac/test_decacr.deca)

result=$(cat src/test/deca/decac/test_decacr.ass)

if echo "$result" | grep -q -e "POP" -e "POP"; then
    echo "Pas de probleme detecte avec decac -r."
else
    echo "ERREUR: decac -r n'a pas produit le code attendu."
    exit 1
fi


decac_moins_P=$(decac -P src/test/deca/codegen/valid/expr/test_and.deca src/test/deca/codegen/valid/expr/test_or.deca)

if [ "$?" -ne 0 ]; then
    echo "ERREUR: decac -P a termine avec un status different de 0."
    exit 1
else
    echo "Pas de probleme detecte avec decac -P."
fi


decac_moins_P=$(decac -P src/test/deca/codegen/valid/expr/test_and.deca src/test/deca/codegen/valid/expr/test_r.deca)

if [ "$?" -ne 1 ]; then
    echo "ERREUR: decac -P a termine avec un status different de 1."
    exit 1
else
    echo "Pas de probleme detecte avec decac -P."
fi
