package me.nullium21.autorail.mod;

import me.nullium21.autorail.mod.registry.ARBlocks;
import me.nullium21.autorail.mod.registry.ARRegistry;
import net.fabricmc.api.ModInitializer;

import java.util.List;
import java.util.function.Supplier;

public class Autorail implements ModInitializer {
    public static final String MODID = "autorail";

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
