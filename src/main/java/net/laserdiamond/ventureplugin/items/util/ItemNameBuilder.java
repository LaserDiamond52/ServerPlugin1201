package net.laserdiamond.ventureplugin.items.util;

import org.bukkit.ChatColor;

public class ItemNameBuilder {

    public static String addStars(int count) {

        StringBuilder stars = new StringBuilder(" ");

        for (int i = 0; i < count; i++) {
            stars.append(ChatColor.GOLD + "✪");
        }
        return stars.toString();
    }

    public static String name(String itemName, int starCount) {

        StringBuilder stars = new StringBuilder(itemName + " ");

        for (int i = 0; i < starCount; i++) {
            stars.append(ChatColor.GOLD + "✪");
        }
        return stars.toString();
    }

    public static String addStars(VentureItemRarity.Rarity itemRarity, String itemName, int starCount) {

        StringBuilder stringBuilder = new StringBuilder(itemRarity.getRarityColor() + itemName + " ");

        for (int i = 0; i < starCount; i++) {
            stringBuilder.append(ChatColor.GOLD + "✪");
        }
        return stringBuilder.toString();
    }
}
