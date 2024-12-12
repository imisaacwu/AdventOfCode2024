package lib;

import java.util.HashMap;
import java.util.Map;

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

    public static class Node<V> extends Tuple.Unit<V> {
        protected Map<Node<V>, Long> neighbors;

        public Node(V data) {
            super(data);
            this.neighbors = new HashMap<>();
        }
    }
}