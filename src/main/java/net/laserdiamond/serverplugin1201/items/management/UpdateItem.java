package net.laserdiamond.serverplugin1201.items.management;

import com.google.common.collect.Multimap;
import net.laserdiamond.serverplugin1201.Management.ItemStatKeys;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UpdateItem {

    // TODO: Item keys for custom stats don't update in item tags, but update for in-game stats
    public static void renewItem(ItemStack itemStack) {
        List<String> newLore = new ArrayList<>();

        if (itemStack != null) {
            if (itemStack.getType() != Material.AIR) {
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (itemMeta != null) {
                    for (Enchantment enchantment : itemMeta.getEnchants().keySet()) {
                        ItemMappings.enchantString(enchantment, itemMeta);
                    }

                    if (itemMeta.hasCustomModelData()) {

                        Integer stars = ItemForger.getItemStars(itemStack);
                        HashMap<Integer, ItemForger> itemProfiles = ItemMappings.itemForgerHashMap(stars);

                        try {
                            ItemForger itemForger = itemProfiles.get(itemMeta.getCustomModelData());
                            PluginItemRarity.Rarity itemRarity = itemForger.getRarity();
                            List<String> lore = itemForger.getLore();
                            Multimap<Attribute, AttributeModifier> attributes = itemForger.getAttributes();
                            HashMap<ItemStatKeys, Double> itemStatMap = itemForger.getItemStats();

                            if (itemRarity != null) {
                                ItemForger.setItemRarity(itemMeta, itemRarity, itemForger.getName());
                            }
                            if (itemStatMap != null) {
                                ItemForger.setItemStats(itemStack, itemStatMap);
                            }
                            if (lore != null) {
                                for (String l : lore) {
                                    newLore.add(l);
                                }
                            }
                            if (attributes != null) {
                                itemMeta.setAttributeModifiers(attributes);
                            }


                        } catch (NullPointerException exception) {
                            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Item found with no mapping! :(");
                            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Custom model data: " + itemMeta.getCustomModelData());

                        }
                    }

                    itemMeta.setLore(newLore);
                    itemStack.setItemMeta(itemMeta);
                } else {
                    itemStack.setLore(null);
                }
            }
        }
    }
    public static void renewItem(ItemStack itemStack, Player player) {
        List<String> newLore = new ArrayList<>();

        if (itemStack != null) {
            if (itemStack.getType() != Material.AIR) {
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (itemMeta != null) {

                    for (Enchantment enchantment : itemMeta.getEnchants().keySet()) {
                        newLore.add(ItemMappings.enchantString(enchantment, itemMeta));
                    }

                    if (itemMeta.hasCustomModelData()) {


                        Integer stars = ItemForger.getItemStars(itemStack);

                        HashMap<Integer, ItemForger> itemForgerProfiles = ItemMappings.itemForgerHashMap(stars);

                        try {
                            ItemForger itemForger = itemForgerProfiles.get(itemMeta.getCustomModelData());
                            PluginItemRarity.Rarity itemRarity = itemForger.getRarity();
                            List<String> lore = itemForger.getLore();
                            Multimap<Attribute, AttributeModifier> attributes = itemForger.getAttributes();
                            HashMap<ItemStatKeys, Double> itemStatMap = itemForger.getItemStats();

                            if (itemRarity != null) {
                                ItemForger.setItemRarity(itemMeta, itemRarity, itemForger.getName());

                            }
                            if (itemStatMap != null) {
                                ItemForger.setItemStats(itemStack, itemStatMap);
                            }

                            HashMap<Integer, List<String>> playerLoreMap = ItemMappings.itemLoreHashMapPlayer(stars, player);
                            List<String> playerLore = playerLoreMap.get(itemMeta.getCustomModelData());

                            if (lore != null) {
                                for (String l : lore) {
                                    if (itemForgerProfiles.containsKey(itemMeta.getCustomModelData()) && !playerLoreMap.containsKey(itemMeta.getCustomModelData())) {
                                        newLore.add(l);
                                    }

                                }
                            }
                            if (playerLore != null) {
                                for (String l : playerLore) {
                                    if (itemForgerProfiles.containsKey(itemMeta.getCustomModelData()) && playerLoreMap.containsKey(itemMeta.getCustomModelData())) {
                                        newLore.add(l);
                                    }
                                }
                            }
                            if (attributes != null) {
                                itemMeta.setAttributeModifiers(attributes);
                            }



                        } catch (NullPointerException exception) {
                            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Item found with no mapping! :(");
                            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Custom model data: " + itemMeta.getCustomModelData());

                        }

                    }

                    itemMeta.setLore(newLore);
                    itemStack.setItemMeta(itemMeta);
                } else {
                    itemStack.setLore(null);
                }
            }
        }
    }

}
