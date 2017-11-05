!#/usr/local/bin/python3

import sys
import jinja2
import re

#### COMPLETE #####
# fasta gene object, attr: left end, right end, forwardness, lf/rt uncertainty 
class gene:
    id = ""
    l = 0
    r = 0
    sns = ""
    l_u = False
    r_u = False

    def __init__(self, args):
        self.id = args[0]
        self.l = args[1]
        self.r = args[2]
        self.sns = args[3]

        if len(args) >4:
            l_u = args[4]
            r_u = args[5]

#### COMPLETE #####			
# gene comparison object for genebank/genemark sequences
# 	attr: accession, 5'/3' genbank ends, genemark num, 5'/3' genemark ends,
#	uncertainty qualifier (3=3',5=5',8=both), end match qualifier (same as unc)
class gene_comparison:
	acc = ""
	p5 = 0
	p3 = 0
	sense = ""
	gm_id = ""
	gm_p5 = 0
	gm_p3 = 0
	uncertain = 0
	match = 0
	p5_agd = "disagrees"
	p3_agd = "disagrees"
	
	# constructor
	def __init__(self, args):
		self.acc = args[0]
		self.sense = args[1]
		if self.sense is "+":
			self.p5 = args[2]
			self.p3 = args[3]
			
		else:
			self.p5 = args[3]
			self.p3 = args[2]
			
		
		
	def set_uncertain(self, l_u, r_u):
		if self.sense is "+":
			if l_u:
				self.uncertain = self.uncertain + 5
			elif r_u:
				self.uncertain = self.uncertain + 3
		else:
			if l_u:
				self.uncertain = self.uncertain + 3
			elif r_u:
				self.uncertain = self.uncertain + 5
	
	def set_gm_pars(self, args):
		self.gm_id = args[0]
		if self.sense is "+":
			self.gm_p5 = args[1]
			self.gm_p3 = args[2]
		else:
			self.gm_p5 = args[2]
			self.gm_p3 = args[1]
			
	def set_match(self):
		if self.p5 == self.gm_p5:
			self.match = self.match + 5
			self.p5_agd = "agrees"
		if self.p3 == self.gm_p3:
			self.match = self.match + 3
			self.p3_agd = "agrees"
		
	
#### COMPLETE #####					
# load header from the fasta file, manipulate into title for analysis page
file = open("/home/dsaeva1/ecoli_o157h7_pO157.fasta").readlines()
header = file[0]
header = header.split(',',1)[0].strip('>')
header = header.split(' ',1)
gb_acc = header[0]
header = header[1].strip(' DNA')

#### COMPLETE #####
## load plasmid genes from cds file
file = open("/home/dsaeva1/ecoli_o157h7_pO157_cds.fasta").readlines()

cds_cnt = 0
cds_genes = []

# iterate over lines
for line in file:
	if '>' in line:
		cds_cnt = cds_cnt+1
		m = re.search('protein_id=([A-Z0-9.]*)\] \[location=([><a-z0-9,.()]*)', line)
		protein_id = m.group(1)
		sites = m.group(2)
		l_u = False
		r_u = False
		sns = "+"
			
		# for genes overlapping the plasmid OR, remove inner teminal sites
		if ',' in sites:
			sites = re.sub('[a-z()]', '', sites)
			m = re.search('([<0-9]*)..[0-9]*,[0-9]*..([>0-9]*)', sites)
			sites = [m.group(1),m.group(2)]
				
		# for others, split l/r sites into an array, if complement set sns "-"
		else:
			if 'complement' in sites:
				sns = "-"
				sites = sites.lstrip('complement(').rstrip(')')
			sites = sites.split('..')
			
		# check sites for '>'/'<' chars, label corr. site uncertain on match
		if '<' in sites[0]:
			l_u = True
			l = int(sites[0].strip('<'))
		else:
			l = int(sites[0])
			
		if '>' in sites[1]:
			r_u = True
			sites[1] = int(sites[1].strip('>'))
		else:
			r = int(sites[1])

		cds_genes.append(gene([protein_id, l, r, sns, l_u, r_u]))

#### COMPLETE #####
## load gm predicted genes from lst file
file = open("/home/dsaeva1/ecoli_o157h7_pO157.fasta.lst").readlines()

gm_cnt = 0
gm_genes = []
sns = ""
# iterate over lines, match gene num, polarity, start and end sites
for line in file:
	line = line.rstrip('\n')
	if ('+' in line or '-' in line):
		m = re.search('\s+(\d{1,3})\s+[+-]\s+([<>0-9]{1,6})\s+([<>0-9]{1,6})\s+', line)
		gm_cnt = m.group(1)
		sns = "+";
		
		# change sense to anti if "-" found
		if '-' in m.group(0):
			sns = "-";
		
		# deal with OR crossover, 
		if '<' in m.group(2):
			l = int(m.group(2).lstrip('<'))
			r = int(m.group(3))
			
		elif '>' in m.group(3):
			gm_genes[0].l = int(m.group(2))
			break
		
		else:
			l = int(m.group(2))
			r = int(m.group(3))
		
		gm_genes.append(gene([gm_cnt, l, r, sns]))


## examine genemark & genbank genes, make comparison objects, count matches
comparisons = []
ex_mat = 0
p5_mat = 0
p3_mat = 0
non_mat = 0
j = 0
j_dec = False
j_inc = False

# iterate over all genbank cds, instantiate gene_comparison object for each and
#	attempt to match ends to genemark cds. if matching hasn't occurred after 
#	setting	genemark params to adjacent predicted start sites, move on
for i in range(len(cds_genes):
	j_dec = False
	j_inc = False
	comparisons.append(gene_comparison([cds_genes[i].id, cds_genes[i].sns, cds_genes[i].l, 
		cds_genes[i].r]))
	comparisons[i].set_uncertain(cds_genes[i].l_u, cds_genes[i].r_u) 
	
	# while loop with matching algorithm for genemark cds 
	while comparisons[i].match == 10 and (not j_dec or not j_inc):
			
		# if both the genbank and predicted sequences are of the same sense,
		# 	set comparison object genemark params and run set_match()
		if cds_genes[i].sns == gm_genes[j].sns:
			comparisons[i].set_gm_pars([gm_genes[j].id, gm_genes[j].l, gm_genes[j].r])
			comparisons[i].set_match()
		
		# if the newly-set genemark params increased the match score, exit the while loop
		if comparisons[i].match > 0:
			j = j+1
			break
		k = j
		# otherwise iteratively decrement or increment j, avoiding index limitations
		#	look one direction for a match until an index limit is reached. 
		while k > 0:
			k = k-1
			if cds_genes[i].sns == gm_genes[k].sns:
				comparisons[i].set_gm_pars([gm_genes[k].id, gm_genes[k].l, gm_genes[k].r])
				comparisons[i].set_match()
			if comparisons[i].match > 0:
				j = k+1
				break
		j_dec = True		
		k = j
		while k < len(gm_genes)-1:
			k=k+1
			if cds_genes[i].sns == gm_genes[k].sns:
				comparisons[i].set_gm_pars([gm_genes[k].id, gm_genes[k].l, gm_genes[k].r])
				comparisons[i].set_match()
			if comparisons[i].match > 0:
				j = k+1
				break
		j_inc = True
	
	# increment corresponding match counter
	if comparisons[i].match == 3:
		p3_mat = p3_mat+1
	elif comparisons[i].match == 5:
		p5_mat = p5_mat+1
	elif comparisons[i].match == 8:
		ex_mat = ex_mat+1
	else:
		non_mat = non_mat+1
	
# jinja2 setup
templateLoader = jinja2.FileSystemLoader(
	searchpath = "/var/www/html/dsaeva1/midterm/templates")
env = jinja2.Environment(loader = templateLoader)
template = env.get_template("midterm.html")
		
print("Content-type: text/html\n\n")
print(template.render(header = header, cds_cnt = len(cds_genes), gm_cnt =
		len(gm_genes), exact_cnt = ex_mat, p5_cnt = p5_mat, p3_cnt = p3_mat,
		non_mat = nomat_cnt, acc = gb_acc, genes = comparisons))
