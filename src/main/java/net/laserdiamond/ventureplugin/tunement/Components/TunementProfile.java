package net.laserdiamond.ventureplugin.tunement.Components;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.tunement.Manager.TunementProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public record TunementProfile(TunementPoints tunementPoints, TunementStats tunementStats) {

    private static final VenturePlugin plugin = VenturePlugin.getInstance();
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
