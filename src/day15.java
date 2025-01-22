import java.util.ArrayList;
import java.util.List;

import file.FileIO;
import grid.Divider;
import grid.Grid;
import structures.Coord;
import structures.Direction;

public class day15 {
    public static void main(String[] args) {
        List<String> input = FileIO.read("input/day15.in");
        List<String> map = new ArrayList<>();
        List<String> scaled = new ArrayList<>();
        String moves = "";
        
        boolean separator = false;
        for (String s : input) {
            if (s.isBlank()) {
                separator = true;
                continue;
            }
            if (!separator) {
                map.add(s);
                String row = "";
                for (char c : s.toCharArray()) {
                    switch (c) {
                        case '#': row += "##"; break;
                        case 'O': row += "[]"; break;
                        case '.': row += ".."; break;
                        case '@': row += "@."; break;
                    }
                }
                scaled.add(row);
            } else {
                moves += s;
            }
        }

        Grid<Character> g = new Grid<>(map, new Divider.Char());
        Grid<Character> wide = new Grid<>(scaled, new Divider.Char());

        Coord robot = g.find('@');
        Coord robot2 = wide.find('@');
        
        for (char c : moves.toCharArray()) {
            Direction d = new Direction(0, 0);
            switch (c) {
                case '^': d = Direction.N; break;
                case '>': d = Direction.E; break;
                case 'v': d = Direction.S; break;
                case '<': d = Direction.W; break;
            }

            g.set(robot, '.');
            Coord next = robot.relative(d), next2 = robot2.relative(d);

            // Part 1
            if (g.get(next) == '.') {
                robot = next;
            } else if (g.get(next) == 'O' && canPush(g, next, d, false)) {
                // Push boxes
                push(g, next, d, false);
                robot = next;
            }
            g.set(robot, '@');

            // Part 2
            if (wide.get(next2) == '.') {
                wide.set(robot2, '.');
                robot2 = next2;
            } else if ((wide.get(next2) == '[' || wide.get(next2) == ']')) {
                // Push large boxes
                if ((d.equals(Direction.E) || d.equals(Direction.W)) && canPush(wide, robot2, d, false)) {
                    // Horizontal, don't need to calculate for wide boxes
                    push(wide, robot2, d, false);
                    wide.set(robot2, '.');
                    robot2 = next2;
                } else if (canPush(wide, robot2, d, true)) {
                    // Vertical, we do need to push recursively
                    push(wide, robot2, d, true);
                    wide.set(robot2, '.');
                    robot2 = next2;
                }
            }
            wide.set(robot2, '@');
        }

        long sum = 0;
        for (int r = 0; r < g.getHeight(); r++) {
            for (int c = 0; c < g.getWidth(); c++) {
                if (g.get(r, c) == 'O') {
                    sum += 100 * r + c;
                }
            }
        }

        long wide_sum = 0;
        for (int r = 0 ; r < wide.getHeight(); r++) {
            for (int c = 0; c < wide.getWidth(); c++) {
                if (wide.get(r, c) == '[') {
                    wide_sum += 100 * r + c;
                }
            }
        }

        System.out.println("Day 15:");
        System.out.printf("Part 1: %d\n", sum);
        System.out.printf("Part 2: %d\n", wide_sum);
    }

    static boolean canPush(Grid<Character> g, Coord target, Direction d, boolean wide) {
        if (g.get(target) == '#') { return false; }
        if (g.get(target) == '.') { return true; }
        return canPush(g, target.relative(d), d, wide) && (!wide || g.get(target) == '@' ||
               canPush(g, target.relative(d).relative(
                   g.get(target) == '[' ? Direction.E : Direction.W
               ), d, wide));
    }

    static void push(Grid<Character> g, Coord curr, Direction d, boolean wide) {
        if (g.get(curr.relative(d)) == '.') {
            // Free space, push ourselves
            g.set(curr.relative(d), g.get(curr));
            g.set(curr, '.');
        } else {
            // Another box in front, try to push it
            if (wide) {
                push(g, curr.relative(d).relative(
                    g.get(curr.relative(d)) == '[' ? Direction.E : Direction.W
                ), d, wide);
            }
            push(g, curr.relative(d), d, wide);
            // Pushed boxes in front of us, now we can push ourselves
            g.set(curr.relative(d), g.get(curr));
            g.set(curr, '.');
        }
    }
}