## Plink code
# filtering by <10% missing rate per snp (geno)
# maf > 10% (maf)
# <30% missing rate per individual (mind)
# hwe at signficance <0.001 (hwe)

plink --map subjects_153.map --ped subjects_153.ped --geno 0.1 --maf 0.1 -hwe 0.001 --mind 0.3 --recode --out m_p_1

# genome file, MDS
plink --ped subjects_153.ped --map subjects_153.map --genome
plink --ped subjects_153.ped --map subjects_153.map --read-genome plink.genome --cluster --mds-plot 2

## R code to plot MDS

mds <- read.table("D:/Data/hw2/plink.mds", header=T)
cli <- read.table("D:/Data/hw2/clinical_table.txt", header=T, sep="\t")

classes <- data.frame(3, 1:153, 4)
colnames(classes) <- c("Profile", "Sex", "Suicide_Status")
row.names(classes) <- cli[,1]
for (i in 1:nrow(cli)) { 
  # profile
  if (cli[i, 7] == "Unaffected control") {
    classes$Profile[i] = 1
  }
  if (cli[i, 7] == "Schiz.") {
    classes$Profile[i] = 2
  }
  if (cli[i, 7] == "BP") {
    classes$Profile[i] = 3
  }
  if (cli[i, 7] == "Dep.") {
    classes$Profile[i] = 4
  }
  
  # sex
  if (cli[i, 5] == "M") {
    classes$Sex[i] = 1
  }
  if (cli[i, 5] == "F") {
    classes$Sex[i] = 2
  }
  
  # suicide_status
  if(cli[i,13] == "Yes") {
    classes$Suicide_Status[i] = 1
  }
  if(cli[i,13] == "No") {
    classes$Suicide_Status[i] = 2
  }
}

plot(mds[,4:5], col=classes$Profile, pch=16, xlab="p1",
  ylab="p2",main="Two eigenvectors from MDS of 153 GWAS subjects colored by disease profile")
legend("center", pch=16, col=c(1:4), c("Unaffected control","Schiz.","BP","Dep."))

par(mfrow=c(1,2))
plot(mds[,4:5], col=classes$Sex, pch=16, xlab="p1",
  ylab="p2",main="Two eigenvectors from MDS of 153 GWAS\n subjects colored by sex")
legend("center", pch=16, col=c(1:2), c("M","F"))

plot(mds[,4:5], col=classes$Suicide_Status, pch=16, xlab="p1",
  ylab="p2",main="Two eigenvectors from MDS of 153 GWAS\n subjects colored by suicide status")
legend("center", pch=16, col=c(1:2), c("Y","N"))

# identify Lifetime_Drug_Use variables, loop into classes matrix
unique(cli[,19])
[1] Moderate drug use in present Social                       Heavy drug use in present   
[4] Little or none               Moderate drug use in past    Heavy drug use in past      
[7] Unknown 

Lifetime_Drug_Use <- c()
for (i in 1:nrow(cli)) { 
  if (cli[i, 19] == "Unknown") {
    Lifetime_Drug_Use[i] = 1
  }
  if (cli[i, 19] == "Social") {
    Lifetime_Drug_Use[i] = 2
  }
  if (cli[i, 19] == "Little or none") {
    Lifetime_Drug_Use[i] = 3
  }
  if (cli[i, 19] == "Moderate drug use in present") {
    Lifetime_Drug_Use[i] = 4
  }
  if (cli[i, 19] == "Moderate drug use in past") {
    Lifetime_Drug_Use[i] = 5
  }
  if (cli[i, 19] == "Heavy drug use in present") {
    Lifetime_Drug_Use[i] = 6
  }
  if (cli[i, 19] == "Heavy drug use in past") {
    Lifetime_Drug_Use[i] = 7
  }
}
classes <- cbind(classes, Lifetime_Drug_Use)

plot(mds[,4:5], col=classes$Lifetime_Drug_Use, pch=16, xlab="p1",
  ylab="p2",main="Two eigenvectors from MDS of 153 GWAS\n subjects colored by drug use")
legend("center", pch=16, col=c(1:7), c("Unk","Soc", "L/N", "M/Pr", "M/Pa", "H/Pr", "H/Pa"))

Psychotic_Feature <- c()
for (i in 1:nrow(cli)) { 
  if (cli[i, 14] == "Unknown") {
    Psychotic_Feature[i] = 1
  }
  if (cli[i, 14] == "Yes") {
    Psychotic_Feature[i] = 2
  }
  if (cli[i, 14] == "No") {
    Psychotic_Feature[i] = 3
  }
}
classes <- cbind(classes, Psychotic_Feature)
plot(mds[,4:5], col=classes$Psychotic_Feature, pch=16, xlab="p1",
  ylab="p2",main="Two eigenvectors from MDS of 153 GWAS\n subjects colored by psychotic feature")
legend("center", pch=16, col=c(1:7), c("Unk","Y", "N"))


plot(mds[,4:5], col=cli[,4]/5, pch=16, xlab="p1",
  ylab="p2",main="Two eigenvectors from MDS of 153 GWAS subjects")

range10 <- function(x){
  (10*(x-min(x))/(max(x)-min(x)))
}

Brain_PH <- range10(cli[,11]) 
classes <- cbind(classes, Brain_PH)
classes$Brain_PH <- round(classes$Brain_PH)
for (i in 1:nrow(classes)) {
  classes$Brain_PH[i] <- classes$Brain_PH[i]+1
}
rbPal <- colorRampPalette(c('red','blue'))
classes$Brain_PH <- rbPal(11)[classes$Brain_PH]
plot(mds[,4:5], col=classes$Brain_PH, pch=16, xlab="p1",
  ylab="p2",main="Two eigenvectors from MDS of 153 GWAS\n subjects colored by brain pH")
legend("center", pch=16, col=c('red', 'blue'), c("Acidic","Neutral"))


classes$Brain_PH_Simple <- cli[,11]
for (i in 1:nrow(classes)) {
  if (classes$Brain_PH_Simple[i] >= 6.395) {
    classes$Brain_PH_Simple[i] <- 'blue'
  } else {
    classes$Brain_PH_Simple[i] <- 'red'
  }
}
plot(mds[,4:5], col=classes$Brain_PH_Simple, pch=16, xlab="p1",
  ylab="p2",main="Two eigenvectors from MDS of 153 GWAS\n subjects colored by simplified brain pH")
legend("center", pch=16, col=c('red', 'blue'), c("Acidic","Neutral"))

# create keep file from list of subjects either BP or control and generate phenotype file
keep.bp <- matrix(,,2)
for (i in 1:nrow(cli)) {
  if (cli[i,7] == "BP" || cli[i,7] == "Unaffected control") {
    keep.bp <- rbind(keep.bp, c(as.character(cli[i,1]), 1))
  }
}
keep.bp <- keep.bp[2:nrow(keep.bp),]
    
affected.bp <- matrix(,,2)
for (i in 1:nrow(cli)) {
  if (cli[i,7] == "BP") {
    affected.bp <- rbind(affected.bp, c(as.character(cli[i,1]),2))
  } else {
    affected.bp <- rbind(affected.bp, c(as.character(cli[i,1]),1))
  }
}
affected.bp <- affected.bp[2:nrow(affected.bp),]
pheno.bp <- cbind(affected.bp[,1], c(rep(1,nrow(affected.bp))), affected.bp[,2])
colnames(pheno.bp) <- c("FID", "IID", "pheno")
pheno.bp <- as.data.frame(pheno.bp)

# generate covariate file for plink usage with eigenvector 1, sex, and left brain status
cov <- matrix(,,5)
for (i in 1:nrow(cli)) {
  cov <- rbind(cov, c(as.character(cli$Database_ID[i]), 1, mds[i,4], as.character(cli[i,5]), 
      as.character(cli[i,12])))
}
cov <- cov[2:nrow(cov),]
colnames(cov) <- c("Family_ID", "Individual_ID", "covariate_1", "covariate_2", "covariate_3")
for (i in 1:nrow(cov)) {
  if (cov[i,4] == "M") {
    cov [i,4] <- 0
  }
  if (cov[i,4] == "F") {
    cov [i,4] <- 1
  }
  if (cov[i,5] == "Fixed") {
    cov [i,5] <- 0
  }
  if (cov[i,5] == "Frozen") {
    cov [i,5] <- 1
  }
}
cov <- as.data.frame(cov)

write.table(cov, file="D:/Data/hw2/mycov.txt", sep="\t", col.names=T, row.names=F, quote=F)
write.table(pheno.bp, file="D:/Data/hw2/pheno_bp.txt", sep="\t", col.names=T, row.names=F, quote=F)
write.table(keep.bp, file="D:/Data/hw2/keep_bp.txt", sep="\t", col.names=T, row.names=F, quote=F)

## Plink code
# linear regression using files from above and map/ped files
plink --ped subjects_153.ped --map subjects_153.map --linear --covar mycov.txt --keep keep_bp.txt --pheno pheno_bp.txt --all-pheno

## R code
# read in assoc file, identify SNPs with p < 0.01 and most prevalent chr among top 100 SNPs

snp.lin <- read.table("D:/Data/hw2/plink.pheno.assoc.logistic", header=T)
snp.lin.0_01 <- snp.lin[snp.lin[,9] < .01,]
nrow(snp.lin)
nrow(snp.lin.0_01)
snp.lin.sorted <- snp.lin.0_01[order(snp.lin.0_01$P),]
table(snp.lin.sorted$CHR[1:100])

# create keep file from list of subjects either schizophrenic or control and generate phenotype file
keep.sc <- matrix(,,2)
for (i in 1:nrow(cli)) {
  if (cli[i,7] == "Schiz." || cli[i,7] == "Unaffected control") {
    keep.sc <- rbind(keep.sc, c(as.character(cli[i,1]), 1))
  }
}
keep.sc <- keep.sc[2:nrow(keep.sc),]
    
affected.sc <- matrix(,,2)
for (i in 1:nrow(cli)) {
  if (cli[i,7] == "Schiz.") {
    affected.sc <- rbind(affected.sc, c(as.character(cli[i,1]),2))
  } else {
    affected.sc <- rbind(affected.sc, c(as.character(cli[i,1]),1))
  }
}
affected.sc <- affected.sc[2:nrow(affected.sc),]
pheno.sc <- cbind(affected.sc[,1], c(rep(1,nrow(affected.sc))), affected.sc[,2])
colnames(pheno.sc) <- c("FID", "IID", "pheno")
pheno.sc <- as.data.frame(pheno.sc)

write.table(pheno.sc, file="D:/Data/hw2/pheno_sc.txt", sep="\t", col.names=T, row.names=F, quote=F)
write.table(keep.sc, file="D:/Data/hw2/keep_sc.txt", sep="\t", col.names=T, row.names=F, quote=F)

## Plink code
# linear regression using files from above and map/ped files
plink --ped subjects_153.ped --map subjects_153.map --linear --covar mycov.txt --keep keep_sc.txt --pheno pheno_sc.txt --all-pheno

## R code
# read in assoc file, identify SNPs with p < 0.01 and most prevalent chr among top 100 SNPs

snp.lin <- read.table("D:/Data/hw2/plink.pheno.assoc.logistic", header=T)
snp.lin.0_01 <- snp.lin[snp.lin[,9] < .01,]
nrow(snp.lin)
nrow(snp.lin.0_01)
snp.lin.sorted <- snp.lin.0_01[order(snp.lin.0_01$P),]
table(snp.lin.sorted$CHR[1:100])
