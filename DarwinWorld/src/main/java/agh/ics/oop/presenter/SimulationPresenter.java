package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.Objects;

public class SimulationPresenter implements MapChangeListener {

    public TableColumn<RowData, String> column1;
    public TableColumn<RowData, String> column2;
    public TableView<RowData> simulationStatsTable;
    public TableView<RowData> animalStatsTable;
    public Button pauseButton;
    public Label dayLabel;
    public GridPane mapGrid;
    public TableColumn<RowData, String> animalStatsColumn1;
    public TableColumn<RowData, String> animalStatsColumn2;

    WorldMap map;
    Simulation simulation;
    Animal followedAnimal;
    double tileWidth = 0;
    double tileHeight = 0;

    String message;


    @FXML
    public void initialize() {
        column1.setCellValueFactory(new PropertyValueFactory<>("column1"));
        column2.setCellValueFactory(new PropertyValueFactory<>("column2"));
        animalStatsColumn1.setCellValueFactory(new PropertyValueFactory<>("column1"));
        animalStatsColumn2.setCellValueFactory(new PropertyValueFactory<>("column2"));
    }


    public void pause(){
        simulation.pause();
        if(simulation.isPaused()){
            pauseButton.setText("Resume");
        } else {
            pauseButton.setText("Pause");
        }
    }


    //=========
    //==Tables==
    //==========
    void drawStats(){
        simulationStatsTable.getItems().clear();
        animalStatsTable.getItems().clear();

        for (int i = 0; i < 7; i++) {
            simulationStatsTable.getItems().add(map.getStatistics().getRow(i));
        }

        if(followedAnimal != null){
            animalStatsTable.getItems().clear();
            for (int i = 0; i < 7; i++) {
                animalStatsTable.getItems().add(followedAnimal.getStatistics().getRow(i));
            }
        }

        dayLabel.setText("Day: " + simulation.getDay());
    }


    //===========
    //=== Map ===
    //===========
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

        shape.setOnMouseClicked(event -> {
            if(element instanceof Animal && simulation.isPaused()){
                followedAnimal = (Animal) element;
                drawStats();
            }
        });
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().getFirst());
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }


    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(this::drawMap);
        Platform.runLater(this::drawStats);

        this.message = message;
    }

    public void setSimulation(Simulation simulation) {
        this.map = simulation.getMap();
        this.simulation = simulation;
        map.registerMapChangeListener(this);
    }

}
