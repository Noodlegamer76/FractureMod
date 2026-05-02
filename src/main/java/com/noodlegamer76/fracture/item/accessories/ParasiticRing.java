package com.noodlegamer76.fracture.item.accessories;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.UUID;

public class ParasiticRing extends Item implements ICurioItem {
    public static final UUID PARASITIC_RING_DAMAGE_UUID =
            UUID.fromString("95124cfc-f543-4530-811b-565baff8e4ca");

    public static final UUID PARASITIC_RING_HEALTH_UUID =
            UUID.fromString("453eed46-d79a-415f-9f54-1b8149ccbe8a");

    public ParasiticRing(Properties properties) {
        super(properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(
            SlotContext slotContext,
            UUID uuid,
            ItemStack stack
    ) {
        Multimap<Attribute, AttributeModifier> modifiers = HashMultimap.create();

        modifiers.put(
                Attributes.ATTACK_DAMAGE,
                new AttributeModifier(
                        PARASITIC_RING_DAMAGE_UUID,
                        "Parasitic Ring Damage Bonus",
                        2.0D,
                        AttributeModifier.Operation.ADDITION
                )
        );

        modifiers.put(
                Attributes.MAX_HEALTH,
                new AttributeModifier(
                        PARASITIC_RING_HEALTH_UUID,
                        "Parasitic Ring Health Penalty",
                        -4.0D,
                        AttributeModifier.Operation.ADDITION
                )
        );

        return modifiers;
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        LivingEntity living = slotContext.entity();

        float maxHealth = living.getMaxHealth();

        if (living.getHealth() > maxHealth) {
            living.setHealth(maxHealth);
        }
    }
}