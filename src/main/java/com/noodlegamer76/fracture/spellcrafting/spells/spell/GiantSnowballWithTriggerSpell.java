package com.noodlegamer76.fracture.spellcrafting.spells.spell;

import com.noodlegamer76.fracture.entity.InitEntities;
import com.noodlegamer76.fracture.entity.projectile.GiantSnowballProjectile;
import com.noodlegamer76.fracture.item.InitItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GiantSnowballWithTriggerSpell extends ProjectileSpell {
    boolean casted = false;
    public GiantSnowballWithTriggerSpell(ItemStack stack, Entity caster) {
        super(stack, caster);
        projectile = new GiantSnowballProjectile(InitEntities.GIANT_SLOWBALL.get(), caster, level);
    }

    @Override
    public void cast() {
        if (!casted) {
            shootProjectileFromRotation();
        }
        casted = true;
    }

    @Override
    public int triggerDraws() {
        return 1;
    }

    @Override
    public boolean createsCastStates() {
        return true;
    }

    @Override
    public Component getName() {
        return Component.literal("Giant Snowball with Trigger");
    }

    @Override
    public float getManaCost() {
        return 20;
    }

    @Override
    public float getRechargeTime() {
        return 20;
    }

    @Override
    public float getCastDelay() {
        return 10;
    }

    @Override
    public Item getCastItem() {
        return InitItems.GIANT_SNOWBALL_WITH_TRIGGER_SPELL_ITEM.get();
    }
}
