# read in files
map <- read.csv("D:/Data/HuEx-1_0-st-v2.na24.hg18.probeset_abbr.csv",header=T,row.names=1)
e <- read.table("D:/Data/exon-rma-sketch.summary.txt",header=T,skip=49,sep="\t",row.names=1)
g <- read.table("D:/data/gene-rma-sketch.summary.txt",header=T,skip=49,sep="\t",row.names=1)
p <- read.table("D:/data/dabg.summary.txt",header=T,skip=49,sep="\t",row.names=1)

#class membership
r <- c(1,0,1,1,1,0,1,1,1,0,1,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1)

#intersect matching probes between annotation file and data matrix
x <- intersect(dimnames(e)[[1]],dimnames(map)[[1]])

#subset the rows in the exon, p-value matrix, and annotation file to the intersecting probes
e <- e[x,]
p <- p[x,]
map <- map[x,]

#get unique transcript cluster IDs (gene IDs) from annotation file
u <- unique(as.character(map$transcript_cluster_id))
u <- intersect(u,dimnames(g)[[1]])

#you should filter probes based on guidlines first, but will save for your assignment to do this

#exon ni value calculation and t-test function


#takes transcript cluster values, exon probes for this transcript cluster, and classification vector
exon.ni <- function(genex,exonx,rx) {
  ni <- t(t(exonx)-genex)
  ttest <- t(apply(ni,1,t.two,sam=as.logical(r),v=F))
  return(ttest)
}

t.two <- function(x,sam,v=F) {
  x <- as.numeric(x)
  out <- t.test(as.numeric(x[sam]),as.numeric(x[!sam]),alternative="two.sided",var.equal=v)
  o <- as.numeric(c(out$statistic,out$p.value,out$conf.int[1],out$conf.int[2]))
  names(o) <- c("test_statistic","pv","lower_ci","upper_ci")
  return(o)
}

# filter exon array probes based on dabg values
for (i in 1:
