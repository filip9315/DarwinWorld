package agh.ics.oop.model;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
            case WATER -> "~";
            case STEPPE -> " ";
        };
    }

    public Node getShape(double width, double height) {
        Rectangle rectangle = new Rectangle(width, height);

        switch (this.tileType) {
            case WATER: rectangle.setFill(Color.BLUE); break;
            case STEPPE: rectangle.setFill(Color.GREEN); break;
        }

        return rectangle;
    }
}
