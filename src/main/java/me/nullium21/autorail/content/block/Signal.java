package me.nullium21.autorail.content.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;

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
    }

    @Override
    public String getIdentifier() {
        return "rail_signal";
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(CLOSED);
    }
}
