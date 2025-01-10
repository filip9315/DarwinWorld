package agh.ics.oop.model;

import java.util.List;

public class Genotype {
    public int activeGene = 0;
    List<Integer> genesList;
    int numOfGenes;

    public Genotype(List<Integer> genesList) {
        this.genesList = genesList;
        this.numOfGenes = genesList.size();
    }

    public int useGenotype(){
        int currentGene = activeGene;
        activeGene++;
        activeGene %= numOfGenes;
        return currentGene;
    }

    public void mutate(){
        for (int i = 0; i < genesList.size(); i++) {
            if (Math.random() < 0.5) {
                int newGene = (int) Math.round(Math.random() * numOfGenes + 1);
                genesList.set(i, newGene);
            }
        }
    }
}
