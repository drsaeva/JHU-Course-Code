#!/usr/bin/python3

# ^^^ change to represent your own python path if needed ^^^

import sys
from multistack import multistack, stack

# main.py
# @author David R Saeva
# instantiates a pair of stacks using passed-in data or default values
# prints the states of stacks to stdout prior to and after operations 


input_a = [2, 5, 10, 7, 33, 16, 3, 24, 1, 14]
input_b = [12, 8, 0, 27, 13, 6, 1, 18, 31, 21]        
k_val_notice = ('Note that if "k" is greater than the number of values in '+
          'the first multistack, "k" will automatically be set to that number '+
          'instead.')

if len(sys.argv) > 2:
    try:
        input_for_a = open(sys.argv[1]).readlines()
        a_num = True
        for i in input_for_a:
            if not i.test_isnumeric():
                a_num = False
        if a_num:
            input_a = input_for_a
        
    except Exception as e:
        print("Invalid file path provided.")
        print("Using default array: <2, 5, 10, 7, 33, 16, 3, 24, 1, 14>") 
        pass
    try:
        input_for_b = open(sys.argv[1]).readlines()
        b_num = True
        for i in input_for_b:
            if not i.test_isnumeric():
                b_num = False
        if b_num:
            input_b = input_for_b
    except Exception as e:
        print("Invalid file path provided.")
        print("Using default array: <12, 8, 0, 27, 13, 6, 1, 18, 31, 21>")
        pass
else:
    print('No input files provided. Using the following default arrays:\n'
          '<2, 5, 10, 7, 33, 16, 3, 24, 1, 14>\n'
          '<12, 8, 0, 27, 13, 6, 1, 18, 31, 21>\n')
m = multistack(input_a, input_b)
m.print_tops()
userin = 0
print('Please provide a numerical input corresponding to one of the'
          'available operations to the Multi-multistack program:\n'
          '1. Push value "x" onto the first multistack.\n'
          '2. Push value "x" onto the second multistack.\n' 
          '3. Remove "k" number of values from the first multistack.\n'
          '4. Remove "k" number of values from the second multistack.\n'
          '5. Transfer "k" number of values from the first multistack to the '
          'second.\n' 
          '6. Exit the program.\n')
while True:
    print(m.print_tops())
    try:
        userin = int(input('\nMulti-multistack action: '))
    except Exception as E:
        print('Incorrect option typed. Please try again!')
        pass
    
    # call PushA with user input value
    if userin == 1:
        try:
            value = int(input('Please provide the value you wish to push to the'
                      ' first multistack: '))
            m.PushA(value)
        except Exception as e:
            print('Non-numeric value provided to be pushed to the multistack. Only'
                  ' numeric values can be pushed to the multistack.')
        userin = 0
        
    # call PushB with user input value
    elif userin == 2:
        try:
            value = int(input('Please provide the value you wish to push to the'
                      ' second multistack: '))
            m.Pushb(value)
        except Exception as e:
            print('Non-numeric value provided to be pushed to the multistack. Only'
                  ' numeric values can be pushed to the multistack.')
        userin = 0
        
    # call MultiPopA with user input value
    elif userin == 3:
        try:
            value = int(input('Please provide the number of items you want to '
                      'remove from the first multistack: '))
            print(k_val_notice)
            if value > m.a.size:
                value = m.a.size
            m.MultiPopA(value)
        except Exception as e:
            print('Non-numeric value provided. Removing 1 item from the multistack'
                  ' instead.')
        userin = 0
        
    # call MultiPopB with user input value
    elif userin == 4:
        try:
            value = int(input('Please provide the number of items you want to '
                      'remove from the second multistack: '))
            m.MultiPopB(value)
        except Exception as e:
            print('Non-numeric value provided. Removing 1 item from the multistack'
                  ' instead.')
        userin = 0
    
    # call Transfer with user input value
    elif userin == 5:
        try:
            value = int(input('Please provide the number of items you want to '
                'remove from the first multistack and push to the second multistack: '))
            if m.a.size > 0:
                m.Transfer(value)
            elif m.a.size() > 0:
                print('Illegal operation - the first multistack is empty.')
        except Exception as e:
            print('Non-numeric value provided. Removing 1 item from the multistack'
                  ' instead.')
        userin = 0 
        
    elif userin == 6:
        break

print('Final multistack states:\n' + m.print_tops())
print('Attempting to print to output files.')

# if valid output file paths are provided, print each multistack top to bottom to
#    each provided file. if a path is invalid, instead print that multistack's data
#    to stdout
if len(sys.argv) > 4:
    try:
        out_for_a = open(sys.argv[3])
        out_for_a.write('Stack - from top to bottom:\n')
        while m.a.size > 0:
            out_for_a.write(m.a.pop())
            
    except Exception as e:
        print("Invalid file path provided.")
        print("Cannot write the first multistack to an output file. Printing values"
              " top to bottom to stdout:\n")
        while m.a.size() > 0:
            print(m.a.pop())
        pass
    try:
        out_for_b = open(sys.argv[4])
        while m.b.size > 0:
            out_for_a.write(m.a.pop())
    except Exception as e:
        print("Invalid file path provided.")
        print("Cannot write the first multistack to an output file. Printing values"
              " top to bottom to stdout")
        while m.b.size > 0:
            print(m.b.pop())
        pass
else:
    print("Cannot write either multistack to an output file. Printing values top to"
          " bottom to stdout")
    print('\nFirst multistack:\n')
    while m.a.size > 0:
        print(m.a.pop())
    print('\nSecond multistack:\n')
    while m.b.size > 0:
        print(m.b.pop())
