package net.laserdiamond.serverplugin1201.management;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;

public enum ItemStatKeys {

    MELEE_DAMAGE_KEY (new NamespacedKey("itemdata", "melee_damage"), ChatColor.GRAY + "Melee Damage: +", ChatColor.RED),
    RANGE_DAMAGE_KEY (new NamespacedKey("itemdata", "range_damage"), ChatColor.GRAY + "Range Damage: +", ChatColor.DARK_PURPLE),
    MAGIC_DAMAGE_KEY (new NamespacedKey("itemdata", "magic_damage"), ChatColor.GRAY + "Magic Damage: +", ChatColor.AQUA),
    MAX_MANA_KEY (new NamespacedKey("itemdata", "max_mana"), ChatColor.GRAY + "Mana: +", ChatColor.BLUE),
    HEALTH_KEY (new NamespacedKey("itemdata", "health"), ChatColor.GRAY + "Health: +", ChatColor.RED),
    ARMOR_KEY (new NamespacedKey("itemdata", "armor"), ChatColor.GRAY + "Armor: +", ChatColor.GREEN),
    TOUGHNESS_KEY (new NamespacedKey("itemdata", "toughness"), ChatColor.GRAY + "Toughness: +", ChatColor.GREEN),
    FORTITUDE_KEY (new NamespacedKey("itemdata", "fortitude"), ChatColor.GRAY + "Fortitude: +", ChatColor.DARK_GREEN),
    SPEED_KEY (new NamespacedKey("itemdata", "speed"), ChatColor.GRAY + "Speed: +", ChatColor.WHITE);

    private final NamespacedKey key;
    private final String displayName;
    private final ChatColor displayColor;

    ItemStatKeys(NamespacedKey key, String displayName, ChatColor displayColor) {
        this.key = key;
        this.displayName = displayName;
        this.displayColor = displayColor;
    }

    public static boolean isPercentageStat(ItemStatKeys itemStatKeys) {

        if (itemStatKeys.equals(MELEE_DAMAGE_KEY) || itemStatKeys.equals(RANGE_DAMAGE_KEY) || itemStatKeys.equals(MAGIC_DAMAGE_KEY)) {
            return true;
        }
        return false;
    }

    public NamespacedKey getKey() {
        return key;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ChatColor getDisplayColor() {
        return displayColor;
    }
}
