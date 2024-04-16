package net.laserdiamond.ventureplugin.items.menuItems.util;

import net.laserdiamond.ventureplugin.util.StatSymbols;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum MenuItem {

    BLANK (Material.BLACK_STAINED_GLASS_PANE, " ", "blank"),
    EXIT (Material.BARRIER, ChatColor.RED + "Exit", "exit"),
    GO_FORWARD(Material.ARROW, ChatColor.YELLOW + "" + ChatColor.ITALIC + "Next Page", "next_page"),
    GO_BACK (Material.ARROW, ChatColor.YELLOW + "" + ChatColor.ITALIC + "Previous page", "previous_page"),
    HEALTH_STAT (Material.RED_DYE, ChatColor.RED + "Health " + StatSymbols.HEALTH.getSymbol(), "health"),
    DEFENSE_STAT (Material.IRON_CHESTPLATE, ChatColor.GREEN + "Defense " + StatSymbols.DEFENSE.getSymbol(), "defense_stat"),
    DEFENSE_STAT_MORE (Material.IRON_CHESTPLATE, ChatColor.GREEN + "Defense " + StatSymbols.DEFENSE.getSymbol(), "defense_info"),
    FIRE_DEFENSE_STAT_MORE (Material.BLAZE_POWDER, ChatColor.GOLD + "Fire Defense " + StatSymbols.DEFENSE.getSymbol(), "fire_defense_info"),
    EXPLOSION_DEFENSE_STAT_MORE (Material.TNT, ChatColor.DARK_RED + "Explosion Defense " + StatSymbols.DEFENSE.getSymbol(), "explosion_defense_info"),
    PROJECTILE_DEFENSE_STAT_MORE (Material.ARROW, ChatColor.DARK_PURPLE  + "Projectile Defense " + StatSymbols.DEFENSE.getSymbol(), "projectile_defense_info"),
    MAGIC_DEFENSE_STAT_MORE (Material.POTION, ChatColor.AQUA + "Magic Defense " + StatSymbols.DEFENSE.getSymbol(), "magic_defense_info"),
    TOUGHNESS_STAT (Material.DIAMOND_CHESTPLATE, ChatColor.GREEN + "Toughness " + StatSymbols.TOUGHNESS.getSymbol(), "toughness_stat"),
    MAX_MANA_STAT (Material.HEART_OF_THE_SEA, ChatColor.BLUE + "Mana " + StatSymbols.MANA.getSymbol(), "mana_stat"),
    DAMAGE_STAT (Material.IRON_SWORD, ChatColor.RED + "Damage " + StatSymbols.MELEE_DAMAGE.getSymbol(), "damage_stat"),
    MELEE_DAMAGE_STAT (Material.IRON_SWORD, ChatColor.RED + "Melee Damage " + StatSymbols.MELEE_DAMAGE.getSymbol(), "melee_damage_info"),
    MAGIC_DAMAGE_STAT (Material.DIAMOND, ChatColor.AQUA + "Magic Damage " + StatSymbols.MAGIC_DAMAGE.getSymbol(), "magic_damage_info"),
    RANGE_DAMAGE_STAT (Material.BOW, ChatColor.DARK_PURPLE + "Range Damage " + StatSymbols.RANGE_DAMAGE.getSymbol(), "range_damage_info"),
    SPEED_STAT (Material.IRON_BOOTS, ChatColor.WHITE + "Speed " + StatSymbols.SPEED.getSymbol(), "speed_stat"),
    FORTUNE_STAT (Material.EMERALD, ChatColor.GREEN + "Fortune And Luck " + StatSymbols.MINING_FORTUNE.getSymbol(), "fortune_and_luck_stat"),
    MOB_FORTUNE_MORE (Material.DIAMOND_SWORD, ChatColor.GREEN + "Looting " + StatSymbols.MOB_FORTUNE.getSymbol(), "mob_fortune_info"),
    MINING_FORTUNE_MORE (Material.DIAMOND_PICKAXE, ChatColor.GREEN + "Mining Fortune " + StatSymbols.MINING_FORTUNE.getSymbol(), "mining_fortune_info"),
    FORAGING_FORTUNE_MORE (Material.DIAMOND_AXE, ChatColor.GREEN + "Foraging Fortune " + StatSymbols.FORAGING_FORTUNE.getSymbol(), "foraging_fortune_info"),
    FARMING_FORTUNE_MORE (Material.DIAMOND_HOE, ChatColor.GREEN + "Farming Fortune " + StatSymbols.FARMING_FORTUNE.getSymbol(), "farming_fortune_info"),
    FISHING_LUCK_MORE (Material.FISHING_ROD, ChatColor.DARK_AQUA + "Fishing Luck " + StatSymbols.FISHING_LUCK.getSymbol(), "fishing_luck_info"),
    POTION_STAT (Material.POTION, ChatColor.DARK_AQUA + "Potion Buffs " + StatSymbols.BREWING.getSymbol(), "potion_buffs_stat"),
    LONGEVITY_POTION_MORE (Material.REDSTONE, ChatColor.DARK_RED + "Longevity " + StatSymbols.LONGEVITY.getSymbol(), "longevity_info"),
    CAFFEINATION_POTION_MORE (Material.SUGAR, ChatColor.LIGHT_PURPLE + "Caffeination " + StatSymbols.CAFFEINATION.getSymbol(), "caffeination_info"),
    FORTITUDE (Material.NETHERITE_CHESTPLATE, ChatColor.DARK_GREEN + "Fortitude " + StatSymbols.FORTITUDE.getSymbol(), "fortitude_stat"),
    TUNING_POINTS (Material.EXPERIENCE_BOTTLE, ChatColor.GREEN + "Tuning Points", "tuning_points"),
    TUNING_HEALTH (Material.RED_DYE, ChatColor.RED + "Health", "health_tuning"),
    TUNING_DEFENSE (Material.IRON_CHESTPLATE, ChatColor.GREEN + "Defense", "defense_tuning"),
    TUNING_SPEED (Material.IRON_BOOTS, ChatColor.WHITE + "Speed", "speed_tuning"),
    TUNING_MANA (Material.HEART_OF_THE_SEA, ChatColor.BLUE + "Mana", "mana_tuning"),
    TUNING_MELEE (Material.IRON_SWORD, ChatColor.RED + "Melee Damage", "melee_damage_tuning"),
    TUNING_MAGIC (Material.DIAMOND, ChatColor.AQUA + "Magic Damage", "magic_damage_tuning"),
    TUNING_RANGE (Material.BOW, ChatColor.DARK_PURPLE + "Range Damage", "range_damage_tuning"),
    SKILL_COMBAT (Material.STONE_SWORD, ChatColor.DARK_RED + "Combat " + StatSymbols.COMBAT.getSymbol(), "combat_skill"),
    SKILL_MINING (Material.IRON_PICKAXE, ChatColor.DARK_BLUE + "Mining " + StatSymbols.MINING.getSymbol(), "mining_skill"),
    SKILL_FORAGING (Material.WOODEN_AXE, ChatColor.DARK_GREEN + "Foraging " + StatSymbols.FORAGING.getSymbol(), "foraging_skill"),
    SKILL_FARMING (Material.GOLDEN_HOE, ChatColor.GREEN + "Farming " + StatSymbols.FARMING.getSymbol(), "farming_skill"),
    SKILL_ENCHANTING (Material.ENCHANTING_TABLE, ChatColor.LIGHT_PURPLE + "Enchanting " + StatSymbols.ENCHANTING.getSymbol(), "enchanting_skill"),
    SKILL_FISHING (Material.FISHING_ROD, ChatColor.AQUA + "Fishing " + StatSymbols.FISHING.getSymbol(), "fishing_skill"),
    SKILL_BREWING (Material.BREWING_STAND, ChatColor.BLUE + "Brewing " + StatSymbols.BREWING.getSymbol(), "brewing_skill");

    private final Material material;
    private final String displayName;
    private final String keyName;

    MenuItem(Material material, String displayName, String keyName)
    {
        this.material = material;
        this.displayName = displayName;
        this.keyName = keyName;
    }

    public Material getMaterial() {
        return material;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getKeyName()
    {
        return keyName;
    }
}
