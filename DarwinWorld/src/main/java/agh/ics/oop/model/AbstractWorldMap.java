package agh.ics.oop.model;

import agh.ics.oop.IncorrectPositionException;
import agh.ics.oop.model.MapVisualizer;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {

    int width;
    int height;

    Map<Vector2d, Animal> animals = new HashMap<>();
    Map<Vector2d, Grass> grasses = new HashMap<>();

    Map<Vector2d, Tile> jungles = new HashMap<>();

    List<Animal> animalList = new ArrayList<>();
    List<MapChangeListener> mapChangeListeners= new ArrayList<>();
    UUID id;

    abstract public boolean canMoveTo(Vector2d position);

    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    public void place(Animal animal) throws IncorrectPositionException {
        if (canMoveTo(animal.getPosition())) {
            animals.put(animal.getPosition(), animal);
            animalList.add(animal);
            mapChanged("Animal placed on " + animal.getPosition());
        }else{
            throw new IncorrectPositionException(animal.getPosition());
        }
    }

    public void move(Animal animal) {

        Vector2d position = animal.getPosition();

//        switch (genomeValue) {
//            case 0: animal.direction = animal.direction; break;
//            case 1: animal.direction = animal.direction.next(); break;
//            case FORWARD: {
//                Vector2d tmp = animal.getPosition().add(animal.direction.toUnitVector());
//                if (this.canMoveTo(tmp)) {
//                    position = tmp;
//                }
//            } break;
//            case BACKWARD: {
//                Vector2d tmp = animal.getPosition().subtract(animal.direction.toUnitVector());
//                if (this.canMoveTo(tmp)) {
//                    position = tmp;
//                }
//            } break;
//        };

        MapDirection newAnimalDirection = animal.getDirection().rotate(animal.getActiveGenome());
        animal.setDirection(newAnimalDirection);
        Vector2d tmp = animal.getPosition().add(animal.getDirection().toUnitVector());


        if (this.canMoveTo(tmp)) {
            animals.remove(animal.getPosition());
            animal.move(tmp);
            animals.put(animal.getPosition(), animal);
            mapChanged("Animal moved to " + animal.getPosition());
        }

    }

    public List<WorldElement> getElements() {
        return new ArrayList<>(animals.values());
    }

    public String toString(){
        MapVisualizer mapVisualizer = new MapVisualizer(this);
        return mapVisualizer.draw(getCurrentBounds().lowerLeft(), getCurrentBounds().upperRight());
    }

    public Boundary getCurrentBounds(){
        return new Boundary(new Vector2d(0, 0), new Vector2d(width, height));
    }

    public List<Animal> getAnimals() {
        return animalList;
    }

    public void mapChanged(String message){
        for (MapChangeListener mapChangeListener : mapChangeListeners){
            mapChangeListener.mapChanged(this, message);
        }
    }

    public WorldElement objectAt(Vector2d position) {
        if (animals.get(position) != null) {
            return animals.get(position);
        }
        if (grasses.get(position) != null) {
            return grasses.get(position);
        }
        if(jungles.get(position) != null) {
            return jungles.get(position);
        }
        return null;
    }

    public void registerMapChangeListener(MapChangeListener mapChangeListener){
        mapChangeListeners.add(mapChangeListener);
    }

    public void unregisterMapChangeListener(MapChangeListener mapChangeListener){
        mapChangeListeners.remove(mapChangeListener);
    }

    public UUID getId() {
        return id;
    }

    void createJungle(){
        int center = height / 2;
        int jungleHeight = height / 5;

        for (int i = 0; i <= width; i++) {
            for (int j = center-jungleHeight; j <= center+jungleHeight; j++) {
                if (Math.random() > 0.3) {
                    jungles.put(new Vector2d(i, j), new Tile(TileType.JUNGLE, new Vector2d(i, j)));
                }
            }

        }
    }

    public Map<Vector2d, Tile> getJungles() {
        return jungles;
    }
}
