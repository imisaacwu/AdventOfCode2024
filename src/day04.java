import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lib.Coord;
import lib.Direction;
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
                    List<List<Character>> matches = g.radialSearch(
                        new Coord(r, c), 3, Direction.ALL_DIRECTIONS);
                    for (List<Character> list : matches) {
                        if(list.toString().replaceAll("[\\[\\], ]", "").equals("MAS")) {
                            xmas++;
                        }
                    }
                }
                if (g.get(r, c) == 'A') {
                    List<List<Character>> matches = g.radialSearch(
                        new Coord(r, c), 1, Direction.ORDINAL_DIRECTIONS);
                    List<String> validMatches = new ArrayList<>(Arrays.asList("MSSM", "MMSS", "SMMS", "SSMM"));
                    if(validMatches.contains(matches.toString().replaceAll("[\\[\\], ]", ""))) {
                        mas++;
                    }
                }
            }
        }

        System.out.println("Day 04:");
        System.out.printf("Part 1: %d\n", xmas);
        System.out.printf("Part 2: %d\n", mas);
    }
}