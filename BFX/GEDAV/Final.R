# Obstructive sleep apnea response to continuous positive airway pressure treatment: leukocytes
#
# Summary: Analysis of peripheral blood leukocytes from patients with severe obstructive sleep apnea 
#    (OSA) before and after continuous positive airway pressure (CPAP) treatment. Results provide 
#    insight into the system-wide molecular response of OSA patients to CPAP therapy.
#
# Platform: GPL6244: [HuGene-1_0-st] Affymetrix Human Gene 1.0 ST Array [transcript (gene) version]
#
# Citation: Gharib SA, Seiger AN, Hayes AL, Mehra R et al. Treatment of obstructive sleep apnea alters 
#    cancer-associated transcriptional signatures in circulating leukocytes. Sleep 2014 Apr 1;37(4):709-14,
#    714A-714T. PMID: 24688164

# load Bioconductor, install GEOquery for loading GEO
source("http://www.bioconductor.org/biocLite.R")
biocLite("GEOquery")
# or if GEOquery is already installed
library(GEOquery)
# load GEO soft file
setwd("D:/Data")
gds <- getGEO(filename='GDS5358.soft.gz')
colnames(Table(gds))
Table(gds)[1:10,1:6]
eset <- GDS2eSet(gds, do.log2=TRUE)
gds.exp <- exprs(eset)

#### OUTLIER DETECTION ######

# correlation plot

library(gplots)
gds.cor <- cor(gds.exp)

layout(matrix(c(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 2, 2), 8, 2, byrow=TRUE))
par(oma=c(5, 7, 1, 1))
leg <- seq(min(gds.cor, na.rm=TRUE), max(gds.cor, na.rm=TRUE), length=10)
image(gds.cor, main="Correlation plot of SA Pt Samples before/after COPD treatment", axes=F)
axis(1, at=seq(0, 1, length=ncol(gds.cor)), label=dimnames(gds.cor)[[2]], cex.axis=0.9, las=2)
axis(2, at=seq(0, 1, length=ncol(gds.cor)), label=dimnames(gds.cor)[[2]], cex.axis=0.9, las=2)

par(mar=rep(2, 4))
image(as.matrix(leg), axes=F)
tmp <- round(leg, 2)
axis(1,at=seq(0, 1, length=length(leg)), labels=tmp, cex.axis=1)

# avg corr plot
ave <- apply(gds.cor, 1, mean) 
par(oma=c(3, 0.1, 0.1, 0.1)) 
plot(c(1, length(ave)), range(ave), type="n", main="Average Correlation Plot for SA Pt Samples", xlab="", ylab ="Average Correlation (r)", axes=F) 
points(ave, bg="Red", col=1, pch=21, cex=1.25) 
axis(1, at = c(1:length(ave)), labels = dimnames(gds.exp)[[2]], las=2, cex.lab=0.4, 
cex.axis=0.6) 
axis(2) 
abline(v = seq(0.5, 62.5, 1)) 

# cv vs mean plot
gds.mean <- apply(log2(gds), 2, mean) 
gds.stdev <- sqrt(apply(log2(gds), 2, var)) 
gds.cv <- gds.stdev/gds.mean 
plot(gds.mean, gds.cv, main ="Sample Coeff. of Variance v. Mean", xlab="Mean", ylab="CV", col="Red", cex=1.5, type="n") 
points(gds.mean, gds.cv, bg ="Blue", col=1, pch=21) 
text(gds.mean, gds.cv, label=dimnames(gds)[[2]], pos=1, cex=0.5) 

#### FEATURE SELECTION #####

# subset object to dataframe, columns only represent samples (Table(gds) produces two cols with gene id#s/identifiers)
# set row names in subset equal to id#'s - identifying names can't be used due to duplications
gds.nex <- Table(gds)[,3:38]
rownames(gds.nex) <- Table(gds)[,1]

# set class vectors

pre <- c(1,3,5,7,9,11,13,15,17,19,21,23,25,27,29,31,33,35)
post <- c(2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,32,34,36)

classes <- colnames(gds.nex)
pre.cl <- classes[pre]
post.cl <- classes[post]

# filter out genes with avg expression < 50
gds.rm <- rowMeans(gds.nex)
gds.rm.50 <- gds.rm[gds.rm > 50]
gds.nex.50 <- gds.nex[gds.rm.50, ]

# filtered out 8248 genes below avg expression val 
> nrow(gds.nex.50)
[1] 25049
> nrow(gds.nex)
[1] 33297

# log2 e values
gds.log2 = log2(gds.nex.50)

# t-test all genes
t.test.all.genes <- function(x, d1, d2){
	x1 <- x[d1]
	x2 <- x[d2]
	x1 <- as.numeric(x1)
	x2 <- as.numeric(x2)
	t.out <- t.test(x1, x2, alternative="two.sided", var.equal=T)
	out <- as.numeric(t.out$p.value)
	return(out)
}

p.values <- apply(gds.log2, 1, t.test.all.genes, d1=pre.cl, d2=post.cl)

# plot p-values, histogram p-values
plot(
	p.raw, type = "b", pch = 1, col = "Pink",
	xlab="Genes",
	ylab="P-values",
	main="Raw P-values for All Genes From SA Pt PBLs\n"
)

hist(
  p.values, col="lightblue",	xlab="p-values", 
  main="P-values of probesets to Pre- \n and Post-CPAP 
  treated SA patient PBLs", 
  cex.main=0.9
)
abline(v=0.05, col=2, lwd=2)

# fold change 
pre.mean <- apply(gds.log2[, pre.cl], 1, mean, na.rm=T)
post.mean <- apply(gds.log2[, post.cl], 1, mean, na.rm=T)
fold <- pre.mean - post.mean

# fold change histogram
hist(
	fold, col="Green", xlab="Log2 Fold Change", ylab="Frequency",
	main = paste("Histogram of Fold change values for SA Pt PBLs")
)

# transform p-values, make volcano plot, draw two lines at 2 and -2
p.values.trans <- (-1*log10(p.values))

plot(range(p.values.trans), range(fold), type="n", xlab="-1 * log10 (P-Value)", 
ylab="Fold Change", main="Volcano plot of fold changes between pre- and post-CPAP
  treatment in SA Pt PBLs")

points(p.values.trans, fold, col=1, bg=1, pch=21)

points(p.values.trans[(p.values.trans > -log10(0.05) & fold > 0.1)], fold[(
p.values.trans > -log10(0.05) & fold > 0.1)], col=1, bg=2, pch=21)

points(p.values.trans[(p.values.trans > -log10(0.05) & fold < -0.1)], fold[(
p.values.trans > -log10(0.05) & fold < -0.1)], col=1, bg=3, pch=21)

abline(v= -log10(0.05))
abline(h= -0.1)
abline(h= 0.1)
