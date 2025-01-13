package agh.ics.oop.model;

public interface Visitor {
    void visit(Globe globe);
    void visit(WaterMap waterMap);
}
