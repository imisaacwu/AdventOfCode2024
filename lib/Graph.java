package lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class Graph<T> {
    public Map<T, Node<T>> nodes;

    public Graph() {
        this.nodes = new HashMap<>();
    }

    public boolean hasNode(T node) {
        return nodes.containsKey(node);
    }

    public void addNode(T node) {
        if (!hasNode(node)) { nodes.put(node, new Node<>(node)); }
    }

    public void removeNode(T node) {
        nodes.remove(node);
        for (T n : nodes.keySet()) {
            nodes.get(n).neighbors.remove(node);
        }
    }

    public Map<Node<T>, Long> edges(T node) {
        return nodes.get(node).neighbors;
    }

    public boolean hasEdge(T from, T to) {
        return hasNode(from) && hasNode(to) &&
               nodes.get(from).neighbors.containsKey(nodes.get(to));
    }

    public void addEdge(T from, T to, long weight) {
        if (!hasNode(from)) { addNode(from); }
        if (!hasNode(to)) { addNode(to); }
        nodes.get(from).neighbors.put(nodes.get(to), weight);
    }
    public void addEdge(T from, T to) { addEdge(from, to, 1); }

    public void removeEdge(T from, T to) {
        nodes.get(from).neighbors.remove(nodes.get(to));
    }

    public long getWeight(T from, T to) {
        if (!hasEdge(from, to)) { return 0; }
        return nodes.get(from).neighbors.get(nodes.get(to));
    }

    public Set<Node<T>> dijkstra(T from) {
        Set<Node<T>> finished = new HashSet<>();
        PriorityQueue<Node<T>> pq = new PriorityQueue<>(nodes.size());

        // Set start point
        nodes.get(from).dist = 0;
        pq.add(nodes.get(from));

        while (!pq.isEmpty()) {
            Node<T> node = pq.poll();
            // Iterate over all neighbors
            for (Node<T> n : node.neighbors.keySet()) {
                if (!finished.contains(n)) {
                    // This node has non-finalized weight
                    if (n.dist < node.dist + node.neighbors.get(n)) {
                        // This path is shorter, clear predecessors
                        n.predecessors.clear();
                    }

                    n.dist = node.dist + node.neighbors.get(n);
                    n.predecessors.add(node);
                    pq.add(n);
                }
            }
            // Finished with this node, finalize
            finished.add(node);
        }

        return finished;
    }

    public long dijkstra(T from, T to) {
        for (Node<T> node : dijkstra(from)) {
            if (nodes.get(to).equals(node)) {
                return node.dist;
            }
        }
        return -1;
    }

    public String toString() {
        String s = "{";
        for (Node<T> node : nodes.values()) {
            s += node.v0();
            if (node.neighbors.size() > 0) {
                s += "={";
                for (Node<T> neighbor : node.neighbors.keySet()) {
                    s += neighbor.v0();
                    s += " (";
                    s += node.neighbors.get(neighbor);
                    s += "), ";
                }
                s = s.substring(0, s.length() - 2);
                s += "}";
            }
            s += ", ";
        }
        s = s.substring(0, s.length() - 2);
        s += "}";
        return s;
    }

    public static class Node<V> extends Tuple.Unit<V> implements Comparable<Node<V>> {
        public Map<Node<V>, Long> neighbors = new HashMap<>();
        public List<Node<V>> predecessors = new ArrayList<>();
        public long dist = Long.MAX_VALUE;

        public Node(V data) {
            super(data);
        }

        @Override
        public int compareTo(Node<V> o) {
            return ((Long) (this.dist - o.dist)).intValue();
        }
    }
}