package com.noodlegamer76.fracture.spellcrafting.data;

import com.noodlegamer76.fracture.util.InitCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerSpellDataProvider implements ICapabilitySerializable<CompoundTag> {
    private final PlayerSpellData spellData = new PlayerSpellData();
    private final LazyOptional<PlayerSpellData> instance = LazyOptional.of(() -> spellData);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == InitCapabilities.PLAYER_SPELL_DATA ? instance.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        spellData.saveNBTData(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        spellData.loadNBTData(nbt);
    }
}

