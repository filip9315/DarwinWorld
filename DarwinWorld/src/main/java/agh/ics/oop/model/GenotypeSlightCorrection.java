package agh.ics.oop.model;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GenotypeSlightCorrection extends Genotype {

    public GenotypeSlightCorrection(List<Integer> genesList) {
        super(genesList);
    }

    @Override
    public void mutate() {
        Random random = new Random();
        int numOfGenesToMutate = random.nextInt(maxGenesToMutate - minGenesToMutate + 1) + minGenesToMutate;

        Set<Integer> genesToMutateSet = new HashSet<>();

        while (genesToMutateSet.size() < numOfGenesToMutate) {
            genesToMutateSet.add(random.nextInt(numOfGenes + 1));
        }

        for (Integer geneIndex : genesToMutateSet) {
            int correction = Math.random()>0.5 ? -1 : 1;
                int newGene = (genesList.get(geneIndex)+correction)%8;
                genesList.set(geneIndex, newGene);
        }

//        for (int i = 0; i < genesList.size(); i++) {
//            if (Math.random() < 0.5) {
//                int correction = Math.random()>0.5 ? -1 : 1;
//                int newGene = (genesList.get(i)+correction)%8;
//                genesList.set(i, newGene);
//            }
//        }
    }
}
