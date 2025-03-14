package com.noodlegamer76.fracture.spellcrafting.spells.spell;

import com.noodlegamer76.fracture.entity.projectile.AbstractProjectileSpellEntity;
import com.noodlegamer76.fracture.spellcrafting.CastState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public abstract class ProjectileSpell extends Spell {
    public float baseDamage = 1;
    public float inaccuracyMultiplier = 1;
    public float knockbackMultiplier = 1;
    public float velocity = 2;
    public int baseKnockback = 1;
    public boolean hasGravity = true;
    AbstractProjectileSpellEntity projectile;
    Vec3 lastShooterPos;
    Vec3 lastShooterDelta;

    ProjectileSpell(ItemStack stack, Entity caster) {
        super(stack, caster);
        projectile = setProjectile();
    }

    public abstract AbstractProjectileSpellEntity setProjectile();

    public AbstractProjectileSpellEntity getProjectile() {
        return projectile;
    }

    @Override
    public void tick() {
        if (life == 0) {
            projectile.setBaseDamage(baseDamage * damageMultiplier);
            projectile.setKnockback((int) (baseKnockback * knockbackMultiplier));
            projectile.setNoGravity(!hasGravity);
        }
        super.tick();
        if (projectile != null && projectile.getOwner() != null) {
            lastShooterPos = projectile.getOwner().position();
            lastShooterDelta = projectile.getOwner().getDeltaMovement();
        }
    }

    public void shootProjectileFromRotation() {
        if (projectile == null || lastShooterPos == null) {
            return;
        }
        projectile.shootFromRotation(caster, caster.getXRot(), caster.getYHeadRot(), 0,
                velocity, 2.75f * inaccuracyMultiplier);

        projectile.setPos(lastShooterPos);
        if (caster != null) {
            projectile.setPos(lastShooterPos.x, lastShooterPos.y + caster.getEyeHeight(), lastShooterPos.z);
        }
        level.addFreshEntity(projectile);
    }

    @Override
    public void setTriggerCastState(CastState state) {
        super.setTriggerCastState(state);
        projectile.spell = this;
        System.out.println("Setting trigger cast state with level: " + state.stateLevel);
    }
}
