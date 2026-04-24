package com.noodlegamer76.fracture.worldgen.megastructure.structure.variables;

import com.noodlegamer76.fracture.worldgen.megastructure.Node;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class GenVarCache {
    private static final GenVarCache INSTANCE = new GenVarCache();

    public static GenVarCache instance() {
        return INSTANCE;
    }

    private GenVarCache() {
    }

    public static final int MAX_CACHE_SIZE = 100;
    private final Map<Node, Map<String, GenVar<?>>> cache = new LinkedHashMap<>();

    public void addVar(Node node, GenVar<?> var) {
        if (!var.isCacheable()) {
            return;
        }
        cache.computeIfAbsent(node, n -> new HashMap<>()).put(var.getName(), var);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <T extends GenVar<V>, V> T getVar(Node node, String name, Class<V> type) {
        GenVar<?> var = cache.getOrDefault(node, Map.of()).get(name);
        if (var == null || var.getType() != type) {
            return null;
        }
        return (T) var;
    }

    public void removePendingRemoval() {
        if (cache.size() > MAX_CACHE_SIZE) {
            Iterator<Map.Entry<Node, Map<String, GenVar<?>>>> iterator = cache.entrySet().iterator();

            while (cache.size() > MAX_CACHE_SIZE && iterator.hasNext()) {
                iterator.next();
                iterator.remove();
            }
        }
    }

    public boolean contains(Node node, String name) {
        return cache.getOrDefault(node, Map.of()).containsKey(name);
    }

    public void clear() {
        cache.clear();
    }
}
