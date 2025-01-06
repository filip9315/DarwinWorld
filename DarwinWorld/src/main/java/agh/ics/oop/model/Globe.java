package agh.ics.oop.model;

import java.util.UUID;

public class Globe extends AbstractWorldMap {

    int width;
    int height;

    public Globe(int width, int height) {
        this.width = width;
        this.height = height;
        this.id = UUID.randomUUID();
    }

    public WorldElement objectAt(Vector2d position) {
        return animals.get(position);
    }

    public boolean canMoveTo(Vector2d position) {
        return position.follows(getCurrentBounds().lowerLeft()) && position.precedes(getCurrentBounds().upperRight()) && !isOccupied(position);
    }

    public Boundary getCurrentBounds(){
        return new Boundary(new Vector2d(0, 0), new Vector2d(width, height));
    }
}
