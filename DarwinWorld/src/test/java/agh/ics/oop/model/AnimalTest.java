package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimalTest {

    @Test
    public void testAnimalInitialization() {
        Globe globe = new Globe(1, 1 , 0, 2);
        Genotype genotype = new Genotype(new ArrayList<>(Arrays.asList(2, 1, 0)));
        Animal animal = new Animal(new Vector2d(10, 10), 5, genotype, globe);

        assertEquals(new Vector2d(10, 10), animal.getPosition());
        assertEquals(MapDirection.NORTH, animal.getDirection());
        assertEquals(5, animal.energy);
    }

    @Test
    public void testAnimalSetDirection() {
        Globe globe = new Globe(1, 1 , 0, 2);
        Genotype genotype = new Genotype(new ArrayList<>(Arrays.asList(2, 1, 0)));
        Animal animal = new Animal(new Vector2d(10, 10), 5, genotype, globe);

        animal.setDirection(MapDirection.EAST);
        assertEquals(MapDirection.EAST, animal.getDirection());

        animal.setDirection(MapDirection.SOUTH);
        assertEquals(MapDirection.SOUTH, animal.getDirection());
    }

    @Test
    public void testAnimalActiveGene() {
        Globe globe = new Globe(1, 1 , 0, 2);
        Genotype genotype = new Genotype(new ArrayList<>(Arrays.asList(2, 1, 0)));
        Animal animal = new Animal(new Vector2d(10, 10), 5, genotype, globe);

        assertEquals(2, animal.useActiveGene());
        assertEquals(1, animal.useActiveGene());
        assertEquals(0, animal.useActiveGene());
        assertEquals(2, animal.useActiveGene());
    }

    @Test
    public void testAnimalMovement() {
        Globe globe = new Globe(1, 1 , 0, 2);
        Genotype genotype = new Genotype(new ArrayList<>(Arrays.asList(2, 1, 0)));
        Animal animal = new Animal(new Vector2d(10, 10), 5, genotype, globe);

        animal.move(new Vector2d(11, 10));
        assertEquals(new Vector2d(11, 10), animal.getPosition());

        animal.move(new Vector2d(11, 11));
        assertEquals(new Vector2d(11, 11), animal.getPosition());
    }
}
