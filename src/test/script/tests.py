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
        compile = os.system("test_lex " + file)
        if(compile != 0):
            raise Exception("Unexpected error in file : " + file)
            
        else:
            print("Expected success in file : " + file)
    for file in files["lex"]["invalid"]:
        compile = os.system("test_lex " + file)
        if(compile == 0):
            raise Exception("Unexpected success in file : " + file)
            
        else:
            print("Expected error in file : " + file)
def runTestSynt():
    files = get_files()
    for file in files["synt"]["valid"]:
        compile = os.system("test_synt " + file)
        if(compile != 0):
            raise Exception("Unexpected error in file : " + file)
            
        else:
            print("Expected success in file : " + file)
    for file in files["synt"]["invalid"]:
        compile = os.system("test_synt " + file)
        if(compile == 0):
            raise Exception("Unexpected success in file : " + file)
            
        else:
            print("Expected error in file : " + file)

def runTestContext():
    files = get_files()
    for file in files["context"]["valid"]:
        compile = os.system("test_context " + file)
        if(compile != 0):
            raise Exception("Unexpected error in file : " + file)
            
        else:
            print("Expected success in file : " + file)
    for file in files["context"]["invalid"]:
        compile = os.system("test_context " + file)
        if(compile == 0):
            raise Exception("Unexpected success in file : " + file)
        else:
            print("Expected error in file : " + file)

def runTestGen():
    files = get_files()
    for file in files["gen"]["valid"]:
        compile = os.system("decac " + file)
        if(compile != 0):
            raise Exception("Unexpected error in file : " + file)
            
        else:
            print("Expected success in file : " + file)
    for file in files["gen"]["invalid"]:
        compile = os.system("decac " + file)
        if(compile == 0):
            raise Exception("Unexpected success in file : " + file)
        else:
            print("Expected error in file : " + file)


def runAllTests():
    runTestLex()
    runTestSynt()
    runTestContext()
    runTestGen()

def runTestsDev():
    runTestLex()
    runTestSynt()
    runTestContext()

if __name__ == "__main__":
    if(len(sys.argv) == 1):
        runAllTests()
    elif(sys.argv[1] == "-p"):
        runTestsDev()
    else:
        print("error: invalid argument")
        exit(1)