package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable {

    List<Vector2d> positions = new ArrayList<>();
    List<MoveDirection> directions;
    int numberOfAnimals;
    int simulationLength;
    Genotype genotype;

    WorldMap map;
    Visitor visitor = new MapActionVisitor();

    public Simulation(int numberOfAnimals, WorldMap map, int initEnergy, Genotype genotype, int simulationLength) {
        this.numberOfAnimals = numberOfAnimals;
        this.map = map;
        this.simulationLength = simulationLength;
        this.genotype = genotype;

        generatePositions(numberOfAnimals);

        for (Vector2d position : positions) {
            Animal tmp = new Animal(position, initEnergy, genotype);
            try {
                map.place(tmp);
            } catch(IncorrectPositionException e) {
                e.printStackTrace();
            }
        }
    }

    public Animal getAnimal(int i) {
        return map.getAnimals().get(i);
    }

    public void generatePositions(int n) {
        for (int i = 0; i < n; i++) {
            int size = map.getLivableTiles().size();
            Vector2d position = map.getLivableTiles().get((int) (Math.random() * size));
            positions.add(position);
        }
    }

    public void run(){
        System.out.println(map);

        for (int i=0; i < simulationLength; i++) {
            for (Animal animal : map.getAnimals()) {
                map.move(animal);
            }
            System.out.println(map);

            map.accept(visitor);
        }

    }

}
