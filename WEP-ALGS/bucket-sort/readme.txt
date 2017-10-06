Bucket-Sort
by David R. Saeva
last updated: 7/24/17 
licensed under the GNU GPL v 3.0 

This python package contains two files:

bucket.py
---------
This contains all class code necessary to produce a bucket (linked list) that exists in the 
array utilized by the bucket sort algorithm for sorting, along with all code necessary to 
perform the actual sorting operations. This file must be in the same directory as main.py in 
order for proper execution. It contains a shebang line for a python3 interpreter path 
('/usr/bin/python3') - if the path currently in that file is not your python3 path and you 
wish to execute this program in a non-windows environment, alter that path to fit the location 
of your python 3 interpreter.

main.py
---------
This file contains all code necessary for input/output functions and the actual execution of 
bucket sort. Input and output files should be provided with the following syntax in command line:

python main.py <input file path> <output file path>

This file can run with an input file, an input file AND an output file, or neither. Input files 
must contain a single float per line, and all input values must be between 0 and 1. In the event 
that you do not provide an output file, the sorting results will print to stdout instead. In the 
event that an input file is not provided, this file will use the default array shown for bucket 
sort operation in the CLRS3e problem 8.4-1 instead. Invalid input files or data read off a line 
will result in the program defaulting to the default array.
