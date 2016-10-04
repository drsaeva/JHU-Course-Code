# correlation plot

library(gplots)
rcc.cor <- cor(rcc)

layout(matrix(c(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 2, 2), 8, 2, byrow=TRUE))
par(oma=c(5, 7, 1, 1))
leg <- seq(min(rcc.cor, na.rm=TRUE), max(rcc.cor, na.rm=TRUE), length=10)
image(rcc.cor, main="Correlation plot of Renal Epithelium-derived Normal and Tumor Samples", axes=F)
axis(1, at=seq(0, 1, length=ncol(rcc.cor)), label=dimnames(rcc.cor)[[2]], cex.axis=0.9, las=2)
axis(2, at=seq(0, 1, length=ncol(rcc.cor)), label=dimnames(rcc.cor)[[2]], cex.axis=0.9, las=2)

par(mar=rep(2, 4))
image(as.matrix(leg), axes=F)
tmp <- round(leg, 2)
axis(1,at=seq(0, 1, length=length(leg)), labels=tmp, cex.axis=1)

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

# avg corr plot
rcc.average <- apply(rcc.cor, 1, mean) 
par(oma=c(3, 0.1, 0.1, 0.1)) 
plot(c(1, length(rcc.average)), range(rcc.average), type="n", main="Average Correlation Plot of Tumor v. Normal Renal Empithelial Samples", xlab="", ylab ="Average Correlation (r)", axes=F) 
points(rcc.average, bg="Red", col=1, pch=21, cex=1.25) 
axis(1, at = c(1:length(rcc.average)), labels = dimnames(rcc)[[2]], las=2, cex.lab=0.4, 
cex.axis=0.6) 
axis(2) 
abline(v = seq(0.5, 62.5, 1)) 

# remove samples GSM146798, GSM146799
rcc <- rcc[,-c(10, 19)]
dim(rcc) 
dimnames(rcc)[[2]] 

# assess KNG1, AQP2 expression patterns

KNG1a <- rcc["206054_at",] 
KNG1b <- rcc["217512_at",]
both.KNG1 <- rbind(KNG1a,KNG1b)

AQP2 <- rcc["206672_at",]
all3 <- rbind(KNG1a, KNG1b, AQP2) 

par(mfrow=c(3, 1)) 

plot(c(1, ncol(rcc)), range(both.KNG1), type='n', main="Profile plot of KNG1 probeset '206054_at'", xlab ="Samples", ylab="Expression", axes=F) 
axis(side=1, at=c(1:20), labels=dimnames(all3)[[2]], cex.axis=0.4, las=2) 
axis(side=2) 
for(i in 1:length(KNG1a)) { 
	KNG1a.nona <- as.numeric(KNG1a[i,]) 
	lines(c(1:ncol(KNG1a)), KNG1a.nona, col=i, lwd=2) 
} 
 
plot(c(1, ncol(rcc)), range(both.KNG1), type='n', main="Profile plot of KNG1 probeset '206054_at'", xlab ="Samples", ylab="Expression", axes=F) 
axis(side=1, at=c(1:20), labels=dimnames(all3)[[2]], cex.axis=0.4, las=2) 
axis(side=2) 
for(i in 1:length(KNG1a)) { 
	KNG1b.nona <- as.numeric(KNG1b[i,]) 
	lines(c(1:ncol(KNG1b)), KNG1b.nona, col=i, lwd=2) 
} 
 
plot(c(1, ncol(rcc)), range(AQP2), type='n', main="Profile plot of AQP2 probeset '206672_at'", xlab ="Samples", ylab ="Expression", axes=F) 
axis(side=1, at=c(1:20), labels=dimnames(all3)[[2]], cex.axis=0.4, las=2) 
axis(side=2) 
for(i in 1:length(AQP2)) { 
	AQP2.nona <- as.numeric(AQP2[i,]) 
	lines(c(1:ncol(AQP2)), AQP2.nona, col=i, lwd=2) 
} 

# assessing imputation

rcc.mat <- as.matrix(rcc) 
KNG1a.original  <- rcc.mat["206054_at", "GSM146784 Normal"] 
rcc.mat["206054_at", "GSM146784 Normal"] <- NA 
rcc.mat["206054_at", "GSM146784 Normal"]


rcc.knn <- impute.knn(rcc.mat, 6) 
rcc.knn$data["206054_at", "GSM146784 Normal"] 
rcc.knn <- rcc.knn$data 
imputed <- rcc.knn["206054_at", "GSM146784 Normal"] 

rel.err <- abs(imputed-KNG1a.original)/KNG1a.original
rel.err

# svd_impute testing
# rcc.mat still contains NA values for GSM145784 Normal
rcc.pca <- pca(rcc.mat, method = "svdImpute", nPcs = 9) 
rcc.pca.cobs <- completeObs(rcc.pca) 
rcc.pca.cobs["206054_at", "GSM146784_Normal"] 

# gene profile plot
KNG1a.original <- rcc["206054_at",] 
KNG1a.pca <- rcc.pca.cobs["206054_at",] 
KNG1a.knn <- rcc.knn["206054_at",] 
KNG1a.all3 <- rbind(KNG1a.original, KNG1a.pca, KNG1a.knn) 
 
plot(c(1, ncol(rcc)), range(KNG1a.all3), type ="n", main ="Profile plot comparing imputed and actual values of the 'GSM146784 Normal' array for KNG1 probeset '206054_at'", xlab="Samples", ylab="Expression", axes=F) 
axis(side=1, at=1:length(rcc), labels = dimnames(KNG1a.all3)[[2]], cex.axis=0.6, las=2) 
axis(side=2) 
 
for(i in 1:length(KNG1a.all3)) { 
	y <- as.numeric(KNG1a.all3[i,]) 
	lines(c(1:ncol(KNG1a.all3)), y, col=i, lwd=2) 
}
