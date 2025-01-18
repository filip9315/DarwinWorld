package agh.ics.oop.presenter;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
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

import java.text.ParsePosition;
import java.util.List;
import java.util.stream.Stream;

public class SimulationPresenter implements MapChangeListener {

    WorldMap map;

    String message;

    @FXML
    private Label infoLabel;
    @FXML
    private TextField textField;
    @FXML
    private Button startButton;
    @FXML
    private GridPane mapGrid;


    public void setMap(WorldMap map) {
        this.map = map;
    }


    public void drawMap(){
        clearGrid();
        mapGrid.getColumnConstraints().add(new ColumnConstraints(50));
        mapGrid.getRowConstraints().add(new RowConstraints(50));


        Label label = new Label("");
        mapGrid.add(label, 0, 0, 1, 1);
        infoLabel.setText(message);
        int x0 = map.getCurrentBounds().lowerLeft().getX();
        int y0 = map.getCurrentBounds().lowerLeft().getY();
        int xn = map.getCurrentBounds().upperRight().getX();
        int yn = map.getCurrentBounds().upperRight().getY();
        int width = xn-x0;
        int height = yn-y0;

        for (int i = 0; i<=width; i++){
            int j = x0+i;
            mapGrid.getColumnConstraints().add(new ColumnConstraints(50));
            label = new Label(""+j);
            mapGrid.add(label, i+1, 0, 1, 1);
            GridPane.setHalignment(label, HPos.CENTER);
        }
        for (int i = 0; i<=height; i++){
            int j = y0+i;
            mapGrid.getRowConstraints().add(new RowConstraints(50));
            label = new Label(""+j);
            mapGrid.add(label, 0, i+1, 1, 1);
            GridPane.setHalignment(label, HPos.CENTER);
        }

        map.getElements().stream()
                .filter(worldElement -> worldElement instanceof Animal)
                .forEach(worldElement -> drawWorldElement(worldElement, x0, y0));

        map.getElements().stream()
                .filter(worldElement -> worldElement instanceof Grass)
                .filter(worldElement -> !(map.objectAt(worldElement.getPosition()) instanceof Animal))
                .forEach(worldElement -> drawWorldElement(worldElement, x0, y0));
    }

    private void drawWorldElement(WorldElement element, int x0, int y0){
        int x = element.getPosition().getX();
        int y = element.getPosition().getY();
        Label label = new Label(element.toString());
        mapGrid.add(label, x+1-x0, y+1-y0, 1, 1);
        GridPane.setHalignment(label, HPos.CENTER);
    }


    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().getFirst());
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(this::drawMap);
        this.message = message;
    }

    public void onSimulationStartClicked() {

    }
}
