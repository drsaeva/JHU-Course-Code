# read in eqtl files
u <- read.table("D:/Data/eqtl/bp_ct_u133A.txt", header=T)
p <- read.table("D:/Data/eqtl/bp_ct_83_subjects.ped", sep=" ")
m <- read.table("D:/Data/eqtl/bp_ct_83_subjects.map")

#DICER1 gene and SNPs cis to DICER1
probe <- "216280_s_at"	
snps <- c("SNP_A-2240938","SNP_A-2249234","SNP_A-2120212","SNP_A-1864785","SNP_A-1944204",
"SNP_A-2101022","SNP_A-2192181","SNP_A-4296300","SNP_A-1961028","SNP_A-2215249","SNP_A-2172180","SNP_A-1945117","SNP_A-1941491")

# get row numbers for snps in map file
m.snps <- which(m[,2] %in% snps)
p2 <- m.snps*2+6
p1 <- p2-1

# recode snps in ped file from 2-digit to 1-digit codes
recode <- function(x) {
        x <- as.numeric(x)
        x1 <- seq(1,length(x),by=2)
        x2 <- seq(2,length(x),by=2)
        geno <- paste(x[x1],x[x2],sep="")
        geno[geno=="00"] <- NA
        geno[geno=="11"] <- 0
        geno[geno=="12" | geno=="21"] <- 1
        geno[geno=="22"] <- 2
        geno
}

t(p)[p1,1:10]

# subset pedigree matrix, apply recode
p.sub <- p[,p1]
p.re <- apply(p.sub, 1, recode)

# p.re appears to be somewhat corrupted while p.sub appears to have 1-digit codes already
# using p.sub for later steps

#linear model function to correlation SNPs w/ gene expression
lin.mod <- function(x,y) {
		x <- as.numeric(x)
		dat <- data.frame(y,x)
		out <- lm(y~x,data=dat)
		outx <- summary(out)
		return(outx$coefficients["x",4])
}

#calculate linear model between gene and SNPs
pvs <- apply(p.sub,2,lin.mod,y=t(u[probe,]))
pvs

# extract all remaining SNPS on chr 14, recalculate p-vals with linear model
all.14.snps <- m[m[,1] %in% 14],] 
trans.snps <- all.14.snps[all.14.snps[,2] != snps, ]
trans.rows <- as.numeric(rownames(trans.snps))
length(trans.rows)
trans.rows[1:10]

p.t2 <- trans.rows*2+6
p.t1 <- p.t2-1

p.sub.t <- p[,p.t1]
pvs.t <- apply(p.sub.t,2,lin.mod,y=t(u[probe,]))
pvs.t.sorted <- sort(pvs.t)
pvs.t.sorted[1:10]

# remove 'V' from p-value names
names(pvs.t) <- gsub("V", "", names(pvs.t))
names(pvs) <- gsub("V", "", names(pvs))

# plot distances for cis- and trans-acting SNPs
par(mfrow=c(2,1))
plot(m[p1,4], -log10(pvs), xlab="Physical Distance", 
  ylab="-log10(p-value)", main="Cis-acting SNP log-adjusted p-values vs physical distance")
plot(m[trans.rows,4],-log10(pvs.t), xlab="Physical Distance", 
  ylab="-log10(p-value)", main="Trans-acting SNP log-adjusted p-values vs physical distance")
