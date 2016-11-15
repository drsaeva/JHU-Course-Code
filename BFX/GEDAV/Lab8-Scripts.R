# load dataset
library(fibroEset)
data(fibroEset)
fib <- fibroEset
fib
fib$species

# random 50-gene subset
fib.50 <- fib[sample(featureNames(fib), 50),]
colnames(exprs(fib.50)) <- as.character(fib.50$species)

# hc with manhattan distance and median linkage
fib.50.cols <- colnames(exprs(fib.50))
fib.50.man <- dist(t(exprs(fib.50)), method="manhattan")
fib.50.hc <- hclust(fib.50.man, method="median")
plot(fib.50.hc, labels=fib.50.cols, main="Hierarchical Clustering of 
a 50-gene subset of the fibroEset data")

# heatmap
hm.rg <-c("#FF0000","#CC0000","#990000","#660000","#330000","#000000",
"#000000","#0A3300","#146600","#1F9900","#29CC00","#33FF00")
heatmap(as.matrix(exprs(fib.50)), xlab="Sample", ylab="Gene", main="Heatmap from 
fibroEset 50-gene subset")

# k-means clustering
fib.50.pca <- prcomp(t(exprs(fib.50)))
fib.50.km <- kmeans(fib.50.pca$rotation[,1:2], centers=3, iter.max = 20)

# scatter plot labels
par(mfrow = c(2, 1))
plot(fib.50.pca$rotation[,1:2], col=fib.50.km$cluster, cex=1,
	main="Scatter Plot of fibroEset classification labels from\nk-means-clustering, k = 3",
	xlab = "P1", ylab = "P2")
points(fib.50.km$centers, col = 1:3, cex = 2.5)
