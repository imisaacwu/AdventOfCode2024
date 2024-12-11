package lib;

import java.util.List;

public class Direction extends Coord {
    public static final Direction N = new Direction(-1, 0);
    public static final Direction E = new Direction(0, 1);
    public static final Direction S = new Direction(1, 0);
    public static final Direction W = new Direction(0, -1);
    public static final Direction NE = new Direction(-1, 1);
    public static final Direction SE = new Direction(1, 1);
    public static final Direction SW = new Direction(1, -1);
    public static final Direction NW = new Direction(-1, -1);

    public static final List<Direction> ALL_DIRECTIONS = List.of(N, NE, E, SE, S, SW, W, NW);
    public static final List<Direction> CARDINAL_DIRECTIONS = List.of(N, E, S, W);
    public static final List<Direction> ORDINAL_DIRECTIONS = List.of(NE, SE, SW, NW);

    public static Direction left(Direction dir) {
        int i = ALL_DIRECTIONS.indexOf(dir) - 1;
        return ALL_DIRECTIONS.get(i < 0 ? ALL_DIRECTIONS.size() - 1 : i);
    }
    public static Direction left90(Direction dir) {
        return new Direction(-1 * dir.c(), dir.r());
    }

    public static Direction right(Direction dir) {
        return ALL_DIRECTIONS.get((ALL_DIRECTIONS.indexOf(dir) + 1) % ALL_DIRECTIONS.size());
    }
    public static Direction right90(Direction dir) {
        return new Direction(dir.c(), -1 * dir.r());
    }

    public static Direction opposite(Direction dir) {
        return new Direction(-1 * dir.r(), -1 * dir.c());
    }

    public Direction(int Δr, int Δc) {
        super(Δr, Δc);
    }

    public int Δr() { return this.r(); }
    public int Δc() { return this.c(); }
}