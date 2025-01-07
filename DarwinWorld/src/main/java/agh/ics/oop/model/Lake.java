package agh.ics.oop.model;

import java.util.HashMap;
import java.util.Map;

public class Lake {
    Map<Vector2d, Tile> waters = new HashMap<>();
    Vector2d center;
    int radius;

    public Lake (Vector2d center, int radius, WaterMap map) {
        this.center = center;
        this.radius = radius;

        for (int i = 0; i <= map.getCurrentBounds().upperRight().getX(); i++) {
            for (int j = 0; j <= map.getCurrentBounds().upperRight().getY(); j++) {
                if (Math.pow(Math.abs(i - center.getX()), 2) + Math.pow(Math.abs(j - center.getY()), 2) <= radius) {
                    Vector2d pos = new Vector2d(i, j);
                    waters.put(pos, new Tile(TileType.WATER, pos));
                }
            }
        }
    }

    public Map<Vector2d, Tile> getWaters() {
        return waters;
    }

    public Vector2d getCenter() {
        return center;
    }

    public int getRadius() {
        return radius;
    }
}
