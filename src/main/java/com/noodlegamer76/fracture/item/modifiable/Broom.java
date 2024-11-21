package com.noodlegamer76.fracture.item.modifiable;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class Broom extends ModifiableItem {
    public Broom(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        double boundary = 2;
        //AABB box = new AABB(player.getX() - boundary, player.getY() - boundary, player.getZ() - boundary,
        //        player.getX() + boundary, player.getY() + boundary, player.getZ() + boundary);
        AABB box = new AABB(player.getX() - boundary, player.getY() - boundary, player.getZ() - boundary,
                player.getX() + boundary, player.getY() + boundary, player.getZ() + boundary);

        List<Entity> entities = level.getEntities(player, box, EntitySelector.ENTITY_STILL_ALIVE);

        for (Entity entity : entities) {
            if (entity instanceof ItemEntity) {
                double d0 = entity.getX() - player.getX();
                double d1 = entity.getZ() - player.getZ();
                double d2 = Mth.absMax(d0, d1);
                if (d2 >= (double) 0.01F) {
                    d2 = Math.sqrt(d2);
                    d0 /= d2;
                    d1 /= d2;
                    double d3 = 1.0D / d2;
                    if (d3 > 1.0D) {
                        d3 = 1.0D;
                    }

                    d0 *= d3;
                    d1 *= d3;
                    d0 *= 0.4F;
                    d1 *= 0.4F;
                    double height = 0.3D;

                    if (!level.isClientSide) {
                        entity.push(d0, height, d1);
                        player.getCooldowns().addCooldown(this, 15);
                    }
                    for(int i = 0; i < 3; i++) {
                        level.addParticle(ParticleTypes.CLOUD, entity.getX(), entity.getY() + 0.2, entity.getZ(),
                                    d0 * Math.random(), height * Math.random(), d1 * Math.random());
                    }
                }
            }
        }
        return super.use(level, player, usedHand);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        pContext.getLevel().getBlockState(pContext.getClickedPos());
        return super.useOn(pContext);
    }
}
