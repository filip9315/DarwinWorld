package agh.ics.oop.model;

import agh.ics.oop.IncorrectPositionException;
import agh.ics.oop.model.MapVisualizer;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {

    Map<Vector2d, Animal> animals = new HashMap<>();
    List<Animal> animalList = new ArrayList<>();
    List<MapChangeListener> mapChangeListeners= new ArrayList<>();
    UUID id;

    abstract public WorldElement objectAt(Vector2d position);
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

    public void move(Animal animal, MoveDirection direction) {
        animals.remove(animal.getPosition());
        animal.move(direction, this);
        animals.put(animal.getPosition(), animal);
        mapChanged("Animal moved to " + animal.getPosition());
    }

    public List<WorldElement> getElements() {
        return new ArrayList<>(animals.values());
    }

    public String toString(){
        MapVisualizer mapVisualizer = new MapVisualizer(this);
        return mapVisualizer.draw(getCurrentBounds().lowerLeft(), getCurrentBounds().upperRight());
    }

    public abstract Boundary getCurrentBounds();

    public List<Animal> getAnimals() {
        return animalList;
    }

    public void mapChanged(String message){
        for (MapChangeListener mapChangeListener : mapChangeListeners){
            mapChangeListener.mapChanged(this, message);
        }
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
}
