package net.laserdiamond.ventureplugin.util;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;

public enum VentureItemStatKeys {

    ARMOR_MELEE_DAMAGE_KEY(new NamespacedKey("armor_data", "melee_damage"), ChatColor.GRAY + "Melee Damage: +", ChatColor.RED),
    ARMOR_RANGE_DAMAGE_KEY(new NamespacedKey("armor_data", "range_damage"), ChatColor.GRAY + "Range Damage: +", ChatColor.DARK_PURPLE),
    ARMOR_MAGIC_DAMAGE_KEY(new NamespacedKey("armor_data", "magic_damage"), ChatColor.GRAY + "Magic Damage: +", ChatColor.AQUA),
    ARMOR_MAX_MANA_KEY(new NamespacedKey("armor_data", "max_mana"), ChatColor.GRAY + "Mana: +", ChatColor.BLUE),
    ARMOR_HEALTH_KEY(new NamespacedKey("armor_data", "health"), ChatColor.GRAY + "Health: +", ChatColor.RED),
    ARMOR_DEFENSE_KEY(new NamespacedKey("armor_data", "defense"), ChatColor.GRAY + "Defense: +", ChatColor.GREEN),
    ARMOR_FIRE_DEFENSE_KEY(new NamespacedKey("armor_data", "fire_defense"), ChatColor.GRAY + "Fire Defense: +", ChatColor.GREEN),
    ARMOR_PROJECTILE_DEFENSE_KEY(new NamespacedKey("armor_data", "projectile_defense"), ChatColor.GRAY + "Projectile Defense: +", ChatColor.GREEN),
    ARMOR_EXPLOSION_DEFENSE_KEY(new NamespacedKey("armor_data", "explosion_defense"), ChatColor.GRAY + "Blast Defense: +", ChatColor.GREEN),
    ARMOR_MAGIC_DEFENSE_KEY(new NamespacedKey("armor_data", "magic_defense"), ChatColor.GRAY + "Magic Defense: +", ChatColor.GREEN),
    ARMOR_TOUGHNESS_KEY(new NamespacedKey("armor_data", "toughness"), ChatColor.GRAY + "Toughness: +", ChatColor.GREEN),
    ARMOR_FORTITUDE_KEY(new NamespacedKey("armor_data", "fortitude"), ChatColor.GRAY + "Fortitude: +", ChatColor.DARK_GREEN),
    ARMOR_SPEED_KEY(new NamespacedKey("armor_data", "speed"), ChatColor.GRAY + "Speed: +", ChatColor.WHITE);

    private final NamespacedKey key;
    private final String displayName;
    private final ChatColor displayColor;

    VentureItemStatKeys(NamespacedKey key, String displayName, ChatColor displayColor) {
        this.key = key;
        this.displayName = displayName;
        this.displayColor = displayColor;
    }

    public static boolean isPercentageStat(VentureItemStatKeys ventureItemStatKeys) {

        if (ventureItemStatKeys.equals(ARMOR_MELEE_DAMAGE_KEY) || ventureItemStatKeys.equals(ARMOR_RANGE_DAMAGE_KEY) || ventureItemStatKeys.equals(ARMOR_MAGIC_DAMAGE_KEY)) {
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
