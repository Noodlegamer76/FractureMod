package com.noodlegamer76.fracture.entity.projectile;

import com.noodlegamer76.fracture.spellcrafting.CardHolder;
import com.noodlegamer76.fracture.spellcrafting.CastState;
import com.noodlegamer76.fracture.spellcrafting.spells.spell.Spell;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class AbstractProjectileSpellEntity extends AbstractArrow {
    boolean isTrigger = false;
    public CastState triggerState;
    CardHolder spells;

    public AbstractProjectileSpellEntity(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public void setSpells(CardHolder spells) {
        this.spells = spells;
    }

    public void setTriggerState(CastState triggerState) {
        this.triggerState = triggerState;
    }

    public CardHolder getSpells() {
        return spells;
    }

    public void trigger() {
        if (triggerState != null) {
            triggerState.cast();
            System.out.println("triggering");
        }
    }

    @Override
    protected void tickDespawn() {
        this.discard();
    }

    @Override
    protected ItemStack getPickupItem() {
        return Items.AIR.getDefaultInstance();
    }

    @Override
    public void shoot(double pX, double pY, double pZ, float pVelocity, float pInaccuracy) {
        Vec3 vec3 = (new Vec3(pX, pY, pZ)).normalize()
                .add(this.random.triangle(0.0D, 0.0172275D * (double)pInaccuracy),
                        this.random.triangle(0.0D, 0.0172275D * (double)pInaccuracy),
                        this.random.triangle(0.0D, 0.0172275D * (double)pInaccuracy))
                .normalize()  // Normalize again to ensure consistent velocity
                .scale((double)pVelocity);

        this.setDeltaMovement(vec3);

        double d0 = vec3.horizontalDistance();
        this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * (double)(180F / (float)Math.PI)));
        this.setXRot((float)(Mth.atan2(vec3.y, d0) * (double)(180F / (float)Math.PI)));
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
    }

    @Override
    public void tick() {
        super.tick();
        faceMovementDirection();
    }

    public void faceMovementDirection() {
        // Get the entity's current velocity
        Vec3 velocity = this.getDeltaMovement();

        // Calculate horizontal distance, which is used to determine the pitch
        double horizontalDistance = velocity.horizontalDistance();

        // Calculate the yaw (Y-axis rotation) based on the movement direction
        float yaw = (float)(Mth.atan2(velocity.z, velocity.x) * (180F / Math.PI)) - 90.0F; // -90F to correct for Minecraft's default yaw rotation

        // Calculate the pitch (X-axis rotation) based on the vertical and horizontal movement direction
        float pitch = (float)(Mth.atan2(velocity.y, horizontalDistance) * (180F / Math.PI));

        // Apply the calculated rotations to the entity
        this.setYRot(yaw);
        this.setXRot(pitch);

        // Update old rotation variables if necessary
        this.yRotO = yaw;
        this.xRotO = pitch;
    }

}
