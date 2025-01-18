package agh.ics.oop.presenter;

import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MenuPresenter {

    public TextField mapWidthTextField;
    public TextField mapHeightTextField;
    public Button chooseFileButton;
    public Label chosenFileLabel;
    WorldMap map;

    String message;

    @FXML
    private Label infoLabel;
    @FXML
    private GridPane mapGrid;

    @FXML
    private void handleChooseFileButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        Stage stage = (Stage) chooseFileButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            System.out.println("File selected: " + selectedFile.getAbsolutePath());
            chosenFileLabel.setText("File selected: " + selectedFile.getAbsolutePath());
        } else {
            chosenFileLabel.setText("No file selected.");
        }
    }





    public void handleSaveFileButtonAction() {

    }

    public void handleStartSimulationButton() {

    }
}
