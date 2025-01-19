package agh.ics.oop.model;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Animal implements WorldElement {

    MapDirection direction;
    private Vector2d position;
    int energy;
    int age;
    int numOfChildren;
    ArrayList<Animal> children;
    int numberOfEatenGrasses = 0;
    int numberOfDays = 0;
    int dayOfDeath;
    Genotype genotype;
    WorldMap map;

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

    public int getNumberOfDescendants() {
        int numOfDescendants = children.size();
        for (Animal child : children) {
            numOfDescendants += child.getNumberOfDescendants();
        }
        return numOfDescendants;
    }

    public int getAge() {
        return age;
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

    public void setNumberOfEatenGrasses(int numOfEatenGrasses) {
        this.numberOfEatenGrasses = numOfEatenGrasses;
    }

    public void setNumberOfChildren(int numOfChildren) {
        this.numOfChildren = numOfChildren;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public Animal(Vector2d position, int energy, Genotype genotype, WorldMap map) {
        this.direction = MapDirection.NORTH;
        this.position = position;
        this.energy = energy;
        this.genotype = genotype;
        this.map = map;
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
        this.genotype = new Genotype(genes);
        this.map = map;
    }

    public void consumeGrass() {
        this.energy += map.getGrassEnergy();
        numberOfEatenGrasses++;
    }

    public boolean canProcreate(){
        return this.energy >= map.getProcretionEnergy();
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
        setDayOfDeath(this.getAge());
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public Animal procreate(Animal animal2) {
        this.setEnergy(this.getEnergy() - map.getProcretionEnergy());
        animal2.setEnergy(animal2.getEnergy() - map.getProcretionEnergy());
        this.setNumberOfChildren(this.getNumOfChildren() + 1);
        animal2.setNumberOfChildren(this.getNumOfChildren() + 1);

        Animal descendant = new Animal(this.getPosition(), 2 * map.getProcretionEnergy(), genotype, map);
        this.children.add(descendant);
        animal2.children.add(descendant);
        return descendant;
    }
    public Node getShape(double width, double height) {
        double radius = Math.min(width, height)/2;
        Circle circle = new Circle(radius);
        circle.setFill(Color.BROWN);
        return circle;
    }


}
