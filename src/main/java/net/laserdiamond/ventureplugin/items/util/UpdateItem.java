package net.laserdiamond.ventureplugin.items.util;

import com.google.common.collect.Multimap;
import net.laserdiamond.ventureplugin.util.VentureItemStatKeys;
import net.laserdiamond.ventureplugin.items.armor.Trims.Components.TrimLore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UpdateItem {

    // TODO: Item keys for custom stats don't update in item tags, but update for in-game stats

    // Updates the lore (best to call on recipe results)
    public static List<String> renewLore(ItemStack itemStack) {

        List<String> newLore = new ArrayList<>();

        if (itemStack != null && !itemStack.getType().isAir()) {
            ItemMeta itemMeta = itemStack.getItemMeta();

            if (itemMeta != null) {

                // Trim Lore
                if (itemMeta instanceof ArmorMeta armorMeta)
                {
                    List<String> trimLore = TrimLore.createLore(armorMeta);
                    newLore.addAll(trimLore);
                }

                // Enchant Lore
                newLore.addAll(ItemForgerRegistry.enchantLore(itemMeta.getEnchants()));

                // Item Lore
                if (itemMeta.hasCustomModelData()) {

                    Integer stars = ItemForger.getItemStars(itemStack);
                    HashMap<Integer, ItemForger> itemProfiles = ItemForgerRegistry.itemForgerHashMap(stars);

                    try {

                        ItemForger itemForger = itemProfiles.get(itemMeta.getCustomModelData());
                        List<String> lore = itemForger.getLore();

                        if (lore != null) {
                            newLore.addAll(lore);
                        }

                    } catch (NullPointerException exception) {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Item found with no mapping! :(");
                        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Custom model data: " + itemMeta.getCustomModelData());

                    }
                }

                return newLore;

            } else {
                return new ArrayList<>();
            }
        }

        return new ArrayList<>();
    }
    public static List<String> renewLore(ItemStack itemStack, Player player) {
        List<String> newLore = new ArrayList<>();

        if (itemStack != null && !itemStack.getType().isAir()) {
            ItemMeta itemMeta = itemStack.getItemMeta();

            if (itemMeta != null) {

                // Trim Lore
                try {
                    ArmorMeta armorMeta = (ArmorMeta) itemMeta;
                    List<String> trimLore = TrimLore.createLore(armorMeta);
                    newLore.addAll(trimLore);

                } catch (ClassCastException ignored) {
                }

                // Enchant Lore
                for (Enchantment enchantment : itemMeta.getEnchants().keySet()) {
                    newLore.add(ItemForgerRegistry.enchantString(enchantment, itemMeta));

                }

                // Item Lore
                if (itemMeta.hasCustomModelData()) {

                    Integer stars = ItemForger.getItemStars(itemStack);
                    HashMap<Integer, ItemForger> itemProfiles = ItemForgerRegistry.itemForgerHashMap(stars);
                    HashMap<Integer, List<String>> playerLoreProfiles = ItemForgerRegistry.itemLoreHashMapPlayer(stars, player);

                    try {

                        ItemForger itemForger = itemProfiles.get(itemMeta.getCustomModelData());
                        List<String> lore = itemForger.getLore();
                        List<String> playerLore = playerLoreProfiles.get(itemMeta.getCustomModelData());

                        if (lore != null) {
                            if (itemProfiles.containsKey(itemMeta.getCustomModelData()) && !playerLoreProfiles.containsKey(itemMeta.getCustomModelData())) {
                                newLore.addAll(lore);
                            }
                        }

                        if (playerLore != null) {
                            if (itemProfiles.containsKey(itemMeta.getCustomModelData()) && playerLoreProfiles.containsKey(itemMeta.getCustomModelData())) {
                                newLore.addAll(playerLore);
                            }
                        }

                    } catch (NullPointerException exception) {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Item found with no mapping! :(");
                        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Custom model data: " + itemMeta.getCustomModelData());

                    }
                }

                return newLore;

            } else {
                return new ArrayList<>();
            }
        }

        return new ArrayList<>();
    }

    // Updates the entire item. Should be called when absolutely necessary
    public static void renewItem(ItemStack itemStack) {

        if (itemStack != null) {
            if (itemStack.getType() != Material.AIR) {
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (itemMeta != null) {
                    List<String> lore = renewLore(itemStack);

                    if (itemMeta.hasCustomModelData()) {

                        Integer stars = ItemForger.getItemStars(itemStack);
                        HashMap<Integer, ItemForger> itemProfiles = ItemForgerRegistry.itemForgerHashMap(stars);

                        try {
                            ItemForger itemForger = itemProfiles.get(itemMeta.getCustomModelData());
                            VentureItemRarity.Rarity itemRarity = itemForger.getRarity();
                            Multimap<Attribute, AttributeModifier> attributes = itemForger.getAttributes();
                            HashMap<VentureItemStatKeys, Double> itemStatMap = itemForger.getItemStats();

                            if (itemRarity != null) {
                                ItemForger.setItemRarity(itemMeta, itemRarity, itemForger.getName());
                            }
                            if (itemStatMap != null) {
                                ItemForger.setItemStats(itemStack, itemStatMap);
                            }
                            if (attributes != null) {
                                itemMeta.setAttributeModifiers(attributes);
                            }


                        } catch (NullPointerException exception) {
                            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Item found with no mapping! :(");
                            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Custom model data: " + itemMeta.getCustomModelData());

                        }
                    }

                    itemMeta.setLore(lore);
                    itemStack.setItemMeta(itemMeta);
                } else {
                    itemStack.setLore(new ArrayList<>());
                }
            }
        }
    }
    public static void renewItem(ItemStack itemStack, Player player) {

        if (itemStack != null) {
            if (itemStack.getType() != Material.AIR) {
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (itemMeta != null) {
                    List<String> lore = renewLore(itemStack, player);

                    if (itemMeta.hasCustomModelData()) {

                        Integer stars = ItemForger.getItemStars(itemStack);

                        HashMap<Integer, ItemForger> itemForgerProfiles = ItemForgerRegistry.itemForgerHashMap(stars);

                        try {
                            ItemForger itemForger = itemForgerProfiles.get(itemMeta.getCustomModelData());
                            VentureItemRarity.Rarity itemRarity = itemForger.getRarity();
                            Multimap<Attribute, AttributeModifier> attributes = itemForger.getAttributes();
                            HashMap<VentureItemStatKeys, Double> itemStatMap = itemForger.getItemStats();

                            if (itemRarity != null) {
                                ItemForger.setItemRarity(itemMeta, itemRarity, itemForger.getName());

                            }
                            if (itemStatMap != null) {
                                ItemForger.setItemStats(itemStack, itemStatMap);
                            }

                            if (attributes != null) {
                                itemMeta.setAttributeModifiers(attributes);
                            }

                        } catch (NullPointerException exception) {
                            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Item found with no mapping! :(");
                            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Custom model data: " + itemMeta.getCustomModelData());

                        }

                    }

                    itemMeta.setLore(lore);
                    itemStack.setItemMeta(itemMeta);
                } else {
                    itemStack.setLore(new ArrayList<>());
                }
            }
        }
    }

}
