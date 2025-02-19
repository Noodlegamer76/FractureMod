package com.noodlegamer76.fracture.network;

import com.noodlegamer76.fracture.spellcrafting.data.SpellHelper;
import com.noodlegamer76.fracture.spellcrafting.spells.item.SpellItem;
import com.noodlegamer76.fracture.spellcrafting.wand.Wand;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SpellInvPacket {
    public ArrayList<ItemStack> spellInv;
    public InteractionHand hand;

    public SpellInvPacket(ArrayList<ItemStack> spells, InteractionHand hand) {
        spellInv = spells;
        this.hand = hand;
    }

    public SpellInvPacket(FriendlyByteBuf buf) {

        int size = buf.readInt();
        spellInv = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            spellInv.add(buf.readItem());
        }

        hand = buf.readInt() == 0 ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(spellInv.size());

        for (ItemStack stack : spellInv) {
            buf.writeItem(stack);
        }

        buf.writeInt(hand.ordinal());
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        ServerPlayer player = context.getSender();

        if (context.getDirection().getReceptionSide().isClient() || player == null) {
            return;
        }

        ItemStack wand = player.getItemInHand(hand);
        if (wand.getItem() instanceof Wand) {
            CompoundTag baseTag = wand.getOrCreateTag();
            CompoundTag tag = new CompoundTag();

            ArrayList<ItemStack> spell = new ArrayList<>();

            for (int i = 0; i < spellInv.size(); i++) {
                if (spellInv.get(i).getItem() instanceof SpellItem spellItem) {
                    tag.putString(String.valueOf(i), spellItem.getName().getString());
                    spell.add(spellItem.getDefaultInstance());
                }
                else {
                    tag.putString(String.valueOf(i), "air");
                }
            }

            if (!canMakeSpell(spell, player)) {
                return;
            }

            baseTag.put("spells", tag);
            wand.setTag(baseTag);
            player.getInventory().setChanged();
        }

        context.setPacketHandled(true);
    }

    //makes sure the player has enough of the spell items put in, otherwise don't save
    public static boolean canMakeSpell(ArrayList<ItemStack> spell, Player player) {
        Map<Component, Integer> data = SpellHelper.getPlayerSpells(player);

        Map<Component, Integer> spellCountMap = new HashMap<>();

        for (ItemStack spellItemStack : spell) {
            if (spellItemStack.getItem() instanceof SpellItem spellItem) {
                Component spellName = spellItem.getName();

                spellCountMap.put(spellName, spellCountMap.getOrDefault(spellName, 0) + 1);
            }
        }

        for (Map.Entry<Component, Integer> entry : spellCountMap.entrySet()) {
            Component spellName = entry.getKey();
            int requiredAmount = entry.getValue();

            Integer playerAmount = data.getOrDefault(spellName, 0);

            if (playerAmount < requiredAmount) {
                return false;
            }
        }

        return true;
    }
}