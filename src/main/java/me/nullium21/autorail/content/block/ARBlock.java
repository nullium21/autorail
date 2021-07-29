package me.nullium21.autorail.content.block;

import net.minecraft.block.Block;

/**
 * Base class for all blocks in this mod.
 */
public abstract class ARBlock extends Block {
    public ARBlock(Settings settings) {
        super(settings);
    }

    /**
     * @return The name to use in identifiers.
     */
    public abstract String getIdentifier();
}
