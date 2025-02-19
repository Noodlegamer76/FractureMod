package com.noodlegamer76.fracture.spellcrafting.data;

import com.noodlegamer76.fracture.FractureMod;
import com.noodlegamer76.fracture.util.InitCapabilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerSpellDataEvents {
    private static final ResourceLocation SPELL_CAP = new ResourceLocation(FractureMod.MODID, "player_spell_data");

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(SPELL_CAP, new PlayerSpellDataProvider());
        }
    }

    @SubscribeEvent
    public static void playerClone(PlayerEvent.Clone event) {
        event.getOriginal().getCapability(InitCapabilities.PLAYER_SPELL_DATA).ifPresent(oldData -> {
            event.getEntity().getCapability(InitCapabilities.PLAYER_SPELL_DATA).ifPresent(newData -> {
                for (Component spell : oldData.spells.keySet()) {
                    newData.unlockSpell(spell, oldData.getSpellAmount(spell));
                }
            });
        });
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        event.getEntity().getCapability(InitCapabilities.PLAYER_SPELL_DATA).ifPresent(data -> {
            CompoundTag tag = new CompoundTag();
            data.saveNBTData(tag);
            data.loadNBTData(tag);
        });
    }

}