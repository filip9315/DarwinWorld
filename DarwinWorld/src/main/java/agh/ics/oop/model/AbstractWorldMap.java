package agh.ics.oop.model;

import agh.ics.oop.IncorrectPositionException;
import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {

    int width;
    int height;
    int numberOfGrasses;
    int grassEnergy = 1;
    int procreationEnergy = 100;
    AnimalsHashMap animals = new AnimalsHashMap();
    Map<Vector2d, Grass> grasses = new HashMap<>();

    List<Animal> animalList = new ArrayList<>();
    List<MapChangeListener> mapChangeListeners= new ArrayList<>();
    UUID id;

    public int getGrassEnergy() {
        return grassEnergy;
    }

    public int getProcretionEnergy() {
        return procreationEnergy;
    }

    abstract public boolean canMoveTo(Vector2d position);

    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    public void place(Animal animal) throws IncorrectPositionException {
        Vector2d animalPosition = animal.getPosition();
        if (canMoveTo(animal.getPosition())) {
//            animals.put(animalPosition, animal);
            animals.addAnimal(animal);

            animalList.add(animal);
            mapChanged("Animal placed on " + animalPosition);
        }else{
            throw new IncorrectPositionException(animalPosition);
        }
    }

    public Vector2d newAnimalPosition(Animal animal) {
        Vector2d position = animal.getPosition();
        MapDirection newAnimalDirection = animal.getDirection().rotate(animal.getActiveGene());
        animal.setDirection(newAnimalDirection);
        Vector2d newPosition = animal.getPosition().add(animal.getDirection().toUnitVector());
        return newPosition;
    }

    public Vector2d normaliseNewPosition(Vector2d position) {
        return position;
    }

    public void killAnimal(Animal animal) {
        animals.removeAnimal(animal);
        animalList.remove(animal);
        mapChanged("Animal killed on " + animal.getPosition());
    }

    public void move(Animal animal) {
        Vector2d newPosition = newAnimalPosition(animal);
        newPosition = normaliseNewPosition(newPosition);

        if (this.canMoveTo(newPosition)) {
//            animals.remove(animal.getPosition());
            animals.removeAnimal(animal);
            animal.move(newPosition);
//            animals.put(animal.getPosition(), animal);
            animals.addAnimal(animal);
            mapChanged("Animal moved to " + animal.getPosition());
        }

    }

//    public List<WorldElement> getElements() {
//        return new ArrayList<>(animals.values());
//    }
    public ArrayList<WorldElement> getElements() {
        ArrayList<WorldElement> elements = new ArrayList<>();
        elements.addAll(animals.getAllAnimals());
        elements.addAll(grasses.values());

        return elements;
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

    public ArrayList<WorldElement> objectsAt(Vector2d position) {
        ArrayList<WorldElement> objects = new ArrayList<>();
        objects.add((WorldElement) animals.getAnimalsAtPosition(position));
        objects.add(grasses.get(position));
        return objects;
    }

    public WorldElement objectAt(Vector2d position) {

        if (!animals.getAnimalsAtPosition(position).isEmpty()) {
            return animals.getAnimalsAtPosition(position).getFirst();
        }
//        if (animals.get(position) != null) {
//            return animals.get(position);
//        }
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

    public void updateWorldMap(){
        animalsFeed();
        animalsProcreate();
        removeDeadAnimals();
    }

    public void animalsFeed () {

    }

    public void animalsProcreate() {

    }

    public void removeDeadAnimals() {

    }
}
