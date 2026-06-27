package com.noodlegamer76.fracture.item;

import com.noodlegamer76.fracture.client.render.item.LaserGunRenderer;
import com.noodlegamer76.fracture.client.render.item.VoidBlockItemRenderer;
import com.noodlegamer76.fracture.entity.monster.boss.FleshObelisk;
import com.noodlegamer76.fracture.entity.projectile.ObeliskLaser;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.UUID;
import java.util.function.Consumer;

public class LaserGunItem extends Item implements GeoItem {
    AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public LaserGunItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (!level.isClientSide) {
            player.startUsingItem(usedHand);
            ItemStack stack = player.getItemInHand(usedHand);

            CompoundTag tag = stack.getOrCreateTag();

            if (tag.contains("laserData")) {
                return InteractionResultHolder.pass(stack);
            }

            Vec3 look = player.getViewVector(0);
            Vec3 laserPos = player.getEyePosition().add(look.scale(1.5));
            int chargeUp = FleshObelisk.LASER_BEAM_CHARGE_UP;
            int fireTime = FleshObelisk.LASER_BEAM_FIRE_TIME;
            int cooldown = FleshObelisk.LASER_BEAM_COOLDOWN;
            int laserFreezeTime = FleshObelisk.LASER_BEAM_FREEZE_TIME;
            ObeliskLaser laser = new ObeliskLaser(
                    laserPos,
                    level,
                    chargeUp,
                    fireTime,
                    cooldown,
                    laserFreezeTime,
                    (laser1) -> {}
            );
            laser.setLaserDirection(player.getViewVector(0));
            level.addFreshEntity(laser);

            CompoundTag laserData = new CompoundTag();
            laserData.putInt("time", chargeUp + fireTime + cooldown + laserFreezeTime);
            laserData.putInt("entityId", laser.getId());

            tag.put("laserData", laserData);

            return InteractionResultHolder.pass(stack);
        }

        return super.use(level, player, usedHand);
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        super.releaseUsing(pStack, pLevel, pLivingEntity, pTimeCharged);

        if (!pLevel.isClientSide) {
            CompoundTag tag = pStack.getOrCreateTag();
            CompoundTag laserData = tag.getCompound("laserData");
            int id = laserData.getInt("entityId");

            Entity laserId = pLevel.getEntity(id);
            if (laserId instanceof ObeliskLaser laser) {
                laser.discard();
            }

            tag.remove("laserData");
            if (pLivingEntity instanceof Player player && !player.isCreative()) {
                player.getCooldowns().addCooldown(this, 500);
            }
        }
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        CompoundTag tag = pStack.getOrCreateTag();
        CompoundTag laserData = tag.getCompound("laserData");
        return laserData.getInt("time");
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.TOOT_HORN;
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        super.onUseTick(level, livingEntity, stack, remainingUseDuration);

        CompoundTag tag = stack.getOrCreateTag();
        CompoundTag laserData = tag.getCompound("laserData");
        int id = laserData.getInt("entityId");

        Entity laserId = level.getEntity(id);
        if (laserId instanceof ObeliskLaser laser) {
            if (!level.isClientSide) {
                Vec3 look = livingEntity.getViewVector(0);
                Vec3 laserPos = livingEntity.getEyePosition().add(look.scale(1.5));
                laser.setPos(laserPos.x, laserPos.y, laserPos.z);

                float maxRotationDegrees = 1.0f;

                Vec3 current = laser.getLaserDirection().normalize();
                Vec3 target = livingEntity.getViewVector(0).normalize();

                double maxAngle = Math.toRadians(maxRotationDegrees);

                double dot = Mth.clamp(current.dot(target), -1.0, 1.0);
                double angle = Math.acos(dot);

                Vec3 newDirection;

                if (angle <= maxAngle || angle < 1e-6) {
                    newDirection = target;
                } else {
                    Vec3 axis = current.cross(target);

                    if (axis.lengthSqr() < 1e-8) {
                        axis = new Vec3(0, 1, 0);
                        if (Math.abs(current.y) > 0.99)
                            axis = new Vec3(1, 0, 0);
                    }

                    axis = axis.normalize();

                    Quaternionf rotationQuat = new Quaternionf()
                            .fromAxisAngleRad(
                                    (float) axis.x,
                                    (float) axis.y,
                                    (float) axis.z,
                                    (float) maxAngle
                            );

                    Vector3f vec = current.toVector3f();
                    vec.rotate(rotationQuat);

                    newDirection = new Vec3(vec.x(), vec.y(), vec.z()).normalize();
                }

                laser.setLaserDirection(newDirection);
            }
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private LaserGunRenderer renderer = null;
            @Override public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null)
                    this.renderer = new LaserGunRenderer();

                return renderer;
            }
        });
    }
}
