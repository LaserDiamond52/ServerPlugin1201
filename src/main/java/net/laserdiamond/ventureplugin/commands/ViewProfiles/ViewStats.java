package net.laserdiamond.ventureplugin.commands.ViewProfiles;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.items.util.misc.MenuItems;
import net.laserdiamond.ventureplugin.stats.Components.StatsItemManager;
import net.laserdiamond.ventureplugin.util.Permissions;
import net.laserdiamond.ventureplugin.util.messages.Messages;
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
            if (player.hasPermission(Permissions.STATS.getPermissionString())) {

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
                player.sendMessage(Messages.notAllowedCommand());
            }
        } else {
            sender.sendMessage(Messages.notPlayerCommand());
        }

        return true;
    }
}
