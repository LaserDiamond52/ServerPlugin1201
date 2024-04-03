package net.laserdiamond.ventureplugin.tuning.Components;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.tuning.Manager.TuningProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public record TuningProfile(TuningPoints tuningPoints, TuningStats tuningStats) {

    private static final VenturePlugin plugin = VenturePlugin.getInstance();
    private static final TuningProfileManager TUNING_PROFILE_MANAGER = plugin.getTunementProfileManager();
    public static TuningProfile createTunementProfile(Player player)
    {
        TuningProfile tuningProfile = TUNING_PROFILE_MANAGER.getPlayerProfile(player.getUniqueId());
        if (tuningProfile == null) // Player does not have profile
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Player " + player.getName() + " does not have a tunement profile");
            tuningProfile = TUNING_PROFILE_MANAGER.createNewProfile(player);
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Creating new profile for " + player.getName());
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Player UUID: " + player.getUniqueId());
        } else // Player has profile
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Player " + player.getName() + " already has a tunement profile");
            Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Player UUID: " + player.getUniqueId());
        }
        return tuningProfile;
    }
}
