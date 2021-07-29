package me.nullium21.autorail.content.block;

public class Signal extends ARBlock {
    public Signal(Settings settings) {
        super(settings);
    }

    @Override
    public String getIdentifier() {
        return "rail_signal";
    }
}
