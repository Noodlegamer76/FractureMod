package com.noodlegamer76.fracture.worldgen.megastructure;

import java.util.Objects;

public class Node {
    private final int x;
    private final int z;
    private final int level;
    private final int size;

    public Node(int worldX, int worldZ, int level) {
        int nodeSize = 16 << level;
        this.x = Math.floorDiv(worldX, nodeSize) * nodeSize;
        this.z = Math.floorDiv(worldZ, nodeSize) * nodeSize;
        this.level = level;
        this.size = nodeSize;
    }

    public int getLevel() {
        return level;
    }

    public int getSize() {
        return size;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Node node)) return false;
        return x == node.x && z == node.z && level == node.level;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, z, level);
    }

}
