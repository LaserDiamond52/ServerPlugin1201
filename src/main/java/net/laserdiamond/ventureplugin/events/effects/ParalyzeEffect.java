package net.laserdiamond.ventureplugin.events.effects;

import net.laserdiamond.ventureplugin.VenturePlugin;
import net.laserdiamond.ventureplugin.util.EffectKeys;
import net.laserdiamond.ventureplugin.util.messages.Messages;
import org.bukkit.ChatColor;
import org.bukkit.entity.Mob;
import org.bukkit.scheduler.BukkitRunnable;

public class ParalyzeEffect {

    static VenturePlugin plugin = VenturePlugin.getInstance();

    @Deprecated
    public static void paralyze(Mob mob, int durationSeconds) {

        if (!EffectBoolean.hasEffect(mob, EffectKeys.PARALYZE)) {

            EffectBoolean.setEffectBoolean(mob, EffectKeys.PARALYZE, true);
        }

        mob.sendMessage(Messages.paralyzeMessage(durationSeconds));

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
