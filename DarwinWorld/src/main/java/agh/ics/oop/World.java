package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.List;

public class World {

    public static void main(String[] args) {
        Globe globe = new Globe(10, 10, 3);
        WaterMap waterMap = new WaterMap(10, 10, 2, 3);



        Simulation simulation = new Simulation(3, waterMap, 10, 5, 3);


        SimulationEngine simulationEngine = new SimulationEngine();
        simulationEngine.runAsyncInThreadPool(simulation);
    }

}
