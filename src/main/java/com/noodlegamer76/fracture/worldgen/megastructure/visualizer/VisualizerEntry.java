package com.noodlegamer76.fracture.worldgen.megastructure.visualizer;

import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVar;

public abstract class VisualizerEntry<T extends GenVar<?>> {
    T var;

    public VisualizerEntry(T var) {
        this.var = var;
    }

    public T getVar() {
        return var;
    }

    public abstract void renderData();

    public abstract void renderVisualization();
}
