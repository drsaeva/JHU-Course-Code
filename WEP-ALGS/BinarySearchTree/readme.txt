BinarySearchTree.py
David R. Saeva
last updated 7/3/2017
licensed under GPL v3

##################################

This Python file was written with and tested in a Windows environment utilizing the Python Interpreter (v3.6).
Earlier versions of the interpreter, most notably pre-Py3, may not be able to execute this file. A shebang
line containing a probable path to your environment's Python3 interpreter (/usr/bin/env python3) was included 
for execution in Linux/Mac environments. This line may be changed to fit your preferences.

By default, this file accepts two run-time arguments for input and output file paths, provided as such:

BinarySearchTree.py ~/input.txt ~/output.txt

Input files should contain numerical inputs, one per line, with each to represent an item to be inserted into
the BST. Output files should be empty.

In the event of an invalid or nonexistent output file argument, the program will default to console output. In 
the event of an invalid input file, the program will generate its own BST.

A default Input/Output setting, with no arguments provided at runtime, generates the following output:
----
No input file provided - this program will default to addingthe included BST data to your existing tree
Wrong file or path - this program will default to stdout.

Post-order traversal of the tree: [-1, 1, 2, 0, 5, 13, 12, 8, 4, 3]
Height of the tree: 5
Number of the leaves in the tree: 4
Number of the internal nodes in the tree: 6
----
