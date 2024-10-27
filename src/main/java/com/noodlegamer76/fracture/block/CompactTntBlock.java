package com.noodlegamer76.fracture.block;

import com.noodlegamer76.fracture.entity.CompactPrimedTnt;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;

public class CompactTntBlock extends TntBlock {
    public CompactTntBlock(Properties pProperties) {
        super(pProperties);
    }


    @Deprecated //Forge: Prefer using IForgeBlock#catchFire
    public static void explode(Level pLevel, BlockPos pPos) {
        explode(pLevel, pPos, (LivingEntity)null);
    }

    @Deprecated //Forge: Prefer using IForgeBlock#catchFire
    private static void explode(Level pLevel, BlockPos pPos, @Nullable LivingEntity pEntity) {
        if (!pLevel.isClientSide) {
            CompactPrimedTnt primedtnt = new CompactPrimedTnt(pLevel, (double)pPos.getX() + 0.5D, (double)pPos.getY(), (double)pPos.getZ() + 0.5D, pEntity);
            pLevel.addFreshEntity(primedtnt);
            pLevel.playSound((Player)null, primedtnt.getX(), primedtnt.getY(), primedtnt.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
            pLevel.gameEvent(pEntity, GameEvent.PRIME_FUSE, pPos);
        }
    }

    public void wasExploded(Level pLevel, BlockPos pPos, Explosion pExplosion) {
        if (!pLevel.isClientSide) {
            CompactPrimedTnt primedtnt = new CompactPrimedTnt(pLevel, (double)pPos.getX() + 0.5D, (double)pPos.getY(), (double)pPos.getZ() + 0.5D, pExplosion.getIndirectSourceEntity());
            int i = primedtnt.getFuse();
            primedtnt.setFuse((short)(pLevel.random.nextInt(i / 4) + i / 8));
            pLevel.addFreshEntity(primedtnt);
        }
    }

    public void onCaughtFire(BlockState state, Level world, BlockPos pos, @Nullable net.minecraft.core.Direction face, @Nullable LivingEntity igniter) {
        explode(world, pos, igniter);
    }
}
