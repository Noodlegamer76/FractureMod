package com.noodlegamer76.fracture.spellcrafting.graph;

import java.util.HashMap;
import java.util.Map;

public class NodeContext {
    private final Map<Pin, Object> inputValues = new HashMap<>();
    private final Map<Pin, Object> outputValues = new HashMap<>();

    public void setInput(Pin pin, Object value) {
        inputValues.put(pin, value);
    }

    public Object getInput(Pin pin) {
        return inputValues.get(pin);
    }

    public void setOutput(Pin pin, Object value) {
        outputValues.put(pin, value);
    }

    public Object getOutput(Pin pin) {
        return outputValues.get(pin);
    }
}