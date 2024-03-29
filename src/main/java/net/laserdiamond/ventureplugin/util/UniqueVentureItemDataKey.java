package net.laserdiamond.ventureplugin.util;

import org.bukkit.NamespacedKey;

public enum UniqueVentureItemDataKey {

    TOTAL_KILLS(new NamespacedKey("sfb_armor_data", "total_kills")),
    SOUL_FIRE_BLAZE_DAMAGE_BONUS(new NamespacedKey("sfb_armor_data", "damage_bonus"));

    private final NamespacedKey key;

    UniqueVentureItemDataKey(NamespacedKey key)
    {
        this.key = key;
    }

    public NamespacedKey getKey() {
        return key;
    }
}
