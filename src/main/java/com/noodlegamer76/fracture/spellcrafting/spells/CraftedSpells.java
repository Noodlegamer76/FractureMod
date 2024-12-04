package com.noodlegamer76.fracture.spellcrafting.spells;

import com.noodlegamer76.fracture.spellcrafting.CardHolder;
import com.noodlegamer76.fracture.spellcrafting.spells.spell.DoubleCastSpell;
import com.noodlegamer76.fracture.spellcrafting.spells.spell.GiantSnowballSpell;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;

/**
 * The CraftedSpells class is responsible for creating and managing spell cards for a magic wand.
 * It provides methods to generate specific spell combinations and to set these spells onto a wand.
 */
public class CraftedSpells {

    public CardHolder getSnowballLaunch(ItemStack wand, LivingEntity caster) {
        CardHolder snowballLaunch = new CardHolder();
        snowballLaunch.addCard(new GiantSnowballSpell(wand, caster));
        snowballLaunch.addCard(new GiantSnowballSpell(wand, caster));
        snowballLaunch.addCard(new GiantSnowballSpell(wand, caster));
        snowballLaunch.addCard(new GiantSnowballSpell(wand, caster));
        snowballLaunch.addCard(new GiantSnowballSpell(wand, caster));
        return snowballLaunch;
    }

    public CardHolder getSnowballShotgun(ItemStack wand, LivingEntity caster) {
        CardHolder snowballLaunch = new CardHolder();
        snowballLaunch.addCard(new DoubleCastSpell(wand, caster));
        snowballLaunch.addCard(new DoubleCastSpell(wand, caster));
        snowballLaunch.addCard(new DoubleCastSpell(wand, caster));
        snowballLaunch.addCard(new DoubleCastSpell(wand, caster));
        snowballLaunch.addCard(new GiantSnowballSpell(wand, caster));
        snowballLaunch.addCard(new GiantSnowballSpell(wand, caster));
        snowballLaunch.addCard(new GiantSnowballSpell(wand, caster));
        snowballLaunch.addCard(new GiantSnowballSpell(wand, caster));
        snowballLaunch.addCard(new GiantSnowballSpell(wand, caster));
        return snowballLaunch;
    }

    public static void setWandSpells(CardHolder deck, ItemStack wand) {
        LazyOptional<IItemHandler> iItemHandler = wand.getCapability(ForgeCapabilities.ITEM_HANDLER, null);
        iItemHandler.ifPresent((itemHandler) -> {
            for (int i = 0; i < wand.getTag().getInt("capacity"); i++) {
                itemHandler.extractItem(i, 64, false);
                if (i < deck.spells.size()) {
                    itemHandler.insertItem(i, deck.spells.get(i).getCastItem().getDefaultInstance(), false);
                }
            }

        });
    }
}
