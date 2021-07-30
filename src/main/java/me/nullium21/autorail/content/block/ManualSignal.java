package me.nullium21.autorail.content.block;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * A signal that needs to be toggled manually.
 */
public class ManualSignal extends Signal {

    public ManualSignal(Settings settings) {
        super(settings);
    }

    @Override
    public String getIdentifier() {
        return "manual_signal";
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        world.setBlockState(pos, state.with(CLOSED, !state.get(CLOSED)));

        return ActionResult.SUCCESS;
    }
}
