package me.nullium21.autorail.base.interfaces;

@FunctionalInterface
public interface ARIdentifiable {
    /**
     * @return The name to use in identifiers.
     */
    String getIdentifier();
}
