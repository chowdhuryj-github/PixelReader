/*
 * Course: CSC1120
 * Spring 2024
 * Lab 5 - Mean Image Median Revisited
 * Name: Jawadul Chowdhury
 * Created: 1/29/24
 */
package chowdhuryj;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Lab5UnitTests {

    @Test
    @DisplayName("Read Basic Test")
    void readBasicTest(){
        final int msoeHeader = 1297305413;
        String input = "images/basicMSOE.msoe";
        ArrayList<Integer> correct =
                new ArrayList<>(Arrays.asList(msoeHeader, 2, 3, -1, -1, -16777216, -65536, -16711936,-16776961));
        ArrayList<Integer> test = new ArrayList<>();
        try {
            Image image = MeanImageMedian.readImage(Paths.get(input));

            test.add(msoeHeader);
            test.add((int)image.getWidth());
            test.add((int)image.getHeight());
            PixelReader pixelReader = image.getPixelReader();
            for(int i = 0; i<image.getHeight();i++){
                for(int j = 0; j<image.getWidth();j++){
                    test.add(pixelReader.getArgb(j,i));
                }
            }
            Assertions.assertEquals(correct, test);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Read and Write Test")
    void readAndWriteTest(){
        String input = "images/basicMSOE.msoe";
        String output = "images/readWriteTest.msoe";

        ArrayList<Integer> correct = new ArrayList<>();
        ArrayList<Integer> test = new ArrayList<>();
        try {
            if(Files.exists(Path.of(output))){
                Files.delete(Path.of(output));
            }

            Image image = MeanImageMedian.readImage(Paths.get(input));
            MeanImageMedian.writeImage(Paths.get(output), image);

            try(FileInputStream fileInputStream = new FileInputStream(input);
                DataInputStream dataInputStream = new DataInputStream(fileInputStream);) {
                for(int i = 0; i<3;i++){
                    correct.add(dataInputStream.readInt());
                }
                for(int i = 0; i<image.getWidth() * image.getHeight();i++){
                    correct.add(dataInputStream.readInt());
                }
            }

            try(FileInputStream fileInputStream = new FileInputStream(output);
                DataInputStream dataInputStream = new DataInputStream(fileInputStream);) {
                for(int i = 0; i<3;i++){
                    test.add(dataInputStream.readInt());
                }
                for(int i = 0; i<image.getWidth() * image.getHeight();i++){
                    test.add(dataInputStream.readInt());
                }
            }
            Assertions.assertEquals(correct, test);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Transformation Test")
    void meanTest(){
        String[] types = {"Mean", "Median", "Max", "Min"};
        String filesIn = "smiley1.ppm smiley2.ppm smiley3.ppm";
        for(String p: types) {
            String pref = p;
            System.out.println("Transformation: "+p);
            String correctOut = "images/smiley" + pref + ".ppm";
            ArrayList<Integer> correct = new ArrayList<>();
            ArrayList<Integer> test = new ArrayList<>();

            try {
                Image[] images = Arrays.stream(filesIn.split("\\s+")).
                        map(a -> "images/" + a).map(a -> {
                            try {
                                return MeanImageMedian.readImage(Paths.get(a));
                            } catch (IOException e) {
                                throw new RuntimeException("Error reading image "+e.getMessage());
                            }
                        }).toList().toArray(new Image[0]);
                Image testImage = MeanImageMedian.generateImage(images, pref);
                Image correctImage = MeanImageMedian.readImage(Paths.get(correctOut));

                for (int i = 0; i < testImage.getHeight(); i++) {
                    for (int j = 0; j < testImage.getWidth(); j++) {
                        test.add(testImage.getPixelReader().getArgb(j, i));
                    }
                }
                for (int i = 0; i < correctImage.getHeight(); i++) {
                    for (int j = 0; j < correctImage.getWidth(); j++) {
                        correct.add(correctImage.getPixelReader().getArgb(j, i));
                    }
                }
                Assertions.assertEquals(correct, test);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

