package agh.ics.oop.model;
import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class GlobeTest {

    @Test
    void AnimalMovesCorrectly(){

        WorldMap map = new Globe(4,4, 1);

        Genotype genotype = new Genotype(new ArrayList<>(Arrays.asList(2, 0, 0)));

        Animal animal = new Animal(new Vector2d(0, 0), 3, genotype);

        map.move(animal);
        map.move(animal);
        System.out.println(animal.getPosition());
        Assertions.assertEquals(new Vector2d(2, 0), animal.getPosition());
    }

    @Test
    void AnimalCantGoOutsideMap(){

        WorldMap map = new Globe(4,4, 1);

        Genotype genotype = new Genotype(new ArrayList<>(Arrays.asList(2, 0, 0)));

        Animal animal = new Animal(new Vector2d(3, 4), 3, genotype);

        map.move(animal);
        map.move(animal);
        System.out.println(animal.getPosition());
    }

}
