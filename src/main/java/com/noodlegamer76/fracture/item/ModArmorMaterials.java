package com.noodlegamer76.fracture.item;

import com.noodlegamer76.fracture.FractureMod;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {
    DENIM("decor", 64, new int[]{1, 1, 2, 1}, 0,
            SoundEvents.ARMOR_EQUIP_GENERIC, 0f, 0f, () -> Ingredient.of(Items.DEBUG_STICK));

    private final String name;
    private final int durabilityMuntiplyer;
    private final int[] protectionAmounts;
    private final int enchantmentValue;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngerdient;

    private static final int[] BASE_DURIBILITY = {13, 15, 16, 11};

    ModArmorMaterials(String name, int durabilityMuntiplyer, int[] protectionAmounts, int enchantmentValue, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngerdient) {
        this.name = name;
        this.durabilityMuntiplyer = durabilityMuntiplyer;
        this.protectionAmounts = protectionAmounts;
        this.enchantmentValue = enchantmentValue;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngerdient = repairIngerdient;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type pType) {
        return BASE_DURIBILITY[pType.ordinal()] * durabilityMuntiplyer;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type pType) {
        return protectionAmounts[pType.ordinal()];
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairIngerdient.get();
    }

    @Override
    public String getName() {
        return FractureMod.MODID + ":" + name;
    }

    @Override
    public float getToughness() {
        return toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return knockbackResistance;
    }
}
