package me.nullium21.autorail.content.block;

import net.minecraft.block.Block;

public abstract class ARBlock extends Block {
    public ARBlock(Settings settings) {
        super(settings);
    }

    public abstract String getIdentifier();
}
