import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import file.FileIO;
import graph.Graph;
import graph.Graph.Node;
import grid.Divider;
import grid.Grid;
import structures.Coord;
import structures.Direction;
import structures.Tuple.Pair;

public class day16 {
    public static void main(String[] args) {
        List<String> input = FileIO.read("input/day16.in");

        Grid<Character> g = new Grid<>(input, new Divider.Char());
        Graph<Pair<Coord, Direction>> graph = new Graph<>();

        // Initalize Graph using Grid
        for (int r = 0; r < g.getHeight(); r++) {
            for (int c = 0; c < g.getWidth(); c++) {
                if (g.get(r, c) == '.' || g.get(r, c) == 'S' || g.get(r, c) == 'E') {
                    // Each open spot, add node as if coming from Direction d
                    for (Direction d : Direction.CARDINAL) {
                        Pair<Coord, Direction> self = new Pair<>(new Coord(r, c), d);

                        // Straight movement, 1 cost
                        Coord test = self.v0().relative(d);
                        if (g.isValid(test) && g.get(test) != '#') {
                            graph.addEdge(self, new Pair<>(test, d), 1);
                        }

                        // Turning, 1000 cost
                        test = self.v0().relative(Direction.left90(d));
                        if (g.isValid(test) && g.get(test) != '#') {
                            graph.addEdge(self, new Pair<>(self.v0(), Direction.left90(d)), 1000);
                        }

                        test = self.v0().relative(Direction.right90(d));
                        if (g.isValid(test) && g.get(test) != '#') {
                            graph.addEdge(self, new Pair<>(self.v0(), Direction.right90(d)), 1000);
                        }
                    }
                }
            }
        }

        Pair<Coord, Direction> start = new Pair<>(g.find('S'), Direction.E);
        Coord end = g.find('E');

        Map<Pair<Coord, Direction>, Node<Pair<Coord, Direction>>> map = graph.dijkstra(start);
        long min = Long.MAX_VALUE;
        // Check all 4 possible end positions
        for (Direction d : Direction.CARDINAL) {
            min = Math.min(min, map.get(new Pair<>(end, d)).dist);
        }

        Set<Coord> tiles = new HashSet<>();
        Stack<Node<Pair<Coord, Direction>>> stack = new Stack<>();
        // Search for end node
        for (Direction d : Direction.CARDINAL) {
            if (map.get(new Pair<>(end, d)).dist == min) {
                stack.push(map.get(new Pair<>(end, d)));
                break;
            }
        }

        // Walk backwards from the end, keep track of unique tiles
        while(!stack.isEmpty()) {
            Node<Pair<Coord, Direction>> node = stack.pop();
            tiles.add(node.v0().v0());
            for (Node<Pair<Coord, Direction>> predecessor : node.predecessors) {
                if (!stack.contains(predecessor)) {
                    stack.push(predecessor);
                }
            }
        }

        System.out.println("Day 16:");
        System.out.printf("Part 1: %d\n", min);
        System.out.printf("Part 2: %d\n", tiles.size());
    }
}