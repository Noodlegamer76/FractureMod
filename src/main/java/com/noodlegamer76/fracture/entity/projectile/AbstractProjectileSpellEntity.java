package com.noodlegamer76.fracture.entity.projectile;

import com.google.common.collect.Lists;
import com.noodlegamer76.fracture.spellcrafting.CardHolder;
import com.noodlegamer76.fracture.spellcrafting.CastState;
import com.noodlegamer76.fracture.spellcrafting.spells.spell.ProjectileSpell;
import com.noodlegamer76.fracture.spellcrafting.spells.spell.Spell;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class AbstractProjectileSpellEntity extends AbstractArrow {
    public ProjectileSpell spell;

    public AbstractProjectileSpellEntity(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public void trigger() {
        if (spell == null) {
            //System.out.println("PROJECTILE TRIGGER SPELL IS NULL");
            return;
        }
        if (spell.triggerCastState != null) {
            spell.trigger();
            System.out.println("Triggering spell with level: " + spell.triggerCastState.stateLevel);
            spell.triggerCastState = null;
        }
    }

    public void setSpell(ProjectileSpell spell) {
        this.spell = spell;
    }

    @Override
    public void onRemovedFromWorld() {
        trigger();
        super.onRemovedFromWorld();
    }


    @Override
    protected void onHit(@NotNull HitResult pResult) {
        super.onHit(pResult);
    }

    protected void onHitEntity(EntityHitResult pResult) {
        trigger();
        Entity entity = pResult.getEntity();
        float f = (float)this.getDeltaMovement().length();
        int i = Mth.ceil(Mth.clamp((double)f * getBaseDamage(), 0.0D, (double)Integer.MAX_VALUE));

        if (this.isCritArrow()) {
            long j = (long)this.random.nextInt(i / 2 + 2);
            i = (int)Math.min(j + (long)i, 2147483647L);
        }

        Entity entity1 = this.getOwner();
        DamageSource damagesource;
        if (entity1 == null) {
            damagesource = this.damageSources().arrow(this, this);
        } else {
            damagesource = this.damageSources().arrow(this, entity1);
            if (entity1 instanceof LivingEntity) {
                ((LivingEntity)entity1).setLastHurtMob(entity);
            }
        }

        boolean flag = entity.getType() == EntityType.ENDERMAN;
        int k = entity.getRemainingFireTicks();
        if (this.isOnFire() && !flag) {
            entity.setSecondsOnFire(5);
        }

        entity.hurt(damagesource, (float)i);
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
        if (isRemoved()) {
            trigger();
        }
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
