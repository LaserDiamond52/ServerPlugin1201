package net.laserdiamond.serverplugin1201.events.effects;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.management.EffectKeys;
import org.bukkit.ChatColor;
import org.bukkit.entity.Mob;
import org.bukkit.scheduler.BukkitRunnable;

public class VulnerableEffect {

    static ServerPlugin1201 plugin = ServerPlugin1201.getInstance();

    @Deprecated
    public static void vulnerable(Mob target, int durationSeconds) {

        EffectBoolean.setEffectBoolean(target, EffectKeys.VULNERABLE, true);

        target.sendMessage("" + ChatColor.DARK_RED + ChatColor.BOLD + ChatColor.MAGIC + "X" + ChatColor.RESET + ChatColor.DARK_GRAY + ChatColor.BOLD + " You're now vulnerable to incoming damage for " + durationSeconds + " seconds " + ChatColor.DARK_RED + ChatColor.BOLD + ChatColor.MAGIC + "X");

        new BukkitRunnable() {

            int i = durationSeconds;
            public void run() {
                i--;
                if (i == 0 || target.isDead()) {
                    EffectBoolean.setEffectBoolean(target, EffectKeys.VULNERABLE, false);
                    target.sendMessage(ChatColor.DARK_RED + "You are no longer vulnerable to incoming damage");
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    @Deprecated
    public static void unVulnerable(Mob target) {

        EffectBoolean.setEffectBoolean(target, EffectKeys.VULNERABLE, false);
        target.sendMessage(ChatColor.DARK_RED + "You are no longer vulnerable to incoming attacks");
    }
}
