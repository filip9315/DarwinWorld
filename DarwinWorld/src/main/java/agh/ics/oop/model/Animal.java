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

    public Animal(Vector2d position) {
        this.direction = MapDirection.NORTH;
        this.position = position;
    }

    public String toString() {
        return switch (direction) {
            case EAST -> "E";
            case WEST -> "W";
            case NORTH -> "N";
            case SOUTH -> "S";
        };
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public void move(MoveDirection direction, WorldMap map) {
        switch (direction) {
            case LEFT: this.direction = this.direction.previous(); break;
            case RIGHT: this.direction = this.direction.next(); break;
            case FORWARD: {
                Vector2d tmp = this.position.add(this.direction.toUnitVector());
                if (map.canMoveTo(tmp)) {
                    this.position = tmp;
                }
            } break;
            case BACKWARD: {
                Vector2d tmp = this.position.subtract(this.direction.toUnitVector());
                if (map.canMoveTo(tmp)) {
                    this.position = tmp;
                }
            } break;
        }
    }


}
