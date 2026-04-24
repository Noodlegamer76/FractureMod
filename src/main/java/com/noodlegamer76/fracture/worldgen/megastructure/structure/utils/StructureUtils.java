package com.noodlegamer76.fracture.worldgen.megastructure.structure.utils;

import com.google.common.collect.Lists;
import com.ibm.icu.impl.Pair;
import com.noodlegamer76.fracture.mixin.accessor.StructureTemplateAccessor;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.access.WorldAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Clearable;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BitSetDiscreteVoxelShape;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;

import java.util.Iterator;
import java.util.List;

public class StructureUtils {

    public static void placeTemplateInChunk(ServerLevelAccessor level,
                                            StructureTemplate template,
                                            BlockPos structureOrigin,
                                            AnchorPoint anchorPoint,
                                            BlockPos chunkMin,
                                            BlockPos chunkMax,
                                            StructurePlaceSettings settings,
                                            RandomSource random,
                                            int flags) {
        List<StructureTemplate.Palette> palettes = ((StructureTemplateAccessor) template).fracture$getPalettes();
        if (palettes.isEmpty()) return;

        List<StructureTemplate.StructureBlockInfo> blocks = settings.getRandomPalette(palettes, structureOrigin).blocks();
        if (blocks.isEmpty() && settings.isIgnoreEntities()) return;

        BoundingBox boundingBox = settings.getBoundingBox();
        List<BlockPos> liquidsToPlaceLater = Lists.newArrayList();
        List<BlockPos> liquidSources = Lists.newArrayList();
        List<Pair<BlockPos, CompoundTag>> blockEntityInfos = Lists.newArrayList();

        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, minZ = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE, maxZ = Integer.MIN_VALUE;
        BlockPos anchorOffset = getAnchorOffset(template, anchorPoint);
        BlockPos adjustedOrigin = structureOrigin.subtract(anchorOffset);

        StructurePlaceSettings noTransformSettings = settings.copy();
        noTransformSettings.setRotation(Rotation.NONE);
        noTransformSettings.setMirror(Mirror.NONE);

        for (StructureTemplate.StructureBlockInfo info :
                StructureTemplate.processBlockInfos(level, adjustedOrigin, BlockPos.ZERO, noTransformSettings, blocks, template)) {

            BlockPos templateLocal = info.pos().subtract(adjustedOrigin);

            BlockPos relativeToAnchor = templateLocal.subtract(anchorOffset);

            BlockPos rotatedRelative = transformRelative(relativeToAnchor, settings.getMirror(), settings.getRotation());

            BlockPos worldPos = structureOrigin.offset(rotatedRelative.getX(), rotatedRelative.getY(), rotatedRelative.getZ());

            if (worldPos.getX() < chunkMin.getX() || worldPos.getX() > chunkMax.getX()) continue;
            if (worldPos.getY() < level.getMinBuildHeight() || worldPos.getY() > level.getMaxBuildHeight()) continue;
            if (worldPos.getZ() < chunkMin.getZ() || worldPos.getZ() > chunkMax.getZ()) continue;

            if (boundingBox != null && !boundingBox.isInside(worldPos)) continue;

            FluidState fluidState = settings.shouldKeepLiquids() ? level.getFluidState(worldPos) : null;

            BlockState state = info.state().mirror(settings.getMirror()).rotate(settings.getRotation());

            if (info.nbt() != null) {
                BlockEntity be = level.getBlockEntity(worldPos);
                Clearable.tryClear(be);
                level.setBlock(worldPos, Blocks.BARRIER.defaultBlockState(), 20);
            }

            if (level.setBlock(worldPos, state, flags)) {
                minX = Math.min(minX, worldPos.getX());
                minY = Math.min(minY, worldPos.getY());
                minZ = Math.min(minZ, worldPos.getZ());
                maxX = Math.max(maxX, worldPos.getX());
                maxY = Math.max(maxY, worldPos.getY());
                maxZ = Math.max(maxZ, worldPos.getZ());

                if (info.nbt() != null) {
                    blockEntityInfos.add(Pair.of(worldPos, info.nbt()));
                    BlockEntity be = level.getBlockEntity(worldPos);
                    if (be instanceof RandomizableContainerBlockEntity) {
                        info.nbt().putLong("LootTableSeed", random.nextLong());
                    }
                    if (be != null) be.load(info.nbt());
                }

                if (fluidState != null) {
                    if (state.getFluidState().isSource()) {
                        liquidSources.add(worldPos);
                    } else if (state.getBlock() instanceof LiquidBlockContainer) {
                        ((LiquidBlockContainer) state.getBlock()).placeLiquid(level, worldPos, state, fluidState);
                        if (!fluidState.isSource()) liquidsToPlaceLater.add(worldPos);
                    }
                }
            }
        }

        boolean placedLiquids;
        Direction[] directions = new Direction[]{Direction.UP, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

        do {
            placedLiquids = false;
            Iterator<BlockPos> iterator = liquidsToPlaceLater.iterator();
            while (iterator.hasNext()) {
                BlockPos pos = iterator.next();
                FluidState current = level.getFluidState(pos);

                for (Direction dir : directions) {
                    BlockPos neighbor = pos.relative(dir);
                    FluidState neighborFluid = level.getFluidState(neighbor);
                    if (neighborFluid.isSource() && !liquidSources.contains(neighbor)) current = neighborFluid;
                }

                if (current.isSource()) {
                    BlockState state = level.getBlockState(pos);
                    Block block = state.getBlock();
                    if (block instanceof LiquidBlockContainer) {
                        ((LiquidBlockContainer) block).placeLiquid(level, pos, state, current);
                        placedLiquids = true;
                        iterator.remove();
                    }
                }
            }
        } while (placedLiquids && !liquidsToPlaceLater.isEmpty());

        if (minX <= maxX) {
            if (!settings.getKnownShape()) {
                DiscreteVoxelShape shape = new BitSetDiscreteVoxelShape(maxX - minX + 1, maxY - minY + 1, maxZ - minZ + 1);
                for (Pair<BlockPos, CompoundTag> pair : blockEntityInfos) {
                    BlockPos pos = pair.first;
                    shape.fill(pos.getX() - minX, pos.getY() - minY, pos.getZ() - minZ);
                }
                StructureTemplate.updateShapeAtEdge(level, flags, shape, minX, minY, minZ);
            }

            for (Pair<BlockPos, CompoundTag> pair : blockEntityInfos) {
                BlockPos pos = pair.first;
                if (!settings.getKnownShape()) {
                    BlockState current = level.getBlockState(pos);
                    BlockState updated = Block.updateFromNeighbourShapes(current, level, pos);
                    if (current != updated) level.setBlock(pos, updated, flags & -2 | 16);
                    level.blockUpdated(pos, updated.getBlock());
                }

                if (pair.second != null) {
                    BlockEntity be = level.getBlockEntity(pos);
                    if (be != null) be.setChanged();
                }
            }
        }

        if (!settings.isIgnoreEntities()) {
            ((StructureTemplateAccessor) template).fracture$addEntitiesToWorld(level, structureOrigin, settings);
        }
    }

    private static BlockPos transformRelative(BlockPos in, Mirror mirror, Rotation rot) {
        int x = in.getX();
        int y = in.getY();
        int z = in.getZ();

        if (mirror == Mirror.LEFT_RIGHT) {
            x = -x;
        } else if (mirror == Mirror.FRONT_BACK) {
            z = -z;
        }

        switch (rot) {
            case CLOCKWISE_90:
                return new BlockPos(-z, y, x);
            case CLOCKWISE_180:
                return new BlockPos(-x, y, -z);
            case COUNTERCLOCKWISE_90:
                return new BlockPos(z, y, -x);
            default:
                return new BlockPos(x, y, z);
        }
    }


    public static int getLongestAxis(BlockPos a, BlockPos b) {
        int dx = Math.abs(a.getX() - b.getX());
        int dy = Math.abs(a.getY() - b.getY());
        int dz = Math.abs(a.getZ() - b.getZ());

        return Math.max(dx, Math.max(dy, dz));
    }

    public static AABB getChunkIntersection(WorldAccess access, AABB structure) {
        ChunkPos cp = new ChunkPos(access.origin());
        int chunkMinX = cp.getMinBlockX();
        int chunkMaxX = cp.getMaxBlockX() + 1;
        int chunkMinZ = cp.getMinBlockZ();
        int chunkMaxZ = cp.getMaxBlockZ() + 1;

        AABB chunk = new AABB(chunkMinX, access.getMinBuildHeight(), chunkMinZ,
                chunkMaxX, access.getMaxBuildHeight(), chunkMaxZ);

        AABB intersection = structure.intersect(chunk);

        if (intersection.minX >= intersection.maxX ||
                intersection.minY >= intersection.maxY ||
                intersection.minZ >= intersection.maxZ) {
            return null;
        }
        return intersection;
    }



    public static BlockPos getAnchorOffset(StructureTemplate template, AnchorPoint anchor) {
        Vec3i size = template.getSize();

        int sizeX = size.getX();
        int sizeY = size.getY();
        int sizeZ = size.getZ();

        switch (anchor) {
            case BOTTOM_MIDDLE:
                return new BlockPos(
                        getCenteredOffset(sizeX),
                        0,
                        getCenteredOffset(sizeZ)
                );

            case CENTER:
                return new BlockPos(
                        getCenteredOffset(sizeX),
                        getCenteredOffset(sizeY),
                        getCenteredOffset(sizeZ)
                );

            case CORNER:
            default:
                return BlockPos.ZERO;
        }
    }

    private static int getCenteredOffset(int size) {
        return (size - 1) / 2;
    }

    public static AABB calculateBoundingBox(StructureTemplate template,
                                            BlockPos structureOrigin,
                                            AnchorPoint anchorPoint,
                                            StructurePlaceSettings settings) {
        Vec3i size = template.getSize();

        int width = size.getX();
        int height = size.getY();
        int depth = size.getZ();

        switch (settings.getRotation()) {
            case CLOCKWISE_90, COUNTERCLOCKWISE_90 -> {
                int temp = width;
                width = depth;
                depth = temp;
            }
            default -> {}
        }

        int minX = structureOrigin.getX();
        int minY = structureOrigin.getY();
        int minZ = structureOrigin.getZ();

        switch (anchorPoint) {
            case CORNER -> {
                //Nothing obviously NERD
            }

            case BOTTOM_MIDDLE -> {
                minX -= (width - 1) / 2;
                minZ -= (depth - 1) / 2;
            }

            case CENTER -> {
                minX -= (width - 1) / 2;
                minY -= (height - 1) / 2;
                minZ -= (depth - 1) / 2;
            }
        }

        int maxX = minX + width;
        int maxY = minY + height;
        int maxZ = minZ + depth;

        return new AABB(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public static AABB getChunkAABB(ChunkPos chunkPos, WorldAccess worldAccess) {
        return new AABB(
                chunkPos.getMinBlockX(),
                worldAccess.getMinBuildHeight(),
                chunkPos.getMinBlockZ(),
                chunkPos.getMaxBlockX() + 1,
                worldAccess.getMaxBuildHeight(),
                chunkPos.getMaxBlockZ() + 1
        );
    }

    public static int getSurfaceHeight(ServerLevel serverLevel, int x, int z) {
        int minY = serverLevel.getMinBuildHeight();
        int maxY = serverLevel.getMaxBuildHeight();
        int step = 8;

        DensityFunction finalDensity = serverLevel.getChunkSource().randomState().router().finalDensity();

        int coarseSurface = minY;
        for (int y = maxY - 1; y >= minY; y -= step) {
            if (finalDensity.compute(new DensityFunction.SinglePointContext(x, y, z)) > 0) {
                coarseSurface = y;
                break;
            }
        }

        int fineStart = Math.min(coarseSurface + step, maxY - 1);
        for (int y = fineStart; y >= coarseSurface - step && y >= minY; y--) {
            if (finalDensity.compute(new DensityFunction.SinglePointContext(x, y, z)) > 0) {
                return y;
            }
        }

        return coarseSurface;
    }

    public static BlockPos getDirectionalOffset(Direction dir, int sizeX, int sizeY, int sizeZ) {
        return switch (dir) {
            case UP -> new BlockPos(0, sizeY, 0);
            case DOWN -> new BlockPos(0, -sizeY, 0);
            case NORTH -> new BlockPos(0, 0, -sizeZ);
            case SOUTH -> new BlockPos(0, 0, sizeZ);
            case EAST -> new BlockPos(sizeX, 0, 0);
            case WEST -> new BlockPos(-sizeX, 0, 0);
        };
    }
}
