package me.nullium21.autorail.content.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.state.property.Properties.HORIZONTAL_FACING;
import static net.minecraft.util.math.Direction.NORTH;

/**
 * The rail signal block.
 */
public class Signal extends ARBlock {
    /**
     * The 'closed' blockstate property controlling the signal's light color.
     */
    public static final BooleanProperty CLOSED = BooleanProperty.of("closed");

    public Signal(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState()
                .with(CLOSED, false)
                .with(HORIZONTAL_FACING, NORTH));
    }

    @Override
    public String getIdentifier() {
        return "rail_signal";
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(HORIZONTAL_FACING);
        stateManager.add(CLOSED);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState()
                .with(HORIZONTAL_FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(2/16d, 0d, 2/16d, 14/16d, 14/16d, 16/16d);
    }
}
