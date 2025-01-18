package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable {

    List<Vector2d> positions = new ArrayList<>();
    List<MoveDirection> directions;
    int numberOfAnimals;
    int simulationLength;
    int genotypeLength;

    WorldMap map;
    Visitor visitor = new MapActionVisitor();

    public Simulation(int numberOfAnimals, WorldMap map, int initEnergy, int genotypeLength, int simulationLength) {
        this.numberOfAnimals = numberOfAnimals;
        this.map = map;
        this.simulationLength = simulationLength;
        this.genotypeLength = genotypeLength;

        generatePositions(numberOfAnimals);

        //TODO usunąć to (konstruktor animala się zmienił:
        List<Integer> genesList = List.of(1, 2, 3, 4, 5, 6, 7, 2, 0, 1);
        Genotype genotype = new Genotype(genesList);

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
