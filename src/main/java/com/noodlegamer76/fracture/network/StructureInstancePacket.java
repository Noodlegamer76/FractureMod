package com.noodlegamer76.fracture.network;

import com.noodlegamer76.fracture.gui.structure.StructureInstanceVisualizer;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.StructureInstance;
import com.noodlegamer76.fracture.worldgen.megastructure.structure.variables.GenVar;
import com.noodlegamer76.fracture.worldgen.megastructure.visualizer.StructureInstanceSerializer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class StructureInstancePacket {
    public CompoundTag tag;

    public StructureInstancePacket(StructureInstance instance) {
        CompoundTag tag = new CompoundTag();
        StructureInstanceSerializer.serialize(tag, instance);
        this.tag = tag;
    }

    public StructureInstancePacket(FriendlyByteBuf buf) {
        this.tag = buf.readNbt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeNbt(tag);
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            List<GenVar<?>> vars = StructureInstanceSerializer.deserialize(tag);
            StructureInstanceVisualizer.getInstance().setVars(vars, null);
        });
        context.setPacketHandled(true);
    }
}