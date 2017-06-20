#!/usr/bin/python3
import sys

print("\nThis script requires you to pass in the file path to the genome FASTA you"
	" wish to analyze as the first argument.\nIf you do not specify a file via "
	"arguments, the script will automatically use 'e_coli_k12_dh10b.faa' and "
	"assume\nits presence in your home directory.\n")

# file import and associated error handling
if len(sys.argv) > 1:
	file = sys.argv[1]
else: 
	file = "~/e_coli_k12_dh10b.faa"

try:
	ecoli = iter(open(file).readlines())
except OSError as e:
	print("Wrong file or path - please try again with a proper file path in the"
	" arguments or the 'e_coli_k12_dh10b.faa' FASTA in your home directory.")
	sys.exit()
	
# get accession from user
acc = raw_input("Please provide an NCBI protein accession number to look for.\n")

# check genome file for accession, print all lines in accession until next gene
for line in ecoli:
	if acc in line:
		print '\n',line
		line = next(ecoli)
		while not '>' in line:
			print line
			line = next(ecoli)
		break
