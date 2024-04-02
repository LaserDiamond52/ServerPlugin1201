package net.laserdiamond.ventureplugin.items.util;

import com.google.common.collect.Multimap;
import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.commands.ViewProfiles.ViewStats;
import net.laserdiamond.ventureplugin.enchants.Components.VentureEnchants;
import net.laserdiamond.ventureplugin.items.armor.armor_sets.SoulFireBlazeArmor;
import net.laserdiamond.ventureplugin.items.armor.trims.Components.TrimLore;
import net.laserdiamond.ventureplugin.items.menuItems.stats.StatMenuItems;
import net.laserdiamond.ventureplugin.items.armor.util.ArmorCMD;
import net.laserdiamond.ventureplugin.items.armor.util.ArmorPieceTypes;
import net.laserdiamond.ventureplugin.items.armor.util.VentureArmorSet;
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

    public static HashMap<Integer, List<String>> playerMenuItemMap(Player player)
    {
        HashMap<Integer, List<String>> map = new HashMap<>();

        map.put(StatMenuItems.HEALTH_STAT_ITEM.menuItem().getCustomModelData(), StatMenuItems.HEALTH_STAT_ITEM.createLore(player));
        map.put(StatMenuItems.DEFENSE_STAT_ITEM.menuItem().getCustomModelData(), StatMenuItems.DEFENSE_STAT_ITEM.createLore(player));
        map.put(StatMenuItems.TOUGHNESS_STAT_ITEM.menuItem().getCustomModelData(), StatMenuItems.TOUGHNESS_STAT_ITEM.createLore(player));
        map.put(StatMenuItems.MANA_STAT_ITEM.menuItem().getCustomModelData(), StatMenuItems.MANA_STAT_ITEM.createLore(player));
        map.put(StatMenuItems.DAMAGE_STAT_ITEM.menuItem().getCustomModelData(), StatMenuItems.DAMAGE_STAT_ITEM.createLore(player));
        map.put(StatMenuItems.SPEED_STAT_ITEM.menuItem().getCustomModelData(), StatMenuItems.SPEED_STAT_ITEM.createLore(player));
        map.put(StatMenuItems.FORTITUDE_STAT_ITEM.menuItem().getCustomModelData(), StatMenuItems.FORTITUDE_STAT_ITEM.createLore(player));
        map.put(StatMenuItems.DEFENSE_STAT_ITEM_MORE.menuItem().getCustomModelData(), StatMenuItems.DEFENSE_STAT_ITEM_MORE.createLore(player));
        map.put(StatMenuItems.FIRE_DEFENSE_STAT_ITEM_MORE.menuItem().getCustomModelData(), StatMenuItems.FIRE_DEFENSE_STAT_ITEM_MORE.createLore(player));
        map.put(StatMenuItems.EXPLOSION_DEFENSE_STAT_ITEM_MORE.menuItem().getCustomModelData(), StatMenuItems.EXPLOSION_DEFENSE_STAT_ITEM_MORE.createLore(player));
        map.put(StatMenuItems.PROJECTILE_DEFENSE_STAT_ITEM_MORE.menuItem().getCustomModelData(), StatMenuItems.PROJECTILE_DEFENSE_STAT_ITEM_MORE.createLore(player));
        map.put(StatMenuItems.MAGIC_DEFENSE_STAT_ITEM_MORE.menuItem().getCustomModelData(), StatMenuItems.MAGIC_DEFENSE_STAT_ITEM_MORE.createLore(player));
        map.put(StatMenuItems.MELEE_DAMAGE_STAT_ITEM.menuItem().getCustomModelData(), StatMenuItems.MELEE_DAMAGE_STAT_ITEM.createLore(player));
        map.put(StatMenuItems.MAGIC_DAMAGE_STAT_ITEM.menuItem().getCustomModelData(), StatMenuItems.MAGIC_DAMAGE_STAT_ITEM.createLore(player));
        map.put(StatMenuItems.RANGE_DAMAGE_STAT_ITEM.menuItem().getCustomModelData(), StatMenuItems.RANGE_DAMAGE_STAT_ITEM.createLore(player));

        return map;
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
    public void refreshSwitchMainHand(PlayerItemHeldEvent event)
    {
        Player player = event.getPlayer();

        for (ItemStack itemStack : player.getInventory().getContents())
        {
            renewItemNew(itemStack, player);
        }
    }

    private static final List<String> PLAYER_INV_TITLES = new ArrayList<>();
    static
    {
        PLAYER_INV_TITLES.add(ViewStats.STAT_INV_TITLE);
        PLAYER_INV_TITLES.add(ViewStats.DEFENSE_STAT_INV_TITLE);
        PLAYER_INV_TITLES.add(ViewStats.DAMAGE_STAT_INV_TITLE);
    }

    private static boolean isAnyPlayerInvTitle(String invTitle)
    {
        for (String playerInvTitle : PLAYER_INV_TITLES)
        {
            if (invTitle.contains(playerInvTitle))
            {
                return true;
            }
        }
        return false;
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
                    renewItemNew(event.getCursor(), player);

                } else
                {
                    if (isAnyPlayerInvTitle(event.getView().getTitle()))
                    {
                        renewItemNew(event.getCursor(), player);
                    } else {
                        renewItemNew(event.getCursor());
                    }
                }
                for (ItemStack itemStack : player.getInventory().getContents())
                {
                    if (event.getView().getTitle().contains(ViewStats.STAT_INV_TITLE))
                    {
                        renewItemNew(itemStack, player);
                    } else {
                        renewItemNew(itemStack, player);
                    }

                }
            }
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
                String title = event.getView().getTitle();
                if (isAnyPlayerInvTitle(title))
                {
                    renewItemNew(itemStack, player);
                    //player.sendMessage("player gui");
                } else {
                    renewItemNew(itemStack);
                    //player.sendMessage("not player gui");
                }
            }
            for (ItemStack itemStack : player.getInventory().getContents())
            {
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
                if (isAnyPlayerInvTitle(event.getView().getTitle()))
                {
                    renewItemNew(itemStack, player);
                } else {
                    renewItemNew(itemStack);
                }

            }

            for (ItemStack itemStack : player.getInventory().getContents())
            {
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

    public static ItemStack renewItemNew(ItemStack itemStack)
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
                        if (itemForgerMapItem != null)
                        {
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
                        if (itemForgerMapItem != null)
                        {
                            List<String> lore = itemForgerMapItem.getLore();
                            List<String> playerLore = playerLoreMap.get(itemMeta.getCustomModelData());
                            VentureItemRarity.Rarity rarity = itemForgerMapItem.getRarity();
                            HashMap<VentureItemStatKeys, Double> itemStatMap = itemForgerMapItem.getItemStats();
                            Multimap<Attribute, AttributeModifier> attributes = itemForgerMapItem.getAttributes();


                            if (lore != null)
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
                            if (playerLore != null)
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
                        }
                        HashMap<Integer, List<String>> menuItemLoreMap = playerMenuItemMap(player);
                        if (menuItemLoreMap.containsKey(itemMeta.getCustomModelData()))
                        {
                            List<String> lore = menuItemLoreMap.get(itemMeta.getCustomModelData());
                            itemMeta.setLore(lore);
                            itemStack.setItemMeta(itemMeta);
                            return itemStack;
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
