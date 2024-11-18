package com.noodlegamer76.fracture.spellcrafting.spells;

import com.noodlegamer76.fracture.spellcrafting.spells.item.SpellItem;
import com.noodlegamer76.fracture.spellcrafting.spells.item.VoidBallSpellItem;
import com.noodlegamer76.fracture.spellcrafting.spells.spell.Spell;
import com.noodlegamer76.fracture.spellcrafting.spells.spell.VoidBallSpell;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;

public class SpellTypes {

    public static Spell getSpellTypes(ItemStack item, Player player) {

        if (item.getItem() instanceof VoidBallSpellItem) {
            return new VoidBallSpell(item, player);
        }
        //didnt find spell from item
        return null;
    }
}
