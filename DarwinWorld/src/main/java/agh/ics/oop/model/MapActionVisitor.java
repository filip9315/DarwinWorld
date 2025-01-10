package agh.ics.oop.model;

public class MapActionVisitor implements Visitor {

    @Override
    public void visit(Globe globe, int n) {
        System.out.println("This map doesn't support actions");
    }

    @Override
    public void visit(WaterMap waterMap, int n) {
        waterMap.changeSizeOfLakes(n);
    }
}
