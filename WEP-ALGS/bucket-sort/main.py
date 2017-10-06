#!/usr/bin/python3

# ^^^ change to represent your own python path if needed ^^^

from bucket import Bucket
import sys


# main.py
# @author David R Saeva
# performs bucket-sort on an array passed in through args or a default array
# prints to a file path passed in through args or to stdout (default)

# standard input array provided by CLRS3e 8.4-1

default = [0.79, 0.13, 0.16, 0.64, 0.39, 0.20, 0.89, 0.53, 0.71, 0.42]
input = default

# check for passed in input file arg, attempt to read file
if len(sys.argv) > 0 :
    try:
        input = open(sys.argv[1]).readlines()
    except Exception as e:
        print("Invalid or no file path provided.")
        print("Using default array as shown in CLRS3e 8.4-1.") 
        pass    

# check values read from input file or existing input array, if invalid
for i in input:
    try:
        temp = int(i)
        if temp > 1 or temp <0:
            print("Invalid value found in file. File must contain float values"
                  " no greater than 1 or no less than 0, 1 value per line.")
            input = default
    except TypeError as e:
        print("Invalid values found in file.")
        print("Using default array as shown in CLRS3e 8.4-1.")
        input = default
        break 
    if len(str(i).strip()) == 0:
        print("Using default array as shown in CLRS3e 8.4-1.")
        input = default
        break
    
print('Array input to be sorted using bucket sort: \n') 
print(input)
sorted_arr = []

# execute bucket sort
for i in range(10):
    sorted_arr.append(Bucket())


for i in input:
    index = int(i*10)
    sorted_arr[index].add_item(i)

# if output file path provided, print to file. otherwise print to stdout
if len(sys.argv) > 1:
    try:
        output = open(sys.argv[2])
        for i in sorted_arr:
            output.write(i)
    except Exception as e:
        print("Invalid output file path provided. Writing to stdout instead.")
        print('Sorted array from input: ')
        for i in sorted_arr:
            print(i)
        
else:
    print("\nNo output file path provided. Writing to stdout instead.")
    print('Sorted array from input: ')
    for i in sorted_arr:
        print(i)
