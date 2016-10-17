# load marray, genepix files, extract fore/background median vals from Cy3/Cy5 channels
library(marray)
dir <- "E:/R_Data"
gp <- read.GenePix(path=dir, name.Gf="F532 Median", name.Gb="B532 Median", 
name.Rf="F635 Median", name.Rb="B635 Median", name.W="Flags") 

# mva plots for each subject
one <- gp[, 1]
two <- gp[, 2]
three <- gp[, 3]
four <- gp[, 4] 

one.med <- maNorm(one, norm=c("median"))
two.med <- maNorm(two, norm=c("median"))
three.med <- maNorm(three, norm=c("median"))
four.med <- maNorm(four, norm=c("median"))

one.loe <- maNorm(one, norm=c("loess"))
two.loe <- maNorm(two, norm=c("loess"))
three.loe <- maNorm(three, norm=c("loess"))
four.loe <- maNorm(four, norm=c("loess"))

one.ptl <- maNorm(one, norm=c("printTipLoess"))
two.ptl <- maNorm(two, norm=c("printTipLoess"))
three.ptl <- maNorm(three, norm=c("printTipLoess"))
four.ptl <- maNorm(four, norm=c("printTipLoess"))

par(mfrow = c(4, 1)) 
maPlot(one, main="Subject 1: Non-normalized", 
       lines.func=NULL, legend.func=NULL) 
maPlot(one.med, main="Subject 1: Global Median-normalized", 
       lines.func=NULL, legend.func=NULL) 
maPlot(one.loe, main="Subject 1: Loess-normalized", 
       lines.func=NULL, legend.func=NULL) 
maPlot(one.ptl, main="Subject 1: Print-tip-group Loess-normalized", 
       lines.func=NULL, legend.func=NULL) 

par(mfrow = c(4, 1)) 
maPlot(three, main="Subject 3: Non-normalized", 
       lines.func=NULL, legend.func=NULL) 
maPlot(three.med, main="Subject 3: Global Median-normalized", 
       lines.func=NULL, legend.func=NULL) 
maPlot(three.loe, main="Subject 3: Loess-normalized", 
       lines.func=NULL, legend.func=NULL) 
maPlot(three.ptl, main="Subject 3: Print-tip-group Loess-normalized", 
       lines.func=NULL, legend.func=NULL) 

par(mfrow = c(4, 1)) 
maPlot(two, main="Subject 2: Non-normalized", 
       lines.func=NULL, legend.func=NULL) 
maPlot(two.med, main="Subject 2: Global Median-normalized", 
       lines.func=NULL, legend.func=NULL) 
maPlot(two.loe, main="Subject 2: Loess-normalized", 
       lines.func=NULL, legend.func=NULL) 
maPlot(two.ptl, main="Subject 2: Print-tip-group Loess-normalized", 
       lines.func=NULL, legend.func=NULL) 

par(mfrow = c(4, 1)) 
maPlot(four, main="Subject 4: Non-normalized", 
       lines.func=NULL, legend.func=NULL) 
maPlot(four.med, main="Subject 4: Global Median-normalized", 
       lines.func=NULL, legend.func=NULL) 
maPlot(four.loe, main="Subject 4: Loess-normalized", 
       lines.func=NULL, legend.func=NULL) 
maPlot(four.ptl, main="Subject 4: Print-tip-group Loess-normalized", 
       lines.func=NULL, legend.func=NULL) 

# plot density plots of log ratios for each normalization for subject 4

plot(density(na.omit(maM(four))), main="Subject 4 Density Plot of 
       Expression Log Ratios (pre- and post-normalized)", ylim=c(0, 0.9), 
       xlim=c(-6.6, 11.7), col="black")
lines(density(na.omit(maM(four.med))), col="blue") 
lines(density(na.omit(maM(four.loe))), col="orange") 
lines(density(na.omit(maM(four.ptl))), col="green") 

leg.txt <- c("Non-normalized", "Global Median-normalized", 
       "Loess-normalized", "Print-tip-group 
       Loess-normalized")
legend(1, 0.85, legend=leg.txt, lty=c(1, 1), lwd=c(2.5, 2.5),col=c("black", 
       "blue", "orange", "green")) 
