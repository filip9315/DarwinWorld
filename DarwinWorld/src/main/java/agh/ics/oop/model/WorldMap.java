package agh.ics.oop.model;

import agh.ics.oop.IncorrectPositionException;

import java.util.List;
import java.util.UUID;

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2d and MoveDirection classes are defined.
 *
 * @author apohllo, idzik
 */
public interface WorldMap extends MoveValidator {

    /**
     * Place an animal on the map.
     *
     * @param animal The animal to place on the map.
     * @return True if the animal was placed. The animal cannot be placed if the move is not valid.
     */
    void place(Animal animal) throws IncorrectPositionException;

    /**
     * Moves an animal (if it is present on the map) according to specified direction.
     * If the move is not possible, this method has no effect.
     */
    void move(Animal animal);

    /**
     * Return true if given position on the map is occupied. Should not be
     * confused with canMove since there might be empty positions where the animal
     * cannot move.
     *
     * @param position Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(Vector2d position);

    /**
     * Return an animal at a given position.
     *
     * @param position The position of the animal.
     * @return animal or null if the position is not occupied.
     */
    WorldElement objectAt(Vector2d position);

    List<WorldElement> getElements();
    List<Animal> getAnimals();
    Boundary getCurrentBounds();
    void registerMapChangeListener(MapChangeListener mapChangeListener);
    void unregisterMapChangeListener(MapChangeListener mapChangeListener);
    UUID getId();
    List<Vector2d> getLivableTiles();
    void accept(Visitor visitor);
    int getGrassEnergy();
    void updateWorldMap();
    int getMapWidth();
    int getMapHeight();
    SimulationStatistics getStatistics();
    int getDay();
    void updateDay();
    int getEnergyUsedToProcreate();
    List<Animal> getDeadAnimals();
    int getMutationType();
    int getEnergyToBeAbleToProcreate();
}