library(tidyverse)
library(ggplot2)
library(dplyr)

x <- 1:256
y <- 1:201

#
#
#
# load the data
#
#
#
#
#lion 20 with sizes
lionGroupRGB20Sizes <- read.csv("../ImageCSV/lionBFRange20Sizes.csv")
lionBlue20Sizes <- slice(lionGroupRGB20Sizes, 1:256)
lionGreen20Sizes <- slice(lionGroupRGB20Sizes, (1+256):(256+256))
lionRed20Sizes <- slice(lionGroupRGB20Sizes, (1+256*2):(256+256*2))
lion20GroupSizes <- as.numeric(as.vector(lionGroupRGB20Sizes[769,]))
#sorted tibble of pixels per group
dfLion20GroupSizes <- enframe(lion20GroupSizes)
dfLion20GroupSizes <- dfLion20GroupSizes %>% filter(!is.na(value))
dfLion20GroupSizes <- dfLion20GroupSizes %>% mutate(name = name - 2)
arrange(dfLion20GroupSizes, desc(value))

#lion 40 with sizes
lionGroupRGB40Sizes <- read.csv("../ImageCSV/lionBFRange40Sizes.csv")
lionBlue40Sizes <- slice(lionGroupRGB40Sizes, 1:256)
lionGreen40Sizes <- slice(lionGroupRGB40Sizes, (1+256):(256+256))
lionRed40Sizes <- slice(lionGroupRGB40Sizes, (1+256*2):(256+256*2))
lion40GroupSizes <- as.numeric(as.vector(lionGroupRGB40Sizes[769,]))
#sorted tibble of pixels per group
dfLion40GroupSizes <- enframe(lion40GroupSizes)
dfLion40GroupSizes <- dfLion40GroupSizes %>% filter(!is.na(value))
dfLion40GroupSizes <- dfLion40GroupSizes %>% mutate(name = name - 2)
arrange(dfLion40GroupSizes, desc(value))

#lion 60 with sizes
lionGroupRGB60Sizes <- read.csv("../ImageCSV/lionBFRange60Sizes.csv")
lionBlue60Sizes <- slice(lionGroupRGB60Sizes, 1:256)
lionGreen60Sizes <- slice(lionGroupRGB60Sizes, (1+256):(256+256))
lionRed60Sizes <- slice(lionGroupRGB60Sizes, (1+256*2):(256+256*2))
lion60GroupSizes <- as.numeric(as.vector(lionGroupRGB60Sizes[769,]))
#sorted tibble of pixels per group
dfLion60GroupSizes <- enframe(lion60GroupSizes)
dfLion60GroupSizes <- dfLion60GroupSizes %>% filter(!is.na(value))
dfLion60GroupSizes <- dfLion60GroupSizes %>% mutate(name = name - 2)
arrange(dfLion60GroupSizes, desc(value))

#
#
#
#
# histograms of group sizes
#
#
#
#
#histograms of group sizes
ggplot(dfLion20GroupSizes, aes(value)) + 
  geom_histogram(bins=20) + 
  ylim(0, 5250) +
  scale_x_log10(
    labels=c("1", "10", "100", "1,000", "10k", "100k"),
    breaks=c(1, 10, 100, 1000, 10000, 100000))
#ylim(0, 5250) demonstrates the difference in distribution
ggplot(dfLion40GroupSizes, aes(value)) + 
  ylim(0, 5250) +
  geom_histogram(bins = 20) + 
  scale_x_log10(
    labels=c("1", "10", "100", "1,000", "10k", "100k"), 
    breaks=c(1, 10, 100, 1000, 10000, 100000))
ggplot(dfLion60GroupSizes, aes(value)) +
  geom_histogram(bins = 20) +
  ylim(0, 5250) +
  scale_x_log10(
    labels=c("1", "10", "100", "1,000", "10k", "100k"), 
    breaks=c(1, 10, 100, 1000, 10000, 100000))

#
#
#
#
# plotting largest groups
#
#
#
arrange(dfLion20GroupSizes, desc(value))
arrange(dfLion40GroupSizes, desc(value))
arrange(dfLion60GroupSizes, desc(value))
#
#
#The group that encapsulates the whitespace behind the lion. 
#Almost all white pixels
#
plot(x, lionBlue20Sizes$G2)
plot(x, lionBlue40Sizes$G2)
plot(x, lionBlue60Sizes$G1)
#
#
#The plot of the 20 grouping's second and third largest 
#grouping.
#
#shows pretty centered blue, green, and red groups,
#which would allow for good encoding
#
plot(x, lionBlue20Sizes$G7)
plot(x, lionGreen20Sizes$G7)
plot(x, lionRed20Sizes$G7)
#
#blue and green have mostly 0 values, but red is more distributed
#
plot(x, lionBlue20Sizes$G8)
plot(x, lionGreen20Sizes$G8)
plot(x, lionRed20Sizes$G8)
#
#The plot of the 40 grouping's second and third largest 
#grouping.
#
#The blue and green values plot with a nice center, but the
#red values don't
#
plot(x, lionBlue40Sizes$G0)
plot(x, lionGreen40Sizes$G0)
plot(x, lionRed40Sizes$G0)
#
#all three colors well centered
#
plot(x, lionBlue40Sizes$G4)
plot(x, lionGreen40Sizes$G4)
plot(x, lionRed40Sizes$G4)
#
plot(x, lionBlue40Sizes$G6)
plot(x, lionGreen40Sizes$G6)
plot(x, lionRed40Sizes$G6)
#
#
#The plot of the 60 grouping's second and third largest
#grouping.
#
#Like 40, the blue and green values plot nicely and the
#reds don't
#
plot(x, lionBlue60Sizes$G0)
plot(x, lionGreen60Sizes$G0)
plot(x, lionRed60Sizes$G0)
#
#COMPARE TO THIRD GROUP OF 20, very similar distribution
#
plot(x, lionBlue60Sizes$G2)
plot(x, lionGreen60Sizes$G2)
plot(x, lionRed60Sizes$G2)
