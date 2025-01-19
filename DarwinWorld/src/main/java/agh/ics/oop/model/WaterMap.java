package agh.ics.oop.model;

import java.util.*;

public class WaterMap extends AbstractWorldMap {

    List<Lake> lakes = new ArrayList<>();
    Map<Vector2d, Tile> waters = new HashMap<>();
    int numberOfLakes;

    public WaterMap(int width, int height, int numberOfLakes, int numberOfGrasses, int grassGrowingSpeed) {
        this.width = width;
        this.height = height;
        this.numberOfGrasses = numberOfGrasses;
        this.numberOfLakes = numberOfLakes;
        this.grassGrowingSpeed = grassGrowingSpeed;

        for (int i = 0; i < numberOfLakes; i++) {
            Vector2d center;
            if (Math.random() < 0.5) {
                center = new Vector2d((int) (Math.random()*width), (int) (Math.random()*((double) height /5)));
            } else {
                center = new Vector2d((int) (Math.random()*width), (height*4/5) + (int) (Math.random()*(height)/5));
            }
            int radius = (int) (Math.random()*5);
            Lake lake = new Lake(center, radius, this);
            lakes.add(lake);
            waters.putAll(lake.getWaters());
        }
//        placeGrass(numberOfGrasses);
        growGrass(numberOfGrasses);
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        if (!animals.getAnimalsAtPosition(position).isEmpty()) {
            return animals.getAnimalsAtPosition(position).getFirst();
        }
        if (grasses.get(position) != null) {
            return grasses.get(position);
        }
        if(waters.get(position) != null) {
            return waters.get(position);
        }
        return null;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(getCurrentBounds().lowerLeft()) && position.precedes(getCurrentBounds().upperRight())
                && !waters.containsKey(position);

    }

    public ArrayList<Vector2d> calculateObstaclesPositions() {
        ArrayList<Vector2d> obstaclePositions = new ArrayList<>();
        obstaclePositions.addAll(grasses.keySet());
        obstaclePositions.addAll(waters.keySet());
        return obstaclePositions;
    }

    public void changeSizeOfLakes(){
//        int flow = (int)(Math.random() * 3);
//        int flow = (int) ((Math.random() * 5 - 1) + 1);
        int flow = (Math.random() >= 0.5) ? 1 : -1;
        waters.clear();
        for (Lake lake : lakes) {
            int radius = lake.getRadius() + flow;
            if (radius < 2) {
                radius = 2;
            } else if (radius > 10) {
                radius = 10;
            }
            Lake newLake = new Lake(lake.getCenter(), radius, this);
            lakes.set(lakes.indexOf(lake), newLake);
            waters.putAll(newLake.getWaters());
        }

        for (Vector2d position : waters.keySet()) {
            ArrayList<Animal> animalsAtPosition = (ArrayList<Animal>) animals.getAnimalsAtPosition(position).clone();
            if (!animalsAtPosition.isEmpty()) {
                for (Animal animal : animalsAtPosition) {
                removeAnimal(animal);
            }
            }


            Grass grassAtPosiotion = grasses.get(position);

            if (grassAtPosiotion != null) {
                grasses.remove(position);
            }
        }
    }

    @Override
    public ArrayList<WorldElement> getElements() {
        ArrayList<WorldElement> elements = new ArrayList<>();
        elements.addAll(animals.getAllAnimalsAsWorldElements());
        elements.addAll(grasses.values());
        elements.addAll(waters.values());

        return elements;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
