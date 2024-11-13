package com.noodlegamer76.fracture.entity.block;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class VoidBlockEntity extends BlockEntity {
    public VoidBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntities.VOID_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public AABB getRenderBoundingBox() {
        float renderDistance = Minecraft.getInstance().gameRenderer.getRenderDistance();
        return new AABB(new Vec3(renderDistance + getBlockPos().getX(), renderDistance + getBlockPos().getY(), renderDistance + getBlockPos().getZ()),
                new Vec3(-renderDistance + getBlockPos().getX(), -renderDistance + getBlockPos().getY(), -renderDistance + getBlockPos().getZ()));
    }
}
