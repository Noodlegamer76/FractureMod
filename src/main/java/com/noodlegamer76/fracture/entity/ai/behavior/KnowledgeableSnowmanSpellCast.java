package com.noodlegamer76.fracture.entity.ai.behavior;

import com.mojang.datafixers.util.Pair;
import com.noodlegamer76.fracture.client.renderers.entity.MultiAttackMonster;
import com.noodlegamer76.fracture.spellcrafting.CardHolder;
import com.noodlegamer76.fracture.spellcrafting.WandCast;
import com.noodlegamer76.fracture.spellcrafting.spells.CraftedSpells;
import com.noodlegamer76.fracture.spellcrafting.spells.spell.GiantSnowballSpell;
import com.noodlegamer76.fracture.spellcrafting.wand.Wand;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.item.ItemStack;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;

import java.util.List;

public class KnowledgeableSnowmanSpellCast<E extends MultiAttackMonster> extends ExtendedBehaviour<E> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(
            Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT),
            Pair.of(MemoryModuleType.ATTACK_COOLING_DOWN, MemoryStatus.VALUE_ABSENT));

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        if (!entity.hasItemInSlot(EquipmentSlot.MAINHAND) || !(entity.getMainHandItem().getItem() instanceof Wand)) {
            return false;
        }
        return true;
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }

    @Override
    protected void start(E entity) {
        ItemStack wand = entity.getMainHandItem();

        switch (entity.attackNumber) {
            case 1:
                CraftedSpells.setWandSpells(new CraftedSpells().getSnowballLaunch(wand, entity), wand);
                break;
            case 2:
                CraftedSpells.setWandSpells(new CraftedSpells().getSnowballShotgun(wand, entity), wand);
                break;
        }
        new WandCast(entity.level(), entity, wand);
    }
}
