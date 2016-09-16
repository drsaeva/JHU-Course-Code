# This script file generates visualizations specific to a localized version 
#   of the GEO data published in the following paper published by Paul 
#   Spellman's lab at Stanford:
#
#   “Comprehensive Identification of Cell Cycle–regulated Genes of the 
#   Yeast Saccharomyces cerevisiae by Microarray Hybridization”
#   Mol Biol Cell. 1998 Dec; 9(12): 3273–3297. PMCID: PMC25624

# Written by David Saeva, but referencing code written by B. Higgs


# Read in file, isolate cdc15 experiment-specific data
spell <- read.table("E:/R_Data/spellman.txt", header=TRUE, row.names=1)
cdc15 <- spell[, 23:46]


# Generate Pearson correlation matrix between time points
library(gplots)
cdc15.cor <- cor(cdc15)

layout(matrix(c(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 2, 2), 8, 2, byrow=TRUE))
par(oma=c(5, 7, 1, 1))
leg <- seq(min(cdc15.cor, na.rm=TRUE), max(cdc15.cor, na.rm=TRUE), length=10)
image(cdc15.cor, main="Correlation plot of Time-Dependent cdc15 Relative Expression", axes=F, col=cx)
axis(1, at=seq(0, 1, length=ncol(cdc15.cor)), label=dimnames(cdc15.cor)[[2]], cex.axis=0.9, las=2)
axis(2, at=seq(0, 1, length=ncol(cdc15.cor)), label=dimnames(cdc15.cor)[[2]], cex.axis=0.9, las=2)

par(mar=rep(2, 4))
image(as.matrix(leg), col=cx, axes=F)
tmp <- round(leg, 2)
axis(1,at=seq(0, 1, length=length(leg)), labels=tmp, cex.axis=1)


# Reference VPS8 expression data, remove 'NA' values and impute row means
vps8 <- cdc15["YAL002W", ]
nas <- which(is.na(vps8), arr.ind=TRUE)
vps8(nas) <- rowMeans(vps8, na.rm=TRUE)[nas[, 1]]


# Generate profile plot of time-dependent VPS8 relative expression 
plot(c(1, ncol(cdc15)), range(vps8), type='n', main="Profile plot of Time-
Dependent VPS8 Relative Expression", xlab="Time", ylab="Relative Expression", axes=F)
timedim <- gsub("cdc15_", "", colnames(cdc15), perl=TRUE)
axis(side=1, at=c(1:24), labels=timedim, cex.axis=0.4, las=2)
axis(side=2)
for(i in 1:length(vps8)) {
    cdc15.y <- as.numeric(vps8)
    lines(c(1:ncol(cdc15)), cdc15.y, col=i, lwd=2)
}

# Generate R Shiny app with reactive plot for comparing time points against each other
# UI
ui < - fluidPage(
    headerPanel('Time-dependent relative gene expression in a cdc15 mutant yeast strain'),
    sidebarPanel(
        selectInput('xcol', 'X Variable', names(cdc15), selected=names(cdc15[[2]]),
        selectInput('ycol', 'Y Variable', names(cdc15), selected=names(cdc15[[2]]),
    ),
    mainPanel(plotOutput('plot'))
)

# Server
server <- function(input, output) {
    output$plot <- renderPlot({
        par(mar=c(5.1, 4.1, 0, 1))
        plot(selectedData(), col="#FF3333", pch=1, cex=1)
        points(selectedData(), pch=1, cex=1, lwd=1)
    })
}

# Start Server
shinyApp(ui=ui, server=server)
