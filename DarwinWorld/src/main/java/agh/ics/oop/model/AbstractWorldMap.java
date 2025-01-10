package agh.ics.oop.model;

import agh.ics.oop.IncorrectPositionException;
import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {

    int width;
    int height;
    int numberOfGrasses;

    Map<Vector2d, Animal> animals = new HashMap<>();
    Map<Vector2d, Grass> grasses = new HashMap<>();

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

        MapDirection newAnimalDirection = animal.getDirection().rotate(animal.getActiveGene());
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

    public List<Vector2d> getLivableTiles(){

        List<Vector2d> livableTiles = new ArrayList<>();

        for (int i = 0; i <= this.getCurrentBounds().upperRight().getX(); i++) {
            for (int j = 0; j <= this.getCurrentBounds().upperRight().getY(); j++) {
                if(objectAt(new Vector2d(i, j)) == null){
                    livableTiles.add(new Vector2d(i, j));
                }
            }
        }
        return livableTiles;
    }

    void placeGrass(int n){
        RandomPositionsGenerator randomPositionsGenerator = new RandomPositionsGenerator(width, height, n);
        for(Vector2d grassPosition : randomPositionsGenerator) {
            grasses.put(grassPosition, new Grass(grassPosition));
        }
    }
}
