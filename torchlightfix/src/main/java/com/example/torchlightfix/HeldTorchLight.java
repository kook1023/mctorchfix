package com.example.torchlightfix;

import java.util.Set;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public final class HeldTorchLight {
    /** The light level other mods will "see" while you hold a light source. */
    public static final int FAKE_LEVEL = 5;

    /** Exact item IDs. One line per item, each one only once, no comma after the last. */
    private static final Set<String> LIGHT_ITEMS = Set.of(
        "glowstone",
        "glow_berries",
        "shroomlight",
        "glow_item_frame",
        "lava_bucket",
        "magma_block",
        "ender_chest",
        "enchanting_table",
        "beacon",
        "sea_pickle",
        "amethyst_cluster",
        "large_amethyst_bud",
        "blaze_powder",
        "blaze_rod",
        "redstone_block",
        "nether_star",
        "end_crystal",
        "ender_eye"
    );

    /** Any item whose ID ends with one of these also counts (catches every variant). */
    private static final String[] LIGHT_SUFFIXES = {
        "torch",      // torch, soul_torch, copper_torch, redstone_torch
        "lantern",    // lantern, soul_lantern, every copper_lantern, sea_lantern, jack_o_lantern
        "campfire",   // campfire, soul_campfire
        "froglight"   // ochre, verdant, pearlescent
    };

    private HeldTorchLight() {}

    public static int apply(Object self, BlockPos pos, int original) {
        if (original >= FAKE_LEVEL) return original;
        if (!(self instanceof Level level)) return original;

        for (Player player : level.players()) {
            if (isAtPlayer(player, pos) && holdingLightSource(player)) {
                return FAKE_LEVEL;
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
        if (LIGHT_ITEMS.contains(path)) return true;
        for (String suffix : LIGHT_SUFFIXES) {
            if (path.endsWith(suffix)) return true;
        }
        return false;
    }
}
