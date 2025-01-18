package agh.ics.oop.model;

import agh.ics.oop.IncorrectPositionException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class WaterMapTest {

    @Test
    public void testWaterMapInitialization() {
        WaterMap waterMap = new WaterMap(20, 20, 3, 10);

        assertEquals(3, waterMap.lakes.size());
        assertEquals(10, waterMap.numberOfGrasses);
        assertFalse(waterMap.waters.isEmpty());
    }

    @Test
    public void testCanMoveTo() {
        WaterMap waterMap = new WaterMap(20, 20, 0, 0);

        assertTrue(waterMap.canMoveTo(new Vector2d(5, 5)));
        assertFalse(waterMap.canMoveTo(new Vector2d(-1, -1)));
        assertFalse(waterMap.canMoveTo(new Vector2d(21, 21)));
    }

    @Test
    public void testChangeSizeOfLakes() {
        WaterMap waterMap = new WaterMap(20, 20, 2, 0);
        int initialWaterTiles = waterMap.waters.size();

        waterMap.changeSizeOfLakes();

        assertNotEquals(initialWaterTiles, waterMap.waters.size());
    }


    @Test
    public void testAnimalPlacement() throws IncorrectPositionException {
        WaterMap waterMap = new WaterMap(20, 20, 1, 0);
        Genotype genotype = new Genotype(new ArrayList<>(Arrays.asList(2, 0, 0)));
        Animal animal = new Animal(new Vector2d(5, 5), 3, genotype);
        waterMap.place(animal);

        assertEquals(new Vector2d(5, 5), animal.getPosition());
        assertTrue(waterMap.objectAt(new Vector2d(5, 5)) instanceof Animal);
    }

    @Test
    public void testAnimalMovementDirection() throws IncorrectPositionException {
        WaterMap waterMap = new WaterMap(20, 20, 1, 0);
        Genotype genotype = new Genotype(new ArrayList<>(Arrays.asList(2, 0, 0)));
        Animal animal = new Animal(new Vector2d(5, 5), 3, genotype);
        waterMap.place(animal);

        waterMap.move(animal);
        Vector2d expectedPosition = new Vector2d(6, 5); // EAST moves +1 to x-coordinate

        assertEquals(expectedPosition, animal.getPosition());
        assertInstanceOf(Animal.class, waterMap.objectAt(expectedPosition));
        assertNull(waterMap.objectAt(new Vector2d(5, 5)));
    }

    @Test
    public void testAnimalAvoidsWater() throws IncorrectPositionException {
        WaterMap waterMap = new WaterMap(20, 20, 1, 0);
        Genotype genotype = new Genotype(new ArrayList<>(Arrays.asList(2, 0, 0)));
        Animal animal = new Animal(new Vector2d(5, 5), 3, genotype);
        waterMap.place(animal);

        for (Vector2d waterPosition : waterMap.waters.keySet()) {
            animal.move(waterPosition);
            assertNotEquals(waterPosition, animal.getPosition());
            assertFalse(waterMap.objectAt(waterPosition) instanceof Animal);
        }
    }

}
