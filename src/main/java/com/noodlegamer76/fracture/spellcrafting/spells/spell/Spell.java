package com.noodlegamer76.fracture.spellcrafting.spells.spell;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;

public class Spell {
    ItemStack stack;
    Player player;
    Level level;

    Spell(ItemStack stack,Player player) {
        this.stack = stack;
        this.player = player;
        this.level = player.level();
    }

    public void cast() {
    }

    public Component getName() {
        return Component.literal("spell not detected when cast, please report this to the mod dev");
    }
}
