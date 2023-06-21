package io.github.nano.devilry.item.custom;

import io.github.nano.devilry.data.recipes.BlockStateContainer;
import io.github.nano.devilry.data.recipes.ModRecipeTypes;
import io.github.nano.devilry.data.recipes.PestleRecipe;
import io.github.nano.devilry.networking.ModMessages;
import io.github.nano.devilry.networking.packets.BreakParticlePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;
import java.util.Optional;

public class Pestle extends Item {
    public Pestle(Properties properties) {
        super(properties);
    }
    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Level level = context.getLevel();

        if(!level.isClientSide) {
            Player player = Objects.requireNonNull(context.getPlayer());
            BlockState clickedBlock = level.getBlockState(context.getClickedPos());

            return rightClickOnCertainBlockState(clickedBlock, context, player, stack, level);
        }
        return super.onItemUseFirst(stack, context);
    }

    private InteractionResult rightClickOnCertainBlockState(BlockState clickedBlock, UseOnContext context, Player player, ItemStack stack, Level level) {
        if (!level.isClientSide() && player.isShiftKeyDown()) {
            player.getCooldowns().addCooldown(this, 10);
            BlockStateContainer container = new BlockStateContainer(clickedBlock, level);
            stack.hurtAndBreak(1, player, player1 -> player1.broadcastBreakEvent(context.getHand()));
            Optional<PestleRecipe> recipe = level.getRecipeManager().getRecipeFor(ModRecipeTypes.PESTLE_RECIPE.get(),
                    container, level);

            if (recipe.isPresent() && level.random.nextDouble() <= recipe.get().getChance()) {
                level.addFreshEntity(new ItemEntity(level,
                        context.getClickedPos().getX(),
                        context.getClickedPos().getY(),
                        context.getClickedPos().getZ(),
                        recipe.get().assemble(container, level.registryAccess())));

                level.destroyBlock(context.getClickedPos(), false);
                context.getLevel().playSound(null, context.getClickedPos(), SoundEvents.CALCITE_PLACE, SoundSource.NEUTRAL, 1.0F, 0.8F + context.getLevel().random.nextFloat() * 0.4F);
                return InteractionResult.SUCCESS;
            } else if (recipe.isPresent()) {
                context.getLevel().playSound(null, context.getClickedPos(), SoundEvents.STONE_FALL, SoundSource.NEUTRAL, 1.0f, 0.8F + context.getLevel().random.nextFloat() * 0.4f);
                for (ServerPlayer serverPlayer : level.getServer().getPlayerList().getPlayers()) {
                    ModMessages.sendToPlayer(new BreakParticlePacket(context.getClickedPos()), serverPlayer);
                }
            } else {
                context.getLevel().playSound(null, context.getClickedPos(), SoundEvents.POLISHED_DEEPSLATE_PLACE, SoundSource.NEUTRAL, 1.0F, 0.8F + context.getLevel().random.nextFloat() * 0.4F);
                return InteractionResult.FAIL;
            }
        }
        return InteractionResult.FAIL;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        ItemStack item = itemStack.copy();
        return item.hurt(1, RandomSource.create(), null) ? null : item;
    }
}
