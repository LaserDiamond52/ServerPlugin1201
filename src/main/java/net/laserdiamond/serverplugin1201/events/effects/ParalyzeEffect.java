package net.laserdiamond.serverplugin1201.events.effects;

import net.laserdiamond.serverplugin1201.ServerPlugin1201;
import net.laserdiamond.serverplugin1201.Management.EffectKeys;
import net.laserdiamond.serverplugin1201.Management.messages.messages;
import org.bukkit.ChatColor;
import org.bukkit.entity.Mob;
import org.bukkit.scheduler.BukkitRunnable;

public class ParalyzeEffect {

    static ServerPlugin1201 plugin = ServerPlugin1201.getInstance();

    @Deprecated
    public static void paralyze(Mob mob, int durationSeconds) {

        if (!EffectBoolean.hasEffect(mob, EffectKeys.PARALYZE)) {

            EffectBoolean.setEffectBoolean(mob, EffectKeys.PARALYZE, true);
        }

        mob.sendMessage(messages.paralyzeMessage(durationSeconds));

        new BukkitRunnable() {
            int i = durationSeconds;

            public void run() {
                i--;
                if (i == 0 || mob.isDead()) {
                    EffectBoolean.setEffectBoolean(mob, EffectKeys.PARALYZE, false);
                    mob.sendMessage(ChatColor.RED + "You are no longer paralyzed");
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    @Deprecated
    public static void unparalyzeMob(Mob mob) {
        EffectBoolean.setEffectBoolean(mob, EffectKeys.PARALYZE, false);
        mob.sendMessage(ChatColor.RED + "You are no longer paralyzed");
    }


}
