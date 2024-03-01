package net.laserdiamond.serverplugin1201.commands.ViewProfiles;

import net.laserdiamond.serverplugin1201.items.management.misc.MenuItems;
import net.laserdiamond.serverplugin1201.stats.Components.StatsItemManager;
import net.laserdiamond.serverplugin1201.management.messages.messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ViewStats implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player player) {
            if (player.hasPermission("serverplugin1201.stats")) {

                Inventory statInventory = Bukkit.createInventory(null, 54, ChatColor.GOLD + player.getName() + "'s Stats");

                for (StatsItemManager.StatsItem statsItem : StatsItemManager.StatsItem.values()) {
                    ItemStack statMenuItem = StatsItemManager.createStatItem(player, statsItem).toItemStack();
                    int inventorySlot = statsItem.getInventorySlot();
                    statInventory.setItem(inventorySlot, statMenuItem);
                }

                ItemStack[] contents = statInventory.getContents();
                ItemStack itemStack;
                int i = 0;
                while (i < contents.length) {
                    itemStack = contents[i];
                    if (itemStack == null) {
                        statInventory.setItem(i, MenuItems.createMenuItem(MenuItems.MenuItemEnum.BLANK_ITEM).toItemStack());
                    }
                    i++;
                }

                player.openInventory(statInventory);
            } else {
                player.sendMessage(messages.notAllowedCommand());
            }
        } else {
            sender.sendMessage(messages.notPlayerCommand());
        }

        return true;
    }
}
