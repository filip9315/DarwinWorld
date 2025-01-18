package agh.ics.oop.model;

import java.util.List;
import java.util.Objects;

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

    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Genotype))
            return false;

        for(int i = 0; i < numOfGenes; i++){
            if(!this.genesList.get(i).equals(((Genotype)other).genesList.get(i))){
                return false;
            }
        }
        return true;
    }

    public int hashCode(){
        return Objects.hash(genesList);
    }
}
