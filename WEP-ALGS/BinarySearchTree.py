#!/usr/bin/env python3

import sys

# This file contains all code necessary to run the BST, take input, and generate the desired
#    output as requested in the PA 1.pdf document. 
# @author: D.R. Saeva, 7/3/2017

class Node:
    def __init__(self, data):
        self.left = None
        self.right = None
        self.data = data

class BST:
    # Constructor for BST
    def __init__(self):
        self.root = None
        
    # Default node addition method. Overrided in the event of an existing root by _add
    def add(self, val):
        if(self.root == None):
            self.root = Node(val)
        else:
            self._add(val, self.root)
    
    # Add method for a BST with an existing root. Nodes are added left or right based on their
    #    value compared to the root, as well as child nodes beneath the root.
    def _add(self, val, node):
        if(val < node.data):
            if(node.left != None):
                self._add(val, node.left)
            else:
                node.left = Node(val)
        else:
            if(node.right != None):
                self._add(val, node.right)
            else:
                node.right = Node(val)
    
    # Called to generate a default tree in the event that no input file is provided
    def defaultTree(self):
        self.add(3)
        self.add(4)
        self.add(0)
        self.add(8)
        self.add(2)
        self.add(12)
        self.add(-1)
        self.add(1)
        self.add(5)
        self.add(13)
    
    # Traverses each node in the BST in a post-order fashion recursively
    # Visited nodes are stored in an array which is returned.        
    def postOrderTraversal(self, node):
        traversal = []
        def visit(node):
            if not node:
                return
            else:
                visit(node.left)
                visit(node.right)
                traversal.append(node.data)
        visit(node)
        return traversal
    
    # Determines the height of the BST via recursively locating the deepest leaf node.
    # Returns this value.
    def height(self, node):
        if (node.left != None):
            l_h = self.height(node.left)
        else:
            l_h = 0
        if (node.right != None):
            r_h = self.height(node.right)
        else:
            r_h = 0
        return 1 + max(l_h, r_h)
    
    # Counts the number of leaf nodes in the BST via recursion.
    # Returns this value.
    def getNumberOfLeaves (self):
        def visit(node):
            numLeaves = 0
            if (node.right is None and node.left is None):                
                numLeaves += 1
            if (node.right is not None):
                numLeaves += visit(node.right)
            if (node.left is not None):
                numLeaves += visit(node.left)
            return numLeaves
        return visit(self.root)
        
    # Counts the number of internal nodes in the BST via recursion.
    # Returns this value.
    def getNumberOfNonLeaves(self):
        def visit(node):
            numNonLeaves = 0
            if (node.right is not None or node.left is not None):
                numNonLeaves += 1
                if (node.right is not None):
                    numNonLeaves += visit(node.right)
                if (node.left is not None):
                    numNonLeaves += visit(node.left)
            return numNonLeaves
        return visit(self.root)

    # Prints requested method outputs to stdout
    def printOutputsToStdout(self):
        print("Wrong file or path - this program will default to stdout.\n")
        print('Post-order traversal of the tree:', tree.postOrderTraversal(self.root))
        print('Height of the tree:', self.height(self.root))
        print('Number of the leaves in the tree:', self.getNumberOfLeaves())
        print('Number of the internal nodes in the tree:', self.getNumberOfNonLeaves())
        
## DEFAULT TREE ##            
#      3
#   0     4
#-1   2      8
#    1      5  12
#                13

# creates the initially-empty BST
tree = BST()

## Check input file, read it if possible, and generate BST. Catch associated exceptions
##     and add default BST nodes in that event
if (len(sys.argv) == 1):
    print("No input file provided - this program will default to adding" 
          "the included BST data to your existing tree")
    tree.defaultTree()
else:
    try:
        input = open(sys.argv[1], 'r').readlines()
        for line in input:
            try:
                if (line != -12935105):
                    tree.add(line)
            except TypeError as e:
                print("Non-numeric data in file - this program will default to adding" 
                      "the included BST data to your existing tree")
                tree.defaultTree()
                break
        input.close()
    except OSError as e:
        print("Wrong file or path - this program will default to the included BST.")
        tree.defaultTree()
        
# write to output file, or stdout in the event of an invalid output file
if (len(sys.argv) > 2):
    try:
        output = open(sys.argv[2], 'w')
        output.write('Post-order traversal of the tree:', tree.postOrderTraversal(tree.root))
        output.write('Height of the tree:', tree.height(tree.root))
        output.write('Number of the leaves in the tree:', tree.getNumberOfLeaves())
        output.write('Number of the internal nodes in the tree:', tree.getNumberOfNonLeaves())
    except OSError as e:
        tree.printOutputsToStdout()
else:
    tree.printOutputsToStdout()
