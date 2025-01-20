package agh.ics.oop.model;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GenotypeSlightCorrection extends Genotype {

    public GenotypeSlightCorrection(List<Integer> genesList, int minGenesToMutate, int maxGenesToMutate) {super(genesList, minGenesToMutate, maxGenesToMutate);}
    public GenotypeSlightCorrection(Animal animal1, Animal animal2) {super(animal1, animal2);}


    @Override
    public void mutate() {
        Random random = new Random();
        int numOfGenesToMutate = random.nextInt(maxGenesToMutate - minGenesToMutate + 1) + minGenesToMutate;
        Set<Integer> genesToMutateSet = new HashSet<>();
        while (genesToMutateSet.size() < numOfGenesToMutate) {
            genesToMutateSet.add(random.nextInt(numOfGenes));
        }
        for (Integer geneIndex : genesToMutateSet) {
            int correction = Math.random()>0.5 ? -1 : 1;
            int x = genesList.get(geneIndex)+correction;
            int newGene = (x % 8 + 8) % 8;
            genesList.set(geneIndex, newGene);
        }
    }
}
