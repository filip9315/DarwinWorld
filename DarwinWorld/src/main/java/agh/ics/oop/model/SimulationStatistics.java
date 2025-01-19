package agh.ics.oop.model;

import agh.ics.oop.presenter.SimulationPresenter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SimulationStatistics {

    String numberOfAnimals;
    String numberOfGrasses;
    String numberOfEmptyTiles;
    String mostCommonGenotype;
    String averageEnergy;
    String averageLifetime;
    String averageNumberOfChildren;

    WorldMap map;
    List<RowData> rowData = new ArrayList<>();
    Map<Vector2d, Integer> grassCount = new HashMap<>();

    public SimulationStatistics(WorldMap map) {
        this.map = map;
    }

    public RowData getRow(int i){
        if(i < rowData.size()){
            return rowData.get(i);
        }
        return new RowData("No data", "No data");
    }

    public void updateStatistics() {
        rowData.clear();

        numberOfAnimals = "" + map.getAnimals().size();
        numberOfGrasses = "" + map.getElements().stream().filter(worldElement -> worldElement instanceof Grass).count();
        numberOfEmptyTiles = "" + ((long) map.getMapWidth() * map.getMapHeight() - (long) map.getElements().size());
        mostCommonGenotype = getMostCommonGenotype().toString();
        averageEnergy = getAverageEnergy();
        averageLifetime = getAverageLifetime();
        averageNumberOfChildren = getAverageNumberOfChildren();

        rowData.add(new RowData("Number of animals", numberOfAnimals));
        rowData.add(new RowData("Number of grasses", numberOfGrasses));
        rowData.add(new RowData("Number of empty tiles", numberOfEmptyTiles));
        rowData.add(new RowData("Most common genotype", mostCommonGenotype));
        rowData.add(new RowData("Average energy", averageEnergy));
        rowData.add(new RowData("Average lifetime", averageLifetime));
        rowData.add(new RowData("Average number of children", averageNumberOfChildren));
    }


    public Genotype getMostCommonGenotype() {
        Map<Genotype, Integer> genotypes = new HashMap<>();

        for (Animal animal : map.getAnimals()) {
            Genotype tmp = animal.genotype;
            genotypes.put(tmp, genotypes.getOrDefault(tmp, 0) + 1);
        }

        Genotype mostCommonGenotype = null;
        int maxCount = 0;
        for (Map.Entry<Genotype, Integer> entry : genotypes.entrySet()) {
            if (entry.getValue() > maxCount) {
                mostCommonGenotype = entry.getKey();
                maxCount = entry.getValue();
            }
        }

        return mostCommonGenotype;
    }

    void updateGrassCount(Vector2d position) {
        grassCount.put(position, grassCount.getOrDefault(position, 0) + 1);
    }

    public List<Vector2d> getPreferredGrassTiles() {
        int maxValue = Collections.max(grassCount.values());

        List<Vector2d> preferredGrassTiles = new ArrayList<>();
        for (Map.Entry<Vector2d, Integer> entry : grassCount.entrySet()) {
            if (entry.getValue() == maxValue) {
                preferredGrassTiles.add(entry.getKey());
            }
        }
        return preferredGrassTiles;
    }

    String getAverageEnergy() {
        double sum = 0;
        for (Animal animal : map.getAnimals()) {
            sum += animal.energy;
        }
        return String.format("%.2f", sum / map.getAnimals().size());
    }


    String getAverageLifetime() {
        double sum = 0;
        for (Animal animal : map.getAnimals()) {
            sum += animal.getAge();
        }
        return String.format("%.2f", sum / map.getAnimals().size());
    }


    String getAverageNumberOfChildren() {
        double sum = 0;
        for (Animal animal : map.getAnimals()) {
            sum += animal.getNumOfChildren();
        }
        return String.format("%.2f", sum / map.getAnimals().size());
    }

    public void save(String path) throws IOException {
        File file = new File(path);
        BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));

        if (file.length() == 0) {
            for (RowData rowData : rowData) {
                writer.write(rowData.getColumn1() + ",");
            }
            writer.newLine();
        }

        for (RowData rowData : rowData) {
            writer.write(rowData.getColumn2() + ",");
        }

        writer.newLine();

        writer.close();
    }
}


