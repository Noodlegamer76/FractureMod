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
            ResourceLocation.fromNamespaceAndPath(FractureMod.MODID, "main"))
            .serverAcceptedVersions((e) -> true)
            .clientAcceptedVersions((e) -> true)
            .networkProtocolVersion(() -> NetworkConstants.NETVERSION)
            .simpleChannel();

    public static void register() {
       INSTANCE.messageBuilder(StructureInstancePacket.class, 0, NetworkDirection.PLAY_TO_CLIENT)
               .encoder(StructureInstancePacket::encode)
               .decoder(StructureInstancePacket::new)
               .consumerMainThread(StructureInstancePacket::handle)
               .add();
    }

    public static void sendToServer(Object msg) {
        INSTANCE.send(PacketDistributor.SERVER.noArg(), msg);
    }

    public static void sendToPlayer(ServerPlayer player, Object msg) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }
}
