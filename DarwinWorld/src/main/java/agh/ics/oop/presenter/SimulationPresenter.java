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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.*;

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
    public Button showMostCommonGenotypeButton;
    public Button showPreferredGrassTilesButton;

    WorldMap map;
    Simulation simulation;
    Animal followedAnimal;
    double tileWidth = 0;
    double tileHeight = 0;
    boolean showingMostCommonGenotype = false;
    boolean showingPreferredGrassTiles = false;
    List<Circle> circles = new ArrayList<>();
    List<Rectangle> rectangles = new ArrayList<>();

    String message;


    @FXML
    public void initialize() {
        column1.setCellValueFactory(new PropertyValueFactory<>("column1"));
        column2.setCellValueFactory(new PropertyValueFactory<>("column2"));
        animalStatsColumn1.setCellValueFactory(new PropertyValueFactory<>("column1"));
        animalStatsColumn2.setCellValueFactory(new PropertyValueFactory<>("column2"));
        showMostCommonGenotypeButton.setDisable(true);
        showPreferredGrassTilesButton.setDisable(true);
    }


    public void pause(){
        simulation.pause();
        if(simulation.isPaused()){
            pauseButton.setText("Resume");
            showMostCommonGenotypeButton.setDisable(false);
            showPreferredGrassTilesButton.setDisable(false);
        } else {
            pauseButton.setText("Pause");
            showingMostCommonGenotype = false;
            showingPreferredGrassTiles = false;
            showMostCommonGenotypeButton.setDisable(true);
            showPreferredGrassTilesButton.setDisable(true);
        }
    }

    public void showMostCommonGenotype(){
        if(!showingMostCommonGenotype){
            for(Animal animal : map.getAnimals()){
                if(animal.getGenotype().equals(map.getStatistics().getMostCommonGenotype())){
                    int x = animal.getPosition().getX();
                    int y = animal.getPosition().getY();

                    Circle emptyCircle = new Circle(Math.min(tileWidth/2, tileHeight/2));
                    emptyCircle.setStroke(Color.RED);
                    emptyCircle.setFill(Color.TRANSPARENT);
                    emptyCircle.setStrokeWidth(2);
                    emptyCircle.setMouseTransparent(true);
                    mapGrid.add(emptyCircle, x, y, 1, 1);
                    GridPane.setHalignment(emptyCircle, HPos.CENTER);
                    circles.add(emptyCircle);
                }
            }
            showingMostCommonGenotype = true;
        } else{
            for (Circle circle : circles) {
                mapGrid.getChildren().remove(circle);
            }
            circles.clear();
            showingMostCommonGenotype = false;
        }
    }


    public void showPreferredGrassTiles() {
        if(!showingPreferredGrassTiles){
            for(Vector2d position : map.getStatistics().getPreferredGrassTiles()){
                int x = position.getX();
                int y = position.getY();

                Rectangle rectangle = new Rectangle(tileWidth, tileHeight);
                rectangle.setStroke(Color.RED);
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStrokeWidth(2);
                mapGrid.add(rectangle, x, y, 1, 1);
                GridPane.setHalignment(rectangle, HPos.CENTER);
                rectangles.add(rectangle);
            }
            showingPreferredGrassTiles = true;
        } else {
            for (Rectangle rectangle : rectangles) {
                mapGrid.getChildren().remove(rectangle);
            }
            rectangles.clear();
            showingPreferredGrassTiles = false;
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
                .filter(worldElement -> !(worldElement instanceof Animal))
                .forEach(this::drawWorldElement);

        map.getElements().stream()
                .filter(worldElement -> worldElement instanceof Animal)
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
