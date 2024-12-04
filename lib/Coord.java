package lib;

public class Coord extends Tuple.Pair<Integer, Integer> {
    public Coord(int r, int c) {
        super(r, c);
    }

    public int r() { return this.v0(); }
    public int c() { return this.v1(); }

    public Coord relative(Direction d, int n) {
        return new Coord(r() + (n * d.Δr()), c() + (n * d.Δc()));
    }
    public Coord relative(Direction d) { return relative(d, 1); }
}
