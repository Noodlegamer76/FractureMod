package com.noodlegamer76.fracture.worldgen.megastructure;

import com.noodlegamer76.fracture.worldgen.megastructure.structure.access.WorldAccess;
import net.minecraft.util.RandomSource;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class StructMath {

    /**
     * Calculates the appropriate quadtree level for a given size.
     * The level is determined based on the size divided by a standard
     * base value of 16, and then converted using a logarithmic scale.
     *
     * @param size the size for which the quadtree level is to be calculated.
     *             Must be a positive integer.
     * @return the calculated quadtree level as an integer.
     */
    public static int getQuadTreeLevel(int size) {
        return (int) Math.ceil(Math.log(size / 16.0) / Math.log(2));
    }

    /**
     * Returns the origin (bottom-left corner) of the quadtree node
     * containing the given world position at the specified level.
     *
     * @param x     world X position
     * @param z     world Z position
     * @param level quadtree level
     * @return Vector2f representing the origin of the quadtree node
     */
    public static Vector2f getNodeOrigin(float x, float z, int level) {
        float nodeSize = 16 * (1 << level); // 16 * 2^level
        float originX = (float) Math.floor(x / nodeSize) * nodeSize;
        float originY = (float) Math.floor(z / nodeSize) * nodeSize;
        return new Vector2f(originX, originY);
    }

    public static RandomSource getNodeRandom(Node node, WorldAccess access, int extra) {
        Vector2f origin = getNodeOrigin(node.getX(), node.getZ(), node.getLevel());
        int nodeSize = getSizeFromLevel(node.getLevel());

        long worldSeed = access.getSeed();

        long seed = (worldSeed << 32) | (worldSeed & 0xFFFFFFFFL);
        seed ^= ((long) origin.x * 341873128712L)
                ^ ((long) origin.y * 132897987541L)
                ^ nodeSize
                ^ ((long) extra * 2654435761L);

        return RandomSource.create(seed);
    }

    /**
     * Returns the size of a quadtree node at the given level.
     *
     * @param level the quadtree level
     * @return the size (width and height) of the node at that level
     */
    public static int getSizeFromLevel(int level) {
        return 16 * (1 << level); // 16 * 2^level
    }

    /**
     * Generates a 3x3 grid of quadtree nodes surrounding the given node,
     * at the level one higher than the input node's level.
     * The grid is constructed relative to the central node, covering offsets
     * of -1, 0, and 1 in both X and Z coordinates, with node size computations
     * based on the level provided.
     *
     * @param node the central quadtree node used as the reference for generating
     *             the 3x3 grid. Must not be null.
     * @return a list of 3x3 quadtree nodes around the specified node at one level higher.
     */
    public static List<Node> get3x3Nodes(Node node) {
        int level = node.getLevel();
        int size = node.getSize();
        int x = node.getX();
        int z = node.getZ();

        List<Node> nodes = new ArrayList<>();

        for (int xOffset = -1; xOffset <= 1; xOffset++) {
            for (int zOffset = -1; zOffset <= 1; zOffset++) {
                nodes.add(new Node(x + xOffset * size, z + zOffset * size, level));
            }
        }

        return nodes;
    }
}
