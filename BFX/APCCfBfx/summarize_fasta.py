#!/usr/bin/python3

import sys

print("\nThis script requires you to pass in the file path to the genome FASTA you"
	" wish to analyze as the first argument.\nIf you do not specify a file via "
	"arguments, the script will automatically use 'e_coli_k12_dh10b.faa' and\n"
	"assume its presence in your home directory.\n")

# file import and associated error handling
if len(sys.argv) > 1:
	file = sys.argv[1]
else: 
	file = "~/e_coli_k12_dh10b.faa"

try:
	ecoli = open(file).readlines()
except OSError as e:
	print("Wrong file or path - please try again with a proper file path in the"
	" arguments or the 'e_coli_k12_dh10b.faa' FASTA in your home directory.")
	sys.exit()

# print file header to begin FASTA summary
print "header:", ecoli[0]
	
# count number of genes & hypothetical genes, protein sequence lengths 
num_genes = 0
num_hyp_genes = 0
pro = 0
pro_lens = []
for line in ecoli:
	if '>' in line:
		num_genes = num_genes+1
		if 'hypothetical' in line:
			num_hyp_genes = num_hyp_genes+1
			
		pro_lens.append(pro)
		pro = 0
	else:
		pro = pro+len(line)
		
print "gene count:",num_genes
print "min protein length:",min(pro_lens)
print "max protein length:",max(pro_lens)
print "avg protein length:",sum(pro_lens)/len(pro_lens)
print "number of genes with 'hypothetical':",num_hyp_genes
