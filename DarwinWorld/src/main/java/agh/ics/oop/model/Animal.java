package agh.ics.oop.model;

public class Animal implements WorldElement {

    MapDirection direction;
    private Vector2d position;
    int energy;
    Genotype genotype;

    public Vector2d getPosition() {
        return position;
    }

    public MapDirection getDirection() {
        return direction;
    }

    public void setDirection(MapDirection direction) {
        this.direction = direction;
    }

    public Animal(Vector2d position, int energy, Genotype genotype) {
        this.direction = MapDirection.NORTH;
        this.position = position;
        this.energy = energy;
        this.genotype = genotype;
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

}
