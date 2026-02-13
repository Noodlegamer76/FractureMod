package com.noodlegamer76.fracture.item;

import com.mojang.authlib.GameProfile;
import com.noodlegamer76.fracture.entity.InitEntities;
import com.noodlegamer76.fracture.entity.monster.PlayerMimic;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class TestItem extends Item {
    public TestItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide) {
            //Vec3 forwardVector = ModVectors.getForwardVector(player);
            //for(int i = 0; i < 100; i++) {
            //    double random = Math.random() + 0.5;
            //    Vec3 vec = forwardVector.scale(2.0).multiply(random, random, random).add(new Vec3(
            //            Math.random() - 0.5,
            //            Math.random() - 0.5,
            //            Math.random() - 0.5)
            //            .normalize().scale(Math.random()));
//
            //    level.addParticle(InitParticles.CONFETTI_PARTICLES.get(),
            //            player.getX(),
            //            player.getY() + player.getEyeHeight(),
            //            player.getZ(),
            //            vec.x,
            //            vec.y,
            //            vec.z
            //    );
            //}
            //System.out.println(player.getPortalCooldown());
            //System.out.println(player.isOnPortalCooldown());

        }

        if (!level.isClientSide) {
           for (int i = 0; i < 1; i++) {
               PlayerMimic mimic = new PlayerMimic(InitEntities.PLAYER_MIMIC.get(), level);
               GameProfile profile = new GameProfile(null, "mackattack20222");
               mimic.setProfile(profile);
               mimic.setPos(player.getX(), player.getY(), player.getZ());
               mimic.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.1);
               level.addFreshEntity(mimic);
           }
        }

        return super.use(level, player, hand);
    }



}
