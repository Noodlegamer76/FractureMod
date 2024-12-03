package com.noodlegamer76.fracture.spellcrafting.spells;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.spellcrafting.spells.spell.Spell;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Iterator;

@Mod.EventBusSubscriber(modid = FractureMod.MODID)
public class SpellTicker {
    private static final ArrayList<Spell> spells = new ArrayList<>();

    public static void tickSpells() {
        if (spells.isEmpty()) return;

        Iterator<Spell> iterator = spells.iterator();
        while (iterator.hasNext()) {
            Spell spell = iterator.next();
            spell.cast();
            spell.life += 1;
            if (spell.getLife() > spell.getMaxLife()) {
                iterator.remove();
            }
        }
    }

    public static void addSpellToTicker(Spell spell) {
        if (spell != null) {
            spells.add(spell);
        }
        else {
            System.out.println("null Spell added to SpellTicker");
        }
    }

    @SubscribeEvent
    public static void tickEvent(TickEvent.LevelTickEvent event) {
        if (event.side == LogicalSide.SERVER) {
            SpellTicker.tickSpells();
        }
    }
}