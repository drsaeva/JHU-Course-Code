## bash code, RSEM
# Reference preparation
genomeFastaFile=~/Genomics/Data/softmask_partial.fa
annotation=/mnt/d/Data/hw3/genes.gtf
bowtiepath=~/Genomics/bowtie2-2.3.1

rsem-prepare-reference --gtf $annotation --bowtie2 --bowtie2-path $bowtiepath -p 8 $genomeFastaFile hg38

# Read Mapping
bowtiepath=~/Genomics/bowtie2-2.3.1
thyroid1=/mnt/d/Data/hw3/s_1_1_sequence.txt
thyroid2=/mnt/d/Data/hw3/s_1_2_sequence.txt
rsem-calculate-expression -p 8 --paired-end --bowtie2 --bowtie2-path $bowtiepath --append-names --output-genome-bam  --strandedness forward --bowtie2-sensitivity-level fast $thyroid1 $thyroid2 hg38 thyroid

bowtiepath=~/Genomics/bowtie2-2.3.1
testes1=/mnt/d/Data/hw3/s_2_1_sequence.txt
testes2=/mnt/d/Data/hw3/s_2_2_sequence.txt
rsem-calculate-expression -p 8 --paired-end --bowtie2 --bowtie2-path $bowtiepath --append-names --output-genome-bam  --strandedness forward --bowtie2-sensitivity-level fast $testes1 $testes2 hg38 testes


## R code
# load thyroid, testes gene.results & prep fpkm/tpm vals for pearson corr
thy <- read.table("D:/Data/hw3/thyroid.gene.results", header=T, row.names=1)
thy.fpkm <- log2(thy$fpkm +1)
thy.tpm <- log2(thy$tpm +1)

tes <- read.table("D:/Data/hw3/testes.gene.results", header=T, row.names=1)
tes.fpkm <- log2(tes$fpkm +1)
tes.tpm <- log2(tes$tpm +1)

plot(thy.fpkm, tes.fpkm, xlab="Thyroid, FPKM", ylab="Testes, FPKM", main="Tumor/normal differential
gene expression in Testes vs Thyroid, FPKM")
corr(thy.fpkm, tes.fpkm, use="complete.obs", method="pearson")

plot(thy.fpkm, tes.fpkm, xlab="Thyroid, TPM", ylab="Testes, TPM", main="Tumor/normal differential
gene expression in Testes vs Thyroid, TPM")
corr(thy.tpm, tes.tpm, use="complete.obs", method="pearson")

fc.fpkm <- foldchange(tes.fpkm, thy.fpkm)
fc.tpm <- foldchange(tes.tpm, thy.tpm)

fc.fpkm <- sort(fc.fpkm, decreasing=T)
fc.fpkm[1:10]
fc.tpm <- sort(fc.tpm, decreasing=T)
fc.fpkm[1:10]

## bash code, samtools
# sort/index
samtools sort -o thyroid_sort.bam -@ 8 thyroid.bam
samtools index thyroid_sort.bam -@ 8 

samtools sort -o testes_sort.bam -@ 8 testes.bam
samtools index testes_sort.bam -@ 8 
