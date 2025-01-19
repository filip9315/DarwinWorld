package agh.ics.oop.model;

import java.util.UUID;

public class Globe extends AbstractWorldMap {

    public Globe(int width, int height, int numberOfGrasses, int grassGrowingSpeed, int energyUsedToProcreate,int energyToBeAbleToProcreate, int grassEnergy, int minMutations, int maxMutations, int genotypeLength, int initialEnergy) {
        this.width = width;
        this.height = height;
        this.id = UUID.randomUUID();
        this.numberOfGrasses = numberOfGrasses;
        growGrass(numberOfGrasses);
        this.grassGrowingSpeed = grassGrowingSpeed;
        this.energyUsedToProcreate = energyUsedToProcreate;
        this.energyToBeAbleToProcreate = energyToBeAbleToProcreate;
        this.grassEnergy = grassEnergy;
        this.minMutations = minMutations;
        this.maxMutations = maxMutations;
        this.genotypeLength = genotypeLength;
        this.initialEnergy = initialEnergy;
    }

    @Override
    public Vector2d normaliseNewPosition(Vector2d position) {
        if (position.getX() >= width) {
            return new Vector2d(position.getX() - width, position.getY());
        }
        if (position.getX() <= -1) {
            return new Vector2d(position.getX() + width, position.getY());
        }
        return position;
    }

    public boolean canMoveTo(Vector2d position) {
        int top = getCurrentBounds().upperRight().getY();
        int bottom = getCurrentBounds().lowerLeft().getY();

        return position.getY() <= top && position.getY() >= bottom;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
