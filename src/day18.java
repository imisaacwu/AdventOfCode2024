import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import lib.*;
import lib.Tuple.Pair;

public class day18 {
    public static final int L = 70;
    public static void main(String[] args) {
        List<String> input = FileIO.read("input/day18.in");
        LinkedList<Coord> unsafe = new LinkedList<>();
        for (String s : input) {
            String[] coord = s.split(",");
            unsafe.add(new Coord(Integer.parseInt(coord[1]), Integer.parseInt(coord[0])));
        }

        // Initialize Grid
        Grid<Character> g = new Grid<>(new ArrayList<>());
        for (int r = 0; r <= L; r++) {
            g.grid.add(new ArrayList<>());
            for (int c = 0; c <= L; c++) {
                g.grid.get(r).add('.');
            }
        }

        int bytes = 0;
        for (bytes = 0; bytes < 1024; bytes++) {
            Coord c = unsafe.removeFirst();
            g.set(c, '#');
        }

        int postKB = pathSteps(g);
        Coord last;

        do {
            last = unsafe.removeFirst();
            g.set(last, '#');
            bytes++;
        } while (pathSteps(g) > 0);

        System.out.println("Day 18:");
        System.out.printf("Part 1: %d\n", postKB);
        System.out.printf("Part 2: %s,%s\n", last.c(), last.r());   // Flip for consistency
    }

    private static int pathSteps(Grid<Character> g) {
        Set<Coord> visited = new HashSet<>();
        LinkedList<Pair<Coord, List<Coord>>> stack = new LinkedList<>();
        stack.push(new Pair<>(new Coord(0, 0), new ArrayList<>()));

        int min = Integer.MAX_VALUE;

        while(!stack.isEmpty()) {
            Pair<Coord, List<Coord>> p = stack.pop();
            if (p.v0().equals(new Coord(L, L))) {
                min = Math.min(min, p.v1().size());
            }
            for (Direction d : Direction.CARDINAL_DIRECTIONS) {
                Coord c = p.v0().relative(d);
                if (g.isValid(c) && !visited.contains(c) && g.get(c) != '#') {
                    visited.add(c);
                    ArrayList<Coord> copy = new ArrayList<>(p.v1());
                    copy.add(c);
                    stack.add(new Pair<>(c, copy));
                }
            }
        }

        return min != Integer.MAX_VALUE ? min : -1;
    }
}