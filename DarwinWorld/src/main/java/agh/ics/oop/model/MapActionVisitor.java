package agh.ics.oop.model;

public class MapActionVisitor implements Visitor {

    @Override
    public void visit(Globe globe) {
        System.out.println("This map doesn't support actions");
    }

    @Override
    public void visit(WaterMap waterMap) {
        waterMap.changeSizeOfLakes();
    }
}
