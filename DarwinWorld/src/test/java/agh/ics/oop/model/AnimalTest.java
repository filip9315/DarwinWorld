package agh.ics.oop.model;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimalTest {

    @Test
    public void testAnimalInitialization() {
        Genotype genotype = new Genotype(new ArrayList<>(Arrays.asList(2, 1, 0)));
        Animal animal = new Animal(new Vector2d(10, 10), 5, genotype);

        assertEquals(new Vector2d(10, 10), animal.getPosition());
        assertEquals(MapDirection.NORTH, animal.getDirection());
        assertEquals(5, animal.energy);
    }

    @Test
    public void testAnimalSetDirection() {
        Genotype genotype = new Genotype(new ArrayList<>(Arrays.asList(2, 1, 0)));
        Animal animal = new Animal(new Vector2d(10, 10), 5, genotype);

        animal.setDirection(MapDirection.EAST);
        assertEquals(MapDirection.EAST, animal.getDirection());

        animal.setDirection(MapDirection.SOUTH);
        assertEquals(MapDirection.SOUTH, animal.getDirection());
    }

    @Test
    public void testAnimalActiveGene() {
        Genotype genotype = new Genotype(new ArrayList<>(Arrays.asList(2, 1, 0)));
        Animal animal = new Animal(new Vector2d(10, 10), 5, genotype);

        assertEquals(2, animal.getActiveGene());
        assertEquals(1, animal.getActiveGene());
        assertEquals(0, animal.getActiveGene());
        assertEquals(2, animal.getActiveGene());
    }

    @Test
    public void testAnimalMovement() {
        Genotype genotype = new Genotype(new ArrayList<>(Arrays.asList(2, 1, 0)));
        Animal animal = new Animal(new Vector2d(10, 10), 5, genotype);

        animal.move(new Vector2d(11, 10));
        assertEquals(new Vector2d(11, 10), animal.getPosition());

        animal.move(new Vector2d(11, 11));
        assertEquals(new Vector2d(11, 11), animal.getPosition());
    }
}
