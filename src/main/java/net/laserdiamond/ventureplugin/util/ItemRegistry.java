package net.laserdiamond.ventureplugin.util;

import com.google.common.collect.Multimap;
import io.papermc.paper.event.player.PlayerPickItemEvent;
import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.enchants.Components.EnchantsClass;
import net.laserdiamond.ventureplugin.items.armor.Blaze.Components.BlazeArmorManager;
import net.laserdiamond.ventureplugin.items.armor.Trims.Components.TrimLore;
import net.laserdiamond.ventureplugin.items.util.ItemForger;
import net.laserdiamond.ventureplugin.items.util.VentureItemRarity;
import net.laserdiamond.ventureplugin.items.util.armor.VentureArmorSet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ItemRegistry implements Listener {

    private static final VenturePlugin PLUGIN = VenturePlugin.getInstance();
    private static final HashMap<ItemRegistryKey, ItemForger> ITEM_REGISTRY_MAP = PLUGIN.getItemRegistryMap();
    private static final HashMap<ItemRegistryKey, ItemForger> PLAYER_ITEM_REGISTRY_MAP = PLUGIN.getPlayerItemRegistry();

    public ItemRegistry(VenturePlugin plugin)
    {
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
                    renewItem(event.getCursor());
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
                renewItem(itemStack);
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
                renewItem(itemStack);
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

    public static ItemStack renewItem(ItemStack itemStack)
    {
        List<String> newLore = new ArrayList<>();

        if (itemStack != null && itemStack.getType().isItem() && !itemStack.getType().isAir())
        {
            ItemForger itemForger = new ItemForger(itemStack);

            if (itemForger.isArmorItem())
            {
                List<String> trimLore = TrimLore.createLore(itemForger.getArmorMeta());
                newLore.addAll(trimLore);
            }

            newLore.addAll(EnchantsClass.enchantLore(itemForger.getEnchantments()));

            if (itemForger.hasCustomModelData())
            {

                ItemRegistryKey itemRegistryKey = new ItemRegistryKey(itemForger.getCustomModelData(), itemForger.getStars());

                try {
                    ItemForger itemForgerMapItem = ITEM_REGISTRY_MAP.get(itemRegistryKey);
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
                        itemForger.setRarity(rarity);
                    }
                    if (itemStatMap != null)
                    {
                        itemForger.setItemStats(itemStatMap);
                    }
                    if (attributes != null)
                    {
                        itemForger.setAttributeModifiers(attributes, true);
                    }

                } catch (NullPointerException exception)
                {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Item found with no mapping! :(");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Custom model data: " + itemForger.getCustomModelData());
                }
            }
            return itemForger.setLore(newLore).toItemStack();
        }
        return itemStack;
    }

    @Deprecated
    public static ItemStack renewPlayerItem(ItemStack itemStack, Player player)
    {
        List<String> newLore = new ArrayList<>();

        if (itemStack != null && itemStack.getType().isItem())
        {
            ItemForger itemForger = new ItemForger(itemStack);
            if (itemForger.isArmorItem())
            {
                List<String> trimLore = TrimLore.createLore(itemForger.getArmorMeta());
                newLore.addAll(trimLore);
            }

            newLore.addAll(EnchantsClass.enchantLore(itemForger.getEnchantments()));

            if (itemForger.hasCustomModelData())
            {
                //TODO: Need to continue working on player item lore, etc.
                ItemRegistryKey itemRegistryKey = new ItemRegistryKey(itemForger.getCustomModelData(), itemForger.getStars());

                try {
                    if (ITEM_REGISTRY_MAP.containsKey(itemRegistryKey) && !PLAYER_ITEM_REGISTRY_MAP.containsKey(itemRegistryKey))
                    {
                        ItemForger itemForgerMapItem = ITEM_REGISTRY_MAP.get(itemRegistryKey);
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
                            itemForger.setRarity(rarity);
                        }
                        if (itemStatMap != null)
                        {
                            itemForger.setItemStats(itemStatMap);
                        }
                        if (attributes != null)
                        {
                            itemForger.setAttributeModifiers(attributes, true);
                        }
                    }

                    else if (ITEM_REGISTRY_MAP.containsKey(itemRegistryKey) && PLAYER_ITEM_REGISTRY_MAP.containsKey(itemRegistryKey))
                    {
                        ItemForger itemForgerMapItem = PLAYER_ITEM_REGISTRY_MAP.get(itemRegistryKey);
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
                            itemForger.setRarity(rarity);
                        }
                        if (itemStatMap != null)
                        {
                            itemForger.setItemStats(itemStatMap);
                        }
                        if (attributes != null)
                        {
                            itemForger.setAttributeModifiers(attributes, true);
                        }
                    }


                } catch (NullPointerException exception)
                {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Item found with no mapping! :(");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Custom model data: " + itemForger.getCustomModelData());
                }
            }
            return itemForger.setLore(newLore).toItemStack();
        }
        return itemStack;
    }
}
