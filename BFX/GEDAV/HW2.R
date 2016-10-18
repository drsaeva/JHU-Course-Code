# load marray, genepix files, extract fore/background median vals from Cy3/Cy5 channels
library(marray)
dir <- "D:/Data/GSE12050"
gp <- read.GenePix(path=dir, name.Gf="F532 Median", name.Gb="B532 Median", 
name.Rf="F635 Median", name.Rb="B635 Median", name.W="Flags") 

# mva plots for each subject
one <- gp[, 1]
two <- gp[, 2]
three <- gp[, 3]
four <- gp[, 4] 

one.med <- maNorm(one, norm=c("median"))
two.med <- maNorm(two, norm=c("median"))
three.med <- maNorm(three, norm=c("median"))
four.med <- maNorm(four, norm=c("median"))

one.loe <- maNorm(one, norm=c("loess"))
two.loe <- maNorm(two, norm=c("loess"))
three.loe <- maNorm(three, norm=c("loess"))
four.loe <- maNorm(four, norm=c("loess"))

one.ptl <- maNorm(one, norm=c("printTipLoess"))
two.ptl <- maNorm(two, norm=c("printTipLoess"))
three.ptl <- maNorm(three, norm=c("printTipLoess"))
four.ptl <- maNorm(four, norm=c("printTipLoess"))

par(mfrow = c(4, 1)) 
maPlot(one, main="Subject 1: Non-normalized", 
       lines.func=NULL, legend.func=NULL) 
maPlot(one.med, main="Subject 1: Global Median-normalized", 
       lines.func=NULL, legend.func=NULL) 
maPlot(one.loe, main="Subject 1: Loess-normalized", 
       lines.func=NULL, legend.func=NULL) 
maPlot(one.ptl, main="Subject 1: Print-tip-group Loess-normalized", 
       lines.func=NULL, legend.func=NULL) 

par(mfrow = c(4, 1)) 
maPlot(three, main="Subject 3: Non-normalized", 
       lines.func=NULL, legend.func=NULL) 
maPlot(three.med, main="Subject 3: Global Median-normalized", 
       lines.func=NULL, legend.func=NULL) 
maPlot(three.loe, main="Subject 3: Loess-normalized", 
       lines.func=NULL, legend.func=NULL) 
maPlot(three.ptl, main="Subject 3: Print-tip-group Loess-normalized", 
       lines.func=NULL, legend.func=NULL) 

par(mfrow = c(4, 1)) 
maPlot(two, main="Subject 2: Non-normalized", 
       lines.func=NULL, legend.func=NULL) 
maPlot(two.med, main="Subject 2: Global Median-normalized", 
       lines.func=NULL, legend.func=NULL) 
maPlot(two.loe, main="Subject 2: Loess-normalized", 
       lines.func=NULL, legend.func=NULL) 
maPlot(two.ptl, main="Subject 2: Print-tip-group Loess-normalized", 
       lines.func=NULL, legend.func=NULL) 

par(mfrow = c(4, 1)) 
maPlot(four, main="Subject 4: Non-normalized", 
       lines.func=NULL, legend.func=NULL) 
maPlot(four.med, main="Subject 4: Global Median-normalized", 
       lines.func=NULL, legend.func=NULL) 
maPlot(four.loe, main="Subject 4: Loess-normalized", 
       lines.func=NULL, legend.func=NULL) 
maPlot(four.ptl, main="Subject 4: Print-tip-group Loess-normalized", 
       lines.func=NULL, legend.func=NULL) 

# plot density plots of log ratios for each normalization for subject 4

plot(density(na.omit(maM(four))), main="Subject 4 Density Plot of 
       Expression Log Ratios (pre- and post-normalized)", ylim=c(0, 0.9), 
       xlim=c(-6.6, 11.7), col="black")
lines(density(na.omit(maM(four.med))), col="blue") 
lines(density(na.omit(maM(four.loe))), col="orange") 
lines(density(na.omit(maM(four.ptl))), col="green") 

leg.txt <- c("Non-normalized", "Global Median-normalized", 
       "Loess-normalized", "Print-tip-group 
       Loess-normalized")
legend(1, 0.85, legend=leg.txt, lty=c(1, 1), lwd=c(2.5, 2.5),col=c("black", 
       "blue", "orange", "green")) 

# extract cy5 fg/bg, subtract, log2, global med norm

for (i in 1:4) {
       name <- paste("log2", i, sep=".")
       fgrd <- maRf(gp[,i])
       bgrd <- maRb(gp[,i])
       fgrd.bgrd <- fgrd-bgrd
       for (i in 1:nrow(fgrd.bgrd)) {
              if (fgrd.bgrd[i] < 0) {
                     fgrd.bgrd[i] = 0
              }
       }
       assign(name, log2(fgrd.bgrd))
}       

log2.non.norm <- cbind(log2.1, log2.2, log2.3, log2.4)
log2.med <- apply(log2.non.norm, 2, median, na.rm=T)
log2.norm  <- sweep(log2.non.norm, 2, log2.med)

colnames(log2.norm) <- c("Sample 1", "Sample 2", "Sample 3", "Sample 4")

median(log2.norm[, 1], na.rm=T)
median(log2.norm[, 2], na.rm=T)
median(log2.norm[, 3], na.rm=T)
median(log2.norm[, 4], na.rm=T)

# Calc spearman's rank corr between loess data from (2) and data from (5)
mam.one <- maM(one.loe)
mam.two <- maM(two.loe)
mam.three <- maM(three.loe)
mam.four <- maM(four.loe)

samples <- c("Sample 1", "Sample 2", "Sample 3", "Sample 4")

all.loe <- cbind(mam.one, mam.two, mam.three, mam.four)
colnames(all.loe) <- make.names(samples, unique=TRUE)
all.loe.cor <- cor(all.loe, use="complete.obs", method="spearman") 
colnames(all.loe.cor) <- make.names(samples, unique=TRUE)
rownames(all.loe.cor) <- make.names(samples, unique=TRUE)
all.loe.cor

norm.cor <- cor(log2.norm, use="complete.obs", method="spearman") 
rownames(norm.cor) <- make.names(samples, unique=TRUE)
colnames(norm.cor) <- make.names(samples, unique=TRUE)
norm.cor

pairs(log2.norm, main="Scatterplot Matrix for\nGlobal Median Normalization")
pairs(all.loess, main="Scatterplot Matrix for\nLoess Normalization")

# compare normalizations to quantile normalizations
one.dif <- maRf(one) - maRb(one)
two.dif <- maRf(two) - maRb(two)
three.dif <- maRf(three) - maRb(three)
four.dif <- maRf(four) - maRb(four)

all.dif <- cbind(one.dif, two.dif, three.dif, four.dif)

colnames(all.dif) <- c("Sample 1", "Sample 2", "Sample 3", "Sample 4")
all.dif.sorted <- apply(all.dif, 2, sort) 
all.rowmeans <- rowMeans(all.dif.sorted, na.rm=FALSE)

one.order <- order(one.dif)  
two.order <- order(two.dif)
three.order <- order(three.dif)
four.order <- order(four.dif)

one.norm <- rep(NA, nrow(all.dif))
two.norm <- rep(NA, nrow(all.dif))
three.norm <- rep(NA, nrow(all.dif))
four.norm <- rep(NA, nrow(all.dif))

one.norm[one.order] <- all.rowmeans
two.norm[two.order] <- all.rowmeans
three.norm[three.order] <- all.rowmeans
four.norm[four.order] <- all.rowmeans

quantile <- cbind(one.norm, two.norm, three.norm, four.norm)
head(all.dif)
head(quantile)
 
# verify quantile results in histogram

par(mfrow = c(4, 1))
hist(log2(quantile[ , 1]))
hist(log2(quantile[ , 2])) 
hist(log2(quantile[ , 3]))
hist(log2(quantile[ , 4]))

# spearmans on quantile results
quantile.log2 <- log2(quantile)	
quantile.cor <- cor(quantile.log2, use="complete.obs", method="spearman") 
quantile.cor

pairs(quantile.log2, main="Scatterplot Matrix for\nQuantile Normalization")

# qRT-PCR file formatting, fold change calc script
f.parse <- function(path=pa,file=fi,out=out.fi) {
	d <- read.table(paste(path,file,sep=""),skip=11,sep=",",header=T)
	u <- as.character(unique(d$Name))
	u <- u[u!=""]; u <- u[!is.na(u)];
	ref <- unique(as.character(d$Name[d$Type=="Reference"]))
	u <- unique(c(ref,u))
	hg <- c("B-ACTIN","GAPDH","18S")
	hg <- toupper(hg)
	p <- unique(toupper(as.character(d$Name.1)))
	p <- sort(setdiff(p,c("",hg)))

	mat <- matrix(0,nrow=length(u),ncol=length(p))
	dimnames(mat) <- list(u,p)
	for (i in 1:length(u)) {
		print(paste(i,": ",u[i],sep=""))
		tmp <- d[d$Name %in% u[i],c(1:3,6,9)]
		g <- toupper(unique(as.character(tmp$Name.1)))
		g <- sort(setdiff(g,c("",hg)))
		
for (j in 1:length(g)) {
			v <- tmp[toupper(as.character(tmp$Name.1)) %in% g[j],5]
			v <- v[v!=999]
			v <- v[((v/mean(v))<1.5) & ((v/mean(v))>0.67)]	#gene j vector

			hv3 <- NULL
			for (k in 1:length(hg)) {	#housekeeping gene vector (each filtered by reps)
				hv <- tmp[toupper(as.character(tmp$Name.1)) %in% hg[k],5]
				hv <- hv[hv!=999]
				hv3 <- c(hv3,hv[((hv/mean(hv))<1.5) & ((hv/mean(hv))>0.67)]) 	
			}

			
			sv <- mean(as.numeric(v)) - mean(as.numeric(hv3))	#scaled value for gene j
			
			if(i==1) { #reference sample only
				mat[u[i],g[j]] <- sv
				next
			}
			
			mat[u[i],g[j]] <- sv - mat[u[1],g[j]]
		}
	}

	mat[1,][!is.na(mat[1,])] <- 0
	fc <- 2^(-1 * mat)
	write.table(t(c("Subject",dimnames(mat)[[2]])),paste(path,out,sep=""),quote=F,sep="\t",col.names=F,row.names=F)
	write.table(round(fc,3),paste(path,out,sep=""),quote=F,sep="\t",append=T,col.names=F)
}



# run function
pa <- "D:/Data/"
fi <- "qRT-PCR.CSV"
out.fi <- "fold_chg_matrix.txt"

f.parse(pa,fi,out.fi)

# read output matrix in, 
qrtpcr.out <- read.table("D:/Data/fold_chg_matrix.txt", header=T, row.names=1, fill=T)
out.tran <- t(qrtpcr.out)
out.tran[is.na(out.tran)] <-0 
out.log2 <- log2(out.tran)
out.cor <- cor(out.log2, use="complete.obs", method="spearman")
out.cor

plot(out.tran[,7], out.tran[,8])
