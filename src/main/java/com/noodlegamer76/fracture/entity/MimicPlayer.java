package com.noodlegamer76.fracture.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.FakePlayer;

import java.util.UUID;

    public class MimicPlayer extends PathfinderMob {
        protected MimicPlayer(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
            super(pEntityType, pLevel);
        }
    }
