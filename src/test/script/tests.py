#! /usr/bin/env python3

import os
import sys


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

def runTestLex():
    print("########## TEST LEX ##########")
    files = get_files()
    for file in files["lex"]["valid"]:
        compile = os.system("./src/test/script/launchers/test_lex " + file)
        if(compile != 0):
            raise Exception("Unexpected error in file : " + file)
            
        else:
            print("Expected success in file : " + file)
    for file in files["lex"]["invalid"]:
        compile = os.system("./src/test/script/launchers/test_lex " + file)
        if(compile == 0):
            raise Exception("Unexpected success in file : " + file)
            
        else:
            print("Expected error in file : " + file)
    print("\033[32m" + "########## ALL TEST LEX PASSED ##########" + "\033[0m")
        
def runTestSynt():
    print("########## TEST SYNT ##########")

    files = get_files()
    for file in files["synt"]["valid"]:
        compile = os.system("./src/test/script/launchers/test_synt " + file)
        if(compile != 0):
            raise Exception("Unexpected error in file : " + file)
            
        else:
            print("Expected success in file : " + file)
    for file in files["synt"]["invalid"]:
        compile = os.system("./src/test/script/launchers/test_synt " + file)
        if(compile == 0):
            raise Exception("Unexpected success in file : " + file)
            
        else:
            print("Expected error in file : " + file)
    print("\033[32m" + "########## ALL TEST SYNT PASSED ##########" + "\033[0m")
    

def runTestContext():
    print("########## TEST CONTEXT ##########")

    files = get_files()
    for file in files["context"]["valid"]:
        compile = os.system("./src/test/script/launchers/test_context " + file)
        if(compile != 0):
            raise Exception("Unexpected error in file : " + file)
            
        else:
            print("Expected success in file : " + file)
    for file in files["context"]["invalid"]:
        compile = os.system("./src/test/script/launchers/test_context " + file)
        if(compile == 0):
            raise Exception("Unexpected success in file : " + file)
        else:
            print("Expected error in file : " + file)
    print("\033[32m" + "########## ALL TEST CONTEXT PASSED ##########" + "\033[0m")
    

def runTestGen():
    print("########## TEST GEN ##########")

    files = get_files()
    for file in files["gen"]["valid"]:
        compile = os.system("./src/test/script/launchers/decac " + file)
        if(compile != 0):
            raise Exception("Unexpected error in file : " + file)
            
        else:
            print("Expected success in file : " + file)
    for file in files["gen"]["invalid"]:
        compile = os.system("./src/test/script/launchers/decac " + file)
        if(compile == 0):
            raise Exception("Unexpected success in file : " + file)
        else:
            print("Expected error in file : " + file)
    print("\033[32m" + "########## ALL TEST GEN PASSED ##########" + "\033[0m")
    


def runAllTests():
    runTestLex()
    runTestSynt()
    # runTestContext()
    # runTestGen()

def runTestsDev():
    runTestLex()
    runTestSynt()
    # runTestContext()
    print("\033[32m" + "########## ALL TESTS PASSED ##########" + "\033[0m")

if __name__ == "__main__":
    if(len(sys.argv) == 1):
        runAllTests()
    elif(sys.argv[1] == "-f"):
        runTestsDev()
    else:
        print("error: invalid argument")
        exit(1)