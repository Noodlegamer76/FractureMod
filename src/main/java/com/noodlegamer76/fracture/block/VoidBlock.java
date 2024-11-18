package com.noodlegamer76.fracture.block;

import com.noodlegamer76.fracture.entity.block.VoidBlockEntity;
import com.noodlegamer76.fracture.particles.InitParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;

public class VoidBlock extends Block implements EntityBlock {
    public VoidBlock(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new VoidBlockEntity(pPos, pState);
    }
    @Override
    public boolean skipRendering(BlockState pState, BlockState pAdjacentState, Direction pDirection) {
        return true;
    }

    @Override
    public boolean addLandingEffects(BlockState state1, ServerLevel level, BlockPos pos, BlockState state2, LivingEntity entity, int numberOfParticles) {
        Vector3d vec = new Vector3d(entity.getX(), entity.getY(), entity.getZ());
        vec.x = (Math.random() - 0.5) / 2;
        vec.y = (Math.random() - 0.75) / 2;
        vec.z = (Math.random() - 0.5) / 2;
        vec.normalize();
        level.addParticle(InitParticles.VOID_PARTICLES.get(),
                entity.getX(), entity.getY(), entity.getZ(),
                vec.x(), vec.y(), vec.z());
        return true;
    }

    @Override
    public boolean addRunningEffects(BlockState state, Level level, BlockPos pos, Entity entity) {
        Vector3d vec = new Vector3d(entity.getX(), entity.getY(), entity.getZ());
        vec.x = (Math.random() - 0.5) / 2;
        vec.y = (Math.random() - 0.75) / 2;
        vec.z = (Math.random() - 0.5) / 2;
        vec.normalize();
        level.addParticle(InitParticles.VOID_PARTICLES.get(),
                entity.getX(), entity.getY(), entity.getZ(),
                vec.x(), vec.y(), vec.z());
        return true;
    }
}
