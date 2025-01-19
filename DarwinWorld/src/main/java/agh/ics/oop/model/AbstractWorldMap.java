package agh.ics.oop.model;

import agh.ics.oop.IncorrectPositionException;
import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {

    int width;
    int height;
    int day = 0;
    int numberOfGrasses;
    int grassEnergy;
    int energyUsedToProcreate;
    int energyToBeAbleToProcreate;
    int grassGrowingSpeed;
    int minMutations;
    int maxMutations;
    int genotypeLength;
    int initialEnergy;
    int mutationType;
    AnimalsHashMap animals = new AnimalsHashMap();
    Map<Vector2d, Grass> grasses = new HashMap<>();
    ArrayList<Animal> deadAnimals = new ArrayList<>();
    SimulationStatistics statistics = new SimulationStatistics(this);

    List<MapChangeListener> mapChangeListeners= new ArrayList<>();
    UUID id;

    Map<Vector2d, Boolean> emptyTiles = new HashMap<>();

    public int getEnergyToBeAbleToProcreate() {
        return energyToBeAbleToProcreate;
    }

    public int getDay() {
        return day;
    }

    public int getMutationType() {
        return mutationType;
    }

    public void updateDay() {
        this.day++;
    }

    public List<Animal> getDeadAnimals() {
        return deadAnimals;
    }


    public int getGrassEnergy() {
        return grassEnergy;
    }

    public int getEnergyUsedToProcreate() {
        return energyUsedToProcreate;
    }

    abstract public boolean canMoveTo(Vector2d position);

    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    public void place(Animal animal) throws IncorrectPositionException {
        Vector2d animalPosition = animal.getPosition();
        if (canMoveTo(animal.getPosition())) {
            animals.addAnimal(animal);
            mapChanged("Animal placed on " + animalPosition);
        }else{
            throw new IncorrectPositionException(animalPosition);
        }
    }

    public Vector2d newAnimalPosition(Animal animal) {
        MapDirection newAnimalDirection = animal.getDirection().rotate(animal.useActiveGene());
        animal.setDirection(newAnimalDirection);
        Vector2d newPosition = animal.getPosition().add(animal.getDirection().toUnitVector());
        return newPosition;
    }

    public Vector2d normaliseNewPosition(Vector2d position) {
        return position;
    }

    public void move(Animal animal) {
        Vector2d newPosition = newAnimalPosition(animal);
        newPosition = normaliseNewPosition(newPosition);

        if (this.canMoveTo(newPosition)) {
            animals.removeAnimal(animal);
            animal.move(newPosition);
            animal.setEnergy(animal.getEnergy() - 1);
            animals.addAnimal(animal);
            mapChanged("Animal moved to " + animal.getPosition());
        }

    }

    public ArrayList<WorldElement> getElements() {
        ArrayList<WorldElement> elements = new ArrayList<>();
        elements.addAll(animals.getAllAnimalsAsWorldElements());
        elements.addAll(grasses.values());

        return elements;
    }

    public String toString(){
        MapVisualizer mapVisualizer = new MapVisualizer(this);
        return mapVisualizer.draw(getCurrentBounds().lowerLeft(), getCurrentBounds().upperRight());
    }

    public Boundary getCurrentBounds(){
        return new Boundary(new Vector2d(0, 0), new Vector2d(width-1, height-1));
    }

    public List<Animal> getAnimals() {
        return animals.getAllAnimals();
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

    void removeGrass(Vector2d grassPosition){
        grasses.remove(grassPosition);
    }

    public void updateWorldMap(){
        animalsConsumeGrass();
        animalsProcreate();
        removeDeadAnimals();
        updateAnimalsAge();
        growGrass(grassGrowingSpeed);
    }

    public void updateAnimalsAge(){
        ArrayList<Animal> allAnimals = animals.getAllAnimals();
        for (Animal animal : allAnimals) {
            animal.setAge(animal.getAge() + 1);
        }
    }

    public void animalsConsumeGrass () {
        Set<Vector2d> grassesPositions = grasses.keySet();
        List<Vector2d> grassesToRemove = new ArrayList<>();
        for (Vector2d grassPosition : grassesPositions) {
            List<Animal> sortedAnimals = animals.sortAnimalsAtGivenPosition(grassPosition);
            if (!sortedAnimals.isEmpty()) {
                sortedAnimals.getFirst().consumeGrass();
                grassesToRemove.add(grassPosition);
            }
        }
        for (Vector2d grassPosition : grassesToRemove) {
            removeGrass(grassPosition);
        }
    }

    public void animalsProcreate() {
        List<Animal> animalsToAdd = new ArrayList<>();

        for (ArrayList<Animal> animalsList : animals.values()) {
            if (animalsList.size() > 1) {
                List<Animal> sortedAnimals = animals.sortAnimalsAtGivenPosition(animalsList.getFirst().getPosition());
                Animal animal1 = sortedAnimals.getFirst();
                Animal animal2 = sortedAnimals.get(1);
                if (animal1.canProcreate() && animal2.canProcreate()) {
                    Animal newAnimal = animal1.procreate(animal2);
                    animalsToAdd.add(newAnimal);
                }
            }
        }
        if (!animalsToAdd.isEmpty()) {
            for (Animal animal : animalsToAdd) {
            animals.addAnimal(animal);
            }
        }
    }

    public void removeAnimal(Animal animal) {
        animals.removeAnimal(animal);
        animal.die();
        deadAnimals.add(animal);
    }

    public void removeDeadAnimals() {
        ArrayList<Animal> allAnimals = animals.getAllAnimals();
        ArrayList<Animal> deadAnimals = new ArrayList<>();
        for (Animal animal : allAnimals) {
            if (animal.getEnergy() <= 0) {
                deadAnimals.add(animal);
            }
        }
        for (Animal deadAnimal : deadAnimals) {
            removeAnimal(deadAnimal);
        }

    }

    public ArrayList<Vector2d> calculateObstaclesPositions() {
        ArrayList<Vector2d> obstaclePositions = new ArrayList<>();
        obstaclePositions.addAll(grasses.keySet());
        return obstaclePositions;
    }

    public void calculateEmptyTiles() {
        emptyTiles.clear();
        ArrayList<Vector2d> obstaclesPositions = calculateObstaclesPositions();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Vector2d position = new Vector2d(i, j);
                if (!obstaclesPositions.contains(position)) {
                    emptyTiles.put(position, true);
                }

            }
        }
    }


    public void growGrass(int n){
        int grassToGenerate = n;
        calculateEmptyTiles();
        while (grassToGenerate > 0) {
            if (emptyTiles.size() > grassToGenerate) {
                RandomPositionsGenerator randomPositionsGenerator = new RandomPositionsGenerator(width, height, n);
                for(Vector2d grassPosition : randomPositionsGenerator) {
                    if (emptyTiles.containsKey(grassPosition)) {
                        grasses.put(grassPosition, new Grass(grassPosition));
                        statistics.updateGrassCount(grassPosition);
                        emptyTiles.remove(grassPosition);
                        grassToGenerate--;
                    }

                }
            }
            else if (emptyTiles.size() < grassToGenerate) {
                Set<Vector2d> positions = emptyTiles.keySet();
                for (Vector2d grassPosition : positions) {
                    grasses.put(grassPosition, new Grass(grassPosition));
                    statistics.updateGrassCount(grassPosition);
                }
                emptyTiles.clear();
                break;
            }
        }
    }

    public int getMapWidth() {
        return width;
    }
    public int getMapHeight() {
        return height;
    }
    public SimulationStatistics getStatistics() {return statistics;}
}
