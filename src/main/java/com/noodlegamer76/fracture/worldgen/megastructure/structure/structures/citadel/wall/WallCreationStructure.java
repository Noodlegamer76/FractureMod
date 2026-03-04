package com.noodlegamer76.fracture.worldgen.megastructure.structure.structures.citadel.wall;

import com.noodlegamer76.fracture.worldgen.megastructure.Node;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.Structure;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureInstance;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.StructureUtils;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.polygon.Polygon;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.polygon.Wall;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.polygon.WallGenerator;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVar;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVarSerializers;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class WallCreationStructure extends Structure {
    private final GenVar<Wall> wallVar = new GenVar<>(null, GenVarSerializers.WALL, "wall");
    private static final double COLLINEAR_EPSILON = 1e-6;
    private static final int HEIGHT_STEP = 4;
    private static final int MAX_OVERHANG = 16;

    public WallCreationStructure(int priority) {
        super(priority);
    }

    @Override
    public int getMaxSize() {
        return 255;
    }

    @Override
    public void generate(FeaturePlaceContext<NoneFeatureConfiguration> ctx, Node n, RandomSource random, StructureInstance instance) {
        double edgeLength = 18;
        int angle = 90;
        int maxTries = 900;
        int wallHeight = 10;

        WallGenerator.Settings wallSettings = new WallGenerator.Settings(
                n.getX(),
                n.getZ(),
                n.getX() + getMaxSize(),
                n.getZ() + getMaxSize(),
                edgeLength,
                angle,
                maxTries
        );
        Polygon polygon = WallGenerator.generate(wallSettings, random);

        if (polygon == null) return;

        List<Vec3> verts = new ArrayList<>(polygon.vertices());
        int size = verts.size();

        int[] surface = new int[size];
        int[] preferredHeight = new int[size];
        for (int i = 0; i < size; i++) {
            Vec3 v = verts.get(i);
            surface[i] = StructureUtils.getSurfaceHeight(ctx.level().getLevel(), (int) v.x, (int) v.z);
            surface[i] = Math.max(surface[i], 64);
            int ideal = surface[i] + wallHeight;
            int capped = Math.min(ideal, surface[i] + MAX_OVERHANG);
            preferredHeight[i] = snapDown(capped, HEIGHT_STEP);
        }

        int[] edgeHeight = new int[size];
        for (int i = 0; i < size; i++) {
            edgeHeight[i] = Math.max(preferredHeight[i], preferredHeight[(i + 1) % size]);
        }

        boolean[] isTower = new boolean[size];
        int[] vertexHeight = new int[size];
        for (int i = 0; i < size; i++) {
            Vec3 prev = verts.get((i - 1 + size) % size);
            Vec3 curr = verts.get(i);
            Vec3 next = verts.get((i + 1) % size);

            int leftEdge = edgeHeight[(i - 1 + size) % size];
            int rightEdge = edgeHeight[i];

            boolean corner = !isCollinear(prev, curr, next);
            boolean heightChange = leftEdge != rightEdge;

            isTower[i] = corner || heightChange;
            vertexHeight[i] = isTower[i] ? Math.max(leftEdge, rightEdge) : leftEdge;
        }

        wallVar.setValue(new Wall(polygon, surface, edgeHeight, vertexHeight, isTower, edgeLength));
    }

    private static int snapDown(int value, int step) {
        return (value / step) * step;
    }

    private boolean isCollinear(Vec3 prev, Vec3 curr, Vec3 next) {
        Vec3 d1 = curr.subtract(prev);
        Vec3 d2 = next.subtract(curr);
        double cross = (d1.x * d2.z) - (d1.z * d2.x);
        double dot = d1.x * d2.x + d1.z * d2.z;
        return Math.abs(cross) < COLLINEAR_EPSILON && dot > 0;
    }

    @Override
    public boolean shouldGenerate(FeaturePlaceContext<NoneFeatureConfiguration> ctx, RandomSource random) {
        return true;
    }

    @Override
    public List<GenVar<?>> getGenVariables() {
        return List.of(
                wallVar
        );
    }
}