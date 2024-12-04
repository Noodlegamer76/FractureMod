package com.noodlegamer76.fracture.spellcrafting.spells.spell;

import com.noodlegamer76.fracture.entity.projectile.AbstractProjectileSpellEntity;
import com.noodlegamer76.fracture.spellcrafting.CastState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public abstract class ProjectileSpell extends Spell {
    public float inaccuracyMultiplier = 1;
    AbstractProjectileSpellEntity projectile;
    Vec3 lastShooterPos;
    Vec3 lastShooterDelta;

    ProjectileSpell(ItemStack stack, Entity caster) {
        super(stack, caster);
    }

    public AbstractProjectileSpellEntity getProjectile() {
        return projectile;
    }

    @Override
    public void tick() {
        super.tick();
        if (projectile != null && projectile.getOwner() != null) {
            lastShooterPos = projectile.getOwner().position();
            lastShooterDelta = projectile.getOwner().getDeltaMovement();
        }
    }

    public void shootProjectileFromRotation() {
        if (projectile == null) {
            return;
        }
        if (triggerCastState != null && triggerCastState.stateLevel != 1) {
            shootWithBounce(lastShooterDelta, 1.0f);
        }
        else {
            projectile.shootFromRotation(caster, caster.getXRot(), caster.getYRot(), 0, 2.0f, 2.75f * inaccuracyMultiplier);
        }
        projectile.setPos(lastShooterPos);
        level.addFreshEntity(projectile);
    }

    public void shootWithBounce(Vec3 delta, float elasticity) {
        projectile.setDeltaMovement(-delta.x, -delta.y, -delta.z);
    }

    @Override
    public void setTriggerCastState(CastState state) {
        super.setTriggerCastState(state);
        projectile.setTriggerState(triggerCastState);
    }
}
