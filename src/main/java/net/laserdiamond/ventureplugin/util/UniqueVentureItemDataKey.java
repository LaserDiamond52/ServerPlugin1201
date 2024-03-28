package net.laserdiamond.ventureplugin.util;

import org.bukkit.NamespacedKey;

public enum UniqueVentureItemDataKey {

    SOUL_FIRE_BLAZE_TOTAL_KILLS(new NamespacedKey("sfb_armor_data", "total_kills")),
    SOUL_FIRE_BLAZE_DAMAGE_BONUS(new NamespacedKey("sfb_armor_data", "damage_bonus")),
    SOUL_FIRE_BLAZE_KILLS_TO_NEXT_PRESTIGE(new NamespacedKey("sfb_armor_data", "kills_to_next_prestige")),
    SOUL_FIRE_BLAZE_PRESTIGE(new NamespacedKey("sfb_armor_data", "prestige"));

    private final NamespacedKey key;

    UniqueVentureItemDataKey(NamespacedKey key)
    {
        this.key = key;
    }

    public NamespacedKey getKey() {
        return key;
    }
}
