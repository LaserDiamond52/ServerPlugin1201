package net.laserdiamond.ventureplugin.util;

import io.papermc.paper.event.player.PlayerPickItemEvent;
import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.items.util.ItemForger;
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

import java.util.HashMap;

public class ItemRegistry implements Listener {

    private final VenturePlugin plugin;
    private final HashMap<String, ItemForger> itemCommandNameMap;
    private final HashMap<ItemRegistryKey, ItemForger> itemRegistryMap;
    private final HashMap<ItemRegistryKey, ItemForger> playerItemRegistry;

    public ItemRegistry(VenturePlugin plugin)
    {
        this.plugin = plugin;
        itemCommandNameMap = new HashMap<>();
        itemRegistryMap = new HashMap<>();
        playerItemRegistry = new HashMap<>();
    }

    @EventHandler
    public void refreshItemOnJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();

        for (ItemStack itemStack : player.getInventory().getContents())
        {
            // TODO: Update item
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

    public HashMap<String, ItemForger> getItemCommandNameMap() {
        return itemCommandNameMap;
    }

    public HashMap<ItemRegistryKey, ItemForger> getItemRegistryMap() {
        return itemRegistryMap;
    }

    public HashMap<ItemRegistryKey, ItemForger> getPlayerItemRegistry() {
        return playerItemRegistry;
    }


}
