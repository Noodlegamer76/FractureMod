package com.noodlegamer76.fracture.spellcrafting;

import com.noodlegamer76.fracture.spellcrafting.spells.SpellTicker;
import com.noodlegamer76.fracture.spellcrafting.spells.spell.ProjectileSpell;
import com.noodlegamer76.fracture.spellcrafting.spells.spell.Spell;
import net.minecraft.world.item.ItemStack;

/**
 * The CastState class manages the current state of a wand's cast, tracking the available mana,
 * maximum mana, and recharge time. It manages a collection of spells that can be applied to the
 * wand's cast state and updates the wand's tag data with the current casting state.
 */
public class CastState {
    float currentMana;
    float maxMana;
    float rechargeTime = 0;
    public float inaccuracyMultiplier = 1;
    public float damageMultiplier = 1;
    private CardHolder stateSpells = new CardHolder();
    private CardHolder currentSpells = new CardHolder();
    ItemStack wand;
    public int stateLevel = 0;

    public CastState(ItemStack wand) {
        this.wand = wand;
        currentMana = wand.getTag().getFloat("currentMana");
        maxMana = wand.getTag().getFloat("maxMana");
    }

    void addStateLevel() {
        stateLevel += 1;
    }

    /**
     * Executes the casting of spells contained within the current state. For each spell, it checks if there is sufficient
     * mana available to cast the spell. If there is enough mana, the spell is added to the spell ticker for continuous
     * management, and the mana cost of the spell is subtracted from the current mana pool. It also updates the recharge time
     * based on the casting delay of each spell. The method then updates the wand's tag with the current mana, cast delay,
     * and recharge time values.
     *
     * This method operates on all spells present in the `stateSpells`. If current mana is insufficient for a particular spell,
     * that spell is skipped without affecting the recharge time or current mana.
     */
    public void cast() {
        for (int i = 0; i < stateSpells.spells.size(); i++) {
            Spell spell = stateSpells.spells.get(i);
            if (currentMana >= spell.getManaCost()) {
                if (spell.createsCastStates()) {
                    CastState state = createNewCastState(stateSpells, i);
                    if (state != null) {
                        spell.setTriggerCastState(state);
                    }
                }
                else {
                    addSpellStats(spell);
                    currentSpells.addCard(spell);
                    spell.setStateSpells(currentSpells);
                }
                spell.preTicker();

                //add spells to SpellTicker
                SpellTicker.addSpellToTicker(spell);

                currentMana -= spell.getManaCost();
            }

            rechargeTime += spell.getCastDelay();
        }
        if (stateLevel == 0) {
            wand.getTag().putFloat("currentMana", currentMana);
            wand.getTag().putFloat("currentCastDelay", rechargeTime);
            wand.getTag().putFloat("lastRechargeTime", rechargeTime);
        }

    }

    private CastState createNewCastState(CardHolder spells, int start) {
        start += 1;

        if (start >= spells.spells.size()) {
            System.err.println("Starting index out of bounds: No spells to process.");
            return null;
        }

        CastState castState = new CastState(wand);
        castState.stateLevel = stateLevel;
        castState.addStateLevel();
        System.out.println("Added state level: " + castState.stateLevel);

        int availableSpells = spells.spells.size() - start;
        int draws = Math.min(spells.spells.get(start - 1).triggerDraws(), availableSpells);

        for (int i = 0; i < draws && start < spells.spells.size(); i++) {
            Spell spell = spells.spells.remove(start);
            castState.stateSpells.addCard(spell);
            spell.applyCastEffects(castState);

            if (spell instanceof ProjectileSpell projectileSpell &&
                    spells.spells.get(start - 1) instanceof ProjectileSpell projectileSpell2) {
                projectileSpell.caster = projectileSpell2.getProjectile();
                projectileSpell.getProjectile().setOwner(projectileSpell2.getProjectile());
            }

            draws += spell.draws();
            draws += spell.triggerDraws();
        }

        return castState;
    }

    private void addSpellStats(Spell spell) {
        if (spell instanceof ProjectileSpell projectileSpell) {
            projectileSpell.inaccuracyMultiplier = inaccuracyMultiplier;
            projectileSpell.damageMultiplier = damageMultiplier;
        }
    }

    public void addSpell(Spell spell, WandCast cast) {
        stateSpells.addCard(spell);
        cast.casts += spell.draws();
        cast.casts += spell.triggerDraws();
        spell.applyCastEffects(this);
    }
}
