package me.nullium21.autorail.mod.registry;

import me.nullium21.autorail.content.block.ARBlock;
import me.nullium21.autorail.content.block.ChainSignal;
import me.nullium21.autorail.content.block.Signal;
import me.nullium21.autorail.mod.Autorail;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.List;

public class ARBlocks implements ARRegistry {

    public static final Signal B_SIGNAL = new Signal(
            FabricBlockSettings.of(Material.METAL)
                    .strength(4f));

    public static final ChainSignal B_CHAIN_SIGNAL = new ChainSignal(
            FabricBlockSettings.of(Material.METAL)
                    .strength(4f));

    public static final List<ARBlock> BLOCKS = List.of(
            B_SIGNAL, B_CHAIN_SIGNAL
    );

    public static final List<String> NO_ITEMS = List.of(

    );

    @Override
    public void register() {
        BLOCKS.forEach(block -> {
            Identifier id = new Identifier(Autorail.MODID, block.getIdentifier());

            Registry.register(Registry.BLOCK, id, block);

            if (!NO_ITEMS.contains(id.getPath()))
                Registry.register(Registry.ITEM, id, new BlockItem(block, new FabricItemSettings()
                                .group(ItemGroup.TRANSPORTATION)));
        });
    }
}
