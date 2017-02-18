# read data in
cn <- read.table("D:/Data/cn_states.txt", header=T)
log.2 <- read.table("D:/Data/log2ratios.txt", header=T)

# subset cn and log2 data for Chr 13
chr13 <- log.2[log.2$Chromosome==13,]
dx <- chr13[,-c(1:3)]
d.logratio <- CNA(genomdat=as.matrix(dx),chrom=chr13$Chromosome,maploc=chr13$Position,data.type=c("logratio"),sampleid=NULL)

#run smoothing (note that we have a smoothed values from cnat, but we need this in an R object format)
d.smoothed <- smooth.CNA(d.logratio)

#run circular binary segmentation
d.segment <- segment(d.smoothed, verbose=1)

#plot results for chr 13 for all subjects
pdf("CNV_plot.pdf")
plot(d.segment, plot.type="chrombysample", pt.cex=0.5,lwd=0.5)
dev.off()
