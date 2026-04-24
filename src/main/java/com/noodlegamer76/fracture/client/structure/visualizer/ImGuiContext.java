package com.noodlegamer76.fracture.client.structure.visualizer;

import com.noodlegamer76.fracture.client.structure.MegastructureViewer;

public class ImGuiContext {
    public final MegastructureViewer editor;
    public final MegastructureOverlayTab overlay;

    public final int selectedDefinition;
    public final int selectedStructure;

    public ImGuiContext(MegastructureViewer editor,
                        MegastructureOverlayTab overlay,
                        int selectedDefinition,
                        int selectedStructure) {
        this.editor = editor;
        this.overlay = overlay;
        this.selectedDefinition = selectedDefinition;
        this.selectedStructure = selectedStructure;
    }
}