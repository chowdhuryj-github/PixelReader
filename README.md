
# The PixelReader Lab Collection
Welcome to the Benchmarking Lab Collection! This repository is a data structures project that I've worked on during my freshman year at the Milwaukee School of Engineering!

## Lab 1 | Mean Image Median
Worked on creating a program that will read images in the PPM format and produce an image that contains the median and average of the input images. The program is designed to accept a operation of mean or median and then output a filename after inputting two or more image files. 

Also implemented the ```MeanImageMedian``` class that contains methods to be called by the program. Implemented the following class methods:
* ```Image readPPMImage(Path imagePath)``` — Reads the ```.ppm``` image file specified by the image path
* ```void writePPMImage(Path imagePath, Image image)``` — Writes image to a ```.ppm``` image file specified by the image path
* ```Image calculateMedianImage(Image[] inputImages)``` — calculates the median color for each pixel in the input images
* ```Image calculateMeanImage(Image[] inputImages)``` — calculates the mean color for each pixel in the input images

## Lab 2 | JavaFX Application


