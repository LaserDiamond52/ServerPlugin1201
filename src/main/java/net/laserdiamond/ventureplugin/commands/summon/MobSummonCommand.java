package net.laserdiamond.ventureplugin.commands.summon;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.entities.mobs.VentureMobType;
import net.laserdiamond.ventureplugin.entities.util.VentureMob;
import net.laserdiamond.ventureplugin.util.Permissions;
import net.laserdiamond.ventureplugin.util.messages.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MobSummonCommand implements CommandExecutor, TabExecutor {

    private final VenturePlugin plugin = VenturePlugin.getInstance();
    private final HashMap<String, VentureMob<?>> mobs = plugin.getVentureMobMap();
    private final VentureMobType[] spawnableMobs = VentureMobType.values();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!sender.hasPermission(Permissions.SUMMON_MOBS.getPermission()))
        {
            sender.sendMessage(Messages.notAllowedCommand());
            return true;
        }
        switch (args.length)
        {
            case 0:
                sender.sendMessage(ChatColor.RED + "Please specify a mob to spawn");
                break;
            case 1:
                // TODO: spawn mob at sender location (if a player)
                if (sender instanceof Player player)
                {
                    String arg = args[0];
                    spawnMob(arg, sender, player);
                } else
                {
                    sender.sendMessage(Messages.notPlayerCommand());
                }
                break;
            case 2:
                // TODO: spawn mob at target location
                String arg = args[0];
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null)
                {
                    spawnMob(arg, sender, target);
                }
                break;
        }
        return true;
    }

    private void spawnMob(String arg, CommandSender sender, Player target)
    {
        for (String name : mobs.keySet()) // Loop through
        {
            if (arg.equals(name)) // Check for equal value
            {
                VentureMob<?> ventureMob = mobs.get(name);
                ventureMob.summonMob(target.getLocation()); // Summon mob
                sender.sendMessage(ChatColor.GREEN + "Summoned " + ventureMob.ventureMobType().getDisplayName() + ChatColor.GREEN + " at " + ChatColor.GOLD + target.getName());
            }
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        List<String> argsList = new ArrayList<>();

        if (!sender.hasPermission(Permissions.SUMMON_MOBS.getPermission()))
        {
            return new ArrayList<>();
        }
        switch (args.length)
        {
            case 1:
                argsList.addAll(mobs.keySet());
                break;
            case 2:
                for (Player player : Bukkit.getOnlinePlayers())
                {
                    argsList.add(player.getName());
                }
                break;
        }

        return argsList;
    }
}
