package net.laserdiamond.ventureplugin.commands.GiveItems;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.items.util.ItemForger;
import net.laserdiamond.ventureplugin.items.util.ItemMappings;
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
import java.util.List;

public class GiveItemsCommand implements CommandExecutor, TabExecutor {

    private VenturePlugin plugin;

    public GiveItemsCommand(VenturePlugin plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender.hasPermission("ventureplugin.giveitems")) {
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

    private void give(CommandSender sender, Player target, String input) {

        try {
            ItemForger itemForger = ItemMappings.ItemMaps.of(input);
            target.getInventory().addItem(itemForger.toItemStack());
            sender.sendMessage(ChatColor.GREEN + "Gave " + itemForger.getName() + ChatColor.GREEN + "to " + target.getName());
        } catch (IllegalArgumentException exception) {
            sender.sendMessage(ChatColor.RED + "Not an item: " + input);
        }
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        List<String> argsList = new ArrayList<>();

        if (sender.hasPermission("serverplugin1201.giveitems")) {
            if (args.length == 1) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    argsList.add(player.getName());
                }
            } else if (args.length == 2) {
                for (ItemMappings.ItemMaps itemMaps : ItemMappings.ItemMaps.values()) {
                    String itemMapsName = itemMaps.getName();
                    argsList.add(itemMapsName);
                }
            }
        } else {
            return new ArrayList<>();
        }
        return argsList;
    }
}
