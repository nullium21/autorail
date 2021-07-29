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
    private static final List<Supplier<ARRegistry>> REGISTRIES = List.of(
            ARBlocks::new
    );

    @Override
    public void onInitialize() {
        REGISTRIES.stream()
                .map(Supplier::get) // construct instances of registries
                .forEach(ARRegistry::register); // register everything
    }
}
