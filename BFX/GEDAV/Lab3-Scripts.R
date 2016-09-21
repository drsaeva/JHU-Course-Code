# This script file analyses evaluating power and sample size in a 
#   gene expression dataset generated with cDNA microarrays by
#   Alizadeh and Eisen et al. at Stanford. File locations specified 
#   are local,and should be adjusted accordingly in order to perform 
#   equivalent analyses.
#
#   "Distinct types of diffuse large B-cell lymphoma identified by gene 
#      expression profiling"
#   Nature 403, 503-511 (3 February 2000) | doi:10.1038/35000501

# Written by David Saeva, but referencing code written by B. Higgs


# Read in data (eis) and class annotations (eisCla) file
eis <- read.table("E:/R_Data/eisen.txt", header=T, na.strings="NA", blank.lines.skip=F, row.names=1)
eisCla <- read.table("E:/R_Data/eisenClasses.txt", header=T)

# Subset dataframe with class annotations
eisCl.char <- as.character(eiCl[, 2])
eis.sub <- eis[, eisCl.char]

# Generate GC/ABC classes by merging individual samples associated with each class
# NA values are removed without replacement

# GC data
gc <- eisCl.char[1:19]
gc.2468 <- as.numeric(eis[2468, gc])
gc.2468 <- gc.2468[!is.na(gc.2468)]

# ABC data
abc <- eisCl.char[20:39]
abc.2648 <- as.numeric(eis[2468, abc])
abc.2468 <- abc.2468[!is.na(abc.2468)]

# Merge data into list, generate boxplot comparing two classes
ga2468.list <- list(gc.2468, abc.2468)
boxplot(ga2468.list, col=c("green","red"), main = "Gene 2468 from DLBCL cDNA 2-channel dataset", 
+axes=F, yLab="log2(ratio intensity)")
axis(2)
axis(1, at=c(1, 2), c("GC", "ABC"))

# Generate histograms for each class
par(mfrow=c(2, 1))
range <- range(c(abc.2468, gc.2468))
hist(gc.2468, n=20, col="green", border="black", main="", xlab="Distribution of GC sample", xlim=range)
title("Histogram of gene expression for gene 2468 in Germinal Center DLBCL")
hist(abc.2468, n=20, col="red", border="black", main="", xlab="Distribution of ABC samples", xlim=range)
title("Histogram of gene expression for gene 2468 in Activated B cell DLBCL")

# Calculate pooled variance, minimum sample size req to detect 1.5-fold difference at 80% power, 99% confidence
# Store group sample sizes
num.gc.2468 <- length(gc.2468)
num.abc.2468 <- length(abc.2468)

# Calc pooled variance
pvar <- (((num.gc.2468-1)*var(gc.2468)) + ((num.abc.2468-1)*var(abc.2468)))/(num.gc.2468+num.abc.2468-2)

# Sample size calculation, 1.5-fold difference between means
dif1.5 <- log2(1.5)/sqrt(pvar)
ss <- pwr.t.test(d=dif1.5, sig.level=.01, power=0.8, type="two.sample")

# Calculate minimum sample size using empirically-determined delta value
# delta
dif <- abs(mean(num.gc.2468)-mean(num.abc.2468))/sqrt(pvar)

# Sample size calc with empirically-determined delta
ss <- pwr.t.test(d=dif, sig.level=.01, power=0.8, type="two.sample")

# Generate histogram of standard deviations of genes in data set
# Calculate std devs
eis.sub.sd <- sapply(eis.sub, sd, na.rm=T)

# Hist plot of the distribution of the st. devs 
hist(eis.sub.sd, n=40, col="orange", border="red", main="", xlab="Standard Deviation (for data on the log2
+ scale)")
title("Histogram of Standard Deviations for ~13,000 genes")

# Calculate and plot a proportion of genes vs. sample size graph to get an idea of the number of genes 
# that have an adequate sample size for confidence=95%, effect size=3 (log2 transform for the function), 
# and power=80%.
all.size <- ssize(sd=eis.sub.sd, delta=log2(3), sig.level=0.05, power=0.8)
ssize.plot(all.size, lwd=2, col="blue", xlim=c(1,20))
xmax <- par("usr")[2]-1;
ymin <- par("usr")[3] + 0.05

legend(x=xmax, y=ymin, legend= strsplit( paste("fold change=",3,",", "alpha=", 0.05, ",",
"power=",power,",", "# genes=", length(eis.sub.sd), sep=''), "," )[[1]], xjust=1, yjust=0, cex=1.0)
title("Sample Size to Detect 3-Fold Change")
