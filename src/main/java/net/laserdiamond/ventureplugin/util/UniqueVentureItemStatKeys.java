package net.laserdiamond.ventureplugin.util;

import org.bukkit.NamespacedKey;

public enum UniqueVentureItemStatKeys {

    SOUL_FIRE_BLAZE_KILL_COUNT (new NamespacedKey("unique_armor_data", "soul_fire_blaze_kill_count"));

    private final NamespacedKey key;

    UniqueVentureItemStatKeys(NamespacedKey key)
    {
        this.key = key;
    }

    public NamespacedKey getKey() {
        return key;
    }
}
