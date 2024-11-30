package com.noodlegamer76.fracture.spellcrafting.spells.spell;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class GiantSnowballSpell extends Spell {
    public GiantSnowballSpell(ItemStack stack, LivingEntity caster) {
        super(stack, caster);
    }

    @Override
    public void cast() {

    }

    @Override
    public Component getName() {
        return Component.literal("Giant Snowball");
    }
}
