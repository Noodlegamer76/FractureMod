package com.noodlegamer76.fracture.spellcrafting.wand;

import com.noodlegamer76.fracture.gui.wand.WandScreen;
import com.noodlegamer76.fracture.item.InitItems;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class WandInventoryCapability implements ICapabilitySerializable<CompoundTag> {
    boolean isCreated;
    CompoundTag stats;

    public static final Capability<WandInventoryCapability> WAND_INVENTORY_CAPABILITY =
            CapabilityManager.get(new CapabilityToken<WandInventoryCapability>(){});
    boolean test;
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onItemDropped(ItemTossEvent event) {
            if (event.getEntity().getItem().getItem() == InitItems.WAND.get()) {
            if (Minecraft.getInstance().screen instanceof WandScreen) {
                Minecraft.getInstance().player.closeContainer();
            }
        }
    }
    public WandInventoryCapability() {
    }

    private final LazyOptional<ItemStackHandler> inventory = LazyOptional.of(this::createItemHandler);

    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
        return capability == ForgeCapabilities.ITEM_HANDLER ? this.inventory.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = getItemHandler().serializeNBT();
        nbt.putBoolean("is_created", true);
        return nbt;
    }
    @Override
    public void deserializeNBT(CompoundTag nbt) {
        getItemHandler().deserializeNBT(nbt);
        isCreated = nbt.getBoolean("is_created");
    }

    private ItemStackHandler createItemHandler() {
        return new ItemStackHandler(27) {
            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.getItem() != InitItems.WAND.get();
            }

            @Override
            public void setSize(int size) {
            }

        };
    }

    public void create() {
        if(!Dist.CLIENT.isClient()) {
            inventory.ifPresent(item -> {
                ItemStack stack = item.getStackInSlot(1);
                stack.setCount(stack.getCount() + 1);
                item.setStackInSlot(1, stack);
            });
        }
    }
    public void cast() {
        if(!isCreated) {
            create();
        }
    }

    private ItemStackHandler getItemHandler() {
        return inventory.orElseThrow(RuntimeException::new);
    }
}
