package agh.ics.oop.model;

import agh.ics.oop.World;
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
    Genotype genotype;
    WorldMap map;

    public Vector2d getPosition() {
        return position;
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

    public int getActiveGene() {
        return genotype.useGenotype();
    }

    public int getEnergy() {
        return energy;
    }

    public int getAge() {
        return age;
    }

    public int getNumOfChildren() {
        return numOfChildren;
    }

    public Animal procreate(Animal animal) {
        if (this.canProcreate() && animal.canProcreate()) {

        }
        return this;
    }
    public Node getShape(double width, double height) {
        double radius = Math.min(width, height)/2;
        Circle circle = new Circle(radius);
        circle.setFill(Color.BROWN);
        return circle;
    }


}
