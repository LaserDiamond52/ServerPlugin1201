package net.laserdiamond.ventureplugin.commands.items;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.items.util.VentureItemBuilder;
import net.laserdiamond.ventureplugin.items.util.ItemRegistry;
import net.laserdiamond.ventureplugin.util.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GiveItemsCommand implements CommandExecutor, TabExecutor {

    private final VenturePlugin plugin;

    public GiveItemsCommand(VenturePlugin plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender.hasPermission(Permissions.GIVE_ITEMS.getPermission())) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Please specify a target");
            } else if (args.length == 1) {
                sender.sendMessage(ChatColor.RED + "Please specify an item");
            } else if (args.length == 2) {
                Player target = Bukkit.getPlayer(args[0]);
                String inputItem = args[1];
                if (target != null) {
                    give(sender, target, inputItem);
                }
            }
        }
        return true;
    }

    /**
     * Gives item to the target
     * @param sender Who is executing the command
     * @param target The player target of the command
     * @param input The sender's input
     */
    private void give(CommandSender sender, Player target, String input)
    {
        HashMap<String, VentureItemBuilder> itemRegistryMap = plugin.getItemRegistryMap();
        if (itemRegistryMap.containsKey(input))
        {
            VentureItemBuilder itemToGive = itemRegistryMap.get(input);
            ItemStack itemStackToGive = ItemRegistry.renewItem(itemToGive.toItemStack(), target);
            target.getInventory().addItem(itemStackToGive);
            sender.sendMessage(ChatColor.GREEN + "Gave " + itemToGive.getName() + ChatColor.GREEN + " to " + ChatColor.GOLD + target.getName());
            target.sendMessage(ChatColor.GREEN + "You received a " + itemToGive.getName() + ChatColor.GREEN + " from " + ChatColor.GOLD + sender.getName());
        } else {
            sender.sendMessage(ChatColor.RED + "Not an item: " + input);
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        List<String> argsList = new ArrayList<>();

        if (sender.hasPermission(Permissions.GIVE_ITEMS.getPermission())) {
            if (args.length == 1) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    argsList.add(player.getName());
                }
            } else if (args.length == 2) {
                HashMap<String, VentureItemBuilder> itemRegistryMap = plugin.getItemRegistryMap();
                for (String string : itemRegistryMap.keySet())
                {
                    argsList.add(string);
                }
            }
        } else {
            return new ArrayList<>();
        }
        return argsList;
    }
}
