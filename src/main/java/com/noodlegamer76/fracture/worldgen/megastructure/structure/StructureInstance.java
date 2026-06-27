package com.noodlegamer76.fracture.worldgen.megastructure.structure;

import com.google.common.collect.ImmutableList;
import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.worldgen.megastructure.Node;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.access.WorldAccess;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.placers.Placer;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVar;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVarCache;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StructureInstance {
    private final StructureDefinition definition;
    private final List<Placer> placers = new ArrayList<>();
    private final Map<Node, Map<String, GenVar<?>>> vars = new HashMap<>();
    private Node currentNode;
    private final int maxNodeLevel;

    public StructureInstance(StructureDefinition definition) {
        this.definition = definition;
        this.maxNodeLevel = definition.getMaxNodeLevel();
    }

    public void addPlacer(Placer placer) {
        placers.add(placer);
    }

    public List<Placer> getPlacers() {
        return placers;
    }

    public void generate(WorldAccess access, boolean onlyCenter) {
        definition.generate(access, this, onlyCenter);
    }

    public StructureDefinition getDefinition() {
        return definition;
    }

    public void setGenVar(Node n, GenVar<?> genVar) {
        if (currentNode == null) {
            FractureMod.LOGGER.error("Tried to set GenVar [" + genVar.getName() + "] without a node");
            return;
        }

        vars.computeIfAbsent(currentNode, ignored -> new HashMap<>())
                .put(genVar.getName(), genVar);

        if (genVar.isCacheable()) {
            GenVarCache.instance().addVar(currentNode, genVar);
        }
    }

    public void setGenVar(Node n, String name, Object object) {
        setGenVar(n, new GenVar<>(name, object));
    }

    public void setGenVar(Node n, String name, Object object, boolean cachable) {
        setGenVar(n, new GenVar<>(name, object, cachable));
    }

    public List<GenVar<?>> getGenVars() {
        return vars.values()
                .stream()
                .flatMap(nodeVars -> nodeVars.values().stream())
                .collect(ImmutableList.toImmutableList());
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <T> GenVar<T> getGenVar(Node node, String name, Class<T> type) {
        GenVar<?> raw = getScopedRawGenVar(node, name);

        if (raw == null) {
            for (int level = node.getLevel(); level <= maxNodeLevel; level++) {
                Node scopeNode = new Node(node.getX(), node.getZ(), level);
                T cachedValue = GenVarCache.instance().getRawValue(scopeNode, name, type);

                if (cachedValue != null) {
                    GenVar<T> cachedVar = new GenVar<>(name, cachedValue, true);

                    vars.computeIfAbsent(scopeNode, ignored -> new HashMap<>())
                            .put(name, cachedVar);

                    return cachedVar;
                }
            }

            return null;
        }

        if (!type.isAssignableFrom(raw.getType())) {
            FractureMod.LOGGER.error("Clazz mismatch for GenVar: " + name);
            return null;
        }

        GenVar<T> result = (GenVar<T>) raw;

        if (result.getValue() == null) {
            T cachedValue = getScopedCachedValue(node, name, type);
            if (cachedValue != null) {
                result.setValue(cachedValue);
            }
        }

        return result;
    }

    @Nullable
    private <T> T getScopedCachedValue(Node node, String name, Class<T> type) {
        for (int level = node.getLevel(); level <= maxNodeLevel; level++) {
            Node scopeNode = new Node(node.getX(), node.getZ(), level);
            T cachedValue = GenVarCache.instance().getRawValue(scopeNode, name, type);

            if (cachedValue != null) {
                return cachedValue;
            }
        }

        return null;
    }

    private <T> Node getScopedNodeForCachedValue(Node node, String name, Class<T> type) {
        for (int level = node.getLevel(); level <= maxNodeLevel; level++) {
            Node scopeNode = new Node(node.getX(), node.getZ(), level);

            if (GenVarCache.instance().contains(scopeNode, name)) {
                return scopeNode;
            }
        }

        return node;
    }

    @Nullable
    private GenVar<?> getScopedRawGenVar(Node node, String name) {
        for (int level = node.getLevel(); level <= maxNodeLevel; level++) {
            Node scopeNode = new Node(node.getX(), node.getZ(), level);
            Map<String, GenVar<?>> nodeVars = vars.get(scopeNode);

            if (nodeVars == null) {
                continue;
            }

            GenVar<?> genVar = nodeVars.get(name);

            if (genVar != null) {
                return genVar;
            }
        }

        return null;
    }

    public void clearGenVars() {
        vars.clear();
    }

    public void removeGenVar(String name) {
        if (currentNode == null) {
            return;
        }

        Map<String, GenVar<?>> nodeVars = vars.get(currentNode);

        if (nodeVars == null) {
            return;
        }

        nodeVars.remove(name);

        if (nodeVars.isEmpty()) {
            vars.remove(currentNode);
        }
    }

    public boolean hasGenVar(String name) {
        return getGenVar(currentNode, name, Object.class) != null;
    }

    public void setCurrentNode(Node currentNode) {
        this.currentNode = currentNode;
    }

    public Node getCurrentNode() {
        return currentNode;
    }
}