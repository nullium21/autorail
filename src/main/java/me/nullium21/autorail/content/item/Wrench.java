package me.nullium21.autorail.content.item;

import me.nullium21.autorail.content.block.Signal;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public class Wrench extends ARItem {
    public Wrench(Settings settings) {
        super(settings);
    }

    @Override
    public String getIdentifier() {
        return "rail_wrench";
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext ctx) {
        World world = ctx.getWorld();
        BlockState blockState = world.getBlockState(ctx.getBlockPos());
        Block block = blockState.getBlock();

        if (block instanceof Signal) {
            Signal.RedstoneBehavior currentBehavior = blockState.get(Signal.REDSTONE_BEHAVIOR);
            Signal.RedstoneBehavior nextBehavior = nextSignalRedstoneBehavior(currentBehavior);

            world.setBlockState(ctx.getBlockPos(),
                    blockState.with(Signal.REDSTONE_BEHAVIOR, nextBehavior));

            if (ctx.getPlayer() != null)
                ctx.getPlayer().sendMessage(Text.of(
                        String.format("Changed redstone behavior from %s to %s.", currentBehavior, nextBehavior)),
                        false);
        }

        return super.useOnBlock(ctx);
    }

    private static Signal.RedstoneBehavior nextSignalRedstoneBehavior(Signal.RedstoneBehavior current) {
        return Signal.RedstoneBehavior.values()[
                (current.ordinal() + 1) %
                Signal.RedstoneBehavior.values().length];
    }
}
