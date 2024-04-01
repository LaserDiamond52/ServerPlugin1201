package net.laserdiamond.ventureplugin.items.menuItems.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum MenuItem {

    HEALTH_STAT (Material.RED_DYE, ChatColor.RED + "Health", 116),
    DEFENSE_STAT (Material.IRON_CHESTPLATE, ChatColor.GREEN + "Defense", 117),
    DEFENSE_STAT_MORE (Material.IRON_CHESTPLATE, ChatColor.GREEN + "Defense Info", 117),
    FIRE_DEFENSE_STAT_MORE (Material.BLAZE_POWDER, ChatColor.GOLD + "Fire Defense Info", 37),
    EXPLOSION_DEFENSE_STAT_MORE (Material.TNT, ChatColor.DARK_RED + "Explosion Defense Info", 38),
    PROJECTILE_DEFENSE_STAT_MORE (Material.ARROW, ChatColor.DARK_PURPLE  + "Projectile Defense Info", 39),
    MAGIC_DEFENSE_STAT_MORE (Material.POTION, ChatColor.AQUA + "Magic Defense Info", 40),
    TOUGHNESS_STAT (Material.DIAMOND_CHESTPLATE, ChatColor.GREEN + "Toughness", 118),
    MAX_MANA_STAT (Material.HEART_OF_THE_SEA, ChatColor.BLUE + "Mana", 119),
    DAMAGE_STAT (Material.IRON_SWORD, ChatColor.RED + "Damage", 125),
    MELEE_DAMAGE_STAT (Material.IRON_SWORD, ChatColor.RED + "Melee Damage", 120),
    MAGIC_DAMAGE_STAT (Material.DIAMOND, ChatColor.AQUA + "Magic Damage", 121),
    RANGE_DAMAGE_STAT (Material.BOW, ChatColor.DARK_PURPLE + "Range Damage", 122),
    SPEED_STAT (Material.IRON_BOOTS, ChatColor.WHITE + "Speed", 123),
    FORTITUDE (Material.NETHERITE_CHESTPLATE, ChatColor.DARK_GREEN + "Fortitude", 124),
    BLANK (Material.BLACK_STAINED_GLASS_PANE, " ", 99);

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
