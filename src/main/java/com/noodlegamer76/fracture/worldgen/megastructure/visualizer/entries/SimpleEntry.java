package com.noodlegamer76.fracture.worldgen.megastructure.visualizer.entries;

import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVar;
import com.noodlegamer76.fracture.worldgen.megastructure.visualizer.VisualizerEntry;

public class SimpleEntry<T extends GenVar<?>> extends VisualizerEntry<T> {
    public SimpleEntry(T var) {
        super(var);
    }

    @Override
    public void renderData() {

    }

    @Override
    public void renderVisualization() {

    }
}
