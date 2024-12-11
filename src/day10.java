import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lib.Coord;
import lib.Direction;
import lib.Divider;
import lib.FileIO;
import lib.Grid;

public class day10 {
    public static void main(String[] args) {
        List<String> input = FileIO.read("input/day10.in");
        Grid<Character> g = new Grid<>(input, new Divider.Char());

        List<Coord> heads = g.findAll('0');

        int score = 0, trails = 0;
        for (Coord h : heads) {
            Set<Coord> nines = new HashSet<>();
            for (Direction d : Direction.CARDINAL_DIRECTIONS) {
                trails += explore(g, h, d, nines);
            }
            score += nines.size();
        }

        System.out.println("Day 10:");
        System.out.printf("Part 1: %d\n", score);
        System.out.printf("Part 2: %d\n", trails);
    }

    static int explore(Grid<Character> g, Coord c, Direction d, Set<Coord> nines) {
        if (g.isValid(c.relative(d)) && g.get(c.relative(d)) == g.get(c) + 1) {
            if (g.get(c.relative(d)) == '9') {
                nines.add(c.relative(d));
                return 1;
            } else {
                int trails = 0;
                for (Direction dir : Direction.CARDINAL_DIRECTIONS) {
                    trails += explore(g, c.relative(d), dir, nines);
                }
                return trails;
            }
        }
        return 0;
    }
}