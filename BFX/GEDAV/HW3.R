# get/load libs, golub

biocLite("multtest")
biocLite("annotate")
library(Biobase)
library(annotate)
library(multtest)

setwd("E:/R_Data")
data(golub)
gol <- as.data.frame(golub)
dimnames(gol)[[2]] <- golub.cl


# wilcox test

wilcox.test.all.genes <- function(x,s1,s2) {
  x1 <- x[s1]
  x2 <- x[s2]
  x1 <- as.numeric(x1)
  x2 <- as.numeric(x2)
  wil.out <- wilcox.test(x1, x2, exact=F, alternative=“two.sided”, correct=T, var.equal=T)
  out <- as.numeric(wil.out$statistic)
  return(out)
}

original.wmw.run <- apply(gol, 1, wilcox.test.all.genes, 
  s1=colnames(gol)==0, s2=colnames(gol)==1)

wil.500 <- list(1:500)
	
for (i in 1:500){
	colnames(gol) <- sample(colnames(gol))
	test.stat <- apply(gol, 1, wilcox.test.all.genes, 
    s1=colnames(gol)==0, s2=colnames(gol)==1)
	wil.500[i] <- max(test.stat)
}

wil.500 <- as.character(t(wil.500))

# 95% - top 25 in list
top.5.pct <- sort(wil.500)[25]
orig.top.5.pct <- original.wmw.run[original.wmw.run > top.5.pct]

# ebayes
gol.0 <- gol[colnames(gol)==0]
gol.1 <- gol[colnames(gol)==1]
gol.design <- cbind(a = 1, b = c(rep(1, length(gol.1)), rep(0, length(gol.0))))
gol.ebayes <- eBayes(lmFit(gol, gol.design))
topTable(gol.ebayes)
attributes(gol.ebayes)
gol.p.values <- gol.ebayes$p.value[,2]
gol.p.values

# sort pvals
n <- length(orig.top.5.pct)
gol.p.values.sorted <- sort(gol.p.values)
gol.p.values.sorted <- gol.p.values.sorted[1:n]
gol.x <- intersect(names(gol.p.values.sorted), names(orig.top.5.pct))
length(gol.x)
gol.x

# t-test vs bayes
t.test.all.genes <- function(x, d1, d2){
	x1 <- x[d1]
	x2 <- x[d2]
	x1 <- as.numeric(x1)
	x2 <- as.numeric(x2)
	t.out <- t.test(x1, x2, alternative="two.sided", var.equal=T)
	out <- as.numeric(t.out$p.value)
	return(out)
}

gol.t <- apply(gol, 1, t.test.all.genes, d1=colnames(gol)==0, 
	       d2=colnames(gol)==1)
gol.sig.p <- gol.t[gol.t < 0.01]
gol.sig.p <- as.matrix(gol.sig.p)

gol.b <- as.matrix(gol.ebayes$p.value)
gol.b <- as.matrix(gol.b[, -2])

gol.t.b <- as.matrix(merge(gol.sig.p, gol.b, by="row.names", all=F)) 
rownames(gol.t.b) <- gol.t.b[, 1]
gol.t.b <- gol.t.b[, -1]
colnames(gol.t.b) <- c("Student's t-test", "Empirical Bayes")
class(gol.t.b) <- "numeric"

plot(c(1, nrow(gol.t.b)), range(gol.t.b), type = "n", xaxt ="n", 
     ylab="p-value", xlab="Genes")
points(1:nrow(gol.t.b), col="Red", gol.t.b[, 2])
points(1:nrow(gol.t.b), col="Black", gol.t.b[, 1])
title(main="Plot of Student's t-test- vs empirical Bayes-calculated p-values")
legend(350, 1 , colnames(gol.t.b), col=c("Red", "Black"), pch=12, cex=0.9)
