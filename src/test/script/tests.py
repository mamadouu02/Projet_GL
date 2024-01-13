#! /usr/bin/env python3

import os
import sys
import subprocess

def exec_test_write_result(file, test_name, expected):
    fichier_sortie = file[:-5] + ".lis"
    resultat_compilation = subprocess.run(["./src/test/script/launchers/" + test_name, file], stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)
    if(expected == "success"):
        if(resultat_compilation.returncode != 0):
            with open(fichier_sortie, 'w') as fichier_lis:
                fichier_lis.write(resultat_compilation.stdout)
                fichier_lis.write(resultat_compilation.stderr)
                raise Exception("Unexpected error in file : " + file)
        else:
            with open(fichier_sortie, 'w') as fichier_lis:
                fichier_lis.write(resultat_compilation.stdout)
    elif(expected == "error"):
        if(resultat_compilation.returncode == 0):
            with open(fichier_sortie, 'w') as fichier_lis:
                fichier_lis.write(resultat_compilation.stdout)
                fichier_lis.write(resultat_compilation.stderr)
                raise Exception("Unexpected success in file : " + file)
        else:
            with open(fichier_sortie, 'w') as fichier_lis:
                fichier_lis.write(resultat_compilation.stdout)
                fichier_lis.write(resultat_compilation.stderr)

def exec_test(file, test_name, expected):
    compile = os.system("./src/test/script/launchers/" + test_name + " " + file)
    if(expected == "success"):
        if(compile != 0):   
            raise Exception("Unexpected error in file : " + file)
        else:
            print("Expected success in file : " + file)  
    elif(expected == "error"):
        if(compile == 0):
            raise Exception("Unexpected success in file : " + file)
        else:
            print("Expected error in file : " + file)

def exec_test_gen(file, expected):
    fichier_sortie = file[:-5] + ".res"
    resultat_compilation = subprocess.run(["decac" , file], stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)
    if(expected == "success"):
        if(resultat_compilation.returncode != 0):
            with open(fichier_sortie, 'w') as fichier_lis:
                fichier_lis.write(resultat_compilation.stdout)
                fichier_lis.write(resultat_compilation.stderr)
                raise Exception("Unexpected error in file : " + file)
        else:
            resultat_execution = subprocess.run(["ima" , file[:-4] + "ass"], stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)
            with open(fichier_sortie, 'w') as fichier_lis:
                fichier_lis.write(resultat_execution.stdout)
    elif(expected == "error"):
        if(resultat_compilation.returncode == 0):
            with open(fichier_sortie, 'w') as fichier_lis:
                fichier_lis.write(resultat_compilation.stdout)
                fichier_lis.write(resultat_compilation.stderr)
                raise Exception("Unexpected success in file : " + file)
        else:
            resultat_execution = subprocess.run(["ima" , file[:-4] + "ass"], stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)
            with open(fichier_sortie, 'w') as fichier_lis:
                fichier_lis.write(resultat_execution.stdout)
                fichier_lis.write(resultat_execution.stderr)

def get_files():
    filesSorted = {"lex" : {"invalid" : [], "valid" : []} , "synt" : {"invalid" : [], "valid" : []}, "context" : {"invalid" : [], "valid" : []}, "gen" : {"invalid" : [], "valid" : []}}
    for root, dirs, files in os.walk("src/test/deca"): 
        for file in files:
            if file.endswith(".deca"):
                if "lex" in root:
                    if "invalid" in root:
                        filesSorted["lex"]["invalid"].append(os.path.join(root, file))
                    else:
                        filesSorted["lex"]["valid"].append(os.path.join(root, file))
                elif "synt" in root:
                    if "invalid" in root:
                        filesSorted["synt"]["invalid"].append(os.path.join(root, file))
                    else:
                        filesSorted["synt"]["valid"].append(os.path.join(root, file))
                elif "context" in root:
                    if "invalid" in root:
                        filesSorted["context"]["invalid"].append(os.path.join(root, file))
                    else:
                        filesSorted["context"]["valid"].append(os.path.join(root, file))
                elif "gen" in root:
                    if "invalid" in root:
                        filesSorted["gen"]["invalid"].append(os.path.join(root, file))
                    else:
                        filesSorted["gen"]["valid"].append(os.path.join(root, file))
    return filesSorted

def runTestLex(execFunction):
    print("########## TEST LEX ##########")
    files = get_files()
    for file in files["lex"]["valid"]:
        execFunction(file, "test_lex", "success")

    for file in files["lex"]["invalid"]:
        execFunction(file, "test_lex", "error")
    print("\033[32m" + "########## ALL TEST LEX PASSED ##########" + "\033[0m")
        
def runTestSynt(execFunction):
    print("########## TEST SYNT ##########")

    files = get_files()
    for file in files["synt"]["valid"]:
        execFunction(file, "test_synt", "success")
    for file in files["synt"]["invalid"]:
        execFunction(file, "test_synt", "error")
    print("\033[32m" + "########## ALL TEST SYNT PASSED ##########" + "\033[0m")
    

def runTestContext(execFunction):
    print("########## TEST CONTEXT ##########")

    files = get_files()
    for file in files["context"]["valid"]:
        execFunction(file, "test_context", "success")
    for file in files["context"]["invalid"]:
        execFunction(file, "test_context", "error")
    print("\033[32m" + "########## ALL TEST CONTEXT PASSED ##########" + "\033[0m")
    

def runTestGen(execFunction):
    print("########## TEST GEN ##########")

    files = get_files()
    for file in files["gen"]["valid"]:
        execFunction(file, "success")
    for file in files["gen"]["invalid"]:
        execFunction(file, "error")
    print("\033[32m" + "########## ALL TEST GEN PASSED ##########" + "\033[0m")
    


def runAllTests(execFunction):
    runTestLex(execFunction)
    runTestSynt(execFunction)
    runTestContext(execFunction)
    # runTestGen(exec_test_gen)

def runTestsDev(execFunction):
    runTestLex(execFunction)
    runTestSynt(execFunction)
    runTestContext(execFunction)
    print("\033[32m" + "########## ALL TESTS PASSED ##########" + "\033[0m")

if __name__ == "__main__":
    if(len(sys.argv) == 1):
        runAllTests(exec_test)
    elif(sys.argv[1] == "-f"):
        runTestsDev(exec_test)
    elif(sys.argv[1] == "-w"):
        runAllTests(exec_test_write_result)
    else:
        print("error: invalid argument")
        exit(1)