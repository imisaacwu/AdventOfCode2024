package lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Grid<T> {
    public List<List<T>> grid;

    public Grid(List<String> in, Divider<T> d) {
        grid = new ArrayList<>();
        for (String s : in) {
            grid.add(d.toList(s));
        }
    }
    public Grid(List<List<T>> g) { grid = g; }

    public int getHeight() { return grid.size(); }
    public int getWidth() { return grid.get(0).size(); }

    public T get(Coord c) { return get(c.r(), c.c()); }
    public T get(int row, int col) { return grid.get(row).get(col); }

    public void set(Coord c, T val) { set(c.r(), c.c(), val); }
    public void set(int row, int col, T val) { grid.get(row).set(col, val); }

    public boolean isValid(Coord c) { return isValid(c.r(), c.c()); }
    public boolean isValid(int row, int col) {
        return 0 <= row && row < getHeight() && 0 <= col && col < getWidth();
    }

    public Coord find(T target) {
        for (int r = 0; r < getHeight(); r++) {
            for (int c = 0; c < getWidth(); c++) {
                if(get(r, c).equals(target)) {
                    return new Coord(r, c);
                }
            }
        }
        return new Coord(-1, -1);
    }

    public List<Coord> findAll(T target) {
        List<Coord> list = new ArrayList<>();
        for (int r = 0; r < getHeight(); r++) {
            for (int c = 0; c < getWidth(); c++) {
                if(get(r, c).equals(target)) {
                    list.add(new Coord(r, c));
                }
            }
        }
        return list;
    }

    public List<List<T>> radialSearch(Coord origin, int radius, List<Direction> directions) {
        List<List<T>> matches = new ArrayList<>();
        List<T> list;

        for (Direction d : directions) {
            list = new ArrayList<>();
            if (isValid(origin.relative(d, radius))) {
                for (int r = 1; r <= radius; r++) {
                    list.add(get(origin.relative(d, r)));
                }
                matches.add(list);
            }
        }

        return matches;
    }

    public Grid<T> rotateCW() {
        List<List<T>> g = new ArrayList<>();
        for (int c = 0; c < getWidth(); c++) {
            List<T> col = new ArrayList<>();
            for (int r = getHeight()-1; r >= 0; r--) {
                col.add(get(r, c));
            }
            g.add(col);
        }
        return new Grid<T>(g);
    }

    public Grid<T> rotateCCW() {
        List<List<T>> g = new ArrayList<>();
        for (int c = getWidth()-1; c >= 0; c--) {
            List<T> col = new ArrayList<>();
            for (int r = 0; r < getHeight(); r++) {
                col.add(get(r, c));
            }
            g.add(col);
        }
        return new Grid<T>(g);
    }

    public Grid<T> transpose() {
        List<List<T>> g = new ArrayList<>();
        for (int c = 0; c < getWidth(); c++) {
            List<T> toRow = new ArrayList<>();
            for (int r = 0; r < getHeight(); r++) {
                toRow.add(get(r, c));
            }
            g.add(toRow);
        }
        return new Grid<T>(g);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (List<T> row : grid) {
            for (T val : row) {
                s.append(val.toString()).append(" ");
            }
            s.deleteCharAt(s.length() - 1);
            s.append("\n");
        }
        return s.toString();
    }

    @Override
    public int hashCode() { return grid.hashCode(); }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object other) {
        if (other == this) { return true; }
        if (other == null || !(other instanceof Grid<?>)) { return false; }
        Grid<?> o = (Grid<?>) other;
        if (!(o.get(0,0).getClass() != this.get(0,0).getClass())) {
            return false;
        }
        return Arrays.equals(((Grid<T>) other).grid.toArray(), this.grid.toArray());
    }
}