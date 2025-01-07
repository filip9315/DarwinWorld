package agh.ics.oop.model;

public class Animal implements WorldElement {

    MapDirection direction;
    private Vector2d position;

    public Animal() {
        this(new Vector2d(2,2));
    }

    public Vector2d getPosition() {
        return position;
    }

    public MapDirection getDirection() {
        return direction;
    }

    public void setDirection(MapDirection direction) {
        this.direction = direction;
    }

    public Animal(Vector2d position) {
        this.direction = MapDirection.NORTH;
        this.position = position;
    }

    public String toString() {
//        return switch (direction) {
//            case EAST -> "E";
//            case WEST -> "W";
//            case NORTH -> "N";
//            case SOUTH -> "S";
//        };
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
