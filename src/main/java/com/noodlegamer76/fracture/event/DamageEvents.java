package com.noodlegamer76.fracture.event;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.item.InitItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
}
