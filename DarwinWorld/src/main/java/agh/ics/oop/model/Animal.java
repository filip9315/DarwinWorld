package agh.ics.oop.model;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.*;

public class Animal implements WorldElement {

    int ID;
    static int ID_count = 0;
    MapDirection direction;
    private Vector2d position;
    int energy;
    int age = 0;
    ArrayList<Animal> children = new ArrayList<>();
    int numberOfEatenGrasses = 0;
    int dayOfDeath = -1;
    Genotype genotype;
    WorldMap map;
    AnimalStatistics animalStatistics= new AnimalStatistics(this);

    public Genotype getGenotype() {
        return genotype;
    }

    public int getActiveGene() {
        return genotype.getActiveGene();
    }

    public Vector2d getPosition() {
        return position;
    }

    public int getEnergy() {
        return energy;
    }

    public int getNumberOfEatenGrasses() {
        return numberOfEatenGrasses;
    }

    public int getNumOfChildren() {
        return children.size();
    }

//    public int getNumberOfDescendants() {
//        Set<Integer> visited = new HashSet<>();
//        int numOfDescendants = 0;
//        for (Animal animal : children) {
//            if (!visited.contains(animal.ID)) {
//                visited.add(animal.ID);
//                numOfDescendants++;
//            }
//        }
//        for (Animal child : children) {
//            numOfDescendants += child.getNumberOfDescendants(visited, numOfDescendants);
//        }
//        return numOfDescendants;
//    }
//
//    public int getNumberOfDescendants(Set<Integer> visited, int numOfDescendants) {
//        for (Animal child : children) {
//            if (!visited.contains(child.ID)) {
//                visited.add(child.ID);
//                numOfDescendants++;
//                numOfDescendants += child.getNumberOfDescendants();
//            }
//        }
//        return numOfDescendants;
//    }
    public int getNumberOfDescendants() {
        Set<Integer> visited = new HashSet<>();
        return getNumberOfDescendants(visited);
    }

    private int getNumberOfDescendants(Set<Integer> visited) {
        int numOfDescendants = 0;

        for (Animal child : children) {
            if (!visited.contains(child.ID)) {
                visited.add(child.ID);
                numOfDescendants++;
                numOfDescendants += child.getNumberOfDescendants(visited);
            }
        }
        return numOfDescendants;
    }

    public int getAge() {
        return age;
    }

    public int getDayOfDeath() {
        return dayOfDeath;
    }

    public boolean isAlive() {
        return (dayOfDeath == -1);
    }

    public void setAge(int age) {
        this.age = age;
    }

    public MapDirection getDirection() {
        return direction;
    }

    public void setDirection(MapDirection direction) {
        this.direction = direction;
    }

    public Animal(Vector2d position, int energy, Genotype genotype, WorldMap map) {
        this.direction = MapDirection.NORTH;
        this.position = position;
        this.energy = energy;
        this.genotype = genotype;
        this.map = map;
        this.ID = ID_count++;
    }

    public Animal(Vector2d position, int energy, int genotypeLength, WorldMap map) {
        List<Integer> genes = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < genotypeLength; i++) {
            genes.add(r.nextInt(7 + 1));
        }

        this.direction = MapDirection.NORTH;
        this.position = position;
        this.energy = energy;
        this.map = map;
        this.ID = ID_count++;

        if(map.getMutationType() == 0){
            this.genotype = new Genotype(genes);
        } else {
            this.genotype = new GenotypeSlightCorrection(genes);
        }
    }

    public void consumeGrass() {
        this.energy += map.getGrassEnergy();
        numberOfEatenGrasses++;
    }

    public boolean canProcreate(){
        return this.energy >= map.getEnergyToBeAbleToProcreate();
    }

    public String toString() {
        return "Z";
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public void move(Vector2d position) {
        this.position = position;
    }

    public int useActiveGene() {
        return genotype.useGenotype();
    }

    public void setDayOfDeath(int dayOfDeath) {
        this.dayOfDeath = dayOfDeath;
    }

    public void die() {
        setDayOfDeath(map.getDay());
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public Animal procreate(Animal animal2) {
        this.setEnergy(this.getEnergy() - map.getEnergyUsedToProcreate());
        animal2.setEnergy(animal2.getEnergy() - map.getEnergyUsedToProcreate());

        Animal descendant = new Animal(this.getPosition(), 2 * map.getEnergyUsedToProcreate(), genotype, map);
        this.children.add(descendant);
        animal2.children.add(descendant);
        return descendant;
    }
    public Node getShape(double width, double height) {
        double radius = Math.min(width, height)/2;
        Circle circle = new Circle(radius);
        circle.setFill(Color.hsb(27, 0.76, (double) Math.min((double) map.getEnergyUsedToProcreate()/energy, 1.0)));
        return circle;
    }
    public AnimalStatistics getStatistics() {
        return animalStatistics;
    }

}
