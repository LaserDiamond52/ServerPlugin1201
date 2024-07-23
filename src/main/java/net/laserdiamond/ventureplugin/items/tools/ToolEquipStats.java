package net.laserdiamond.ventureplugin.items.tools;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.enchants.Components.VentureEnchants;
import net.laserdiamond.ventureplugin.entities.player.StatPlayer;
import net.laserdiamond.ventureplugin.items.util.VentureItemBuilder;
import net.laserdiamond.ventureplugin.items.util.VentureItemEnchantabilities;
import net.laserdiamond.ventureplugin.items.util.VentureItemStatKeys;
import net.laserdiamond.ventureplugin.stats.Components.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class ToolEquipStats implements Listener {

    private final VenturePlugin plugin;
    private final HashMap<UUID, Boolean> shouldEditOnClose; // If boolean is false, player inventory is open

    // TODO: New Solution:
    // Inventory click event already handles player's own inventory
    //
    // Inventory close event is called when the player closes their own inventory
    // Inventory open event is not called when the player opens their own inventory
    //
    // For inventories that aren't the player's own, HashMap is used to set a boolean for the player. If when the player
    // opens the inventory the boolean becomes set to true, the stats will be edited once the player closes the inventory
    //
    // Create a new profile type for handling item stats

    // TODO: UPDATE: Edit handheld player stats when the inventory is CLOSED
    // Create a new profile type for handling item stats

    public ToolEquipStats(VenturePlugin plugin)
    {
        this.plugin = plugin;
        this.shouldEditOnClose = new HashMap<>();
    }

    /**
     * Determines if the player's hand held item stats should be modified when they close an inventory
     * @param player The player closing the inventory
     * @return False if the HashMap does not contain the player's UUID or if the HashMap value for the player's UUID returns false (if the player closes their own inventory). Returns true only if the HashMap value for the player's UUID returns true (if the player closes an inventory that is not their own).
     */
    private Boolean shouldEditStatsOnClose(Player player)
    {
        UUID playerUUID = player.getUniqueId();
        if (this.shouldEditOnClose.containsKey(playerUUID) && this.shouldEditOnClose.get(playerUUID) != null)
        {
            return this.shouldEditOnClose.get(playerUUID);
        }
        return false;
    }

    @EventHandler
    private void applyOnJoin(PlayerJoinEvent event)
    {
        final Player player = event.getPlayer();
        final StatPlayer statPlayer = new StatPlayer(player);
        LootStats playerLootStats = statPlayer.getLootStats();

        ItemStack mainHand = player.getInventory().getItemInMainHand();

        this.shouldEditOnClose.put(player.getUniqueId(), false);

        if (mainHand != null && mainHand.getItemMeta() != null)
        {
            double lootingStat = VentureEnchants.getMobLootingValue(mainHand);
            playerLootStats.setMobLooting(playerLootStats.getMobLooting() + lootingStat);
        }
    }

    @EventHandler
    private void newItemInHand(PlayerItemHeldEvent event)
    {
        final Player player = event.getPlayer();
        final StatPlayer statPlayer = new StatPlayer(player);
        final LootStats playerLootStats = statPlayer.getLootStats();
        final PlayerInventory playerInventory = player.getInventory();

        ItemStack oldItem = playerInventory.getItem(event.getPreviousSlot());
        ItemStack newItem = playerInventory.getItem(event.getNewSlot());

        if (event.isCancelled())
        {
            return; // If the event is cancelled, there are no stats to manipulate, so end method here
        }

        if (oldItem != null && oldItem.getItemMeta() != null)
        {
            // REMOVE stats here
            double lootingStat = VentureEnchants.getMobLootingValue(oldItem);
            playerLootStats.setMobLooting(playerLootStats.getMobLooting() - lootingStat);
        }

        if (newItem != null && newItem.getItemMeta() != null)
        {
            // ADD stats here
            double lootingStat = VentureEnchants.getMobLootingValue(newItem);
            playerLootStats.setMobLooting(playerLootStats.getMobLooting() + lootingStat);
        }
    }

    @EventHandler
    private void closeInventory(InventoryCloseEvent event)
    {
        final HumanEntity humanEntity = event.getPlayer();
        if (humanEntity instanceof final Player player)
        {
            final StatPlayer statPlayer = new StatPlayer(player);
            final MainhandStats mainhandStats = statPlayer.getHandheldStats();
            final Stats stats = statPlayer.getStats();
            final DefenseStats defenseStats = statPlayer.getDefenseStats();
            final PotionStats potionStats = statPlayer.getPotionStats();
            final LootStats playerLootStats = statPlayer.getLootStats();

            ItemStack mainHand = player.getInventory().getItemInMainHand();

            if (mainHand.getItemMeta() != null) // Set main hand stats if main hand item meta is not null
            {
                // Fortune and Luck values
                double itemLootingEnchant = VentureEnchants.getMobLootingValue(mainHand);
                double itemMiningFortuneEnchant = VentureEnchants.getFortuneValue(mainHand, VentureItemEnchantabilities.PICKAXE);
                double itemForagingFortuneEnchant = VentureEnchants.getFortuneValue(mainHand, VentureItemEnchantabilities.AXE);
                double itemFarmingFortuneEnchant = VentureEnchants.getFortuneValue(mainHand, VentureItemEnchantabilities.HOE);
                double itemFishingLuckEnchant = VentureEnchants.getFishingLuck(mainHand);

                VentureItemBuilder ventureItemBuilder = new VentureItemBuilder(mainHand);
                double itemHealth = ventureItemBuilder.getItemStat(VentureItemStatKeys.MAINHAND_HEALTH);
                double itemDefense = ventureItemBuilder.getItemStat(VentureItemStatKeys.MAINHAND_DEFENSE);
                double itemFireDefense = ventureItemBuilder.getItemStat(VentureItemStatKeys.MAINHAND_FIRE_DEFENSE);
                double itemExplosionDefense = ventureItemBuilder.getItemStat(VentureItemStatKeys.MAINHAND_EXPLOSION_DEFENSE);
                double itemProjectileDefense = ventureItemBuilder.getItemStat(VentureItemStatKeys.MAINHAND_PROJECTILE_DEFENSE);
                double itemMagicDefense = ventureItemBuilder.getItemStat(VentureItemStatKeys.MAINHAND_MAGIC_DEFENSE);
                double itemToughness = ventureItemBuilder.getItemStat(VentureItemStatKeys.MAINHAND_TOUGHNESS);
                double itemFortitude = ventureItemBuilder.getItemStat(VentureItemStatKeys.MAINHAND_FORTITUDE);
                double itemSpeed = ventureItemBuilder.getItemStat(VentureItemStatKeys.MAINHAND_SPEED);
                double itemMana = ventureItemBuilder.getItemStat(VentureItemStatKeys.MAINHAND_MANA);
                double itemMobLooting = ventureItemBuilder.getItemStat(VentureItemStatKeys.MAINHAND_MOB_LOOTING);
                double itemMiningFortune = ventureItemBuilder.getItemStat(VentureItemStatKeys.MAINHAND_MINING_FORTUNE);
                double itemForagingFortune = ventureItemBuilder.getItemStat(VentureItemStatKeys.MAINHAND_FORAGING_FORTUNE);
                double itemFarmingFortune = ventureItemBuilder.getItemStat(VentureItemStatKeys.MAINHAND_FARMING_FORTUNE);
                double itemFishingLuck = ventureItemBuilder.getItemStat(VentureItemStatKeys.MAINHAND_FISHING_LUCK);
                double itemLuck = ventureItemBuilder.getItemStat(VentureItemStatKeys.MAINHAND_LUCK);

                //playerLootStats.setMobLooting(itemLootingStat);
                // TODO: Set player stats to values INCLUDING new main hand (old main hand is not included here)

                setItemStats(statPlayer, itemHealth, itemDefense, itemFireDefense, itemExplosionDefense, itemProjectileDefense, itemMagicDefense, itemToughness, itemFortitude, itemSpeed, itemMana, itemMobLooting + itemLootingEnchant, itemMiningFortune + itemMiningFortuneEnchant, itemForagingFortune + itemForagingFortuneEnchant, itemFarmingFortune + itemFarmingFortuneEnchant, itemFishingLuck + itemFishingLuckEnchant, itemLuck);
            } else // Otherwise, reset (Nothing in mainhand)
            {
                //playerLootStats.setMobLooting(0);
                // TODO: Set player stats to values WITHOUT main hand

                setItemStats(statPlayer, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
            }
        }
    }

    // TODO: Update stats when dropping/picking up an item

    // TIP: Hypixel Skyblock calculates the stat value to add/remove after the inventory is closed as opposed to immediately altering
    // May want to consider this approach when dropping items from the inventory?


    @EventHandler
    private void removeStatsDropItem(PlayerDropItemEvent event)
    {
        // TODO: remove stats
        final Player player = event.getPlayer();
        final StatPlayer statPlayer = new StatPlayer(player);
        final LootStats playerLootStats = statPlayer.getLootStats();

        if (event.isCancelled())
        {
            return;
        }

        ItemStack droppedItem = event.getItemDrop().getItemStack();
        if (droppedItem.getItemMeta() != null)
        {
            // REMOVE stats here
            double lootingStat = VentureEnchants.getMobLootingValue(droppedItem);
            playerLootStats.setMobLooting(playerLootStats.getMobLooting() - lootingStat);
        }

    }

    @EventHandler
    private void addStatsPickupItem(EntityPickupItemEvent event)
    {
        // TODO: add stats
        if (event.getEntity() instanceof final Player player)
        {
            final StatPlayer statPlayer = new StatPlayer(player);
            final LootStats playerLootStats = statPlayer.getLootStats();

            if (event.isCancelled())
            {
                return;
            }

            ItemStack pickedUpItem = event.getItem().getItemStack();
            if (pickedUpItem.getItemMeta() != null)
            {
                new BukkitRunnable() // Runs 1 tick after event happens
                {

                    @Override
                    public void run()
                    {
                        if (player.getInventory().getItemInMainHand().equals(pickedUpItem))
                        {
                            // ADD stats here
                            double lootingStat = VentureEnchants.getMobLootingValue(pickedUpItem);
                            playerLootStats.setMobLooting(playerLootStats.getMobLooting() + lootingStat);
                        }
                    }
                }.runTaskLaterAsynchronously(plugin, 1L);
            }
        }
    }

    private void setItemStats(final StatPlayer statPlayer, double health, double defense, double fireDefense, double explosionDefense, double projectileDefense, double magicDefense, double toughness, double fortitude, double speed, double mana, double mobLooting, double miningFortune, double foragingFortune, double farmingFortune, double fishingLuck, double luck)
    {
        final MainhandStats mainhandStats = statPlayer.getHandheldStats();

        mainhandStats.setHealth(health);
        mainhandStats.setDefense(defense);
        mainhandStats.setFireDefense(fireDefense);
        mainhandStats.setExplosionDefense(explosionDefense);
        mainhandStats.setProjectileDefense(projectileDefense);
        mainhandStats.setMagicDefense(magicDefense);
        mainhandStats.setToughness(toughness);
        mainhandStats.setFortitude(fortitude);
        mainhandStats.setSpeed(speed);
        mainhandStats.setMana(mana);
        mainhandStats.setMobLooting(mobLooting);
        mainhandStats.setMiningFortune(miningFortune);
        mainhandStats.setForagingFortune(foragingFortune);
        mainhandStats.setFarmingFortune(farmingFortune);
        mainhandStats.setFishingLuck(fishingLuck);
        mainhandStats.setLuck(luck);

    }
}
