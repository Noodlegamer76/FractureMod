package com.noodlegamer76.fracture.spellcrafting.nodes;

import com.noodlegamer76.fracture.spellcrafting.graph.Graph;
import com.noodlegamer76.fracture.spellcrafting.graph.Node;

import java.util.function.Function;
import java.util.function.Supplier;

public class NodeType<T extends Node> {
    private final Function<Graph, T> nodeFactory;

    public NodeType(Function<Graph, T> nodeFactory) {
        this.nodeFactory = nodeFactory;
    }

    public T create(Graph graph) {
        return nodeFactory.apply(graph);
    }
}
