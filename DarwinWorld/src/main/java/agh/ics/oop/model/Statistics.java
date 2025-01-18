package agh.ics.oop.model;

import java.util.*;

public class Statistics {

    String numberOfAnimals;
    String numberOfGrasses;
    String numberOfEmptyTiles;
    String mostCommonGenotype;
    String averageEnergy;
    String averageLifetime;
    String averageNumberOfChildren;

    WorldMap map;
    List<String> dataList = new ArrayList<>();

    public Statistics(WorldMap map) {
        this.map = map;
    }

    public RowData getRow(int i){

    }

    void updateStatistics() {
        numberOfAnimals = "" + map.getAnimals().size();
        numberOfGrasses = "" + map.getElements().stream().filter(worldElement -> worldElement instanceof Grass).count();
        numberOfEmptyTiles = "" + (map.getElements().size() - map.getElements().stream().filter(worldElement -> worldElement instanceof Grass).count());
        mostCommonGenotype = getMostCommonGenotype();
        averageEnergy = getAverageEnergy();

    }

    String getMostCommonGenotype() {
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

        return mostCommonGenotype != null ? mostCommonGenotype.toString() : "No genotypes found";
    }


    String getAverageEnergy() {
        double sum = 0;
        for (Animal animal : map.getAnimals()) {
            sum += animal.energy;
        }
        return String.format("%.2f", sum / map.getAnimals().size());
    }


    String getAverageLifetime() {

    }


    public static class RowData {
        private final String column1;
        private final String column2;

        public RowData(String column1, String column2) {
            this.column1 = column1;
            this.column2 = column2;
        }

        public String getColumn1() {
            return column1;
        }

        public String getColumn2() {
            return column2;
        }
    }
}


