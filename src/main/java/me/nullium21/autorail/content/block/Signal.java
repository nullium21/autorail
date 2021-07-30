package me.nullium21.autorail.content.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

import static net.minecraft.state.property.Properties.HORIZONTAL_FACING;
import static net.minecraft.util.math.Direction.*;

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
        Optional<BlockPos> railPos = getAdjacentRail(ctx.getBlockPos(), ctx.getWorld());
        Optional<Vec3i>    delta   = railPos.map(it -> it.subtract(ctx.getBlockPos()));

        try {
            return getDefaultState().with(HORIZONTAL_FACING, RAIL_DIRECTIONS.get(delta.get()));
        } catch (Exception e) {
            return getDefaultState().with(HORIZONTAL_FACING, NORTH); // gonna be discarded anyway
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(2/16d, 0d, 2/16d, 14/16d, 14/16d, 16/16d);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return getAdjacentRail(pos, world).isPresent();
    }

    private static Optional<BlockPos> getAdjacentRail(BlockPos pos, WorldView world) {
        return RAIL_DIRECTIONS.keySet().stream()                           // get vectors
                .map(pos::add)                                              // make relative to 'pos'
                .filter(p -> world.getBlockState(p).isIn(BlockTags.RAILS))  // filter for rails block
                .findFirst();
    }

    public static final Map<Vec3i, Direction> RAIL_DIRECTIONS = Map.of(
            new Vec3i(+1, 0, 0), EAST,
            new Vec3i(-1, 0, 0), WEST);
}
