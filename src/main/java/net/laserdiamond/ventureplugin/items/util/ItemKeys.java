package net.laserdiamond.ventureplugin.items.util;

import net.laserdiamond.ventureplugin.VenturePlugin;
import org.bukkit.NamespacedKey;

/**
 * Holds keys that apply across all items in-game
 */
public final class ItemKeys {

    private static final VenturePlugin PLUGIN = VenturePlugin.getInstance();

    /**
     * Key used to store the rarity of the item
     */
    public static final NamespacedKey RARITY_KEY = new NamespacedKey(PLUGIN, "rarity");

    /**
     * Key used to determine if an item should have fire resistance, similar to netherite items
     */
    public static final NamespacedKey FIRE_RESISTANCE_KEY = new NamespacedKey(PLUGIN, "fire_resistant");

    /**
     * Key used to store the stars of the item
     */
    public static final NamespacedKey STARS_KEY = new NamespacedKey(PLUGIN, "stars");

    /**
     * Key purely used to determine if a potion has been claimed from the brewing stand
     */
    public static final NamespacedKey POTION_CLAIMED_KEY = new NamespacedKey(PLUGIN, "potion_claimed");

    /**
     * Key used to determine what enchantments can be applied to an item
     */
    public static final NamespacedKey ENCHANTMENT_CATEGORY_KEY = new NamespacedKey(PLUGIN, "enchantment_category");

    /**
     * Key for all items that aren't menu-related
     */
    public static final NamespacedKey ITEM_MAP_KEY = new NamespacedKey(PLUGIN, "item_key");

    /**
     * Key for menu-related items
     */
    public static final NamespacedKey MENU_ITEM_MAP_KEY = new NamespacedKey(PLUGIN, "menu_item_key");

    /**
     * Key used purely for skill type items that are the skill they represent
     */
    public static final NamespacedKey SKILL_PROGRESS_SKILL_KEY = new NamespacedKey(PLUGIN, "skill_progress_skill_key");

    /**
     * Key used purely for skill progress items that stores the level they represent
     */
    public static final NamespacedKey SKILL_PROGRESS_LEVEL_KEY = new NamespacedKey(PLUGIN, "skill_progress_level_key");

}
