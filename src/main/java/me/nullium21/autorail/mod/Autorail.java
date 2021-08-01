package me.nullium21.autorail.mod;

import me.nullium21.autorail.mod.registry.ARBlocks;
import me.nullium21.autorail.mod.registry.ARRegistry;
import net.fabricmc.api.ModInitializer;

import java.util.List;
import java.util.function.Supplier;

/**
 * The main entry point for the mod.
 */
public class Autorail implements ModInitializer {
    /**
     * Mod's ID for use in identifiers.
     */
    public static final String MODID = "autorail";

    /**
     * List of registries to be initialized.
     */
    public static final List<ARRegistry<?>> REGISTRIES = List.of(
            new ARBlocks()
    );

    @Override
    public void onInitialize() {
        REGISTRIES.forEach(ARRegistry::register); // register everything
    }
}
