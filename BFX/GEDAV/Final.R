# Uses RNAseq data (https://www.ncbi.nlm.nih.gov/geo/query/acc.cgi?acc=GSE86004) from Kim, Lu, and Zhang's 2016 Cell Reports pub on 
# mechanisms driving cytokine induction of ovarian clear cell carcinomas (DOI: 10.1016/j.celrep.2016.09.003). 

# RNAseq data used is the FKPM xlsx file sourced from GEO. Due to Excel formatting issues (conversion of month-date similar 
# combinations into 'mm/dd/yyyy' format), some gene names had to be reabstracted from the surrounding gene names. File was
# then saved as a tab-delimited .txt file for loading into R

setwd("mydir")
ova <- read.table("ova_dat.txt", row.names=1, header=T)

# correlation plot

library(gplots)
ova.cor <- cor(ova)

layout(matrix(c(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 2, 2), 8, 2, byrow=TRUE))
par(oma=c(5, 7, 1, 1))
leg <- seq(min(ova.cor, na.rm=TRUE), max(ova.cor, na.rm=TRUE), length=10)
image(ova.cor, main="Correlation plot of Renal Epithelium-derived Normal and Tumor Samples", axes=F)
axis(1, at=seq(0, 1, length=ncol(ova.cor)), label=dimnames(ova.cor)[[2]], cex.axis=0.9, las=2)
axis(2, at=seq(0, 1, length=ncol(ova.cor)), label=dimnames(ova.cor)[[2]], cex.axis=0.9, las=2)

par(mar=rep(2, 4))
image(as.matrix(leg), axes=F)
tmp <- round(leg, 2)
axis(1,at=seq(0, 1, length=length(leg)), labels=tmp, cex.axis=1)

# avg corr plot
ova.average <- apply(ova.cor, 1, mean) 
par(oma=c(3, 0.1, 0.1, 0.1)) 
plot(c(1, length(ova.average)), range(ova.average), type="n", main="Average Correlation Plot", xlab="", ylab ="Average Correlation (r)", axes=F) 
points(ova.average, bg="Red", col=1, pch=21, cex=1.25) 
axis(1, at = c(1:length(ova.average)), labels = dimnames(ova)[[2]], las=2, cex.lab=0.4, 
cex.axis=0.6) 
axis(2) 
abline(v = seq(0.5, 62.5, 1)) 

# plot density plots of log ratios for each normalization for subject 4
plotM<-function(l) {
 plot(density(log10(ova[,l]+1)), ylim=c(0,3.5), xlim=c(-1,5), col=l)
}
par(mfrow=c(2,2))

for (i in 1:14) {
  plotM(i)
}

ova1.log <- log10(ova[,1]+1)
ova2.log <- log10(ova[,2]+1)
ova3.log <- log10(ova[,3]+1)
ova4.log <- log10(ova[,4]+1)
ova5.log <- log10(ova[,5
plot(density(ova1.log), main="Density Plot of Gene FKPMs per Sample", 
ylim=c(min(ova1.log),max(ova1.log)), xlim=c(-1, 5), col=1)
lines(density(ova2.log), col="blue")
lines(density(ova3.log), col="red") 
lines(density(ova4.log), col="green") 
       
#################################

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

