/*
 * Course: CSC1120
 * Spring 2024
 * Lab 3 - Imaging Wrap Up
 * Name: Jawadul Chowdhury
 * Created: 2/5/24
 */
package chowdhuryj;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.nio.file.Path;
import java.util.List;
import javafx.scene.control.Tooltip;


/**
 * class for Lab5 Controller
 */
public class Lab5Controller {

    private List<ImageView> viewImage = new ArrayList<>();
    private Image meanImage;
    private Image medianImage;
    private Image maxImage;
    private Image minImage;
    private Image randomImage;

    @FXML
    private Label addFile;
    @FXML
    private Label retStatus;
    @FXML
    private HBox displayImage;
    @FXML
    private ImageView imageViewing;

    @FXML
    private void maxClick() {
        int imageSize = displayImage.getChildren().size();
        Image[] retImages = new Image[imageSize];

        if (imageSize < 2) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Not enough images");
            alert.show();
        }

        for (int i = 0; i < imageSize; i++) {
            retImages[i] = viewImage.get(i).getImage();
            double width = retImages[0].getWidth();
            double height = retImages[0].getHeight();

            if (retImages[i].getWidth() != width || retImages[i].getHeight() != height) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Width or Height " +
                        "of images aren't the same");
                alert.show();
            }
        }


        maxImage = MeanImageMedian.generateImage(retImages, "max");
        imageViewing.setImage(maxImage);
        addFile.setText(" Image Max Calculated");
    }

    @FXML
    private void minClick() {
        int imageSize = displayImage.getChildren().size();
        Image[] retImages = new Image[imageSize];

        if (imageSize < 2) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Not enough images");
            alert.show();
        }

        for (int i = 0; i < imageSize; i++) {
            retImages[i] = viewImage.get(i).getImage();
            double width = retImages[0].getWidth();
            double height = retImages[0].getHeight();

            if (retImages[i].getWidth() != width || retImages[i].getHeight() != height) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Width or Height " +
                        "of images aren't the same");
                alert.show();
            }
        }


        minImage = MeanImageMedian.generateImage(retImages, "min");
        imageViewing.setImage(minImage);
        addFile.setText(" Image Min Calculated");
    }

    @FXML
    private void randomClick() {
        int imageSize = displayImage.getChildren().size();
        Image[] retImages = new Image[imageSize];

        if (imageSize < 2) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Not enough images");
            alert.show();
        }

        for (int i = 0; i < imageSize; i++) {
            retImages[i] = viewImage.get(i).getImage();
            double width = retImages[0].getWidth();
            double height = retImages[0].getHeight();

            if (retImages[i].getWidth() != width || retImages[i].getHeight() != height) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Width or Height " +
                        "of images aren't the same");
                alert.show();
            }
        }


        randomImage = MeanImageMedian.generateImage(retImages, "random");
        imageViewing.setImage(randomImage);
        addFile.setText(" Image Random Calculated");
    }

    @FXML
    private void saveAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        File retDirectory = Path.of("images/").toFile();
        fileChooser.setInitialDirectory(retDirectory);
        File retFile = fileChooser.showSaveDialog(null);

        if (imageViewing.getImage() == null) {
            addFile.setText("No Image To Save");
        } else {
            if (retFile == null) {
                addFile.setText("Operation Cancelled");
            } else {
                try {
                    MeanImageMedian.writeImage(retFile.toPath(), imageViewing.getImage());
                    addFile.setText("File Saved!");
                } catch (IllegalArgumentException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage());
                    alert.show();
                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "Error Saving: " + e.getMessage());
                    alert.show();
                }
            }
        }
    }

    @FXML
    private void imageAdd() {
        FileChooser retFile = new FileChooser();
        File retDirectory = Path.of("images/").toFile();
        retFile.setInitialDirectory(retDirectory);
        List<File> selectedFiles = retFile.showOpenMultipleDialog(null);
        addFile.setText("Adding Files...");

        StringBuilder fileList = new StringBuilder();

        if (selectedFiles != null) {
            for (File file : selectedFiles) {
                try {
                    if (!file.getPath().endsWith(".png") && !file.getPath()
                            .endsWith(".jpg") && !file.getPath().endsWith(".ppm")
                            && !file.getPath().endsWith(".msoe")) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong File Type!");
                        alert.show();
                    }

                    fileList.append(file.getName()).append(" ");
                    Image image = MeanImageMedian.readImage(file.toPath());
                    Tooltip retTip = new Tooltip();
                    retTip.setText(file.getName());
                    ImageView imageView = new ImageView();
                    Tooltip.install(imageView, retTip);
                    imageView.setImage(image);
                    imageView.setPreserveRatio(true);
                    imageView.setFitHeight(displayImage.getHeight());
                    imageView.setOnMouseClicked(a -> {
                        displayImage.getChildren().remove(imageView);
                        addFile.setText(file.getName() + "was removed");
                        viewImage.remove(imageView);
                    });


                    displayImage.getChildren().add(imageView);
                    viewImage.add(imageView);
                } catch (IOException e) {
                    e.getMessage();
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong File Type!");
                    alert.show();
                }

            }
            addFile.setText(fileList.toString());
        }
    }

    @FXML
    private void meanClick() {
        int imageSize = displayImage.getChildren().size();
        Image[] retImages = new Image[imageSize];

        for (int i = 0; i < imageSize; i++) {
            retImages[i] = viewImage.get(i).getImage();
            //double width = retImages[0].getWidth();
            //double height = retImages[0].getHeight();
        }
        try {
            meanImage = MeanImageMedian.generateImage(retImages, "mean");
            imageViewing.setImage(meanImage);
            addFile.setText(" Image Mean Calculated");
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Dimensions not equal! ");
            alert.show();
        }

    }

    @FXML
    private void medianClick() {
        int imageSize = displayImage.getChildren().size();
        Image[] retImages = new Image[imageSize];
        for (int i = 0; i < imageSize; i++) {
            retImages[i] = viewImage.get(i).getImage();
            //double width = retImages[0].getWidth();
            //double height = retImages[0].getHeight();
        }

        try {
            medianImage = MeanImageMedian.generateImage(retImages, "median");
            imageViewing.setImage(medianImage);
            addFile.setText(" Image Median Calculated");
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }
    }
}

