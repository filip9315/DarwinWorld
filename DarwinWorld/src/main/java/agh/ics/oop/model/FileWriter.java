package agh.ics.oop.model;

import agh.ics.oop.Simulation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

public class FileWriter {

    public FileWriter() {}

    public void saveSimulation(String path, Simulation simulation) {
        File file = new File(path);

//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
//            // Writing the variables into the file
//            writer.write("Simulation Name: " + simulationName);
//            writer.newLine();
//            writer.write("Map Width: " + mapWidth);
//            writer.newLine();
//            writer.write("Map Height: " + mapHeight);
//            writer.newLine();
//            writer.write("Initial Number of Animals: " + initialAnimals);
//            writer.newLine();
//            writer.write("Initial Energy: " + initialEnergy);
//            writer.newLine();
//
//            System.out.println("File created and data written successfully.");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
