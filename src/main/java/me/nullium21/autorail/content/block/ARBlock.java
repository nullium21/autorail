package me.nullium21.autorail.content.block;

import me.nullium21.autorail.base.ARIdentifiable;
import net.minecraft.block.Block;

/**
 * Base class for all blocks in this mod.
 */
public abstract class ARBlock extends Block implements ARIdentifiable {
    public ARBlock(Settings settings) {
        super(settings);
    }
}
