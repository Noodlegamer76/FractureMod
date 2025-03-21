package com.noodlegamer76.fracture.network;

import com.noodlegamer76.fracture.FractureMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkConstants;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {
    public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(
            new ResourceLocation(FractureMod.MODID, "main"))
            .serverAcceptedVersions((e) -> true)
            .clientAcceptedVersions((e) -> true)
            .networkProtocolVersion(() -> NetworkConstants.NETVERSION)
            .simpleChannel();

    public static void register() {
        INSTANCE.messageBuilder(SSkyboxGeneratorPacket.class, 0, NetworkDirection.PLAY_TO_SERVER)
                .encoder(SSkyboxGeneratorPacket::encode)
                .decoder(SSkyboxGeneratorPacket::new)
                .consumerMainThread(SSkyboxGeneratorPacket::handle)
                .add();

        INSTANCE.messageBuilder(SpellAmountPacket.class, 1, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(SpellAmountPacket::encode)
                .decoder(SpellAmountPacket::new)
                .consumerMainThread(SpellAmountPacket::handle)
                .add();

        INSTANCE.messageBuilder(SpellInvPacket.class, 0, NetworkDirection.PLAY_TO_SERVER)
                .encoder(SpellInvPacket::encode)
                .decoder(SpellInvPacket::new)
                .consumerMainThread(SpellInvPacket::handle)
                .add();
    }

    public static void sendToServer(Object msg) {
        INSTANCE.send(PacketDistributor.SERVER.noArg(), msg);
    }

    public static void sendToPlayer(ServerPlayer player, Object msg) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }
}
