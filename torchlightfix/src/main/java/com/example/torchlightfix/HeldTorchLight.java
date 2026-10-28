package com.example.torchlightfix;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public final class HeldTorchLight {
    private HeldTorchLight() {}

    public static int apply(Object self, BlockPos pos, int original) {
        int fake = TorchLightConfig.lightLevel();
        if (original >= fake) return original;
        if (!(self instanceof Level level)) return original;

        for (Player player : level.players()) {
            if (isAtPlayer(player, pos) && holdingLightSource(player)) {
                return fake;
            }
        }
        return original;
    }

    private static boolean isAtPlayer(Player player, BlockPos pos) {
        if (pos.getX() != player.getBlockX() || pos.getZ() != player.getBlockZ()) return false;
        return pos.getY() == player.getBlockY() || pos.getY() == Mth.floor(player.getEyeY());
    }

    private static boolean holdingLightSource(Player player) {
        return isLightSource(player.getMainHandItem()) || isLightSource(player.getOffhandItem());
    }

    private static boolean isLightSource(ItemStack stack) {
        if (stack.isEmpty()) return false;
        String path = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
        return TorchLightConfig.isLightItem(path);
    }
}
