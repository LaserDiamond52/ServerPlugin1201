package net.laserdiamond.ventureplugin.commands.ViewProfiles;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.items.menuItems.misc.MiscMenuItems;
import net.laserdiamond.ventureplugin.items.menuItems.stats.StatMenuItems;
import net.laserdiamond.ventureplugin.util.Permissions;
import net.laserdiamond.ventureplugin.util.messages.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ViewStats implements CommandExecutor, Listener {

    public static final String STAT_INV_TITLE = "'s Stats";

    public static final String DEFENSE_STAT_INV_TITLE = "'s Defense Stats";

    public static final String DAMAGE_STAT_INV_TITLE = "'s Damage Stats";

    public ViewStats(VenturePlugin plugin)
    {
        plugin.getCommand("stats").setExecutor(this);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private Inventory statInventory(Player player)
    {
        Inventory statInventory = Bukkit.createInventory(null, 54, ChatColor.GOLD + player.getName() + STAT_INV_TITLE);

        for (StatMenuItems.StatItemSlots statItemSlots : StatMenuItems.StatItemSlots.values())
        {
            ItemStack statMenuItem = statItemSlots.getVentureMenuItem().createItem(player).toItemStack();
            int inventorySlot = statItemSlots.getInventorySlot();
            statInventory.setItem(inventorySlot, statMenuItem);
        }
        MiscMenuItems.placeExitButton(player, statInventory);

        MiscMenuItems.fillBlankSlotsPlayerInv(player, statInventory);
        return statInventory;
    }

    private Inventory defenseStatInventory(Player player)
    {
        Inventory defenseInventory = Bukkit.createInventory(null, 54, ChatColor.GREEN + player.getName() + DEFENSE_STAT_INV_TITLE);

        for (StatMenuItems.DefenseStatSlots defenseStatSlots : StatMenuItems.DefenseStatSlots.values())
        {
            ItemStack defenseStatItem = defenseStatSlots.getVentureMenuItem().createItem(player).toItemStack();
            int inventorySlot = defenseStatSlots.getInventorySlot();
            defenseInventory.setItem(inventorySlot, defenseStatItem);
        }
        MiscMenuItems.placeExitButton(player, defenseInventory);
        MiscMenuItems.placeGoBackButton(player, defenseInventory);

        MiscMenuItems.fillBlankSlotsPlayerInv(player, defenseInventory);
        return defenseInventory;
    }

    private Inventory damageStatInventory(Player player)
    {
        Inventory damageInventory = Bukkit.createInventory(null, 54, ChatColor.RED + player.getName() + DAMAGE_STAT_INV_TITLE);

        for (StatMenuItems.DamageStatSlots damageStatSlots : StatMenuItems.DamageStatSlots.values())
        {
            ItemStack damageStatItem = damageStatSlots.getVentureMenuItem().createItem(player).toItemStack();
            int inventorySlot = damageStatSlots.getInventorySlot();
            damageInventory.setItem(inventorySlot, damageStatItem);
        }
        MiscMenuItems.placeExitButton(player, damageInventory);
        MiscMenuItems.placeGoBackButton(player, damageInventory);

        MiscMenuItems.fillBlankSlotsPlayerInv(player, damageInventory);
        return damageInventory;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player player) {
            if (player.hasPermission(Permissions.STATS.getPermission()))
            {
                player.openInventory(statInventory(player));
            } else {
                player.sendMessage(Messages.notAllowedCommand());
            }
        } else {
            sender.sendMessage(Messages.notPlayerCommand());
        }

        return true;
    }

    @EventHandler
    public void clickInsideInv(InventoryClickEvent event)
    {
        HumanEntity humanEntity = event.getWhoClicked();
        if (humanEntity instanceof Player player)
        {
            if (event.getClickedInventory() != null)
            {
                Inventory clickedInv = event.getClickedInventory();
                String invTitle = event.getView().getTitle();
                int clickedSlot = event.getSlot();
                if (invTitle.contains(STAT_INV_TITLE)) // Player is clicking inside the home stat inventory
                {
                    event.setCancelled(true); // Prevent players from moving items in the inventory

                    int defenseSlot = StatMenuItems.StatItemSlots.DEFENSE.getInventorySlot();
                    int damageSlot = StatMenuItems.StatItemSlots.DAMAGE.getInventorySlot();

                    if (clickedSlot == defenseSlot)
                    {
                        player.openInventory(defenseStatInventory(player));
                    } else if (clickedSlot == damageSlot)
                    {
                        player.openInventory(damageStatInventory(player));
                    }
                } else if (invTitle.contains(DEFENSE_STAT_INV_TITLE)) // Player is clicking inside the defense stat inventory
                {
                    event.setCancelled(true); // Prevent players from moving items in the inventory

                    if (clickedSlot == MiscMenuItems.getGoBackButtonLocation(clickedInv))
                    {
                        player.openInventory(statInventory(player));
                    }
                } else if (invTitle.contains(DAMAGE_STAT_INV_TITLE)) // Player is clicking inside the damage stat inventory
                {
                    event.setCancelled(true); // Prevent players from moving items in the inventory

                    if (clickedSlot == MiscMenuItems.getGoBackButtonLocation(clickedInv))
                    {
                        player.openInventory(statInventory(player));
                    }
                }
            }
        }
    }
}
