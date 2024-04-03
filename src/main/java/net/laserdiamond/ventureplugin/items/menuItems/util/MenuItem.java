package net.laserdiamond.ventureplugin.items.menuItems.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum MenuItem {

    BLANK (Material.BLACK_STAINED_GLASS_PANE, " ", 1),
    EXIT (Material.BARRIER, ChatColor.RED + "Exit", 2),
    GO_FORWARD(Material.ARROW, ChatColor.YELLOW + "" + ChatColor.ITALIC + "Next Page", 3),
    GO_BACK (Material.ARROW, ChatColor.YELLOW + "" + ChatColor.ITALIC + "Previous page", 4),
    HEALTH_STAT (Material.RED_DYE, ChatColor.RED + "Health", 5),
    DEFENSE_STAT (Material.IRON_CHESTPLATE, ChatColor.GREEN + "Defense", 6),
    DEFENSE_STAT_MORE (Material.IRON_CHESTPLATE, ChatColor.GREEN + "Defense Info", 7),
    FIRE_DEFENSE_STAT_MORE (Material.BLAZE_POWDER, ChatColor.GOLD + "Fire Defense Info", 87),
    EXPLOSION_DEFENSE_STAT_MORE (Material.TNT, ChatColor.DARK_RED + "Explosion Defense Info", 9),
    PROJECTILE_DEFENSE_STAT_MORE (Material.ARROW, ChatColor.DARK_PURPLE  + "Projectile Defense Info", 10),
    MAGIC_DEFENSE_STAT_MORE (Material.POTION, ChatColor.AQUA + "Magic Defense Info", 11),
    TOUGHNESS_STAT (Material.DIAMOND_CHESTPLATE, ChatColor.GREEN + "Toughness", 12),
    MAX_MANA_STAT (Material.HEART_OF_THE_SEA, ChatColor.BLUE + "Mana", 13),
    DAMAGE_STAT (Material.IRON_SWORD, ChatColor.RED + "Damage", 14),
    MELEE_DAMAGE_STAT (Material.IRON_SWORD, ChatColor.RED + "Melee Damage", 15),
    MAGIC_DAMAGE_STAT (Material.DIAMOND, ChatColor.AQUA + "Magic Damage", 16),
    RANGE_DAMAGE_STAT (Material.BOW, ChatColor.DARK_PURPLE + "Range Damage", 17),
    SPEED_STAT (Material.IRON_BOOTS, ChatColor.WHITE + "Speed", 18),
    FORTITUDE (Material.NETHERITE_CHESTPLATE, ChatColor.DARK_GREEN + "Fortitude", 19),
    TUNING_POINTS (Material.EXPERIENCE_BOTTLE, ChatColor.GREEN + "Tuning Points", 20),
    TUNING_HEALTH (Material.RED_DYE, ChatColor.RED + "Health", 21),
    TUNING_DEFENSE (Material.IRON_CHESTPLATE, ChatColor.GREEN + "Defense", 22),
    TUNING_SPEED (Material.IRON_BOOTS, ChatColor.WHITE + "Speed", 23),
    TUNING_MANA (Material.HEART_OF_THE_SEA, ChatColor.BLUE + "Mana", 24),
    TUNING_MELEE (Material.IRON_SWORD, ChatColor.RED + "Melee Damage", 25),
    TUNING_MAGIC (Material.DIAMOND, ChatColor.AQUA + "Magic Damage", 26),
    TUNING_RANGE (Material.BOW, ChatColor.DARK_PURPLE + "Range Damage", 27);


    private final Material material;
    private final String displayName;
    private final int customModelData;

    MenuItem(Material material, String displayName, int customModelData)
    {
        this.material = material;
        this.displayName = displayName;
        this.customModelData = customModelData;
    }

    public Material getMaterial() {
        return material;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getCustomModelData() {
        return customModelData;
    }
}
