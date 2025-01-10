package agh.ics.oop.model;

import java.util.List;

public class GenotypeSlightCorrection extends Genotype {

    public GenotypeSlightCorrection(List<Integer> genesList) {
        super(genesList);
    }

    @Override
    public void mutate() {
        for (int i = 0; i < genesList.size(); i++) {
            if (Math.random() < 0.5) {
                int correction = Math.random()>0.5 ? -1 : 1;
                int newGene = (genesList.get(i)+correction)%8;
                genesList.set(i, newGene);
            }
        }
    }
}
