# read in rat ketogenic data
rat.kd <- read.table("D:/Data/rat_KD.txt", header=T, row.names=1)

# student's t-test to determine changing genes between classes
classes <- as.character(names(rat.kd))
rat.kd.log <- log2(rat.kd)[,classes]
ctrl <- classes[1:6]
keto <- classes[7:11]

t.test.all.genes <- function(x, s1, s2){
	x1 <- x[s1]
	x2 <- x[s2]
	x1 <- as.numeric(x1)
	x2 <- as.numeric(x2)
	t.out <- t.test(x1, x2, alternative="two.sided", var.equal=T)
	out <- as.numeric(t.out$p.value)
	return(out)
}

p.values <- apply(rat.kd.log, 1, t.test.all.genes, s1=ctrl, s2=keto)

# histogram of p-values reporting probesets of significance
length(p.values[p.values < 0.05])
length(p.values[p.values < 0.01])

# bonferroni correction p-values
sig.lvl <- 0.05/length(p.values)
length(p.values)
length(p.values[p.values < sig.lvl])

hist(p.values, col="lightblue",	xlab="p-values", main="P-value change between \nControl- and 
Ketogenic-fed rat \nhippocampi gene expression", cex.main=0.9)
abline(v=0.05, col=2, lwd=2)

# fold change between class means
ctrl.mean <- apply(rat.kd.log[, ctrl], 1, mean,na.rm=T)
keto.mean <- apply(rat.kd.log[, keto], 1, mean,na.rm=T)
fold <- ctrl.mean - keto.mean

# linear scale max/min fold changes
2^max(fold)
2^min(fold)

# probesets less than bonferroni threshold & |fold|>2

sig.probes <- names(p.values[p.values<sig.lvl & abs(2^fold)>2])
sig.probes

# transform p-values, make volcano plot, draw two lines at 2 and -2
p.values.trans <- (-1*log10(p.values))

plot(range(p.values.trans), range(fold), type="n", xlab="-1 * log10 (P-Value)", 
ylab="Fold Change", main="Volcano plot of fold changes between control and 
ketogenic-fed rat hippocampi gene expression")

points(p.values.trans, fold, col=1, bg=1, pch=21)

points(p.values.trans[(p.values.trans > -log10(0.05) & fold > log2(2))], fold[(
p.values.trans > -log10(0.05) & fold > log2(2))], col=1, bg=2, pch=21)

points(p.values.trans[(p.values.trans > -log10(0.05) & fold < -log2(2))], fold[(
p.values.trans > -log10(0.05) & fold < -log2(2))], col=1, bg=3, pch=21)

abline(v= -log10(0.05))
abline(h= -log2(2))
abline(h=log2(2))
