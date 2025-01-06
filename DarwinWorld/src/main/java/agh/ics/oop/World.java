package agh.ics.oop;

import agh.ics.oop.model.Globe;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WaterMap;

import java.util.List;

public class World {

    public static void main(String[] args){
        Globe globe = new Globe(10, 10);
        WaterMap waterMap = new WaterMap(10, 10, 2);

        List<Vector2d> positions = List.of(new Vector2d(3,3), new Vector2d(3,4));
        List<MoveDirection> directions = OptionsParser.parse(args);


        Simulation simulation = new Simulation(positions, directions, waterMap);

        simulation.run();
    }

}
