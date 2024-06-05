
# The PixelReader Lab Collection
Welcome to the PixelReader Lab Collection! This repository is a data structures project that I've worked on during my freshman year at the Milwaukee School of Engineering!

## Lab 1 | Mean Image Median
Worked on creating a program that will read images in the PPM format and produce an image that contains the median and average of the input images. The program is designed to accept a operation of mean or median and then output a filename after inputting two or more image files. 

Also implemented the ```MeanImageMedian``` class that contains methods to be called by the program. Implemented the following class methods:
* ```Image readPPMImage(Path imagePath)``` — Reads the ```.ppm``` image file specified by the image path
* ```void writePPMImage(Path imagePath, Image image)``` — Writes image to a ```.ppm``` image file specified by the image path
* ```Image calculateMedianImage(Image[] inputImages)``` — calculates the median color for each pixel in the input images
* ```Image calculateMeanImage(Image[] inputImages)``` — calculates the mean color for each pixel in the input images

## Lab 2 | JavaFX Application
Created a graphical user interface (GUI) to load and display images. Made the following updates:
* Declared ```readPPMImage()``` and ```writePPMImage()``` as private methods
* Implemented ```readImage()``` and ```writeImage()``` methods with the same parameters as ```readPPMImage()``` and ```writePPMImage()``` that will call ```readPPMImage()``` and ```writePPMImage()``` when the file extention on the Path ends with ```.ppm```.
* Ensured ```calculateMedianImage()``` and ```calculateMeanImage()``` calculate alpha value for each pixel.
* Update ```readImage()``` and ```writeImage()``` methods to use JavaFX classes to add support for reading and writing JPG and PNG file formats.

## Lab 3 | GUI Fnuctionality Extended
Added the following updates to the graphical user interface:
* Load multiple input images
* Button to request generation and display of a "median" image
* Button to request generation and display of a "mean" image
* Button to save the generated image

## Lab 5 | A Functional Programming Approach
Updated the program to read and write images in a cusom binary format. Updated the methods to use a functional programming approach. Added the following extra functionality:
* ```Max``` — produces an output image where the pixel components for the output image are selected as the maximum value from the input images.
* ```Min``` — produces an output image where the pixel components for the output image are selected as the minimum value from the input images.
* ```Random``` — produces an output image where the pixel components for the output image are selected at random from the input images.
