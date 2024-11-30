package com.noodlegamer76.fracture.spellcrafting.spells;

import com.noodlegamer76.fracture.spellcrafting.spells.item.GiantSnowBallSpellItem;
import com.noodlegamer76.fracture.spellcrafting.spells.item.VoidBallSpellItem;
import com.noodlegamer76.fracture.spellcrafting.spells.spell.GiantSnowballSpell;
import com.noodlegamer76.fracture.spellcrafting.spells.spell.Spell;
import com.noodlegamer76.fracture.spellcrafting.spells.spell.VoidBallSpell;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class SpellTypes {

    public static Spell getSpellTypes(ItemStack item, Player player) {

        if (item.getItem() instanceof VoidBallSpellItem) {
            return new VoidBallSpell(item, player);
        }
        else if (item.getItem() instanceof GiantSnowBallSpellItem) {
            return new GiantSnowballSpell(item, player);
        }
        //didnt find spell from item
        return null;
    }
}
