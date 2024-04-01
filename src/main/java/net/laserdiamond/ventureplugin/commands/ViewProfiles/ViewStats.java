package net.laserdiamond.ventureplugin.commands.ViewProfiles;

import net.laserdiamond.ventureplugin.items.menuItems.misc.MiscMenuItems;
import net.laserdiamond.ventureplugin.items.menuItems.stats.StatMenuItems;
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


                for (StatMenuItems.StatItemSlots statItemSlots : StatMenuItems.StatItemSlots.values())
                {
                    ItemStack statMenuItem = statItemSlots.getVentureMenuItem().createItem(player).toItemStack();
                    int inventorySlot = statItemSlots.getInventorySlot();
                    statInventory.setItem(inventorySlot, statMenuItem);
                }

                MiscMenuItems.fillBlankSlotsPlayerInv(player, statInventory);

                player.openInventory(statInventory);
            } else {
                player.sendMessage(Messages.notAllowedCommand());
            }
        } else {
            sender.sendMessage(Messages.notPlayerCommand());
        }

        return true;
    }

    public static String statInvTitle(Player player)
    {
        return player.getName() + "'s Stats";
    }
}
