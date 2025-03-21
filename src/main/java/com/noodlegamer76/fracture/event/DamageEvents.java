package com.noodlegamer76.fracture.event;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.entity.projectile.AbstractProjectileSpellEntity;
import com.noodlegamer76.fracture.item.InitItems;
import com.noodlegamer76.fracture.particles.InitParticles;
import com.noodlegamer76.fracture.spellcrafting.spells.SpellTicker;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;

import java.sql.SQLOutput;

@Mod.EventBusSubscriber(modid = FractureMod.MODID)
public class DamageEvents {

    @SubscribeEvent
    public static void attackEvent(LivingDamageEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            ItemStack stack = player.getMainHandItem();

            if (stack.getItem() == InitItems.PARASITIC_SWORD.get()) {
                float damage = event.getAmount();
                player.heal(damage / 6 > 12 ? 12 : damage / 6);

                if (player.getItemBySlot(EquipmentSlot.HEAD).is(InitItems.BLOODY_SKULL.get())) {
                    player.heal(damage / 6 > 12 ? 12 : damage / 6);
                }
            }

        }
    }

    @SubscribeEvent
    public static void tooltipEvent(ItemTooltipEvent event) {
        if (event.getItemStack().is(InitItems.PARASITIC_SWORD.get())) {
            event.getToolTip().add(1, Component.literal("§4Absorbs health when you hit mobs"));
        }
        else if (event.getItemStack().is(InitItems.BLOODY_SKULL.get())) {
            event.getToolTip().add(1, Component.literal("§4Enhances the lifesteal of the Parasitic Sword"));
        }
        else if (event.getItemStack().is(InitItems.PARASITIC_AXE.get()) ||
                event.getItemStack().is(InitItems.PARASITIC_PICKAXE.get()) ||
                event.getItemStack().is(InitItems.PARASITIC_SHOVEL.get())) {
            event.getToolTip().add(1, Component.literal("§4Mines quickly at the cost of your health"));
        }
        else if (event.getItemStack().is(InitItems.PERMAFROST_SWORD.get())) {
            event.getToolTip().add(1, Component.literal("§bFreezes enemies for a short amount of time"));
        }
        else if (event.getItemStack().is(InitItems.PERMAFROST_PICKAXE.get())) {
            event.getToolTip().add(1, Component.literal("§bRight click to place blue ice"));
        }
        else if (event.getItemStack().is(InitItems.PERMAFROST_AXE.get())) {
            event.getToolTip().add(1, Component.literal("§bSlows enemies for a short time"));
        }
        else if (event.getItemStack().is(InitItems.PERMAFROST_SHOVEL.get())) {
            event.getToolTip().add(1, Component.literal("§btba"));
        }
        else if (event.getItemStack().is(InitItems.PERMAFROST_HOE.get())) {
            event.getToolTip().add(1, Component.literal("§btba"));
        }

    }
}
