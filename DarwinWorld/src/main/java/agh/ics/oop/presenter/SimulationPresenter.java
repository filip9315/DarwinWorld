package agh.ics.oop.presenter;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.text.ParsePosition;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class SimulationPresenter implements MapChangeListener {

    WorldMap map;
    double tileWidth = 0;
    double tileHeight = 0;

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

        int x0 = map.getCurrentBounds().lowerLeft().getX();
        int y0 = map.getCurrentBounds().lowerLeft().getY();
        int xn = map.getCurrentBounds().upperRight().getX();
        int yn = map.getCurrentBounds().upperRight().getY();
        int width = xn - x0;
        int height = yn - y0;
        tileWidth = (double) 500 /width;
        tileHeight = (double) 500/height;


        for (int i = 0; i < width; i++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(tileWidth));
        }
        for (int i = 0; i < height; i++) {
            mapGrid.getRowConstraints().add(new RowConstraints(tileHeight));
        }

        map.getElements().stream()
                .filter(Objects::nonNull)
                .forEach(worldElement -> drawWorldElement(worldElement, x0, y0));

        map.getElements().stream()
                .filter(worldElement -> worldElement instanceof Grass)
                .filter(worldElement -> !(map.objectAt(worldElement.getPosition()) instanceof Animal))
                .forEach(worldElement -> drawWorldElement(worldElement, x0, y0));

    }

    private void drawWorldElement(WorldElement element, int x0, int y0) {
        int x = element.getPosition().getX();
        int y = element.getPosition().getY();


        Node shape = element.getShape(tileWidth, tileHeight);
        mapGrid.add(shape, x + 1 - x0, y + 1 - y0, 1, 1);
        GridPane.setHalignment(shape, HPos.CENTER);
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

    public void setSimulation(Simulation simulation) {
        WorldMap map = simulation.getMap();
        setMap(map);
        map.registerMapChangeListener(this);
    }
}
