package agh.ics.oop.model;

import java.util.*;

public class Genotype {
    public int activeGeneIndex = 0;
    public int activeGene;
    List<Integer> genesList;
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

    public int useGenotype(){
        int currentGeneIndex = activeGeneIndex;
        activeGeneIndex++;
        activeGeneIndex %= numOfGenes;
        return genesList.get(currentGeneIndex);
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

        this.minGenesToMutate = animal1.getGenotype().minGenesToMutate;
        this.maxGenesToMutate = animal1.getGenotype().maxGenesToMutate;
        this.mutate();
    }



    public void mutate(){
        Random random = new Random();
        int numOfGenesToMutate = random.nextInt(maxGenesToMutate - minGenesToMutate + 1) + minGenesToMutate;

        Set<Integer> genesToMutateSet = new HashSet<>();

        while (genesToMutateSet.size() < numOfGenesToMutate) {
            genesToMutateSet.add(random.nextInt(numOfGenes + 1));
        }

        for (Integer geneIndex : genesToMutateSet) {
            int newGene = (int) Math.round(Math.random() * numOfGenes + 1);
                genesList.set(geneIndex, newGene);
        }
    }

    public List<Integer> getGenesList() {
        return genesList;
    }
}
