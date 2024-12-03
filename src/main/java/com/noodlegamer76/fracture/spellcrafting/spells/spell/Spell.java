package com.noodlegamer76.fracture.spellcrafting.spells.spell;

import com.noodlegamer76.fracture.spellcrafting.CastState;
import com.noodlegamer76.fracture.spellcrafting.WandCast;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;

public abstract class Spell {
    ItemStack stack;
    LivingEntity caster;
    Level level;
    public int life = 0;

    Spell(ItemStack stack, LivingEntity caster) {
        this.stack = stack;
        this.caster = caster;
        this.level = caster.level();
    }

    public int getMaxLife() {
        return 0;
    }

    public int getLife() {
        return life;
    }

    public abstract void cast();

    public abstract Component getName();

    public abstract float getManaCost();

    public abstract float getRechargeTime();

    public abstract float getCastDelay();

    public WandCast applyCastEffects(CastState state, WandCast cast) {
        return cast;
    }

    public abstract Item getCastItem();
}
