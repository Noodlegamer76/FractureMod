package com.noodlegamer76.fracture.client.structure;

import com.noodlegamer76.fracture.client.structure.visualizer.ImGuiContext;
import com.noodlegamer76.fracture.client.structure.visualizer.MegastructureOverlayTab;
import com.noodlegamer76.fracture.worldgen.megastructure.MegaStructureGenerator;
import com.noodlegamer76.fracture.worldgen.megastructure.rules.StructureRule;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.Structure;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureDefinition;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureInstance;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.Structures;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.access.BlankWorldAccess;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.placers.Placer;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVar;
import imgui.ImGui;
import imgui.flag.ImGuiDataType;
import imgui.flag.ImGuiTreeNodeFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import imgui.type.ImInt;
import imgui.type.ImLong;
import net.minecraft.core.BlockPos;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class MegastructureViewer {
    public interface SnapshotProvider {
        List<StructureDefinition> getDefinitions();
        StructureInstance getLastInstance();
        List<DebugEvent> getDebugEvents();
    }

    public static class DebugEvent {
        public final long timestamp;
        public final String type;
        public final String source;
        public final String message;
        public final Map<String, Object> data;

        public DebugEvent(String type, String source, String message) {
            this(type, source, message, Map.of());
        }

        public DebugEvent(String type, String source, String message, Map<String, Object> data) {
            this.timestamp = System.currentTimeMillis();
            this.type = type;
            this.source = source;
            this.message = message;
            this.data = new LinkedHashMap<>(data);
        }
    }

    private final MegastructureOverlayTab overlayTab = new MegastructureOverlayTab();

    private final SnapshotProvider provider;

    private final ImBoolean showNodes = new ImBoolean(true);
    private final ImBoolean showRules = new ImBoolean(true);
    private final ImBoolean showVariables = new ImBoolean(true);
    private final ImBoolean showPlacements = new ImBoolean(true);
    private final ImBoolean showEvents = new ImBoolean(true);
    private final ImBoolean autoRefresh = new ImBoolean(false);
    private final ImBoolean showRawData = new ImBoolean(false);

    private final ImInt selectedDefinitionIndex = new ImInt(0);
    private final ImInt selectedStructureIndex = new ImInt(0);
    private final ImInt selectedEventIndex = new ImInt(-1);
    private final ImLong seed = new ImLong(0L);
    private BlockPos origin = new BlockPos(0, 0, 0);

    public MegastructureViewer(SnapshotProvider provider) {
        this.provider = Objects.requireNonNull(provider, "provider");
    }

    public void draw() {
        final List<StructureDefinition> definitions = safeList(provider.getDefinitions());
        final StructureInstance instance = provider.getLastInstance();
        final List<DebugEvent> events = safeList(provider.getDebugEvents());

        ImGui.setNextWindowSize(1600, 900);
        ImGui.setNextWindowPos(0, 0);
        ImGui.begin("Megastructure Editor", ImGuiWindowFlags.AlwaysUseWindowPadding);

        drawTopBar(definitions, instance);
        ImGui.separator();

        ImGui.columns(2, "MegastructureColumns", true);
        drawLeftPane(definitions);
        ImGui.nextColumn();
        drawRightPane(definitions, instance, events);
        ImGui.columns(1);

        if (showEvents.get()) {
            ImGui.separator();
            drawEventTimeline(events);
        }

        ImGui.end();

        ImGuiContext ctx = new ImGuiContext(
                this,
                overlayTab,
                selectedDefinitionIndex.get(),
                selectedStructureIndex.get()
        );

        overlayTab.setSelection(
                selectedDefinitionIndex.get(),
                selectedStructureIndex.get()
        );

        overlayTab.renderGui(ctx, instance);
    }

    private void drawTopBar(List<StructureDefinition> definitions, StructureInstance instance) {
        ImGui.text("Definitions: " + definitions.size());
        ImGui.sameLine();
        ImGui.checkbox("Auto Refresh", autoRefresh);
        ImGui.sameLine();
        ImGui.checkbox("Nodes", showNodes);
        ImGui.sameLine();
        ImGui.checkbox("Rules", showRules);
        ImGui.sameLine();
        ImGui.checkbox("Vars", showVariables);
        ImGui.sameLine();
        ImGui.checkbox("Placements", showPlacements);
        ImGui.sameLine();
        ImGui.checkbox("Events", showEvents);
        ImGui.sameLine();
        ImGui.checkbox("Raw Data", showRawData);

        ImGui.separator();

        if (ImGui.inputScalar("Seed", ImGuiDataType.S64, seed)) {
            // Seed stored in ImLong
        }
        ImGui.sameLine();
        if (ImGui.button("Regenerate Same Seed")) {
            regenerateDummyInstance();
        }
        ImGui.sameLine();
        if (ImGui.button("Regenerate New Seed")) {
            seed.set(System.currentTimeMillis());
            regenerateDummyInstance();
        }

        if (instance != null) {
            ImGui.sameLine();
            ImGui.textColored(0, 255, 0, 255, "Instance Active");
        }
    }

    private void drawLeftPane(List<StructureDefinition> definitions) {
        ImGui.beginChild("DefinitionTree", 0, 0, true);
        ImGui.text("Structure Definitions");
        ImGui.separator();

        if (definitions.isEmpty()) {
            ImGui.textDisabled("No definitions available.");
            ImGui.endChild();
            return;
        }

        for (int defIdx = 0; defIdx < definitions.size(); defIdx++) {
            StructureDefinition def = definitions.get(defIdx);
            List<Structure> structures = def.getStructures();

            int defFlags = ImGuiTreeNodeFlags.SpanFullWidth | ImGuiTreeNodeFlags.OpenOnArrow;
            if (selectedDefinitionIndex.get() == defIdx) {
                defFlags |= ImGuiTreeNodeFlags.Selected;
            }

            String defLabel = "Definition #" + defIdx + " (" + structures.size() + " structures)";
            boolean defOpen = ImGui.treeNodeEx(defLabel + "##def" + defIdx, defFlags);

            if (ImGui.isItemClicked()) {
                selectedDefinitionIndex.set(defIdx);
            }

            if (defOpen) {
                for (int structIdx = 0; structIdx < structures.size(); structIdx++) {
                    Structure structure = structures.get(structIdx);

                    int structFlags = ImGuiTreeNodeFlags.SpanFullWidth | ImGuiTreeNodeFlags.Leaf;
                    if (selectedStructureIndex.get() == structIdx && selectedDefinitionIndex.get() == defIdx) {
                        structFlags |= ImGuiTreeNodeFlags.Selected;
                    }

                    String structLabel = String.format("Structure [P:%d L:%d ID:%d]",
                            structure.getPriority(),
                            structure.getNodeLevel(),
                            structure.getId());

                    boolean structOpen = ImGui.treeNodeEx(structLabel + "##struct" + structIdx, structFlags);

                    if (ImGui.isItemClicked()) {
                        selectedDefinitionIndex.set(defIdx);
                        selectedStructureIndex.set(structIdx);
                    }

                    if (structOpen) {
                        ImGui.text("Rules: " + structure.getRules().size());
                        ImGui.treePop();
                    }
                }
                ImGui.treePop();
            }
        }

        ImGui.endChild();
    }

    private void drawRightPane(List<StructureDefinition> definitions,
                               StructureInstance instance,
                               List<DebugEvent> events) {
        ImGui.beginChild("DetailsPane", 0, 0, true);

        int defIdx = selectedDefinitionIndex.get();
        if (defIdx < 0 || defIdx >= definitions.size()) {
            ImGui.textDisabled("Select a structure definition.");
            ImGui.endChild();
            return;
        }

        StructureDefinition definition = definitions.get(defIdx);
        List<Structure> structures = definition.getStructures();

        int structIdx = selectedStructureIndex.get();
        if (structIdx < 0 || structIdx >= structures.size()) {
            ImGui.text("Definition #" + defIdx);
            ImGui.separator();
            ImGui.text("Total Structures: " + structures.size());
            ImGui.textDisabled("Select a structure to view details.");
            ImGui.endChild();
            return;
        }

        Structure structure = structures.get(structIdx);

        ImGui.text("Structure Details");
        ImGui.separator();
        ImGui.text("ID: " + structure.getId());
        ImGui.text("Priority: " + structure.getPriority());
        ImGui.text("Node Level: " + structure.getNodeLevel());
        ImGui.text("Node Size: " + (16 << structure.getNodeLevel()) + " blocks");

        if (showRules.get()) {
            ImGui.separator();
            ImGui.text("Rules (" + structure.getRules().size() + ")");
            if (structure.getRules().isEmpty()) {
                ImGui.textDisabled("(no rules)");
            } else {
                for (int i = 0; i < structure.getRules().size(); i++) {
                    StructureRule rule = structure.getRules().get(i);
                    String ruleName = rule.getClass().getSimpleName();
                    ImGui.bulletText("[" + i + "] " + ruleName + ": " + rule.getDescription());
                    
                    if (showRawData.get()) {
                        ImGui.indent();
                        ImGui.textDisabled("Class: " + rule.getClass().getName());
                        ImGui.unindent();
                    }
                }
            }
        }

        if (showVariables.get() && instance != null) {
            ImGui.separator();
            ImGui.text("Instance Variables");
            List<GenVar<?>> vars = instance.getGenVars();
            
            if (vars.isEmpty()) {
                ImGui.textDisabled("(no variables)");
            } else {
                for (GenVar<?> var : vars) {
                    String varLabel = var.getName();
                    if (var.isCacheable()) {
                        varLabel += " [cacheable]";
                    }

                    drawValue(varLabel, var.getValue());
                }
            }
        }

        if (showPlacements.get() && instance != null) {
            ImGui.separator();
            ImGui.text("Placers (" + instance.getPlacers().size() + ")");
            
            if (instance.getPlacers().isEmpty()) {
                ImGui.textDisabled("(no placers)");
            } else {
                for (int i = 0; i < instance.getPlacers().size(); i++) {
                    Placer placer = instance.getPlacers().get(i);
                    String placerName = placer.getClass().getSimpleName();
                    ImGui.bulletText("[" + i + "] " + placerName);
                    
                    if (showRawData.get()) {
                        ImGui.indent();
                        ImGui.text("Bounds: " + placer.getBoundingBox());
                        ImGui.unindent();
                    }
                }
            }
        }

        if (showNodes.get()) {
            ImGui.separator();
            ImGui.text("Node Information");
            int nodeSize = 16 << structure.getNodeLevel();
            ImGui.text("3x3 Grid Coverage: " + (nodeSize * 3) + " x " + (nodeSize * 3) + " blocks");
            ImGui.textDisabled("Use overlay renderer to visualize node grid.");
        }

        ImGui.endChild();
    }

    @SuppressWarnings("unchecked")
    private <T> void drawValue(String label, T value) {
        if (value == null) {
            ImGui.text(label + ": null");
            return;
        }

        StructureValueView<T> view =
                (StructureValueView<T>) StructureInspectorRegistry.get(value.getClass());

        if (view != null) {
            view.draw(label, value);
            return;
        }

        if (ImGui.treeNode(label + "##" + System.identityHashCode(value))) {
            ImGui.textWrapped(String.valueOf(value));
            ImGui.treePop();
        }
    }

    private void drawEventTimeline(List<DebugEvent> events) {
        ImGui.beginChild("EventTimeline", 0, 220, true);
        ImGui.text("Event Timeline (" + events.size() + " events)");
        ImGui.separator();

        if (events.isEmpty()) {
            ImGui.textDisabled("No events recorded.");
            ImGui.endChild();
            return;
        }

        ImGui.beginChild("EventList", 0, 160, true);
        for (int i = 0; i < events.size(); i++) {
            DebugEvent event = events.get(i);
            boolean selected = selectedEventIndex.get() == i;
            String label = "#" + i + " [" + event.type + "] " + event.source + "##event" + i;
            
            if (ImGui.selectable(label, selected)) {
                selectedEventIndex.set(i);
            }
        }
        ImGui.endChild();

        int idx = selectedEventIndex.get();
        if (idx >= 0 && idx < events.size()) {
            DebugEvent event = events.get(idx);
            ImGui.separator();
            ImGui.text("Type: " + event.type);
            ImGui.text("Source: " + event.source);
            ImGui.textWrapped("Message: " + event.message);
            
            if (!event.data.isEmpty()) {
                if (ImGui.treeNode("Event Data")) {
                    for (Map.Entry<String, Object> entry : event.data.entrySet()) {
                        drawValue(entry.getKey(), entry.getValue());
                    }
                    ImGui.treePop();
                }
            }
        }

        ImGui.endChild();
    }

    public void setOrigin(BlockPos origin) {
        this.origin = origin;
    }

    public BlockPos getOrigin() {
        return origin;
    }

    public void regenerateDummyInstance() {
        StructureDefinition def = Structures.getInstance().getStructures().get(0);
        StructureInstance instance = new StructureInstance(def);

        BlankWorldAccess access = new BlankWorldAccess(
                seed.get(),
                getOrigin()
        );

        instance.generate(access, true);

        MegaStructureGenerator.setLastInstance(instance);
    }

    private static boolean isAtomic(Object value) {
        return value instanceof Number
                || value instanceof CharSequence
                || value instanceof Boolean
                || value instanceof Enum<?>;
    }

    private static <T> List<T> safeList(List<T> list) {
        return list == null ? List.of() : list;
    }
}
