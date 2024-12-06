import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lib.Coord;
import lib.Direction;
import lib.Divider;
import lib.FileIO;
import lib.Grid;

public class day06 {
    public static void main(String[] args) {
        List<String> input = FileIO.read("input/day06.in");
        Grid<Character> g = new Grid<>(input, new Divider.Char());
        Set<Coord> visited = new HashSet<>();

        Direction dir = Direction.N;
        Coord start = g.find('^'), curr = start;

        while (g.isValid(curr)) {
            visited.add(curr);
            Coord ahead = curr.relative(dir);
            if (g.isValid(ahead) && g.get(ahead) == '#') {
                dir = Direction.right(dir);
            } else {
                curr = curr.relative(dir);
            }
        }

        System.out.println("Day 06:");
        System.out.printf("Part 1: %d\n", visited.size());
        System.out.printf("Part 2: %d\n", 0);
    }
}