package net.laserdiamond.ventureplugin.items.util;

import net.laserdiamond.ventureplugin.VenturePlugin;
import org.bukkit.NamespacedKey;

public enum UniqueVentureItemDataKey {

    TOTAL_KILLS(new NamespacedKey(VenturePlugin.getInstance(), "total_kills")),
    SOUL_FIRE_BLAZE_DAMAGE_BONUS(new NamespacedKey(VenturePlugin.getInstance(), "sbf_damage_bonus"));

    private final NamespacedKey key;

    UniqueVentureItemDataKey(NamespacedKey key)
    {
        this.key = key;
    }

    public NamespacedKey getKey() {
        return key;
    }
}
