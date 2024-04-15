package net.laserdiamond.ventureplugin.items.util;

import net.laserdiamond.ventureplugin.items.armor.util.ArmorPieceTypes;
import org.bukkit.ChatColor;

import java.util.LinkedList;

public class ItemStringBuilder {

    /**
     * Capitalizes the first letter of input, and sets the rest of the letters to lower case
     * @param input The String input
     * @return The input String with the first letter capitalized, and the rest lower cased
     */
    public static String capitalizeFirstLetter(String input)
    {
        return input.substring(0,1).toUpperCase() + input.substring(1).toLowerCase();
    }

    /**
     * Adds stars to the item's name
     * @param starCount The amount of stars to be displayed on the item
     * @return A String variable of the item's name with the amount of stars
     */
    public static String name(int starCount) {

        StringBuilder stars = new StringBuilder(" ");

        for (int i = 0; i < starCount; i++) {
            stars.append(ChatColor.GOLD + "✪");
        }
        return stars.toString();
    }

    /**
     * Creates the name of the item with stars
     * @param itemName The name of the item
     * @param starCount The amount of stars displayed on the item
     * @return The name of the item with the amount of stars to be displayed on it
     */
    public static String name(String itemName, int starCount) {

        StringBuilder stars = new StringBuilder(itemName + " ");

        for (int i = 0; i < starCount; i++) {
            stars.append(ChatColor.GOLD + "✪");
        }
        return stars.toString();
    }

    /**
     * Creates the name of the item with stars and colors the name based on the VentureItemRarity of the item. Note that the stars will still remain gold
     * @param itemRarity The rarity of the item. This will purely determine the color of the name
     * @param itemName The name of the item
     * @param starCount The amount of stars to be displayed on the item
     * @return The name of the item in the rarity-specified color with the amount of stars to be displayed on it
     */
    public static String name(VentureItemRarity.Rarity itemRarity, String itemName, int starCount) {

        StringBuilder stringBuilder = new StringBuilder(itemRarity.getRarityColor() + itemName + " ");

        for (int i = 0; i < starCount; i++) {
            stringBuilder.append(ChatColor.GOLD + "✪");
        }
        return stringBuilder.toString();
    }

    /**
     * A String variable that should be added at the end of the item lore to display its rarity.
     * @param rarity The rarity of the item
     * @return A String variable in the rarity color depicting its rarity, followed by " Item".
     */
    public static String addItemStringRarity(VentureItemRarity.Rarity rarity)
    {
        return rarity.getDisplayName() + " Item";
    }

    /**
     * A String variable that should be added at the end of the armor item lore to display its rarity and the type of armor piece that it is
     * @param rarity The rarity of the item
     * @param armorPieceTypes A String variable in the rarity color depicting its rarity, followed by a String depicting what armor piece the item is
     * @return A String variable in the rarity color depicting its rarity and the type of armor piece the item is.
     */
    public static String addItemStringRarity(VentureItemRarity.Rarity rarity, ArmorPieceTypes armorPieceTypes)
    {
        return rarity.getDisplayName() + " " + capitalizeFirstLetter(armorPieceTypes.getName());
    }

    /**
     * Sets the last String in the lore of an item to the input String
     * @param linkedListInput The lore of the item
     * @param input The last entry of the lore
     * @return The lore of the item as a LinkedList with the input as the last entry
     */
    public static LinkedList<String> setLastLoreEntry(LinkedList<String> linkedListInput, String input)
    {
        linkedListInput.add(linkedListInput.size(), input);
        return linkedListInput;
    }
}
