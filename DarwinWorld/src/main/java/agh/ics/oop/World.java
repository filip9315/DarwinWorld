package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.List;

public class World {

    public static void main(String[] args){
        Globe globe = new Globe(10, 10, 3);
        WaterMap waterMap = new WaterMap(10, 10, 2, 3);

        List<Vector2d> positions = List.of(new Vector2d(3,3), new Vector2d(3,4));
        List<MoveDirection> directions = OptionsParser.parse(args);

        int genomeLength = 10;
        List<Integer> genesList = List.of(1, 2, 3, 4, 5, 6, 7, 2, 0, 1);

        Genotype genotype = new Genotype(genesList);
        Genotype genotype2 = new GenotypeSlightCorrection(genesList);

        Simulation simulation = new Simulation(3, waterMap, 10, genotype, 3);

        simulation.run();
    }

}
