package com.noodlegamer76.fracture.spellcrafting.spells.spell;

import com.noodlegamer76.fracture.entity.InitEntities;
import com.noodlegamer76.fracture.entity.projectile.AbstractProjectileSpellEntity;
import com.noodlegamer76.fracture.entity.projectile.GiantSnowballProjectile;
import com.noodlegamer76.fracture.item.InitItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GiantSnowballSpell extends ProjectileSpell {
    public GiantSnowballSpell(ItemStack stack, Entity caster) {
        super(stack, caster);
    }

    @Override
    public AbstractProjectileSpellEntity setProjectile() {
        return new GiantSnowballProjectile(InitEntities.GIANT_SLOWBALL.get(), caster, level);
    }

    @Override
    public void cast() {
        shootProjectileFromRotation();
    }

    @Override
    public Component getName() {
        return Component.literal("Giant Snowball");
    }

    @Override
    public float getManaCost() {
        return 10;
    }

    @Override
    public float getRechargeTime() {
        return 15;
    }

    @Override
    public float getCastDelay() {
        return 7.5f;
    }

    @Override
    public Item getCastItem() {
        return InitItems.GIANT_SNOWBALL_SPELL_ITEM.get();
    }
}
