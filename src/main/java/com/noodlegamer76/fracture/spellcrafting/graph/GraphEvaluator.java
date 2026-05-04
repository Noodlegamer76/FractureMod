package com.noodlegamer76.fracture.spellcrafting.graph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphEvaluator {
    private final Map<Node, Map<Pin, Object>> values = new HashMap<>();

    public void evaluate(Graph graph) {
        List<Node> order = GraphTopology.sort(graph);

        values.clear();

        for (Node node : order) {
            evaluateNode(node, graph);
        }
    }

    private void evaluateNode(Node node, Graph graph) {
        NodeContext ctx = new NodeContext();

        for (Pin input : node.inputs()) {
            Connection c = input.connection();
            if (c == null) continue;

            Node from = c.from().parent();

            Object value = getOutput(from, c.from());

            ctx.setInput(input, value);
        }

        node.evaluate(ctx);

        Map<Pin, Object> outMap = new HashMap<>();
        for (Pin output : node.outputs()) {
            Object value = ctx.getOutput(output);
            outMap.put(output, value);
        }

        values.put(node, outMap);
    }

    private Object getOutput(Node node, Pin pin) {
        Map<Pin, Object> out = values.get(node);
        if (out == null) return null;
        return out.get(pin);
    }

    public Object getValue(Node node, Pin pin) {
        Map<Pin, Object> out = values.get(node);
        return out != null ? out.get(pin) : null;
    }
}