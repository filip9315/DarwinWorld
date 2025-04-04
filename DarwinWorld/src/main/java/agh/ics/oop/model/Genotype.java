package agh.ics.oop.model;

import java.util.*;
import java.util.List;
import java.util.Objects;

public class Genotype {
    public int activeGeneIndex = 0;
    public int activeGene;
    List<Integer> genesList = new ArrayList<>();
    int numOfGenes;
    int minGenesToMutate = 0;
    int maxGenesToMutate;

    public Genotype(List<Integer> genesList) {
        this.genesList = genesList;
        this.numOfGenes = genesList.size();
        this.activeGene = genesList.getFirst();
        this.minGenesToMutate = 0;
        this.maxGenesToMutate = numOfGenes;
    }

    public Genotype(List<Integer> genesList, int minGenesToMutate, int maxGenesToMutate) {
        this.genesList = genesList;
        this.numOfGenes = genesList.size();
        activeGene = genesList.getFirst();
        this.minGenesToMutate = minGenesToMutate;
        this.maxGenesToMutate = maxGenesToMutate;
    }

    public Genotype(Animal animal1, Animal animal2){
        numOfGenes = animal1.getGenotype().getGenesList().size();
        List<Integer> newGenesList = new ArrayList<>();
        if (Math.random() < 0.5) {
            double energyRatio = (double) animal1.getEnergy() / (animal1.getEnergy() + animal2.getEnergy());
            int partitionIndex = (int) (numOfGenes * energyRatio);
            newGenesList.addAll(animal1.getGenotype().getGenesList().subList(0, partitionIndex));
            newGenesList.addAll(animal2.getGenotype().getGenesList().subList(partitionIndex, numOfGenes));
        } else {
            double energyRatio = (double) animal2.getEnergy() / (animal1.getEnergy() + animal2.getEnergy());
            int partitionIndex = (int) (numOfGenes * energyRatio);
            newGenesList.addAll(animal2.getGenotype().getGenesList().subList(0, partitionIndex));
            newGenesList.addAll(animal1.getGenotype().getGenesList().subList(partitionIndex, numOfGenes));
        }
        this.genesList = newGenesList;
        this.minGenesToMutate = animal1.getGenotype().minGenesToMutate;
        this.maxGenesToMutate = animal1.getGenotype().maxGenesToMutate;
        this.mutate();
    }

    public int useGenotype(){
        int currentGeneIndex = activeGeneIndex;
        activeGeneIndex++;
        activeGeneIndex %= numOfGenes;
        return genesList.get(currentGeneIndex);
    }

    public int getActiveGene() {
        return genesList.get((activeGeneIndex + 1) % numOfGenes);
    }

    public void mutate() {
        Random random = new Random();
        int numOfGenesToMutate = random.nextInt(maxGenesToMutate - minGenesToMutate + 1) + minGenesToMutate;

        Set<Integer> genesToMutateSet = new HashSet<>();
        while (genesToMutateSet.size() < numOfGenesToMutate) {
            genesToMutateSet.add(random.nextInt(genesList.size()-1));
        }

        List<Integer> newGenes = new ArrayList<>(genesList);

        for (Integer geneIndex : genesToMutateSet) {

            int newGene = (int) Math.round(Math.random() * numOfGenes);
            newGenes.set(geneIndex, newGene);
        }

        genesList = newGenes;

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

    public synchronized String toString() {
        return genesList.toString();
    }

    public List<Integer> getGenesList() {
        return genesList;
    }
}
