package agh.ics.oop.model;

import agh.ics.oop.presenter.SimulationPresenter;

import java.util.ArrayList;
import java.util.List;

public class AnimalStatistics {

    Animal animal;
    List<RowData> rowData = new ArrayList<>();

    String genotype;
    String activeGene;
    String numberOfGrassesEaten;
    String numberOfChildren;
    String numberOfDescendants;
    String age;
    String dayOfDeath;


    public AnimalStatistics(Animal animal) {
        this.animal = animal;
    }

    public RowData getRow(int i){
        if(i < rowData.size()){
            return rowData.get(i);
        }
        return new RowData("No data", "No data");
    }

    public void updateStatistics() {
        rowData.clear();

        genotype = "" + animal.getGenotype();
        activeGene = "" + animal.getActiveGene();


        rowData.add(new RowData("Genotype", genotype));
        rowData.add(new RowData("Active", activeGene));
    }
}
