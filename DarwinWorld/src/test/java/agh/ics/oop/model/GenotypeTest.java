package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GenotypeTest {

    @Test
    public void testGenotypeUseGenotype() {
        Genotype genotype = new Genotype(new ArrayList<>(Arrays.asList(2, 1, 0)));

        assertEquals(2, genotype.useGenotype());
        assertEquals(1, genotype.useGenotype());
        assertEquals(0, genotype.useGenotype());
        assertEquals(2, genotype.useGenotype());
    }

    @Test
    public void testGenotypeMutation() {
        List<Integer> genes = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        Genotype genotype = new Genotype(genes);

        genotype.mutate();

        for (int gene : genotype.genesList) {
            assertTrue(gene >= 1 && gene <= 6, "Gene out of range after mutation: " + gene);
        }
    }

}
