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

public class day12 {
    public static void main(String[] args) {
        List<String> input = FileIO.read("input/day12.in");
        Grid<Character> g = new Grid<>(input, new Divider.Char());

        Map<Character, List<Region>> regions = new HashMap<>();

        for (int r = 0; r < g.getHeight(); r++) {
            for (int c = 0; c < g.getWidth(); c++) {
                char type = g.get(r, c);
                if (!regions.containsKey(type)) {
                    regions.put(type, new ArrayList<>());
                }

                boolean found = false;
                // List to keep track of all adjacent regions to merge
                List<Region> matched = new ArrayList<>();
                
                for (Region reg : regions.get(type)) {
                    if (reg.adjacent(new Coord(r, c))) {
                        // Found an adjacent region
                        matched.add(reg);
                        if (!found) {
                            // First adjacent region, add this plot to its plots
                            reg.plots.add(new Coord(r, c));
                            found = true;
                        }
                    }
                }
                
                if (!found) {
                    // No adjacent region found, create a new one for this plot
                    Region reg = new Region();
                    reg.plots.add(new Coord(r, c));
                    regions.get(type).add(reg);
                } else if (matched.size() > 1) {
                    // We found multiple adjacent regions, merge them together
                    Region reg = matched.get(0);
                    for (int i = 1; i < matched.size(); i++) {
                        for (Coord plot : matched.get(i).plots) {
                            reg.plots.add(plot);
                        }
                        regions.get(type).remove(matched.get(i));
                    }
                }
            }
        }

        long price = 0, bulk_price = 0;
        for (Character plant : regions.keySet()) {
            for (Region r : regions.get(plant)) {
                price += r.area() * r.perimeter(g);
                bulk_price += r.area() * r.sides(g);
            }
        }
        
        System.out.println("Day 12:");
        System.out.printf("Part 1: %d\n", price);
        System.out.printf("Part 2: %d\n", bulk_price);
    }

    static class Region {
        Set<Coord> plots = new HashSet<>();

        public boolean adjacent(Coord other) {
            for (Coord c : plots) {
                if (c.distance(other).magnitude() == 1.0) {
                    return true;
                }
            }
            return false;
        }

        public int area() {
            return plots.size();
        }

        public int perimeter(Grid<Character> g) {
            int result = 0;
            for (Coord c : plots) {
                // Default all 4 sides of a plot count towards the perimeter
                result += 4;
                // Subtract one for every side that borders a plot in the same region
                for (List<Character> side : g.radialSearch(c, 1, Direction.CARDINAL)) {
                    if (g.get(c) == side.get(0)) { result--; }
                }
            }
            return result;
        }

        public int sides(Grid<Character> g) {
            // Keep track of all edges
            List<Edge> sides = new ArrayList<>();

            // Using a similar algorithm for constructing "Regions" of Edges as above
            for (Coord c : plots) {
                for (Direction d : Direction.CARDINAL) {
                    // Skip edges that border plots in the same region
                    Coord edge = c.relative(d);
                    if (g.isValid(edge) && g.get(edge) == g.get(c)) { continue; }

                    boolean found = false;
                    // List of adjacent edges to merge if needed
                    List<Edge> matched = new ArrayList<>();
                    for (Edge e : sides) {
                        if (e.adjacent(c, d)) {
                            // Found Edge group
                            matched.add(e);
                            if (!found) {
                                // First adjacent Edge group found
                                e.cells.add(c);
                                found = true;
                            }
                        }
                    }

                    if (!found) {
                        // Didn't find an adjacent Edge group, create one
                        Edge e = new Edge(d);
                        e.cells.add(c);
                        sides.add(e);
                    } else if (matched.size() > 1) {
                        // Found more than one adjacent Edge group, merge them
                        Edge e = matched.get(0);
                        for (int i = 1; i < matched.size(); i++) {
                            for (Coord cell : matched.get(i).cells) {
                                e.cells.add(cell);
                            }
                            sides.remove(matched.get(i));
                        }
                    }
                }
            }

            return sides.size();
        }

        static class Edge {
            // Coordinates this edge borders
            Set<Coord> cells = new HashSet<>();
            // Which side of the cells this edge is located on
            Direction d;

            public Edge(Direction d) { this.d = d; }

            public boolean adjacent(Coord c, Direction d) {
                // Adjacent edges must be facing the same side
                if (!this.d.equals(d)) { return false; }

                for (Coord cell : cells) {
                    // Can't be with or opposite edge face
                    if (cell.relative(Direction.left90(d)).equals(c) ||
                        cell.relative(Direction.right90(d)).equals(c)) {
                        return true;
                    }
                }
                return false;
            }
        }
    }
}