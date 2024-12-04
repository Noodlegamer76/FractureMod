package com.noodlegamer76.fracture.spellcrafting.spells.spell;

import com.noodlegamer76.fracture.spellcrafting.CardHolder;
import com.noodlegamer76.fracture.spellcrafting.CastState;
import com.noodlegamer76.fracture.spellcrafting.WandCast;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Represents an abstract base class for a magical spell that can be cast by a living entity
 * in a given level. This class defines the basic structure and behavior of spells such as
 * casting, retrieving spell details, and applying casting effects.
 */
public abstract class Spell {
    CardHolder stateSpells;
    ItemStack stack;
    public Entity caster;
    Level level;
    CastState triggerCastState;
    public int life = 0;

    Spell(ItemStack stack, Entity caster) {
        this.stack = stack;
        this.caster = caster;
        this.level = caster.level();
    }

    public void setTriggerCastState(CastState state) {
        this.triggerCastState = state;
    }

    public void tick() {

    }

    //main casting logic
    public void cast() {}

    //runs right before the spell gets added to the SpellTicker but after stateSpells gets initialized
    public void preTicker() {}

    //change the state of the wand cast when adding the spell to the CastState
    public WandCast applyCastEffects(CastState state, WandCast cast) {
        return cast;
    }

    public boolean createsCastStates() {
        return false;
    }

    public int getMaxLife() {
        return 0;
    }

    public int getLife() {
        return life;
    }

    public void setStateSpells(CardHolder stateSpells) {
        this.stateSpells = stateSpells;
    }

    public int draws() {
        return 0;
    }

    public int triggerDraws() {
        return 0;
    }

    public abstract Component getName();

    public abstract float getManaCost();

    public abstract float getRechargeTime();

    public abstract float getCastDelay();



    public abstract Item getCastItem();
}
