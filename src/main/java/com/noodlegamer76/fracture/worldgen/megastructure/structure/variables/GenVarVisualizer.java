package com.noodlegamer76.fracture.worldgen.megastructure.structure.variables;


import com.noodlegamer76.fracture.worldgen.megastructure.visualizer.VisualizerEntry;

public interface GenVarVisualizer<T> {
    VisualizerEntry<GenVar<T>> create(GenVar<T> genVar);
}