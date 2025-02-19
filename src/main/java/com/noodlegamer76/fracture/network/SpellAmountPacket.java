package com.noodlegamer76.fracture.network;

import com.noodlegamer76.fracture.spellcrafting.data.SpellHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.function.Supplier;

public class SpellAmountPacket {
    public int[] amounts;

    public SpellAmountPacket(int[] array) {
        amounts = array;
    }

    public SpellAmountPacket(FriendlyByteBuf buf) {
        this(buf.readVarIntArray());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeVarIntArray(amounts);
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();

        context.enqueueWork(() -> {
            if (Minecraft.getInstance().player != null) {

                SpellHelper.amounts = new ArrayList<>();
                for (int amount: amounts) {
                    SpellHelper.amounts.add(amount);
                }
            }
        });

        context.setPacketHandled(true);
    }
}