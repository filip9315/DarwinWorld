package agh.ics.oop.model;

public class Animal implements WorldElement {

    MapDirection direction;
    private Vector2d position;
    int energy;

    public Vector2d getPosition() {
        return position;
    }

    public MapDirection getDirection() {
        return direction;
    }

    public void setDirection(MapDirection direction) {
        this.direction = direction;
    }

    public Animal(Vector2d position, int energy) {
        this.direction = MapDirection.NORTH;
        this.position = position;
    }

    public String toString() {
        return "Z";
    }

    public int getActiveGenome() {
        return 0;
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public void move(Vector2d position) {
        this.position = position;
    }


}
