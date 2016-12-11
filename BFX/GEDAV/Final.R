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

# subset object to dataframe, columns only represent samples (Table(gds) produces two cols with gene id#s/identifiers)
# set row names in subset equal to id#'s - identifying names can't be used due to duplications
gds.nex <- Table(gds)[,3:38]
rownames(gds.nex) <- Table(gds)[,1]

# filter out genes with avg expression < 50
gds.rm <- rowMeans(gds.nex)
gds.rm.50 <- gds.rm[gds.rm > 50]
gds.nex.50 <- gds.nex[gds.rm.50, ]

# filtered out 8248 genes below avg expression val 
> nrow(gds.nex.50)
[1] 25049
> nrow(gds.nex)
[1] 33297



# heirarchical clustering dendrogram
rcc.transposed <- t(rcc) 
rcc.dist <- dist(rcc.transposed, method = "euclidean") 
rcc.clusters <- hclust(rcc.dist, method = "single") 
plot(rcc.clusters, main="Dendrogram of Renal Epithelial Sample Clustering", xlab="Samples", ylab="Distance between clusters", cex = 0.75) 

# cv vs mean plot
rcc.mean <- apply(log2(rcc), 2, mean) 
rcc.stdev <- sqrt(apply(log2(rcc), 2, var)) 
rcc.cv <- rcc.stdev/rcc.mean 
plot(rcc.mean, rcc.cv, main ="Renal Epithelial Sample Coeff. of Variance v. Mean", xlab="Mean", ylab="CV", col="Red", cex=1.5, type="n") 
points(rcc.mean, rcc.cv, bg ="Blue", col=1, pch=21) 
text(rcc.mean, rcc.cv, label=dimnames(rcc)[[2]], pos=1, cex=0.5) 

