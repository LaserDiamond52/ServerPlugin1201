package net.laserdiamond.serverplugin1201.tunement.Components;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.tunement.Manager.TunementProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public record TunementProfile(TunementPoints tunementPoints, TunementStats tunementStats) {

    private static final ServerPlugin1201 plugin = ServerPlugin1201.getInstance();
    private static final TunementProfileManager tunementProfileManager = plugin.getTunementProfileManager();
    public static TunementProfile createTunementProfile(Player player)
    {
        TunementProfile tunementProfile = tunementProfileManager.getPlayerProfile(player.getUniqueId());
        if (tunementProfile == null) // Player does not have profile
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Player " + player.getName() + " does not have a tunement profile");
            tunementProfile = tunementProfileManager.createNewProfile(player);
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Creating new profile for " + player.getName());
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Player UUID: " + player.getUniqueId());
        } else // Player has profile
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Player " + player.getName() + " already has a tunement profile");
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Player UUID: " + player.getUniqueId());
        }
        return tunementProfile;
    }
}
