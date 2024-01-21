#!/bin/sh

# Auteur : gl42
# Version initiale : 01/01/2024

cd "$(dirname "$0")"/../../.. || exit 1

PATH=./src/test/script/launchers:./src/main/bin:"$PATH"
DIR=src/test/deca/codegen

RED='\033[1;31m'
GREEN='\033[0;32m'
NC='\033[0m'
PASSED="[${GREEN}PASSED${NC}]"
FAILED="[${RED}FAILED${NC}]"

for f in $(find $DIR/valid -type f -name "*.deca"); do
    dest="${f%.deca}.ass"
    rm -f "$dest" 2>/dev/null

    if decac "$f" >/dev/null 2>&1; then
        echo "$PASSED Succes attendu: decac $f"
    else
        echo "$FAILED Echec inattendu: decac $f"
        exit 1
    fi

    if [ ! -f "$dest" ]; then
        echo "$FAILED Fichier .ass non généré: decac $f"
        exit 1
    fi

    if ima "$dest" >/dev/null 2>&1; then
        echo "$PASSED Succes attendu: ima $dest"
        rm -f "$dest"
    else
        echo "$FAILED Echec inattendu: ima $dest"
        rm -f "$dest"
        exit 1
    fi
done

for f in $DIR/invalid/*.deca; do
    dest="${f%.deca}.ass"
    rm -f "$dest" 2>/dev/null

    if decac "$f" >/dev/null 2>&1; then
        echo "$PASSED Succes attendu: decac $f"
    else
        echo "$FAILED Echec inattendu: decac $f"
        exit 1
    fi

    if [ ! -f "$dest" ]; then
        echo "$FAILED Fichier .ass non généré: decac $f"
        exit 1
    fi

    if ima "$dest" 2>&1 | grep -q -e "Error"; then
        echo "$PASSED Succes attendu: ima $dest"
        rm -f "$dest"
    else
        echo "$FAILED Echec inattendu: ima $dest"
        rm -f "$dest"
        exit 1
    fi
done