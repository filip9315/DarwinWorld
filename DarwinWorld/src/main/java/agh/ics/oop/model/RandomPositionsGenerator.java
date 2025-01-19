package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class RandomPositionsGenerator implements Iterable<Vector2d> {

    int maxWidth;
    int maxHeight;
    int grassCount;
    List<Vector2d> positions = new ArrayList<>();

    public RandomPositionsGenerator(int maxWidth, int maxHeight, int grassCount) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.grassCount = grassCount;

        for(int x = 0; x < maxWidth; x++) {
            for(int y = 0; y < maxHeight; y++) {
                if(y < (2*maxHeight)/5 || y > (3*maxHeight)/5){
                    if(Math.random() < 0.2){
                        positions.add(new Vector2d(x, y));
                    }
                } else {
                    positions.add(new Vector2d(x, y));
                }

            }
        }
    }

    @Override
    public Iterator<Vector2d> iterator() {

        return new Iterator<>() {

            int index = 0;

            @Override
            public boolean hasNext() {
                return index < grassCount;
            }

            @Override
            public Vector2d next() {
                index++;
                int i = (int) (Math.random() * (positions.size()));
                Vector2d v = positions.get(i);
                positions.remove(i);
                return v;
            }
        };
    }
}
