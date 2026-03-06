package com.noodlegamer76.fracture.worldgen.megastructure.structure.structures.citadel.wall;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.worldgen.megastructure.Node;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.Structure;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureInstance;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.access.WorldAccess;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.placers.StackedStructurePlacer;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.placers.StructurePlacer;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.AnchorPoint;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.polygon.Polygon;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.utils.polygon.Wall;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVar;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVarTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class WallPlacementStructure extends Structure {
    public WallPlacementStructure(int priority) {
        super(priority);
    }

    @Override
    public int getMaxSize() {
        return 1023;
    }

    @Override
    public void generate(WorldAccess access, Node n, RandomSource random, StructureInstance instance) {
        GenVar<Wall> wallVar = instance.getGenVar("wall", GenVarTypes.WALL);
        if (wallVar == null) return;

        Wall wall = wallVar.getValue();
        if (wall == null) return;

        MinecraftServer server = access.getServer();
        if (server == null) return;

        long chunkKey = ChunkPos.asLong(access.origin().getX() >> 4, access.origin().getZ() >> 4);

        List<Integer> vertexIndices = wall.vertexChunks().get(chunkKey);
        List<Integer> edgeIndices = wall.edgeChunks().get(chunkKey);

        if ((vertexIndices == null || vertexIndices.isEmpty()) && (edgeIndices == null || edgeIndices.isEmpty())) return;

        ResourceLocation wallSliceLoc = ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "castle/walls/large_slice");
        ResourceLocation wallTopLoc = ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "castle/walls/large_top");
        ResourceLocation towerSliceLoc = ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "castle/towers/large_slice");
        ResourceLocation towerTopLoc = ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "castle/towers/large_top");

        StructureTemplate wallSlice = server.getStructureManager().get(wallSliceLoc).orElse(null);
        StructureTemplate wallTop = server.getStructureManager().get(wallTopLoc).orElse(null);
        StructureTemplate towerSlice = server.getStructureManager().get(towerSliceLoc).orElse(null);
        StructureTemplate towerTop = server.getStructureManager().get(towerTopLoc).orElse(null);

        if (wallSlice == null || wallTop == null || towerSlice == null || towerTop == null) return;

        Polygon polygon = wall.polygon();
        List<Vec3> verts = new ArrayList<>(polygon.vertices());
        int size = verts.size();
        int[] vertexHeight = wall.vertexHeight();
        int[] edgeHeight = wall.edgeHeight();
        boolean[] isTower = wall.isTower();

        if (vertexIndices != null) {
            for (int i : vertexIndices) {
                Vec3 prev = verts.get((i - 1 + size) % size);
                Vec3 curr = verts.get(i);
                Vec3 next = verts.get((i + 1) % size);
                BlockPos pos = new BlockPos((int) curr.x, vertexHeight[i], (int) curr.z);

                if (isTower[i]) {
                    instance.addPlacer(new StructurePlacer(pos, towerTop, new StructurePlaceSettings(), AnchorPoint.BOTTOM_MIDDLE));
                    instance.addPlacer(new StackedStructurePlacer(pos, towerSlice, new StructurePlaceSettings(), AnchorPoint.BOTTOM_MIDDLE, 10, Direction.DOWN));
                } else {
                    Vec3 dir = prev.subtract(next);
                    int angleDegrees = Math.round((float) Math.toDegrees(Math.atan2(dir.z, dir.x)));
                    if (angleDegrees == -180) angleDegrees = 180;

                    StructurePlaceSettings settings = new StructurePlaceSettings();
                    switch (angleDegrees) {
                        case 90: settings.setRotation(Rotation.CLOCKWISE_90); break;
                        case 180: settings.setRotation(Rotation.CLOCKWISE_180); break;
                        case -90: settings.setRotation(Rotation.COUNTERCLOCKWISE_90); break;
                        default:
                    }

                    instance.addPlacer(new StructurePlacer(pos, wallTop, settings, AnchorPoint.BOTTOM_MIDDLE));
                    instance.addPlacer(new StackedStructurePlacer(pos, wallSlice, settings, AnchorPoint.BOTTOM_MIDDLE, 10, Direction.DOWN));
                }
            }
        }

        if (edgeIndices != null) {
            List<Polygon.Edge> edges = polygon.edges();
            for (int i : edgeIndices) {
                Polygon.Edge edge = edges.get(i);
                Vec3 center = edge.start().add(edge.end()).scale(0.5);
                BlockPos centerPos = new BlockPos((int) center.x, edgeHeight[i], (int) center.z);

                StructurePlaceSettings settings = new StructurePlaceSettings();
                switch (edge.angleDegrees()) {
                    case 90: settings.setRotation(Rotation.CLOCKWISE_90); break;
                    case 180: settings.setRotation(Rotation.CLOCKWISE_180); break;
                    case -90: settings.setRotation(Rotation.COUNTERCLOCKWISE_90); break;
                    default:
                }

                instance.addPlacer(new StructurePlacer(centerPos, wallTop, settings, AnchorPoint.BOTTOM_MIDDLE));
                instance.addPlacer(new StackedStructurePlacer(centerPos, wallSlice, settings, AnchorPoint.BOTTOM_MIDDLE, 10, Direction.DOWN));
            }
        }
    }

    @Override
    public boolean shouldGenerate(WorldAccess access, RandomSource random) {
        return true;
    }

    @Override
    public List<GenVar<?>> getGenVariables() {
        return List.of();
    }
}