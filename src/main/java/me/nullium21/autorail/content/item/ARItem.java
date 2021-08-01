package me.nullium21.autorail.content.item;

import me.nullium21.autorail.base.ARIdentifiable;
import net.minecraft.item.Item;

public abstract class ARItem extends Item implements ARIdentifiable {
    public ARItem(Settings settings) {
        super(settings);
    }
}
