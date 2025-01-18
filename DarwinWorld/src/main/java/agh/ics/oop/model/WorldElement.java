package agh.ics.oop.model;

import javafx.scene.Node;
import javafx.scene.shape.Circle;

public interface WorldElement {

    String toString();
    Vector2d getPosition();
    Node getShape(double width, double height);
}
