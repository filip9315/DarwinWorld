package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        return (ArrayList<Animal>) animals.values().stream().flatMap(List::stream).collect(Collectors.toList());
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
}
