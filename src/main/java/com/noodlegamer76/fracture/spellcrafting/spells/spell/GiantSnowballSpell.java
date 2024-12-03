package com.noodlegamer76.fracture.spellcrafting.spells.spell;

import com.noodlegamer76.fracture.entity.InitEntities;
import com.noodlegamer76.fracture.entity.projectile.GiantSnowballProjectile;
import com.noodlegamer76.fracture.entity.projectile.VoidBall;
import com.noodlegamer76.fracture.item.InitItems;
import com.noodlegamer76.fracture.particles.InitParticles;
import com.noodlegamer76.fracture.util.ModVectors;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class GiantSnowballSpell extends Spell {
    public GiantSnowballSpell(ItemStack stack, LivingEntity caster) {
        super(stack, caster);
    }

    @Override
    public void cast() {
        GiantSnowballProjectile snowball = new GiantSnowballProjectile(InitEntities.GIANT_SLOWBALL.get(), caster, level);
        snowball.shootFromRotation(caster, caster.getXRot(), caster.getYRot(), 0, 2.0f, 2.75f);
        level.addFreshEntity(snowball);

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
