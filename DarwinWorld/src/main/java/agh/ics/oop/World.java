package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.List;

public class World {

    public static void main(String[] args) {
        WaterMap waterMap = new WaterMap(10, 10, 2, 3, 2, 2, 3, 4, 2, 3, 4, 4, 5);

        Simulation simulation = new Simulation(3, waterMap, 10, 5, 3, false);
    }

}
