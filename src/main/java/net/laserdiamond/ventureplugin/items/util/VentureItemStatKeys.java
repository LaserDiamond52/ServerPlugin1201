package net.laserdiamond.ventureplugin.items.util;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.util.StatSymbols;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;

public enum VentureItemStatKeys {

    ARMOR_MELEE_DAMAGE_KEY(new NamespacedKey(VenturePlugin.getInstance(), "armor_melee_damage"), ChatColor.GRAY + "Melee Damage: +", ChatColor.RED, StatSymbols.MELEE_DAMAGE),
    ARMOR_RANGE_DAMAGE_KEY(new NamespacedKey(VenturePlugin.getInstance(), "armor_range_damage"), ChatColor.GRAY + "Range Damage: +", ChatColor.DARK_PURPLE, StatSymbols.RANGE_DAMAGE),
    ARMOR_MAGIC_DAMAGE_KEY(new NamespacedKey(VenturePlugin.getInstance(), "armor_magic_damage"), ChatColor.GRAY + "Magic Damage: +", ChatColor.AQUA, StatSymbols.MAGIC_DAMAGE),
    ARMOR_MAX_MANA_KEY(new NamespacedKey(VenturePlugin.getInstance(), "armor_max_mana"), ChatColor.GRAY + "Mana: +", ChatColor.BLUE, StatSymbols.MANA),
    ARMOR_HEALTH_KEY(new NamespacedKey(VenturePlugin.getInstance(), "armor_health"), ChatColor.GRAY + "Health: +", ChatColor.RED, StatSymbols.HEALTH),
    ARMOR_DEFENSE_KEY(new NamespacedKey(VenturePlugin.getInstance(), "armor_defense"), ChatColor.GRAY + "Defense: +", ChatColor.GREEN, StatSymbols.DEFENSE),
    ARMOR_FIRE_DEFENSE_KEY(new NamespacedKey(VenturePlugin.getInstance(), "armor_fire_defense"), ChatColor.GRAY + "Fire Defense: +", ChatColor.GOLD, StatSymbols.DEFENSE),
    ARMOR_PROJECTILE_DEFENSE_KEY(new NamespacedKey(VenturePlugin.getInstance(), "armor_projectile_defense"), ChatColor.GRAY + "Projectile Defense: +", ChatColor.DARK_PURPLE, StatSymbols.DEFENSE),
    ARMOR_EXPLOSION_DEFENSE_KEY(new NamespacedKey(VenturePlugin.getInstance(), "armor_explosion_defense"), ChatColor.GRAY + "Blast Defense: +", ChatColor.DARK_RED, StatSymbols.DEFENSE),
    ARMOR_MAGIC_DEFENSE_KEY(new NamespacedKey(VenturePlugin.getInstance(), "armor_magic_defense"), ChatColor.GRAY + "Magic Defense: +", ChatColor.AQUA, StatSymbols.DEFENSE),
    ARMOR_TOUGHNESS_KEY(new NamespacedKey(VenturePlugin.getInstance(), "armor_toughness"), ChatColor.GRAY + "Toughness: +", ChatColor.GREEN, StatSymbols.TOUGHNESS),
    ARMOR_FORTITUDE_KEY(new NamespacedKey(VenturePlugin.getInstance(), "armor_fortitude"), ChatColor.GRAY + "Fortitude: +", ChatColor.DARK_GREEN, StatSymbols.FORTITUDE),
    ARMOR_SPEED_KEY(new NamespacedKey(VenturePlugin.getInstance(), "armor_speed"), ChatColor.GRAY + "Speed: +", ChatColor.WHITE, StatSymbols.SPEED),
    ARMOR_MOB_FORTUNE_KEY(new NamespacedKey(VenturePlugin.getInstance(), "armor_mob_fortune"), ChatColor.GRAY + "Looting: +", ChatColor.GREEN, StatSymbols.MOB_FORTUNE),
    ARMOR_MINING_FORTUNE_KEY(new NamespacedKey(VenturePlugin.getInstance(), "armor_mining_fortune"), ChatColor.GRAY + "Mining Fortune: +", ChatColor.GREEN, StatSymbols.MINING_FORTUNE),
    ARMOR_FORAGING_FORTUNE_KEY(new NamespacedKey(VenturePlugin.getInstance(), "armor_foraging_fortune"), ChatColor.GRAY + "Foraging Fortune: +", ChatColor.GREEN, StatSymbols.FORAGING_FORTUNE),
    ARMOR_FARMING_FORTUNE_KEY(new NamespacedKey(VenturePlugin.getInstance(), "armor_farming_fortune"), ChatColor.GRAY + "Farming Fortune: +", ChatColor.GREEN, StatSymbols.FARMING_FORTUNE),
    ARMOR_FISHING_LUCK_KEY(new NamespacedKey(VenturePlugin.getInstance(), "armor_fishing_luck"), ChatColor.GRAY + "Fishing Luck: +", ChatColor.DARK_AQUA, StatSymbols.FISHING_LUCK),
    ARMOR_LUCK_KEY(new NamespacedKey(VenturePlugin.getInstance(), "armor_luck"), ChatColor.GRAY + "Luck: +", ChatColor.GREEN, StatSymbols.LUCK),
    ARMOR_LONGEVITY(new NamespacedKey(VenturePlugin.getInstance(), "armor_longevity"), ChatColor.GRAY + "Longevity: +", ChatColor.DARK_RED, StatSymbols.LONGEVITY),
    ARMOR_CAFFEINATION(new NamespacedKey(VenturePlugin.getInstance(), "armor_caffeination"), ChatColor.GRAY + "Caffeination: +", ChatColor.LIGHT_PURPLE, StatSymbols.CAFFEINATION),
    MAINHAND_HEALTH(new NamespacedKey(VenturePlugin.getInstance(), "mainhand_health"), ChatColor.GRAY + "Health: +", ChatColor.RED, StatSymbols.HEALTH),
    MAINHAND_DEFENSE(new NamespacedKey(VenturePlugin.getInstance(), "mainhand_defense"), ChatColor.GRAY + "Defense: +", ChatColor.GREEN, StatSymbols.DEFENSE),
    MAINHAND_FIRE_DEFENSE(new NamespacedKey(VenturePlugin.getInstance(), "mainhand_fire_defense"), ChatColor.GRAY + "Fire Defense: +", ChatColor.GOLD, StatSymbols.DEFENSE),
    MAINHAND_EXPLOSION_DEFENSE(new NamespacedKey(VenturePlugin.getInstance(), "mainhand_explosion_defense"), ChatColor.GRAY + "Explosion Defense: +", ChatColor.DARK_RED, StatSymbols.DEFENSE),
    MAINHAND_PROJECTILE_DEFENSE(new NamespacedKey(VenturePlugin.getInstance(), "mainhand_projectile_defense"), ChatColor.GRAY + "Projectile Defense: +", ChatColor.DARK_PURPLE, StatSymbols.DEFENSE),
    MAINHAND_MAGIC_DEFENSE(new NamespacedKey(VenturePlugin.getInstance(), "mainhand_magic_defense"), ChatColor.GRAY + "Magic Defense: +", ChatColor.AQUA, StatSymbols.DEFENSE),
    MAINHAND_TOUGHNESS(new NamespacedKey(VenturePlugin.getInstance(), "mainhand_toughness"), ChatColor.GRAY + "Toughness: +", ChatColor.GREEN, StatSymbols.TOUGHNESS),
    MAINHAND_FORTITUDE(new NamespacedKey(VenturePlugin.getInstance(), "mainhand_fortitude"), ChatColor.GRAY + "Fortitude: +", ChatColor.DARK_GREEN, StatSymbols.FORTITUDE),
    MAINHAND_SPEED(new NamespacedKey(VenturePlugin.getInstance(), "mainhand_speed"), ChatColor.GRAY + "Speed: +", ChatColor.WHITE, StatSymbols.SPEED),
    MAINHAND_MANA(new NamespacedKey(VenturePlugin.getInstance(), "mainhand_mana"), ChatColor.GRAY + "Mana: +", ChatColor.BLUE, StatSymbols.MANA),
    MAINHAND_MOB_LOOTING(new NamespacedKey(VenturePlugin.getInstance(), "mainhand_mob_looting"), ChatColor.GRAY + "Looting: +", ChatColor.GREEN, StatSymbols.MOB_FORTUNE),
    MAINHAND_MINING_FORTUNE(new NamespacedKey(VenturePlugin.getInstance(), "mainhand_mining_fortune"), ChatColor.GRAY + "Mining Fortune: +", ChatColor.GREEN, StatSymbols.MINING_FORTUNE),
    MAINHAND_FORAGING_FORTUNE(new NamespacedKey(VenturePlugin.getInstance(), "mainhand_foraging_fortune"), ChatColor.GRAY + "Foraging Fortune: +", ChatColor.GREEN, StatSymbols.FORAGING_FORTUNE),
    MAINHAND_FARMING_FORTUNE(new NamespacedKey(VenturePlugin.getInstance(), "mainhand_farming_fortune"), ChatColor.GRAY + "Farming Fortune: +", ChatColor.GREEN, StatSymbols.FARMING_FORTUNE),
    MAINHAND_FISHING_LUCK(new NamespacedKey(VenturePlugin.getInstance(), "mainhand_fishing_luck"), ChatColor.GRAY + "Fishing Luck: +", ChatColor.DARK_AQUA, StatSymbols.FISHING_LUCK),
    MAINHAND_LUCK(new NamespacedKey(VenturePlugin.getInstance(), "mainhand_luck"), ChatColor.GRAY + "Luck: +", ChatColor.GREEN, StatSymbols.LUCK)

    ;

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
