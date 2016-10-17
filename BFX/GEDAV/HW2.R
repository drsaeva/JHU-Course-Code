# load marray, genepix files, extract fore/background median vals from Cy3/Cy5 channels
library(marray)
dir <- "E:/R_Data"
gp <- read.GenePix(path=dir, name.Gf="F532 Median", name.Gb="B532 Median", 
name.Rf="F635 Median", name.Rb="B635 Median", name.W="Flags") 
