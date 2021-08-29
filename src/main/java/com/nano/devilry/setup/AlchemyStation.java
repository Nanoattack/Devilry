package com.nano.devilry.setup;

import com.nano.devilry.setup.container.AlchemyStationContainer;
import com.nano.devilry.setup.tileentity.AlchemyStationTile;
import com.nano.devilry.setup.tileentity.ModTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.stream.Stream;

public class AlchemyStation extends Block {

    private static final DirectionProperty FACING = HorizontalBlock.FACING;

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.box(10.5, 0, 10.5, 12.5, 5, 12.5),
            Block.box(11.25, 5, 11.25, 11.75, 5.5, 11.75),
            Block.box(14, 0, 8, 15, 7, 9),
            Block.box(14, 0, 14, 15, 7, 15),
            Block.box(8, 0, 14, 9, 7, 15),
            Block.box(8, 0, 8, 9, 7, 9),
            Block.box(8, 7, 9, 15, 8, 14),
            Block.box(9, 7, 8, 14, 8, 9),
            Block.box(9, 7, 14, 14, 8, 15),
            Block.box(8, 9, 9, 9, 12, 14),
            Block.box(9, 9, 8, 14, 12, 9),
            Block.box(9, 9, 14, 14, 12, 15),
            Block.box(14, 9, 9, 15, 12, 14),
            Block.box(9, 8, 9, 14, 9, 14),
            Block.box(9, 12, 9, 14, 13, 10),
            Block.box(9, 12, 13, 14, 13, 14),
            Block.box(13, 12, 10, 14, 13, 13),
            Block.box(9, 12, 10, 10, 13, 13),
            Block.box(10, 13, 10, 13, 15, 11),
            Block.box(10, 13, 12, 13, 15, 13),
            Block.box(12, 13, 11, 13, 15, 12),
            Block.box(10, 13, 11, 11, 15, 12),
            Block.box(9, 15, 9, 14, 16, 10),
            Block.box(9, 15, 13, 14, 16, 14),
            Block.box(13, 15, 10, 14, 16, 13),
            Block.box(9, 15, 10, 10, 16, 13),
            Block.box(2, 0, 4, 6, 1, 8),
            Block.box(1, 1, 4, 2, 2, 8),
            Block.box(6, 1, 4, 7, 2, 8),
            Block.box(2, 1, 3, 6, 2, 4),
            Block.box(2, 1, 8, 6, 2, 9),
            Block.box(8.149999999999999, 0.25, 2, 10.649999999999999, 1, 6),
            Block.box(11.350000000000001, 0.25, 2, 13.850000000000001, 1, 6),
            Block.box(7.899999999999999, 0, 2, 14.100000000000001, 0.25, 6),
            Block.box(10.899999999999999, 0.25, 2, 11.100000000000001, 0.55, 6),
            Block.box(11.100000000000001, 0.25, 2, 11.350000000000001, 0.7, 6),
            Block.box(10.649999999999999, 0.25, 2, 10.899999999999999, 0.7, 6)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

    private static final VoxelShape SHAPE_W = Stream.of(
                    Block.box(10.5, 0, 3.5, 12.5, 5, 5.5),
                    Block.box(11.25, 5, 4.25, 11.75, 5.5, 4.75),
                    Block.box(8, 0, 1, 9, 7, 2),
                    Block.box(14, 0, 1, 15, 7, 2),
                    Block.box(14, 0, 7, 15, 7, 8),
                    Block.box(8, 0, 7, 9, 7, 8),
                    Block.box(9, 7, 1, 14, 8, 8),
                    Block.box(8, 7, 2, 9, 8, 7),
                    Block.box(14, 7, 2, 15, 8, 7),
                    Block.box(9, 9, 7, 14, 12, 8),
                    Block.box(8, 9, 2, 9, 12, 7),
                    Block.box(14, 9, 2, 15, 12, 7),
                    Block.box(9, 9, 1, 14, 12, 2),
                    Block.box(9, 8, 2, 14, 9, 7),
                    Block.box(9, 12, 2, 10, 13, 7),
                    Block.box(13, 12, 2, 14, 13, 7),
                    Block.box(10, 12, 2, 13, 13, 3),
                    Block.box(10, 12, 6, 13, 13, 7),
                    Block.box(10, 13, 3, 11, 15, 6),
                    Block.box(12, 13, 3, 13, 15, 6),
                    Block.box(11, 13, 3, 12, 15, 4),
                    Block.box(11, 13, 5, 12, 15, 6),
                    Block.box(9, 15, 2, 10, 16, 7),
                    Block.box(13, 15, 2, 14, 16, 7),
                    Block.box(10, 15, 2, 13, 16, 3),
                    Block.box(10, 15, 6, 13, 16, 7),
                    Block.box(5, 0, 11, 9, 1, 14.999999999999998),
                    Block.box(5, 1, 14.999999999999998, 9, 2, 15.999999999999998),
                    Block.box(5, 1, 10, 9, 2, 11),
                    Block.box(4, 1, 11, 5, 2, 14.999999999999998),
                    Block.box(9, 1, 11, 10, 2, 14.999999999999998),
                    Block.box(2, 0.25, 5.35, 6, 1, 7.85),
                    Block.box(2, 0.25, 2.1500000000000004, 6, 1, 4.65),
                    Block.box(2, 0, 1.9000000000000004, 6, 0.25, 8.1),
                    Block.box(2, 0.25, 4.9, 6, 0.55, 5.1),
                    Block.box(2, 0.25, 4.65, 6, 0.7, 4.9),
                    Block.box(2, 0.25, 5.1, 6, 0.7, 5.35)
            ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.box(3.5, 0, 3.5, 5.5, 5, 5.5),
            Block.box(4.25, 5, 4.25, 4.75, 5.5, 4.75),
            Block.box(1, 0, 7, 2, 7, 8),
            Block.box(1, 0, 1, 2, 7, 2),
            Block.box(7, 0, 1, 8, 7, 2),
            Block.box(7, 0, 7, 8, 7, 8),
            Block.box(1, 7, 2, 8, 8, 7),
            Block.box(2, 7, 7, 7, 8, 8),
            Block.box(2, 7, 1, 7, 8, 2),
            Block.box(7, 9, 2, 8, 12, 7),
            Block.box(2, 9, 7, 7, 12, 8),
            Block.box(2, 9, 1, 7, 12, 2),
            Block.box(1, 9, 2, 2, 12, 7),
            Block.box(2, 8, 2, 7, 9, 7),
            Block.box(2, 12, 6, 7, 13, 7),
            Block.box(2, 12, 2, 7, 13, 3),
            Block.box(2, 12, 3, 3, 13, 6),
            Block.box(6, 12, 3, 7, 13, 6),
            Block.box(3, 13, 5, 6, 15, 6),
            Block.box(3, 13, 3, 6, 15, 4),
            Block.box(3, 13, 4, 4, 15, 5),
            Block.box(5, 13, 4, 6, 15, 5),
            Block.box(2, 15, 6, 7, 16, 7),
            Block.box(2, 15, 2, 7, 16, 3),
            Block.box(2, 15, 3, 3, 16, 6),
            Block.box(6, 15, 3, 7, 16, 6),
            Block.box(11, 0, 7, 14.999999999999998, 1, 11),
            Block.box(14.999999999999998, 1, 7, 15.999999999999998, 2, 11),
            Block.box(10, 1, 7, 11, 2, 11),
            Block.box(11, 1, 11, 14.999999999999998, 2, 12),
            Block.box(11, 1, 6, 14.999999999999998, 2, 7),
            Block.box(5.35, 0.25, 10, 7.85, 1, 14),
            Block.box(2.1500000000000004, 0.25, 10, 4.65, 1, 14),
            Block.box(1.9000000000000004, 0, 10, 8.1, 0.25, 14),
            Block.box(4.9, 0.25, 10, 5.1, 0.55, 14),
            Block.box(4.65, 0.25, 10, 4.9, 0.7, 14),
            Block.box(5.1, 0.25, 10, 5.35, 0.7, 14)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            Block.box(3.5, 0, 10.5, 5.5, 5, 12.5),
            Block.box(4.25, 5, 11.25, 4.75, 5.5, 11.75),
            Block.box(7, 0, 14, 8, 7, 15),
            Block.box(1, 0, 14, 2, 7, 15),
            Block.box(1, 0, 8, 2, 7, 9),
            Block.box(7, 0, 8, 8, 7, 9),
            Block.box(2, 7, 8, 7, 8, 15),
            Block.box(7, 7, 9, 8, 8, 14),
            Block.box(1, 7, 9, 2, 8, 14),
            Block.box(2, 9, 8, 7, 12, 9),
            Block.box(7, 9, 9, 8, 12, 14),
            Block.box(1, 9, 9, 2, 12, 14),
            Block.box(2, 9, 14, 7, 12, 15),
            Block.box(2, 8, 9, 7, 9, 14),
            Block.box(6, 12, 9, 7, 13, 14),
            Block.box(2, 12, 9, 3, 13, 14),
            Block.box(3, 12, 13, 6, 13, 14),
            Block.box(3, 12, 9, 6, 13, 10),
            Block.box(5, 13, 10, 6, 15, 13),
            Block.box(3, 13, 10, 4, 15, 13),
            Block.box(4, 13, 12, 5, 15, 13),
            Block.box(4, 13, 10, 5, 15, 11),
            Block.box(6, 15, 9, 7, 16, 14),
            Block.box(2, 15, 9, 3, 16, 14),
            Block.box(3, 15, 13, 6, 16, 14),
            Block.box(3, 15, 9, 6, 16, 10),
            Block.box(7, 0, 1.0000000000000018, 11, 1, 5),
            Block.box(7, 1, 1.7763568394002505e-15, 11, 2, 1.0000000000000018),
            Block.box(7, 1, 5, 11, 2, 6),
            Block.box(11, 1, 1.0000000000000018, 12, 2, 5),
            Block.box(6, 1, 1.0000000000000018, 7, 2, 5),
            Block.box(10, 0.25, 8.15, 14, 1, 10.65),
            Block.box(10, 0.25, 11.35, 14, 1, 13.85),
            Block.box(10, 0, 7.9, 14, 0.25, 14.1),
            Block.box(10, 0.25, 10.9, 14, 0.55, 11.1),
            Block.box(10, 0.25, 11.1, 14, 0.7, 11.35),
            Block.box(10, 0.25, 10.65, 14, 0.7, 10.9)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

    public AlchemyStation(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        switch (p_220053_1_.getValue(FACING)) {
            case NORTH:
                return SHAPE_N;
            case EAST:
                return SHAPE_E;
            case WEST:
                return SHAPE_W;
            case SOUTH:
                return SHAPE_S;
            default:
                return SHAPE_N;
        }
    }
        @SuppressWarnings("deprecation")
        @Override
        public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
        {
            if (!worldIn.isClientSide())
            {
                TileEntity tileEntity = worldIn.getBlockEntity(pos);
                if (tileEntity instanceof AlchemyStationTile)
                {
                    INamedContainerProvider containerProvider = new INamedContainerProvider() {
                        @Override
                        public ITextComponent getDisplayName() {
                            return new TranslationTextComponent("screen.devilry.alchemy_station");
                        }

                        @Override
                        public Container createMenu(int p_createMenu_1_, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                            return new AlchemyStationContainer(p_createMenu_1_, worldIn, pos, playerInventory, playerEntity);
                        }
                    };
                    NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, tileEntity.getBlockPos());
                }
                else
                {
                    throw new IllegalStateException("Our Container provider is missing!");
                }
            }

            return ActionResultType.SUCCESS;
        }

    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return ModTileEntities.ALCHEMY_STATION_TILE_ENTITY.get().create();
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState rotate(BlockState state, Rotation rot)
    {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn)
    {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition (StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(FACING);
    }
}
