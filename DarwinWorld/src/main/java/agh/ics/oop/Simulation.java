package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable {

    List<Vector2d> positions = new ArrayList<>();
    List<MoveDirection> directions;
    int numberOfAnimals;

    WorldMap map;

    public Simulation(int numberOfAnimals, List<MoveDirection> directions, WorldMap map, int initEnergy, int genomeLength) {
        this.numberOfAnimals = numberOfAnimals;
        this.directions = directions;
        this.map = map;

        generatePositions(numberOfAnimals);

        for (Vector2d position : positions) {
            Animal tmp = new Animal(position, initEnergy);
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
        int i = 0;
        int max = map.getAnimals().size();
        System.out.println(map);

        for (MoveDirection direction : directions) {
            if (i == max) i = 0;
            map.move(map.getAnimals().get(i));
            i++;
            System.out.println(map);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
