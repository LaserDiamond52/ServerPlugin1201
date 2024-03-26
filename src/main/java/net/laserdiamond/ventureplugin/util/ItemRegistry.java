package net.laserdiamond.ventureplugin.util;

import com.google.common.collect.Multimap;
import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.enchants.Components.VentureEnchants;
import net.laserdiamond.ventureplugin.items.armor.Trims.Components.TrimLore;
import net.laserdiamond.ventureplugin.items.util.ItemForger;
import net.laserdiamond.ventureplugin.items.util.VentureItemRarity;
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
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemRegistry implements Listener {

    private final VenturePlugin plugin;

    private final HashMap<String, ItemForger> itemCommandNameMap;
    private final List<VentureArmorSet> playerItemMap;

    public ItemRegistry(VenturePlugin plugin)
    {
        this.plugin = plugin;
        itemCommandNameMap = plugin.getItemRegistryMap();
        playerItemMap = plugin.getPlayerItemMap();
    }

    public HashMap<String, List<String>> playerVentureArmorSetLore (String keyValue, Player player) {

        for (VentureArmorSet ventureArmorSet : playerItemMap)
        {
            
        }
        return null;
    }

    @EventHandler
    public void refreshItemOnJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();

        for (ItemStack itemStack : player.getInventory().getContents())
        {
            // TODO: Update item to have player-defined lore

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

                } else
                {
                    // TODO: Update item
                    //renewItem(event.getCursor());
                    renewItemNew(event.getCursor());
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
                // TODO: Update item
                //renewItem(itemStack);
                renewItemNew(itemStack);
            }
            for (ItemStack itemStack : player.getInventory().getContents())
            {
                // TODO: Update item to have player-defined lore

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

            }
        }
    }

    @EventHandler
    public void refreshItemPickUp(PlayerAttemptPickupItemEvent event)
    {
        Player player = event.getPlayer();
        ItemStack itemToPickUp = event.getItem().getItemStack();

        // TODO: Update item to have player-defined lore


    }

    @EventHandler
    public void refreshManipulateArmorStand(PlayerArmorStandManipulateEvent event)
    {
        Player player = event.getPlayer();
        ItemStack armorStandItem = event.getArmorStandItem();
        ItemStack playerItem = event.getPlayerItem();

        // TODO: Update both items to have player-defined lore

    }

    public void renewItemNew(ItemStack itemStack)
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
                        ItemForger itemForgerMapItem = itemCommandNameMap.get(keyValue);
                        List<String> lore = itemForgerMapItem.getLore();
                        VentureItemRarity.Rarity rarity = itemForgerMapItem.getRarity();
                        HashMap<VentureItemStatKeys, Double> itemStatMap = itemForgerMapItem.getItemStats();
                        Multimap<Attribute, AttributeModifier> attributes = itemForgerMapItem.getAttributes();

                        if (lore != null)
                        {
                            newLore.addAll(lore);
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
    public void renewItemNew(ItemStack itemStack, Player player)
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
                        if (itemCommandNameMap.containsKey(keyValue))
                        {
                            ItemForger itemForgerMapItem = itemCommandNameMap.get(keyValue);
                            List<String> lore = itemForgerMapItem.getLore();
                            VentureItemRarity.Rarity rarity = itemForgerMapItem.getRarity();
                            HashMap<VentureItemStatKeys, Double> itemStatMap = itemForgerMapItem.getItemStats();
                            Multimap<Attribute, AttributeModifier> attributes = itemForgerMapItem.getAttributes();

                            if (lore != null)
                            {
                                newLore.addAll(lore);
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
                        } else if (itemCommandNameMap.containsKey(keyValue))
                        {

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
}
