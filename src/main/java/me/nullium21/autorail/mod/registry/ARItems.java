package me.nullium21.autorail.mod.registry;

import me.nullium21.autorail.content.item.ARItem;
import me.nullium21.autorail.content.item.Wrench;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Collection;
import java.util.List;

import static me.nullium21.autorail.mod.Autorail.MODID;

public class ARItems implements ARRegistry<ARItem> {

    /**
     * Default settings for items.
     */
    public static final FabricItemSettings S_ITEM = ARBlocks.S_BLOCK_ITEM;

    /**
     * The wrench singleton.
     */
    public static final Wrench I_WRENCH = new Wrench(S_ITEM);

    /**
     * The list of items to be registered.
     */
    public static final List<ARItem> ITEMS = List.of(
            I_WRENCH
    );

    @Override
    public void register() {
        ITEMS.forEach(item -> Registry.register(Registry.ITEM, new Identifier(MODID, item.getIdentifier()), item));
    }

    @Override
    public Collection<ARItem> getAll() {
        return ITEMS;
    }
}
