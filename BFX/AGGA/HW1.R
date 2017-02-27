## APT code, windows command line
# gene estimates (exon array)
apt-probeset-summarize -a rma-sketch -a dabg 
-p files/HuEx-1_0-st-v2.r2.pgf 
-c files/HuEx-1_0-st-v2.r2.clf 
-b files/HuEx-1_0-st-v2.r2.antigenomic.bgp 
--qc-probesets files/HuEx-1_0-st-v2.r2.qcc 
-m files/HuEx-1_0-st-v2.r2.dt1.hg18.core.mps 
--cel-files files/cel_list.txt 
-o gene_output

# exon estimates (exon array)
apt-probeset-summarize -a rma-sketch 
-p files/HuEx-1_0-st-v2.r2.pgf 
-c files/HuEx-1_0-st-v2.r2.clf 
-b files/HuEx-1_0-st-v2.r2.antigenomic.bgp 
--qc-probesets files/HuEx-1_0-st-v2.r2.qcc 
-s files/HuEx-1_0-st-v2.r2.dt1.hg18.core.ps 
--cel-files files/cel_list.txt 
-o exon_output

## R code
# read in exon annotation file, exon and gene matrices

exon <- read.table("D:/Data/cel/exon_output/rma-sketch.summary.txt", skip=59, header=T, row.names=1, sep="\t")
gene <- read.table("D:/Data/cel/gene_output/rma-sketch.summary.txt", skip=59, header=T, row.names=1, sep="\t")
nrow(exon)
nrow(gene)
ex_annot <- read.table("D:/Data/cel/HuEx-1_0-st-v2.na24.hg18.probeset_abbr.csv", header=T, sep=",")

par(mfrow=c(1,2))
g.pca <- prcomp(t(gene))
plot(g.pca$x[,1:2],col=c(rep(1,3),rep(2,3),rep(3,3),rep(4,3),rep(5,3)),pch=c(rep(1,3),rep(2,3),rep(3,3),rep(4,3),rep(5,3)),
     main="Two principal components of gene matrix data")
legend("bottomleft", col=c(1:5), pch=c(1:5), c("Ctrl","Clotrimazole","Flunarizine","Ctrl-Chlor","Chlorhexidine"))

e.pca <- prcomp(t(exon))
plot(e.pca$x[,1:2],col=c(rep(1,3),rep(2,3),rep(3,3),rep(4,3),rep(5,3)),pch=c(rep(1,3),rep(2,3),rep(3,3),rep(4,3),rep(5,3)), 
     main="Two principal components of exon matrix data")
legend("bottomleft",  col=c(1:5), pch=c(1:5), c("Ctrl","Clotrimazole","Flunarizine","Ctrl-Chlor","Chlorhexidine"))

# exon.ni function from lecture notes
exon.ni <- function(genex,exonx,rx) {
	ni <- t(t(exonx)-genex)
	ttest <- t(apply(ni,1,t.two,sam=as.logical(r),v=F))
	return(ttest)	
}


# two-sample t-test function from notes modified to get foldchange
t.two <- function(x,sam,v=F) {
	x <- as.numeric(x)
	out <- t.test(as.numeric(x[sam]),as.numeric(x[!sam]),alternative="two.sided",var.equal=v)
	o <- as.numeric(c(out$statistic,out$p.value,out$conf.int[1],out$conf.int[2],out$estimate[1]-out$estimate[2]))
	names(o) <- c("test_statistic","pv","lower_ci","upper_ci","fold_change")
	return(o)
}


# intersect annotations & exon matrix, subset each by intersection results
# get unique transcript ids
ex.probes <- intersect(rownames(exon), rownames(ex_annot))
ex_annot.sub <- ex_annot[ex.probes,]
exon.sub <- exon[ex.probes,]
unique.ids <- unique(as.character(ex_annot.sub$transcript_cluster_id))
unique.ids <- intersect(unique.ids, rownames(gene))

# prepare new matrices for t test
classes <- c(F,F,F,T,T,T)
results <- NULL

# loop over clot samples
for (i in unique.ids) {
	a <- rownames(ex_annot.sub[ex_annot.sub$transcript_cluster_id %in% i,])
	ni <- as.data.frame(exon.ni(as.numeric(gene[i,10:15]),exon.sub[a,10:15],classes))
	ni$transcript_id <- rep(i,nrow(ni))
	results <- rbind(results,ni)
}

write.table(results, file="D:/Data/hw2/Chlorhexidine_T_Test.txt", col.names=T, row.names=T)

results.b <- NULL
#  looop over oother samples
for (i in unique.ids) {
	a <- rownames(ex_annot.sub[ex_annot.sub$transcript_cluster_id %in% i,])
	ni <- as.data.frame(exon.ni(as.numeric(gene[i,1:6]),exon.sub[a,1:6],classes))
	ni$transcript_id <- rep(i,nrow(ni))
	results.b <- rbind(results.b,ni)
}

write.table(results.b, file="D:/Data/hw2/Clotrimazole_T_Test.txt", col.names=T, row.names=T)

# chlor probes with |fc| > 1.5 and p<.01, unique probes
sum(abs(results$fold_change)>log2(1.5) & results$pv<.01, na.rm=T)
length(unique(results[abs(results$fold_change)>log2(1.5) & results$pv<.01,6]))

# clotr probes with |fc| > 1.5 and p<.01, unique probes
sum(abs(results.b$fold_change)>log2(1.5) & results.b$pv<.01, na.rm=T)
length(unique(results[abs(results.b$fold_change)>log2(1.5) & results.b$pv<.01,6]))

# intersect unique transcripts
common <- intersect(unique(results.b[abs(results.b$fold_change)>log2(1.5) & results.b$pv<.01,6]), 
                    unique(results[abs(results$fold_change)>log2(1.5) & results$pv<.01,6]))

length(common)
write.table(common,"D:/Data/hw1/common", row.names=T, col.names=T)
            
# write out significant transcripts for each drug
write.table(results.b[(abs(results.b$fold_change)>log2(1.5) & results.b$pv<.01), "transcript_id"],
            "D:/Data/hw1/chlor_ids.txt", col.names=F, row.names=F, quote=F)
            
write.table(results[(abs(results.b$fold_change)>log2(1.5) & results.b$pv<.01), "transcript_id"],
            "D:/Data/hw1/clot_ids.txt", col.names=F, row.names=F, quote=F)
