import java.util.ArrayList;
import java.util.List;

import file.FileIO;
import grid.Grid;
import structures.Coord;
import structures.Direction;

public class day14 {
    public static void main(String[] args) {
        List<String> input = FileIO.read("input/day14.in");
        List<Robot> robots = new ArrayList<>();
        Grid<List<Robot>> g = new Grid<>(new ArrayList<>());

        for (int r = 0; r < 103; r++) {
            List<List<Robot>> row = new ArrayList<>();
            for (int c = 0; c < 101; c++) {
                row.add(new ArrayList<>());
            }
            g.grid.add(row);
        }

        for (String s : input) {
            String[] p = s.split(" ")[0].split("=")[1].split(",");
            String[] v = s.split(" ")[1].split("=")[1].split(",");

            Robot r = new Robot();
            r.p = new Coord(Integer.parseInt(p[1]), Integer.parseInt(p[0]));
            r.v = new Direction(Integer.parseInt(v[1]), Integer.parseInt(v[0]));

            g.get(r.p).add(r);
            robots.add(r);
        }

        int step = 0, safety = 1;
        while (true) {
            for (Robot r : robots) {
                r.step(g);
            }
            step++;

            if (step == 100) {
                // Quadrant division
                for (int qr = 0; qr < 2; qr++) {
                    for (int qc = 0; qc < 2; qc++) {
                        int qSafety = 0;
                        // Summing quadrant's safety score
                        for (int r = qr * (g.getHeight() / 2) + qr; r < (qr + 1) * (g.getHeight() / 2) + qr; r++) {
                            for (int c = qc * (g.getWidth() / 2) + qc; c < (qc + 1) * (g.getWidth() / 2) + qc; c++) {
                                qSafety += g.get(r, c).size();
                            }
                        }
                        safety *= qSafety;
                    }
                }
            }

            // Find the first time robots are all on unique tiles
            boolean unique = true;
            for (int r = 0; r < g.getHeight(); r++) {
                for (int c = 0; c < g.getWidth(); c++) {
                    if (g.get(r, c).size() > 1) { unique = false; }
                }
            }

            if (step > 100 && unique) { break; }
        }

        System.out.println("Day 14:");
        System.out.printf("Part 1: %d\n", safety);
        System.out.printf("Part 2: %d\n", step);
    }

    static class Robot {
        Coord p;
        Direction v;

        public void step(Grid<List<Robot>> g) {
            g.get(p).remove(this);
            p = clamp(p.relative(v), g.getHeight(), g.getWidth());
            g.get(p).add(this);
        }

        private Coord clamp(Coord p, int r, int c) {
            int pr = p.r(), pc = p.c();
            if (pr < 0) {
                pr += r;
            } else if (pr >= r) {
                pr %= r;
            }
            if (pc < 0) {
                pc += c;
            } else if (pc >= c) {
                pc %= c;
            }
            return new Coord(pr, pc);
        }
    }
}