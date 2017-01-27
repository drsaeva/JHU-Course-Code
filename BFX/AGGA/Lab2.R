## Plink code
# filtering by <5% missing rate per snp (geno)
# maf > 10% (maf)
# <20% missing rate per individual (mind)
# hwe at signficance <0.001 (hwe)

plink --file ped_final_re_MHC_mod --geno 0.05 --maf 0.1 -hwe 0.001 --mind 0.2 --recode --out new_ped

#recode mendelian errors to missing
plink --file new_ped --recode --me 1 1 --set-me-missing

#generate HW statistics for snps
plink --file new_ped --hardy --recode --out new_ped_hardy

# IBD calculation for trio data; note that the ped and map files have the same name with exception of the file extension
plink --file new_ped --genome rel-check --recode --out new_ped_gen

# R Code
# subset ibd data by ppc<.01 (p-vals)
ibd <- read.table("insert file here", header=T)
ibd01 <- subset(ibd, ibd[,13]<.01)
ibd01

#TDT in plink
plink --file new_ped_gen --tdt --perm --ci 0.95 --recode --out new_ped_tdt

# R Code
# subset tdt table for p<.05
tdt <- read.table("insert file here", header=T)
tdt05 <- subset(tdt, tdt[,12]<.05)

# FDR examination
tdt.fdr05 <- subset(tdt.fdr$qvalues, tdt.fdr$qvalues < .05)
tdt.fdr
tdt.fdr.qv <- tdt.fdr$qvalues
tdt.fdr.qv05 <- tdt.fdr.qv[tdt.fdr.qv < .05]
tdt.fdr.qv05

# generate plot
tdt.dat <- cbind(tdt[,3], tdt[,12])
plot(tdt.dat[,1], -log(tdt.dat[,2]), type="h", xlab="Position", ylab="-log10(p-val)", 
main="Distribution of p-values from TDT across chr 6")
abline(a = 2, b = 0, col = "red", lty = 3)

