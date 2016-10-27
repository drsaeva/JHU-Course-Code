# read in brain data
ag <- read.table("D:/Data/agingStudy11FCortexAffy.txt", header=T, row.names=1)
ag.cl <- read.table("D:/Data/agingStudy1FCortexAffyAnn.txt", header=T, row.names=1)

# read in annotations with no row names
ag.cl.noRN <- read.table("D:/Data/agingStudy1FCortexAffyAnn.txt", header=T)
#remove whitespace from Age column (exists in 2-digit ages) in order to facilitate concatenations
ag.cl.noRN$Age <- gsub('\\s+', '', ag.cl.noRN$Age)

# isolate by gender
ag.cl.m <- ag.cl.noRN[1:18,]
ag.cl.f <- ag.cl.noRN[19:30,]


# isolate by age <50 & age >=50 
x <- c("", "Gender", "Age")
ag.cl.y <- data.frame(matrix(ncol = 3, nrow = 0))
ag.cl.o <- data.frame(matrix(ncol = 3, nrow = 0))
colnames(ag.cl.y) <- x
colnames(ag.cl.o) <- x

for (i in 1:30) {
  if (ag.cl.noRN[i, 3] >= 50) {
    ag.cl.o =  rbind(ag.cl.o, ag.cl.noRN[i,]) 
  } else {
    ag.cl.y =  rbind(ag.cl.y, ag.cl.noRN[i,])
  }
}

# reorder columns in order to
ag.cl.m <- ag.cl.m[c(1,3,2)]
ag.cl.f <- ag.cl.f[c(1,3,2)]
ag.cl.y <- ag.cl.y[c(1,3,2)]
ag.cl.o <- ag.cl.o[c(1,3,2)]

# gender comparison gene vector
g.g <- c(1394,  1474,  1917,  2099,  2367,  2428, 2625,  3168,  3181,  3641,  3832,  4526,
4731,  4863,  6062,  6356,  6684,  6787,  6900,  7223,  7244,  7299,  8086,  8652,
8959,  9073,  9145,  9389, 10219, 11238, 11669, 11674, 11793)

# age comparison gene vector
g.a <- c(25, 302,  1847,  2324,  246,  2757, 3222, 3675,  4429,  4430,  4912,  5640, 5835, 
5856,  6803,  7229,  7833,  8133, 8579,  8822,  8994, 10101, 11433, 12039, 12353,
12404, 12442, 67, 88, 100)

# t-test function
t.test.all.genes <- function(x, s1, s2){
	x1 <- x[, s1]
	x2 <- x[, s2]
	x1 <- as.numeric(x1)
	x2 <- as.numeric(x2)
	t.out <- t.test(x1, x2, alternative="two.sided", var.equal=T)
	out <- as.numeric(t.out$p.value)
	return(out)
}

# concatenate noRN annotation file rows into vectors for gender, age for subsetting
ag.cols.y <- apply(ag.cl.y, 1, paste, collapse=".")
ag.cols.y <- as.vector(ag.cols.y)
ag.cols.o <- apply(ag.cl.o, 1, paste, collapse=".")
ag.cols.o <- as.vector(ag.cols.o)
ag.cols.m <- apply(ag.cl.m.r, 1, paste, collapse=".")
ag.cols.m <- as.vector(ag.cols.m)
ag.cols.f <- apply(ag.cl.f, 1, paste, collapse=".")
ag.cols.f <- as.vector(ag.cols.f)

# holm and raw t-test for gender and age
gen.raw.p.vals <- apply(ag[g.g, ], 1, t.test.all.genes, s1=colnames(ag)%in%ag.cols.f, s2=colnames(ag)%in%ag.cols.m) 
age.raw.p.vals <- apply(ag[g.g, ], 1, t.test.all.genes, s1=colnames(ag)%in%ag.cols.y, s2=colnames(ag)%in%ag.cols.o) 

gen.holm <- p.adjust(gen.raw.p.vals, method="holm")
age.holm <- p.adjust(age.raw.p.vals, method="holm")

# plot holm vs raw for gender
gen.holm.raw <- cbind(sort(gen.raw.p.vals), sort(gen.holm))
colnames(gen.holm.raw) <- c("Raw P-values", "Holm-adjusted P-Values") 
matplot(gen.holm.raw, type="b", pch=1, col=1:2, 
		main="Raw vs Holm-adjusted P-Values for T-test\nbetween Samples of Different Genders", 
		ylab="P-values") 
legend("topleft", legend=colnames(gen.holm.raw), pch=1, col=1:2) 

# plot holm vs raw for age
age.holm.raw <- cbind(sort(age.raw.p.vals), sort(age.holm))
colnames(age.holm.raw) <- c("Raw P-values", "Holm-adjusted P-Values") 
matplot(age.holm.raw, type="b", pch=1, col=1:2, 
		main="Raw vs Holm-adjusted P-Values for T-test\nbetween Samples Above or Below Age 50", 
		ylab="P-values") 
legend("topleft", legend=colnames(age.holm.raw), pch=1, col=1:2) 

# bonferroni corrected p-vals
gen.bon <- p.adjust(gen.raw.p.vals, method="bonferroni")
age.bon <- p.adjust(age.raw.p.vals, method="bonferroni")

# plot bon vs raw for gender
gen.bon.raw <- cbind(sort(gen.raw.p.vals), sort(gen.bon))
colnames(gen.bon.raw) <- c("Raw P-values", "Bonferroni-adjusted P-Values") 
matplot(gen.bon.raw, type="b", pch=1, col=1:2, 
		main="Raw vs Bonferroni-adjusted P-Values for T-test\nbetween Samples of Different Genders", 
		ylab="P-values") 
legend("topleft", legend=colnames(gen.bon.raw), pch=1, col=1:2) 

# plot bon vs raw for age
age.bon.raw <- cbind(sort(age.raw.p.vals), sort(age.bon))
colnames(age.bon.raw) <- c("Raw P-values", "Bonferroni-adjusted P-Values") 
matplot(age.bon.raw, type="b", pch=1, col=1:2, 
		main="Raw vs Bonferroni-adjusted P-Values for T-test\nbetween Samples Above or Below Age 50", 
		ylab="P-values") 
legend("topleft", legend=colnames(age.bon.raw), pch=1, col=1:2) 

# grep GATA3 from brca 
gata3 <- grep("GATA3", rownames(brca))
brca.gata3 <- as.numeric(brca[6362, ])add
b.g.max <- max(brca.gata3)
group = brca.gata3/b.g.max
for (i in 1:length(group)) {
  if (group[i] >= 0.75) {
    group[i] <- 1
  } else {
    group[i] <- 0
  }
}

# add group vector as column to new data frame with annotations
brca.surv <- data.frame(brca.cl[, 4], group)
brca.surv <- group

# Kaplan-Meier survival curve
km<-survfit(Surv(month, group),type="kaplan-meier",data=brca.surv)
plot(km,lwd=2,xlab='Vital Status',ylab='Expression level of GATA3',main='KM Plots for Remission data')

b.surv.compl <- brca.surv[complete.cases(brca.surv),]
bsur <- b.surv.compl
bsur[,4] <- b.surv.compl[,4]=="LIVING"

# extra chi-sq
bg.survdif <- survdiff(Surv(months_to_event, vital_status) ~ group, data=bsur)
bg.chisq.pval <- pchisq(bg.survdif$chisq, length(bg.survdif$n)-1, lower.tail = FALSE)


cox <- coxph(Surv(months_to_event, vital_status) ~ group, data=bsur)
cox.coef <- coef(summary(cox))
cox.haz <- cox.coef[1,2]
cox.pval <- cox.coef[1,5]

gata3.surv <- survfit(Surv(months_to_event, vital_status) ~ group, data=bsur)
layout(matrix(c(1,1,2,2), 2, 2, byrow = T), heights = c(4,1))
plot(gata3.surv, col=c("red", "blue"), lty = 2:3,xlab="Months",ylab="S(t)")
legend(100, .9, c("High GATA3 Expression", "Low GATA3 Expression"), col=c("red", "blue"), lty = 2:3)
title("Kaplan-Meier Curves\nfor GATA3 Expression in BRCA study")

Corner_text <- function(text, location="topright"){
  legend(location,legend=text, bty ="n", pch=NA) 
}

Corner_text(text=paste("KM P-value =", round(bgchisq.pval, 1), "\nCoxPH P-value =", 
         round(cox.pval, 1), "\nCox PH Hazard =", round(cox.haz, 1), "\nGroup 0, n =", 
         bg.survdif$n[1], "\nGroup 1, n =", bg.survdif$n[2]), location= "center")
