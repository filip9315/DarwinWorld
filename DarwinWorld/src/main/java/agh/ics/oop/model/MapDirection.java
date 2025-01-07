package agh.ics.oop.model;

public enum MapDirection {
    NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST;

    public String toString() {
        return switch (this) {
            case NORTH -> "północ";
            case NORTH_EAST -> "północny-wschód";
            case EAST -> "wschód";
            case SOUTH_EAST -> "południowy-wschód";
            case SOUTH -> "południe";
            case SOUTH_WEST -> "południowy-zachód";
            case WEST -> "zachód";
            case NORTH_WEST -> "północny-zachód";
        };
    }
//    public MapDirection next() {
//        return switch (this) {
//            case NORTH -> EAST;
//            case SOUTH -> WEST;
//            case WEST -> NORTH;
//            case EAST -> SOUTH;
//        };
//    }

    public MapDirection rotate(int rotation) {
        return values()[(this.ordinal() + rotation) % 8];
    }

//    public MapDirection previous() {
//        return switch (this) {
//            case NORTH -> WEST;
//            case WEST -> SOUTH;
//            case SOUTH -> EAST;
//            case EAST -> NORTH;
//        };
//    }

    public Vector2d toUnitVector() {
        return switch (this) {
            case NORTH -> new Vector2d(0, 1);
            case NORTH_EAST -> new Vector2d(1, 1);
            case EAST -> new Vector2d(1, 0);
            case SOUTH_EAST -> new Vector2d(1, -1);
            case SOUTH -> new Vector2d(0, -1);
            case SOUTH_WEST -> new Vector2d(-1, -1);
            case WEST -> new Vector2d(-1, 0);
            case NORTH_WEST -> new Vector2d(-1, 1);

        };
    }
}
