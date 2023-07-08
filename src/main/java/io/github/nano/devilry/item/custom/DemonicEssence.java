package io.github.nano.devilry.item.custom;

import io.github.nano.devilry.block.ModBlocks;
import io.github.nano.devilry.block.custom.DemonicAltar;
import io.github.nano.devilry.block.custom.DemonicAltarSide;
import io.github.nano.devilry.block.custom.LimeStoneAltar;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class DemonicEssence extends Item {
    public DemonicEssence(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext pContext) {
        if (!pContext.getLevel().isClientSide() && pContext.getItemInHand().is(this)) {
            BlockState clickedBlock = pContext.getLevel().getBlockState(pContext.getClickedPos());
            if (clickedBlock.is(ModBlocks.LIMESTONE_ALTAR.get())) {
                int sides = (clickedBlock.getValue(LimeStoneAltar.NORTH) ? 1 : 0) << 1 |
                        (clickedBlock.getValue(LimeStoneAltar.EAST) ? 1 : 0) << 2 |
                        (clickedBlock.getValue(LimeStoneAltar.SOUTH) ? 1 : 0) << 3 |
                        (clickedBlock.getValue(LimeStoneAltar.WEST) ? 1 : 0) << 4;
                if ((sides & sides-1) != 0 || sides == 0) {
                    return InteractionResult.FAIL;
                }
                var side = pContext.getLevel().getBlockState(pContext.getClickedPos().relative(getDirection(clickedBlock)));
                sides = (side.getValue(LimeStoneAltar.NORTH) ? 1 : 0) << 1 |
                        (side.getValue(LimeStoneAltar.EAST) ? 1 : 0) << 2 |
                        (side.getValue(LimeStoneAltar.SOUTH) ? 1 : 0) << 3 |
                        (side.getValue(LimeStoneAltar.WEST) ? 1 : 0) << 4;
                if ((sides & sides-1) != 0 || sides == 0) {
                    return InteractionResult.FAIL;
                }
                if (isRight(pContext.getHorizontalDirection(), clickedBlock)) {
                    pContext.getLevel().setBlock(pContext.getClickedPos(), ModBlocks.DEMONIC_ALTAR.get().defaultBlockState().setValue(DemonicAltar.FACING, getDirection(clickedBlock).getClockWise()), 3);
                    pContext.getLevel().setBlock(pContext.getClickedPos().relative(getDirection(clickedBlock)), ModBlocks.DEMONIC_ALTAR_SIDE.get().defaultBlockState().setValue(DemonicAltar.FACING, getDirection(clickedBlock).getCounterClockWise()), 3);
                    ((ServerLevel) pContext.getLevel()).sendParticles(ParticleTypes.CRIMSON_SPORE.getType(), pContext.getClickedPos().getX(), pContext.getClickedPos().getY(), pContext.getClickedPos().getZ(), 20, 0, 0, 0, 2);
                    ((ServerLevel) pContext.getLevel()).sendParticles(ParticleTypes.CRIMSON_SPORE.getType(), pContext.getClickedPos().relative(getDirection(clickedBlock)).getX(), pContext.getClickedPos().relative(getDirection(clickedBlock)).getY(), pContext.getClickedPos().relative(getDirection(clickedBlock)).getZ(), 10, 0, 0, 0, 1);
                } else {
                    pContext.getLevel().setBlock(pContext.getClickedPos().relative(getDirection(clickedBlock)), ModBlocks.DEMONIC_ALTAR.get().defaultBlockState().setValue(DemonicAltar.FACING, getDirection(clickedBlock).getCounterClockWise()), 3);
                    pContext.getLevel().setBlock(pContext.getClickedPos(), ModBlocks.DEMONIC_ALTAR_SIDE.get().defaultBlockState().setValue(DemonicAltar.FACING, getDirection(clickedBlock).getClockWise()), 3);
                    ((ServerLevel) pContext.getLevel()).sendParticles(ParticleTypes.CRIMSON_SPORE.getType(), pContext.getClickedPos().getX(), pContext.getClickedPos().getY(), pContext.getClickedPos().getZ(), 20, 0, 0, 0, 2);
                    ((ServerLevel) pContext.getLevel()).sendParticles(ParticleTypes.CRIMSON_SPORE.getType(), pContext.getClickedPos().relative(getDirection(clickedBlock)).getX(), pContext.getClickedPos().relative(getDirection(clickedBlock)).getY(), pContext.getClickedPos().relative(getDirection(clickedBlock)).getZ(), 10, 0, 0, 0, 1);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.useOn(pContext);
    }

    Direction getDirection(BlockState state){
        if (state.getValue(LimeStoneAltar.NORTH)){
            return Direction.NORTH;
        }else if (state.getValue(LimeStoneAltar.EAST)){
            return Direction.EAST;
        }else if (state.getValue(LimeStoneAltar.SOUTH)){
            return Direction.SOUTH;
        }else if (state.getValue(LimeStoneAltar.WEST)){
            return Direction.WEST;
        } else {
            return Direction.NORTH;
        }
    }
    private static boolean isRight(Direction direction, BlockState state) {
        return switch (direction) {
            case NORTH -> state.getValue(LimeStoneAltar.WEST) || state.getValue(LimeStoneAltar.NORTH);
            case SOUTH -> state.getValue(LimeStoneAltar.EAST) || state.getValue(LimeStoneAltar.SOUTH);
            case WEST -> state.getValue(LimeStoneAltar.SOUTH) || state.getValue(LimeStoneAltar.WEST);
            case EAST -> state.getValue(LimeStoneAltar.NORTH) || state.getValue(LimeStoneAltar.EAST);
            default -> state.getValue(LimeStoneAltar.WEST) || state.getValue(LimeStoneAltar.NORTH);
        };
    }
}
