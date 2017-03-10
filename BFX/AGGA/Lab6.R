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

# convert F/R to +/-
tmp <- as.character(chr6[,5])

tmp[tmp=="F"] <- "+"
tmp[tmp=="R"] <- "-"

chr6[,5] <- tmp

unique(chr6[,5])

# matrix of unique loci and frequencies
f <- chr6[chr6[,5]=="+",]
r <- chr6[chr6[,5]=="-",]
loci_f <- table(f[,4])
loci_r <- table(r[,4])
loci_f[1:10]

# generate MACS formatted matrix
output <- matrix( , nrow(loci_f)+nrow(loci_r), 6)
colnames(output) <- c("Chr", "start", "end", "??", "tags", "sense")
output <- as.data.frame(output)
output[1] <- c(rep("chr6", nrow(output)))
output[4] <- c(rep(0, nrow(output)))

for_end <- length(loci_f)
rev_start <- for_end+1
end <- nrow(output)

output$start[1:for_end] <- names(loci_f)
output$end[1:for_end] <- as.character(as.numeric(names(loci_f))+28)
output$sense[1:for_end] <- "+"
output$tags[1:for_end] <- loci_f

output$end[rev_start:end] <- names(loci_r)
output$start[rev_start:end] <- as.character(as.numeric(names(loci_r))-28)
output$sense[rev_start:end] <- "-"
output$tags[rev_start:end] <- loci_r

rev_start+5
rev_start-5
output[68179:68189,]
write.table(output, "D:/Data/fc/MACS_In.txt", sep="\t", col.names=T, row.names=F, quote=F)

## bash
# macs command to run file
Macs14 -t /mnt/d/Data/fc/MACS_in.txt -name chr6
