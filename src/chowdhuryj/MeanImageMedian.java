/*
 * Course: CSC1120
 * Spring 2024
 * Lab 5 - Mean Image Median Revisited
 * Name: Jawadul Chowdhury
 * Created: 1/29/24
 */
package chowdhuryj;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Random;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.io.File;

/**
 * class for Mean Image Median
 */
public class MeanImageMedian {
    /**
     * Maximum color value
     */
    public static final int MAX_COLOR = 255;

    /**
     * HARDCODED value
     */
    private static final int HARDCODED = 1297305413;

    @FunctionalInterface
    interface Transform {
        int apply(int[] array);
    }

    /**
     * Calculates the median of all the images passed to the method.
     * <br />
     * Each pixel in the output image consists is calculated as the median
     * red, green, and blue components of the input images at the same location.
     *
     * @param inputImages Images to be used as input
     * @return An image containing the median color value for each pixel in the input images
     * @throws IllegalArgumentException Thrown if inputImages or any element of inputImages is null,
     * the length of the array is less than two, or  if any of the input images differ in size.
     * @throws IOException throw IOException
     * Calculates the median of all the images passed to the method.
     * ...
     * @deprecated use {@link #generateImage} instead
     **/
    @Deprecated
    public static Image calculateMedianImage(Image[] inputImages) throws IllegalArgumentException {
        if(inputImages == null) {
            throw new IllegalArgumentException();
        } else if(inputImages.length < 2) {
            throw new IllegalArgumentException();
        }

        for(int i = 0; i < inputImages.length; i++) {
            if(inputImages[i] == null) {
                throw new IllegalArgumentException();
            }
        }

        int height = (int) inputImages[0].getHeight();
        int width = (int) inputImages[0].getWidth();

        for(int i = 0; i < inputImages.length; i++) {
            if(height != inputImages[i].getHeight() && width != inputImages[i].getWidth()) {
                throw new IllegalArgumentException();
            }
        }

        WritableImage retObjMedian = new WritableImage(
                (int) inputImages[0].getWidth(), (int) inputImages[0].getHeight());
        ArrayList<Integer> redList = new ArrayList<>();
        ArrayList<Integer> greenList = new ArrayList<>();
        ArrayList<Integer> blueList = new ArrayList<>();
        int red = 0;
        int redMedian = 0;
        int green = 0;
        int greenMedian = 0;
        int blue = 0;
        int blueMedian = 0;
        // loop through the inputImages
        for(int x = 0; x < inputImages[0].getWidth(); x++){
            for(int y = 0; y < inputImages[0].getHeight(); y++){
                redList.clear();
                greenList.clear();
                blueList.clear();
                for(int i = 0; i < inputImages.length; i++){
                    PixelReader obj = inputImages[i].getPixelReader();
                    int retXY = obj.getArgb(x, y);

                    red = argbToRed(retXY);
                    blue = argbToBlue(retXY);
                    green = argbToGreen(retXY);

                    redList.add(red);
                    greenList.add(green);
                    blueList.add(blue);
                }


                Collections.sort(redList);
                if(redList.size() % 2 == 0) {
                    // accounting for even length
                    int retMidOne = redList.get(redList.size() / 2 - 1);
                    int retMidTwo = redList.get(redList.size() / 2);
                    redMedian = (retMidOne + retMidTwo) / 2;
                } else {
                    redMedian = redList.get(redList.size() / 2);
                }

                Collections.sort(greenList);
                if(greenList.size() % 2 == 0) {
                    int retMidOne = greenList.get(greenList.size() / 2 - 1);
                    int retMidTwo = greenList.get(greenList.size() / 2);
                    greenMedian = (retMidOne + retMidTwo) / 2;
                } else {
                    greenMedian = greenList.get(greenList.size() / 2);
                }

                Collections.sort(blueList);
                if(blueList.size() % 2 == 0) {
                    int retMidOne = blueList.get(blueList.size() / 2 - 1);
                    int retMidTwo = blueList.get(blueList.size() / 2);
                    blueMedian = (retMidOne + retMidTwo) / 2;
                } else {
                    blueMedian = blueList.get(blueList.size() / 2);
                }

                int retIntVal = argbToInt(MAX_COLOR, redMedian, greenMedian, blueMedian);

                retObjMedian.getPixelWriter().setArgb(x, y, retIntVal);


            }
        }

        return retObjMedian;
    }

    /**
     * Calculates the mean of all the images passed to the method.
     * <br />
     * Each pixel in the output image consists is calculated as the average of the
     * red, green, and blue components of the input images at the same location.
     *
     * @param inputImages Images to be used as input
     * @return An image containing the mean color value for each pixel in the input images
     * @throws IllegalArgumentException Thrown if inputImages or any
     * element of inputImages is null, the length of the array
     * is less than two, or  if any of the input images differ in size.
     * Calculates the mean of all the images passed to the method.
     * ...
     * @deprecated use {@link #generateImage} instead
     **/
    @Deprecated
    public static Image calculateMeanImage(Image[] inputImages) throws IllegalArgumentException {
        if(inputImages == null) {
            throw new IllegalArgumentException();
        } else if(inputImages.length < 2) {
            throw new IllegalArgumentException();
        }

        for(int i = 0; i < inputImages.length; i++) {
            if(inputImages[i] == null) {
                throw new IllegalArgumentException();
            }
        }

        int height = (int) inputImages[0].getHeight();
        int width = (int) inputImages[0].getWidth();

        for(int i = 0; i < inputImages.length; i++) {
            if(height != inputImages[i].getHeight() && width != inputImages[i].getWidth()) {
                throw new IllegalArgumentException();
            }
        }


        WritableImage retObjMean = new WritableImage(
                (int) inputImages[0].getWidth(), (int) inputImages[0].getHeight());
        ArrayList<Integer> redList = new ArrayList<>();
        ArrayList<Integer> greenList = new ArrayList<>();
        ArrayList<Integer> blueList = new ArrayList<>();
        int red = 0;
        int redSum = 0;
        int green = 0;
        int greenSum = 0;
        int blue = 0;
        int blueSum = 0;
        // loop through the inputImages
        for(int x = 0; x < inputImages[0].getWidth(); x++){
            for(int y = 0; y < inputImages[0].getHeight(); y++){
                // resetting the sum to zero
                redSum = 0;
                greenSum = 0;
                blueSum = 0;
                redList.clear();
                greenList.clear();
                blueList.clear();
                for(int i = 0; i < inputImages.length; i++){
                    PixelReader obj = inputImages[i].getPixelReader();
                    int retXY = obj.getArgb(x, y);

                    red = argbToRed(retXY);
                    blue = argbToBlue(retXY);
                    green = argbToGreen(retXY);

                    redList.add(red);
                    greenList.add(green);
                    blueList.add(blue);

                }

                Collections.sort(redList);
                for(int i = 0; i < redList.size(); i++){
                    redSum = redSum + redList.get(i);
                }

                Collections.sort(greenList);
                for(int i = 0; i < greenList.size(); i++){
                    greenSum = greenSum + greenList.get(i);
                }

                Collections.sort(blueList);
                for(int i = 0; i < blueList.size(); i++){
                    blueSum = blueSum + blueList.get(i);
                }

                redSum = redSum / redList.size();
                greenSum = greenSum / greenList.size();
                blueSum = blueSum / blueList.size();
                int retIntVal = argbToInt(MAX_COLOR, redSum, greenSum, blueSum);

                retObjMean.getPixelWriter().setArgb(x, y, retIntVal);

            }
        }

        return retObjMean;
    }

    /**
     * Reads an image in PPM format. The method only supports the plain
     * PPM (P3) format with 24-bit color and does not support comments in the image file.
     *
     * @param imagePath the path to the image to be read
     * @return An image object containing the image read from the file.
     * @throws IllegalArgumentException Thrown if imagePath is null.
     * @throws IOException              Thrown if the image
     * format is invalid or there was trouble reading the file.
     */


    private static Image readMSOEImage(Path imagePath) throws IOException {
        System.out.println(imagePath.toString());
        WritableImage retWrite = null;
        PixelWriter retPixel = null;

        int retLength = 0;
        int retHeight = 0;

        try(FileInputStream fileIn = new FileInputStream(imagePath.toString())) {
            DataInputStream dataIn = new DataInputStream(fileIn);
            dataIn.readInt();

            retLength = dataIn.readInt();
            retHeight = dataIn.readInt();

            retWrite = new WritableImage(retLength, retHeight);
            retPixel = retWrite.getPixelWriter();

            int x = 0;
            int y = 0;
            while (dataIn.available() > 0){
                System.out.println(x + " " + y);

                while (y < retHeight) {
                    int newVal = dataIn.readInt();
                    retPixel.setArgb(x, y, newVal);
                    if(x >= retLength - 1) {
                        y++;
                        x = 0;
                    } else {
                        x++;
                    }
                }
            }
        }

        return retWrite;
    }


    private static Image readPPMImage(Path imagePath) throws IOException {
        System.out.println(imagePath.toString());
        WritableImage retWrite;
        PixelWriter retPixel;

        String stringTwo = "";

        int retLength = 0;
        int retHeight = 0;

        try (Scanner fileIn = new Scanner(imagePath)) {
            fileIn.nextLine();
            stringTwo = fileIn.nextLine();
            fileIn.nextLine();

            String[] numbers = stringTwo.split(" ");
            retLength = Integer.parseInt(numbers[0]);
            retHeight = Integer.parseInt(numbers[1]);

            retWrite = new WritableImage(retLength, retHeight);
            retPixel = retWrite.getPixelWriter();

            int x = 0;
            int y = 0;

            while (y < retHeight) {
                int red = Integer.parseInt(fileIn.next());
                int green = Integer.parseInt(fileIn.next());
                int blue = Integer.parseInt(fileIn.next());
                int retCombine = argbToInt(MAX_COLOR, red, green, blue);
                retPixel.setArgb(x, y, retCombine);
                if(x >= retLength - 1) {
                    y++;
                    x = 0;
                } else {
                    x++;
                }

            }

        }

        return retWrite;
    }

    /**
     * class for readImage
     * @param imagePath imagePath
     * @return obj
     * @throws IOException IOException
     */

    // fixed and edited
    public static Image readImage(Path imagePath) throws IOException {
        Image obj = null;
        String filePath = imagePath.toString();
        String lastThreeLetters = "";

        int length = filePath.length();
        if(length >= 3) {
            String strFile = filePath.toString();
            int index = strFile.indexOf(".");
            lastThreeLetters = filePath.substring(index+1, length);
        }

        if(lastThreeLetters.equals("png")) {
            obj = new Image(new FileInputStream(filePath));
        } else if(lastThreeLetters.equals("jpg")) {
            obj = new Image(new FileInputStream(filePath));
        } else if (lastThreeLetters.equals("ppm")) {
            obj = readPPMImage(imagePath);
        } else if(lastThreeLetters.equals("msoe")) {
            obj = readMSOEImage(imagePath);
        } else {
            throw new IOException("not valid extension");
        }

        return obj;


    }

    /**
     * class for writeImage
     * @param imagePath imagePath
     * @param image image
     * @throws IOException IOException
     */
    // fixed and edited
    public static void writeImage(Path imagePath, Image image) throws IOException {
        String filePath = imagePath.toString();
        String lastFourLetters = "";

        int length = filePath.length();
        if(length >= 3) {
            lastFourLetters = filePath.substring(length - 4);
        }

        if(lastFourLetters.equals(".png")) {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", new File(filePath));
        } else if (lastFourLetters.equals(".jpg")) {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "jpg", new File(filePath));
        } else if (lastFourLetters.equals(".ppm")) {
            writePPMImage(imagePath, image);
        } else if(lastFourLetters.equals("msoe")) {
            writeMSOEIMage(imagePath, image);
        } else {
            throw new IOException("Incorrect File Type");
        }
    }


    private static void writeMSOEIMage(Path imagePath, Image image) throws IOException {
        try(FileOutputStream fileOut = new FileOutputStream(imagePath.toString())) {
            DataOutputStream dataOut = new DataOutputStream(fileOut);

            dataOut.writeInt(HARDCODED);
            dataOut.writeInt((int) image.getWidth());
            dataOut.writeInt((int) image.getHeight());


            int retX = 0;
            int retY = 0;

            while(retY < image.getHeight()) {
                dataOut.writeInt(image.getPixelReader().getArgb(retX, retY));
                if(retX >= (int) image.getWidth() - 1) {
                    retY++;
                    retX = 0;
                } else {
                    retX++;
                }
            }
        }

    }


    /**
     * Writes an image in PPM format. The method only supports the
     * plain PPM (P3) format with 24-bit color
     * and does not support comments in the image file.
     * @param imagePath the path to where the file should be written
     * @param image the image containing the pixels to be written to the file
     *
     * @throws IllegalArgumentException Thrown if imagePath is null.
     * @throws IOException Thrown if the image format is
     * invalid or there was trouble reading the file.
     */
    private static void writePPMImage(Path imagePath, Image image) throws IOException {
        PrintWriter objPrint = new PrintWriter(String.valueOf(imagePath));

        objPrint.println("P3");

        objPrint.println((int) image.getWidth() + " " + (int) image.getHeight());
        objPrint.println("255");

        int retX = 0;
        int retY = 0;

        while (retY < image.getHeight()) {
            objPrint.print(argbToRed(image.getPixelReader().getArgb(retX, retY)));
            objPrint.print(" ");
            objPrint.print(argbToGreen(image.getPixelReader().getArgb(retX, retY)));
            objPrint.print(" ");
            objPrint.print(argbToBlue(image.getPixelReader().getArgb(retX, retY)));
            objPrint.print(" ");
            if(retX >= (int) image.getWidth() - 1) {
                retY++;
                retX = 0;
                objPrint.println();
            } else {
                retX++;
            }
        }

        objPrint.close();
    }

    /**
     * Extract 8-bit Alpha value of color from 32-bit representation of the color in the format
     * described by the INT_ARGB PixelFormat type.
     * @param argb the 32-bit representation of the color
     * @return the 8-bit Alpha value of the color.
     */
    private static int argbToAlpha(int argb) {
        final int bitShift = 24;
        return argb >> bitShift;
    }

    /**
     * Extract 8-bit Red value of color from 32-bit representation of the color in the format
     * described by the INT_ARGB PixelFormat type.
     * @param argb the 32-bit representation of the color
     * @return the 8-bit Red value of the color.
     */

    private static int argbToRed(int argb) {
        final int bitShift = 16;
        final int mask = 0xff;
        return (argb >> bitShift) & mask;
    }

    /**
     * Extract 8-bit Green value of color from 32-bit representation of the color in the format
     * described by the INT_ARGB PixelFormat type.
     * @param argb the 32-bit representation of the color
     * @return the 8-bit Green value of the color.
     */
    private static int argbToGreen(int argb) {
        final int bitShift = 8;
        final int mask = 0xff;
        return (argb >> bitShift) & mask;
    }

    /**
     * Extract 8-bit Blue value of color from 32-bit representation of the color in the format
     * described by the INT_ARGB PixelFormat type.
     * @param argb the 32-bit representation of the color
     * @return the 8-bit Blue value of the color.
     */
    private static int argbToBlue(int argb) {
        final int bitShift = 0;
        final int mask = 0xff;
        return (argb >> bitShift) & mask;
    }

    /**
     * Converts argb components into a single int that represents the argb value of a color.
     * @param a the 8-bit Alpha channel value of the color
     * @param r the 8-bit Red channel value of the color
     * @param g the 8-bit Green channel value of the color
     * @param b the 8-bit Blue channel value of the color
     * @return a 32-bit representation of the color in the format
     * described by the INT_ARGB PixelFormat type.
     */
    private static int argbToInt(int a, int r, int g, int b) {
        final int alphaShift = 24;
        final int redShift = 16;
        final int greenShift = 8;
        final int mask = 0xff;
        return a << alphaShift | ((r & mask) << redShift) | (g & mask) << greenShift | b & mask;
    }

    /**
     * method for generating image
     * @param images images
     * @param operation operation
     * @return Image
     */
    public static Image generateImage(Image[] images, String operation) {
        Image retImage = null;
        operation = operation.toLowerCase();
        if (operation.equals("mean")) {
            retImage = applyTransform(images, array ->
                    Arrays.stream(array).sum() / array.length);
        }

        // calculating the median
        if (operation.equals("median")) {
            retImage = applyTransform(images, array -> {
                int retMedian = 0;
                if (array.length % 2 == 0) {
                    int retMidOne = array.length / 2 - 1;
                    int retMidTwo = array.length / 2;
                    retMedian = (array[retMidOne] + array[retMidTwo]) / 2;
                } else {
                    retMedian = array[array.length / 2];
                }
                return retMedian;
            });
        }

        // calculating the max
        if (operation.equals("max")) {
            retImage = applyTransform(images, array -> {
                return Arrays.stream(array).max().getAsInt();
            });
        }

        // calculating the min
        if (operation.equals("min")) {
            retImage = applyTransform(images, array -> {
                return Arrays.stream(array).min().getAsInt();
            });
        }

        // calculating random
        if (operation.equals("random")) {
            retImage = applyTransform(images, array -> {
                Random rand = new Random();
                return array[rand.nextInt(0, array.length - 1)];
            });
        }
        return retImage;
    }


    /**
     * method for applyTransform
     * @param images images
     * @param transformation transformation
     * @return Image
     * @throws IllegalArgumentException e
     */
    public static Image applyTransform(Image[] images, Transform transformation)
            throws IllegalArgumentException {
        if(images == null) {
            throw new IllegalArgumentException();
        } else if(images.length < 2) {
            throw new IllegalArgumentException();
        }

        for(int i = 0; i < images.length; i++) {
            if(images[i] == null) {
                throw new IllegalArgumentException();
            }
        }

        int height = (int) images[0].getHeight();
        int width = (int) images[0].getWidth();

        for(int i = 0; i < images.length; i++) {
            if(height != images[i].getHeight() && width != images[i].getWidth()) {
                throw new IllegalArgumentException();
            }
        }

        WritableImage retObjMean = new WritableImage(
                (int) images[0].getWidth(), (int) images[0].getHeight());
        ArrayList<Integer> redList = new ArrayList<>();
        ArrayList<Integer> greenList = new ArrayList<>();
        ArrayList<Integer> blueList = new ArrayList<>();
        int red = 0;
        int redSum = 0;
        int green = 0;
        int greenSum = 0;
        int blue = 0;
        int blueSum = 0;
        for(int x = 0; x < images[0].getWidth(); x++){
            for(int y = 0; y < images[0].getHeight(); y++){
                redSum = 0;
                greenSum = 0;
                blueSum = 0;
                redList.clear();
                greenList.clear();
                blueList.clear();
                for(int i = 0; i < images.length; i++){
                    PixelReader obj = images[i].getPixelReader();
                    int retXY = obj.getArgb(x, y);

                    red = argbToRed(retXY);
                    blue = argbToBlue(retXY);
                    green = argbToGreen(retXY);

                    redList.add(red);
                    greenList.add(green);
                    blueList.add(blue);
                }

                Collections.sort(redList);
                Collections.sort(greenList);
                Collections.sort(blueList);

                int[] redArray = redList.stream().mapToInt(Integer::intValue).toArray();
                int[] greenArray = greenList.stream().mapToInt(Integer::intValue).toArray();
                int[] blueArray = blueList.stream().mapToInt(Integer::intValue).toArray();

                redSum = transformation.apply(redArray);
                greenSum = transformation.apply(greenArray);
                blueSum = transformation.apply(blueArray);

                int retIntVal = argbToInt(MAX_COLOR, redSum, greenSum, blueSum);
                retObjMean.getPixelWriter().setArgb(x, y, retIntVal);

            }
        }

        return retObjMean;

    }


}
