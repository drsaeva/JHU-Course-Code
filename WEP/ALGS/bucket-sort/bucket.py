#!/usr/bin/python3

# ^^^ change to represent your own python path if needed ^^^

# bucket.py
# @author David R Saeva
# represents a bucket (linked list) used in bucket-sort

# node class representing nodes in a bucket        
class Node:
    # constructor
    def __init__(self, val = None, next = None):
        self.val = val
        self.next = next
        
    def __str__(self):
        return str(self.val)
    
###################################################################
# bucket class represented by a singly-linked-list implementation
class Bucket:
    

    # constructor
    def __init__(self):
        self.root = None
      

    # if empty add new item as root, otherwise call insert_item
    def add_item(self, val):
        if self.root is None:
            self.root = Node(val, None)
        else:
            self.insert_item(self.root, val)
            

    # inserts an item into the bucket so that it obeys increasing order        
    def insert_item(self, node, val):
        # stop case: bucket is empty, so add this as the new root
        if self.root is None:
            self.root = Node(val, None)
            
        # stop case: bucket root is greater than val, insert val as new root
        elif self.root.val > val:
            new = Node(val, self.root)
            self.root = new
        
        # recurse through bucket until current < val but current.next > val    
        elif node.val < val: 
            if node.next is None:
                new = Node(val, None)
                node.next = new
            elif node.next.val > val:
                new = Node(val, node.next)
                node.next = new
            elif node.next.val < val:
                self.insert_item(node.next, val)    
            
        

    # to-string method for bucket
    def __str__(self):
        if self.root is not None:
            current = self.root
            out = current.__str__()
            while current.next is not None:
                current = current.next
                out += "\n" + current.__str__()
            return out
        else:
            return ''
        
    # clears the bucket
    def clear(self):
        self.__init__()
