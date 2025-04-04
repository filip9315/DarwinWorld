package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDateTime;

public class MenuPresenter {

    public Button chooseFileButton;
    public Label chosenFileLabel;
    public RadioButton globeRadioButton;
    public RadioButton waterMapRadioButton;
    public TextField numberOfGrassesTextField;
    public TextField grassEnergyTextField;
    public TextField numberOfAnimalsTextField;
    public TextField initEnergyTextField;
    public TextField genotypeLengthTextField;
    public TextField simulationLengthTextField;
    public Button saveSimulationButton;
    public Button startSimulationButton;
    public TextField growingGrassesTextField;
    public TextField energyToBeAbleToProcreateTextField;
    public TextField energyToProcreateTextField;
    public TextField minNumberOfMutationsTextField;
    public TextField maxNumberOfMutationsTextField;
    public RadioButton randomMutationButton;
    public RadioButton slightCorrectionMutationButton;
    public CheckBox saveToCSVCheckbox;

    @FXML
    private TextField widthTextField;
    @FXML
    private TextField heightTextField;

    WorldMap map;
    int simulationLength;
    int mapWidth;
    int mapHeight;
    int mapType;
    int numberOfGrasses;
    int grassEnergy;
    int grassGrowingSpeed;
    int numberOfAnimals;
    int initEnergy;
    int energyToBeAbleToProcreate;
    int energyToProcreate;
    int minNumberOfMutations;
    int maxNumberOfMutations;
    int mutationType;
    int genotypeLength;
    boolean saveToCSV;

    SimulationEngine simulationEngine;

    @FXML
    public void initialize() {
        simulationEngine = new SimulationEngine();
    }


    private void getInfoFromGUI(){
        simulationLength = Integer.parseInt(simulationLengthTextField.getText());
        mapWidth = Integer.parseInt(widthTextField.getText());
        mapHeight = Integer.parseInt(heightTextField.getText());
        numberOfGrasses = Integer.parseInt(numberOfGrassesTextField.getText());
        grassEnergy = Integer.parseInt(grassEnergyTextField.getText());
        grassGrowingSpeed = Integer.parseInt(growingGrassesTextField.getText());
        numberOfAnimals = Integer.parseInt(numberOfAnimalsTextField.getText());
        initEnergy = Integer.parseInt(initEnergyTextField.getText());
        energyToBeAbleToProcreate = Integer.parseInt(energyToBeAbleToProcreateTextField.getText());
        energyToProcreate = Integer.parseInt(energyToProcreateTextField.getText());
        minNumberOfMutations = Integer.parseInt(minNumberOfMutationsTextField.getText());
        maxNumberOfMutations = Integer.parseInt(maxNumberOfMutationsTextField.getText());
        genotypeLength = Integer.parseInt(genotypeLengthTextField.getText());


        if(mapType == 0){
            map = new Globe(mapWidth, mapHeight, numberOfGrasses, grassGrowingSpeed, energyToProcreate, energyToBeAbleToProcreate,grassEnergy, minNumberOfMutations, maxNumberOfMutations, genotypeLength, initEnergy, mutationType);
        } else if (mapType == 1){
            map = new WaterMap(mapWidth, mapHeight, 1+((int) (Math.random()*2)), numberOfGrasses, grassGrowingSpeed, energyToProcreate, energyToBeAbleToProcreate, genotypeLength, minNumberOfMutations, maxNumberOfMutations, genotypeLength, initEnergy, mutationType);
        }

        saveToCSV = saveToCSVCheckbox.isSelected();
    }


    public void handleMapTypeSelection0(ActionEvent event) {
        if (globeRadioButton.isSelected()) {
            waterMapRadioButton.setSelected(false);
            globeRadioButton.setSelected(true);
            mapType = 0;
        }
    }

    public void handleMapTypeSelection1(ActionEvent event) {
         if (waterMapRadioButton.isSelected()) {
            globeRadioButton.setSelected(false);
            waterMapRadioButton.setSelected(true);
            mapType = 1;
        }
    }

    public void handleMutationTypeSelection0(ActionEvent event) {
        if(randomMutationButton.isSelected()){
            slightCorrectionMutationButton.setSelected(false);
            randomMutationButton.setSelected(true);
            mutationType = 0;
        }
    }

    public void handleMutationTypeSelection1(ActionEvent event) {
        if(slightCorrectionMutationButton.isSelected()){
            slightCorrectionMutationButton.setSelected(true);
            randomMutationButton.setSelected(false);
            mutationType = 1;
        }
    }


    public void handleChooseFileButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        Stage stage = (Stage) chooseFileButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            System.out.println("File selected: " + selectedFile.getAbsolutePath());
            chosenFileLabel.setText("File selected: " + selectedFile.getAbsolutePath());

            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                simulationLength = Integer.parseInt(reader.readLine());
                mapWidth = Integer.parseInt(reader.readLine());
                mapHeight = Integer.parseInt(reader.readLine());
                mapType = Integer.parseInt(reader.readLine());
                numberOfGrasses = Integer.parseInt(reader.readLine());
                grassEnergy = Integer.parseInt(reader.readLine());
                grassGrowingSpeed = Integer.parseInt(reader.readLine());
                numberOfAnimals = Integer.parseInt(reader.readLine());
                initEnergy = Integer.parseInt(reader.readLine());
                energyToBeAbleToProcreate = Integer.parseInt(reader.readLine());
                energyToProcreate = Integer.parseInt(reader.readLine());
                minNumberOfMutations = Integer.parseInt(reader.readLine());
                maxNumberOfMutations = Integer.parseInt(reader.readLine());
                mutationType = Integer.parseInt(reader.readLine());
                genotypeLength = Integer.parseInt(reader.readLine());


                simulationLengthTextField.setText(String.valueOf(simulationLength));
                widthTextField.setText(String.valueOf(mapWidth));
                heightTextField.setText(String.valueOf(mapHeight));
                numberOfGrassesTextField.setText(String.valueOf(numberOfGrasses));
                grassEnergyTextField.setText(String.valueOf(grassEnergy));
                growingGrassesTextField.setText(String.valueOf(grassGrowingSpeed));
                numberOfAnimalsTextField.setText(String.valueOf(numberOfAnimals));
                initEnergyTextField.setText(String.valueOf(initEnergy));
                energyToBeAbleToProcreateTextField.setText(String.valueOf(energyToBeAbleToProcreate));
                energyToProcreateTextField.setText(String.valueOf(energyToProcreate));
                minNumberOfMutationsTextField.setText(String.valueOf(minNumberOfMutations));
                maxNumberOfMutationsTextField.setText(String.valueOf(maxNumberOfMutations));
                genotypeLengthTextField.setText(String.valueOf(genotypeLength));


                if (mapType == 0) {
                    globeRadioButton.setSelected(true);
                } else if (mapType == 1) {
                    waterMapRadioButton.setSelected(true);
                }

                if (mutationType == 0) {
                    randomMutationButton.setSelected(true);
                } else if (mutationType == 1) {
                    slightCorrectionMutationButton.setSelected(true);
                }

                System.out.println("File loaded successfully.");
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
                chosenFileLabel.setText("Error loading file.");
            }
        } else {
            chosenFileLabel.setText("No file selected.");
        }
    }



    public void handleSaveFileButtonAction() {

        getInfoFromGUI();

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Folder to Save Simulation");

        File selectedDirectory = directoryChooser.showDialog(null);

        if (selectedDirectory != null) {

            File file = new File(selectedDirectory, "simulation_data.txt");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(simulationLength + "\n");
                writer.write(mapWidth + "\n");
                writer.write(mapHeight + "\n");
                writer.write(mapType + "\n");
                writer.write(numberOfGrasses + "\n");
                writer.write(grassEnergy + "\n");
                writer.write(grassGrowingSpeed + "\n");
                writer.write(numberOfAnimals + "\n");
                writer.write(initEnergy + "\n");
                writer.write(energyToBeAbleToProcreate + "\n");
                writer.write(energyToProcreate + "\n");
                writer.write(minNumberOfMutations + "\n");
                writer.write(maxNumberOfMutations + "\n");
                writer.write(mutationType + "\n");
                writer.write(genotypeLength + "\n");


                System.out.println("File created and data written successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            chosenFileLabel.setText("No folder chosen");
        }
    }



    public void handleStartSimulationButton() throws IOException {
        getInfoFromGUI();
        Simulation simulation = new Simulation(numberOfAnimals, map, initEnergy, genotypeLength, simulationLength, saveToCSV);
        if(saveToCSV){
            String filename = "simulation_data_" + LocalDateTime.now() +".txt";
            simulation.setFileName(filename);
        }
       simulationEngine.createNewSimulation(simulation);
    }


}
