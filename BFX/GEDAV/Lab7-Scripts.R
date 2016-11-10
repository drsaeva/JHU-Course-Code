# read in sotiriou data and annotations files
sot <- read.table("D:/Data/Sotiriou.txt", row.names=1, header=T)
sot.ant <- read.table("D:/Data/Sotiriou_annotations.txt", row.names=1, header=T)

# sotiriou pca plot
colnames(sot.ant)
sot.ant[,1]
sot.pca <- prcomp(t(sot),cor=F)
plot(
range(sot.pca$x[,1]), range(sot.pca$x[,2]), type="n", xlab="KIU", ylab="OXF",
main="Sotiriou Data PCA plot\nKIU vs OXF")
points(sot.pca$x[,1][sot.ant$site=="KIU"], sot.pca$x[,2][sot.ant$site=="KIU"], bg="Red", pch=21)
points(sot.pca$x[,1][sot.ant$site=="OXF"], sot.pca$x[,2][sot.ant$site=="OXF"], bg="Blue", pch=21)
legend("topright", c("KIU","OXF"), col=c("Red", "Blue"), fill = c("Red", "Blue"))

# scree plot from sotiriou pca plot
sot.pca.var <- round(sot.pca$sdev^2 / sum(sot.pca$sdev^2)*100,2)
plot(c(1:length(sot.pca.var)),sot.pca.var,type="b",xlab="# components",ylab="% variance",pch=21,col=1,bg=3,cex=1.5)
title("Scree plot showing % variability explained by each eigenvalue\nKIU/OXF dataset")

# Kruskal’s non-metric MDS on samples
library(MASS);
library(multtest);
sot.dist <- dist(t(sot))
sot.mds <- isoMDS(sot.dist)
plot(sot.mds$points, type="n", main="MDS plot of Sotiriou data-stress=20%")
points(sot.mds$points[,1][sot.ant$site=="KIU"], sot.mds$points[,2][sot.ant$site=="KIU"], col="Red", pch=16, cex=1.5)
points(sot.mds$points[,1][sot.ant$site=="OXF"], sot.mds$points[,2][sot.ant$site=="OXF"], col="Blue", pch=16, cex=1.5)
legend("top", c("KIU","OXF"), col=c("Red", "Blue"), fill = c("Red", "Blue"))


# classical metric MDS on samples (no stress value provided)
sot.loc <- cmdscale(sot.dist)
plot(sot.loc, type="n", main="MDS plot of Sotiriou data")
points(sot.loc[,1][sot.ant$site=="KIU"], sot.loc[,2][sot.ant$site=="KIU"],col="Red",pch=16,cex=1.5)
points(sot.loc[,1][sot.ant$site=="OXF"], sot.loc[,2][sot.ant$site=="OXF"],col="Blue",pch=16,cex=1.5)
legend("topright", c("KIU","OXF"), col=c("Red", "Blue"), fill = c("Red", "Blue"))

# weighted graph laplacian
k.speClust2 <- function (X, qnt=NULL) {
	dist2full <- function(dis) {
		n <- attr(dis, "Size")
		full <- matrix(0, n, n)
		full[lower.tri(full)] <- dis
		full + t(full)
	}

	dat.dis <- dist(t(X),"euc")^2
	if(!is.null(qnt)) {eps <- as.numeric(quantile(dat.dis,qnt))}
	if(is.null(qnt))  {eps <- min(dat.dis[dat.dis != 0])}
	kernal         <- exp(-1 * dat.dis / (eps))
	K1             <- dist2full(kernal)
	diag(K1)       <- 0
	D              <- matrix(0, ncol = ncol(K1), nrow = ncol(K1))
	tmpe           <- apply(K1, 1, sum)
	tmpe[tmpe > 0] <- 1/sqrt(tmpe[tmpe > 0])
	tmpe[tmpe < 0] <- 0
	diag(D)        <- tmpe
	L              <- D%*% K1 %*% D
	X              <- svd(L)$u
	Y              <- X / sqrt(apply(X^2, 1, sum))
}

temp <- t(sot)
temp <- scale(temp,center=T,scale=T) 
phi <- k.speClust2(t(temp),qnt=NULL)
plot(range(phi[, 1]), range(phi[, 2]), xlab="phi1", ylab="phi2", 
main="Sotiriou Data\nWeighted Graph Laplacian Plot")
points(phi[,1][sot.ant$site=="KIU"], phi[, 2][sot.ant$site=="KIU"], col="Red", pch=16, cex=1.5)
points(phi[,1][sot.ant$site=="OXF"], phi[, 2][sot.ant$site=="OXF"], col="Blue", pch=16, cex=1.5)
legend("top", c("KIU", "OXF"), col = c("Red", "Blue"), fill = c("Red", "Blue"))
