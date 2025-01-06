package agh.ics.oop.model;

public class Tile implements WorldElement {
    int numberOfGrasses = 0;
    TileType tileType;
    Vector2d position;

    public Tile(TileType tileType, Vector2d position) {
        this.tileType = tileType;
        this.position = position;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public String toString(){
        return switch (this.tileType) {
            case JUNGLE -> "J";
            case WATER -> "~";
            case STEPPE -> " ";
        };
    }
}
