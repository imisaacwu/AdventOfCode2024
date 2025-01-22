import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import file.FileIO;
import grid.Divider;
import grid.Grid;
import structures.Coord;
import structures.Direction;

public class day08 {
    public static void main(String[] args) {
        List<String> input = FileIO.read("input/day08.in");
        Grid<Character> g = new Grid<>(input, new Divider.Char());

        Map<Character, List<Coord>> antennae = new HashMap<>();
        for (int r = 0; r < g.getHeight(); r++) {
            for (int c = 0; c < g.getWidth(); c++) {
                char a = g.get(r, c);
                if (a != '.') {
                    if (!antennae.containsKey(a)) {
                        antennae.put(a, new ArrayList<>());
                    }
                    antennae.get(a).add(new Coord(r, c));
                }
            }
        }

        Set<Coord> antinodes = new HashSet<>(), full = new HashSet<>();
        for (char c : antennae.keySet()) {
            List<Coord> matching = antennae.get(c);
            for (int i = 0; i < matching.size(); i++) {
                for (int j = 0; j < matching.size(); j++) {
                    if (i == j) { continue; }
                    Direction d = matching.get(i).distance(matching.get(j));
                    if (g.isValid(matching.get(j).relative(d))) {
                        antinodes.add(matching.get(j).relative(d));
                    }
                    Coord curr = matching.get(i);
                    while (g.isValid(curr.relative(d))) {
                        full.add(curr.relative(d));
                        curr = curr.relative(d);
                    }
                }
            }
        }
        
        System.out.println("Day 08:");
        System.out.printf("Part 1: %d\n", antinodes.size());
        System.out.printf("Part 2: %d\n", full.size());
    }
}