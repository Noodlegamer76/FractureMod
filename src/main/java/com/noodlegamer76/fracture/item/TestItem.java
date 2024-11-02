package com.noodlegamer76.fracture.item;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.noodlegamer76.fracture.block.InitBlocks;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.GameTestInfo;
import net.minecraft.gametest.framework.TestFunction;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.util.FakePlayer;

import java.util.UUID;

public class TestItem extends Item {
    public TestItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        Zombie serverplayer = new Zombie(EntityType.ZOMBIE, level);
        serverplayer.setPos(player.getX(), player.getY(), player.getZ());
        level.addFreshEntity(serverplayer);
        if (!level.isClientSide) {
            Camera oldCamera = Minecraft.getInstance().gameRenderer.getMainCamera();
            Camera tempCamera = new Camera();
            tempCamera.setup(level, serverplayer, true, false, Minecraft.getInstance().getPartialTick());
            //Minecraft.getInstance().setCameraEntity(tempCamera.getEntity());
        }
        System.out.println(Minecraft.getInstance().player.getUUID());
        return super.use(level, player, usedHand);
    }
}
