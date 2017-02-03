## Plink code
# filtering by <5% missing rate per snp (geno)
# maf > 5% (maf)
# <10% missing rate per individual (mind)
# hwe at signficance <0.001 (hwe)

plink --map snps_1809.map --ped nr_no_443.ped --geno 0.05 --maf 0.05 -hwe 0.001 --mind 0.1 --recode --out new_snps

# fisher's exact tests for p<0.05 and p<0.01
plink --ped new_snps.ped --map new_snps.map --fisher --ci 0.95 --recode --out fisher

# R code
# generate plot
fish <- read.table("D:/Data/ccsnp/fisher.assoc.fisher", header=T)
fish.dat <- cbind(fish[,3], fish[,8])
plot(fish.dat[,1], -log(fish.dat[,2]), type="h", xlab="Position", ylab="-log10(p-val)", 
main="Distribution of p-values from Fisher's Exact Test across chr 6")
abline(a = 1.3, b = 0, col = "blue", lty = 3)
abline(a = 2, b = 0, col = "red", lty = 3)
text(x = median(fish.dat[,1]), y = 7, labels = "red: p<.01, blue: p<.05")

# subset p<.01 and p<.05
fish05 <- subset(fish, fish[,08]<.05)
fish01 <- subset(fish, fish[,08]<.01)

## Plink code
# mds vectors for rejector and normal control data
plink --ped new_snps.ped --map new_snps.map --genome
plink --ped new_snps.ped --map new_snps.map --read-genome plink.genome --cluster --mds-plot 2

## R code
#plot first 2 vectors from mds for rejector/control data
mds <- read.table("D:/Data/ccsnp/plink.mds", header=T)
sam <- c(rep(1,36),rep(2,400))
plot(mds[,4:5],col=sam,pch=16,xlab="p1",ylab="p2",main="MDS of 441 case-control samples")

# output file
cov <- cbind(mds[,1], mds[,2], mds[,4], mds[,5])
colnames(cov) <- c("Family ID", "Individual ID", "covariate 1", "covariate 2")
cov[1:5,]
write.table(cov, file="D:/Data/ccsnp/mycov.txt", col.names=T)

# Plink code
# association model using linear regression and covariates
# note that the mycov.txt file has 2 columns for family ID, individual ID, then each other column is a covariate
# add the –-genotypic flag to include the dominance term
# here I created a covariate file using the first 2 MDS eigenvectors from above as my 2 covariates
plink --ped new_snps.ped --map new_snps.map --linear --covar mycov.txt

