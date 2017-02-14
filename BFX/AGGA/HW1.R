## APT code, windows command line
# gene estimates (exon array)
apt-probeset-summarize -a rma-sketch -a dabg 
-p files/HuEx-1_0-st-v2.r2.pgf 
-c files/HuEx-1_0-st-v2.r2.clf 
-b files/HuEx-1_0-st-v2.r2.antigenomic.bgp 
--qc-probesets files/HuEx-1_0-st-v2.r2.qcc 
-m files/HuEx-1_0-st-v2.r2.dt1.hg18.core.mps 
--cel-files files/cel_list.txt 
-o gene_output

# exon estimates (exon array)
apt-probeset-summarize -a rma-sketch 
-p files/HuEx-1_0-st-v2.r2.pgf 
-c files/HuEx-1_0-st-v2.r2.clf 
-b files/HuEx-1_0-st-v2.r2.antigenomic.bgp 
--qc-probesets files/HuEx-1_0-st-v2.r2.qcc 
-s files/HuEx-1_0-st-v2.r2.dt1.hg18.core.ps 
--cel-files files/cel_list.txt 
-o exon_output

## R code
# read in exon annotation file, exon and gene matrices

exon <- read.table("D:/Data/cel/exon_output/rma-sketch.summary.txt", skip=121, header=T)
colnames(exon)
nrow(exon)

gene <- read.table("D:/Data/cel/gene_output/rma-sketch.summary.txt", skip=121, header=T)
ex_annot <- read.table("D:/Data/cel/HuEx-1_0-st-v2.na24.hg18.probeset_abbr.csv", header=T, sep=",")

gene.dat <- gene[,2:16]
exon.dat <- exon[,2:16]

g.pca <- prcomp(t(gene.dat))
plot(g.pca$x[,1:2], main="Two principal components of gene matrix data")

e.pca <- prcomp(t(exon.dat))
plot(e.pca$x[,1:2], main="Two principal components of exon matrix data")


# exon ni value calculation and t-test function
# takes transcript cluster values, exon probes for this transcript cluster, and classification vector
exon.ni <- function(genex,exonx,rx) {

ni <- t(t(exonx)-genex)
ttest <- t(apply(ni,1,t.two,sam=as.logical(r),v=F))
return(ttest)

}

#two-sample t-test function (called by an apply() function)

t.two <- function(x,sam,v=F) {

x <- as.numeric(x)
out <- t.test(as.numeric(x[sam]),as.numeric(x[!sam]),alternative="two.sided",var.equal=v)
o.pvals <- as.numeric(c(out$statistic,out$p.value,out$conf.int[1],out$conf.int[2]))
names(o.pvals) <- c("test_statistic","pv","lower_ci","upper_ci")

return(list("p-values"=o.pvals, "fold change"=log2(x)))

}

# intersect annotations & exon matrix, subset each by intersection results
ex.probes <- intersect(exon[,1], rownames(ex_annot))
ex_annot.sub <- ex_annot[ex.probes,]
exon.lab <- exon.dat
row.names(exon.lab) <- exon[,1]
exon.sub <- exon.lab[ex.probes,]

clon
