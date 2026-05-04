package com.noodlegamer76.fracture.spellcrafting.graph;

import java.util.*;

public class GraphTopology {
    public static List<Node> sort(Graph graph) {
        Map<Node, Integer> inDegree = new HashMap<>();

        for (Node n : graph.nodes()) {
            inDegree.put(n, 0);
        }

        for (Connection c : graph.connections()) {
            Node to = c.to().parent();
            inDegree.put(to, inDegree.get(to) + 1);
        }

        Deque<Node> queue = new ArrayDeque<>();

        for (var e : inDegree.entrySet()) {
            if (e.getValue() == 0) {
                queue.add(e.getKey());
            }
        }

        List<Node> result = new ArrayList<>();

        while (!queue.isEmpty()) {
            Node n = queue.remove();
            result.add(n);

            for (Node dep : graph.dependentsOf(n)) {
                int deg = inDegree.get(dep) - 1;
                inDegree.put(dep, deg);

                if (deg == 0) {
                    queue.add(dep);
                }
            }
        }

        if (result.size() != graph.nodes().size()) {
            throw new IllegalStateException("Cycle detected in graph");
        }

        return result;
    }
}