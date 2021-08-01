package me.nullium21.autorail.base;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public abstract class ARIdentifiableBlockItem extends BlockItem implements ARIdentifiable {
    public ARIdentifiableBlockItem(Block block, Settings settings) {
        super(block, settings);
    }
}
