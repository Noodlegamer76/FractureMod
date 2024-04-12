package com.noodlegamer76.fracture.entity.block;

import com.noodlegamer76.fracture.block.InitBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class FleshyGroupSpawnerEntity extends BlockEntity {
    BaseSpawner spawner1 = new BaseSpawner() {

        @Override
        public void broadcastEvent(Level pLevel, BlockPos pPos, int pEventId) {
            level.blockEvent(pPos, InitBlocks.FLESHY_GROUP_SPAWNER.get(), pEventId, 0);
        }

        public void setNextSpawnData(@Nullable Level level, BlockPos blockPos, SpawnData spawnData) {
            super.setNextSpawnData(level, blockPos, spawnData);
            if (level != null) {
                BlockState blockstate = level.getBlockState(blockPos);
                level.sendBlockUpdated(blockPos, blockstate, blockstate, 4);
            }

        }

        @org.jetbrains.annotations.Nullable
        public net.minecraft.world.level.block.entity.BlockEntity getSpawnerBlockEntity(){ return FleshyGroupSpawnerEntity.this; }
    };
    BaseSpawner spawner2 = new BaseSpawner() {

        @Override
        public void broadcastEvent(Level pLevel, BlockPos pPos, int pEventId) {
            level.blockEvent(pPos, InitBlocks.FLESHY_GROUP_SPAWNER.get(), pEventId, 0);
        }

        public void setNextSpawnData(@Nullable Level level, BlockPos blockPos, SpawnData spawnData) {
            super.setNextSpawnData(level, blockPos, spawnData);
            if (level != null) {
                BlockState blockstate = level.getBlockState(blockPos);
                level.sendBlockUpdated(blockPos, blockstate, blockstate, 4);
            }

        }

        @org.jetbrains.annotations.Nullable
        public net.minecraft.world.level.block.entity.BlockEntity getSpawnerBlockEntity(){ return FleshyGroupSpawnerEntity.this; }
    };
    BaseSpawner spawner3 = new BaseSpawner() {

        @Override
        public void broadcastEvent(Level pLevel, BlockPos pPos, int pEventId) {
            level.blockEvent(pPos, InitBlocks.FLESHY_GROUP_SPAWNER.get(), pEventId, 0);
        }

        public void setNextSpawnData(@Nullable Level level, BlockPos blockPos, SpawnData spawnData) {
            super.setNextSpawnData(level, blockPos, spawnData);
            if (level != null) {
                BlockState blockstate = level.getBlockState(blockPos);
                level.sendBlockUpdated(blockPos, blockstate, blockstate, 4);
            }

        }

        @org.jetbrains.annotations.Nullable
        public net.minecraft.world.level.block.entity.BlockEntity getSpawnerBlockEntity(){ return FleshyGroupSpawnerEntity.this; }
    };
    BaseSpawner spawner4 = new BaseSpawner() {

        @Override
        public void broadcastEvent(Level pLevel, BlockPos pPos, int pEventId) {
            level.blockEvent(pPos, InitBlocks.FLESHY_GROUP_SPAWNER.get(), pEventId, 0);
        }

        public void setNextSpawnData(@Nullable Level level, BlockPos blockPos, SpawnData spawnData) {
            super.setNextSpawnData(level, blockPos, spawnData);
            if (level != null) {
                BlockState blockstate = level.getBlockState(blockPos);
                level.sendBlockUpdated(blockPos, blockstate, blockstate, 4);
            }

        }

        @org.jetbrains.annotations.Nullable
        public net.minecraft.world.level.block.entity.BlockEntity getSpawnerBlockEntity(){ return FleshyGroupSpawnerEntity.this; }
    };

    public FleshyGroupSpawnerEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntities.FLESHY_GROUP_SPAWNER.get(), pPos, pBlockState);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, FleshyGroupSpawnerEntity pBlockEntity) {
        if (pLevel.isClientSide) {
            System.out.println("TICKING CLIENT SPAWNER");
            pBlockEntity.spawner1.clientTick(pLevel, pPos);
            pBlockEntity.spawner2.clientTick(pLevel, pPos);
            pBlockEntity.spawner3.clientTick(pLevel, pPos);
            pBlockEntity.spawner4.clientTick(pLevel, pPos);
        }

        else {
            System.out.println("TICKING SERVER SPAWNER");
            pBlockEntity.spawner1.serverTick((ServerLevel)pLevel, pPos);
            pBlockEntity.spawner2.serverTick((ServerLevel)pLevel, pPos);
            pBlockEntity.spawner3.serverTick((ServerLevel)pLevel, pPos);
            pBlockEntity.spawner4.serverTick((ServerLevel)pLevel, pPos);
        }
    }

    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.spawner1.load(this.level, this.worldPosition, pTag);
        this.spawner2.load(this.level, this.worldPosition, pTag);
        this.spawner3.load(this.level, this.worldPosition, pTag);
        this.spawner4.load(this.level, this.worldPosition, pTag);
    }

    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        this.spawner1.save(pTag);
        this.spawner2.save(pTag);
        this.spawner3.save(pTag);
        this.spawner4.save(pTag);
    }

    public CompoundTag getUpdateTag() {
        CompoundTag compoundtag = this.saveWithoutMetadata();
        compoundtag.remove("SpawnPotentials");
        return compoundtag;
    }

    public boolean triggerEvent(int pId, int pType) {
        return this.spawner1.onEventTriggered(this.level, pId) ||
                this.spawner2.onEventTriggered(this.level, pId) ||
                this.spawner3.onEventTriggered(this.level, pId) ||
                this.spawner4.onEventTriggered(this.level, pId) ||
                super.triggerEvent(pId, pType);
    }

    public boolean onlyOpCanSetNbt() {
        return true;
    }

    public void setEntityIds(EntityType<?> pType, RandomSource pRandom, int spawner) {
        switch (spawner) {
            case 0: this.spawner1.setEntityId(pType, this.level, pRandom, this.worldPosition);
            case 1: this.spawner2.setEntityId(pType, this.level, pRandom, this.worldPosition);
            case 2: this.spawner3.setEntityId(pType, this.level, pRandom, this.worldPosition);
            case 3: this.spawner4.setEntityId(pType, this.level, pRandom, this.worldPosition);
        }
    }
}
