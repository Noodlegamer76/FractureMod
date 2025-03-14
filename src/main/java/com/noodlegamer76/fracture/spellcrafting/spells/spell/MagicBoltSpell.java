package com.noodlegamer76.fracture.spellcrafting.spells.spell;

import com.noodlegamer76.fracture.entity.InitEntities;
import com.noodlegamer76.fracture.entity.projectile.AbstractProjectileSpellEntity;
import com.noodlegamer76.fracture.entity.projectile.MagicBoltProjectile;
import com.noodlegamer76.fracture.item.InitItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class MagicBoltSpell extends ProjectileSpell {
    public MagicBoltSpell(ItemStack stack, Entity caster) {
        super(stack, caster);
    }

    @Override
    public AbstractProjectileSpellEntity setProjectile() {
        return new MagicBoltProjectile(InitEntities.MAGIC_BOLT.get(), caster, level);
    }

    @Override
    public void cast() {
        shootProjectileFromRotation();
    }

    @Override
    public Component getName() {
        return Component.literal("Magic Bolt");
    }

    @Override
    public float getManaCost() {
        return 0;
    }

    @Override
    public float getRechargeTime() {
        return 0;
    }

    @Override
    public float getCastDelay() {
        return 0;
    }

    @Override
    public Item getCastItem() {
        return InitItems.MAGIC_BOLT_SPELL_ITEM.get();
    }
}
