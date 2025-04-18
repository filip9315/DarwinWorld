package agh.ics.oop;

import agh.ics.oop.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable {

    List<Vector2d> positions = new ArrayList<>();
    List<MoveDirection> directions;
    int numberOfAnimals;
    int simulationLength;
    int genotypeLength;
    boolean saveToCSV;
    String nameFile;

    WorldMap map;
    Visitor visitor = new MapActionVisitor();


    public void setFileName(String fileName) {
        this.nameFile = fileName;
    }

    private volatile boolean isPaused = false;

    public synchronized void pause() {
        if (!isPaused) {
            isPaused = true;
        } else {
            isPaused = false;
            notify();
        }
    }

    public boolean isPaused() {
        return isPaused;
    }

    public int getDay() {
        return map.getDay();
    }


    public Simulation(int numberOfAnimals, WorldMap map, int initEnergy, int genotypeLength, int simulationLength, boolean saveToCSV) {
        this.numberOfAnimals = numberOfAnimals;
        this.map = map;
        this.simulationLength = simulationLength;
        this.genotypeLength = genotypeLength;
        this.saveToCSV = saveToCSV;
        generatePositions(numberOfAnimals);

        for (Vector2d position : positions) {
            Animal tmp = new Animal(position, initEnergy, genotypeLength, map);
            try {
                map.place(tmp);
            } catch(IncorrectPositionException e) {
                e.printStackTrace();
            }
        }
    }

    public WorldMap getMap() {return map;}

    public void generatePositions(int n) {
        for (int i = 0; i < n; i++) {
            int size = map.getLivableTiles().size();
            Vector2d position = map.getLivableTiles().get((int) (Math.random() * size));
            positions.add(position);
        }
    }

    public void run(){

        for (int i=0; i < simulationLength; i++) {

            synchronized (this) {
                while (isPaused) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            List<Animal> allAnimals;
            allAnimals = map.getAnimals();
            allAnimals.addAll(map.getDeadAnimals());
            for (Animal animal : map.getAnimals()) {
                map.move(animal);
            }
            for(Animal animal : allAnimals) {
                animal.getStatistics().updateStatistics();
            }

            map.accept(visitor);
            map.updateWorldMap();
            map.getStatistics().updateStatistics();
            if(saveToCSV) {
                try {
                    map.getStatistics().save(nameFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            map.updateDay();
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
