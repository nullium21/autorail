package me.nullium21.autorail.mod.registry;

import me.nullium21.autorail.base.ARBlock;
import me.nullium21.autorail.base.ARIdentifiableBlockItem;
import me.nullium21.autorail.content.block.*;
import me.nullium21.autorail.mod.Autorail;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Blocks registry for the mod.
 */
public class ARBlocks implements ARRegistry<ARBlock> {

    /**
     * Default block settings for all signal blocks.
     */
    private static final AbstractBlock.Settings S_SIGNAL =
            FabricBlockSettings.of(Material.METAL)
                    .strength(4f);

    /**
     * Default item settings for block items.
     */
    public static final FabricItemSettings S_BLOCK_ITEM = new FabricItemSettings()
            .group(ItemGroup.TRANSPORTATION);

    /**
     * The rail signal block singleton.
     */
    public static final Signal B_SIGNAL = new Signal(S_SIGNAL);

    /**
     * The chain signal block singleton.
     */
    public static final ChainSignal B_CHAIN_SIGNAL = new ChainSignal(S_SIGNAL);

    /**
     * The manual signal block singleton.
     */
    public static final ManualSignal B_MANUAL_SIGNAL = new ManualSignal(S_SIGNAL);

    /**
     * The list of blocks to be registered.
     */
    public static final List<ARBlock> BLOCKS = List.of(
            B_SIGNAL, B_CHAIN_SIGNAL, B_MANUAL_SIGNAL
    );

    /**
     * The list of names for the blocks which should not have associated items.
     */
    public static final List<String> NO_ITEMS = List.of(

    );

    public static final Map<ARBlock, ARIdentifiableBlockItem> B_ITEMS = new HashMap<>();

    @Override
    public void register() {
        BLOCKS.forEach(block -> {
            Identifier id = new Identifier(Autorail.MODID, block.getIdentifier());

            Registry.register(Registry.BLOCK, id, block);

            if (!NO_ITEMS.contains(id.getPath()))
                B_ITEMS.put(block, Registry.register(Registry.ITEM, id,
                        new ARIdentifiableBlockItem(block, S_BLOCK_ITEM) {
                            @Override
                            public String getIdentifier() { return block.getIdentifier(); }
                        }));
        });
    }

    @Override
    public Collection<ARBlock> getAll() {
        return BLOCKS;
    }
}
