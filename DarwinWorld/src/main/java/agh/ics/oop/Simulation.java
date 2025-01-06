package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldMap;

import java.util.List;

public class Simulation implements Runnable {

    List<Vector2d> positions;
    List<MoveDirection> directions;

    WorldMap map;

    public Simulation(List<Vector2d> positions, List<MoveDirection> directions, WorldMap map) {
        this.positions = positions;
        this.directions = directions;
        this.map = map;
        for (Vector2d position : positions) {
            Animal tmp = new Animal(position);
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

    public void run(){
        int i = 0;
        int max = map.getAnimals().size();
        System.out.println(map);

        for (MoveDirection direction : directions) {
            if (i == max) i = 0;
            map.move(map.getAnimals().get(i), direction);
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
