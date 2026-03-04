package com.noodlegamer76.fracture.worldgen.megastructure.visualizer.entries;

import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.polygon.Polygon;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVar;
import com.noodlegamer76.fracture.worldgen.megastructure.visualizer.VisualizerEntry;

public class PolygonEntry<T extends GenVar<Polygon>> extends VisualizerEntry<T> {
    public PolygonEntry(T var) {
        super(var);
    }

    @Override
    public void renderData() {

    }

    @Override
    public void renderVisualization() {

    }
}
