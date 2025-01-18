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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

    public TableView tableView;
    public TableColumn column1;
    public TableColumn column2;
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



    @FXML
    public void initialize() {
        column1.setCellValueFactory(new PropertyValueFactory<>("column1"));
        column2.setCellValueFactory(new PropertyValueFactory<>("column2"));

        for (int i = 1; i <= 10; i++) {
            tableView.getItems().add();
        }
    }
    
    
    
    
    //===========
    //=== Map ===
    //===========
    
    public void setMap(WorldMap map) {
        this.map = map;
    }

    public void drawMap(){
        clearGrid();

        int width = map.getMapWidth();
        int height = map.getMapHeight();
        tileWidth = (double) 500/width;
        tileHeight = (double) 500/height;

        for (int i = 0; i < width; i++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(tileWidth));
        }

        for (int i = 0; i < height; i++) {
            mapGrid.getRowConstraints().add(new RowConstraints(tileHeight));
        }

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                drawWorldElement(new Tile(TileType.STEPPE, new Vector2d(i, j)));
            }
        }

        map.getElements().stream()
                .filter(Objects::nonNull)
                .forEach(this::drawWorldElement);

        map.getElements().stream()
                .filter(worldElement -> worldElement instanceof Grass)
                .filter(worldElement -> !(map.objectAt(worldElement.getPosition()) instanceof Animal))
                .forEach(this::drawWorldElement);

    }

    private void drawWorldElement(WorldElement element) {
        int x = element.getPosition().getX();
        int y = element.getPosition().getY();

        Node shape = element.getShape(tileWidth, tileHeight);
        mapGrid.add(shape, x, y, 1, 1);
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
