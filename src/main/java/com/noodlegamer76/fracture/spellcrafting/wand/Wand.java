package com.noodlegamer76.fracture.spellcrafting.wand;

import com.noodlegamer76.fracture.client.renderers.item.wand.WandRenderer;
import com.noodlegamer76.fracture.gui.wand.WandMenu;
import com.noodlegamer76.fracture.network.PacketHandler;
import com.noodlegamer76.fracture.network.SpellAmountPacket;
import com.noodlegamer76.fracture.spellcrafting.CreateWand;
import com.noodlegamer76.fracture.spellcrafting.WandCast;
import com.noodlegamer76.fracture.spellcrafting.data.PlayerSpellData;
import com.noodlegamer76.fracture.spellcrafting.data.SpellHelper;
import io.netty.buffer.Unpooled;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class Wand extends Item implements GeoAnimatable {
    AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public double spinTime = 0;

    public Wand(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
       spinTime /= 1.025;
       if (spinTime <= 0) {
           spinTime = 0;
       }
       if (pStack.getOrCreateTag().getBoolean("isCreated")) {
           float currentMana = pStack.getTag().getFloat("currentMana");
           float manaRechargeSpeed = pStack.getTag().getFloat("manaRechargeSpeed");
           float maxMana = pStack.getTag().getFloat("maxMana");
           if (currentMana + manaRechargeSpeed >= maxMana) {
               pStack.getTag().putFloat("currentMana", maxMana);
           }
           else {
               pStack.getTag().putFloat("currentMana", currentMana + manaRechargeSpeed);
           }
           float currentCastDelay = pStack.getTag().getFloat("currentCastDelay");
           if (currentCastDelay > 0) {
               pStack.getTag().putFloat("currentCastDelay", currentCastDelay - 1);
           }
       }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(level, player, hand);
        ItemStack itemstack = ar.getObject();
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();
        if (player instanceof LocalPlayer localPlayer) {
            if (!localPlayer.isCrouching()) {
                spinTime += 250;
            }
        }
        if (player instanceof ServerPlayer serverPlayer) {
            ItemStack item = player.getItemInHand(hand);





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

                        PlayerSpellData data = SpellHelper.getSpellData(serverPlayer);
                        int[] amounts = new int[data.getSpells().size()];

                        int index = 0;
                        for (int amount : data.getSpells().values()) {
                            amounts[index] = amount;
                            index++;
                        }

                        PacketHandler.sendToPlayer(serverPlayer, new SpellAmountPacket(amounts));

                        return new WandMenu(id, inventory, packetBuffer);
                    }

                }, buf -> {
                    buf.writeBlockPos(player.blockPosition());
                    buf.writeByte(hand == InteractionHand.MAIN_HAND ? 0 : 1);
                });
            }

            if (!serverPlayer.isCrouching()) {
                    new WandCast(level, player, item);

            }
        }

        return ar;
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag compound) {
        CompoundTag nbt = stack.getOrCreateTag();
        if (!nbt.contains("isCreated")) {
            new CreateWand().createStats(nbt, stack);
        }
        int capacity = stack.getTag().getInt("capacity");

        WandInventoryCapability capability = new WandInventoryCapability(capacity);
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

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object object) {
        return 0;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private WandRenderer renderer = null;
            // Don't instantiate until ready. This prevents race conditions breaking things
            @Override public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null)
                    this.renderer = new WandRenderer();

                return renderer;
            }
        });
    }
}
