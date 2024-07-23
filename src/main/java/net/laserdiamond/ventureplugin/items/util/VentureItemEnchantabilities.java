package net.laserdiamond.ventureplugin.items.util;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

/**
 * Enum that contains all the enchantability types for equipment in this plugin
 */
public enum VentureItemEnchantabilities {

    SWORD,
    PICKAXE,
    AXE,
    SHOVEL,
    HOE,
    HELMET,
    CHESTPLATE,
    LEGGINGS,
    BOOTS,
    TRIDENT,
    BOW,
    CROSSBOW,
    FISHING_ROD,
    SHIELD;

    /**
     * Determines if the item stack is of an armor enchantability type. This includes the helmet, chestplate, leggings, and boots enchantability type
     * @param itemStack The item stack to check
     * @return True if of an armor enchantability type, false otherwise
     */
    public static boolean isArmorEnchantability(ItemStack itemStack)
    {
        VentureItemBuilder ventureItemBuilder = new VentureItemBuilder(itemStack);
        return switch (ventureItemBuilder.getEnchantmentType()) {
            case HELMET, CHESTPLATE, LEGGINGS, BOOTS -> true;
            default -> false;
        };
    }

    /**
     * Determines if the item stack is of a melee weapon enchantability type. This includes only the sword and axe enchantability types
     * @param itemStack The item stack to check
     * @return True if the enchantabilty type of the item is the sword or axe type, false otherwise
     */
    public static boolean isMeleeWeaponEnchantability(ItemStack itemStack)
    {
        VentureItemBuilder ventureItemBuilder = new VentureItemBuilder(itemStack);
        return switch (ventureItemBuilder.getEnchantmentType())
        {
            case SWORD, AXE -> true;
            default -> false;
        };
    }

    /**
     * Determines if the item stack is of a range weapon enchantability type. This includes only the bow and crossbow enchantability types
     * @param itemStack The item stack to check
     * @return True if the enchantability type of the item is the bow or crossbow type, false otherwise
     */
    public static boolean isRangeWeaponEnchantability(ItemStack itemStack)
    {
        VentureItemBuilder ventureItemBuilder = new VentureItemBuilder(itemStack);
        return switch (ventureItemBuilder.getEnchantmentType())
        {
            case BOW, CROSSBOW -> true;
            default -> false;
        };
    }

    /**
     * Determines if the item stack is of a tool enchantability type. This includes the pickaxe, axe, shovel, and hoe enchantability types
     * @param itemStack the item stack to check
     * @return True if the enchantability type of the item is the pickaxe, axe, shovel, or hoe type, false otherwise
     */
    public static boolean isToolEnchantability(ItemStack itemStack)
    {
        VentureItemBuilder ventureItemBuilder = new VentureItemBuilder(itemStack);
        return switch (ventureItemBuilder.getEnchantmentType())
        {
            case PICKAXE, AXE, SHOVEL, HOE -> true;
            default -> false;
        };
    }

    /**
     * Determines if the item stack is of any enchantability type
     * @param itemStack the item stack to check
     * @return True if the item stack can be enchanted, false otherwise
     */
    public static boolean isAnyEnchantability(ItemStack itemStack)
    {
        VentureItemBuilder ventureItemBuilder = new VentureItemBuilder(itemStack);
        if (ventureItemBuilder.getEnchantmentType() != null)
        {
            return true;
        }
        return false;
    }

    /**
     * Gets the venture enchantability type from a string
     * @param value The string passed through
     * @return If the value of the string matches the name of any enchantability type, that enchantability type will be returned. If no match is found, an IllegalArgumentException is thrown
     */
    public static VentureItemEnchantabilities fromString(String value)
    {
        for (VentureItemEnchantabilities enchantabilityType : values())
        {
            if (value.equals(enchantabilityType.name()))
            {
                return enchantabilityType;
            }
        }
        throw new IllegalArgumentException(ChatColor.RED + "NOT A VENTURE ENCHANTMENT TYPE: " + value);
    }
}
