package com.noodlegamer76.fracture.spellcrafting.spells.item;

import com.noodlegamer76.fracture.spellcrafting.SpellcraftingItem;
import com.noodlegamer76.fracture.spellcrafting.data.PlayerSpellData;
import com.noodlegamer76.fracture.spellcrafting.data.SpellHelper;
import com.noodlegamer76.fracture.spellcrafting.spells.spell.Spell;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class SpellItem extends SpellcraftingItem {
    public SpellItem(Properties pProperties) {
        super(pProperties);
    }

    public abstract Spell getSpell(ItemStack stack, Entity entity);

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (!level.isClientSide) {
            PlayerSpellData spellData = SpellHelper.getSpellData(player);
            Component spellId = getSpell(player.getItemInHand(usedHand), player).getName();

            spellData.unlockSpell(spellId, 1);

            player.getItemInHand(usedHand).shrink(1);
        }

        return super.use(level, player, usedHand);
    }
}
