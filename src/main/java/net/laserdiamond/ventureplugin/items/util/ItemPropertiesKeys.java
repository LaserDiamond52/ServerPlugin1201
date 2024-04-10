package net.laserdiamond.ventureplugin.items.util;

import org.bukkit.NamespacedKey;

public enum ItemPropertiesKeys {

    RARITY_KEY (new NamespacedKey("item_data", "rarity")),
    STARS_KEY (new NamespacedKey("item_data", "star")),
    FIRE_RESISTANCE_KEY (new NamespacedKey("item_data", "fire_resistant")),
    POTION_CLAIMED_KEY (new NamespacedKey("item_data", "potion_claimed")),
    ITEM_MAP_KEY(new NamespacedKey("item_data", "item_key"));

    private final NamespacedKey key;
    ItemPropertiesKeys(NamespacedKey key) {
        this.key = key;
    }

    public NamespacedKey getKey() {
        return key;
    }
}
