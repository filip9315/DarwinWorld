package agh.ics.oop.model;

import java.util.List;

public class Genotype {
    public int activeGeneIndex = 0;
    public int activeGene;
    List<Integer> genesList;
    int numOfGenes;

    public Genotype(List<Integer> genesList) {
        this.genesList = genesList;
        this.numOfGenes = genesList.size();
        activeGene = genesList.getFirst();
    }

    public int useGenotype(){
        int currentGeneIndex = activeGeneIndex;
        activeGeneIndex++;
        activeGeneIndex %= numOfGenes;
        return genesList.get(currentGeneIndex);
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
