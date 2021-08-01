package me.nullium21.autorail.mod.registry;

import me.nullium21.autorail.base.ARIdentifiable;

import java.util.Collection;

/**
 * Base interface for the mod's registries.
 */
public interface ARRegistry<T extends ARIdentifiable> {
    /**
     * Register everything contained in this registry.
     */
    void register();

    Collection<T> getAll();
}
