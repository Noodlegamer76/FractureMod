package com.noodlegamer76.fracture.network;

import com.noodlegamer76.fracture.entity.block.SkyboxGeneratorEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SSkyboxGeneratorPacket {
    public int skybox = 10;
    public int rotationInitial = 1;
    public int rotationSpeed = 10;
    public int transparency = 10; //max 255
    public int renderPriority = 10; //order of skybox rendering
    public int minRenderDistance = 48;
    public int maxRenderDistance = 64;
    public BlockPos pos;

    public SSkyboxGeneratorPacket(int[] array, BlockPos pos) {
        this.skybox = array[0];
        this.rotationInitial = array[1];
        this.rotationSpeed = array[2];
        this.transparency = array[3];
        this.renderPriority = array[4];
        this.minRenderDistance = array[5];
        this.maxRenderDistance = array[6];
        this.pos = pos;
    }

    public SSkyboxGeneratorPacket(FriendlyByteBuf buf) {
        this(buf.readVarIntArray(), buf.readBlockPos());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeVarIntArray(new int[] {skybox, rotationInitial, rotationSpeed, transparency, renderPriority, minRenderDistance, maxRenderDistance});
        buf.writeBlockPos(pos);
    }

   // public void handle(NetworkEvent.ServerCustomPayloadEvent context) {
   //
   // }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        ServerPlayer player = contextSupplier.get().getSender();
        Level level = player.level();
        SkyboxGeneratorEntity entity = (SkyboxGeneratorEntity) level.getBlockEntity(pos);
        entity.skybox = skybox;
        entity.rotationInitial = rotationInitial;
        entity.rotationSpeed = rotationSpeed;
        entity.transparency = transparency;
        entity.renderPriority = renderPriority;
        entity.minRenderDistance = minRenderDistance;
        entity.maxRenderDistance = maxRenderDistance;
        level.sendBlockUpdated(pos, entity.getBlockState(), entity.getBlockState(), 2);
        entity.setChanged();

    }
}
