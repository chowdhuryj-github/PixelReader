/*
 * Course: CSC1120
 * Spring 2024
 * Lab 5 - Mean Image Median Revisited
 * Name: Jawadul Chowdhury
 * Created: 1/29/24
 */
package chowdhuryj;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * class for Lab5
 */
public class Lab5 extends Application {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LAB5FXML.fxml"));
        Scene s1 = new Scene(root, WIDTH, HEIGHT);
        stage.setScene(s1);
        stage.setTitle("FXML Welcome");
        stage.show();


    }
}
