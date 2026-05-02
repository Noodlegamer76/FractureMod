package com.noodlegamer76.fracture.client.structure;

import com.noodlegamer76.fracture.worldgen.megastructure.MegaStructureGenerator;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureDefinition;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureInstance;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.Structures;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter that connects the editor to your actual megastructure system.
 * Scalable and easy to extend.
 */
public class MegastructureEditorAdapter implements MegastructureViewer.SnapshotProvider {
    private final List<MegastructureViewer.DebugEvent> debugEvents = new ArrayList<>();

    @Override
    public List<StructureDefinition> getDefinitions() {
        return Structures.getInstance().getStructures();
    }

    @Override
    public StructureInstance getLastInstance() {
        return MegaStructureGenerator.getLastInstance();
    }

    @Override
    public List<MegastructureViewer.DebugEvent> getDebugEvents() {
        return new ArrayList<>(debugEvents);
    }

    public void recordEvent(String type, String source, String message) {
        debugEvents.add(new MegastructureViewer.DebugEvent(type, source, message));

        if (debugEvents.size() > 1000) {
            debugEvents.remove(0);
        }
    }

    public void clearEvents() {
        debugEvents.clear();
    }
}