# read in data, load MASS, generate transposed data frame
lung <- read.table("D:/Data/lung_cancer_data.txt", header=T, row.names=1)
library(MASS)
names <- colnames(lung)
length(names)
lung.t <- data.frame(names, t(lung))
dim(lung)

# make training and test sets
lung.train <- rbind(lung.t[1:6, ], lung.t[11:16, ], lung.t[20:22, ])
lung.test <- rbind(lung.t[7:10, ], lung.t[17:19, ], lung.t[23:24, ])
temp.l.names <- lung.test$names
lung.test.names  <- factor(gsub('[[:digit:]]+', '', temp.l.names))
lung.test$names  <- NULL

# train the model
temp.tr.names  <- lung.train$names
lung.train.names <- factor(gsub('[[:digit:]]+', '', temp.tr.names))
lung.train$names <- NULL

lung.train.lda <- lda(lung.train.names ~ ., data=lung.train[, c(1, 2)])
lung.train.pred <- predict(lung.train.lda, lung.test[, c(1, 2)])
table(lung.train.pred$class, lung.test.names)

# plot fitted data

plot(range(lung.train.pred$x[, 1]), range(lung.train.pred$x[, 2]), type="n", 
	xlab="LD1", ylab="LD2", main="LDA Plot of 2 Genes from Training Set")
points(lung.train.pred$x, col=c(rep("Green", 4), rep("Blue", 3), rep("Red", 2)),
	pch=c(rep(16, 4), rep(17, 3), rep(18, 2)))
legend("bottomright", c("Adeno", "SCLC", "Normal"), col = c("Green", "Blue", "Red"),
	pch = c(16:18))

# train/plot with all genes
lung.train.lda <- lda(lung.train.names ~ ., data=lung.train)
lung.train.out <- predict(lung.train.lda, lung.test)
table(lung.train.out$class, lung.test.names)

plot(range(lung.train.out$x[, 1]), range(lung.train.out$x[, 2]), type = "n", 
	xlab = "LD1", ylab = "LD2", main = "LDA Plot of All Genes from Training Set")
points(lung.train.out$x, col = c(rep("Green", 4), rep("Blue", 3), rep("Red", 2)),
	pch = c(rep(16, 4), rep(17, 3), rep(18, 2)))
legend("bottomright", c("Adeno", "SCLC", "Normal"), col = c("Green", "Blue", "Red"), 
  pch = c(16:18)
