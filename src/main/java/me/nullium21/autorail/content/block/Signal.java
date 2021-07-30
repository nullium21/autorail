package me.nullium21.autorail.content.block;

import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.stream.IntStream;

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
        Direction dir = getAdjacentRail(ctx.getBlockPos(), ctx.getWorld())
                .flatMap(railPos -> {
                    BlockPos delta = ctx.getBlockPos().subtract(railPos);
                    Direction d = Direction.fromVector(delta);

                    return Optional.ofNullable(d).map(Direction::rotateYClockwise);
                }).orElse(NORTH);

        return getDefaultState().with(HORIZONTAL_FACING, dir);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(2/16d, 0d, 2/16d, 14/16d, 14/16d, 16/16d);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return getAdjacentRail(pos, world).isPresent();
    }

    private static Optional<BlockPos> getAdjacentRail(BlockPos thisPos, WorldView world) {
        return IntStream.range(0, 4)
                .mapToObj(Direction::fromHorizontal)
                .map(d -> thisPos.add(d.getVector()))
                .filter(p -> AbstractRailBlock.isRail(world.getBlockState(p)))
                .filter(p -> {
                    Direction d = Direction.fromVector(thisPos.subtract(p));

                    BlockState st = world.getBlockState(p);
                    AbstractRailBlock arb = (AbstractRailBlock) st.getBlock();

                    return switch (st.get(arb.getShapeProperty())) { // check if ok side
                        case ASCENDING_NORTH, ASCENDING_SOUTH, NORTH_SOUTH -> d == EAST || d == WEST;
                        case ASCENDING_EAST, ASCENDING_WEST, EAST_WEST -> d == NORTH || d == SOUTH;

                        case NORTH_EAST -> d == SOUTH || d == WEST;
                        case NORTH_WEST -> d == SOUTH || d == EAST;
                        case SOUTH_EAST -> d == NORTH || d == WEST;
                        case SOUTH_WEST -> d == NORTH || d == EAST;
                    };
                })
                .findFirst();
    }
}
