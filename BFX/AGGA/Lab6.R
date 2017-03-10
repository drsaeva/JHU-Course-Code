## bash code
# copy all lines containing 'chr6.fa' into new file
awk '/chr6.fa/' /mnt/d/Data/fc/FC305JN_s_1_eland_result.txt > /mnt/d/Data/fc/chr6.txt

## R code
# read in file
chr6 <- read.table("D:/Data/fc/chr6.txt")
nrow(chr6)

## bash code
# make new file with only columns 1,2,7,8,9
awk '{print $1 "\t" $2 "\t" $7 "\t" $8 "\t" $9}' /mnt/d/Data/fc/chr6.txt > /mnt/d/Data/fc/chr6_short.txt

## R code
# read in file
chr6 <- read.table("D:/Data/fc/chr6_short.txt")
length(unique(chr6[chr6[,5]=="F", 4]))
length(unique(chr6[chr6[,5]=="R", 4]))

tmp <- as.character(chr6[,5])

tmp[tmp=="F"] <- "+"
tmp[tmp=="R"] <- "-"

chr6[,5] <- tmp

unique(chr6[,5])
