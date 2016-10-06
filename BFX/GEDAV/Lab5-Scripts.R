# This script file works with the swirl 2-channel cDNA data set as well
#   as a pair of external microarray datasets

# Written by David Saeva, but referencing code written by B. Higgs

# load marray and the swirl object
library(marray)

# mva plot of array 3 without stratified lines
maPlot(swirl[,3], main="MvA Plot of Array 3", lines.func=NULL, legend.func=NULL)

# normalization of array 3 via median location normalization
sw.three.norm <- maNorm(swirl[,3], norm =c("median"))

# mva plot of normalized array 3
maPlot(sw.three.norm, main="MvA Plot of Median-Normalized Array 3", lines.func=NULL, legend.func=NULL)

# loess normalized data and mva plot
sw.three.loess <- maNorm(swirl[,3], norm = c("loess"))
maPlot(sw.three.loess, main="MvA Plot of Loess-Normalized Array 3", lines.func=NULL, legend.func=NULL)

# read in and set up genepix data
dir.path <- "D:/Data"
a.cdna <- read.GenePix(path=dir.path, name.Gf="F532 Median", name.Gb="B532 Median", name.Rf="F635 Median", 
  name.Rb="B635 Median", name.W="Flags")

# Compare each patient's mva plots with no, loess, and scale print-tip normalization methods
pt1 <- a.cdna[,1]
pt1.loess <- maNorm(pt1, norm=c("printTipLoess"))
pt1.ptm <- maNorm(pt1, norm=c("scalePrintTipMAD"))
par(mfrow=c(3,1))
maPlot(pt1, main="Patient 1, No Normalization", lines.func=NULL, legend.func=NULL)
maPlot(pt1.loess, main="Patient 1, Print-tip Loess Normalization", lines.func=NULL, legend.func=NULL)
maPlot(pt1.ptm, main="Patient 1,Scale Print-tip Normalization Using MAD", lines.func=NULL, legend.func=NULL)

pt2 <- a.cdna[,2]
pt2.loess <- maNorm(pt2, norm=c("printTipLoess"))
pt2.ptm <- maNorm(pt2, norm=c("scalePrintTipMAD"))
par(mfrow=c(3,1))
maPlot(pt2, main="Patient 2, No Normalization", lines.func=NULL, legend.func=NULL)
maPlot(pt2.loess, main="Patient 2, Print-tip Loess Normalization", lines.func=NULL, legend.func=NULL)
maPlot(pt2.ptm, main="Patient 2,Scale Print-tip Normalization Using MAD", lines.func=NULL, legend.func=NULL)

# make data matrix with normalized data, get probe IDs and use as row names
pts.unref <- cbind(pt1, pt2)
pts.loess <- cbind(pt1.loess, pt2.loess)
pts.ptm <- cbind(pt1.ptm, pt2.ptm)
pids <- pts.unref@maGnames@maLabels

loess <- maM(pts.loess)
dimnames(loess)[[1]] <- pids
ptm <- maM(pts.ptm)
dimnames(ptm)[[1]] <- pids

# load required packages
library(affy)
library(limma)
library(simpleaffy) 
library(affyPLM) 
library(fpc)

# read in metadata object for 3 normal patient arrays
fns <- sort(list.celfiles(path=dir.path, full.names=TRUE))
affy <- ReadAffy(filenames=fns, phenoData=NULL)

# 
affy.MAS <- justMAS(affy)
affy.rma <- call.exprs(affy, "rma")

dim(affy.rma) 
dim(affy.MAS)


# 
rma <- cor(exprs(affy.rma))
mas <- cor(exprs(affy.MAS))

rma
mas
