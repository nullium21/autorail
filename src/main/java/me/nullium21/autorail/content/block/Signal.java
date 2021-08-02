package me.nullium21.autorail.content.block;

import me.nullium21.autorail.base.ARBlock;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.stream.IntStream;

import static net.minecraft.state.property.Properties.HORIZONTAL_FACING;
import static net.minecraft.util.math.Direction.NORTH;
import static net.minecraft.util.math.Direction.SOUTH;
import static net.minecraft.util.math.Direction.EAST;
import static net.minecraft.util.math.Direction.WEST;

/**
 * The rail signal block.
 */
public class Signal extends ARBlock {

    public enum RedstoneBehavior implements StringIdentifiable {
        /**
         * Won't emit any output nor accept any redstone inputs.
         */
        NONE("none"),
        /**
         * Will emit the highest possible value when open.
         */
        EMIT_OPEN("emit_open"),
        /**
         * Will emit the highest possible value when closed.
         */
        EMIT_CLOSED("emit_closed"),
        /**
         * Will close when input a >0 redstone signal.
         */
        CLOSE_ON_POWER("close_on_power"),
        /**
         * Will open when input a >0 redstone signal.
         */
        OPEN_ON_POWER("open_on_power");

        public final String id;
        RedstoneBehavior(String id) {
            this.id = id;
        }
        @Override
        public String asString() {
            return id;
        }
    }

    /**
     * The 'closed' blockstate property controlling the signal's light color.
     */
    public static final BooleanProperty CLOSED = BooleanProperty.of("closed");

    public static final EnumProperty<RedstoneBehavior> REDSTONE_BEHAVIOR =
            EnumProperty.of("redstone_behavior", RedstoneBehavior.class);

    public Signal(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState()
                .with(CLOSED, false)
                .with(REDSTONE_BEHAVIOR, RedstoneBehavior.EMIT_OPEN)
                .with(HORIZONTAL_FACING, NORTH));
    }

    @Override
    public String getIdentifier() {
        return "rail_signal";
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(HORIZONTAL_FACING, CLOSED, REDSTONE_BEHAVIOR);
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

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        RedstoneBehavior rb = state.get(REDSTONE_BEHAVIOR);
        return rb == RedstoneBehavior.EMIT_OPEN || rb == RedstoneBehavior.EMIT_CLOSED;
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return switch (state.get(REDSTONE_BEHAVIOR)) {
            case EMIT_OPEN -> state.get(CLOSED) ? 0 : 15;
            case EMIT_CLOSED -> state.get(CLOSED) ? 15 : 0;
            default -> 0;
        };
    }

    @Override
    public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return getWeakRedstonePower(state, world, pos, direction);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (!world.isClient() && state.get(REDSTONE_BEHAVIOR) != RedstoneBehavior.NONE) {
            if (world.getReceivedRedstonePower(pos) > 0) {
                switch (state.get(REDSTONE_BEHAVIOR)) {
                    case OPEN_ON_POWER:
                        if (state.get(CLOSED)) world.setBlockState(pos, state.with(CLOSED, false));
                        break;
                    case CLOSE_ON_POWER:
                        if (!state.get(CLOSED)) world.setBlockState(pos, state.with(CLOSED, true));
                        break;
                }
            }
        }
    }
}
