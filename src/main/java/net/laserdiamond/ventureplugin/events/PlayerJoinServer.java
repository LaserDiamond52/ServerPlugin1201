package net.laserdiamond.ventureplugin.events;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.events.effects.Components.EffectProfile;
import net.laserdiamond.ventureplugin.events.effects.Managers.EffectManager;
import net.laserdiamond.ventureplugin.stats.Components.StatProfile;
import net.laserdiamond.ventureplugin.stats.Manager.StatProfileManager;
import net.laserdiamond.ventureplugin.tuning.Manager.TuningProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.*;

import java.net.InetSocketAddress;
import java.util.UUID;

public final class PlayerJoinServer implements Listener {

    private final StatProfileManager statProfileManager;
    private final TuningProfileManager tuningProfileManager;
    private final EffectManager effectManager;

    public PlayerJoinServer(VenturePlugin plugin) {
        statProfileManager = plugin.getStatProfileManager();
        tuningProfileManager = plugin.getTunementProfileManager();
        effectManager = plugin.getEffectManager();
    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        /*
         * Output player information to console
         * <p>
         * Determine whether this is a player you want to player in the server or not
         */

        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + player.getName() + " has joined the game");
        InetSocketAddress playerAddress = player.getAddress();
        if (playerAddress != null)
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Host Name: " + ChatColor.DARK_AQUA + playerAddress.getHostName());
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Port: " + ChatColor.DARK_AQUA + playerAddress.getPort());
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Address: " + ChatColor.DARK_AQUA + playerAddress.getAddress());
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Virtual Host: " + ChatColor.DARK_AQUA + player.getVirtualHost());
        } else
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "PLAYER ADDRESS IS NULL");
        }

        // Create health display above player
        ScoreboardManager manager = Bukkit.getScoreboardManager();

        Scoreboard HealthDisplay = manager.getNewScoreboard();
        Objective objective_health_display = HealthDisplay.registerNewObjective("healthdisplay","health", ChatColor.RED + "❤");
        objective_health_display.setDisplaySlot(DisplaySlot.BELOW_NAME);
        player.setScoreboard(HealthDisplay);

        // Set these players as OP
        UUID playeruuid = player.getUniqueId();
        String uuidString = playeruuid.toString();
        if (uuidString.equals("7c20841e-1d63-4dd7-a60b-2afb2f65777a") || uuidString.equals("3491b0ce-ffc6-4ec4-a023-ae6c16a0f2d7")) {
            String username = player.getName();
            player.sendMessage(ChatColor.LIGHT_PURPLE + "Welcome back, " + username);
            player.setOp(true);
        }

        // Create/load profiles player will need on the server
        StatProfile statProfile = statProfileManager.createNewStatProfile(player);

        EffectProfile effectProfile = effectManager.getEffectProfile(player.getUniqueId());
        if (effectProfile == null) {
            effectManager.createNewProfile(player);
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "Player " + player.getName() + "'s effect profile already exists");
        }

    }
}
