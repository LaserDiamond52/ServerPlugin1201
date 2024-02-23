package net.laserdiamond.serverplugin1201.Management;

import org.bukkit.NamespacedKey;

public enum ItemPropertiesKeys {

    RARITY_KEY (new NamespacedKey("itemdata", "rarity")),
    STARS_KEY (new NamespacedKey("itemdata", "stars")),
    FIRE_RESISTANCE_KEY (new NamespacedKey("itemdata", "fire_resistant")),
    ITEM_TYPE (new NamespacedKey("itemdata", "itemtype"));

    private final NamespacedKey key;
    ItemPropertiesKeys(NamespacedKey key) {
        this.key = key;
    }

    public NamespacedKey getKey() {
        return key;
    }
}
