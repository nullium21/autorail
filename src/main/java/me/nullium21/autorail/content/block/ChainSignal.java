package me.nullium21.autorail.content.block;

/**
 * The chain signal block.
 */
public class ChainSignal extends ARBlock {
    public ChainSignal(Settings settings) {
        super(settings);
    }

    @Override
    public String getIdentifier() {
        return "chain_signal";
    }
}
