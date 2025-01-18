package agh.ics.oop.model;

import java.util.*;
import java.util.stream.Collectors;

public class AnimalsHashMap {
    private final HashMap<Vector2d, ArrayList<Animal>> animals = new HashMap<>();

    public ArrayList<Animal> getAnimalsAtPosition(Vector2d position) {
        return animals.getOrDefault(position, new ArrayList<Animal>());
    }

    public ArrayList<Vector2d> getOccupiedPositions() {
        return (ArrayList<Vector2d>) animals.keySet().stream().filter(position -> !animals.get(position).isEmpty()).collect(Collectors.toList());
    }

    public ArrayList<Animal> getAllAnimals() {
        return animals.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<WorldElement> getAllAnimalsAsWorldElements() {
        return animals.values().stream()
                .flatMap(List::stream)
                .map(animal -> (WorldElement) animal)
                .collect(Collectors.toCollection(ArrayList::new));
    }


    public void addAnimal(Animal animal) {
        Vector2d position = animal.getPosition();
        if (!animals.containsKey(position)) {
            animals.put(position, new ArrayList<Animal>());
        }
        animals.get(position).add(animal);
    }

    public void removeAnimal(Animal animal) {
        Vector2d position = animal.getPosition();
        animals.getOrDefault(position, new ArrayList<Animal>()).remove(animal);
    }

    public ArrayList<Animal> sortAnimalsAtGivenPosition(Vector2d position) {
        ArrayList<Animal> sortedAnimals = getAnimalsAtPosition(position);
        if (sortedAnimals.size() < 2) {
            return sortedAnimals;
        }
        sortedAnimals.stream().sorted(Comparator.comparingInt(Animal::getEnergy)).collect(Collectors.toCollection(ArrayList::new));
        if (sortedAnimals.get(0).getEnergy() != sortedAnimals.get(1).getEnergy()) {
            return sortedAnimals;
        }
        sortedAnimals.stream().sorted(Comparator.comparingInt(Animal::getAge)).collect(Collectors.toCollection(ArrayList::new));
        if (sortedAnimals.get(0).getAge() != sortedAnimals.get(1).getAge()) {
            return sortedAnimals;
        }
        sortedAnimals.stream().sorted(Comparator.comparingInt(Animal::getNumOfChildren)).collect(Collectors.toCollection(ArrayList::new));
        if (sortedAnimals.get(0).getNumOfChildren() != sortedAnimals.get(1).getNumOfChildren()) {
            return sortedAnimals;
        }
        if (Math.random() < 0.5) {
            Collections.swap(sortedAnimals, 1, 0);
        }
        return sortedAnimals;
    }

    public ArrayList<ArrayList<Animal>> values() {
        return new ArrayList<>(animals.values());
    }
}
