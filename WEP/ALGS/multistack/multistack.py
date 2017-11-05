#!/usr/bin/python3

# ^^^ change to represent your own python path if needed ^^^

import sys

# multistack.py
# @author David R Saeva
# instantiates a multistack using passed-in data with standard stack operations
#     instantiate of a multistack creates two stacks

# class for doubly-linked list (DLL) node - stack implemented as DLL
class stack_item:
    
    # constructor
    def __init__(self, value, next, prev):
        self.value = value
        self.next = next
        self.prev = prev
    
    def __str__(self):
        return str(self.value)

# single stack implementation as DLL
class stack:
    #constructor
    def __init__(self, values, size_limit = 50):
        self.top = None
        self.size = 0
        self.size_limit = size_limit
        if values is not None:
            if isinstance(values, list):
                if len(values) > size_limit:
                    self.size_limit = len(values)
                for i in values:
                    self.push(i)
            else:
                new = stack_item(values, None, None)
                self.push(values)
            
    # pushes an item onto the stack
    def push(self, value):
        if self.size < self.size_limit:
            new_item = stack_item(value, None, None)
            if self.top is not None:
                old_item = self.top
                old_item.prev = new_item
                new_item.next = old_item
            self.top = new_item
            self.size += 1
        else:
            print('Stack overflow!')
    
    # pops an item from the stack
    def pop(self):
        if self.size > 0:   
            removed_item = self.top
            if removed_item.next is not None:
                self.top = removed_item.next
                self.top.prev = None
                removed_item.next = None
            self.size -= 1
            return removed_item
        else:
            print('Stack underflow!')
    
    # returns the top item in the stack without removing it
    def peek(self):
        return self.top.__str__()
# multistack containing two stack objects with operations for manipulating each    
class multistack:
    def __init__(self, values_A, values_B):
        if len(values_A) > 0:
            self.a = stack(values_A)
        if len(values_B) > 0:
            self.b = stack(values_B)
        
        
    def PushA(self, x):
        self.a.push(x)
    
    def PushB(self, x):
        self.b.push(x)
        
    def MultiPopA(self, k):
        popped = list()
        for i in range((k, self.a.size)):
            popped.append(self.a.pop())
        return popped
    
    # pops k elements from 
    def MultiPopB(self, k):
        popped = list()
        for i in range((k, self.b.size)):
            popped.append(self.b.pop())
        return popped
    
    # transfers k elements from a to b via pop/push, where k <= n, size of a
    def Transfer(self, k):
        for i in range(min(k, self.a.size)):
            self.b.push(self.a.pop())
    
    # print top of each stack to examine multistack state
    def print_tops(self):
        return('Top of stacks A and B:'+'\nA: ' + self.a.peek() + '\nB: ' + 
              self.b.peek())
