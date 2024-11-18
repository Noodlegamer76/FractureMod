package com.noodlegamer76.fracture.item.armor;

import com.noodlegamer76.fracture.FractureMod;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class InvertedGlasses extends ArmorItem {
    public static final ResourceLocation SHADER_LOCATION = new ResourceLocation("minecraft", "shaders/post/flip.json");

    public InvertedGlasses(ArmorMaterial pMaterial, Type pType, Properties pProperties) {
        super(pMaterial, pType, pProperties);
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack item, Player player) {
        return super.onDroppedByPlayer(item, player);
    }

    @Override
    public void onDestroyed(ItemEntity itemEntity, DamageSource damageSource) {
        super.onDestroyed(itemEntity, damageSource);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        startEffect(level);
        super.onArmorTick(stack, level, player);
    }

    @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
        super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
    }

    public void stopEffect(Level level) {

    }

    public void startEffect(Level level) {
        if(level.isClientSide) {
            System.out.println("EARY LOAD");
            if (Minecraft.getInstance().gameRenderer.currentEffect() == null ||
                    !(Minecraft.getInstance().gameRenderer.currentEffect().getName().equals("minecraft:shaders/post/flip.json"))) {

                Minecraft.getInstance().gameRenderer.loadEffect(new ResourceLocation("minecraft", "shaders/post/flip.json"));
                System.out.println("LOADING");
            }
        }
    }
}
