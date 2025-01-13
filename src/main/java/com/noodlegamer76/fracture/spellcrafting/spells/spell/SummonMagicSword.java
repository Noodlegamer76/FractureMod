package com.noodlegamer76.fracture.spellcrafting.spells.spell;

import com.noodlegamer76.fracture.item.InitItems;
import com.noodlegamer76.fracture.item.MagicSword;
import com.noodlegamer76.fracture.spellcrafting.spells.item.SummonMagicSwordSpellItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SummonMagicSword extends Spell {
    public SummonMagicSword(ItemStack stack, Entity caster) {
        super(stack, caster);
    }

    @Override
    public void cast() {
        ItemStack stack = InitItems.MAGIC_SWORD.get().getDefaultInstance();
        if (stack.getItem() instanceof MagicSword sword) {
            sword.setSpell(stack, this);
        }
        ItemEntity itemEntity = new ItemEntity(level, caster.getX(), caster.getY(), caster.getZ(), stack);
        level.addFreshEntity(itemEntity);
    }

    @Override
    public Component getName() {
        return Component.literal("Summon Magic Sword");
    }

    @Override
    public float getManaCost() {
        return 50;
    }

    @Override
    public float getRechargeTime() {
        return 35;
    }

    @Override
    public float getCastDelay() {
        return 25;
    }

    @Override
    public Item getCastItem() {
        return InitItems.SUMMON_MAGIC_SWORD_SPELL_ITEM.get();
    }
}
