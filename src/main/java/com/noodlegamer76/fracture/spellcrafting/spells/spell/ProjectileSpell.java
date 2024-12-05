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
        if (life == 0) {
            System.out.println("baseDamage " + damageMultiplier + " " + projectile.getBaseDamage());
            projectile.setBaseDamage(projectile.getBaseDamage() * damageMultiplier);
            System.out.println("baseDamage " + damageMultiplier + " " + projectile.getBaseDamage());
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
        projectile.shootFromRotation(caster, caster.getXRot(), caster.getYRot(), 0,
                2.0f, 2.75f * inaccuracyMultiplier);

        projectile.setPos(lastShooterPos);
        if (caster != null) {
            projectile.setPos(lastShooterPos.x, lastShooterPos.y + caster.getEyeHeight(), lastShooterPos.z);
        }
        level.addFreshEntity(projectile);
    }

    public void shootWithBounce(Vec3 delta, float elasticity) {
            Vec3 projectDelta = new Vec3(delta.x, -delta.y, delta.z);
            projectile.setDeltaMovement(projectDelta);
    }

    @Override
    public void setTriggerCastState(CastState state) {
        super.setTriggerCastState(state);
        System.out.println("Setting trigger cast state with level: " + state.stateLevel);
        projectile.setTriggerState(triggerCastState);
    }
}
