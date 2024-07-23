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
import java.util.List;

public class StarItemCommand implements CommandExecutor, TabExecutor {

    private final VenturePlugin plugin = VenturePlugin.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender.hasPermission(Permissions.GIVE_ITEMS.getPermission()))
        {
            if (args.length == 0)
            {
                sender.sendMessage(ChatColor.RED + "Please specify a target");
            } else if (args.length == 1)
            {
                sender.sendMessage(ChatColor.RED + "Please specify the amount of stars to set to the item");
            } else if (args.length == 2)
            {
                Player target = Bukkit.getPlayer(args[0]);
                String input = args[1];
                if (target != null)
                {
                    starItem(sender, target, input);
                }
            }
        }

        return true;
    }

    private void starItem(CommandSender sender, Player target, String input)
    {
        ItemStack mainHand = target.getInventory().getItemInMainHand();
        if (mainHand.getItemMeta() != null)
        {
            try {
                int stars = Integer.parseInt(input);
                VentureItemBuilder mainHandForger = new VentureItemBuilder(mainHand);
                int oldStars = mainHandForger.getStars();
                mainHandForger.setStars(stars);
                ItemStack newItem = ItemRegistry.renewItem(mainHandForger.toItemStack(), target);
                target.getInventory().setItemInMainHand(newItem);

                sender.sendMessage(ChatColor.GREEN + "Successfully set stars on " + ChatColor.GOLD + target.getName() + ChatColor.GREEN + "'s main hand from " + ChatColor.RED + oldStars + ChatColor.GREEN + " to " + ChatColor.RED + Math.max(0, Math.min(stars, plugin.getConfig().getInt("maxStars"))));

                target.sendMessage(ChatColor.GREEN + "Your item's stars was set from " + ChatColor.RED + oldStars + ChatColor.GREEN + " to " + ChatColor.RED + Math.max(0, Math.min(stars, plugin.getConfig().getInt("maxStars"))) + ChatColor.GREEN + " by " + ChatColor.GOLD + sender.getName());

            } catch (NumberFormatException e)
            {
                sender.sendMessage(ChatColor.RED + "Please input an integer");
            }

        } else {
            sender.sendMessage(ChatColor.RED + "Cannot star item: " + mainHand.getType().getKey().getKey());
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        List<String> argsList = new ArrayList<>();
        if (sender.hasPermission(Permissions.GIVE_ITEMS.getPermission()))
        {
            if (args.length == 1)
            {
                for (Player player : Bukkit.getOnlinePlayers())
                {
                    argsList.add(player.getName());
                }
            } else if (args.length == 2)
            {
                for (int i = 0; i < plugin.getConfig().getInt("maxStars") + 1; i++)
                {
                    argsList.add(String.valueOf(i));
                }
            }
        }

        return argsList;
    }
}
