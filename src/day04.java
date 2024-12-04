import java.util.List;

import lib.Divider;
import lib.FileIO;
import lib.Grid;

public class day04 {
    public static void main(String[] args) {
        List<String> input = FileIO.read("input/day04.in");
        Grid<Character> g = new Grid<>(input, new Divider.Char());
        
        int xmas = 0, mas = 0;

        for (int r = 0; r < g.getHeight(); r++) {
            for (int c = 0; c < g.getWidth(); c++) {
                if (g.get(r, c) == 'X') {
                    xmas += searchXMAS(g, r, c);
                }
                if (g.get(r, c) == 'A') {
                    mas += searchX_MAS(g, r, c);
                }
            }
        }

        System.out.println("Day 04:");
        System.out.printf("Part 1: %d\n", xmas);
        System.out.printf("Part 2: %d\n", mas);
    }

    static int searchX_MAS(Grid<Character> g, int r, int c) {
        int mas = 0;
        if (g.isValid(r - 1, c - 1) && g.isValid(r + 1, c + 1)) {
            // M up
            if (g.get(r - 1, c - 1) == 'M' &&
                g.get(r - 1, c + 1) == 'M' &&
                g.get(r + 1, c - 1) == 'S' &&
                g.get(r + 1, c + 1) == 'S') { mas++; }
            // M left
            if (g.get(r - 1, c - 1) == 'M' &&
                g.get(r - 1, c + 1) == 'S' &&
                g.get(r + 1, c - 1) == 'M' &&
                g.get(r + 1, c + 1) == 'S') { mas++; }
            // M down
            if (g.get(r - 1, c - 1) == 'S' &&
                g.get(r - 1, c + 1) == 'S' &&
                g.get(r + 1, c - 1) == 'M' &&
                g.get(r + 1, c + 1) == 'M') { mas++; }
            // M right
            if (g.get(r - 1, c - 1) == 'S' &&
                g.get(r - 1, c + 1) == 'M' &&
                g.get(r + 1, c - 1) == 'S' &&
                g.get(r + 1, c + 1) == 'M') { mas++; }
        }
        return mas;
    }

    static int searchXMAS(Grid<Character> g, int r, int c) {
        int xmas = 0;
        // Up
        if (g.isValid(r - 3, c)) {
            if (g.get(r - 1, c) == 'M' &&
                g.get(r - 2, c) == 'A' &&
                g.get(r - 3, c) == 'S') { xmas++; }
        }
        // Down
        if (g.isValid(r + 3, c)) {
            if (g.get(r + 1, c) == 'M' &&
                g.get(r + 2, c) == 'A' &&
                g.get(r + 3, c) == 'S') { xmas++; }
        }
        // Left
        if (g.isValid(r, c - 3)) {
            if (g.get(r, c - 1) == 'M' &&
                g.get(r, c - 2) == 'A' &&
                g.get(r, c - 3) == 'S') { xmas++; }
        }
        // Right
        if (g.isValid(r, c + 3)) {
            if (g.get(r, c + 1) == 'M' &&
                g.get(r, c + 2) == 'A' &&
                g.get(r, c + 3) == 'S') { xmas++; }
        }
        // UL
        if (g.isValid(r - 3, c - 3)) {
            if (g.get(r - 1, c - 1) == 'M' &&
                g.get(r - 2, c - 2) == 'A' &&
                g.get(r - 3, c - 3) == 'S') { xmas++; }
        }
        // UR
        if (g.isValid(r - 3, c + 3)) {
            if (g.get(r - 1, c + 1) == 'M' &&
                g.get(r - 2, c + 2) == 'A' &&
                g.get(r - 3, c + 3) == 'S') { xmas++; }
        }
        // DR
        if (g.isValid(r + 3, c + 3)) {
            if (g.get(r + 1, c + 1) == 'M' &&
                g.get(r + 2, c + 2) == 'A' &&
                g.get(r + 3, c + 3) == 'S') { xmas++; }
        }
        // DL
        if (g.isValid(r + 3, c - 3)) {
            if (g.get(r + 1, c - 1) == 'M' &&
                g.get(r + 2, c - 2) == 'A' &&
                g.get(r + 3, c - 3) == 'S') { xmas++; }
        }
        return xmas;
    }
}