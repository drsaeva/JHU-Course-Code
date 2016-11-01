# get/load libs, golub

biocLite("multtest")
biocLite("annotate")
library(Biobase)
library(annotate)
library(multtest)

setwd("E:/R_Data")
data(golub)
gol <- as.data.frame(golub)
dimnames(dat)[[2]] <- golub.cl


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
