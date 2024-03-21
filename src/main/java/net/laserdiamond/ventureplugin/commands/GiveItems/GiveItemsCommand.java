package net.laserdiamond.ventureplugin.commands.GiveItems;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.items.util.ItemForger;
import net.laserdiamond.ventureplugin.items.util.ItemForgerRegistry;
import net.laserdiamond.ventureplugin.util.ItemRegistry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
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

        if (sender.hasPermission("venture_plugin.giveitems")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Please specify a target");
            } else if (args.length == 1) {
                sender.sendMessage(ChatColor.RED + "Please specify an item");
            } else if (args.length == 2) {
                Player target = Bukkit.getPlayer(args[0]);
                String inputItem = args[1];
                if (target != null) {
                    testGive(sender, target, inputItem);
                }
            }
        }
        return true;
    }

    private void give(CommandSender sender, Player target, String input) {

        try {
            ItemForger itemForger = ItemForgerRegistry.ItemMaps.of(input);
            target.getInventory().addItem(itemForger.toItemStack());
            sender.sendMessage(ChatColor.GREEN + "Gave " + itemForger.getName() + ChatColor.GREEN + "to " + target.getName());
        } catch (IllegalArgumentException exception) {
            sender.sendMessage(ChatColor.RED + "Not an item: " + input);
        }
    }

    /**
     * Gives item to the target
     * @param sender Who is executing the command
     * @param target The player target of the command
     * @param input The sender's input
     */
    private void testGive(CommandSender sender, Player target, String input)
    {
        //ItemRegistry itemRegistry = new ItemRegistry(plugin);
        HashMap<String, ItemForger> itemCommandNameMap = plugin.getItemCommandNameMap();
        if (itemCommandNameMap.containsKey(input))
        {
            ItemForger itemToGive = itemCommandNameMap.get(input);
            target.getInventory().addItem(itemToGive.toItemStack());
            sender.sendMessage(ChatColor.GREEN + "Gave " + itemToGive.getName() + ChatColor.GREEN + " to " + ChatColor.GOLD + target.getName());
            target.sendMessage(ChatColor.GREEN + "You received a " + itemToGive.getName() + ChatColor.GREEN + " from " + ChatColor.GOLD + sender.getName());
        } else {
            sender.sendMessage(ChatColor.RED + "Not an item: " + input);
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        List<String> argsList = new ArrayList<>();

        if (sender.hasPermission("venture_plugin.giveitems")) {
            if (args.length == 1) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    argsList.add(player.getName());
                }
            } else if (args.length == 2) {
                HashMap<String, ItemForger> itemCommandNameMap = plugin.getItemCommandNameMap();
                for (String string : itemCommandNameMap.keySet())
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
