Multi-Stack
by David R. Saeva
last updated: 7/24/17 
licensed under the GNU GPL v 3.0 

This python package contains three files:

multistack.py
---------
This contains all class code necessary to produce a multistack containing two stacks. Stacks 
are implemented as doubly-linked lists, containing node objects, with methods for push, pop,
peek, and size. Multistack contains all code referencing the operations specified in CLRS3e 
Chapter 17 Problem 2, including PushA, PushB, MultiPopA, MultiPopB, and Transfer. A print 
method printing the top of each stack (a.peek() and b.peek()) is included for printing stack 
states to stdout after a multistack operation is called. This file must be in the same 
directory as main.py in order for proper execution. It contains a shebang line for a python3 
interpreter path ('/usr/bin/python3') - if the path currently in that file is not your python3 
path and you wish to execute this program in a non-windows environment, alter that path to fit 
the location of your python 3 interpreter.

main.py
---------
This file contains all code necessary for input/output functions and the actual execution of 
multistack. Input and output files should be provided with the following syntax in command line:

python main.py <stack a input path> <stack b input path> <stack a output path> <stack b output path>

This file can run with two input files, two input files AND two output files, or neither. Input 
files must contain a single numerical value per line, which will be cast to integer format. In 
the event that you do not provide two output files, the file stack states after operations are 
finished will print to stdout instead. In the event that two input files are not provided, this 
file will use a default pair of arrays to initialize the stacks instead. Invalid input files or 
invalid data read off a line will result in the program defaulting to the default stack values. 
Multistack operations can be ordered by the user by providing the corresponding integer input 
when prompted. Invalid inputs will be ignored, and multistack operations can called so long as 
there are values in arrays (e.g. a Transfer will fail if stack A lacks items, and will notify the 
user of such). Multistack file execution will continue to loop and request user input for 
operations until the user provides the input value corresponding to 'end program' (6). At this 
time the program will print the final stacks top states to stdout and then attempt to write the 
values in each stack to output files in top-to-bottom format.
