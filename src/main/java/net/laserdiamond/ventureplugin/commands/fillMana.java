package net.laserdiamond.ventureplugin.commands;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.util.Permissions;
import net.laserdiamond.ventureplugin.util.messages.Messages;
import net.laserdiamond.ventureplugin.stats.Components.Stats;
import net.laserdiamond.ventureplugin.stats.Manager.StatProfileManager;
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

public class fillMana implements CommandExecutor, TabExecutor {

    private final VenturePlugin plugin = VenturePlugin.getInstance();
    private final StatProfileManager statProfileManager = plugin.getStatProfileManager();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender.hasPermission(Permissions.EDIT_MANA.getPermission()))
        {
            if (args.length == 0)
            {
                if (sender instanceof Player player) {
                    Stats stats = statProfileManager.getStatProfile(player.getUniqueId()).stats();
                    stats.setAvailableMana(stats.getMaxMana());
                    player.sendMessage(ChatColor.BLUE + "Mana refilled!");
                } else
                {
                    sender.sendMessage(ChatColor.RED + "Target is not a player!");
                }
            } else if (args.length == 1)
            {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null)
                {
                    Stats stats = statProfileManager.getStatProfile(target.getUniqueId()).stats();
                    stats.setAvailableMana(stats.getMaxMana());
                    target.sendMessage(ChatColor.BLUE + "Mana refilled!");

                } else
                {
                    sender.sendMessage(ChatColor.RED + "Target is not a player!");
                }
            }
        } else
        {
            sender.sendMessage(Messages.notAllowedCommand());
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        List<String> playerNames = new ArrayList<>();

        if (sender.hasPermission(Permissions.EDIT_MANA.getPermission())) {

            if (args.length == 1)
            {
                for (Player player : Bukkit.getOnlinePlayers())
                {
                    playerNames.add(player.getName());
                }
                return playerNames;
            }
        }
        return new ArrayList<>();
    }
}
