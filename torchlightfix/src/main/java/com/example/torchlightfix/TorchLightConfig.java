package com.example.torchlightfix;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public final class TorchLightConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger("torchlightfix");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    // ---- what the config file contains (these are also the defaults) ----
    @SerializedName("light_level")
    private int lightLevel = 5;

    @SerializedName("items")
    private List<String> items = List.of(
        "glowstone", "glow_berries", "shroomlight", "glow_item_frame",
        "lava_bucket", "magma_block", "ender_chest", "enchanting_table",
        "beacon", "sea_pickle", "amethyst_cluster", "large_amethyst_bud",
        "blaze_powder", "blaze_rod", "redstone_block", "nether_star",
        "end_crystal", "ender_eye"
    );

    @SerializedName("item_suffixes")
    private List<String> itemSuffixes = List.of("torch", "lantern", "campfire", "froglight");

    // ---- values the rest of the mod reads ----
    private static volatile int activeLevel;
    private static volatile Set<String> activeItems;
    private static volatile List<String> activeSuffixes;

    static {
        use(new TorchLightConfig());
    }

    public static int lightLevel() {
        return activeLevel;
    }

    public static boolean isLightItem(String path) {
        if (activeItems.contains(path)) return true;
        for (String suffix : activeSuffixes) {
            if (path.endsWith(suffix)) return true;
        }
        return false;
    }

    public static void load() {
        Path file = FabricLoader.getInstance().getConfigDir().resolve("torchlightfix.json");

        if (!Files.exists(file)) {
            try {
                Files.createDirectories(file.getParent());
                try (Writer w = Files.newBufferedWriter(file)) {
                    GSON.toJson(new TorchLightConfig(), w);
                }
            } catch (IOException e) {
                LOGGER.warn("Could not create {}, using defaults", file, e);
            }
            return; // defaults are already active
        }

        try (Reader r = Files.newBufferedReader(file)) {
            TorchLightConfig read = GSON.fromJson(r, TorchLightConfig.class);
            if (read != null) use(read);
        } catch (IOException | JsonParseException e) {
            LOGGER.warn("Could not read {}, using defaults (file left untouched)", file, e);
        }
        LOGGER.info("Torch Light Fix: light level {}, {} items, {} suffixes",
            activeLevel, activeItems.size(), activeSuffixes.size());
    }

    private static void use(TorchLightConfig cfg) {
        activeLevel = Math.max(0, Math.min(15, cfg.lightLevel));
        activeItems = clean(cfg.items);
        activeSuffixes = List.copyOf(clean(cfg.itemSuffixes));
    }

    /** Lowercases, trims, strips "minecraft:" style prefixes, drops empty entries. */
    private static Set<String> clean(List<String> raw) {
        Set<String> out = new HashSet<>();
        if (raw == null) return out;
        for (String s : raw) {
            if (s == null) continue;
            String id = s.trim().toLowerCase(Locale.ROOT);
            id = id.substring(id.indexOf(':') + 1);
            if (!id.isEmpty()) out.add(id);
        }
        return out;
    }
}
