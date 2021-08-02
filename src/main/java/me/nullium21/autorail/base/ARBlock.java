package me.nullium21.autorail.base;

import me.nullium21.autorail.base.interfaces.ARIdentifiable;
import net.minecraft.block.Block;

/**
 * Base class for all blocks in this mod.
 */
public abstract class ARBlock extends Block implements ARIdentifiable {
    public ARBlock(Settings settings) {
        super(settings);
    }
}
