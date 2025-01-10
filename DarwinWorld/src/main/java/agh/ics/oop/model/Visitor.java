package agh.ics.oop.model;

public interface Visitor {
    void visit(Globe globe, int n);
    void visit(WaterMap waterMap, int n);
}
