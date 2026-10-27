package com.example.torchlightfix;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Core logic. If a player is standing at (or has their head in) the queried
 * block and is holding a torch, any light reading below FAKE_LEVEL is bumped
 * up to FAKE_LEVEL. Nothing in the world is actually changed.
 */
public final class HeldTorchLight {
    /** The light level other mods will "see" while you hold a torch. */
    public static final int FAKE_LEVEL = 5;

    private HeldTorchLight() {}

    public static int apply(Object self, BlockPos pos, int original) {
        if (original >= FAKE_LEVEL) return original;
        if (!(self instanceof Level level)) return original;

        for (Player player : level.players()) {
            if (isAtPlayer(player, pos) && holdingTorch(player)) {
                return FAKE_LEVEL;
            }
        }
        return original;
    }

    private static boolean isAtPlayer(Player player, BlockPos pos) {
        if (pos.getX() != player.getBlockX() || pos.getZ() != player.getBlockZ()) return false;
        return pos.getY() == player.getBlockY() || pos.getY() == Mth.floor(player.getEyeY());
    }

    private static boolean holdingTorch(Player player) {
        return isTorch(player.getMainHandItem()) || isTorch(player.getOffhandItem());
    }

    /** torch, soul_torch, copper_torch, modded *_torch items; not redstone_torch. */
    private static boolean isTorch(ItemStack stack) {
        if (stack.isEmpty()) return false;
        String path = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
        return path.endsWith("torch") && !path.startsWith("redstone");
    }
}
