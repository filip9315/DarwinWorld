package agh.ics.oop.model;

import java.util.UUID;

public class Globe extends AbstractWorldMap {

    public Globe(int width, int height, int numberOfGrasses) {
        this.width = width;
        this.height = height;
        this.id = UUID.randomUUID();
        this.numberOfGrasses = numberOfGrasses;
        placeGrass(numberOfGrasses);
    }

    public boolean canMoveTo(Vector2d position) {
        int top = getCurrentBounds().upperRight().getY();
        int bottom = getCurrentBounds().lowerLeft().getY();

        return position.getY() <= top && position.getY() >= bottom;
    }
}
