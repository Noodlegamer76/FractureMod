package com.noodlegamer76.fracture.entity.block;

import com.noodlegamer76.fracture.gui.skyboxgenerator.SkyboxGeneratorMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SkyboxGeneratorEntity extends BlockEntity implements MenuProvider, GeoAnimatable {
    private static final Component TITLE = Component.translatable("gui.fracture.skybox_generator");
    public int skybox = 0;
    public int rotationInitial = 1;
    public int rotationSpeed = 0;
    public int transparency = 0; //max 255
    public int renderPriority = 1; //order of skybox rendering (lower is higher priority)
    public int minRenderDistance = 0;
    public int maxRenderDistance = 512;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    
    
    public SkyboxGeneratorEntity(BlockPos pPos, BlockState pBlockState) {
        super(InitBlockEntities.SKYBOX_GENERATOR.get(), pPos, pBlockState);
    }

    @Override
    public AABB getRenderBoundingBox() {
        float renderDistance = Minecraft.getInstance().gameRenderer.getRenderDistance();
        return new AABB(new Vec3(renderDistance + getBlockPos().getX(), renderDistance + getBlockPos().getY(), renderDistance + getBlockPos().getZ()),
                new Vec3(-renderDistance + getBlockPos().getX(), -renderDistance + getBlockPos().getY(), -renderDistance + getBlockPos().getZ()));
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        skybox = nbt.getInt("skybox");
        rotationInitial = nbt.getInt("rotation_initial");
        rotationSpeed = nbt.getInt("rotation_speed");
        transparency = nbt.getInt("transparency");
        renderPriority = nbt.getInt("render_priority");
        minRenderDistance = nbt.getInt("min_render_distance");
        maxRenderDistance = nbt.getInt("max_render_distance");
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.putInt("skybox", skybox);
        nbt.putInt("rotation_initial", rotationInitial);
        nbt.putInt("rotation_speed", rotationSpeed);
        nbt.putInt("transparency", transparency);
        nbt.putInt("render_priority", renderPriority);
        nbt.putInt("min_render_distance", minRenderDistance);
        nbt.putInt("max_render_distance", maxRenderDistance);
        super.saveAdditional(nbt);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("skybox", skybox);
        nbt.putInt("rotation_initial", rotationInitial);
        nbt.putInt("rotation_speed", rotationSpeed);
        nbt.putInt("transparency", transparency);
        nbt.putInt("render_priority", renderPriority);
        nbt.putInt("min_render_distance", minRenderDistance);
        nbt.putInt("max_render_distance", maxRenderDistance);
        //Write your data into the tag
        return nbt;
    }

    public final ContainerData dataAccess = new ContainerData() {
        public int get(int index) {
            switch (index) {
                case 0:
                    return SkyboxGeneratorEntity.this.skybox;
                case 1:
                    return SkyboxGeneratorEntity.this.rotationInitial;
                case 2:
                    return SkyboxGeneratorEntity.this.rotationSpeed;
                case 3:
                    return SkyboxGeneratorEntity.this.transparency;
                case 4:
                    return SkyboxGeneratorEntity.this.renderPriority;
                case 5:
                    return SkyboxGeneratorEntity.this.minRenderDistance;
                case 6:
                    return SkyboxGeneratorEntity.this.maxRenderDistance;
                default:
                    return 0;
            }
        }

        public void set(int index, int value) {
            switch (index) {
                case 0:
                    SkyboxGeneratorEntity.this.skybox = value;
                    break;
                case 1:
                    SkyboxGeneratorEntity.this.rotationInitial = value;
                    break;
                case 2:
                    SkyboxGeneratorEntity.this.rotationSpeed = value;
                    break;
                case 3:
                    SkyboxGeneratorEntity.this.transparency = value;
                    break;
                case 4:
                    SkyboxGeneratorEntity.this.renderPriority = value;
                    break;
                case 5:
                    SkyboxGeneratorEntity.this.minRenderDistance = value;
                    break;
                case 6:
                    SkyboxGeneratorEntity.this.maxRenderDistance = value;
                    break;
            }

        }

        public int getCount() {
            return 7;
        }
    };

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        // Will get tag from #getUpdateTag
        return ClientboundBlockEntityDataPacket.create(this);
    }


    @Override
    public Component getDisplayName() {
        return TITLE;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        System.out.println(minRenderDistance);
        return new SkyboxGeneratorMenu(pContainerId, pPlayerInventory, this, this.dataAccess);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public double getTick(Object object) {
        return 0;
    }
}
