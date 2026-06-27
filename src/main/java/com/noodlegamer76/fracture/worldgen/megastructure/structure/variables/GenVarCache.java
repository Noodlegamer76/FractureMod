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

    private final Object lock = new Object();
    private final Map<Node, Map<String, Object>> cache = new LinkedHashMap<>();

    public void addVar(Node node, GenVar<?> var) {
        if (!var.isCacheable() || var.getValue() == null) {
            return;
        }

        synchronized (lock) {
            cache.computeIfAbsent(node, n -> new HashMap<>()).put(var.getName(), var.getValue());
            this.removePendingRemoval();
        }
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <V> V getRawValue(Node node, String name, Class<V> type) {
        synchronized (lock) {
            Map<String, Object> nodeVars = cache.get(node);
            if (nodeVars == null) {
                return null;
            }

            Object value = nodeVars.get(name);
            if (value == null || !type.isAssignableFrom(value.getClass())) {
                return null;
            }
            return (V) value;
        }
    }

    public void removePendingRemoval() {
        synchronized (lock) {
            if (cache.size() > MAX_CACHE_SIZE) {
                Iterator<Map.Entry<Node, Map<String, Object>>> iterator = cache.entrySet().iterator();

                while (cache.size() > MAX_CACHE_SIZE && iterator.hasNext()) {
                    iterator.next();
                    iterator.remove();
                }
            }
        }
    }

    public boolean contains(Node node, String name) {
        synchronized (lock) {
            Map<String, Object> nodeVars = cache.get(node);
            return nodeVars != null && nodeVars.containsKey(name);
        }
    }

    public void clear() {
        synchronized (lock) {
            cache.clear();
        }
    }
}