package com.noodlegamer76.fracture.spellcrafting;

import com.noodlegamer76.fracture.gui.wand.WandMenu;
import io.netty.buffer.Unpooled;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class Wand extends Item {
    public Wand(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(level, player, hand);
        ItemStack itemstack = ar.getObject();
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();
        if (player instanceof ServerPlayer serverPlayer) {
            ItemStack item = player.getItemInHand(hand);

            CompoundTag nbt = item.getOrCreateTag();
            if (!nbt.contains("isCreated")) {
               new CreateWand().createStats(nbt);

            }



            if (serverPlayer.isCrouching()) {
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
                        return new WandMenu(id, inventory, packetBuffer);
                    }
                }, buf -> {
                    buf.writeBlockPos(player.blockPosition());
                    buf.writeByte(hand == InteractionHand.MAIN_HAND ? 0 : 1);
                });
            }

            if (!serverPlayer.isCrouching()) {
                    new WandCast(level, (ServerPlayer)  player, item);
            }
        }

        return ar;
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag compound) {
        WandInventoryCapability capability = new WandInventoryCapability();
        return capability;
    }

    @Override
    public CompoundTag getShareTag(ItemStack stack) {
        CompoundTag nbt = super.getShareTag(stack);
        if (nbt != null) {
            stack.getCapability(ForgeCapabilities.ITEM_HANDLER, null)
                    .ifPresent(capability -> nbt.put("Inventory", ((ItemStackHandler) capability).serializeNBT()));
        }
        return nbt;
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
        super.readShareTag(stack, nbt);
        if (nbt != null) {
            stack.getCapability(ForgeCapabilities.ITEM_HANDLER, null)
                    .ifPresent(capability -> ((ItemStackHandler) capability).deserializeNBT((CompoundTag) nbt.get("Inventory")));
        }
    }
}
