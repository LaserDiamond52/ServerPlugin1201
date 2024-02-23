package net.laserdiamond.serverplugin1201.events;

import com.destroystokyo.paper.event.entity.EntityKnockbackByEntityEvent;
import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.events.effects.Components.EffectProfile;
import net.laserdiamond.serverplugin1201.events.effects.Managers.EffectManager;
import net.laserdiamond.serverplugin1201.stats.Manager.StatProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.UUID;

public class PlayerJoinServer implements Listener {

    private ServerPlugin1201 plugin;
    private StatProfileManager statProfileManager;
    private EffectManager effectManager;

    public PlayerJoinServer(ServerPlugin1201 plugin) {
        this.plugin = plugin;
        statProfileManager = plugin.getStatProfileManager();
        effectManager = plugin.getEffectManager();
    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();


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
        statProfileManager.createNewStatProfile(player);

        EffectProfile effectProfile = effectManager.getEffectProfile(player.getUniqueId());
        if (effectProfile == null) {
            effectManager.createNewProfile(player);
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "Player " + player.getName() + "'s effect profile already exists");
        }

    }
}
