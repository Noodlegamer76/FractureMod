package com.noodlegamer76.fracture.spellcrafting.graph;

import java.util.*;

public class Graph {
    private final List<Node> nodes = new ArrayList<>();
    private final List<Connection> connections = new ArrayList<>();

    private final Map<Node, List<Node>> dependents = new HashMap<>();
    private int nextId = 0;

    public void addNode(Node node) {
        nodes.add(node);
        node.setId(nextId());
    }

    public int nextId() {
        return nextId++;
    }

    public void addConnection(Connection c) {
        connections.add(c);

        Node from = c.from().parent();
        Node to = c.to().parent();

        dependents.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
    }

    public void removeConnection(Connection c) {
        connections.remove(c);

        Node from = c.from().parent();
        Node to = c.to().parent();

        List<Node> deps = dependents.get(from);
        if (deps != null) {
            deps.remove(to);

            if (deps.isEmpty()) {
                dependents.remove(from);
            }
        }
    }

    public List<Node> nodes() {
        return List.copyOf(nodes);
    }

    public List<Connection> connections() {
        return List.copyOf(connections);
    }

    public List<Node> dependentsOf(Node node) {
        return dependents.getOrDefault(node, List.of());
    }
}