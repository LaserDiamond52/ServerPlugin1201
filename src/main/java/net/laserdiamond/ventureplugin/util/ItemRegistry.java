package net.laserdiamond.ventureplugin.util;

import com.google.common.collect.Multimap;
import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.enchants.Components.VentureEnchants;
import net.laserdiamond.ventureplugin.items.armor.components.SoulFireBlazeArmor;
import net.laserdiamond.ventureplugin.items.armor.trims.Components.TrimLore;
import net.laserdiamond.ventureplugin.items.util.ItemForger;
import net.laserdiamond.ventureplugin.items.util.VentureItemRarity;
import net.laserdiamond.ventureplugin.items.util.armor.ArmorCMD;
import net.laserdiamond.ventureplugin.items.util.armor.ArmorPieceTypes;
import net.laserdiamond.ventureplugin.items.util.armor.VentureArmorSet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemRegistry implements Listener {

    private static final VenturePlugin PLUGIN = VenturePlugin.getInstance();
    private static final HashMap<String, ItemForger> ITEM_COMMAND_NAME_MAP = PLUGIN.getItemRegistryMap();
    private static final List<VentureArmorSet> PLAYER_ARMOR_ITEM_MAP = PLUGIN.getPlayerArmorItemMap();

    public HashMap<String, List<String>> playerLore(Player player)
    {
        HashMap<String, List<String>> playerLoreMap = new HashMap<>();
        int maxStars = PLUGIN.getConfig().getInt("maxStars");
        for (VentureArmorSet ventureArmorSet : PLAYER_ARMOR_ITEM_MAP)
        {
            String armorName = ventureArmorSet.armorSetName();
            for (int i = 0; i < maxStars; i++)
            {
                for (ArmorPieceTypes armorPieceTypes : ArmorPieceTypes.values())
                {
                    String armorPieceTypeName = armorPieceTypes.getName();
                    String keyName = (armorName + "_" + armorPieceTypeName + "_" + i).toLowerCase();
                    playerLoreMap.put(keyName, ventureArmorSet.createPlayerLore(player, armorPieceTypes, i));
                }
            }
        }
        return playerLoreMap;
    }

    public static HashMap<Integer, List<String>> defaultPlayerLore(Player player, int stars)
    {
        HashMap<Integer, List<String>> defaultPlayerLore = new HashMap<>();

        for (VentureArmorSet ventureArmorSet : PLAYER_ARMOR_ITEM_MAP) // Armor pieces
        {
            defaultPlayerLore.put(ventureArmorSet.getArmorCMD().getHelmet(), ventureArmorSet.createPlayerLore(player, ArmorPieceTypes.HELMET, stars));
            defaultPlayerLore.put(ventureArmorSet.getArmorCMD().getChestplate(), ventureArmorSet.createPlayerLore(player, ArmorPieceTypes.CHESTPLATE, stars));
            defaultPlayerLore.put(ventureArmorSet.getArmorCMD().getLeggings(), ventureArmorSet.createPlayerLore(player, ArmorPieceTypes.LEGGINGS, stars));
            defaultPlayerLore.put(ventureArmorSet.getArmorCMD().getBoots(), ventureArmorSet.createPlayerLore(player, ArmorPieceTypes.BOOTS, stars));
        }

        // Weapons

        // Menu Items

        return defaultPlayerLore;
    }

    @EventHandler
    public void refreshItemOnJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();

        for (ItemStack itemStack : player.getInventory().getContents())
        {
            // TODO: Update item to have player-defined lore
            renewItemNew(itemStack, player);
        }
    }

    @EventHandler
    public void refreshOnClick(InventoryClickEvent event)
    {
        Inventory eventInv = event.getClickedInventory();

        HumanEntity humanEntity = event.getWhoClicked();

        if (humanEntity instanceof Player player)
        {
            if (eventInv != null)
            {
                if (eventInv.equals(player.getInventory()))
                {
                    // TODO: Update item to have player-defined lore
                    renewItemNew(event.getCursor(), player);


                } else
                {
                    // TODO: Update item
                    renewItemNew(event.getCursor());
                }
                for (ItemStack itemStack : player.getInventory().getContents())
                {
                    renewItemNew(itemStack, player);
                }
            }
        }
    }

    @EventHandler
    public void switchMainHand(PlayerItemHeldEvent event)
    {
        Player player = event.getPlayer();

        for (ItemStack itemStack : player.getInventory().getContents())
        {
            renewItemNew(itemStack, player);
        }
    }

    @EventHandler
    public void refreshOpenInventory(InventoryOpenEvent event)
    {
        HumanEntity humanEntity = event.getPlayer();
        Inventory inventory = event.getInventory();

        if (humanEntity instanceof Player player)
        {
            for (ItemStack itemStack : inventory.getContents())
            {
                // TODO: Update item
                //renewItem(itemStack);
                renewItemNew(itemStack);
            }
            for (ItemStack itemStack : player.getInventory().getContents())
            {
                // TODO: Update item to have player-defined lore
                renewItemNew(itemStack, player);
            }
        }
    }

    @EventHandler
    public void refreshCloseInventory(InventoryCloseEvent event)
    {
        HumanEntity humanEntity = event.getPlayer();
        Inventory inventory = event.getInventory();

        if (humanEntity instanceof Player player)
        {
            for (ItemStack itemStack : inventory.getContents())
            {
                // TODO: Update item
                //renewItem(itemStack);
                renewItemNew(itemStack);
            }

            for (ItemStack itemStack : player.getInventory().getContents())
            {
                // TODO: Update item to have player-defined lore
                renewItemNew(itemStack, player);
            }
        }
    }

    @EventHandler
    public void refreshItemPickUp(PlayerAttemptPickupItemEvent event)
    {
        Player player = event.getPlayer();
        ItemStack itemToPickUp = event.getItem().getItemStack();

        // TODO: Update item to have player-defined lore
        renewItemNew(itemToPickUp, player);

    }

    @EventHandler
    public void refreshManipulateArmorStand(PlayerArmorStandManipulateEvent event)
    {
        Player player = event.getPlayer();
        ItemStack armorStandItem = event.getArmorStandItem();
        ItemStack playerItem = event.getPlayerItem();

        // TODO: Update both items to have player-defined lore
        renewItemNew(armorStandItem, player);
        renewItemNew(playerItem, player);

    }

    public static void renewItemNew(ItemStack itemStack)
    {
        List<String> newLore = new ArrayList<>();

        if (itemStack != null && itemStack.getType().isItem())
        {
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta != null)
            {
                ItemForger itemForger = new ItemForger(itemStack);
                if (itemMeta instanceof ArmorMeta armorMeta)
                {
                    List<String> trimLore = TrimLore.createLore(armorMeta);
                    newLore.addAll(trimLore);
                }

                if (itemMeta.hasEnchants())
                {
                    newLore.addAll(VentureEnchants.enchantLore(itemForger.getEnchantments()));
                }

                if (itemMeta.hasCustomModelData())
                {

                    String keyValue = itemForger.getItemKey();
                    try
                    {
                        ItemForger itemForgerMapItem = ITEM_COMMAND_NAME_MAP.get(keyValue);
                        List<String> lore = itemForgerMapItem.getLore();
                        VentureItemRarity.Rarity rarity = itemForgerMapItem.getRarity();
                        HashMap<VentureItemStatKeys, Double> itemStatMap = itemForgerMapItem.getItemStats();
                        Multimap<Attribute, AttributeModifier> attributes = itemForgerMapItem.getAttributes();

                        if (lore != null)
                        {

                            if (ArmorCMD.isAnyArmorPiece(itemStack, ArmorCMD.SOUL_FIRE_BLAZE))
                            {
                                newLore.addAll(SoulFireBlazeArmor.itemSpecificLore(itemForger, lore));
                            } else {
                                newLore.addAll(lore);
                            }
                        }
                        if (rarity != null)
                        {
                            itemMeta.setDisplayName(itemForgerMapItem.getName());
                            itemMeta.getPersistentDataContainer().set(ItemPropertiesKeys.RARITY_KEY.getKey(), VentureItemRarity.STRING, rarity.getRarity());
                        }
                        if (itemStatMap != null)
                        {
                            for (VentureItemStatKeys ventureItemStatKeys : itemStatMap.keySet())
                            {
                                NamespacedKey itemStatKey = ventureItemStatKeys.getKey();
                                Double statKeyValue = itemStatMap.get(ventureItemStatKeys);
                                if (itemMeta.getPersistentDataContainer().get(itemStatKey, PersistentDataType.DOUBLE) != null)
                                {
                                    itemMeta.getPersistentDataContainer().set(itemStatKey, PersistentDataType.DOUBLE, statKeyValue);
                                }
                            }
                        }
                        if (attributes != null)
                        {
                            itemMeta.setAttributeModifiers(attributes);
                        }

                    } catch (NullPointerException exception)
                    {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Item found with no mapping! :(");
                        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Custom model data: " + itemForger.getCustomModelData());
                        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Item key value: " + keyValue);
                        exception.printStackTrace();
                    }

                }
                itemMeta.setLore(newLore);
                itemStack.setItemMeta(itemMeta);

            }
        }
    }

    // FIXME: Finish renewItem for players
    public static ItemStack renewItemNew(ItemStack itemStack, Player player)
    {
        List<String> newLore = new ArrayList<>();

        if (itemStack != null && itemStack.getType().isItem())
        {
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta != null)
            {
                ItemForger itemForger = new ItemForger(itemStack);
                if (itemMeta instanceof ArmorMeta armorMeta)
                {
                    List<String> trimLore = TrimLore.createLore(armorMeta);
                    newLore.addAll(trimLore);
                }

                if (itemMeta.hasEnchants())
                {
                    newLore.addAll(VentureEnchants.enchantLore(itemForger.getEnchantments()));
                }

                if (itemMeta.hasCustomModelData())
                {
                    int stars = itemForger.getStarsNew();
                    String keyValue = itemForger.getItemKey();
                    try
                    {
                        HashMap<Integer, List<String>> playerLoreMap = defaultPlayerLore(player, stars);

                        ItemForger itemForgerMapItem = ITEM_COMMAND_NAME_MAP.get(keyValue);
                        List<String> lore = itemForgerMapItem.getLore();
                        List<String> playerLore = playerLoreMap.get(itemMeta.getCustomModelData());
                        VentureItemRarity.Rarity rarity = itemForgerMapItem.getRarity();
                        HashMap<VentureItemStatKeys, Double> itemStatMap = itemForgerMapItem.getItemStats();
                        Multimap<Attribute, AttributeModifier> attributes = itemForgerMapItem.getAttributes();


                        if (lore != null) // TODO: Test
                        {
                            if (!playerLoreMap.containsKey(itemMeta.getCustomModelData()))
                            {
                                if (ArmorCMD.isAnyArmorPiece(itemStack, ArmorCMD.SOUL_FIRE_BLAZE))
                                {
                                    newLore.addAll(SoulFireBlazeArmor.itemSpecificLore(itemForger, lore));
                                } else {
                                    newLore.addAll(lore);
                                }
                            }
                        }
                        if (playerLore != null) // TODO: Test
                        {
                            if (playerLoreMap.containsKey(itemMeta.getCustomModelData()))
                            {
                                newLore.addAll(playerLore);
                            }
                        }

                        if (rarity != null)
                        {
                            itemMeta.setDisplayName(itemForgerMapItem.getName());
                            itemMeta.getPersistentDataContainer().set(ItemPropertiesKeys.RARITY_KEY.getKey(), VentureItemRarity.STRING, rarity.getRarity());
                        }
                        if (itemStatMap != null)
                        {
                            for (VentureItemStatKeys ventureItemStatKeys : itemStatMap.keySet())
                            {
                                NamespacedKey itemStatKey = ventureItemStatKeys.getKey();
                                Double statKeyValue = itemStatMap.get(ventureItemStatKeys);
                                if (itemMeta.getPersistentDataContainer().get(itemStatKey, PersistentDataType.DOUBLE) != null)
                                {
                                    itemMeta.getPersistentDataContainer().set(itemStatKey, PersistentDataType.DOUBLE, statKeyValue);
                                }
                            }
                        }
                        if (attributes != null)
                        {
                            itemMeta.setAttributeModifiers(attributes);
                        }

                    } catch (NullPointerException exception)
                    {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Item found with no mapping! :(");
                        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Custom model data: " + itemForger.getCustomModelData());
                        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Item key value: " + keyValue);
                        exception.printStackTrace();
                    }

                }
                itemMeta.setLore(newLore);
                itemStack.setItemMeta(itemMeta);

            }
        }
        return itemStack;
    }
}
