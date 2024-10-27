package com.noodlegamer76.fracture.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class CompactPrimedTnt extends CustomPrimedTnt{
    @Nullable
    public LivingEntity owner;
    public CompactPrimedTnt(Level pLevel) {
        super(InitEntities.COMPACT_TNT.get(), pLevel);
    }

    public CompactPrimedTnt(EntityType<CompactPrimedTnt> compactPrimedTntEntityType, Level level) {
        super(compactPrimedTntEntityType, level);
    }

    public CompactPrimedTnt(Level pLevel, double pX, double pY, double pZ, @Nullable LivingEntity pOwner) {
        this(InitEntities.COMPACT_TNT.get(), pLevel);
        this.setPos(pX, pY, pZ);
        double d0 = pLevel.random.nextDouble() * (double)((float)Math.PI * 2F);
        this.setDeltaMovement(-Math.sin(d0) * 0.02D, (double)0.2F, -Math.cos(d0) * 0.02D);
        this.setFuse(80);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
        owner = pOwner;
    }

    @Override
    protected void explode() {
        float f = 4.0F;
        this.level().explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 6.0F, Level.ExplosionInteraction.TNT);
    }

    /**
     * Returns null or the entityliving it was ignited by
     */
    @Nullable
    public LivingEntity getOwner() {
        return owner;
    }
}
