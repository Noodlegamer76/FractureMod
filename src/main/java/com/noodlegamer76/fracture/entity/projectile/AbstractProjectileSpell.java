package com.noodlegamer76.fracture.entity.projectile;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class AbstractProjectileSpell extends AbstractArrow {
    public AbstractProjectileSpell(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void tickDespawn() {
        this.discard();
    }

    @Override
    protected ItemStack getPickupItem() {
        return Items.AIR.getDefaultInstance();
    }
}
