package com.noodlegamer76.fracture.util;

import com.noodlegamer76.fracture.item.InitItems;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class ToolTiers {
    public enum ModItemTier implements Tier {
        BLOOD(2, 1200, 26.0F, 2.0F, 15, () -> Ingredient.of(InitItems.LIVING_FLESH.get())),

        MAGIC(1, 1, 1, 2, 0, () -> Ingredient.EMPTY),

        PERMAFROST(3, 2500, 8.0F, 3.0F, 20,
                () -> Ingredient.of(Items.DIAMOND));

        private final int level;
        private final int uses;
        private final float speed;
        private final float damage;
        private final int enchantmentValue;
        private final LazyLoadedValue<Ingredient> repairIngredient;

        ModItemTier(int level, int durability, float miningSpeed, float damage, int enchantability, Supplier<Ingredient> repairIngredient) {
            this.level = level;
            this.uses = durability;
            this.speed = miningSpeed;
            this.damage = damage;
            this.enchantmentValue = enchantability;
            this.repairIngredient = new LazyLoadedValue<>(repairIngredient);
        }

        public int getUses() {
            return this.uses;
        }

        public float getSpeed() {
            return this.speed;
        }

        public float getAttackDamageBonus() {
            return this.damage;
        }

        public int getLevel() {
            return this.level;
        }

        public int getEnchantmentValue() {
            return this.enchantmentValue;
        }

        public Ingredient getRepairIngredient() {
            return this.repairIngredient.get();
        }
    }
}
