package net.laserdiamond.ventureplugin.items.util;

import net.laserdiamond.ventureplugin.util.StatSymbols;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;

public enum VentureItemStatKeys {

    ARMOR_MELEE_DAMAGE_KEY(new NamespacedKey("armor_data", "melee_damage"), ChatColor.GRAY + "Melee Damage: +", ChatColor.RED, StatSymbols.MELEE_DAMAGE),
    ARMOR_RANGE_DAMAGE_KEY(new NamespacedKey("armor_data", "range_damage"), ChatColor.GRAY + "Range Damage: +", ChatColor.DARK_PURPLE, StatSymbols.RANGE_DAMAGE),
    ARMOR_MAGIC_DAMAGE_KEY(new NamespacedKey("armor_data", "magic_damage"), ChatColor.GRAY + "Magic Damage: +", ChatColor.AQUA, StatSymbols.MAGIC_DAMAGE),
    ARMOR_MAX_MANA_KEY(new NamespacedKey("armor_data", "max_mana"), ChatColor.GRAY + "Mana: +", ChatColor.BLUE, StatSymbols.MANA),
    ARMOR_HEALTH_KEY(new NamespacedKey("armor_data", "health"), ChatColor.GRAY + "Health: +", ChatColor.RED, StatSymbols.HEALTH),
    ARMOR_DEFENSE_KEY(new NamespacedKey("armor_data", "defense"), ChatColor.GRAY + "Defense: +", ChatColor.GREEN, StatSymbols.DEFENSE),
    ARMOR_FIRE_DEFENSE_KEY(new NamespacedKey("armor_data", "fire_defense"), ChatColor.GRAY + "Fire Defense: +", ChatColor.GOLD, StatSymbols.DEFENSE),
    ARMOR_PROJECTILE_DEFENSE_KEY(new NamespacedKey("armor_data", "projectile_defense"), ChatColor.GRAY + "Projectile Defense: +", ChatColor.DARK_PURPLE, StatSymbols.DEFENSE),
    ARMOR_EXPLOSION_DEFENSE_KEY(new NamespacedKey("armor_data", "explosion_defense"), ChatColor.GRAY + "Blast Defense: +", ChatColor.DARK_RED, StatSymbols.DEFENSE),
    ARMOR_MAGIC_DEFENSE_KEY(new NamespacedKey("armor_data", "magic_defense"), ChatColor.GRAY + "Magic Defense: +", ChatColor.AQUA, StatSymbols.DEFENSE),
    ARMOR_TOUGHNESS_KEY(new NamespacedKey("armor_data", "toughness"), ChatColor.GRAY + "Toughness: +", ChatColor.GREEN, StatSymbols.TOUGHNESS),
    ARMOR_FORTITUDE_KEY(new NamespacedKey("armor_data", "fortitude"), ChatColor.GRAY + "Fortitude: +", ChatColor.DARK_GREEN, StatSymbols.FORTITUDE),
    ARMOR_SPEED_KEY(new NamespacedKey("armor_data", "speed"), ChatColor.GRAY + "Speed: +", ChatColor.WHITE, StatSymbols.SPEED),
    ARMOR_MOB_FORTUNE_KEY(new NamespacedKey("armor_data", "mob_fortune"), ChatColor.GRAY + "Looting: +", ChatColor.GREEN, StatSymbols.MOB_FORTUNE),
    ARMOR_MINING_FORTUNE_KEY(new NamespacedKey("armor_data", "mining_fortune"), ChatColor.GRAY + "Mining Fortune: +", ChatColor.GREEN, StatSymbols.MINING_FORTUNE),
    ARMOR_FORAGING_FORTUNE_KEY(new NamespacedKey("armor_data", "foraging_fortune"), ChatColor.GRAY + "Foraging Fortune: +", ChatColor.GREEN, StatSymbols.FORAGING_FORTUNE),
    ARMOR_FARMING_FORTUNE_KEY(new NamespacedKey("armor_data", "farming_fortune"), ChatColor.GRAY + "Farming Fortune: +", ChatColor.GREEN, StatSymbols.FARMING_FORTUNE),
    ARMOR_FISHING_LUCK_KEY(new NamespacedKey("armor_data", "fishing_luck"), ChatColor.GRAY + "Fishing Luck: +", ChatColor.DARK_AQUA, StatSymbols.FISHING_LUCK),
    ARMOR_LONGEVITY(new NamespacedKey("armor_data", "longevity"), ChatColor.GRAY + "Longevity: +", ChatColor.DARK_RED, StatSymbols.LONGEVITY),
    ARMOR_CAFFEINATION(new NamespacedKey("armor_data", "caffeination"), ChatColor.GRAY + "Caffeination: +", ChatColor.LIGHT_PURPLE, StatSymbols.CAFFEINATION);

    private final NamespacedKey key;
    private final String displayName;
    private final ChatColor displayColor;
    private final StatSymbols statSymbol;

    VentureItemStatKeys(NamespacedKey key, String displayName, ChatColor displayColor, StatSymbols statSymbol) {
        this.key = key;
        this.displayName = displayName;
        this.displayColor = displayColor;
        this.statSymbol = statSymbol;
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

    public StatSymbols getStatSymbol() {
        return statSymbol;
    }
}
