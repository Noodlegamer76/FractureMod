package com.noodlegamer76.fracture.item;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.noodlegamer76.fracture.block.InitBlocks;
import com.noodlegamer76.fracture.gui.guibuilder.GuiBuilderMenu;
import com.noodlegamer76.fracture.gui.guibuilder.GuiBuilderScreen;
import com.noodlegamer76.fracture.gui.wand.WandMenu;
import com.noodlegamer76.fracture.particles.InitParticles;
import com.noodlegamer76.fracture.util.ModVectors;
import io.netty.buffer.Unpooled;
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
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class TestItem extends Item {
    public TestItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide) {
            Vec3 forwardVector = ModVectors.getForwardVector(player);
            for(int i = 0; i < 100; i++) {
                double random = Math.random() + 0.5;
                Vec3 vec = forwardVector.scale(2.0).multiply(random, random, random).add(new Vec3(
                        Math.random() - 0.5,
                        Math.random() - 0.5,
                        Math.random() - 0.5)
                        .normalize().scale(Math.random()));

                level.addParticle(InitParticles.CONFETTI_PARTICLES.get(),
                        player.getX(),
                        player.getY() + player.getEyeHeight(),
                        player.getZ(),
                        vec.x,
                        vec.y,
                        vec.z
                );
            }
        }

        if (player instanceof ServerPlayer serverPlayer && serverPlayer.isCrouching()) {
            NetworkHooks.openScreen(serverPlayer, new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.translatable("gui.fracture.wand");
                }

                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                    FriendlyByteBuf packetBuffer = new FriendlyByteBuf(Unpooled.buffer());
                    packetBuffer.writeBlockPos(player.blockPosition());
                    packetBuffer.writeByte(hand == InteractionHand.MAIN_HAND ? 0 : 1);
                    return new GuiBuilderMenu(id, inventory, packetBuffer);
                }

            }, buf -> {
                buf.writeBlockPos(player.blockPosition());
                buf.writeByte(hand == InteractionHand.MAIN_HAND ? 0 : 1);
            });
        }
        return super.use(level, player, hand);
    }



}
