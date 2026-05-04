package com.noodlegamer76.fracture.spellcrafting.nodes.logic;

import com.noodlegamer76.fracture.spellcrafting.graph.*;

public class AddNode extends Node {
    private Pin input0;
    private Pin input1;
    private Pin output;

    public AddNode(Graph graph) {
        super(graph);
    }

    @Override
    public void init(Graph graph) {
        input0 = addInput(
                new Pin(
                        graph.nextId(),
                        this,
                        PinType.INPUT,
                        Integer.class
                )
        );
        input1 = addInput(
                new Pin(
                        graph.nextId(),
                        this,
                        PinType.INPUT,
                        Integer.class
                )
        );

        output = addOutput(
                new Pin(
                        graph.nextId(),
                        this,
                        PinType.OUTPUT,
                        Integer.class
                )
        );
    }

    @Override
    public void evaluate(NodeContext context) {
        if (!canEvaluate(input0, context) || !canEvaluate(input1, context)) {
            return;
        }

        int result = (int) context.getInput(input0) + (int) context.getInput(input1);
        context.setOutput(output, result);
    }
}
