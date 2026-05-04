package com.noodlegamer76.fracture.spellcrafting.graph;

import java.util.ArrayList;
import java.util.List;

public abstract class Node {
    private int id;
    private final List<Pin> inputs = new ArrayList<>();
    private final List<Pin> outputs = new ArrayList<>();

    public Node(Graph graph) {
        graph.addNode(this);
        init(graph);
    }

    public abstract void init(Graph graph);

    void setId(int id) {
        this.id = id;
    }

    public int id() {
        return id;
    }

    public List<Pin> inputs() {
        return List.copyOf(inputs);
    }

    public List<Pin> outputs() {
        return List.copyOf(outputs);
    }

    public Pin addInput(Pin pin) {
        inputs.add(pin);
        return pin;
    }

    public Pin addOutput(Pin pin) {
        outputs.add(pin);
        return pin;
    }

    public boolean canEvaluate(Pin pin, NodeContext context) {
        return context.getInput(pin) != null && context.getInput(pin).getClass() == pin.clazz();
    }

    /**
     * Evaluator calls this with resolved input values per pin.
     */
    public abstract void evaluate(NodeContext context);
}